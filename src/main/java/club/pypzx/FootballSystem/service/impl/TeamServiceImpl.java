package club.pypzx.FootballSystem.service.impl;

import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import club.pypzx.FootballSystem.dao.TeamDao;
import club.pypzx.FootballSystem.dao.TeamRankDao;
import club.pypzx.FootballSystem.dbmgr.EntityFactroy;
import club.pypzx.FootballSystem.dto.TeamPrint;
import club.pypzx.FootballSystem.entity.Group;
import club.pypzx.FootballSystem.entity.GroupVo;
import club.pypzx.FootballSystem.entity.Player;
import club.pypzx.FootballSystem.entity.PlayerInfo;
import club.pypzx.FootballSystem.entity.PlayerVo;
import club.pypzx.FootballSystem.entity.Team;
import club.pypzx.FootballSystem.entity.TeamVo;
import club.pypzx.FootballSystem.service.GroupService;
import club.pypzx.FootballSystem.service.PlayerService;
import club.pypzx.FootballSystem.service.TeamService;
import club.pypzx.FootballSystem.template.BaseExcution;
import club.pypzx.FootballSystem.template.BaseStateEnum;
import club.pypzx.FootballSystem.utils.IDUtils;
import club.pypzx.FootballSystem.utils.ResultUtil;

@Service
public class TeamServiceImpl implements TeamService {

	@Autowired
	private TeamDao dao;
	@Autowired
	private PlayerService playerService;
	@Autowired
	private TeamRankDao rankDao;
	@Autowired
	private GroupService groupService;

	public Team packageTeam(String cupId, String name, String code, String desc) {
		Team obj = EntityFactroy.getBean(Team.class);
		obj.setCupId(cupId);
		obj.setTeamId(IDUtils.getUUID());
		obj.setTeamName(name);
		obj.setVaildCode(code);
		obj.setTeamDesc(desc);
		return obj;
	}

	public Team packageTeam(String id, String cupId, String name, String leaderId, String code, String desc) {
		Team obj = EntityFactroy.getBean(Team.class);
		obj.setCupId(cupId);
		obj.setTeamId(id);
		obj.setTeamName(name);
		obj.setLeaderId(leaderId);
		obj.setVaildCode(code);
		obj.setTeamDesc(desc);
		return obj;
	}

	@Override
	@Transactional
	public BaseExcution<Team> removeById(String objId) throws Exception {
		Team selectByPrimaryKey = dao.findById(objId);
		if (selectByPrimaryKey == null || selectByPrimaryKey.getCupId() == null) {
			return new BaseExcution<>(BaseStateEnum.FAIL);
		}
		BaseExcution<Group> findByCondition = groupService.findByCondition(new Group(selectByPrimaryKey.getCupId()));
		if (!ResultUtil.failListResult(findByCondition) && findByCondition.getCount() > 0) {
			return new BaseExcution<>(BaseStateEnum.NEED_DELETE_GROUP);
		}

		Player p = EntityFactroy.getBean(Player.class);
		p.setTeamId(objId);
		BaseExcution<Player> findByCondition2 = playerService.findByCondition(p);
		if (ResultUtil.failListResult(findByCondition2)) {
			return new BaseExcution<>(BaseStateEnum.QUERY_ERROR);
		}
		List<Player> selectByExample = findByCondition2.getObjList();
		if (selectByExample != null && selectByExample.size() > 0) {
			Iterator<Player> iterator = selectByExample.iterator();
			while (iterator.hasNext()) {
				playerService.removeById(iterator.next().getPlayerId());
			}
		}
		rankDao.remove(objId);
		dao.remove(objId);
		return new BaseExcution<>(BaseStateEnum.SUCCESS);
	}

	@Override
	public BaseExcution<Team> createTeam(Team team) {
		BaseExcution<Team> insertObj = add(team);
		if (ResultUtil.failResult(insertObj)) {
			return insertObj;
		}
		return new BaseExcution<Team>(BaseStateEnum.SUCCESS, insertObj.getObj());

	}

	@Override
	public BaseExcution<Team> edit(Team obj) {
		if (obj == null) {
			return new BaseExcution<>(BaseStateEnum.EMPTY);
		}
		dao.edit(obj);
		return new BaseExcution<>(BaseStateEnum.SUCCESS);
	}

	@Override
	public BaseExcution<Team> findById(String objId) {
		Team selectByPrimaryKey = dao.findById(objId);
		if (selectByPrimaryKey == null) {
			return new BaseExcution<Team>(BaseStateEnum.QUERY_ERROR);
		}
		return new BaseExcution<Team>(BaseStateEnum.SUCCESS, selectByPrimaryKey);
	}

	@Override
	public BaseExcution<Team> findByCondition(Team obj) {
		List<Team> selectRowBounds = dao.findAllCondition(obj);
		if (selectRowBounds != null && selectRowBounds.size() > -1) {
			return new BaseExcution<Team>(BaseStateEnum.SUCCESS, selectRowBounds, selectRowBounds.size());
		}
		return new BaseExcution<Team>(BaseStateEnum.QUERY_ERROR);

	}

