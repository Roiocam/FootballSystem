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

@Entity(name = "GameVo")
@Table(name = "pypzx_game")
@Component
@Scope("prototype")
public class GameVo {
	@Id
	@Column(name = "game_id")
	private String gameId;
	@Column(name = "game_date")
	private String gameDate;
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "team_home", referencedColumnName = "team_id")
	private Team home;
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "team_away", referencedColumnName = "team_id")
	private Team away;
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "cup_id", referencedColumnName = "cup_id")
	private Cup cup;
	public GameVo() {
	}

	public GameVo(String cupId) {
		this.cup = new Cup(cupId);
	}

	public String getGameId() {
		return gameId;
	}

	public void setGameId(String gameId) {
		this.gameId = gameId;
	}

	public String getGameDate() {
		return gameDate;
	}

	public void setGameDate(String gameDate) {
		this.gameDate = gameDate;
	}

	public Team getHome() {
		return home;
	}

	public void setHome(Team home) {
		this.home = home;
	}

	public Team getAway() {
		return away;
	}

	public void setAway(Team away) {
		this.away = away;
	}

	public Cup getCup() {
		return cup;
	}

	public void setCup(Cup cup) {
		this.cup = cup;
	}

}
