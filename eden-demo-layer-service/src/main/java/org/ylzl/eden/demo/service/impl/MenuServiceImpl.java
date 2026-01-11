package org.ylzl.eden.demo.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.ylzl.eden.demo.api.MenuService;
import org.ylzl.eden.demo.api.dto.MenuRequestDTO;
import org.ylzl.eden.demo.api.dto.MenuResponseDTO;
import org.ylzl.eden.demo.api.dto.MenuTreeDTO;
import org.ylzl.eden.demo.dao.MenuDAO;
import org.ylzl.eden.demo.dao.database.dataobject.MenuDO;
import org.ylzl.eden.demo.manager.dao.RbacManager;
import org.ylzl.eden.demo.service.converter.MenuConvertor;
import org.ylzl.eden.spring.framework.dto.MultiResult;
import org.ylzl.eden.spring.framework.dto.Result;
import org.ylzl.eden.spring.framework.dto.SingleResult;
import org.ylzl.eden.spring.framework.error.ClientAssert;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 菜单业务逻辑实现
 *
 * @author <a href="mailto:shiyindaxiaojie@gmail.com">gyl</a>
 * @since 2.4.14
 */
@RequiredArgsConstructor
@Slf4j
@Service("menuService")
public class MenuServiceImpl implements MenuService {

	private final MenuDAO menuDAO;
	private final RbacManager rbacManager;
	private final MenuConvertor menuConvertor;

	@Override
	public Result createMenu(MenuRequestDTO dto) {
		MenuDO menuDO = menuConvertor.dtoToDataObject(dto);
		menuDO.setStatus(1);
		menuDO.setCreatedDate(LocalDateTime.now());
		menuDAO.save(menuDO);
		return Result.buildSuccess();
	}

	@Override
	public Result modifyMenu(Long id, MenuRequestDTO dto) {
		MenuDO menuDO = menuDAO.findById(id);
		ClientAssert.notNull(menuDO, "MENU-003");

		// 不能将菜单设置为自己的子菜单
		if (dto.getParentId() != null) {
			ClientAssert.isTrue(!id.equals(dto.getParentId()), "MENU-004");
		}

		menuConvertor.updateDataObjectFromDTO(dto, menuDO);
		menuDO.setLastModifiedDate(LocalDateTime.now());
		menuDAO.updateById(menuDO);
		return Result.buildSuccess();
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public Result removeMenu(Long id) {
		MenuDO menuDO = menuDAO.findById(id);
		ClientAssert.notNull(menuDO, "MENU-003");

		// 级联删除关联关系和子菜单
		rbacManager.deleteMenuRelations(id);
		menuDAO.deleteById(id);
		return Result.buildSuccess();
	}

	@Override
	public Result showMenu(Long id) {
		MenuDO menuDO = menuDAO.findById(id);
		ClientAssert.notNull(menuDO, "MENU-003");

		menuDO.setStatus(1);
		menuDO.setLastModifiedDate(LocalDateTime.now());
		menuDAO.updateById(menuDO);
		return Result.buildSuccess();
	}

	@Override
	public Result hideMenu(Long id) {
		MenuDO menuDO = menuDAO.findById(id);
		ClientAssert.notNull(menuDO, "MENU-003");

		menuDO.setStatus(0);
		menuDO.setLastModifiedDate(LocalDateTime.now());
		menuDAO.updateById(menuDO);
		return Result.buildSuccess();
	}

	@Override
	public SingleResult<MenuResponseDTO> getMenuById(Long id) {
		MenuDO menuDO = menuDAO.findById(id);
		ClientAssert.notNull(menuDO, "MENU-003");
		return SingleResult.build(menuConvertor.dataObjectToDTO(menuDO));
	}

	@Override
	public MultiResult<MenuTreeDTO> getMenuTree() {
		List<MenuDO> menus = menuDAO.findAll();
		List<MenuTreeDTO> tree = menuConvertor.buildMenuTree(menus);
		return MultiResult.build(tree);
	}
}
