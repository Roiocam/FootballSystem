package club.pypzx.FootballSystem.entity;

import javax.persistence.*;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
@Entity
@Table(name = "pypzx_team_rank")
@Component
@Scope("prototype")
public class TeamRank {
	@Id
	@Column(name = "team_id")
	private String teamId;

	private Integer points;

	private Integer goals;

	private Integer yellow;

	public TeamRank() {

	}

	public TeamRank(String teamId) {
		setTeamId(teamId);
	}

	/**
	 * @return team_id
	 */
	public String getTeamId() {
		return teamId;
	}

	/**
	 * @param teamId
	 */
	public void setTeamId(String teamId) {
		this.teamId = teamId == null ? null : teamId.trim();
	}

	/**
	 * @return points
	 */
	public Integer getPoints() {
		return points;
	}

	/**
	 * @param points
	 */
	public void setPoints(Integer points) {
		this.points = points;
	}

	/**
	 * @return goals
	 */
	public Integer getGoals() {
		return goals;
	}

	/**
	 * @param goals
	 */
	public void setGoals(Integer goals) {
		this.goals = goals;
	}

	/**
	 * @return yellow
	 */
	public Integer getYellow() {
		return yellow;
	}

	/**
	 * @param yellow
	 */
	public void setYellow(Integer yellow) {
		this.yellow = yellow;
	}
}