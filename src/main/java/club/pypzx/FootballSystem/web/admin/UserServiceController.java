package club.pypzx.FootballSystem.web.admin;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;

import club.pypzx.FootballSystem.dto.UserExcution;
import club.pypzx.FootballSystem.enums.UserStateEnum;
import club.pypzx.FootballSystem.service.UserService;
import club.pypzx.FootballSystem.template.BaseStateEnum;
import club.pypzx.FootballSystem.utils.HttpServletRequestUtil;
import club.pypzx.FootballSystem.utils.ModelMapUtil;
import club.pypzx.FootballSystem.utils.ParamUtils;

@Controller
@ResponseBody
@RequestMapping("admin/service/user")
public class UserServiceController {
	@Autowired
	private UserService service;

	@PostMapping("/getUsers")
	public Map<String, Object> getUsers(HttpServletRequest request) {
		int pageIndex = HttpServletRequestUtil.getInt(request, "pageIndex");
		int pageSize = HttpServletRequestUtil.getInt(request, "pageSize");
		if (ParamUtils.wrongPage(pageIndex, pageSize)) {
			return ModelMapUtil.getErrorMap(BaseStateEnum.PAGE_ERROR.getStateInfo());
		}
		UserExcution userList = service.getUserList(pageIndex, pageSize);
		if (UserStateEnum.SUCCESS.getState() != userList.getState()) {
			return ModelMapUtil.getErrorMap("查询管理员列表失败:" + userList.getStateInfo());
		}
		return ModelMapUtil.getSuccessMapWithList("查询管理员列表成功", userList.getUserList(),userList.getCount());

	}

	@PostMapping("/addUser")
	public Map<String, Object> addCups(HttpServletRequest request) {
		try {
			String username = HttpServletRequestUtil.getString(request, "username");
			String password = HttpServletRequestUtil.getString(request, "password");
			UserExcution addUser = service.addUser(username, password);
			if (addUser.getState() != UserStateEnum.SUCCESS.getState()) {
				return ModelMapUtil.getErrorMap("新增管理员失败:" + addUser.getStateInfo());
			}
			return ModelMapUtil.getSuccessMap("新增管理员成功");
		} catch (Exception e) {
			return ModelMapUtil.getErrorMap("新增管理员失败:" + e.getMessage());
		}

	}

	@PostMapping("/editUser")
	public Map<String, Object> editCup(HttpServletRequest request) {
		String username = HttpServletRequestUtil.getString(request, "username");
		String password = HttpServletRequestUtil.getString(request, "password");
		if (ParamUtils.emptyString(username) || ParamUtils.emptyString(password)) {
			return ModelMapUtil.getErrorMap(BaseStateEnum.EMPTY.getStateInfo());
		}
		UserExcution editUser = service.editUser(username, password);
		if (editUser.getState() != UserStateEnum.SUCCESS.getState()) {
			return ModelMapUtil.getErrorMap("修改管理员失败:" + editUser.getStateInfo());
		}
		return ModelMapUtil.getSuccessMap("修改管理员成功");
	}

	@PostMapping("/deleteUser")
	public Map<String, Object> deleteCup(HttpServletRequest request) {
		String username = HttpServletRequestUtil.getString(request, "username");
		if (ParamUtils.emptyString(username)) {
			return ModelMapUtil.getErrorMap(BaseStateEnum.EMPTY.getStateInfo());
		}
		try {

			UserExcution removeUser = service.removeUser(username);
			if (removeUser.getState() != UserStateEnum.SUCCESS.getState()) {
				return ModelMapUtil.getErrorMap("删除管理员失败:" + removeUser.getStateInfo());
			}
			return ModelMapUtil.getSuccessMap("删除管理员成功");
		} catch (Exception e) {
			return ModelMapUtil.getErrorMap("删除管理员失败:" + e.getMessage());
		}

	}

	@PostMapping("/deleteUserList")
	public Map<String, Object> deleteCupList(HttpServletRequest request) {
		String str = HttpServletRequestUtil.getString(request, "list");
		List<String> list = (List<String>) JSON.parseArray(str, String.class);
		try {
			if (list == null || list.size() <= 0) {
				return ModelMapUtil.getErrorMap("删除失败,请选择管理员后删除!");
			}
			UserExcution removeUserList = service.removeUserList(list);
			if (removeUserList.getState() != UserStateEnum.SUCCESS.getState()) {
				return ModelMapUtil.getErrorMap("Oops!删除失败，请联系管理员");
			}
			return ModelMapUtil.getSuccessMap("删除成功");
		} catch (Exception e) {
			return ModelMapUtil.getErrorMap("删除管理员失败:" + e.getMessage());
		}

	}
}
