package org.ylzl.eden.demo.api.dto;

import lombok.*;

import java.io.Serializable;
import java.util.List;

/**
 * 权限树形结构（数据传输对象）
 *
 * @author <a href="mailto:shiyindaxiaojie@gmail.com">gyl</a>
 * @since 2.4.14
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
@Data
public class PermissionTreeDTO implements Serializable {

	private Long id;

	private String code;

	private String name;

	private Integer type;

	private String description;

	private Integer sort;

	private List<PermissionTreeDTO> children;
}
