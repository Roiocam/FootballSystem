package club.pypzx.FootballSystem.web.admin;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import club.pypzx.FootballSystem.dto.RequestEntity;
import club.pypzx.FootballSystem.dto.UserExcution;
import club.pypzx.FootballSystem.entity.User;
import club.pypzx.FootballSystem.enums.UserStateEnum;
import club.pypzx.FootballSystem.service.UserService;
import club.pypzx.FootballSystem.utils.ModelMapUtil;

@Controller
@RequestMapping(value = "admin/service")
public class LoginController {

	@Autowired
	private UserService userService;

	@RequestMapping(value = "/logout", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> logout(HttpServletRequest request) {
		User attribute = (User) request.getSession().getAttribute("user");
		if (attribute != null && attribute.getUsername() != null || !"".equals(attribute.getUsername())) {
			request.getSession().setAttribute("user", null);
			return ModelMapUtil.getSuccessMap("登录状态已清除!");
		} else {
			return ModelMapUtil.getErrorMap("你尚未登录!");
		}
	}

	@RequestMapping(value = "/loginCheck", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> loginCheck(RequestEntity entity, HttpServletRequest request) {
		User user = entity.getUser();
		if (user == null || "".equals(user.getUsername()) || "".equals(user.getPassword())) {
			return ModelMapUtil.getErrorMap("用户名或密码为空!");
		}
		UserExcution ue = userService.getUser(user.getUsername(), user.getPassword());
		if (ue.getState() == UserStateEnum.SUCCESS.getState()) {
			request.getSession().setAttribute("user", ue.getUser());
			return ModelMapUtil.getSuccessMap("登录成功!");
		} else {
			return ModelMapUtil.getErrorMap("用户名或密码不正确!" + ue.getStateInfo());
		}
	}
}
