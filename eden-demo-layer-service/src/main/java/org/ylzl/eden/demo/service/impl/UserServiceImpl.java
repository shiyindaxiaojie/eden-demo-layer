package org.ylzl.eden.demo.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.Page;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.ylzl.eden.demo.api.UserService;
import org.ylzl.eden.demo.api.dto.*;
import org.ylzl.eden.demo.dao.UserDAO;
import org.ylzl.eden.demo.dao.database.dataobject.MenuDO;
import org.ylzl.eden.demo.dao.database.dataobject.PermissionDO;
import org.ylzl.eden.demo.dao.database.dataobject.RoleDO;
import org.ylzl.eden.demo.dao.database.dataobject.UserDO;
import org.ylzl.eden.demo.dao.database.mapper.UserMapper;
import org.ylzl.eden.demo.manager.dao.RbacManager;
import org.ylzl.eden.demo.service.converter.MenuConvertor;
import org.ylzl.eden.demo.service.converter.PermissionConvertor;
import org.ylzl.eden.demo.service.converter.RoleConvertor;
import org.ylzl.eden.demo.service.converter.UserConvertor;
import org.ylzl.eden.spring.framework.dto.MultiResult;
import org.ylzl.eden.spring.framework.dto.PageResult;
import org.ylzl.eden.spring.framework.dto.Result;
import org.ylzl.eden.spring.framework.dto.SingleResult;
import org.ylzl.eden.spring.framework.error.ClientAssert;

import java.util.List;

/**
 * 用户业务逻辑实现
 *
 * @author <a href="mailto:shiyindaxiaojie@gmail.com">gyl</a>
 * @since 2.4.13
 */
@RequiredArgsConstructor
@Slf4j
@Service("userService")
public class UserServiceImpl extends ServiceImpl<UserMapper, UserDO> implements UserService {

	private final UserDAO userDAO;
	private final RbacManager rbacManager;
	private final UserConvertor userConvertor;
	private final RoleConvertor roleConvertor;
	private final PermissionConvertor permissionConvertor;
	private final MenuConvertor menuConvertor;

	/**
	 * 创建用户
	 *
	 * @param dto
	 * @return
	 */
	@Override
	public Result createUser(UserRequestDTO dto) {
		UserDO userDO = userConvertor.dtoToDataObject(dto);
		userDAO.save(userDO);
		return Result.buildSuccess();
	}

	/**
	 * 修改用户
	 *
	 * @param id
	 * @param dto
	 */
	@Override
	public Result modifyUser(Long id, UserRequestDTO dto) {
		UserDO userDO = userDAO.findById(id);
		ClientAssert.notNull(userDO, "USER-FOUND-404");

		userConvertor.updateDataObjectFromDTO(dto, userDO);
		userDAO.updateById(userDO);
		return Result.buildSuccess();
	}

	/**
	 * 删除用户
	 *
	 * @param id
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public Result removeUser(Long id) {
		ClientAssert.isTrue(userDAO.deleteById(id), "USER-FOUND-404");
		// 删除用户关联关系
		rbacManager.deleteUserRelations(id);
		return Result.buildSuccess();
	}

	/**
	 * 获取用户信息
	 *
	 * @param id
	 * @return
	 */
	@Override
	public SingleResult<UserResponseDTO> getUserById(Long id) {
		UserDO userDO = userDAO.findById(id);
		ClientAssert.notNull(userDO, "USER-FOUND-404");
		return SingleResult.build(userConvertor.dataObjectToVO(userDO));
	}

	/**
	 * 获取用户分页
	 *
	 * @param query
	 * @return
	 */
	@Override
	public PageResult<UserResponseDTO> listUserByPage(UserPageQuery query) {
		Page<UserDO> page = userDAO.findByPage(query);
		List<UserResponseDTO> userVOList = userConvertor.dataObjectListToVOList(page.getResult());
		return PageResult.build(userVOList, Integer.parseInt(String.valueOf(page.getTotal())));
	}

	// ========== RBAC 扩展方法 ==========

	@Override
	public Result activateUser(Long id) {
		UserDO userDO = userDAO.findById(id);
		ClientAssert.notNull(userDO, "USER-001");
		ClientAssert.isTrue(userDO.getStatus() == null || userDO.getStatus() != 1, "USER-002");

		userDO.setStatus(1);
		userDAO.updateById(userDO);
		return Result.buildSuccess();
	}

	@Override
	public Result lockUser(Long id) {
		UserDO userDO = userDAO.findById(id);
		ClientAssert.notNull(userDO, "USER-001");
		ClientAssert.isTrue(userDO.getStatus() == null || userDO.getStatus() != 2, "USER-003");

		userDO.setStatus(2);
		userDAO.updateById(userDO);
		return Result.buildSuccess();
	}

	@Override
	public Result unlockUser(Long id) {
		UserDO userDO = userDAO.findById(id);
		ClientAssert.notNull(userDO, "USER-001");

		userDO.setStatus(1);
		userDAO.updateById(userDO);
		return Result.buildSuccess();
	}

	@Override
	public Result disableUser(Long id) {
		UserDO userDO = userDAO.findById(id);
		ClientAssert.notNull(userDO, "USER-001");
		ClientAssert.isTrue(userDO.getStatus() == null || userDO.getStatus() != 0, "USER-004");

		userDO.setStatus(0);
		userDAO.updateById(userDO);
		return Result.buildSuccess();
	}

	@Override
	public Result assignRoles(Long userId, List<Long> roleIds) {
		UserDO userDO = userDAO.findById(userId);
		ClientAssert.notNull(userDO, "USER-001");

		rbacManager.assignRolesToUser(userId, roleIds);
		return Result.buildSuccess();
	}

	@Override
	public MultiResult<RoleResponseDTO> getUserRoles(Long userId) {
		List<RoleDO> roles = rbacManager.getUserRoles(userId);
		return MultiResult.build(roleConvertor.dataObjectListToDTOList(roles));
	}

	@Override
	public MultiResult<PermissionResponseDTO> getUserPermissions(Long userId) {
		List<PermissionDO> permissions = rbacManager.getUserPermissions(userId);
		return MultiResult.build(permissionConvertor.dataObjectListToDTOList(permissions));
	}

	@Override
	public MultiResult<MenuTreeDTO> getUserMenus(Long userId) {
		List<MenuDO> menus = rbacManager.getUserMenus(userId);
		List<MenuTreeDTO> tree = menuConvertor.buildMenuTree(menus);
		return MultiResult.build(tree);
	}
}
