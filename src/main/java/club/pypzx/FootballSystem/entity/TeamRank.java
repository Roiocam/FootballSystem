package club.pypzx.FootballSystem.entity;

import javax.persistence.*;

@Table(name = "pypzx_team_rank")
public class TeamRank {
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