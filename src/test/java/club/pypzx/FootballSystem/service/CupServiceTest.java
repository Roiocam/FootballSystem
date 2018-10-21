package club.pypzx.FootballSystem.service;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import club.pypzx.FootballSystem.BaseTestTemplate;
import club.pypzx.FootballSystem.dto.BaseExcution;

public class CupServiceTest extends BaseTestTemplate{
	@Autowired
	private CupService service;
	
	@Test
	public void testSelect() {
		BaseExcution queryAll = service.queryAll(1,55);
		System.out.println(queryAll);
	}

}
