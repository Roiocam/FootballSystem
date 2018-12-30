//package club.pypzx.FootballSystem.config.dao;
//
//import java.io.IOException;
//
//import javax.sql.DataSource;
//
//import org.mybatis.spring.SqlSessionFactoryBean;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.core.io.ClassPathResource;
//import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
//import org.springframework.core.io.support.ResourcePatternResolver;
//
//
///**
// *	 定义SqlSession的配置
// * @author Roiocam
// * @date 2018年9月10日 下午12:46:35
// *
// */
//@Configuration
//public class SqlSessionConfiguration {
//	
//	// mybatis配置文件所在路径,在application.properties中查找
//	private static String configLocation;
//	@Value("${config_location}")
//	public void setConfigLocation(String configLocation) {
//		SqlSessionConfiguration.configLocation = configLocation;
//	}
//	// mapper映射文件所在路径,在application.properties中查找
//	private static String mapperLocations;
//	@Value("${mapper_locations}")
//	public void setMapperLocations(String mapperLocations) {
//		SqlSessionConfiguration.mapperLocations = mapperLocations;
//	}
//	// 实体类所在包
//	@Value("${type_aliases_package}")
//	private String typeAliasesPackage;
//	
//	@Autowired
//	private DataSource dataSource;
//
//	/**
//	 * 设置SqlSessionFactoryBean实例,设置configuration，设置mapper映射路径 设置datasource数据源
//	 * 
//	 * @return
//	 * @throws IOException
//	 */
//	@Bean(name = "sqlSessionFactory")
//	public SqlSessionFactoryBean createSqlSessionFactoryBean() throws IOException {
//		SqlSessionFactoryBean sessionFactoryBean = new SqlSessionFactoryBean();
//		//添加mybatis配置文件路径
//		sessionFactoryBean.setConfigLocation(new ClassPathResource(configLocation));
//		// 添加mapper扫描
//		PathMatchingResourcePatternResolver pathMatchingResourcePatternResolver = new PathMatchingResourcePatternResolver();
//		String packageSearchPath = ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX + mapperLocations;
//		//设置mapper路径为包扫描路径
//		sessionFactoryBean.setMapperLocations(pathMatchingResourcePatternResolver.getResources(packageSearchPath));
//		sessionFactoryBean.setDataSource(dataSource);
//		//实体类所在包,驼峰命名转换
//		sessionFactoryBean.setTypeAliasesPackage(typeAliasesPackage);
//		
//		
//		return sessionFactoryBean;
//	}
//
//}
