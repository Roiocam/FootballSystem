package club.pypzx.FootballSystem.config.service;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.TransactionManagementConfigurer;


/**
 * 	TransactionManager,用于开启事务管理
 * @author Roiocam
 * @date 2018年9月10日 下午12:49:46
 *
 */
@Configuration
@EnableTransactionManagement //开启事务支持
public class TransactionManagerConfiguration  implements TransactionManagementConfigurer{//继承TransactionManagementConfigurer则可以使用tx:annotation-driven
	@Autowired
	private DataSource dataSource;

	
	@Override
	/**
	 * 关于事务管理,需要返回PlatformTransactionManager的实现,
	 */
	public PlatformTransactionManager annotationDrivenTransactionManager() {
		return new DataSourceTransactionManager(dataSource);
	}
}
