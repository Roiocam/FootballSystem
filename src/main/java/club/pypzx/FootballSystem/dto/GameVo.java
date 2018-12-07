package club.pypzx.FootballSystem.dto;

import java.util.Date;

public class GameVo {
	private String gameId;
	private String gameDate;
	private String teamHome;
	private String teamAway;
	private String cupName;
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
	public String getTeamHome() {
		return teamHome;
	}
	public void setTeamHome(String teamHome) {
		this.teamHome = teamHome;
	}
	public String getTeamAway() {
		return teamAway;
	}
	public void setTeamAway(String teamAway) {
		this.teamAway = teamAway;
	}
	public String getCupName() {
		return cupName;
	}
	public void setCupName(String cupName) {
		this.cupName = cupName;
	}

	
}
