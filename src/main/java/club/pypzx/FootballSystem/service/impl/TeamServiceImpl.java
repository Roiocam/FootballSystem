package club.pypzx.FootballSystem.service.impl;

import java.util.Iterator;
import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import club.pypzx.FootballSystem.dao.jpa.TeamRankRepository;
import club.pypzx.FootballSystem.dao.jpa.TeamRepository;
import club.pypzx.FootballSystem.dao.jpa.TeamVoRepository;
import club.pypzx.FootballSystem.dao.mybatis.TeamMapper;
import club.pypzx.FootballSystem.dao.mybatis.TeamRankMapper;
import club.pypzx.FootballSystem.datasource.DBIdentifier;
import club.pypzx.FootballSystem.dto.GroupVo;
import club.pypzx.FootballSystem.dto.PlayerVo;
import club.pypzx.FootballSystem.dto.TeamPrint;
import club.pypzx.FootballSystem.dto.TeamVo;
import club.pypzx.FootballSystem.entity.Group;
import club.pypzx.FootballSystem.entity.Page;
import club.pypzx.FootballSystem.entity.Player;
import club.pypzx.FootballSystem.entity.Team;
import club.pypzx.FootballSystem.entity.TeamRank;
import club.pypzx.FootballSystem.enums.DBType;
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
	private TeamRepository repository;
	@Autowired
	private PlayerService playerService;
	@Autowired
	private TeamRankMapper rankMapper;
	@Autowired
	private TeamRankRepository rankRepository;
	@Autowired
	private GroupService groupService;
	@Autowired
	private TeamVoRepository voRepository;

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
		Team selectByPrimaryKey = null;
		if (DBIdentifier.getDbType().equals(DBType.MY_BATIS)) {
			selectByPrimaryKey = mapper.selectPrimary(new Team(objId));
		} else if (DBIdentifier.getDbType().equals(DBType.JPA)) {
			selectByPrimaryKey = repository.findOne(Example.of(new Team(objId))).get();
		}
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
		if (DBIdentifier.getDbType().equals(DBType.MY_BATIS)) {
			rankMapper.delete(new TeamRank(objId));
			if (1 != mapper.delete(new Team(objId))) {
				throw new Exception("删除球队记录失败");
			}
		} else if (DBIdentifier.getDbType().equals(DBType.JPA)) {
			rankRepository.delete(new TeamRank(objId));
			repository.delete(new Team(objId));
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
		if (DBIdentifier.getDbType().equals(DBType.MY_BATIS)) {
			if (1 != mapper.update(obj)) {
				return new BaseExcution<>(BaseStateEnum.FAIL);
			}
		} else if (DBIdentifier.getDbType().equals(DBType.JPA)) {
			repository.save(obj);
		}
		return new BaseExcution<>(BaseStateEnum.SUCCESS);
	}

	@Override
	public BaseExcution<Team> findById(String objId) {
		Team selectByPrimaryKey = null;
		if (DBIdentifier.getDbType().equals(DBType.MY_BATIS)) {
			selectByPrimaryKey = mapper.selectByPrimary(objId);
		} else if (DBIdentifier.getDbType().equals(DBType.JPA)) {
			selectByPrimaryKey = repository.findById(objId).get();
		}
		if (selectByPrimaryKey == null) {
			return new BaseExcution<Team>(BaseStateEnum.QUERY_ERROR);
		}
		return new BaseExcution<Team>(BaseStateEnum.SUCCESS, selectByPrimaryKey);
	}

	@Override
	public BaseExcution<Team> findByCondition(Team obj) {
		List<Team> selectRowBounds = null;
		if (DBIdentifier.getDbType().equals(DBType.MY_BATIS)) {
			int selectCount = mapper.selectCount(obj);
			selectRowBounds = mapper.selectRowBounds(obj, new RowBounds(0, selectCount));
		} else if (DBIdentifier.getDbType().equals(DBType.JPA)) {
			selectRowBounds = repository.findAll(Example.of(obj));
		}
		if (selectRowBounds != null && selectRowBounds.size() > -1) {
			return new BaseExcution<Team>(BaseStateEnum.SUCCESS, selectRowBounds, selectRowBounds.size());
		}
		return new BaseExcution<Team>(BaseStateEnum.QUERY_ERROR);

	}

	@Override
	public BaseExcution<Team> findAll(int pageIndex, int pageSize) {
		List<Team> selectAll = null;
		int selectCount = 0;
		if (DBIdentifier.getDbType().equals(DBType.MY_BATIS)) {
			selectAll = mapper.selectRowBounds(new Team(), Page.getInstance(pageIndex, pageSize));
			selectCount = mapper.selectCount(new Team());
		} else if (DBIdentifier.getDbType().equals(DBType.JPA)) {
			org.springframework.data.domain.Page<Team> findAll = repository
					.findAll(PageRequest.of(pageIndex - 1, pageSize));
			selectAll = findAll.getContent();
			selectCount = (int) repository.count();
		}
		if (selectAll == null) {
			return new BaseExcution<Team>(BaseStateEnum.QUERY_ERROR);
		}
		return new BaseExcution<Team>(BaseStateEnum.SUCCESS, selectAll, selectCount);
	}

	@Override
	public BaseExcution<TeamVo> findByIdMore(String id) {
		TeamVo selectByPrimary = null;
		if (DBIdentifier.getDbType().equals(DBType.MY_BATIS)) {
			selectByPrimary = mapper.selectMorePrimary(id);
		} else if (DBIdentifier.getDbType().equals(DBType.JPA)) {
			selectByPrimary = voRepository.findById(id).get();
		}
		if (selectByPrimary == null) {
			return new BaseExcution<TeamVo>(BaseStateEnum.QUERY_ERROR);
		}
		return new BaseExcution<TeamVo>(BaseStateEnum.SUCCESS, selectByPrimary);
	}

	@Override
	public BaseExcution<TeamVo> findAllMore(Team obj, int pageIndex, int pageSize) {
		List<TeamVo> selectAllByPage = null;
		int selectCount = 0;
		if (DBIdentifier.getDbType().equals(DBType.MY_BATIS)) {
			selectAllByPage = mapper.selectMoreRowBounds(obj, Page.getInstance(pageIndex, pageSize));
			selectCount = mapper.selectCount(new Team());
		} else if (DBIdentifier.getDbType().equals(DBType.JPA)) {
			org.springframework.data.domain.Page<TeamVo> findAll = voRepository
					.findAll(PageRequest.of(pageIndex - 1, pageSize));
			selectAllByPage = findAll.getContent();
			selectCount = (int) repository.count();
		}
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
		Team selectPrimary = null;
		int selectCount = 0;
		Team cupTeam = new Team();
		cupTeam.setCupId(obj.getCupId());
		if (DBIdentifier.getDbType().equals(DBType.MY_BATIS)) {
			selectPrimary = mapper.selectPrimary(temp);
			selectCount = mapper.selectCount(cupTeam);
		} else if (DBIdentifier.getDbType().equals(DBType.JPA)) {
			selectPrimary = repository.findOne(Example.of(temp)).get();
			selectCount = (int) repository.count(Example.of(cupTeam));
		}
		if (null != selectPrimary) {
			return new BaseExcution<>(BaseStateEnum.SAME_TEAMNAME);
		}
		if (selectCount >= 9) {
			return new BaseExcution<>(BaseStateEnum.MAX_TEAM_COUNT);
		}
		if (DBIdentifier.getDbType().equals(DBType.MY_BATIS)) {
			if (1 != mapper.insert(obj)) {
				return new BaseExcution<>(BaseStateEnum.FAIL);
			}
		} else if (DBIdentifier.getDbType().equals(DBType.JPA)) {
			repository.save(obj);
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
		Team selectPrimary = null;
		if (DBIdentifier.getDbType().equals(DBType.MY_BATIS)) {
			selectPrimary = mapper.selectPrimary(new Team(teamId));
		} else if (DBIdentifier.getDbType().equals(DBType.JPA)) {
			selectPrimary = repository.findOne(Example.of(new Team(teamId))).get();
		}
		temp.setTeam(selectPrimary);
		BaseExcution<PlayerVo> findByIdMore = playerService.findByIdMore(selectPrimary.getLeaderId());
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
