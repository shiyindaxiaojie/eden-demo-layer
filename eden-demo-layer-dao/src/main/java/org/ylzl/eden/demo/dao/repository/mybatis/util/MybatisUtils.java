package org.ylzl.eden.demo.dao.repository.mybatis.util;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.experimental.UtilityClass;
import org.apache.commons.lang3.StringUtils;
import org.ylzl.eden.commons.lang.StringConstants;
import org.ylzl.eden.spring.data.mybatis.util.SortRuleEnum;

import java.util.Arrays;

/**
 * Mybatis 工具集
 *
 * @author <a href="mailto:shiyindaxiaojie@gmail.com">gyl</a>
 * @since 2.4.13
 */
@UtilityClass
public class MybatisUtils {

	/**
	 * 追加 Like %
	 *
	 * @param column 字段
	 * @return
	 */
	public static String appendLikeSuffix(String column) {
		return StringUtils.join(column, "%");
	}

	/**
	 * 追加 23:59:59
	 *
	 * @param column 字段
	 * @return
	 */
	public static String appendEndTimeSuffix(String column) {
		return StringUtils.join(column, " 23:59:59");
	}

	/**
	 * 追加 00:00:00
	 *
	 * @param column 字段
	 * @return
	 */
	public static String appendBeginTimeSuffix(String column) {
		return StringUtils.join(column, " 00:00:00");
	}

	/**
	 * 获取表名
	 *
	 * @param clazz domain
	 * @return
	 */
	public static String getTableName(Class<?> clazz) {
		TableName tableName = clazz.getAnnotation(TableName.class);
		return tableName.value();
	}

	/**
	 * 获取排序 SQL
	 *
	 * @param sortColumn 排序字段
	 * @param sortRule 排序规则（asc/desc）
	 * @return
	 */
	public static String getOrderBySQL(String sortColumn, String sortRule) {
		StringBuilder sql = new StringBuilder();
		if (StringUtils.isNotBlank(sortColumn) && StringUtils.isNotBlank(sortRule)) {
			sql.append(" ORDER BY ");
			String rule =
				Arrays.stream(SortRuleEnum.values())
					.filter(sortRuleEnum -> sortRuleEnum.name().equalsIgnoreCase(sortRule)).findFirst().get().name();
			String[] sortColumns = sortColumn.split(StringConstants.COLON);
			int len = sortColumns.length;
			int i = 0;
			for (String column : sortColumns) {
				sql.append(column).append(StringConstants.SPACE).append(rule);
				i++;
				if (i < len) {
					sql.append(StringConstants.COMMA);
				}
			}
		}
		return sql.toString();
	}

	/**
	 * 获取分页 SQL
	 *
	 * @param pageNum 页号
	 * @param pageSize 行数
	 * @return
	 */
	public static String getLimitSQL(long pageNum, long pageSize) {
		return " LIMIT " + ((pageNum - 1) * pageSize) + ", " + pageSize;
	}
}
