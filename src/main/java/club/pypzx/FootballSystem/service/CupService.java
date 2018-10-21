package club.pypzx.FootballSystem.service;

import club.pypzx.FootballSystem.dto.BaseExcution;
import club.pypzx.FootballSystem.entity.Cup;

public interface CupService extends BaseService<Cup> {
	public Cup packageCup(String name);

	public Cup packageCup(String id, String name);

	public BaseExcution<Cup> randomTeamToGroup(String cupId) throws Exception;

}
