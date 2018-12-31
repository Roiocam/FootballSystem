package club.pypzx.FootballSystem.dao.mybatis;

import org.apache.ibatis.annotations.Select;

import club.pypzx.FootballSystem.entity.KickDay;
import club.pypzx.FootballSystem.template.BaseMapper;

/**
 * 管理每日踢球人数记录的持久层
 * 
 * @author Roiocam
 * @date 2018年9月15日 下午8:57:35
 */
public interface KickDayMapper extends BaseMapper<KickDay>{
	@Select("select IFNULL(date,curdate()) date,IFNULL(num,0) num from pypzx_kick_day where date=curdate()")
	public KickDay queryKickToday();

}
