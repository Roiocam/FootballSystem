package club.pypzx.FootballSystem.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Entity(name = "GroupVo")
@Table(name = "pypzx_group")
@Component
@Scope("prototype")
public class GroupVo {
	@Id
	@Column(name = "team_id")
	private String teamId;
	@Column(name = "team_name")
	private String teamName;
	@Column(name = "team_group")
	private String teamGroup;
	public GroupVo() {
	}
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
