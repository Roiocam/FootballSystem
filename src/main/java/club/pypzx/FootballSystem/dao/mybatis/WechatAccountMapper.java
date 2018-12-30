package club.pypzx.FootballSystem.dao.mybatis;

import club.pypzx.FootballSystem.entity.WechatAccount;

public interface WechatAccountMapper  {
	public WechatAccount selectByPrimary(String objId);

	public int updateByPrimaryKeySelective(WechatAccount obj);

	public int insert(WechatAccount obj);
}