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
	private final WechatAccountDao dao;

	@Autowired
	public WxchatAccountServiceImpl(WechatAccountDao dao) {
		this.dao = dao;
	}

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

		BaseExcution<WechatAccount> be= (selectByPrimaryKey == null) ?new BaseExcution<WechatAccount>(BaseStateEnum.SUCCESS, selectByPrimaryKey)
				:new BaseExcution<WechatAccount>(BaseStateEnum.QUERY_ERROR);
		return be;
	}

	@Override
	public BaseExcution<WechatAccount> findByCondition(WechatAccount obj) {
		List<WechatAccount> selectRowBounds = dao.findAllCondition(obj);
		BaseExcution<WechatAccount> be=(selectRowBounds != null && selectRowBounds.size() > -1)? new BaseExcution<WechatAccount>(BaseStateEnum.SUCCESS, selectRowBounds, selectRowBounds.size())
				:new BaseExcution<WechatAccount>(BaseStateEnum.QUERY_ERROR);
		return be;
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
