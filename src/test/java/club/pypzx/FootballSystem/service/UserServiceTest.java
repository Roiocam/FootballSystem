package club.pypzx.FootballSystem.service;


import static org.junit.Assert.assertEquals;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import club.pypzx.FootballSystem.BaseTestTemplate;
import club.pypzx.FootballSystem.dto.UserExcution;
import club.pypzx.FootballSystem.enums.UserStateEnum;

public class UserServiceTest extends BaseTestTemplate {
	@Autowired
	private UserService userService;
	
	 
	@Test
	public void testAinsert() {

		UserExcution addUser = userService.addUser("asd","asd");
		assertEquals(UserStateEnum.SUCCESS.getState(),addUser.getState());
	}
	@Test
	@Ignore

	public void testBQuery() {
//		UserExcution user = userService.getUser("test2", "test2");
//		assertEquals(UserStateEnum.SUCCESS.getState(),user.getState());
	}

	@Test
	@Ignore

	public void testDDelete() {
//		 UserExcution userList = userService.getUserList(1, 10);
//		 assertEquals(UserStateEnum.SUCCESS.getState(),userList.getState());
//		int deleteUser=0;
//		for(User u:userList.getUserList()) {
//			if(u.getUsername().equals("test1"))  continue;
//			UserExcution removeUser = userService.removeUser(u.getUsername());
//			 assertEquals(UserStateEnum.SUCCESS.getState(),removeUser.getState());
//		}
	}

	
}
