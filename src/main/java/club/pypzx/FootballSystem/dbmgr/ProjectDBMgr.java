package club.pypzx.FootballSystem.dbmgr;


import java.util.HashMap;
import java.util.Map;

import club.pypzx.FootballSystem.datasource.ServerDatasource;

/**
 * 项目数据库管理。提供根据项目编码查询数据库名称和IP的接口。
 * 
 * @author Roiocam
 * @date 2018年12月30日 下午4:10:12
 */
public class ProjectDBMgr {
	private static ProjectDBMgr instance = null;

	/**
	 * 保存项目编码与数据名称的映射关系。这里是硬编码，实际开发中这个关系数据可以保存到redis缓存中；
	 * 新增一个项目或者删除一个项目只需要更新缓存。到时这个类的接口只需要修改为从缓存拿数据。
	 */
	private Map<String, String> dbNameMap = new HashMap<String, String>();

	/**
	 * 保存项目编码与数据库IP的映射关系。
	 */
	private Map<String, ServerDatasource> dbIPMap = new HashMap<String, ServerDatasource>();

	private ProjectDBMgr() {
		dbNameMap.put("119.29.35.133:1306", "FootballSystem");
		dbIPMap.put("119.29.35.133:1306", new ServerDatasource(5, 5, "119.29.35.133:1306"));

		dbNameMap.put("127.0.0.1:3306", "FootballSystem");
		dbIPMap.put("127.0.0.1:3306", new ServerDatasource(1, 1, "127.0.0.1:3306"));
	}

	/**
	 * 获取单例对象
	 * 
	 * @return
	 */
	public static ProjectDBMgr instance() {
		if (instance == null) {
			instance = new ProjectDBMgr();
		}
		return instance;
	}

	/**
	 * 获取数据库名称
	 * 
	 * @param projectCode
	 * @return
	 */
	public String getDBName(String projectCode) {
		if (dbNameMap.containsKey(projectCode)) {
			return dbNameMap.get(projectCode);
		}

		return "";
	}

	public boolean containsKey(String projectCode) {
		return dbIPMap.containsKey(projectCode) & dbNameMap.containsKey(projectCode);
	}

	/**
	 * 获取数据库ip
	 * 使用平滑加权轮询算法
	 * @param projectCode
	 * @return
	 */
	public String getDBIP() {
		ServerDatasource maxWeightServer = null;
		//获取总权重
		int allWeight = dbIPMap.values().stream().mapToInt(ServerDatasource::getWeight).sum();
		//遍历服务器，选择最大权重
		for (Map.Entry<String, ServerDatasource> item : dbIPMap.entrySet()) {
			ServerDatasource currentServer = item.getValue();
			//当前权重是否大于最大权重，若大于，则替换
			if (maxWeightServer == null || currentServer.getCurrentWeight() > maxWeightServer.getCurrentWeight()) {
				maxWeightServer = currentServer;
			}
		}
		assert maxWeightServer != null;
		//为当前最大权重设置新的权重值（减去所有权重）
		maxWeightServer.setCurrentWeight(maxWeightServer.getCurrentWeight() - allWeight);
		//遍历服务器，当前服务器的当前权重设置为（当前权重加与固定权重。）
		for (Map.Entry<String, ServerDatasource> item : dbIPMap.entrySet()) {
			ServerDatasource currentServer = item.getValue();
			currentServer.setCurrentWeight(currentServer.getCurrentWeight() + currentServer.getWeight());
		}
		return maxWeightServer.getIp();

	}

}