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
import org.ylzl.eden.demo.dao.database.dataobject.RolePermissionDO;

import java.util.List;

/**
 * 角色权限关联表数据库映射
 *
 * @author <a href="mailto:shiyindaxiaojie@gmail.com">gyl</a>
 * @since 2.4.14
 */
@Mapper
public interface RolePermissionMapper extends BaseMapper<RolePermissionDO> {

	int insertBatch(@Param("list") List<RolePermissionDO> list);

	int deleteByRoleId(@Param("roleId") Long roleId);

	int deleteByPermissionId(@Param("permissionId") Long permissionId);

	List<Long> selectPermissionIdsByRoleId(@Param("roleId") Long roleId);

	List<Long> selectPermissionIdsByRoleIds(@Param("roleIds") List<Long> roleIds);
}
