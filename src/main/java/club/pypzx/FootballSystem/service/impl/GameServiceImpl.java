package club.pypzx.FootballSystem.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import club.pypzx.FootballSystem.dao.jpa.GameRepository;
import club.pypzx.FootballSystem.dao.jpa.GameVoRepository;
import club.pypzx.FootballSystem.dao.mybatis.GameMapper;
import club.pypzx.FootballSystem.datasource.DBIdentifier;
import club.pypzx.FootballSystem.entity.Cup;
import club.pypzx.FootballSystem.entity.Game;
import club.pypzx.FootballSystem.entity.GameVo;
import club.pypzx.FootballSystem.entity.Group;
import club.pypzx.FootballSystem.entity.Page;
import club.pypzx.FootballSystem.enums.DBType;
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
	private GameMapper mapper;
	@Autowired
	private GameRepository repository;
	@Autowired
	private GameVoRepository gameVoRepository;
	@Autowired
	private CupService cupService;

	@Override
	public BaseExcution<Game> add(Game obj) {
		if (obj == null) {
			return new BaseExcution<>(BaseStateEnum.EMPTY);
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
	public BaseExcution<Game> edit(Game obj) throws Exception {
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
	public BaseExcution<Game> findById(String objId) {
		Game game = new Game();
		game.setCupId(objId);
		Game findById = null;
		if (DBIdentifier.getDbType().equals(DBType.MY_BATIS)) {
			findById = mapper.selectPrimary(game);
		} else if (DBIdentifier.getDbType().equals(DBType.JPA)) {
			findById = repository.findById(objId).get();
		}
		if (findById == null) {
			return new BaseExcution<Game>(BaseStateEnum.QUERY_ERROR);
		}
		return new BaseExcution<Game>(BaseStateEnum.SUCCESS, findById);
	}

	@Override
	public BaseExcution<Game> findByCondition(Game obj) {
		List<Game> selectRowBounds = null;
		if (DBIdentifier.getDbType().equals(DBType.MY_BATIS)) {
			int selectCount = mapper.selectCount(obj);
			selectRowBounds = mapper.selectRowBounds(obj, new RowBounds(0, selectCount));
		} else if (DBIdentifier.getDbType().equals(DBType.JPA)) {
			selectRowBounds = repository.findAll(Example.of(obj));
		}
		if (selectRowBounds != null && selectRowBounds.size() > -1) {
			return new BaseExcution<Game>(BaseStateEnum.SUCCESS, selectRowBounds, selectRowBounds.size());
		}
		return new BaseExcution<Game>(BaseStateEnum.QUERY_ERROR);
	}

	@Override
	public BaseExcution<GameVo> findAll(String cupId, int pageIndex, int pageSize) {
		List<GameVo> selectAll = null;
		int selectCount = 0;
		if (DBIdentifier.getDbType().equals(DBType.MY_BATIS)) {
			Game game = new Game();
			game.setCupId(cupId);
			selectAll = mapper.selectGameByCup(cupId, Page.getInstance(pageIndex, pageSize));
			selectCount = mapper.selectCount(game);
		} else if (DBIdentifier.getDbType().equals(DBType.JPA)) {
			GameVo gameVo = new GameVo(cupId);
			org.springframework.data.domain.Page<GameVo> findAll = gameVoRepository.findAll(Example.of(gameVo),
					PageRequest.of(pageIndex - 1, pageSize));
			selectAll = findAll.getContent();
			selectCount = (int) gameVoRepository.count();
		}
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
		Group group = new Group();
		group.setCupId(cupId);
		if (DBIdentifier.getDbType().equals(DBType.MY_BATIS)) {
			if (0 == mapper.delete(record)
					|| BaseStateEnum.SUCCESS.getState() != groupService.removeById(cupId).getState()) {
				throw new RuntimeException("删除赛程表和分组时出错");
			}
		} else if (DBIdentifier.getDbType().equals(DBType.JPA)) {
			repository.delete(record);
		}
		Cup cup = new Cup();
		cup.setCupId(cupId);
		cup.setIsGroup(0);
		if (BaseStateEnum.SUCCESS.getState() != cupService.edit(cup).getState()) {
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
			if (DBIdentifier.getDbType().equals(DBType.MY_BATIS)) {
				if (1 != mapper.insert(game)) {
					throw new RuntimeException("创建赛程失败");
				}
			} else if (DBIdentifier.getDbType().equals(DBType.JPA)) {
				repository.save(game);
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
