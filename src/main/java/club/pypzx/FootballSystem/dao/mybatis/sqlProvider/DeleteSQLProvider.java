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
 * 删除SQL语句生成器
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

	public static void main(String[] args) {
		System.out.println(DeleteSQLProvider.delete(new Cup()));
		System.out.println(DeleteSQLProvider.delete(new Game()));
		System.out.println(DeleteSQLProvider.delete(new GameRecord()));
		System.out.println(DeleteSQLProvider.delete(new Group()));
		System.out.println(DeleteSQLProvider.delete(new KickDay()));
		System.out.println(DeleteSQLProvider.delete(new Player()));
		System.out.println(DeleteSQLProvider.delete(new PlayerInfo()));
		System.out.println(DeleteSQLProvider.delete(new PlayerRank()));
		System.out.println(DeleteSQLProvider.delete(new Team()));
		System.out.println(DeleteSQLProvider.delete(new User()));
		System.out.println(DeleteSQLProvider.delete(new WechatAccount()));
	}
}
