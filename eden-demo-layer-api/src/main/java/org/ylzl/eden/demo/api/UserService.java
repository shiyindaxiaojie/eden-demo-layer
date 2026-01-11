package org.ylzl.eden.demo.api;

import org.ylzl.eden.demo.api.dto.*;
import org.ylzl.eden.spring.framework.dto.MultiResult;
import org.ylzl.eden.spring.framework.dto.PageResult;
import org.ylzl.eden.spring.framework.dto.Result;
import org.ylzl.eden.spring.framework.dto.SingleResult;

import java.util.List;

/**
 * 用户业务逻辑接口
 *
 * @author <a href="mailto:shiyindaxiaojie@gmail.com">gyl</a>
 * @since 2.4.13
 */
public interface UserService {

	/**
	 * 创建用户
	 *
	 * @param dto
	 */
	Result createUser(UserRequestDTO dto);

	/**
	 * 修改用户
	 *
	 * @param id
	 * @param dto
	 */
	Result modifyUser(Long id, UserRequestDTO dto);

	/**
	 * 删除用户
	 *
	 * @param id
	 */
	Result removeUser(Long id);

	/**
	 * 获取用户信息
	 *
	 * @param id
	 * @return
	 */
	SingleResult<UserResponseDTO> getUserById(Long id);

	/**
	 * 获取用户分页
	 *
	 * @param query
	 * @return
	 */
	PageResult<UserResponseDTO> listUserByPage(UserPageQuery query);

	// ========== RBAC 扩展方法 ==========

	/**
	 * 激活用户
	 *
	 * @param id 用户ID
	 * @return 操作结果
	 */
	Result activateUser(Long id);

	/**
	 * 锁定用户
	 *
	 * @param id 用户ID
	 * @return 操作结果
	 */
	Result lockUser(Long id);

	/**
	 * 解锁用户
	 *
	 * @param id 用户ID
	 * @return 操作结果
	 */
	Result unlockUser(Long id);

	/**
	 * 禁用用户
	 *
	 * @param id 用户ID
	 * @return 操作结果
	 */
	Result disableUser(Long id);

	/**
	 * 为用户分配角色
	 *
	 * @param userId  用户ID
	 * @param roleIds 角色ID列表
	 * @return 操作结果
	 */
	Result assignRoles(Long userId, List<Long> roleIds);

	/**
	 * 获取用户角色列表
	 *
	 * @param userId 用户ID
	 * @return 角色列表
	 */
	MultiResult<RoleResponseDTO> getUserRoles(Long userId);

	/**
	 * 获取用户权限列表
	 *
	 * @param userId 用户ID
	 * @return 权限列表
	 */
	MultiResult<PermissionResponseDTO> getUserPermissions(Long userId);

	/**
	 * 获取用户菜单树
	 *
	 * @param userId 用户ID
	 * @return 菜单树
	 */
	MultiResult<MenuTreeDTO> getUserMenus(Long userId);
}
