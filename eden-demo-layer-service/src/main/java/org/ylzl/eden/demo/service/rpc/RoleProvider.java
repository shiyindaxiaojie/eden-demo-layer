/*
 * Copyright 2012-2019 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.ylzl.eden.demo.service.rpc;

import org.apache.dubbo.config.annotation.DubboService;
import org.ylzl.eden.demo.api.RoleService;
import org.ylzl.eden.demo.dao.RoleDAO;
import org.ylzl.eden.demo.manager.dao.RbacManager;
import org.ylzl.eden.demo.service.constant.RPC;
import org.ylzl.eden.demo.service.converter.MenuConvertor;
import org.ylzl.eden.demo.service.converter.PermissionConvertor;
import org.ylzl.eden.demo.service.converter.RoleConvertor;
import org.ylzl.eden.demo.service.impl.RoleServiceImpl;

/**
 * 角色业务 RPC服务端
 *
 * @author <a href="mailto:shiyindaxiaojie@gmail.com">gyl</a>
 * @since 2.4.14
 */
@DubboService(timeout = RPC.DEFAULT_TIMEOUT)
public class RoleProvider extends RoleServiceImpl implements RoleService {

	public RoleProvider(RoleDAO roleDAO, RbacManager rbacManager, RoleConvertor roleConvertor,
						PermissionConvertor permissionConvertor, MenuConvertor menuConvertor) {
		super(roleDAO, rbacManager, roleConvertor, permissionConvertor, menuConvertor);
	}
}
