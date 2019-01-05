package club.pypzx.FootballSystem.dao;

import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import club.pypzx.FootballSystem.dao.jpa.WechatAccountRepository;
import club.pypzx.FootballSystem.dao.mybatis.WechatAccountMapper;
import club.pypzx.FootballSystem.datasource.DBIdentifier;
import club.pypzx.FootballSystem.dbmgr.EntityFactroy;
import club.pypzx.FootballSystem.entity.Page;
import club.pypzx.FootballSystem.entity.WechatAccount;
import club.pypzx.FootballSystem.enums.DBType;
import club.pypzx.FootballSystem.template.BaseDao;

@Repository
@Scope(value = "singleton")
public class WechatAccountDao implements BaseDao<WechatAccount> {
	@Autowired
	private WechatAccountMapper mapper;
	@Autowired
	private WechatAccountRepository repository;

	public static BaseDao<WechatAccount> instance() {
		return EntityFactroy.getBean(WechatAccountDao.class);
	}

	@Override
	public void add(WechatAccount obj) {

		if (DBIdentifier.getDbType().equals(DBType.MY_BATIS)) {
			int count = 0;
			if (mapper.selectPrimary(obj) != null) {
				count = mapper.update(obj);
			} else {
				count = mapper.insert(obj);
			}
			if (1 != count) {
				throw new RuntimeException("更新微信用户失败");
			}
		} else if (DBIdentifier.getDbType().equals(DBType.JPA)) {
			repository.save(obj);
		}
	}

	@Override
	public void edit(WechatAccount obj) {
		if (DBIdentifier.getDbType().equals(DBType.MY_BATIS)) {
			if (1 != mapper.update(obj)) {
				throw new RuntimeException("更新微信用户失败");
			}
		} else if (DBIdentifier.getDbType().equals(DBType.JPA)) {
			repository.save(obj);
		}

	}

	@Override
	public void remove(String objId) {
		WechatAccount bean = EntityFactroy.getBean(WechatAccount.class);
		bean.setOpenid(objId);
		if (DBIdentifier.getDbType().equals(DBType.MY_BATIS)) {
			if (1 != mapper.delete(bean)) {
				throw new RuntimeException("删除微信用户失败");
			}
		} else if (DBIdentifier.getDbType().equals(DBType.JPA)) {
			repository.delete(bean);
		}

	}

	@Override
	public WechatAccount findById(String objId) {
		WechatAccount selectByPrimaryKey = EntityFactroy.getBean(WechatAccount.class);
		selectByPrimaryKey.setOpenid(objId);
		if (DBIdentifier.getDbType().equals(DBType.MY_BATIS)) {
			selectByPrimaryKey = mapper.selectPrimary(selectByPrimaryKey);
		} else if (DBIdentifier.getDbType().equals(DBType.JPA)) {
			selectByPrimaryKey = repository.findById(objId).orElse(null);
		}
		return selectByPrimaryKey;

	}

	@Override
	public WechatAccount findByCondition(WechatAccount obj) {
		if (DBIdentifier.getDbType().equals(DBType.MY_BATIS)) {
			return mapper.selectPrimary(obj);
		} else if (DBIdentifier.getDbType().equals(DBType.JPA)) {
			return repository.findOne(Example.of(obj)).orElse(null);
		}
		return null;
	}

	@Override
	public List<WechatAccount> findAllCondition(WechatAccount obj) {
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
			return mapper.selectCount(EntityFactroy.getBean(WechatAccount.class));
		} else {
			return (int) repository.count();
		}
	}

	@Override
	public int countExmaple(WechatAccount obj) {
		if (DBIdentifier.getDbType().equals(DBType.MY_BATIS)) {
			return mapper.selectCount(obj);
		} else {
			return (int) repository.count(Example.of(obj));
		}
	}

	@Override
	public List<WechatAccount> findAll(int pageIndex, int pageSize) {
		WechatAccount bean = EntityFactroy.getBean(WechatAccount.class);
		if (DBIdentifier.getDbType().equals(DBType.MY_BATIS)) {
			return mapper.selectRowBounds(bean, Page.getInstance(pageIndex, pageSize));
		} else {
			org.springframework.data.domain.Page<WechatAccount> findAll = repository
					.findAll(PageRequest.of(pageIndex - 1, pageSize));
			return findAll.getContent();
		}

	}

}
