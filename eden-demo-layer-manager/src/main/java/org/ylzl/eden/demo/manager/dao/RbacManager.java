/*
 * Copyright 2012-2019 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.ylzl.eden.demo.manager.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.ylzl.eden.demo.dao.*;
import org.ylzl.eden.demo.dao.database.dataobject.MenuDO;
import org.ylzl.eden.demo.dao.database.dataobject.PermissionDO;
import org.ylzl.eden.demo.dao.database.dataobject.RoleDO;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * RBAC 通用业务处理层
 * <p>《阿里巴巴Java开发手册》Manager 层职责：</p>
 * <ul>
 *     <li>与 DAO 层交互，对多个 DAO 的组合复用</li>
 *     <li>复杂业务场景下的数据访问编排</li>
 *     <li>跨表、跨数据源的数据操作封装</li>
 *     <li>通用能力下沉，避免 Service 层代码膨胀</li>
 * </ul>
 *
 * @author <a href="mailto:shiyindaxiaojie@gmail.com">gyl</a>
 * @since 2.4.14
 */
@RequiredArgsConstructor
@Service
public class RbacManager {

	private final UserRoleDAO userRoleDAO;
	private final RolePermissionDAO rolePermissionDAO;
	private final RoleMenuDAO roleMenuDAO;
	private final RoleDAO roleDAO;
	private final PermissionDAO permissionDAO;
	private final MenuDAO menuDAO;

	// ========== 用户角色管理 ==========

	/**
	 * 为用户分配角色
	 * <p>先删除用户现有角色关联，再批量插入新的角色关联</p>
	 *
	 * @param userId  用户ID
	 * @param roleIds 角色ID列表
	 * @return 是否成功
	 */
	@Transactional(rollbackFor = Exception.class)
	public boolean assignRolesToUser(Long userId, List<Long> roleIds) {
		userRoleDAO.deleteByUserId(userId);
		if (roleIds == null || roleIds.isEmpty()) {
			return true;
		}
		return userRoleDAO.saveAll(userId, roleIds);
	}

	/**
	 * 获取用户的角色列表
	 *
	 * @param userId 用户ID
	 * @return 角色列表
	 */
	public List<RoleDO> getUserRoles(Long userId) {
		List<Long> roleIds = userRoleDAO.findRoleIdsByUserId(userId);
		if (roleIds.isEmpty()) {
			return new ArrayList<>();
		}
		return roleDAO.findByIds(roleIds);
	}

	// ========== 角色权限管理 ==========

	/**
	 * 为角色分配权限
	 * <p>先删除角色现有权限关联，再批量插入新的权限关联</p>
	 *
	 * @param roleId        角色ID
	 * @param permissionIds 权限ID列表
	 * @return 是否成功
	 */
	@Transactional(rollbackFor = Exception.class)
	public boolean assignPermissionsToRole(Long roleId, List<Long> permissionIds) {
		rolePermissionDAO.deleteByRoleId(roleId);
		if (permissionIds == null || permissionIds.isEmpty()) {
			return true;
		}
		return rolePermissionDAO.saveAll(roleId, permissionIds);
	}

	/**
	 * 获取角色的权限列表
	 *
	 * @param roleId 角色ID
	 * @return 权限列表
	 */
	public List<PermissionDO> getRolePermissions(Long roleId) {
		List<Long> permissionIds = rolePermissionDAO.findPermissionIdsByRoleId(roleId);
		if (permissionIds.isEmpty()) {
			return new ArrayList<>();
		}
		return permissionDAO.findByIds(permissionIds);
	}

	// ========== 角色菜单管理 ==========

	/**
	 * 为角色分配菜单
	 * <p>先删除角色现有菜单关联，再批量插入新的菜单关联</p>
	 *
	 * @param roleId  角色ID
	 * @param menuIds 菜单ID列表
	 * @return 是否成功
	 */
	@Transactional(rollbackFor = Exception.class)
	public boolean assignMenusToRole(Long roleId, List<Long> menuIds) {
		roleMenuDAO.deleteByRoleId(roleId);
		if (menuIds == null || menuIds.isEmpty()) {
			return true;
		}
		return roleMenuDAO.saveAll(roleId, menuIds);
	}

