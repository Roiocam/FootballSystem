package club.pypzx.FootballSystem.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import club.pypzx.FootballSystem.config.dao.BaseMapper;
import club.pypzx.FootballSystem.dto.PlayerVo;
import club.pypzx.FootballSystem.entity.Page;
import club.pypzx.FootballSystem.entity.Player;

public interface PlayerMapper extends BaseMapper<Player> {
	public List<PlayerVo> selectAllByPage(@Param("example")Player example,@Param("page")Page page);
	public PlayerVo selectByPrimary(String playerId);
	public int selectCountByPrimary(String playerId);
}