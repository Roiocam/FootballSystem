package club.pypzx.FootballSystem.dao;

import club.pypzx.FootballSystem.config.dao.BaseMapper;
import club.pypzx.FootballSystem.entity.Cup;

public interface CupMapper extends BaseMapper<Cup> {
	public int deleteTeamByCup(String cupId);

	public int updateCupGrouped(String cupId);

	public int updateCupNotGroup(String cupId);
}