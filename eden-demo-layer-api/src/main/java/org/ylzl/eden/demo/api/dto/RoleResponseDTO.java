package org.ylzl.eden.demo.api.dto;

import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 角色响应（数据传输对象）
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
public class RoleResponseDTO implements Serializable {

	private Long id;

	private String code;

	private String name;

	private String description;

	private Integer status;

	private Integer sort;

	private LocalDateTime createdDate;
}
