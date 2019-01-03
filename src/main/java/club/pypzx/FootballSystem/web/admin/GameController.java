package club.pypzx.FootballSystem.web.admin;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import club.pypzx.FootballSystem.entity.GameVo;
import club.pypzx.FootballSystem.service.GameService;
import club.pypzx.FootballSystem.template.BaseExcution;
import club.pypzx.FootballSystem.utils.HttpServletRequestUtil;
import club.pypzx.FootballSystem.utils.ModelMapUtil;
import club.pypzx.FootballSystem.utils.ResultUtil;

@Controller
@ResponseBody
@RequestMapping("admin/service/game")
public class GameController {
	@Autowired
	private GameService service;

	@PostMapping("/getGames")
	public Map<String, Object> getGames(HttpServletRequest request) {
		int pageIndex = HttpServletRequestUtil.getInt(request, "pageIndex");
		int pageSize = HttpServletRequestUtil.getInt(request, "pageSize");
		String cupId = HttpServletRequestUtil.getString(request, "cupId");
		BaseExcution<GameVo> queryAll = service.findAll(cupId, pageIndex, pageSize);
		if (ResultUtil.failResult(queryAll)) {
			return ModelMapUtil.getDtoMap(queryAll, "查询赛事列表失败");
		}
		return ModelMapUtil.getSuccessMapWithList("查询赛事列表成功", queryAll.getObjList(), queryAll.getCount());

	}

//	@PostMapping("/editGame")
//	public Map<String, Object> editGame(HttpServletRequest request) {
//		String cupId = HttpServletRequestUtil.getString(request, "cupId");
//		String cupName = HttpServletRequestUtil.getString(request, "cupName");
//		if (ParamUtils.emptyString(cupName) || ParamUtils.emptyString(cupId)) {
//			return ModelMapUtil.getErrorMap("请检查参数,赛事名称或编号为空");
//		}
//		try {
//			BaseExcution<Game> updateObjByPrimaryKey = service
//					.updateObjByPrimaryKey(service.packageGame(cupId, cupName));
//			if (ResultUtil.failResult(updateObjByPrimaryKey)) {
//				return ModelMapUtil.getErrorMap("编辑赛事失败");
//			}
//			return ModelMapUtil.getSuccessMap("编辑成功");
//		} catch (Exception e) {
//			return ModelMapUtil.getErrorMap("编辑赛事失败:" + e.getMessage());
//		}
//
//	}

}
