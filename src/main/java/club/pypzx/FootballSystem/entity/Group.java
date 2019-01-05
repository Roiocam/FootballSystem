package club.pypzx.FootballSystem.entity;

import javax.persistence.*;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
@Entity
@Table(name = "pypzx_group")
@Component
@Scope("prototype")
public class Group {
	
	@Column(name = "cup_id")
	private String cupId;
	@Id
	@Column(name = "team_id")
	private String teamId;

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

	


	public String getTeamId() {
		return teamId;
	}
	public void setTeamId(String teamId) {
		this.teamId = teamId;
	}
	public String getTeamGroup() {
		return teamGroup;
	}


	public void setTeamGroup(String teamGroup) {
		this.teamGroup = teamGroup;
	}

	

}