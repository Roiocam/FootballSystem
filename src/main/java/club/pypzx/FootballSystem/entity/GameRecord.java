package club.pypzx.FootballSystem.entity;

import java.util.Date;
import javax.persistence.*;

@Table(name = "pypzx_game_record")
public class GameRecord {
    @Column(name = "game_id")
    private String gameId;

    @Column(name = "player_id")
    private String playerId;

    private Integer type;

    private Date time;

    /**
     * @return game_id
     */
    public String getGameId() {
        return gameId;
    }

    /**
     * @param gameId
     */
    public void setGameId(String gameId) {
        this.gameId = gameId == null ? null : gameId.trim();
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
     * @return type
     */
    public Integer getType() {
        return type;
    }

    /**
     * @param type
     */
    public void setType(Integer type) {
        this.type = type;
    }

    /**
     * @return time
     */
    public Date getTime() {
        return time;
    }

    /**
     * @param time
     */
    public void setTime(Date time) {
        this.time = time;
    }
}