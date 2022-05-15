package org.ylzl.eden.demo.api.dto;

import lombok.*;

/**
 * 用户已创建（值对象）
 *
 * @author <a href="mailto:shiyindaxiaojie@gmail.com">gyl</a>
 * @since 2.4.x
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
@Data
public class UserResponseDTO {

	private Long id;

	private String login;

	private String email;
}
