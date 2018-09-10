package club.pypzx.FootballSystem.service;


import club.pypzx.FootballSystem.dto.UserExcution;

public interface UserService {
	/**
	 * 根据（用户名，密码，操作员姓名，用户权限0为店家，1为员工）生成用户记录
	 * @param username 用户名
	 * @param password 密码
	 * @param emplyoeeName 操作员姓名
	 * @param userType 用户权限0为店家，1为员工
	 * @return  操作标识
	 */
	public UserExcution addUser(String username,String password);
	
	/**
	 * 根据用户id，变更用户的操作员姓名和权限
	 * @param userId  用户id
	 * @param emplyoeeName  变更的操作员姓名
	 * @param userType  变更的用户权限
	 * @return 操作标识
	 */
//	public UserExcution editUserMes(long userId,String emplyoeeName,int userType);
//
//	/**
//	 * 根据用户名和旧密码，验证是否正确后修改为新的密码
//	 * @param username  用户名
//	 * @param oldPassword  旧密码
//	 * @param newPassword  新密码
//	 * @return  操作标识
//	 */
	public UserExcution editUserPassword(String username,String oldPassword,String newPassword);
	/**
	 * 根据用户id删除该用户
	 * @param userId  用户id
	 * @return  操作标识
	 */
//	public UserExcution removeUser(long userId);
//	/**
//	 * 根据用户名和密码获取用户
//	 * @param username  用户名
//	 * @param password  密码
//	 * @return  带用户信息的操作标识
//	 */
	public UserExcution getUser(String username,String password);
	/**
	 * 分页获取用户列表
	 * @param pageIndex 页码
	 * @param pageSize 页面大小
	 * @return  带用户列表信息的操作标识
	 */
	public UserExcution getUserList(int pageIndex,int pageSize);
}
