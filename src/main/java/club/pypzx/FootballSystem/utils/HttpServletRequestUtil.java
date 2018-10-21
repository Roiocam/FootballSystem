package club.pypzx.FootballSystem.utils;

import javax.servlet.http.HttpServletRequest;

public class HttpServletRequestUtil {
	public static int getInt(HttpServletRequest request,String key) {
		try {
			return Integer.decode(request.getParameter(key));
		}catch (Exception e) {
			return -1;
		}
	}
	public static Long getLong(HttpServletRequest request,String key) {
		try {
			return Long.valueOf(request.getParameter(key));
		}catch (Exception e) {
			return -1L;
		}
	}
	public static Double getDouble(HttpServletRequest request,String key) {
		try {
			return Double.parseDouble(request.getParameter(key));
		}catch (Exception e) {
			return -1d;
		}
	}
	public static boolean getBoolean(HttpServletRequest request,String key) {
		try {
			return Boolean.valueOf(request.getParameter(key));
		}catch (Exception e) {
			return false;
		}
	}
	public static String getString(HttpServletRequest request,String key) {
		try {
			String result= request.getParameter(key);
			if(result!=null) {
				result=result.trim();
			}
			if("".equals(result)) {
				result=null;
			}
			return result;
		}catch (Exception e) {
			return null;
		}
	}
//	public static List<String> getStringList(HttpServletRequest request,String key){
//		List list=request.getParameter(key);
//	}

}
	