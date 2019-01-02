package club.pypzx.FootballSystem.service.impl;

import java.util.Iterator;
import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import club.pypzx.FootballSystem.dao.mybatis.TeamMapper;
import club.pypzx.FootballSystem.dao.mybatis.TeamRankMapper;
import club.pypzx.FootballSystem.dto.GroupVo;
import club.pypzx.FootballSystem.dto.PlayerVo;
import club.pypzx.FootballSystem.dto.TeamPrint;
import club.pypzx.FootballSystem.dto.TeamVo;
import club.pypzx.FootballSystem.entity.Group;
import club.pypzx.FootballSystem.entity.Page;
import club.pypzx.FootballSystem.entity.Player;
import club.pypzx.FootballSystem.entity.Team;
import club.pypzx.FootballSystem.entity.TeamRank;
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
	private TeamMapper mapper;
	@Autowired
	private PlayerService playerService;
	@Autowired
	private TeamRankMapper rankMapper;
	@Autowired
	private GroupService groupService;

	public Team packageTeam(String cupId, String name, String code, String desc) {
		Team obj = new Team();
		obj.setCupId(cupId);
		obj.setTeamId(IDUtils.getUUID());
		obj.setTeamName(name);
		obj.setVaildCode(code);
		obj.setTeamDesc(desc);
		return obj;
	}

	public Team packageTeam(String id, String cupId, String name, String leaderId, String code, String desc) {
		Team obj = new Team();
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
		Team selectByPrimaryKey = mapper.selectPrimary(new Team(objId));
		if (selectByPrimaryKey == null || selectByPrimaryKey.getCupId() == null) {
			return new BaseExcution<>(BaseStateEnum.FAIL);
		}
		BaseExcution<Group> findByCondition = groupService.findByCondition(new Group(selectByPrimaryKey.getCupId()));
		if (!ResultUtil.failListResult(findByCondition) && findByCondition.getCount() > 0) {
			return new BaseExcution<>(BaseStateEnum.NEED_DELETE_GROUP);
		}

		Player p = new Player();
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
		rankMapper.delete(new TeamRank(objId));
		if (1 != mapper.delete(new Team(objId))) {
			throw new Exception("删除球队记录失败");
		}
		return new BaseExcution<>(BaseStateEnum.SUCCESS);
	}

	@Override
	public BaseExcution<Team> createTeam(String cupId, String teamName, String vaildCode, String teamDesc) {
		Team packageTeam;
		packageTeam = packageTeam(cupId, teamName, vaildCode, teamDesc);
		BaseExcution<Team> insertObj = add(packageTeam);
		if (ResultUtil.failResult(insertObj)) {
			return insertObj;
		}
		return new BaseExcution<Team>(BaseStateEnum.SUCCESS, packageTeam);

	}

	@Override
	public BaseExcution<Team> edit(Team obj) {
		if (obj == null) {
			return new BaseExcution<>(BaseStateEnum.EMPTY);
		}
		if (1 != mapper.update(obj)) {
			return new BaseExcution<>(BaseStateEnum.FAIL);
		}
		return new BaseExcution<>(BaseStateEnum.SUCCESS);
	}

	@Override
	public BaseExcution<Team> findById(String objId) {
		Team selectByPrimaryKey = mapper.selectByPrimary(objId);
		if (selectByPrimaryKey == null) {
			return new BaseExcution<Team>(BaseStateEnum.QUERY_ERROR);
		}
		return new BaseExcution<Team>(BaseStateEnum.SUCCESS, selectByPrimaryKey);
	}

	@Override
	public BaseExcution<Team> findByCondition(Team obj) {
		int selectCount = mapper.selectCount(obj);
		List<Team> selectRowBounds = mapper.selectRowBounds(obj, new RowBounds(0, selectCount));
		if (selectRowBounds != null && selectRowBounds.size() > -1) {
			return new BaseExcution<Team>(BaseStateEnum.SUCCESS, selectRowBounds, selectRowBounds.size());
		}
		return new BaseExcution<Team>(BaseStateEnum.QUERY_ERROR);

	}

	@Override
	public BaseExcution<Team> findAll(int pageIndex, int pageSize) {

		List<Team> selectAll = mapper.selectRowBounds(new Team(), Page.getInstance(pageIndex, pageSize));
		if (selectAll == null) {
			return new BaseExcution<Team>(BaseStateEnum.QUERY_ERROR);
		}
		int selectCount = mapper.selectCount(new Team());
		return new BaseExcution<Team>(BaseStateEnum.SUCCESS, selectAll, selectCount);
	}

	@Override
	public BaseExcution<TeamVo> findByIdMore(String id) {
		TeamVo selectByPrimary = mapper.selectMorePrimary(id);
		if (selectByPrimary == null) {
			return new BaseExcution<TeamVo>(BaseStateEnum.QUERY_ERROR);
		}
		return new BaseExcution<TeamVo>(BaseStateEnum.SUCCESS, selectByPrimary);
	}

	@Override
	public BaseExcution<TeamVo> findAllMore(Team obj, int pageIndex, int pageSize) {
		List<TeamVo> selectAllByPage = mapper.selectMoreRowBounds(obj, Page.getInstance(pageIndex, pageSize));
		if (selectAllByPage == null) {
			return new BaseExcution<TeamVo>(BaseStateEnum.QUERY_ERROR);
		}
		int selectCount = mapper.selectCount(new Team());
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
		Team team = new Team();
		team.setTeamId(teamId);
		team.setLeaderId(leaderId);
		int updateByPrimaryKey = mapper.update(team);
		if (updateByPrimaryKey != 1) {
			return new BaseExcution<>(BaseStateEnum.FAIL);
		}
		return new BaseExcution<>(BaseStateEnum.SUCCESS);
	}

	@Override
	public BaseExcution<Team> add(Team obj) {
		if (obj == null) {
			return new BaseExcution<>(BaseStateEnum.EMPTY);
		}
		Team temp = new Team();
		temp.setTeamName(obj.getTeamName());
		temp.setCupId(obj.getCupId());
		if (null != mapper.selectPrimary(temp)) {
			return new BaseExcution<>(BaseStateEnum.SAME_TEAMNAME);
		}
		Team cupTeam = new Team();
		cupTeam.setCupId(obj.getCupId());
		int selectCount = mapper.selectCount(cupTeam);
		if (selectCount >= 9) {
			return new BaseExcution<>(BaseStateEnum.MAX_TEAM_COUNT);
		}
		if (1 != mapper.insert(obj)) {
			return new BaseExcution<>(BaseStateEnum.FAIL);
		}
		return new BaseExcution<>(BaseStateEnum.SUCCESS);
	}

	@Override
	@Transactional
	public BaseExcution<Team> createTeamAddPlayer(String cupId, String teamName, String vaildCode, String teamDesc,
			String playerName, int playerNum, String playerStuno, String playerDepart, String playerTel)
			throws Exception {
		BaseExcution<Team> createTeam = createTeam(cupId, teamName, vaildCode, teamDesc);
		if (ResultUtil.failResult(createTeam)) {
			throw new RuntimeException("创建球队失败：" + createTeam.getStateInfo());
		}
		BaseExcution<Player> insertObj = playerService.add(createTeam.getObj().getTeamId(), playerName, playerNum,
				playerStuno, playerDepart, playerTel);
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
		TeamPrint temp = new TeamPrint();
		Team team = mapper.selectByPrimary(teamId);
		if (team == null) {
			return new BaseExcution<>(BaseStateEnum.FAIL);
		}
		temp.setTeam(team);
		BaseExcution<PlayerVo> findByIdMore = playerService.findByIdMore(team.getLeaderId());
		if (ResultUtil.failResult(findByIdMore)) {
			return new BaseExcution<>(BaseStateEnum.FAIL);
		}
		temp.setLeader(findByIdMore.getObj());
		Player condition = new Player();
		condition.setTeamId(teamId);
		BaseExcution<PlayerVo> findAllMore = playerService.findAllMore(condition, 1, 999);
		if (ResultUtil.failListResult(findAllMore)) {
			return new BaseExcution<>(BaseStateEnum.FAIL);
		}
		temp.setPlayerList(findAllMore.getObjList());
		return new BaseExcution<TeamPrint>(BaseStateEnum.SUCCESS, temp);
	}

}
