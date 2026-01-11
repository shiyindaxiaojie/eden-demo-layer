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

package org.ylzl.eden.demo.dao;

import java.util.List;

/**
 * 角色菜单关联（数据对象）存储访问接口
 *
 * @author <a href="mailto:shiyindaxiaojie@gmail.com">gyl</a>
 * @since 2.4.14
 */
public interface RoleMenuDAO {

	boolean saveAll(Long roleId, List<Long> menuIds);

	boolean deleteByRoleId(Long roleId);

	boolean deleteByMenuId(Long menuId);

	List<Long> findMenuIdsByRoleId(Long roleId);

	List<Long> findMenuIdsByRoleIds(List<Long> roleIds);
}
