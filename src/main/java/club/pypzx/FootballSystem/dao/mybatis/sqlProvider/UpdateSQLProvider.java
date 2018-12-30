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
 * 更新SQL生成器
 * 
 * @author Roiocam
 * @date 2018年12月31日 上午3:08:03
 */
public class UpdateSQLProvider {
	/**
	 * 更新语句，对传入的obj不为空的属性进行设置，以属性第一位做条件
	 * 
	 * @param obj
	 * @return
	 */
	public static <T> String update(T obj) {
		try {
			return new SQL() {
				{
					UPDATE(TableEnum.keyOf(obj.getClass().getSimpleName()));
					Field[] fields = obj.getClass().getDeclaredFields();
					for (int index = 0; index < fields.length; index++) {
						fields[index].setAccessible(true);
						if (fields[index].get(obj) != null && !"".equals(fields[index].get(obj))) {
							SET(StringUtil.underscoreName(fields[index].getName()) + " = #{" + fields[index].getName()
									+ "}");

						}
					}
					WHERE(StringUtil.underscoreName(fields[0].getName()) + " = #{" + fields[0].getName() + "}");

				}
			}.toString();
		} catch (

		Exception e) {
			return null;
		}

	}

	public static void main(String[] args) {
		System.out.println(UpdateSQLProvider.update(new Cup()));
		System.out.println(UpdateSQLProvider.update(new Game()));
		System.out.println(UpdateSQLProvider.update(new GameRecord()));
		System.out.println(UpdateSQLProvider.update(new Group()));
		System.out.println(UpdateSQLProvider.update(new KickDay()));
		System.out.println(UpdateSQLProvider.update(new Player()));
		System.out.println(UpdateSQLProvider.update(new PlayerInfo()));
		System.out.println(UpdateSQLProvider.update(new PlayerRank()));
		System.out.println(UpdateSQLProvider.update(new Team()));
		System.out.println(UpdateSQLProvider.update(new User()));
		System.out.println(UpdateSQLProvider.update(new WechatAccount()));
	}
}
