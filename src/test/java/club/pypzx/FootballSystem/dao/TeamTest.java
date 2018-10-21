package club.pypzx.FootballSystem.dao;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Ignore;
import org.springframework.beans.factory.annotation.Autowired;

import club.pypzx.FootballSystem.BaseTestTemplate;
import club.pypzx.FootballSystem.dto.TeamVo;
import club.pypzx.FootballSystem.entity.Page;
import club.pypzx.FootballSystem.entity.Team;
import club.pypzx.FootballSystem.utils.IDUtils;

public class TeamTest extends BaseTestTemplate {
	@Autowired
	private TeamMapper mapper;
	@Autowired
	private CupMapper cupMapper;

	@Override
	
	public void testAInsert() {
		Team t = new Team();
		t.setTeamId(IDUtils.getUUID());
		String cupId = cupMapper.selectAll().get(0).getCupId();
		t.setCupId(cupId);
		t.setTeamName("测试队伍1");
		t.setVaildCode("12345");
		Team t2 = new Team();
		t2.setTeamId(IDUtils.getUUID());
		t2.setCupId(cupId);
		t2.setTeamName("测试队伍2");
		t2.setVaildCode("ascfqf");
		int insert = mapper.insert(t);
		insert += mapper.insert(t2);
		assertEquals(insert, 2);


	}

	@Override
	@Ignore
	public void testBQueryAll() {
		List<Team> selectAll = mapper.selectAll();
		System.out.println(selectAll.get(0).getTeamName());
		assertEquals(selectAll.size(),2);
//		List<TeamVo> selectAllByPage = mapper.selectAllByPage(null, Page.getInstance(1,50));
//		System.out.println(selectAllByPage);
	}

	@Override
	@Ignore
	public void testCUpdate() {
		Team c=mapper.selectAll().get(0);
		c.setTeamName("修改测试");
		c.setLeaderId("asd");
		int updateByPrimaryKey = mapper.updateByPrimaryKey(c);
		assertEquals(updateByPrimaryKey, 1);

	}

	@Override
	@Ignore
	public void testDQueryOne() {
		Team t=new Team();
		t.setTeamName("修改测试");
		Team selectOne = mapper.selectOne(t);
		assertEquals("修改测试", selectOne.getTeamName());
	}

	@Override
	@Ignore
	public void testEDelelte() {
//		List<Team> selectAll = mapper.selectAll();
//		Iterator<Team> iterator = selectAll.iterator();
//		int result = 0;
//		while (iterator.hasNext()) {
//			result += mapper.deleteByPrimaryKey(iterator.next());
//		}
//		assertEquals(2, result);

	}

}
