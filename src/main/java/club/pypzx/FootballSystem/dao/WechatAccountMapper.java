package club.pypzx.FootballSystem.dao;

import club.pypzx.FootballSystem.entity.WechatAccount;
import club.pypzx.FootballSystem.template.BaseMapper;

public interface WechatAccountMapper extends BaseMapper<WechatAccount> {
	public WechatAccount selectByPrimary(String objId);
}