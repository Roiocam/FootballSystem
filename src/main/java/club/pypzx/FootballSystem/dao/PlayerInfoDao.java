package club.pypzx.FootballSystem.dao;

import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import club.pypzx.FootballSystem.dao.jpa.PlayerInfoRepository;
import club.pypzx.FootballSystem.dao.mybatis.PlayerInfoMapper;
import club.pypzx.FootballSystem.datasource.DBIdentifier;
import club.pypzx.FootballSystem.dbmgr.EntityFactroy;
import club.pypzx.FootballSystem.entity.PlayerInfo;
import club.pypzx.FootballSystem.entity.Page;
import club.pypzx.FootballSystem.enums.DBType;
import club.pypzx.FootballSystem.template.BaseDao;

@Repository
@Scope(value = "singleton")
public class PlayerInfoDao implements BaseDao<PlayerInfo> {
	@Autowired
	private PlayerInfoMapper mapper;
	@Autowired
	private PlayerInfoRepository repository;

	public static BaseDao<PlayerInfo> instance() {
		return EntityFactroy.getBean(PlayerInfoDao.class);
	}

	@Override
	public void add(PlayerInfo obj) throws Exception {
		if (DBIdentifier.getDbType().equals(DBType.MY_BATIS)) {
			if (1 != mapper.insert(obj)) {
				throw new RuntimeException("新增球员信息失败");
			}
		} else if (DBIdentifier.getDbType().equals(DBType.JPA)) {
			repository.save(obj);
		}
	}

	@Override
	public void edit(PlayerInfo obj) {
		if (DBIdentifier.getDbType().equals(DBType.MY_BATIS)) {
			if (1 != mapper.update(obj)) {
				throw new RuntimeException("更新球员信息失败");
			}
		} else if (DBIdentifier.getDbType().equals(DBType.JPA)) {
			repository.save(obj);
		}

	}

	@Override
	public void remove(String objId) {
		PlayerInfo bean = EntityFactroy.getBean(PlayerInfo.class);
		bean.setPlayerId(objId);
		if (DBIdentifier.getDbType().equals(DBType.MY_BATIS)) {
			if (1 != mapper.delete(bean)) {
				throw new RuntimeException("删除球员信息失败");
			}
		} else if (DBIdentifier.getDbType().equals(DBType.JPA)) {
			repository.delete(bean);
		}

	}

	@Override
	public PlayerInfo findById(String objId) {
		PlayerInfo selectByPrimaryKey = EntityFactroy.getBean(PlayerInfo.class);
		selectByPrimaryKey.setPlayerId(objId);
		if (DBIdentifier.getDbType().equals(DBType.MY_BATIS)) {
			selectByPrimaryKey = mapper.selectPrimary(selectByPrimaryKey);
		} else if (DBIdentifier.getDbType().equals(DBType.JPA)) {
			selectByPrimaryKey = repository.findById(objId).orElse(null);
		}
		return selectByPrimaryKey;

	}

	@Override
	public PlayerInfo findByCondition(PlayerInfo obj) {
		if (DBIdentifier.getDbType().equals(DBType.MY_BATIS)) {
			return mapper.selectPrimary(obj);
		} else if (DBIdentifier.getDbType().equals(DBType.JPA)) {
			return repository.findOne(Example.of(obj)).orElse(null);
		}
		return null;
	}

	@Override
	public List<PlayerInfo> findAllCondition(PlayerInfo obj) {
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
			return mapper.selectCount(EntityFactroy.getBean(PlayerInfo.class));
		} else {
			return (int) repository.count();
		}
	}

	@Override
	public int countExmaple(PlayerInfo obj) {
		if (DBIdentifier.getDbType().equals(DBType.MY_BATIS)) {
			return mapper.selectCount(obj);
		} else {
			return (int) repository.count(Example.of(obj));
		}
	}

	@Override
	public List<PlayerInfo> findAll(int pageIndex, int pageSize) {
		PlayerInfo bean = EntityFactroy.getBean(PlayerInfo.class);
		if (DBIdentifier.getDbType().equals(DBType.MY_BATIS)) {
			return mapper.selectRowBounds(bean, Page.getInstance(pageIndex, pageSize));
		} else {
			org.springframework.data.domain.Page<PlayerInfo> findAll = repository
					.findAll(PageRequest.of(pageIndex - 1, pageSize));
			return findAll.getContent();
		}

	}

}
