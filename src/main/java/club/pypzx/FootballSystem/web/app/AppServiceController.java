package club.pypzx.FootballSystem.web.app;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import club.pypzx.FootballSystem.dto.RequestEntity;
import club.pypzx.FootballSystem.entity.KickDay;
import club.pypzx.FootballSystem.entity.Player;
import club.pypzx.FootballSystem.entity.PlayerInfo;
import club.pypzx.FootballSystem.entity.PlayerVo;
import club.pypzx.FootballSystem.entity.Team;
import club.pypzx.FootballSystem.entity.TeamVo;
import club.pypzx.FootballSystem.entity.WechatAccount;
import club.pypzx.FootballSystem.service.KickDayService;
import club.pypzx.FootballSystem.service.PlayerService;
import club.pypzx.FootballSystem.service.TeamService;
import club.pypzx.FootballSystem.service.WechatAccountService;
import club.pypzx.FootballSystem.template.BaseExcution;
import club.pypzx.FootballSystem.template.BaseStateEnum;
import club.pypzx.FootballSystem.utils.ModelMapUtil;
import club.pypzx.FootballSystem.utils.ParamUtils;
import club.pypzx.FootballSystem.utils.ResultUtil;

@Controller
@RequestMapping(value = "app/service")
public class AppServiceController {

	@Autowired
	private KickDayService kickServer;
	@Autowired
	private PlayerService playerService;
	@Autowired
	private TeamService teamService;
	@Autowired
	private WechatAccountService wechatService;

