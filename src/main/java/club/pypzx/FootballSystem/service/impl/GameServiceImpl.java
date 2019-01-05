package club.pypzx.FootballSystem.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import club.pypzx.FootballSystem.dao.GameDao;
import club.pypzx.FootballSystem.dbmgr.EntityFactroy;
import club.pypzx.FootballSystem.entity.Cup;
import club.pypzx.FootballSystem.entity.Game;
import club.pypzx.FootballSystem.entity.GameVo;
import club.pypzx.FootballSystem.entity.Group;
import club.pypzx.FootballSystem.service.CupService;
import club.pypzx.FootballSystem.service.GameService;
import club.pypzx.FootballSystem.service.GroupService;
import club.pypzx.FootballSystem.template.BaseExcution;
import club.pypzx.FootballSystem.template.BaseStateEnum;
import club.pypzx.FootballSystem.utils.GameDayUtils;
import club.pypzx.FootballSystem.utils.IDUtils;
import club.pypzx.FootballSystem.utils.ResultUtil;

@Service
public class GameServiceImpl implements GameService {
	@Autowired
	private GroupService groupService;
	@Autowired
	private GameDao dao;
	@Autowired
	private CupService cupService;

	@Override
	@Transactional
	public BaseExcution<Game> add(Game obj) {
		if (obj == null) {
			return new BaseExcution<>(BaseStateEnum.EMPTY);
		}
		dao.add(obj);
		return new BaseExcution<>(BaseStateEnum.SUCCESS);
	}

	@Override
	public BaseExcution<Game> edit(Game obj) throws Exception {
		if (obj == null) {
			return new BaseExcution<>(BaseStateEnum.EMPTY);
		}
		dao.edit(obj);
		return new BaseExcution<>(BaseStateEnum.SUCCESS);
	}

	@Override
	public BaseExcution<Game> findById(String objId) {
		Game game = new Game();
		game.setCupId(objId);
		Game findById = dao.findByCondition(game);
		if (findById == null) {
			return new BaseExcution<Game>(BaseStateEnum.QUERY_ERROR);
		}
		return new BaseExcution<Game>(BaseStateEnum.SUCCESS, findById);
	}

	@Override
	public BaseExcution<Game> findByCondition(Game obj) {
		List<Game> selectRowBounds = dao.findAllCondition(obj);
		if (selectRowBounds != null && selectRowBounds.size() > -1) {
			return new BaseExcution<Game>(BaseStateEnum.SUCCESS, selectRowBounds, selectRowBounds.size());
		}
		return new BaseExcution<Game>(BaseStateEnum.QUERY_ERROR);
	}

	@Override
	public BaseExcution<GameVo> findAll(String cupId, int pageIndex, int pageSize) {
		List<GameVo> selectAll = null;
		int selectCount = 0;
		selectAll = dao.findAll(cupId, pageIndex, pageSize);
		Game bean = EntityFactroy.getBean(Game.class);
		bean.setCupId(cupId);
		selectCount = dao.countExmaple(bean);
		if (selectAll == null) {
			return new BaseExcution<GameVo>(BaseStateEnum.QUERY_ERROR);
		}
		return new BaseExcution<GameVo>(BaseStateEnum.SUCCESS, selectAll, selectCount);
	}

	@Override
	@Deprecated
	public BaseExcution<Game> removeById(String objId) throws Exception {
		return null;
	}

	@Override
	@Deprecated
	public BaseExcution<Game> removeByIdList(List<String> list) throws Exception {
		return null;
	}

	@Override
	@Transactional
	public BaseExcution<Game> removeGroupByCup(String cupId) throws Exception {
		Game record = new Game();
		record.setCupId(cupId);
		dao.remove(cupId);
		if (BaseStateEnum.SUCCESS.getState() != groupService.removeById(cupId).getState()) {
			throw new RuntimeException("删除赛程分组时出错");
		}
		BaseExcution<Cup> findById = cupService.findById(cupId);
		if (BaseStateEnum.SUCCESS.getState() != findById.getState()) {
			throw new Exception("更新赛事分组状态失败");
		}
		Cup obj = findById.getObj();
		obj.setIsGroup(0);
		if (BaseStateEnum.SUCCESS.getState() != cupService.edit(obj).getState()) {
			throw new Exception("更新赛事分组状态失败");
		}
		return new BaseExcution<>(BaseStateEnum.SUCCESS);
	}

	@Override
	@Transactional
	public BaseExcution<Game> randomGameByGroup(String cupId) throws Exception {
		BaseExcution<Group> findByCondition = groupService.findByCondition(new Group(cupId));
		if (ResultUtil.failListResult(findByCondition)) {
			throw new RuntimeException("查询分组失败");
		}
		Iterator<Group> iterator = findByCondition.getObjList().iterator();
		List<Group> A = new ArrayList<Group>();
		List<Group> B = new ArrayList<Group>();
		List<Group> C = new ArrayList<Group>();
		while (iterator.hasNext()) {
			Group group = (Group) iterator.next();
			switch (group.getTeamGroup()) {
			case "A":
				A.add(group);
				break;
			case "B":
				B.add(group);
				break;
			case "C":
				C.add(group);
				break;

			default:
				break;
			}

		}
		List<Game> list = new ArrayList<Game>();
		packList(list, A);
		packList(list, B);
		packList(list, C);
		Collections.shuffle(list);
		List<Date> gameDay = GameDayUtils.getGameDay(list.size());
		for (int i = 0; i < gameDay.size(); i++) {
			list.get(i).setGameDate(gameDay.get(i));
		}
		Iterator<Game> iterator2 = list.iterator();
		while (iterator2.hasNext()) {
			Game game = (Game) iterator2.next();
			dao.add(game);
		}
		return new BaseExcution<>(BaseStateEnum.SUCCESS);
	}

	private Game packGame(String cupId, String homeId, String awayId) {
		Game g = new Game();
		g.setCupId(cupId);
		g.setGameId(IDUtils.getUUID());
		g.setTeamHome(homeId);
		g.setTeamAway(awayId);
		return g;
	}

	private void packList(List<Game> listAll, List<Group> list) {

		for (int i = 0; i < 3; i++) {
			Game packGame = packGame(list.get(i).getCupId(), list.get(i).getTeamId(),
					list.get((i == 2) ? 0 : (i + 1)).getTeamId());
			listAll.add(packGame);
		}
	}

	@Override
	@Deprecated
	public BaseExcution<Game> findAll(int pageIndex, int pageSize) {
		return null;
	}

}
