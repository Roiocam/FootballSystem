package club.pypzx.FootballSystem.dto;

import club.pypzx.FootballSystem.entity.Cup;

public class TeamVo {
	private String teamId;
	private String teamName;
	private Cup cup;
	private String leaderName;
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
	public String getLeaderName() {
		return leaderName;
	}
	public void setLeaderName(String leaderName) {
		this.leaderName = leaderName;
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
