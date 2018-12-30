package club.pypzx.FootballSystem.datasource;

import java.util.TimerTask;

/**
 * 清除空闲连接的任务
 * 
 * @author Roiocam
 * @date 2018年12月30日 下午4:09:43
 */
public class ClearIdleTimerTask extends TimerTask {

	@Override
	public void run() {
		DDSHolder.instance().clearIdleDDS();
	}
}