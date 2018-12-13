package club.pypzx.FootballSystem.config.dao;

import java.beans.PropertyVetoException;

import tk.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.mchange.v2.c3p0.ComboPooledDataSource;

import club.pypzx.FootballSystem.utils.DESUtil;
/**
 * 定义dataSource的Bean（配置文件）
 * 
 * @author Roiocam
 * @date 2018年9月10日 下午12:39:28
 *
 */
@Configuration
@MapperScan("club.pypzx.FootballSystem.dao") // 配置mybatis的mapper的扫描路径
public class DataSourceConfiguration {
	//配置DataSource所需要属性,导入application.properties中的配置
	@Value("${jdbc.driver}")
	private String jdbcDriver;     
	@Value("${jdbc.url}")
	private String jdbcUrl;
	@Value("${jdbc.username}")
	private String jdbcUsername;
	@Value("${jdbc.password}")
	private String jdbcPassword;

	@Bean(name = "dataSource")
	public ComboPooledDataSource createDataSource() throws PropertyVetoException {
		// 生成dataSource实例
		ComboPooledDataSource dataSource = new ComboPooledDataSource();
		// 和spring-dao一样做相同配置
		dataSource.setDriverClass(jdbcDriver);
		dataSource.setJdbcUrl(jdbcUrl);
		dataSource.setUser(DESUtil.getDecryptString(jdbcUsername));
		dataSource.setPassword(DESUtil.getDecryptString(jdbcPassword));
		// !--每6个小时检查所有连接池中的空闲连接，这个值一定要小于MySQL的wait_timeout时间，默认为8小时。默认0 -->
		dataSource.setIdleConnectionTestPeriod(21600);
		// <!-- c3p0连接池的私有属性 -->
		dataSource.setMaxPoolSize(10);
		dataSource.setMinPoolSize(5);
		dataSource.setMaxIdleTime(21600);
		// <!-- 关闭连接后不自动commit -->
		dataSource.setAutoCommitOnClose(false);
		// <!-- 获取连接超时时间 -->
		dataSource.setCheckoutTimeout(10000);
		// <!-- 获取连接失败后重试次数 -->
		dataSource.setAcquireRetryAttempts(2);
		return dataSource;
	}

}