	/**
	 * 获取角色的菜单列表
	 *
	 * @param roleId 角色ID
	 * @return 菜单列表
	 */
	public List<MenuDO> getRoleMenus(Long roleId) {
		List<Long> menuIds = roleMenuDAO.findMenuIdsByRoleId(roleId);
		if (menuIds.isEmpty()) {
			return new ArrayList<>();
		}
		return menuDAO.findByIds(menuIds);
	}

	// ========== 用户权限聚合 ==========

	/**
	 * 获取用户的所有权限（聚合用户所有角色的权限）
	 * <p>多个 DAO 组合复用的典型场景：</p>
	 * <ol>
	 *     <li>查询用户的角色列表</li>
	 *     <li>查询所有角色的权限列表</li>
	 *     <li>去重后返回</li>
	 * </ol>
	 *
	 * @param userId 用户ID
	 * @return 权限列表（已去重）
	 */
	public List<PermissionDO> getUserPermissions(Long userId) {
		List<Long> roleIds = userRoleDAO.findRoleIdsByUserId(userId);
		if (roleIds.isEmpty()) {
			return new ArrayList<>();
		}
		List<Long> permissionIds = rolePermissionDAO.findPermissionIdsByRoleIds(roleIds);
		if (permissionIds.isEmpty()) {
			return new ArrayList<>();
		}
		// 去重
		Set<Long> uniqueIds = new HashSet<>(permissionIds);
		return permissionDAO.findByIds(new ArrayList<>(uniqueIds));
	}

	/**
	 * 获取用户的所有菜单（聚合用户所有角色的菜单）
	 * <p>多个 DAO 组合复用的典型场景：</p>
	 * <ol>
	 *     <li>查询用户的角色列表</li>
	 *     <li>查询所有角色的菜单列表</li>
	 *     <li>去重后返回</li>
	 * </ol>
	 *
	 * @param userId 用户ID
	 * @return 菜单列表（已去重）
	 */
	public List<MenuDO> getUserMenus(Long userId) {
		List<Long> roleIds = userRoleDAO.findRoleIdsByUserId(userId);
		if (roleIds.isEmpty()) {
			return new ArrayList<>();
		}
		List<Long> menuIds = roleMenuDAO.findMenuIdsByRoleIds(roleIds);
		if (menuIds.isEmpty()) {
			return new ArrayList<>();
		}
		// 去重
		Set<Long> uniqueIds = new HashSet<>(menuIds);
		return menuDAO.findByIds(new ArrayList<>(uniqueIds));
	}

	// ========== 级联删除 ==========

	/**
	 * 删除角色的所有关联关系
	 * <p>级联删除：用户-角色关联、角色-权限关联、角色-菜单关联</p>
	 *
	 * @param roleId 角色ID
	 */
	@Transactional(rollbackFor = Exception.class)
	public void deleteRoleRelations(Long roleId) {
		userRoleDAO.deleteByRoleId(roleId);
		rolePermissionDAO.deleteByRoleId(roleId);
		roleMenuDAO.deleteByRoleId(roleId);
	}

	/**
	 * 删除权限的所有关联关系
	 * <p>级联删除：角色-权限关联</p>
	 *
	 * @param permissionId 权限ID
	 */
	@Transactional(rollbackFor = Exception.class)
	public void deletePermissionRelations(Long permissionId) {
		rolePermissionDAO.deleteByPermissionId(permissionId);
	}

	/**
	 * 删除菜单的所有关联关系
	 * <p>级联删除：角色-菜单关联，并递归删除子菜单</p>
	 *
	 * @param menuId 菜单ID
	 */
	@Transactional(rollbackFor = Exception.class)
	public void deleteMenuRelations(Long menuId) {
		roleMenuDAO.deleteByMenuId(menuId);
		// 递归删除子菜单
		List<MenuDO> children = menuDAO.findByParentId(menuId);
		for (MenuDO child : children) {
			deleteMenuRelations(child.getId());
			menuDAO.deleteById(child.getId());
		}
	}

	/**
	 * 删除用户的所有关联关系
	 * <p>级联删除：用户-角色关联</p>
	 *
	 * @param userId 用户ID
	 */
	@Transactional(rollbackFor = Exception.class)
	public void deleteUserRelations(Long userId) {
		userRoleDAO.deleteByUserId(userId);
	}
}
