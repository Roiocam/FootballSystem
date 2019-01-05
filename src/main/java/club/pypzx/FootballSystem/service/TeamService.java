package club.pypzx.FootballSystem.service;

import club.pypzx.FootballSystem.dto.TeamPrint;
import club.pypzx.FootballSystem.entity.GroupVo;
import club.pypzx.FootballSystem.entity.Player;
import club.pypzx.FootballSystem.entity.PlayerInfo;
import club.pypzx.FootballSystem.entity.Team;
import club.pypzx.FootballSystem.entity.TeamVo;
import club.pypzx.FootballSystem.template.BaseExcution;
import club.pypzx.FootballSystem.template.BaseService;

public interface TeamService extends BaseService<Team> {
	public Team packageTeam(String cupId, String name, String code, String desc) throws Exception;

	public Team packageTeam(String id, String cupId, String name, String leaderId, String code, String desc)
			throws Exception;

	public BaseExcution<Team> createTeam(Team team);

	public BaseExcution<TeamVo> findByIdMore(String id);

	public BaseExcution<TeamVo> findAllMore(Team t, int pageIndex, int pageSize);

	public BaseExcution<Team> editTeamLeader(String teamId, String leaderId);

	public BaseExcution<Team> createTeamAddPlayer(Team team, Player player, PlayerInfo info) throws Exception;

	public BaseExcution<GroupVo> findTeamByGroup(String cupId);

	public BaseExcution<TeamPrint> getTeamPrint(String teamId);
}
