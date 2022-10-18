package org.ylzl.eden.demo.service.converter;

import org.ylzl.eden.demo.api.dto.UserRequestDTO;
import org.ylzl.eden.demo.api.dto.UserResponseDTO;
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
 * @since 2.4.13
 */
@Mapper(componentModel = "spring",
	nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
	nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface UserConvertor {

	UserDO dtoToDataObject(UserRequestDTO dto);

	void updateDataObjectFromDTO(UserRequestDTO dto, @MappingTarget UserDO dataObject);

	UserResponseDTO dataObjectToVO(UserDO dataObject);

	List<UserResponseDTO> dataObjectListToVOList(List<UserDO> dataObjectList);
}
