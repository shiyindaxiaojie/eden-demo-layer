package org.ylzl.eden.demo.web;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.ylzl.eden.demo.api.RoleService;
import org.ylzl.eden.demo.api.dto.*;
import org.ylzl.eden.demo.web.constant.API;
import org.ylzl.eden.spring.framework.dto.MultiResult;
import org.ylzl.eden.spring.framework.dto.PageResult;
import org.ylzl.eden.spring.framework.dto.Result;
import org.ylzl.eden.spring.framework.dto.SingleResult;

import javax.validation.Valid;
import java.util.List;

/**
 * 角色控制器
 *
 * @author <a href="mailto:shiyindaxiaojie@gmail.com">gyl</a>
 * @since 2.4.14
 */
@RequiredArgsConstructor
@Slf4j
@RequestMapping(API.WEB_API_PATH + "/roles")
@RestController
public class RoleController {

	private final RoleService roleService;

	@PostMapping
	public Result createRole(@Valid @RequestBody RoleRequestDTO dto) {
		return roleService.createRole(dto);
	}

	@PutMapping("/{id}")
	public Result modifyRole(@PathVariable Long id, @Valid @RequestBody RoleRequestDTO dto) {
		return roleService.modifyRole(id, dto);
	}

	@DeleteMapping("/{id}")
	public Result removeRole(@PathVariable Long id) {
		return roleService.removeRole(id);
	}

	@PutMapping("/{id}/enable")
	public Result enableRole(@PathVariable Long id) {
		return roleService.enableRole(id);
	}

	@PutMapping("/{id}/disable")
	public Result disableRole(@PathVariable Long id) {
		return roleService.disableRole(id);
	}

	@GetMapping("/{id}")
	public SingleResult<RoleResponseDTO> getRoleById(@PathVariable Long id) {
		return roleService.getRoleById(id);
	}

	@GetMapping
	public PageResult<RoleResponseDTO> listRoleByPage(@Valid @ModelAttribute RolePageQuery query) {
		return roleService.listRoleByPage(query);
	}

	@PutMapping("/{id}/permissions")
	public Result assignPermissions(@PathVariable Long id, @RequestBody List<Long> permissionIds) {
		return roleService.assignPermissions(id, permissionIds);
	}

	@PutMapping("/{id}/menus")
	public Result assignMenus(@PathVariable Long id, @RequestBody List<Long> menuIds) {
		return roleService.assignMenus(id, menuIds);
	}

	@GetMapping("/{id}/permissions")
	public MultiResult<PermissionResponseDTO> getRolePermissions(@PathVariable Long id) {
		return roleService.getRolePermissions(id);
	}

	@GetMapping("/{id}/menus")
	public MultiResult<MenuTreeDTO> getRoleMenus(@PathVariable Long id) {
		return roleService.getRoleMenus(id);
	}
}
