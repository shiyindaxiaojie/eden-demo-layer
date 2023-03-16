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
import org.ylzl.eden.demo.dao.database.dataobject.UserDO;
import org.ylzl.eden.demo.dao.database.mapper.UserMapper;
import org.ylzl.eden.demo.service.converter.UserConvertor;
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

	private final UserConvertor userConvertor;

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
	public Result removeUser(Long id) {
		ClientAssert.isTrue(userDAO.deleteById(id), "USER-FOUND-404");
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
		log.info("Service 调用");
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
}
