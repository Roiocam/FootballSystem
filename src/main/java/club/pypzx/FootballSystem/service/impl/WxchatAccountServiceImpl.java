package club.pypzx.FootballSystem.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import club.pypzx.FootballSystem.dao.WechatAccountDao;
import club.pypzx.FootballSystem.dbmgr.EntityFactroy;
import club.pypzx.FootballSystem.entity.WechatAccount;
import club.pypzx.FootballSystem.service.WechatAccountService;
import club.pypzx.FootballSystem.template.BaseExcution;
import club.pypzx.FootballSystem.template.BaseStateEnum;

@Service
public class WxchatAccountServiceImpl implements WechatAccountService {
	@Autowired
	private WechatAccountDao dao;

	@Override
	public BaseExcution<WechatAccount> add(WechatAccount obj) {
		if (obj == null) {
			return new BaseExcution<>(BaseStateEnum.EMPTY);
		}
		dao.add(obj);
		return new BaseExcution<>(BaseStateEnum.SUCCESS);
	}

	@Override
	public BaseExcution<WechatAccount> edit(WechatAccount obj) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BaseExcution<WechatAccount> findById(String objId) {
		WechatAccount obj = EntityFactroy.getBean(WechatAccount.class);
		obj.setOpenid(objId);
		WechatAccount selectByPrimaryKey = dao.findByCondition(obj);
		if (selectByPrimaryKey == null) {
			return new BaseExcution<WechatAccount>(BaseStateEnum.QUERY_ERROR);
		}
		return new BaseExcution<WechatAccount>(BaseStateEnum.SUCCESS, selectByPrimaryKey);
	}

	@Override
	public BaseExcution<WechatAccount> findByCondition(WechatAccount obj) {
		List<WechatAccount> selectRowBounds = dao.findAllCondition(obj);
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
