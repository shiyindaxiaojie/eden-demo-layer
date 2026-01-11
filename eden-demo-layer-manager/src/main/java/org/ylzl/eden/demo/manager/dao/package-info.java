/**
 * DAO 组合复用层
 * <p>《阿里巴巴Java开发手册》Manager 层职责：</p>
 * <ul>
 *     <li>与 DAO 层交互，对多个 DAO 的组合复用</li>
 *     <li>复杂业务场景下的数据访问编排</li>
 *     <li>跨表、跨数据源的数据操作封装</li>
 *     <li>通用能力下沉，避免 Service 层代码膨胀</li>
 * </ul>
 *
 * @author <a href="mailto:shiyindaxiaojie@gmail.com">gyl</a>
 * @since 2.4.14
 */
package org.ylzl.eden.demo.manager.dao;
