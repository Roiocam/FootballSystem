package club.pypzx.FootballSystem.entity;

import java.util.Date;


import javax.persistence.*;

/**
 *   每日踢球人数
 * @author Roiocam
 * @date 2018年9月15日 下午9:11:54
 */
@Entity
@Table(name = "pypzx_kick_day")
public class KickDay {
	@Id
	private Date date;
	private int num;
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
}
