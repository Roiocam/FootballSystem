package club.pypzx.FootballSystem;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import tk.mybatis.spring.annotation.MapperScan;

@RunWith(SpringRunner.class)
@MapperScan("club.pypzx.FootballSystem.dao")
@SpringBootTest()
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class BaseTestTemplate {
	@Test
	public void testAInsert() {
	}

	@Test
	public void testBQueryAll() {
	}

	@Test
	public void testCUpdate() {
	}

	@Test
	public void testDQueryOne() {
	}

	@Test
	public void testEDelelte() {
	}
}
