package club.pypzx.FootballSystem.template;

import java.util.List;

/**
 * 通用业务层模版接口
 * 
 * @author Roiocam
 * @date 2019年1月5日 下午3:32:53
 * @param <T>
 */
public interface BaseService<T> {
	/**
	 * 新增一条记录
	 * 
	 * @param obj
	 * @return
	 */
	public BaseExcution<T> add(T obj);

	/**
	 * 更新一条记录
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	public BaseExcution<T> edit(T obj) throws Exception;;

	/**
	 * 查找指定id的对象
	 * 
	 * @param objId
	 * @return
	 */
	public BaseExcution<T> findById(String objId);

	/**
	 * 根据条件查找指定的一个对象
	 * 
	 * @param obj
	 * @return
	 */
	public BaseExcution<T> findByCondition(T obj);

	/**
	 * 查找所有对象
	 * 
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	public BaseExcution<T> findAll(int pageIndex, int pageSize);

	/**
	 * 删除一个记录
	 * 
	 * @param objId
	 * @return
	 * @throws Exception
	 */
	public BaseExcution<T> removeById(String objId) throws Exception;

	/**
	 * 删除一个集合的数据
	 * 
	 * @param list
	 * @return
	 * @throws Exception
	 */
	public BaseExcution<T> removeByIdList(List<String> list) throws Exception;
}
