package club.pypzx.FootballSystem.template;

import java.util.List;

public class BaseExcution<T> {
	// 结果状态
	private int state;

	// 状态标识
	private String stateInfo;

	// 改变数量
	private int count;

	private T obj;

	private List<T> objList;

	public BaseExcution() {
	}

	// 失败的构造器
	public BaseExcution(BaseStateEnum stateEnum) {
		this.state = stateEnum.getState();
		this.stateInfo = stateEnum.getStateInfo();
	}

	// 成功的构造器
	public BaseExcution(BaseStateEnum stateEnum, T obj) {
		this.state = stateEnum.getState();
		this.stateInfo = stateEnum.getStateInfo();
		this.obj = obj;
	}

	// 成功的构造器
	public BaseExcution(BaseStateEnum stateEnum, List<T> objList, int count) {
		this.state = stateEnum.getState();
		this.stateInfo = stateEnum.getStateInfo();
		this.objList = objList;
		this.count = count;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public String getStateInfo() {
		return stateInfo;
	}

	public void setStateInfo(String stateInfo) {
		this.stateInfo = stateInfo;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public T getObj() {
		return obj;
	}

	public void setObj(T obj) {
		this.obj = obj;
	}

	public List<T> getObjList() {
		return objList;
	}

	public void setObjList(List<T> objList) {
		this.objList = objList;
	}

}
