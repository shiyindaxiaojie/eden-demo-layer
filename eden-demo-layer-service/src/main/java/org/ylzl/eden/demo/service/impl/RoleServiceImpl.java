package org.ylzl.eden.demo.service.impl;

import com.github.pagehelper.Page;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.ylzl.eden.demo.api.RoleService;
import org.ylzl.eden.demo.api.dto.*;
import org.ylzl.eden.demo.dao.RoleDAO;
import org.ylzl.eden.demo.dao.database.dataobject.MenuDO;
import org.ylzl.eden.demo.dao.database.dataobject.PermissionDO;
import org.ylzl.eden.demo.dao.database.dataobject.RoleDO;
import org.ylzl.eden.demo.manager.dao.RbacManager;
import org.ylzl.eden.demo.service.converter.MenuConvertor;
import org.ylzl.eden.demo.service.converter.PermissionConvertor;
import org.ylzl.eden.demo.service.converter.RoleConvertor;
import org.ylzl.eden.spring.framework.dto.MultiResult;
import org.ylzl.eden.spring.framework.dto.PageResult;
import org.ylzl.eden.spring.framework.dto.Result;
import org.ylzl.eden.spring.framework.dto.SingleResult;
import org.ylzl.eden.spring.framework.error.ClientAssert;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 角色业务逻辑实现
 *
 * @author <a href="mailto:shiyindaxiaojie@gmail.com">gyl</a>
 * @since 2.4.14
 */
@RequiredArgsConstructor
@Slf4j
@Service("roleService")
public class RoleServiceImpl implements RoleService {

	private final RoleDAO roleDAO;
	private final RbacManager rbacManager;
	private final RoleConvertor roleConvertor;
	private final PermissionConvertor permissionConvertor;
	private final MenuConvertor menuConvertor;

	@Override
	public Result createRole(RoleRequestDTO dto) {
		// 检查编码唯一性
		RoleDO existing = roleDAO.findByCode(dto.getCode());
		ClientAssert.isNull(existing, "ROLE-001");

		RoleDO roleDO = roleConvertor.dtoToDataObject(dto);
		roleDO.setStatus(1);
		roleDO.setCreatedDate(LocalDateTime.now());
		roleDAO.save(roleDO);
		return Result.buildSuccess();
	}

	@Override
	public Result modifyRole(Long id, RoleRequestDTO dto) {
		RoleDO roleDO = roleDAO.findById(id);
		ClientAssert.notNull(roleDO, "ROLE-002");

		roleConvertor.updateDataObjectFromDTO(dto, roleDO);
		roleDO.setLastModifiedDate(LocalDateTime.now());
		roleDAO.updateById(roleDO);
		return Result.buildSuccess();
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public Result removeRole(Long id) {
		RoleDO roleDO = roleDAO.findById(id);
		ClientAssert.notNull(roleDO, "ROLE-002");

		// 级联删除关联关系
		rbacManager.deleteRoleRelations(id);
		roleDAO.deleteById(id);
		return Result.buildSuccess();
	}

	@Override
	public Result enableRole(Long id) {
		RoleDO roleDO = roleDAO.findById(id);
		ClientAssert.notNull(roleDO, "ROLE-002");
		ClientAssert.isTrue(roleDO.getStatus() != 1, "ROLE-003");

		roleDO.setStatus(1);
		roleDO.setLastModifiedDate(LocalDateTime.now());
		roleDAO.updateById(roleDO);
		return Result.buildSuccess();
	}

	@Override
	public Result disableRole(Long id) {
		RoleDO roleDO = roleDAO.findById(id);
		ClientAssert.notNull(roleDO, "ROLE-002");
		ClientAssert.isTrue(roleDO.getStatus() != 0, "ROLE-004");

		roleDO.setStatus(0);
		roleDO.setLastModifiedDate(LocalDateTime.now());
		roleDAO.updateById(roleDO);
		return Result.buildSuccess();
	}

	@Override
	public SingleResult<RoleResponseDTO> getRoleById(Long id) {
		RoleDO roleDO = roleDAO.findById(id);
		ClientAssert.notNull(roleDO, "ROLE-002");
		return SingleResult.build(roleConvertor.dataObjectToDTO(roleDO));
	}

	@Override
	public PageResult<RoleResponseDTO> listRoleByPage(RolePageQuery query) {
		Page<RoleDO> page = roleDAO.findByPage(
			query.getPageIndex(), query.getPageSize(),
			query.getCode(), query.getName(), query.getStatus());
		List<RoleResponseDTO> dtoList = roleConvertor.dataObjectListToDTOList(page.getResult());
		return PageResult.build(dtoList, Integer.parseInt(String.valueOf(page.getTotal())));
	}

	@Override
	public Result assignPermissions(Long roleId, List<Long> permissionIds) {
		RoleDO roleDO = roleDAO.findById(roleId);
		ClientAssert.notNull(roleDO, "ROLE-002");

		rbacManager.assignPermissionsToRole(roleId, permissionIds);
		return Result.buildSuccess();
	}

	@Override
	public Result assignMenus(Long roleId, List<Long> menuIds) {
		RoleDO roleDO = roleDAO.findById(roleId);
		ClientAssert.notNull(roleDO, "ROLE-002");

		rbacManager.assignMenusToRole(roleId, menuIds);
		return Result.buildSuccess();
	}

	@Override
	public MultiResult<PermissionResponseDTO> getRolePermissions(Long roleId) {
		List<PermissionDO> permissions = rbacManager.getRolePermissions(roleId);
		return MultiResult.build(permissionConvertor.dataObjectListToDTOList(permissions));
	}

	@Override
	public MultiResult<MenuTreeDTO> getRoleMenus(Long roleId) {
		List<MenuDO> menus = rbacManager.getRoleMenus(roleId);
		List<MenuTreeDTO> tree = menuConvertor.buildMenuTree(menus);
		return MultiResult.build(tree);
	}
}
