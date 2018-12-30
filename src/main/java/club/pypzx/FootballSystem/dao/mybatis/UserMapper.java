package club.pypzx.FootballSystem.dao.mybatis;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import club.pypzx.FootballSystem.entity.User;
import club.pypzx.FootballSystem.template.BaseMapper;

public interface UserMapper extends BaseMapper<User> {
	/**
	 * 新增用户（注册）
	 * 
	 * @param user 用户信息
	 * @return 数据库变更记录，成功为1，失败为0
	 */
	public int insertUser(User user);

	/**
	 * 根据用户id删除用户记录
	 * 
	 * @param userId 用户id
	 * @return 数据库变更记录，成功为1，失败为0
	 */
	public int deleteUser(String username);

	/**
	 * 根据用户名和密码查询用户（用作登录检查）或根据userId差
	 * 
	 * @param username 用户名
	 * @param password 密码
	 * @return 符合条件的用户记录（若不符合，则返回null）
	 */
	public User queryUser(User user);

	/**
	 * 分页查询出用户列表
	 * 
	 * @param rowIndex 数据库查询开始索引（一般为0）
	 * @param pageSize 数据库页面大小
	 * @return list列表
	 */
	public List<User> queryUserList(@Param("rowIndex") int rowIndex, @Param("pageSize") int pageSize);

	/**
	 * 根据指定用户id查询出用户
	 * 
	 * @param userId 指定id
	 * @return 用户记录
	 */
	public int updateUser(User user);

	/**
	 * 根据指定用户名称查找用户
	 * 
	 * @param userId 指定id
	 * @return 是否存在
	 */
	public boolean queryUserByName(String username);

	/**
	 * 返回用户总记录数
	 * 
	 * @return
	 */
	public int queryUserListCount();
}
