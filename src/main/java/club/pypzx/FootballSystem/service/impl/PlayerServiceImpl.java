package club.pypzx.FootballSystem.service.impl;

import java.util.Iterator;
import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import club.pypzx.FootballSystem.dao.jpa.PlayerInfoRepository;
import club.pypzx.FootballSystem.dao.jpa.PlayerRankRepository;
import club.pypzx.FootballSystem.dao.jpa.PlayerRepository;
import club.pypzx.FootballSystem.dao.jpa.PlayerVoRepository;
import club.pypzx.FootballSystem.dao.mybatis.PlayerInfoMapper;
import club.pypzx.FootballSystem.dao.mybatis.PlayerMapper;
import club.pypzx.FootballSystem.dao.mybatis.PlayerRankMapper;
import club.pypzx.FootballSystem.datasource.DBIdentifier;
import club.pypzx.FootballSystem.entity.Page;
import club.pypzx.FootballSystem.entity.Player;
import club.pypzx.FootballSystem.entity.PlayerInfo;
import club.pypzx.FootballSystem.entity.PlayerRank;
import club.pypzx.FootballSystem.entity.PlayerVo;
import club.pypzx.FootballSystem.entity.Team;
import club.pypzx.FootballSystem.enums.DBType;
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
	private PlayerMapper mapper;
	@Autowired
	private PlayerVoRepository voRepository;
	@Autowired
	private PlayerRepository repository;
	@Autowired
	private PlayerRankMapper rankMapper;
	@Autowired
	private PlayerRankRepository rankRepository;
	@Autowired
	private PlayerInfoMapper infoMapper;
	@Autowired
	private PlayerInfoRepository infoRepository;
	@Autowired
	private TeamService teamService;

	public Player packagePlayer(String teamId, String name, int num) throws Exception {
		Player obj = new Player();
		obj.setPlayerNum(num);
		obj.setPlayerId(IDUtils.getUUID());
		obj.setPlayerName(name);
		obj.setTeamId(teamId);
		return obj;
	}

	public Player packagePlayer(String id, String teamId, String name, int num) throws Exception {
		Player obj = new Player();
		obj.setPlayerId(id);
		obj.setPlayerNum(num);
		obj.setPlayerName(name);
		obj.setTeamId(teamId);
		return obj;
	}

	public PlayerInfo packagePlayerInfo(String id, String stuno, String depart, String tel) throws Exception {
		PlayerInfo obj = new PlayerInfo();
		obj.setPlayerId(id);
		obj.setPlayerStuno(stuno);
		obj.setPlayerDepart(depart);
		obj.setPlayerTel(tel);
		return obj;
	}

	@Override
	@Transactional
	public BaseExcution<Player> removeById(String objId) throws Exception {
		if (DBIdentifier.getDbType().equals(DBType.MY_BATIS)) {
			infoMapper.delete(new PlayerInfo(objId));
			rankMapper.delete(new PlayerRank(objId));
			if (1 != mapper.delete(new Player(objId))) {
				throw new Exception("删除球员失败");
			}
		} else if (DBIdentifier.getDbType().equals(DBType.JPA)) {
			infoRepository.delete(new PlayerInfo(objId));
			rankRepository.delete(new PlayerRank(objId));
			repository.delete(new Player(objId));
		}
		return new BaseExcution<>(BaseStateEnum.SUCCESS);
	}

	@Override
	public BaseExcution<Player> add(Player obj) {
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
	public BaseExcution<Player> edit(Player obj) {
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
	public BaseExcution<Player> findById(String objId) {
		Player selectByPrimaryKey = null;
		if (DBIdentifier.getDbType().equals(DBType.MY_BATIS)) {
			selectByPrimaryKey = mapper.selectByPrimary(objId);
		} else if (DBIdentifier.getDbType().equals(DBType.JPA)) {
			selectByPrimaryKey = repository.findById(objId).get();
		}
		if (selectByPrimaryKey == null) {
			return new BaseExcution<Player>(BaseStateEnum.QUERY_ERROR);
		}
		return new BaseExcution<Player>(BaseStateEnum.SUCCESS, selectByPrimaryKey);
	}

	@Override
	public BaseExcution<Player> findByCondition(Player obj) {
		List<Player> selectRowBounds = null;
		if (DBIdentifier.getDbType().equals(DBType.MY_BATIS)) {
			int selectCount = mapper.selectCount(obj);
			selectRowBounds = mapper.selectRowBounds(obj, new RowBounds(0, selectCount));
		} else if (DBIdentifier.getDbType().equals(DBType.JPA)) {
			selectRowBounds = repository.findAll(Example.of(obj));
		}
		if (selectRowBounds != null && selectRowBounds.size() > -1) {
			return new BaseExcution<Player>(BaseStateEnum.SUCCESS, selectRowBounds, selectRowBounds.size());
		}
		return new BaseExcution<Player>(BaseStateEnum.QUERY_ERROR);
	}

	@Override
	public BaseExcution<Player> findAll(int pageIndex, int pageSize) {
		List<Player> selectAll = null;
		int selectCount = 0;
		if (DBIdentifier.getDbType().equals(DBType.MY_BATIS)) {
			selectAll = mapper.selectRowBounds(new Player(), Page.getInstance(pageIndex, pageSize));
			selectCount = mapper.selectCount(new Player());
		} else if (DBIdentifier.getDbType().equals(DBType.JPA)) {
			org.springframework.data.domain.Page<Player> findAll = repository
					.findAll(PageRequest.of(pageIndex - 1, pageSize));
			selectAll = findAll.getContent();
			selectCount = (int) repository.count();
		}
		if (selectAll == null) {
			return new BaseExcution<Player>(BaseStateEnum.QUERY_ERROR);
		}
		return new BaseExcution<Player>(BaseStateEnum.SUCCESS, selectAll, selectCount);
	}

	@Override
	public BaseExcution<PlayerVo> findByIdMore(String id) {
		PlayerVo selectByPrimary = null;
		if (DBIdentifier.getDbType().equals(DBType.MY_BATIS)) {
			selectByPrimary = mapper.selectPrimaryVo(new Player(id));
		} else if (DBIdentifier.getDbType().equals(DBType.JPA)) {
			selectByPrimary = voRepository.findById(id).get();
		}
		if (selectByPrimary == null) {
			return new BaseExcution<PlayerVo>(BaseStateEnum.QUERY_ERROR);
		}
		return new BaseExcution<PlayerVo>(BaseStateEnum.SUCCESS, selectByPrimary);
	}

	@Override
	public BaseExcution<PlayerVo> findAllMore(Player example, int pageIndex, int pageSize) {
		List<PlayerVo> selectAllByPage = null;
		int selectCount = 0;
		if (DBIdentifier.getDbType().equals(DBType.MY_BATIS)) {
			selectAllByPage = mapper.selectMoreRowBounds(example, Page.getInstance(pageIndex, pageSize));
			selectCount = mapper.selectCount(new Player());
		} else if (DBIdentifier.getDbType().equals(DBType.JPA)) {
			org.springframework.data.domain.Page<PlayerVo> findAll = voRepository
					.findAll(PageRequest.of(pageIndex - 1, pageSize));
			selectAllByPage = findAll.getContent();
			selectCount = (int) repository.count();
		}
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
		Player temp = new Player();
		temp.setTeamId(teamId);
		int selectCount = 0;
		if (DBIdentifier.getDbType().equals(DBType.MY_BATIS)) {
			selectCount = mapper.selectCount(temp);
		} else if (DBIdentifier.getDbType().equals(DBType.JPA)) {
			selectCount = (int) repository.count(Example.of(temp));
		}
		if (selectCount >= 14) {
			return new BaseExcution<>(BaseStateEnum.TO_MANY_PLAYER);
		}
		temp.setPlayerNum(num);
		Player selectPrimary = null;
		if (DBIdentifier.getDbType().equals(DBType.MY_BATIS)) {
			selectPrimary = mapper.selectPrimary(temp);
		} else if (DBIdentifier.getDbType().equals(DBType.JPA)) {
			selectPrimary = repository.findOne(Example.of(temp)).get();
		}
		if (null != selectPrimary) {
			return new BaseExcution<Player>(BaseStateEnum.SAME_PLAYERNUM);
		}
		if (BaseStateEnum.SUCCESS.getState() != add(packagePlayer).getState()) {
			throw new Exception("加入球队失败");
		}
		if (DBIdentifier.getDbType().equals(DBType.MY_BATIS)) {
			if (1 != infoMapper.insert(packagePlayerInfo)) {
				throw new Exception("加入球队失败,请检查学号");
			}
		} else if (DBIdentifier.getDbType().equals(DBType.JPA)) {
			infoRepository.save(packagePlayerInfo);
		}
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
		if (DBIdentifier.getDbType().equals(DBType.MY_BATIS)) {
			if (1 != infoMapper.update(packagePlayerInfo)) {
				throw new Exception("修改球员失败");
			}
		} else if (DBIdentifier.getDbType().equals(DBType.JPA)) {
			infoRepository.save(packagePlayerInfo);
		}

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
		int selectCount = 0;
		if (DBIdentifier.getDbType().equals(DBType.MY_BATIS)) {
			selectCount = mapper.selectCount(new Player(playerId));
		} else if (DBIdentifier.getDbType().equals(DBType.JPA)) {
			selectCount = (int) repository.count(Example.of(new Player(playerId)));
		}
		if (0 != selectCount) {
			return new BaseExcution<>(BaseStateEnum.FAIL);
		}
		return new BaseExcution<>(BaseStateEnum.SUCCESS);
	}

}
