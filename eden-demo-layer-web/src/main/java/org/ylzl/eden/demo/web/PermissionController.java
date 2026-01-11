package org.ylzl.eden.demo.web;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.ylzl.eden.demo.api.PermissionService;
import org.ylzl.eden.demo.api.dto.*;
import org.ylzl.eden.demo.web.constant.API;
import org.ylzl.eden.spring.framework.dto.MultiResult;
import org.ylzl.eden.spring.framework.dto.PageResult;
import org.ylzl.eden.spring.framework.dto.Result;
import org.ylzl.eden.spring.framework.dto.SingleResult;

import javax.validation.Valid;

/**
 * 权限控制器
 *
 * @author <a href="mailto:shiyindaxiaojie@gmail.com">gyl</a>
 * @since 2.4.14
 */
@RequiredArgsConstructor
@Slf4j
@RequestMapping(API.WEB_API_PATH + "/permissions")
@RestController
public class PermissionController {

	private final PermissionService permissionService;

	@PostMapping
	public Result createPermission(@Valid @RequestBody PermissionRequestDTO dto) {
		return permissionService.createPermission(dto);
	}

	@PutMapping("/{id}")
	public Result modifyPermission(@PathVariable Long id, @Valid @RequestBody PermissionRequestDTO dto) {
		return permissionService.modifyPermission(id, dto);
	}

	@DeleteMapping("/{id}")
	public Result removePermission(@PathVariable Long id) {
		return permissionService.removePermission(id);
	}

	@GetMapping("/{id}")
	public SingleResult<PermissionResponseDTO> getPermissionById(@PathVariable Long id) {
		return permissionService.getPermissionById(id);
	}

	@GetMapping
	public PageResult<PermissionResponseDTO> listPermissionByPage(@Valid @ModelAttribute PermissionPageQuery query) {
		return permissionService.listPermissionByPage(query);
	}

	@GetMapping("/tree")
	public MultiResult<PermissionTreeDTO> getPermissionTree() {
		return permissionService.getPermissionTree();
	}
}
