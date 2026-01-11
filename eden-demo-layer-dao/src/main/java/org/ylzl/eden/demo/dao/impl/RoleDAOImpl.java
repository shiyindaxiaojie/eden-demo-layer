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
import org.ylzl.eden.demo.dao.RoleDAO;
import org.ylzl.eden.demo.dao.database.dataobject.RoleDO;
import org.ylzl.eden.demo.dao.database.mapper.RoleMapper;

import java.util.Collections;
import java.util.List;

/**
 * 角色（数据对象）存储访问实现
 *
 * @author <a href="mailto:shiyindaxiaojie@gmail.com">gyl</a>
 * @since 2.4.14
 */
@RequiredArgsConstructor
@Repository
public class RoleDAOImpl implements RoleDAO {

	private final RoleMapper roleMapper;

	/**
	 * 创建角色
	 *
	 * @param dataObject 角色数据对象
	 * @return 是否成功
	 */
	@Override
	public boolean save(RoleDO dataObject) {
		return roleMapper.insert(dataObject) > 0;
	}

	/**
	 * 修改角色
	 *
	 * @param dataObject 角色数据对象
	 * @return 是否成功
	 */
	@Override
	public boolean updateById(RoleDO dataObject) {
		return roleMapper.updateById(dataObject) > 0;
	}

	/**
	 * 删除角色
	 *
	 * @param id 角色ID
	 * @return 是否成功
	 */
	@Override
	public boolean deleteById(Long id) {
		return roleMapper.deleteById(id) > 0;
	}

	/**
	 * 根据ID查询角色
	 *
	 * @param id 角色ID
	 * @return 角色信息
	 */
	@Override
	public RoleDO findById(Long id) {
		return roleMapper.selectById(id);
	}

	/**
	 * 根据编码查询角色
	 *
	 * @param code 角色编码
	 * @return 角色信息
	 */
	@Override
	public RoleDO findByCode(String code) {
		return roleMapper.selectByCode(code);
	}

	/**
	 * 分页查询角色
	 *
	 * @param pageIndex 页码
	 * @param pageSize  每页大小
	 * @param code      角色编码
	 * @param name      角色名称
	 * @param status    状态
	 * @return 角色分页列表
	 */
	@Override
	public Page<RoleDO> findByPage(int pageIndex, int pageSize, String code, String name, Integer status) {
		return PageHelper.startPage(pageIndex, pageSize)
			.doSelectPage(() -> roleMapper.selectPage(code, name, status));
	}

	/**
	 * 根据ID列表查询角色
	 *
	 * @param ids ID列表
	 * @return 角色列表
	 */
	@Override
	public List<RoleDO> findByIds(List<Long> ids) {
		if (ids == null || ids.isEmpty()) {
			return Collections.emptyList();
		}
		return roleMapper.selectByIds(ids);
	}
}
