package club.pypzx.FootballSystem.dao;

import static org.junit.Assert.assertEquals;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.junit.Ignore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import club.pypzx.FootballSystem.BaseTestTemplate;
import club.pypzx.FootballSystem.dto.BaseExcution;
import club.pypzx.FootballSystem.dto.GroupVo;
import club.pypzx.FootballSystem.entity.Cup;
import club.pypzx.FootballSystem.entity.Game;
import club.pypzx.FootballSystem.entity.Group;
import club.pypzx.FootballSystem.service.TeamService;
import club.pypzx.FootballSystem.utils.GameDayUtils;
import club.pypzx.FootballSystem.utils.IDUtils;

public class CupTest extends BaseTestTemplate {
	@Autowired
	private CupMapper mapper;
	@Autowired
	private TeamService service;
	@Autowired
	private GroupMapper groupMapper;
	@Autowired
	private GameMapper gameMapper;

	@Override
	public void testAInsert() {
//		Cup c1 = new Cup();
//		c1.setCupId(IDUtils.getUUID());
//		c1.setCupName("测试比赛1");
//		Cup c2 = new Cup();
//		c2.setCupId(IDUtils.getUUID());
//		c2.setCupName("测试比赛2");
//		int insert = mapper.insert(c1);
//		insert += mapper.insert(c2);
//		for(int i=0;i<1000;i++) {
//			Cup c1 = new Cup();
//			c1.setCupId(IDUtils.getUUID());
//			c1.setCupName("测试赛事"+i);
//			mapper.insert(c1);
//		}
//		assertEquals(2, insert);
		
		System.out.println("完成");
		
	}

	

	@Override
	@Ignore
	public void testBQueryAll() {
		List<Cup> selectAll = mapper.selectAll();
		System.out.println(selectAll.get(selectAll.size() - 1).getCupName());
		assertEquals(2, selectAll.size());

	}

	@Override
	@Ignore
	public void testCUpdate() {
//		List<Cup> selectAll = mapper.selectAll();
//		Cup cup = selectAll.get(0);
//		cup.setCupName("更新测试");
//		int updateByPrimaryKey = mapper.updateByPrimaryKey(cup);
//		assertEquals(1, updateByPrimaryKey);

	}

	@Override
	@Ignore
	public void testDQueryOne() {
//		Cup cup = new Cup();
//		cup.setCupName("更新测试");
//		Cup selectOne = mapper.selectOne(cup);
//		assertEquals("更新测试", selectOne.getCupName());

	}

	public void testEDelelte() {
//		List<Cup> selectAll = mapper.selectAll();
//		Iterator<Cup> iterator = selectAll.iterator();
//		int result = 0;
//		while (iterator.hasNext()) {
//			result += mapper.deleteByPrimaryKey(iterator.next());
//		}
//		assertEquals(2, result);
		List<Cup> selectAll = mapper.selectAll();
		Iterator<Cup> iterator = selectAll.iterator();
		while (iterator.hasNext()) {
			Cup next = iterator.next();
			if(next.getCupName()!="测试所用赛事") {
				mapper.deleteByPrimaryKey(next);
			}
			
		}
	}

}
