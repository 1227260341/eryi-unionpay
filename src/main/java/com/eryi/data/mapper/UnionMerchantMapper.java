package com.eryi.data.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.eryi.data.domain.UnionMerchant;


/**
 * <p>
 * 银联商户 Mapper 接口
 * </p>
 *
 * @author zhouzhenjang123
 * @since 2018-11-19
 */
@Mapper
public interface UnionMerchantMapper{

	/**
	 * 根据id获取
	 *
	 * @author zhouzhenjang123
	 * @since 2018-11-19
	 */
	@Select("SELECT * FROM union_merchant WHERE id = #{id}")
	public UnionMerchant get(@Param("id") int id);
	
	@Select("SELECT * FROM union_merchant WHERE app_id = #{appId} and valid_flag = 'Y'")
	public UnionMerchant getByAppId(@Param("appId") int appId);

	/**
	 * 获取列表
	 *
	 * @author zhouzhenjang123
	 * @since 2018-11-19
	 */
	public List<UnionMerchant> queryByPage(UnionMerchant unionMerchant);
	
	/**
	 * 添加方法
	 *
	 * @author zhouzhenjang123
	 * @since 2018-11-19
	 */
	public int add(UnionMerchant unionMerchant);
	
	/**
	 * 设置无效（ID 为int 类型）
	 *
	 * @author zhouzhenjang123
	 * @since 2018-11-19
	 */
	public int update(UnionMerchant unionMerchant);
	
	/**
	 * 更新方法
	 *
	 * @author zhouzhenjang123
	 * @since 2018-11-19
	 */
	public int setInvalidWithInt(int id);
	
	/**
	 * 设置无效（ID 为string 类型）
	 *
	 * @author zhouzhenjang123
	 * @since 2018-11-19
	 */
	public int setInvalidWithString(String id);
	
}
