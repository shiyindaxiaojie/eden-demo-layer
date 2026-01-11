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

import com.github.pagehelper.Page;
import org.ylzl.eden.demo.dao.database.dataobject.RoleDO;

import java.util.List;

/**
 * 角色（数据对象）存储访问接口
 *
 * @author <a href="mailto:shiyindaxiaojie@gmail.com">gyl</a>
 * @since 2.4.14
 */
public interface RoleDAO {

	/**
	 * 创建角色
	 *
	 * @param dataObject 角色数据对象
	 * @return 是否成功
	 */
	boolean save(RoleDO dataObject);

	/**
	 * 修改角色
	 *
	 * @param dataObject 角色数据对象
	 * @return 是否成功
	 */
	boolean updateById(RoleDO dataObject);

	/**
	 * 删除角色
	 *
	 * @param id 角色ID
	 * @return 是否成功
	 */
	boolean deleteById(Long id);

	/**
	 * 根据ID查询角色
	 *
	 * @param id 角色ID
	 * @return 角色信息
	 */
	RoleDO findById(Long id);

	/**
	 * 根据编码查询角色
	 *
	 * @param code 角色编码
	 * @return 角色信息
	 */
	RoleDO findByCode(String code);

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
	Page<RoleDO> findByPage(int pageIndex, int pageSize, String code, String name, Integer status);

	/**
	 * 根据ID列表查询角色
	 *
	 * @param ids ID列表
	 * @return 角色列表
	 */
	List<RoleDO> findByIds(List<Long> ids);
}
