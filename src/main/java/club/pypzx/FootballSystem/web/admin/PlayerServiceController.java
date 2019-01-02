package club.pypzx.FootballSystem.web.admin;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;

import club.pypzx.FootballSystem.dto.PlayerVo;
import club.pypzx.FootballSystem.entity.Player;
import club.pypzx.FootballSystem.service.PlayerService;
import club.pypzx.FootballSystem.template.BaseExcution;
import club.pypzx.FootballSystem.template.BaseStateEnum;
import club.pypzx.FootballSystem.utils.HttpServletRequestUtil;
import club.pypzx.FootballSystem.utils.ModelMapUtil;
import club.pypzx.FootballSystem.utils.ParamUtils;
import club.pypzx.FootballSystem.utils.ResultUtil;

@Controller
@ResponseBody
@RequestMapping("admin/service/player")
public class PlayerServiceController {
	@Autowired
	private PlayerService service;

	@PostMapping("/getPlayers")
	public Map<String, Object> getPlayers(HttpServletRequest request) {
		int pageIndex = HttpServletRequestUtil.getInt(request, "pageIndex");
		int pageSize = HttpServletRequestUtil.getInt(request, "pageSize");
		Player example = null;
		String playerName = HttpServletRequestUtil.getString(request, "playerName");
		String teamId = HttpServletRequestUtil.getString(request, "teamId");
		// 参数验证
		if (ParamUtils.validString(playerName) || ParamUtils.validString(teamId)) {
			example = new Player();
			example.setPlayerName(playerName);
			example.setTeamId(teamId);
		}
		if (ParamUtils.wrongPage(pageIndex, pageSize)) {
			return ModelMapUtil.getErrorMap(BaseStateEnum.PAGE_ERROR.getStateInfo());
		}
		// 条件查找
		BaseExcution<PlayerVo> queryAllByPage = service.findAllMore(example, pageIndex, pageSize);
		if (ResultUtil.failResult(queryAllByPage)) {
			return ModelMapUtil.getDtoMap(queryAllByPage, "查询球员列表失败");
		}
		return ModelMapUtil.getSuccessMapWithList("查询球员列表成功", queryAllByPage.getObjList(), queryAllByPage.getCount());

	}

	@PostMapping("/addPlayer")
	public Map<String, Object> addPlayers(HttpServletRequest request) {
		String teamId = HttpServletRequestUtil.getString(request, "teamId");
		String playerName = HttpServletRequestUtil.getString(request, "playerName");
		int playerNum = HttpServletRequestUtil.getInt(request, "playerNum");
		String stuno = HttpServletRequestUtil.getString(request, "playerStuno");
		String depart = HttpServletRequestUtil.getString(request, "playerDepart");
		String tel = HttpServletRequestUtil.getString(request, "playerTel");
		if (ParamUtils.emptyString(teamId) || ParamUtils.emptyString(playerName) || ParamUtils.emptyString(stuno)
				|| ParamUtils.emptyString(depart) || ParamUtils.emptyString(tel) || playerNum < 0) {
			return ModelMapUtil.getErrorMap(BaseStateEnum.EMPTY.getStateInfo());
		}
		try {
			BaseExcution<Player> insertObj = service.add(teamId, playerName, playerNum, stuno, depart, tel);
			if (ResultUtil.failResult(insertObj)) {
				return ModelMapUtil.getErrorMap("加入球队失败:" + insertObj.getStateInfo());
			}
			return ModelMapUtil.getSuccessMap("新增成功");
		} catch (Exception e) {
			return ModelMapUtil.getErrorMap(e.getMessage());
		}
	}

	@PostMapping("/editPlayer")
	public Map<String, Object> editPlayer(HttpServletRequest request) {
		String playerId = HttpServletRequestUtil.getString(request, "playerId");
		String teamId = HttpServletRequestUtil.getString(request, "teamId");
		String playerName = HttpServletRequestUtil.getString(request, "playerName");
		int playerNum = HttpServletRequestUtil.getInt(request, "playerNum");
		String stuno = HttpServletRequestUtil.getString(request, "playerStuno");
		String depart = HttpServletRequestUtil.getString(request, "playerDepart");
		String tel = HttpServletRequestUtil.getString(request, "playerTel");
		if (ParamUtils.emptyString(playerId)) {
			return ModelMapUtil.getErrorMap(BaseStateEnum.EMPTY.getStateInfo());
		}
		try {
			BaseExcution<Player> updateObj = service.edit(playerId, teamId, playerName, playerNum, stuno,
					depart, tel);
			if (ResultUtil.failResult(updateObj)) {
				return ModelMapUtil.getErrorMap("编辑球员失败:" + updateObj.getStateInfo());
			}
			return ModelMapUtil.getSuccessMap("编辑成功");
		} catch (Exception e) {
			return ModelMapUtil.getErrorMap("编辑球员异常:" + e.getMessage());
		}
	}

	@PostMapping("/deletePlayer")
	public Map<String, Object> deletePlayer(HttpServletRequest request) {
		String playerId = HttpServletRequestUtil.getString(request, "playerId");
		if (ParamUtils.emptyString(playerId)) {
			return ModelMapUtil.getErrorMap(BaseStateEnum.EMPTY.getStateInfo());
		}
		try {
			BaseExcution<Player> deleteObjByPrimaryKey = service.removeById(playerId);
			if (ResultUtil.failResult(deleteObjByPrimaryKey)) {
				return ModelMapUtil.getErrorMap("删除球员失败");
			}
			return ModelMapUtil.getSuccessMap("删除球员成功");
		} catch (Exception e) {
			return ModelMapUtil.getErrorMap("删除球员失败:" + e.getMessage());
		}

	}

	@PostMapping("/deletePlayerList")
	public Map<String, Object> deletePlayerList(HttpServletRequest request) {
		String str = HttpServletRequestUtil.getString(request, "list");
		List<String> list = (List<String>) JSON.parseArray(str, String.class);
		if (list == null || list.size() <= 0) {
			return ModelMapUtil.getErrorMap("删除失败,请选择球员后删除!");
		}
		try {
			BaseExcution<Player> deleteObjectList = service.removeByIdList(list);
			if (ResultUtil.failResult(deleteObjectList)) {
				return ModelMapUtil.getErrorMap("Oops!删除失败，请联系管理员");
			}
			return ModelMapUtil.getSuccessMap("删除成功");
		} catch (Exception e) {
			return ModelMapUtil.getErrorMap("删除失败:" + e.getMessage());
		}

	}
}
