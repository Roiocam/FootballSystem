package club.pypzx.FootballSystem.dao.mybatis.sqlProvider;

import java.lang.reflect.Field;

import org.apache.ibatis.jdbc.SQL;

import club.pypzx.FootballSystem.enums.TableEnum;
import club.pypzx.FootballSystem.utils.StringUtil;

/**
 *   插入SQL语句构建器类
 * @author Roiocam
 * @date 2018年12月31日 上午3:04:48
 */
public class InsertSQLProvider {
	/**
	 * 新增语句，新增所有的属性
	 * @param obj
	 * @return
	 */
	public static <T> String insert(T obj) {
		return new SQL() {
			{
				//反射获取类名,根据类名去枚举类中找相应的数据库表名
				INSERT_INTO(TableEnum.keyOf(obj.getClass().getSimpleName()));
				//反射获取属性名，遍历一遍，使用MyBatis官方API+编写驼峰命名转换，实现JAVA属性与数据库列名对应
				for (Field field : obj.getClass().getDeclaredFields()) {
					field.setAccessible(true);
					VALUES(StringUtil.underscoreName(field.getName()), "#{" + field.getName() + "}");
				}
			}
		}.toString();

	}


}
