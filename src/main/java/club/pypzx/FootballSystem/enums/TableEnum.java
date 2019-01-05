package club.pypzx.FootballSystem.enums;

/**
 *   数据表对应枚举
 * @author Roiocam
 * @date 2019年1月5日 下午3:35:35
 */
public enum TableEnum {
	USER("User", "pypzx_" + "user"), CUP("Cup", "pypzx_" + "cup"), GAME("Game", "pypzx_" + "game"),
	GAME_RECORD("GameRecord", "pypzx_" + "game_record"), GROUP("Group", "pypzx_" + "group"),
	KICK_DAY("KickDay", "pypzx_" + "kick_day"), PLAYER("Player", "pypzx_" + "player"),
	PLAYER_INFO("PlayerInfo", "pypzx_" + "player_info"), PLAYER_RANK("PlayerRank", "pypzx_" + "player_rank"),
	TEAM("Team", "pypzx_" + "team"), TEAM_RANK("TeamRank", "pypzx_" + "team_rank"),
	WECHAT_ACCOUNT("WechatAccount", "pypzx_" + "wechat_account");
	private String key;

	private String value;

	private TableEnum(String key, String value) {
		this.key = key;
		this.value = value;
	}

	public String getKey() {
		return key;
	}

	public String getValue() {
		return value;
	}

	public static String keyOf(String key) {
		for (TableEnum string : values()) {
			if (string.getKey().equals(key)) {
				return string.value;
			}
		}
		return null;
	}

	public static TableEnum stringOf(String key) {
		for (TableEnum string : values()) {
			if (string.getKey().equals(key)) {
				return string;
			}
		}
		return null;
	}

}
