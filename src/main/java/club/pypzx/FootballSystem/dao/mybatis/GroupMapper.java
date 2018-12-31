package club.pypzx.FootballSystem.dao.mybatis;

import java.util.List;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import club.pypzx.FootballSystem.dto.GroupVo;
import club.pypzx.FootballSystem.entity.Group;
import club.pypzx.FootballSystem.template.BaseMapper;

public interface GroupMapper extends BaseMapper<Group> {
	@Results({ @Result(column = "team_name", property = "teamName"), @Result(column = "team_id", property = "teamId"),
			@Result(column = "team_group", property = "teamGroup") })
	@Select("SELECT g.team_id,t.team_name,g.team_group FROM pypzx_group g  LEFT JOIN"
			+ "pypzx_team t ON g.team_id=t.team_id  WHERE g.cup_id=#{value}")
	public List<GroupVo> queryTeamByGroup(String cupId);

}