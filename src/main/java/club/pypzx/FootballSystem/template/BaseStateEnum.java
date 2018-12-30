package club.pypzx.FootballSystem.template;

public enum BaseStateEnum {
	SUCCESS(0, "操作成功"), INNER_ERROR(-1004, "新增失败"), EMPTY(-1001, "存在必填项未填写,对象为空!"), QUERY_ERROR(-1003, "查询失败"),
	DELETE_ERROR(-1006, "删除失败"), UPDATE_ERROR(-1005, "更新失败"), FAIL(-1000, "操作失败"), PAGE_ERROR(-1002, "分页信息错误"),
	WRONG_TEAM_COUNT(-1007,"球队数不足"),NEED_DELETE_GROUP(-1008,"请删除该赛事下的赛程分配后重试!"),SAME_TEAMNAME(-1009,"该赛事下存在相同的球队名称!"),
	SAME_PLAYERNUM(-1010,"球衣号码已被选,请重新选择号码"),MAX_TEAM_COUNT(-1011,"该赛事下球队数已满"),TO_MANY_PLAYER(-1012,"球队人数已满");
	private int state;

	private String stateInfo;

	private BaseStateEnum(int state, String stateInfo) {
		this.state = state;
		this.stateInfo = stateInfo;
	}

	public int getState() {
		return state;
	}

	public String getStateInfo() {
		return stateInfo;
	}

	public static BaseStateEnum stateOf(int index) {
		for (BaseStateEnum state : values()) {
			if (state.getState() == index) {
				return state;
			}
		}
		return null;
	}

}
