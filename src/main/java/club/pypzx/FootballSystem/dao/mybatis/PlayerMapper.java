package club.pypzx.FootballSystem.dao.mybatis;

import java.util.List;

import org.apache.ibatis.annotations.One;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.session.RowBounds;

import club.pypzx.FootballSystem.dao.mybatis.sqlProvider.SelectSQLProvider;
import club.pypzx.FootballSystem.dto.PlayerVo;
import club.pypzx.FootballSystem.entity.Player;
import club.pypzx.FootballSystem.template.BaseMapper;

public interface PlayerMapper extends BaseMapper<Player> {
	@Results(id = "selectMore", value = { @Result(column = "player_id", property = "playerId"),
			@Result(column = "player_name", property = "playerName"),
			@Result(column = "player_num", property = "playerNum"),
			@Result(property = "info", column = "player_id", one = @One(select = "club.pypzx.FootballSystem.dao.mybatis.PlayerInfoMapper.selectByPrimary")),
			@Result(property = "team", column = "team_id", one = @One(select = "club.pypzx.FootballSystem.dao.mybatis.TeamMapper.selectByPrimary")) })
	@SelectProvider(type = SelectSQLProvider.class, method = "selectRowBounds")
	public List<PlayerVo> selectMoreRowBounds(Player example, RowBounds rowBounds);

	@ResultMap("selectMore")
	@SelectProvider(type = SelectSQLProvider.class, method = "selectPrimary")
	public PlayerVo selectPrimaryVo(Player example);

	@Select("SELECT * FROM pypzx_player  WHERE player_id=#{value} LIMIT 1")
	public Player selectByPrimary(String playerId);

}