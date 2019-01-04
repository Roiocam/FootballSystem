package club.pypzx.FootballSystem.dao.mybatis;

import java.util.List;

import org.apache.ibatis.annotations.One;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.session.RowBounds;

import club.pypzx.FootballSystem.dao.mybatis.sqlProvider.SelectSQLProvider;
import club.pypzx.FootballSystem.entity.Team;
import club.pypzx.FootballSystem.entity.TeamVo;
import club.pypzx.FootballSystem.template.BaseMapper;

public interface TeamMapper extends BaseMapper<Team> {
	@Select("SELECT * FROM pypzx_team  WHERE team_id=#{value} LIMIT 1")
	public Team selectByPrimary(String teamId);

	@Results(id = "selectMore", value = { @Result(column = "team_id", property = "teamId"),
			@Result(column = "team_name", property = "teamName"),
			@Result(column = "vaild_code", property = "vaildCode"),
			@Result(column = "team_desc", property = "teamDesc"),
			@Result(property = "cup", column = "cup_id", one = @One(select = "club.pypzx.FootballSystem.dao.mybatis.CupMapper.selectByPrimary")),
			@Result(property = "leader", column = "leader_id", one = @One(select = "club.pypzx.FootballSystem.dao.mybatis.PlayerMapper.selectByPrimary")) })
	@SelectProvider(type = SelectSQLProvider.class, method = "selectRowBounds")
	public List<TeamVo> selectMoreRowBounds(Team obj, RowBounds rowBounds);

	@ResultMap("selectMore")
	@Select("SELECT * FROM pypzx_team  WHERE team_id=#{value} LIMIT 1")
	public TeamVo selectMorePrimary(String teamId);

	@Update("UPDATE pypzx_team SET leader_id=#{leaderId} WHERE team_id=#{teamId}")
	public int updateLeader(Team obj);

}