package club.pypzx.FootballSystem.dao.mybatis.sqlProvider;

import java.lang.reflect.Field;

import org.apache.ibatis.jdbc.SQL;

import club.pypzx.FootballSystem.enums.TableEnum;
import club.pypzx.FootballSystem.utils.StringUtil;

/**
 * 查询SQL语句构建器类
 * 
 * @author Roiocam
 * @date 2018年12月31日 上午3:07:52
 */
public class SelectSQLProvider {
	/**
	 * 分页查询语句，返回List，根据传入的obj不为空的属性条件查询 若未提供分页值
	 * 
	 * @param obj rowBounds 分页对象
	 * @return
	 */
	public static <T> String selectRowBounds(T obj) {
		try {
			return new SQL() {
				{
					SELECT("*");
					FROM(TableEnum.keyOf(obj.getClass().getSimpleName()));
					for (Field field : obj.getClass().getDeclaredFields()) {
						field.setAccessible(true);
						if (field.get(obj) != null && !"serialVersionUID".equals(field.getName())
								&& !"".equals(field.get(obj))) {
							WHERE(StringUtil.underscoreName(field.getName()) + " = #{" + field.getName() + "}");
						}
					}
				}
			}.toString();
		} catch (

		Exception e) {
			return null;
		}

	}

	/**
	 * 根据传入的obj不为空的属性条件查询 总条数 ,与分页连用
	 * 
	 * @param obj
	 * @return
	 */
	public static <T> String selectCount(T obj) {
		try {
			return new SQL() {
				{
					Field[] fields = obj.getClass().getDeclaredFields();
					SELECT("COUNT(" + StringUtil.underscoreName(fields[0].getName()) + ")");
					FROM(TableEnum.keyOf(obj.getClass().getSimpleName()));
					for (Field field : fields) {
						field.setAccessible(true);
						if (field.get(obj) != null && !"serialVersionUID".equals(field.getName())
								&& !"".equals(field.get(obj))) {
							WHERE(StringUtil.underscoreName(field.getName()) + " = #{" + field.getName() + "}");
						}
					}
				}
			}.toString();
		} catch (Exception e) {
			return null;
		}

	}

	/**
	 * 查询语句，返回第一条匹配数据，根据传入的obj不为空的属性条件查询
	 *
	 * @param obj
	 * @return
	 */
	public static <T> String selectPrimary(T obj) {
		try {
			return new SQL() {
				{
					SELECT("*");
					FROM(TableEnum.keyOf(obj.getClass().getSimpleName()));
					for (Field field : obj.getClass().getDeclaredFields()) {
						field.setAccessible(true);
						if (field.get(obj) != null && !"serialVersionUID".equals(field.getName())
								&& !"".equals(field.get(obj))) {
							WHERE(StringUtil.underscoreName(field.getName()) + " = #{" + field.getName() + "}");
						}
					}

				}
			}.toString().concat(" LIMIT 0,1 ");
		} catch (Exception e) {
			return null;
		}
	}

	public static void main(String[] args) {
//		System.out.println(SelectSQLProvider.selectRowBounds(new Cup(), null));
//		System.out.println(SelectSQLProvider.selectRowBounds(new Game(), null));
//		System.out.println(SelectSQLProvider.selectRowBounds(new GameRecord(), null));
//		System.out.println(SelectSQLProvider.selectRowBounds(new Group(), null));
//		System.out.println(SelectSQLProvider.selectRowBounds(new KickDay(), null));
//		System.out.println(SelectSQLProvider.selectRowBounds(new Player(), null));
//		System.out.println(SelectSQLProvider.selectRowBounds(new PlayerInfo(), null));
//		System.out.println(SelectSQLProvider.selectRowBounds(new PlayerRank(), null));
//		System.out.println(SelectSQLProvider.selectRowBounds(new Team(), null));
//		System.out.println(SelectSQLProvider.selectRowBounds(new User(), null));
//		System.out.println(SelectSQLProvider.selectRowBounds(new WechatAccount(), null));
	}
}
