package club.pypzx.FootballSystem.config.web;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import club.pypzx.FootballSystem.interceptor.DataSourceInterceptor;
import club.pypzx.FootballSystem.interceptor.LoginInterceptor;

/**
 * MVC配置文件,注入Spring容器. WebMvcConfigurationSupport（配置视图解析器）
 * ApplicationContextAware （方便获取applicationContext）
 * 
 * @author Roiocam
 * @date 2018年9月10日 下午12:50:49
 *
 */
@Configuration
public class MvcConfiguration extends WebMvcConfigurationSupport implements ApplicationContextAware {
	private ApplicationContext applicationContext;

	@Override
	public void setApplicationContext(org.springframework.context.ApplicationContext applicationContext)
			throws BeansException {
		this.applicationContext = applicationContext;
		super.setApplicationContext(applicationContext);
	}

	/**
	 * 定义默认的请求处理器 对应 <mvc:default-servlet-handler/>
	 */
	@Override
	protected void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
		super.configureDefaultServletHandling(configurer);
		configurer.enable();
	}

	/**
	 * 配置视图解析器
	 * 
	 * @return
	 */
	@Bean
	public ViewResolver createViewResolver() {
		InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
		// 设置Spring容器
		viewResolver.setApplicationContext(applicationContext);
		// 取消缓存
		viewResolver.setCache(false);
		// 设置前后缀
		viewResolver.setPrefix("/WEB-INF/html/");
		viewResolver.setSuffix(".html");
		return viewResolver;
	}

	@Override
	/**
	 * 配置拦截器
	 */
	public void addInterceptors(InterceptorRegistry registry) {
		// 拦截路径
		String interceptPath = "/admin/view/**";
		// 注册拦截器
		InterceptorRegistration loginIR = registry.addInterceptor(new LoginInterceptor());
		// 配置拦截路径
		loginIR.addPathPatterns(interceptPath);
		// 配置不拦截路径
		loginIR.excludePathPatterns("/admin/view/login");
		loginIR.excludePathPatterns("/admin/service/logout");
		loginIR.excludePathPatterns("/admin/service/loginCheck");
		String adminServicePath = "/admin/service/**";
		String appServicePath = "/app/service/**";
		// 注册拦截器
		InterceptorRegistration dataSourceInterceptor = registry.addInterceptor(new DataSourceInterceptor());
		// 配置拦截路径
		dataSourceInterceptor.addPathPatterns(adminServicePath);
		dataSourceInterceptor.addPathPatterns(appServicePath);
	}

	@Override
	protected void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/").setViewName("forward:/index");
		super.addViewControllers(registry);

	}

}
