package club.pypzx.FootballSystem.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
/**
 *   
 * @author Roiocam
 * @date 2018年12月31日 下午4:57:29
 */
@Entity
@Table(name = "pypzx_game")
@Component
@Scope("prototype")
public class Game {


	@Id
    @Column(name = "game_id")
    private String gameId;

    @Column(name = "game_date")
    private Date gameDate;

    @Column(name = "team_home")
    private String teamHome;

    @Column(name = "team_away")
    private String teamAway;

    @Column(name = "cup_id")
    private String cupId;

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
     * @return game_date
     */
    public Date getGameDate() {
        return gameDate;
    }

    /**
     * @param gameDate
     */
    public void setGameDate(Date gameDate) {
        this.gameDate = gameDate;
    }

    /**
     * @return team_home
     */
    public String getTeamHome() {
        return teamHome;
    }

    /**
     * @param teamHome
     */
    public void setTeamHome(String teamHome) {
        this.teamHome = teamHome == null ? null : teamHome.trim();
    }

    /**
     * @return team_away
     */
    public String getTeamAway() {
        return teamAway;
    }

    /**
     * @param teamAway
     */
    public void setTeamAway(String teamAway) {
        this.teamAway = teamAway == null ? null : teamAway.trim();
    }

    /**
     * @return cup_id
     */
    public String getCupId() {
        return cupId;
    }

    /**
     * @param cupId
     */
    public void setCupId(String cupId) {
        this.cupId = cupId == null ? null : cupId.trim();
    }
}