package org.ylzl.eden.demo.service.impl;

import com.github.pagehelper.Page;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.ylzl.eden.demo.api.PermissionService;
import org.ylzl.eden.demo.api.dto.*;
import org.ylzl.eden.demo.dao.PermissionDAO;
import org.ylzl.eden.demo.dao.database.dataobject.PermissionDO;
import org.ylzl.eden.demo.manager.dao.RbacManager;
import org.ylzl.eden.demo.service.converter.PermissionConvertor;
import org.ylzl.eden.spring.framework.dto.MultiResult;
import org.ylzl.eden.spring.framework.dto.PageResult;
import org.ylzl.eden.spring.framework.dto.Result;
import org.ylzl.eden.spring.framework.dto.SingleResult;
import org.ylzl.eden.spring.framework.error.ClientAssert;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 权限业务逻辑实现
 *
 * @author <a href="mailto:shiyindaxiaojie@gmail.com">gyl</a>
 * @since 2.4.14
 */
@RequiredArgsConstructor
@Slf4j
@Service("permissionService")
public class PermissionServiceImpl implements PermissionService {

	private final PermissionDAO permissionDAO;
	private final RbacManager rbacManager;
	private final PermissionConvertor permissionConvertor;

	@Override
	public Result createPermission(PermissionRequestDTO dto) {
		// 检查编码唯一性
		PermissionDO existing = permissionDAO.findByCode(dto.getCode());
		ClientAssert.isNull(existing, "PERMISSION-001");

		PermissionDO permissionDO = permissionConvertor.dtoToDataObject(dto);
		permissionDO.setCreatedDate(LocalDateTime.now());
		permissionDAO.save(permissionDO);
		return Result.buildSuccess();
	}

	@Override
	public Result modifyPermission(Long id, PermissionRequestDTO dto) {
		PermissionDO permissionDO = permissionDAO.findById(id);
		ClientAssert.notNull(permissionDO, "PERMISSION-002");

		permissionConvertor.updateDataObjectFromDTO(dto, permissionDO);
		permissionDO.setLastModifiedDate(LocalDateTime.now());
		permissionDAO.updateById(permissionDO);
		return Result.buildSuccess();
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public Result removePermission(Long id) {
		PermissionDO permissionDO = permissionDAO.findById(id);
		ClientAssert.notNull(permissionDO, "PERMISSION-002");

		// 级联删除关联关系
		rbacManager.deletePermissionRelations(id);
		permissionDAO.deleteById(id);
		return Result.buildSuccess();
	}

	@Override
	public SingleResult<PermissionResponseDTO> getPermissionById(Long id) {
		PermissionDO permissionDO = permissionDAO.findById(id);
		ClientAssert.notNull(permissionDO, "PERMISSION-002");
		return SingleResult.build(permissionConvertor.dataObjectToDTO(permissionDO));
	}

	@Override
	public PageResult<PermissionResponseDTO> listPermissionByPage(PermissionPageQuery query) {
		Page<PermissionDO> page = permissionDAO.findByPage(
			query.getPageIndex(), query.getPageSize(),
			query.getCode(), query.getName(), query.getType());
		List<PermissionResponseDTO> dtoList = permissionConvertor.dataObjectListToDTOList(page.getResult());
		return PageResult.build(dtoList, Integer.parseInt(String.valueOf(page.getTotal())));
	}

	@Override
	public MultiResult<PermissionTreeDTO> getPermissionTree() {
		List<PermissionDO> permissions = permissionDAO.findAll();
		List<PermissionTreeDTO> tree = permissionConvertor.buildPermissionTree(permissions);
		return MultiResult.build(tree);
	}
}
