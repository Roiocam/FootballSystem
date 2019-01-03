package club.pypzx.FootballSystem.dto;

import javax.persistence.Column;

public class GroupVo {
	@Column(name = "team_id")
	private String teamId;
	@Column(name = "team_name")
	private String teamName;
	@Column(name = "team_group")
	private String teamGroup;

	public GroupVo(String teamId, String teamName, String teamGroup) {
		this.teamId = teamId;
		this.teamGroup = teamGroup;
		this.teamName = teamName;
	}

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

	public String getTeamGroup() {
		return teamGroup;
	}

	public void setTeamGroup(String teamGroup) {
		this.teamGroup = teamGroup;
	}

}
