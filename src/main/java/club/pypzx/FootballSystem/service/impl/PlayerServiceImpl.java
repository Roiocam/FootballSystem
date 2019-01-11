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

	private final PlayerDao dao;
	private final PlayerRankDao rankDao;
	private final PlayerInfoDao infoDao;

	private final TeamService teamService;

	@Autowired
	public PlayerServiceImpl(PlayerDao dao, PlayerRankDao rankDao, PlayerInfoDao infoDao, TeamService teamService) {
		this.dao = dao;
		this.rankDao = rankDao;
		this.infoDao = infoDao;
		this.teamService = teamService;
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
		obj.setPlayerId(IDUtils.getUUID());
		dao.add(obj);
		return new BaseExcution<>(BaseStateEnum.SUCCESS, obj);
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
	public BaseExcution<Player> add(Player player, PlayerInfo info) throws Exception {
		// 球队人数限制
		Player temp = EntityFactroy.getBean(Player.class);
		temp.setTeamId(player.getTeamId());
		int selectCount = dao.countExmaple(temp);
		if (selectCount >= 14) {
			return new BaseExcution<>(BaseStateEnum.TO_MANY_PLAYER);
		}
		// 球衣号码限制
		temp.setPlayerNum(player.getPlayerNum());
		Player selectPrimary = dao.findByCondition(temp);
		if (null != selectPrimary) {
			return new BaseExcution<Player>(BaseStateEnum.SAME_PLAYERNUM);
		}
		BaseExcution<Player> checkValidId = checkValidId(info.getPlayerStuno());
		if(BaseStateEnum.SUCCESS.getState()!=checkValidId.getState()) {
			return new BaseExcution<Player>(BaseStateEnum.SAME_PLAYER_STUNO);
		}
		BaseExcution<Player> add = add(player);
		if (BaseStateEnum.SUCCESS.getState() != add.getState()) {
			throw new Exception("加入球队失败");
		}
		info.setPlayerId(add.getObj().getPlayerId());
		infoDao.add(info);
		return new BaseExcution<>(BaseStateEnum.SUCCESS, add.getObj());
	}

	@Override
	@Transactional
	public BaseExcution<Player> edit(Player player, PlayerInfo info) throws Exception {
		if (BaseStateEnum.SUCCESS.getState() != edit(player).getState()) {
			throw new Exception("修改球员失败");
		}
		info.setPlayerId(player.getPlayerId());
		dao.edit(player);
		infoDao.edit(info);
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
		for(String is:list){
			removeById(is);
		}
		return new BaseExcution<>(BaseStateEnum.SUCCESS);
	}

	@Override
	public BaseExcution<Player> checkValidId(String playerStuno) {
		PlayerInfo bean = EntityFactroy.getBean(PlayerInfo.class);
		bean.setPlayerStuno(playerStuno);
		int selectCount = infoDao.countExmaple(bean);
		if (0 != selectCount) {
			return new BaseExcution<>(BaseStateEnum.FAIL);
		}
		return new BaseExcution<>(BaseStateEnum.SUCCESS);
	}

}
