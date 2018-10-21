package club.pypzx.FootballSystem.dao;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import club.pypzx.FootballSystem.BaseTestTemplate;
import club.pypzx.FootballSystem.entity.Player;
import club.pypzx.FootballSystem.utils.IDUtils;

public class PlayerTest   extends BaseTestTemplate {
	@Autowired
	private PlayerMapper mapper;
	@Autowired
	private TeamMapper teamMapper;
	

	@Override
	public void testAInsert() {
		Player t = new Player();
		t.setPlayerId(IDUtils.getUUID());
		String teamId = teamMapper.selectAll().get(0).getTeamId();
		t.setTeamId(teamId);
		t.setPlayerName("球员姓名1");
		t.setPlayerNum(7);

		Player t2 = new Player();
		t2.setPlayerId(IDUtils.getUUID());
		t2.setTeamId(teamId);
		t2.setPlayerName("球员姓名2");
		t2.setPlayerNum(17);

		int insert = mapper.insert(t);
		insert += mapper.insert(t2);
		assertEquals(insert, 2);

	}

	@Override
	public void testBQueryAll() {
		List<Player> selectAll = mapper.selectAll();
		System.out.println(selectAll.get(0).getPlayerName());
		assertEquals(selectAll.size(),2);
	}

	@Override
	public void testCUpdate() {
		Player c=mapper.selectAll().get(0);
		c.setPlayerName("修改测试");
		int updateByPrimaryKey = mapper.updateByPrimaryKey(c);
		assertEquals(updateByPrimaryKey, 1);

	}

	@Override
	public void testDQueryOne() {
		Player t=new Player();
		t.setPlayerName("修改测试");
		Player selectOne = mapper.selectOne(t);
		assertEquals("修改测试", selectOne.getPlayerName());
	}

	@Override
	public void testEDelelte() {
//		List<Player> selectAll = mapper.selectAll();
//		Iterator<Player> iterator = selectAll.iterator();
//		int result = 0;
//		while (iterator.hasNext()) {
//			result += mapper.deleteByPrimaryKey(iterator.next());
//		}
//		assertEquals(2, result);

	}

}
