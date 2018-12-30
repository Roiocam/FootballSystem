package club.pypzx.FootballSystem.dao.mybatis;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import club.pypzx.FootballSystem.dto.TeamVo;
import club.pypzx.FootballSystem.entity.Page;
import club.pypzx.FootballSystem.entity.Team;

public interface TeamMapper  {
	public TeamVo selectByPrimary(String teamId);
	public List<TeamVo> selectAllByPage(@Param("example")Team example,@Param("page")Page page);
	public List<Team> select(Team t);
	public Team selectOne(Team team);
	public Team selectByPrimaryKey(String objId);
	public int updateByPrimaryKey(Team obj);
	public List<Team> selectByRowBounds(Object object, RowBounds rowBounds);
	public int selectCount(Object object);
	public int updateByPrimaryKeySelective(Team team);
	public int delete(Team team);
	public int insert(Team obj);
}