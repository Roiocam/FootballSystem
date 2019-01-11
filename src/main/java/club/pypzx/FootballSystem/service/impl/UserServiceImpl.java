package club.pypzx.FootballSystem.service.impl;

import java.util.Iterator;
import java.util.List;

import club.pypzx.FootballSystem.template.BaseExcution;
import club.pypzx.FootballSystem.template.BaseStateEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import club.pypzx.FootballSystem.dao.UserDao;
import club.pypzx.FootballSystem.dbmgr.EntityFactroy;
import club.pypzx.FootballSystem.dto.UserExcution;
import club.pypzx.FootballSystem.entity.User;
import club.pypzx.FootballSystem.enums.UserStateEnum;
import club.pypzx.FootballSystem.exception.UserException;
import club.pypzx.FootballSystem.service.UserService;
import club.pypzx.FootballSystem.utils.DESUtil;
import club.pypzx.FootballSystem.utils.ParamUtils;

@Service
public class UserServiceImpl implements UserService {
	private static final String EMPTY_STRING = "";
	private final UserDao dao;

	@Autowired
	public UserServiceImpl(UserDao dao) {
		this.dao = dao;
	}

	@Override
	@Transactional
	public UserExcution addUser(String username, String password) {
		if (username == null || password == null || EMPTY_STRING.equals(username) || EMPTY_STRING.equals(password))
			throw new UserException("用户信息不完整");
		User user = EntityFactroy.getBean(User.class);
		user.setUsername(username);
		User selectPrimary = dao.findById(username);
		if (null != selectPrimary && null != selectPrimary.getUsername()) {
			throw new UserException("用户名已存在");
		}
		// 对密码进行DES加密
		user.setPassword(DESUtil.getEncryptStrig(password));
		dao.add(user);
		return new UserExcution(UserStateEnum.SUCCESS, user);
	}

	@Override
	@Transactional
	public UserExcution removeUser(String username) {
		if (ParamUtils.emptyString(username))
			throw new UserException("用户id为空");
		dao.remove(username);
		return new UserExcution(UserStateEnum.SUCCESS);

	}

	@Override
	public UserExcution getUser(String username, String password) {
		if ("".equals(username) || "".equals(password))
			throw new UserException("用户信息为空");
		User temp =EntityFactroy.getBean(User.class);
		temp.setUsername(username);
		temp.setPassword(DESUtil.getEncryptStrig(password));
		User selectPrimary = null;
		selectPrimary = dao.findByCondition(temp);
		UserExcution ue=(selectPrimary != null)? new UserExcution(UserStateEnum.SUCCESS, selectPrimary):new UserExcution(UserStateEnum.QUERY_ERROR);
		return ue;
	}

	@Override
	public UserExcution getUserList(int pageIndex, int pageSize) {
		List<User> selectAll = dao.findAll(pageIndex, pageSize);
		int selectCount = dao.count();
		if (selectAll == null||selectAll.size() < 0) {
			return new UserExcution(UserStateEnum.QUERY_ERROR);
		}
		for(User iu:selectAll){
			iu.setPassword(DESUtil.getDecryptString(iu.getPassword()));
		}
		return new UserExcution(UserStateEnum.SUCCESS, selectAll, selectCount);

	}

	@Override
	public UserExcution editUser(String username, String password) {
		if (ParamUtils.emptyString(username) || ParamUtils.emptyString(password))
			throw new UserException("用户ID或密码为空");
		User user = EntityFactroy.getBean(User.class);
		user.setUsername(username);
		user.setPassword(DESUtil.getEncryptStrig(password));
		dao.edit(user);
		return new UserExcution(UserStateEnum.SUCCESS);
	}

	@Override
	public UserExcution removeUserList(List<String> list) {
		for(String is:list){
			removeUser(is);
		}
		return new UserExcution(UserStateEnum.SUCCESS);
	}

}
