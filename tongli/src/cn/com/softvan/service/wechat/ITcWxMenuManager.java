/*
 * 微信服务_自定义菜单 service 接口类
 *
 * VERSION  DATE        BY              REASON
 * -------- ----------- --------------- ------------------------------------------
 * 1.00     2014.03.05  wuxiaogang      程序・发布
 * -------- ----------- --------------- ------------------------------------------
 * Copyright 2014 童励 System. - All Rights Reserved.
 *
 */
package cn.com.softvan.service.wechat;

import java.util.List;

import cn.com.softvan.bean.wechat.TcWxMenuBean;


/**
 * 微信服务_自定义菜单  service 接口类
 * @author wuxiaogang
 *
 */
public interface ITcWxMenuManager {
	
	/**
	 * <p>信息编辑。</p>
	 * <ol>[功能概要] 
	 * <div>新增信息。</div>
	 * <div>修改信息。</div>
	 * </ol>
	 * @return 处理结果
	 */
	public String saveOrUpdateData(TcWxMenuBean bean);
	/**
	 * <p>信息删除。</p>
	 * <ol>[功能概要] 
	 * <div>物理删除。</div>
	 * </ol>
	 * @return 处理结果
	 */
	public String deleteData(TcWxMenuBean bean);
	/**
	 * <p>信息列表。</p>
	 * <ol>[功能概要] 
	 * <div>信息检索。</div>
	 * <div>列表。</div>
	 * </ol>
	 * @return 处理结果
	 */
	public List<TcWxMenuBean> findDataIsList(TcWxMenuBean bean);
	/**
	 * <p>信息树。</p>
	 * <ol>[功能概要] 
	 * <div>信息检索。</div>
	 * <div>树。</div>
	 * </ol>
	 * @return 处理结果
	 */
	public List<TcWxMenuBean> findDataIsTree(TcWxMenuBean bean);
	/**
	 * <p>信息详情。</p>
	 * <ol>[功能概要] 
	 * <div>信息检索。</div>
	 * <div>详情。</div>
	 * </ol>
	 * @return 处理结果
	 */
	public TcWxMenuBean findDataById(TcWxMenuBean bean);
	/**
	 * <p>发布微信菜单。</p>
	 * <ol>[功能概要] 
	 * <div>发布上传。</div>
	 * </ol>
	 * @return 处理结果
	 */
	public String uploadMenu();
	/**
	 * <p>下载微信菜单。</p>
	 * <ol>[功能概要] 
	 * <div>菜单下载。</div>
	 * </ol>
	 * @return 处理结果
	 */
	public String downMenu();
}
