package club.pypzx.FootballSystem.dao.mybatis;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import club.pypzx.FootballSystem.dto.GameVo;
import club.pypzx.FootballSystem.entity.Game;
import club.pypzx.FootballSystem.entity.Page;

public interface GameMapper {
	public List<GameVo> selectGameByCup(@Param("cupId")String cupId,@Param("page")Page page);

	public int insert(Game obj);

	public int updateByPrimaryKey(Game obj);

	public Game selectByPrimaryKey(String objId);

	public Game selectOne(Game obj);

	public int selectCount(Object object);

	public int delete(Game record);
}