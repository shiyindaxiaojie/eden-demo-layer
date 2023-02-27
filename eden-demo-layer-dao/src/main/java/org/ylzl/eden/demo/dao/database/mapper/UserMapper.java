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
import org.ylzl.eden.demo.api.dto.UserPageQuery;
import org.ylzl.eden.demo.dao.database.dataobject.UserDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 用户信息表数据库映射
 *
 * @author <a href="mailto:shiyindaxiaojie@gmail.com">gyl</a>
 * @since 2.4.13
 */
@Mapper
public interface UserMapper extends BaseMapper<UserDO> {

    /**
     * 查询用户列表
     *
	 * @param query 查询条件
     * @return 数据列表
     */
    List<UserDO> selectPage(UserPageQuery query);
}
