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

package org.ylzl.eden.demo.dao.database.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.ylzl.eden.demo.dao.database.dataobject.RoleDO;

import java.util.List;

/**
 * 角色表数据库映射
 *
 * @author <a href="mailto:shiyindaxiaojie@gmail.com">gyl</a>
 * @since 2.4.14
 */
@Mapper
public interface RoleMapper extends BaseMapper<RoleDO> {

	/**
	 * 根据角色编码查询
	 *
	 * @param code 角色编码
	 * @return 角色信息
	 */
	RoleDO selectByCode(@Param("code") String code);

	/**
	 * 分页查询角色列表
	 *
	 * @param code   角色编码
	 * @param name   角色名称
	 * @param status 状态
	 * @return 角色列表
	 */
	List<RoleDO> selectPage(@Param("code") String code,
							@Param("name") String name,
							@Param("status") Integer status);

	/**
	 * 根据ID列表查询
	 *
	 * @param ids ID列表
	 * @return 角色列表
	 */
	List<RoleDO> selectByIds(@Param("ids") List<Long> ids);
}
