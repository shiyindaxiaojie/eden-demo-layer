package org.ylzl.eden.demo.service.converter;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.ylzl.eden.demo.api.dto.PermissionRequestDTO;
import org.ylzl.eden.demo.api.dto.PermissionResponseDTO;
import org.ylzl.eden.demo.api.dto.PermissionTreeDTO;
import org.ylzl.eden.demo.dao.database.dataobject.PermissionDO;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 权限转换器
 *
 * @author <a href="mailto:shiyindaxiaojie@gmail.com">gyl</a>
 * @since 2.4.14
 */
@Mapper(componentModel = "spring",
	nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
	nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface PermissionConvertor {

	PermissionDO dtoToDataObject(PermissionRequestDTO dto);

	void updateDataObjectFromDTO(PermissionRequestDTO dto, @MappingTarget PermissionDO dataObject);

	PermissionResponseDTO dataObjectToDTO(PermissionDO dataObject);

	List<PermissionResponseDTO> dataObjectListToDTOList(List<PermissionDO> dataObjectList);

	PermissionTreeDTO dataObjectToTreeDTO(PermissionDO dataObject);

	/**
	 * 构建权限树
	 */
	default List<PermissionTreeDTO> buildPermissionTree(List<PermissionDO> permissions) {
		if (permissions == null || permissions.isEmpty()) {
			return new ArrayList<>();
		}

		// 转换为 TreeDTO
		List<PermissionTreeDTO> allNodes = permissions.stream()
			.map(this::dataObjectToTreeDTO)
			.collect(Collectors.toList());

		// 按父ID分组
		Map<Long, List<PermissionTreeDTO>> childrenMap = allNodes.stream()
			.filter(p -> p.getId() != null)
			.collect(Collectors.groupingBy(p -> {
				PermissionDO original = permissions.stream()
					.filter(o -> o.getId().equals(p.getId()))
					.findFirst().orElse(null);
				return original != null && original.getParentId() != null ? original.getParentId() : 0L;
			}));

		// 构建树
		List<PermissionTreeDTO> roots = new ArrayList<>();
		for (PermissionTreeDTO node : allNodes) {
			PermissionDO original = permissions.stream()
				.filter(o -> o.getId().equals(node.getId()))
				.findFirst().orElse(null);
			if (original != null && (original.getParentId() == null || original.getParentId() == 0)) {
				node.setChildren(childrenMap.getOrDefault(node.getId(), new ArrayList<>()));
				buildChildrenRecursive(node, childrenMap);
				roots.add(node);
			}
		}
		return roots;
	}

	default void buildChildrenRecursive(PermissionTreeDTO parent, Map<Long, List<PermissionTreeDTO>> childrenMap) {
		List<PermissionTreeDTO> children = childrenMap.getOrDefault(parent.getId(), new ArrayList<>());
		parent.setChildren(children);
		for (PermissionTreeDTO child : children) {
			buildChildrenRecursive(child, childrenMap);
		}
	}
}
