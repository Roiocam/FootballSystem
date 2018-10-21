package club.pypzx.FootballSystem.utils;

/**
 * 字符串判断方法
 * 
 * @author Roiocam
 * @date 2018年10月6日 下午3:47:24
 */
public class ParamUtils {

	/**
	 * 判断字符串是否有效
	 * 
	 * @param str
	 * @return
	 */
	public static boolean validString(String str) {
		if (str != null && !"".equals(str)) {
			return true;
		}
		return false;
	}

	/**
	 * 判断字符串是否错误，抛出时使用
	 * 
	 * @param str
	 * @return
	 */
	public static boolean emptyString(String str) {
		if (str == null || "".equals(str)) {
			return true;
		}
		return false;
	}

	/**
	 * 错误的分页
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	public static boolean wrongPage(int pageIndex, int pageSize) {
		if (pageIndex <= 0 || pageSize <= 0) {
			return true;
		}
		return false;
	}
}
