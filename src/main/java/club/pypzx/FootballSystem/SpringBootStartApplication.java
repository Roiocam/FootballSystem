package club.pypzx.FootballSystem;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/**
 * 修改启动类，继承 SpringBootServletInitializer 并重写 configure 方法
 * https://blog.csdn.net/tianyaleixiaowu/article/details/78201587 根据条件创建实体类
 */

public class SpringBootStartApplication extends SpringBootServletInitializer {

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		// 注意这里要指向原先用main方法执行的Application启动类
		return builder.sources(FootballSystemApplication.class);
	}
}