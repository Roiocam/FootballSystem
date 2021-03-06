package club.pypzx.FootballSystem.enums;

/**
 *   决策枚举
 * @author Roiocam
 * @date 2019年1月5日 下午3:35:22
 */
public enum DecideEnum {
	TRUE(1, "1"), FALSE(0, "0");
	private int state;

	private String value;

	private DecideEnum(int state, String value) {
		this.state = state;
		this.value = value;
	}

	public int getState() {
		return state;
	}

	public String getValue() {
		return value;
	}
	public static DecideEnum stringOf(int index) {
		for (DecideEnum string : values()) {
			if (string.getState() == index) {
				return string;
			}
		}
		return null;
	}
}
