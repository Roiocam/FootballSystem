package club.pypzx.FootballSystem.dao.mybatis;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.session.RowBounds;

import club.pypzx.FootballSystem.dto.GameVo;
import club.pypzx.FootballSystem.entity.Game;
import club.pypzx.FootballSystem.template.BaseMapper;

public interface GameMapper extends BaseMapper<Game> {

	@Results({ @Result(column = "game_id", property = "gameId"), @Result(column = "game_date", property = "gameDate"),
			@Result(column = "team_home", property = "teamHome"), @Result(column = "team_away", property = "teamAway"),
			@Result(column = "cup_name", property = "cupName") })
	@Select("SELECT game_id,game_date,(select team_name from pypzx_team where team_id=g.team_home and cup_id "
			+ "=g.cup_id limit 1) team_home ,(select team_name from pypzx_team " + "where team_id=g.team_away and "
			+ "cup_id =g.cup_id limit 1) team_away,c.cup_name"
			+ " FROM pypzx_game g LEFT JOIN pypzx_cup c ON g.cup_id=c.cup_id and g.cup_id=#{cupId} and g.game_date>=curdate() ORDER BY g.game_date ASC")
	public List<GameVo> selectGameByCup(@Param("cupId") String cupId, RowBounds rowBounds);


}