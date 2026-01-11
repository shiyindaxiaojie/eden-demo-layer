package org.ylzl.eden.demo.web;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.ylzl.eden.demo.api.MenuService;
import org.ylzl.eden.demo.api.dto.MenuRequestDTO;
import org.ylzl.eden.demo.api.dto.MenuResponseDTO;
import org.ylzl.eden.demo.api.dto.MenuTreeDTO;
import org.ylzl.eden.demo.web.constant.API;
import org.ylzl.eden.spring.framework.dto.MultiResult;
import org.ylzl.eden.spring.framework.dto.Result;
import org.ylzl.eden.spring.framework.dto.SingleResult;

import javax.validation.Valid;

/**
 * 菜单控制器
 *
 * @author <a href="mailto:shiyindaxiaojie@gmail.com">gyl</a>
 * @since 2.4.14
 */
@RequiredArgsConstructor
@Slf4j
@RequestMapping(API.WEB_API_PATH + "/menus")
@RestController
public class MenuController {

	private final MenuService menuService;

	@PostMapping
	public Result createMenu(@Valid @RequestBody MenuRequestDTO dto) {
		return menuService.createMenu(dto);
	}

	@PutMapping("/{id}")
	public Result modifyMenu(@PathVariable Long id, @Valid @RequestBody MenuRequestDTO dto) {
		return menuService.modifyMenu(id, dto);
	}

	@DeleteMapping("/{id}")
	public Result removeMenu(@PathVariable Long id) {
		return menuService.removeMenu(id);
	}

	@PutMapping("/{id}/show")
	public Result showMenu(@PathVariable Long id) {
		return menuService.showMenu(id);
	}

	@PutMapping("/{id}/hide")
	public Result hideMenu(@PathVariable Long id) {
		return menuService.hideMenu(id);
	}

	@GetMapping("/{id}")
	public SingleResult<MenuResponseDTO> getMenuById(@PathVariable Long id) {
		return menuService.getMenuById(id);
	}

	@GetMapping("/tree")
	public MultiResult<MenuTreeDTO> getMenuTree() {
		return menuService.getMenuTree();
	}
}
