package club.pypzx.FootballSystem.utils;

public class WechatUtil {
	/**
	 * GET方法获取用户信息，需要OPENID
	 */
	private String GET_USER_INFO = "https://api.weixin.qq.com/cgi-bin/user/info?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN";
	private String GET_ACCESS_TOKEN = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";
}
