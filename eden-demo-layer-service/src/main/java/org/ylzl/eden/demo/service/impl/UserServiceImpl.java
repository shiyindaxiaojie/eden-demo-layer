package org.ylzl.eden.demo.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.Page;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.ylzl.eden.demo.api.UserService;
import org.ylzl.eden.demo.api.dto.UserPageQuery;
import org.ylzl.eden.demo.api.dto.UserRequestDTO;
import org.ylzl.eden.demo.api.dto.UserResponseDTO;
import org.ylzl.eden.demo.dao.UserDAO;
import org.ylzl.eden.demo.dao.repository.mybatis.dataobject.UserDO;
import org.ylzl.eden.demo.dao.repository.mybatis.mapper.UserMapper;
import org.ylzl.eden.demo.service.converter.UserConvertor;
import org.ylzl.eden.spring.framework.cola.dto.PageResponse;
import org.ylzl.eden.spring.framework.cola.dto.Response;
import org.ylzl.eden.spring.framework.cola.dto.SingleResponse;
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

	private final UserConvertor userConvertor;

	/**
	 * 创建用户
	 *
	 * @param dto
	 * @return
	 */
	@Override
	public Response createUser(UserRequestDTO dto) {
		UserDO userDO = userConvertor.dtoToDataObject(dto);
		userDAO.save(userDO);
		return Response.buildSuccess();
	}

	/**
	 * 修改用户
	 *
	 * @param id
	 * @param dto
	 */
	@Override
	public Response modifyUser(Long id, UserRequestDTO dto) {
		UserDO userDO = userDAO.findById(id);
		ClientAssert.notNull(userDO, "USER-FOUND-404");

		userConvertor.updateDataObjectFromDTO(dto, userDO);
		userDAO.updateById(userDO);
		return Response.buildSuccess();
	}

	/**
	 * 删除用户
	 *
	 * @param id
	 */
	@Override
	public Response removeUser(Long id) {
		ClientAssert.isTrue(userDAO.deleteById(id), "USER-FOUND-404");
		return Response.buildSuccess();
	}

	/**
	 * 获取用户信息
	 *
	 * @param id
	 * @return
	 */
	@Override
	public SingleResponse<UserResponseDTO> getUserById(Long id) {
		UserDO userDO = userDAO.findById(id);
		ClientAssert.notNull(userDO, "USER-FOUND-404");
		return SingleResponse.of(userConvertor.dataObjectToVO(userDO));
	}

	/**
	 * 获取用户分页
	 *
	 * @param query
	 * @return
	 */
	@Override
	public PageResponse<UserResponseDTO> listUserByPage(UserPageQuery query) {
		Page<UserDO> page = userDAO.findByPage(query);
		List<UserResponseDTO> userVOList = userConvertor.dataObjectListToVOList(page.getResult());
		return PageResponse.of(userVOList,
			Integer.parseInt(String.valueOf(page.getTotal())),
			query.getPageSize(), query.getPageIndex());
	}
}
