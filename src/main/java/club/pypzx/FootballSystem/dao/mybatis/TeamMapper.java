package club.pypzx.FootballSystem.dao.mybatis;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import club.pypzx.FootballSystem.dto.TeamVo;
import club.pypzx.FootballSystem.entity.Page;
import club.pypzx.FootballSystem.entity.Team;
import club.pypzx.FootballSystem.template.BaseMapper;

public interface TeamMapper extends BaseMapper<Team> {
	public TeamVo selectByPrimary(String teamId);
	public List<TeamVo> selectAllByPage(@Param("example")Team example,@Param("page")Page page);
}