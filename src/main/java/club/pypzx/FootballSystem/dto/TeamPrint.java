package club.pypzx.FootballSystem.dto;

import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import club.pypzx.FootballSystem.entity.PlayerVo;
import club.pypzx.FootballSystem.entity.Team;
@Component
@Scope("prototype")
public class TeamPrint {
	private Team team;
	private PlayerVo leader;
	private List<PlayerVo> playerList;
	public Team getTeam() {
		return team;
	}
	public void setTeam(Team team) {
		this.team = team;
	}
	public PlayerVo getLeader() {
		return leader;
	}
	public void setLeader(PlayerVo leader) {
		this.leader = leader;
	}
	public List<PlayerVo> getPlayerList() {
		return playerList;
	}
	public void setPlayerList(List<PlayerVo> playerList) {
		this.playerList = playerList;
	}
	
	
}