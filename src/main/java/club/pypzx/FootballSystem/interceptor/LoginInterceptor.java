package club.pypzx.FootballSystem.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import club.pypzx.FootballSystem.entity.User;

/**
 * 足协后台管理系统登录验证拦截
 * 
 * @author Roiocam
 * @date 2018年9月10日 下午10:56:49
 */
public class LoginInterceptor extends HandlerInterceptorAdapter {
	/**
	 * 事前拦截,用户操作之前,对登录进行拦截
	 * 
	 * @param request  请求对象
	 * @param response 响应对象
	 * @param handler  处理器
	 * @return true OR false
	 * @throws Exception 可能会抛出异常
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		// 取出用户数据
		Object userObj = request.getSession().getAttribute("user");
		if (userObj != null) {
			// 转换为personInfo实体类
			User user = (User) userObj;
			// 非空判断,并判断用用户状态为可用
			if (user != null && user.getUsername() !=null||!"".equals(user.getUsername())) {
				return true;
			}
		}
		// 若不满足,则跳到帐号登录页面
		response.sendRedirect("/FootballSystem/admin/login");
		return false;
	}

}
