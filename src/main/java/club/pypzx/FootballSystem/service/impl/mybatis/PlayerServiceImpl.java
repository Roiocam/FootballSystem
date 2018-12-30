package club.pypzx.FootballSystem.service.impl.mybatis;

import java.util.Iterator;
import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import club.pypzx.FootballSystem.dao.mybatis.PlayerInfoMapper;
import club.pypzx.FootballSystem.dao.mybatis.PlayerMapper;
import club.pypzx.FootballSystem.dao.mybatis.PlayerRankMapper;
import club.pypzx.FootballSystem.dao.mybatis.TeamMapper;
import club.pypzx.FootballSystem.dto.PlayerVo;
import club.pypzx.FootballSystem.entity.Page;
import club.pypzx.FootballSystem.entity.Player;
import club.pypzx.FootballSystem.entity.PlayerInfo;
import club.pypzx.FootballSystem.entity.PlayerRank;
import club.pypzx.FootballSystem.entity.Team;
import club.pypzx.FootballSystem.service.PlayerService;
import club.pypzx.FootballSystem.template.BaseExcution;
import club.pypzx.FootballSystem.template.BaseStateEnum;
import club.pypzx.FootballSystem.utils.IDUtils;
import club.pypzx.FootballSystem.utils.ParamUtils;

@Service
public class PlayerServiceImpl implements PlayerService {

	@Autowired
	private PlayerMapper mapper;
	@Autowired
	private PlayerRankMapper rankMapper;
	@Autowired
	private PlayerInfoMapper infoMapper;
	@Autowired
	private TeamMapper teamMapper;

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
	public BaseExcution<Player> deleteObjByPrimaryKey(String objId) throws Exception {
		infoMapper.delete(new PlayerInfo(objId));
		rankMapper.delete(new PlayerRank(objId));
		if (1 != mapper.delete(new Player(objId))) {
			throw new Exception("删除球员失败");
		}
		return new BaseExcution<>(BaseStateEnum.SUCCESS);
	}

	@Override
	@Deprecated
	public BaseExcution<Player> insertObj(Player obj) {
		if (obj == null) {
			return new BaseExcution<>(BaseStateEnum.EMPTY);
		}
		if (1 != mapper.insert(obj)) {
			return new BaseExcution<>(BaseStateEnum.FAIL);
		}
		return new BaseExcution<>(BaseStateEnum.SUCCESS);
	}

	@Override
	@Deprecated
	public BaseExcution<Player> updateObjByPrimaryKey(Player obj) {
		if (obj == null) {
			return new BaseExcution<>(BaseStateEnum.EMPTY);
		}
		if (1 != mapper.updateByPrimaryKey(obj)) {
			return new BaseExcution<>(BaseStateEnum.FAIL);
		}
		return new BaseExcution<>(BaseStateEnum.SUCCESS);
	}

	@Override
	public BaseExcution<Player> queryObjOneByPrimaryKey(String objId) {
		Player selectByPrimaryKey = mapper.selectByPrimaryKey(objId);
		if (selectByPrimaryKey == null) {
			return new BaseExcution<Player>(BaseStateEnum.QUERY_ERROR);
		}
		return new BaseExcution<Player>(BaseStateEnum.SUCCESS, selectByPrimaryKey);
	}

	@Override
	public BaseExcution<Player> queryObjOne(Player obj) {
		Player selectOne = mapper.selectOne(obj);
		if (selectOne == null) {
			return new BaseExcution<Player>(BaseStateEnum.QUERY_ERROR);
		}
		return new BaseExcution<Player>(BaseStateEnum.SUCCESS, selectOne);
	}

	@Override
	public BaseExcution<Player> queryAll(int pageIndex, int pageSize) {

		List<Player> selectAll = mapper.selectByRowBounds(null, new RowBounds((pageIndex - 1) * pageSize, pageSize));
		if (selectAll == null) {
			return new BaseExcution<Player>(BaseStateEnum.QUERY_ERROR);
		}
		int selectCount = mapper.selectCount(null);
		return new BaseExcution<Player>(BaseStateEnum.SUCCESS, selectAll,selectCount);
	}

