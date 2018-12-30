package club.pypzx.FootballSystem.service;

import club.pypzx.FootballSystem.dto.PlayerVo;
import club.pypzx.FootballSystem.entity.Player;
import club.pypzx.FootballSystem.template.BaseExcution;
import club.pypzx.FootballSystem.template.BaseService;

public interface PlayerService extends BaseService<Player>{
	public  Player packagePlayer(String teamId, String name, int num) throws Exception;
	public  Player packagePlayer(String id, String teamId, String name, int num) throws Exception;
	public BaseExcution<Player> insertObject(String teamId,String name,int num,String stuno,String depart,String tel) throws Exception;
	public BaseExcution<Player> updateObject(String id,String teamId,String name,int num,String stuno,String depart,String tel)throws Exception;
	public BaseExcution<PlayerVo> selectByPrimary(String id);
	public BaseExcution<PlayerVo> queryAllByPage(Player example,int pageIndex, int pageSize) ;
	public boolean checkValidCode(String validCode,String teamId);
	public BaseExcution<Player> checkValidId(String playerId);
}
