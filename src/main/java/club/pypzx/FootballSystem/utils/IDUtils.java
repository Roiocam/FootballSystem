package club.pypzx.FootballSystem.utils;

/**
 *   UUID工具类
 * @author Roiocam
 * @date 2018年10月7日 下午7:12:00
 */
public class IDUtils {
	/**
	 * 获取依靠当前时间戳的UUID
	 * @return
	 */
	public static String getUUID() {
		int start = (int) ((Math.random() * 9 + 1) * 10000);
		int end = (int) ((Math.random() * 9 + 1) * 10000);
		long middle=System.currentTimeMillis();
		return start+middle+""+end;
	}
}
