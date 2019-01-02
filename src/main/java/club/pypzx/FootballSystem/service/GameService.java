package club.pypzx.FootballSystem.service;

import club.pypzx.FootballSystem.dto.GameVo;
import club.pypzx.FootballSystem.entity.Game;
import club.pypzx.FootballSystem.template.BaseExcution;
import club.pypzx.FootballSystem.template.BaseService;

public interface GameService extends BaseService<Game> {

	BaseExcution<Game> randomGameByGroup(String cupId) throws Exception;
	BaseExcution<GameVo> findAll(String cupId,int pageIndex, int pageSize);
	BaseExcution<Game> removeGroupByCup(String cupId) throws Exception;

}
