package club.pypzx.FootballSystem.config.dao;

import club.pypzx.FootballSystem.datasource.DynamicDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

/**
 * 数据源的配置管理
 * 
 * @author Roiocam
 * @date 2018年12月30日 下午2:36:36
 */
@Configuration
@MapperScan("club.pypzx.FootballSystem.dao.mybatis") //配置mybatis的mapper的扫描路径
public class DataSourceConfiguration {

	/**
	 * 根据配置参数创建数据源。使用派生的子类创建。
	 * 
	 * @return 数据源
	 */
	@Bean(name = "dataSource")
	@ConfigurationProperties(prefix = "spring.datasource")
	public DataSource getDataSource() {
		/*
		 * Spring Boot还提供了一个名为的实用程序构建器类，DataSourceBuilder可用于创建其中一个标准数据源（如果它位于类路径中）。
		 * 构建器可以根据类路径上的可用内容检测要使用的那个。它还会根据JDBC URL自动检测驱动程序。
		 * 
		 * @Bean
		 * 
		 * @ConfigurationProperties("app.datasource") public DataSource dataSource() {
		 * return DataSourceBuilder.create().build(); }
		 * 
		 * Spring boot
		 * 文档:https://docs.spring.io/spring-boot/docs/2.0.0.RELEASE/reference/htmlsingle
		 * /#howto-configure-a-datasource 另有其他两种方式，在本文件夹的Markdown文件中
		 */
		DataSourceBuilder builder = DataSourceBuilder.create();
		builder.type(DynamicDataSource.class);
		// 使用Spring
		// Boot自动生成一个dataSource对象(已写好的参数为url,username,password,driver-class-name)

		return builder.build();
	}

	/**
	 * 创建会话工厂。
	 * 
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
