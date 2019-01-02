package club.pypzx.FootballSystem.datasource;

import club.pypzx.FootballSystem.dbmgr.ProjectDBMgr;

/**
 * 数据库标识管理类。用于区分数据源连接的不同数据库。
 * 
 * @author Roiocam
 * @date 2018年12月30日 下午1:58:14
 */
public class DBIdentifier {
	/*
	 * 由于我们为不同的project创建了单独的数据库，所以使用项目编码作为数据库的索引。而微服务支持多线程并发的，采用线程变量。
	 */

	/**
	 * 用不同的工程编码来区分数据库
	 */
	private static ThreadLocal<String> dbCode = new ThreadLocal<String>();

	public static String getDbCode() {
		return dbCode.get();
	}

	/**
	 * 设置工程编码，如果工程编码不存在，则返回false
	 * 
	 * @param code
	 * @return
	 */
	public static boolean setDbCode(String code) {
		if (!ProjectDBMgr.instance().containsKey(code)) {
			return false;
		}
		dbCode.set(code);
		return true;
	}
}