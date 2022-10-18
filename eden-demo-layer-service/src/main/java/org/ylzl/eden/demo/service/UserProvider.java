package org.ylzl.eden.demo.service;

import org.apache.dubbo.config.annotation.DubboService;
import org.ylzl.eden.demo.api.UserService;
import org.ylzl.eden.demo.dao.UserDAO;
import org.ylzl.eden.demo.service.constant.DubboConstant;
import org.ylzl.eden.demo.service.converter.UserConvertor;
import org.ylzl.eden.demo.service.impl.UserServiceImpl;

/**
 * 用户业务 RPC服务端
 *
 * @author <a href="mailto:shiyindaxiaojie@gmail.com">gyl</a>
 * @since 2.4.13
 */
@DubboService(timeout = DubboConstant.DEFAULT_TIMEOUT)
public class UserProvider extends UserServiceImpl implements UserService {

	public UserProvider(UserDAO userDAO, UserConvertor userConvertor) {
		super(userDAO, userConvertor);
	}
}
