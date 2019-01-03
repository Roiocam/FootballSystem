package club.pypzx.FootballSystem.config.service;

import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

@Configuration
public class EntityManagerFactoriesConfiguration {
	@Autowired
	@Qualifier("dataSource")
	private DataSource dataSource;

	@Bean(name = "entityManagerFactory")
	public LocalContainerEntityManagerFactoryBean emf() {
		LocalContainerEntityManagerFactoryBean emf = new LocalContainerEntityManagerFactoryBean();
		emf.setDataSource(dataSource);
		emf.setPackagesToScan(new String[] { "club.pypzx.FootballSystem.entity" });
		emf.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
		return emf;
	}
}