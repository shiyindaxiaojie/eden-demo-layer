package org.ylzl.eden.demo.service.converter;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.ylzl.eden.demo.api.dto.MenuRequestDTO;
import org.ylzl.eden.demo.api.dto.MenuResponseDTO;
import org.ylzl.eden.demo.api.dto.MenuTreeDTO;
import org.ylzl.eden.demo.dao.database.dataobject.MenuDO;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 菜单转换器
 *
 * @author <a href="mailto:shiyindaxiaojie@gmail.com">gyl</a>
 * @since 2.4.14
 */
@Mapper(componentModel = "spring",
	nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
	nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface MenuConvertor {

	MenuDO dtoToDataObject(MenuRequestDTO dto);

	void updateDataObjectFromDTO(MenuRequestDTO dto, @MappingTarget MenuDO dataObject);

	MenuResponseDTO dataObjectToDTO(MenuDO dataObject);

	List<MenuResponseDTO> dataObjectListToDTOList(List<MenuDO> dataObjectList);

	MenuTreeDTO dataObjectToTreeDTO(MenuDO dataObject);

	/**
	 * 构建菜单树
	 */
	default List<MenuTreeDTO> buildMenuTree(List<MenuDO> menus) {
		if (menus == null || menus.isEmpty()) {
			return new ArrayList<>();
		}

		// 转换为 TreeDTO
		List<MenuTreeDTO> allNodes = menus.stream()
			.map(this::dataObjectToTreeDTO)
			.collect(Collectors.toList());

		// 按父ID分组
		Map<Long, List<MenuTreeDTO>> childrenMap = allNodes.stream()
			.filter(m -> m.getId() != null)
			.collect(Collectors.groupingBy(m -> {
				MenuDO original = menus.stream()
					.filter(o -> o.getId().equals(m.getId()))
					.findFirst().orElse(null);
				return original != null && original.getParentId() != null ? original.getParentId() : 0L;
			}));

		// 构建树
		List<MenuTreeDTO> roots = new ArrayList<>();
		for (MenuTreeDTO node : allNodes) {
			MenuDO original = menus.stream()
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

	default void buildChildrenRecursive(MenuTreeDTO parent, Map<Long, List<MenuTreeDTO>> childrenMap) {
		List<MenuTreeDTO> children = childrenMap.getOrDefault(parent.getId(), new ArrayList<>());
		parent.setChildren(children);
		for (MenuTreeDTO child : children) {
			buildChildrenRecursive(child, childrenMap);
		}
	}
}
