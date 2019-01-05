package club.pypzx.FootballSystem.service;

import club.pypzx.FootballSystem.entity.Player;
import club.pypzx.FootballSystem.entity.PlayerInfo;
import club.pypzx.FootballSystem.entity.PlayerVo;
import club.pypzx.FootballSystem.template.BaseExcution;
import club.pypzx.FootballSystem.template.BaseService;

public interface PlayerService extends BaseService<Player> {
	public Player packagePlayer(String teamId, String name, int num) throws Exception;

	public Player packagePlayer(String id, String teamId, String name, int num) throws Exception;

	public BaseExcution<Player> add(Player player, PlayerInfo info) throws Exception;

	public BaseExcution<Player> edit(Player player, PlayerInfo info) throws Exception;

	public BaseExcution<PlayerVo> findByIdMore(String id);

	public BaseExcution<PlayerVo> findAllMore(Player example, int pageIndex, int pageSize);

	public boolean checkValidCode(String validCode, String teamId);

	public BaseExcution<Player> checkValidId(String playerId);
}
