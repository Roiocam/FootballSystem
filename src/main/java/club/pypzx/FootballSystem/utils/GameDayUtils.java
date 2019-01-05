package club.pypzx.FootballSystem.utils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 获取随机比赛日期工具类
 * 
 * @author Roiocam
 * @date 2019年1月5日 下午3:15:57
 */
public class GameDayUtils {
	private static Calendar cal = Calendar.getInstance();

	/**
	 * 根据当前日期，获取第N周后的周N的日期
	 * 
	 * @param dayOfWeek  一周中的哪天,如 Calendar.MONTHDAY
	 * @param whitchWeek 周数,1为当前周,2为下周
	 * @return
	 */
	public static Date getWeekDate(int dayOfWeek, int whitchWeek) {
		whitchWeek = (whitchWeek - 1) * 7;
		cal.setTime(new Date());

		// 获得当前日期是一个星期的第几天
		int dayWeek = cal.get(Calendar.DAY_OF_WEEK);
		if (1 == dayOfWeek && dayWeek != 1) {
			cal.add(Calendar.DAY_OF_MONTH, 7);
		}
		// 设置一个星期的第一天，按中国的习惯一个星期的第一天是星期一

		// 根据日历的规则，给当前日期减去星期几与一个星期第一天的差值
		cal.add(Calendar.DATE, dayOfWeek - dayWeek);
		cal.add(Calendar.DATE, whitchWeek);
		cal.set(Calendar.HOUR_OF_DAY, 16);
		cal.set(Calendar.MINUTE, 40);
		cal.set(Calendar.SECOND, 0);
		return cal.getTime();
	}

	private static Date getGameDayIndex(int count, int index) {
		Date temp = null;
		count = count % 5;
		switch (count) {
		case 1:
			temp = GameDayUtils.getWeekDate(Calendar.MONDAY, index);
			break;
		case 2:
			temp = GameDayUtils.getWeekDate(Calendar.TUESDAY, index);
			break;
		case 3:
			temp = GameDayUtils.getWeekDate(Calendar.THURSDAY, index);
			break;
		case 4:
			temp = GameDayUtils.getWeekDate(Calendar.THURSDAY, index);
			break;
		case 0:
			temp = GameDayUtils.getWeekDate(Calendar.FRIDAY, index);
			break;
		default:
			break;
		}
		return temp;
	}

	public static List<Date> getGameDay(int gameCount) {
		List<Date> list = new ArrayList<Date>();
		int count = 1;
		while (count != gameCount + 1) {
			Date gameDayIndex = getGameDayIndex(count, ((count - 1) / 5) + 2);
			count++;
			list.add(gameDayIndex);
		}
		return list;

	}

}