	@RequestMapping(value = "plusKick", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> plusKick(HttpServletRequest request) {
		String attribute = (String) request.getSession().getAttribute("kick");
		if (attribute != null && "true".equals(attribute)) {
			return ModelMapUtil.getErrorMap("请勿重复报名");
		}
		BaseExcution<KickDay> plusKick = kickServer.changeKick(true);
		if (plusKick.getState() != BaseStateEnum.SUCCESS.getState()) {
			return ModelMapUtil.getErrorMap("报名失败:" + plusKick.getStateInfo());
		}
		request.getSession().setAttribute("kick", "true");
		return ModelMapUtil.getSuccessMap("报名今日踢球成功, 地点:风雨操场,请准时到达.");
	}

	@RequestMapping(value = "reduceKick", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> reduceKick(HttpServletRequest request) {
		String attribute = (String) request.getSession().getAttribute("kick");
		if (attribute == null || !"true".equals(attribute)) {
			return ModelMapUtil.getErrorMap("你未报名!");
		}
		BaseExcution<KickDay> plusKick = kickServer.changeKick(false);
		if (plusKick.getState() != BaseStateEnum.SUCCESS.getState()) {
			return ModelMapUtil.getErrorMap("取消失败:" + plusKick.getStateInfo());
		}
		request.getSession().setAttribute("kick", "false");
		return ModelMapUtil.getSuccessMap("取消成功");
	}

	@RequestMapping(value = "getKick")
	@ResponseBody
	public Map<String, Object> getKick(HttpServletRequest request) {
		BaseExcution<?> todayKick = kickServer.todayKick();
		if (todayKick.getState() != BaseStateEnum.SUCCESS.getState() && todayKick.getObj() == null) {
			todayKick = kickServer.newDay();
		}
		return ModelMapUtil.getSuccessMapWithObject("查询成功", todayKick);
	}

	@PostMapping("/addPlayer")
	@ResponseBody
	public Map<String, Object> addPlayers(RequestEntity requestEntity, HttpServletRequest request) {
		Team team = requestEntity.getTeam();
		if (team == null || ParamUtils.emptyString(team.getVaildCode()) || ParamUtils.emptyString(team.getTeamId())) {
			return ModelMapUtil.getErrorMap(BaseStateEnum.EMPTY.getStateInfo());
		}
		if (false == playerService.checkValidCode(team.getVaildCode(), team.getTeamId())) {
			return ModelMapUtil.getErrorMap("入队验证码错误");
		}
		Player player = requestEntity.getPlayer();
		PlayerInfo playerInfo = requestEntity.getPlayerInfo();
		if (player == null || playerInfo == null) {
			return ModelMapUtil.getErrorMap("请检查参数,");
		}
		if (ParamUtils.emptyString(player.getTeamId()) || ParamUtils.emptyString(player.getPlayerName())
				|| ParamUtils.emptyString(playerInfo.getPlayerStuno())
				|| ParamUtils.emptyString(playerInfo.getPlayerTel())
				|| ParamUtils.emptyString(playerInfo.getPlayerTel()) || player.getPlayerNum() < 0) {
			return ModelMapUtil.getErrorMap(BaseStateEnum.EMPTY.getStateInfo());
		}
		player.setTeamId(team.getTeamId());
		try {
			BaseExcution<Player> insertObj = playerService.add(player, playerInfo);
			if (insertObj.getState() != BaseStateEnum.SUCCESS.getState()) {
				return ModelMapUtil.getErrorMap("加入球队失败：" + insertObj.getStateInfo());
			}
			String playerId = insertObj.getObj().getPlayerId();
			request.getSession().setAttribute("signed", playerId);
			if (ParamUtils.validString(requestEntity.getOpenid())) {
				WechatAccount wechat = new WechatAccount();
				wechat.setOpenid(requestEntity.getOpenid());
				wechat.setPlayerId(playerId);
				wechatService.add(wechat);
			}
			return ModelMapUtil.getSuccessMap("新增成功");
		} catch (Exception e) {
			return ModelMapUtil.getErrorMap("加入球队错误，重复学号");
		}
	}

	@PostMapping("/createTeam")
	@ResponseBody
	public Map<String, Object> createTeam(HttpServletRequest request, RequestEntity entity) {
		String openid = entity.getOpenid();

		Team team = entity.getTeam();
		if (team == null || ParamUtils.emptyString(team.getCupId()) || ParamUtils.emptyString(team.getVaildCode())
				|| ParamUtils.emptyString(team.getTeamName()) || ParamUtils.emptyString(team.getTeamDesc())) {
			return ModelMapUtil.getErrorMap(BaseStateEnum.EMPTY.getStateInfo());
		}
		Player player = entity.getPlayer();
		PlayerInfo info = entity.getPlayerInfo();
		if (player == null || info == null || ParamUtils.emptyString(player.getPlayerName())
				|| ParamUtils.emptyString(info.getPlayerStuno()) || ParamUtils.emptyString(info.getPlayerDepart())
				|| ParamUtils.emptyString(info.getPlayerTel()) || player.getPlayerNum() < 0) {
			return ModelMapUtil.getErrorMap(BaseStateEnum.EMPTY.getStateInfo());
		}
		try {
			BaseExcution<Team> createTeamAddPlayer = teamService.createTeamAddPlayer(team, player, info);
			String teamId = createTeamAddPlayer.getObj().getTeamId();
			request.getSession().setAttribute("created", teamId);
			if (ParamUtils.validString(openid)) {
				WechatAccount wechat = new WechatAccount();
				wechat.setOpenid(openid);
				wechat.setTeamId(teamId);
				wechatService.add(wechat);
			}
			return ModelMapUtil.getSuccessMap("创建球队成功");
		} catch (Exception e) {
			return ModelMapUtil.getErrorMap("异常" + e.getMessage());
		}
	}

	@PostMapping("/getByOpenid")
	@ResponseBody
	public Map<String, Object> getByOpenid(RequestEntity request) {
		String openid = request.getOpenid();
		BaseExcution<WechatAccount> queryObjOneByPrimaryKey = null;
		if (ParamUtils.emptyString(openid)) {
			return ModelMapUtil.getErrorMap("查询失败，记录不存在[code:a]");
		}
		queryObjOneByPrimaryKey = wechatService.findById(openid);
		if (ResultUtil.failResult(queryObjOneByPrimaryKey)) {
			return ModelMapUtil.getErrorMap("该微信用户下无创建信息");
		}
		String teamId = queryObjOneByPrimaryKey.getObj().getTeamId();
		if (teamId != null && teamId != "") {
			BaseExcution<Team> queryObjOne = teamService.findByCondition(new Team(teamId));
			BaseExcution<PlayerVo> playerDto = playerService.findByIdMore(queryObjOne.getObj().getLeaderId());
			return ModelMapUtil.getSuccessMapWithDuals("查询成功", queryObjOne.getObj(), playerDto.getObj());
		}
		String playerId = queryObjOneByPrimaryKey.getObj().getPlayerId();
		if (playerId != null && playerId != "") {
			BaseExcution<PlayerVo> selectByPrimary = playerService.findByIdMore(playerId);
			return ModelMapUtil.getSuccessMapWithObject("查询成功", selectByPrimary.getObj());
		}
		return ModelMapUtil.getErrorMap("查询失败，记录不存在[code:b]");

	}

	@PostMapping("/getSigned")
	@ResponseBody
	public Map<String, Object> getSigned(HttpServletRequest request) {
		String playerId = (String) request.getSession().getAttribute("signed");

		if (ParamUtils.emptyString(playerId)) {
			return ModelMapUtil.getErrorMap("查询失败，记录不存在[code:a]");
		}
		BaseExcution<PlayerVo> selectByPrimary = playerService.findByIdMore(playerId);
		if (selectByPrimary.getState() != BaseStateEnum.SUCCESS.getState()) {
			return ModelMapUtil.getErrorMap("查询失败，记录不存在[code:b]");
		}
		return ModelMapUtil.getSuccessMapWithObject("查询成功", selectByPrimary.getObj());
	}

	@PostMapping("/getCreated")
	@ResponseBody
	public Map<String, Object> getCreated(HttpServletRequest request) {
		String teamId = (String) request.getSession().getAttribute("created");
		if (ParamUtils.emptyString(teamId)) {
			return ModelMapUtil.getErrorMap("查询失败，记录不存在[code:a]");
		}
		BaseExcution<Team> queryObjOne = teamService.findByCondition(new Team(teamId));
		if (queryObjOne.getState() != BaseStateEnum.SUCCESS.getState()) {
			return ModelMapUtil.getErrorMap("查询失败，记录不存在[code:b]");
		}
		BaseExcution<PlayerVo> playerDto = playerService.findByIdMore(queryObjOne.getObj().getLeaderId());
		if (queryObjOne.getState() != BaseStateEnum.SUCCESS.getState()) {
			return ModelMapUtil.getErrorMap("查询失败，记录不存在[code:c]");
		}
		return ModelMapUtil.getSuccessMapWithDuals("查询成功", queryObjOne.getObj(), playerDto.getObj());

	}

	@PostMapping("/getTeamDetail")
	@ResponseBody
	public Map<String, Object> getTeamDetail(RequestEntity request) {
		String teamId = request.getTeam() == null ? null : request.getTeam().getTeamId();
		if (ParamUtils.emptyString(teamId)) {
			return ModelMapUtil.getErrorMap("该球队信息有误,请联系管理员解决.");
		}
		BaseExcution<TeamVo> queryObjOne = teamService.findByIdMore(teamId);
		if (queryObjOne.getState() != BaseStateEnum.SUCCESS.getState()) {
			return ModelMapUtil.getErrorMap("查询失败，记录不存在[code:b]");
		}
		return ModelMapUtil.getSuccessMapWithObject("查询球队详情成功", queryObjOne.getObj());

	}

	@PostMapping("/checkValidId")
	@ResponseBody
	public Map<String, Object> checkValidId(RequestEntity request) {
		String playerStuno = request.getPlayerInfo() == null ? null : request.getPlayerInfo().getPlayerStuno();
		if (ParamUtils.emptyString(playerStuno)) {
			return ModelMapUtil.getErrorMap("输入有误.");
		}
		BaseExcution<Player> checkValidId = playerService.checkValidId(playerStuno);
		if (checkValidId.getState() != BaseStateEnum.SUCCESS.getState()) {
			return ModelMapUtil.getErrorMap("该学号已报名,请确认学号正确!");
		}
		return ModelMapUtil.getSuccessMap("学号可用");
	}
}
