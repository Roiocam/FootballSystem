package club.pypzx.FootballSystem.utils;

import club.pypzx.FootballSystem.template.BaseExcution;
import club.pypzx.FootballSystem.template.BaseStateEnum;

/**
 * 统一结果类的校验工具
 * 
 * @author Roiocam
 * @date 2019年1月5日 下午3:20:46
 */
public class ResultUtil {
	/**
	 * 错误的单结果
	 * 
	 * @param obj
	 * @return
	 */
	public static <T> boolean failResult(BaseExcution<T> obj) {
		if (obj == null || BaseStateEnum.SUCCESS.getState() != obj.getState()) {
			return true;
		}
		return false;
	}

	/**
	 * 错误的集合结果
	 * 
	 * @param obj
	 * @return
	 */
	public static <T> boolean failListResult(BaseExcution<T> obj) {
		if (obj == null || BaseStateEnum.SUCCESS.getState() != obj.getState() || obj.getObjList() == null
				|| obj.getObjList().size() < 0) {
			return true;
		}
		return false;
	}
}
