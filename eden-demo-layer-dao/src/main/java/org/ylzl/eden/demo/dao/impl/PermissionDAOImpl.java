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

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.ylzl.eden.demo.dao.PermissionDAO;
import org.ylzl.eden.demo.dao.database.dataobject.PermissionDO;
import org.ylzl.eden.demo.dao.database.mapper.PermissionMapper;

import java.util.Collections;
import java.util.List;

/**
 * 权限（数据对象）存储访问实现
 *
 * @author <a href="mailto:shiyindaxiaojie@gmail.com">gyl</a>
 * @since 2.4.14
 */
@RequiredArgsConstructor
@Repository
public class PermissionDAOImpl implements PermissionDAO {

	private final PermissionMapper permissionMapper;

	@Override
	public boolean save(PermissionDO dataObject) {
		return permissionMapper.insert(dataObject) > 0;
	}

	@Override
	public boolean updateById(PermissionDO dataObject) {
		return permissionMapper.updateById(dataObject) > 0;
	}

	@Override
	public boolean deleteById(Long id) {
		return permissionMapper.deleteById(id) > 0;
	}

	@Override
	public PermissionDO findById(Long id) {
		return permissionMapper.selectById(id);
	}

	@Override
	public PermissionDO findByCode(String code) {
		return permissionMapper.selectByCode(code);
	}

	@Override
	public Page<PermissionDO> findByPage(int pageIndex, int pageSize, String code, String name, Integer type) {
		return PageHelper.startPage(pageIndex, pageSize)
			.doSelectPage(() -> permissionMapper.selectPage(code, name, type));
	}

	@Override
	public List<PermissionDO> findAll() {
		return permissionMapper.selectAll();
	}

	@Override
	public List<PermissionDO> findByIds(List<Long> ids) {
		if (ids == null || ids.isEmpty()) {
			return Collections.emptyList();
		}
		return permissionMapper.selectByIds(ids);
	}
}
