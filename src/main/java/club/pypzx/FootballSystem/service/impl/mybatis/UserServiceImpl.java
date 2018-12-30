package club.pypzx.FootballSystem.service.impl.mybatis;

import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import club.pypzx.FootballSystem.dao.mybatis.UserMapper;
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
	@Autowired
	private UserMapper userDao;

	@Override
	@Transactional
	public UserExcution addUser(String username, String password) {
		if (username == null || password == null || EMPTY_STRING.equals(username) || EMPTY_STRING.equals(password))
			throw new UserException("用户信息不完整");
		boolean queryUserByName = userDao.queryUserByName(username);
		if (queryUserByName) {
			throw new UserException("用户名已存在");
		}
		User user = new User();
		// 对密码进行DES加密
		user.setPassword(DESUtil.getEncryptStrig(password));
		user.setUsername(username);
		int insertUser = userDao.insertUser(user);
		if (insertUser > 0) {
			return new UserExcution(UserStateEnum.SUCCESS, user);
		} else {
			return new UserExcution(UserStateEnum.INNER_ERROR);
		}
	}

	@Override
	@Transactional
	public UserExcution removeUser(String username) {
		if (ParamUtils.emptyString(username))
			throw new UserException("用户id为空");
		User user = new User();
		user.setUsername(username);
		User queryUserById = userDao.queryUser(user);
		if (queryUserById != null) {
			userDao.deleteUser(queryUserById.getUsername());
			return new UserExcution(UserStateEnum.SUCCESS);
		} else {
			return new UserExcution(UserStateEnum.DELETE_ERROR);
		}

	}

	@Override
	public UserExcution getUser(String username, String password) {
		if ("".equals(username) || "".equals(password))
			throw new UserException("用户信息为空");
		User temp = new User();
		temp.setUsername(username);
		temp.setPassword(DESUtil.getEncryptStrig(password));
		User queryUser = userDao.queryUser(temp);
		if (queryUser != null) {
			return new UserExcution(UserStateEnum.SUCCESS, queryUser);
		} else {
			return new UserExcution(UserStateEnum.QUERY_ERROR);
		}
	}

	@Override
	public UserExcution getUserList(int pageIndex, int pageSize) {
		List<User> queryUserList = userDao.queryUserList(pageIndex - 1, pageSize);
		if (queryUserList.size() > 0) {
			Iterator<User> iterator = queryUserList.iterator();
			while (iterator.hasNext()) {
				User next = iterator.next();
				next.setPassword(DESUtil.getDecryptString(next.getPassword()));
			}
			int queryUserListCount = userDao.queryUserListCount();
			return new UserExcution(UserStateEnum.SUCCESS, queryUserList, queryUserListCount);
		} else {
			return new UserExcution(UserStateEnum.QUERY_ERROR);
		}
	}

	@Override
	public UserExcution editUser(String username, String password) {
		if (ParamUtils.emptyString(username) || ParamUtils.emptyString(password))
			throw new UserException("用户ID或密码为空");
		User user = new User();
		user.setUsername(username);
		user.setPassword(DESUtil.getEncryptStrig(password));
		int updateUser = userDao.updateUser(user);
		if (updateUser > 0) {
			return new UserExcution(UserStateEnum.SUCCESS);
		} else {
			return new UserExcution(UserStateEnum.UPDATE_ERROR);
		}

	}

	@Override
	public UserExcution removeUserList(List<String> list) {
		Iterator<?> iterator = list.iterator();
		if (iterator == null) {
			return new UserExcution(UserStateEnum.DELETE_ERROR);
		}
		while (iterator.hasNext()) {
			removeUser((String) iterator.next());
		}
		return new UserExcution(UserStateEnum.SUCCESS);
	}

}
