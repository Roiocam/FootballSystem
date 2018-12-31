package club.pypzx.FootballSystem.dao.mybatis;

import java.util.List;

import org.apache.ibatis.annotations.One;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.session.RowBounds;

import club.pypzx.FootballSystem.dto.TeamVo;
import club.pypzx.FootballSystem.entity.Team;
import club.pypzx.FootballSystem.entity.TeamRank;
import club.pypzx.FootballSystem.template.BaseMapper;

public interface TeamMapper extends BaseMapper<TeamRank> {
	@Select("SELECT * FROM pypzx_team  WHERE team_id=#{value} LIMIT 1")
	public Team selectByPrimary(String teamId);

	@Results(id = "selectMore", value = { @Result(column = "team_id", property = "teamId"),
			@Result(column = "team_name", property = "teamName"),
			@Result(column = "vaild_code", property = "vaildCode"),
			@Result(column = "leader_name", property = "leaderName"),
			@Result(column = "team_desc", property = "teamDesc"),
			@Result(column = "leader_name", property = "leaderName"),
			@Result(property = "cup", column = "cup_id", one = @One(select = "club.pypzx.FootballSystem.dao.mybatis.CupMapper.selectPrimary")) })
	@Select("SELECT t.team_id,t.team_name,IFNULL(player_name,'无') leader_name,t.vaild_code,t.team_desc  FROM pypzx_team t "
			+ "		LEFT JOIN pypzx_player p ON t.leader_id=p.player_id")
	public List<TeamVo> selectMoreRowBounds(RowBounds rowBounds);

	@Results(id = "selectMore")
	@Select("SELECT t.team_id,t.team_name,IFNULL(player_name,'无') leader_name,t.vaild_code,t.team_desc  FROM pypzx_team t "
			+ "		LEFT JOIN pypzx_player p ON t.leader_id=p.player_id WHERE t.team_id=#{value} LIMIT 1")
	public TeamVo selectMorePrimary(String teamId);

}