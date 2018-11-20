package com.eryi.data.mapper;


import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.eryi.data.domain.UnionPay;


/**
 * <p>
 * 银联支付记录 Mapper 接口
 * </p>
 *
 * @author zhouzhenjang123
 * @since 2018-11-15
 */
@Mapper
public interface UnionPayMapper {

	/**
	 * 根据id获取
	 *
	 * @author zhouzhenjang123
	 * @since 2018-11-15
	 */
	@Select("SELECT * FROM union_pay WHERE id = #{id}")
	public UnionPay get(@Param("id") int id);
	
	@Select("SELECT * FROM union_pay WHERE query_id = #{queryId}")
	public UnionPay getByQueryId(@Param("queryId") String queryId);
	

	/**
	 * 获取列表
	 *
	 * @author zhouzhenjang123
	 * @since 2018-11-15
	 */
	public List<UnionPay> queryByPage(UnionPay unionPay);
	
	/**
	 * 添加方法
	 *
	 * @author zhouzhenjang123
	 * @since 2018-11-15
	 */
	public int add(UnionPay unionPay);
	
	public int addForMap(Map map);
	
	/**
	 * 设置无效（ID 为int 类型）
	 *
	 * @author zhouzhenjang123
	 * @since 2018-11-15
	 */
	public int update(UnionPay unionPay);
	
	public int updateForMap(Map map);
	
	/**
	 * 更新方法
	 *
	 * @author zhouzhenjang123
	 * @since 2018-11-15
	 */
	public int setInvalidWithInt(int id);
	
	/**
	 * 设置无效（ID 为string 类型）
	 *
	 * @author zhouzhenjang123
	 * @since 2018-11-15
	 */
	public int setInvalidWithString(String id);
	
}
