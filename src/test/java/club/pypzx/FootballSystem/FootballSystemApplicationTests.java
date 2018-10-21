package club.pypzx.FootballSystem;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import tk.mybatis.spring.annotation.MapperScan;

@RunWith(SpringRunner.class)
@MapperScan("club.pypzx.FootballSystem.dao")
@SpringBootTest
public class FootballSystemApplicationTests {

	@Test
	public void contextLoads() {
	}

}
