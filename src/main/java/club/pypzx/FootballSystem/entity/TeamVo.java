package club.pypzx.FootballSystem.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import club.pypzx.FootballSystem.dbmgr.EntityFactroy;

@Entity(name = "TeamVo")
@Table(name = "pypzx_team")
@Component
@Scope("prototype")
public class TeamVo {
	@Id
	@Column(name = "team_id")
	private String teamId;
	@Column(name = "team_name")
	private String teamName;
	@Column(name = "vaild_code")
	private String vaildCode;
	@Column(name = "team_desc")
	private String teamDesc;
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "cup_id", referencedColumnName = "cup_id")
	private Cup cup;
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "leader_id", referencedColumnName = "player_id")
	private Player leader;

	public TeamVo() {
	}

	public TeamVo(String cupId) {
		Cup bean = EntityFactroy.getBean(Cup.class);
		bean.setCupId(cupId);
		this.cup = bean;
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