	@Override
	public BaseExcution<Team> findAll(int pageIndex, int pageSize) {
		List<Team> selectAll = dao.findAll(pageIndex, pageSize);
		int selectCount = dao.count();
		if (selectAll == null) {
			return new BaseExcution<Team>(BaseStateEnum.QUERY_ERROR);
		}
		return new BaseExcution<Team>(BaseStateEnum.SUCCESS, selectAll, selectCount);
	}

	@Override
	public BaseExcution<TeamVo> findByIdMore(String id) {
		TeamVo selectByPrimary = dao.findIdMore(id);
		if (selectByPrimary == null) {
			return new BaseExcution<TeamVo>(BaseStateEnum.QUERY_ERROR);
		}
		return new BaseExcution<TeamVo>(BaseStateEnum.SUCCESS, selectByPrimary);
	}

	@Override
	public BaseExcution<TeamVo> findAllMore(Team obj, int pageIndex, int pageSize) {
		List<TeamVo> selectAllByPage = dao.findAllMore(obj, pageIndex, pageSize);
		int selectCount = dao.countExmaple(obj);
		if (selectAllByPage == null) {
			return new BaseExcution<TeamVo>(BaseStateEnum.QUERY_ERROR);
		}
		return new BaseExcution<TeamVo>(BaseStateEnum.SUCCESS, selectAllByPage, selectCount);
	}

	@Override
	@Transactional
	public BaseExcution<Team> removeByIdList(List<String> list) {
		try {
			Iterator<?> iterator = list.iterator();
			if (iterator == null) {
				return new BaseExcution<>(BaseStateEnum.FAIL);
			}
			while (iterator.hasNext()) {
				removeById((String) iterator.next());
			}
			return new BaseExcution<>(BaseStateEnum.SUCCESS);
		} catch (Exception e) {
			return new BaseExcution<>(BaseStateEnum.FAIL);
		}

	}

	@Override
	public BaseExcution<Team> editTeamLeader(String teamId, String leaderId) {
		dao.editTeamLeader(teamId, leaderId);
		return new BaseExcution<>(BaseStateEnum.SUCCESS);
	}

	@Override
	public BaseExcution<Team> add(Team obj) {
		if (obj == null) {
			return new BaseExcution<>(BaseStateEnum.EMPTY);
		}
		Team temp = EntityFactroy.getBean(Team.class);
		temp.setCupId(obj.getCupId());
		int selectCount = dao.countExmaple(temp);
		temp.setTeamName(obj.getTeamName());
		Team selectPrimary = dao.findByCondition(temp);
		if (null != selectPrimary) {
			return new BaseExcution<>(BaseStateEnum.SAME_TEAMNAME);
		}
		if (selectCount >= 9) {
			return new BaseExcution<>(BaseStateEnum.MAX_TEAM_COUNT);
		}
		obj.setTeamId(IDUtils.getUUID());
		dao.add(obj);
		return new BaseExcution<>(BaseStateEnum.SUCCESS,obj);
	}

	@Override
	@Transactional
	public BaseExcution<Team> createTeamAddPlayer(Team team, Player player, PlayerInfo info) throws Exception {
		BaseExcution<Team> createTeam = createTeam(team);
		if (ResultUtil.failResult(createTeam)) {
			throw new RuntimeException("创建球队失败：" + createTeam.getStateInfo());
		}
		player.setTeamId(createTeam.getObj().getTeamId());
		BaseExcution<Player> insertObj = playerService.add(player, info);
		if (ResultUtil.failResult(insertObj)) {
			throw new RuntimeException("创建球员失败" + insertObj.getStateInfo());
		}
		BaseExcution<Team> updateTeamLeader = editTeamLeader(createTeam.getObj().getTeamId(),
				insertObj.getObj().getPlayerId());
		if (ResultUtil.failResult(updateTeamLeader)) {
			throw new RuntimeException("更新队长失败" + updateTeamLeader.getStateInfo());
		}
		return createTeam;
	}

	@Override
	public BaseExcution<GroupVo> findTeamByGroup(String cupId) {
		BaseExcution<GroupVo> queryTeamByGroup = groupService.queryTeamByGroup(cupId);
		if (ResultUtil.failListResult(queryTeamByGroup)) {
			return new BaseExcution<>(BaseStateEnum.QUERY_ERROR);
		}
		return queryTeamByGroup;
	}

	@Override
	public BaseExcution<TeamPrint> getTeamPrint(String teamId) {
		TeamPrint temp = EntityFactroy.getBean(TeamPrint.class);
		Team selectPrimary = dao.findById(teamId);
		temp.setTeam(selectPrimary);
		BaseExcution<PlayerVo> findByIdMore = playerService.findByIdMore(selectPrimary.getLeaderId());
		if (ResultUtil.failResult(findByIdMore)) {
			return new BaseExcution<>(BaseStateEnum.FAIL);
		}
		temp.setLeader(findByIdMore.getObj());
		Player condition = EntityFactroy.getBean(Player.class);
		condition.setTeamId(teamId);
		BaseExcution<PlayerVo> findAllMore = playerService.findAllMore(condition, 1, 999);
		if (ResultUtil.failListResult(findAllMore)) {
			return new BaseExcution<>(BaseStateEnum.FAIL);
		}
		temp.setPlayerList(findAllMore.getObjList());
		return new BaseExcution<TeamPrint>(BaseStateEnum.SUCCESS, temp);
	}

}
