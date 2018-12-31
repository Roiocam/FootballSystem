package club.pypzx.FootballSystem.dao.mybatis;

import org.apache.ibatis.annotations.Select;

import club.pypzx.FootballSystem.entity.PlayerInfo;
import club.pypzx.FootballSystem.template.BaseMapper;

public interface PlayerInfoMapper extends BaseMapper<PlayerInfo> {
	@Select("SELECT * FROM pypzx_player_info  WHERE player_id=#{value} LIMIT 1")
	public PlayerInfo selectByPrimary(String objId);

	@Select("SELECT COUNT(1)  FROM pypzx_player_info WHERE player_stuno=#{value} LIMIT 1")
	public PlayerInfo selectCountByStuno(String player_stuno);
}