package club.pypzx.FootballSystem.web.admin;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import club.pypzx.FootballSystem.dto.RequestEntity;
import club.pypzx.FootballSystem.dto.UserExcution;
import club.pypzx.FootballSystem.entity.User;
import club.pypzx.FootballSystem.enums.UserStateEnum;
import club.pypzx.FootballSystem.service.UserService;
import club.pypzx.FootballSystem.template.BaseStateEnum;
import club.pypzx.FootballSystem.utils.ModelMapUtil;
import club.pypzx.FootballSystem.utils.ParamUtils;

@Controller
@ResponseBody
@RequestMapping("admin/service/user")
public class UserServiceController {
	@Autowired
	private UserService service;

	@PostMapping("/getUsers")
	public Map<String, Object> getUsers(RequestEntity request) {
		if (ParamUtils.wrongPage(request.getPageIndex(), request.getPageSize())) {
			return ModelMapUtil.getErrorMap(BaseStateEnum.PAGE_ERROR.getStateInfo());
		}
		UserExcution userList = service.getUserList(request.getPageIndex(), request.getPageSize());
		if (UserStateEnum.SUCCESS.getState() != userList.getState()) {
			return ModelMapUtil.getErrorMap("查询管理员列表失败:" + userList.getStateInfo());
		}
		return ModelMapUtil.getSuccessMapWithList("查询管理员列表成功", userList.getUserList(), userList.getCount());

	}

	@PostMapping("/addUser")
	public Map<String, Object> addCups(RequestEntity request) {
		User user = request.getUser();
		if (user == null || ParamUtils.emptyString(user.getUsername()) || ParamUtils.emptyString(user.getPassword())) {
			return ModelMapUtil.getErrorMap(BaseStateEnum.EMPTY.getStateInfo());
		}
		try {
			UserExcution addUser = service.addUser(user.getUsername(), user.getPassword());
			if (addUser.getState() != UserStateEnum.SUCCESS.getState()) {
				return ModelMapUtil.getErrorMap("新增管理员失败:" + addUser.getStateInfo());
			}
			return ModelMapUtil.getSuccessMap("新增管理员成功");
		} catch (Exception e) {
			return ModelMapUtil.getErrorMap("新增管理员失败:" + e.getMessage());
		}

	}

	@PostMapping("/editUser")
	public Map<String, Object> editCup(RequestEntity request) {
		User user = request.getUser();
		if (user == null || ParamUtils.emptyString(user.getUsername()) || ParamUtils.emptyString(user.getPassword())) {
			return ModelMapUtil.getErrorMap(BaseStateEnum.EMPTY.getStateInfo());
		}
		UserExcution editUser = service.editUser(user.getUsername(), user.getPassword());
		if (editUser.getState() != UserStateEnum.SUCCESS.getState()) {
			return ModelMapUtil.getErrorMap("修改管理员失败:" + editUser.getStateInfo());
		}
		return ModelMapUtil.getSuccessMap("修改管理员成功");
	}

	@PostMapping("/deleteUser")
	public Map<String, Object> deleteCup(RequestEntity request) {
		User user = request.getUser();
		if (user == null || ParamUtils.emptyString(user.getUsername())) {
			return ModelMapUtil.getErrorMap(BaseStateEnum.EMPTY.getStateInfo());
		}
		try {

			UserExcution removeUser = service.removeUser(user.getUsername());
			if (removeUser.getState() != UserStateEnum.SUCCESS.getState()) {
				return ModelMapUtil.getErrorMap("删除管理员失败:" + removeUser.getStateInfo());
			}
			return ModelMapUtil.getSuccessMap("删除管理员成功");
		} catch (Exception e) {
			return ModelMapUtil.getErrorMap("删除管理员失败:" + e.getMessage());
		}

	}

	@PostMapping("/deleteUserList")
	public Map<String, Object> deleteCupList(RequestEntity request) {
		List<String> idList = request.getIdList();
		try {
			if (idList == null || idList.size() <= 0) {
				return ModelMapUtil.getErrorMap("删除失败,请选择管理员后删除!");
			}
			UserExcution removeUserList = service.removeUserList(idList);
			if (removeUserList.getState() != UserStateEnum.SUCCESS.getState()) {
				return ModelMapUtil.getErrorMap("Oops!删除失败，请联系管理员");
			}
			return ModelMapUtil.getSuccessMap("删除成功");
		} catch (Exception e) {
			return ModelMapUtil.getErrorMap("删除管理员失败:" + e.getMessage());
		}

	}
}
