package club.pypzx.FootballSystem.entity;

import javax.persistence.*;

@Table(name = "pypzx_player_info")
public class PlayerInfo {
	@Id
	@Column(name = "player_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private String playerId;

	@Column(name = "player_stuno")
	private String playerStuno;

	@Column(name = "player_depart")
	private String playerDepart;

	@Column(name = "player_tel")
	private String playerTel;

	public PlayerInfo() {

	}

	public PlayerInfo(String playerId) {
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

	public String getPlayerStuno() {
		return playerStuno;
	}

	public void setPlayerStuno(String playerStuno) {
		this.playerStuno = playerStuno == null ? null : playerStuno.trim();
	}

	public String getPlayerDepart() {
		return playerDepart;
	}

	public void setPlayerDepart(String playerDepart) {
		this.playerDepart = playerDepart;
	}

	public String getPlayerTel() {
		return playerTel;
	}

	public void setPlayerTel(String playerTel) {
		this.playerTel =playerTel;
	}

}