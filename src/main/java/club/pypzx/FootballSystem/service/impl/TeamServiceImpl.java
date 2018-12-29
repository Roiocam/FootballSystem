package club.pypzx.FootballSystem.service.impl;

import java.util.Iterator;
import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import club.pypzx.FootballSystem.dao.GroupMapper;
import club.pypzx.FootballSystem.dao.PlayerMapper;
import club.pypzx.FootballSystem.dao.TeamMapper;
import club.pypzx.FootballSystem.dao.TeamRankMapper;
import club.pypzx.FootballSystem.dto.BaseExcution;
import club.pypzx.FootballSystem.dto.GroupVo;
import club.pypzx.FootballSystem.dto.PlayerVo;
import club.pypzx.FootballSystem.dto.TeamPrint;
import club.pypzx.FootballSystem.dto.TeamVo;
import club.pypzx.FootballSystem.entity.Group;
import club.pypzx.FootballSystem.entity.Page;
import club.pypzx.FootballSystem.entity.Player;
import club.pypzx.FootballSystem.entity.Team;
import club.pypzx.FootballSystem.entity.TeamRank;
import club.pypzx.FootballSystem.enums.BaseStateEnum;
import club.pypzx.FootballSystem.service.PlayerService;
import club.pypzx.FootballSystem.service.TeamService;
import club.pypzx.FootballSystem.utils.IDUtils;
import club.pypzx.FootballSystem.utils.ResultUtil;

@Service
public class TeamServiceImpl implements TeamService {

