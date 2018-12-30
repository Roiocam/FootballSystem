package club.pypzx.FootballSystem.entity;

import javax.persistence.*;
@Entity
@Table(name = "pypzx_team")
public class Team {
    @Id
    @Column(name = "team_id")
    private String teamId;

    @Column(name = "team_name")
    private String teamName;

    @Column(name = "cup_id")
    private String cupId;
    
    @Column(name = "leader_id")
    private String leaderId;
    
    @Column(name = "vaild_code")
    private String vaildCode;
    @Column(name = "team_desc")
    private String teamDesc;
    
    public Team() {
    	
    }
    public Team(String teamId) {
    	setTeamId(teamId);
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

    /**
     * @return team_name
     */
    public String getTeamName() {
        return teamName;
    }

    /**
     * @param teamName
     */
    public void setTeamName(String teamName) {
        this.teamName = teamName == null ? null : teamName.trim();
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

	/**
	 * @return leaderId
	 */
	public String getLeaderId() {
		return leaderId;
	}

	/**
	 * @param leader_id
	 */
	public void setLeaderId(String leaderId) {
		this.leaderId = leaderId;
	}

	/**
	 * @return vaild_code
	 */
	public String getVaildCode() {
		return vaildCode;
	}

	/**
	 * @param vaildCode
	 */
	public void setVaildCode(String vaildCode) {
		this.vaildCode = vaildCode==null?null:vaildCode.substring(0,4);
	}
	public String getTeamDesc() {
		return teamDesc;
	}
	public void setTeamDesc(String teamDesc) {
		this.teamDesc = teamDesc;
	}
	
    
    
}