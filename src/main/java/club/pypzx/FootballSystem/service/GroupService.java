package club.pypzx.FootballSystem.service;

import club.pypzx.FootballSystem.dto.GroupVo;
import club.pypzx.FootballSystem.entity.Group;
import club.pypzx.FootballSystem.template.BaseExcution;
import club.pypzx.FootballSystem.template.BaseService;

public interface GroupService extends BaseService<Group> {
	BaseExcution<GroupVo> queryTeamByGroup(String cupId);
}
