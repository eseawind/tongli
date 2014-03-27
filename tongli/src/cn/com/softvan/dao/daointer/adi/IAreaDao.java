/*
 * 接口Dao类  行政区划信息表
 *
 * VERSION  DATE        BY              REASON
 * -------- ----------- --------------- ------------------------------------------
 * 1.00     2014.03.26  wuxiaogang      程序发布
 * -------- ----------- --------------- ------------------------------------------
 * Copyright 2014 jfq  System. - All Rights Reserved.
 *
 */
package cn.com.softvan.dao.daointer.adi;

import java.util.List;
import java.util.Map;

import cn.com.softvan.bean.adi.AreaBean;
/**
 * <p>  行政区划信息表 Dao类 <p>
 * @author wuxiaogang
 *
 */
public interface IAreaDao {
	/**
	 * <p>信息列表。</p>
	 * <ol>[功能概要] 
	 * <div>信息检索。</div>
	 * <div>列表 省。</div>
	 * </ol>
	 * @return 处理结果
	 */
	public List<AreaBean> findDataIsListA(Map<String,String> map);
	/**
	 * <p>信息列表。</p>
	 * <ol>[功能概要] 
	 * <div>信息检索。</div>
	 * <div>列表 市。</div>
	 * </ol>
	 * @return 处理结果
	 */
	public List<AreaBean> findDataIsListB(Map<String,String> map);
	/**
	 * <p>信息列表。</p>
	 * <ol>[功能概要] 
	 * <div>信息检索。</div>
	 * <div>列表 县。</div>
	 * </ol>
	 * @return 处理结果
	 */
	public List<AreaBean> findDataIsListC(Map<String,String> map);
	/**
	 * <p>信息列表。</p>
	 * <ol>[功能概要] 
	 * <div>信息检索。</div>
	 * <div>列表 镇。</div>
	 * </ol>
	 * @return 处理结果
	 */
	public List<AreaBean> findDataIsListD(Map<String,String> map);
	/**
	 * <p>信息列表。</p>
	 * <ol>[功能概要] 
	 * <div>信息检索。</div>
	 * <div>列表村。</div>
	 * </ol>
	 * @return 处理结果
	 */
	public List<AreaBean> findDataIsListE(Map<String,String> map);
	
}