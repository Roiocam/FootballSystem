package club.pypzx.FootballSystem.utils;

import club.pypzx.FootballSystem.dto.BaseExcution;
import club.pypzx.FootballSystem.enums.BaseStateEnum;

public class ResultUtil {
	public static <T> boolean failResult(BaseExcution<T> obj) {
		if (obj == null || BaseStateEnum.SUCCESS.getState() != obj.getState()) {
			return true;
		}
		return false;
	}

	public static <T> boolean failListResult(BaseExcution<T> obj) {
		if (obj == null || BaseStateEnum.SUCCESS.getState() != obj.getState() || obj.getObjList() == null
				|| obj.getObjList().size() < 0) {
			return true;
		}
		return false;
	}
}
