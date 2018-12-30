package club.pypzx.FootballSystem.dao.mybatis;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import club.pypzx.FootballSystem.dto.GroupVo;
import club.pypzx.FootballSystem.entity.Group;

public interface GroupMapper  {
	public List<GroupVo> queryTeamByGroup(String cupId);

	public int selectCount(Group group);

	public int delete(Group group);

	public List<Group> select(Group group);

	public int insert(Group obj);

	public int updateByPrimaryKey(Group obj);

	public List<Group> selectByRowBounds(Object object, RowBounds rowBounds);

	public Group selectOne(Group obj);

	public Group selectByPrimaryKey(String objId);
}