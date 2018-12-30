package club.pypzx.FootballSystem.template;

import java.util.List;

public interface BaseService<T> {
	public BaseExcution<T> insertObj(T obj);

	public BaseExcution<T> updateObjByPrimaryKey(T obj)  throws Exception;;

	public BaseExcution<T> queryObjOneByPrimaryKey(String objId);

	public BaseExcution<T> queryObjOne(T obj);

	public BaseExcution<T> queryAll(int pageIndex, int pageSize);

	public BaseExcution<T> deleteObjByPrimaryKey(String objId) throws Exception;

	public BaseExcution<T> deleteObjectList(List<String> list) throws Exception;
}
