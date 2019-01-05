package club.pypzx.FootballSystem.web.admin;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import club.pypzx.FootballSystem.dto.RequestEntity;
import club.pypzx.FootballSystem.entity.Player;
import club.pypzx.FootballSystem.entity.PlayerInfo;
import club.pypzx.FootballSystem.entity.PlayerVo;
import club.pypzx.FootballSystem.service.PlayerService;
import club.pypzx.FootballSystem.template.BaseExcution;
import club.pypzx.FootballSystem.template.BaseStateEnum;
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
	public Map<String, Object> getPlayers(RequestEntity request) {
		Player example = request.getPlayer();
		if (ParamUtils.wrongPage(request.getPageIndex(), request.getPageSize())) {
			return ModelMapUtil.getErrorMap(BaseStateEnum.PAGE_ERROR.getStateInfo());
		}
		// 条件查找
		BaseExcution<PlayerVo> queryAllByPage = service.findAllMore(example, request.getPageIndex(),
				request.getPageSize());
		if (ResultUtil.failResult(queryAllByPage)) {
			return ModelMapUtil.getDtoMap(queryAllByPage, "查询球员列表失败");
		}
		return ModelMapUtil.getSuccessMapWithList("查询球员列表成功", queryAllByPage.getObjList(), queryAllByPage.getCount());

	}

	@PostMapping("/addPlayer")
	public Map<String, Object> addPlayers(RequestEntity request) {
		Player player = request.getPlayer();
		PlayerInfo playerInfo = request.getPlayerInfo();
		if (player == null || playerInfo == null) {
			return ModelMapUtil.getErrorMap("请检查参数,");
		}
		if (ParamUtils.emptyString(player.getTeamId()) || ParamUtils.emptyString(player.getPlayerName())
				|| ParamUtils.emptyString(playerInfo.getPlayerStuno())
				|| ParamUtils.emptyString(playerInfo.getPlayerTel())
				|| ParamUtils.emptyString(playerInfo.getPlayerTel()) || player.getPlayerNum() < 0) {
			return ModelMapUtil.getErrorMap(BaseStateEnum.EMPTY.getStateInfo());
		}
		try {
			BaseExcution<Player> insertObj = service.add(player, playerInfo);
			if (ResultUtil.failResult(insertObj)) {
				return ModelMapUtil.getErrorMap("加入球队失败:" + insertObj.getStateInfo());
			}
			return ModelMapUtil.getSuccessMap("新增成功");
		} catch (Exception e) {
			return ModelMapUtil.getErrorMap(e.getMessage());
		}
	}

	@PostMapping("/editPlayer")
	public Map<String, Object> editPlayer(RequestEntity request) {
		Player player = request.getPlayer();
		PlayerInfo playerInfo = request.getPlayerInfo();
		if (player == null || playerInfo == null) {
			return ModelMapUtil.getErrorMap("请检查参数,");
		}
		try {
			BaseExcution<Player> updateObj = service.edit(player, playerInfo);
			if (ResultUtil.failResult(updateObj)) {
				return ModelMapUtil.getErrorMap("编辑球员失败:" + updateObj.getStateInfo());
			}
			return ModelMapUtil.getSuccessMap("编辑成功");
		} catch (Exception e) {
			return ModelMapUtil.getErrorMap("编辑球员异常:" + e.getMessage());
		}
	}

	@PostMapping("/deletePlayer")
	public Map<String, Object> deletePlayer(RequestEntity request) {
		Player player = request.getPlayer();
		if (player == null) {
			return ModelMapUtil.getErrorMap("请检查参数,");
		}
		try {
			BaseExcution<Player> deleteObjByPrimaryKey = service.removeById(player.getPlayerId());
			if (ResultUtil.failResult(deleteObjByPrimaryKey)) {
				return ModelMapUtil.getErrorMap("删除球员失败");
			}
			return ModelMapUtil.getSuccessMap("删除球员成功");
		} catch (Exception e) {
			return ModelMapUtil.getErrorMap("删除球员失败:" + e.getMessage());
		}

	}

	@PostMapping("/deletePlayerList")
	public Map<String, Object> deletePlayerList(RequestEntity request) {
		List<String> idList = request.getIdList();
		if (idList == null || idList.size() <= 0) {
			return ModelMapUtil.getErrorMap("删除失败,请选择球员后删除!");
		}
		try {
			BaseExcution<Player> deleteObjectList = service.removeByIdList(idList);
			if (ResultUtil.failResult(deleteObjectList)) {
				return ModelMapUtil.getErrorMap("Oops!删除失败，请联系管理员");
			}
			return ModelMapUtil.getSuccessMap("删除成功");
		} catch (Exception e) {
			return ModelMapUtil.getErrorMap("删除失败:" + e.getMessage());
		}

	}
}
