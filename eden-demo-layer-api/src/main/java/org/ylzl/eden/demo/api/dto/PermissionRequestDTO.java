package org.ylzl.eden.demo.api.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 权限请求（数据传输对象）
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
public class PermissionRequestDTO implements Serializable {

	@NotBlank(message = "权限编码不能为空")
	private String code;

	@NotBlank(message = "权限名称不能为空")
	private String name;

	@NotNull(message = "权限类型不能为空")
	private Integer type;

	private Long parentId;

	private String description;

	private Integer sort;
}
