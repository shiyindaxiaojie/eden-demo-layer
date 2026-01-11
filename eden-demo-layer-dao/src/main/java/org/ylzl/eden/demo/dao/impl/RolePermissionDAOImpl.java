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
import org.ylzl.eden.demo.dao.RolePermissionDAO;
import org.ylzl.eden.demo.dao.database.dataobject.RolePermissionDO;
import org.ylzl.eden.demo.dao.database.mapper.RolePermissionMapper;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 角色权限关联（数据对象）存储访问实现
 *
 * @author <a href="mailto:shiyindaxiaojie@gmail.com">gyl</a>
 * @since 2.4.14
 */
@RequiredArgsConstructor
@Repository
public class RolePermissionDAOImpl implements RolePermissionDAO {

	private final RolePermissionMapper rolePermissionMapper;

	@Override
	public boolean saveAll(Long roleId, List<Long> permissionIds) {
		if (permissionIds == null || permissionIds.isEmpty()) {
			return true;
		}
		List<RolePermissionDO> list = permissionIds.stream()
			.map(permissionId -> RolePermissionDO.builder().roleId(roleId).permissionId(permissionId).build())
			.collect(Collectors.toList());
		return rolePermissionMapper.insertBatch(list) > 0;
	}

	@Override
	public boolean deleteByRoleId(Long roleId) {
		return rolePermissionMapper.deleteByRoleId(roleId) >= 0;
	}

	@Override
	public boolean deleteByPermissionId(Long permissionId) {
		return rolePermissionMapper.deleteByPermissionId(permissionId) >= 0;
	}

	@Override
	public List<Long> findPermissionIdsByRoleId(Long roleId) {
		if (roleId == null) {
			return Collections.emptyList();
		}
		return rolePermissionMapper.selectPermissionIdsByRoleId(roleId);
	}

	@Override
	public List<Long> findPermissionIdsByRoleIds(List<Long> roleIds) {
		if (roleIds == null || roleIds.isEmpty()) {
			return Collections.emptyList();
		}
		return rolePermissionMapper.selectPermissionIdsByRoleIds(roleIds);
	}
}
