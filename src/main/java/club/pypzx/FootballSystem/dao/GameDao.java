package club.pypzx.FootballSystem.dao;

import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import club.pypzx.FootballSystem.dao.jpa.GameRepository;
import club.pypzx.FootballSystem.dao.jpa.GameVoRepository;
import club.pypzx.FootballSystem.dao.mybatis.GameMapper;
import club.pypzx.FootballSystem.datasource.DBIdentifier;
import club.pypzx.FootballSystem.dbmgr.EntityFactroy;
import club.pypzx.FootballSystem.entity.Cup;
import club.pypzx.FootballSystem.entity.Game;
import club.pypzx.FootballSystem.entity.GameVo;
import club.pypzx.FootballSystem.entity.Page;
import club.pypzx.FootballSystem.enums.DBType;
import club.pypzx.FootballSystem.template.BaseDao;

@Repository
@Scope(value = "singleton")
public class GameDao implements BaseDao<Game> {
	@Autowired
	private GameMapper mapper;
	@Autowired
	private GameRepository repository;
	@Autowired
	private GameVoRepository voRepository;

	public static BaseDao<Game> instance() {
		return EntityFactroy.getBean(GameDao.class);
	}

	@Override
	public void add(Game obj) {
		if (DBIdentifier.getDbType().equals(DBType.MY_BATIS)) {
			if (1 != mapper.insert(obj)) {
				throw new RuntimeException("新增比赛失败");
			}
		} else if (DBIdentifier.getDbType().equals(DBType.JPA)) {
			repository.save(obj);
		}
	}

	@Override
	public void edit(Game obj) {
		if (DBIdentifier.getDbType().equals(DBType.MY_BATIS)) {
			if (1 != mapper.update(obj)) {
				throw new RuntimeException("更新比赛失败");
			}
		} else if (DBIdentifier.getDbType().equals(DBType.JPA)) {
			repository.save(obj);
		}

	}

	@Override
	public void remove(String objId) {
		Game bean = EntityFactroy.getBean(Game.class);
		bean.setGameId(objId);
		if (DBIdentifier.getDbType().equals(DBType.MY_BATIS)) {
			if (1 != mapper.delete(bean)) {
				throw new RuntimeException("删除比赛失败");
			}
		} else if (DBIdentifier.getDbType().equals(DBType.JPA)) {
			List<Game> findAll = repository.findAll(Example.of(bean));
			repository.deleteInBatch(findAll);
		}

	}

	@Override
	public Game findById(String objId) {
		Game selectByPrimaryKey = EntityFactroy.getBean(Game.class);
		selectByPrimaryKey.setGameId(objId);
		if (DBIdentifier.getDbType().equals(DBType.MY_BATIS)) {
			selectByPrimaryKey = mapper.selectPrimary(selectByPrimaryKey);
		} else if (DBIdentifier.getDbType().equals(DBType.JPA)) {
			selectByPrimaryKey = repository.findById(objId).orElse(null);
		}
		return selectByPrimaryKey;

	}

	@Override
	public Game findByCondition(Game obj) {
		if (DBIdentifier.getDbType().equals(DBType.MY_BATIS)) {
			return mapper.selectPrimary(obj);
		} else if (DBIdentifier.getDbType().equals(DBType.JPA)) {
			return repository.findOne(Example.of(obj)).orElse(null);
		}
		return null;
	}

	@Override
	public List<Game> findAllCondition(Game obj) {
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
			return mapper.selectCount(EntityFactroy.getBean(Game.class));
		} else {
			return (int) repository.count();
		}
	}

	@Override
	public int countExmaple(Game obj) {
		if (DBIdentifier.getDbType().equals(DBType.MY_BATIS)) {
			return mapper.selectCount(obj);
		} else {
			return (int) repository.count(Example.of(obj));
		}
	}

	@Override
	public List<Game> findAll(int pageIndex, int pageSize) {
		Game bean = EntityFactroy.getBean(Game.class);
		if (DBIdentifier.getDbType().equals(DBType.MY_BATIS)) {
			return mapper.selectRowBounds(bean, Page.getInstance(pageIndex, pageSize));
		} else {
			org.springframework.data.domain.Page<Game> findAll = repository
					.findAll(PageRequest.of(pageIndex - 1, pageSize));
			return findAll.getContent();
		}

	}

	public List<GameVo> findAll(String cupId, int pageIndex, int pageSize) {
		if (DBIdentifier.getDbType().equals(DBType.MY_BATIS)) {
			Game game = EntityFactroy.getBean(Game.class);
			game.setCupId(cupId);
			return mapper.selectGameByCup(cupId, Page.getInstance(pageIndex, pageSize));
		} else {
			GameVo gameVo = EntityFactroy.getBean(GameVo.class);
			Cup bean = EntityFactroy.getBean(Cup.class);
			bean.setCupId(cupId);
			gameVo.setCup(bean);
			org.springframework.data.domain.Page<GameVo> findAll = voRepository.findAll(Example.of(gameVo),
					PageRequest.of(pageIndex - 1, pageSize));
			return findAll.getContent();
		}
	}

}
