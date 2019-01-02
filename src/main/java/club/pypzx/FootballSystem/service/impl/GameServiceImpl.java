package club.pypzx.FootballSystem.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import club.pypzx.FootballSystem.dao.mybatis.CupMapper;
import club.pypzx.FootballSystem.dao.mybatis.GameMapper;
import club.pypzx.FootballSystem.dao.mybatis.GroupMapper;
import club.pypzx.FootballSystem.dto.GameVo;
import club.pypzx.FootballSystem.entity.Cup;
import club.pypzx.FootballSystem.entity.Game;
import club.pypzx.FootballSystem.entity.Group;
import club.pypzx.FootballSystem.entity.Page;
import club.pypzx.FootballSystem.service.GameService;
import club.pypzx.FootballSystem.template.BaseExcution;
import club.pypzx.FootballSystem.template.BaseStateEnum;
import club.pypzx.FootballSystem.utils.GameDayUtils;
import club.pypzx.FootballSystem.utils.IDUtils;

@Service
public class GameServiceImpl implements GameService {
	@Autowired
	private GroupMapper groupMapper;
	@Autowired
	private GameMapper mapper;
	@Autowired
	private CupMapper cupMapper;

	@Override
	public BaseExcution<Game> add(Game obj) {
		if (obj == null) {
			return new BaseExcution<>(BaseStateEnum.EMPTY);
		}
		if (1 != mapper.insert(obj)) {
			return new BaseExcution<>(BaseStateEnum.FAIL);
		}
		return new BaseExcution<>(BaseStateEnum.SUCCESS);
	}

	@Override
	public BaseExcution<Game> edit(Game obj) throws Exception {
		if (obj == null) {
			return new BaseExcution<>(BaseStateEnum.EMPTY);
		}
		if (1 != mapper.update(obj)) {
			return new BaseExcution<>(BaseStateEnum.FAIL);
		}
		return new BaseExcution<>(BaseStateEnum.SUCCESS);
	}

	@Override
	public BaseExcution<Game> findById(String objId) {
		Game game = new Game();
		game.setCupId(objId);
		Game selectByPrimaryKey = mapper.selectPrimary(game);
		if (selectByPrimaryKey == null) {
			return new BaseExcution<Game>(BaseStateEnum.QUERY_ERROR);
		}
		return new BaseExcution<Game>(BaseStateEnum.SUCCESS, selectByPrimaryKey);
	}

	@Override
	public BaseExcution<Game> findByCondition(Game obj) {
		int selectCount = mapper.selectCount(obj);
		List<Game> selectRowBounds = mapper.selectRowBounds(obj, new RowBounds(0, selectCount));
		if (selectRowBounds != null && selectRowBounds.size() > -1) {
			return new BaseExcution<Game>(BaseStateEnum.SUCCESS, selectRowBounds, selectRowBounds.size());
		}
		return new BaseExcution<Game>(BaseStateEnum.QUERY_ERROR);
	}

	@Override
	public BaseExcution<GameVo> findAll(String cupId, int pageIndex, int pageSize) {
		List<GameVo> selectGameByCup = mapper.selectGameByCup(cupId, Page.getInstance(pageIndex, pageSize));
		if (selectGameByCup == null) {
			return new BaseExcution<GameVo>(BaseStateEnum.QUERY_ERROR);
		}
		int selectCount = mapper.selectCount(new Game());
		return new BaseExcution<GameVo>(BaseStateEnum.SUCCESS, selectGameByCup, selectCount);
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
		Group group = new Group();
		group.setCupId(cupId);
		if (0 == mapper.delete(record) || 0 == groupMapper.delete(group)) {
			throw new RuntimeException("删除赛程表和分组时出错");
		}
		Cup cup = new Cup();
		cup.setCupId(cupId);
		cup.setIsGroup(0);
		if (1 != cupMapper.update(cup)) {
			throw new Exception("更新赛事分组状态失败");
		}
		return new BaseExcution<>(BaseStateEnum.SUCCESS);
	}

	@Override
	@Transactional
	public BaseExcution<Game> randomGameByGroup(String cupId) throws Exception {
		int selectCount = groupMapper.selectCount(new Group(cupId));
		List<Group> select = groupMapper.selectRowBounds(new Group(cupId), new RowBounds(0, selectCount));
		Iterator<Group> iterator = select.iterator();
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
			if (1 != mapper.insert(game)) {
				throw new RuntimeException("创建赛程失败");
			}
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
			Game packGame = packGame(list.get(i).getCupId(), list.get(i).getTeam_id(),
					list.get((i == 2) ? 0 : (i + 1)).getTeam_id());
			listAll.add(packGame);
		}
	}

	@Override
	@Deprecated
	public BaseExcution<Game> findAll(int pageIndex, int pageSize) {
		return null;
	}

}
