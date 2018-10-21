package club.pypzx.FootballSystem.dao;

import static org.junit.Assert.assertEquals;

import java.awt.Panel;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import club.pypzx.FootballSystem.BaseTestTemplate;
import club.pypzx.FootballSystem.entity.Player;
import club.pypzx.FootballSystem.entity.PlayerRank;
import club.pypzx.FootballSystem.entity.Team;
import club.pypzx.FootballSystem.entity.TeamRank;

public class PlayerRankTest extends BaseTestTemplate {
	@Autowired
	private PlayerRankMapper mapper;
	@Autowired
	private PlayerMapper playerMapper;

	@Override
	public void testAInsert() {
		Iterator<Player> iterator = playerMapper.selectAll().iterator();
		int result = 0;
		while (iterator.hasNext()) {
			Player next = iterator.next();
			PlayerRank t = new PlayerRank();
			t.setPlayerId(next.getPlayerId());
			t.setGoals(0);
			t.setPenalty(2);
			result += mapper.insert(t);
		}
		assertEquals(2, result);
	}

	@Override
	public void testBQueryAll() {
		List<PlayerRank> selectAll = mapper.selectAll();
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
		PlayerRank pr=new PlayerRank();
		pr.setPlayerId("153902055896434908");
		int delete = mapper.delete(pr);
		System.out.println(delete);
	}

}
