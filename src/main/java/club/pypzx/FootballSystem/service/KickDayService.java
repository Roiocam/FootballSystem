package club.pypzx.FootballSystem.service;

import club.pypzx.FootballSystem.dto.BaseExcution;
import club.pypzx.FootballSystem.entity.KickDay;

public interface KickDayService {
	public BaseExcution<KickDay> newDay();
	public BaseExcution<KickDay> todayKick();
	public BaseExcution<KickDay> changeKick(boolean isPlus);
}
