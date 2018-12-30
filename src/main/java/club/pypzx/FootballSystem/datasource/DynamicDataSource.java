package club.pypzx.FootballSystem.datasource;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.SQLException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.tomcat.jdbc.pool.DataSource;
import org.apache.tomcat.jdbc.pool.PoolProperties;

import club.pypzx.FootballSystem.dbmgr.ProjectDBMgr;

/**
 * 动态数据源派生类。从基础的DataSource派生,实现数据库连接的动态切换
 * 
 * @author Roiocam
 * @date 2018年12月30日 下午3:46:26
 */
public class DynamicDataSource extends DataSource {

	private static Logger log = LogManager.getLogger(DynamicDataSource.class);

	/**
	 * 改写本方法是为了在请求不同工程的数据时去连接不同的数据库。
	 */
	@Override
	public Connection getConnection() {
		// 获取工程编码
		String projectCode = DBIdentifier.getProjectCode();
		// 1、根据工程编码获取获取对应数据源
		DataSource dds = DDSHolder.instance().getDDS(projectCode);
		// 2、如果数据源不存在则创建
		if (dds == null) {
			try {
				DataSource newDDS = initDDS(projectCode);
				// 在动态数据源管理器中添加该数据源
				DDSHolder.instance().addDDS(projectCode, newDDS);
				// 这里不直接返回,而是全部管理交给DDSHolder，这里的代码可用AOP优化
			} catch (IllegalArgumentException | IllegalAccessException e) {
				log.error("Init data source fail. projectCode:" + projectCode);
				return null;
			}
		}
		// 3.获取动态数据源管理器中的数据源
		dds = DDSHolder.instance().getDDS(projectCode);
		// 4.获取数据库连接并返回
		try {
			return dds.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 以当前数据对象作为模板复制一份。
	 * 
	 * @return dds
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 */
	private DataSource initDDS(String projectCode) throws IllegalArgumentException, IllegalAccessException {
		// 1.创建数据源
		DataSource dds = new DataSource();
		// 2.复制PoolConfiguration的属性
		PoolProperties property = new PoolProperties();
		Field[] pfields = PoolProperties.class.getDeclaredFields();
		for (Field f : pfields) {
			f.setAccessible(true);
			Object value = f.get(this.getPoolProperties());
			try {
				f.set(property, value);
			} catch (Exception e) {
				// 有一些static final的属性不能修改。忽略。
				log.info("Set value fail. attr name:" + f.getName());
				continue;
			}
		}
		dds.setPoolProperties(property);
		// 3.设置数据库名称和IP(一般来说，端口和用户名、密码都是统一固定的),在properties中写好了
		// 根据项目名,获取ProjectDBMgr下写好的数据库名称和具体数据库Ip
		String urlFormat = this.getUrl();
		String url = String.format(urlFormat, ProjectDBMgr.instance().getDBIP(projectCode),
				ProjectDBMgr.instance().getDBName(projectCode));
		dds.setUrl(url);
		// 4.返回动态生成的数据源
		return dds;
	}
}