package club.pypzx.FootballSystem.enums;

/**
 *   组别枚举
 * @author Roiocam
 * @date 2019年1月5日 下午3:35:13
 */
public enum GroupEnum {
	GROUP_A(1, "A"), GROUP_B(2, "B"), GROUP_C(3, "C");
	private int group_int;

	private String group_string;

	private GroupEnum(int group_int, String group_string) {
		this.group_int = group_int;
		this.group_string = group_string;
	}

	public int getGroup_int() {
		return group_int;
	}

	public String getGroup_string() {
		return group_string;
	}

	public static GroupEnum stringOf(int index) {
		for (GroupEnum string : values()) {
			if (string.getGroup_int() == index) {
				return string;
			}
		}
		return null;
	}
}
