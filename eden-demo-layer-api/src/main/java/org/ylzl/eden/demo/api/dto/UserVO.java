package org.ylzl.eden.demo.api.dto;

import lombok.*;

/**
 * 用户已创建（值对象）
 *
 * @author gyl
 * @since 2.4.x
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
@Data
public class UserVO {

	private Long id;

	private String login;

	private String email;
}
