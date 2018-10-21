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
import club.pypzx.FootballSystem.dto.GroupVo;
import club.pypzx.FootballSystem.dto.TeamVo;
import club.pypzx.FootballSystem.entity.Team;
import club.pypzx.FootballSystem.enums.BaseStateEnum;
import club.pypzx.FootballSystem.service.TeamService;
import club.pypzx.FootballSystem.utils.HttpServletRequestUtil;
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
	public Map<String, Object> getTeams(HttpServletRequest request) {
		int pageIndex = HttpServletRequestUtil.getInt(request, "pageIndex");
		int pageSize = HttpServletRequestUtil.getInt(request, "pageSize");
		if (ParamUtils.wrongPage(pageIndex, pageSize)) {
			return ModelMapUtil.getErrorMap(BaseStateEnum.PAGE_ERROR.getStateInfo());
		}
		BaseExcution<TeamVo> queryAllByPage = service.queryAllByPage(null, pageIndex, pageSize);
		if (ResultUtil.failResult(queryAllByPage)) {
			return ModelMapUtil.getDtoMap(queryAllByPage, "查询球队列表失败");
		}
		return ModelMapUtil.getSuccessMapWithList("查询球队列表成功", queryAllByPage.getObjList(),queryAllByPage.getCount());

	}

	@PostMapping("/addTeam")
	public Map<String, Object> addTeams(HttpServletRequest request) {
		String teamName = HttpServletRequestUtil.getString(request, "teamName");
		String cupId = HttpServletRequestUtil.getString(request, "cupId");
		String vaildCode = HttpServletRequestUtil.getString(request, "vaildCode");
		String teamDesc = HttpServletRequestUtil.getString(request, "teamDesc");
		if (ParamUtils.emptyString(teamName) || ParamUtils.emptyString(cupId) || ParamUtils.emptyString(vaildCode)
				|| ParamUtils.emptyString(teamDesc)) {
			return ModelMapUtil.getErrorMap(BaseStateEnum.EMPTY.getStateInfo());
		}
		try {
			BaseExcution<Team> insertObj = service.insertObj(service.packageTeam(cupId, teamName, vaildCode, teamDesc));
			if (ResultUtil.failResult(insertObj)) {
				return ModelMapUtil.getErrorMap("新增球队失败:"+insertObj.getStateInfo());
			}
			return ModelMapUtil.getSuccessMap("新增成功");
		} catch (Exception e) {
			return ModelMapUtil.getErrorMap("新增球队异常:" + e.getMessage());
		}
	}

	@PostMapping("/editTeam")
	public Map<String, Object> editTeam(HttpServletRequest request) {
		String teamId = HttpServletRequestUtil.getString(request, "teamId");
		String cupId = HttpServletRequestUtil.getString(request, "cupId");
		String teamName = HttpServletRequestUtil.getString(request, "teamName");
		String leaderId = HttpServletRequestUtil.getString(request, "leaderId");
		String vaildCode = HttpServletRequestUtil.getString(request, "vaildCode");
		String teamDesc = HttpServletRequestUtil.getString(request, "teamDesc");
		if (ParamUtils.emptyString(teamId)) {
			return ModelMapUtil.getErrorMap(BaseStateEnum.EMPTY.getStateInfo());
		}
		try {
			BaseExcution<Team> updateObj = service
					.updateObjByPrimaryKey(service.packageTeam(teamId, cupId, teamName, leaderId, vaildCode, teamDesc));
			if (ResultUtil.failResult(updateObj)) {
				return ModelMapUtil.getErrorMap("编辑球队失败");
			}
			return ModelMapUtil.getSuccessMap("编辑成功");
		} catch (Exception e) {
			return ModelMapUtil.getErrorMap("编辑球队异常:" + e.getMessage());
		}
	}

	@PostMapping("/deleteTeam")
	public Map<String, Object> deleteTeam(HttpServletRequest request) {
		String teamId = HttpServletRequestUtil.getString(request, "teamId");
		if (ParamUtils.emptyString(teamId)) {
			return ModelMapUtil.getErrorMap("删除失败，编号错误");
		}
		try {
			BaseExcution<Team> deleteObjByPrimaryKey = service.deleteObjByPrimaryKey(teamId);
			if (ResultUtil.failResult(deleteObjByPrimaryKey)) {
				return ModelMapUtil.getErrorMap("删除球队失败:"+deleteObjByPrimaryKey.getStateInfo());
			}
			return ModelMapUtil.getSuccessMap("删除球队成功");
		} catch (Exception e) {
			return ModelMapUtil.getErrorMap("删除球队失败:" + e.getMessage());
		}

	}

	@PostMapping("/deleteTeamList")
	public Map<String, Object> deleteTeamList(@RequestBody List<String> list) {
		if (list == null || list.size() <= 0) {
			return ModelMapUtil.getErrorMap("删除失败,请选择球队后删除!");
		}
		try {
			BaseExcution<Team> deleteObjectList = service.deleteObjectList(list);
			if (ResultUtil.failResult(deleteObjectList)) {
				return ModelMapUtil.getErrorMap("Oops!删除失败，请联系管理员");
			}
			return ModelMapUtil.getSuccessMap("删除成功");
		} catch (Exception e) {
			return ModelMapUtil.getErrorMap("删除失败:" + e.getMessage());
		}
	}

	@PostMapping("/updateTeamLeader")
	public Map<String, Object> updateTeamLeader(HttpServletRequest request) {
		String teamId = HttpServletRequestUtil.getString(request, "teamId");
		String leaderId = HttpServletRequestUtil.getString(request, "leaderId");
		if (ParamUtils.emptyString(teamId) || ParamUtils.emptyString(leaderId)) {
			return ModelMapUtil.getErrorMap(BaseStateEnum.EMPTY.getStateInfo());
		}
		BaseExcution<Team> updateTeamLeader = service.updateTeamLeader(teamId, leaderId);
		if (ResultUtil.failResult(updateTeamLeader)) {
			return ModelMapUtil.getErrorMap("指定球队队长失败");
		}
		return ModelMapUtil.getSuccessMap("指定球队队长成功");
	}

	@PostMapping("/getTeamGroup")
	public Map<String, Object> getTeamGroup(HttpServletRequest request) {
		String cupId = HttpServletRequestUtil.getString(request, "cupId");
		if (ParamUtils.emptyString(cupId)) {
			return ModelMapUtil.getErrorMap("请选择赛事");
		}
		BaseExcution<GroupVo> queryTeamByGroup = service.queryTeamByGroup(cupId);
		if (ResultUtil.failResult(queryTeamByGroup)) {
			return ModelMapUtil.getDtoMap(queryTeamByGroup, "查询球队分组失败,该赛事并未分组！");
		}
		return ModelMapUtil.getSuccessMapWithObject("查询球队列表成功", queryTeamByGroup.getObjList());

	}
}
