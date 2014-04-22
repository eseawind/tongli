/*
 * 微信服务_关注者账号 service 接口类
 *
 * VERSION  DATE        BY              REASON
 * -------- ----------- --------------- ------------------------------------------
 * 1.00     2014.03.20  wuxiaogang      程序・发布
 * -------- ----------- --------------- ------------------------------------------
 * Copyright 2014 童励 System. - All Rights Reserved.
 *
 */
package cn.com.softvan.service.wechat;

import java.util.List;

import cn.com.softvan.bean.wechat.TcWxUserBean;


/**
 * 微信服务_关注者账号  service 接口类
 * @author wuxiaogang
 *
 */
public interface ITcWxUserManager {
	/**
	 * <p>信息编辑。</p>
	 * <ol>[功能概要] 
	 * <div>新增信息。</div>
	 * <div>修改信息。</div>
	 * </ol>
	 * @return 处理结果
	 * @throws Exception 
	 */
	public void saveOrUpdateData(TcWxUserBean bean) throws Exception;
	/**
	 * <p>信息分页。</p>
	 * <ol>[功能概要] 
	 * <div>信息检索。</div>
	 * <div>分页。</div>
	 * </ol>
	 * @return 处理结果
	 */
	public List<TcWxUserBean> findDataIsPage(TcWxUserBean bean);
	/**
	 * <p>信息列表。</p>
	 * <ol>[功能概要] 
	 * <div>信息检索。</div>
	 * <div>列表。</div>
	 * </ol>
	 * @return 处理结果
	 */
	public List<TcWxUserBean> findDataIsList(TcWxUserBean bean);
	/**
	 * <p>信息详情。</p>
	 * <ol>[功能概要] 
	 * <div>信息检索。</div>
	 * <div>详情。</div>
	 * </ol>
	 * @return 处理结果
	 */
	public TcWxUserBean findDataById(TcWxUserBean bean);
	/**
	 * <p>下载关注者(粉丝信息)。</p>
	 * <ol>[功能概要] 
	 * <div>保存入库。</div>
	 * </ol>
	 * @return 处理结果
	 */
	public String downUser();
}
