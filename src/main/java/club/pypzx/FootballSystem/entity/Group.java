package club.pypzx.FootballSystem.entity;

import javax.persistence.*;

@Table(name = "pypzx_group")
public class Group {
	@Column(name = "cup_id")
	private String cupId;

	@Column(name = "team_id")
	private String team_id;

	@Column(name = "team_group")
	private String teamGroup;

	public Group() {

	}
	public Group(String cupId) {
		setCupId(cupId);
	}


	public String getCupId() {
		return cupId;
	}

	public void setCupId(String cupId) {
		this.cupId = cupId;
	}

	public String getTeam_id() {
		return team_id;
	}

	public void setTeam_id(String team_id) {
		this.team_id = team_id;
	}


	public String getTeamGroup() {
		return teamGroup;
	}


	public void setTeamGroup(String teamGroup) {
		this.teamGroup = teamGroup;
	}

	

}