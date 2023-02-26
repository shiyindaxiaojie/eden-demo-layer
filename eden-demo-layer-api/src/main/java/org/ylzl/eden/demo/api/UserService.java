package org.ylzl.eden.demo.api;

import org.ylzl.eden.demo.api.dto.UserPageQuery;
import org.ylzl.eden.demo.api.dto.UserRequestDTO;
import org.ylzl.eden.demo.api.dto.UserResponseDTO;
import org.ylzl.eden.spring.framework.dto.PageResult;
import org.ylzl.eden.spring.framework.dto.Result;
import org.ylzl.eden.spring.framework.dto.SingleResult;

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
}
