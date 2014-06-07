/*
 * 咨询  ActionClass
 *
 * VERSION  DATE        BY              REASON
 * -------- ----------- --------------- ------------------------------------------
 * 1.00     2014.04.16  wuxiaogang      程序・发布
 * -------- ----------- --------------- ------------------------------------------
 * Copyright 2014 童励  System. - All Rights Reserved.
 *
 */
package cn.com.softvan.web.action.consult;

import java.io.IOException;
import java.util.List;

import org.apache.log4j.Logger;

import cn.com.softvan.bean.consult.TcCsConsultBean;
import cn.com.softvan.bean.consult.TcCsConsultMsgBean;
import cn.com.softvan.bean.customerservice.TcCsCustomerServiceBean;
import cn.com.softvan.bean.wechat.TcWxPublicUserBean;
import cn.com.softvan.bean.wechat.send.WxSendTextMsg;
import cn.com.softvan.common.CommonConstant;
import cn.com.softvan.common.Validator;
import cn.com.softvan.common.wechat.WxApiUtil;
import cn.com.softvan.service.consult.IConsultManager;
import cn.com.softvan.service.consult.IConsultMsgManager;
import cn.com.softvan.service.customerservice.ICustomerServiceManager;
import cn.com.softvan.service.wechat.impl.TcWxPublicUserManager;
import cn.com.softvan.web.action.BaseAction;
import cn.com.softvan.web.tag.PageInfo;

/**
 * 咨询咨询 ActionClass
 * 
 * @author wuxiaogang
 * 
 */
public class C301Action extends BaseAction {

	/**
	 * 序列号
	 */
	private static final long serialVersionUID = -3061791975484213551L;
	private static final transient Logger log = Logger.getLogger(C301Action.class);
	
