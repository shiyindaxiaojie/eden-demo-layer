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

package org.ylzl.eden.demo.dao.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.ylzl.eden.demo.dao.RoleMenuDAO;
import org.ylzl.eden.demo.dao.database.dataobject.RoleMenuDO;
import org.ylzl.eden.demo.dao.database.mapper.RoleMenuMapper;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 角色菜单关联（数据对象）存储访问实现
 *
 * @author <a href="mailto:shiyindaxiaojie@gmail.com">gyl</a>
 * @since 2.4.14
 */
@RequiredArgsConstructor
@Repository
public class RoleMenuDAOImpl implements RoleMenuDAO {

	private final RoleMenuMapper roleMenuMapper;

	@Override
	public boolean saveAll(Long roleId, List<Long> menuIds) {
		if (menuIds == null || menuIds.isEmpty()) {
			return true;
		}
		List<RoleMenuDO> list = menuIds.stream()
			.map(menuId -> RoleMenuDO.builder().roleId(roleId).menuId(menuId).build())
			.collect(Collectors.toList());
		return roleMenuMapper.insertBatch(list) > 0;
	}

	@Override
	public boolean deleteByRoleId(Long roleId) {
		return roleMenuMapper.deleteByRoleId(roleId) >= 0;
	}

	@Override
	public boolean deleteByMenuId(Long menuId) {
		return roleMenuMapper.deleteByMenuId(menuId) >= 0;
	}

	@Override
	public List<Long> findMenuIdsByRoleId(Long roleId) {
		if (roleId == null) {
			return Collections.emptyList();
		}
		return roleMenuMapper.selectMenuIdsByRoleId(roleId);
	}

	@Override
	public List<Long> findMenuIdsByRoleIds(List<Long> roleIds) {
		if (roleIds == null || roleIds.isEmpty()) {
			return Collections.emptyList();
		}
		return roleMenuMapper.selectMenuIdsByRoleIds(roleIds);
	}
}
