/*
 * 微信服务_消息接收 ActionClass
 *
 * VERSION  DATE        BY              REASON
 * -------- ----------- --------------- ------------------------------------------
 * 1.00     2014.03.11  wuxiaogang      程序・发布
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
 * 微信服务_消息接收 ActionClass
 * 
 * @author wuxiaogang
 * 
 */
public class W008Action extends BaseAction {

	/**
	 * 序列号
	 */
	private static final long serialVersionUID = -3061791975484213551L;
	private static final transient Logger log = Logger.getLogger(W008Action.class);
	/**BEAN类  微信资源信息*/
	private TcWxInfoBean bean;
	/**BEAN类  微信资源信息 集合*/
	private List<TcWxInfoBean> beans;
	/**微信服务_资源信息管理 业务处理*/
	private ITcWxInfoManager tcWxInfoManager;
	//
	public W008Action() {
		log.info("默认构造器......W008Action");
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
		log.info("W008Action init.........");
		
		beans=tcWxInfoManager.findDataIsPage(null);
		
		return "init";
	}
	/**
	 * <p>
	 * 列表展示
	 * </p>
	 * <ol>
	 * [功能概要] <div>列表展示。</div>
	 * </ol>
	 * @return 转发字符串
	 */
	public String list() {
		log.info("W008Action list.........");
		return "list";
	}
	/**
	 * <p>
	 * 详情
	 * </p>
	 * <ol>
	 * [功能概要] <div>展示。</div>
	 * </ol>
	 * @return 转发字符串
	 */
	public String view() {
		log.info("W008Action view.........");
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
