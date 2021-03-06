package club.pypzx.FootballSystem.datasource;

import org.apache.tomcat.jdbc.pool.DataSource;

/**
 * 动态数据源定时器管理。长时间无访问的数据库连接关闭。
 * 
 * @author Roiocam
 * @date 2018年12月30日 下午4:01:55
 */

public class DDSTimer {

	/**
	 * 空闲时间周期。超过这个时长没有访问的数据库连接将被释放。默认为10分钟。
	 */
	private static long idlePeriodTime = 10 * 60 * 1000;

	/**
	 * 动态数据源
	 */
	private DataSource dds;

	/**
	 * 上一次访问的时间
	 */
	private long lastUseTime;

	public DDSTimer() {
	}

	public static DDSTimer instance(DataSource dds) {
		return new DDSTimer().build(dds);
	}

	public DDSTimer build(DataSource dds) {
		this.dds = dds;
		this.lastUseTime = System.currentTimeMillis();
		return this;
	}

	/**
	 * 更新最近访问时间
	 */
	public void refreshTime() {
		lastUseTime = System.currentTimeMillis();
	}

	/**
	 * 检测数据连接是否超时关闭。
	 * 
	 * @return true-已超时关闭; false-未超时
	 */
	public boolean checkAndClose() {
		if (System.currentTimeMillis() - lastUseTime > idlePeriodTime) {
			dds.close();
			return true;
		}
		return false;
	}

	public DataSource getDds() {
		return dds;
	}
}