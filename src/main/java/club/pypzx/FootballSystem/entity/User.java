package club.pypzx.FootballSystem.entity;

import javax.persistence.*;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;



/**
 * 管理员类
 * 
 * @author Roiocam
 * @date 2018年9月10日 下午10:58:24
 */
@Entity
@Table(name = "pypzx_user")
@Component
@Scope("prototype")
public class User {
	@Id
	@Column(name = "username")
	private String username;
	
	@Column(name = "password")
	private String password;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
