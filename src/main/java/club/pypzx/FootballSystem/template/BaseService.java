package club.pypzx.FootballSystem.template;

import java.util.List;

public interface BaseService<T> {
	public BaseExcution<T> add(T obj);

	public BaseExcution<T> edit(T obj) throws Exception;;

	public BaseExcution<T> findById(String objId);

	public BaseExcution<T> findByCondition(T obj);

	public BaseExcution<T> findAll(int pageIndex, int pageSize);

	public BaseExcution<T> removeById(String objId) throws Exception;

	public BaseExcution<T> removeByIdList(List<String> list) throws Exception;
}
