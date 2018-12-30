package club.pypzx.FootballSystem.template;


import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

/**
 *   公用Mapper接口
 * @author Roiocam
 * @date 2018年10月5日 下午7:03:50
 * @param <T>
 */
public interface BaseMapper<T> extends Mapper<T>,MySqlMapper<T> {

}