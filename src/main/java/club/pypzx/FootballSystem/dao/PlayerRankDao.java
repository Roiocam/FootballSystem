package club.pypzx.FootballSystem.dao;

import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import club.pypzx.FootballSystem.dao.jpa.PlayerRankRepository;
import club.pypzx.FootballSystem.dao.mybatis.PlayerRankMapper;
import club.pypzx.FootballSystem.datasource.DBIdentifier;
import club.pypzx.FootballSystem.dbmgr.EntityFactroy;
import club.pypzx.FootballSystem.entity.PlayerRank;
import club.pypzx.FootballSystem.entity.Page;
import club.pypzx.FootballSystem.enums.DBType;
import club.pypzx.FootballSystem.template.BaseDao;

@Repository
@Scope(value = "singleton")
public class PlayerRankDao implements BaseDao<PlayerRank> {
	@Autowired
	private PlayerRankMapper mapper;
	@Autowired
	private PlayerRankRepository repository;

	public static BaseDao<PlayerRank> instance() {
		return EntityFactroy.getBean(PlayerRankDao.class);
	}

	@Override
	public void add(PlayerRank obj) throws Exception {
		if (DBIdentifier.getDbType().equals(DBType.MY_BATIS)) {
			if (1 != mapper.insert(obj)) {
				throw new RuntimeException("新增球员排行失败");
			}
		} else if (DBIdentifier.getDbType().equals(DBType.JPA)) {
			repository.save(obj);
		}
	}

	@Override
	public void edit(PlayerRank obj) {
		if (DBIdentifier.getDbType().equals(DBType.MY_BATIS)) {
			if (1 != mapper.update(obj)) {
				throw new RuntimeException("更新球员排行失败");
			}
		} else if (DBIdentifier.getDbType().equals(DBType.JPA)) {
			repository.save(obj);
		}

	}

	@Override
	public void remove(String objId) {
		PlayerRank bean = EntityFactroy.getBean(PlayerRank.class);
		bean.setPlayerId(objId);
		if (DBIdentifier.getDbType().equals(DBType.MY_BATIS)) {
			if (1 != mapper.delete(bean)) {
				throw new RuntimeException("删除球员排行失败");
			}
		} else if (DBIdentifier.getDbType().equals(DBType.JPA)) {
			repository.delete(bean);
		}

	}

	@Override
	public PlayerRank findById(String objId) {
		PlayerRank selectByPrimaryKey = EntityFactroy.getBean(PlayerRank.class);
		selectByPrimaryKey.setPlayerId(objId);
		if (DBIdentifier.getDbType().equals(DBType.MY_BATIS)) {
			selectByPrimaryKey = mapper.selectPrimary(selectByPrimaryKey);
		} else if (DBIdentifier.getDbType().equals(DBType.JPA)) {
			selectByPrimaryKey = repository.findById(objId).orElse(null);
		}
		return selectByPrimaryKey;

	}

	@Override
	public PlayerRank findByCondition(PlayerRank obj) {
		if (DBIdentifier.getDbType().equals(DBType.MY_BATIS)) {
			return mapper.selectPrimary(obj);
		} else if (DBIdentifier.getDbType().equals(DBType.JPA)) {
			return repository.findOne(Example.of(obj)).orElse(null);
		}
		return null;
	}

	@Override
	public List<PlayerRank> findAllCondition(PlayerRank obj) {
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
			return mapper.selectCount(EntityFactroy.getBean(PlayerRank.class));
		} else {
			return (int) repository.count();
		}
	}

	@Override
	public int countExmaple(PlayerRank obj) {
		if (DBIdentifier.getDbType().equals(DBType.MY_BATIS)) {
			return mapper.selectCount(obj);
		} else {
			return (int) repository.count(Example.of(obj));
		}
	}

	@Override
	public List<PlayerRank> findAll(int pageIndex, int pageSize) {
		PlayerRank bean = EntityFactroy.getBean(PlayerRank.class);
		if (DBIdentifier.getDbType().equals(DBType.MY_BATIS)) {
			return mapper.selectRowBounds(bean, Page.getInstance(pageIndex, pageSize));
		} else {
			org.springframework.data.domain.Page<PlayerRank> findAll = repository
					.findAll(PageRequest.of(pageIndex - 1, pageSize));
			return findAll.getContent();
		}

	}

}
