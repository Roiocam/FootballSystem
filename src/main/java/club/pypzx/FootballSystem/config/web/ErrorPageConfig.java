package club.pypzx.FootballSystem.config.web;

import org.springframework.boot.web.server.ErrorPage;
import org.springframework.boot.web.server.ErrorPageRegistrar;
import org.springframework.boot.web.server.ErrorPageRegistry;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;

/**
 * 错误页面配置
 * 
 * @author Roiocam
 * @date 2019年1月5日 下午3:25:28
 */
@Configuration
public class ErrorPageConfig implements ErrorPageRegistrar {

	/**
	 * 配置默认错误页面（仅用于内嵌tomcat启动时） 使用这种方式，在打包为war后不起作用
	 *
	 * @return
	 */
	@Override
	public void registerErrorPages(ErrorPageRegistry registry) {
		// 1、按错误的类型显示错误的网页
		// 错误类型为404，找不到网页的，默认显示404.html网页
		ErrorPage e404 = new ErrorPage(HttpStatus.NOT_FOUND, "/app/view/404");
		// 错误类型为500，表示服务器响应错误，默认显示500.html网页
		ErrorPage e500 = new ErrorPage(HttpStatus.INTERNAL_SERVER_ERROR, "/app/view/404");
		registry.addErrorPages(e404, e500);
	}

}
