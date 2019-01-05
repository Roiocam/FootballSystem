package club.pypzx.FootballSystem.enums;


/**
 *   用户状态枚举
 * @author Roiocam
 * @date 2019年1月5日 下午3:35:51
 */
public enum UserStateEnum {
	 SUCCESS(0, "操作成功"), INNER_ERROR(-1001, "新增失败"), EMPTY(
			-1002, "用户信息为空"),QUERY_ERROR(-1003,"查询失败"),DELETE_ERROR(-1004,"删除失败"),UPDATE_ERROR(-1005,"更新失败"),
	 WRONG_PWD(-1006,"密码错误");

	private int state;

	private String stateInfo;

	private UserStateEnum(int state, String stateInfo) {
		this.state = state;
		this.stateInfo = stateInfo;
	}

	public int getState() {
		return state;
	}

	public String getStateInfo() {
		return stateInfo;
	}

	public static UserStateEnum stateOf(int index) {
		for (UserStateEnum state : values()) {
			if (state.getState() == index) {
				return state;
			}
		}
		return null;
	}

}
