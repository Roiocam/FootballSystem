package club.pypzx.FootballSystem.utils;

import javax.servlet.http.HttpServletRequest;

/**
 * HTTP参数获取工具类
 * 
 * @author Roiocam
 * @date 2019年1月5日 下午3:16:28
 */
public class HttpServletRequestUtil {
	/**
	 * 根据KEY获取参数并将获取的参数转换成INT类型
	 * 
	 * @param request
	 * @param key
	 * @return
	 */
	public static int getInt(HttpServletRequest request, String key) {
		try {
			return Integer.decode(request.getParameter(key));
		} catch (Exception e) {
			return -1;
		}
	}

	/**
	 * 根据KEY获取参数并将获取的参数转换成Long类型
	 * 
	 * @param request
	 * @param key
	 * @return
	 */
	public static Long getLong(HttpServletRequest request, String key) {
		try {
			return Long.valueOf(request.getParameter(key));
		} catch (Exception e) {
			return -1L;
		}
	}
	/**
	 * 根据KEY获取参数并将获取的参数转换成Double类型
	 * 
	 * @param request
	 * @param key
	 * @return
	 */
	public static Double getDouble(HttpServletRequest request, String key) {
		try {
			return Double.parseDouble(request.getParameter(key));
		} catch (Exception e) {
			return -1d;
		}
	}

	/**
	 * 根据KEY获取参数并将获取的参数转换成boolean类型
	 * 
	 * @param request
	 * @param key
	 * @return
	 */
	public static boolean getBoolean(HttpServletRequest request, String key) {
		try {
			return Boolean.valueOf(request.getParameter(key));
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * 根据KEY获取参数并将获取的参数转换成String类型
	 * @param request
	 * @param key
	 * @return
	 */
	public static String getString(HttpServletRequest request, String key) {
		try {
			String result = request.getParameter(key);
			if (result != null) {
				result = result.trim();
			}
			if ("".equals(result)) {
				result = null;
			}
			return result;
		} catch (Exception e) {
			return null;
		}
	}

}
