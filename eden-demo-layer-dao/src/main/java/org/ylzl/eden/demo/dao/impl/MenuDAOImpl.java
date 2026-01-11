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
import org.ylzl.eden.demo.dao.MenuDAO;
import org.ylzl.eden.demo.dao.database.dataobject.MenuDO;
import org.ylzl.eden.demo.dao.database.mapper.MenuMapper;

import java.util.Collections;
import java.util.List;

/**
 * 菜单（数据对象）存储访问实现
 *
 * @author <a href="mailto:shiyindaxiaojie@gmail.com">gyl</a>
 * @since 2.4.14
 */
@RequiredArgsConstructor
@Repository
public class MenuDAOImpl implements MenuDAO {

	private final MenuMapper menuMapper;

	@Override
	public boolean save(MenuDO dataObject) {
		return menuMapper.insert(dataObject) > 0;
	}

	@Override
	public boolean updateById(MenuDO dataObject) {
		return menuMapper.updateById(dataObject) > 0;
	}

	@Override
	public boolean deleteById(Long id) {
		return menuMapper.deleteById(id) > 0;
	}

	@Override
	public MenuDO findById(Long id) {
		return menuMapper.selectById(id);
	}

	@Override
	public List<MenuDO> findAll() {
		return menuMapper.selectAll();
	}

	@Override
	public List<MenuDO> findByParentId(Long parentId) {
		return menuMapper.selectByParentId(parentId);
	}

	@Override
	public List<MenuDO> findByIds(List<Long> ids) {
		if (ids == null || ids.isEmpty()) {
			return Collections.emptyList();
		}
		return menuMapper.selectByIds(ids);
	}
}
