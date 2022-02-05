package org.ylzl.eden.demo.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;
import org.ylzl.eden.demo.api.UserService;
import org.ylzl.eden.demo.api.dto.UserDTO;
import org.ylzl.eden.demo.api.dto.UserPageQuery;
import org.ylzl.eden.demo.api.dto.UserVO;
import org.ylzl.eden.demo.web.constant.ApiConstant;
import org.ylzl.eden.spring.framework.cola.dto.PageResponse;
import org.ylzl.eden.spring.framework.cola.dto.Response;
import org.ylzl.eden.spring.framework.cola.dto.SingleResponse;

import javax.validation.Valid;

/**
 * 用户控制器
 *
 * @author gyl
 * @since 2.4.x
 */
@Slf4j
@RequestMapping(ApiConstant.WEB_API_PATH + "/users")
@RestController
public class UserController {

	private final UserService userService;

	public UserController(@Qualifier("userService") UserService userService) {
		this.userService = userService;
	}

	/**
	 * 创建用户
	 *
	 * @param dto
	 * @return
	 */
	@PostMapping
	public Response createUser(@Valid @RequestBody UserDTO dto) {
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
	public Response modifyUser(@PathVariable Long id, @Valid @RequestBody UserDTO dto) {
		return userService.modifyUser(id, dto);
	}

	/**
	 * 删除用户
	 *
	 * @param id
	 * @return
	 */
	@DeleteMapping("/{id}")
	public Response removeUser(@PathVariable Long id) {
		return userService.removeUser(id);
	}

	/**
	 * 根据主键获取用户信息
	 *
	 * @param id
	 * @return
	 */
	@GetMapping("/{id}")
	public SingleResponse<UserVO> getUserById(@PathVariable Long id) {
		return userService.getUserById(id);
	}

	/**
	 * 根据分页获取用户列表
	 *
	 * @param query
	 * @return
	 */
	@GetMapping
	public PageResponse<UserVO> listUserByPage(@Valid @ModelAttribute UserPageQuery query) {
		return userService.listUserByPage(query);
	}
}
