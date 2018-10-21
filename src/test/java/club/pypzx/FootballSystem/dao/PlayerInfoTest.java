package club.pypzx.FootballSystem.dao;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import club.pypzx.FootballSystem.BaseTestTemplate;
import club.pypzx.FootballSystem.entity.PlayerInfo;

public class PlayerInfoTest   extends BaseTestTemplate {
	@Autowired
	private PlayerInfoMapper mapper;
	@Autowired
	private PlayerMapper playerMapper;
	

	@Override
	public void testAInsert() {
		String teamId = playerMapper.selectAll().get(0).getPlayerId();
		PlayerInfo t = new PlayerInfo();
		t.setPlayerId(teamId);
		t.setPlayerDepart("管理学院");
		t.setPlayerStuno("1613420233d");
		t.setPlayerTel("132765883044");

		PlayerInfo t2 = new PlayerInfo();
		t2.setPlayerId(teamId);
		t2.setPlayerDepart("信息学院");
		t2.setPlayerStuno("1813420233");
		t2.setPlayerTel("13274588304");

		int insert = mapper.insert(t);
		insert += mapper.insert(t2);
		assertEquals(insert, 2);

	}

	@Override
	public void testBQueryAll() {
		List<PlayerInfo> selectAll = mapper.selectAll();
		System.out.println(selectAll.get(0).getPlayerDepart());
		assertEquals(selectAll.size(),2);
	}

	@Override
	public void testCUpdate() {
		PlayerInfo c=mapper.selectAll().get(0);
		c.setPlayerDepart("修改测试");
		int updateByPrimaryKey = mapper.updateByPrimaryKey(c);
		assertEquals(updateByPrimaryKey, 1);

	}

	@Override
	public void testDQueryOne() {
		PlayerInfo t=new PlayerInfo();
		t.setPlayerDepart("修改测试");
		PlayerInfo selectOne = mapper.selectOne(t);
		assertEquals("修改测试", selectOne.getPlayerDepart());
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
