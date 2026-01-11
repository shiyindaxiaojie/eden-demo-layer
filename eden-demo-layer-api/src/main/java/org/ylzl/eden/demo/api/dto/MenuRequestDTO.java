package org.ylzl.eden.demo.api.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

/**
 * 菜单请求（数据传输对象）
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
public class MenuRequestDTO implements Serializable {

	@NotBlank(message = "菜单名称不能为空")
	private String name;

	@NotBlank(message = "菜单路径不能为空")
	@Pattern(regexp = "^/[a-zA-Z0-9/_-]*$", message = "菜单路径格式不正确")
	private String path;

	private String icon;

	private Long parentId;

	private Integer sort;

	private String component;
}
