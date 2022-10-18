package org.ylzl.eden.demo.dao.repository.mybatis.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.ylzl.eden.demo.api.dto.UserPageQuery;
import org.ylzl.eden.demo.dao.repository.mybatis.dataobject.UserDO;
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
