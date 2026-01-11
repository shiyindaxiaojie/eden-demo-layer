package org.ylzl.eden.demo.api.dto;

import lombok.*;
import org.ylzl.eden.spring.framework.dto.PageQuery;

/**
 * 角色分页查询（数据传输对象）
 *
 * @author <a href="mailto:shiyindaxiaojie@gmail.com">gyl</a>
 * @since 2.4.14
 */
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Data
public class RolePageQuery extends PageQuery {

	private String code;

	private String name;

	private Integer status;
}
