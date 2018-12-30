package club.pypzx.FootballSystem.utils;

import java.util.HashMap;
import java.util.Map;

import club.pypzx.FootballSystem.template.BaseExcution;
import club.pypzx.FootballSystem.template.BaseStateEnum;

public class ModelMapUtil {
	public static Map<String, Object> getErrorMap(String message) {
		Map<String, Object> modelMap = new HashMap<>();
		modelMap.put("state", BaseStateEnum.FAIL.getState());
		modelMap.put("stateInfo", BaseStateEnum.FAIL.getStateInfo());
		modelMap.put("message", message);
		return modelMap;
	}

	public static Map<String, Object> getPageErrorMap() {
		Map<String, Object> modelMap = new HashMap<>();
		modelMap.put("state", BaseStateEnum.PAGE_ERROR.getState());
		modelMap.put("stateInfo", BaseStateEnum.PAGE_ERROR.getStateInfo());
		modelMap.put("message", "请输入分页页码和分页大小");
		return modelMap;
	}

	public static Map<String, Object> getSuccessMap(String successMessage) {
		Map<String, Object> modelMap = new HashMap<>();
		modelMap.put("state", BaseStateEnum.SUCCESS.getState());
		modelMap.put("stateInfo", BaseStateEnum.SUCCESS.getStateInfo());
		modelMap.put("message", successMessage);
		return modelMap;
	}

	public static <T> Map<String, Object> getSuccessMapWithObject(String message, T dto) {
		Map<String, Object> modelMap = new HashMap<>();
		modelMap.put("state", BaseStateEnum.SUCCESS.getState());
		modelMap.put("stateInfo", BaseStateEnum.SUCCESS.getStateInfo());
		modelMap.put("message", message);
		modelMap.put("result", dto);
		return modelMap;
	}
	public static <T> Map<String, Object> getSuccessMapWithList(String message, T dto,int count) {
		Map<String, Object> modelMap = new HashMap<>();
		modelMap.put("state", BaseStateEnum.SUCCESS.getState());
		modelMap.put("stateInfo", BaseStateEnum.SUCCESS.getStateInfo());
		modelMap.put("message", message);
		modelMap.put("result", dto);
		modelMap.put("count", count);
		return modelMap;
	}

	public static Map<String, Object> getDtoMap(BaseExcution<?> result, String message) {
		Map<String, Object> modelMap = new HashMap<>();
		modelMap.put("state", result.getState());
		modelMap.put("stateInfo", result.getStateInfo());
		modelMap.put("message", message);
		return modelMap;
	}
	
	public static <T> Map<String, Object> getSuccessMapWithDuals(String message, T dtoOne,T dtoTwo) {
		Map<String, Object> modelMap = new HashMap<>();
		modelMap.put("state", BaseStateEnum.SUCCESS.getState());
		modelMap.put("stateInfo", BaseStateEnum.SUCCESS.getStateInfo());
		modelMap.put("message", message);
		modelMap.put("resultOne", dtoOne);
		modelMap.put("resultTwo", dtoTwo);
		return modelMap;
	}

}
