package club.pypzx.FootballSystem.dao.mybatis;

import club.pypzx.FootballSystem.entity.PlayerInfo;

public interface PlayerInfoMapper {

	void delete(PlayerInfo playerInfo);

	int insert(PlayerInfo packagePlayerInfo);

	int updateByPrimaryKey(PlayerInfo packagePlayerInfo);
}