package club.pypzx.FootballSystem.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import club.pypzx.FootballSystem.dao.KickDayDao;
import club.pypzx.FootballSystem.dbmgr.EntityFactroy;
import club.pypzx.FootballSystem.entity.KickDay;
import club.pypzx.FootballSystem.service.KickDayService;
import club.pypzx.FootballSystem.template.BaseExcution;
import club.pypzx.FootballSystem.template.BaseStateEnum;

@Service
public class KickDayServiceImpl implements KickDayService {
	@Autowired
	private KickDayDao dao;

	@Override
	public BaseExcution<KickDay> newDay() {
		KickDay kick = EntityFactroy.getBean(KickDay.class);
		kick.setDate(new Date());
		kick.setNum(0);
		dao.add(kick);
		return new BaseExcution<KickDay>(BaseStateEnum.SUCCESS, kick);

	}

	@Override
	public BaseExcution<KickDay> todayKick() {
		KickDay queryKickToday = dao.queryKickToday();
		if (queryKickToday == null) {
			queryKickToday = new KickDay(new Date(), 0);
		}
		if (queryKickToday != null && queryKickToday.getDate() != null) {
			return new BaseExcution<KickDay>(BaseStateEnum.SUCCESS, queryKickToday);
		} else {
			return new BaseExcution<KickDay>(BaseStateEnum.QUERY_ERROR);
		}
	}

	@Override
	public BaseExcution<KickDay> changeKick(boolean isPlus) {
		KickDay queryKickToday = todayKick().getObj();
		if (queryKickToday != null && queryKickToday.getDate() != null) {
			queryKickToday.setNum(isPlus ? queryKickToday.getNum() + 1 : queryKickToday.getNum() - 1);
			dao.edit(queryKickToday);
			return new BaseExcution<KickDay>(BaseStateEnum.SUCCESS);
		} else {
			return new BaseExcution<KickDay>(BaseStateEnum.FAIL);
		}
	}

}
