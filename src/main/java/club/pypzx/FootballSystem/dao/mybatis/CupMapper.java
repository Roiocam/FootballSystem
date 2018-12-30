package club.pypzx.FootballSystem.dao.mybatis;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.session.RowBounds;

import club.pypzx.FootballSystem.dao.mybatis.sqlProvider.DeleteSQLProvider;
import club.pypzx.FootballSystem.dao.mybatis.sqlProvider.InsertSQLProvider;
import club.pypzx.FootballSystem.entity.Cup;

@Mapper
public interface CupMapper {
	@InsertProvider(type = InsertSQLProvider.class, method = "insert")
	public int insert(Cup cup);

	@Delete("DELETE FROM pypzx_team where cup_id = #{value}")
	public int deleteTeamByCup(String cupId);

	@Update("UPDATE pypzx_cup SET is_group = 1 WHERE cup_id=#{value}")
	public int updateCupGrouped(String cupId);

	@Update("UPDATE pypzx_cup SET is_group = 0 WHERE cup_id=#{value}")
	public int updateCupNotGroup(String cupId);

	@InsertProvider(type = DeleteSQLProvider.class, method = "delete")
	public int delete(Cup cup);

	public int updateByPrimaryKey(Cup obj);

	public Cup selectOne(Cup obj);

	public Cup selectByPrimaryKey(String objId);

	public List<Cup> selectByRowBounds(Object object, RowBounds rowBounds);

	public int selectCount(Object object);
}