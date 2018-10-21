package club.pypzx.FootballSystem.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import club.pypzx.FootballSystem.config.dao.BaseMapper;
import club.pypzx.FootballSystem.dto.TeamVo;
import club.pypzx.FootballSystem.entity.Page;
import club.pypzx.FootballSystem.entity.Team;

public interface TeamMapper extends BaseMapper<Team> {
	public TeamVo selectByPrimary(String teamId);
	public List<TeamVo> selectAllByPage(@Param("example")Team example,@Param("page")Page page);
}