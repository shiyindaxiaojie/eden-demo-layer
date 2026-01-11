package org.ylzl.eden.demo.api;

import org.ylzl.eden.demo.api.dto.*;
import org.ylzl.eden.spring.framework.dto.MultiResult;
import org.ylzl.eden.spring.framework.dto.PageResult;
import org.ylzl.eden.spring.framework.dto.Result;
import org.ylzl.eden.spring.framework.dto.SingleResult;

/**
 * 权限业务逻辑接口
 *
 * @author <a href="mailto:shiyindaxiaojie@gmail.com">gyl</a>
 * @since 2.4.14
 */
public interface PermissionService {

	Result createPermission(PermissionRequestDTO dto);

	Result modifyPermission(Long id, PermissionRequestDTO dto);

	Result removePermission(Long id);

	SingleResult<PermissionResponseDTO> getPermissionById(Long id);

	PageResult<PermissionResponseDTO> listPermissionByPage(PermissionPageQuery query);

	MultiResult<PermissionTreeDTO> getPermissionTree();
}
