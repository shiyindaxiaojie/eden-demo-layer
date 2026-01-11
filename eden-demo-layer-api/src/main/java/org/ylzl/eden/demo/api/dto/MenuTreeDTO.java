package org.ylzl.eden.demo.api.dto;

import lombok.*;

import java.io.Serializable;
import java.util.List;

/**
 * 菜单树形结构（数据传输对象）
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
public class MenuTreeDTO implements Serializable {

	private Long id;

	private String name;

	private String path;

	private String icon;

	private Integer sort;

	private Integer status;

	private String component;

	private List<MenuTreeDTO> children;
}
