package club.pypzx.FootballSystem.dao;

import static org.junit.Assert.assertEquals;

import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import club.pypzx.FootballSystem.BaseTestTemplate;
import club.pypzx.FootballSystem.entity.Team;
import club.pypzx.FootballSystem.entity.TeamRank;

public class TeamRankTest  extends BaseTestTemplate {
	@Autowired
	private TeamRankMapper mapper;
	@Autowired
	private TeamMapper teamMapper;
	
	@Override
	public void testAInsert() {
		 Iterator<Team> iterator = teamMapper.selectAll().iterator();
		 int result=0;
		 while(iterator.hasNext()) {
			 Team next = iterator.next();
			 TeamRank t=new TeamRank();
			 t.setTeamId(next.getTeamId());
			 t.setGoals(0);
			 t.setPoints(3);
			 t.setYellow(2);
			 result+=mapper.insert(t);
		 }
		 assertEquals(2, result);
	}

	@Override
	public void testBQueryAll() {
		List<TeamRank> selectAll = mapper.selectAll();
		assertEquals(2, selectAll.size());

	}

	@Override
	public void testCUpdate() {
		// TODO Auto-generated method stub

	}

	@Override
	public void testDQueryOne() {
		// TODO Auto-generated method stub

	}

	@Override
	public void testEDelelte() {
		// TODO Auto-generated method stub

	}

}
