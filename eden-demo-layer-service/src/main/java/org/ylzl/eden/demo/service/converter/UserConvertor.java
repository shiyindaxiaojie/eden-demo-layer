package org.ylzl.eden.demo.service.converter;

import org.ylzl.eden.demo.api.dto.UserDTO;
import org.ylzl.eden.demo.api.dto.UserVO;
import org.ylzl.eden.demo.dao.repository.mybatis.dataobject.UserDO;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 用户转换器
 *
 * @author <a href="mailto:shiyindaxiaojie@gmail.com">gyl</a>
 * @since 2.4.x
 */
@Mapper(componentModel = "spring",
	nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
	nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface UserConvertor {

	UserConvertor INSTANCE = Mappers.getMapper(UserConvertor.class);

	UserDO dtoToDataObject(UserDTO dto);

	void updateDataObjectFromDTO(UserDTO dto, @MappingTarget UserDO dataObject);

	UserVO dataObjectToVO(UserDO dataObject);

	List<UserVO> dataObjectListToVOList(List<UserDO> dataObjectList);
}
