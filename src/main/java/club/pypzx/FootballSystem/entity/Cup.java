package club.pypzx.FootballSystem.entity;

import javax.persistence.*;

@Entity
@Table(name = "pypzx_cup")
public class Cup {
	@Id
	@Column(name = "cup_id")
	private String cupId;

	@Column(name = "cup_name")
	private String cupName;
	@Column(name = "is_group")
	private Integer isGroup;

	public Cup() {

	}

	public Cup(String cupId) {
		setCupId(cupId);
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
	 * @return cup_name
	 */
	public String getCupName() {
		return cupName;
	}

	/**
	 * @param cupName
	 */
	public void setCupName(String cupName) {
		this.cupName = cupName == null ? null : cupName.trim();
	}

	public Integer getIsGroup() {
		return isGroup;
	}

	public void setIsGroup(Integer isGroup) {
		this.isGroup = isGroup;
	}

}