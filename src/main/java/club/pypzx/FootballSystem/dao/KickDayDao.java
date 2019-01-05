package club.pypzx.FootballSystem.dao;

import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import club.pypzx.FootballSystem.dao.jpa.KickDayRepository;
import club.pypzx.FootballSystem.dao.mybatis.KickDayMapper;
import club.pypzx.FootballSystem.datasource.DBIdentifier;
import club.pypzx.FootballSystem.dbmgr.EntityFactroy;
import club.pypzx.FootballSystem.entity.KickDay;
import club.pypzx.FootballSystem.entity.Page;
import club.pypzx.FootballSystem.enums.DBType;
import club.pypzx.FootballSystem.template.BaseDao;

@Repository
@Scope(value = "singleton")
public class KickDayDao implements BaseDao<KickDay> {
	@Autowired
	private KickDayMapper mapper;
	@Autowired
	private KickDayRepository repository;

	public static BaseDao<KickDay> instance() {
		return EntityFactroy.getBean(KickDayDao.class);
	}

	@Override
	public void add(KickDay obj) {
		if (DBIdentifier.getDbType().equals(DBType.MY_BATIS)) {
			if (1 != mapper.insert(obj)) {
				throw new RuntimeException("新增失败");
			}
		} else if (DBIdentifier.getDbType().equals(DBType.JPA)) {
			repository.save(obj);
		}
	}

	@Override
	public void edit(KickDay obj) {
		if (DBIdentifier.getDbType().equals(DBType.MY_BATIS)) {
			if (1 != mapper.update(obj)) {
				throw new RuntimeException("更新失败");
			}
		} else if (DBIdentifier.getDbType().equals(DBType.JPA)) {
			repository.save(obj);
		}

	}

	@Override
	@Deprecated
	public void remove(String objId) {
	}

	@Override
	@Deprecated
	public KickDay findById(String objId) {
		return null;
	}

	@Override
	public KickDay findByCondition(KickDay obj) {
		if (DBIdentifier.getDbType().equals(DBType.MY_BATIS)) {
			return mapper.selectPrimary(obj);
		} else if (DBIdentifier.getDbType().equals(DBType.JPA)) {
			return repository.findOne(Example.of(obj)).orElse(null);
		}
		return null;
	}

	@Override
	public List<KickDay> findAllCondition(KickDay obj) {
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
			return mapper.selectCount(EntityFactroy.getBean(KickDay.class));
		} else {
			return (int) repository.count();
		}
	}

	@Override
	public int countExmaple(KickDay obj) {
		if (DBIdentifier.getDbType().equals(DBType.MY_BATIS)) {
			return mapper.selectCount(obj);
		} else {
			return (int) repository.count(Example.of(obj));
		}
	}

	@Override
	public List<KickDay> findAll(int pageIndex, int pageSize) {
		KickDay bean = EntityFactroy.getBean(KickDay.class);
		if (DBIdentifier.getDbType().equals(DBType.MY_BATIS)) {
			return mapper.selectRowBounds(bean, Page.getInstance(pageIndex, pageSize));
		} else {
			org.springframework.data.domain.Page<KickDay> findAll = repository
					.findAll(PageRequest.of(pageIndex - 1, pageSize));
			return findAll.getContent();
		}

	}

	public KickDay queryKickToday() {
		if (DBIdentifier.getDbType().equals(DBType.MY_BATIS)) {
			return mapper.queryKickToday();
		} else {
			return repository.queryKickToday();
			
		}
	}

}
