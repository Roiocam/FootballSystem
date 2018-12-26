package club.pypzx.FootballSystem.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import club.pypzx.FootballSystem.dao.WechatAccountMapper;
import club.pypzx.FootballSystem.dto.BaseExcution;
import club.pypzx.FootballSystem.entity.WechatAccount;
import club.pypzx.FootballSystem.enums.BaseStateEnum;
import club.pypzx.FootballSystem.service.WechatAccountService;

@Service
public class WxchatAccountServiceImpl implements WechatAccountService {
	@Autowired
	private WechatAccountMapper mapper;

	@Override
	public BaseExcution<WechatAccount> insertObj(WechatAccount obj) {
		if (obj == null) {
			return new BaseExcution<>(BaseStateEnum.EMPTY);
		}
		if (1 != mapper.insert(obj)) {
			return new BaseExcution<>(BaseStateEnum.FAIL);
		}
		return new BaseExcution<>(BaseStateEnum.SUCCESS);
	}

	@Override
	public BaseExcution<WechatAccount> updateObjByPrimaryKey(WechatAccount obj) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BaseExcution<WechatAccount> queryObjOneByPrimaryKey(String objId) {
		WechatAccount selectByPrimaryKey = mapper.selectByPrimaryKey(objId);
		if (selectByPrimaryKey == null) {
			return new BaseExcution<WechatAccount>(BaseStateEnum.QUERY_ERROR);
		}
		return new BaseExcution<WechatAccount>(BaseStateEnum.SUCCESS, selectByPrimaryKey);
	}

	@Override
	public BaseExcution<WechatAccount> queryObjOne(WechatAccount obj) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BaseExcution<WechatAccount> queryAll(int pageIndex, int pageSize) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BaseExcution<WechatAccount> deleteObjByPrimaryKey(String objId) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BaseExcution<WechatAccount> deleteObjectList(List<String> list) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

}