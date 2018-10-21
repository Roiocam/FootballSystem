package club.pypzx.FootballSystem.dao;

import java.util.List;

import club.pypzx.FootballSystem.config.dao.BaseMapper;
import club.pypzx.FootballSystem.dto.GroupVo;
import club.pypzx.FootballSystem.entity.Group;

public interface GroupMapper extends BaseMapper<Group> {
	public List<GroupVo> queryTeamByGroup(String cupId);
}