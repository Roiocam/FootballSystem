package club.pypzx.FootballSystem.dao.mybatis;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import club.pypzx.FootballSystem.dto.PlayerVo;
import club.pypzx.FootballSystem.entity.Page;
import club.pypzx.FootballSystem.entity.Player;

public interface PlayerMapper {
	public List<PlayerVo> selectAllByPage(@Param("example") Player example, @Param("page") Page page);

	public PlayerVo selectByPrimary(String playerId);

	public int selectCountByPrimary(String playerId);

	public int insert(Player obj);

	public int delete(Player player);

	public int updateByPrimaryKey(Player obj);

	public Player selectByPrimaryKey(String objId);

	public Player selectOne(Player obj);

	public List<Player> selectByRowBounds(Object object, RowBounds rowBounds);

	public int selectCount(Object object);

	public List<Player> select(Player p);
}