	@Autowired
	private TeamMapper mapper;
	@Autowired
	private PlayerService playerService;
	@Autowired
	private PlayerMapper playerMapper;
	@Autowired
	private TeamRankMapper rankMapper;
	@Autowired
	private GroupMapper groupMapper;

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
	public BaseExcution<Team> deleteObjByPrimaryKey(String objId) throws Exception {
		Team selectByPrimaryKey = mapper.selectByPrimaryKey(objId);
		if (selectByPrimaryKey == null || selectByPrimaryKey.getCupId() == null) {
			return new BaseExcution<>(BaseStateEnum.FAIL);
		}
		if (0 < groupMapper.selectCount(new Group(selectByPrimaryKey.getCupId()))) {
			return new BaseExcution<>(BaseStateEnum.NEED_DELETE_GROUP);
		}

		Player p = new Player();
		p.setTeamId(objId);
		List<Player> selectByExample = playerMapper.select(p);
		if (selectByExample != null && selectByExample.size() > 0) {
			Iterator<Player> iterator = selectByExample.iterator();
			while (iterator.hasNext()) {
				playerService.deleteObjByPrimaryKey(iterator.next().getPlayerId());
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
		BaseExcution<Team> insertObj = insertObj(packageTeam);
		if (ResultUtil.failResult(insertObj)) {
			return insertObj;
		}
		return new BaseExcution<Team>(BaseStateEnum.SUCCESS, packageTeam);

	}

	@Override
	public BaseExcution<Team> updateObjByPrimaryKey(Team obj) {
		if (obj == null) {
			return new BaseExcution<>(BaseStateEnum.EMPTY);
		}
		if (1 != mapper.updateByPrimaryKey(obj)) {
			return new BaseExcution<>(BaseStateEnum.FAIL);
		}
		return new BaseExcution<>(BaseStateEnum.SUCCESS);
	}

	@Override
	public BaseExcution<Team> queryObjOneByPrimaryKey(String objId) {
		Team selectByPrimaryKey = mapper.selectByPrimaryKey(objId);
		if (selectByPrimaryKey == null) {
			return new BaseExcution<Team>(BaseStateEnum.QUERY_ERROR);
		}
		return new BaseExcution<Team>(BaseStateEnum.SUCCESS, selectByPrimaryKey);
	}

	@Override
	public BaseExcution<Team> queryObjOne(Team obj) {
		Team selectOne = mapper.selectOne(obj);
		if (selectOne == null) {
			return new BaseExcution<Team>(BaseStateEnum.QUERY_ERROR);
		}
		return new BaseExcution<Team>(BaseStateEnum.SUCCESS, selectOne);
	}

	@Override
	public BaseExcution<Team> queryAll(int pageIndex, int pageSize) {

		List<Team> selectAll = mapper.selectByRowBounds(null, new RowBounds((pageIndex - 1) * pageSize, pageSize));
		if (selectAll == null) {
			return new BaseExcution<Team>(BaseStateEnum.QUERY_ERROR);
		}
		int selectCount = mapper.selectCount(null);
		return new BaseExcution<Team>(BaseStateEnum.SUCCESS, selectAll, selectCount);
	}

	@Override
	public BaseExcution<TeamVo> selectByPrimary(String id) {
		TeamVo selectByPrimary = mapper.selectByPrimary(id);
		if (selectByPrimary == null) {
			return new BaseExcution<TeamVo>(BaseStateEnum.QUERY_ERROR);
		}
		return new BaseExcution<TeamVo>(BaseStateEnum.SUCCESS, selectByPrimary);
	}

	@Override
	public BaseExcution<TeamVo> queryAllByPage(Team example, int pageIndex, int pageSize) {

		List<TeamVo> selectAllByPage = mapper.selectAllByPage(example, Page.getInstance(pageIndex, pageSize));
		if (selectAllByPage == null) {
			return new BaseExcution<TeamVo>(BaseStateEnum.QUERY_ERROR);
		}
		int selectCount = mapper.selectCount(example);
		return new BaseExcution<TeamVo>(BaseStateEnum.SUCCESS, selectAllByPage, selectCount);
	}

	@Override
	@Transactional
	public BaseExcution<Team> deleteObjectList(List<String> list) {
		try {
			Iterator<?> iterator = list.iterator();
			if (iterator == null) {
				return new BaseExcution<>(BaseStateEnum.FAIL);
			}
			while (iterator.hasNext()) {
				deleteObjByPrimaryKey((String) iterator.next());
			}
			return new BaseExcution<>(BaseStateEnum.SUCCESS);
		} catch (Exception e) {
			return new BaseExcution<>(BaseStateEnum.FAIL);
		}

	}

	@Override
	public BaseExcution<Team> updateTeamLeader(String teamId, String leaderId) {
		Team team = new Team();
		team.setTeamId(teamId);
		team.setLeaderId(leaderId);
		int updateByPrimaryKey = mapper.updateByPrimaryKeySelective(team);
		if (updateByPrimaryKey != 1) {
			return new BaseExcution<>(BaseStateEnum.FAIL);
		}
		return new BaseExcution<>(BaseStateEnum.SUCCESS);
	}

	@Override
	public BaseExcution<Team> insertObj(Team obj) {
		if (obj == null) {
			return new BaseExcution<>(BaseStateEnum.EMPTY);
		}
		Team temp = new Team();
		temp.setTeamName(obj.getTeamName());
		temp.setCupId(obj.getCupId());
		if (null != mapper.selectOne(temp)) {
			return new BaseExcution<>(BaseStateEnum.SAME_TEAMNAME);
		}
		Team cupTeam=new Team();
		cupTeam.setCupId(obj.getCupId());
		int selectCount = mapper.selectCount(cupTeam);
		if(selectCount>=9) {
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
		BaseExcution<Player> insertObj = playerService.insertObject(createTeam.getObj().getTeamId(), playerName,
				playerNum, playerStuno, playerDepart, playerTel);
		if (ResultUtil.failResult(insertObj)) {
			throw new RuntimeException("创建球员失败" + insertObj.getStateInfo());
		}
		BaseExcution<Team> updateTeamLeader = updateTeamLeader(createTeam.getObj().getTeamId(),
				insertObj.getObj().getPlayerId());
		if (ResultUtil.failResult(updateTeamLeader)) {
			throw new RuntimeException("更新队长失败" + updateTeamLeader.getStateInfo());
		}
		return createTeam;
	}

	@Override
	public BaseExcution<GroupVo> queryTeamByGroup(String cupId) {
		List<GroupVo> queryTeamByGroup = groupMapper.queryTeamByGroup(cupId);
		if (queryTeamByGroup == null || queryTeamByGroup.size() < 0) {
			return new BaseExcution<>(BaseStateEnum.QUERY_ERROR);

		}
		return new BaseExcution<>(BaseStateEnum.SUCCESS, queryTeamByGroup, 0);
	}

	@Override
	public BaseExcution<TeamPrint> getTeamPrint(String teamId) {
		TeamPrint temp = new TeamPrint();
		Team team = mapper.selectByPrimaryKey(teamId);
		if (team == null) {
			return new BaseExcution<>(BaseStateEnum.FAIL);
		}
		temp.setTeam(team);
		PlayerVo leader = playerMapper.selectByPrimary(team.getLeaderId());
		if (leader == null) {
			return new BaseExcution<>(BaseStateEnum.FAIL);
		}
		temp.setLeader(leader);
		Player condition = new Player();
		condition.setTeamId(teamId);
		List<PlayerVo> select = playerMapper.selectAllByPage(condition, Page.getInstance(1, 999));
		if (select == null) {
			return new BaseExcution<>(BaseStateEnum.FAIL);
		}
		temp.setPlayerList(select);
		return new BaseExcution<TeamPrint>(BaseStateEnum.SUCCESS, temp);
	}

}
