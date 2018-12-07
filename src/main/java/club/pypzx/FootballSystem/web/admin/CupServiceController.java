package club.pypzx.FootballSystem.web.admin;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import club.pypzx.FootballSystem.dto.BaseExcution;
import club.pypzx.FootballSystem.entity.Cup;
import club.pypzx.FootballSystem.entity.Game;
import club.pypzx.FootballSystem.service.CupService;
import club.pypzx.FootballSystem.service.GameService;
import club.pypzx.FootballSystem.utils.HttpServletRequestUtil;
import club.pypzx.FootballSystem.utils.ModelMapUtil;
import club.pypzx.FootballSystem.utils.ParamUtils;
import club.pypzx.FootballSystem.utils.ResultUtil;

@Controller
@ResponseBody
@RequestMapping("admin/service/cup")
public class CupServiceController {
	@Autowired
	private CupService service;
	@Autowired
	private GameService gameService;

	@PostMapping("/getCups")
	public Map<String, Object> getCups(HttpServletRequest request) {
		int pageIndex = HttpServletRequestUtil.getInt(request, "pageIndex");
		int pageSize = HttpServletRequestUtil.getInt(request, "pageSize");
		BaseExcution<Cup> queryAll = service.queryAll(pageIndex, pageSize);
		if (ResultUtil.failResult(queryAll)) {
			return ModelMapUtil.getDtoMap(queryAll, "查询赛事列表失败");
		}
		return ModelMapUtil.getSuccessMapWithList("查询赛事列表成功", queryAll.getObjList(), queryAll.getCount());

	}

	@PostMapping("/addCup")
	public Map<String, Object> addCups(HttpServletRequest request) {
		String cupName = HttpServletRequestUtil.getString(request, "cupName");
		if (ParamUtils.emptyString(cupName)) {
			return ModelMapUtil.getErrorMap("请检查参数,赛事名称为空");
		}
		BaseExcution<Cup> insertObj = service.insertObj(service.packageCup(cupName));
		if (ResultUtil.failResult(insertObj)) {
			return ModelMapUtil.getErrorMap("新增赛事失败");
		}
		return ModelMapUtil.getSuccessMap("新增成功");
	}

	@PostMapping("/editCup")
	public Map<String, Object> editCup(HttpServletRequest request) {
		String cupId = HttpServletRequestUtil.getString(request, "cupId");
		String cupName = HttpServletRequestUtil.getString(request, "cupName");
		if (ParamUtils.emptyString(cupName) || ParamUtils.emptyString(cupId)) {
			return ModelMapUtil.getErrorMap("请检查参数,赛事名称或编号为空");
		}
		try {
			BaseExcution<Cup> updateObjByPrimaryKey = service.updateObjByPrimaryKey(service.packageCup(cupId, cupName));
			if (ResultUtil.failResult(updateObjByPrimaryKey)) {
				return ModelMapUtil.getErrorMap("编辑赛事失败");
			}
			return ModelMapUtil.getSuccessMap("编辑成功");
		} catch (Exception e) {
			return ModelMapUtil.getErrorMap("编辑赛事失败:" + e.getMessage());
		}

	}

	@PostMapping("/deleteCup")
	public Map<String, Object> deleteCup(HttpServletRequest request) {
		String cupId = HttpServletRequestUtil.getString(request, "cupId");
		if (ParamUtils.emptyString(cupId)) {
			return ModelMapUtil.getErrorMap("请检查参数,赛事编号为空");
		}
		try {
			BaseExcution<Cup> deleteObjByPrimaryKey = service.deleteObjByPrimaryKey(cupId);
			if (ResultUtil.failResult(deleteObjByPrimaryKey)) {
				return ModelMapUtil.getErrorMap("删除赛事失败:" + deleteObjByPrimaryKey.getStateInfo());
			}
			return ModelMapUtil.getSuccessMap("删除赛事成功");
		} catch (Exception e) {
			return ModelMapUtil.getErrorMap("删除赛事时出现异常，数据库错误.");
		}

	}

	@PostMapping("/deleteCupList")
	public Map<String, Object> deleteCupList(@RequestBody List<String> list) {
		if (list == null || list.size() <= 0) {
			return ModelMapUtil.getErrorMap("删除失败,请选择赛事后删除!");
		}
		try {
			BaseExcution<Cup> deleteObjectList = service.deleteObjectList(list);
			if (ResultUtil.failResult(deleteObjectList)) {
				return ModelMapUtil.getErrorMap("Oops!删除失败，请联系管理员");
			}
			return ModelMapUtil.getSuccessMap("删除成功");
		} catch (Exception e) {
			return ModelMapUtil.getErrorMap("删除失败:" + e.getMessage());
		}

	}

	@PostMapping("/randomTeamToGroup")
	public Map<String, Object> randomTeamToGroup(HttpServletRequest request) {
		String cupId = HttpServletRequestUtil.getString(request, "cupId");
		if (ParamUtils.emptyString(cupId)) {
			return ModelMapUtil.getErrorMap("请选择赛事!");
		}
		try {
			BaseExcution<Cup> randomTeamToGroup = service.randomTeamToGroup(cupId);
			if (ResultUtil.failResult(randomTeamToGroup)) {
				return ModelMapUtil.getErrorMap("分组失败!" + randomTeamToGroup.getStateInfo());
			}
			return ModelMapUtil.getSuccessMap("分组成功");
		} catch (Exception e) {
			return ModelMapUtil.getErrorMap("分组失败!" + e.getMessage());
		}
	}

	@PostMapping("/randomGameByGroup")
	public Map<String, Object> randomGameByGroup(HttpServletRequest request) {
		String cupId = HttpServletRequestUtil.getString(request, "cupId");
		if (ParamUtils.emptyString(cupId)) {
			return ModelMapUtil.getErrorMap("请选择赛事!");
		}
		try {
			BaseExcution<Game> randomGameByGroup = gameService.randomGameByGroup(cupId);
			if (ResultUtil.failResult(randomGameByGroup)) {
				return ModelMapUtil.getErrorMap("安排赛程失败!" + randomGameByGroup.getStateInfo());
			}
			return ModelMapUtil.getSuccessMap("安排赛程成功");
		} catch (Exception e) {
			return ModelMapUtil.getErrorMap("安排赛程失败!" + e.getMessage());
		}
	}
	@PostMapping("/removeGroupByCup")
	public Map<String, Object> removeGroupByCup(HttpServletRequest request) {
		String cupId = HttpServletRequestUtil.getString(request, "cupId");
		if (ParamUtils.emptyString(cupId)) {
			return ModelMapUtil.getErrorMap("请选择赛事!");
		}
		//编写删除赛程逻辑
		return ModelMapUtil.getSuccessMap("删除分组及赛程安排成功");
	}
}
