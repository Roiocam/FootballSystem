package club.pypzx.FootballSystem.utils;

/**
 *   唯一时间戳工具类
 * @author Roiocam
 * @date 2018年10月7日 下午7:11:43
 */
public class UniqueTimeStamp {

	private static long lastTime = System.currentTimeMillis();

	private UniqueTimeStamp() {
	}

	/**
	 * 用来产生唯一id
	 * 
	 * @return
	 */
	public synchronized long getUniqueTimeStamp() {
		boolean done = false;
		while (!done) {
			long nowTime = System.currentTimeMillis();
			if (nowTime == lastTime) {
				try {
					Thread.sleep(1);// 可以保证当前得到的时间和上一次的时间不一致
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				continue;
			} else {
				lastTime = nowTime;
				done = true;
			}
		}
		return lastTime;
	}

}
