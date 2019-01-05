package club.pypzx.FootballSystem.web.admin;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import club.pypzx.FootballSystem.dto.RequestEntity;
import club.pypzx.FootballSystem.entity.Cup;
import club.pypzx.FootballSystem.entity.Game;
import club.pypzx.FootballSystem.service.CupService;
import club.pypzx.FootballSystem.service.GameService;
import club.pypzx.FootballSystem.template.BaseExcution;
import club.pypzx.FootballSystem.utils.ModelMapUtil;
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
	public Map<String, Object> getCups(RequestEntity request) {
		BaseExcution<Cup> queryAll = service.findAll(request.getPageIndex(), request.getPageSize());
		if (ResultUtil.failResult(queryAll)) {
			return ModelMapUtil.getDtoMap(queryAll, "查询赛事列表失败");
		}
		return ModelMapUtil.getSuccessMapWithList("查询赛事列表成功", queryAll.getObjList(), queryAll.getCount());

	}

	@PostMapping("/addCup")
	public Map<String, Object> addCups(RequestEntity request) {
		Cup cup = request.getCup();
		if (cup == null) {
			return ModelMapUtil.getErrorMap("请检查赛事参数,");
		}
		BaseExcution<Cup> insertObj = service.add(service.packageCup(cup.getCupName()));
		if (ResultUtil.failResult(insertObj)) {
			return ModelMapUtil.getErrorMap("新增赛事失败");
		}
		return ModelMapUtil.getSuccessMap("新增成功");
	}

	@PostMapping("/editCup")
	public Map<String, Object> editCup(RequestEntity request) {
		Cup cup = request.getCup();
		if (cup == null) {
			return ModelMapUtil.getErrorMap("请检查赛事参数,");
		}
		try {
			BaseExcution<Cup> updateObjByPrimaryKey = service
					.edit(service.packageCup(cup.getCupId(), cup.getCupName()));
			if (ResultUtil.failResult(updateObjByPrimaryKey)) {
				return ModelMapUtil.getErrorMap("编辑赛事失败");
			}
			return ModelMapUtil.getSuccessMap("编辑成功");
		} catch (Exception e) {
			return ModelMapUtil.getErrorMap("编辑赛事失败:" + e.getMessage());
		}

	}

	@PostMapping("/deleteCup")
	public Map<String, Object> deleteCup(RequestEntity request) {
		Cup cup = request.getCup();
		if (cup == null) {
			return ModelMapUtil.getErrorMap("请检查赛事参数,");
		}
		try {
			BaseExcution<Cup> deleteObjByPrimaryKey = service.removeById(cup.getCupId());
			if (ResultUtil.failResult(deleteObjByPrimaryKey)) {
				return ModelMapUtil.getErrorMap("删除赛事失败:" + deleteObjByPrimaryKey.getStateInfo());
			}
			return ModelMapUtil.getSuccessMap("删除赛事成功");
		} catch (Exception e) {
			return ModelMapUtil.getErrorMap("删除赛事时出现异常，数据库错误." + e.getMessage());
		}

	}

	@PostMapping("/deleteCupList")
	public Map<String, Object> deleteCupList(RequestEntity request) {
		List<String> idList = request.getIdList();
		if (idList == null || idList.size() <= 0) {
			return ModelMapUtil.getErrorMap("删除失败,请选择赛事后删除!");
		}
		try {
			BaseExcution<Cup> deleteObjectList = service.removeByIdList(idList);
			if (ResultUtil.failResult(deleteObjectList)) {
				return ModelMapUtil.getErrorMap("Oops!删除失败，请联系管理员");
			}
			return ModelMapUtil.getSuccessMap("删除成功");
		} catch (Exception e) {
			return ModelMapUtil.getErrorMap("删除失败:" + e.getMessage());
		}

	}

	@PostMapping("/randomTeamToGroup")
	public Map<String, Object> randomTeamToGroup(RequestEntity request) {
		Cup cup = request.getCup();
		if (cup == null) {
			return ModelMapUtil.getErrorMap("请检查赛事参数,");
		}
		try {
			BaseExcution<Cup> randomTeamToGroup = service.randomTeamToGroup(cup.getCupId());
			if (ResultUtil.failResult(randomTeamToGroup)) {
				return ModelMapUtil.getErrorMap("分组失败!" + randomTeamToGroup.getStateInfo());
			}
			return ModelMapUtil.getSuccessMap("分组成功");
		} catch (Exception e) {
			return ModelMapUtil.getErrorMap("分组失败!" + e.getMessage());
		}
	}

	@PostMapping("/randomGameByGroup")
	public Map<String, Object> randomGameByGroup(RequestEntity request) {
		Cup cup = request.getCup();
		if (cup == null) {
			return ModelMapUtil.getErrorMap("请检查赛事参数,");
		}
		try {
			BaseExcution<Game> randomGameByGroup = gameService.randomGameByGroup(cup.getCupId());
			if (ResultUtil.failResult(randomGameByGroup)) {
				return ModelMapUtil.getErrorMap("安排赛程失败!" + randomGameByGroup.getStateInfo());
			}
			return ModelMapUtil.getSuccessMap("安排赛程成功");
		} catch (Exception e) {
			return ModelMapUtil.getErrorMap("安排赛程失败!" + e.getMessage());
		}
	}

	@PostMapping("/removeGroupByCup")
	public Map<String, Object> removeGroupByCup(RequestEntity request) {
		Cup cup = request.getCup();
		if (cup == null) {
			return ModelMapUtil.getErrorMap("请检查赛事参数,");
		}
		// 编写删除赛程逻辑
		try {
			BaseExcution<Game> removeGroupByCup = gameService.removeGroupByCup(cup.getCupId());
			if (ResultUtil.failResult(removeGroupByCup)) {
				return ModelMapUtil.getErrorMap("删除赛程失败!" + removeGroupByCup.getStateInfo());
			}
			return ModelMapUtil.getSuccessMap("删除赛程分组和安排成功");
		} catch (Exception e) {
			return ModelMapUtil.getErrorMap("删除赛程分组和安排失败!" + e.getMessage());
		}
	}
}
