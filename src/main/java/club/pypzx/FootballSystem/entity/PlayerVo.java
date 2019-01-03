package club.pypzx.FootballSystem.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity(name="PlayerVo")
@Table(name = "pypzx_player")
public class PlayerVo {
	@Id
	@Column(name = "player_id")
	private String playerId;
	@Column(name = "player_name")
	private String playerName;
	@Column(name = "player_num")
	private Integer playerNum;
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "team_id", referencedColumnName = "team_id")
	private Team team;
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "player_id", referencedColumnName = "player_id")
	private PlayerInfo info;
	public PlayerVo() {
	}
	public String getPlayerId() {
		return playerId;
	}

	public void setPlayerId(String playerId) {
		this.playerId = playerId;
	}

	public String getPlayerName() {
		return playerName;
	}

	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}

	public Integer getPlayerNum() {
		return playerNum;
	}

	public void setPlayerNum(Integer playerNum) {
		this.playerNum = playerNum;
	}

	public Team getTeam() {
		return team;
	}

	public void setTeam(Team team) {
		this.team = team;
	}

	public PlayerInfo getInfo() {
		return info;
	}

	public void setInfo(PlayerInfo info) {
		this.info = info;
	}

}