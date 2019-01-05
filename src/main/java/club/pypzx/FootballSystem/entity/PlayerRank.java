package club.pypzx.FootballSystem.entity;

import javax.persistence.*;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
@Entity
@Table(name = "pypzx_player_rank")
@Component
@Scope("prototype")
public class PlayerRank {
	@Id
	@Column(name = "player_id")
	private String playerId;

	private Integer goals;

	private Integer penalty;

	public PlayerRank() {

	}

	public PlayerRank(String playerId) {
		setPlayerId(playerId);
	}

	/**
	 * @return player_id
	 */
	public String getPlayerId() {
		return playerId;
	}

	/**
	 * @param playerId
	 */
	public void setPlayerId(String playerId) {
		this.playerId = playerId == null ? null : playerId.trim();
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
	 * @return penalty
	 */
	public Integer getPenalty() {
		return penalty;
	}

	/**
	 * @param penalty
	 */
	public void setPenalty(Integer penalty) {
		this.penalty = penalty;
	}
}