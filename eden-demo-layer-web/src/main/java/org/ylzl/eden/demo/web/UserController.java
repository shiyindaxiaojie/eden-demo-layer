package org.ylzl.eden.demo.web;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.ylzl.eden.demo.api.UserService;
import org.ylzl.eden.demo.api.dto.*;
import org.ylzl.eden.demo.web.constant.API;
import org.ylzl.eden.spring.framework.dto.MultiResult;
import org.ylzl.eden.spring.framework.dto.PageResult;
import org.ylzl.eden.spring.framework.dto.Result;
import org.ylzl.eden.spring.framework.dto.SingleResult;

import javax.validation.Valid;
import java.util.List;

/**
 * 用户控制器
 *
 * @author <a href="mailto:shiyindaxiaojie@gmail.com">gyl</a>
 * @since 2.4.13
 */
@RequiredArgsConstructor
@Slf4j
@RequestMapping(API.WEB_API_PATH + "/users")
@RestController
public class UserController {

	private final UserService userService;

	/**
	 * 创建用户
	 *
	 * @param dto
	 * @return
	 */
	@PostMapping
	public Result createUser(@Valid @RequestBody UserRequestDTO dto) {
		return userService.createUser(dto);
	}

	/**
	 * 修改用户
	 *
	 * @param id
	 * @param dto
	 * @return
	 */
	@PutMapping("/{id}")
	public Result modifyUser(@PathVariable Long id, @Valid @RequestBody UserRequestDTO dto) {
		return userService.modifyUser(id, dto);
	}

	/**
	 * 删除用户
	 *
	 * @param id
	 * @return
	 */
	@DeleteMapping("/{id}")
	public Result removeUser(@PathVariable Long id) {
		return userService.removeUser(id);
	}

	/**
	 * 根据主键获取用户信息
	 *
	 * @param id
	 * @return
	 */
	@GetMapping("/{id}")
	public SingleResult<UserResponseDTO> getUserById(@PathVariable Long id) {
		return userService.getUserById(id);
	}

	/**
	 * 根据分页获取用户列表
	 *
	 * @param query
	 * @return
	 */
	@GetMapping
	public PageResult<UserResponseDTO> listUserByPage(@Valid @ModelAttribute UserPageQuery query) {
		return userService.listUserByPage(query);
	}

	// ========== RBAC 扩展接口 ==========

	@PutMapping("/{id}/activate")
	public Result activateUser(@PathVariable Long id) {
		return userService.activateUser(id);
	}

	@PutMapping("/{id}/lock")
	public Result lockUser(@PathVariable Long id) {
		return userService.lockUser(id);
	}

	@PutMapping("/{id}/unlock")
	public Result unlockUser(@PathVariable Long id) {
		return userService.unlockUser(id);
	}

	@PutMapping("/{id}/disable")
	public Result disableUser(@PathVariable Long id) {
		return userService.disableUser(id);
	}

	@PutMapping("/{id}/roles")
	public Result assignRoles(@PathVariable Long id, @RequestBody List<Long> roleIds) {
		return userService.assignRoles(id, roleIds);
	}

	@GetMapping("/{id}/roles")
	public MultiResult<RoleResponseDTO> getUserRoles(@PathVariable Long id) {
		return userService.getUserRoles(id);
	}

	@GetMapping("/{id}/permissions")
	public MultiResult<PermissionResponseDTO> getUserPermissions(@PathVariable Long id) {
		return userService.getUserPermissions(id);
	}

	@GetMapping("/{id}/menus")
	public MultiResult<MenuTreeDTO> getUserMenus(@PathVariable Long id) {
		return userService.getUserMenus(id);
	}
}
