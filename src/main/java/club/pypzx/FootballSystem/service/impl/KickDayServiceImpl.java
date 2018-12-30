package club.pypzx.FootballSystem.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import club.pypzx.FootballSystem.dao.KickDayDao;
import club.pypzx.FootballSystem.entity.KickDay;
import club.pypzx.FootballSystem.service.KickDayService;
import club.pypzx.FootballSystem.template.BaseExcution;
import club.pypzx.FootballSystem.template.BaseStateEnum;

@Service
public class KickDayServiceImpl implements KickDayService {
	@Autowired
	private KickDayDao kickDAO;

	@Override
	public BaseExcution<KickDay> newDay() {
		KickDay kick = new KickDay();
		kick.setDate(new Date());
		kick.setNum(0);
		int insertKickDay = kickDAO.insertKickDay(kick);
		if (insertKickDay > 0) {
			return new BaseExcution<KickDay>(BaseStateEnum.SUCCESS, kick);
		} else {
			return new BaseExcution<KickDay>(BaseStateEnum.FAIL);
		}
	}

	@Override
	public BaseExcution<KickDay> todayKick() {
		KickDay queryKickToday = kickDAO.queryKickToday();
		if (queryKickToday != null && queryKickToday.getDate() != null) {
			return new BaseExcution<KickDay>(BaseStateEnum.SUCCESS, queryKickToday);
		} else {
			return new BaseExcution<KickDay>(BaseStateEnum.QUERY_ERROR);
		}
	}

	@Override
	public BaseExcution<KickDay> changeKick(boolean isPlus) {
		KickDay queryKickToday = kickDAO.queryKickToday();
		if (queryKickToday != null && queryKickToday.getDate() != null) {
			int updateKickDay = 0;
			if (isPlus) {
				updateKickDay = kickDAO.updateKickDayPlus();
			} else {
				updateKickDay = kickDAO.updateKickDayReduce();
			}
			if (updateKickDay > 0)
				return new BaseExcution<KickDay>(BaseStateEnum.SUCCESS);
			return new BaseExcution<KickDay>(BaseStateEnum.FAIL);
		} else {
			return new BaseExcution<KickDay>(BaseStateEnum.FAIL);
		}
	}

}
