package org.ylzl.eden.demo.api.dto;

import lombok.*;

import java.io.Serializable;

/**
 * 权限响应（数据传输对象）
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
public class PermissionResponseDTO implements Serializable {

	private Long id;

	private String code;

	private String name;

	private Integer type;

	private Long parentId;

	private String description;

	private Integer sort;
}
