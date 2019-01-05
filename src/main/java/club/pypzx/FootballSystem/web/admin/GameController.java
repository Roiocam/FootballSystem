package club.pypzx.FootballSystem.web.admin;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import club.pypzx.FootballSystem.dto.RequestEntity;
import club.pypzx.FootballSystem.entity.Cup;
import club.pypzx.FootballSystem.entity.GameVo;
import club.pypzx.FootballSystem.service.GameService;
import club.pypzx.FootballSystem.template.BaseExcution;
import club.pypzx.FootballSystem.utils.ModelMapUtil;
import club.pypzx.FootballSystem.utils.ResultUtil;

@Controller
@ResponseBody
@RequestMapping("admin/service/game")
public class GameController {
	@Autowired
	private GameService service;

	@PostMapping("/getGames")
	public Map<String, Object> getGames(RequestEntity request) {
		Cup cup = request.getCup();
		if (cup == null) {
			return ModelMapUtil.getErrorMap("请检查赛事参数,");
		}
		BaseExcution<GameVo> queryAll = service.findAll(cup.getCupId(), request.getPageIndex(), request.getPageSize());
		if (ResultUtil.failResult(queryAll)) {
			return ModelMapUtil.getDtoMap(queryAll, "查询赛事列表失败");
		}
		return ModelMapUtil.getSuccessMapWithList("查询赛事列表成功", queryAll.getObjList(), queryAll.getCount());

	}

}
