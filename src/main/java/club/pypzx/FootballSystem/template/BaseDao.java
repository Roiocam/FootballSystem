package club.pypzx.FootballSystem.template;

import java.util.List;

public interface BaseDao<T> {
	public void add(T obj) throws Exception;

	public void edit(T obj) throws Exception;

	public void remove(String objId) throws Exception;

	public List<T> findAll(int pageIndex, int pageSize);

	public T findById(String objId);

	public T findByCondition(T obj);

	public List<T> findAllCondition(T obj);

	public int count();

	public int countExmaple(T obj);
}
