package club.pypzx.FootballSystem.datasource;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Timer;

import org.apache.tomcat.jdbc.pool.DataSource;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import club.pypzx.FootballSystem.dbmgr.EntityFactroy;

/**
 * 动态数据源管理器
 * 
 * @author Roiocam
 * @date 2018年12月30日 下午4:03:09
 */
@Component
@Scope(value = "singleton")
public class DDSHolder {
	private static DDSHolder instance = null;

	/**
	 * 管理动态数据源列表。<工程编码，数据源> 根据工程编码管理具体的数据源 DDSTimer， 对数据源进行二次封装，保证清除超时连接
	 */
	private Map<String, DDSTimer> ddsMap = new HashMap<String, DDSTimer>();

	/**
	 * 通过定时任务周期性清除不使用的数据源
	 */
	private static Timer clearIdleTask = new Timer();
	static {
		clearIdleTask.schedule(new ClearIdleTimerTask(), 5000, 60 * 1000);
	};

	/**
	 * 关闭默认构造函数，使用统一的instance方法（主要用途实现单例模式）
	 */
	private DDSHolder() {
	}

	/**
	 * 获取单例对象
	 */
	public static DDSHolder instance() {
		if (instance == null) {
			instance = EntityFactroy.getBean(DDSHolder.class);
		}
		return instance;
	}

	/**
	 * 添加动态数据源。（以工程编码保存）
	 * 
	 * @param projectCode 项目编码
	 * @param dds         dds
	 */
	public synchronized void addDDS(String projectCode, DataSource dds) {
		DDSTimer ddst = DDSTimer.instance(dds);
		ddsMap.put(projectCode, ddst);
	}

	/**
	 * 查询动态数据源
	 * 
	 * @param projectCode 项目编码
	 * @return dds
	 */
	public synchronized DataSource getDDS(String projectCode) {
		if (ddsMap.containsKey(projectCode)) {
			DDSTimer ddst = ddsMap.get(projectCode);
			// 数据源重新使用，刷新时间
			ddst.refreshTime();
			return ddst.getDds();
		}

		return null;
	}

	/**
	 * 清除超时无人使用的数据源。
	 */
	public synchronized void clearIdleDDS() {
		Iterator<Entry<String, DDSTimer>> iter = ddsMap.entrySet().iterator();
		for (; iter.hasNext();) {
			Entry<String, DDSTimer> entry = iter.next();
			if (entry.getValue().checkAndClose()) {
				iter.remove();
			}
		}
	}

}