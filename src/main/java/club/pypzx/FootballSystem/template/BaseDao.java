package club.pypzx.FootballSystem.template;

import java.util.List;

/**
 * 统计持久层接口，定义基础方法
 * 
 * @author Roiocam
 * @date 2019年1月5日 下午3:26:56
 * @param <T> 泛型
 */
public interface BaseDao<T> {
	/**
	 * 在数据库中新增一条记录
	 * 
	 * @param obj 泛型对象 
	 * @throws Exception 事务异常
	 */
	public void add(T obj) throws Exception;

	/**
	 * 根据主键更新数据库记录
	 * 
	 * @param obj
	 * 	泛型对象
	 * @throws Exception
	 * 	 事务异常
	 */
	public void edit(T obj) throws Exception;

	/**
	 * 根据主键删除数据库记录
	 * 
	 * @param objId 
	 * 	主键Id
	 * @throws Exception
	 * 	 事务异常
	 */
	public void remove(String objId) throws Exception;

	/**
	 * 分页查找所有对象表数据库记录
	 * 
	 * @param pageIndex
	 * 	页码
	 * @param pageSize
	 * 页面大小
	 * @return
	 * 集合数据
	 */
	public List<T> findAll(int pageIndex, int pageSize);

	/**
	 * 根据主键查找指定数据库记录
	 * 
	 * @param objId
	 * @return
	 */
	public T findById(String objId);

	/**
	 * 根据符合条件查找指定数据库记录（只返回一条）
	 * 
	 * @param obj
	 *  泛型对象
	 * @return
	 * 完整数据
	 */
	public T findByCondition(T obj);

	/**
	 * 根据符合条件查找指定数据库记录（返回多条）
	 * 
	 * @param T obj
	 * 泛型对象
	 * @return
	 * 集合数据
	 */
	public List<T> findAllCondition(T obj);

	/**
	 * 获取当前对象的数据库表大小
	 * 
	 * @return
	 * 数据库表大小
	 */
	public int count();

	/**
	 * 根据条件获取当前对象的数据库表大小
	 * 
	 * @param obj
	 * 泛型对象
	 * @return
	 * 带条件的数据库集合大小
	 */
	public int countExmaple(T obj);
}
