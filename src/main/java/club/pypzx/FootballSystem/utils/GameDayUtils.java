package club.pypzx.FootballSystem.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class GameDayUtils {
	private static Calendar cal = Calendar.getInstance();
	private static SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm");

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

	private static Date getGameDayIndex(int count, int index)  {
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

	public static List<Date> getGameDay(int gameCount)  {
		List<Date> list = new ArrayList<Date>();
		int count = 1;
		while (count != gameCount + 1) {
			Date gameDayIndex = getGameDayIndex(count, ((count-1) / 5) + 2);
			count++;
			list.add(gameDayIndex);
		}
		return list;

	}

	public static void main(String[] args) throws ParseException {
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:MM:ss");
//		System.out.println(sdf.format(GameDayUtils.getWeekDate(Calendar.MONDAY, 1)));
//		System.out.println(sdf.format(GameDayUtils.getWeekDate(Calendar.SUNDAY, 1)));
//		Date parse = sdf.parse("2018-10-21");
//		System.out.println(sdf.format(GameDayUtils.getWeekDate(parse,Calendar.SUNDAY, 1)));
		List<Date> gameDay = getGameDay(12);
		Iterator<Date> iterator = gameDay.iterator();
		while (iterator.hasNext()) {
			Date date = (Date) iterator.next();
			System.out.println(sdf1.format(date));
		}
	}

}
