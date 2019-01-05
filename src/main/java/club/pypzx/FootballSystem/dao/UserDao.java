package club.pypzx.FootballSystem.dao;

import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import club.pypzx.FootballSystem.dao.jpa.UserRepository;
import club.pypzx.FootballSystem.dao.mybatis.UserMapper;
import club.pypzx.FootballSystem.datasource.DBIdentifier;
import club.pypzx.FootballSystem.dbmgr.EntityFactroy;
import club.pypzx.FootballSystem.entity.Page;
import club.pypzx.FootballSystem.entity.User;
import club.pypzx.FootballSystem.enums.DBType;
import club.pypzx.FootballSystem.template.BaseDao;

@Repository
@Scope(value = "singleton")
public class UserDao implements BaseDao<User> {
	@Autowired
	private UserMapper mapper;
	@Autowired
	private UserRepository repository;

	public static BaseDao<User> instance() {
		return EntityFactroy.getBean(UserDao.class);
	}

	@Override
	public void add(User obj) {
		if (DBIdentifier.getDbType().equals(DBType.MY_BATIS)) {
			if (1 != mapper.insert(obj)) {
				throw new RuntimeException("新增用户失败");
			}
		} else if (DBIdentifier.getDbType().equals(DBType.JPA)) {
			repository.save(obj);
		}
	}

	@Override
	public void edit(User obj) {
		if (DBIdentifier.getDbType().equals(DBType.MY_BATIS)) {
			if (1 != mapper.update(obj)) {
				throw new RuntimeException("更新用户失败");
			}
		} else if (DBIdentifier.getDbType().equals(DBType.JPA)) {
			repository.save(obj);
		}

	}

	@Override
	public void remove(String objId) {
		User bean = EntityFactroy.getBean(User.class);
		bean.setUsername(objId);
		if (DBIdentifier.getDbType().equals(DBType.MY_BATIS)) {
			bean = mapper.selectPrimary(bean);
			if (1 != mapper.delete(bean)) {
				throw new RuntimeException("删除用户失败");
			}
		} else if (DBIdentifier.getDbType().equals(DBType.JPA)) {
			bean = repository.findOne(Example.of(bean)).orElse(null);
			repository.delete(bean);
		}

	}

	@Override
	public User findById(String objId) {
		User selectByPrimaryKey = EntityFactroy.getBean(User.class);
		selectByPrimaryKey.setUsername(objId);
		if (DBIdentifier.getDbType().equals(DBType.MY_BATIS)) {
			selectByPrimaryKey = mapper.selectPrimary(selectByPrimaryKey);
		} else if (DBIdentifier.getDbType().equals(DBType.JPA)) {
			selectByPrimaryKey = repository.findOne(Example.of(selectByPrimaryKey)).orElse(null);
		}
		return selectByPrimaryKey;

	}

	@Override
	public User findByCondition(User obj) {
		if (DBIdentifier.getDbType().equals(DBType.MY_BATIS)) {
			return mapper.selectPrimary(obj);
		} else if (DBIdentifier.getDbType().equals(DBType.JPA)) {
			return repository.findOne(Example.of(obj)).orElse(null);
		}
		return null;
	}

	@Override
	public List<User> findAllCondition(User obj) {
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
			return mapper.selectCount(EntityFactroy.getBean(User.class));
		} else {
			return (int) repository.count();
		}
	}

	@Override
	public int countExmaple(User obj) {
		if (DBIdentifier.getDbType().equals(DBType.MY_BATIS)) {
			return mapper.selectCount(obj);
		} else {
			return (int) repository.count(Example.of(obj));
		}
	}

	@Override
	public List<User> findAll(int pageIndex, int pageSize) {
		User bean = EntityFactroy.getBean(User.class);
		if (DBIdentifier.getDbType().equals(DBType.MY_BATIS)) {
			return mapper.selectRowBounds(bean, Page.getInstance(pageIndex, pageSize));
		} else {
			org.springframework.data.domain.Page<User> findAll = repository
					.findAll(PageRequest.of(pageIndex - 1, pageSize));
			return findAll.getContent();
		}

	}

}
