package club.pypzx.FootballSystem.entity;

import javax.persistence.*;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
@Entity
@Table(name = "pypzx_player")
@Component
@Scope("prototype")
public class Player {
    @Id
    @Column(name = "player_id")
    private String playerId;

    @Column(name = "player_name")
    private String playerName;

    @Column(name = "player_num")
    private Integer playerNum;

    @Column(name = "team_id")
    private String teamId;

    public Player() {
    	
    }
    public Player(String playerId) {
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
     * @return player_name
     */
    public String getPlayerName() {
        return playerName;
    }

    /**
     * @param playerName
     */
    public void setPlayerName(String playerName) {
        this.playerName = playerName == null ? null : playerName.trim();
    }

    /**
     * @return player_num
     */
    public Integer getPlayerNum() {
        return playerNum;
    }

    /**
     * @param playerNum
     */
    public void setPlayerNum(Integer playerNum) {
        this.playerNum = playerNum;
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
}