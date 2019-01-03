package club.pypzx.FootballSystem.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import club.pypzx.FootballSystem.dao.jpa.KickDayRepository;
import club.pypzx.FootballSystem.dao.mybatis.KickDayMapper;
import club.pypzx.FootballSystem.datasource.DBIdentifier;
import club.pypzx.FootballSystem.entity.KickDay;
import club.pypzx.FootballSystem.enums.DBType;
import club.pypzx.FootballSystem.service.KickDayService;
import club.pypzx.FootballSystem.template.BaseExcution;
import club.pypzx.FootballSystem.template.BaseStateEnum;

@Service
public class KickDayServiceImpl implements KickDayService {
	@Autowired
	private KickDayMapper kickDAO;
	@Autowired
	private KickDayRepository repository;

	@Override
	public BaseExcution<KickDay> newDay() {
		KickDay kick = new KickDay();
		kick.setDate(new Date());
		kick.setNum(0);
		if (DBIdentifier.getDbType().equals(DBType.MY_BATIS)) {
			if (kickDAO.insert(kick) != 1) {
				return new BaseExcution<KickDay>(BaseStateEnum.FAIL);
			}
		} else if (DBIdentifier.getDbType().equals(DBType.JPA)) {
			kick = repository.save(kick);
		}
		return new BaseExcution<KickDay>(BaseStateEnum.SUCCESS, kick);

	}

	@Override
	public BaseExcution<KickDay> todayKick() {
		KickDay queryKickToday = kickDAO.queryKickToday();
		if (DBIdentifier.getDbType().equals(DBType.MY_BATIS)) {
			queryKickToday = kickDAO.queryKickToday();
		} else if (DBIdentifier.getDbType().equals(DBType.JPA)) {
			queryKickToday = repository.queryKickToday();
			if (queryKickToday == null) {
				queryKickToday = new KickDay(new Date(), 0);
			}
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
			int updateKickDay = 0;
			queryKickToday.setNum(isPlus ? queryKickToday.getNum() + 1 : queryKickToday.getNum() - 1);
			if (DBIdentifier.getDbType().equals(DBType.MY_BATIS)) {
				updateKickDay = kickDAO.update(queryKickToday);
				if (updateKickDay != 1) {
					return new BaseExcution<KickDay>(BaseStateEnum.FAIL);
				}
			} else if (DBIdentifier.getDbType().equals(DBType.JPA)) {
				repository.save(queryKickToday);
			}
			return new BaseExcution<KickDay>(BaseStateEnum.SUCCESS);
		} else {
			return new BaseExcution<KickDay>(BaseStateEnum.FAIL);
		}
	}

}
