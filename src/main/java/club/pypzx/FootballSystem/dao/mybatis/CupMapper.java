package club.pypzx.FootballSystem.dao.mybatis;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import club.pypzx.FootballSystem.entity.Cup;
import club.pypzx.FootballSystem.template.BaseMapper;

@Mapper
public interface CupMapper extends BaseMapper<Cup>{
	@Select("SELECT * FROM pypzx_cup  WHERE cup_id=#{value} LIMIT 1")
	public Cup selectByPrimary(String cupId);

}