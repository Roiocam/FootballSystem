package club.pypzx.FootballSystem.entity;

/**
 * 分页工厂类
 * 
 * @author Roiocam
 * @date 2018年10月8日 下午3:02:08
 */
public class Page {
	private static Page instance;
	private Integer index;
	private Integer size;
	private Page() {
	}

	public static Page getInstance(int pageIndex, int pageSize) {
		instance = new Page();
		instance.index = (pageIndex-1)*10;
		instance.size = pageSize;
		return instance;
	}


	public Integer getIndex() {
		return index;
	}

	public Integer getSize() {
		return size;
	}

	
}
