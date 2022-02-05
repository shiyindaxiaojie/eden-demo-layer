package org.ylzl.eden.demo.api.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;

/**
 * 用户创建（数据传输对象）
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
public class UserDTO {

	@NotBlank(message = "用户不能为空")
	private String login;

	@NotBlank(message = "邮箱不能为空")
	private String email;
}
