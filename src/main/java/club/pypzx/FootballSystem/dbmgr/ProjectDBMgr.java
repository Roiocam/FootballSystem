package club.pypzx.FootballSystem.dbmgr;

import java.util.HashMap;
import java.util.Map;

/**
 * 项目数据库管理。提供根据项目编码查询数据库名称和IP的接口。
 * 
 * @author Roiocam
 * @date 2018年12月30日 下午4:10:12
 */
public class ProjectDBMgr {

	/**
	 * 保存项目编码与数据名称的映射关系。这里是硬编码，实际开发中这个关系数据可以保存到redis缓存中；
	 * 新增一个项目或者删除一个项目只需要更新缓存。到时这个类的接口只需要修改为从缓存拿数据。
	 */
	private Map<String, String> dbNameMap = new HashMap<String, String>();

	/**
	 * 保存项目编码与数据库IP的映射关系。
	 */
	private Map<String, String> dbIPMap = new HashMap<String, String>();

	private ProjectDBMgr() {
		dbNameMap.put("某个ProjectCode[项目编码]", "输入你的数据库名");
		dbIPMap.put("某个ProjectCode[项目编码]", "输入你的数据库ip+端口");

	}

	/**
	 * 获取单例对象
	 * 
	 * @return
	 */
	public static ProjectDBMgr instance() {
		return ProjectDBMgrBuilder.instance;
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
	 * 
	 * @param projectCode
	 * @return
	 */
	public String getDBIP(String projectCode) {
		if (dbIPMap.containsKey(projectCode)) {
			return dbIPMap.get(projectCode);
		}

		return "";
	}

	/**
	 * 单例对象构建类
	 * 
	 * @author Roiocam
	 * @date 2018年12月30日 下午4:13:07
	 */
	private static class ProjectDBMgrBuilder {
		private static ProjectDBMgr instance = new ProjectDBMgr();
	}
}