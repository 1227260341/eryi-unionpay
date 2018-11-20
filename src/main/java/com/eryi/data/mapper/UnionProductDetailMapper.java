package com.eryi.data.mapper;


import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.eryi.data.domain.UnionProductDetail;


/**
 * <p>
 * 银联产品详情表 Mapper 接口
 * </p>
 *
 * @author zhouzhenjang123
 * @since 2018-11-15
 */
@Mapper
public interface UnionProductDetailMapper {

	/**
	 * 根据id获取
	 *
	 * @author zhouzhenjang123
	 * @since 2018-11-15
	 */
	@Select("SELECT * FROM union_product_detail WHERE id = #{id}")
	public UnionProductDetail get(@Param("id") int id);
	
	@Select("SELECT * FROM union_product_detail WHERE order_id = #{orderId} order by txn_time desc limit 1")
	public UnionProductDetail getByOrderId(@Param("orderId") String orderId);

	/**
	 * 获取列表
	 *
	 * @author zhouzhenjang123
	 * @since 2018-11-15
	 */
	public List<UnionProductDetail> queryByPage(UnionProductDetail unionProductDetail);
	
	/**
	 * 添加方法
	 *
	 * @author zhouzhenjang123
	 * @since 2018-11-15
	 */
	public int add(UnionProductDetail unionProductDetail);
	
	public int addForMap(Map map);
	
	/**
	 * 设置无效（ID 为int 类型）
	 *
	 * @author zhouzhenjang123
	 * @since 2018-11-15
	 */
	public int update(UnionProductDetail unionProductDetail);
	
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
