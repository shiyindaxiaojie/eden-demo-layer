package org.ylzl.eden.demo.api.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * 角色请求（数据传输对象）
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
public class RoleRequestDTO implements Serializable {

	@NotBlank(message = "角色编码不能为空")
	@Pattern(regexp = "^[A-Z][A-Z0-9_]{1,29}$", message = "角色编码格式不正确")
	private String code;

	@NotBlank(message = "角色名称不能为空")
	@Size(min = 2, max = 50, message = "角色名称长度2-50")
	private String name;

	private String description;

	private Integer sort;
}
