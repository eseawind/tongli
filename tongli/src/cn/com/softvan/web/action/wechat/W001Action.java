/*
 * 微信服务_微信号绑定  ActionClass
 *
 * VERSION  DATE        BY              REASON
 * -------- ----------- --------------- ------------------------------------------
 * 1.00     2014.03.04  wuxiaogang      程序・发布
 * -------- ----------- --------------- ------------------------------------------
 * Copyright 2014 车主管家  System. - All Rights Reserved.
 *
 */
package cn.com.softvan.web.action.wechat;

import org.apache.log4j.Logger;

import cn.com.softvan.bean.BaseUserBean;
import cn.com.softvan.bean.wechat.TcWxPublicUserBean;
import cn.com.softvan.common.CommonConstant;
import cn.com.softvan.service.wechat.ITcWxPublicUserManager;
import cn.com.softvan.web.action.BaseAction;

/**
 * 微信服务_微信号绑定 ActionClass
 * 
 * @author wuxiaogang
 * 
 */
public class W001Action extends BaseAction {

	/**
	 * 序列号
	 */
	private static final long serialVersionUID = -3061791975484213551L;
	private static final transient Logger log = Logger
			.getLogger(W001Action.class);
	/**BEAN类  微信公共号信息*/
	private TcWxPublicUserBean bean;
	/**微信服务_公共账号 service*/
	private ITcWxPublicUserManager tcWxPublicUserManager;
	//
	public W001Action() {
		log.info("默认构造器......W001Action");
	}

	/**
	 * <p>
	 * 初始化处理。
	 * </p>
	 * <ol>
	 * [功能概要] <div>初始化处理。</div>
	 * </ol>
	 * 
	 * @return 转发字符串
	 */
	public String init() {
		log.info("W001Action init.........");
		TcWxPublicUserBean bean1=new TcWxPublicUserBean();
		BaseUserBean user = (BaseUserBean) request.getSession().getAttribute(CommonConstant.SESSION_KEY_USER);
		bean1.setCreate_id(user.getUser_id());
		this.bean=tcWxPublicUserManager.findDataById(bean1);
		return "init";
	}
	/**
	 * <p>
	 * 服务号绑定。
	 * </p>
	 * <ol>
	 * [功能概要] <div>绑定。</div>
	 * </ol>
	 * 
	 * @return 转发字符串
	 */
	public String save() {
		log.info("W001Action save.........");
		BaseUserBean user = (BaseUserBean) request.getSession().getAttribute(CommonConstant.SESSION_KEY_USER);
		if(user!=null){
			bean.setCreate_id(user.getUser_id());
			bean.setCreate_ip(getIpAddr());
			bean.setUpdate_id(user.getUser_id());
			bean.setUpdate_ip(getIpAddr());
		}
		String msg=tcWxPublicUserManager.saveOrUpdateData(this.bean);
		request.setAttribute("msg",msg);
		
		return SUCCESS;
	}

	/**
	 * BEAN类  微信公共号信息取得
	 * @return BEAN类  微信公共号信息
	 */
	public TcWxPublicUserBean getBean() {
	    return bean;
	}

	/**
	 * BEAN类  微信公共号信息设定
	 * @param bean BEAN类  微信公共号信息
	 */
	public void setBean(TcWxPublicUserBean bean) {
	    this.bean = bean;
	}

	/**
	 * 微信服务_公共账号 service取得
	 * @return 微信服务_公共账号 service
	 */
	public ITcWxPublicUserManager getTcWxPublicUserManager() {
	    return tcWxPublicUserManager;
	}

	/**
	 * 微信服务_公共账号 service设定
	 * @param tcWxPublicUserManager 微信服务_公共账号 service
	 */
	public void setTcWxPublicUserManager(ITcWxPublicUserManager tcWxPublicUserManager) {
	    this.tcWxPublicUserManager = tcWxPublicUserManager;
	}
}
