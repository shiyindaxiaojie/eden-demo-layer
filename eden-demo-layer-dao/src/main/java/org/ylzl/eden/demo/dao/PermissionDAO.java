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
import org.ylzl.eden.demo.dao.database.dataobject.PermissionDO;

import java.util.List;

/**
 * 权限（数据对象）存储访问接口
 *
 * @author <a href="mailto:shiyindaxiaojie@gmail.com">gyl</a>
 * @since 2.4.14
 */
public interface PermissionDAO {

	boolean save(PermissionDO dataObject);

	boolean updateById(PermissionDO dataObject);

	boolean deleteById(Long id);

	PermissionDO findById(Long id);

	PermissionDO findByCode(String code);

	Page<PermissionDO> findByPage(int pageIndex, int pageSize, String code, String name, Integer type);

	List<PermissionDO> findAll();

	List<PermissionDO> findByIds(List<Long> ids);
}
