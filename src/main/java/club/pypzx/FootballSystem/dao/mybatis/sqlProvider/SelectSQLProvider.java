package club.pypzx.FootballSystem.dao.mybatis.sqlProvider;

import java.lang.reflect.Field;

import org.apache.ibatis.jdbc.SQL;

import club.pypzx.FootballSystem.entity.Cup;
import club.pypzx.FootballSystem.entity.Game;
import club.pypzx.FootballSystem.entity.GameRecord;
import club.pypzx.FootballSystem.entity.Group;
import club.pypzx.FootballSystem.entity.KickDay;
import club.pypzx.FootballSystem.entity.Player;
import club.pypzx.FootballSystem.entity.PlayerInfo;
import club.pypzx.FootballSystem.entity.PlayerRank;
import club.pypzx.FootballSystem.entity.Team;
import club.pypzx.FootballSystem.entity.User;
import club.pypzx.FootballSystem.entity.WechatAccount;
import club.pypzx.FootballSystem.enums.TableEnum;
import club.pypzx.FootballSystem.utils.StringUtil;

/**
 * 查询SQL语句生成器
 * 
 * @author Roiocam
 * @date 2018年12月31日 上午3:07:52
 */
public class SelectSQLProvider {
	/**
	 * 查询语句，返回List，根据传入的obj不为空的属性条件查询
	 * 
	 * @param obj
	 * @return
	 */
	public static <T> String select(T obj) {
		try {
			return new SQL() {
				{
					SELECT("*");
					FROM(TableEnum.keyOf(obj.getClass().getSimpleName()));
					for (Field field : obj.getClass().getDeclaredFields()) {
						field.setAccessible(true);
						if (field.get(obj) != null && !"".equals(field.get(obj))) {
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
	 * 查询语句，返回第一条匹配数据，根据传入的obj不为空的属性条件查询
	 * 
	 * @param obj
	 * @return
	 */
	public static <T> String selectOne(T obj) {
		try {
			return new SQL() {
				{
					SELECT("*");
					FROM(TableEnum.keyOf(obj.getClass().getSimpleName()));
					for (Field field : obj.getClass().getDeclaredFields()) {
						field.setAccessible(true);
						if (field.get(obj) != null && !"".equals(field.get(obj))) {
							WHERE(StringUtil.underscoreName(field.getName()) + " = #{" + field.getName() + "}");
						}
					}

				}
			}.toString() + " LITMIT 0,1";
		} catch (

		Exception e) {
			return null;
		}
	}

	public static void main(String[] args) {
		System.out.println(SelectSQLProvider.select(new Cup()));
		System.out.println(SelectSQLProvider.select(new Game()));
		System.out.println(SelectSQLProvider.select(new GameRecord()));
		System.out.println(SelectSQLProvider.select(new Group()));
		System.out.println(SelectSQLProvider.select(new KickDay()));
		System.out.println(SelectSQLProvider.select(new Player()));
		System.out.println(SelectSQLProvider.select(new PlayerInfo()));
		System.out.println(SelectSQLProvider.select(new PlayerRank()));
		System.out.println(SelectSQLProvider.select(new Team()));
		System.out.println(SelectSQLProvider.select(new User()));
		System.out.println(SelectSQLProvider.select(new WechatAccount()));
	}
}
