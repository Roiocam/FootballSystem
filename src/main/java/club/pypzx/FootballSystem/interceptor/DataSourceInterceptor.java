package club.pypzx.FootballSystem.interceptor;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.alibaba.fastjson.JSON;

import club.pypzx.FootballSystem.datasource.DBIdentifier;
import club.pypzx.FootballSystem.datasource.DynamicDataSource;
import club.pypzx.FootballSystem.utils.HttpServletRequestUtil;
import club.pypzx.FootballSystem.utils.ModelMapUtil;

/**
 * 指定数据源拦截器 用途：强制请求传递projectcode参数以访问指定数据源
 * 
 * @author Roiocam
 * @date 2018年12月30日 下午5:29:09
 */
public class DataSourceInterceptor extends HandlerInterceptorAdapter {
	private static Logger log = LogManager.getLogger(DynamicDataSource.class);

	@Override
	@ResponseBody
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		String dbCode = HttpServletRequestUtil.getString(request, "dbCode");
		if (!DBIdentifier.setDbCode(dbCode)) {
			Map<String, Object> errorMap = ModelMapUtil.getErrorMap("数据库编码不存在或有误");
			String json = JSON.toJSON(errorMap).toString();
			returnJson(response, json);
			return false;
		}
		String dbType = HttpServletRequestUtil.getString(request, "dbType");
		if (!DBIdentifier.setDbType(dbType)) {
			Map<String, Object> errorMap = ModelMapUtil.getErrorMap("数据访问对象编码不存在或有误");
			String json = JSON.toJSON(errorMap).toString();
			returnJson(response, json);
			return false;
		}
		return true;
	}

	private void returnJson(HttpServletResponse response, String json) throws Exception {
		PrintWriter writer = null;
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json; charset=utf-8");
		try {
			writer = response.getWriter();
			writer.print(json);

		} catch (IOException e) {
			log.error("response error", e);
		} finally {
			if (writer != null)
				writer.close();
		}
	}

}
