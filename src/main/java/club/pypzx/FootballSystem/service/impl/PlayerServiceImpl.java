package club.pypzx.FootballSystem.service.impl;

import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import club.pypzx.FootballSystem.dao.PlayerDao;
import club.pypzx.FootballSystem.dao.PlayerInfoDao;
import club.pypzx.FootballSystem.dao.PlayerRankDao;
import club.pypzx.FootballSystem.dbmgr.EntityFactroy;
import club.pypzx.FootballSystem.entity.Player;
import club.pypzx.FootballSystem.entity.PlayerInfo;
import club.pypzx.FootballSystem.entity.PlayerVo;
import club.pypzx.FootballSystem.entity.Team;
import club.pypzx.FootballSystem.service.PlayerService;
import club.pypzx.FootballSystem.service.TeamService;
import club.pypzx.FootballSystem.template.BaseExcution;
import club.pypzx.FootballSystem.template.BaseStateEnum;
import club.pypzx.FootballSystem.utils.IDUtils;
import club.pypzx.FootballSystem.utils.ParamUtils;
import club.pypzx.FootballSystem.utils.ResultUtil;

@Service
public class PlayerServiceImpl implements PlayerService {

	@Autowired
	private PlayerDao dao;
	@Autowired
	private PlayerRankDao rankDao;
	@Autowired
	private PlayerInfoDao infoDao;

	@Autowired
	private TeamService teamService;

	public Player packagePlayer(String teamId, String name, int num) throws Exception {
		Player obj = EntityFactroy.getBean(Player.class);
		obj.setPlayerNum(num);
		obj.setPlayerId(IDUtils.getUUID());
		obj.setPlayerName(name);
		obj.setTeamId(teamId);
		return obj;
	}

	public Player packagePlayer(String id, String teamId, String name, int num) throws Exception {
		Player obj = EntityFactroy.getBean(Player.class);
		obj.setPlayerId(id);
		obj.setPlayerNum(num);
		obj.setPlayerName(name);
		obj.setTeamId(teamId);
		return obj;
	}

	public PlayerInfo packagePlayerInfo(String id, String stuno, String depart, String tel) throws Exception {
		PlayerInfo obj = EntityFactroy.getBean(PlayerInfo.class);
		obj.setPlayerId(id);
		obj.setPlayerStuno(stuno);
		obj.setPlayerDepart(depart);
		obj.setPlayerTel(tel);
		return obj;
	}

	@Override
	@Transactional
	public BaseExcution<Player> removeById(String objId) throws Exception {
		BaseExcution<Player> findById = findById(objId);
		if (findById.getState() != BaseStateEnum.SUCCESS.getState()) {
			throw new RuntimeException("球员信息有误");
		}
		// 球队不为空，删除球队
		if (ParamUtils.validString(findById.getObj().getTeamId())) {
			BaseExcution<Team> editTeamLeader = teamService.editTeamLeader(findById.getObj().getTeamId(), null);
			if (editTeamLeader.getState() != BaseStateEnum.SUCCESS.getState()) {
				throw new RuntimeException("球员信息有误");
			}
		}
		infoDao.remove(objId);
		rankDao.remove(objId);
		dao.remove(objId);

		return new BaseExcution<>(BaseStateEnum.SUCCESS);
	}

	@Override
	public BaseExcution<Player> add(Player obj) {
		if (obj == null) {
			return new BaseExcution<>(BaseStateEnum.EMPTY);
		}
		dao.add(obj);
		return new BaseExcution<>(BaseStateEnum.SUCCESS);
	}

	@Override
	public BaseExcution<Player> edit(Player obj) {
		if (obj == null) {
			return new BaseExcution<>(BaseStateEnum.EMPTY);
		}
		dao.edit(obj);
		return new BaseExcution<>(BaseStateEnum.SUCCESS);
	}

	@Override
	public BaseExcution<Player> findById(String objId) {
		Player selectByPrimaryKey = dao.findById(objId);
		if (selectByPrimaryKey == null) {
			return new BaseExcution<Player>(BaseStateEnum.QUERY_ERROR);
		}
		return new BaseExcution<Player>(BaseStateEnum.SUCCESS, selectByPrimaryKey);
	}

	@Override
	public BaseExcution<Player> findByCondition(Player obj) {
		List<Player> selectRowBounds = dao.findAllCondition(obj);
		if (selectRowBounds != null && selectRowBounds.size() > -1) {
			return new BaseExcution<Player>(BaseStateEnum.SUCCESS, selectRowBounds, selectRowBounds.size());
		}
		return new BaseExcution<Player>(BaseStateEnum.QUERY_ERROR);
	}

