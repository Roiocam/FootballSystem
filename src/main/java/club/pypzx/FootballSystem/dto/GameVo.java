package club.pypzx.FootballSystem.dto;

import club.pypzx.FootballSystem.entity.Cup;
import club.pypzx.FootballSystem.entity.Team;

public class GameVo {
	private String gameId;
	private String gameDate;
	private Team home;
	private Team away;
	private Cup cup;
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
