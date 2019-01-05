package club.pypzx.FootballSystem.entity;

import javax.persistence.*;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Entity
@Table(name = "pypzx_wechat_account")
@Component
@Scope("prototype")
public class WechatAccount {
	@Id
	@Column(name = "openid")
	private String openid;

	@Column(name = "nick_name")
	private String nickName;

	@Column(name = "player_id")
	private String playerId;
	@Column(name = "team_id")
	private String teamId;

	private String img;

	/**
	 * @return openid
	 */
	public String getOpenid() {
		return openid;
	}

	/**
	 * @param openid
	 */
	public void setOpenid(String openid) {
		this.openid = openid == null ? null : openid.trim();
	}

	/**
	 * @return nick_name
	 */
	public String getNickName() {
		return nickName;
	}

	/**
	 * @param nickName
	 */
	public void setNickName(String nickName) {
		this.nickName = nickName == null ? null : nickName.trim();
	}

	/**
	 * @return img
	 */
	public String getImg() {
		return img;
	}

	/**
	 * @param img
	 */
	public void setImg(String img) {
		this.img = img == null ? null : img.trim();
	}

	public String getPlayerId() {
		return playerId;
	}

	public void setPlayerId(String playerId) {
		this.playerId = playerId;
	}

	public String getTeamId() {
		return teamId;
	}

	public void setTeamId(String teamId) {
		this.teamId = teamId;
	}

}