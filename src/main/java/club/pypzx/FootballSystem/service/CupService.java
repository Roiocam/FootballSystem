package club.pypzx.FootballSystem.service;

import club.pypzx.FootballSystem.entity.Cup;
import club.pypzx.FootballSystem.template.BaseExcution;
import club.pypzx.FootballSystem.template.BaseService;

public interface CupService extends BaseService<Cup> {
	public Cup packageCup(String name);

	public Cup packageCup(String id, String name);

	public BaseExcution<Cup> randomTeamToGroup(String cupId) throws Exception;

}
