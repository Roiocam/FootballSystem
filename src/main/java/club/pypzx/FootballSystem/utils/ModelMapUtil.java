package club.pypzx.FootballSystem.utils;


import java.util.HashMap;
import java.util.Map;


public class ModelMapUtil {
	public static Map<String, Object> getErrorMap(String errorMessage) {
		Map<String, Object> modelMap = new HashMap<>();
		modelMap.put("success", false);
		modelMap.put("errMsg", errorMessage);
		return modelMap;
	}
	public static Map<String, Object> getPageErrorMap() {
		Map<String, Object> modelMap = new HashMap<>();
		modelMap.put("success", false);
		modelMap.put("errMsg", "请输入分页页码和分页大小");
		return modelMap;
	}

	public static Map<String, Object> getSuccessMap(String successMessage) {
		Map<String, Object> modelMap = new HashMap<>();
		modelMap.put("success", true);
		modelMap.put("message", successMessage);
		return modelMap;
	}

	public static Map<String, Object> getSuccessMapWithDto(String message, Object dto) {
		Map<String, Object> modelMap = new HashMap<>();
		modelMap.put("success", true);
		modelMap.put("message", message);
		modelMap.put("result", dto);
		return modelMap;
	}

}
