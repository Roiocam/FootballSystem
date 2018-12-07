package club.pypzx.FootballSystem.service;

import club.pypzx.FootballSystem.dto.BaseExcution;
import club.pypzx.FootballSystem.dto.GroupVo;
import club.pypzx.FootballSystem.dto.TeamPrint;
import club.pypzx.FootballSystem.dto.TeamVo;
import club.pypzx.FootballSystem.entity.Team;

public interface TeamService extends BaseService<Team> {
	public Team packageTeam(String cupId, String name, String code, String desc) throws Exception;

	public Team packageTeam(String id, String cupId, String name, String leaderId, String code, String desc)
			throws Exception;

	public BaseExcution<Team> createTeam(String cupId, String teamName, String vaildCode, String teamDesc);

	public BaseExcution<TeamVo> selectByPrimary(String id);

	public BaseExcution<TeamVo> queryAllByPage(Team example, int pageIndex, int pageSize);

	public BaseExcution<Team> updateTeamLeader(String teamId, String leaderId);

	public BaseExcution<Team> createTeamAddPlayer(String cupId, String teamName, String vaildCode, String teamDesc,
			String playerName, int playerNum, String playerStuno, String playerDepart, String playerTel)
			throws Exception;

	public BaseExcution<GroupVo> queryTeamByGroup(String cupId);
	public BaseExcution<TeamPrint> getTeamPrint(String teamId);
}
