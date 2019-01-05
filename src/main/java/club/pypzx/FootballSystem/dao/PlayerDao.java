package club.pypzx.FootballSystem.dao;

import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import club.pypzx.FootballSystem.dao.jpa.PlayerRepository;
import club.pypzx.FootballSystem.dao.jpa.PlayerVoRepository;
import club.pypzx.FootballSystem.dao.mybatis.PlayerMapper;
import club.pypzx.FootballSystem.datasource.DBIdentifier;
import club.pypzx.FootballSystem.dbmgr.EntityFactroy;
import club.pypzx.FootballSystem.entity.Page;
import club.pypzx.FootballSystem.entity.Player;
import club.pypzx.FootballSystem.entity.PlayerVo;
import club.pypzx.FootballSystem.entity.Team;
import club.pypzx.FootballSystem.enums.DBType;
import club.pypzx.FootballSystem.template.BaseDao;

@Repository
@Scope(value = "singleton")
public class PlayerDao implements BaseDao<Player> {
	@Autowired
	private PlayerMapper mapper;
	@Autowired
	private PlayerVoRepository voRepository;
	@Autowired
	private PlayerRepository repository;

	public static BaseDao<Player> instance() {
		return EntityFactroy.getBean(PlayerDao.class);
	}

	@Override
	public void add(Player obj) {
		if (DBIdentifier.getDbType().equals(DBType.MY_BATIS)) {
			if (1 != mapper.insert(obj)) {
				throw new RuntimeException("新增球员失败");
			}
		} else if (DBIdentifier.getDbType().equals(DBType.JPA)) {
			repository.save(obj);
		}
	}

	@Override
	public void edit(Player obj) {
		if (DBIdentifier.getDbType().equals(DBType.MY_BATIS)) {
			if (1 != mapper.update(obj)) {
				throw new RuntimeException("更新球员失败");
			}
		} else if (DBIdentifier.getDbType().equals(DBType.JPA)) {
			repository.save(obj);
		}

	}

	@Override
	public void remove(String objId) {
		Player bean = EntityFactroy.getBean(Player.class);
		bean.setPlayerId(objId);
		if (DBIdentifier.getDbType().equals(DBType.MY_BATIS)) {
			if (1 != mapper.delete(bean)) {
				throw new RuntimeException("删除球员失败");
			}
		} else if (DBIdentifier.getDbType().equals(DBType.JPA)) {
			repository.delete(bean);
		}

	}

	@Override
	public Player findById(String objId) {
		Player selectByPrimaryKey = null;
		if (DBIdentifier.getDbType().equals(DBType.MY_BATIS)) {
			selectByPrimaryKey = mapper.selectByPrimary(objId);
		} else if (DBIdentifier.getDbType().equals(DBType.JPA)) {
			selectByPrimaryKey = repository.findById(objId).orElse(null);
		}
		return selectByPrimaryKey;

	}

	@Override
	public Player findByCondition(Player obj) {
		if (DBIdentifier.getDbType().equals(DBType.MY_BATIS)) {
			return mapper.selectPrimary(obj);
		} else if (DBIdentifier.getDbType().equals(DBType.JPA)) {
			return repository.findOne(Example.of(obj)).orElse(null);
		}
		return null;
	}

	@Override
	public List<Player> findAllCondition(Player obj) {
		if (DBIdentifier.getDbType().equals(DBType.MY_BATIS)) {
			int selectCount = mapper.selectCount(obj);
			return mapper.selectRowBounds(obj, new RowBounds(0, selectCount));
		} else {
			return repository.findAll(Example.of(obj));
		}
	}

	@Override
	public int count() {
		if (DBIdentifier.getDbType().equals(DBType.MY_BATIS)) {
			return mapper.selectCount(EntityFactroy.getBean(Player.class));
		} else {
			return (int) repository.count();
		}
	}

	@Override
	public int countExmaple(Player obj) {
		if (DBIdentifier.getDbType().equals(DBType.MY_BATIS)) {
			return mapper.selectCount(obj);
		} else {
			return (int) repository.count(Example.of(obj));
		}
	}

	@Override
	public List<Player> findAll(int pageIndex, int pageSize) {
		Player bean = EntityFactroy.getBean(Player.class);
		if (DBIdentifier.getDbType().equals(DBType.MY_BATIS)) {
			return mapper.selectRowBounds(bean, Page.getInstance(pageIndex, pageSize));
		} else {
			org.springframework.data.domain.Page<Player> findAll = repository
					.findAll(PageRequest.of(pageIndex - 1, pageSize));
			return findAll.getContent();
		}

	}

	public PlayerVo findIdMore(String objId) {
		if (DBIdentifier.getDbType().equals(DBType.MY_BATIS)) {
			Player p = EntityFactroy.getBean(Player.class);
			p.setPlayerId(objId);
			return mapper.selectPrimaryVo(p);
		} else if (DBIdentifier.getDbType().equals(DBType.JPA)) {
			return voRepository.findById(objId).orElse(null);
		} else {
			return null;
		}
	}

	public List<PlayerVo> findAllMore(Player example, int pageIndex, int pageSize) {
		if (DBIdentifier.getDbType().equals(DBType.MY_BATIS)) {
			return mapper.selectMoreRowBounds(example, Page.getInstance(pageIndex, pageSize));
		} else if (DBIdentifier.getDbType().equals(DBType.JPA)) {
			PlayerVo bean = EntityFactroy.getBean(PlayerVo.class);
			Team team = EntityFactroy.getBean(Team.class);
			team.setTeamId(example.getTeamId());
			bean.setTeam(team);
			bean.setPlayerName(example.getPlayerName());
			org.springframework.data.domain.Page<PlayerVo> findAll = voRepository.findAll(Example.of(bean),
					PageRequest.of(pageIndex - 1, pageSize));
			return findAll.getContent();
		}
		return null;
	}

}
