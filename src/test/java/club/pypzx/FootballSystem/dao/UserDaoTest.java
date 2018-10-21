package club.pypzx.FootballSystem.dao;


import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import club.pypzx.FootballSystem.entity.User;


@RunWith(SpringRunner.class)
@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class UserDaoTest {
	@Autowired
	private UserDao userDao;
	
	 
	@Test

	public void testAinsert() {
		User u1=new User();
		u1.setUsername("test1");
		u1.setPassword("test1");
		User u2=new User();
		u2.setUsername("test2");
		u2.setPassword("test2");
		int insertUser = userDao.insertUser(u1);
		insertUser+= userDao.insertUser(u2);
		assertEquals(2,insertUser);
	}
//	@Test
//	@Ignore
//	public void testBQuery() {
//		User queryUser = userDao.queryUser("test1", "test1");
//		assertEquals("操作员1",queryUser.getEmplyoeeName());
//	}

	@Test
	@Ignore

	public void testDDelete() {
		List<User> queryUserList = userDao.queryUserList(0, 99);
		assertEquals(2,queryUserList.size());
		int deleteUser=0;
		for(User u:queryUserList) {
			if(u.getUsername().equals("test1"))  continue;
			 deleteUser += userDao.deleteUser(u.getUsername());
		}
		assertEquals(1,deleteUser);
	}

	
}
