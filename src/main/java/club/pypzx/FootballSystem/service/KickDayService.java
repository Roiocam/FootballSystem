package club.pypzx.FootballSystem.service;

import club.pypzx.FootballSystem.entity.KickDay;
import club.pypzx.FootballSystem.template.BaseExcution;

public interface KickDayService {
	public BaseExcution<KickDay> newDay();
	public BaseExcution<KickDay> todayKick();
	public BaseExcution<KickDay> changeKick(boolean isPlus);
}
