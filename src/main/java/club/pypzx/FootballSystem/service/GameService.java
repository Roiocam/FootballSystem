package club.pypzx.FootballSystem.service;

import club.pypzx.FootballSystem.dto.BaseExcution;
import club.pypzx.FootballSystem.dto.GameVo;
import club.pypzx.FootballSystem.entity.Game;

public interface GameService extends BaseService<Game> {

	BaseExcution<Game> randomGameByGroup(String cupId) throws Exception;
	BaseExcution<GameVo> queryAll(String cupId,int pageIndex, int pageSize);
	BaseExcution<Game> removeGroupByCup(String cupId) throws Exception;

}