	/**BEAN类  咨询信息*/
	private TcCsConsultBean bean;
	/**BEAN类  咨询 集合*/
	private List<TcCsConsultBean> beans;
	/** 咨询管理 业务处理*/
	private IConsultManager consultManager;
	/** 咨询管理 交互信息 业务处理*/
	private IConsultMsgManager consultMsgManager;
	/** 客服管理 业务处理*/
	private ICustomerServiceManager customerServiceManager;
	/** 微信服务_公共账号 service 接口类*/
	private TcWxPublicUserManager tcWxPublicUserManager;
	//
	public C301Action() {
		log.info("默认构造器......C301Action");
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
		log.info("C301Action init.........");
		
		int offset = 0;
		// 分页偏移量
		if (!Validator.isNullEmpty(request.getParameter("offset"))
				&& Validator.isNum(request.getParameter("offset"))) {
			offset = Integer.parseInt(request.getParameter("offset"));
		}
		PageInfo page = new PageInfo(); 
		//当前页
		page.setCurrOffset(offset);
		//每页显示条数
		page.setPageRowCount(15);
		TcCsConsultBean bean1=new TcCsConsultBean();
		bean1.setPageInfo(page);
		
		bean1.setConsult_status("0");//0待处理
		
		//列表
		List<TcCsConsultBean> beans=consultManager.findDataIsPage(bean1);
		request.setAttribute("beans",beans);
		request.setAttribute(CommonConstant.PAGEROW_OBJECT_KEY,page);
		return "init";
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
	public String list1() {
		log.info("C301Action list1.........");
		int offset = 0;
		// 分页偏移量
		if (!Validator.isNullEmpty(request.getParameter("offset"))
				&& Validator.isNum(request.getParameter("offset"))) {
			offset = Integer.parseInt(request.getParameter("offset"));
		}
		PageInfo page = new PageInfo(); 
		//当前页
		page.setCurrOffset(offset);
		//每页显示条数
		page.setPageRowCount(15);
		TcCsConsultBean bean1=new TcCsConsultBean();
		bean1.setPageInfo(page);
		TcCsCustomerServiceBean customerServiceBean = (TcCsCustomerServiceBean) request.getSession().getAttribute(CommonConstant.SESSION_KEY_USER_CUSTOMER_SERVICE);
		bean1.setCs_id(customerServiceBean.getId());
		//列表
		List<TcCsConsultBean> beans=consultManager.findDataIsPage(bean1);
		request.setAttribute("beans",beans);
		request.setAttribute(CommonConstant.PAGEROW_OBJECT_KEY,page);
		
		request.setAttribute("csbean", customerServiceBean);
		
		return "list1";
	}
	/**
	 * <p>
	 * 接入
	 * </p>
	 * <ol>
	 * [功能概要] <div>接入。</div>
	 * <div>修改。</div>
	 * </ol>
	 * @return 转发字符串
	 * @throws IOException 
	 */
	public String access() throws IOException {
		log.info("C301Action access.........");
		String msg="1";
		try {
			bean=new TcCsConsultBean();
			TcCsCustomerServiceBean customerServiceBean = (TcCsCustomerServiceBean) request.getSession().getAttribute(CommonConstant.SESSION_KEY_USER_CUSTOMER_SERVICE);
			bean.setUpdate_ip(getIpAddr());
			bean.setUpdate_id(customerServiceBean.getUid());
			bean.setCs_id(customerServiceBean.getId());//客服id
			bean.setId(request.getParameter("id"));//咨询信息
			bean.setConsult_status("1");//处理中
			msg=consultManager.updateData(bean);
			
			if("1".equals(msg)){
				String oid=request.getParameter("oid");
				if(Validator.notEmpty(oid)){
					//公共账号信息
					TcWxPublicUserBean publicUser=(TcWxPublicUserBean) getJedisHelper().get(CommonConstant.SESSION_WECHAT_BEAN);
					if(publicUser==null){
						tcWxPublicUserManager.initCache();
						publicUser=(TcWxPublicUserBean) getJedisHelper().get(CommonConstant.SESSION_WECHAT_BEAN);
					}
					String appid=publicUser.getAppid();
					String secret=publicUser.getAppsecret();
					
					WxSendTextMsg sendText=new WxSendTextMsg(oid, "text","工号"+customerServiceBean.getId()+"为您服务!");
					
					WxApiUtil wxApiUtil=new WxApiUtil();
					msg=wxApiUtil.sendCustomerService(wxApiUtil.getAccess_token(false, getJedisHelper(),appid, secret), oid, sendText.toJson());
					if(wxApiUtil.isErrAccessToken(msg)){
						msg=wxApiUtil.uploadMedia(wxApiUtil.getAccess_token(true, getJedisHelper(),appid, secret), oid, sendText.toJson());
					}
				}
				//同时更新交互的信息
				TcCsConsultMsgBean msgbean=new TcCsConsultMsgBean();
				msgbean.setConsult_id(bean.getId());
				msgbean.setCs_id(bean.getCs_id());
				consultMsgManager.updateData(msgbean);
			}
		} catch (Exception e) {
			msg=e.getMessage();
		}
		getWriter().print(msg);
		return null;
	}
	/**
	 * <p>
	 * 处理完成
	 * </p>
	 * <ol>
	 * [功能概要] <div>处理完成。</div>
	 * <div>修改。</div>
	 * </ol>
	 * @return 转发字符串
	 * @throws IOException 
	 */
	public String ok() throws IOException {
		log.info("C301Action ok.........");
		String msg="1";
		try {
			bean=new TcCsConsultBean();
			TcCsCustomerServiceBean customerServiceBean = (TcCsCustomerServiceBean) request.getSession().getAttribute(CommonConstant.SESSION_KEY_USER_CUSTOMER_SERVICE);
			bean.setUpdate_ip(getIpAddr());
			bean.setUpdate_id(customerServiceBean.getUid());
			bean.setCs_id(customerServiceBean.getId());//客服id
			bean.setId(request.getParameter("id"));//咨询信息
			bean.setConsult_status("2");//处理完成
			msg=consultManager.updateData(bean);
		} catch (Exception e) {
			msg=e.getMessage();
		}
		getWriter().print(msg);
		return null;
	}
	/**
	 * <p>
	 * 咨询完结
	 * </p>
	 * <ol>
	 * [功能概要] <div>处理完结。</div>
	 * <div>修改。</div>
	 * </ol>
	 * @return 转发字符串
	 * @throws IOException 
	 */
	public String close() throws IOException {
		log.info("C301Action close.........");
		String msg="1";
		try {
			bean=new TcCsConsultBean();
			TcCsCustomerServiceBean customerServiceBean = (TcCsCustomerServiceBean) request.getSession().getAttribute(CommonConstant.SESSION_KEY_USER_CUSTOMER_SERVICE);
			bean.setUpdate_ip(getIpAddr());
			bean.setUpdate_id(customerServiceBean.getUid());
			bean.setCs_id(customerServiceBean.getId());//客服id
			bean.setId(request.getParameter("id"));//咨询信息
			bean.setConsult_status("3");//处理完结
			bean.setFinish_time("xxxxxxx");//完结时间
			msg=consultManager.updateData(bean);
			try {
				if("1".equals(msg)){
					String oid=request.getParameter("oid");
					if(Validator.isEmpty(oid)){
						bean=consultManager.findDataById(bean);
						if(bean!=null){
							oid=bean.getUser_id();
						}
					}
					getJedisHelper().del(oid+"_consult_flag");
				}
			} catch (Exception e) {
				log.error(e);
			}
		} catch (Exception e) {
			msg=e.getMessage();
		}
		getWriter().print(msg);
		return null;
	}
	/**
	 * <p>
	 * 用户登录
	 * </p>
	 * <ol>
	 * [功能概要] <div>登陆。</div>
	 * </ol>
	 * @return 转发字符串
	 */
	public String login() throws Exception {
		log.info("c301Action login");
		TcCsCustomerServiceBean bean1=new TcCsCustomerServiceBean();
		//用户名
		bean1.setUid(request.getParameter("uid"));
		//用户密码
		bean1.setPwd(request.getParameter("pwd"));
		//用户信息
		TcCsCustomerServiceBean userBean=customerServiceManager.checkPWD(bean1);
		//用户名密码正确
		if(userBean!=null){
			request.getSession().setAttribute(CommonConstant.SESSION_KEY_USER_CUSTOMER_SERVICE, userBean);
			return SUCCESS;
		}else{
			request.setAttribute("msg","登陆失败!用户名或密码错误!");
		}
		return "cslogin";
	}
	/**
	 * <p>
	 * 用户登出
	 * </p>
	 * <ol>
	 * [功能概要] <div>登出。</div>
	 * </ol>
	 * @return 转发字符串
	 */
	public String logout() throws Exception {
		log.info("c301Action logout");
		//清空用户登录信息
		request.getSession().removeAttribute(CommonConstant.SESSION_KEY_USER_CUSTOMER_SERVICE);
		request.getSession().invalidate();
		return "cslogin";
	}
	/**
	 * BEAN类  咨询信息取得
	 * @return BEAN类  咨询信息
	 */
	public TcCsConsultBean getBean() {
	    return bean;
	}

	/**
	 * BEAN类  咨询信息设定
	 * @param bean BEAN类  咨询信息
	 */
	public void setBean(TcCsConsultBean bean) {
	    this.bean = bean;
	}

	/**
	 * BEAN类  咨询 集合取得
	 * @return BEAN类  咨询 集合
	 */
	public List<TcCsConsultBean> getBeans() {
	    return beans;
	}

	/**
	 * BEAN类  咨询 集合设定
	 * @param beans BEAN类  咨询 集合
	 */
	public void setBeans(List<TcCsConsultBean> beans) {
	    this.beans = beans;
	}

	/**
	 * 咨询管理 业务处理取得
	 * @return 咨询管理 业务处理
	 */
	public IConsultManager getConsultManager() {
	    return consultManager;
	}

	/**
	 * 咨询管理 业务处理设定
	 * @param consultManager 咨询管理 业务处理
	 */
	public void setConsultManager(IConsultManager consultManager) {
	    this.consultManager = consultManager;
	}

	/**
	 * 咨询管理 交互信息 业务处理取得
	 * @return 咨询管理 交互信息 业务处理
	 */
	public IConsultMsgManager getConsultMsgManager() {
	    return consultMsgManager;
	}

	/**
	 * 咨询管理 交互信息 业务处理设定
	 * @param consultMsgManager 咨询管理 交互信息 业务处理
	 */
	public void setConsultMsgManager(IConsultMsgManager consultMsgManager) {
	    this.consultMsgManager = consultMsgManager;
	}

	/**
	 * 客服管理 业务处理取得
	 * @return 客服管理 业务处理
	 */
	public ICustomerServiceManager getCustomerServiceManager() {
	    return customerServiceManager;
	}

	/**
	 * 客服管理 业务处理设定
	 * @param customerServiceManager 客服管理 业务处理
	 */
	public void setCustomerServiceManager(ICustomerServiceManager customerServiceManager) {
	    this.customerServiceManager = customerServiceManager;
	}

	/**
	 * 微信服务_公共账号 service 接口类取得
	 * @return 微信服务_公共账号 service 接口类
	 */
	public TcWxPublicUserManager getTcWxPublicUserManager() {
	    return tcWxPublicUserManager;
	}

	/**
	 * 微信服务_公共账号 service 接口类设定
	 * @param tcWxPublicUserManager 微信服务_公共账号 service 接口类
	 */
	public void setTcWxPublicUserManager(TcWxPublicUserManager tcWxPublicUserManager) {
	    this.tcWxPublicUserManager = tcWxPublicUserManager;
	}
}
