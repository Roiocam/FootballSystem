package club.pypzx.FootballSystem.dto;

import club.pypzx.FootballSystem.entity.Cup;
import club.pypzx.FootballSystem.entity.Player;

public class TeamVo {
	private String teamId;
	private String teamName;
	private Cup cup;
	private Player leader;
	private String vaildCode;
	private String teamDesc;
	public String getTeamId() {
		return teamId;
	}
	public void setTeamId(String teamId) {
		this.teamId = teamId;
	}
	public String getTeamName() {
		return teamName;
	}
	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}
	public Cup getCup() {
		return cup;
	}
	public void setCup(Cup cup) {
		this.cup = cup;
	}
	public Player getLeader() {
		return leader;
	}
	public void setLeader(Player leader) {
		this.leader = leader;
	}
	public String getVaildCode() {
		return vaildCode;
	}
	public void setVaildCode(String vaildCode) {
		this.vaildCode = vaildCode;
	}
	public String getTeamDesc() {
		return teamDesc;
	}
	public void setTeamDesc(String teamDesc) {
		this.teamDesc = teamDesc;
	}
	
	
	
}
