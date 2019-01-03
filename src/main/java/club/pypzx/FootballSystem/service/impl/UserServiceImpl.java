package club.pypzx.FootballSystem.service.impl;

import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import club.pypzx.FootballSystem.dao.jpa.UserRepository;
import club.pypzx.FootballSystem.dao.mybatis.UserMapper;
import club.pypzx.FootballSystem.datasource.DBIdentifier;
import club.pypzx.FootballSystem.dto.UserExcution;
import club.pypzx.FootballSystem.entity.Page;
import club.pypzx.FootballSystem.entity.User;
import club.pypzx.FootballSystem.enums.DBType;
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
	@Autowired
	private UserRepository repository;

	@Override
	@Transactional
	public UserExcution addUser(String username, String password) {
		if (username == null || password == null || EMPTY_STRING.equals(username) || EMPTY_STRING.equals(password))
			throw new UserException("用户信息不完整");
		User user = new User();
		user.setUsername(username);
		User selectPrimary = null;
		if (DBIdentifier.getDbType().equals(DBType.MY_BATIS)) {
			selectPrimary = userDao.selectPrimary(user);
		} else if (DBIdentifier.getDbType().equals(DBType.JPA)) {
			selectPrimary = repository.findOne(Example.of(user)).get();
		}
		if (null != selectPrimary && null != selectPrimary.getUsername()) {
			throw new UserException("用户名已存在");
		}
		// 对密码进行DES加密
		user.setPassword(DESUtil.getEncryptStrig(password));
		if (DBIdentifier.getDbType().equals(DBType.MY_BATIS)) {
			if (1 != userDao.insert(user)) {
				return new UserExcution(UserStateEnum.INNER_ERROR);
			}
		} else if (DBIdentifier.getDbType().equals(DBType.JPA)) {
			user = repository.save(user);
		}
		return new UserExcution(UserStateEnum.SUCCESS, user);
	}

	@Override
	@Transactional
	public UserExcution removeUser(String username) {
		if (ParamUtils.emptyString(username))
			throw new UserException("用户id为空");
		User user = new User();
		user.setUsername(username);
		User selectPrimary = null;
		if (DBIdentifier.getDbType().equals(DBType.MY_BATIS)) {
			selectPrimary = userDao.selectPrimary(user);
			if (1 != userDao.delete(selectPrimary)) {
				return new UserExcution(UserStateEnum.DELETE_ERROR);
			}
		} else if (DBIdentifier.getDbType().equals(DBType.JPA)) {
			selectPrimary = repository.findOne(Example.of(user)).get();
			repository.delete(selectPrimary);
		}
		return new UserExcution(UserStateEnum.SUCCESS);

	}

	@Override
	public UserExcution getUser(String username, String password) {
		if ("".equals(username) || "".equals(password))
			throw new UserException("用户信息为空");
		User temp = new User();
		temp.setUsername(username);
		temp.setPassword(DESUtil.getEncryptStrig(password));
		User selectPrimary = null;
		if (DBIdentifier.getDbType().equals(DBType.MY_BATIS)) {
			selectPrimary = userDao.selectPrimary(temp);
		} else if (DBIdentifier.getDbType().equals(DBType.JPA)) {
			selectPrimary = repository.findOne(Example.of(temp)).get();
		}
		if (selectPrimary != null) {
			return new UserExcution(UserStateEnum.SUCCESS, selectPrimary);
		} else {
			return new UserExcution(UserStateEnum.QUERY_ERROR);
		}
	}

	@Override
	public UserExcution getUserList(int pageIndex, int pageSize) {
		List<User> selectAll = null;
		int selectCount = 0;
		if (DBIdentifier.getDbType().equals(DBType.MY_BATIS)) {
			selectAll = userDao.selectRowBounds(new User(), Page.getInstance(pageIndex, pageSize));
			Iterator<User> iterator = selectAll.iterator();
			while (iterator.hasNext()) {
				User next = iterator.next();
				next.setPassword(DESUtil.getDecryptString(next.getPassword()));
			}
			selectCount = userDao.selectCount(new User());
		} else if (DBIdentifier.getDbType().equals(DBType.JPA)) {
			org.springframework.data.domain.Page<User> findAll = repository
					.findAll(PageRequest.of(pageIndex - 1, pageSize));
			selectAll = findAll.getContent();
			selectCount = (int) repository.count();
		}
		if (selectAll == null) {
			return new UserExcution(UserStateEnum.QUERY_ERROR);
		}
		if (selectAll.size() < 0) {
			return new UserExcution(UserStateEnum.QUERY_ERROR);
		}
		Iterator<User> iterator = selectAll.iterator();
		while (iterator.hasNext()) {
			User next = iterator.next();
			next.setPassword(DESUtil.getDecryptString(next.getPassword()));
		}
		return new UserExcution(UserStateEnum.SUCCESS, selectAll, selectCount);

	}

	@Override
	public UserExcution editUser(String username, String password) {
		if (ParamUtils.emptyString(username) || ParamUtils.emptyString(password))
			throw new UserException("用户ID或密码为空");
		User user = new User();
		user.setUsername(username);
		user.setPassword(DESUtil.getEncryptStrig(password));

		if (DBIdentifier.getDbType().equals(DBType.MY_BATIS)) {
			if (1 != userDao.update(user)) {
				return new UserExcution(UserStateEnum.UPDATE_ERROR);
			}
		} else if (DBIdentifier.getDbType().equals(DBType.JPA)) {
			repository.save(user);
		}
		return new UserExcution(UserStateEnum.SUCCESS);
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
