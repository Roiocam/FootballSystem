package club.pypzx.FootballSystem.dto;

import java.util.List;

import club.pypzx.FootballSystem.entity.User;
import club.pypzx.FootballSystem.enums.UserStateEnum;

public class UserExcution {
	// 结果状态
	private int state;

	// 状态标识
	private String stateInfo;

	// 用户数量
	private int count;

	// 操作的用户
	private User user;

	// 获取的user列表
	private List<User> userList;

	public UserExcution() {
		}

	// 失败的构造器
	public UserExcution(UserStateEnum stateEnum) {
			this.state = stateEnum.getState();
			this.stateInfo = stateEnum.getStateInfo();
		}

	// 成功的构造器
	public UserExcution(UserStateEnum stateEnum, User user) {
			this.state = stateEnum.getState();
			this.stateInfo = stateEnum.getStateInfo();
			this.user = user;
		}

	// 成功的构造器
	public UserExcution(UserStateEnum stateEnum, List<User> userList) {
			this.state = stateEnum.getState();
			this.stateInfo = stateEnum.getStateInfo();
			this.userList = userList;
		}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public String getStateInfo() {
		return stateInfo;
	}

	public void setStateInfo(String stateInfo) {
		this.stateInfo = stateInfo;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<User> getUserList() {
		return userList;
	}

	public void setUserList(List<User> userList) {
		this.userList = userList;
	}

}
