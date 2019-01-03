package club.pypzx.FootballSystem.dao.mybatis;

import java.util.List;

import org.apache.ibatis.annotations.One;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.session.RowBounds;

import club.pypzx.FootballSystem.entity.Game;
import club.pypzx.FootballSystem.entity.GameVo;
import club.pypzx.FootballSystem.template.BaseMapper;

public interface GameMapper extends BaseMapper<Game> {

	@Results({ @Result(column = "game_id", property = "gameId"), @Result(column = "game_date", property = "gameDate"),
			@Result(property = "cup", column = "cup_id", one = @One(select = "club.pypzx.FootballSystem.dao.mybatis.CupMapper.selectByPrimary")),
			@Result(property = "home", column = "team_home", one = @One(select = "club.pypzx.FootballSystem.dao.mybatis.TeamMapper.selectByPrimary")),
			@Result(property = "away", column = "team_away", one = @One(select = "club.pypzx.FootballSystem.dao.mybatis.TeamMapper.selectByPrimary"))
	})
	@Select("SELECT * FROM pypzx_game WHERE cup_id=#{cupId} and game_date>=curdate() ORDER BY game_date ASC")
	public List<GameVo> selectGameByCup(@Param("cupId") String cupId, RowBounds rowBounds);

}