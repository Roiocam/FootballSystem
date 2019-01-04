package club.pypzx.FootballSystem.dao.mybatis.sqlProvider;

import java.lang.reflect.Field;

import org.apache.ibatis.jdbc.SQL;

import club.pypzx.FootballSystem.enums.TableEnum;
import club.pypzx.FootballSystem.utils.StringUtil;

/**
 * 删除SQL语句构建器类
 * 
 * @author Roiocam
 * @date 2018年12月31日 上午3:07:40
 */
public class DeleteSQLProvider {
	/**
	 * 删除语句，传入不为空的属性将作为条件
	 * @param obj
	 * @return
	 */
	public static <T> String delete(T obj) {
		try {
			return new SQL() {
				{
					DELETE_FROM(TableEnum.keyOf(obj.getClass().getSimpleName()));
					// 根据传入Obj的不为空列名检索
					for (Field field : obj.getClass().getDeclaredFields()) {
						field.setAccessible(true);
						if (field.get(obj) != null && !"".equals(field.get(obj))) {
							WHERE(StringUtil.underscoreName(field.getName()) + " = #{" + field.getName() + "}");
						}
					}
				}
			}.toString();
		} catch (Exception e) {
			return null;
		}

	}

}
