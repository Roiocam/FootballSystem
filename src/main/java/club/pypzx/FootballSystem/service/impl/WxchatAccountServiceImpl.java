package club.pypzx.FootballSystem.service.impl;

import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import club.pypzx.FootballSystem.dao.jpa.WechatAccountRepository;
import club.pypzx.FootballSystem.dao.mybatis.WechatAccountMapper;
import club.pypzx.FootballSystem.datasource.DBIdentifier;
import club.pypzx.FootballSystem.entity.WechatAccount;
import club.pypzx.FootballSystem.enums.DBType;
import club.pypzx.FootballSystem.service.WechatAccountService;
import club.pypzx.FootballSystem.template.BaseExcution;
import club.pypzx.FootballSystem.template.BaseStateEnum;

@Service
public class WxchatAccountServiceImpl implements WechatAccountService {
	@Autowired
	private WechatAccountMapper mapper;
	@Autowired
	private WechatAccountRepository repository;

	@Override
	public BaseExcution<WechatAccount> add(WechatAccount obj) {
		if (obj == null) {
			return new BaseExcution<>(BaseStateEnum.EMPTY);
		}
		int count = 0;
		if (DBIdentifier.getDbType().equals(DBType.MY_BATIS)) {
			if (mapper.selectPrimary(obj) != null) {
				count = mapper.update(obj);
			} else {
				count = mapper.insert(obj);
			}
			if (1 != count) {
				return new BaseExcution<>(BaseStateEnum.FAIL);
			}
		} else if (DBIdentifier.getDbType().equals(DBType.JPA)) {
			repository.save(obj);
		}
		return new BaseExcution<>(BaseStateEnum.SUCCESS);
	}

	@Override
	public BaseExcution<WechatAccount> edit(WechatAccount obj) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BaseExcution<WechatAccount> findById(String objId) {
		WechatAccount obj = new WechatAccount();
		obj.setOpenid(objId);
		WechatAccount selectByPrimaryKey = null;
		if (DBIdentifier.getDbType().equals(DBType.MY_BATIS)) {
			selectByPrimaryKey = mapper.selectPrimary(obj);
		} else if (DBIdentifier.getDbType().equals(DBType.JPA)) {
			selectByPrimaryKey = repository.findOne(Example.of(obj)).get();
		}
		if (selectByPrimaryKey == null) {
			return new BaseExcution<WechatAccount>(BaseStateEnum.QUERY_ERROR);
		}
		return new BaseExcution<WechatAccount>(BaseStateEnum.SUCCESS, selectByPrimaryKey);
	}

	@Override
	public BaseExcution<WechatAccount> findByCondition(WechatAccount obj) {
		List<WechatAccount> selectRowBounds = null;
		if (DBIdentifier.getDbType().equals(DBType.MY_BATIS)) {
			int selectCount = mapper.selectCount(obj);
			selectRowBounds = mapper.selectRowBounds(obj, new RowBounds(0, selectCount));
		} else if (DBIdentifier.getDbType().equals(DBType.JPA)) {
			selectRowBounds = repository.findAll(Example.of(obj));
		}
		if (selectRowBounds != null && selectRowBounds.size() > -1) {
			return new BaseExcution<WechatAccount>(BaseStateEnum.SUCCESS, selectRowBounds, selectRowBounds.size());
		}
		return new BaseExcution<WechatAccount>(BaseStateEnum.QUERY_ERROR);
	}

	@Override
	public BaseExcution<WechatAccount> findAll(int pageIndex, int pageSize) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BaseExcution<WechatAccount> removeById(String objId) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BaseExcution<WechatAccount> removeByIdList(List<String> list) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

}
