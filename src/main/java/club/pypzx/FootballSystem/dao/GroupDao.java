package club.pypzx.FootballSystem.dao;

import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import club.pypzx.FootballSystem.dao.jpa.GroupRepository;
import club.pypzx.FootballSystem.dao.mybatis.GroupMapper;
import club.pypzx.FootballSystem.datasource.DBIdentifier;
import club.pypzx.FootballSystem.dbmgr.EntityFactroy;
import club.pypzx.FootballSystem.entity.Group;
import club.pypzx.FootballSystem.entity.GroupVo;
import club.pypzx.FootballSystem.entity.Page;
import club.pypzx.FootballSystem.enums.DBType;
import club.pypzx.FootballSystem.template.BaseDao;

@Repository
@Scope(value = "singleton")
public class GroupDao implements BaseDao<Group> {
	@Autowired
	private GroupMapper mapper;
	@Autowired
	private GroupRepository repository;

	public static BaseDao<Group> instance() {
		return EntityFactroy.getBean(GroupDao.class);
	}

	@Override
	public void add(Group obj) {
		if (DBIdentifier.getDbType().equals(DBType.MY_BATIS)) {
			if (1 != mapper.insert(obj)) {
				throw new RuntimeException("新增分组失败");
			}
		} else if (DBIdentifier.getDbType().equals(DBType.JPA)) {
			repository.save(obj);
		}
	}

	@Override
	public void edit(Group obj) {
		if (DBIdentifier.getDbType().equals(DBType.MY_BATIS)) {
			if (1 != mapper.update(obj)) {
				throw new RuntimeException("更新分组失败");
			}
		} else if (DBIdentifier.getDbType().equals(DBType.JPA)) {
			repository.save(obj);
		}

	}

	@Override
	public void remove(String objId) throws Exception {
		Group bean = EntityFactroy.getBean(Group.class);
		bean.setCupId(objId);
		if (DBIdentifier.getDbType().equals(DBType.MY_BATIS)) {
			if (0 == mapper.delete(bean)) {
				throw new RuntimeException("删除赛程表和分组时出错");
			}
		} else if (DBIdentifier.getDbType().equals(DBType.JPA)) {
			List<Group> findAll = repository.findAll(Example.of(bean));
			repository.deleteInBatch(findAll);
		}

	}

	@Override
	@Deprecated
	public Group findById(String objId) {
		Group bean = EntityFactroy.getBean(Group.class);
		bean.setCupId(objId);
		if (DBIdentifier.getDbType().equals(DBType.MY_BATIS)) {
			return mapper.selectPrimary(bean);
		} else {
			return repository.findById(objId).orElse(null);
		}

	}

	@Override
	public Group findByCondition(Group obj) {
		if (DBIdentifier.getDbType().equals(DBType.MY_BATIS)) {
			return mapper.selectPrimary(obj);
		} else if (DBIdentifier.getDbType().equals(DBType.JPA)) {
			return repository.findOne(Example.of(obj)).orElse(null);
		}
		return null;
	}

	@Override
	public List<Group> findAllCondition(Group obj) {
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
			return mapper.selectCount(EntityFactroy.getBean(Group.class));
		} else {
			return (int) repository.count();
		}
	}

	@Override
	public int countExmaple(Group obj) {
		if (DBIdentifier.getDbType().equals(DBType.MY_BATIS)) {
			return mapper.selectCount(obj);
		} else {
			return (int) repository.count(Example.of(obj));
		}
	}

	@Override
	public List<Group> findAll(int pageIndex, int pageSize) {
		Group bean = EntityFactroy.getBean(Group.class);
		if (DBIdentifier.getDbType().equals(DBType.MY_BATIS)) {
			return mapper.selectRowBounds(bean, Page.getInstance(pageIndex, pageSize));
		} else {
			org.springframework.data.domain.Page<Group> findAll = repository
					.findAll(PageRequest.of(pageIndex - 1, pageSize));
			return findAll.getContent();
		}

	}

	public List<GroupVo> queryTeamGroup(String cupId) {
		if (DBIdentifier.getDbType().equals(DBType.MY_BATIS)) {
			return mapper.queryTeamByGroup(cupId);
		} else {
			return repository.queryTeamByGroup(cupId);
		}
	}

}
