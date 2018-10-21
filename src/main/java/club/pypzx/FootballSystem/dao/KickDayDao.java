package club.pypzx.FootballSystem.dao;

import club.pypzx.FootballSystem.entity.KickDay;

/**
 * 管理每日踢球人数记录的持久层
 * 
 * @author Roiocam
 * @date 2018年9月15日 下午8:57:35
 */
public interface KickDayDao {
	public KickDay queryKickToday();

	public int insertKickDay(KickDay kickDay);

	public int updateKickDayPlus();

	public int updateKickDayReduce();
}
