package org.ylzl.eden.demo.api.dto;

import lombok.*;

import java.io.Serializable;

/**
 * 菜单响应（数据传输对象）
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
public class MenuResponseDTO implements Serializable {

	private Long id;

	private String name;

	private String path;

	private String icon;

	private Long parentId;

	private Integer sort;

	private Integer status;

	private String component;
}
