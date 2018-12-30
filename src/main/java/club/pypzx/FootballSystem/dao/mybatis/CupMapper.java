package club.pypzx.FootballSystem.dao.mybatis;

import club.pypzx.FootballSystem.entity.Cup;
import club.pypzx.FootballSystem.template.BaseMapper;

public interface CupMapper extends BaseMapper<Cup> {
	public int deleteTeamByCup(String cupId);

	public int updateCupGrouped(String cupId);

	public int updateCupNotGroup(String cupId);
}