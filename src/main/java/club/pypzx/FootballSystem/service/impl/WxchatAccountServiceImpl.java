package club.pypzx.FootballSystem.service.impl;

import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import club.pypzx.FootballSystem.dao.mybatis.WechatAccountMapper;
import club.pypzx.FootballSystem.entity.WechatAccount;
import club.pypzx.FootballSystem.service.WechatAccountService;
import club.pypzx.FootballSystem.template.BaseExcution;
import club.pypzx.FootballSystem.template.BaseStateEnum;

@Service
public class WxchatAccountServiceImpl implements WechatAccountService {
	@Autowired
	private WechatAccountMapper mapper;

	@Override
	public BaseExcution<WechatAccount> add(WechatAccount obj) {
		if (obj == null) {
			return new BaseExcution<>(BaseStateEnum.EMPTY);
		}
		int count = 0;
		if (mapper.selectPrimary(obj) != null) {
			count = mapper.update(obj);
		} else {
			count = mapper.insert(obj);
		}
		if (1 != count) {
			return new BaseExcution<>(BaseStateEnum.FAIL);
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
		WechatAccount selectByPrimaryKey = mapper.selectPrimary(obj);
		if (selectByPrimaryKey == null) {
			return new BaseExcution<WechatAccount>(BaseStateEnum.QUERY_ERROR);
		}
		return new BaseExcution<WechatAccount>(BaseStateEnum.SUCCESS, selectByPrimaryKey);
	}

	@Override
	public BaseExcution<WechatAccount> findByCondition(WechatAccount obj) {
		int selectCount = mapper.selectCount(obj);
		List<WechatAccount> selectRowBounds = mapper.selectRowBounds(obj, new RowBounds(0, selectCount));
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
