package club.pypzx.FootballSystem.config.service;

import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

/**
 * JPA的实体类管理工厂的配置类
 * 
 * @author Roiocam
 * @date 2019年1月5日 下午3:22:26
 */
@Configuration
public class EntityManagerFactoriesConfiguration {
	@Autowired
	@Qualifier("dataSource")
	private DataSource dataSource;

	/**
	 * 设置实体类的管理工厂,预设参数 [ 扫描包、数据源、适配器 ] 
	 * @return
	 */
	@Bean(name = "entityManagerFactory")
	public LocalContainerEntityManagerFactoryBean emf() {
		LocalContainerEntityManagerFactoryBean emf = new LocalContainerEntityManagerFactoryBean();
		emf.setDataSource(dataSource);
		emf.setPackagesToScan(new String[] { "club.pypzx.FootballSystem.entity" });
		emf.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
		return emf;
	}
}