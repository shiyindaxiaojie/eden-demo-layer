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
import org.ylzl.eden.demo.dao.UserRoleDAO;
import org.ylzl.eden.demo.dao.database.dataobject.UserRoleDO;
import org.ylzl.eden.demo.dao.database.mapper.UserRoleMapper;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 用户角色关联（数据对象）存储访问实现
 *
 * @author <a href="mailto:shiyindaxiaojie@gmail.com">gyl</a>
 * @since 2.4.14
 */
@RequiredArgsConstructor
@Repository
public class UserRoleDAOImpl implements UserRoleDAO {

	private final UserRoleMapper userRoleMapper;

	@Override
	public boolean saveAll(Long userId, List<Long> roleIds) {
		if (roleIds == null || roleIds.isEmpty()) {
			return true;
		}
		List<UserRoleDO> list = roleIds.stream()
			.map(roleId -> UserRoleDO.builder().userId(userId).roleId(roleId).build())
			.collect(Collectors.toList());
		return userRoleMapper.insertBatch(list) > 0;
	}

	@Override
	public boolean deleteByUserId(Long userId) {
		return userRoleMapper.deleteByUserId(userId) >= 0;
	}

	@Override
	public boolean deleteByRoleId(Long roleId) {
		return userRoleMapper.deleteByRoleId(roleId) >= 0;
	}

	@Override
	public List<Long> findRoleIdsByUserId(Long userId) {
		if (userId == null) {
			return Collections.emptyList();
		}
		return userRoleMapper.selectRoleIdsByUserId(userId);
	}

	@Override
	public List<Long> findUserIdsByRoleId(Long roleId) {
		if (roleId == null) {
			return Collections.emptyList();
		}
		return userRoleMapper.selectUserIdsByRoleId(roleId);
	}
}
