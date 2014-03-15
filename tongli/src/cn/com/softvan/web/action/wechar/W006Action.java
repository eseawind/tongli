/*
 * 微信服务_自动回复(关键字)_视频消息 ActionClass
 *
 * VERSION  DATE        BY              REASON
 * -------- ----------- --------------- ------------------------------------------
 * 1.00     2014.03.04  wuxiaogang      程序・发布
 * -------- ----------- --------------- ------------------------------------------
 * Copyright 2014 车主管家  System. - All Rights Reserved.
 *
 */
package cn.com.softvan.web.action.wechar;

import java.util.List;

import org.apache.log4j.Logger;

import cn.com.softvan.bean.wechar.TcWxInfoBean;
import cn.com.softvan.service.wechar.ITcWxInfoManager;
import cn.com.softvan.web.action.BaseAction;

/**
 * 微信服务_自动回复(关键字)_视频消息 ActionClass
 * 
 * @author wuxiaogang
 * 
 */
public class W006Action extends BaseAction {

	/**
	 * 序列号
	 */
	private static final long serialVersionUID = -3061791975484213551L;
	private static final transient Logger log = Logger.getLogger(W006Action.class);
	
	/**BEAN类  微信资源信息*/
	private TcWxInfoBean bean;
	/**BEAN类  微信资源信息 集合*/
	private List<TcWxInfoBean> beans;
	/**微信服务_资源信息管理 业务处理*/
	private ITcWxInfoManager tcWxInfoManager;
	//
	public W006Action() {
		log.info("默认构造器......W006Action");
	}

	/**
	 * <p>
	 * 初始化处理。
	 * </p>
	 * <ol>
	 * [功能概要] <div>初始化处理。</div>
	 * </ol>
	 * @return 转发字符串
	 */
	public String init() {
		log.info("W006Action init.........");
		return "init";
	}
	/**
	 * <p>
	 * 编辑。
	 * </p>
	 * <ol>
	 * [功能概要] <div>编辑。</div>
	 * </ol>
	 * @return 转发字符串
	 */
	public String edit() {
		log.info("W006Action edit.........");
		
		return "edit";
	}
	/**
	 * <p>
	 * 删除。
	 * </p>
	 * <ol>
	 * [功能概要] <div>逻辑删除。</div>
	 * </ol>
	 * @return 转发字符串
	 */
	public String del() {
		log.info("W006Action del.........");
		
		
		return SUCCESS;
	}
	/**
	 * <p>
	 * 预览。
	 * </p>
	 * <ol>
	 * [功能概要] <div>预览。</div>
	 * </ol>
	 * @return 转发字符串
	 */
	public String view() {
		log.info("W006Action view.........");
		return "view";
	}

	/**
	 * BEAN类  微信资源信息取得
	 * @return BEAN类  微信资源信息
	 */
	public TcWxInfoBean getBean() {
	    return bean;
	}

	/**
	 * BEAN类  微信资源信息设定
	 * @param bean BEAN类  微信资源信息
	 */
	public void setBean(TcWxInfoBean bean) {
	    this.bean = bean;
	}

	/**
	 * BEAN类  微信资源信息 集合取得
	 * @return BEAN类  微信资源信息 集合
	 */
	public List<TcWxInfoBean> getBeans() {
	    return beans;
	}

	/**
	 * BEAN类  微信资源信息 集合设定
	 * @param beans BEAN类  微信资源信息 集合
	 */
	public void setBeans(List<TcWxInfoBean> beans) {
	    this.beans = beans;
	}

	/**
	 * 微信服务_资源信息管理 业务处理取得
	 * @return 微信服务_资源信息管理 业务处理
	 */
	public ITcWxInfoManager getTcWxInfoManager() {
	    return tcWxInfoManager;
	}

	/**
	 * 微信服务_资源信息管理 业务处理设定
	 * @param tcWxInfoManager 微信服务_资源信息管理 业务处理
	 */
	public void setTcWxInfoManager(ITcWxInfoManager tcWxInfoManager) {
	    this.tcWxInfoManager = tcWxInfoManager;
	}
}
