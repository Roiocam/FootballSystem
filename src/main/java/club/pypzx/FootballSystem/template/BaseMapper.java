package club.pypzx.FootballSystem.template;

import java.util.List;

import org.apache.ibatis.annotations.DeleteProvider;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;
import org.apache.ibatis.session.RowBounds;

import club.pypzx.FootballSystem.dao.mybatis.sqlProvider.DeleteSQLProvider;
import club.pypzx.FootballSystem.dao.mybatis.sqlProvider.InsertSQLProvider;
import club.pypzx.FootballSystem.dao.mybatis.sqlProvider.SelectSQLProvider;
import club.pypzx.FootballSystem.dao.mybatis.sqlProvider.UpdateSQLProvider;

/**
 * 默认Mapper数据访问接口
 * 
 * @author Roiocam
 * @date 2018年12月31日 下午1:52:35
 * @param <T>
 */
public interface BaseMapper<T> {
	/**
	 * 新增，新增所有的属性，根据对象自动识别表结构
	 * 
	 * @param obj
	 * @return
	 */
	@InsertProvider(type = InsertSQLProvider.class, method = "insert")
	public int insert(T obj);

	/**
	 * 更新对象，以obj的第一个属性为主键条件，更新不为空的属性
	 * 
	 * @param obj
	 * @return
	 */
	@UpdateProvider(type = UpdateSQLProvider.class, method = "update")
	public int update(T obj);

	/**
	 * 删除对象，以obj的不为空的属性做条件
	 * 
	 * @param obj
	 * @return
	 */
	@DeleteProvider(type = DeleteSQLProvider.class, method = "delete")
	public int delete(T obj);

	/**
	 * 查询对象，返回一条数据，以obj的不为空的属性做条件
	 * 
	 * @param obj
	 * @return
	 */
	@SelectProvider(type = SelectSQLProvider.class, method = "selectPrimary")
	public T selectPrimary(T obj);

	/**
	 * 分页查询，以obj的不为空的属性做条件，若不提供RowBounds对象，则默认返回20条数据
	 * 
	 * @param obj
	 * @param RowBounds
	 * @return
	 */
	@SelectProvider(type = SelectSQLProvider.class, method = "selectRowBounds")
	public List<T> selectRowBounds(T obj, RowBounds rowBounds);

	/**
	 * 查询总数，以obj的不为空的属性做条件,与分页连用
	 * 
	 * @param obj
	 * @return
	 */
	@SelectProvider(type = SelectSQLProvider.class, method = "selectCount")
	public int selectCount(T obj);
}
