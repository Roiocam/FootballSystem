package club.pypzx.FootballSystem.dto;

import club.pypzx.FootballSystem.entity.PlayerInfo;
import club.pypzx.FootballSystem.entity.Team;

public class PlayerVo {
	private String playerId;
	private String playerName;
	private Integer playerNum;
	private Team team;
	private PlayerInfo info;
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
