package org.ylzl.eden.demo.api.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * 用户创建（数据传输对象）
 *
 * @author <a href="mailto:shiyindaxiaojie@gmail.com">gyl</a>
 * @since 2.4.13
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
@Data
public class UserRequestDTO implements Serializable {

	@NotBlank(message = "用户不能为空")
	private String login;

	@NotBlank(message = "邮箱不能为空")
	private String email;
}
