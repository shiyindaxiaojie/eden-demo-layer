package org.ylzl.eden.demo.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.Page;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.ylzl.eden.demo.api.UserService;
import org.ylzl.eden.demo.api.dto.UserDTO;
import org.ylzl.eden.demo.api.dto.UserPageQuery;
import org.ylzl.eden.demo.api.dto.UserVO;
import org.ylzl.eden.demo.dao.UserDAO;
import org.ylzl.eden.demo.dao.repository.mybatis.dataobject.UserDO;
import org.ylzl.eden.demo.dao.repository.mybatis.mapper.UserMapper;
import org.ylzl.eden.demo.service.converter.UserConvertor;
import org.ylzl.eden.spring.framework.cola.catchlog.annotation.CatchLog;
import org.ylzl.eden.spring.framework.cola.dto.PageResponse;
import org.ylzl.eden.spring.framework.cola.dto.Response;
import org.ylzl.eden.spring.framework.cola.dto.SingleResponse;
import org.ylzl.eden.spring.framework.cola.exception.ClientErrorType;

import java.util.List;

/**
 * 用户业务逻辑实现
 *
 * @author <a href="mailto:shiyindaxiaojie@gmail.com">gyl</a>
 * @since 2.4.x
 */
@CatchLog
@Slf4j
@Service("userService")
public class UserServiceImpl extends ServiceImpl<UserMapper, UserDO> implements UserService {

	private final UserDAO userDAO;

	public UserServiceImpl(UserDAO userDAO) {
		this.userDAO = userDAO;
	}

	/**
	 * 创建用户
	 *
	 * @param dto
	 * @return
	 */
	@Override
	public Response createUser(UserDTO dto) {
		UserDO userDO = UserConvertor.INSTANCE.dtoToDataObject(dto);
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
	public Response modifyUser(Long id, UserDTO dto) {
		UserDO userDO = userDAO.findById(id);
		ClientErrorType.A0201.notNull(userDO);

		UserConvertor.INSTANCE.updateDataObjectFromDTO(dto, userDO);
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
		ClientErrorType.A0201.isTrue(userDAO.deleteById(id));
		return Response.buildSuccess();
	}

	/**
	 * 获取用户信息
	 *
	 * @param id
	 * @return
	 */
	@Override
	public SingleResponse<UserVO> getUserById(Long id) {
		UserDO userDO = userDAO.findById(id);
		ClientErrorType.A0201.notNull(userDO);
		return SingleResponse.of(UserConvertor.INSTANCE.dataObjectToVO(userDO));
	}

	/**
	 * 获取用户分页
	 *
	 * @param query
	 * @return
	 */
	@Override
	public PageResponse<UserVO> listUserByPage(UserPageQuery query) {
		Page<UserDO> page = userDAO.findByPage(query);
		List<UserVO> userVOList = UserConvertor.INSTANCE.dataObjectListToVOList(page.getResult());
		return PageResponse.of(userVOList,
			Integer.parseInt(String.valueOf(page.getTotal())),
			query.getPageSize(), query.getPageIndex());
	}
}
