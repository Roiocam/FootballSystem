package club.pypzx.FootballSystem.entity;

import org.apache.ibatis.session.RowBounds;

/**
 * 分页工厂类
 * 
 * @author Roiocam
 * @date 2018年10月8日 下午3:02:08
 */
public class Page {

	private Page() {
	}

	public static RowBounds getInstance(int pageIndex, int pageSize) {
		return new RowBounds((pageIndex - 1) * 10, pageSize);
	}
}
