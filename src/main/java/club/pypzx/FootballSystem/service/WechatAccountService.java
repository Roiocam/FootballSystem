package club.pypzx.FootballSystem.service;

import club.pypzx.FootballSystem.dto.BaseExcution;
import club.pypzx.FootballSystem.entity.WechatAccount;

public interface WechatAccountService extends BaseService<WechatAccount> {
	public WechatAccount packageWechatAccount(String name);

	public WechatAccount packageWechatAccount(String id, String name);

	public BaseExcution<WechatAccount> randomTeamToGroup(String cupId) throws Exception;

}
