package club.pypzx.FootballSystem.dao;

import static org.junit.Assert.assertEquals;

import java.util.Date;

import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import club.pypzx.FootballSystem.entity.KickDay;

@RunWith(SpringRunner.class)
@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class KickDayDaoTest {
	@Autowired
	private KickDayDao kickdayDao;

	@Test
	@Ignore
	public void testAinsert() throws Exception {
		KickDay kick1 = new KickDay();
		kick1.setDate(new Date());
		kick1.setNum(0);
		int insertKickDay = kickdayDao.insertKickDay(kick1);
		assertEquals(1, insertKickDay);
	}

	@Test
	@Ignore
	public void testCQuery() throws Exception {
		KickDay queryKickToday = kickdayDao.queryKickToday();
		assertEquals(0, queryKickToday.getNum());
	}

	@Test
	@Ignore
	public void testBUpdate() throws Exception {
		int updateKickDay = kickdayDao.updateKickDayPlus();
		updateKickDay += kickdayDao.updateKickDayReduce();
		assertEquals(2, updateKickDay);
	}

}