	@Override
	public BaseExcution<PlayerVo> selectByPrimary(String id) {
		PlayerVo selectByPrimary = mapper.selectByPrimary(id);
		if (selectByPrimary == null) {
			return new BaseExcution<PlayerVo>(BaseStateEnum.QUERY_ERROR);
		}
		return new BaseExcution<PlayerVo>(BaseStateEnum.SUCCESS, selectByPrimary);
	}

	@Override
	public BaseExcution<PlayerVo> queryAllByPage(Player example, int pageIndex, int pageSize) {

		List<PlayerVo> selectAllByPage = mapper.selectAllByPage(example, Page.getInstance(pageIndex, pageSize));
		if (selectAllByPage == null) {
			return new BaseExcution<PlayerVo>(BaseStateEnum.QUERY_ERROR);
		}
		int selectCount = mapper.selectCount(example);
		return new BaseExcution<PlayerVo>(BaseStateEnum.SUCCESS, selectAllByPage,selectCount);
	}

	@Override
	@Transactional
	public BaseExcution<Player> insertObject(String teamId, String name, int num, String stuno, String depart,
			String tel) throws Exception {
		Player packagePlayer = null;
		PlayerInfo packagePlayerInfo = null;
		try {
			packagePlayer = packagePlayer(teamId, name, num);
			packagePlayerInfo = packagePlayerInfo(packagePlayer.getPlayerId(), stuno, depart, tel);
		} catch (Exception e) {
			return new BaseExcution<Player>(BaseStateEnum.EMPTY);
		}
		//球队人数限制
		Player temp=new Player();
		temp.setTeamId(teamId);
		int selectCount = mapper.selectCount(temp);
		if(selectCount>=14) {
			return new BaseExcution<>(BaseStateEnum.TO_MANY_PLAYER);
		}
		
		temp.setPlayerNum(num);
		if(null!=mapper.selectOne(temp)) {
			return new BaseExcution<Player>(BaseStateEnum.SAME_PLAYERNUM);
		}
		if (1 != mapper.insert(packagePlayer)) {
			throw new Exception("加入球队失败");
		}
		if (1 != infoMapper.insert(packagePlayerInfo)) {
			throw new Exception("加入球队失败,请检查学号");
		}

		return new BaseExcution<>(BaseStateEnum.SUCCESS, packagePlayer);
	}

	@Override
	@Transactional
	public BaseExcution<Player> updateObject(String id, String teamId, String name, int num, String stuno,
			String depart, String tel) throws Exception {
		Player packagePlayer = null;
		PlayerInfo packagePlayerInfo = null;
		try {
			packagePlayer = packagePlayer(id, teamId, name, num);
			packagePlayerInfo = packagePlayerInfo(packagePlayer.getPlayerId(), stuno, depart, tel);
		} catch (Exception e) {
			return new BaseExcution<Player>(BaseStateEnum.FAIL);
		}
		if (1 != mapper.updateByPrimaryKey(packagePlayer)) {
			throw new Exception("修改球员失败");
		}
		if (1 != infoMapper.updateByPrimaryKey(packagePlayerInfo)) {
			throw new Exception("修改球员失败");
		}
		return new BaseExcution<>(BaseStateEnum.SUCCESS);
	}

	@Override
	public boolean checkValidCode(String validCode, String teamId) {

		Team selectOne = teamMapper.selectOne(new Team(teamId));
		if (selectOne == null | ParamUtils.emptyString(selectOne.getVaildCode())) {
			return false;
		}
		if (!validCode.equals(selectOne.getVaildCode())) {
			return false;
		}
		return true;
	}

	@Override
	public BaseExcution<Player> deleteObjectList(List<String> list) throws Exception {
		Iterator<?> iterator = list.iterator();
		if (iterator == null) {
			return new BaseExcution<>(BaseStateEnum.FAIL);
		}
		while (iterator.hasNext()) {
			deleteObjByPrimaryKey((String) iterator.next());
		}
		return new BaseExcution<>(BaseStateEnum.SUCCESS);
	}

	@Override
	public BaseExcution<Player> checkValidId(String playerId) {
		if (0 != mapper.selectCountByPrimary(playerId)) {
			return new BaseExcution<>(BaseStateEnum.FAIL);
		}
		return new BaseExcution<>(BaseStateEnum.SUCCESS);
	}

}
