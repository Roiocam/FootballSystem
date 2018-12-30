package club.pypzx.FootballSystem.config.dao;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import club.pypzx.FootballSystem.datasource.DynamicDataSource;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * 数据源的配置管理
 * 
 * @author Roiocam
 * @date 2018年12月30日 下午2:36:36
 */
@Configuration
@MapperScan("club.pypzx.FootballSystem.dao") // 配置mybatis的mapper的扫描路径
public class DataSourceConfiguration {

	/**
	 * 根据配置参数创建数据源。使用派生的子类创建。
	 * 
	 * @return 数据源
	 */
	@Bean(name = "dataSource")
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
		return builder.build();
	}

	 * @param dataSource 数据源
	 * @return 会话工厂
	 */
	@Bean(name = "sqlSessionFactory")
	public SqlSessionFactory getSqlSessionFactory(@Qualifier("dataSource") DataSource dataSource) {
		SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
		bean.setDataSource(dataSource);

		try {
			return bean.getObject();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/*
	 * 原数据源配置
	 * 
	 * @Bean(name = "dataSource")
	 * 
	 * @Primary public ComboPooledDataSource createDataSourcepPrimary() throws
	 * PropertyVetoException { // 生成dataSource实例 ComboPooledDataSource dataSource =
	 * new ComboPooledDataSource(); // 和spring-dao一样做相同配置
	 * dataSource.setDriverClass(primaryJdbcDriver);
	 * dataSource.setJdbcUrl(primaryJdbcUrl);
	 * dataSource.setUser(DESUtil.getDecryptString(primaryJdbcUsername));
	 * dataSource.setPassword(DESUtil.getDecryptString(primaryJdbcPassword)); //
	 * !--每6个小时检查所有连接池中的空闲连接，这个值一定要小于MySQL的wait_timeout时间，默认为8小时。默认0 -->
	 * dataSource.setIdleConnectionTestPeriod(21600); // <!-- c3p0连接池的私有属性 -->
	 * dataSource.setMaxPoolSize(10); dataSource.setMinPoolSize(5);
	 * dataSource.setMaxIdleTime(21600); // <!-- 关闭连接后不自动commit -->
	 * dataSource.setAutoCommitOnClose(false); // <!-- 获取连接超时时间 -->
	 * dataSource.setCheckoutTimeout(10000); // <!-- 获取连接失败后重试次数 -->
	 * dataSource.setAcquireRetryAttempts(2); return dataSource; }
	 */

}
