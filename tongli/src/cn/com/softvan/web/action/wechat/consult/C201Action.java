/*
 * 咨询  ActionClass
 *
 * VERSION  DATE        BY              REASON
 * -------- ----------- --------------- ------------------------------------------
 * 1.00     2014.04.04  wuxiaogang      程序・发布
 * -------- ----------- --------------- ------------------------------------------
 * Copyright 2014 童励  System. - All Rights Reserved.
 *
 */
package cn.com.softvan.web.action.wechat.consult;

import org.apache.log4j.Logger;

import cn.com.softvan.bean.BaseUserBean;
import cn.com.softvan.bean.consult.TcCsConsultBean;
import cn.com.softvan.common.CommonConstant;
import cn.com.softvan.service.consult.IConsultManager;
import cn.com.softvan.web.action.BaseAction;

/**
 * 咨询 ActionClass
 * 
 * @author wuxiaogang
 * 
 */
public class C201Action extends BaseAction {

	/**
	 * 序列号
	 */
	private static final long serialVersionUID = -3061791975484213551L;
	private static final transient Logger log = Logger
			.getLogger(C201Action.class);
	/**BEAN类  咨询信息*/
	private TcCsConsultBean bean;
	/**咨询 service*/
	private IConsultManager consultManager;
	//
	public C201Action() {
		log.info("默认构造器......C201Action");
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
		log.info("C201Action init.........");
		TcCsConsultBean bean1=new TcCsConsultBean();
		BaseUserBean user = (BaseUserBean) request.getSession().getAttribute(CommonConstant.SESSION_KEY_USER);
		bean1.setCreate_id(user.getUser_id());
		this.bean=consultManager.findDataById(bean1);
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
		log.info("C201Action save.........");
		BaseUserBean user = (BaseUserBean) request.getSession().getAttribute(CommonConstant.SESSION_KEY_USER);
		if(user!=null){
			bean.setCreate_id(user.getUser_id());
			bean.setCreate_ip(getIpAddr());
			bean.setUpdate_id(user.getUser_id());
			bean.setUpdate_ip(getIpAddr());
		}
		String msg=consultManager.saveOrUpdateData(this.bean);
		request.setAttribute("msg",msg);
		
		return SUCCESS;
	}
}
