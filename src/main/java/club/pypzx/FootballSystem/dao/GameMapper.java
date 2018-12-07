package club.pypzx.FootballSystem.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import club.pypzx.FootballSystem.config.dao.BaseMapper;
import club.pypzx.FootballSystem.dto.GameVo;
import club.pypzx.FootballSystem.entity.Game;
import club.pypzx.FootballSystem.entity.Page;

public interface GameMapper extends BaseMapper<Game> {
	public List<GameVo> selectGameByCup(@Param("cupId")String cupId,@Param("page")Page page);
}