	@Override
	public BaseExcution<Player> findAll(int pageIndex, int pageSize) {
		List<Player> selectAll = dao.findAll(pageIndex, pageSize);
		int selectCount = dao.count();
		if (selectAll == null) {
			return new BaseExcution<Player>(BaseStateEnum.QUERY_ERROR);
		}
		return new BaseExcution<Player>(BaseStateEnum.SUCCESS, selectAll, selectCount);
	}

	@Override
	public BaseExcution<PlayerVo> findByIdMore(String id) {
		PlayerVo selectByPrimary = dao.findIdMore(id);
		if (selectByPrimary == null) {
			return new BaseExcution<PlayerVo>(BaseStateEnum.QUERY_ERROR);
		}
		return new BaseExcution<PlayerVo>(BaseStateEnum.SUCCESS, selectByPrimary);
	}

	@Override
	public BaseExcution<PlayerVo> findAllMore(Player example, int pageIndex, int pageSize) {
		List<PlayerVo> selectAllByPage = dao.findAllMore(example, pageIndex, pageSize);
		int selectCount = dao.countExmaple(example);
		if (selectAllByPage == null) {
			return new BaseExcution<PlayerVo>(BaseStateEnum.QUERY_ERROR);
		}
		return new BaseExcution<PlayerVo>(BaseStateEnum.SUCCESS, selectAllByPage, selectCount);
	}

	@Override
	@Transactional
	public BaseExcution<Player> add(String teamId, String name, int num, String stuno, String depart, String tel)
			throws Exception {
		Player packagePlayer = null;
		PlayerInfo packagePlayerInfo = null;
		try {
			packagePlayer = packagePlayer(teamId, name, num);
			packagePlayerInfo = packagePlayerInfo(packagePlayer.getPlayerId(), stuno, depart, tel);
		} catch (Exception e) {
			return new BaseExcution<Player>(BaseStateEnum.EMPTY);
		}
		// 球队人数限制
		Player temp =EntityFactroy.getBean(Player.class);
		temp.setTeamId(teamId);
		int selectCount = dao.countExmaple(temp);
		if (selectCount >= 14) {
			return new BaseExcution<>(BaseStateEnum.TO_MANY_PLAYER);
		}
		temp.setPlayerNum(num);
		Player selectPrimary = dao.findByCondition(temp);
		if (null != selectPrimary) {
			return new BaseExcution<Player>(BaseStateEnum.SAME_PLAYERNUM);
		}
		if (BaseStateEnum.SUCCESS.getState() != add(packagePlayer).getState()) {
			throw new Exception("加入球队失败");
		}
		infoDao.add(packagePlayerInfo);
		return new BaseExcution<>(BaseStateEnum.SUCCESS, packagePlayer);
	}

	@Override
	@Transactional
	public BaseExcution<Player> edit(String id, String teamId, String name, int num, String stuno, String depart,
			String tel) throws Exception {
		Player packagePlayer = null;
		PlayerInfo packagePlayerInfo = null;
		try {
			packagePlayer = packagePlayer(id, teamId, name, num);
			packagePlayerInfo = packagePlayerInfo(packagePlayer.getPlayerId(), stuno, depart, tel);
		} catch (Exception e) {
			return new BaseExcution<Player>(BaseStateEnum.FAIL);
		}
		if (BaseStateEnum.SUCCESS.getState() != edit(packagePlayer).getState()) {
			throw new Exception("修改球员失败");
		}
		infoDao.edit(packagePlayerInfo);
		return new BaseExcution<>(BaseStateEnum.SUCCESS);
	}

	@Override
	public boolean checkValidCode(String validCode, String teamId) {

		BaseExcution<Team> findById = teamService.findById(teamId);
		if (ResultUtil.failResult(findById)) {
			return false;
		}
		if (ParamUtils.emptyString(findById.getObj().getVaildCode())) {
			return false;
		}
		if (!validCode.equals(findById.getObj().getVaildCode())) {
			return false;
		}
		return true;
	}

	@Override
	public BaseExcution<Player> removeByIdList(List<String> list) throws Exception {
		Iterator<?> iterator = list.iterator();
		if (iterator == null) {
			return new BaseExcution<>(BaseStateEnum.FAIL);
		}
		while (iterator.hasNext()) {
			removeById((String) iterator.next());
		}
		return new BaseExcution<>(BaseStateEnum.SUCCESS);
	}

	@Override
	public BaseExcution<Player> checkValidId(String playerId) {
		Player bean = EntityFactroy.getBean(Player.class);
		bean.setPlayerId(playerId);
		int selectCount = dao.countExmaple(bean);
		if (0 != selectCount) {
			return new BaseExcution<>(BaseStateEnum.FAIL);
		}
		return new BaseExcution<>(BaseStateEnum.SUCCESS);
	}

}
