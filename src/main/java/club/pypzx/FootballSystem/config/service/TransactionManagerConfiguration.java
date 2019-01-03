package club.pypzx.FootballSystem.config.service;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import club.pypzx.FootballSystem.datasource.DBIdentifier;
import club.pypzx.FootballSystem.enums.DBType;

/**
 * TransactionManager,用于开启事务管理
 * 
 * @author Roiocam
 * @date 2018年9月10日 下午12:49:46
 *
 */
@Configuration
@EnableTransactionManagement // 开启事务支持
public class TransactionManagerConfiguration {
	@Autowired
	private EntityManagerFactory emf;
	@Autowired
	@Qualifier("dataSource")
	private DataSource dataSource;

	@Bean(name = "transactionManager")
	public PlatformTransactionManager transactionManager() {
		if (DBIdentifier.getDbType() != null && DBIdentifier.getDbType().equals(DBType.MY_BATIS)) {
			return new DataSourceTransactionManager(dataSource);
		} else {
			JpaTransactionManager tm = new JpaTransactionManager();
			tm.setEntityManagerFactory(emf);
			tm.setDataSource(dataSource);
			return tm;
		}

	}
}
