package club.pypzx.FootballSystem.web.admin;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import club.pypzx.FootballSystem.dto.RequestEntity;
import club.pypzx.FootballSystem.dto.TeamPrint;
import club.pypzx.FootballSystem.entity.GroupVo;
import club.pypzx.FootballSystem.entity.Team;
import club.pypzx.FootballSystem.entity.TeamVo;
import club.pypzx.FootballSystem.service.TeamService;
import club.pypzx.FootballSystem.template.BaseExcution;
import club.pypzx.FootballSystem.template.BaseStateEnum;
import club.pypzx.FootballSystem.utils.ModelMapUtil;
import club.pypzx.FootballSystem.utils.ParamUtils;
import club.pypzx.FootballSystem.utils.ResultUtil;

@Controller
@ResponseBody
@RequestMapping("admin/service/team")
public class TeamServiceController {
	@Autowired
	private TeamService service;

	@PostMapping("/getTeams")
	public Map<String, Object> getTeams(RequestEntity request) {
		Team condition = new Team();
		if (request.getCup() != null && ParamUtils.validString(request.getCup().getCupId())) {
			condition.setCupId(request.getCup().getCupId());
		}
		if (ParamUtils.wrongPage(request.getPageIndex(), request.getPageSize())) {
			return ModelMapUtil.getErrorMap(BaseStateEnum.PAGE_ERROR.getStateInfo());
		}
		BaseExcution<TeamVo> queryAllByPage = service.findAllMore(condition, request.getPageIndex(),
				request.getPageSize());
		if (ResultUtil.failResult(queryAllByPage)) {
			return ModelMapUtil.getDtoMap(queryAllByPage, "查询球队列表失败");
		}
		return ModelMapUtil.getSuccessMapWithList("查询球队列表成功", queryAllByPage.getObjList(), queryAllByPage.getCount());

	}

	@PostMapping("/addTeam")
	public Map<String, Object> addTeams(RequestEntity request) {
		Team team = request.getTeam();
		if (team == null || ParamUtils.emptyString(team.getTeamName()) || ParamUtils.emptyString(team.getCupId())
				|| ParamUtils.emptyString(team.getVaildCode()) || ParamUtils.emptyString(team.getTeamDesc())) {
			return ModelMapUtil.getErrorMap(BaseStateEnum.EMPTY.getStateInfo());
		}
		try {
			BaseExcution<Team> insertObj = service.add(team);
			if (ResultUtil.failResult(insertObj)) {
				return ModelMapUtil.getErrorMap("新增球队失败:" + insertObj.getStateInfo());
			}
			return ModelMapUtil.getSuccessMap("新增成功");
		} catch (Exception e) {
			return ModelMapUtil.getErrorMap("新增球队异常:" + e.getMessage());
		}
	}

	@PostMapping("/editTeam")
	public Map<String, Object> editTeam(RequestEntity request) {
		Team team = request.getTeam();
		if (team == null || ParamUtils.emptyString(team.getTeamName()) || ParamUtils.emptyString(team.getCupId())
				|| ParamUtils.emptyString(team.getVaildCode()) || ParamUtils.emptyString(team.getTeamDesc())) {
			return ModelMapUtil.getErrorMap(BaseStateEnum.EMPTY.getStateInfo());
		}
		try {
			BaseExcution<Team> updateObj = service.edit(team);
			if (ResultUtil.failResult(updateObj)) {
				return ModelMapUtil.getErrorMap("编辑球队失败");
			}
			return ModelMapUtil.getSuccessMap("编辑成功");
		} catch (Exception e) {
			return ModelMapUtil.getErrorMap("编辑球队异常:" + e.getMessage());
		}
	}

	@PostMapping("/deleteTeam")
	public Map<String, Object> deleteTeam(RequestEntity request) {
		Team team = request.getTeam();
		if (team == null || ParamUtils.emptyString(team.getTeamId())) {
			return ModelMapUtil.getErrorMap("删除失败，编号错误");
		}
		try {
			BaseExcution<Team> deleteObjByPrimaryKey = service.removeById(team.getTeamId());
			if (ResultUtil.failResult(deleteObjByPrimaryKey)) {
				return ModelMapUtil.getErrorMap("删除球队失败:" + deleteObjByPrimaryKey.getStateInfo());
			}
			return ModelMapUtil.getSuccessMap("删除球队成功");
		} catch (Exception e) {
			return ModelMapUtil.getErrorMap("删除球队失败:" + e.getMessage());
		}

	}

	@PostMapping("/deleteTeamList")
	public Map<String, Object> deleteTeamList(RequestEntity request) {
		List<String> idList = request.getIdList();
		if (idList == null || idList.size() <= 0) {
			return ModelMapUtil.getErrorMap("删除失败,请选择球队后删除!");
		}
		try {
			BaseExcution<Team> deleteObjectList = service.removeByIdList(idList);
			if (ResultUtil.failResult(deleteObjectList)) {
				return ModelMapUtil.getErrorMap("Oops!删除失败，请联系管理员");
			}
			return ModelMapUtil.getSuccessMap("删除成功");
		} catch (Exception e) {
			return ModelMapUtil.getErrorMap("删除失败:" + e.getMessage());
		}
	}

	@PostMapping("/updateTeamLeader")
	public Map<String, Object> updateTeamLeader(RequestEntity request) {
		Team team = request.getTeam();
		if (team == null || ParamUtils.emptyString(team.getTeamId()) || ParamUtils.emptyString(team.getLeaderId())) {
			return ModelMapUtil.getErrorMap(BaseStateEnum.EMPTY.getStateInfo());
		}
		BaseExcution<Team> updateTeamLeader = service.editTeamLeader(team.getTeamId(), team.getLeaderId());
		if (ResultUtil.failResult(updateTeamLeader)) {
			return ModelMapUtil.getErrorMap("指定球队队长失败");
		}
		return ModelMapUtil.getSuccessMap("指定球队队长成功");
	}

	@PostMapping("/getTeamGroup")
	public Map<String, Object> getTeamGroup(RequestEntity request) {
		Team team = request.getTeam();
		if (team == null || ParamUtils.emptyString(team.getCupId())) {
			return ModelMapUtil.getErrorMap("请选择赛事");
		}
		BaseExcution<GroupVo> queryTeamByGroup = service.findTeamByGroup(team.getCupId());
		if (ResultUtil.failResult(queryTeamByGroup)) {
			return ModelMapUtil.getDtoMap(queryTeamByGroup, "查询球队分组失败,该赛事并未分组！");
		}
		return ModelMapUtil.getSuccessMapWithObject("查询球队列表成功", queryTeamByGroup.getObjList());

	}

	@PostMapping("/getTeamById")
	public Map<String, Object> getTeamById(RequestEntity request) {
		Team team = request.getTeam();
		if (team == null || ParamUtils.emptyString(team.getTeamId())) {
			return ModelMapUtil.getErrorMap("请选择球队");
		}
		BaseExcution<TeamPrint> teamPrint = service.getTeamPrint(team.getTeamId());
		if (ResultUtil.failResult(teamPrint)) {
			return ModelMapUtil.getDtoMap(teamPrint, "查询球队详情失败！");
		}
		return ModelMapUtil.getSuccessMapWithObject("查询球队详情成功", teamPrint.getObj());

	}

}
