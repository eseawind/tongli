/*
 * 咨询咨询  ActionClass
 *
 * VERSION  DATE        BY              REASON
 * -------- ----------- --------------- ------------------------------------------
 * 1.00     2014.04.16  wuxiaogang      程序・发布
 * 1.01     2014.04.21  wuxiaogang      程序・更新 
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
import cn.com.softvan.common.JedisHelper;
import cn.com.softvan.common.wechat.WxApiUtil;
import cn.com.softvan.service.consult.IConsultManager;
import cn.com.softvan.service.consult.IConsultMsgManager;
import cn.com.softvan.service.wechat.impl.TcWxPublicUserManager;
import cn.com.softvan.web.action.BaseAction;

/**
 * 咨询咨询 ActionClass
 * 
 * @author wuxiaogang
 * 
 */
public class C302Action extends BaseAction {

	/**
	 * 序列号
	 */
	private static final long serialVersionUID = -3061791975484213551L;
	private static final transient Logger log = Logger.getLogger(C302Action.class);
	
	/**BEAN类  咨询信息*/
	private TcCsConsultMsgBean bean;
	/**BEAN类  咨询*/
	private TcCsConsultBean cbean;
	/**BEAN类  咨询 集合*/
	private List<TcCsConsultMsgBean> beans;
	/** 咨询 业务处理*/
	private IConsultManager consultManager;
	/** 咨询管理 业务处理*/
	private IConsultMsgManager consultMsgManager;
	/**redis缓存工具类*/
	private JedisHelper jedisHelper;
	/** 微信服务_公共账号 service 接口类*/
	private TcWxPublicUserManager tcWxPublicUserManager;
	//
	public C302Action() {
		log.info("默认构造器......C302Action");
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
		log.info("C302Action init.........");
		//咨询id
		String cid=request.getParameter("cid");
		
		TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
		bean1.setConsult_id(cid);
		TcCsCustomerServiceBean customerServiceBean = (TcCsCustomerServiceBean) request.getSession().getAttribute(CommonConstant.SESSION_KEY_USER_CUSTOMER_SERVICE);
		//客服id
		bean1.setCs_id(customerServiceBean.getId());
		//咨询交互记录列表
		List<TcCsConsultMsgBean> beans=consultMsgManager.findDataIsList(bean1);
		request.setAttribute("beans",beans);
		//咨询信息
		TcCsConsultBean bean2=new TcCsConsultBean();
		bean2.setId(cid);
		request.setAttribute("cbean", consultManager.findDataById(bean2));
		return "init";
	}
	/**
	 * <p>
	 * 信息发送。
	 * </p>
	 * <ol>
	 * [功能概要] <div>信息发送。</div>
	 * </ol>
	 * @return 转发字符串
	 * @throws IOException 
	 */
	public String send() throws IOException {
		log.info("C302Action send.........");
		String msg="1";
		try {
			//公共账号信息
			TcWxPublicUserBean publicUser=(TcWxPublicUserBean) jedisHelper.get(CommonConstant.SESSION_WECHAT_BEAN);
			if(publicUser==null){
				tcWxPublicUserManager.initCache();
				publicUser=(TcWxPublicUserBean) jedisHelper.get(CommonConstant.SESSION_WECHAT_BEAN);
			}
			String appid=publicUser.getAppid();
			String secret=publicUser.getAppsecret();
			
			WxSendTextMsg sendText=new WxSendTextMsg(bean.getUser_id(), "text", bean.getContent());
			
			WxApiUtil wxApiUtil=new WxApiUtil();
			msg=wxApiUtil.sendCustomerService(wxApiUtil.getAccess_token(false, jedisHelper,appid, secret), bean.getUser_id(), sendText.toJson());
			if(wxApiUtil.isErrAccessToken(msg)){
				msg=wxApiUtil.uploadMedia(wxApiUtil.getAccess_token(true, jedisHelper,appid, secret), bean.getUser_id(), sendText.toJson());
			}
			
			try {
				TcCsCustomerServiceBean customerServiceBean = (TcCsCustomerServiceBean) request.getSession().getAttribute(CommonConstant.SESSION_KEY_USER_CUSTOMER_SERVICE);
				bean.setUpdate_ip(getIpAddr());
				bean.setUpdate_id(customerServiceBean.getUid());
				bean.setCs_id(customerServiceBean.getId());//客服id
				bean.setInfo_source("2");//客服回复
				consultMsgManager.saveOrUpdateData(bean);
			} catch (Exception e) {
				log.error("a客服回复,信息入库异常!",e);
			}
		} catch (Exception e) {
			log.error("a客服回复异常!",e);
		}
		getWriter().print(msg);
		return null;
	}
	/**
	 * <p>
	 * 信息接收。
	 * </p>
	 * <ol>
	 * [功能概要] <div>信息接收。</div>
	 * </ol>
	 * @return 转发字符串
	 * @throws IOException 
	 */
	public String receive() throws IOException {
		log.info("C302Action receive.........");
		String msg="";
		try {
			//咨询id
			String cid=request.getParameter("cid");
			//用户openid
			String oid=request.getParameter("oid");
			
			msg=(String) jedisHelper.get(oid+"_consult_info");
			getWriter().print(msg);
			//清除缓存
			jedisHelper.del(oid+"_consult_info");
			
		} catch (Exception e) {
			//msg=e.getMessage();
			log.error("客服信息交互 信息接收", e);
		}
		return null;
	}
//	/**
//	 * <p>
//	 * 编辑。
//	 * </p>
//	 * <ol>
//	 * [功能概要] <div>编辑。</div>
//	 * </ol>
//	 * @return 转发字符串
//	 */
//	public String edit() {
//		log.info("C301Action edit.........");
//		String id=request.getParameter("id");
//		if(id!=null){
//			TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
//			bean1.setId(id);
//			bean=consultMsgManager.findDataById(bean1);
//		}else{
//			SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
//			request.setAttribute("date", sdf.format(new Date()));
//		}
//		return "edit";
//	}
//	/**
//	 * <p>
//	 * 信息保存
//	 * </p>
//	 * <ol>
//	 * [功能概要] <div>新增。</div>
//	 * <div>修改。</div>
//	 * </ol>
//	 * @return 转发字符串
//	 */
//	public String save() {
//		log.info("C301Action edit.........");
//		if(bean!=null){
//			String msg="1";
//			try {
//					BaseUserBean user = (BaseUserBean) request.getSession().getAttribute(CommonConstant.SESSION_KEY_USER_CUSTOMER_SERVICE);
//					if(user!=null){
//						bean.setCreate_ip(getIpAddr());
//						bean.setCreate_id(user.getUser_id());
//						bean.setUpdate_ip(getIpAddr());
//						bean.setUpdate_id(user.getUser_id());
//					}
//					msg=consultMsgManager.saveOrUpdateData(bean);
//			} catch (Exception e) {
//				msg=e.getMessage();
//			}
//			request.setAttribute("msg",msg);
//		}else{
//			request.setAttribute("msg", "信息保存失败!");
//		}
//		return SUCCESS;
//	}
//	/**
//	 * <p>
//	 * 删除。
//	 * </p>
//	 * <ol>
//	 * [功能概要] <div>删除。</div>
//	 * </ol>
//	 * @return 转发字符串
//	 */
//	public String del() {
//		log.info("C301Action del.........");
//		
//		String id=request.getParameter("id");
//		TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
//		bean1.setId(id);
//		String msg="1";
//		try {
//			msg=consultMsgManager.deleteDataById(bean1);
//		} catch (Exception e) {
//			msg=e.getMessage();
//		}
//		request.setAttribute("msg",msg);
//		return SUCCESS;
//	}

	

	/**
	 * BEAN类  咨询信息取得
	 * @return BEAN类  咨询信息
	 */
	//	/**
	//	 * <p>
	//	 * 编辑。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>编辑。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String edit() {
	//		log.info("C301Action edit.........");
	//		String id=request.getParameter("id");
	//		if(id!=null){
	//			TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//			bean1.setId(id);
	//			bean=consultMsgManager.findDataById(bean1);
	//		}else{
	//			SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
	//			request.setAttribute("date", sdf.format(new Date()));
	//		}
	//		return "edit";
	//	}
	//	/**
	//	 * <p>
	//	 * 信息保存
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>新增。</div>
	//	 * <div>修改。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String save() {
	//		log.info("C301Action edit.........");
	//		if(bean!=null){
	//			String msg="1";
	//			try {
	//					BaseUserBean user = (BaseUserBean) request.getSession().getAttribute(CommonConstant.SESSION_KEY_USER_CUSTOMER_SERVICE);
	//					if(user!=null){
	//						bean.setCreate_ip(getIpAddr());
	//						bean.setCreate_id(user.getUser_id());
	//						bean.setUpdate_ip(getIpAddr());
	//						bean.setUpdate_id(user.getUser_id());
	//					}
	//					msg=consultMsgManager.saveOrUpdateData(bean);
	//			} catch (Exception e) {
	//				msg=e.getMessage();
	//			}
	//			request.setAttribute("msg",msg);
	//		}else{
	//			request.setAttribute("msg", "信息保存失败!");
	//		}
	//		return SUCCESS;
	//	}
	//	/**
	//	 * <p>
	//	 * 删除。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>删除。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String del() {
	//		log.info("C301Action del.........");
	//		
	//		String id=request.getParameter("id");
	//		TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//		bean1.setId(id);
	//		String msg="1";
	//		try {
	//			msg=consultMsgManager.deleteDataById(bean1);
	//		} catch (Exception e) {
	//			msg=e.getMessage();
	//		}
	//		request.setAttribute("msg",msg);
	//		return SUCCESS;
	//	}
	
	

	/**
	 * BEAN类  咨询信息设定
	 * @param bean BEAN类  咨询信息
	 */
	//	/**
	//	 * <p>
	//	 * 编辑。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>编辑。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String edit() {
	//		log.info("C301Action edit.........");
	//		String id=request.getParameter("id");
	//		if(id!=null){
	//			TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//			bean1.setId(id);
	//			bean=consultMsgManager.findDataById(bean1);
	//		}else{
	//			SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
	//			request.setAttribute("date", sdf.format(new Date()));
	//		}
	//		return "edit";
	//	}
	//	/**
	//	 * <p>
	//	 * 信息保存
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>新增。</div>
	//	 * <div>修改。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String save() {
	//		log.info("C301Action edit.........");
	//		if(bean!=null){
	//			String msg="1";
	//			try {
	//					BaseUserBean user = (BaseUserBean) request.getSession().getAttribute(CommonConstant.SESSION_KEY_USER_CUSTOMER_SERVICE);
	//					if(user!=null){
	//						bean.setCreate_ip(getIpAddr());
	//						bean.setCreate_id(user.getUser_id());
	//						bean.setUpdate_ip(getIpAddr());
	//						bean.setUpdate_id(user.getUser_id());
	//					}
	//					msg=consultMsgManager.saveOrUpdateData(bean);
	//			} catch (Exception e) {
	//				msg=e.getMessage();
	//			}
	//			request.setAttribute("msg",msg);
	//		}else{
	//			request.setAttribute("msg", "信息保存失败!");
	//		}
	//		return SUCCESS;
	//	}
	//	/**
	//	 * <p>
	//	 * 删除。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>删除。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String del() {
	//		log.info("C301Action del.........");
	//		
	//		String id=request.getParameter("id");
	//		TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//		bean1.setId(id);
	//		String msg="1";
	//		try {
	//			msg=consultMsgManager.deleteDataById(bean1);
	//		} catch (Exception e) {
	//			msg=e.getMessage();
	//		}
	//		request.setAttribute("msg",msg);
	//		return SUCCESS;
	//	}
	
	

	/**
	 * BEAN类  咨询信息取得
	 * @return BEAN类  咨询信息
	 */
	//	/**
	//	 * <p>
	//	 * 编辑。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>编辑。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String edit() {
	//		log.info("C301Action edit.........");
	//		String id=request.getParameter("id");
	//		if(id!=null){
	//			TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//			bean1.setId(id);
	//			bean=consultMsgManager.findDataById(bean1);
	//		}else{
	//			SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
	//			request.setAttribute("date", sdf.format(new Date()));
	//		}
	//		return "edit";
	//	}
	//	/**
	//	 * <p>
	//	 * 信息保存
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>新增。</div>
	//	 * <div>修改。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String save() {
	//		log.info("C301Action edit.........");
	//		if(bean!=null){
	//			String msg="1";
	//			try {
	//					BaseUserBean user = (BaseUserBean) request.getSession().getAttribute(CommonConstant.SESSION_KEY_USER_CUSTOMER_SERVICE);
	//					if(user!=null){
	//						bean.setCreate_ip(getIpAddr());
	//						bean.setCreate_id(user.getUser_id());
	//						bean.setUpdate_ip(getIpAddr());
	//						bean.setUpdate_id(user.getUser_id());
	//					}
	//					msg=consultMsgManager.saveOrUpdateData(bean);
	//			} catch (Exception e) {
	//				msg=e.getMessage();
	//			}
	//			request.setAttribute("msg",msg);
	//		}else{
	//			request.setAttribute("msg", "信息保存失败!");
	//		}
	//		return SUCCESS;
	//	}
	//	/**
	//	 * <p>
	//	 * 删除。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>删除。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String del() {
	//		log.info("C301Action del.........");
	//		
	//		String id=request.getParameter("id");
	//		TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//		bean1.setId(id);
	//		String msg="1";
	//		try {
	//			msg=consultMsgManager.deleteDataById(bean1);
	//		} catch (Exception e) {
	//			msg=e.getMessage();
	//		}
	//		request.setAttribute("msg",msg);
	//		return SUCCESS;
	//	}
	
	
	
	/**
	 * BEAN类  咨询信息取得
	 * @return BEAN类  咨询信息
	 */
	//	/**
	//	 * <p>
	//	 * 编辑。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>编辑。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String edit() {
	//		log.info("C301Action edit.........");
	//		String id=request.getParameter("id");
	//		if(id!=null){
	//			TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//			bean1.setId(id);
	//			bean=consultMsgManager.findDataById(bean1);
	//		}else{
	//			SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
	//			request.setAttribute("date", sdf.format(new Date()));
	//		}
	//		return "edit";
	//	}
	//	/**
	//	 * <p>
	//	 * 信息保存
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>新增。</div>
	//	 * <div>修改。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String save() {
	//		log.info("C301Action edit.........");
	//		if(bean!=null){
	//			String msg="1";
	//			try {
	//					BaseUserBean user = (BaseUserBean) request.getSession().getAttribute(CommonConstant.SESSION_KEY_USER_CUSTOMER_SERVICE);
	//					if(user!=null){
	//						bean.setCreate_ip(getIpAddr());
	//						bean.setCreate_id(user.getUser_id());
	//						bean.setUpdate_ip(getIpAddr());
	//						bean.setUpdate_id(user.getUser_id());
	//					}
	//					msg=consultMsgManager.saveOrUpdateData(bean);
	//			} catch (Exception e) {
	//				msg=e.getMessage();
	//			}
	//			request.setAttribute("msg",msg);
	//		}else{
	//			request.setAttribute("msg", "信息保存失败!");
	//		}
	//		return SUCCESS;
	//	}
	//	/**
	//	 * <p>
	//	 * 删除。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>删除。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String del() {
	//		log.info("C301Action del.........");
	//		
	//		String id=request.getParameter("id");
	//		TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//		bean1.setId(id);
	//		String msg="1";
	//		try {
	//			msg=consultMsgManager.deleteDataById(bean1);
	//		} catch (Exception e) {
	//			msg=e.getMessage();
	//		}
	//		request.setAttribute("msg",msg);
	//		return SUCCESS;
	//	}
	
	

	/**
	 * BEAN类  咨询信息设定
	 * @param bean BEAN类  咨询信息
	 */
	//	/**
	//	 * <p>
	//	 * 编辑。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>编辑。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String edit() {
	//		log.info("C301Action edit.........");
	//		String id=request.getParameter("id");
	//		if(id!=null){
	//			TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//			bean1.setId(id);
	//			bean=consultMsgManager.findDataById(bean1);
	//		}else{
	//			SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
	//			request.setAttribute("date", sdf.format(new Date()));
	//		}
	//		return "edit";
	//	}
	//	/**
	//	 * <p>
	//	 * 信息保存
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>新增。</div>
	//	 * <div>修改。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String save() {
	//		log.info("C301Action edit.........");
	//		if(bean!=null){
	//			String msg="1";
	//			try {
	//					BaseUserBean user = (BaseUserBean) request.getSession().getAttribute(CommonConstant.SESSION_KEY_USER_CUSTOMER_SERVICE);
	//					if(user!=null){
	//						bean.setCreate_ip(getIpAddr());
	//						bean.setCreate_id(user.getUser_id());
	//						bean.setUpdate_ip(getIpAddr());
	//						bean.setUpdate_id(user.getUser_id());
	//					}
	//					msg=consultMsgManager.saveOrUpdateData(bean);
	//			} catch (Exception e) {
	//				msg=e.getMessage();
	//			}
	//			request.setAttribute("msg",msg);
	//		}else{
	//			request.setAttribute("msg", "信息保存失败!");
	//		}
	//		return SUCCESS;
	//	}
	//	/**
	//	 * <p>
	//	 * 删除。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>删除。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String del() {
	//		log.info("C301Action del.........");
	//		
	//		String id=request.getParameter("id");
	//		TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//		bean1.setId(id);
	//		String msg="1";
	//		try {
	//			msg=consultMsgManager.deleteDataById(bean1);
	//		} catch (Exception e) {
	//			msg=e.getMessage();
	//		}
	//		request.setAttribute("msg",msg);
	//		return SUCCESS;
	//	}
	
	
	
	/**
	 * BEAN类  咨询信息取得
	 * @return BEAN类  咨询信息
	 */
	//	/**
	//	 * <p>
	//	 * 编辑。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>编辑。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String edit() {
	//		log.info("C301Action edit.........");
	//		String id=request.getParameter("id");
	//		if(id!=null){
	//			TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//			bean1.setId(id);
	//			bean=consultMsgManager.findDataById(bean1);
	//		}else{
	//			SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
	//			request.setAttribute("date", sdf.format(new Date()));
	//		}
	//		return "edit";
	//	}
	//	/**
	//	 * <p>
	//	 * 信息保存
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>新增。</div>
	//	 * <div>修改。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String save() {
	//		log.info("C301Action edit.........");
	//		if(bean!=null){
	//			String msg="1";
	//			try {
	//					BaseUserBean user = (BaseUserBean) request.getSession().getAttribute(CommonConstant.SESSION_KEY_USER_CUSTOMER_SERVICE);
	//					if(user!=null){
	//						bean.setCreate_ip(getIpAddr());
	//						bean.setCreate_id(user.getUser_id());
	//						bean.setUpdate_ip(getIpAddr());
	//						bean.setUpdate_id(user.getUser_id());
	//					}
	//					msg=consultMsgManager.saveOrUpdateData(bean);
	//			} catch (Exception e) {
	//				msg=e.getMessage();
	//			}
	//			request.setAttribute("msg",msg);
	//		}else{
	//			request.setAttribute("msg", "信息保存失败!");
	//		}
	//		return SUCCESS;
	//	}
	//	/**
	//	 * <p>
	//	 * 删除。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>删除。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String del() {
	//		log.info("C301Action del.........");
	//		
	//		String id=request.getParameter("id");
	//		TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//		bean1.setId(id);
	//		String msg="1";
	//		try {
	//			msg=consultMsgManager.deleteDataById(bean1);
	//		} catch (Exception e) {
	//			msg=e.getMessage();
	//		}
	//		request.setAttribute("msg",msg);
	//		return SUCCESS;
	//	}
	
	
	
	/**
	 * BEAN类  咨询信息设定
	 * @param bean BEAN类  咨询信息
	 */
	//	/**
	//	 * <p>
	//	 * 编辑。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>编辑。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String edit() {
	//		log.info("C301Action edit.........");
	//		String id=request.getParameter("id");
	//		if(id!=null){
	//			TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//			bean1.setId(id);
	//			bean=consultMsgManager.findDataById(bean1);
	//		}else{
	//			SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
	//			request.setAttribute("date", sdf.format(new Date()));
	//		}
	//		return "edit";
	//	}
	//	/**
	//	 * <p>
	//	 * 信息保存
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>新增。</div>
	//	 * <div>修改。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String save() {
	//		log.info("C301Action edit.........");
	//		if(bean!=null){
	//			String msg="1";
	//			try {
	//					BaseUserBean user = (BaseUserBean) request.getSession().getAttribute(CommonConstant.SESSION_KEY_USER_CUSTOMER_SERVICE);
	//					if(user!=null){
	//						bean.setCreate_ip(getIpAddr());
	//						bean.setCreate_id(user.getUser_id());
	//						bean.setUpdate_ip(getIpAddr());
	//						bean.setUpdate_id(user.getUser_id());
	//					}
	//					msg=consultMsgManager.saveOrUpdateData(bean);
	//			} catch (Exception e) {
	//				msg=e.getMessage();
	//			}
	//			request.setAttribute("msg",msg);
	//		}else{
	//			request.setAttribute("msg", "信息保存失败!");
	//		}
	//		return SUCCESS;
	//	}
	//	/**
	//	 * <p>
	//	 * 删除。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>删除。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String del() {
	//		log.info("C301Action del.........");
	//		
	//		String id=request.getParameter("id");
	//		TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//		bean1.setId(id);
	//		String msg="1";
	//		try {
	//			msg=consultMsgManager.deleteDataById(bean1);
	//		} catch (Exception e) {
	//			msg=e.getMessage();
	//		}
	//		request.setAttribute("msg",msg);
	//		return SUCCESS;
	//	}
	
	

	/**
	 * BEAN类  咨询信息取得
	 * @return BEAN类  咨询信息
	 */
	//	/**
	//	 * <p>
	//	 * 编辑。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>编辑。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String edit() {
	//		log.info("C301Action edit.........");
	//		String id=request.getParameter("id");
	//		if(id!=null){
	//			TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//			bean1.setId(id);
	//			bean=consultMsgManager.findDataById(bean1);
	//		}else{
	//			SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
	//			request.setAttribute("date", sdf.format(new Date()));
	//		}
	//		return "edit";
	//	}
	//	/**
	//	 * <p>
	//	 * 信息保存
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>新增。</div>
	//	 * <div>修改。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String save() {
	//		log.info("C301Action edit.........");
	//		if(bean!=null){
	//			String msg="1";
	//			try {
	//					BaseUserBean user = (BaseUserBean) request.getSession().getAttribute(CommonConstant.SESSION_KEY_USER_CUSTOMER_SERVICE);
	//					if(user!=null){
	//						bean.setCreate_ip(getIpAddr());
	//						bean.setCreate_id(user.getUser_id());
	//						bean.setUpdate_ip(getIpAddr());
	//						bean.setUpdate_id(user.getUser_id());
	//					}
	//					msg=consultMsgManager.saveOrUpdateData(bean);
	//			} catch (Exception e) {
	//				msg=e.getMessage();
	//			}
	//			request.setAttribute("msg",msg);
	//		}else{
	//			request.setAttribute("msg", "信息保存失败!");
	//		}
	//		return SUCCESS;
	//	}
	//	/**
	//	 * <p>
	//	 * 删除。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>删除。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String del() {
	//		log.info("C301Action del.........");
	//		
	//		String id=request.getParameter("id");
	//		TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//		bean1.setId(id);
	//		String msg="1";
	//		try {
	//			msg=consultMsgManager.deleteDataById(bean1);
	//		} catch (Exception e) {
	//			msg=e.getMessage();
	//		}
	//		request.setAttribute("msg",msg);
	//		return SUCCESS;
	//	}
	
	
	
	/**
	 * BEAN类  咨询信息取得
	 * @return BEAN类  咨询信息
	 */
	//	/**
	//	 * <p>
	//	 * 编辑。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>编辑。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String edit() {
	//		log.info("C301Action edit.........");
	//		String id=request.getParameter("id");
	//		if(id!=null){
	//			TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//			bean1.setId(id);
	//			bean=consultMsgManager.findDataById(bean1);
	//		}else{
	//			SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
	//			request.setAttribute("date", sdf.format(new Date()));
	//		}
	//		return "edit";
	//	}
	//	/**
	//	 * <p>
	//	 * 信息保存
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>新增。</div>
	//	 * <div>修改。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String save() {
	//		log.info("C301Action edit.........");
	//		if(bean!=null){
	//			String msg="1";
	//			try {
	//					BaseUserBean user = (BaseUserBean) request.getSession().getAttribute(CommonConstant.SESSION_KEY_USER_CUSTOMER_SERVICE);
	//					if(user!=null){
	//						bean.setCreate_ip(getIpAddr());
	//						bean.setCreate_id(user.getUser_id());
	//						bean.setUpdate_ip(getIpAddr());
	//						bean.setUpdate_id(user.getUser_id());
	//					}
	//					msg=consultMsgManager.saveOrUpdateData(bean);
	//			} catch (Exception e) {
	//				msg=e.getMessage();
	//			}
	//			request.setAttribute("msg",msg);
	//		}else{
	//			request.setAttribute("msg", "信息保存失败!");
	//		}
	//		return SUCCESS;
	//	}
	//	/**
	//	 * <p>
	//	 * 删除。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>删除。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String del() {
	//		log.info("C301Action del.........");
	//		
	//		String id=request.getParameter("id");
	//		TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//		bean1.setId(id);
	//		String msg="1";
	//		try {
	//			msg=consultMsgManager.deleteDataById(bean1);
	//		} catch (Exception e) {
	//			msg=e.getMessage();
	//		}
	//		request.setAttribute("msg",msg);
	//		return SUCCESS;
	//	}
	
	
	
	/**
	 * BEAN类  咨询信息设定
	 * @param bean BEAN类  咨询信息
	 */
	//	/**
	//	 * <p>
	//	 * 编辑。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>编辑。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String edit() {
	//		log.info("C301Action edit.........");
	//		String id=request.getParameter("id");
	//		if(id!=null){
	//			TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//			bean1.setId(id);
	//			bean=consultMsgManager.findDataById(bean1);
	//		}else{
	//			SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
	//			request.setAttribute("date", sdf.format(new Date()));
	//		}
	//		return "edit";
	//	}
	//	/**
	//	 * <p>
	//	 * 信息保存
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>新增。</div>
	//	 * <div>修改。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String save() {
	//		log.info("C301Action edit.........");
	//		if(bean!=null){
	//			String msg="1";
	//			try {
	//					BaseUserBean user = (BaseUserBean) request.getSession().getAttribute(CommonConstant.SESSION_KEY_USER_CUSTOMER_SERVICE);
	//					if(user!=null){
	//						bean.setCreate_ip(getIpAddr());
	//						bean.setCreate_id(user.getUser_id());
	//						bean.setUpdate_ip(getIpAddr());
	//						bean.setUpdate_id(user.getUser_id());
	//					}
	//					msg=consultMsgManager.saveOrUpdateData(bean);
	//			} catch (Exception e) {
	//				msg=e.getMessage();
	//			}
	//			request.setAttribute("msg",msg);
	//		}else{
	//			request.setAttribute("msg", "信息保存失败!");
	//		}
	//		return SUCCESS;
	//	}
	//	/**
	//	 * <p>
	//	 * 删除。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>删除。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String del() {
	//		log.info("C301Action del.........");
	//		
	//		String id=request.getParameter("id");
	//		TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//		bean1.setId(id);
	//		String msg="1";
	//		try {
	//			msg=consultMsgManager.deleteDataById(bean1);
	//		} catch (Exception e) {
	//			msg=e.getMessage();
	//		}
	//		request.setAttribute("msg",msg);
	//		return SUCCESS;
	//	}
	
	
	
	/**
	 * BEAN类  咨询信息取得
	 * @return BEAN类  咨询信息
	 */
	//	/**
	//	 * <p>
	//	 * 编辑。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>编辑。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String edit() {
	//		log.info("C301Action edit.........");
	//		String id=request.getParameter("id");
	//		if(id!=null){
	//			TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//			bean1.setId(id);
	//			bean=consultMsgManager.findDataById(bean1);
	//		}else{
	//			SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
	//			request.setAttribute("date", sdf.format(new Date()));
	//		}
	//		return "edit";
	//	}
	//	/**
	//	 * <p>
	//	 * 信息保存
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>新增。</div>
	//	 * <div>修改。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String save() {
	//		log.info("C301Action edit.........");
	//		if(bean!=null){
	//			String msg="1";
	//			try {
	//					BaseUserBean user = (BaseUserBean) request.getSession().getAttribute(CommonConstant.SESSION_KEY_USER_CUSTOMER_SERVICE);
	//					if(user!=null){
	//						bean.setCreate_ip(getIpAddr());
	//						bean.setCreate_id(user.getUser_id());
	//						bean.setUpdate_ip(getIpAddr());
	//						bean.setUpdate_id(user.getUser_id());
	//					}
	//					msg=consultMsgManager.saveOrUpdateData(bean);
	//			} catch (Exception e) {
	//				msg=e.getMessage();
	//			}
	//			request.setAttribute("msg",msg);
	//		}else{
	//			request.setAttribute("msg", "信息保存失败!");
	//		}
	//		return SUCCESS;
	//	}
	//	/**
	//	 * <p>
	//	 * 删除。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>删除。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String del() {
	//		log.info("C301Action del.........");
	//		
	//		String id=request.getParameter("id");
	//		TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//		bean1.setId(id);
	//		String msg="1";
	//		try {
	//			msg=consultMsgManager.deleteDataById(bean1);
	//		} catch (Exception e) {
	//			msg=e.getMessage();
	//		}
	//		request.setAttribute("msg",msg);
	//		return SUCCESS;
	//	}
	
	
	
	/**
	 * BEAN类  咨询信息取得
	 * @return BEAN类  咨询信息
	 */
	//	/**
	//	 * <p>
	//	 * 编辑。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>编辑。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String edit() {
	//		log.info("C301Action edit.........");
	//		String id=request.getParameter("id");
	//		if(id!=null){
	//			TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//			bean1.setId(id);
	//			bean=consultMsgManager.findDataById(bean1);
	//		}else{
	//			SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
	//			request.setAttribute("date", sdf.format(new Date()));
	//		}
	//		return "edit";
	//	}
	//	/**
	//	 * <p>
	//	 * 信息保存
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>新增。</div>
	//	 * <div>修改。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String save() {
	//		log.info("C301Action edit.........");
	//		if(bean!=null){
	//			String msg="1";
	//			try {
	//					BaseUserBean user = (BaseUserBean) request.getSession().getAttribute(CommonConstant.SESSION_KEY_USER_CUSTOMER_SERVICE);
	//					if(user!=null){
	//						bean.setCreate_ip(getIpAddr());
	//						bean.setCreate_id(user.getUser_id());
	//						bean.setUpdate_ip(getIpAddr());
	//						bean.setUpdate_id(user.getUser_id());
	//					}
	//					msg=consultMsgManager.saveOrUpdateData(bean);
	//			} catch (Exception e) {
	//				msg=e.getMessage();
	//			}
	//			request.setAttribute("msg",msg);
	//		}else{
	//			request.setAttribute("msg", "信息保存失败!");
	//		}
	//		return SUCCESS;
	//	}
	//	/**
	//	 * <p>
	//	 * 删除。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>删除。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String del() {
	//		log.info("C301Action del.........");
	//		
	//		String id=request.getParameter("id");
	//		TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//		bean1.setId(id);
	//		String msg="1";
	//		try {
	//			msg=consultMsgManager.deleteDataById(bean1);
	//		} catch (Exception e) {
	//			msg=e.getMessage();
	//		}
	//		request.setAttribute("msg",msg);
	//		return SUCCESS;
	//	}
	
	

	/**
	 * BEAN类  咨询信息设定
	 * @param bean BEAN类  咨询信息
	 */
	//	/**
	//	 * <p>
	//	 * 编辑。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>编辑。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String edit() {
	//		log.info("C301Action edit.........");
	//		String id=request.getParameter("id");
	//		if(id!=null){
	//			TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//			bean1.setId(id);
	//			bean=consultMsgManager.findDataById(bean1);
	//		}else{
	//			SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
	//			request.setAttribute("date", sdf.format(new Date()));
	//		}
	//		return "edit";
	//	}
	//	/**
	//	 * <p>
	//	 * 信息保存
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>新增。</div>
	//	 * <div>修改。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String save() {
	//		log.info("C301Action edit.........");
	//		if(bean!=null){
	//			String msg="1";
	//			try {
	//					BaseUserBean user = (BaseUserBean) request.getSession().getAttribute(CommonConstant.SESSION_KEY_USER_CUSTOMER_SERVICE);
	//					if(user!=null){
	//						bean.setCreate_ip(getIpAddr());
	//						bean.setCreate_id(user.getUser_id());
	//						bean.setUpdate_ip(getIpAddr());
	//						bean.setUpdate_id(user.getUser_id());
	//					}
	//					msg=consultMsgManager.saveOrUpdateData(bean);
	//			} catch (Exception e) {
	//				msg=e.getMessage();
	//			}
	//			request.setAttribute("msg",msg);
	//		}else{
	//			request.setAttribute("msg", "信息保存失败!");
	//		}
	//		return SUCCESS;
	//	}
	//	/**
	//	 * <p>
	//	 * 删除。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>删除。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String del() {
	//		log.info("C301Action del.........");
	//		
	//		String id=request.getParameter("id");
	//		TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//		bean1.setId(id);
	//		String msg="1";
	//		try {
	//			msg=consultMsgManager.deleteDataById(bean1);
	//		} catch (Exception e) {
	//			msg=e.getMessage();
	//		}
	//		request.setAttribute("msg",msg);
	//		return SUCCESS;
	//	}
	
	
	
	/**
	 * BEAN类  咨询信息取得
	 * @return BEAN类  咨询信息
	 */
	//	/**
	//	 * <p>
	//	 * 编辑。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>编辑。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String edit() {
	//		log.info("C301Action edit.........");
	//		String id=request.getParameter("id");
	//		if(id!=null){
	//			TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//			bean1.setId(id);
	//			bean=consultMsgManager.findDataById(bean1);
	//		}else{
	//			SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
	//			request.setAttribute("date", sdf.format(new Date()));
	//		}
	//		return "edit";
	//	}
	//	/**
	//	 * <p>
	//	 * 信息保存
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>新增。</div>
	//	 * <div>修改。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String save() {
	//		log.info("C301Action edit.........");
	//		if(bean!=null){
	//			String msg="1";
	//			try {
	//					BaseUserBean user = (BaseUserBean) request.getSession().getAttribute(CommonConstant.SESSION_KEY_USER_CUSTOMER_SERVICE);
	//					if(user!=null){
	//						bean.setCreate_ip(getIpAddr());
	//						bean.setCreate_id(user.getUser_id());
	//						bean.setUpdate_ip(getIpAddr());
	//						bean.setUpdate_id(user.getUser_id());
	//					}
	//					msg=consultMsgManager.saveOrUpdateData(bean);
	//			} catch (Exception e) {
	//				msg=e.getMessage();
	//			}
	//			request.setAttribute("msg",msg);
	//		}else{
	//			request.setAttribute("msg", "信息保存失败!");
	//		}
	//		return SUCCESS;
	//	}
	//	/**
	//	 * <p>
	//	 * 删除。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>删除。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String del() {
	//		log.info("C301Action del.........");
	//		
	//		String id=request.getParameter("id");
	//		TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//		bean1.setId(id);
	//		String msg="1";
	//		try {
	//			msg=consultMsgManager.deleteDataById(bean1);
	//		} catch (Exception e) {
	//			msg=e.getMessage();
	//		}
	//		request.setAttribute("msg",msg);
	//		return SUCCESS;
	//	}
	
	
	
	/**
	 * BEAN类  咨询信息设定
	 * @param bean BEAN类  咨询信息
	 */
	//	/**
	//	 * <p>
	//	 * 编辑。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>编辑。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String edit() {
	//		log.info("C301Action edit.........");
	//		String id=request.getParameter("id");
	//		if(id!=null){
	//			TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//			bean1.setId(id);
	//			bean=consultMsgManager.findDataById(bean1);
	//		}else{
	//			SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
	//			request.setAttribute("date", sdf.format(new Date()));
	//		}
	//		return "edit";
	//	}
	//	/**
	//	 * <p>
	//	 * 信息保存
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>新增。</div>
	//	 * <div>修改。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String save() {
	//		log.info("C301Action edit.........");
	//		if(bean!=null){
	//			String msg="1";
	//			try {
	//					BaseUserBean user = (BaseUserBean) request.getSession().getAttribute(CommonConstant.SESSION_KEY_USER_CUSTOMER_SERVICE);
	//					if(user!=null){
	//						bean.setCreate_ip(getIpAddr());
	//						bean.setCreate_id(user.getUser_id());
	//						bean.setUpdate_ip(getIpAddr());
	//						bean.setUpdate_id(user.getUser_id());
	//					}
	//					msg=consultMsgManager.saveOrUpdateData(bean);
	//			} catch (Exception e) {
	//				msg=e.getMessage();
	//			}
	//			request.setAttribute("msg",msg);
	//		}else{
	//			request.setAttribute("msg", "信息保存失败!");
	//		}
	//		return SUCCESS;
	//	}
	//	/**
	//	 * <p>
	//	 * 删除。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>删除。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String del() {
	//		log.info("C301Action del.........");
	//		
	//		String id=request.getParameter("id");
	//		TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//		bean1.setId(id);
	//		String msg="1";
	//		try {
	//			msg=consultMsgManager.deleteDataById(bean1);
	//		} catch (Exception e) {
	//			msg=e.getMessage();
	//		}
	//		request.setAttribute("msg",msg);
	//		return SUCCESS;
	//	}
	
	
	
	/**
	 * BEAN类  咨询信息取得
	 * @return BEAN类  咨询信息
	 */
	//	/**
	//	 * <p>
	//	 * 编辑。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>编辑。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String edit() {
	//		log.info("C301Action edit.........");
	//		String id=request.getParameter("id");
	//		if(id!=null){
	//			TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//			bean1.setId(id);
	//			bean=consultMsgManager.findDataById(bean1);
	//		}else{
	//			SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
	//			request.setAttribute("date", sdf.format(new Date()));
	//		}
	//		return "edit";
	//	}
	//	/**
	//	 * <p>
	//	 * 信息保存
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>新增。</div>
	//	 * <div>修改。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String save() {
	//		log.info("C301Action edit.........");
	//		if(bean!=null){
	//			String msg="1";
	//			try {
	//					BaseUserBean user = (BaseUserBean) request.getSession().getAttribute(CommonConstant.SESSION_KEY_USER_CUSTOMER_SERVICE);
	//					if(user!=null){
	//						bean.setCreate_ip(getIpAddr());
	//						bean.setCreate_id(user.getUser_id());
	//						bean.setUpdate_ip(getIpAddr());
	//						bean.setUpdate_id(user.getUser_id());
	//					}
	//					msg=consultMsgManager.saveOrUpdateData(bean);
	//			} catch (Exception e) {
	//				msg=e.getMessage();
	//			}
	//			request.setAttribute("msg",msg);
	//		}else{
	//			request.setAttribute("msg", "信息保存失败!");
	//		}
	//		return SUCCESS;
	//	}
	//	/**
	//	 * <p>
	//	 * 删除。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>删除。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String del() {
	//		log.info("C301Action del.........");
	//		
	//		String id=request.getParameter("id");
	//		TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//		bean1.setId(id);
	//		String msg="1";
	//		try {
	//			msg=consultMsgManager.deleteDataById(bean1);
	//		} catch (Exception e) {
	//			msg=e.getMessage();
	//		}
	//		request.setAttribute("msg",msg);
	//		return SUCCESS;
	//	}
	
	
	
	/**
	 * BEAN类  咨询信息取得
	 * @return BEAN类  咨询信息
	 */
	//	/**
	//	 * <p>
	//	 * 编辑。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>编辑。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String edit() {
	//		log.info("C301Action edit.........");
	//		String id=request.getParameter("id");
	//		if(id!=null){
	//			TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//			bean1.setId(id);
	//			bean=consultMsgManager.findDataById(bean1);
	//		}else{
	//			SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
	//			request.setAttribute("date", sdf.format(new Date()));
	//		}
	//		return "edit";
	//	}
	//	/**
	//	 * <p>
	//	 * 信息保存
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>新增。</div>
	//	 * <div>修改。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String save() {
	//		log.info("C301Action edit.........");
	//		if(bean!=null){
	//			String msg="1";
	//			try {
	//					BaseUserBean user = (BaseUserBean) request.getSession().getAttribute(CommonConstant.SESSION_KEY_USER_CUSTOMER_SERVICE);
	//					if(user!=null){
	//						bean.setCreate_ip(getIpAddr());
	//						bean.setCreate_id(user.getUser_id());
	//						bean.setUpdate_ip(getIpAddr());
	//						bean.setUpdate_id(user.getUser_id());
	//					}
	//					msg=consultMsgManager.saveOrUpdateData(bean);
	//			} catch (Exception e) {
	//				msg=e.getMessage();
	//			}
	//			request.setAttribute("msg",msg);
	//		}else{
	//			request.setAttribute("msg", "信息保存失败!");
	//		}
	//		return SUCCESS;
	//	}
	//	/**
	//	 * <p>
	//	 * 删除。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>删除。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String del() {
	//		log.info("C301Action del.........");
	//		
	//		String id=request.getParameter("id");
	//		TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//		bean1.setId(id);
	//		String msg="1";
	//		try {
	//			msg=consultMsgManager.deleteDataById(bean1);
	//		} catch (Exception e) {
	//			msg=e.getMessage();
	//		}
	//		request.setAttribute("msg",msg);
	//		return SUCCESS;
	//	}
	
	
	
	/**
	 * BEAN类  咨询信息设定
	 * @param bean BEAN类  咨询信息
	 */
	//	/**
	//	 * <p>
	//	 * 编辑。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>编辑。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String edit() {
	//		log.info("C301Action edit.........");
	//		String id=request.getParameter("id");
	//		if(id!=null){
	//			TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//			bean1.setId(id);
	//			bean=consultMsgManager.findDataById(bean1);
	//		}else{
	//			SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
	//			request.setAttribute("date", sdf.format(new Date()));
	//		}
	//		return "edit";
	//	}
	//	/**
	//	 * <p>
	//	 * 信息保存
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>新增。</div>
	//	 * <div>修改。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String save() {
	//		log.info("C301Action edit.........");
	//		if(bean!=null){
	//			String msg="1";
	//			try {
	//					BaseUserBean user = (BaseUserBean) request.getSession().getAttribute(CommonConstant.SESSION_KEY_USER_CUSTOMER_SERVICE);
	//					if(user!=null){
	//						bean.setCreate_ip(getIpAddr());
	//						bean.setCreate_id(user.getUser_id());
	//						bean.setUpdate_ip(getIpAddr());
	//						bean.setUpdate_id(user.getUser_id());
	//					}
	//					msg=consultMsgManager.saveOrUpdateData(bean);
	//			} catch (Exception e) {
	//				msg=e.getMessage();
	//			}
	//			request.setAttribute("msg",msg);
	//		}else{
	//			request.setAttribute("msg", "信息保存失败!");
	//		}
	//		return SUCCESS;
	//	}
	//	/**
	//	 * <p>
	//	 * 删除。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>删除。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String del() {
	//		log.info("C301Action del.........");
	//		
	//		String id=request.getParameter("id");
	//		TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//		bean1.setId(id);
	//		String msg="1";
	//		try {
	//			msg=consultMsgManager.deleteDataById(bean1);
	//		} catch (Exception e) {
	//			msg=e.getMessage();
	//		}
	//		request.setAttribute("msg",msg);
	//		return SUCCESS;
	//	}
	
	
	
	/**
	 * BEAN类  咨询信息取得
	 * @return BEAN类  咨询信息
	 */
	//	/**
	//	 * <p>
	//	 * 编辑。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>编辑。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String edit() {
	//		log.info("C301Action edit.........");
	//		String id=request.getParameter("id");
	//		if(id!=null){
	//			TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//			bean1.setId(id);
	//			bean=consultMsgManager.findDataById(bean1);
	//		}else{
	//			SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
	//			request.setAttribute("date", sdf.format(new Date()));
	//		}
	//		return "edit";
	//	}
	//	/**
	//	 * <p>
	//	 * 信息保存
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>新增。</div>
	//	 * <div>修改。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String save() {
	//		log.info("C301Action edit.........");
	//		if(bean!=null){
	//			String msg="1";
	//			try {
	//					BaseUserBean user = (BaseUserBean) request.getSession().getAttribute(CommonConstant.SESSION_KEY_USER_CUSTOMER_SERVICE);
	//					if(user!=null){
	//						bean.setCreate_ip(getIpAddr());
	//						bean.setCreate_id(user.getUser_id());
	//						bean.setUpdate_ip(getIpAddr());
	//						bean.setUpdate_id(user.getUser_id());
	//					}
	//					msg=consultMsgManager.saveOrUpdateData(bean);
	//			} catch (Exception e) {
	//				msg=e.getMessage();
	//			}
	//			request.setAttribute("msg",msg);
	//		}else{
	//			request.setAttribute("msg", "信息保存失败!");
	//		}
	//		return SUCCESS;
	//	}
	//	/**
	//	 * <p>
	//	 * 删除。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>删除。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String del() {
	//		log.info("C301Action del.........");
	//		
	//		String id=request.getParameter("id");
	//		TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//		bean1.setId(id);
	//		String msg="1";
	//		try {
	//			msg=consultMsgManager.deleteDataById(bean1);
	//		} catch (Exception e) {
	//			msg=e.getMessage();
	//		}
	//		request.setAttribute("msg",msg);
	//		return SUCCESS;
	//	}
	
	
	
	/**
	 * BEAN类  咨询信息设定
	 * @param bean BEAN类  咨询信息
	 */
	//	/**
	//	 * <p>
	//	 * 编辑。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>编辑。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String edit() {
	//		log.info("C301Action edit.........");
	//		String id=request.getParameter("id");
	//		if(id!=null){
	//			TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//			bean1.setId(id);
	//			bean=consultMsgManager.findDataById(bean1);
	//		}else{
	//			SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
	//			request.setAttribute("date", sdf.format(new Date()));
	//		}
	//		return "edit";
	//	}
	//	/**
	//	 * <p>
	//	 * 信息保存
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>新增。</div>
	//	 * <div>修改。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String save() {
	//		log.info("C301Action edit.........");
	//		if(bean!=null){
	//			String msg="1";
	//			try {
	//					BaseUserBean user = (BaseUserBean) request.getSession().getAttribute(CommonConstant.SESSION_KEY_USER_CUSTOMER_SERVICE);
	//					if(user!=null){
	//						bean.setCreate_ip(getIpAddr());
	//						bean.setCreate_id(user.getUser_id());
	//						bean.setUpdate_ip(getIpAddr());
	//						bean.setUpdate_id(user.getUser_id());
	//					}
	//					msg=consultMsgManager.saveOrUpdateData(bean);
	//			} catch (Exception e) {
	//				msg=e.getMessage();
	//			}
	//			request.setAttribute("msg",msg);
	//		}else{
	//			request.setAttribute("msg", "信息保存失败!");
	//		}
	//		return SUCCESS;
	//	}
	//	/**
	//	 * <p>
	//	 * 删除。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>删除。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String del() {
	//		log.info("C301Action del.........");
	//		
	//		String id=request.getParameter("id");
	//		TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//		bean1.setId(id);
	//		String msg="1";
	//		try {
	//			msg=consultMsgManager.deleteDataById(bean1);
	//		} catch (Exception e) {
	//			msg=e.getMessage();
	//		}
	//		request.setAttribute("msg",msg);
	//		return SUCCESS;
	//	}
	
	

	/**
	 * BEAN类  咨询信息取得
	 * @return BEAN类  咨询信息
	 */
	//	/**
	//	 * <p>
	//	 * 编辑。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>编辑。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String edit() {
	//		log.info("C301Action edit.........");
	//		String id=request.getParameter("id");
	//		if(id!=null){
	//			TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//			bean1.setId(id);
	//			bean=consultMsgManager.findDataById(bean1);
	//		}else{
	//			SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
	//			request.setAttribute("date", sdf.format(new Date()));
	//		}
	//		return "edit";
	//	}
	//	/**
	//	 * <p>
	//	 * 信息保存
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>新增。</div>
	//	 * <div>修改。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String save() {
	//		log.info("C301Action edit.........");
	//		if(bean!=null){
	//			String msg="1";
	//			try {
	//					BaseUserBean user = (BaseUserBean) request.getSession().getAttribute(CommonConstant.SESSION_KEY_USER_CUSTOMER_SERVICE);
	//					if(user!=null){
	//						bean.setCreate_ip(getIpAddr());
	//						bean.setCreate_id(user.getUser_id());
	//						bean.setUpdate_ip(getIpAddr());
	//						bean.setUpdate_id(user.getUser_id());
	//					}
	//					msg=consultMsgManager.saveOrUpdateData(bean);
	//			} catch (Exception e) {
	//				msg=e.getMessage();
	//			}
	//			request.setAttribute("msg",msg);
	//		}else{
	//			request.setAttribute("msg", "信息保存失败!");
	//		}
	//		return SUCCESS;
	//	}
	//	/**
	//	 * <p>
	//	 * 删除。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>删除。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String del() {
	//		log.info("C301Action del.........");
	//		
	//		String id=request.getParameter("id");
	//		TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//		bean1.setId(id);
	//		String msg="1";
	//		try {
	//			msg=consultMsgManager.deleteDataById(bean1);
	//		} catch (Exception e) {
	//			msg=e.getMessage();
	//		}
	//		request.setAttribute("msg",msg);
	//		return SUCCESS;
	//	}
	
	
	
	/**
	 * BEAN类  咨询信息取得
	 * @return BEAN类  咨询信息
	 */
	//	/**
	//	 * <p>
	//	 * 编辑。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>编辑。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String edit() {
	//		log.info("C301Action edit.........");
	//		String id=request.getParameter("id");
	//		if(id!=null){
	//			TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//			bean1.setId(id);
	//			bean=consultMsgManager.findDataById(bean1);
	//		}else{
	//			SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
	//			request.setAttribute("date", sdf.format(new Date()));
	//		}
	//		return "edit";
	//	}
	//	/**
	//	 * <p>
	//	 * 信息保存
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>新增。</div>
	//	 * <div>修改。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String save() {
	//		log.info("C301Action edit.........");
	//		if(bean!=null){
	//			String msg="1";
	//			try {
	//					BaseUserBean user = (BaseUserBean) request.getSession().getAttribute(CommonConstant.SESSION_KEY_USER_CUSTOMER_SERVICE);
	//					if(user!=null){
	//						bean.setCreate_ip(getIpAddr());
	//						bean.setCreate_id(user.getUser_id());
	//						bean.setUpdate_ip(getIpAddr());
	//						bean.setUpdate_id(user.getUser_id());
	//					}
	//					msg=consultMsgManager.saveOrUpdateData(bean);
	//			} catch (Exception e) {
	//				msg=e.getMessage();
	//			}
	//			request.setAttribute("msg",msg);
	//		}else{
	//			request.setAttribute("msg", "信息保存失败!");
	//		}
	//		return SUCCESS;
	//	}
	//	/**
	//	 * <p>
	//	 * 删除。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>删除。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String del() {
	//		log.info("C301Action del.........");
	//		
	//		String id=request.getParameter("id");
	//		TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//		bean1.setId(id);
	//		String msg="1";
	//		try {
	//			msg=consultMsgManager.deleteDataById(bean1);
	//		} catch (Exception e) {
	//			msg=e.getMessage();
	//		}
	//		request.setAttribute("msg",msg);
	//		return SUCCESS;
	//	}
	
	
	
	/**
	 * BEAN类  咨询信息设定
	 * @param bean BEAN类  咨询信息
	 */
	//	/**
	//	 * <p>
	//	 * 编辑。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>编辑。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String edit() {
	//		log.info("C301Action edit.........");
	//		String id=request.getParameter("id");
	//		if(id!=null){
	//			TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//			bean1.setId(id);
	//			bean=consultMsgManager.findDataById(bean1);
	//		}else{
	//			SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
	//			request.setAttribute("date", sdf.format(new Date()));
	//		}
	//		return "edit";
	//	}
	//	/**
	//	 * <p>
	//	 * 信息保存
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>新增。</div>
	//	 * <div>修改。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String save() {
	//		log.info("C301Action edit.........");
	//		if(bean!=null){
	//			String msg="1";
	//			try {
	//					BaseUserBean user = (BaseUserBean) request.getSession().getAttribute(CommonConstant.SESSION_KEY_USER_CUSTOMER_SERVICE);
	//					if(user!=null){
	//						bean.setCreate_ip(getIpAddr());
	//						bean.setCreate_id(user.getUser_id());
	//						bean.setUpdate_ip(getIpAddr());
	//						bean.setUpdate_id(user.getUser_id());
	//					}
	//					msg=consultMsgManager.saveOrUpdateData(bean);
	//			} catch (Exception e) {
	//				msg=e.getMessage();
	//			}
	//			request.setAttribute("msg",msg);
	//		}else{
	//			request.setAttribute("msg", "信息保存失败!");
	//		}
	//		return SUCCESS;
	//	}
	//	/**
	//	 * <p>
	//	 * 删除。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>删除。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String del() {
	//		log.info("C301Action del.........");
	//		
	//		String id=request.getParameter("id");
	//		TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//		bean1.setId(id);
	//		String msg="1";
	//		try {
	//			msg=consultMsgManager.deleteDataById(bean1);
	//		} catch (Exception e) {
	//			msg=e.getMessage();
	//		}
	//		request.setAttribute("msg",msg);
	//		return SUCCESS;
	//	}
	
	
	
	/**
	 * BEAN类  咨询信息取得
	 * @return BEAN类  咨询信息
	 */
	//	/**
	//	 * <p>
	//	 * 编辑。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>编辑。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String edit() {
	//		log.info("C301Action edit.........");
	//		String id=request.getParameter("id");
	//		if(id!=null){
	//			TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//			bean1.setId(id);
	//			bean=consultMsgManager.findDataById(bean1);
	//		}else{
	//			SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
	//			request.setAttribute("date", sdf.format(new Date()));
	//		}
	//		return "edit";
	//	}
	//	/**
	//	 * <p>
	//	 * 信息保存
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>新增。</div>
	//	 * <div>修改。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String save() {
	//		log.info("C301Action edit.........");
	//		if(bean!=null){
	//			String msg="1";
	//			try {
	//					BaseUserBean user = (BaseUserBean) request.getSession().getAttribute(CommonConstant.SESSION_KEY_USER_CUSTOMER_SERVICE);
	//					if(user!=null){
	//						bean.setCreate_ip(getIpAddr());
	//						bean.setCreate_id(user.getUser_id());
	//						bean.setUpdate_ip(getIpAddr());
	//						bean.setUpdate_id(user.getUser_id());
	//					}
	//					msg=consultMsgManager.saveOrUpdateData(bean);
	//			} catch (Exception e) {
	//				msg=e.getMessage();
	//			}
	//			request.setAttribute("msg",msg);
	//		}else{
	//			request.setAttribute("msg", "信息保存失败!");
	//		}
	//		return SUCCESS;
	//	}
	//	/**
	//	 * <p>
	//	 * 删除。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>删除。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String del() {
	//		log.info("C301Action del.........");
	//		
	//		String id=request.getParameter("id");
	//		TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//		bean1.setId(id);
	//		String msg="1";
	//		try {
	//			msg=consultMsgManager.deleteDataById(bean1);
	//		} catch (Exception e) {
	//			msg=e.getMessage();
	//		}
	//		request.setAttribute("msg",msg);
	//		return SUCCESS;
	//	}
	
	
	
	/**
	 * BEAN类  咨询信息取得
	 * @return BEAN类  咨询信息
	 */
	//	/**
	//	 * <p>
	//	 * 编辑。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>编辑。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String edit() {
	//		log.info("C301Action edit.........");
	//		String id=request.getParameter("id");
	//		if(id!=null){
	//			TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//			bean1.setId(id);
	//			bean=consultMsgManager.findDataById(bean1);
	//		}else{
	//			SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
	//			request.setAttribute("date", sdf.format(new Date()));
	//		}
	//		return "edit";
	//	}
	//	/**
	//	 * <p>
	//	 * 信息保存
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>新增。</div>
	//	 * <div>修改。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String save() {
	//		log.info("C301Action edit.........");
	//		if(bean!=null){
	//			String msg="1";
	//			try {
	//					BaseUserBean user = (BaseUserBean) request.getSession().getAttribute(CommonConstant.SESSION_KEY_USER_CUSTOMER_SERVICE);
	//					if(user!=null){
	//						bean.setCreate_ip(getIpAddr());
	//						bean.setCreate_id(user.getUser_id());
	//						bean.setUpdate_ip(getIpAddr());
	//						bean.setUpdate_id(user.getUser_id());
	//					}
	//					msg=consultMsgManager.saveOrUpdateData(bean);
	//			} catch (Exception e) {
	//				msg=e.getMessage();
	//			}
	//			request.setAttribute("msg",msg);
	//		}else{
	//			request.setAttribute("msg", "信息保存失败!");
	//		}
	//		return SUCCESS;
	//	}
	//	/**
	//	 * <p>
	//	 * 删除。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>删除。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String del() {
	//		log.info("C301Action del.........");
	//		
	//		String id=request.getParameter("id");
	//		TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//		bean1.setId(id);
	//		String msg="1";
	//		try {
	//			msg=consultMsgManager.deleteDataById(bean1);
	//		} catch (Exception e) {
	//			msg=e.getMessage();
	//		}
	//		request.setAttribute("msg",msg);
	//		return SUCCESS;
	//	}
	
	
	
	/**
	 * BEAN类  咨询信息设定
	 * @param bean BEAN类  咨询信息
	 */
	//	/**
	//	 * <p>
	//	 * 编辑。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>编辑。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String edit() {
	//		log.info("C301Action edit.........");
	//		String id=request.getParameter("id");
	//		if(id!=null){
	//			TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//			bean1.setId(id);
	//			bean=consultMsgManager.findDataById(bean1);
	//		}else{
	//			SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
	//			request.setAttribute("date", sdf.format(new Date()));
	//		}
	//		return "edit";
	//	}
	//	/**
	//	 * <p>
	//	 * 信息保存
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>新增。</div>
	//	 * <div>修改。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String save() {
	//		log.info("C301Action edit.........");
	//		if(bean!=null){
	//			String msg="1";
	//			try {
	//					BaseUserBean user = (BaseUserBean) request.getSession().getAttribute(CommonConstant.SESSION_KEY_USER_CUSTOMER_SERVICE);
	//					if(user!=null){
	//						bean.setCreate_ip(getIpAddr());
	//						bean.setCreate_id(user.getUser_id());
	//						bean.setUpdate_ip(getIpAddr());
	//						bean.setUpdate_id(user.getUser_id());
	//					}
	//					msg=consultMsgManager.saveOrUpdateData(bean);
	//			} catch (Exception e) {
	//				msg=e.getMessage();
	//			}
	//			request.setAttribute("msg",msg);
	//		}else{
	//			request.setAttribute("msg", "信息保存失败!");
	//		}
	//		return SUCCESS;
	//	}
	//	/**
	//	 * <p>
	//	 * 删除。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>删除。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String del() {
	//		log.info("C301Action del.........");
	//		
	//		String id=request.getParameter("id");
	//		TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//		bean1.setId(id);
	//		String msg="1";
	//		try {
	//			msg=consultMsgManager.deleteDataById(bean1);
	//		} catch (Exception e) {
	//			msg=e.getMessage();
	//		}
	//		request.setAttribute("msg",msg);
	//		return SUCCESS;
	//	}
	
	
	
	/**
	 * BEAN类  咨询信息取得
	 * @return BEAN类  咨询信息
	 */
	//	/**
	//	 * <p>
	//	 * 编辑。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>编辑。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String edit() {
	//		log.info("C301Action edit.........");
	//		String id=request.getParameter("id");
	//		if(id!=null){
	//			TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//			bean1.setId(id);
	//			bean=consultMsgManager.findDataById(bean1);
	//		}else{
	//			SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
	//			request.setAttribute("date", sdf.format(new Date()));
	//		}
	//		return "edit";
	//	}
	//	/**
	//	 * <p>
	//	 * 信息保存
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>新增。</div>
	//	 * <div>修改。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String save() {
	//		log.info("C301Action edit.........");
	//		if(bean!=null){
	//			String msg="1";
	//			try {
	//					BaseUserBean user = (BaseUserBean) request.getSession().getAttribute(CommonConstant.SESSION_KEY_USER_CUSTOMER_SERVICE);
	//					if(user!=null){
	//						bean.setCreate_ip(getIpAddr());
	//						bean.setCreate_id(user.getUser_id());
	//						bean.setUpdate_ip(getIpAddr());
	//						bean.setUpdate_id(user.getUser_id());
	//					}
	//					msg=consultMsgManager.saveOrUpdateData(bean);
	//			} catch (Exception e) {
	//				msg=e.getMessage();
	//			}
	//			request.setAttribute("msg",msg);
	//		}else{
	//			request.setAttribute("msg", "信息保存失败!");
	//		}
	//		return SUCCESS;
	//	}
	//	/**
	//	 * <p>
	//	 * 删除。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>删除。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String del() {
	//		log.info("C301Action del.........");
	//		
	//		String id=request.getParameter("id");
	//		TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//		bean1.setId(id);
	//		String msg="1";
	//		try {
	//			msg=consultMsgManager.deleteDataById(bean1);
	//		} catch (Exception e) {
	//			msg=e.getMessage();
	//		}
	//		request.setAttribute("msg",msg);
	//		return SUCCESS;
	//	}
	
	
	
	/**
	 * BEAN类  咨询信息设定
	 * @param bean BEAN类  咨询信息
	 */
	//	/**
	//	 * <p>
	//	 * 编辑。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>编辑。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String edit() {
	//		log.info("C301Action edit.........");
	//		String id=request.getParameter("id");
	//		if(id!=null){
	//			TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//			bean1.setId(id);
	//			bean=consultMsgManager.findDataById(bean1);
	//		}else{
	//			SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
	//			request.setAttribute("date", sdf.format(new Date()));
	//		}
	//		return "edit";
	//	}
	//	/**
	//	 * <p>
	//	 * 信息保存
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>新增。</div>
	//	 * <div>修改。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String save() {
	//		log.info("C301Action edit.........");
	//		if(bean!=null){
	//			String msg="1";
	//			try {
	//					BaseUserBean user = (BaseUserBean) request.getSession().getAttribute(CommonConstant.SESSION_KEY_USER_CUSTOMER_SERVICE);
	//					if(user!=null){
	//						bean.setCreate_ip(getIpAddr());
	//						bean.setCreate_id(user.getUser_id());
	//						bean.setUpdate_ip(getIpAddr());
	//						bean.setUpdate_id(user.getUser_id());
	//					}
	//					msg=consultMsgManager.saveOrUpdateData(bean);
	//			} catch (Exception e) {
	//				msg=e.getMessage();
	//			}
	//			request.setAttribute("msg",msg);
	//		}else{
	//			request.setAttribute("msg", "信息保存失败!");
	//		}
	//		return SUCCESS;
	//	}
	//	/**
	//	 * <p>
	//	 * 删除。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>删除。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String del() {
	//		log.info("C301Action del.........");
	//		
	//		String id=request.getParameter("id");
	//		TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//		bean1.setId(id);
	//		String msg="1";
	//		try {
	//			msg=consultMsgManager.deleteDataById(bean1);
	//		} catch (Exception e) {
	//			msg=e.getMessage();
	//		}
	//		request.setAttribute("msg",msg);
	//		return SUCCESS;
	//	}
	
	
	
	/**
	 * BEAN类  咨询信息取得
	 * @return BEAN类  咨询信息
	 */
	//	/**
	//	 * <p>
	//	 * 编辑。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>编辑。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String edit() {
	//		log.info("C301Action edit.........");
	//		String id=request.getParameter("id");
	//		if(id!=null){
	//			TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//			bean1.setId(id);
	//			bean=consultMsgManager.findDataById(bean1);
	//		}else{
	//			SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
	//			request.setAttribute("date", sdf.format(new Date()));
	//		}
	//		return "edit";
	//	}
	//	/**
	//	 * <p>
	//	 * 信息保存
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>新增。</div>
	//	 * <div>修改。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String save() {
	//		log.info("C301Action edit.........");
	//		if(bean!=null){
	//			String msg="1";
	//			try {
	//					BaseUserBean user = (BaseUserBean) request.getSession().getAttribute(CommonConstant.SESSION_KEY_USER_CUSTOMER_SERVICE);
	//					if(user!=null){
	//						bean.setCreate_ip(getIpAddr());
	//						bean.setCreate_id(user.getUser_id());
	//						bean.setUpdate_ip(getIpAddr());
	//						bean.setUpdate_id(user.getUser_id());
	//					}
	//					msg=consultMsgManager.saveOrUpdateData(bean);
	//			} catch (Exception e) {
	//				msg=e.getMessage();
	//			}
	//			request.setAttribute("msg",msg);
	//		}else{
	//			request.setAttribute("msg", "信息保存失败!");
	//		}
	//		return SUCCESS;
	//	}
	//	/**
	//	 * <p>
	//	 * 删除。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>删除。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String del() {
	//		log.info("C301Action del.........");
	//		
	//		String id=request.getParameter("id");
	//		TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//		bean1.setId(id);
	//		String msg="1";
	//		try {
	//			msg=consultMsgManager.deleteDataById(bean1);
	//		} catch (Exception e) {
	//			msg=e.getMessage();
	//		}
	//		request.setAttribute("msg",msg);
	//		return SUCCESS;
	//	}
	
	
	
	/**
	 * BEAN类  咨询信息取得
	 * @return BEAN类  咨询信息
	 */
	//	/**
	//	 * <p>
	//	 * 编辑。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>编辑。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String edit() {
	//		log.info("C301Action edit.........");
	//		String id=request.getParameter("id");
	//		if(id!=null){
	//			TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//			bean1.setId(id);
	//			bean=consultMsgManager.findDataById(bean1);
	//		}else{
	//			SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
	//			request.setAttribute("date", sdf.format(new Date()));
	//		}
	//		return "edit";
	//	}
	//	/**
	//	 * <p>
	//	 * 信息保存
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>新增。</div>
	//	 * <div>修改。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String save() {
	//		log.info("C301Action edit.........");
	//		if(bean!=null){
	//			String msg="1";
	//			try {
	//					BaseUserBean user = (BaseUserBean) request.getSession().getAttribute(CommonConstant.SESSION_KEY_USER_CUSTOMER_SERVICE);
	//					if(user!=null){
	//						bean.setCreate_ip(getIpAddr());
	//						bean.setCreate_id(user.getUser_id());
	//						bean.setUpdate_ip(getIpAddr());
	//						bean.setUpdate_id(user.getUser_id());
	//					}
	//					msg=consultMsgManager.saveOrUpdateData(bean);
	//			} catch (Exception e) {
	//				msg=e.getMessage();
	//			}
	//			request.setAttribute("msg",msg);
	//		}else{
	//			request.setAttribute("msg", "信息保存失败!");
	//		}
	//		return SUCCESS;
	//	}
	//	/**
	//	 * <p>
	//	 * 删除。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>删除。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String del() {
	//		log.info("C301Action del.........");
	//		
	//		String id=request.getParameter("id");
	//		TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//		bean1.setId(id);
	//		String msg="1";
	//		try {
	//			msg=consultMsgManager.deleteDataById(bean1);
	//		} catch (Exception e) {
	//			msg=e.getMessage();
	//		}
	//		request.setAttribute("msg",msg);
	//		return SUCCESS;
	//	}
	
	
	
	/**
	 * BEAN类  咨询信息设定
	 * @param bean BEAN类  咨询信息
	 */
	//	/**
	//	 * <p>
	//	 * 编辑。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>编辑。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String edit() {
	//		log.info("C301Action edit.........");
	//		String id=request.getParameter("id");
	//		if(id!=null){
	//			TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//			bean1.setId(id);
	//			bean=consultMsgManager.findDataById(bean1);
	//		}else{
	//			SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
	//			request.setAttribute("date", sdf.format(new Date()));
	//		}
	//		return "edit";
	//	}
	//	/**
	//	 * <p>
	//	 * 信息保存
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>新增。</div>
	//	 * <div>修改。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String save() {
	//		log.info("C301Action edit.........");
	//		if(bean!=null){
	//			String msg="1";
	//			try {
	//					BaseUserBean user = (BaseUserBean) request.getSession().getAttribute(CommonConstant.SESSION_KEY_USER_CUSTOMER_SERVICE);
	//					if(user!=null){
	//						bean.setCreate_ip(getIpAddr());
	//						bean.setCreate_id(user.getUser_id());
	//						bean.setUpdate_ip(getIpAddr());
	//						bean.setUpdate_id(user.getUser_id());
	//					}
	//					msg=consultMsgManager.saveOrUpdateData(bean);
	//			} catch (Exception e) {
	//				msg=e.getMessage();
	//			}
	//			request.setAttribute("msg",msg);
	//		}else{
	//			request.setAttribute("msg", "信息保存失败!");
	//		}
	//		return SUCCESS;
	//	}
	//	/**
	//	 * <p>
	//	 * 删除。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>删除。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String del() {
	//		log.info("C301Action del.........");
	//		
	//		String id=request.getParameter("id");
	//		TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//		bean1.setId(id);
	//		String msg="1";
	//		try {
	//			msg=consultMsgManager.deleteDataById(bean1);
	//		} catch (Exception e) {
	//			msg=e.getMessage();
	//		}
	//		request.setAttribute("msg",msg);
	//		return SUCCESS;
	//	}
	
	
	
	/**
	 * BEAN类  咨询信息取得
	 * @return BEAN类  咨询信息
	 */
	//	/**
	//	 * <p>
	//	 * 编辑。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>编辑。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String edit() {
	//		log.info("C301Action edit.........");
	//		String id=request.getParameter("id");
	//		if(id!=null){
	//			TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//			bean1.setId(id);
	//			bean=consultMsgManager.findDataById(bean1);
	//		}else{
	//			SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
	//			request.setAttribute("date", sdf.format(new Date()));
	//		}
	//		return "edit";
	//	}
	//	/**
	//	 * <p>
	//	 * 信息保存
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>新增。</div>
	//	 * <div>修改。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String save() {
	//		log.info("C301Action edit.........");
	//		if(bean!=null){
	//			String msg="1";
	//			try {
	//					BaseUserBean user = (BaseUserBean) request.getSession().getAttribute(CommonConstant.SESSION_KEY_USER_CUSTOMER_SERVICE);
	//					if(user!=null){
	//						bean.setCreate_ip(getIpAddr());
	//						bean.setCreate_id(user.getUser_id());
	//						bean.setUpdate_ip(getIpAddr());
	//						bean.setUpdate_id(user.getUser_id());
	//					}
	//					msg=consultMsgManager.saveOrUpdateData(bean);
	//			} catch (Exception e) {
	//				msg=e.getMessage();
	//			}
	//			request.setAttribute("msg",msg);
	//		}else{
	//			request.setAttribute("msg", "信息保存失败!");
	//		}
	//		return SUCCESS;
	//	}
	//	/**
	//	 * <p>
	//	 * 删除。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>删除。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String del() {
	//		log.info("C301Action del.........");
	//		
	//		String id=request.getParameter("id");
	//		TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//		bean1.setId(id);
	//		String msg="1";
	//		try {
	//			msg=consultMsgManager.deleteDataById(bean1);
	//		} catch (Exception e) {
	//			msg=e.getMessage();
	//		}
	//		request.setAttribute("msg",msg);
	//		return SUCCESS;
	//	}
	
	
	
	/**
	 * BEAN类  咨询信息取得
	 * @return BEAN类  咨询信息
	 */
	//	/**
	//	 * <p>
	//	 * 编辑。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>编辑。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String edit() {
	//		log.info("C301Action edit.........");
	//		String id=request.getParameter("id");
	//		if(id!=null){
	//			TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//			bean1.setId(id);
	//			bean=consultMsgManager.findDataById(bean1);
	//		}else{
	//			SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
	//			request.setAttribute("date", sdf.format(new Date()));
	//		}
	//		return "edit";
	//	}
	//	/**
	//	 * <p>
	//	 * 信息保存
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>新增。</div>
	//	 * <div>修改。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String save() {
	//		log.info("C301Action edit.........");
	//		if(bean!=null){
	//			String msg="1";
	//			try {
	//					BaseUserBean user = (BaseUserBean) request.getSession().getAttribute(CommonConstant.SESSION_KEY_USER_CUSTOMER_SERVICE);
	//					if(user!=null){
	//						bean.setCreate_ip(getIpAddr());
	//						bean.setCreate_id(user.getUser_id());
	//						bean.setUpdate_ip(getIpAddr());
	//						bean.setUpdate_id(user.getUser_id());
	//					}
	//					msg=consultMsgManager.saveOrUpdateData(bean);
	//			} catch (Exception e) {
	//				msg=e.getMessage();
	//			}
	//			request.setAttribute("msg",msg);
	//		}else{
	//			request.setAttribute("msg", "信息保存失败!");
	//		}
	//		return SUCCESS;
	//	}
	//	/**
	//	 * <p>
	//	 * 删除。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>删除。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String del() {
	//		log.info("C301Action del.........");
	//		
	//		String id=request.getParameter("id");
	//		TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//		bean1.setId(id);
	//		String msg="1";
	//		try {
	//			msg=consultMsgManager.deleteDataById(bean1);
	//		} catch (Exception e) {
	//			msg=e.getMessage();
	//		}
	//		request.setAttribute("msg",msg);
	//		return SUCCESS;
	//	}
	
	

	/**
	 * BEAN类  咨询信息设定
	 * @param bean BEAN类  咨询信息
	 */
	//	/**
	//	 * <p>
	//	 * 编辑。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>编辑。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String edit() {
	//		log.info("C301Action edit.........");
	//		String id=request.getParameter("id");
	//		if(id!=null){
	//			TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//			bean1.setId(id);
	//			bean=consultMsgManager.findDataById(bean1);
	//		}else{
	//			SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
	//			request.setAttribute("date", sdf.format(new Date()));
	//		}
	//		return "edit";
	//	}
	//	/**
	//	 * <p>
	//	 * 信息保存
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>新增。</div>
	//	 * <div>修改。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String save() {
	//		log.info("C301Action edit.........");
	//		if(bean!=null){
	//			String msg="1";
	//			try {
	//					BaseUserBean user = (BaseUserBean) request.getSession().getAttribute(CommonConstant.SESSION_KEY_USER_CUSTOMER_SERVICE);
	//					if(user!=null){
	//						bean.setCreate_ip(getIpAddr());
	//						bean.setCreate_id(user.getUser_id());
	//						bean.setUpdate_ip(getIpAddr());
	//						bean.setUpdate_id(user.getUser_id());
	//					}
	//					msg=consultMsgManager.saveOrUpdateData(bean);
	//			} catch (Exception e) {
	//				msg=e.getMessage();
	//			}
	//			request.setAttribute("msg",msg);
	//		}else{
	//			request.setAttribute("msg", "信息保存失败!");
	//		}
	//		return SUCCESS;
	//	}
	//	/**
	//	 * <p>
	//	 * 删除。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>删除。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String del() {
	//		log.info("C301Action del.........");
	//		
	//		String id=request.getParameter("id");
	//		TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//		bean1.setId(id);
	//		String msg="1";
	//		try {
	//			msg=consultMsgManager.deleteDataById(bean1);
	//		} catch (Exception e) {
	//			msg=e.getMessage();
	//		}
	//		request.setAttribute("msg",msg);
	//		return SUCCESS;
	//	}
	
	
	
	/**
	 * BEAN类  咨询信息取得
	 * @return BEAN类  咨询信息
	 */
	//	/**
	//	 * <p>
	//	 * 编辑。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>编辑。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String edit() {
	//		log.info("C301Action edit.........");
	//		String id=request.getParameter("id");
	//		if(id!=null){
	//			TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//			bean1.setId(id);
	//			bean=consultMsgManager.findDataById(bean1);
	//		}else{
	//			SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
	//			request.setAttribute("date", sdf.format(new Date()));
	//		}
	//		return "edit";
	//	}
	//	/**
	//	 * <p>
	//	 * 信息保存
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>新增。</div>
	//	 * <div>修改。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String save() {
	//		log.info("C301Action edit.........");
	//		if(bean!=null){
	//			String msg="1";
	//			try {
	//					BaseUserBean user = (BaseUserBean) request.getSession().getAttribute(CommonConstant.SESSION_KEY_USER_CUSTOMER_SERVICE);
	//					if(user!=null){
	//						bean.setCreate_ip(getIpAddr());
	//						bean.setCreate_id(user.getUser_id());
	//						bean.setUpdate_ip(getIpAddr());
	//						bean.setUpdate_id(user.getUser_id());
	//					}
	//					msg=consultMsgManager.saveOrUpdateData(bean);
	//			} catch (Exception e) {
	//				msg=e.getMessage();
	//			}
	//			request.setAttribute("msg",msg);
	//		}else{
	//			request.setAttribute("msg", "信息保存失败!");
	//		}
	//		return SUCCESS;
	//	}
	//	/**
	//	 * <p>
	//	 * 删除。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>删除。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String del() {
	//		log.info("C301Action del.........");
	//		
	//		String id=request.getParameter("id");
	//		TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//		bean1.setId(id);
	//		String msg="1";
	//		try {
	//			msg=consultMsgManager.deleteDataById(bean1);
	//		} catch (Exception e) {
	//			msg=e.getMessage();
	//		}
	//		request.setAttribute("msg",msg);
	//		return SUCCESS;
	//	}
	
	
	
	/**
	 * BEAN类  咨询信息设定
	 * @param bean BEAN类  咨询信息
	 */
	//	/**
	//	 * <p>
	//	 * 编辑。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>编辑。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String edit() {
	//		log.info("C301Action edit.........");
	//		String id=request.getParameter("id");
	//		if(id!=null){
	//			TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//			bean1.setId(id);
	//			bean=consultMsgManager.findDataById(bean1);
	//		}else{
	//			SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
	//			request.setAttribute("date", sdf.format(new Date()));
	//		}
	//		return "edit";
	//	}
	//	/**
	//	 * <p>
	//	 * 信息保存
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>新增。</div>
	//	 * <div>修改。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String save() {
	//		log.info("C301Action edit.........");
	//		if(bean!=null){
	//			String msg="1";
	//			try {
	//					BaseUserBean user = (BaseUserBean) request.getSession().getAttribute(CommonConstant.SESSION_KEY_USER_CUSTOMER_SERVICE);
	//					if(user!=null){
	//						bean.setCreate_ip(getIpAddr());
	//						bean.setCreate_id(user.getUser_id());
	//						bean.setUpdate_ip(getIpAddr());
	//						bean.setUpdate_id(user.getUser_id());
	//					}
	//					msg=consultMsgManager.saveOrUpdateData(bean);
	//			} catch (Exception e) {
	//				msg=e.getMessage();
	//			}
	//			request.setAttribute("msg",msg);
	//		}else{
	//			request.setAttribute("msg", "信息保存失败!");
	//		}
	//		return SUCCESS;
	//	}
	//	/**
	//	 * <p>
	//	 * 删除。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>删除。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String del() {
	//		log.info("C301Action del.........");
	//		
	//		String id=request.getParameter("id");
	//		TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//		bean1.setId(id);
	//		String msg="1";
	//		try {
	//			msg=consultMsgManager.deleteDataById(bean1);
	//		} catch (Exception e) {
	//			msg=e.getMessage();
	//		}
	//		request.setAttribute("msg",msg);
	//		return SUCCESS;
	//	}
	
	
	
	/**
	 * BEAN类  咨询信息取得
	 * @return BEAN类  咨询信息
	 */
	//	/**
	//	 * <p>
	//	 * 编辑。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>编辑。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String edit() {
	//		log.info("C301Action edit.........");
	//		String id=request.getParameter("id");
	//		if(id!=null){
	//			TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//			bean1.setId(id);
	//			bean=consultMsgManager.findDataById(bean1);
	//		}else{
	//			SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
	//			request.setAttribute("date", sdf.format(new Date()));
	//		}
	//		return "edit";
	//	}
	//	/**
	//	 * <p>
	//	 * 信息保存
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>新增。</div>
	//	 * <div>修改。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String save() {
	//		log.info("C301Action edit.........");
	//		if(bean!=null){
	//			String msg="1";
	//			try {
	//					BaseUserBean user = (BaseUserBean) request.getSession().getAttribute(CommonConstant.SESSION_KEY_USER_CUSTOMER_SERVICE);
	//					if(user!=null){
	//						bean.setCreate_ip(getIpAddr());
	//						bean.setCreate_id(user.getUser_id());
	//						bean.setUpdate_ip(getIpAddr());
	//						bean.setUpdate_id(user.getUser_id());
	//					}
	//					msg=consultMsgManager.saveOrUpdateData(bean);
	//			} catch (Exception e) {
	//				msg=e.getMessage();
	//			}
	//			request.setAttribute("msg",msg);
	//		}else{
	//			request.setAttribute("msg", "信息保存失败!");
	//		}
	//		return SUCCESS;
	//	}
	//	/**
	//	 * <p>
	//	 * 删除。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>删除。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String del() {
	//		log.info("C301Action del.........");
	//		
	//		String id=request.getParameter("id");
	//		TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//		bean1.setId(id);
	//		String msg="1";
	//		try {
	//			msg=consultMsgManager.deleteDataById(bean1);
	//		} catch (Exception e) {
	//			msg=e.getMessage();
	//		}
	//		request.setAttribute("msg",msg);
	//		return SUCCESS;
	//	}
	
	
	
	/**
	 * BEAN类  咨询信息取得
	 * @return BEAN类  咨询信息
	 */
	//	/**
	//	 * <p>
	//	 * 编辑。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>编辑。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String edit() {
	//		log.info("C301Action edit.........");
	//		String id=request.getParameter("id");
	//		if(id!=null){
	//			TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//			bean1.setId(id);
	//			bean=consultMsgManager.findDataById(bean1);
	//		}else{
	//			SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
	//			request.setAttribute("date", sdf.format(new Date()));
	//		}
	//		return "edit";
	//	}
	//	/**
	//	 * <p>
	//	 * 信息保存
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>新增。</div>
	//	 * <div>修改。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String save() {
	//		log.info("C301Action edit.........");
	//		if(bean!=null){
	//			String msg="1";
	//			try {
	//					BaseUserBean user = (BaseUserBean) request.getSession().getAttribute(CommonConstant.SESSION_KEY_USER_CUSTOMER_SERVICE);
	//					if(user!=null){
	//						bean.setCreate_ip(getIpAddr());
	//						bean.setCreate_id(user.getUser_id());
	//						bean.setUpdate_ip(getIpAddr());
	//						bean.setUpdate_id(user.getUser_id());
	//					}
	//					msg=consultMsgManager.saveOrUpdateData(bean);
	//			} catch (Exception e) {
	//				msg=e.getMessage();
	//			}
	//			request.setAttribute("msg",msg);
	//		}else{
	//			request.setAttribute("msg", "信息保存失败!");
	//		}
	//		return SUCCESS;
	//	}
	//	/**
	//	 * <p>
	//	 * 删除。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>删除。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String del() {
	//		log.info("C301Action del.........");
	//		
	//		String id=request.getParameter("id");
	//		TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//		bean1.setId(id);
	//		String msg="1";
	//		try {
	//			msg=consultMsgManager.deleteDataById(bean1);
	//		} catch (Exception e) {
	//			msg=e.getMessage();
	//		}
	//		request.setAttribute("msg",msg);
	//		return SUCCESS;
	//	}
	
	
	
	/**
	 * BEAN类  咨询信息设定
	 * @param bean BEAN类  咨询信息
	 */
	//	/**
	//	 * <p>
	//	 * 编辑。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>编辑。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String edit() {
	//		log.info("C301Action edit.........");
	//		String id=request.getParameter("id");
	//		if(id!=null){
	//			TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//			bean1.setId(id);
	//			bean=consultMsgManager.findDataById(bean1);
	//		}else{
	//			SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
	//			request.setAttribute("date", sdf.format(new Date()));
	//		}
	//		return "edit";
	//	}
	//	/**
	//	 * <p>
	//	 * 信息保存
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>新增。</div>
	//	 * <div>修改。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String save() {
	//		log.info("C301Action edit.........");
	//		if(bean!=null){
	//			String msg="1";
	//			try {
	//					BaseUserBean user = (BaseUserBean) request.getSession().getAttribute(CommonConstant.SESSION_KEY_USER_CUSTOMER_SERVICE);
	//					if(user!=null){
	//						bean.setCreate_ip(getIpAddr());
	//						bean.setCreate_id(user.getUser_id());
	//						bean.setUpdate_ip(getIpAddr());
	//						bean.setUpdate_id(user.getUser_id());
	//					}
	//					msg=consultMsgManager.saveOrUpdateData(bean);
	//			} catch (Exception e) {
	//				msg=e.getMessage();
	//			}
	//			request.setAttribute("msg",msg);
	//		}else{
	//			request.setAttribute("msg", "信息保存失败!");
	//		}
	//		return SUCCESS;
	//	}
	//	/**
	//	 * <p>
	//	 * 删除。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>删除。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String del() {
	//		log.info("C301Action del.........");
	//		
	//		String id=request.getParameter("id");
	//		TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//		bean1.setId(id);
	//		String msg="1";
	//		try {
	//			msg=consultMsgManager.deleteDataById(bean1);
	//		} catch (Exception e) {
	//			msg=e.getMessage();
	//		}
	//		request.setAttribute("msg",msg);
	//		return SUCCESS;
	//	}
	
	
	
	/**
	 * BEAN类  咨询信息取得
	 * @return BEAN类  咨询信息
	 */
	//	/**
	//	 * <p>
	//	 * 编辑。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>编辑。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String edit() {
	//		log.info("C301Action edit.........");
	//		String id=request.getParameter("id");
	//		if(id!=null){
	//			TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//			bean1.setId(id);
	//			bean=consultMsgManager.findDataById(bean1);
	//		}else{
	//			SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
	//			request.setAttribute("date", sdf.format(new Date()));
	//		}
	//		return "edit";
	//	}
	//	/**
	//	 * <p>
	//	 * 信息保存
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>新增。</div>
	//	 * <div>修改。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String save() {
	//		log.info("C301Action edit.........");
	//		if(bean!=null){
	//			String msg="1";
	//			try {
	//					BaseUserBean user = (BaseUserBean) request.getSession().getAttribute(CommonConstant.SESSION_KEY_USER_CUSTOMER_SERVICE);
	//					if(user!=null){
	//						bean.setCreate_ip(getIpAddr());
	//						bean.setCreate_id(user.getUser_id());
	//						bean.setUpdate_ip(getIpAddr());
	//						bean.setUpdate_id(user.getUser_id());
	//					}
	//					msg=consultMsgManager.saveOrUpdateData(bean);
	//			} catch (Exception e) {
	//				msg=e.getMessage();
	//			}
	//			request.setAttribute("msg",msg);
	//		}else{
	//			request.setAttribute("msg", "信息保存失败!");
	//		}
	//		return SUCCESS;
	//	}
	//	/**
	//	 * <p>
	//	 * 删除。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>删除。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String del() {
	//		log.info("C301Action del.........");
	//		
	//		String id=request.getParameter("id");
	//		TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//		bean1.setId(id);
	//		String msg="1";
	//		try {
	//			msg=consultMsgManager.deleteDataById(bean1);
	//		} catch (Exception e) {
	//			msg=e.getMessage();
	//		}
	//		request.setAttribute("msg",msg);
	//		return SUCCESS;
	//	}
	
	
	
	/**
	 * BEAN类  咨询信息设定
	 * @param bean BEAN类  咨询信息
	 */
	//	/**
	//	 * <p>
	//	 * 编辑。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>编辑。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String edit() {
	//		log.info("C301Action edit.........");
	//		String id=request.getParameter("id");
	//		if(id!=null){
	//			TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//			bean1.setId(id);
	//			bean=consultMsgManager.findDataById(bean1);
	//		}else{
	//			SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
	//			request.setAttribute("date", sdf.format(new Date()));
	//		}
	//		return "edit";
	//	}
	//	/**
	//	 * <p>
	//	 * 信息保存
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>新增。</div>
	//	 * <div>修改。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String save() {
	//		log.info("C301Action edit.........");
	//		if(bean!=null){
	//			String msg="1";
	//			try {
	//					BaseUserBean user = (BaseUserBean) request.getSession().getAttribute(CommonConstant.SESSION_KEY_USER_CUSTOMER_SERVICE);
	//					if(user!=null){
	//						bean.setCreate_ip(getIpAddr());
	//						bean.setCreate_id(user.getUser_id());
	//						bean.setUpdate_ip(getIpAddr());
	//						bean.setUpdate_id(user.getUser_id());
	//					}
	//					msg=consultMsgManager.saveOrUpdateData(bean);
	//			} catch (Exception e) {
	//				msg=e.getMessage();
	//			}
	//			request.setAttribute("msg",msg);
	//		}else{
	//			request.setAttribute("msg", "信息保存失败!");
	//		}
	//		return SUCCESS;
	//	}
	//	/**
	//	 * <p>
	//	 * 删除。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>删除。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String del() {
	//		log.info("C301Action del.........");
	//		
	//		String id=request.getParameter("id");
	//		TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//		bean1.setId(id);
	//		String msg="1";
	//		try {
	//			msg=consultMsgManager.deleteDataById(bean1);
	//		} catch (Exception e) {
	//			msg=e.getMessage();
	//		}
	//		request.setAttribute("msg",msg);
	//		return SUCCESS;
	//	}
	
	
	
	/**
	 * BEAN类  咨询信息取得
	 * @return BEAN类  咨询信息
	 */
	//	/**
	//	 * <p>
	//	 * 编辑。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>编辑。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String edit() {
	//		log.info("C301Action edit.........");
	//		String id=request.getParameter("id");
	//		if(id!=null){
	//			TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//			bean1.setId(id);
	//			bean=consultMsgManager.findDataById(bean1);
	//		}else{
	//			SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
	//			request.setAttribute("date", sdf.format(new Date()));
	//		}
	//		return "edit";
	//	}
	//	/**
	//	 * <p>
	//	 * 信息保存
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>新增。</div>
	//	 * <div>修改。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String save() {
	//		log.info("C301Action edit.........");
	//		if(bean!=null){
	//			String msg="1";
	//			try {
	//					BaseUserBean user = (BaseUserBean) request.getSession().getAttribute(CommonConstant.SESSION_KEY_USER_CUSTOMER_SERVICE);
	//					if(user!=null){
	//						bean.setCreate_ip(getIpAddr());
	//						bean.setCreate_id(user.getUser_id());
	//						bean.setUpdate_ip(getIpAddr());
	//						bean.setUpdate_id(user.getUser_id());
	//					}
	//					msg=consultMsgManager.saveOrUpdateData(bean);
	//			} catch (Exception e) {
	//				msg=e.getMessage();
	//			}
	//			request.setAttribute("msg",msg);
	//		}else{
	//			request.setAttribute("msg", "信息保存失败!");
	//		}
	//		return SUCCESS;
	//	}
	//	/**
	//	 * <p>
	//	 * 删除。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>删除。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String del() {
	//		log.info("C301Action del.........");
	//		
	//		String id=request.getParameter("id");
	//		TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//		bean1.setId(id);
	//		String msg="1";
	//		try {
	//			msg=consultMsgManager.deleteDataById(bean1);
	//		} catch (Exception e) {
	//			msg=e.getMessage();
	//		}
	//		request.setAttribute("msg",msg);
	//		return SUCCESS;
	//	}
	
	
	
	/**
	 * BEAN类  咨询信息取得
	 * @return BEAN类  咨询信息
	 */
	//	/**
	//	 * <p>
	//	 * 编辑。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>编辑。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String edit() {
	//		log.info("C301Action edit.........");
	//		String id=request.getParameter("id");
	//		if(id!=null){
	//			TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//			bean1.setId(id);
	//			bean=consultMsgManager.findDataById(bean1);
	//		}else{
	//			SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
	//			request.setAttribute("date", sdf.format(new Date()));
	//		}
	//		return "edit";
	//	}
	//	/**
	//	 * <p>
	//	 * 信息保存
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>新增。</div>
	//	 * <div>修改。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String save() {
	//		log.info("C301Action edit.........");
	//		if(bean!=null){
	//			String msg="1";
	//			try {
	//					BaseUserBean user = (BaseUserBean) request.getSession().getAttribute(CommonConstant.SESSION_KEY_USER_CUSTOMER_SERVICE);
	//					if(user!=null){
	//						bean.setCreate_ip(getIpAddr());
	//						bean.setCreate_id(user.getUser_id());
	//						bean.setUpdate_ip(getIpAddr());
	//						bean.setUpdate_id(user.getUser_id());
	//					}
	//					msg=consultMsgManager.saveOrUpdateData(bean);
	//			} catch (Exception e) {
	//				msg=e.getMessage();
	//			}
	//			request.setAttribute("msg",msg);
	//		}else{
	//			request.setAttribute("msg", "信息保存失败!");
	//		}
	//		return SUCCESS;
	//	}
	//	/**
	//	 * <p>
	//	 * 删除。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>删除。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String del() {
	//		log.info("C301Action del.........");
	//		
	//		String id=request.getParameter("id");
	//		TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//		bean1.setId(id);
	//		String msg="1";
	//		try {
	//			msg=consultMsgManager.deleteDataById(bean1);
	//		} catch (Exception e) {
	//			msg=e.getMessage();
	//		}
	//		request.setAttribute("msg",msg);
	//		return SUCCESS;
	//	}
	
	
	
	/**
	 * BEAN类  咨询信息设定
	 * @param bean BEAN类  咨询信息
	 */
	//	/**
	//	 * <p>
	//	 * 编辑。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>编辑。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String edit() {
	//		log.info("C301Action edit.........");
	//		String id=request.getParameter("id");
	//		if(id!=null){
	//			TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//			bean1.setId(id);
	//			bean=consultMsgManager.findDataById(bean1);
	//		}else{
	//			SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
	//			request.setAttribute("date", sdf.format(new Date()));
	//		}
	//		return "edit";
	//	}
	//	/**
	//	 * <p>
	//	 * 信息保存
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>新增。</div>
	//	 * <div>修改。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String save() {
	//		log.info("C301Action edit.........");
	//		if(bean!=null){
	//			String msg="1";
	//			try {
	//					BaseUserBean user = (BaseUserBean) request.getSession().getAttribute(CommonConstant.SESSION_KEY_USER_CUSTOMER_SERVICE);
	//					if(user!=null){
	//						bean.setCreate_ip(getIpAddr());
	//						bean.setCreate_id(user.getUser_id());
	//						bean.setUpdate_ip(getIpAddr());
	//						bean.setUpdate_id(user.getUser_id());
	//					}
	//					msg=consultMsgManager.saveOrUpdateData(bean);
	//			} catch (Exception e) {
	//				msg=e.getMessage();
	//			}
	//			request.setAttribute("msg",msg);
	//		}else{
	//			request.setAttribute("msg", "信息保存失败!");
	//		}
	//		return SUCCESS;
	//	}
	//	/**
	//	 * <p>
	//	 * 删除。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>删除。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String del() {
	//		log.info("C301Action del.........");
	//		
	//		String id=request.getParameter("id");
	//		TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//		bean1.setId(id);
	//		String msg="1";
	//		try {
	//			msg=consultMsgManager.deleteDataById(bean1);
	//		} catch (Exception e) {
	//			msg=e.getMessage();
	//		}
	//		request.setAttribute("msg",msg);
	//		return SUCCESS;
	//	}
	
	
	
	/**
	 * BEAN类  咨询信息取得
	 * @return BEAN类  咨询信息
	 */
	//	/**
	//	 * <p>
	//	 * 编辑。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>编辑。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String edit() {
	//		log.info("C301Action edit.........");
	//		String id=request.getParameter("id");
	//		if(id!=null){
	//			TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//			bean1.setId(id);
	//			bean=consultMsgManager.findDataById(bean1);
	//		}else{
	//			SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
	//			request.setAttribute("date", sdf.format(new Date()));
	//		}
	//		return "edit";
	//	}
	//	/**
	//	 * <p>
	//	 * 信息保存
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>新增。</div>
	//	 * <div>修改。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String save() {
	//		log.info("C301Action edit.........");
	//		if(bean!=null){
	//			String msg="1";
	//			try {
	//					BaseUserBean user = (BaseUserBean) request.getSession().getAttribute(CommonConstant.SESSION_KEY_USER_CUSTOMER_SERVICE);
	//					if(user!=null){
	//						bean.setCreate_ip(getIpAddr());
	//						bean.setCreate_id(user.getUser_id());
	//						bean.setUpdate_ip(getIpAddr());
	//						bean.setUpdate_id(user.getUser_id());
	//					}
	//					msg=consultMsgManager.saveOrUpdateData(bean);
	//			} catch (Exception e) {
	//				msg=e.getMessage();
	//			}
	//			request.setAttribute("msg",msg);
	//		}else{
	//			request.setAttribute("msg", "信息保存失败!");
	//		}
	//		return SUCCESS;
	//	}
	//	/**
	//	 * <p>
	//	 * 删除。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>删除。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String del() {
	//		log.info("C301Action del.........");
	//		
	//		String id=request.getParameter("id");
	//		TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//		bean1.setId(id);
	//		String msg="1";
	//		try {
	//			msg=consultMsgManager.deleteDataById(bean1);
	//		} catch (Exception e) {
	//			msg=e.getMessage();
	//		}
	//		request.setAttribute("msg",msg);
	//		return SUCCESS;
	//	}
	
	
	
	/**
	 * BEAN类  咨询信息取得
	 * @return BEAN类  咨询信息
	 */
	//	/**
	//	 * <p>
	//	 * 编辑。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>编辑。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String edit() {
	//		log.info("C301Action edit.........");
	//		String id=request.getParameter("id");
	//		if(id!=null){
	//			TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//			bean1.setId(id);
	//			bean=consultMsgManager.findDataById(bean1);
	//		}else{
	//			SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
	//			request.setAttribute("date", sdf.format(new Date()));
	//		}
	//		return "edit";
	//	}
	//	/**
	//	 * <p>
	//	 * 信息保存
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>新增。</div>
	//	 * <div>修改。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String save() {
	//		log.info("C301Action edit.........");
	//		if(bean!=null){
	//			String msg="1";
	//			try {
	//					BaseUserBean user = (BaseUserBean) request.getSession().getAttribute(CommonConstant.SESSION_KEY_USER_CUSTOMER_SERVICE);
	//					if(user!=null){
	//						bean.setCreate_ip(getIpAddr());
	//						bean.setCreate_id(user.getUser_id());
	//						bean.setUpdate_ip(getIpAddr());
	//						bean.setUpdate_id(user.getUser_id());
	//					}
	//					msg=consultMsgManager.saveOrUpdateData(bean);
	//			} catch (Exception e) {
	//				msg=e.getMessage();
	//			}
	//			request.setAttribute("msg",msg);
	//		}else{
	//			request.setAttribute("msg", "信息保存失败!");
	//		}
	//		return SUCCESS;
	//	}
	//	/**
	//	 * <p>
	//	 * 删除。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>删除。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String del() {
	//		log.info("C301Action del.........");
	//		
	//		String id=request.getParameter("id");
	//		TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//		bean1.setId(id);
	//		String msg="1";
	//		try {
	//			msg=consultMsgManager.deleteDataById(bean1);
	//		} catch (Exception e) {
	//			msg=e.getMessage();
	//		}
	//		request.setAttribute("msg",msg);
	//		return SUCCESS;
	//	}
	
	
	
	/**
	 * BEAN类  咨询信息设定
	 * @param bean BEAN类  咨询信息
	 */
	//	/**
	//	 * <p>
	//	 * 编辑。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>编辑。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String edit() {
	//		log.info("C301Action edit.........");
	//		String id=request.getParameter("id");
	//		if(id!=null){
	//			TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//			bean1.setId(id);
	//			bean=consultMsgManager.findDataById(bean1);
	//		}else{
	//			SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
	//			request.setAttribute("date", sdf.format(new Date()));
	//		}
	//		return "edit";
	//	}
	//	/**
	//	 * <p>
	//	 * 信息保存
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>新增。</div>
	//	 * <div>修改。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String save() {
	//		log.info("C301Action edit.........");
	//		if(bean!=null){
	//			String msg="1";
	//			try {
	//					BaseUserBean user = (BaseUserBean) request.getSession().getAttribute(CommonConstant.SESSION_KEY_USER_CUSTOMER_SERVICE);
	//					if(user!=null){
	//						bean.setCreate_ip(getIpAddr());
	//						bean.setCreate_id(user.getUser_id());
	//						bean.setUpdate_ip(getIpAddr());
	//						bean.setUpdate_id(user.getUser_id());
	//					}
	//					msg=consultMsgManager.saveOrUpdateData(bean);
	//			} catch (Exception e) {
	//				msg=e.getMessage();
	//			}
	//			request.setAttribute("msg",msg);
	//		}else{
	//			request.setAttribute("msg", "信息保存失败!");
	//		}
	//		return SUCCESS;
	//	}
	//	/**
	//	 * <p>
	//	 * 删除。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>删除。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String del() {
	//		log.info("C301Action del.........");
	//		
	//		String id=request.getParameter("id");
	//		TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//		bean1.setId(id);
	//		String msg="1";
	//		try {
	//			msg=consultMsgManager.deleteDataById(bean1);
	//		} catch (Exception e) {
	//			msg=e.getMessage();
	//		}
	//		request.setAttribute("msg",msg);
	//		return SUCCESS;
	//	}
	
	
	
	/**
	 * BEAN类  咨询信息取得
	 * @return BEAN类  咨询信息
	 */
	//	/**
	//	 * <p>
	//	 * 编辑。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>编辑。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String edit() {
	//		log.info("C301Action edit.........");
	//		String id=request.getParameter("id");
	//		if(id!=null){
	//			TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//			bean1.setId(id);
	//			bean=consultMsgManager.findDataById(bean1);
	//		}else{
	//			SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
	//			request.setAttribute("date", sdf.format(new Date()));
	//		}
	//		return "edit";
	//	}
	//	/**
	//	 * <p>
	//	 * 信息保存
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>新增。</div>
	//	 * <div>修改。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String save() {
	//		log.info("C301Action edit.........");
	//		if(bean!=null){
	//			String msg="1";
	//			try {
	//					BaseUserBean user = (BaseUserBean) request.getSession().getAttribute(CommonConstant.SESSION_KEY_USER_CUSTOMER_SERVICE);
	//					if(user!=null){
	//						bean.setCreate_ip(getIpAddr());
	//						bean.setCreate_id(user.getUser_id());
	//						bean.setUpdate_ip(getIpAddr());
	//						bean.setUpdate_id(user.getUser_id());
	//					}
	//					msg=consultMsgManager.saveOrUpdateData(bean);
	//			} catch (Exception e) {
	//				msg=e.getMessage();
	//			}
	//			request.setAttribute("msg",msg);
	//		}else{
	//			request.setAttribute("msg", "信息保存失败!");
	//		}
	//		return SUCCESS;
	//	}
	//	/**
	//	 * <p>
	//	 * 删除。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>删除。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String del() {
	//		log.info("C301Action del.........");
	//		
	//		String id=request.getParameter("id");
	//		TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//		bean1.setId(id);
	//		String msg="1";
	//		try {
	//			msg=consultMsgManager.deleteDataById(bean1);
	//		} catch (Exception e) {
	//			msg=e.getMessage();
	//		}
	//		request.setAttribute("msg",msg);
	//		return SUCCESS;
	//	}
	
	
	
	/**
	 * BEAN类  咨询信息设定
	 * @param bean BEAN类  咨询信息
	 */
	//	/**
	//	 * <p>
	//	 * 编辑。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>编辑。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String edit() {
	//		log.info("C301Action edit.........");
	//		String id=request.getParameter("id");
	//		if(id!=null){
	//			TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//			bean1.setId(id);
	//			bean=consultMsgManager.findDataById(bean1);
	//		}else{
	//			SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
	//			request.setAttribute("date", sdf.format(new Date()));
	//		}
	//		return "edit";
	//	}
	//	/**
	//	 * <p>
	//	 * 信息保存
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>新增。</div>
	//	 * <div>修改。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String save() {
	//		log.info("C301Action edit.........");
	//		if(bean!=null){
	//			String msg="1";
	//			try {
	//					BaseUserBean user = (BaseUserBean) request.getSession().getAttribute(CommonConstant.SESSION_KEY_USER_CUSTOMER_SERVICE);
	//					if(user!=null){
	//						bean.setCreate_ip(getIpAddr());
	//						bean.setCreate_id(user.getUser_id());
	//						bean.setUpdate_ip(getIpAddr());
	//						bean.setUpdate_id(user.getUser_id());
	//					}
	//					msg=consultMsgManager.saveOrUpdateData(bean);
	//			} catch (Exception e) {
	//				msg=e.getMessage();
	//			}
	//			request.setAttribute("msg",msg);
	//		}else{
	//			request.setAttribute("msg", "信息保存失败!");
	//		}
	//		return SUCCESS;
	//	}
	//	/**
	//	 * <p>
	//	 * 删除。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>删除。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String del() {
	//		log.info("C301Action del.........");
	//		
	//		String id=request.getParameter("id");
	//		TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//		bean1.setId(id);
	//		String msg="1";
	//		try {
	//			msg=consultMsgManager.deleteDataById(bean1);
	//		} catch (Exception e) {
	//			msg=e.getMessage();
	//		}
	//		request.setAttribute("msg",msg);
	//		return SUCCESS;
	//	}
	
	
	
	/**
	 * BEAN类  咨询信息取得
	 * @return BEAN类  咨询信息
	 */
	//	/**
	//	 * <p>
	//	 * 编辑。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>编辑。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String edit() {
	//		log.info("C301Action edit.........");
	//		String id=request.getParameter("id");
	//		if(id!=null){
	//			TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//			bean1.setId(id);
	//			bean=consultMsgManager.findDataById(bean1);
	//		}else{
	//			SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
	//			request.setAttribute("date", sdf.format(new Date()));
	//		}
	//		return "edit";
	//	}
	//	/**
	//	 * <p>
	//	 * 信息保存
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>新增。</div>
	//	 * <div>修改。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String save() {
	//		log.info("C301Action edit.........");
	//		if(bean!=null){
	//			String msg="1";
	//			try {
	//					BaseUserBean user = (BaseUserBean) request.getSession().getAttribute(CommonConstant.SESSION_KEY_USER_CUSTOMER_SERVICE);
	//					if(user!=null){
	//						bean.setCreate_ip(getIpAddr());
	//						bean.setCreate_id(user.getUser_id());
	//						bean.setUpdate_ip(getIpAddr());
	//						bean.setUpdate_id(user.getUser_id());
	//					}
	//					msg=consultMsgManager.saveOrUpdateData(bean);
	//			} catch (Exception e) {
	//				msg=e.getMessage();
	//			}
	//			request.setAttribute("msg",msg);
	//		}else{
	//			request.setAttribute("msg", "信息保存失败!");
	//		}
	//		return SUCCESS;
	//	}
	//	/**
	//	 * <p>
	//	 * 删除。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>删除。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String del() {
	//		log.info("C301Action del.........");
	//		
	//		String id=request.getParameter("id");
	//		TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//		bean1.setId(id);
	//		String msg="1";
	//		try {
	//			msg=consultMsgManager.deleteDataById(bean1);
	//		} catch (Exception e) {
	//			msg=e.getMessage();
	//		}
	//		request.setAttribute("msg",msg);
	//		return SUCCESS;
	//	}
	
	
	
	/**
	 * BEAN类  咨询信息取得
	 * @return BEAN类  咨询信息
	 */
	//	/**
	//	 * <p>
	//	 * 编辑。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>编辑。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String edit() {
	//		log.info("C301Action edit.........");
	//		String id=request.getParameter("id");
	//		if(id!=null){
	//			TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//			bean1.setId(id);
	//			bean=consultMsgManager.findDataById(bean1);
	//		}else{
	//			SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
	//			request.setAttribute("date", sdf.format(new Date()));
	//		}
	//		return "edit";
	//	}
	//	/**
	//	 * <p>
	//	 * 信息保存
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>新增。</div>
	//	 * <div>修改。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String save() {
	//		log.info("C301Action edit.........");
	//		if(bean!=null){
	//			String msg="1";
	//			try {
	//					BaseUserBean user = (BaseUserBean) request.getSession().getAttribute(CommonConstant.SESSION_KEY_USER_CUSTOMER_SERVICE);
	//					if(user!=null){
	//						bean.setCreate_ip(getIpAddr());
	//						bean.setCreate_id(user.getUser_id());
	//						bean.setUpdate_ip(getIpAddr());
	//						bean.setUpdate_id(user.getUser_id());
	//					}
	//					msg=consultMsgManager.saveOrUpdateData(bean);
	//			} catch (Exception e) {
	//				msg=e.getMessage();
	//			}
	//			request.setAttribute("msg",msg);
	//		}else{
	//			request.setAttribute("msg", "信息保存失败!");
	//		}
	//		return SUCCESS;
	//	}
	//	/**
	//	 * <p>
	//	 * 删除。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>删除。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String del() {
	//		log.info("C301Action del.........");
	//		
	//		String id=request.getParameter("id");
	//		TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//		bean1.setId(id);
	//		String msg="1";
	//		try {
	//			msg=consultMsgManager.deleteDataById(bean1);
	//		} catch (Exception e) {
	//			msg=e.getMessage();
	//		}
	//		request.setAttribute("msg",msg);
	//		return SUCCESS;
	//	}
	
	
	
	/**
	 * BEAN类  咨询信息设定
	 * @param bean BEAN类  咨询信息
	 */
	//	/**
	//	 * <p>
	//	 * 编辑。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>编辑。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String edit() {
	//		log.info("C301Action edit.........");
	//		String id=request.getParameter("id");
	//		if(id!=null){
	//			TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//			bean1.setId(id);
	//			bean=consultMsgManager.findDataById(bean1);
	//		}else{
	//			SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
	//			request.setAttribute("date", sdf.format(new Date()));
	//		}
	//		return "edit";
	//	}
	//	/**
	//	 * <p>
	//	 * 信息保存
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>新增。</div>
	//	 * <div>修改。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String save() {
	//		log.info("C301Action edit.........");
	//		if(bean!=null){
	//			String msg="1";
	//			try {
	//					BaseUserBean user = (BaseUserBean) request.getSession().getAttribute(CommonConstant.SESSION_KEY_USER_CUSTOMER_SERVICE);
	//					if(user!=null){
	//						bean.setCreate_ip(getIpAddr());
	//						bean.setCreate_id(user.getUser_id());
	//						bean.setUpdate_ip(getIpAddr());
	//						bean.setUpdate_id(user.getUser_id());
	//					}
	//					msg=consultMsgManager.saveOrUpdateData(bean);
	//			} catch (Exception e) {
	//				msg=e.getMessage();
	//			}
	//			request.setAttribute("msg",msg);
	//		}else{
	//			request.setAttribute("msg", "信息保存失败!");
	//		}
	//		return SUCCESS;
	//	}
	//	/**
	//	 * <p>
	//	 * 删除。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>删除。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String del() {
	//		log.info("C301Action del.........");
	//		
	//		String id=request.getParameter("id");
	//		TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//		bean1.setId(id);
	//		String msg="1";
	//		try {
	//			msg=consultMsgManager.deleteDataById(bean1);
	//		} catch (Exception e) {
	//			msg=e.getMessage();
	//		}
	//		request.setAttribute("msg",msg);
	//		return SUCCESS;
	//	}
	
	
	
	/**
	 * BEAN类  咨询信息取得
	 * @return BEAN类  咨询信息
	 */
	//	/**
	//	 * <p>
	//	 * 编辑。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>编辑。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String edit() {
	//		log.info("C301Action edit.........");
	//		String id=request.getParameter("id");
	//		if(id!=null){
	//			TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//			bean1.setId(id);
	//			bean=consultMsgManager.findDataById(bean1);
	//		}else{
	//			SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
	//			request.setAttribute("date", sdf.format(new Date()));
	//		}
	//		return "edit";
	//	}
	//	/**
	//	 * <p>
	//	 * 信息保存
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>新增。</div>
	//	 * <div>修改。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String save() {
	//		log.info("C301Action edit.........");
	//		if(bean!=null){
	//			String msg="1";
	//			try {
	//					BaseUserBean user = (BaseUserBean) request.getSession().getAttribute(CommonConstant.SESSION_KEY_USER_CUSTOMER_SERVICE);
	//					if(user!=null){
	//						bean.setCreate_ip(getIpAddr());
	//						bean.setCreate_id(user.getUser_id());
	//						bean.setUpdate_ip(getIpAddr());
	//						bean.setUpdate_id(user.getUser_id());
	//					}
	//					msg=consultMsgManager.saveOrUpdateData(bean);
	//			} catch (Exception e) {
	//				msg=e.getMessage();
	//			}
	//			request.setAttribute("msg",msg);
	//		}else{
	//			request.setAttribute("msg", "信息保存失败!");
	//		}
	//		return SUCCESS;
	//	}
	//	/**
	//	 * <p>
	//	 * 删除。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>删除。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String del() {
	//		log.info("C301Action del.........");
	//		
	//		String id=request.getParameter("id");
	//		TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//		bean1.setId(id);
	//		String msg="1";
	//		try {
	//			msg=consultMsgManager.deleteDataById(bean1);
	//		} catch (Exception e) {
	//			msg=e.getMessage();
	//		}
	//		request.setAttribute("msg",msg);
	//		return SUCCESS;
	//	}
	
	
	
	/**
	 * BEAN类  咨询信息设定
	 * @param bean BEAN类  咨询信息
	 */
	//	/**
	//	 * <p>
	//	 * 编辑。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>编辑。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String edit() {
	//		log.info("C301Action edit.........");
	//		String id=request.getParameter("id");
	//		if(id!=null){
	//			TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//			bean1.setId(id);
	//			bean=consultMsgManager.findDataById(bean1);
	//		}else{
	//			SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
	//			request.setAttribute("date", sdf.format(new Date()));
	//		}
	//		return "edit";
	//	}
	//	/**
	//	 * <p>
	//	 * 信息保存
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>新增。</div>
	//	 * <div>修改。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String save() {
	//		log.info("C301Action edit.........");
	//		if(bean!=null){
	//			String msg="1";
	//			try {
	//					BaseUserBean user = (BaseUserBean) request.getSession().getAttribute(CommonConstant.SESSION_KEY_USER_CUSTOMER_SERVICE);
	//					if(user!=null){
	//						bean.setCreate_ip(getIpAddr());
	//						bean.setCreate_id(user.getUser_id());
	//						bean.setUpdate_ip(getIpAddr());
	//						bean.setUpdate_id(user.getUser_id());
	//					}
	//					msg=consultMsgManager.saveOrUpdateData(bean);
	//			} catch (Exception e) {
	//				msg=e.getMessage();
	//			}
	//			request.setAttribute("msg",msg);
	//		}else{
	//			request.setAttribute("msg", "信息保存失败!");
	//		}
	//		return SUCCESS;
	//	}
	//	/**
	//	 * <p>
	//	 * 删除。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>删除。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String del() {
	//		log.info("C301Action del.........");
	//		
	//		String id=request.getParameter("id");
	//		TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//		bean1.setId(id);
	//		String msg="1";
	//		try {
	//			msg=consultMsgManager.deleteDataById(bean1);
	//		} catch (Exception e) {
	//			msg=e.getMessage();
	//		}
	//		request.setAttribute("msg",msg);
	//		return SUCCESS;
	//	}
	
	

	/**
	 * BEAN类  咨询信息取得
	 * @return BEAN类  咨询信息
	 */
	//	/**
	//	 * <p>
	//	 * 编辑。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>编辑。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String edit() {
	//		log.info("C301Action edit.........");
	//		String id=request.getParameter("id");
	//		if(id!=null){
	//			TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//			bean1.setId(id);
	//			bean=consultMsgManager.findDataById(bean1);
	//		}else{
	//			SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
	//			request.setAttribute("date", sdf.format(new Date()));
	//		}
	//		return "edit";
	//	}
	//	/**
	//	 * <p>
	//	 * 信息保存
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>新增。</div>
	//	 * <div>修改。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String save() {
	//		log.info("C301Action edit.........");
	//		if(bean!=null){
	//			String msg="1";
	//			try {
	//					BaseUserBean user = (BaseUserBean) request.getSession().getAttribute(CommonConstant.SESSION_KEY_USER_CUSTOMER_SERVICE);
	//					if(user!=null){
	//						bean.setCreate_ip(getIpAddr());
	//						bean.setCreate_id(user.getUser_id());
	//						bean.setUpdate_ip(getIpAddr());
	//						bean.setUpdate_id(user.getUser_id());
	//					}
	//					msg=consultMsgManager.saveOrUpdateData(bean);
	//			} catch (Exception e) {
	//				msg=e.getMessage();
	//			}
	//			request.setAttribute("msg",msg);
	//		}else{
	//			request.setAttribute("msg", "信息保存失败!");
	//		}
	//		return SUCCESS;
	//	}
	//	/**
	//	 * <p>
	//	 * 删除。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>删除。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String del() {
	//		log.info("C301Action del.........");
	//		
	//		String id=request.getParameter("id");
	//		TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//		bean1.setId(id);
	//		String msg="1";
	//		try {
	//			msg=consultMsgManager.deleteDataById(bean1);
	//		} catch (Exception e) {
	//			msg=e.getMessage();
	//		}
	//		request.setAttribute("msg",msg);
	//		return SUCCESS;
	//	}
	
	
	
	/**
	 * BEAN类  咨询信息取得
	 * @return BEAN类  咨询信息
	 */
	//	/**
	//	 * <p>
	//	 * 编辑。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>编辑。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String edit() {
	//		log.info("C301Action edit.........");
	//		String id=request.getParameter("id");
	//		if(id!=null){
	//			TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//			bean1.setId(id);
	//			bean=consultMsgManager.findDataById(bean1);
	//		}else{
	//			SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
	//			request.setAttribute("date", sdf.format(new Date()));
	//		}
	//		return "edit";
	//	}
	//	/**
	//	 * <p>
	//	 * 信息保存
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>新增。</div>
	//	 * <div>修改。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String save() {
	//		log.info("C301Action edit.........");
	//		if(bean!=null){
	//			String msg="1";
	//			try {
	//					BaseUserBean user = (BaseUserBean) request.getSession().getAttribute(CommonConstant.SESSION_KEY_USER_CUSTOMER_SERVICE);
	//					if(user!=null){
	//						bean.setCreate_ip(getIpAddr());
	//						bean.setCreate_id(user.getUser_id());
	//						bean.setUpdate_ip(getIpAddr());
	//						bean.setUpdate_id(user.getUser_id());
	//					}
	//					msg=consultMsgManager.saveOrUpdateData(bean);
	//			} catch (Exception e) {
	//				msg=e.getMessage();
	//			}
	//			request.setAttribute("msg",msg);
	//		}else{
	//			request.setAttribute("msg", "信息保存失败!");
	//		}
	//		return SUCCESS;
	//	}
	//	/**
	//	 * <p>
	//	 * 删除。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>删除。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String del() {
	//		log.info("C301Action del.........");
	//		
	//		String id=request.getParameter("id");
	//		TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//		bean1.setId(id);
	//		String msg="1";
	//		try {
	//			msg=consultMsgManager.deleteDataById(bean1);
	//		} catch (Exception e) {
	//			msg=e.getMessage();
	//		}
	//		request.setAttribute("msg",msg);
	//		return SUCCESS;
	//	}
	
	
	
	/**
	 * BEAN类  咨询信息设定
	 * @param bean BEAN类  咨询信息
	 */
	//	/**
	//	 * <p>
	//	 * 编辑。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>编辑。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String edit() {
	//		log.info("C301Action edit.........");
	//		String id=request.getParameter("id");
	//		if(id!=null){
	//			TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//			bean1.setId(id);
	//			bean=consultMsgManager.findDataById(bean1);
	//		}else{
	//			SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
	//			request.setAttribute("date", sdf.format(new Date()));
	//		}
	//		return "edit";
	//	}
	//	/**
	//	 * <p>
	//	 * 信息保存
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>新增。</div>
	//	 * <div>修改。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String save() {
	//		log.info("C301Action edit.........");
	//		if(bean!=null){
	//			String msg="1";
	//			try {
	//					BaseUserBean user = (BaseUserBean) request.getSession().getAttribute(CommonConstant.SESSION_KEY_USER_CUSTOMER_SERVICE);
	//					if(user!=null){
	//						bean.setCreate_ip(getIpAddr());
	//						bean.setCreate_id(user.getUser_id());
	//						bean.setUpdate_ip(getIpAddr());
	//						bean.setUpdate_id(user.getUser_id());
	//					}
	//					msg=consultMsgManager.saveOrUpdateData(bean);
	//			} catch (Exception e) {
	//				msg=e.getMessage();
	//			}
	//			request.setAttribute("msg",msg);
	//		}else{
	//			request.setAttribute("msg", "信息保存失败!");
	//		}
	//		return SUCCESS;
	//	}
	//	/**
	//	 * <p>
	//	 * 删除。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>删除。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String del() {
	//		log.info("C301Action del.........");
	//		
	//		String id=request.getParameter("id");
	//		TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//		bean1.setId(id);
	//		String msg="1";
	//		try {
	//			msg=consultMsgManager.deleteDataById(bean1);
	//		} catch (Exception e) {
	//			msg=e.getMessage();
	//		}
	//		request.setAttribute("msg",msg);
	//		return SUCCESS;
	//	}
	
	
	
	/**
	 * BEAN类  咨询信息取得
	 * @return BEAN类  咨询信息
	 */
	//	/**
	//	 * <p>
	//	 * 编辑。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>编辑。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String edit() {
	//		log.info("C301Action edit.........");
	//		String id=request.getParameter("id");
	//		if(id!=null){
	//			TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//			bean1.setId(id);
	//			bean=consultMsgManager.findDataById(bean1);
	//		}else{
	//			SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
	//			request.setAttribute("date", sdf.format(new Date()));
	//		}
	//		return "edit";
	//	}
	//	/**
	//	 * <p>
	//	 * 信息保存
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>新增。</div>
	//	 * <div>修改。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String save() {
	//		log.info("C301Action edit.........");
	//		if(bean!=null){
	//			String msg="1";
	//			try {
	//					BaseUserBean user = (BaseUserBean) request.getSession().getAttribute(CommonConstant.SESSION_KEY_USER_CUSTOMER_SERVICE);
	//					if(user!=null){
	//						bean.setCreate_ip(getIpAddr());
	//						bean.setCreate_id(user.getUser_id());
	//						bean.setUpdate_ip(getIpAddr());
	//						bean.setUpdate_id(user.getUser_id());
	//					}
	//					msg=consultMsgManager.saveOrUpdateData(bean);
	//			} catch (Exception e) {
	//				msg=e.getMessage();
	//			}
	//			request.setAttribute("msg",msg);
	//		}else{
	//			request.setAttribute("msg", "信息保存失败!");
	//		}
	//		return SUCCESS;
	//	}
	//	/**
	//	 * <p>
	//	 * 删除。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>删除。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String del() {
	//		log.info("C301Action del.........");
	//		
	//		String id=request.getParameter("id");
	//		TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//		bean1.setId(id);
	//		String msg="1";
	//		try {
	//			msg=consultMsgManager.deleteDataById(bean1);
	//		} catch (Exception e) {
	//			msg=e.getMessage();
	//		}
	//		request.setAttribute("msg",msg);
	//		return SUCCESS;
	//	}
	
	
	
	/**
	 * BEAN类  咨询信息取得
	 * @return BEAN类  咨询信息
	 */
	//	/**
	//	 * <p>
	//	 * 编辑。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>编辑。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String edit() {
	//		log.info("C301Action edit.........");
	//		String id=request.getParameter("id");
	//		if(id!=null){
	//			TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//			bean1.setId(id);
	//			bean=consultMsgManager.findDataById(bean1);
	//		}else{
	//			SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
	//			request.setAttribute("date", sdf.format(new Date()));
	//		}
	//		return "edit";
	//	}
	//	/**
	//	 * <p>
	//	 * 信息保存
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>新增。</div>
	//	 * <div>修改。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String save() {
	//		log.info("C301Action edit.........");
	//		if(bean!=null){
	//			String msg="1";
	//			try {
	//					BaseUserBean user = (BaseUserBean) request.getSession().getAttribute(CommonConstant.SESSION_KEY_USER_CUSTOMER_SERVICE);
	//					if(user!=null){
	//						bean.setCreate_ip(getIpAddr());
	//						bean.setCreate_id(user.getUser_id());
	//						bean.setUpdate_ip(getIpAddr());
	//						bean.setUpdate_id(user.getUser_id());
	//					}
	//					msg=consultMsgManager.saveOrUpdateData(bean);
	//			} catch (Exception e) {
	//				msg=e.getMessage();
	//			}
	//			request.setAttribute("msg",msg);
	//		}else{
	//			request.setAttribute("msg", "信息保存失败!");
	//		}
	//		return SUCCESS;
	//	}
	//	/**
	//	 * <p>
	//	 * 删除。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>删除。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String del() {
	//		log.info("C301Action del.........");
	//		
	//		String id=request.getParameter("id");
	//		TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//		bean1.setId(id);
	//		String msg="1";
	//		try {
	//			msg=consultMsgManager.deleteDataById(bean1);
	//		} catch (Exception e) {
	//			msg=e.getMessage();
	//		}
	//		request.setAttribute("msg",msg);
	//		return SUCCESS;
	//	}
	
	
	
	/**
	 * BEAN类  咨询信息设定
	 * @param bean BEAN类  咨询信息
	 */
	//	/**
	//	 * <p>
	//	 * 编辑。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>编辑。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String edit() {
	//		log.info("C301Action edit.........");
	//		String id=request.getParameter("id");
	//		if(id!=null){
	//			TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//			bean1.setId(id);
	//			bean=consultMsgManager.findDataById(bean1);
	//		}else{
	//			SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
	//			request.setAttribute("date", sdf.format(new Date()));
	//		}
	//		return "edit";
	//	}
	//	/**
	//	 * <p>
	//	 * 信息保存
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>新增。</div>
	//	 * <div>修改。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String save() {
	//		log.info("C301Action edit.........");
	//		if(bean!=null){
	//			String msg="1";
	//			try {
	//					BaseUserBean user = (BaseUserBean) request.getSession().getAttribute(CommonConstant.SESSION_KEY_USER_CUSTOMER_SERVICE);
	//					if(user!=null){
	//						bean.setCreate_ip(getIpAddr());
	//						bean.setCreate_id(user.getUser_id());
	//						bean.setUpdate_ip(getIpAddr());
	//						bean.setUpdate_id(user.getUser_id());
	//					}
	//					msg=consultMsgManager.saveOrUpdateData(bean);
	//			} catch (Exception e) {
	//				msg=e.getMessage();
	//			}
	//			request.setAttribute("msg",msg);
	//		}else{
	//			request.setAttribute("msg", "信息保存失败!");
	//		}
	//		return SUCCESS;
	//	}
	//	/**
	//	 * <p>
	//	 * 删除。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>删除。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String del() {
	//		log.info("C301Action del.........");
	//		
	//		String id=request.getParameter("id");
	//		TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//		bean1.setId(id);
	//		String msg="1";
	//		try {
	//			msg=consultMsgManager.deleteDataById(bean1);
	//		} catch (Exception e) {
	//			msg=e.getMessage();
	//		}
	//		request.setAttribute("msg",msg);
	//		return SUCCESS;
	//	}
	
	
	
	/**
	 * BEAN类  咨询信息取得
	 * @return BEAN类  咨询信息
	 */
	//	/**
	//	 * <p>
	//	 * 编辑。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>编辑。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String edit() {
	//		log.info("C301Action edit.........");
	//		String id=request.getParameter("id");
	//		if(id!=null){
	//			TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//			bean1.setId(id);
	//			bean=consultMsgManager.findDataById(bean1);
	//		}else{
	//			SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
	//			request.setAttribute("date", sdf.format(new Date()));
	//		}
	//		return "edit";
	//	}
	//	/**
	//	 * <p>
	//	 * 信息保存
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>新增。</div>
	//	 * <div>修改。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String save() {
	//		log.info("C301Action edit.........");
	//		if(bean!=null){
	//			String msg="1";
	//			try {
	//					BaseUserBean user = (BaseUserBean) request.getSession().getAttribute(CommonConstant.SESSION_KEY_USER_CUSTOMER_SERVICE);
	//					if(user!=null){
	//						bean.setCreate_ip(getIpAddr());
	//						bean.setCreate_id(user.getUser_id());
	//						bean.setUpdate_ip(getIpAddr());
	//						bean.setUpdate_id(user.getUser_id());
	//					}
	//					msg=consultMsgManager.saveOrUpdateData(bean);
	//			} catch (Exception e) {
	//				msg=e.getMessage();
	//			}
	//			request.setAttribute("msg",msg);
	//		}else{
	//			request.setAttribute("msg", "信息保存失败!");
	//		}
	//		return SUCCESS;
	//	}
	//	/**
	//	 * <p>
	//	 * 删除。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>删除。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String del() {
	//		log.info("C301Action del.........");
	//		
	//		String id=request.getParameter("id");
	//		TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//		bean1.setId(id);
	//		String msg="1";
	//		try {
	//			msg=consultMsgManager.deleteDataById(bean1);
	//		} catch (Exception e) {
	//			msg=e.getMessage();
	//		}
	//		request.setAttribute("msg",msg);
	//		return SUCCESS;
	//	}
	
	
	
	/**
	 * BEAN类  咨询信息设定
	 * @param bean BEAN类  咨询信息
	 */
	//	/**
	//	 * <p>
	//	 * 编辑。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>编辑。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String edit() {
	//		log.info("C301Action edit.........");
	//		String id=request.getParameter("id");
	//		if(id!=null){
	//			TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//			bean1.setId(id);
	//			bean=consultMsgManager.findDataById(bean1);
	//		}else{
	//			SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
	//			request.setAttribute("date", sdf.format(new Date()));
	//		}
	//		return "edit";
	//	}
	//	/**
	//	 * <p>
	//	 * 信息保存
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>新增。</div>
	//	 * <div>修改。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String save() {
	//		log.info("C301Action edit.........");
	//		if(bean!=null){
	//			String msg="1";
	//			try {
	//					BaseUserBean user = (BaseUserBean) request.getSession().getAttribute(CommonConstant.SESSION_KEY_USER_CUSTOMER_SERVICE);
	//					if(user!=null){
	//						bean.setCreate_ip(getIpAddr());
	//						bean.setCreate_id(user.getUser_id());
	//						bean.setUpdate_ip(getIpAddr());
	//						bean.setUpdate_id(user.getUser_id());
	//					}
	//					msg=consultMsgManager.saveOrUpdateData(bean);
	//			} catch (Exception e) {
	//				msg=e.getMessage();
	//			}
	//			request.setAttribute("msg",msg);
	//		}else{
	//			request.setAttribute("msg", "信息保存失败!");
	//		}
	//		return SUCCESS;
	//	}
	//	/**
	//	 * <p>
	//	 * 删除。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>删除。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String del() {
	//		log.info("C301Action del.........");
	//		
	//		String id=request.getParameter("id");
	//		TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//		bean1.setId(id);
	//		String msg="1";
	//		try {
	//			msg=consultMsgManager.deleteDataById(bean1);
	//		} catch (Exception e) {
	//			msg=e.getMessage();
	//		}
	//		request.setAttribute("msg",msg);
	//		return SUCCESS;
	//	}
	
	
	
	/**
	 * BEAN类  咨询信息取得
	 * @return BEAN类  咨询信息
	 */
	//	/**
	//	 * <p>
	//	 * 编辑。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>编辑。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String edit() {
	//		log.info("C301Action edit.........");
	//		String id=request.getParameter("id");
	//		if(id!=null){
	//			TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//			bean1.setId(id);
	//			bean=consultMsgManager.findDataById(bean1);
	//		}else{
	//			SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
	//			request.setAttribute("date", sdf.format(new Date()));
	//		}
	//		return "edit";
	//	}
	//	/**
	//	 * <p>
	//	 * 信息保存
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>新增。</div>
	//	 * <div>修改。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String save() {
	//		log.info("C301Action edit.........");
	//		if(bean!=null){
	//			String msg="1";
	//			try {
	//					BaseUserBean user = (BaseUserBean) request.getSession().getAttribute(CommonConstant.SESSION_KEY_USER_CUSTOMER_SERVICE);
	//					if(user!=null){
	//						bean.setCreate_ip(getIpAddr());
	//						bean.setCreate_id(user.getUser_id());
	//						bean.setUpdate_ip(getIpAddr());
	//						bean.setUpdate_id(user.getUser_id());
	//					}
	//					msg=consultMsgManager.saveOrUpdateData(bean);
	//			} catch (Exception e) {
	//				msg=e.getMessage();
	//			}
	//			request.setAttribute("msg",msg);
	//		}else{
	//			request.setAttribute("msg", "信息保存失败!");
	//		}
	//		return SUCCESS;
	//	}
	//	/**
	//	 * <p>
	//	 * 删除。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>删除。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String del() {
	//		log.info("C301Action del.........");
	//		
	//		String id=request.getParameter("id");
	//		TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//		bean1.setId(id);
	//		String msg="1";
	//		try {
	//			msg=consultMsgManager.deleteDataById(bean1);
	//		} catch (Exception e) {
	//			msg=e.getMessage();
	//		}
	//		request.setAttribute("msg",msg);
	//		return SUCCESS;
	//	}
	
	
	
	/**
	 * BEAN类  咨询信息取得
	 * @return BEAN类  咨询信息
	 */
	//	/**
	//	 * <p>
	//	 * 编辑。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>编辑。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String edit() {
	//		log.info("C301Action edit.........");
	//		String id=request.getParameter("id");
	//		if(id!=null){
	//			TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//			bean1.setId(id);
	//			bean=consultMsgManager.findDataById(bean1);
	//		}else{
	//			SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
	//			request.setAttribute("date", sdf.format(new Date()));
	//		}
	//		return "edit";
	//	}
	//	/**
	//	 * <p>
	//	 * 信息保存
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>新增。</div>
	//	 * <div>修改。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String save() {
	//		log.info("C301Action edit.........");
	//		if(bean!=null){
	//			String msg="1";
	//			try {
	//					BaseUserBean user = (BaseUserBean) request.getSession().getAttribute(CommonConstant.SESSION_KEY_USER_CUSTOMER_SERVICE);
	//					if(user!=null){
	//						bean.setCreate_ip(getIpAddr());
	//						bean.setCreate_id(user.getUser_id());
	//						bean.setUpdate_ip(getIpAddr());
	//						bean.setUpdate_id(user.getUser_id());
	//					}
	//					msg=consultMsgManager.saveOrUpdateData(bean);
	//			} catch (Exception e) {
	//				msg=e.getMessage();
	//			}
	//			request.setAttribute("msg",msg);
	//		}else{
	//			request.setAttribute("msg", "信息保存失败!");
	//		}
	//		return SUCCESS;
	//	}
	//	/**
	//	 * <p>
	//	 * 删除。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>删除。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String del() {
	//		log.info("C301Action del.........");
	//		
	//		String id=request.getParameter("id");
	//		TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//		bean1.setId(id);
	//		String msg="1";
	//		try {
	//			msg=consultMsgManager.deleteDataById(bean1);
	//		} catch (Exception e) {
	//			msg=e.getMessage();
	//		}
	//		request.setAttribute("msg",msg);
	//		return SUCCESS;
	//	}
	
	
	
	/**
	 * BEAN类  咨询信息设定
	 * @param bean BEAN类  咨询信息
	 */
	//	/**
	//	 * <p>
	//	 * 编辑。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>编辑。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String edit() {
	//		log.info("C301Action edit.........");
	//		String id=request.getParameter("id");
	//		if(id!=null){
	//			TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//			bean1.setId(id);
	//			bean=consultMsgManager.findDataById(bean1);
	//		}else{
	//			SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
	//			request.setAttribute("date", sdf.format(new Date()));
	//		}
	//		return "edit";
	//	}
	//	/**
	//	 * <p>
	//	 * 信息保存
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>新增。</div>
	//	 * <div>修改。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String save() {
	//		log.info("C301Action edit.........");
	//		if(bean!=null){
	//			String msg="1";
	//			try {
	//					BaseUserBean user = (BaseUserBean) request.getSession().getAttribute(CommonConstant.SESSION_KEY_USER_CUSTOMER_SERVICE);
	//					if(user!=null){
	//						bean.setCreate_ip(getIpAddr());
	//						bean.setCreate_id(user.getUser_id());
	//						bean.setUpdate_ip(getIpAddr());
	//						bean.setUpdate_id(user.getUser_id());
	//					}
	//					msg=consultMsgManager.saveOrUpdateData(bean);
	//			} catch (Exception e) {
	//				msg=e.getMessage();
	//			}
	//			request.setAttribute("msg",msg);
	//		}else{
	//			request.setAttribute("msg", "信息保存失败!");
	//		}
	//		return SUCCESS;
	//	}
	//	/**
	//	 * <p>
	//	 * 删除。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>删除。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String del() {
	//		log.info("C301Action del.........");
	//		
	//		String id=request.getParameter("id");
	//		TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//		bean1.setId(id);
	//		String msg="1";
	//		try {
	//			msg=consultMsgManager.deleteDataById(bean1);
	//		} catch (Exception e) {
	//			msg=e.getMessage();
	//		}
	//		request.setAttribute("msg",msg);
	//		return SUCCESS;
	//	}
	
	
	
	/**
	 * BEAN类  咨询信息取得
	 * @return BEAN类  咨询信息
	 */
	//	/**
	//	 * <p>
	//	 * 编辑。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>编辑。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String edit() {
	//		log.info("C301Action edit.........");
	//		String id=request.getParameter("id");
	//		if(id!=null){
	//			TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//			bean1.setId(id);
	//			bean=consultMsgManager.findDataById(bean1);
	//		}else{
	//			SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
	//			request.setAttribute("date", sdf.format(new Date()));
	//		}
	//		return "edit";
	//	}
	//	/**
	//	 * <p>
	//	 * 信息保存
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>新增。</div>
	//	 * <div>修改。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String save() {
	//		log.info("C301Action edit.........");
	//		if(bean!=null){
	//			String msg="1";
	//			try {
	//					BaseUserBean user = (BaseUserBean) request.getSession().getAttribute(CommonConstant.SESSION_KEY_USER_CUSTOMER_SERVICE);
	//					if(user!=null){
	//						bean.setCreate_ip(getIpAddr());
	//						bean.setCreate_id(user.getUser_id());
	//						bean.setUpdate_ip(getIpAddr());
	//						bean.setUpdate_id(user.getUser_id());
	//					}
	//					msg=consultMsgManager.saveOrUpdateData(bean);
	//			} catch (Exception e) {
	//				msg=e.getMessage();
	//			}
	//			request.setAttribute("msg",msg);
	//		}else{
	//			request.setAttribute("msg", "信息保存失败!");
	//		}
	//		return SUCCESS;
	//	}
	//	/**
	//	 * <p>
	//	 * 删除。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>删除。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String del() {
	//		log.info("C301Action del.........");
	//		
	//		String id=request.getParameter("id");
	//		TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//		bean1.setId(id);
	//		String msg="1";
	//		try {
	//			msg=consultMsgManager.deleteDataById(bean1);
	//		} catch (Exception e) {
	//			msg=e.getMessage();
	//		}
	//		request.setAttribute("msg",msg);
	//		return SUCCESS;
	//	}
	
	
	
	/**
	 * BEAN类  咨询信息取得
	 * @return BEAN类  咨询信息
	 */
	//	/**
	//	 * <p>
	//	 * 编辑。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>编辑。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String edit() {
	//		log.info("C301Action edit.........");
	//		String id=request.getParameter("id");
	//		if(id!=null){
	//			TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//			bean1.setId(id);
	//			bean=consultMsgManager.findDataById(bean1);
	//		}else{
	//			SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
	//			request.setAttribute("date", sdf.format(new Date()));
	//		}
	//		return "edit";
	//	}
	//	/**
	//	 * <p>
	//	 * 信息保存
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>新增。</div>
	//	 * <div>修改。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String save() {
	//		log.info("C301Action edit.........");
	//		if(bean!=null){
	//			String msg="1";
	//			try {
	//					BaseUserBean user = (BaseUserBean) request.getSession().getAttribute(CommonConstant.SESSION_KEY_USER_CUSTOMER_SERVICE);
	//					if(user!=null){
	//						bean.setCreate_ip(getIpAddr());
	//						bean.setCreate_id(user.getUser_id());
	//						bean.setUpdate_ip(getIpAddr());
	//						bean.setUpdate_id(user.getUser_id());
	//					}
	//					msg=consultMsgManager.saveOrUpdateData(bean);
	//			} catch (Exception e) {
	//				msg=e.getMessage();
	//			}
	//			request.setAttribute("msg",msg);
	//		}else{
	//			request.setAttribute("msg", "信息保存失败!");
	//		}
	//		return SUCCESS;
	//	}
	//	/**
	//	 * <p>
	//	 * 删除。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>删除。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String del() {
	//		log.info("C301Action del.........");
	//		
	//		String id=request.getParameter("id");
	//		TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//		bean1.setId(id);
	//		String msg="1";
	//		try {
	//			msg=consultMsgManager.deleteDataById(bean1);
	//		} catch (Exception e) {
	//			msg=e.getMessage();
	//		}
	//		request.setAttribute("msg",msg);
	//		return SUCCESS;
	//	}
	
	
	
	/**
	 * BEAN类  咨询信息设定
	 * @param bean BEAN类  咨询信息
	 */
	//	/**
	//	 * <p>
	//	 * 编辑。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>编辑。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String edit() {
	//		log.info("C301Action edit.........");
	//		String id=request.getParameter("id");
	//		if(id!=null){
	//			TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//			bean1.setId(id);
	//			bean=consultMsgManager.findDataById(bean1);
	//		}else{
	//			SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
	//			request.setAttribute("date", sdf.format(new Date()));
	//		}
	//		return "edit";
	//	}
	//	/**
	//	 * <p>
	//	 * 信息保存
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>新增。</div>
	//	 * <div>修改。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String save() {
	//		log.info("C301Action edit.........");
	//		if(bean!=null){
	//			String msg="1";
	//			try {
	//					BaseUserBean user = (BaseUserBean) request.getSession().getAttribute(CommonConstant.SESSION_KEY_USER_CUSTOMER_SERVICE);
	//					if(user!=null){
	//						bean.setCreate_ip(getIpAddr());
	//						bean.setCreate_id(user.getUser_id());
	//						bean.setUpdate_ip(getIpAddr());
	//						bean.setUpdate_id(user.getUser_id());
	//					}
	//					msg=consultMsgManager.saveOrUpdateData(bean);
	//			} catch (Exception e) {
	//				msg=e.getMessage();
	//			}
	//			request.setAttribute("msg",msg);
	//		}else{
	//			request.setAttribute("msg", "信息保存失败!");
	//		}
	//		return SUCCESS;
	//	}
	//	/**
	//	 * <p>
	//	 * 删除。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>删除。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String del() {
	//		log.info("C301Action del.........");
	//		
	//		String id=request.getParameter("id");
	//		TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//		bean1.setId(id);
	//		String msg="1";
	//		try {
	//			msg=consultMsgManager.deleteDataById(bean1);
	//		} catch (Exception e) {
	//			msg=e.getMessage();
	//		}
	//		request.setAttribute("msg",msg);
	//		return SUCCESS;
	//	}
	
	
	
	/**
	 * BEAN类  咨询信息取得
	 * @return BEAN类  咨询信息
	 */
	//	/**
	//	 * <p>
	//	 * 编辑。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>编辑。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String edit() {
	//		log.info("C301Action edit.........");
	//		String id=request.getParameter("id");
	//		if(id!=null){
	//			TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//			bean1.setId(id);
	//			bean=consultMsgManager.findDataById(bean1);
	//		}else{
	//			SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
	//			request.setAttribute("date", sdf.format(new Date()));
	//		}
	//		return "edit";
	//	}
	//	/**
	//	 * <p>
	//	 * 信息保存
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>新增。</div>
	//	 * <div>修改。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String save() {
	//		log.info("C301Action edit.........");
	//		if(bean!=null){
	//			String msg="1";
	//			try {
	//					BaseUserBean user = (BaseUserBean) request.getSession().getAttribute(CommonConstant.SESSION_KEY_USER_CUSTOMER_SERVICE);
	//					if(user!=null){
	//						bean.setCreate_ip(getIpAddr());
	//						bean.setCreate_id(user.getUser_id());
	//						bean.setUpdate_ip(getIpAddr());
	//						bean.setUpdate_id(user.getUser_id());
	//					}
	//					msg=consultMsgManager.saveOrUpdateData(bean);
	//			} catch (Exception e) {
	//				msg=e.getMessage();
	//			}
	//			request.setAttribute("msg",msg);
	//		}else{
	//			request.setAttribute("msg", "信息保存失败!");
	//		}
	//		return SUCCESS;
	//	}
	//	/**
	//	 * <p>
	//	 * 删除。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>删除。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String del() {
	//		log.info("C301Action del.........");
	//		
	//		String id=request.getParameter("id");
	//		TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//		bean1.setId(id);
	//		String msg="1";
	//		try {
	//			msg=consultMsgManager.deleteDataById(bean1);
	//		} catch (Exception e) {
	//			msg=e.getMessage();
	//		}
	//		request.setAttribute("msg",msg);
	//		return SUCCESS;
	//	}
	
	
	
	/**
	 * BEAN类  咨询信息设定
	 * @param bean BEAN类  咨询信息
	 */
	//	/**
	//	 * <p>
	//	 * 编辑。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>编辑。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String edit() {
	//		log.info("C301Action edit.........");
	//		String id=request.getParameter("id");
	//		if(id!=null){
	//			TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//			bean1.setId(id);
	//			bean=consultMsgManager.findDataById(bean1);
	//		}else{
	//			SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
	//			request.setAttribute("date", sdf.format(new Date()));
	//		}
	//		return "edit";
	//	}
	//	/**
	//	 * <p>
	//	 * 信息保存
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>新增。</div>
	//	 * <div>修改。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String save() {
	//		log.info("C301Action edit.........");
	//		if(bean!=null){
	//			String msg="1";
	//			try {
	//					BaseUserBean user = (BaseUserBean) request.getSession().getAttribute(CommonConstant.SESSION_KEY_USER_CUSTOMER_SERVICE);
	//					if(user!=null){
	//						bean.setCreate_ip(getIpAddr());
	//						bean.setCreate_id(user.getUser_id());
	//						bean.setUpdate_ip(getIpAddr());
	//						bean.setUpdate_id(user.getUser_id());
	//					}
	//					msg=consultMsgManager.saveOrUpdateData(bean);
	//			} catch (Exception e) {
	//				msg=e.getMessage();
	//			}
	//			request.setAttribute("msg",msg);
	//		}else{
	//			request.setAttribute("msg", "信息保存失败!");
	//		}
	//		return SUCCESS;
	//	}
	//	/**
	//	 * <p>
	//	 * 删除。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>删除。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String del() {
	//		log.info("C301Action del.........");
	//		
	//		String id=request.getParameter("id");
	//		TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//		bean1.setId(id);
	//		String msg="1";
	//		try {
	//			msg=consultMsgManager.deleteDataById(bean1);
	//		} catch (Exception e) {
	//			msg=e.getMessage();
	//		}
	//		request.setAttribute("msg",msg);
	//		return SUCCESS;
	//	}
	
	
	
	/**
	 * BEAN类  咨询信息取得
	 * @return BEAN类  咨询信息
	 */
	//	/**
	//	 * <p>
	//	 * 编辑。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>编辑。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String edit() {
	//		log.info("C301Action edit.........");
	//		String id=request.getParameter("id");
	//		if(id!=null){
	//			TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//			bean1.setId(id);
	//			bean=consultMsgManager.findDataById(bean1);
	//		}else{
	//			SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
	//			request.setAttribute("date", sdf.format(new Date()));
	//		}
	//		return "edit";
	//	}
	//	/**
	//	 * <p>
	//	 * 信息保存
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>新增。</div>
	//	 * <div>修改。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String save() {
	//		log.info("C301Action edit.........");
	//		if(bean!=null){
	//			String msg="1";
	//			try {
	//					BaseUserBean user = (BaseUserBean) request.getSession().getAttribute(CommonConstant.SESSION_KEY_USER_CUSTOMER_SERVICE);
	//					if(user!=null){
	//						bean.setCreate_ip(getIpAddr());
	//						bean.setCreate_id(user.getUser_id());
	//						bean.setUpdate_ip(getIpAddr());
	//						bean.setUpdate_id(user.getUser_id());
	//					}
	//					msg=consultMsgManager.saveOrUpdateData(bean);
	//			} catch (Exception e) {
	//				msg=e.getMessage();
	//			}
	//			request.setAttribute("msg",msg);
	//		}else{
	//			request.setAttribute("msg", "信息保存失败!");
	//		}
	//		return SUCCESS;
	//	}
	//	/**
	//	 * <p>
	//	 * 删除。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>删除。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String del() {
	//		log.info("C301Action del.........");
	//		
	//		String id=request.getParameter("id");
	//		TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//		bean1.setId(id);
	//		String msg="1";
	//		try {
	//			msg=consultMsgManager.deleteDataById(bean1);
	//		} catch (Exception e) {
	//			msg=e.getMessage();
	//		}
	//		request.setAttribute("msg",msg);
	//		return SUCCESS;
	//	}
	
	
	
	/**
	 * BEAN类  咨询信息取得
	 * @return BEAN类  咨询信息
	 */
	//	/**
	//	 * <p>
	//	 * 编辑。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>编辑。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String edit() {
	//		log.info("C301Action edit.........");
	//		String id=request.getParameter("id");
	//		if(id!=null){
	//			TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//			bean1.setId(id);
	//			bean=consultMsgManager.findDataById(bean1);
	//		}else{
	//			SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
	//			request.setAttribute("date", sdf.format(new Date()));
	//		}
	//		return "edit";
	//	}
	//	/**
	//	 * <p>
	//	 * 信息保存
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>新增。</div>
	//	 * <div>修改。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String save() {
	//		log.info("C301Action edit.........");
	//		if(bean!=null){
	//			String msg="1";
	//			try {
	//					BaseUserBean user = (BaseUserBean) request.getSession().getAttribute(CommonConstant.SESSION_KEY_USER_CUSTOMER_SERVICE);
	//					if(user!=null){
	//						bean.setCreate_ip(getIpAddr());
	//						bean.setCreate_id(user.getUser_id());
	//						bean.setUpdate_ip(getIpAddr());
	//						bean.setUpdate_id(user.getUser_id());
	//					}
	//					msg=consultMsgManager.saveOrUpdateData(bean);
	//			} catch (Exception e) {
	//				msg=e.getMessage();
	//			}
	//			request.setAttribute("msg",msg);
	//		}else{
	//			request.setAttribute("msg", "信息保存失败!");
	//		}
	//		return SUCCESS;
	//	}
	//	/**
	//	 * <p>
	//	 * 删除。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>删除。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String del() {
	//		log.info("C301Action del.........");
	//		
	//		String id=request.getParameter("id");
	//		TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//		bean1.setId(id);
	//		String msg="1";
	//		try {
	//			msg=consultMsgManager.deleteDataById(bean1);
	//		} catch (Exception e) {
	//			msg=e.getMessage();
	//		}
	//		request.setAttribute("msg",msg);
	//		return SUCCESS;
	//	}
	
	
	
	/**
	 * BEAN类  咨询信息设定
	 * @param bean BEAN类  咨询信息
	 */
	//	/**
	//	 * <p>
	//	 * 编辑。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>编辑。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String edit() {
	//		log.info("C301Action edit.........");
	//		String id=request.getParameter("id");
	//		if(id!=null){
	//			TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//			bean1.setId(id);
	//			bean=consultMsgManager.findDataById(bean1);
	//		}else{
	//			SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
	//			request.setAttribute("date", sdf.format(new Date()));
	//		}
	//		return "edit";
	//	}
	//	/**
	//	 * <p>
	//	 * 信息保存
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>新增。</div>
	//	 * <div>修改。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String save() {
	//		log.info("C301Action edit.........");
	//		if(bean!=null){
	//			String msg="1";
	//			try {
	//					BaseUserBean user = (BaseUserBean) request.getSession().getAttribute(CommonConstant.SESSION_KEY_USER_CUSTOMER_SERVICE);
	//					if(user!=null){
	//						bean.setCreate_ip(getIpAddr());
	//						bean.setCreate_id(user.getUser_id());
	//						bean.setUpdate_ip(getIpAddr());
	//						bean.setUpdate_id(user.getUser_id());
	//					}
	//					msg=consultMsgManager.saveOrUpdateData(bean);
	//			} catch (Exception e) {
	//				msg=e.getMessage();
	//			}
	//			request.setAttribute("msg",msg);
	//		}else{
	//			request.setAttribute("msg", "信息保存失败!");
	//		}
	//		return SUCCESS;
	//	}
	//	/**
	//	 * <p>
	//	 * 删除。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>删除。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String del() {
	//		log.info("C301Action del.........");
	//		
	//		String id=request.getParameter("id");
	//		TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//		bean1.setId(id);
	//		String msg="1";
	//		try {
	//			msg=consultMsgManager.deleteDataById(bean1);
	//		} catch (Exception e) {
	//			msg=e.getMessage();
	//		}
	//		request.setAttribute("msg",msg);
	//		return SUCCESS;
	//	}
	
	
	
	/**
	 * BEAN类  咨询信息取得
	 * @return BEAN类  咨询信息
	 */
	//	/**
	//	 * <p>
	//	 * 编辑。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>编辑。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String edit() {
	//		log.info("C301Action edit.........");
	//		String id=request.getParameter("id");
	//		if(id!=null){
	//			TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//			bean1.setId(id);
	//			bean=consultMsgManager.findDataById(bean1);
	//		}else{
	//			SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
	//			request.setAttribute("date", sdf.format(new Date()));
	//		}
	//		return "edit";
	//	}
	//	/**
	//	 * <p>
	//	 * 信息保存
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>新增。</div>
	//	 * <div>修改。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String save() {
	//		log.info("C301Action edit.........");
	//		if(bean!=null){
	//			String msg="1";
	//			try {
	//					BaseUserBean user = (BaseUserBean) request.getSession().getAttribute(CommonConstant.SESSION_KEY_USER_CUSTOMER_SERVICE);
	//					if(user!=null){
	//						bean.setCreate_ip(getIpAddr());
	//						bean.setCreate_id(user.getUser_id());
	//						bean.setUpdate_ip(getIpAddr());
	//						bean.setUpdate_id(user.getUser_id());
	//					}
	//					msg=consultMsgManager.saveOrUpdateData(bean);
	//			} catch (Exception e) {
	//				msg=e.getMessage();
	//			}
	//			request.setAttribute("msg",msg);
	//		}else{
	//			request.setAttribute("msg", "信息保存失败!");
	//		}
	//		return SUCCESS;
	//	}
	//	/**
	//	 * <p>
	//	 * 删除。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>删除。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String del() {
	//		log.info("C301Action del.........");
	//		
	//		String id=request.getParameter("id");
	//		TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//		bean1.setId(id);
	//		String msg="1";
	//		try {
	//			msg=consultMsgManager.deleteDataById(bean1);
	//		} catch (Exception e) {
	//			msg=e.getMessage();
	//		}
	//		request.setAttribute("msg",msg);
	//		return SUCCESS;
	//	}
	
	
	
	/**
	 * BEAN类  咨询信息设定
	 * @param bean BEAN类  咨询信息
	 */
	//	/**
	//	 * <p>
	//	 * 编辑。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>编辑。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String edit() {
	//		log.info("C301Action edit.........");
	//		String id=request.getParameter("id");
	//		if(id!=null){
	//			TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//			bean1.setId(id);
	//			bean=consultMsgManager.findDataById(bean1);
	//		}else{
	//			SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
	//			request.setAttribute("date", sdf.format(new Date()));
	//		}
	//		return "edit";
	//	}
	//	/**
	//	 * <p>
	//	 * 信息保存
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>新增。</div>
	//	 * <div>修改。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String save() {
	//		log.info("C301Action edit.........");
	//		if(bean!=null){
	//			String msg="1";
	//			try {
	//					BaseUserBean user = (BaseUserBean) request.getSession().getAttribute(CommonConstant.SESSION_KEY_USER_CUSTOMER_SERVICE);
	//					if(user!=null){
	//						bean.setCreate_ip(getIpAddr());
	//						bean.setCreate_id(user.getUser_id());
	//						bean.setUpdate_ip(getIpAddr());
	//						bean.setUpdate_id(user.getUser_id());
	//					}
	//					msg=consultMsgManager.saveOrUpdateData(bean);
	//			} catch (Exception e) {
	//				msg=e.getMessage();
	//			}
	//			request.setAttribute("msg",msg);
	//		}else{
	//			request.setAttribute("msg", "信息保存失败!");
	//		}
	//		return SUCCESS;
	//	}
	//	/**
	//	 * <p>
	//	 * 删除。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>删除。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String del() {
	//		log.info("C301Action del.........");
	//		
	//		String id=request.getParameter("id");
	//		TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//		bean1.setId(id);
	//		String msg="1";
	//		try {
	//			msg=consultMsgManager.deleteDataById(bean1);
	//		} catch (Exception e) {
	//			msg=e.getMessage();
	//		}
	//		request.setAttribute("msg",msg);
	//		return SUCCESS;
	//	}
	
	
	
	/**
	 * BEAN类  咨询信息取得
	 * @return BEAN类  咨询信息
	 */
	//	/**
	//	 * <p>
	//	 * 编辑。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>编辑。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String edit() {
	//		log.info("C301Action edit.........");
	//		String id=request.getParameter("id");
	//		if(id!=null){
	//			TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//			bean1.setId(id);
	//			bean=consultMsgManager.findDataById(bean1);
	//		}else{
	//			SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
	//			request.setAttribute("date", sdf.format(new Date()));
	//		}
	//		return "edit";
	//	}
	//	/**
	//	 * <p>
	//	 * 信息保存
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>新增。</div>
	//	 * <div>修改。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String save() {
	//		log.info("C301Action edit.........");
	//		if(bean!=null){
	//			String msg="1";
	//			try {
	//					BaseUserBean user = (BaseUserBean) request.getSession().getAttribute(CommonConstant.SESSION_KEY_USER_CUSTOMER_SERVICE);
	//					if(user!=null){
	//						bean.setCreate_ip(getIpAddr());
	//						bean.setCreate_id(user.getUser_id());
	//						bean.setUpdate_ip(getIpAddr());
	//						bean.setUpdate_id(user.getUser_id());
	//					}
	//					msg=consultMsgManager.saveOrUpdateData(bean);
	//			} catch (Exception e) {
	//				msg=e.getMessage();
	//			}
	//			request.setAttribute("msg",msg);
	//		}else{
	//			request.setAttribute("msg", "信息保存失败!");
	//		}
	//		return SUCCESS;
	//	}
	//	/**
	//	 * <p>
	//	 * 删除。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>删除。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String del() {
	//		log.info("C301Action del.........");
	//		
	//		String id=request.getParameter("id");
	//		TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//		bean1.setId(id);
	//		String msg="1";
	//		try {
	//			msg=consultMsgManager.deleteDataById(bean1);
	//		} catch (Exception e) {
	//			msg=e.getMessage();
	//		}
	//		request.setAttribute("msg",msg);
	//		return SUCCESS;
	//	}
	
	
	
	/**
	 * BEAN类  咨询信息取得
	 * @return BEAN类  咨询信息
	 */
	//	/**
	//	 * <p>
	//	 * 编辑。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>编辑。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String edit() {
	//		log.info("C301Action edit.........");
	//		String id=request.getParameter("id");
	//		if(id!=null){
	//			TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//			bean1.setId(id);
	//			bean=consultMsgManager.findDataById(bean1);
	//		}else{
	//			SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
	//			request.setAttribute("date", sdf.format(new Date()));
	//		}
	//		return "edit";
	//	}
	//	/**
	//	 * <p>
	//	 * 信息保存
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>新增。</div>
	//	 * <div>修改。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String save() {
	//		log.info("C301Action edit.........");
	//		if(bean!=null){
	//			String msg="1";
	//			try {
	//					BaseUserBean user = (BaseUserBean) request.getSession().getAttribute(CommonConstant.SESSION_KEY_USER_CUSTOMER_SERVICE);
	//					if(user!=null){
	//						bean.setCreate_ip(getIpAddr());
	//						bean.setCreate_id(user.getUser_id());
	//						bean.setUpdate_ip(getIpAddr());
	//						bean.setUpdate_id(user.getUser_id());
	//					}
	//					msg=consultMsgManager.saveOrUpdateData(bean);
	//			} catch (Exception e) {
	//				msg=e.getMessage();
	//			}
	//			request.setAttribute("msg",msg);
	//		}else{
	//			request.setAttribute("msg", "信息保存失败!");
	//		}
	//		return SUCCESS;
	//	}
	//	/**
	//	 * <p>
	//	 * 删除。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>删除。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String del() {
	//		log.info("C301Action del.........");
	//		
	//		String id=request.getParameter("id");
	//		TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//		bean1.setId(id);
	//		String msg="1";
	//		try {
	//			msg=consultMsgManager.deleteDataById(bean1);
	//		} catch (Exception e) {
	//			msg=e.getMessage();
	//		}
	//		request.setAttribute("msg",msg);
	//		return SUCCESS;
	//	}
	
	
	
	/**
	 * BEAN类  咨询信息设定
	 * @param bean BEAN类  咨询信息
	 */
	//	/**
	//	 * <p>
	//	 * 编辑。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>编辑。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String edit() {
	//		log.info("C301Action edit.........");
	//		String id=request.getParameter("id");
	//		if(id!=null){
	//			TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//			bean1.setId(id);
	//			bean=consultMsgManager.findDataById(bean1);
	//		}else{
	//			SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
	//			request.setAttribute("date", sdf.format(new Date()));
	//		}
	//		return "edit";
	//	}
	//	/**
	//	 * <p>
	//	 * 信息保存
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>新增。</div>
	//	 * <div>修改。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String save() {
	//		log.info("C301Action edit.........");
	//		if(bean!=null){
	//			String msg="1";
	//			try {
	//					BaseUserBean user = (BaseUserBean) request.getSession().getAttribute(CommonConstant.SESSION_KEY_USER_CUSTOMER_SERVICE);
	//					if(user!=null){
	//						bean.setCreate_ip(getIpAddr());
	//						bean.setCreate_id(user.getUser_id());
	//						bean.setUpdate_ip(getIpAddr());
	//						bean.setUpdate_id(user.getUser_id());
	//					}
	//					msg=consultMsgManager.saveOrUpdateData(bean);
	//			} catch (Exception e) {
	//				msg=e.getMessage();
	//			}
	//			request.setAttribute("msg",msg);
	//		}else{
	//			request.setAttribute("msg", "信息保存失败!");
	//		}
	//		return SUCCESS;
	//	}
	//	/**
	//	 * <p>
	//	 * 删除。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>删除。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String del() {
	//		log.info("C301Action del.........");
	//		
	//		String id=request.getParameter("id");
	//		TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//		bean1.setId(id);
	//		String msg="1";
	//		try {
	//			msg=consultMsgManager.deleteDataById(bean1);
	//		} catch (Exception e) {
	//			msg=e.getMessage();
	//		}
	//		request.setAttribute("msg",msg);
	//		return SUCCESS;
	//	}
	
	
	
	/**
	 * BEAN类  咨询信息取得
	 * @return BEAN类  咨询信息
	 */
	//	/**
	//	 * <p>
	//	 * 编辑。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>编辑。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String edit() {
	//		log.info("C301Action edit.........");
	//		String id=request.getParameter("id");
	//		if(id!=null){
	//			TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//			bean1.setId(id);
	//			bean=consultMsgManager.findDataById(bean1);
	//		}else{
	//			SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
	//			request.setAttribute("date", sdf.format(new Date()));
	//		}
	//		return "edit";
	//	}
	//	/**
	//	 * <p>
	//	 * 信息保存
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>新增。</div>
	//	 * <div>修改。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String save() {
	//		log.info("C301Action edit.........");
	//		if(bean!=null){
	//			String msg="1";
	//			try {
	//					BaseUserBean user = (BaseUserBean) request.getSession().getAttribute(CommonConstant.SESSION_KEY_USER_CUSTOMER_SERVICE);
	//					if(user!=null){
	//						bean.setCreate_ip(getIpAddr());
	//						bean.setCreate_id(user.getUser_id());
	//						bean.setUpdate_ip(getIpAddr());
	//						bean.setUpdate_id(user.getUser_id());
	//					}
	//					msg=consultMsgManager.saveOrUpdateData(bean);
	//			} catch (Exception e) {
	//				msg=e.getMessage();
	//			}
	//			request.setAttribute("msg",msg);
	//		}else{
	//			request.setAttribute("msg", "信息保存失败!");
	//		}
	//		return SUCCESS;
	//	}
	//	/**
	//	 * <p>
	//	 * 删除。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>删除。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String del() {
	//		log.info("C301Action del.........");
	//		
	//		String id=request.getParameter("id");
	//		TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//		bean1.setId(id);
	//		String msg="1";
	//		try {
	//			msg=consultMsgManager.deleteDataById(bean1);
	//		} catch (Exception e) {
	//			msg=e.getMessage();
	//		}
	//		request.setAttribute("msg",msg);
	//		return SUCCESS;
	//	}
	
	
	
	/**
	 * BEAN类  咨询信息取得
	 * @return BEAN类  咨询信息
	 */
	//	/**
	//	 * <p>
	//	 * 编辑。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>编辑。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String edit() {
	//		log.info("C301Action edit.........");
	//		String id=request.getParameter("id");
	//		if(id!=null){
	//			TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//			bean1.setId(id);
	//			bean=consultMsgManager.findDataById(bean1);
	//		}else{
	//			SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
	//			request.setAttribute("date", sdf.format(new Date()));
	//		}
	//		return "edit";
	//	}
	//	/**
	//	 * <p>
	//	 * 信息保存
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>新增。</div>
	//	 * <div>修改。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String save() {
	//		log.info("C301Action edit.........");
	//		if(bean!=null){
	//			String msg="1";
	//			try {
	//					BaseUserBean user = (BaseUserBean) request.getSession().getAttribute(CommonConstant.SESSION_KEY_USER_CUSTOMER_SERVICE);
	//					if(user!=null){
	//						bean.setCreate_ip(getIpAddr());
	//						bean.setCreate_id(user.getUser_id());
	//						bean.setUpdate_ip(getIpAddr());
	//						bean.setUpdate_id(user.getUser_id());
	//					}
	//					msg=consultMsgManager.saveOrUpdateData(bean);
	//			} catch (Exception e) {
	//				msg=e.getMessage();
	//			}
	//			request.setAttribute("msg",msg);
	//		}else{
	//			request.setAttribute("msg", "信息保存失败!");
	//		}
	//		return SUCCESS;
	//	}
	//	/**
	//	 * <p>
	//	 * 删除。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>删除。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String del() {
	//		log.info("C301Action del.........");
	//		
	//		String id=request.getParameter("id");
	//		TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//		bean1.setId(id);
	//		String msg="1";
	//		try {
	//			msg=consultMsgManager.deleteDataById(bean1);
	//		} catch (Exception e) {
	//			msg=e.getMessage();
	//		}
	//		request.setAttribute("msg",msg);
	//		return SUCCESS;
	//	}
	
	
	
	/**
	 * BEAN类  咨询信息设定
	 * @param bean BEAN类  咨询信息
	 */
	//	/**
	//	 * <p>
	//	 * 编辑。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>编辑。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String edit() {
	//		log.info("C301Action edit.........");
	//		String id=request.getParameter("id");
	//		if(id!=null){
	//			TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//			bean1.setId(id);
	//			bean=consultMsgManager.findDataById(bean1);
	//		}else{
	//			SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
	//			request.setAttribute("date", sdf.format(new Date()));
	//		}
	//		return "edit";
	//	}
	//	/**
	//	 * <p>
	//	 * 信息保存
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>新增。</div>
	//	 * <div>修改。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String save() {
	//		log.info("C301Action edit.........");
	//		if(bean!=null){
	//			String msg="1";
	//			try {
	//					BaseUserBean user = (BaseUserBean) request.getSession().getAttribute(CommonConstant.SESSION_KEY_USER_CUSTOMER_SERVICE);
	//					if(user!=null){
	//						bean.setCreate_ip(getIpAddr());
	//						bean.setCreate_id(user.getUser_id());
	//						bean.setUpdate_ip(getIpAddr());
	//						bean.setUpdate_id(user.getUser_id());
	//					}
	//					msg=consultMsgManager.saveOrUpdateData(bean);
	//			} catch (Exception e) {
	//				msg=e.getMessage();
	//			}
	//			request.setAttribute("msg",msg);
	//		}else{
	//			request.setAttribute("msg", "信息保存失败!");
	//		}
	//		return SUCCESS;
	//	}
	//	/**
	//	 * <p>
	//	 * 删除。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>删除。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String del() {
	//		log.info("C301Action del.........");
	//		
	//		String id=request.getParameter("id");
	//		TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//		bean1.setId(id);
	//		String msg="1";
	//		try {
	//			msg=consultMsgManager.deleteDataById(bean1);
	//		} catch (Exception e) {
	//			msg=e.getMessage();
	//		}
	//		request.setAttribute("msg",msg);
	//		return SUCCESS;
	//	}
	
	
	
	/**
	 * BEAN类  咨询信息取得
	 * @return BEAN类  咨询信息
	 */
	//	/**
	//	 * <p>
	//	 * 编辑。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>编辑。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String edit() {
	//		log.info("C301Action edit.........");
	//		String id=request.getParameter("id");
	//		if(id!=null){
	//			TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//			bean1.setId(id);
	//			bean=consultMsgManager.findDataById(bean1);
	//		}else{
	//			SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
	//			request.setAttribute("date", sdf.format(new Date()));
	//		}
	//		return "edit";
	//	}
	//	/**
	//	 * <p>
	//	 * 信息保存
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>新增。</div>
	//	 * <div>修改。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String save() {
	//		log.info("C301Action edit.........");
	//		if(bean!=null){
	//			String msg="1";
	//			try {
	//					BaseUserBean user = (BaseUserBean) request.getSession().getAttribute(CommonConstant.SESSION_KEY_USER_CUSTOMER_SERVICE);
	//					if(user!=null){
	//						bean.setCreate_ip(getIpAddr());
	//						bean.setCreate_id(user.getUser_id());
	//						bean.setUpdate_ip(getIpAddr());
	//						bean.setUpdate_id(user.getUser_id());
	//					}
	//					msg=consultMsgManager.saveOrUpdateData(bean);
	//			} catch (Exception e) {
	//				msg=e.getMessage();
	//			}
	//			request.setAttribute("msg",msg);
	//		}else{
	//			request.setAttribute("msg", "信息保存失败!");
	//		}
	//		return SUCCESS;
	//	}
	//	/**
	//	 * <p>
	//	 * 删除。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>删除。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String del() {
	//		log.info("C301Action del.........");
	//		
	//		String id=request.getParameter("id");
	//		TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//		bean1.setId(id);
	//		String msg="1";
	//		try {
	//			msg=consultMsgManager.deleteDataById(bean1);
	//		} catch (Exception e) {
	//			msg=e.getMessage();
	//		}
	//		request.setAttribute("msg",msg);
	//		return SUCCESS;
	//	}
	
	
	
	/**
	 * BEAN类  咨询信息设定
	 * @param bean BEAN类  咨询信息
	 */
	//	/**
	//	 * <p>
	//	 * 编辑。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>编辑。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String edit() {
	//		log.info("C301Action edit.........");
	//		String id=request.getParameter("id");
	//		if(id!=null){
	//			TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//			bean1.setId(id);
	//			bean=consultMsgManager.findDataById(bean1);
	//		}else{
	//			SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
	//			request.setAttribute("date", sdf.format(new Date()));
	//		}
	//		return "edit";
	//	}
	//	/**
	//	 * <p>
	//	 * 信息保存
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>新增。</div>
	//	 * <div>修改。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String save() {
	//		log.info("C301Action edit.........");
	//		if(bean!=null){
	//			String msg="1";
	//			try {
	//					BaseUserBean user = (BaseUserBean) request.getSession().getAttribute(CommonConstant.SESSION_KEY_USER_CUSTOMER_SERVICE);
	//					if(user!=null){
	//						bean.setCreate_ip(getIpAddr());
	//						bean.setCreate_id(user.getUser_id());
	//						bean.setUpdate_ip(getIpAddr());
	//						bean.setUpdate_id(user.getUser_id());
	//					}
	//					msg=consultMsgManager.saveOrUpdateData(bean);
	//			} catch (Exception e) {
	//				msg=e.getMessage();
	//			}
	//			request.setAttribute("msg",msg);
	//		}else{
	//			request.setAttribute("msg", "信息保存失败!");
	//		}
	//		return SUCCESS;
	//	}
	//	/**
	//	 * <p>
	//	 * 删除。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>删除。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String del() {
	//		log.info("C301Action del.........");
	//		
	//		String id=request.getParameter("id");
	//		TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//		bean1.setId(id);
	//		String msg="1";
	//		try {
	//			msg=consultMsgManager.deleteDataById(bean1);
	//		} catch (Exception e) {
	//			msg=e.getMessage();
	//		}
	//		request.setAttribute("msg",msg);
	//		return SUCCESS;
	//	}
	
	
	
	/**
	 * BEAN类  咨询信息取得
	 * @return BEAN类  咨询信息
	 */
	//	/**
	//	 * <p>
	//	 * 编辑。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>编辑。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String edit() {
	//		log.info("C301Action edit.........");
	//		String id=request.getParameter("id");
	//		if(id!=null){
	//			TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//			bean1.setId(id);
	//			bean=consultMsgManager.findDataById(bean1);
	//		}else{
	//			SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
	//			request.setAttribute("date", sdf.format(new Date()));
	//		}
	//		return "edit";
	//	}
	//	/**
	//	 * <p>
	//	 * 信息保存
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>新增。</div>
	//	 * <div>修改。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String save() {
	//		log.info("C301Action edit.........");
	//		if(bean!=null){
	//			String msg="1";
	//			try {
	//					BaseUserBean user = (BaseUserBean) request.getSession().getAttribute(CommonConstant.SESSION_KEY_USER_CUSTOMER_SERVICE);
	//					if(user!=null){
	//						bean.setCreate_ip(getIpAddr());
	//						bean.setCreate_id(user.getUser_id());
	//						bean.setUpdate_ip(getIpAddr());
	//						bean.setUpdate_id(user.getUser_id());
	//					}
	//					msg=consultMsgManager.saveOrUpdateData(bean);
	//			} catch (Exception e) {
	//				msg=e.getMessage();
	//			}
	//			request.setAttribute("msg",msg);
	//		}else{
	//			request.setAttribute("msg", "信息保存失败!");
	//		}
	//		return SUCCESS;
	//	}
	//	/**
	//	 * <p>
	//	 * 删除。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>删除。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String del() {
	//		log.info("C301Action del.........");
	//		
	//		String id=request.getParameter("id");
	//		TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//		bean1.setId(id);
	//		String msg="1";
	//		try {
	//			msg=consultMsgManager.deleteDataById(bean1);
	//		} catch (Exception e) {
	//			msg=e.getMessage();
	//		}
	//		request.setAttribute("msg",msg);
	//		return SUCCESS;
	//	}
	
	
	
	/**
	 * BEAN类  咨询信息取得
	 * @return BEAN类  咨询信息
	 */
	//	/**
	//	 * <p>
	//	 * 编辑。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>编辑。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String edit() {
	//		log.info("C301Action edit.........");
	//		String id=request.getParameter("id");
	//		if(id!=null){
	//			TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//			bean1.setId(id);
	//			bean=consultMsgManager.findDataById(bean1);
	//		}else{
	//			SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
	//			request.setAttribute("date", sdf.format(new Date()));
	//		}
	//		return "edit";
	//	}
	//	/**
	//	 * <p>
	//	 * 信息保存
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>新增。</div>
	//	 * <div>修改。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String save() {
	//		log.info("C301Action edit.........");
	//		if(bean!=null){
	//			String msg="1";
	//			try {
	//					BaseUserBean user = (BaseUserBean) request.getSession().getAttribute(CommonConstant.SESSION_KEY_USER_CUSTOMER_SERVICE);
	//					if(user!=null){
	//						bean.setCreate_ip(getIpAddr());
	//						bean.setCreate_id(user.getUser_id());
	//						bean.setUpdate_ip(getIpAddr());
	//						bean.setUpdate_id(user.getUser_id());
	//					}
	//					msg=consultMsgManager.saveOrUpdateData(bean);
	//			} catch (Exception e) {
	//				msg=e.getMessage();
	//			}
	//			request.setAttribute("msg",msg);
	//		}else{
	//			request.setAttribute("msg", "信息保存失败!");
	//		}
	//		return SUCCESS;
	//	}
	//	/**
	//	 * <p>
	//	 * 删除。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>删除。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String del() {
	//		log.info("C301Action del.........");
	//		
	//		String id=request.getParameter("id");
	//		TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//		bean1.setId(id);
	//		String msg="1";
	//		try {
	//			msg=consultMsgManager.deleteDataById(bean1);
	//		} catch (Exception e) {
	//			msg=e.getMessage();
	//		}
	//		request.setAttribute("msg",msg);
	//		return SUCCESS;
	//	}
	
	
	
	/**
	 * BEAN类  咨询信息设定
	 * @param bean BEAN类  咨询信息
	 */
	//	/**
	//	 * <p>
	//	 * 编辑。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>编辑。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String edit() {
	//		log.info("C301Action edit.........");
	//		String id=request.getParameter("id");
	//		if(id!=null){
	//			TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//			bean1.setId(id);
	//			bean=consultMsgManager.findDataById(bean1);
	//		}else{
	//			SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
	//			request.setAttribute("date", sdf.format(new Date()));
	//		}
	//		return "edit";
	//	}
	//	/**
	//	 * <p>
	//	 * 信息保存
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>新增。</div>
	//	 * <div>修改。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String save() {
	//		log.info("C301Action edit.........");
	//		if(bean!=null){
	//			String msg="1";
	//			try {
	//					BaseUserBean user = (BaseUserBean) request.getSession().getAttribute(CommonConstant.SESSION_KEY_USER_CUSTOMER_SERVICE);
	//					if(user!=null){
	//						bean.setCreate_ip(getIpAddr());
	//						bean.setCreate_id(user.getUser_id());
	//						bean.setUpdate_ip(getIpAddr());
	//						bean.setUpdate_id(user.getUser_id());
	//					}
	//					msg=consultMsgManager.saveOrUpdateData(bean);
	//			} catch (Exception e) {
	//				msg=e.getMessage();
	//			}
	//			request.setAttribute("msg",msg);
	//		}else{
	//			request.setAttribute("msg", "信息保存失败!");
	//		}
	//		return SUCCESS;
	//	}
	//	/**
	//	 * <p>
	//	 * 删除。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>删除。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String del() {
	//		log.info("C301Action del.........");
	//		
	//		String id=request.getParameter("id");
	//		TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//		bean1.setId(id);
	//		String msg="1";
	//		try {
	//			msg=consultMsgManager.deleteDataById(bean1);
	//		} catch (Exception e) {
	//			msg=e.getMessage();
	//		}
	//		request.setAttribute("msg",msg);
	//		return SUCCESS;
	//	}
	
	
	
	/**
	 * BEAN类  咨询信息取得
	 * @return BEAN类  咨询信息
	 */
	//	/**
	//	 * <p>
	//	 * 编辑。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>编辑。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String edit() {
	//		log.info("C301Action edit.........");
	//		String id=request.getParameter("id");
	//		if(id!=null){
	//			TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//			bean1.setId(id);
	//			bean=consultMsgManager.findDataById(bean1);
	//		}else{
	//			SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
	//			request.setAttribute("date", sdf.format(new Date()));
	//		}
	//		return "edit";
	//	}
	//	/**
	//	 * <p>
	//	 * 信息保存
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>新增。</div>
	//	 * <div>修改。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String save() {
	//		log.info("C301Action edit.........");
	//		if(bean!=null){
	//			String msg="1";
	//			try {
	//					BaseUserBean user = (BaseUserBean) request.getSession().getAttribute(CommonConstant.SESSION_KEY_USER_CUSTOMER_SERVICE);
	//					if(user!=null){
	//						bean.setCreate_ip(getIpAddr());
	//						bean.setCreate_id(user.getUser_id());
	//						bean.setUpdate_ip(getIpAddr());
	//						bean.setUpdate_id(user.getUser_id());
	//					}
	//					msg=consultMsgManager.saveOrUpdateData(bean);
	//			} catch (Exception e) {
	//				msg=e.getMessage();
	//			}
	//			request.setAttribute("msg",msg);
	//		}else{
	//			request.setAttribute("msg", "信息保存失败!");
	//		}
	//		return SUCCESS;
	//	}
	//	/**
	//	 * <p>
	//	 * 删除。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>删除。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String del() {
	//		log.info("C301Action del.........");
	//		
	//		String id=request.getParameter("id");
	//		TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//		bean1.setId(id);
	//		String msg="1";
	//		try {
	//			msg=consultMsgManager.deleteDataById(bean1);
	//		} catch (Exception e) {
	//			msg=e.getMessage();
	//		}
	//		request.setAttribute("msg",msg);
	//		return SUCCESS;
	//	}
	
	
	
	/**
	 * BEAN类  咨询信息取得
	 * @return BEAN类  咨询信息
	 */
	//	/**
	//	 * <p>
	//	 * 编辑。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>编辑。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String edit() {
	//		log.info("C301Action edit.........");
	//		String id=request.getParameter("id");
	//		if(id!=null){
	//			TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//			bean1.setId(id);
	//			bean=consultMsgManager.findDataById(bean1);
	//		}else{
	//			SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
	//			request.setAttribute("date", sdf.format(new Date()));
	//		}
	//		return "edit";
	//	}
	//	/**
	//	 * <p>
	//	 * 信息保存
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>新增。</div>
	//	 * <div>修改。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String save() {
	//		log.info("C301Action edit.........");
	//		if(bean!=null){
	//			String msg="1";
	//			try {
	//					BaseUserBean user = (BaseUserBean) request.getSession().getAttribute(CommonConstant.SESSION_KEY_USER_CUSTOMER_SERVICE);
	//					if(user!=null){
	//						bean.setCreate_ip(getIpAddr());
	//						bean.setCreate_id(user.getUser_id());
	//						bean.setUpdate_ip(getIpAddr());
	//						bean.setUpdate_id(user.getUser_id());
	//					}
	//					msg=consultMsgManager.saveOrUpdateData(bean);
	//			} catch (Exception e) {
	//				msg=e.getMessage();
	//			}
	//			request.setAttribute("msg",msg);
	//		}else{
	//			request.setAttribute("msg", "信息保存失败!");
	//		}
	//		return SUCCESS;
	//	}
	//	/**
	//	 * <p>
	//	 * 删除。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>删除。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String del() {
	//		log.info("C301Action del.........");
	//		
	//		String id=request.getParameter("id");
	//		TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//		bean1.setId(id);
	//		String msg="1";
	//		try {
	//			msg=consultMsgManager.deleteDataById(bean1);
	//		} catch (Exception e) {
	//			msg=e.getMessage();
	//		}
	//		request.setAttribute("msg",msg);
	//		return SUCCESS;
	//	}
	
	

	/**
	 * BEAN类  咨询信息设定
	 * @param bean BEAN类  咨询信息
	 */
	//	/**
	//	 * <p>
	//	 * 编辑。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>编辑。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String edit() {
	//		log.info("C301Action edit.........");
	//		String id=request.getParameter("id");
	//		if(id!=null){
	//			TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//			bean1.setId(id);
	//			bean=consultMsgManager.findDataById(bean1);
	//		}else{
	//			SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
	//			request.setAttribute("date", sdf.format(new Date()));
	//		}
	//		return "edit";
	//	}
	//	/**
	//	 * <p>
	//	 * 信息保存
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>新增。</div>
	//	 * <div>修改。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String save() {
	//		log.info("C301Action edit.........");
	//		if(bean!=null){
	//			String msg="1";
	//			try {
	//					BaseUserBean user = (BaseUserBean) request.getSession().getAttribute(CommonConstant.SESSION_KEY_USER_CUSTOMER_SERVICE);
	//					if(user!=null){
	//						bean.setCreate_ip(getIpAddr());
	//						bean.setCreate_id(user.getUser_id());
	//						bean.setUpdate_ip(getIpAddr());
	//						bean.setUpdate_id(user.getUser_id());
	//					}
	//					msg=consultMsgManager.saveOrUpdateData(bean);
	//			} catch (Exception e) {
	//				msg=e.getMessage();
	//			}
	//			request.setAttribute("msg",msg);
	//		}else{
	//			request.setAttribute("msg", "信息保存失败!");
	//		}
	//		return SUCCESS;
	//	}
	//	/**
	//	 * <p>
	//	 * 删除。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>删除。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String del() {
	//		log.info("C301Action del.........");
	//		
	//		String id=request.getParameter("id");
	//		TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//		bean1.setId(id);
	//		String msg="1";
	//		try {
	//			msg=consultMsgManager.deleteDataById(bean1);
	//		} catch (Exception e) {
	//			msg=e.getMessage();
	//		}
	//		request.setAttribute("msg",msg);
	//		return SUCCESS;
	//	}
	
	
	
	/**
	 * BEAN类  咨询信息取得
	 * @return BEAN类  咨询信息
	 */
	//	/**
	//	 * <p>
	//	 * 编辑。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>编辑。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String edit() {
	//		log.info("C301Action edit.........");
	//		String id=request.getParameter("id");
	//		if(id!=null){
	//			TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//			bean1.setId(id);
	//			bean=consultMsgManager.findDataById(bean1);
	//		}else{
	//			SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
	//			request.setAttribute("date", sdf.format(new Date()));
	//		}
	//		return "edit";
	//	}
	//	/**
	//	 * <p>
	//	 * 信息保存
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>新增。</div>
	//	 * <div>修改。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String save() {
	//		log.info("C301Action edit.........");
	//		if(bean!=null){
	//			String msg="1";
	//			try {
	//					BaseUserBean user = (BaseUserBean) request.getSession().getAttribute(CommonConstant.SESSION_KEY_USER_CUSTOMER_SERVICE);
	//					if(user!=null){
	//						bean.setCreate_ip(getIpAddr());
	//						bean.setCreate_id(user.getUser_id());
	//						bean.setUpdate_ip(getIpAddr());
	//						bean.setUpdate_id(user.getUser_id());
	//					}
	//					msg=consultMsgManager.saveOrUpdateData(bean);
	//			} catch (Exception e) {
	//				msg=e.getMessage();
	//			}
	//			request.setAttribute("msg",msg);
	//		}else{
	//			request.setAttribute("msg", "信息保存失败!");
	//		}
	//		return SUCCESS;
	//	}
	//	/**
	//	 * <p>
	//	 * 删除。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>删除。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String del() {
	//		log.info("C301Action del.........");
	//		
	//		String id=request.getParameter("id");
	//		TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//		bean1.setId(id);
	//		String msg="1";
	//		try {
	//			msg=consultMsgManager.deleteDataById(bean1);
	//		} catch (Exception e) {
	//			msg=e.getMessage();
	//		}
	//		request.setAttribute("msg",msg);
	//		return SUCCESS;
	//	}
	
	
	
	/**
	 * BEAN类  咨询信息设定
	 * @param bean BEAN类  咨询信息
	 */
	//	/**
	//	 * <p>
	//	 * 编辑。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>编辑。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String edit() {
	//		log.info("C301Action edit.........");
	//		String id=request.getParameter("id");
	//		if(id!=null){
	//			TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//			bean1.setId(id);
	//			bean=consultMsgManager.findDataById(bean1);
	//		}else{
	//			SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
	//			request.setAttribute("date", sdf.format(new Date()));
	//		}
	//		return "edit";
	//	}
	//	/**
	//	 * <p>
	//	 * 信息保存
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>新增。</div>
	//	 * <div>修改。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String save() {
	//		log.info("C301Action edit.........");
	//		if(bean!=null){
	//			String msg="1";
	//			try {
	//					BaseUserBean user = (BaseUserBean) request.getSession().getAttribute(CommonConstant.SESSION_KEY_USER_CUSTOMER_SERVICE);
	//					if(user!=null){
	//						bean.setCreate_ip(getIpAddr());
	//						bean.setCreate_id(user.getUser_id());
	//						bean.setUpdate_ip(getIpAddr());
	//						bean.setUpdate_id(user.getUser_id());
	//					}
	//					msg=consultMsgManager.saveOrUpdateData(bean);
	//			} catch (Exception e) {
	//				msg=e.getMessage();
	//			}
	//			request.setAttribute("msg",msg);
	//		}else{
	//			request.setAttribute("msg", "信息保存失败!");
	//		}
	//		return SUCCESS;
	//	}
	//	/**
	//	 * <p>
	//	 * 删除。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>删除。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String del() {
	//		log.info("C301Action del.........");
	//		
	//		String id=request.getParameter("id");
	//		TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//		bean1.setId(id);
	//		String msg="1";
	//		try {
	//			msg=consultMsgManager.deleteDataById(bean1);
	//		} catch (Exception e) {
	//			msg=e.getMessage();
	//		}
	//		request.setAttribute("msg",msg);
	//		return SUCCESS;
	//	}
	
	
	
	/**
	 * BEAN类  咨询信息取得
	 * @return BEAN类  咨询信息
	 */
	//	/**
	//	 * <p>
	//	 * 编辑。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>编辑。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String edit() {
	//		log.info("C301Action edit.........");
	//		String id=request.getParameter("id");
	//		if(id!=null){
	//			TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//			bean1.setId(id);
	//			bean=consultMsgManager.findDataById(bean1);
	//		}else{
	//			SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
	//			request.setAttribute("date", sdf.format(new Date()));
	//		}
	//		return "edit";
	//	}
	//	/**
	//	 * <p>
	//	 * 信息保存
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>新增。</div>
	//	 * <div>修改。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String save() {
	//		log.info("C301Action edit.........");
	//		if(bean!=null){
	//			String msg="1";
	//			try {
	//					BaseUserBean user = (BaseUserBean) request.getSession().getAttribute(CommonConstant.SESSION_KEY_USER_CUSTOMER_SERVICE);
	//					if(user!=null){
	//						bean.setCreate_ip(getIpAddr());
	//						bean.setCreate_id(user.getUser_id());
	//						bean.setUpdate_ip(getIpAddr());
	//						bean.setUpdate_id(user.getUser_id());
	//					}
	//					msg=consultMsgManager.saveOrUpdateData(bean);
	//			} catch (Exception e) {
	//				msg=e.getMessage();
	//			}
	//			request.setAttribute("msg",msg);
	//		}else{
	//			request.setAttribute("msg", "信息保存失败!");
	//		}
	//		return SUCCESS;
	//	}
	//	/**
	//	 * <p>
	//	 * 删除。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>删除。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String del() {
	//		log.info("C301Action del.........");
	//		
	//		String id=request.getParameter("id");
	//		TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//		bean1.setId(id);
	//		String msg="1";
	//		try {
	//			msg=consultMsgManager.deleteDataById(bean1);
	//		} catch (Exception e) {
	//			msg=e.getMessage();
	//		}
	//		request.setAttribute("msg",msg);
	//		return SUCCESS;
	//	}
	
	
	
	/**
	 * BEAN类  咨询信息取得
	 * @return BEAN类  咨询信息
	 */
	//	/**
	//	 * <p>
	//	 * 编辑。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>编辑。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String edit() {
	//		log.info("C301Action edit.........");
	//		String id=request.getParameter("id");
	//		if(id!=null){
	//			TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//			bean1.setId(id);
	//			bean=consultMsgManager.findDataById(bean1);
	//		}else{
	//			SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
	//			request.setAttribute("date", sdf.format(new Date()));
	//		}
	//		return "edit";
	//	}
	//	/**
	//	 * <p>
	//	 * 信息保存
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>新增。</div>
	//	 * <div>修改。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String save() {
	//		log.info("C301Action edit.........");
	//		if(bean!=null){
	//			String msg="1";
	//			try {
	//					BaseUserBean user = (BaseUserBean) request.getSession().getAttribute(CommonConstant.SESSION_KEY_USER_CUSTOMER_SERVICE);
	//					if(user!=null){
	//						bean.setCreate_ip(getIpAddr());
	//						bean.setCreate_id(user.getUser_id());
	//						bean.setUpdate_ip(getIpAddr());
	//						bean.setUpdate_id(user.getUser_id());
	//					}
	//					msg=consultMsgManager.saveOrUpdateData(bean);
	//			} catch (Exception e) {
	//				msg=e.getMessage();
	//			}
	//			request.setAttribute("msg",msg);
	//		}else{
	//			request.setAttribute("msg", "信息保存失败!");
	//		}
	//		return SUCCESS;
	//	}
	//	/**
	//	 * <p>
	//	 * 删除。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>删除。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String del() {
	//		log.info("C301Action del.........");
	//		
	//		String id=request.getParameter("id");
	//		TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//		bean1.setId(id);
	//		String msg="1";
	//		try {
	//			msg=consultMsgManager.deleteDataById(bean1);
	//		} catch (Exception e) {
	//			msg=e.getMessage();
	//		}
	//		request.setAttribute("msg",msg);
	//		return SUCCESS;
	//	}
	
	
	
	/**
	 * BEAN类  咨询信息设定
	 * @param bean BEAN类  咨询信息
	 */
	//	/**
	//	 * <p>
	//	 * 编辑。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>编辑。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String edit() {
	//		log.info("C301Action edit.........");
	//		String id=request.getParameter("id");
	//		if(id!=null){
	//			TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//			bean1.setId(id);
	//			bean=consultMsgManager.findDataById(bean1);
	//		}else{
	//			SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
	//			request.setAttribute("date", sdf.format(new Date()));
	//		}
	//		return "edit";
	//	}
	//	/**
	//	 * <p>
	//	 * 信息保存
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>新增。</div>
	//	 * <div>修改。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String save() {
	//		log.info("C301Action edit.........");
	//		if(bean!=null){
	//			String msg="1";
	//			try {
	//					BaseUserBean user = (BaseUserBean) request.getSession().getAttribute(CommonConstant.SESSION_KEY_USER_CUSTOMER_SERVICE);
	//					if(user!=null){
	//						bean.setCreate_ip(getIpAddr());
	//						bean.setCreate_id(user.getUser_id());
	//						bean.setUpdate_ip(getIpAddr());
	//						bean.setUpdate_id(user.getUser_id());
	//					}
	//					msg=consultMsgManager.saveOrUpdateData(bean);
	//			} catch (Exception e) {
	//				msg=e.getMessage();
	//			}
	//			request.setAttribute("msg",msg);
	//		}else{
	//			request.setAttribute("msg", "信息保存失败!");
	//		}
	//		return SUCCESS;
	//	}
	//	/**
	//	 * <p>
	//	 * 删除。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>删除。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String del() {
	//		log.info("C301Action del.........");
	//		
	//		String id=request.getParameter("id");
	//		TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//		bean1.setId(id);
	//		String msg="1";
	//		try {
	//			msg=consultMsgManager.deleteDataById(bean1);
	//		} catch (Exception e) {
	//			msg=e.getMessage();
	//		}
	//		request.setAttribute("msg",msg);
	//		return SUCCESS;
	//	}
	
	
	
	/**
	 * BEAN类  咨询信息取得
	 * @return BEAN类  咨询信息
	 */
	//	/**
	//	 * <p>
	//	 * 编辑。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>编辑。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String edit() {
	//		log.info("C301Action edit.........");
	//		String id=request.getParameter("id");
	//		if(id!=null){
	//			TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//			bean1.setId(id);
	//			bean=consultMsgManager.findDataById(bean1);
	//		}else{
	//			SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
	//			request.setAttribute("date", sdf.format(new Date()));
	//		}
	//		return "edit";
	//	}
	//	/**
	//	 * <p>
	//	 * 信息保存
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>新增。</div>
	//	 * <div>修改。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String save() {
	//		log.info("C301Action edit.........");
	//		if(bean!=null){
	//			String msg="1";
	//			try {
	//					BaseUserBean user = (BaseUserBean) request.getSession().getAttribute(CommonConstant.SESSION_KEY_USER_CUSTOMER_SERVICE);
	//					if(user!=null){
	//						bean.setCreate_ip(getIpAddr());
	//						bean.setCreate_id(user.getUser_id());
	//						bean.setUpdate_ip(getIpAddr());
	//						bean.setUpdate_id(user.getUser_id());
	//					}
	//					msg=consultMsgManager.saveOrUpdateData(bean);
	//			} catch (Exception e) {
	//				msg=e.getMessage();
	//			}
	//			request.setAttribute("msg",msg);
	//		}else{
	//			request.setAttribute("msg", "信息保存失败!");
	//		}
	//		return SUCCESS;
	//	}
	//	/**
	//	 * <p>
	//	 * 删除。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>删除。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String del() {
	//		log.info("C301Action del.........");
	//		
	//		String id=request.getParameter("id");
	//		TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//		bean1.setId(id);
	//		String msg="1";
	//		try {
	//			msg=consultMsgManager.deleteDataById(bean1);
	//		} catch (Exception e) {
	//			msg=e.getMessage();
	//		}
	//		request.setAttribute("msg",msg);
	//		return SUCCESS;
	//	}
	
	
	
	/**
	 * BEAN类  咨询信息设定
	 * @param bean BEAN类  咨询信息
	 */
	//	/**
	//	 * <p>
	//	 * 编辑。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>编辑。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String edit() {
	//		log.info("C301Action edit.........");
	//		String id=request.getParameter("id");
	//		if(id!=null){
	//			TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//			bean1.setId(id);
	//			bean=consultMsgManager.findDataById(bean1);
	//		}else{
	//			SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
	//			request.setAttribute("date", sdf.format(new Date()));
	//		}
	//		return "edit";
	//	}
	//	/**
	//	 * <p>
	//	 * 信息保存
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>新增。</div>
	//	 * <div>修改。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String save() {
	//		log.info("C301Action edit.........");
	//		if(bean!=null){
	//			String msg="1";
	//			try {
	//					BaseUserBean user = (BaseUserBean) request.getSession().getAttribute(CommonConstant.SESSION_KEY_USER_CUSTOMER_SERVICE);
	//					if(user!=null){
	//						bean.setCreate_ip(getIpAddr());
	//						bean.setCreate_id(user.getUser_id());
	//						bean.setUpdate_ip(getIpAddr());
	//						bean.setUpdate_id(user.getUser_id());
	//					}
	//					msg=consultMsgManager.saveOrUpdateData(bean);
	//			} catch (Exception e) {
	//				msg=e.getMessage();
	//			}
	//			request.setAttribute("msg",msg);
	//		}else{
	//			request.setAttribute("msg", "信息保存失败!");
	//		}
	//		return SUCCESS;
	//	}
	//	/**
	//	 * <p>
	//	 * 删除。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>删除。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String del() {
	//		log.info("C301Action del.........");
	//		
	//		String id=request.getParameter("id");
	//		TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//		bean1.setId(id);
	//		String msg="1";
	//		try {
	//			msg=consultMsgManager.deleteDataById(bean1);
	//		} catch (Exception e) {
	//			msg=e.getMessage();
	//		}
	//		request.setAttribute("msg",msg);
	//		return SUCCESS;
	//	}
	
	
	
	/**
	 * BEAN类  咨询信息取得
	 * @return BEAN类  咨询信息
	 */
	//	/**
	//	 * <p>
	//	 * 编辑。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>编辑。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String edit() {
	//		log.info("C301Action edit.........");
	//		String id=request.getParameter("id");
	//		if(id!=null){
	//			TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//			bean1.setId(id);
	//			bean=consultMsgManager.findDataById(bean1);
	//		}else{
	//			SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
	//			request.setAttribute("date", sdf.format(new Date()));
	//		}
	//		return "edit";
	//	}
	//	/**
	//	 * <p>
	//	 * 信息保存
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>新增。</div>
	//	 * <div>修改。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String save() {
	//		log.info("C301Action edit.........");
	//		if(bean!=null){
	//			String msg="1";
	//			try {
	//					BaseUserBean user = (BaseUserBean) request.getSession().getAttribute(CommonConstant.SESSION_KEY_USER_CUSTOMER_SERVICE);
	//					if(user!=null){
	//						bean.setCreate_ip(getIpAddr());
	//						bean.setCreate_id(user.getUser_id());
	//						bean.setUpdate_ip(getIpAddr());
	//						bean.setUpdate_id(user.getUser_id());
	//					}
	//					msg=consultMsgManager.saveOrUpdateData(bean);
	//			} catch (Exception e) {
	//				msg=e.getMessage();
	//			}
	//			request.setAttribute("msg",msg);
	//		}else{
	//			request.setAttribute("msg", "信息保存失败!");
	//		}
	//		return SUCCESS;
	//	}
	//	/**
	//	 * <p>
	//	 * 删除。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>删除。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String del() {
	//		log.info("C301Action del.........");
	//		
	//		String id=request.getParameter("id");
	//		TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//		bean1.setId(id);
	//		String msg="1";
	//		try {
	//			msg=consultMsgManager.deleteDataById(bean1);
	//		} catch (Exception e) {
	//			msg=e.getMessage();
	//		}
	//		request.setAttribute("msg",msg);
	//		return SUCCESS;
	//	}
	
	
	
	/**
	 * BEAN类  咨询信息取得
	 * @return BEAN类  咨询信息
	 */
	//	/**
	//	 * <p>
	//	 * 编辑。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>编辑。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String edit() {
	//		log.info("C301Action edit.........");
	//		String id=request.getParameter("id");
	//		if(id!=null){
	//			TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//			bean1.setId(id);
	//			bean=consultMsgManager.findDataById(bean1);
	//		}else{
	//			SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
	//			request.setAttribute("date", sdf.format(new Date()));
	//		}
	//		return "edit";
	//	}
	//	/**
	//	 * <p>
	//	 * 信息保存
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>新增。</div>
	//	 * <div>修改。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String save() {
	//		log.info("C301Action edit.........");
	//		if(bean!=null){
	//			String msg="1";
	//			try {
	//					BaseUserBean user = (BaseUserBean) request.getSession().getAttribute(CommonConstant.SESSION_KEY_USER_CUSTOMER_SERVICE);
	//					if(user!=null){
	//						bean.setCreate_ip(getIpAddr());
	//						bean.setCreate_id(user.getUser_id());
	//						bean.setUpdate_ip(getIpAddr());
	//						bean.setUpdate_id(user.getUser_id());
	//					}
	//					msg=consultMsgManager.saveOrUpdateData(bean);
	//			} catch (Exception e) {
	//				msg=e.getMessage();
	//			}
	//			request.setAttribute("msg",msg);
	//		}else{
	//			request.setAttribute("msg", "信息保存失败!");
	//		}
	//		return SUCCESS;
	//	}
	//	/**
	//	 * <p>
	//	 * 删除。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>删除。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String del() {
	//		log.info("C301Action del.........");
	//		
	//		String id=request.getParameter("id");
	//		TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//		bean1.setId(id);
	//		String msg="1";
	//		try {
	//			msg=consultMsgManager.deleteDataById(bean1);
	//		} catch (Exception e) {
	//			msg=e.getMessage();
	//		}
	//		request.setAttribute("msg",msg);
	//		return SUCCESS;
	//	}
	
	
	
	/**
	 * BEAN类  咨询信息设定
	 * @param bean BEAN类  咨询信息
	 */
	//	/**
	//	 * <p>
	//	 * 编辑。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>编辑。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String edit() {
	//		log.info("C301Action edit.........");
	//		String id=request.getParameter("id");
	//		if(id!=null){
	//			TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//			bean1.setId(id);
	//			bean=consultMsgManager.findDataById(bean1);
	//		}else{
	//			SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
	//			request.setAttribute("date", sdf.format(new Date()));
	//		}
	//		return "edit";
	//	}
	//	/**
	//	 * <p>
	//	 * 信息保存
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>新增。</div>
	//	 * <div>修改。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String save() {
	//		log.info("C301Action edit.........");
	//		if(bean!=null){
	//			String msg="1";
	//			try {
	//					BaseUserBean user = (BaseUserBean) request.getSession().getAttribute(CommonConstant.SESSION_KEY_USER_CUSTOMER_SERVICE);
	//					if(user!=null){
	//						bean.setCreate_ip(getIpAddr());
	//						bean.setCreate_id(user.getUser_id());
	//						bean.setUpdate_ip(getIpAddr());
	//						bean.setUpdate_id(user.getUser_id());
	//					}
	//					msg=consultMsgManager.saveOrUpdateData(bean);
	//			} catch (Exception e) {
	//				msg=e.getMessage();
	//			}
	//			request.setAttribute("msg",msg);
	//		}else{
	//			request.setAttribute("msg", "信息保存失败!");
	//		}
	//		return SUCCESS;
	//	}
	//	/**
	//	 * <p>
	//	 * 删除。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>删除。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String del() {
	//		log.info("C301Action del.........");
	//		
	//		String id=request.getParameter("id");
	//		TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//		bean1.setId(id);
	//		String msg="1";
	//		try {
	//			msg=consultMsgManager.deleteDataById(bean1);
	//		} catch (Exception e) {
	//			msg=e.getMessage();
	//		}
	//		request.setAttribute("msg",msg);
	//		return SUCCESS;
	//	}
	
	
	
	/**
	 * BEAN类  咨询信息取得
	 * @return BEAN类  咨询信息
	 */
	//	/**
	//	 * <p>
	//	 * 编辑。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>编辑。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String edit() {
	//		log.info("C301Action edit.........");
	//		String id=request.getParameter("id");
	//		if(id!=null){
	//			TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//			bean1.setId(id);
	//			bean=consultMsgManager.findDataById(bean1);
	//		}else{
	//			SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
	//			request.setAttribute("date", sdf.format(new Date()));
	//		}
	//		return "edit";
	//	}
	//	/**
	//	 * <p>
	//	 * 信息保存
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>新增。</div>
	//	 * <div>修改。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String save() {
	//		log.info("C301Action edit.........");
	//		if(bean!=null){
	//			String msg="1";
	//			try {
	//					BaseUserBean user = (BaseUserBean) request.getSession().getAttribute(CommonConstant.SESSION_KEY_USER_CUSTOMER_SERVICE);
	//					if(user!=null){
	//						bean.setCreate_ip(getIpAddr());
	//						bean.setCreate_id(user.getUser_id());
	//						bean.setUpdate_ip(getIpAddr());
	//						bean.setUpdate_id(user.getUser_id());
	//					}
	//					msg=consultMsgManager.saveOrUpdateData(bean);
	//			} catch (Exception e) {
	//				msg=e.getMessage();
	//			}
	//			request.setAttribute("msg",msg);
	//		}else{
	//			request.setAttribute("msg", "信息保存失败!");
	//		}
	//		return SUCCESS;
	//	}
	//	/**
	//	 * <p>
	//	 * 删除。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>删除。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String del() {
	//		log.info("C301Action del.........");
	//		
	//		String id=request.getParameter("id");
	//		TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//		bean1.setId(id);
	//		String msg="1";
	//		try {
	//			msg=consultMsgManager.deleteDataById(bean1);
	//		} catch (Exception e) {
	//			msg=e.getMessage();
	//		}
	//		request.setAttribute("msg",msg);
	//		return SUCCESS;
	//	}
	
	
	
	/**
	 * BEAN类  咨询信息取得
	 * @return BEAN类  咨询信息
	 */
	//	/**
	//	 * <p>
	//	 * 编辑。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>编辑。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String edit() {
	//		log.info("C301Action edit.........");
	//		String id=request.getParameter("id");
	//		if(id!=null){
	//			TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//			bean1.setId(id);
	//			bean=consultMsgManager.findDataById(bean1);
	//		}else{
	//			SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
	//			request.setAttribute("date", sdf.format(new Date()));
	//		}
	//		return "edit";
	//	}
	//	/**
	//	 * <p>
	//	 * 信息保存
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>新增。</div>
	//	 * <div>修改。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String save() {
	//		log.info("C301Action edit.........");
	//		if(bean!=null){
	//			String msg="1";
	//			try {
	//					BaseUserBean user = (BaseUserBean) request.getSession().getAttribute(CommonConstant.SESSION_KEY_USER_CUSTOMER_SERVICE);
	//					if(user!=null){
	//						bean.setCreate_ip(getIpAddr());
	//						bean.setCreate_id(user.getUser_id());
	//						bean.setUpdate_ip(getIpAddr());
	//						bean.setUpdate_id(user.getUser_id());
	//					}
	//					msg=consultMsgManager.saveOrUpdateData(bean);
	//			} catch (Exception e) {
	//				msg=e.getMessage();
	//			}
	//			request.setAttribute("msg",msg);
	//		}else{
	//			request.setAttribute("msg", "信息保存失败!");
	//		}
	//		return SUCCESS;
	//	}
	//	/**
	//	 * <p>
	//	 * 删除。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>删除。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String del() {
	//		log.info("C301Action del.........");
	//		
	//		String id=request.getParameter("id");
	//		TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//		bean1.setId(id);
	//		String msg="1";
	//		try {
	//			msg=consultMsgManager.deleteDataById(bean1);
	//		} catch (Exception e) {
	//			msg=e.getMessage();
	//		}
	//		request.setAttribute("msg",msg);
	//		return SUCCESS;
	//	}
	
	
	
	/**
	 * BEAN类  咨询信息设定
	 * @param bean BEAN类  咨询信息
	 */
	//	/**
	//	 * <p>
	//	 * 编辑。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>编辑。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String edit() {
	//		log.info("C301Action edit.........");
	//		String id=request.getParameter("id");
	//		if(id!=null){
	//			TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//			bean1.setId(id);
	//			bean=consultMsgManager.findDataById(bean1);
	//		}else{
	//			SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
	//			request.setAttribute("date", sdf.format(new Date()));
	//		}
	//		return "edit";
	//	}
	//	/**
	//	 * <p>
	//	 * 信息保存
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>新增。</div>
	//	 * <div>修改。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String save() {
	//		log.info("C301Action edit.........");
	//		if(bean!=null){
	//			String msg="1";
	//			try {
	//					BaseUserBean user = (BaseUserBean) request.getSession().getAttribute(CommonConstant.SESSION_KEY_USER_CUSTOMER_SERVICE);
	//					if(user!=null){
	//						bean.setCreate_ip(getIpAddr());
	//						bean.setCreate_id(user.getUser_id());
	//						bean.setUpdate_ip(getIpAddr());
	//						bean.setUpdate_id(user.getUser_id());
	//					}
	//					msg=consultMsgManager.saveOrUpdateData(bean);
	//			} catch (Exception e) {
	//				msg=e.getMessage();
	//			}
	//			request.setAttribute("msg",msg);
	//		}else{
	//			request.setAttribute("msg", "信息保存失败!");
	//		}
	//		return SUCCESS;
	//	}
	//	/**
	//	 * <p>
	//	 * 删除。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>删除。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String del() {
	//		log.info("C301Action del.........");
	//		
	//		String id=request.getParameter("id");
	//		TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//		bean1.setId(id);
	//		String msg="1";
	//		try {
	//			msg=consultMsgManager.deleteDataById(bean1);
	//		} catch (Exception e) {
	//			msg=e.getMessage();
	//		}
	//		request.setAttribute("msg",msg);
	//		return SUCCESS;
	//	}
	
	
	
	/**
	 * BEAN类  咨询信息取得
	 * @return BEAN类  咨询信息
	 */
	//	/**
	//	 * <p>
	//	 * 编辑。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>编辑。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String edit() {
	//		log.info("C301Action edit.........");
	//		String id=request.getParameter("id");
	//		if(id!=null){
	//			TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//			bean1.setId(id);
	//			bean=consultMsgManager.findDataById(bean1);
	//		}else{
	//			SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
	//			request.setAttribute("date", sdf.format(new Date()));
	//		}
	//		return "edit";
	//	}
	//	/**
	//	 * <p>
	//	 * 信息保存
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>新增。</div>
	//	 * <div>修改。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String save() {
	//		log.info("C301Action edit.........");
	//		if(bean!=null){
	//			String msg="1";
	//			try {
	//					BaseUserBean user = (BaseUserBean) request.getSession().getAttribute(CommonConstant.SESSION_KEY_USER_CUSTOMER_SERVICE);
	//					if(user!=null){
	//						bean.setCreate_ip(getIpAddr());
	//						bean.setCreate_id(user.getUser_id());
	//						bean.setUpdate_ip(getIpAddr());
	//						bean.setUpdate_id(user.getUser_id());
	//					}
	//					msg=consultMsgManager.saveOrUpdateData(bean);
	//			} catch (Exception e) {
	//				msg=e.getMessage();
	//			}
	//			request.setAttribute("msg",msg);
	//		}else{
	//			request.setAttribute("msg", "信息保存失败!");
	//		}
	//		return SUCCESS;
	//	}
	//	/**
	//	 * <p>
	//	 * 删除。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>删除。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String del() {
	//		log.info("C301Action del.........");
	//		
	//		String id=request.getParameter("id");
	//		TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//		bean1.setId(id);
	//		String msg="1";
	//		try {
	//			msg=consultMsgManager.deleteDataById(bean1);
	//		} catch (Exception e) {
	//			msg=e.getMessage();
	//		}
	//		request.setAttribute("msg",msg);
	//		return SUCCESS;
	//	}
	
	
	
	/**
	 * BEAN类  咨询信息设定
	 * @param bean BEAN类  咨询信息
	 */
	//	/**
	//	 * <p>
	//	 * 编辑。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>编辑。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String edit() {
	//		log.info("C301Action edit.........");
	//		String id=request.getParameter("id");
	//		if(id!=null){
	//			TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//			bean1.setId(id);
	//			bean=consultMsgManager.findDataById(bean1);
	//		}else{
	//			SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
	//			request.setAttribute("date", sdf.format(new Date()));
	//		}
	//		return "edit";
	//	}
	//	/**
	//	 * <p>
	//	 * 信息保存
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>新增。</div>
	//	 * <div>修改。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String save() {
	//		log.info("C301Action edit.........");
	//		if(bean!=null){
	//			String msg="1";
	//			try {
	//					BaseUserBean user = (BaseUserBean) request.getSession().getAttribute(CommonConstant.SESSION_KEY_USER_CUSTOMER_SERVICE);
	//					if(user!=null){
	//						bean.setCreate_ip(getIpAddr());
	//						bean.setCreate_id(user.getUser_id());
	//						bean.setUpdate_ip(getIpAddr());
	//						bean.setUpdate_id(user.getUser_id());
	//					}
	//					msg=consultMsgManager.saveOrUpdateData(bean);
	//			} catch (Exception e) {
	//				msg=e.getMessage();
	//			}
	//			request.setAttribute("msg",msg);
	//		}else{
	//			request.setAttribute("msg", "信息保存失败!");
	//		}
	//		return SUCCESS;
	//	}
	//	/**
	//	 * <p>
	//	 * 删除。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>删除。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String del() {
	//		log.info("C301Action del.........");
	//		
	//		String id=request.getParameter("id");
	//		TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//		bean1.setId(id);
	//		String msg="1";
	//		try {
	//			msg=consultMsgManager.deleteDataById(bean1);
	//		} catch (Exception e) {
	//			msg=e.getMessage();
	//		}
	//		request.setAttribute("msg",msg);
	//		return SUCCESS;
	//	}
	
	
	
	/**
	 * BEAN类  咨询信息取得
	 * @return BEAN类  咨询信息
	 */
	//	/**
	//	 * <p>
	//	 * 编辑。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>编辑。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String edit() {
	//		log.info("C301Action edit.........");
	//		String id=request.getParameter("id");
	//		if(id!=null){
	//			TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//			bean1.setId(id);
	//			bean=consultMsgManager.findDataById(bean1);
	//		}else{
	//			SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
	//			request.setAttribute("date", sdf.format(new Date()));
	//		}
	//		return "edit";
	//	}
	//	/**
	//	 * <p>
	//	 * 信息保存
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>新增。</div>
	//	 * <div>修改。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String save() {
	//		log.info("C301Action edit.........");
	//		if(bean!=null){
	//			String msg="1";
	//			try {
	//					BaseUserBean user = (BaseUserBean) request.getSession().getAttribute(CommonConstant.SESSION_KEY_USER_CUSTOMER_SERVICE);
	//					if(user!=null){
	//						bean.setCreate_ip(getIpAddr());
	//						bean.setCreate_id(user.getUser_id());
	//						bean.setUpdate_ip(getIpAddr());
	//						bean.setUpdate_id(user.getUser_id());
	//					}
	//					msg=consultMsgManager.saveOrUpdateData(bean);
	//			} catch (Exception e) {
	//				msg=e.getMessage();
	//			}
	//			request.setAttribute("msg",msg);
	//		}else{
	//			request.setAttribute("msg", "信息保存失败!");
	//		}
	//		return SUCCESS;
	//	}
	//	/**
	//	 * <p>
	//	 * 删除。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>删除。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String del() {
	//		log.info("C301Action del.........");
	//		
	//		String id=request.getParameter("id");
	//		TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//		bean1.setId(id);
	//		String msg="1";
	//		try {
	//			msg=consultMsgManager.deleteDataById(bean1);
	//		} catch (Exception e) {
	//			msg=e.getMessage();
	//		}
	//		request.setAttribute("msg",msg);
	//		return SUCCESS;
	//	}
	
	
	
	/**
	 * BEAN类  咨询信息取得
	 * @return BEAN类  咨询信息
	 */
	//	/**
	//	 * <p>
	//	 * 编辑。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>编辑。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String edit() {
	//		log.info("C301Action edit.........");
	//		String id=request.getParameter("id");
	//		if(id!=null){
	//			TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//			bean1.setId(id);
	//			bean=consultMsgManager.findDataById(bean1);
	//		}else{
	//			SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
	//			request.setAttribute("date", sdf.format(new Date()));
	//		}
	//		return "edit";
	//	}
	//	/**
	//	 * <p>
	//	 * 信息保存
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>新增。</div>
	//	 * <div>修改。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String save() {
	//		log.info("C301Action edit.........");
	//		if(bean!=null){
	//			String msg="1";
	//			try {
	//					BaseUserBean user = (BaseUserBean) request.getSession().getAttribute(CommonConstant.SESSION_KEY_USER_CUSTOMER_SERVICE);
	//					if(user!=null){
	//						bean.setCreate_ip(getIpAddr());
	//						bean.setCreate_id(user.getUser_id());
	//						bean.setUpdate_ip(getIpAddr());
	//						bean.setUpdate_id(user.getUser_id());
	//					}
	//					msg=consultMsgManager.saveOrUpdateData(bean);
	//			} catch (Exception e) {
	//				msg=e.getMessage();
	//			}
	//			request.setAttribute("msg",msg);
	//		}else{
	//			request.setAttribute("msg", "信息保存失败!");
	//		}
	//		return SUCCESS;
	//	}
	//	/**
	//	 * <p>
	//	 * 删除。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>删除。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String del() {
	//		log.info("C301Action del.........");
	//		
	//		String id=request.getParameter("id");
	//		TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//		bean1.setId(id);
	//		String msg="1";
	//		try {
	//			msg=consultMsgManager.deleteDataById(bean1);
	//		} catch (Exception e) {
	//			msg=e.getMessage();
	//		}
	//		request.setAttribute("msg",msg);
	//		return SUCCESS;
	//	}
	
	
	
	/**
	 * BEAN类  咨询信息设定
	 * @param bean BEAN类  咨询信息
	 */
	//	/**
	//	 * <p>
	//	 * 编辑。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>编辑。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String edit() {
	//		log.info("C301Action edit.........");
	//		String id=request.getParameter("id");
	//		if(id!=null){
	//			TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//			bean1.setId(id);
	//			bean=consultMsgManager.findDataById(bean1);
	//		}else{
	//			SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
	//			request.setAttribute("date", sdf.format(new Date()));
	//		}
	//		return "edit";
	//	}
	//	/**
	//	 * <p>
	//	 * 信息保存
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>新增。</div>
	//	 * <div>修改。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String save() {
	//		log.info("C301Action edit.........");
	//		if(bean!=null){
	//			String msg="1";
	//			try {
	//					BaseUserBean user = (BaseUserBean) request.getSession().getAttribute(CommonConstant.SESSION_KEY_USER_CUSTOMER_SERVICE);
	//					if(user!=null){
	//						bean.setCreate_ip(getIpAddr());
	//						bean.setCreate_id(user.getUser_id());
	//						bean.setUpdate_ip(getIpAddr());
	//						bean.setUpdate_id(user.getUser_id());
	//					}
	//					msg=consultMsgManager.saveOrUpdateData(bean);
	//			} catch (Exception e) {
	//				msg=e.getMessage();
	//			}
	//			request.setAttribute("msg",msg);
	//		}else{
	//			request.setAttribute("msg", "信息保存失败!");
	//		}
	//		return SUCCESS;
	//	}
	//	/**
	//	 * <p>
	//	 * 删除。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>删除。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String del() {
	//		log.info("C301Action del.........");
	//		
	//		String id=request.getParameter("id");
	//		TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//		bean1.setId(id);
	//		String msg="1";
	//		try {
	//			msg=consultMsgManager.deleteDataById(bean1);
	//		} catch (Exception e) {
	//			msg=e.getMessage();
	//		}
	//		request.setAttribute("msg",msg);
	//		return SUCCESS;
	//	}
	
	
	
	/**
	 * BEAN类  咨询信息取得
	 * @return BEAN类  咨询信息
	 */
	//	/**
	//	 * <p>
	//	 * 编辑。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>编辑。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String edit() {
	//		log.info("C301Action edit.........");
	//		String id=request.getParameter("id");
	//		if(id!=null){
	//			TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//			bean1.setId(id);
	//			bean=consultMsgManager.findDataById(bean1);
	//		}else{
	//			SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
	//			request.setAttribute("date", sdf.format(new Date()));
	//		}
	//		return "edit";
	//	}
	//	/**
	//	 * <p>
	//	 * 信息保存
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>新增。</div>
	//	 * <div>修改。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String save() {
	//		log.info("C301Action edit.........");
	//		if(bean!=null){
	//			String msg="1";
	//			try {
	//					BaseUserBean user = (BaseUserBean) request.getSession().getAttribute(CommonConstant.SESSION_KEY_USER_CUSTOMER_SERVICE);
	//					if(user!=null){
	//						bean.setCreate_ip(getIpAddr());
	//						bean.setCreate_id(user.getUser_id());
	//						bean.setUpdate_ip(getIpAddr());
	//						bean.setUpdate_id(user.getUser_id());
	//					}
	//					msg=consultMsgManager.saveOrUpdateData(bean);
	//			} catch (Exception e) {
	//				msg=e.getMessage();
	//			}
	//			request.setAttribute("msg",msg);
	//		}else{
	//			request.setAttribute("msg", "信息保存失败!");
	//		}
	//		return SUCCESS;
	//	}
	//	/**
	//	 * <p>
	//	 * 删除。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>删除。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String del() {
	//		log.info("C301Action del.........");
	//		
	//		String id=request.getParameter("id");
	//		TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//		bean1.setId(id);
	//		String msg="1";
	//		try {
	//			msg=consultMsgManager.deleteDataById(bean1);
	//		} catch (Exception e) {
	//			msg=e.getMessage();
	//		}
	//		request.setAttribute("msg",msg);
	//		return SUCCESS;
	//	}
	
	
	
	/**
	 * BEAN类  咨询信息设定
	 * @param bean BEAN类  咨询信息
	 */
	//	/**
	//	 * <p>
	//	 * 编辑。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>编辑。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String edit() {
	//		log.info("C301Action edit.........");
	//		String id=request.getParameter("id");
	//		if(id!=null){
	//			TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//			bean1.setId(id);
	//			bean=consultMsgManager.findDataById(bean1);
	//		}else{
	//			SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
	//			request.setAttribute("date", sdf.format(new Date()));
	//		}
	//		return "edit";
	//	}
	//	/**
	//	 * <p>
	//	 * 信息保存
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>新增。</div>
	//	 * <div>修改。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String save() {
	//		log.info("C301Action edit.........");
	//		if(bean!=null){
	//			String msg="1";
	//			try {
	//					BaseUserBean user = (BaseUserBean) request.getSession().getAttribute(CommonConstant.SESSION_KEY_USER_CUSTOMER_SERVICE);
	//					if(user!=null){
	//						bean.setCreate_ip(getIpAddr());
	//						bean.setCreate_id(user.getUser_id());
	//						bean.setUpdate_ip(getIpAddr());
	//						bean.setUpdate_id(user.getUser_id());
	//					}
	//					msg=consultMsgManager.saveOrUpdateData(bean);
	//			} catch (Exception e) {
	//				msg=e.getMessage();
	//			}
	//			request.setAttribute("msg",msg);
	//		}else{
	//			request.setAttribute("msg", "信息保存失败!");
	//		}
	//		return SUCCESS;
	//	}
	//	/**
	//	 * <p>
	//	 * 删除。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>删除。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String del() {
	//		log.info("C301Action del.........");
	//		
	//		String id=request.getParameter("id");
	//		TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//		bean1.setId(id);
	//		String msg="1";
	//		try {
	//			msg=consultMsgManager.deleteDataById(bean1);
	//		} catch (Exception e) {
	//			msg=e.getMessage();
	//		}
	//		request.setAttribute("msg",msg);
	//		return SUCCESS;
	//	}
	
	
	
	/**
	 * BEAN类  咨询信息取得
	 * @return BEAN类  咨询信息
	 */
	//	/**
	//	 * <p>
	//	 * 编辑。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>编辑。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String edit() {
	//		log.info("C301Action edit.........");
	//		String id=request.getParameter("id");
	//		if(id!=null){
	//			TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//			bean1.setId(id);
	//			bean=consultMsgManager.findDataById(bean1);
	//		}else{
	//			SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
	//			request.setAttribute("date", sdf.format(new Date()));
	//		}
	//		return "edit";
	//	}
	//	/**
	//	 * <p>
	//	 * 信息保存
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>新增。</div>
	//	 * <div>修改。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String save() {
	//		log.info("C301Action edit.........");
	//		if(bean!=null){
	//			String msg="1";
	//			try {
	//					BaseUserBean user = (BaseUserBean) request.getSession().getAttribute(CommonConstant.SESSION_KEY_USER_CUSTOMER_SERVICE);
	//					if(user!=null){
	//						bean.setCreate_ip(getIpAddr());
	//						bean.setCreate_id(user.getUser_id());
	//						bean.setUpdate_ip(getIpAddr());
	//						bean.setUpdate_id(user.getUser_id());
	//					}
	//					msg=consultMsgManager.saveOrUpdateData(bean);
	//			} catch (Exception e) {
	//				msg=e.getMessage();
	//			}
	//			request.setAttribute("msg",msg);
	//		}else{
	//			request.setAttribute("msg", "信息保存失败!");
	//		}
	//		return SUCCESS;
	//	}
	//	/**
	//	 * <p>
	//	 * 删除。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>删除。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String del() {
	//		log.info("C301Action del.........");
	//		
	//		String id=request.getParameter("id");
	//		TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//		bean1.setId(id);
	//		String msg="1";
	//		try {
	//			msg=consultMsgManager.deleteDataById(bean1);
	//		} catch (Exception e) {
	//			msg=e.getMessage();
	//		}
	//		request.setAttribute("msg",msg);
	//		return SUCCESS;
	//	}
	
	
	
	/**
	 * BEAN类  咨询信息取得
	 * @return BEAN类  咨询信息
	 */
	//	/**
	//	 * <p>
	//	 * 编辑。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>编辑。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String edit() {
	//		log.info("C301Action edit.........");
	//		String id=request.getParameter("id");
	//		if(id!=null){
	//			TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//			bean1.setId(id);
	//			bean=consultMsgManager.findDataById(bean1);
	//		}else{
	//			SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
	//			request.setAttribute("date", sdf.format(new Date()));
	//		}
	//		return "edit";
	//	}
	//	/**
	//	 * <p>
	//	 * 信息保存
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>新增。</div>
	//	 * <div>修改。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String save() {
	//		log.info("C301Action edit.........");
	//		if(bean!=null){
	//			String msg="1";
	//			try {
	//					BaseUserBean user = (BaseUserBean) request.getSession().getAttribute(CommonConstant.SESSION_KEY_USER_CUSTOMER_SERVICE);
	//					if(user!=null){
	//						bean.setCreate_ip(getIpAddr());
	//						bean.setCreate_id(user.getUser_id());
	//						bean.setUpdate_ip(getIpAddr());
	//						bean.setUpdate_id(user.getUser_id());
	//					}
	//					msg=consultMsgManager.saveOrUpdateData(bean);
	//			} catch (Exception e) {
	//				msg=e.getMessage();
	//			}
	//			request.setAttribute("msg",msg);
	//		}else{
	//			request.setAttribute("msg", "信息保存失败!");
	//		}
	//		return SUCCESS;
	//	}
	//	/**
	//	 * <p>
	//	 * 删除。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>删除。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String del() {
	//		log.info("C301Action del.........");
	//		
	//		String id=request.getParameter("id");
	//		TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//		bean1.setId(id);
	//		String msg="1";
	//		try {
	//			msg=consultMsgManager.deleteDataById(bean1);
	//		} catch (Exception e) {
	//			msg=e.getMessage();
	//		}
	//		request.setAttribute("msg",msg);
	//		return SUCCESS;
	//	}
	
	
	
	/**
	 * BEAN类  咨询信息设定
	 * @param bean BEAN类  咨询信息
	 */
	//	/**
	//	 * <p>
	//	 * 编辑。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>编辑。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String edit() {
	//		log.info("C301Action edit.........");
	//		String id=request.getParameter("id");
	//		if(id!=null){
	//			TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//			bean1.setId(id);
	//			bean=consultMsgManager.findDataById(bean1);
	//		}else{
	//			SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
	//			request.setAttribute("date", sdf.format(new Date()));
	//		}
	//		return "edit";
	//	}
	//	/**
	//	 * <p>
	//	 * 信息保存
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>新增。</div>
	//	 * <div>修改。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String save() {
	//		log.info("C301Action edit.........");
	//		if(bean!=null){
	//			String msg="1";
	//			try {
	//					BaseUserBean user = (BaseUserBean) request.getSession().getAttribute(CommonConstant.SESSION_KEY_USER_CUSTOMER_SERVICE);
	//					if(user!=null){
	//						bean.setCreate_ip(getIpAddr());
	//						bean.setCreate_id(user.getUser_id());
	//						bean.setUpdate_ip(getIpAddr());
	//						bean.setUpdate_id(user.getUser_id());
	//					}
	//					msg=consultMsgManager.saveOrUpdateData(bean);
	//			} catch (Exception e) {
	//				msg=e.getMessage();
	//			}
	//			request.setAttribute("msg",msg);
	//		}else{
	//			request.setAttribute("msg", "信息保存失败!");
	//		}
	//		return SUCCESS;
	//	}
	//	/**
	//	 * <p>
	//	 * 删除。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>删除。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String del() {
	//		log.info("C301Action del.........");
	//		
	//		String id=request.getParameter("id");
	//		TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//		bean1.setId(id);
	//		String msg="1";
	//		try {
	//			msg=consultMsgManager.deleteDataById(bean1);
	//		} catch (Exception e) {
	//			msg=e.getMessage();
	//		}
	//		request.setAttribute("msg",msg);
	//		return SUCCESS;
	//	}
	
	
	
	/**
	 * BEAN类  咨询信息取得
	 * @return BEAN类  咨询信息
	 */
	//	/**
	//	 * <p>
	//	 * 编辑。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>编辑。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String edit() {
	//		log.info("C301Action edit.........");
	//		String id=request.getParameter("id");
	//		if(id!=null){
	//			TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//			bean1.setId(id);
	//			bean=consultMsgManager.findDataById(bean1);
	//		}else{
	//			SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
	//			request.setAttribute("date", sdf.format(new Date()));
	//		}
	//		return "edit";
	//	}
	//	/**
	//	 * <p>
	//	 * 信息保存
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>新增。</div>
	//	 * <div>修改。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String save() {
	//		log.info("C301Action edit.........");
	//		if(bean!=null){
	//			String msg="1";
	//			try {
	//					BaseUserBean user = (BaseUserBean) request.getSession().getAttribute(CommonConstant.SESSION_KEY_USER_CUSTOMER_SERVICE);
	//					if(user!=null){
	//						bean.setCreate_ip(getIpAddr());
	//						bean.setCreate_id(user.getUser_id());
	//						bean.setUpdate_ip(getIpAddr());
	//						bean.setUpdate_id(user.getUser_id());
	//					}
	//					msg=consultMsgManager.saveOrUpdateData(bean);
	//			} catch (Exception e) {
	//				msg=e.getMessage();
	//			}
	//			request.setAttribute("msg",msg);
	//		}else{
	//			request.setAttribute("msg", "信息保存失败!");
	//		}
	//		return SUCCESS;
	//	}
	//	/**
	//	 * <p>
	//	 * 删除。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>删除。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String del() {
	//		log.info("C301Action del.........");
	//		
	//		String id=request.getParameter("id");
	//		TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//		bean1.setId(id);
	//		String msg="1";
	//		try {
	//			msg=consultMsgManager.deleteDataById(bean1);
	//		} catch (Exception e) {
	//			msg=e.getMessage();
	//		}
	//		request.setAttribute("msg",msg);
	//		return SUCCESS;
	//	}
	
	
	
	/**
	 * BEAN类  咨询信息取得
	 * @return BEAN类  咨询信息
	 */
	//	/**
	//	 * <p>
	//	 * 编辑。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>编辑。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String edit() {
	//		log.info("C301Action edit.........");
	//		String id=request.getParameter("id");
	//		if(id!=null){
	//			TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//			bean1.setId(id);
	//			bean=consultMsgManager.findDataById(bean1);
	//		}else{
	//			SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
	//			request.setAttribute("date", sdf.format(new Date()));
	//		}
	//		return "edit";
	//	}
	//	/**
	//	 * <p>
	//	 * 信息保存
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>新增。</div>
	//	 * <div>修改。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String save() {
	//		log.info("C301Action edit.........");
	//		if(bean!=null){
	//			String msg="1";
	//			try {
	//					BaseUserBean user = (BaseUserBean) request.getSession().getAttribute(CommonConstant.SESSION_KEY_USER_CUSTOMER_SERVICE);
	//					if(user!=null){
	//						bean.setCreate_ip(getIpAddr());
	//						bean.setCreate_id(user.getUser_id());
	//						bean.setUpdate_ip(getIpAddr());
	//						bean.setUpdate_id(user.getUser_id());
	//					}
	//					msg=consultMsgManager.saveOrUpdateData(bean);
	//			} catch (Exception e) {
	//				msg=e.getMessage();
	//			}
	//			request.setAttribute("msg",msg);
	//		}else{
	//			request.setAttribute("msg", "信息保存失败!");
	//		}
	//		return SUCCESS;
	//	}
	//	/**
	//	 * <p>
	//	 * 删除。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>删除。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String del() {
	//		log.info("C301Action del.........");
	//		
	//		String id=request.getParameter("id");
	//		TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//		bean1.setId(id);
	//		String msg="1";
	//		try {
	//			msg=consultMsgManager.deleteDataById(bean1);
	//		} catch (Exception e) {
	//			msg=e.getMessage();
	//		}
	//		request.setAttribute("msg",msg);
	//		return SUCCESS;
	//	}
	
	
	
	/**
	 * BEAN类  咨询信息设定
	 * @param bean BEAN类  咨询信息
	 */
	//	/**
	//	 * <p>
	//	 * 编辑。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>编辑。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String edit() {
	//		log.info("C301Action edit.........");
	//		String id=request.getParameter("id");
	//		if(id!=null){
	//			TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//			bean1.setId(id);
	//			bean=consultMsgManager.findDataById(bean1);
	//		}else{
	//			SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
	//			request.setAttribute("date", sdf.format(new Date()));
	//		}
	//		return "edit";
	//	}
	//	/**
	//	 * <p>
	//	 * 信息保存
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>新增。</div>
	//	 * <div>修改。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String save() {
	//		log.info("C301Action edit.........");
	//		if(bean!=null){
	//			String msg="1";
	//			try {
	//					BaseUserBean user = (BaseUserBean) request.getSession().getAttribute(CommonConstant.SESSION_KEY_USER_CUSTOMER_SERVICE);
	//					if(user!=null){
	//						bean.setCreate_ip(getIpAddr());
	//						bean.setCreate_id(user.getUser_id());
	//						bean.setUpdate_ip(getIpAddr());
	//						bean.setUpdate_id(user.getUser_id());
	//					}
	//					msg=consultMsgManager.saveOrUpdateData(bean);
	//			} catch (Exception e) {
	//				msg=e.getMessage();
	//			}
	//			request.setAttribute("msg",msg);
	//		}else{
	//			request.setAttribute("msg", "信息保存失败!");
	//		}
	//		return SUCCESS;
	//	}
	//	/**
	//	 * <p>
	//	 * 删除。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>删除。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String del() {
	//		log.info("C301Action del.........");
	//		
	//		String id=request.getParameter("id");
	//		TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//		bean1.setId(id);
	//		String msg="1";
	//		try {
	//			msg=consultMsgManager.deleteDataById(bean1);
	//		} catch (Exception e) {
	//			msg=e.getMessage();
	//		}
	//		request.setAttribute("msg",msg);
	//		return SUCCESS;
	//	}
	
	
	
	/**
	 * BEAN类  咨询信息取得
	 * @return BEAN类  咨询信息
	 */
	//	/**
	//	 * <p>
	//	 * 编辑。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>编辑。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String edit() {
	//		log.info("C301Action edit.........");
	//		String id=request.getParameter("id");
	//		if(id!=null){
	//			TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//			bean1.setId(id);
	//			bean=consultMsgManager.findDataById(bean1);
	//		}else{
	//			SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
	//			request.setAttribute("date", sdf.format(new Date()));
	//		}
	//		return "edit";
	//	}
	//	/**
	//	 * <p>
	//	 * 信息保存
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>新增。</div>
	//	 * <div>修改。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String save() {
	//		log.info("C301Action edit.........");
	//		if(bean!=null){
	//			String msg="1";
	//			try {
	//					BaseUserBean user = (BaseUserBean) request.getSession().getAttribute(CommonConstant.SESSION_KEY_USER_CUSTOMER_SERVICE);
	//					if(user!=null){
	//						bean.setCreate_ip(getIpAddr());
	//						bean.setCreate_id(user.getUser_id());
	//						bean.setUpdate_ip(getIpAddr());
	//						bean.setUpdate_id(user.getUser_id());
	//					}
	//					msg=consultMsgManager.saveOrUpdateData(bean);
	//			} catch (Exception e) {
	//				msg=e.getMessage();
	//			}
	//			request.setAttribute("msg",msg);
	//		}else{
	//			request.setAttribute("msg", "信息保存失败!");
	//		}
	//		return SUCCESS;
	//	}
	//	/**
	//	 * <p>
	//	 * 删除。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>删除。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String del() {
	//		log.info("C301Action del.........");
	//		
	//		String id=request.getParameter("id");
	//		TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//		bean1.setId(id);
	//		String msg="1";
	//		try {
	//			msg=consultMsgManager.deleteDataById(bean1);
	//		} catch (Exception e) {
	//			msg=e.getMessage();
	//		}
	//		request.setAttribute("msg",msg);
	//		return SUCCESS;
	//	}
	
	
	
	/**
	 * BEAN类  咨询信息设定
	 * @param bean BEAN类  咨询信息
	 */
	//	/**
	//	 * <p>
	//	 * 编辑。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>编辑。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String edit() {
	//		log.info("C301Action edit.........");
	//		String id=request.getParameter("id");
	//		if(id!=null){
	//			TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//			bean1.setId(id);
	//			bean=consultMsgManager.findDataById(bean1);
	//		}else{
	//			SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
	//			request.setAttribute("date", sdf.format(new Date()));
	//		}
	//		return "edit";
	//	}
	//	/**
	//	 * <p>
	//	 * 信息保存
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>新增。</div>
	//	 * <div>修改。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String save() {
	//		log.info("C301Action edit.........");
	//		if(bean!=null){
	//			String msg="1";
	//			try {
	//					BaseUserBean user = (BaseUserBean) request.getSession().getAttribute(CommonConstant.SESSION_KEY_USER_CUSTOMER_SERVICE);
	//					if(user!=null){
	//						bean.setCreate_ip(getIpAddr());
	//						bean.setCreate_id(user.getUser_id());
	//						bean.setUpdate_ip(getIpAddr());
	//						bean.setUpdate_id(user.getUser_id());
	//					}
	//					msg=consultMsgManager.saveOrUpdateData(bean);
	//			} catch (Exception e) {
	//				msg=e.getMessage();
	//			}
	//			request.setAttribute("msg",msg);
	//		}else{
	//			request.setAttribute("msg", "信息保存失败!");
	//		}
	//		return SUCCESS;
	//	}
	//	/**
	//	 * <p>
	//	 * 删除。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>删除。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String del() {
	//		log.info("C301Action del.........");
	//		
	//		String id=request.getParameter("id");
	//		TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//		bean1.setId(id);
	//		String msg="1";
	//		try {
	//			msg=consultMsgManager.deleteDataById(bean1);
	//		} catch (Exception e) {
	//			msg=e.getMessage();
	//		}
	//		request.setAttribute("msg",msg);
	//		return SUCCESS;
	//	}
	
	
	
	/**
	 * BEAN类  咨询信息取得
	 * @return BEAN类  咨询信息
	 */
	//	/**
	//	 * <p>
	//	 * 编辑。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>编辑。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String edit() {
	//		log.info("C301Action edit.........");
	//		String id=request.getParameter("id");
	//		if(id!=null){
	//			TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//			bean1.setId(id);
	//			bean=consultMsgManager.findDataById(bean1);
	//		}else{
	//			SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
	//			request.setAttribute("date", sdf.format(new Date()));
	//		}
	//		return "edit";
	//	}
	//	/**
	//	 * <p>
	//	 * 信息保存
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>新增。</div>
	//	 * <div>修改。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String save() {
	//		log.info("C301Action edit.........");
	//		if(bean!=null){
	//			String msg="1";
	//			try {
	//					BaseUserBean user = (BaseUserBean) request.getSession().getAttribute(CommonConstant.SESSION_KEY_USER_CUSTOMER_SERVICE);
	//					if(user!=null){
	//						bean.setCreate_ip(getIpAddr());
	//						bean.setCreate_id(user.getUser_id());
	//						bean.setUpdate_ip(getIpAddr());
	//						bean.setUpdate_id(user.getUser_id());
	//					}
	//					msg=consultMsgManager.saveOrUpdateData(bean);
	//			} catch (Exception e) {
	//				msg=e.getMessage();
	//			}
	//			request.setAttribute("msg",msg);
	//		}else{
	//			request.setAttribute("msg", "信息保存失败!");
	//		}
	//		return SUCCESS;
	//	}
	//	/**
	//	 * <p>
	//	 * 删除。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>删除。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String del() {
	//		log.info("C301Action del.........");
	//		
	//		String id=request.getParameter("id");
	//		TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//		bean1.setId(id);
	//		String msg="1";
	//		try {
	//			msg=consultMsgManager.deleteDataById(bean1);
	//		} catch (Exception e) {
	//			msg=e.getMessage();
	//		}
	//		request.setAttribute("msg",msg);
	//		return SUCCESS;
	//	}
	
	
	
	/**
	 * BEAN类  咨询信息取得
	 * @return BEAN类  咨询信息
	 */
	//	/**
	//	 * <p>
	//	 * 编辑。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>编辑。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String edit() {
	//		log.info("C301Action edit.........");
	//		String id=request.getParameter("id");
	//		if(id!=null){
	//			TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//			bean1.setId(id);
	//			bean=consultMsgManager.findDataById(bean1);
	//		}else{
	//			SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
	//			request.setAttribute("date", sdf.format(new Date()));
	//		}
	//		return "edit";
	//	}
	//	/**
	//	 * <p>
	//	 * 信息保存
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>新增。</div>
	//	 * <div>修改。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String save() {
	//		log.info("C301Action edit.........");
	//		if(bean!=null){
	//			String msg="1";
	//			try {
	//					BaseUserBean user = (BaseUserBean) request.getSession().getAttribute(CommonConstant.SESSION_KEY_USER_CUSTOMER_SERVICE);
	//					if(user!=null){
	//						bean.setCreate_ip(getIpAddr());
	//						bean.setCreate_id(user.getUser_id());
	//						bean.setUpdate_ip(getIpAddr());
	//						bean.setUpdate_id(user.getUser_id());
	//					}
	//					msg=consultMsgManager.saveOrUpdateData(bean);
	//			} catch (Exception e) {
	//				msg=e.getMessage();
	//			}
	//			request.setAttribute("msg",msg);
	//		}else{
	//			request.setAttribute("msg", "信息保存失败!");
	//		}
	//		return SUCCESS;
	//	}
	//	/**
	//	 * <p>
	//	 * 删除。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>删除。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String del() {
	//		log.info("C301Action del.........");
	//		
	//		String id=request.getParameter("id");
	//		TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//		bean1.setId(id);
	//		String msg="1";
	//		try {
	//			msg=consultMsgManager.deleteDataById(bean1);
	//		} catch (Exception e) {
	//			msg=e.getMessage();
	//		}
	//		request.setAttribute("msg",msg);
	//		return SUCCESS;
	//	}
	
	
	
	/**
	 * BEAN类  咨询信息设定
	 * @param bean BEAN类  咨询信息
	 */
	//	/**
	//	 * <p>
	//	 * 编辑。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>编辑。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String edit() {
	//		log.info("C301Action edit.........");
	//		String id=request.getParameter("id");
	//		if(id!=null){
	//			TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//			bean1.setId(id);
	//			bean=consultMsgManager.findDataById(bean1);
	//		}else{
	//			SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
	//			request.setAttribute("date", sdf.format(new Date()));
	//		}
	//		return "edit";
	//	}
	//	/**
	//	 * <p>
	//	 * 信息保存
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>新增。</div>
	//	 * <div>修改。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String save() {
	//		log.info("C301Action edit.........");
	//		if(bean!=null){
	//			String msg="1";
	//			try {
	//					BaseUserBean user = (BaseUserBean) request.getSession().getAttribute(CommonConstant.SESSION_KEY_USER_CUSTOMER_SERVICE);
	//					if(user!=null){
	//						bean.setCreate_ip(getIpAddr());
	//						bean.setCreate_id(user.getUser_id());
	//						bean.setUpdate_ip(getIpAddr());
	//						bean.setUpdate_id(user.getUser_id());
	//					}
	//					msg=consultMsgManager.saveOrUpdateData(bean);
	//			} catch (Exception e) {
	//				msg=e.getMessage();
	//			}
	//			request.setAttribute("msg",msg);
	//		}else{
	//			request.setAttribute("msg", "信息保存失败!");
	//		}
	//		return SUCCESS;
	//	}
	//	/**
	//	 * <p>
	//	 * 删除。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>删除。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String del() {
	//		log.info("C301Action del.........");
	//		
	//		String id=request.getParameter("id");
	//		TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//		bean1.setId(id);
	//		String msg="1";
	//		try {
	//			msg=consultMsgManager.deleteDataById(bean1);
	//		} catch (Exception e) {
	//			msg=e.getMessage();
	//		}
	//		request.setAttribute("msg",msg);
	//		return SUCCESS;
	//	}
	
	
	
	/**
	 * BEAN类  咨询信息取得
	 * @return BEAN类  咨询信息
	 */
	//	/**
	//	 * <p>
	//	 * 编辑。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>编辑。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String edit() {
	//		log.info("C301Action edit.........");
	//		String id=request.getParameter("id");
	//		if(id!=null){
	//			TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//			bean1.setId(id);
	//			bean=consultMsgManager.findDataById(bean1);
	//		}else{
	//			SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
	//			request.setAttribute("date", sdf.format(new Date()));
	//		}
	//		return "edit";
	//	}
	//	/**
	//	 * <p>
	//	 * 信息保存
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>新增。</div>
	//	 * <div>修改。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String save() {
	//		log.info("C301Action edit.........");
	//		if(bean!=null){
	//			String msg="1";
	//			try {
	//					BaseUserBean user = (BaseUserBean) request.getSession().getAttribute(CommonConstant.SESSION_KEY_USER_CUSTOMER_SERVICE);
	//					if(user!=null){
	//						bean.setCreate_ip(getIpAddr());
	//						bean.setCreate_id(user.getUser_id());
	//						bean.setUpdate_ip(getIpAddr());
	//						bean.setUpdate_id(user.getUser_id());
	//					}
	//					msg=consultMsgManager.saveOrUpdateData(bean);
	//			} catch (Exception e) {
	//				msg=e.getMessage();
	//			}
	//			request.setAttribute("msg",msg);
	//		}else{
	//			request.setAttribute("msg", "信息保存失败!");
	//		}
	//		return SUCCESS;
	//	}
	//	/**
	//	 * <p>
	//	 * 删除。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>删除。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String del() {
	//		log.info("C301Action del.........");
	//		
	//		String id=request.getParameter("id");
	//		TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//		bean1.setId(id);
	//		String msg="1";
	//		try {
	//			msg=consultMsgManager.deleteDataById(bean1);
	//		} catch (Exception e) {
	//			msg=e.getMessage();
	//		}
	//		request.setAttribute("msg",msg);
	//		return SUCCESS;
	//	}
	
	
	
	/**
	 * BEAN类  咨询信息取得
	 * @return BEAN类  咨询信息
	 */
	//	/**
	//	 * <p>
	//	 * 编辑。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>编辑。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String edit() {
	//		log.info("C301Action edit.........");
	//		String id=request.getParameter("id");
	//		if(id!=null){
	//			TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//			bean1.setId(id);
	//			bean=consultMsgManager.findDataById(bean1);
	//		}else{
	//			SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
	//			request.setAttribute("date", sdf.format(new Date()));
	//		}
	//		return "edit";
	//	}
	//	/**
	//	 * <p>
	//	 * 信息保存
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>新增。</div>
	//	 * <div>修改。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String save() {
	//		log.info("C301Action edit.........");
	//		if(bean!=null){
	//			String msg="1";
	//			try {
	//					BaseUserBean user = (BaseUserBean) request.getSession().getAttribute(CommonConstant.SESSION_KEY_USER_CUSTOMER_SERVICE);
	//					if(user!=null){
	//						bean.setCreate_ip(getIpAddr());
	//						bean.setCreate_id(user.getUser_id());
	//						bean.setUpdate_ip(getIpAddr());
	//						bean.setUpdate_id(user.getUser_id());
	//					}
	//					msg=consultMsgManager.saveOrUpdateData(bean);
	//			} catch (Exception e) {
	//				msg=e.getMessage();
	//			}
	//			request.setAttribute("msg",msg);
	//		}else{
	//			request.setAttribute("msg", "信息保存失败!");
	//		}
	//		return SUCCESS;
	//	}
	//	/**
	//	 * <p>
	//	 * 删除。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>删除。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String del() {
	//		log.info("C301Action del.........");
	//		
	//		String id=request.getParameter("id");
	//		TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//		bean1.setId(id);
	//		String msg="1";
	//		try {
	//			msg=consultMsgManager.deleteDataById(bean1);
	//		} catch (Exception e) {
	//			msg=e.getMessage();
	//		}
	//		request.setAttribute("msg",msg);
	//		return SUCCESS;
	//	}
	
	
	
	/**
	 * BEAN类  咨询信息设定
	 * @param bean BEAN类  咨询信息
	 */
	//	/**
	//	 * <p>
	//	 * 编辑。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>编辑。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String edit() {
	//		log.info("C301Action edit.........");
	//		String id=request.getParameter("id");
	//		if(id!=null){
	//			TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//			bean1.setId(id);
	//			bean=consultMsgManager.findDataById(bean1);
	//		}else{
	//			SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
	//			request.setAttribute("date", sdf.format(new Date()));
	//		}
	//		return "edit";
	//	}
	//	/**
	//	 * <p>
	//	 * 信息保存
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>新增。</div>
	//	 * <div>修改。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String save() {
	//		log.info("C301Action edit.........");
	//		if(bean!=null){
	//			String msg="1";
	//			try {
	//					BaseUserBean user = (BaseUserBean) request.getSession().getAttribute(CommonConstant.SESSION_KEY_USER_CUSTOMER_SERVICE);
	//					if(user!=null){
	//						bean.setCreate_ip(getIpAddr());
	//						bean.setCreate_id(user.getUser_id());
	//						bean.setUpdate_ip(getIpAddr());
	//						bean.setUpdate_id(user.getUser_id());
	//					}
	//					msg=consultMsgManager.saveOrUpdateData(bean);
	//			} catch (Exception e) {
	//				msg=e.getMessage();
	//			}
	//			request.setAttribute("msg",msg);
	//		}else{
	//			request.setAttribute("msg", "信息保存失败!");
	//		}
	//		return SUCCESS;
	//	}
	//	/**
	//	 * <p>
	//	 * 删除。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>删除。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String del() {
	//		log.info("C301Action del.........");
	//		
	//		String id=request.getParameter("id");
	//		TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//		bean1.setId(id);
	//		String msg="1";
	//		try {
	//			msg=consultMsgManager.deleteDataById(bean1);
	//		} catch (Exception e) {
	//			msg=e.getMessage();
	//		}
	//		request.setAttribute("msg",msg);
	//		return SUCCESS;
	//	}
	
	
	
	/**
	 * BEAN类  咨询信息取得
	 * @return BEAN类  咨询信息
	 */
	//	/**
	//	 * <p>
	//	 * 编辑。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>编辑。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String edit() {
	//		log.info("C301Action edit.........");
	//		String id=request.getParameter("id");
	//		if(id!=null){
	//			TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//			bean1.setId(id);
	//			bean=consultMsgManager.findDataById(bean1);
	//		}else{
	//			SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
	//			request.setAttribute("date", sdf.format(new Date()));
	//		}
	//		return "edit";
	//	}
	//	/**
	//	 * <p>
	//	 * 信息保存
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>新增。</div>
	//	 * <div>修改。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String save() {
	//		log.info("C301Action edit.........");
	//		if(bean!=null){
	//			String msg="1";
	//			try {
	//					BaseUserBean user = (BaseUserBean) request.getSession().getAttribute(CommonConstant.SESSION_KEY_USER_CUSTOMER_SERVICE);
	//					if(user!=null){
	//						bean.setCreate_ip(getIpAddr());
	//						bean.setCreate_id(user.getUser_id());
	//						bean.setUpdate_ip(getIpAddr());
	//						bean.setUpdate_id(user.getUser_id());
	//					}
	//					msg=consultMsgManager.saveOrUpdateData(bean);
	//			} catch (Exception e) {
	//				msg=e.getMessage();
	//			}
	//			request.setAttribute("msg",msg);
	//		}else{
	//			request.setAttribute("msg", "信息保存失败!");
	//		}
	//		return SUCCESS;
	//	}
	//	/**
	//	 * <p>
	//	 * 删除。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>删除。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String del() {
	//		log.info("C301Action del.........");
	//		
	//		String id=request.getParameter("id");
	//		TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//		bean1.setId(id);
	//		String msg="1";
	//		try {
	//			msg=consultMsgManager.deleteDataById(bean1);
	//		} catch (Exception e) {
	//			msg=e.getMessage();
	//		}
	//		request.setAttribute("msg",msg);
	//		return SUCCESS;
	//	}
	
	
	
	/**
	 * BEAN类  咨询信息设定
	 * @param bean BEAN类  咨询信息
	 */
	//	/**
	//	 * <p>
	//	 * 编辑。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>编辑。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String edit() {
	//		log.info("C301Action edit.........");
	//		String id=request.getParameter("id");
	//		if(id!=null){
	//			TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//			bean1.setId(id);
	//			bean=consultMsgManager.findDataById(bean1);
	//		}else{
	//			SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
	//			request.setAttribute("date", sdf.format(new Date()));
	//		}
	//		return "edit";
	//	}
	//	/**
	//	 * <p>
	//	 * 信息保存
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>新增。</div>
	//	 * <div>修改。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String save() {
	//		log.info("C301Action edit.........");
	//		if(bean!=null){
	//			String msg="1";
	//			try {
	//					BaseUserBean user = (BaseUserBean) request.getSession().getAttribute(CommonConstant.SESSION_KEY_USER_CUSTOMER_SERVICE);
	//					if(user!=null){
	//						bean.setCreate_ip(getIpAddr());
	//						bean.setCreate_id(user.getUser_id());
	//						bean.setUpdate_ip(getIpAddr());
	//						bean.setUpdate_id(user.getUser_id());
	//					}
	//					msg=consultMsgManager.saveOrUpdateData(bean);
	//			} catch (Exception e) {
	//				msg=e.getMessage();
	//			}
	//			request.setAttribute("msg",msg);
	//		}else{
	//			request.setAttribute("msg", "信息保存失败!");
	//		}
	//		return SUCCESS;
	//	}
	//	/**
	//	 * <p>
	//	 * 删除。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>删除。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String del() {
	//		log.info("C301Action del.........");
	//		
	//		String id=request.getParameter("id");
	//		TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//		bean1.setId(id);
	//		String msg="1";
	//		try {
	//			msg=consultMsgManager.deleteDataById(bean1);
	//		} catch (Exception e) {
	//			msg=e.getMessage();
	//		}
	//		request.setAttribute("msg",msg);
	//		return SUCCESS;
	//	}
	
	
	
	/**
	 * BEAN类  咨询信息取得
	 * @return BEAN类  咨询信息
	 */
	//	/**
	//	 * <p>
	//	 * 编辑。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>编辑。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String edit() {
	//		log.info("C301Action edit.........");
	//		String id=request.getParameter("id");
	//		if(id!=null){
	//			TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//			bean1.setId(id);
	//			bean=consultMsgManager.findDataById(bean1);
	//		}else{
	//			SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
	//			request.setAttribute("date", sdf.format(new Date()));
	//		}
	//		return "edit";
	//	}
	//	/**
	//	 * <p>
	//	 * 信息保存
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>新增。</div>
	//	 * <div>修改。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String save() {
	//		log.info("C301Action edit.........");
	//		if(bean!=null){
	//			String msg="1";
	//			try {
	//					BaseUserBean user = (BaseUserBean) request.getSession().getAttribute(CommonConstant.SESSION_KEY_USER_CUSTOMER_SERVICE);
	//					if(user!=null){
	//						bean.setCreate_ip(getIpAddr());
	//						bean.setCreate_id(user.getUser_id());
	//						bean.setUpdate_ip(getIpAddr());
	//						bean.setUpdate_id(user.getUser_id());
	//					}
	//					msg=consultMsgManager.saveOrUpdateData(bean);
	//			} catch (Exception e) {
	//				msg=e.getMessage();
	//			}
	//			request.setAttribute("msg",msg);
	//		}else{
	//			request.setAttribute("msg", "信息保存失败!");
	//		}
	//		return SUCCESS;
	//	}
	//	/**
	//	 * <p>
	//	 * 删除。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>删除。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String del() {
	//		log.info("C301Action del.........");
	//		
	//		String id=request.getParameter("id");
	//		TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//		bean1.setId(id);
	//		String msg="1";
	//		try {
	//			msg=consultMsgManager.deleteDataById(bean1);
	//		} catch (Exception e) {
	//			msg=e.getMessage();
	//		}
	//		request.setAttribute("msg",msg);
	//		return SUCCESS;
	//	}
	
	
	
	/**
	 * BEAN类  咨询信息取得
	 * @return BEAN类  咨询信息
	 */
	//	/**
	//	 * <p>
	//	 * 编辑。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>编辑。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String edit() {
	//		log.info("C301Action edit.........");
	//		String id=request.getParameter("id");
	//		if(id!=null){
	//			TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//			bean1.setId(id);
	//			bean=consultMsgManager.findDataById(bean1);
	//		}else{
	//			SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
	//			request.setAttribute("date", sdf.format(new Date()));
	//		}
	//		return "edit";
	//	}
	//	/**
	//	 * <p>
	//	 * 信息保存
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>新增。</div>
	//	 * <div>修改。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String save() {
	//		log.info("C301Action edit.........");
	//		if(bean!=null){
	//			String msg="1";
	//			try {
	//					BaseUserBean user = (BaseUserBean) request.getSession().getAttribute(CommonConstant.SESSION_KEY_USER_CUSTOMER_SERVICE);
	//					if(user!=null){
	//						bean.setCreate_ip(getIpAddr());
	//						bean.setCreate_id(user.getUser_id());
	//						bean.setUpdate_ip(getIpAddr());
	//						bean.setUpdate_id(user.getUser_id());
	//					}
	//					msg=consultMsgManager.saveOrUpdateData(bean);
	//			} catch (Exception e) {
	//				msg=e.getMessage();
	//			}
	//			request.setAttribute("msg",msg);
	//		}else{
	//			request.setAttribute("msg", "信息保存失败!");
	//		}
	//		return SUCCESS;
	//	}
	//	/**
	//	 * <p>
	//	 * 删除。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>删除。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String del() {
	//		log.info("C301Action del.........");
	//		
	//		String id=request.getParameter("id");
	//		TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//		bean1.setId(id);
	//		String msg="1";
	//		try {
	//			msg=consultMsgManager.deleteDataById(bean1);
	//		} catch (Exception e) {
	//			msg=e.getMessage();
	//		}
	//		request.setAttribute("msg",msg);
	//		return SUCCESS;
	//	}
	
	
	
	/**
	 * BEAN类  咨询信息设定
	 * @param bean BEAN类  咨询信息
	 */
	//	/**
	//	 * <p>
	//	 * 编辑。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>编辑。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String edit() {
	//		log.info("C301Action edit.........");
	//		String id=request.getParameter("id");
	//		if(id!=null){
	//			TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//			bean1.setId(id);
	//			bean=consultMsgManager.findDataById(bean1);
	//		}else{
	//			SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
	//			request.setAttribute("date", sdf.format(new Date()));
	//		}
	//		return "edit";
	//	}
	//	/**
	//	 * <p>
	//	 * 信息保存
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>新增。</div>
	//	 * <div>修改。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String save() {
	//		log.info("C301Action edit.........");
	//		if(bean!=null){
	//			String msg="1";
	//			try {
	//					BaseUserBean user = (BaseUserBean) request.getSession().getAttribute(CommonConstant.SESSION_KEY_USER_CUSTOMER_SERVICE);
	//					if(user!=null){
	//						bean.setCreate_ip(getIpAddr());
	//						bean.setCreate_id(user.getUser_id());
	//						bean.setUpdate_ip(getIpAddr());
	//						bean.setUpdate_id(user.getUser_id());
	//					}
	//					msg=consultMsgManager.saveOrUpdateData(bean);
	//			} catch (Exception e) {
	//				msg=e.getMessage();
	//			}
	//			request.setAttribute("msg",msg);
	//		}else{
	//			request.setAttribute("msg", "信息保存失败!");
	//		}
	//		return SUCCESS;
	//	}
	//	/**
	//	 * <p>
	//	 * 删除。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>删除。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String del() {
	//		log.info("C301Action del.........");
	//		
	//		String id=request.getParameter("id");
	//		TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//		bean1.setId(id);
	//		String msg="1";
	//		try {
	//			msg=consultMsgManager.deleteDataById(bean1);
	//		} catch (Exception e) {
	//			msg=e.getMessage();
	//		}
	//		request.setAttribute("msg",msg);
	//		return SUCCESS;
	//	}
	
	
	
	/**
	 * BEAN类  咨询信息取得
	 * @return BEAN类  咨询信息
	 */
	//	/**
	//	 * <p>
	//	 * 编辑。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>编辑。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String edit() {
	//		log.info("C301Action edit.........");
	//		String id=request.getParameter("id");
	//		if(id!=null){
	//			TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//			bean1.setId(id);
	//			bean=consultMsgManager.findDataById(bean1);
	//		}else{
	//			SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
	//			request.setAttribute("date", sdf.format(new Date()));
	//		}
	//		return "edit";
	//	}
	//	/**
	//	 * <p>
	//	 * 信息保存
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>新增。</div>
	//	 * <div>修改。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String save() {
	//		log.info("C301Action edit.........");
	//		if(bean!=null){
	//			String msg="1";
	//			try {
	//					BaseUserBean user = (BaseUserBean) request.getSession().getAttribute(CommonConstant.SESSION_KEY_USER_CUSTOMER_SERVICE);
	//					if(user!=null){
	//						bean.setCreate_ip(getIpAddr());
	//						bean.setCreate_id(user.getUser_id());
	//						bean.setUpdate_ip(getIpAddr());
	//						bean.setUpdate_id(user.getUser_id());
	//					}
	//					msg=consultMsgManager.saveOrUpdateData(bean);
	//			} catch (Exception e) {
	//				msg=e.getMessage();
	//			}
	//			request.setAttribute("msg",msg);
	//		}else{
	//			request.setAttribute("msg", "信息保存失败!");
	//		}
	//		return SUCCESS;
	//	}
	//	/**
	//	 * <p>
	//	 * 删除。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>删除。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String del() {
	//		log.info("C301Action del.........");
	//		
	//		String id=request.getParameter("id");
	//		TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//		bean1.setId(id);
	//		String msg="1";
	//		try {
	//			msg=consultMsgManager.deleteDataById(bean1);
	//		} catch (Exception e) {
	//			msg=e.getMessage();
	//		}
	//		request.setAttribute("msg",msg);
	//		return SUCCESS;
	//	}
	
	
	
	/**
	 * BEAN类  咨询信息设定
	 * @param bean BEAN类  咨询信息
	 */
	//	/**
	//	 * <p>
	//	 * 编辑。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>编辑。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String edit() {
	//		log.info("C301Action edit.........");
	//		String id=request.getParameter("id");
	//		if(id!=null){
	//			TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//			bean1.setId(id);
	//			bean=consultMsgManager.findDataById(bean1);
	//		}else{
	//			SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
	//			request.setAttribute("date", sdf.format(new Date()));
	//		}
	//		return "edit";
	//	}
	//	/**
	//	 * <p>
	//	 * 信息保存
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>新增。</div>
	//	 * <div>修改。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String save() {
	//		log.info("C301Action edit.........");
	//		if(bean!=null){
	//			String msg="1";
	//			try {
	//					BaseUserBean user = (BaseUserBean) request.getSession().getAttribute(CommonConstant.SESSION_KEY_USER_CUSTOMER_SERVICE);
	//					if(user!=null){
	//						bean.setCreate_ip(getIpAddr());
	//						bean.setCreate_id(user.getUser_id());
	//						bean.setUpdate_ip(getIpAddr());
	//						bean.setUpdate_id(user.getUser_id());
	//					}
	//					msg=consultMsgManager.saveOrUpdateData(bean);
	//			} catch (Exception e) {
	//				msg=e.getMessage();
	//			}
	//			request.setAttribute("msg",msg);
	//		}else{
	//			request.setAttribute("msg", "信息保存失败!");
	//		}
	//		return SUCCESS;
	//	}
	//	/**
	//	 * <p>
	//	 * 删除。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>删除。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String del() {
	//		log.info("C301Action del.........");
	//		
	//		String id=request.getParameter("id");
	//		TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//		bean1.setId(id);
	//		String msg="1";
	//		try {
	//			msg=consultMsgManager.deleteDataById(bean1);
	//		} catch (Exception e) {
	//			msg=e.getMessage();
	//		}
	//		request.setAttribute("msg",msg);
	//		return SUCCESS;
	//	}
	
	
	
	/**
	 * BEAN类  咨询信息取得
	 * @return BEAN类  咨询信息
	 */
	//	/**
	//	 * <p>
	//	 * 编辑。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>编辑。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String edit() {
	//		log.info("C301Action edit.........");
	//		String id=request.getParameter("id");
	//		if(id!=null){
	//			TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//			bean1.setId(id);
	//			bean=consultMsgManager.findDataById(bean1);
	//		}else{
	//			SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
	//			request.setAttribute("date", sdf.format(new Date()));
	//		}
	//		return "edit";
	//	}
	//	/**
	//	 * <p>
	//	 * 信息保存
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>新增。</div>
	//	 * <div>修改。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String save() {
	//		log.info("C301Action edit.........");
	//		if(bean!=null){
	//			String msg="1";
	//			try {
	//					BaseUserBean user = (BaseUserBean) request.getSession().getAttribute(CommonConstant.SESSION_KEY_USER_CUSTOMER_SERVICE);
	//					if(user!=null){
	//						bean.setCreate_ip(getIpAddr());
	//						bean.setCreate_id(user.getUser_id());
	//						bean.setUpdate_ip(getIpAddr());
	//						bean.setUpdate_id(user.getUser_id());
	//					}
	//					msg=consultMsgManager.saveOrUpdateData(bean);
	//			} catch (Exception e) {
	//				msg=e.getMessage();
	//			}
	//			request.setAttribute("msg",msg);
	//		}else{
	//			request.setAttribute("msg", "信息保存失败!");
	//		}
	//		return SUCCESS;
	//	}
	//	/**
	//	 * <p>
	//	 * 删除。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>删除。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String del() {
	//		log.info("C301Action del.........");
	//		
	//		String id=request.getParameter("id");
	//		TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//		bean1.setId(id);
	//		String msg="1";
	//		try {
	//			msg=consultMsgManager.deleteDataById(bean1);
	//		} catch (Exception e) {
	//			msg=e.getMessage();
	//		}
	//		request.setAttribute("msg",msg);
	//		return SUCCESS;
	//	}
	
	
	
	/**
	 * BEAN类  咨询信息取得
	 * @return BEAN类  咨询信息
	 */
	//	/**
	//	 * <p>
	//	 * 编辑。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>编辑。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String edit() {
	//		log.info("C301Action edit.........");
	//		String id=request.getParameter("id");
	//		if(id!=null){
	//			TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//			bean1.setId(id);
	//			bean=consultMsgManager.findDataById(bean1);
	//		}else{
	//			SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
	//			request.setAttribute("date", sdf.format(new Date()));
	//		}
	//		return "edit";
	//	}
	//	/**
	//	 * <p>
	//	 * 信息保存
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>新增。</div>
	//	 * <div>修改。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String save() {
	//		log.info("C301Action edit.........");
	//		if(bean!=null){
	//			String msg="1";
	//			try {
	//					BaseUserBean user = (BaseUserBean) request.getSession().getAttribute(CommonConstant.SESSION_KEY_USER_CUSTOMER_SERVICE);
	//					if(user!=null){
	//						bean.setCreate_ip(getIpAddr());
	//						bean.setCreate_id(user.getUser_id());
	//						bean.setUpdate_ip(getIpAddr());
	//						bean.setUpdate_id(user.getUser_id());
	//					}
	//					msg=consultMsgManager.saveOrUpdateData(bean);
	//			} catch (Exception e) {
	//				msg=e.getMessage();
	//			}
	//			request.setAttribute("msg",msg);
	//		}else{
	//			request.setAttribute("msg", "信息保存失败!");
	//		}
	//		return SUCCESS;
	//	}
	//	/**
	//	 * <p>
	//	 * 删除。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>删除。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String del() {
	//		log.info("C301Action del.........");
	//		
	//		String id=request.getParameter("id");
	//		TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//		bean1.setId(id);
	//		String msg="1";
	//		try {
	//			msg=consultMsgManager.deleteDataById(bean1);
	//		} catch (Exception e) {
	//			msg=e.getMessage();
	//		}
	//		request.setAttribute("msg",msg);
	//		return SUCCESS;
	//	}
	
	
	
	/**
	 * BEAN类  咨询信息设定
	 * @param bean BEAN类  咨询信息
	 */
	//	/**
	//	 * <p>
	//	 * 编辑。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>编辑。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String edit() {
	//		log.info("C301Action edit.........");
	//		String id=request.getParameter("id");
	//		if(id!=null){
	//			TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//			bean1.setId(id);
	//			bean=consultMsgManager.findDataById(bean1);
	//		}else{
	//			SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
	//			request.setAttribute("date", sdf.format(new Date()));
	//		}
	//		return "edit";
	//	}
	//	/**
	//	 * <p>
	//	 * 信息保存
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>新增。</div>
	//	 * <div>修改。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String save() {
	//		log.info("C301Action edit.........");
	//		if(bean!=null){
	//			String msg="1";
	//			try {
	//					BaseUserBean user = (BaseUserBean) request.getSession().getAttribute(CommonConstant.SESSION_KEY_USER_CUSTOMER_SERVICE);
	//					if(user!=null){
	//						bean.setCreate_ip(getIpAddr());
	//						bean.setCreate_id(user.getUser_id());
	//						bean.setUpdate_ip(getIpAddr());
	//						bean.setUpdate_id(user.getUser_id());
	//					}
	//					msg=consultMsgManager.saveOrUpdateData(bean);
	//			} catch (Exception e) {
	//				msg=e.getMessage();
	//			}
	//			request.setAttribute("msg",msg);
	//		}else{
	//			request.setAttribute("msg", "信息保存失败!");
	//		}
	//		return SUCCESS;
	//	}
	//	/**
	//	 * <p>
	//	 * 删除。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>删除。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String del() {
	//		log.info("C301Action del.........");
	//		
	//		String id=request.getParameter("id");
	//		TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//		bean1.setId(id);
	//		String msg="1";
	//		try {
	//			msg=consultMsgManager.deleteDataById(bean1);
	//		} catch (Exception e) {
	//			msg=e.getMessage();
	//		}
	//		request.setAttribute("msg",msg);
	//		return SUCCESS;
	//	}
	
	
	
	/**
	 * BEAN类  咨询信息取得
	 * @return BEAN类  咨询信息
	 */
	//	/**
	//	 * <p>
	//	 * 编辑。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>编辑。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String edit() {
	//		log.info("C301Action edit.........");
	//		String id=request.getParameter("id");
	//		if(id!=null){
	//			TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//			bean1.setId(id);
	//			bean=consultMsgManager.findDataById(bean1);
	//		}else{
	//			SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
	//			request.setAttribute("date", sdf.format(new Date()));
	//		}
	//		return "edit";
	//	}
	//	/**
	//	 * <p>
	//	 * 信息保存
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>新增。</div>
	//	 * <div>修改。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String save() {
	//		log.info("C301Action edit.........");
	//		if(bean!=null){
	//			String msg="1";
	//			try {
	//					BaseUserBean user = (BaseUserBean) request.getSession().getAttribute(CommonConstant.SESSION_KEY_USER_CUSTOMER_SERVICE);
	//					if(user!=null){
	//						bean.setCreate_ip(getIpAddr());
	//						bean.setCreate_id(user.getUser_id());
	//						bean.setUpdate_ip(getIpAddr());
	//						bean.setUpdate_id(user.getUser_id());
	//					}
	//					msg=consultMsgManager.saveOrUpdateData(bean);
	//			} catch (Exception e) {
	//				msg=e.getMessage();
	//			}
	//			request.setAttribute("msg",msg);
	//		}else{
	//			request.setAttribute("msg", "信息保存失败!");
	//		}
	//		return SUCCESS;
	//	}
	//	/**
	//	 * <p>
	//	 * 删除。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>删除。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String del() {
	//		log.info("C301Action del.........");
	//		
	//		String id=request.getParameter("id");
	//		TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//		bean1.setId(id);
	//		String msg="1";
	//		try {
	//			msg=consultMsgManager.deleteDataById(bean1);
	//		} catch (Exception e) {
	//			msg=e.getMessage();
	//		}
	//		request.setAttribute("msg",msg);
	//		return SUCCESS;
	//	}
	
	
	
	/**
	 * BEAN类  咨询信息取得
	 * @return BEAN类  咨询信息
	 */
	//	/**
	//	 * <p>
	//	 * 编辑。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>编辑。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String edit() {
	//		log.info("C301Action edit.........");
	//		String id=request.getParameter("id");
	//		if(id!=null){
	//			TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//			bean1.setId(id);
	//			bean=consultMsgManager.findDataById(bean1);
	//		}else{
	//			SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
	//			request.setAttribute("date", sdf.format(new Date()));
	//		}
	//		return "edit";
	//	}
	//	/**
	//	 * <p>
	//	 * 信息保存
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>新增。</div>
	//	 * <div>修改。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String save() {
	//		log.info("C301Action edit.........");
	//		if(bean!=null){
	//			String msg="1";
	//			try {
	//					BaseUserBean user = (BaseUserBean) request.getSession().getAttribute(CommonConstant.SESSION_KEY_USER_CUSTOMER_SERVICE);
	//					if(user!=null){
	//						bean.setCreate_ip(getIpAddr());
	//						bean.setCreate_id(user.getUser_id());
	//						bean.setUpdate_ip(getIpAddr());
	//						bean.setUpdate_id(user.getUser_id());
	//					}
	//					msg=consultMsgManager.saveOrUpdateData(bean);
	//			} catch (Exception e) {
	//				msg=e.getMessage();
	//			}
	//			request.setAttribute("msg",msg);
	//		}else{
	//			request.setAttribute("msg", "信息保存失败!");
	//		}
	//		return SUCCESS;
	//	}
	//	/**
	//	 * <p>
	//	 * 删除。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>删除。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String del() {
	//		log.info("C301Action del.........");
	//		
	//		String id=request.getParameter("id");
	//		TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//		bean1.setId(id);
	//		String msg="1";
	//		try {
	//			msg=consultMsgManager.deleteDataById(bean1);
	//		} catch (Exception e) {
	//			msg=e.getMessage();
	//		}
	//		request.setAttribute("msg",msg);
	//		return SUCCESS;
	//	}
	
	
	
	/**
	 * BEAN类  咨询信息设定
	 * @param bean BEAN类  咨询信息
	 */
	//	/**
	//	 * <p>
	//	 * 编辑。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>编辑。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String edit() {
	//		log.info("C301Action edit.........");
	//		String id=request.getParameter("id");
	//		if(id!=null){
	//			TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//			bean1.setId(id);
	//			bean=consultMsgManager.findDataById(bean1);
	//		}else{
	//			SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
	//			request.setAttribute("date", sdf.format(new Date()));
	//		}
	//		return "edit";
	//	}
	//	/**
	//	 * <p>
	//	 * 信息保存
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>新增。</div>
	//	 * <div>修改。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String save() {
	//		log.info("C301Action edit.........");
	//		if(bean!=null){
	//			String msg="1";
	//			try {
	//					BaseUserBean user = (BaseUserBean) request.getSession().getAttribute(CommonConstant.SESSION_KEY_USER_CUSTOMER_SERVICE);
	//					if(user!=null){
	//						bean.setCreate_ip(getIpAddr());
	//						bean.setCreate_id(user.getUser_id());
	//						bean.setUpdate_ip(getIpAddr());
	//						bean.setUpdate_id(user.getUser_id());
	//					}
	//					msg=consultMsgManager.saveOrUpdateData(bean);
	//			} catch (Exception e) {
	//				msg=e.getMessage();
	//			}
	//			request.setAttribute("msg",msg);
	//		}else{
	//			request.setAttribute("msg", "信息保存失败!");
	//		}
	//		return SUCCESS;
	//	}
	//	/**
	//	 * <p>
	//	 * 删除。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>删除。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String del() {
	//		log.info("C301Action del.........");
	//		
	//		String id=request.getParameter("id");
	//		TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//		bean1.setId(id);
	//		String msg="1";
	//		try {
	//			msg=consultMsgManager.deleteDataById(bean1);
	//		} catch (Exception e) {
	//			msg=e.getMessage();
	//		}
	//		request.setAttribute("msg",msg);
	//		return SUCCESS;
	//	}
	
	
	
	/**
	 * BEAN类  咨询信息取得
	 * @return BEAN类  咨询信息
	 */
	//	/**
	//	 * <p>
	//	 * 编辑。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>编辑。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String edit() {
	//		log.info("C301Action edit.........");
	//		String id=request.getParameter("id");
	//		if(id!=null){
	//			TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//			bean1.setId(id);
	//			bean=consultMsgManager.findDataById(bean1);
	//		}else{
	//			SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
	//			request.setAttribute("date", sdf.format(new Date()));
	//		}
	//		return "edit";
	//	}
	//	/**
	//	 * <p>
	//	 * 信息保存
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>新增。</div>
	//	 * <div>修改。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String save() {
	//		log.info("C301Action edit.........");
	//		if(bean!=null){
	//			String msg="1";
	//			try {
	//					BaseUserBean user = (BaseUserBean) request.getSession().getAttribute(CommonConstant.SESSION_KEY_USER_CUSTOMER_SERVICE);
	//					if(user!=null){
	//						bean.setCreate_ip(getIpAddr());
	//						bean.setCreate_id(user.getUser_id());
	//						bean.setUpdate_ip(getIpAddr());
	//						bean.setUpdate_id(user.getUser_id());
	//					}
	//					msg=consultMsgManager.saveOrUpdateData(bean);
	//			} catch (Exception e) {
	//				msg=e.getMessage();
	//			}
	//			request.setAttribute("msg",msg);
	//		}else{
	//			request.setAttribute("msg", "信息保存失败!");
	//		}
	//		return SUCCESS;
	//	}
	//	/**
	//	 * <p>
	//	 * 删除。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>删除。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String del() {
	//		log.info("C301Action del.........");
	//		
	//		String id=request.getParameter("id");
	//		TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//		bean1.setId(id);
	//		String msg="1";
	//		try {
	//			msg=consultMsgManager.deleteDataById(bean1);
	//		} catch (Exception e) {
	//			msg=e.getMessage();
	//		}
	//		request.setAttribute("msg",msg);
	//		return SUCCESS;
	//	}
	
	
	
	/**
	 * BEAN类  咨询信息设定
	 * @param bean BEAN类  咨询信息
	 */
	//	/**
	//	 * <p>
	//	 * 编辑。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>编辑。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String edit() {
	//		log.info("C301Action edit.........");
	//		String id=request.getParameter("id");
	//		if(id!=null){
	//			TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//			bean1.setId(id);
	//			bean=consultMsgManager.findDataById(bean1);
	//		}else{
	//			SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
	//			request.setAttribute("date", sdf.format(new Date()));
	//		}
	//		return "edit";
	//	}
	//	/**
	//	 * <p>
	//	 * 信息保存
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>新增。</div>
	//	 * <div>修改。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String save() {
	//		log.info("C301Action edit.........");
	//		if(bean!=null){
	//			String msg="1";
	//			try {
	//					BaseUserBean user = (BaseUserBean) request.getSession().getAttribute(CommonConstant.SESSION_KEY_USER_CUSTOMER_SERVICE);
	//					if(user!=null){
	//						bean.setCreate_ip(getIpAddr());
	//						bean.setCreate_id(user.getUser_id());
	//						bean.setUpdate_ip(getIpAddr());
	//						bean.setUpdate_id(user.getUser_id());
	//					}
	//					msg=consultMsgManager.saveOrUpdateData(bean);
	//			} catch (Exception e) {
	//				msg=e.getMessage();
	//			}
	//			request.setAttribute("msg",msg);
	//		}else{
	//			request.setAttribute("msg", "信息保存失败!");
	//		}
	//		return SUCCESS;
	//	}
	//	/**
	//	 * <p>
	//	 * 删除。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>删除。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String del() {
	//		log.info("C301Action del.........");
	//		
	//		String id=request.getParameter("id");
	//		TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//		bean1.setId(id);
	//		String msg="1";
	//		try {
	//			msg=consultMsgManager.deleteDataById(bean1);
	//		} catch (Exception e) {
	//			msg=e.getMessage();
	//		}
	//		request.setAttribute("msg",msg);
	//		return SUCCESS;
	//	}
	
	
	
	/**
	 * BEAN类  咨询信息取得
	 * @return BEAN类  咨询信息
	 */
	//	/**
	//	 * <p>
	//	 * 编辑。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>编辑。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String edit() {
	//		log.info("C301Action edit.........");
	//		String id=request.getParameter("id");
	//		if(id!=null){
	//			TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//			bean1.setId(id);
	//			bean=consultMsgManager.findDataById(bean1);
	//		}else{
	//			SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
	//			request.setAttribute("date", sdf.format(new Date()));
	//		}
	//		return "edit";
	//	}
	//	/**
	//	 * <p>
	//	 * 信息保存
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>新增。</div>
	//	 * <div>修改。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String save() {
	//		log.info("C301Action edit.........");
	//		if(bean!=null){
	//			String msg="1";
	//			try {
	//					BaseUserBean user = (BaseUserBean) request.getSession().getAttribute(CommonConstant.SESSION_KEY_USER_CUSTOMER_SERVICE);
	//					if(user!=null){
	//						bean.setCreate_ip(getIpAddr());
	//						bean.setCreate_id(user.getUser_id());
	//						bean.setUpdate_ip(getIpAddr());
	//						bean.setUpdate_id(user.getUser_id());
	//					}
	//					msg=consultMsgManager.saveOrUpdateData(bean);
	//			} catch (Exception e) {
	//				msg=e.getMessage();
	//			}
	//			request.setAttribute("msg",msg);
	//		}else{
	//			request.setAttribute("msg", "信息保存失败!");
	//		}
	//		return SUCCESS;
	//	}
	//	/**
	//	 * <p>
	//	 * 删除。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>删除。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String del() {
	//		log.info("C301Action del.........");
	//		
	//		String id=request.getParameter("id");
	//		TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//		bean1.setId(id);
	//		String msg="1";
	//		try {
	//			msg=consultMsgManager.deleteDataById(bean1);
	//		} catch (Exception e) {
	//			msg=e.getMessage();
	//		}
	//		request.setAttribute("msg",msg);
	//		return SUCCESS;
	//	}
	
	
	
	/**
	 * BEAN类  咨询信息取得
	 * @return BEAN类  咨询信息
	 */
	//	/**
	//	 * <p>
	//	 * 编辑。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>编辑。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String edit() {
	//		log.info("C301Action edit.........");
	//		String id=request.getParameter("id");
	//		if(id!=null){
	//			TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//			bean1.setId(id);
	//			bean=consultMsgManager.findDataById(bean1);
	//		}else{
	//			SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
	//			request.setAttribute("date", sdf.format(new Date()));
	//		}
	//		return "edit";
	//	}
	//	/**
	//	 * <p>
	//	 * 信息保存
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>新增。</div>
	//	 * <div>修改。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String save() {
	//		log.info("C301Action edit.........");
	//		if(bean!=null){
	//			String msg="1";
	//			try {
	//					BaseUserBean user = (BaseUserBean) request.getSession().getAttribute(CommonConstant.SESSION_KEY_USER_CUSTOMER_SERVICE);
	//					if(user!=null){
	//						bean.setCreate_ip(getIpAddr());
	//						bean.setCreate_id(user.getUser_id());
	//						bean.setUpdate_ip(getIpAddr());
	//						bean.setUpdate_id(user.getUser_id());
	//					}
	//					msg=consultMsgManager.saveOrUpdateData(bean);
	//			} catch (Exception e) {
	//				msg=e.getMessage();
	//			}
	//			request.setAttribute("msg",msg);
	//		}else{
	//			request.setAttribute("msg", "信息保存失败!");
	//		}
	//		return SUCCESS;
	//	}
	//	/**
	//	 * <p>
	//	 * 删除。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>删除。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String del() {
	//		log.info("C301Action del.........");
	//		
	//		String id=request.getParameter("id");
	//		TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//		bean1.setId(id);
	//		String msg="1";
	//		try {
	//			msg=consultMsgManager.deleteDataById(bean1);
	//		} catch (Exception e) {
	//			msg=e.getMessage();
	//		}
	//		request.setAttribute("msg",msg);
	//		return SUCCESS;
	//	}
	
	
	
	/**
	 * BEAN类  咨询信息设定
	 * @param bean BEAN类  咨询信息
	 */
	//	/**
	//	 * <p>
	//	 * 编辑。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>编辑。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String edit() {
	//		log.info("C301Action edit.........");
	//		String id=request.getParameter("id");
	//		if(id!=null){
	//			TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//			bean1.setId(id);
	//			bean=consultMsgManager.findDataById(bean1);
	//		}else{
	//			SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
	//			request.setAttribute("date", sdf.format(new Date()));
	//		}
	//		return "edit";
	//	}
	//	/**
	//	 * <p>
	//	 * 信息保存
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>新增。</div>
	//	 * <div>修改。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String save() {
	//		log.info("C301Action edit.........");
	//		if(bean!=null){
	//			String msg="1";
	//			try {
	//					BaseUserBean user = (BaseUserBean) request.getSession().getAttribute(CommonConstant.SESSION_KEY_USER_CUSTOMER_SERVICE);
	//					if(user!=null){
	//						bean.setCreate_ip(getIpAddr());
	//						bean.setCreate_id(user.getUser_id());
	//						bean.setUpdate_ip(getIpAddr());
	//						bean.setUpdate_id(user.getUser_id());
	//					}
	//					msg=consultMsgManager.saveOrUpdateData(bean);
	//			} catch (Exception e) {
	//				msg=e.getMessage();
	//			}
	//			request.setAttribute("msg",msg);
	//		}else{
	//			request.setAttribute("msg", "信息保存失败!");
	//		}
	//		return SUCCESS;
	//	}
	//	/**
	//	 * <p>
	//	 * 删除。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>删除。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String del() {
	//		log.info("C301Action del.........");
	//		
	//		String id=request.getParameter("id");
	//		TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//		bean1.setId(id);
	//		String msg="1";
	//		try {
	//			msg=consultMsgManager.deleteDataById(bean1);
	//		} catch (Exception e) {
	//			msg=e.getMessage();
	//		}
	//		request.setAttribute("msg",msg);
	//		return SUCCESS;
	//	}
	
	
	
	/**
	 * BEAN类  咨询信息取得
	 * @return BEAN类  咨询信息
	 */
	//	/**
	//	 * <p>
	//	 * 编辑。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>编辑。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String edit() {
	//		log.info("C301Action edit.........");
	//		String id=request.getParameter("id");
	//		if(id!=null){
	//			TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//			bean1.setId(id);
	//			bean=consultMsgManager.findDataById(bean1);
	//		}else{
	//			SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
	//			request.setAttribute("date", sdf.format(new Date()));
	//		}
	//		return "edit";
	//	}
	//	/**
	//	 * <p>
	//	 * 信息保存
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>新增。</div>
	//	 * <div>修改。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String save() {
	//		log.info("C301Action edit.........");
	//		if(bean!=null){
	//			String msg="1";
	//			try {
	//					BaseUserBean user = (BaseUserBean) request.getSession().getAttribute(CommonConstant.SESSION_KEY_USER_CUSTOMER_SERVICE);
	//					if(user!=null){
	//						bean.setCreate_ip(getIpAddr());
	//						bean.setCreate_id(user.getUser_id());
	//						bean.setUpdate_ip(getIpAddr());
	//						bean.setUpdate_id(user.getUser_id());
	//					}
	//					msg=consultMsgManager.saveOrUpdateData(bean);
	//			} catch (Exception e) {
	//				msg=e.getMessage();
	//			}
	//			request.setAttribute("msg",msg);
	//		}else{
	//			request.setAttribute("msg", "信息保存失败!");
	//		}
	//		return SUCCESS;
	//	}
	//	/**
	//	 * <p>
	//	 * 删除。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>删除。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String del() {
	//		log.info("C301Action del.........");
	//		
	//		String id=request.getParameter("id");
	//		TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//		bean1.setId(id);
	//		String msg="1";
	//		try {
	//			msg=consultMsgManager.deleteDataById(bean1);
	//		} catch (Exception e) {
	//			msg=e.getMessage();
	//		}
	//		request.setAttribute("msg",msg);
	//		return SUCCESS;
	//	}
	
	
	
	/**
	 * BEAN类  咨询信息设定
	 * @param bean BEAN类  咨询信息
	 */
	//	/**
	//	 * <p>
	//	 * 编辑。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>编辑。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String edit() {
	//		log.info("C301Action edit.........");
	//		String id=request.getParameter("id");
	//		if(id!=null){
	//			TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//			bean1.setId(id);
	//			bean=consultMsgManager.findDataById(bean1);
	//		}else{
	//			SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
	//			request.setAttribute("date", sdf.format(new Date()));
	//		}
	//		return "edit";
	//	}
	//	/**
	//	 * <p>
	//	 * 信息保存
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>新增。</div>
	//	 * <div>修改。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String save() {
	//		log.info("C301Action edit.........");
	//		if(bean!=null){
	//			String msg="1";
	//			try {
	//					BaseUserBean user = (BaseUserBean) request.getSession().getAttribute(CommonConstant.SESSION_KEY_USER_CUSTOMER_SERVICE);
	//					if(user!=null){
	//						bean.setCreate_ip(getIpAddr());
	//						bean.setCreate_id(user.getUser_id());
	//						bean.setUpdate_ip(getIpAddr());
	//						bean.setUpdate_id(user.getUser_id());
	//					}
	//					msg=consultMsgManager.saveOrUpdateData(bean);
	//			} catch (Exception e) {
	//				msg=e.getMessage();
	//			}
	//			request.setAttribute("msg",msg);
	//		}else{
	//			request.setAttribute("msg", "信息保存失败!");
	//		}
	//		return SUCCESS;
	//	}
	//	/**
	//	 * <p>
	//	 * 删除。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>删除。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String del() {
	//		log.info("C301Action del.........");
	//		
	//		String id=request.getParameter("id");
	//		TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//		bean1.setId(id);
	//		String msg="1";
	//		try {
	//			msg=consultMsgManager.deleteDataById(bean1);
	//		} catch (Exception e) {
	//			msg=e.getMessage();
	//		}
	//		request.setAttribute("msg",msg);
	//		return SUCCESS;
	//	}
	
	

	/**
	 * BEAN类  咨询信息取得
	 * @return BEAN类  咨询信息
	 */
	//	/**
	//	 * <p>
	//	 * 编辑。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>编辑。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String edit() {
	//		log.info("C301Action edit.........");
	//		String id=request.getParameter("id");
	//		if(id!=null){
	//			TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//			bean1.setId(id);
	//			bean=consultMsgManager.findDataById(bean1);
	//		}else{
	//			SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
	//			request.setAttribute("date", sdf.format(new Date()));
	//		}
	//		return "edit";
	//	}
	//	/**
	//	 * <p>
	//	 * 信息保存
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>新增。</div>
	//	 * <div>修改。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String save() {
	//		log.info("C301Action edit.........");
	//		if(bean!=null){
	//			String msg="1";
	//			try {
	//					BaseUserBean user = (BaseUserBean) request.getSession().getAttribute(CommonConstant.SESSION_KEY_USER_CUSTOMER_SERVICE);
	//					if(user!=null){
	//						bean.setCreate_ip(getIpAddr());
	//						bean.setCreate_id(user.getUser_id());
	//						bean.setUpdate_ip(getIpAddr());
	//						bean.setUpdate_id(user.getUser_id());
	//					}
	//					msg=consultMsgManager.saveOrUpdateData(bean);
	//			} catch (Exception e) {
	//				msg=e.getMessage();
	//			}
	//			request.setAttribute("msg",msg);
	//		}else{
	//			request.setAttribute("msg", "信息保存失败!");
	//		}
	//		return SUCCESS;
	//	}
	//	/**
	//	 * <p>
	//	 * 删除。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>删除。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String del() {
	//		log.info("C301Action del.........");
	//		
	//		String id=request.getParameter("id");
	//		TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//		bean1.setId(id);
	//		String msg="1";
	//		try {
	//			msg=consultMsgManager.deleteDataById(bean1);
	//		} catch (Exception e) {
	//			msg=e.getMessage();
	//		}
	//		request.setAttribute("msg",msg);
	//		return SUCCESS;
	//	}
	
	
	
	/**
	 * BEAN类  咨询信息取得
	 * @return BEAN类  咨询信息
	 */
	//	/**
	//	 * <p>
	//	 * 编辑。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>编辑。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String edit() {
	//		log.info("C301Action edit.........");
	//		String id=request.getParameter("id");
	//		if(id!=null){
	//			TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//			bean1.setId(id);
	//			bean=consultMsgManager.findDataById(bean1);
	//		}else{
	//			SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
	//			request.setAttribute("date", sdf.format(new Date()));
	//		}
	//		return "edit";
	//	}
	//	/**
	//	 * <p>
	//	 * 信息保存
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>新增。</div>
	//	 * <div>修改。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String save() {
	//		log.info("C301Action edit.........");
	//		if(bean!=null){
	//			String msg="1";
	//			try {
	//					BaseUserBean user = (BaseUserBean) request.getSession().getAttribute(CommonConstant.SESSION_KEY_USER_CUSTOMER_SERVICE);
	//					if(user!=null){
	//						bean.setCreate_ip(getIpAddr());
	//						bean.setCreate_id(user.getUser_id());
	//						bean.setUpdate_ip(getIpAddr());
	//						bean.setUpdate_id(user.getUser_id());
	//					}
	//					msg=consultMsgManager.saveOrUpdateData(bean);
	//			} catch (Exception e) {
	//				msg=e.getMessage();
	//			}
	//			request.setAttribute("msg",msg);
	//		}else{
	//			request.setAttribute("msg", "信息保存失败!");
	//		}
	//		return SUCCESS;
	//	}
	//	/**
	//	 * <p>
	//	 * 删除。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>删除。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String del() {
	//		log.info("C301Action del.........");
	//		
	//		String id=request.getParameter("id");
	//		TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//		bean1.setId(id);
	//		String msg="1";
	//		try {
	//			msg=consultMsgManager.deleteDataById(bean1);
	//		} catch (Exception e) {
	//			msg=e.getMessage();
	//		}
	//		request.setAttribute("msg",msg);
	//		return SUCCESS;
	//	}
	
	
	
	/**
	 * BEAN类  咨询信息设定
	 * @param bean BEAN类  咨询信息
	 */
	//	/**
	//	 * <p>
	//	 * 编辑。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>编辑。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String edit() {
	//		log.info("C301Action edit.........");
	//		String id=request.getParameter("id");
	//		if(id!=null){
	//			TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//			bean1.setId(id);
	//			bean=consultMsgManager.findDataById(bean1);
	//		}else{
	//			SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
	//			request.setAttribute("date", sdf.format(new Date()));
	//		}
	//		return "edit";
	//	}
	//	/**
	//	 * <p>
	//	 * 信息保存
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>新增。</div>
	//	 * <div>修改。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String save() {
	//		log.info("C301Action edit.........");
	//		if(bean!=null){
	//			String msg="1";
	//			try {
	//					BaseUserBean user = (BaseUserBean) request.getSession().getAttribute(CommonConstant.SESSION_KEY_USER_CUSTOMER_SERVICE);
	//					if(user!=null){
	//						bean.setCreate_ip(getIpAddr());
	//						bean.setCreate_id(user.getUser_id());
	//						bean.setUpdate_ip(getIpAddr());
	//						bean.setUpdate_id(user.getUser_id());
	//					}
	//					msg=consultMsgManager.saveOrUpdateData(bean);
	//			} catch (Exception e) {
	//				msg=e.getMessage();
	//			}
	//			request.setAttribute("msg",msg);
	//		}else{
	//			request.setAttribute("msg", "信息保存失败!");
	//		}
	//		return SUCCESS;
	//	}
	//	/**
	//	 * <p>
	//	 * 删除。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>删除。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String del() {
	//		log.info("C301Action del.........");
	//		
	//		String id=request.getParameter("id");
	//		TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//		bean1.setId(id);
	//		String msg="1";
	//		try {
	//			msg=consultMsgManager.deleteDataById(bean1);
	//		} catch (Exception e) {
	//			msg=e.getMessage();
	//		}
	//		request.setAttribute("msg",msg);
	//		return SUCCESS;
	//	}
	
	
	
	/**
	 * BEAN类  咨询信息取得
	 * @return BEAN类  咨询信息
	 */
	//	/**
	//	 * <p>
	//	 * 编辑。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>编辑。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String edit() {
	//		log.info("C301Action edit.........");
	//		String id=request.getParameter("id");
	//		if(id!=null){
	//			TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//			bean1.setId(id);
	//			bean=consultMsgManager.findDataById(bean1);
	//		}else{
	//			SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
	//			request.setAttribute("date", sdf.format(new Date()));
	//		}
	//		return "edit";
	//	}
	//	/**
	//	 * <p>
	//	 * 信息保存
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>新增。</div>
	//	 * <div>修改。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String save() {
	//		log.info("C301Action edit.........");
	//		if(bean!=null){
	//			String msg="1";
	//			try {
	//					BaseUserBean user = (BaseUserBean) request.getSession().getAttribute(CommonConstant.SESSION_KEY_USER_CUSTOMER_SERVICE);
	//					if(user!=null){
	//						bean.setCreate_ip(getIpAddr());
	//						bean.setCreate_id(user.getUser_id());
	//						bean.setUpdate_ip(getIpAddr());
	//						bean.setUpdate_id(user.getUser_id());
	//					}
	//					msg=consultMsgManager.saveOrUpdateData(bean);
	//			} catch (Exception e) {
	//				msg=e.getMessage();
	//			}
	//			request.setAttribute("msg",msg);
	//		}else{
	//			request.setAttribute("msg", "信息保存失败!");
	//		}
	//		return SUCCESS;
	//	}
	//	/**
	//	 * <p>
	//	 * 删除。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>删除。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String del() {
	//		log.info("C301Action del.........");
	//		
	//		String id=request.getParameter("id");
	//		TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//		bean1.setId(id);
	//		String msg="1";
	//		try {
	//			msg=consultMsgManager.deleteDataById(bean1);
	//		} catch (Exception e) {
	//			msg=e.getMessage();
	//		}
	//		request.setAttribute("msg",msg);
	//		return SUCCESS;
	//	}
	
	
	
	/**
	 * BEAN类  咨询信息取得
	 * @return BEAN类  咨询信息
	 */
	//	/**
	//	 * <p>
	//	 * 编辑。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>编辑。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String edit() {
	//		log.info("C301Action edit.........");
	//		String id=request.getParameter("id");
	//		if(id!=null){
	//			TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//			bean1.setId(id);
	//			bean=consultMsgManager.findDataById(bean1);
	//		}else{
	//			SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
	//			request.setAttribute("date", sdf.format(new Date()));
	//		}
	//		return "edit";
	//	}
	//	/**
	//	 * <p>
	//	 * 信息保存
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>新增。</div>
	//	 * <div>修改。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String save() {
	//		log.info("C301Action edit.........");
	//		if(bean!=null){
	//			String msg="1";
	//			try {
	//					BaseUserBean user = (BaseUserBean) request.getSession().getAttribute(CommonConstant.SESSION_KEY_USER_CUSTOMER_SERVICE);
	//					if(user!=null){
	//						bean.setCreate_ip(getIpAddr());
	//						bean.setCreate_id(user.getUser_id());
	//						bean.setUpdate_ip(getIpAddr());
	//						bean.setUpdate_id(user.getUser_id());
	//					}
	//					msg=consultMsgManager.saveOrUpdateData(bean);
	//			} catch (Exception e) {
	//				msg=e.getMessage();
	//			}
	//			request.setAttribute("msg",msg);
	//		}else{
	//			request.setAttribute("msg", "信息保存失败!");
	//		}
	//		return SUCCESS;
	//	}
	//	/**
	//	 * <p>
	//	 * 删除。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>删除。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String del() {
	//		log.info("C301Action del.........");
	//		
	//		String id=request.getParameter("id");
	//		TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//		bean1.setId(id);
	//		String msg="1";
	//		try {
	//			msg=consultMsgManager.deleteDataById(bean1);
	//		} catch (Exception e) {
	//			msg=e.getMessage();
	//		}
	//		request.setAttribute("msg",msg);
	//		return SUCCESS;
	//	}
	
	
	
	/**
	 * BEAN类  咨询信息设定
	 * @param bean BEAN类  咨询信息
	 */
	//	/**
	//	 * <p>
	//	 * 编辑。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>编辑。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String edit() {
	//		log.info("C301Action edit.........");
	//		String id=request.getParameter("id");
	//		if(id!=null){
	//			TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//			bean1.setId(id);
	//			bean=consultMsgManager.findDataById(bean1);
	//		}else{
	//			SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
	//			request.setAttribute("date", sdf.format(new Date()));
	//		}
	//		return "edit";
	//	}
	//	/**
	//	 * <p>
	//	 * 信息保存
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>新增。</div>
	//	 * <div>修改。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String save() {
	//		log.info("C301Action edit.........");
	//		if(bean!=null){
	//			String msg="1";
	//			try {
	//					BaseUserBean user = (BaseUserBean) request.getSession().getAttribute(CommonConstant.SESSION_KEY_USER_CUSTOMER_SERVICE);
	//					if(user!=null){
	//						bean.setCreate_ip(getIpAddr());
	//						bean.setCreate_id(user.getUser_id());
	//						bean.setUpdate_ip(getIpAddr());
	//						bean.setUpdate_id(user.getUser_id());
	//					}
	//					msg=consultMsgManager.saveOrUpdateData(bean);
	//			} catch (Exception e) {
	//				msg=e.getMessage();
	//			}
	//			request.setAttribute("msg",msg);
	//		}else{
	//			request.setAttribute("msg", "信息保存失败!");
	//		}
	//		return SUCCESS;
	//	}
	//	/**
	//	 * <p>
	//	 * 删除。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>删除。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String del() {
	//		log.info("C301Action del.........");
	//		
	//		String id=request.getParameter("id");
	//		TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//		bean1.setId(id);
	//		String msg="1";
	//		try {
	//			msg=consultMsgManager.deleteDataById(bean1);
	//		} catch (Exception e) {
	//			msg=e.getMessage();
	//		}
	//		request.setAttribute("msg",msg);
	//		return SUCCESS;
	//	}
	
	
	
	/**
	 * BEAN类  咨询信息取得
	 * @return BEAN类  咨询信息
	 */
	//	/**
	//	 * <p>
	//	 * 编辑。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>编辑。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String edit() {
	//		log.info("C301Action edit.........");
	//		String id=request.getParameter("id");
	//		if(id!=null){
	//			TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//			bean1.setId(id);
	//			bean=consultMsgManager.findDataById(bean1);
	//		}else{
	//			SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
	//			request.setAttribute("date", sdf.format(new Date()));
	//		}
	//		return "edit";
	//	}
	//	/**
	//	 * <p>
	//	 * 信息保存
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>新增。</div>
	//	 * <div>修改。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String save() {
	//		log.info("C301Action edit.........");
	//		if(bean!=null){
	//			String msg="1";
	//			try {
	//					BaseUserBean user = (BaseUserBean) request.getSession().getAttribute(CommonConstant.SESSION_KEY_USER_CUSTOMER_SERVICE);
	//					if(user!=null){
	//						bean.setCreate_ip(getIpAddr());
	//						bean.setCreate_id(user.getUser_id());
	//						bean.setUpdate_ip(getIpAddr());
	//						bean.setUpdate_id(user.getUser_id());
	//					}
	//					msg=consultMsgManager.saveOrUpdateData(bean);
	//			} catch (Exception e) {
	//				msg=e.getMessage();
	//			}
	//			request.setAttribute("msg",msg);
	//		}else{
	//			request.setAttribute("msg", "信息保存失败!");
	//		}
	//		return SUCCESS;
	//	}
	//	/**
	//	 * <p>
	//	 * 删除。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>删除。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String del() {
	//		log.info("C301Action del.........");
	//		
	//		String id=request.getParameter("id");
	//		TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//		bean1.setId(id);
	//		String msg="1";
	//		try {
	//			msg=consultMsgManager.deleteDataById(bean1);
	//		} catch (Exception e) {
	//			msg=e.getMessage();
	//		}
	//		request.setAttribute("msg",msg);
	//		return SUCCESS;
	//	}
	
	
	
	/**
	 * BEAN类  咨询信息设定
	 * @param bean BEAN类  咨询信息
	 */
	//	/**
	//	 * <p>
	//	 * 编辑。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>编辑。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String edit() {
	//		log.info("C301Action edit.........");
	//		String id=request.getParameter("id");
	//		if(id!=null){
	//			TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//			bean1.setId(id);
	//			bean=consultMsgManager.findDataById(bean1);
	//		}else{
	//			SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
	//			request.setAttribute("date", sdf.format(new Date()));
	//		}
	//		return "edit";
	//	}
	//	/**
	//	 * <p>
	//	 * 信息保存
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>新增。</div>
	//	 * <div>修改。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String save() {
	//		log.info("C301Action edit.........");
	//		if(bean!=null){
	//			String msg="1";
	//			try {
	//					BaseUserBean user = (BaseUserBean) request.getSession().getAttribute(CommonConstant.SESSION_KEY_USER_CUSTOMER_SERVICE);
	//					if(user!=null){
	//						bean.setCreate_ip(getIpAddr());
	//						bean.setCreate_id(user.getUser_id());
	//						bean.setUpdate_ip(getIpAddr());
	//						bean.setUpdate_id(user.getUser_id());
	//					}
	//					msg=consultMsgManager.saveOrUpdateData(bean);
	//			} catch (Exception e) {
	//				msg=e.getMessage();
	//			}
	//			request.setAttribute("msg",msg);
	//		}else{
	//			request.setAttribute("msg", "信息保存失败!");
	//		}
	//		return SUCCESS;
	//	}
	//	/**
	//	 * <p>
	//	 * 删除。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>删除。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String del() {
	//		log.info("C301Action del.........");
	//		
	//		String id=request.getParameter("id");
	//		TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//		bean1.setId(id);
	//		String msg="1";
	//		try {
	//			msg=consultMsgManager.deleteDataById(bean1);
	//		} catch (Exception e) {
	//			msg=e.getMessage();
	//		}
	//		request.setAttribute("msg",msg);
	//		return SUCCESS;
	//	}
	
	
	
	/**
	 * BEAN类  咨询信息取得
	 * @return BEAN类  咨询信息
	 */
	//	/**
	//	 * <p>
	//	 * 编辑。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>编辑。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String edit() {
	//		log.info("C301Action edit.........");
	//		String id=request.getParameter("id");
	//		if(id!=null){
	//			TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//			bean1.setId(id);
	//			bean=consultMsgManager.findDataById(bean1);
	//		}else{
	//			SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
	//			request.setAttribute("date", sdf.format(new Date()));
	//		}
	//		return "edit";
	//	}
	//	/**
	//	 * <p>
	//	 * 信息保存
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>新增。</div>
	//	 * <div>修改。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String save() {
	//		log.info("C301Action edit.........");
	//		if(bean!=null){
	//			String msg="1";
	//			try {
	//					BaseUserBean user = (BaseUserBean) request.getSession().getAttribute(CommonConstant.SESSION_KEY_USER_CUSTOMER_SERVICE);
	//					if(user!=null){
	//						bean.setCreate_ip(getIpAddr());
	//						bean.setCreate_id(user.getUser_id());
	//						bean.setUpdate_ip(getIpAddr());
	//						bean.setUpdate_id(user.getUser_id());
	//					}
	//					msg=consultMsgManager.saveOrUpdateData(bean);
	//			} catch (Exception e) {
	//				msg=e.getMessage();
	//			}
	//			request.setAttribute("msg",msg);
	//		}else{
	//			request.setAttribute("msg", "信息保存失败!");
	//		}
	//		return SUCCESS;
	//	}
	//	/**
	//	 * <p>
	//	 * 删除。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>删除。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String del() {
	//		log.info("C301Action del.........");
	//		
	//		String id=request.getParameter("id");
	//		TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//		bean1.setId(id);
	//		String msg="1";
	//		try {
	//			msg=consultMsgManager.deleteDataById(bean1);
	//		} catch (Exception e) {
	//			msg=e.getMessage();
	//		}
	//		request.setAttribute("msg",msg);
	//		return SUCCESS;
	//	}
	
	
	
	/**
	 * BEAN类  咨询信息取得
	 * @return BEAN类  咨询信息
	 */
	//	/**
	//	 * <p>
	//	 * 编辑。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>编辑。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String edit() {
	//		log.info("C301Action edit.........");
	//		String id=request.getParameter("id");
	//		if(id!=null){
	//			TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//			bean1.setId(id);
	//			bean=consultMsgManager.findDataById(bean1);
	//		}else{
	//			SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
	//			request.setAttribute("date", sdf.format(new Date()));
	//		}
	//		return "edit";
	//	}
	//	/**
	//	 * <p>
	//	 * 信息保存
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>新增。</div>
	//	 * <div>修改。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String save() {
	//		log.info("C301Action edit.........");
	//		if(bean!=null){
	//			String msg="1";
	//			try {
	//					BaseUserBean user = (BaseUserBean) request.getSession().getAttribute(CommonConstant.SESSION_KEY_USER_CUSTOMER_SERVICE);
	//					if(user!=null){
	//						bean.setCreate_ip(getIpAddr());
	//						bean.setCreate_id(user.getUser_id());
	//						bean.setUpdate_ip(getIpAddr());
	//						bean.setUpdate_id(user.getUser_id());
	//					}
	//					msg=consultMsgManager.saveOrUpdateData(bean);
	//			} catch (Exception e) {
	//				msg=e.getMessage();
	//			}
	//			request.setAttribute("msg",msg);
	//		}else{
	//			request.setAttribute("msg", "信息保存失败!");
	//		}
	//		return SUCCESS;
	//	}
	//	/**
	//	 * <p>
	//	 * 删除。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>删除。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String del() {
	//		log.info("C301Action del.........");
	//		
	//		String id=request.getParameter("id");
	//		TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//		bean1.setId(id);
	//		String msg="1";
	//		try {
	//			msg=consultMsgManager.deleteDataById(bean1);
	//		} catch (Exception e) {
	//			msg=e.getMessage();
	//		}
	//		request.setAttribute("msg",msg);
	//		return SUCCESS;
	//	}
	
	
	
	/**
	 * BEAN类  咨询信息设定
	 * @param bean BEAN类  咨询信息
	 */
	//	/**
	//	 * <p>
	//	 * 编辑。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>编辑。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String edit() {
	//		log.info("C301Action edit.........");
	//		String id=request.getParameter("id");
	//		if(id!=null){
	//			TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//			bean1.setId(id);
	//			bean=consultMsgManager.findDataById(bean1);
	//		}else{
	//			SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
	//			request.setAttribute("date", sdf.format(new Date()));
	//		}
	//		return "edit";
	//	}
	//	/**
	//	 * <p>
	//	 * 信息保存
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>新增。</div>
	//	 * <div>修改。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String save() {
	//		log.info("C301Action edit.........");
	//		if(bean!=null){
	//			String msg="1";
	//			try {
	//					BaseUserBean user = (BaseUserBean) request.getSession().getAttribute(CommonConstant.SESSION_KEY_USER_CUSTOMER_SERVICE);
	//					if(user!=null){
	//						bean.setCreate_ip(getIpAddr());
	//						bean.setCreate_id(user.getUser_id());
	//						bean.setUpdate_ip(getIpAddr());
	//						bean.setUpdate_id(user.getUser_id());
	//					}
	//					msg=consultMsgManager.saveOrUpdateData(bean);
	//			} catch (Exception e) {
	//				msg=e.getMessage();
	//			}
	//			request.setAttribute("msg",msg);
	//		}else{
	//			request.setAttribute("msg", "信息保存失败!");
	//		}
	//		return SUCCESS;
	//	}
	//	/**
	//	 * <p>
	//	 * 删除。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>删除。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String del() {
	//		log.info("C301Action del.........");
	//		
	//		String id=request.getParameter("id");
	//		TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//		bean1.setId(id);
	//		String msg="1";
	//		try {
	//			msg=consultMsgManager.deleteDataById(bean1);
	//		} catch (Exception e) {
	//			msg=e.getMessage();
	//		}
	//		request.setAttribute("msg",msg);
	//		return SUCCESS;
	//	}
	
	
	
	/**
	 * BEAN类  咨询信息取得
	 * @return BEAN类  咨询信息
	 */
	//	/**
	//	 * <p>
	//	 * 编辑。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>编辑。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String edit() {
	//		log.info("C301Action edit.........");
	//		String id=request.getParameter("id");
	//		if(id!=null){
	//			TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//			bean1.setId(id);
	//			bean=consultMsgManager.findDataById(bean1);
	//		}else{
	//			SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
	//			request.setAttribute("date", sdf.format(new Date()));
	//		}
	//		return "edit";
	//	}
	//	/**
	//	 * <p>
	//	 * 信息保存
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>新增。</div>
	//	 * <div>修改。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String save() {
	//		log.info("C301Action edit.........");
	//		if(bean!=null){
	//			String msg="1";
	//			try {
	//					BaseUserBean user = (BaseUserBean) request.getSession().getAttribute(CommonConstant.SESSION_KEY_USER_CUSTOMER_SERVICE);
	//					if(user!=null){
	//						bean.setCreate_ip(getIpAddr());
	//						bean.setCreate_id(user.getUser_id());
	//						bean.setUpdate_ip(getIpAddr());
	//						bean.setUpdate_id(user.getUser_id());
	//					}
	//					msg=consultMsgManager.saveOrUpdateData(bean);
	//			} catch (Exception e) {
	//				msg=e.getMessage();
	//			}
	//			request.setAttribute("msg",msg);
	//		}else{
	//			request.setAttribute("msg", "信息保存失败!");
	//		}
	//		return SUCCESS;
	//	}
	//	/**
	//	 * <p>
	//	 * 删除。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>删除。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String del() {
	//		log.info("C301Action del.........");
	//		
	//		String id=request.getParameter("id");
	//		TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//		bean1.setId(id);
	//		String msg="1";
	//		try {
	//			msg=consultMsgManager.deleteDataById(bean1);
	//		} catch (Exception e) {
	//			msg=e.getMessage();
	//		}
	//		request.setAttribute("msg",msg);
	//		return SUCCESS;
	//	}
	
	
	
	/**
	 * BEAN类  咨询信息取得
	 * @return BEAN类  咨询信息
	 */
	//	/**
	//	 * <p>
	//	 * 编辑。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>编辑。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String edit() {
	//		log.info("C301Action edit.........");
	//		String id=request.getParameter("id");
	//		if(id!=null){
	//			TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//			bean1.setId(id);
	//			bean=consultMsgManager.findDataById(bean1);
	//		}else{
	//			SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
	//			request.setAttribute("date", sdf.format(new Date()));
	//		}
	//		return "edit";
	//	}
	//	/**
	//	 * <p>
	//	 * 信息保存
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>新增。</div>
	//	 * <div>修改。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String save() {
	//		log.info("C301Action edit.........");
	//		if(bean!=null){
	//			String msg="1";
	//			try {
	//					BaseUserBean user = (BaseUserBean) request.getSession().getAttribute(CommonConstant.SESSION_KEY_USER_CUSTOMER_SERVICE);
	//					if(user!=null){
	//						bean.setCreate_ip(getIpAddr());
	//						bean.setCreate_id(user.getUser_id());
	//						bean.setUpdate_ip(getIpAddr());
	//						bean.setUpdate_id(user.getUser_id());
	//					}
	//					msg=consultMsgManager.saveOrUpdateData(bean);
	//			} catch (Exception e) {
	//				msg=e.getMessage();
	//			}
	//			request.setAttribute("msg",msg);
	//		}else{
	//			request.setAttribute("msg", "信息保存失败!");
	//		}
	//		return SUCCESS;
	//	}
	//	/**
	//	 * <p>
	//	 * 删除。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>删除。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String del() {
	//		log.info("C301Action del.........");
	//		
	//		String id=request.getParameter("id");
	//		TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//		bean1.setId(id);
	//		String msg="1";
	//		try {
	//			msg=consultMsgManager.deleteDataById(bean1);
	//		} catch (Exception e) {
	//			msg=e.getMessage();
	//		}
	//		request.setAttribute("msg",msg);
	//		return SUCCESS;
	//	}
	
	
	
	/**
	 * BEAN类  咨询信息设定
	 * @param bean BEAN类  咨询信息
	 */
	//	/**
	//	 * <p>
	//	 * 编辑。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>编辑。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String edit() {
	//		log.info("C301Action edit.........");
	//		String id=request.getParameter("id");
	//		if(id!=null){
	//			TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//			bean1.setId(id);
	//			bean=consultMsgManager.findDataById(bean1);
	//		}else{
	//			SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
	//			request.setAttribute("date", sdf.format(new Date()));
	//		}
	//		return "edit";
	//	}
	//	/**
	//	 * <p>
	//	 * 信息保存
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>新增。</div>
	//	 * <div>修改。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String save() {
	//		log.info("C301Action edit.........");
	//		if(bean!=null){
	//			String msg="1";
	//			try {
	//					BaseUserBean user = (BaseUserBean) request.getSession().getAttribute(CommonConstant.SESSION_KEY_USER_CUSTOMER_SERVICE);
	//					if(user!=null){
	//						bean.setCreate_ip(getIpAddr());
	//						bean.setCreate_id(user.getUser_id());
	//						bean.setUpdate_ip(getIpAddr());
	//						bean.setUpdate_id(user.getUser_id());
	//					}
	//					msg=consultMsgManager.saveOrUpdateData(bean);
	//			} catch (Exception e) {
	//				msg=e.getMessage();
	//			}
	//			request.setAttribute("msg",msg);
	//		}else{
	//			request.setAttribute("msg", "信息保存失败!");
	//		}
	//		return SUCCESS;
	//	}
	//	/**
	//	 * <p>
	//	 * 删除。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>删除。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String del() {
	//		log.info("C301Action del.........");
	//		
	//		String id=request.getParameter("id");
	//		TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//		bean1.setId(id);
	//		String msg="1";
	//		try {
	//			msg=consultMsgManager.deleteDataById(bean1);
	//		} catch (Exception e) {
	//			msg=e.getMessage();
	//		}
	//		request.setAttribute("msg",msg);
	//		return SUCCESS;
	//	}
	
	
	
	/**
	 * BEAN类  咨询信息取得
	 * @return BEAN类  咨询信息
	 */
	//	/**
	//	 * <p>
	//	 * 编辑。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>编辑。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String edit() {
	//		log.info("C301Action edit.........");
	//		String id=request.getParameter("id");
	//		if(id!=null){
	//			TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//			bean1.setId(id);
	//			bean=consultMsgManager.findDataById(bean1);
	//		}else{
	//			SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
	//			request.setAttribute("date", sdf.format(new Date()));
	//		}
	//		return "edit";
	//	}
	//	/**
	//	 * <p>
	//	 * 信息保存
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>新增。</div>
	//	 * <div>修改。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String save() {
	//		log.info("C301Action edit.........");
	//		if(bean!=null){
	//			String msg="1";
	//			try {
	//					BaseUserBean user = (BaseUserBean) request.getSession().getAttribute(CommonConstant.SESSION_KEY_USER_CUSTOMER_SERVICE);
	//					if(user!=null){
	//						bean.setCreate_ip(getIpAddr());
	//						bean.setCreate_id(user.getUser_id());
	//						bean.setUpdate_ip(getIpAddr());
	//						bean.setUpdate_id(user.getUser_id());
	//					}
	//					msg=consultMsgManager.saveOrUpdateData(bean);
	//			} catch (Exception e) {
	//				msg=e.getMessage();
	//			}
	//			request.setAttribute("msg",msg);
	//		}else{
	//			request.setAttribute("msg", "信息保存失败!");
	//		}
	//		return SUCCESS;
	//	}
	//	/**
	//	 * <p>
	//	 * 删除。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>删除。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String del() {
	//		log.info("C301Action del.........");
	//		
	//		String id=request.getParameter("id");
	//		TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//		bean1.setId(id);
	//		String msg="1";
	//		try {
	//			msg=consultMsgManager.deleteDataById(bean1);
	//		} catch (Exception e) {
	//			msg=e.getMessage();
	//		}
	//		request.setAttribute("msg",msg);
	//		return SUCCESS;
	//	}
	
	
	
	/**
	 * BEAN类  咨询信息设定
	 * @param bean BEAN类  咨询信息
	 */
	//	/**
	//	 * <p>
	//	 * 编辑。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>编辑。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String edit() {
	//		log.info("C301Action edit.........");
	//		String id=request.getParameter("id");
	//		if(id!=null){
	//			TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//			bean1.setId(id);
	//			bean=consultMsgManager.findDataById(bean1);
	//		}else{
	//			SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
	//			request.setAttribute("date", sdf.format(new Date()));
	//		}
	//		return "edit";
	//	}
	//	/**
	//	 * <p>
	//	 * 信息保存
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>新增。</div>
	//	 * <div>修改。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String save() {
	//		log.info("C301Action edit.........");
	//		if(bean!=null){
	//			String msg="1";
	//			try {
	//					BaseUserBean user = (BaseUserBean) request.getSession().getAttribute(CommonConstant.SESSION_KEY_USER_CUSTOMER_SERVICE);
	//					if(user!=null){
	//						bean.setCreate_ip(getIpAddr());
	//						bean.setCreate_id(user.getUser_id());
	//						bean.setUpdate_ip(getIpAddr());
	//						bean.setUpdate_id(user.getUser_id());
	//					}
	//					msg=consultMsgManager.saveOrUpdateData(bean);
	//			} catch (Exception e) {
	//				msg=e.getMessage();
	//			}
	//			request.setAttribute("msg",msg);
	//		}else{
	//			request.setAttribute("msg", "信息保存失败!");
	//		}
	//		return SUCCESS;
	//	}
	//	/**
	//	 * <p>
	//	 * 删除。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>删除。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String del() {
	//		log.info("C301Action del.........");
	//		
	//		String id=request.getParameter("id");
	//		TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//		bean1.setId(id);
	//		String msg="1";
	//		try {
	//			msg=consultMsgManager.deleteDataById(bean1);
	//		} catch (Exception e) {
	//			msg=e.getMessage();
	//		}
	//		request.setAttribute("msg",msg);
	//		return SUCCESS;
	//	}
	
	
	
	/**
	 * BEAN类  咨询信息取得
	 * @return BEAN类  咨询信息
	 */
	//	/**
	//	 * <p>
	//	 * 编辑。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>编辑。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String edit() {
	//		log.info("C301Action edit.........");
	//		String id=request.getParameter("id");
	//		if(id!=null){
	//			TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//			bean1.setId(id);
	//			bean=consultMsgManager.findDataById(bean1);
	//		}else{
	//			SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
	//			request.setAttribute("date", sdf.format(new Date()));
	//		}
	//		return "edit";
	//	}
	//	/**
	//	 * <p>
	//	 * 信息保存
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>新增。</div>
	//	 * <div>修改。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String save() {
	//		log.info("C301Action edit.........");
	//		if(bean!=null){
	//			String msg="1";
	//			try {
	//					BaseUserBean user = (BaseUserBean) request.getSession().getAttribute(CommonConstant.SESSION_KEY_USER_CUSTOMER_SERVICE);
	//					if(user!=null){
	//						bean.setCreate_ip(getIpAddr());
	//						bean.setCreate_id(user.getUser_id());
	//						bean.setUpdate_ip(getIpAddr());
	//						bean.setUpdate_id(user.getUser_id());
	//					}
	//					msg=consultMsgManager.saveOrUpdateData(bean);
	//			} catch (Exception e) {
	//				msg=e.getMessage();
	//			}
	//			request.setAttribute("msg",msg);
	//		}else{
	//			request.setAttribute("msg", "信息保存失败!");
	//		}
	//		return SUCCESS;
	//	}
	//	/**
	//	 * <p>
	//	 * 删除。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>删除。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String del() {
	//		log.info("C301Action del.........");
	//		
	//		String id=request.getParameter("id");
	//		TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//		bean1.setId(id);
	//		String msg="1";
	//		try {
	//			msg=consultMsgManager.deleteDataById(bean1);
	//		} catch (Exception e) {
	//			msg=e.getMessage();
	//		}
	//		request.setAttribute("msg",msg);
	//		return SUCCESS;
	//	}
	
	
	
	/**
	 * BEAN类  咨询信息取得
	 * @return BEAN类  咨询信息
	 */
	//	/**
	//	 * <p>
	//	 * 编辑。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>编辑。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String edit() {
	//		log.info("C301Action edit.........");
	//		String id=request.getParameter("id");
	//		if(id!=null){
	//			TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//			bean1.setId(id);
	//			bean=consultMsgManager.findDataById(bean1);
	//		}else{
	//			SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
	//			request.setAttribute("date", sdf.format(new Date()));
	//		}
	//		return "edit";
	//	}
	//	/**
	//	 * <p>
	//	 * 信息保存
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>新增。</div>
	//	 * <div>修改。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String save() {
	//		log.info("C301Action edit.........");
	//		if(bean!=null){
	//			String msg="1";
	//			try {
	//					BaseUserBean user = (BaseUserBean) request.getSession().getAttribute(CommonConstant.SESSION_KEY_USER_CUSTOMER_SERVICE);
	//					if(user!=null){
	//						bean.setCreate_ip(getIpAddr());
	//						bean.setCreate_id(user.getUser_id());
	//						bean.setUpdate_ip(getIpAddr());
	//						bean.setUpdate_id(user.getUser_id());
	//					}
	//					msg=consultMsgManager.saveOrUpdateData(bean);
	//			} catch (Exception e) {
	//				msg=e.getMessage();
	//			}
	//			request.setAttribute("msg",msg);
	//		}else{
	//			request.setAttribute("msg", "信息保存失败!");
	//		}
	//		return SUCCESS;
	//	}
	//	/**
	//	 * <p>
	//	 * 删除。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>删除。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String del() {
	//		log.info("C301Action del.........");
	//		
	//		String id=request.getParameter("id");
	//		TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//		bean1.setId(id);
	//		String msg="1";
	//		try {
	//			msg=consultMsgManager.deleteDataById(bean1);
	//		} catch (Exception e) {
	//			msg=e.getMessage();
	//		}
	//		request.setAttribute("msg",msg);
	//		return SUCCESS;
	//	}
	
	
	
	/**
	 * BEAN类  咨询信息设定
	 * @param bean BEAN类  咨询信息
	 */
	//	/**
	//	 * <p>
	//	 * 编辑。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>编辑。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String edit() {
	//		log.info("C301Action edit.........");
	//		String id=request.getParameter("id");
	//		if(id!=null){
	//			TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//			bean1.setId(id);
	//			bean=consultMsgManager.findDataById(bean1);
	//		}else{
	//			SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
	//			request.setAttribute("date", sdf.format(new Date()));
	//		}
	//		return "edit";
	//	}
	//	/**
	//	 * <p>
	//	 * 信息保存
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>新增。</div>
	//	 * <div>修改。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String save() {
	//		log.info("C301Action edit.........");
	//		if(bean!=null){
	//			String msg="1";
	//			try {
	//					BaseUserBean user = (BaseUserBean) request.getSession().getAttribute(CommonConstant.SESSION_KEY_USER_CUSTOMER_SERVICE);
	//					if(user!=null){
	//						bean.setCreate_ip(getIpAddr());
	//						bean.setCreate_id(user.getUser_id());
	//						bean.setUpdate_ip(getIpAddr());
	//						bean.setUpdate_id(user.getUser_id());
	//					}
	//					msg=consultMsgManager.saveOrUpdateData(bean);
	//			} catch (Exception e) {
	//				msg=e.getMessage();
	//			}
	//			request.setAttribute("msg",msg);
	//		}else{
	//			request.setAttribute("msg", "信息保存失败!");
	//		}
	//		return SUCCESS;
	//	}
	//	/**
	//	 * <p>
	//	 * 删除。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>删除。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String del() {
	//		log.info("C301Action del.........");
	//		
	//		String id=request.getParameter("id");
	//		TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//		bean1.setId(id);
	//		String msg="1";
	//		try {
	//			msg=consultMsgManager.deleteDataById(bean1);
	//		} catch (Exception e) {
	//			msg=e.getMessage();
	//		}
	//		request.setAttribute("msg",msg);
	//		return SUCCESS;
	//	}
	
	
	
	/**
	 * BEAN类  咨询信息取得
	 * @return BEAN类  咨询信息
	 */
	//	/**
	//	 * <p>
	//	 * 编辑。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>编辑。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String edit() {
	//		log.info("C301Action edit.........");
	//		String id=request.getParameter("id");
	//		if(id!=null){
	//			TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//			bean1.setId(id);
	//			bean=consultMsgManager.findDataById(bean1);
	//		}else{
	//			SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
	//			request.setAttribute("date", sdf.format(new Date()));
	//		}
	//		return "edit";
	//	}
	//	/**
	//	 * <p>
	//	 * 信息保存
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>新增。</div>
	//	 * <div>修改。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String save() {
	//		log.info("C301Action edit.........");
	//		if(bean!=null){
	//			String msg="1";
	//			try {
	//					BaseUserBean user = (BaseUserBean) request.getSession().getAttribute(CommonConstant.SESSION_KEY_USER_CUSTOMER_SERVICE);
	//					if(user!=null){
	//						bean.setCreate_ip(getIpAddr());
	//						bean.setCreate_id(user.getUser_id());
	//						bean.setUpdate_ip(getIpAddr());
	//						bean.setUpdate_id(user.getUser_id());
	//					}
	//					msg=consultMsgManager.saveOrUpdateData(bean);
	//			} catch (Exception e) {
	//				msg=e.getMessage();
	//			}
	//			request.setAttribute("msg",msg);
	//		}else{
	//			request.setAttribute("msg", "信息保存失败!");
	//		}
	//		return SUCCESS;
	//	}
	//	/**
	//	 * <p>
	//	 * 删除。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>删除。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String del() {
	//		log.info("C301Action del.........");
	//		
	//		String id=request.getParameter("id");
	//		TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//		bean1.setId(id);
	//		String msg="1";
	//		try {
	//			msg=consultMsgManager.deleteDataById(bean1);
	//		} catch (Exception e) {
	//			msg=e.getMessage();
	//		}
	//		request.setAttribute("msg",msg);
	//		return SUCCESS;
	//	}
	
	
	
	/**
	 * BEAN类  咨询信息设定
	 * @param bean BEAN类  咨询信息
	 */
	//	/**
	//	 * <p>
	//	 * 编辑。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>编辑。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String edit() {
	//		log.info("C301Action edit.........");
	//		String id=request.getParameter("id");
	//		if(id!=null){
	//			TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//			bean1.setId(id);
	//			bean=consultMsgManager.findDataById(bean1);
	//		}else{
	//			SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
	//			request.setAttribute("date", sdf.format(new Date()));
	//		}
	//		return "edit";
	//	}
	//	/**
	//	 * <p>
	//	 * 信息保存
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>新增。</div>
	//	 * <div>修改。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String save() {
	//		log.info("C301Action edit.........");
	//		if(bean!=null){
	//			String msg="1";
	//			try {
	//					BaseUserBean user = (BaseUserBean) request.getSession().getAttribute(CommonConstant.SESSION_KEY_USER_CUSTOMER_SERVICE);
	//					if(user!=null){
	//						bean.setCreate_ip(getIpAddr());
	//						bean.setCreate_id(user.getUser_id());
	//						bean.setUpdate_ip(getIpAddr());
	//						bean.setUpdate_id(user.getUser_id());
	//					}
	//					msg=consultMsgManager.saveOrUpdateData(bean);
	//			} catch (Exception e) {
	//				msg=e.getMessage();
	//			}
	//			request.setAttribute("msg",msg);
	//		}else{
	//			request.setAttribute("msg", "信息保存失败!");
	//		}
	//		return SUCCESS;
	//	}
	//	/**
	//	 * <p>
	//	 * 删除。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>删除。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String del() {
	//		log.info("C301Action del.........");
	//		
	//		String id=request.getParameter("id");
	//		TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//		bean1.setId(id);
	//		String msg="1";
	//		try {
	//			msg=consultMsgManager.deleteDataById(bean1);
	//		} catch (Exception e) {
	//			msg=e.getMessage();
	//		}
	//		request.setAttribute("msg",msg);
	//		return SUCCESS;
	//	}
	
	
	
	/**
	 * BEAN类  咨询信息取得
	 * @return BEAN类  咨询信息
	 */
	//	/**
	//	 * <p>
	//	 * 编辑。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>编辑。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String edit() {
	//		log.info("C301Action edit.........");
	//		String id=request.getParameter("id");
	//		if(id!=null){
	//			TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//			bean1.setId(id);
	//			bean=consultMsgManager.findDataById(bean1);
	//		}else{
	//			SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
	//			request.setAttribute("date", sdf.format(new Date()));
	//		}
	//		return "edit";
	//	}
	//	/**
	//	 * <p>
	//	 * 信息保存
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>新增。</div>
	//	 * <div>修改。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String save() {
	//		log.info("C301Action edit.........");
	//		if(bean!=null){
	//			String msg="1";
	//			try {
	//					BaseUserBean user = (BaseUserBean) request.getSession().getAttribute(CommonConstant.SESSION_KEY_USER_CUSTOMER_SERVICE);
	//					if(user!=null){
	//						bean.setCreate_ip(getIpAddr());
	//						bean.setCreate_id(user.getUser_id());
	//						bean.setUpdate_ip(getIpAddr());
	//						bean.setUpdate_id(user.getUser_id());
	//					}
	//					msg=consultMsgManager.saveOrUpdateData(bean);
	//			} catch (Exception e) {
	//				msg=e.getMessage();
	//			}
	//			request.setAttribute("msg",msg);
	//		}else{
	//			request.setAttribute("msg", "信息保存失败!");
	//		}
	//		return SUCCESS;
	//	}
	//	/**
	//	 * <p>
	//	 * 删除。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>删除。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String del() {
	//		log.info("C301Action del.........");
	//		
	//		String id=request.getParameter("id");
	//		TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//		bean1.setId(id);
	//		String msg="1";
	//		try {
	//			msg=consultMsgManager.deleteDataById(bean1);
	//		} catch (Exception e) {
	//			msg=e.getMessage();
	//		}
	//		request.setAttribute("msg",msg);
	//		return SUCCESS;
	//	}
	
	
	
	/**
	 * BEAN类  咨询信息取得
	 * @return BEAN类  咨询信息
	 */
	//	/**
	//	 * <p>
	//	 * 编辑。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>编辑。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String edit() {
	//		log.info("C301Action edit.........");
	//		String id=request.getParameter("id");
	//		if(id!=null){
	//			TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//			bean1.setId(id);
	//			bean=consultMsgManager.findDataById(bean1);
	//		}else{
	//			SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
	//			request.setAttribute("date", sdf.format(new Date()));
	//		}
	//		return "edit";
	//	}
	//	/**
	//	 * <p>
	//	 * 信息保存
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>新增。</div>
	//	 * <div>修改。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String save() {
	//		log.info("C301Action edit.........");
	//		if(bean!=null){
	//			String msg="1";
	//			try {
	//					BaseUserBean user = (BaseUserBean) request.getSession().getAttribute(CommonConstant.SESSION_KEY_USER_CUSTOMER_SERVICE);
	//					if(user!=null){
	//						bean.setCreate_ip(getIpAddr());
	//						bean.setCreate_id(user.getUser_id());
	//						bean.setUpdate_ip(getIpAddr());
	//						bean.setUpdate_id(user.getUser_id());
	//					}
	//					msg=consultMsgManager.saveOrUpdateData(bean);
	//			} catch (Exception e) {
	//				msg=e.getMessage();
	//			}
	//			request.setAttribute("msg",msg);
	//		}else{
	//			request.setAttribute("msg", "信息保存失败!");
	//		}
	//		return SUCCESS;
	//	}
	//	/**
	//	 * <p>
	//	 * 删除。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>删除。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String del() {
	//		log.info("C301Action del.........");
	//		
	//		String id=request.getParameter("id");
	//		TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//		bean1.setId(id);
	//		String msg="1";
	//		try {
	//			msg=consultMsgManager.deleteDataById(bean1);
	//		} catch (Exception e) {
	//			msg=e.getMessage();
	//		}
	//		request.setAttribute("msg",msg);
	//		return SUCCESS;
	//	}
	
	
	
	/**
	 * BEAN类  咨询信息设定
	 * @param bean BEAN类  咨询信息
	 */
	//	/**
	//	 * <p>
	//	 * 编辑。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>编辑。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String edit() {
	//		log.info("C301Action edit.........");
	//		String id=request.getParameter("id");
	//		if(id!=null){
	//			TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//			bean1.setId(id);
	//			bean=consultMsgManager.findDataById(bean1);
	//		}else{
	//			SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
	//			request.setAttribute("date", sdf.format(new Date()));
	//		}
	//		return "edit";
	//	}
	//	/**
	//	 * <p>
	//	 * 信息保存
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>新增。</div>
	//	 * <div>修改。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String save() {
	//		log.info("C301Action edit.........");
	//		if(bean!=null){
	//			String msg="1";
	//			try {
	//					BaseUserBean user = (BaseUserBean) request.getSession().getAttribute(CommonConstant.SESSION_KEY_USER_CUSTOMER_SERVICE);
	//					if(user!=null){
	//						bean.setCreate_ip(getIpAddr());
	//						bean.setCreate_id(user.getUser_id());
	//						bean.setUpdate_ip(getIpAddr());
	//						bean.setUpdate_id(user.getUser_id());
	//					}
	//					msg=consultMsgManager.saveOrUpdateData(bean);
	//			} catch (Exception e) {
	//				msg=e.getMessage();
	//			}
	//			request.setAttribute("msg",msg);
	//		}else{
	//			request.setAttribute("msg", "信息保存失败!");
	//		}
	//		return SUCCESS;
	//	}
	//	/**
	//	 * <p>
	//	 * 删除。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>删除。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String del() {
	//		log.info("C301Action del.........");
	//		
	//		String id=request.getParameter("id");
	//		TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//		bean1.setId(id);
	//		String msg="1";
	//		try {
	//			msg=consultMsgManager.deleteDataById(bean1);
	//		} catch (Exception e) {
	//			msg=e.getMessage();
	//		}
	//		request.setAttribute("msg",msg);
	//		return SUCCESS;
	//	}
	
	
	
	/**
	 * BEAN类  咨询信息取得
	 * @return BEAN类  咨询信息
	 */
	//	/**
	//	 * <p>
	//	 * 编辑。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>编辑。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String edit() {
	//		log.info("C301Action edit.........");
	//		String id=request.getParameter("id");
	//		if(id!=null){
	//			TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//			bean1.setId(id);
	//			bean=consultMsgManager.findDataById(bean1);
	//		}else{
	//			SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
	//			request.setAttribute("date", sdf.format(new Date()));
	//		}
	//		return "edit";
	//	}
	//	/**
	//	 * <p>
	//	 * 信息保存
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>新增。</div>
	//	 * <div>修改。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String save() {
	//		log.info("C301Action edit.........");
	//		if(bean!=null){
	//			String msg="1";
	//			try {
	//					BaseUserBean user = (BaseUserBean) request.getSession().getAttribute(CommonConstant.SESSION_KEY_USER_CUSTOMER_SERVICE);
	//					if(user!=null){
	//						bean.setCreate_ip(getIpAddr());
	//						bean.setCreate_id(user.getUser_id());
	//						bean.setUpdate_ip(getIpAddr());
	//						bean.setUpdate_id(user.getUser_id());
	//					}
	//					msg=consultMsgManager.saveOrUpdateData(bean);
	//			} catch (Exception e) {
	//				msg=e.getMessage();
	//			}
	//			request.setAttribute("msg",msg);
	//		}else{
	//			request.setAttribute("msg", "信息保存失败!");
	//		}
	//		return SUCCESS;
	//	}
	//	/**
	//	 * <p>
	//	 * 删除。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>删除。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String del() {
	//		log.info("C301Action del.........");
	//		
	//		String id=request.getParameter("id");
	//		TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//		bean1.setId(id);
	//		String msg="1";
	//		try {
	//			msg=consultMsgManager.deleteDataById(bean1);
	//		} catch (Exception e) {
	//			msg=e.getMessage();
	//		}
	//		request.setAttribute("msg",msg);
	//		return SUCCESS;
	//	}
	
	
	
	/**
	 * BEAN类  咨询信息取得
	 * @return BEAN类  咨询信息
	 */
	//	/**
	//	 * <p>
	//	 * 编辑。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>编辑。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String edit() {
	//		log.info("C301Action edit.........");
	//		String id=request.getParameter("id");
	//		if(id!=null){
	//			TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//			bean1.setId(id);
	//			bean=consultMsgManager.findDataById(bean1);
	//		}else{
	//			SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
	//			request.setAttribute("date", sdf.format(new Date()));
	//		}
	//		return "edit";
	//	}
	//	/**
	//	 * <p>
	//	 * 信息保存
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>新增。</div>
	//	 * <div>修改。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String save() {
	//		log.info("C301Action edit.........");
	//		if(bean!=null){
	//			String msg="1";
	//			try {
	//					BaseUserBean user = (BaseUserBean) request.getSession().getAttribute(CommonConstant.SESSION_KEY_USER_CUSTOMER_SERVICE);
	//					if(user!=null){
	//						bean.setCreate_ip(getIpAddr());
	//						bean.setCreate_id(user.getUser_id());
	//						bean.setUpdate_ip(getIpAddr());
	//						bean.setUpdate_id(user.getUser_id());
	//					}
	//					msg=consultMsgManager.saveOrUpdateData(bean);
	//			} catch (Exception e) {
	//				msg=e.getMessage();
	//			}
	//			request.setAttribute("msg",msg);
	//		}else{
	//			request.setAttribute("msg", "信息保存失败!");
	//		}
	//		return SUCCESS;
	//	}
	//	/**
	//	 * <p>
	//	 * 删除。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>删除。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String del() {
	//		log.info("C301Action del.........");
	//		
	//		String id=request.getParameter("id");
	//		TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//		bean1.setId(id);
	//		String msg="1";
	//		try {
	//			msg=consultMsgManager.deleteDataById(bean1);
	//		} catch (Exception e) {
	//			msg=e.getMessage();
	//		}
	//		request.setAttribute("msg",msg);
	//		return SUCCESS;
	//	}
	
	
	
	/**
	 * BEAN类  咨询信息设定
	 * @param bean BEAN类  咨询信息
	 */
	//	/**
	//	 * <p>
	//	 * 编辑。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>编辑。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String edit() {
	//		log.info("C301Action edit.........");
	//		String id=request.getParameter("id");
	//		if(id!=null){
	//			TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//			bean1.setId(id);
	//			bean=consultMsgManager.findDataById(bean1);
	//		}else{
	//			SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
	//			request.setAttribute("date", sdf.format(new Date()));
	//		}
	//		return "edit";
	//	}
	//	/**
	//	 * <p>
	//	 * 信息保存
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>新增。</div>
	//	 * <div>修改。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String save() {
	//		log.info("C301Action edit.........");
	//		if(bean!=null){
	//			String msg="1";
	//			try {
	//					BaseUserBean user = (BaseUserBean) request.getSession().getAttribute(CommonConstant.SESSION_KEY_USER_CUSTOMER_SERVICE);
	//					if(user!=null){
	//						bean.setCreate_ip(getIpAddr());
	//						bean.setCreate_id(user.getUser_id());
	//						bean.setUpdate_ip(getIpAddr());
	//						bean.setUpdate_id(user.getUser_id());
	//					}
	//					msg=consultMsgManager.saveOrUpdateData(bean);
	//			} catch (Exception e) {
	//				msg=e.getMessage();
	//			}
	//			request.setAttribute("msg",msg);
	//		}else{
	//			request.setAttribute("msg", "信息保存失败!");
	//		}
	//		return SUCCESS;
	//	}
	//	/**
	//	 * <p>
	//	 * 删除。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>删除。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String del() {
	//		log.info("C301Action del.........");
	//		
	//		String id=request.getParameter("id");
	//		TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//		bean1.setId(id);
	//		String msg="1";
	//		try {
	//			msg=consultMsgManager.deleteDataById(bean1);
	//		} catch (Exception e) {
	//			msg=e.getMessage();
	//		}
	//		request.setAttribute("msg",msg);
	//		return SUCCESS;
	//	}
	
	
	
	/**
	 * BEAN类  咨询信息取得
	 * @return BEAN类  咨询信息
	 */
	//	/**
	//	 * <p>
	//	 * 编辑。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>编辑。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String edit() {
	//		log.info("C301Action edit.........");
	//		String id=request.getParameter("id");
	//		if(id!=null){
	//			TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//			bean1.setId(id);
	//			bean=consultMsgManager.findDataById(bean1);
	//		}else{
	//			SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
	//			request.setAttribute("date", sdf.format(new Date()));
	//		}
	//		return "edit";
	//	}
	//	/**
	//	 * <p>
	//	 * 信息保存
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>新增。</div>
	//	 * <div>修改。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String save() {
	//		log.info("C301Action edit.........");
	//		if(bean!=null){
	//			String msg="1";
	//			try {
	//					BaseUserBean user = (BaseUserBean) request.getSession().getAttribute(CommonConstant.SESSION_KEY_USER_CUSTOMER_SERVICE);
	//					if(user!=null){
	//						bean.setCreate_ip(getIpAddr());
	//						bean.setCreate_id(user.getUser_id());
	//						bean.setUpdate_ip(getIpAddr());
	//						bean.setUpdate_id(user.getUser_id());
	//					}
	//					msg=consultMsgManager.saveOrUpdateData(bean);
	//			} catch (Exception e) {
	//				msg=e.getMessage();
	//			}
	//			request.setAttribute("msg",msg);
	//		}else{
	//			request.setAttribute("msg", "信息保存失败!");
	//		}
	//		return SUCCESS;
	//	}
	//	/**
	//	 * <p>
	//	 * 删除。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>删除。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String del() {
	//		log.info("C301Action del.........");
	//		
	//		String id=request.getParameter("id");
	//		TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//		bean1.setId(id);
	//		String msg="1";
	//		try {
	//			msg=consultMsgManager.deleteDataById(bean1);
	//		} catch (Exception e) {
	//			msg=e.getMessage();
	//		}
	//		request.setAttribute("msg",msg);
	//		return SUCCESS;
	//	}
	
	
	
	/**
	 * BEAN类  咨询信息设定
	 * @param bean BEAN类  咨询信息
	 */
	//	/**
	//	 * <p>
	//	 * 编辑。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>编辑。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String edit() {
	//		log.info("C301Action edit.........");
	//		String id=request.getParameter("id");
	//		if(id!=null){
	//			TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//			bean1.setId(id);
	//			bean=consultMsgManager.findDataById(bean1);
	//		}else{
	//			SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
	//			request.setAttribute("date", sdf.format(new Date()));
	//		}
	//		return "edit";
	//	}
	//	/**
	//	 * <p>
	//	 * 信息保存
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>新增。</div>
	//	 * <div>修改。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String save() {
	//		log.info("C301Action edit.........");
	//		if(bean!=null){
	//			String msg="1";
	//			try {
	//					BaseUserBean user = (BaseUserBean) request.getSession().getAttribute(CommonConstant.SESSION_KEY_USER_CUSTOMER_SERVICE);
	//					if(user!=null){
	//						bean.setCreate_ip(getIpAddr());
	//						bean.setCreate_id(user.getUser_id());
	//						bean.setUpdate_ip(getIpAddr());
	//						bean.setUpdate_id(user.getUser_id());
	//					}
	//					msg=consultMsgManager.saveOrUpdateData(bean);
	//			} catch (Exception e) {
	//				msg=e.getMessage();
	//			}
	//			request.setAttribute("msg",msg);
	//		}else{
	//			request.setAttribute("msg", "信息保存失败!");
	//		}
	//		return SUCCESS;
	//	}
	//	/**
	//	 * <p>
	//	 * 删除。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>删除。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String del() {
	//		log.info("C301Action del.........");
	//		
	//		String id=request.getParameter("id");
	//		TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//		bean1.setId(id);
	//		String msg="1";
	//		try {
	//			msg=consultMsgManager.deleteDataById(bean1);
	//		} catch (Exception e) {
	//			msg=e.getMessage();
	//		}
	//		request.setAttribute("msg",msg);
	//		return SUCCESS;
	//	}
	
	
	
	/**
	 * BEAN类  咨询信息取得
	 * @return BEAN类  咨询信息
	 */
	//	/**
	//	 * <p>
	//	 * 编辑。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>编辑。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String edit() {
	//		log.info("C301Action edit.........");
	//		String id=request.getParameter("id");
	//		if(id!=null){
	//			TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//			bean1.setId(id);
	//			bean=consultMsgManager.findDataById(bean1);
	//		}else{
	//			SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
	//			request.setAttribute("date", sdf.format(new Date()));
	//		}
	//		return "edit";
	//	}
	//	/**
	//	 * <p>
	//	 * 信息保存
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>新增。</div>
	//	 * <div>修改。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String save() {
	//		log.info("C301Action edit.........");
	//		if(bean!=null){
	//			String msg="1";
	//			try {
	//					BaseUserBean user = (BaseUserBean) request.getSession().getAttribute(CommonConstant.SESSION_KEY_USER_CUSTOMER_SERVICE);
	//					if(user!=null){
	//						bean.setCreate_ip(getIpAddr());
	//						bean.setCreate_id(user.getUser_id());
	//						bean.setUpdate_ip(getIpAddr());
	//						bean.setUpdate_id(user.getUser_id());
	//					}
	//					msg=consultMsgManager.saveOrUpdateData(bean);
	//			} catch (Exception e) {
	//				msg=e.getMessage();
	//			}
	//			request.setAttribute("msg",msg);
	//		}else{
	//			request.setAttribute("msg", "信息保存失败!");
	//		}
	//		return SUCCESS;
	//	}
	//	/**
	//	 * <p>
	//	 * 删除。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>删除。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String del() {
	//		log.info("C301Action del.........");
	//		
	//		String id=request.getParameter("id");
	//		TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//		bean1.setId(id);
	//		String msg="1";
	//		try {
	//			msg=consultMsgManager.deleteDataById(bean1);
	//		} catch (Exception e) {
	//			msg=e.getMessage();
	//		}
	//		request.setAttribute("msg",msg);
	//		return SUCCESS;
	//	}
	
	
	
	/**
	 * BEAN类  咨询信息取得
	 * @return BEAN类  咨询信息
	 */
	//	/**
	//	 * <p>
	//	 * 编辑。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>编辑。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String edit() {
	//		log.info("C301Action edit.........");
	//		String id=request.getParameter("id");
	//		if(id!=null){
	//			TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//			bean1.setId(id);
	//			bean=consultMsgManager.findDataById(bean1);
	//		}else{
	//			SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
	//			request.setAttribute("date", sdf.format(new Date()));
	//		}
	//		return "edit";
	//	}
	//	/**
	//	 * <p>
	//	 * 信息保存
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>新增。</div>
	//	 * <div>修改。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String save() {
	//		log.info("C301Action edit.........");
	//		if(bean!=null){
	//			String msg="1";
	//			try {
	//					BaseUserBean user = (BaseUserBean) request.getSession().getAttribute(CommonConstant.SESSION_KEY_USER_CUSTOMER_SERVICE);
	//					if(user!=null){
	//						bean.setCreate_ip(getIpAddr());
	//						bean.setCreate_id(user.getUser_id());
	//						bean.setUpdate_ip(getIpAddr());
	//						bean.setUpdate_id(user.getUser_id());
	//					}
	//					msg=consultMsgManager.saveOrUpdateData(bean);
	//			} catch (Exception e) {
	//				msg=e.getMessage();
	//			}
	//			request.setAttribute("msg",msg);
	//		}else{
	//			request.setAttribute("msg", "信息保存失败!");
	//		}
	//		return SUCCESS;
	//	}
	//	/**
	//	 * <p>
	//	 * 删除。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>删除。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String del() {
	//		log.info("C301Action del.........");
	//		
	//		String id=request.getParameter("id");
	//		TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//		bean1.setId(id);
	//		String msg="1";
	//		try {
	//			msg=consultMsgManager.deleteDataById(bean1);
	//		} catch (Exception e) {
	//			msg=e.getMessage();
	//		}
	//		request.setAttribute("msg",msg);
	//		return SUCCESS;
	//	}
	
	
	
	/**
	 * BEAN类  咨询信息设定
	 * @param bean BEAN类  咨询信息
	 */
	//	/**
	//	 * <p>
	//	 * 编辑。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>编辑。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String edit() {
	//		log.info("C301Action edit.........");
	//		String id=request.getParameter("id");
	//		if(id!=null){
	//			TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//			bean1.setId(id);
	//			bean=consultMsgManager.findDataById(bean1);
	//		}else{
	//			SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
	//			request.setAttribute("date", sdf.format(new Date()));
	//		}
	//		return "edit";
	//	}
	//	/**
	//	 * <p>
	//	 * 信息保存
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>新增。</div>
	//	 * <div>修改。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String save() {
	//		log.info("C301Action edit.........");
	//		if(bean!=null){
	//			String msg="1";
	//			try {
	//					BaseUserBean user = (BaseUserBean) request.getSession().getAttribute(CommonConstant.SESSION_KEY_USER_CUSTOMER_SERVICE);
	//					if(user!=null){
	//						bean.setCreate_ip(getIpAddr());
	//						bean.setCreate_id(user.getUser_id());
	//						bean.setUpdate_ip(getIpAddr());
	//						bean.setUpdate_id(user.getUser_id());
	//					}
	//					msg=consultMsgManager.saveOrUpdateData(bean);
	//			} catch (Exception e) {
	//				msg=e.getMessage();
	//			}
	//			request.setAttribute("msg",msg);
	//		}else{
	//			request.setAttribute("msg", "信息保存失败!");
	//		}
	//		return SUCCESS;
	//	}
	//	/**
	//	 * <p>
	//	 * 删除。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>删除。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String del() {
	//		log.info("C301Action del.........");
	//		
	//		String id=request.getParameter("id");
	//		TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//		bean1.setId(id);
	//		String msg="1";
	//		try {
	//			msg=consultMsgManager.deleteDataById(bean1);
	//		} catch (Exception e) {
	//			msg=e.getMessage();
	//		}
	//		request.setAttribute("msg",msg);
	//		return SUCCESS;
	//	}
	
	
	
	/**
	 * BEAN类  咨询信息取得
	 * @return BEAN类  咨询信息
	 */
	//	/**
	//	 * <p>
	//	 * 编辑。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>编辑。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String edit() {
	//		log.info("C301Action edit.........");
	//		String id=request.getParameter("id");
	//		if(id!=null){
	//			TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//			bean1.setId(id);
	//			bean=consultMsgManager.findDataById(bean1);
	//		}else{
	//			SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
	//			request.setAttribute("date", sdf.format(new Date()));
	//		}
	//		return "edit";
	//	}
	//	/**
	//	 * <p>
	//	 * 信息保存
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>新增。</div>
	//	 * <div>修改。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String save() {
	//		log.info("C301Action edit.........");
	//		if(bean!=null){
	//			String msg="1";
	//			try {
	//					BaseUserBean user = (BaseUserBean) request.getSession().getAttribute(CommonConstant.SESSION_KEY_USER_CUSTOMER_SERVICE);
	//					if(user!=null){
	//						bean.setCreate_ip(getIpAddr());
	//						bean.setCreate_id(user.getUser_id());
	//						bean.setUpdate_ip(getIpAddr());
	//						bean.setUpdate_id(user.getUser_id());
	//					}
	//					msg=consultMsgManager.saveOrUpdateData(bean);
	//			} catch (Exception e) {
	//				msg=e.getMessage();
	//			}
	//			request.setAttribute("msg",msg);
	//		}else{
	//			request.setAttribute("msg", "信息保存失败!");
	//		}
	//		return SUCCESS;
	//	}
	//	/**
	//	 * <p>
	//	 * 删除。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>删除。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String del() {
	//		log.info("C301Action del.........");
	//		
	//		String id=request.getParameter("id");
	//		TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//		bean1.setId(id);
	//		String msg="1";
	//		try {
	//			msg=consultMsgManager.deleteDataById(bean1);
	//		} catch (Exception e) {
	//			msg=e.getMessage();
	//		}
	//		request.setAttribute("msg",msg);
	//		return SUCCESS;
	//	}
	
	
	
	/**
	 * BEAN类  咨询信息取得
	 * @return BEAN类  咨询信息
	 */
	//	/**
	//	 * <p>
	//	 * 编辑。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>编辑。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String edit() {
	//		log.info("C301Action edit.........");
	//		String id=request.getParameter("id");
	//		if(id!=null){
	//			TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//			bean1.setId(id);
	//			bean=consultMsgManager.findDataById(bean1);
	//		}else{
	//			SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
	//			request.setAttribute("date", sdf.format(new Date()));
	//		}
	//		return "edit";
	//	}
	//	/**
	//	 * <p>
	//	 * 信息保存
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>新增。</div>
	//	 * <div>修改。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String save() {
	//		log.info("C301Action edit.........");
	//		if(bean!=null){
	//			String msg="1";
	//			try {
	//					BaseUserBean user = (BaseUserBean) request.getSession().getAttribute(CommonConstant.SESSION_KEY_USER_CUSTOMER_SERVICE);
	//					if(user!=null){
	//						bean.setCreate_ip(getIpAddr());
	//						bean.setCreate_id(user.getUser_id());
	//						bean.setUpdate_ip(getIpAddr());
	//						bean.setUpdate_id(user.getUser_id());
	//					}
	//					msg=consultMsgManager.saveOrUpdateData(bean);
	//			} catch (Exception e) {
	//				msg=e.getMessage();
	//			}
	//			request.setAttribute("msg",msg);
	//		}else{
	//			request.setAttribute("msg", "信息保存失败!");
	//		}
	//		return SUCCESS;
	//	}
	//	/**
	//	 * <p>
	//	 * 删除。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>删除。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String del() {
	//		log.info("C301Action del.........");
	//		
	//		String id=request.getParameter("id");
	//		TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//		bean1.setId(id);
	//		String msg="1";
	//		try {
	//			msg=consultMsgManager.deleteDataById(bean1);
	//		} catch (Exception e) {
	//			msg=e.getMessage();
	//		}
	//		request.setAttribute("msg",msg);
	//		return SUCCESS;
	//	}
	
	
	
	/**
	 * BEAN类  咨询信息设定
	 * @param bean BEAN类  咨询信息
	 */
	//	/**
	//	 * <p>
	//	 * 编辑。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>编辑。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String edit() {
	//		log.info("C301Action edit.........");
	//		String id=request.getParameter("id");
	//		if(id!=null){
	//			TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//			bean1.setId(id);
	//			bean=consultMsgManager.findDataById(bean1);
	//		}else{
	//			SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
	//			request.setAttribute("date", sdf.format(new Date()));
	//		}
	//		return "edit";
	//	}
	//	/**
	//	 * <p>
	//	 * 信息保存
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>新增。</div>
	//	 * <div>修改。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String save() {
	//		log.info("C301Action edit.........");
	//		if(bean!=null){
	//			String msg="1";
	//			try {
	//					BaseUserBean user = (BaseUserBean) request.getSession().getAttribute(CommonConstant.SESSION_KEY_USER_CUSTOMER_SERVICE);
	//					if(user!=null){
	//						bean.setCreate_ip(getIpAddr());
	//						bean.setCreate_id(user.getUser_id());
	//						bean.setUpdate_ip(getIpAddr());
	//						bean.setUpdate_id(user.getUser_id());
	//					}
	//					msg=consultMsgManager.saveOrUpdateData(bean);
	//			} catch (Exception e) {
	//				msg=e.getMessage();
	//			}
	//			request.setAttribute("msg",msg);
	//		}else{
	//			request.setAttribute("msg", "信息保存失败!");
	//		}
	//		return SUCCESS;
	//	}
	//	/**
	//	 * <p>
	//	 * 删除。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>删除。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String del() {
	//		log.info("C301Action del.........");
	//		
	//		String id=request.getParameter("id");
	//		TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//		bean1.setId(id);
	//		String msg="1";
	//		try {
	//			msg=consultMsgManager.deleteDataById(bean1);
	//		} catch (Exception e) {
	//			msg=e.getMessage();
	//		}
	//		request.setAttribute("msg",msg);
	//		return SUCCESS;
	//	}
	
	
	
	/**
	 * BEAN类  咨询信息取得
	 * @return BEAN类  咨询信息
	 */
	//	/**
	//	 * <p>
	//	 * 编辑。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>编辑。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String edit() {
	//		log.info("C301Action edit.........");
	//		String id=request.getParameter("id");
	//		if(id!=null){
	//			TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//			bean1.setId(id);
	//			bean=consultMsgManager.findDataById(bean1);
	//		}else{
	//			SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
	//			request.setAttribute("date", sdf.format(new Date()));
	//		}
	//		return "edit";
	//	}
	//	/**
	//	 * <p>
	//	 * 信息保存
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>新增。</div>
	//	 * <div>修改。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String save() {
	//		log.info("C301Action edit.........");
	//		if(bean!=null){
	//			String msg="1";
	//			try {
	//					BaseUserBean user = (BaseUserBean) request.getSession().getAttribute(CommonConstant.SESSION_KEY_USER_CUSTOMER_SERVICE);
	//					if(user!=null){
	//						bean.setCreate_ip(getIpAddr());
	//						bean.setCreate_id(user.getUser_id());
	//						bean.setUpdate_ip(getIpAddr());
	//						bean.setUpdate_id(user.getUser_id());
	//					}
	//					msg=consultMsgManager.saveOrUpdateData(bean);
	//			} catch (Exception e) {
	//				msg=e.getMessage();
	//			}
	//			request.setAttribute("msg",msg);
	//		}else{
	//			request.setAttribute("msg", "信息保存失败!");
	//		}
	//		return SUCCESS;
	//	}
	//	/**
	//	 * <p>
	//	 * 删除。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>删除。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String del() {
	//		log.info("C301Action del.........");
	//		
	//		String id=request.getParameter("id");
	//		TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//		bean1.setId(id);
	//		String msg="1";
	//		try {
	//			msg=consultMsgManager.deleteDataById(bean1);
	//		} catch (Exception e) {
	//			msg=e.getMessage();
	//		}
	//		request.setAttribute("msg",msg);
	//		return SUCCESS;
	//	}
	
	
	
	/**
	 * BEAN类  咨询信息设定
	 * @param bean BEAN类  咨询信息
	 */
	//	/**
	//	 * <p>
	//	 * 编辑。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>编辑。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String edit() {
	//		log.info("C301Action edit.........");
	//		String id=request.getParameter("id");
	//		if(id!=null){
	//			TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//			bean1.setId(id);
	//			bean=consultMsgManager.findDataById(bean1);
	//		}else{
	//			SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
	//			request.setAttribute("date", sdf.format(new Date()));
	//		}
	//		return "edit";
	//	}
	//	/**
	//	 * <p>
	//	 * 信息保存
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>新增。</div>
	//	 * <div>修改。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String save() {
	//		log.info("C301Action edit.........");
	//		if(bean!=null){
	//			String msg="1";
	//			try {
	//					BaseUserBean user = (BaseUserBean) request.getSession().getAttribute(CommonConstant.SESSION_KEY_USER_CUSTOMER_SERVICE);
	//					if(user!=null){
	//						bean.setCreate_ip(getIpAddr());
	//						bean.setCreate_id(user.getUser_id());
	//						bean.setUpdate_ip(getIpAddr());
	//						bean.setUpdate_id(user.getUser_id());
	//					}
	//					msg=consultMsgManager.saveOrUpdateData(bean);
	//			} catch (Exception e) {
	//				msg=e.getMessage();
	//			}
	//			request.setAttribute("msg",msg);
	//		}else{
	//			request.setAttribute("msg", "信息保存失败!");
	//		}
	//		return SUCCESS;
	//	}
	//	/**
	//	 * <p>
	//	 * 删除。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>删除。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String del() {
	//		log.info("C301Action del.........");
	//		
	//		String id=request.getParameter("id");
	//		TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//		bean1.setId(id);
	//		String msg="1";
	//		try {
	//			msg=consultMsgManager.deleteDataById(bean1);
	//		} catch (Exception e) {
	//			msg=e.getMessage();
	//		}
	//		request.setAttribute("msg",msg);
	//		return SUCCESS;
	//	}
	
	
	
	/**
	 * BEAN类  咨询信息取得
	 * @return BEAN类  咨询信息
	 */
	//	/**
	//	 * <p>
	//	 * 编辑。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>编辑。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String edit() {
	//		log.info("C301Action edit.........");
	//		String id=request.getParameter("id");
	//		if(id!=null){
	//			TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//			bean1.setId(id);
	//			bean=consultMsgManager.findDataById(bean1);
	//		}else{
	//			SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
	//			request.setAttribute("date", sdf.format(new Date()));
	//		}
	//		return "edit";
	//	}
	//	/**
	//	 * <p>
	//	 * 信息保存
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>新增。</div>
	//	 * <div>修改。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String save() {
	//		log.info("C301Action edit.........");
	//		if(bean!=null){
	//			String msg="1";
	//			try {
	//					BaseUserBean user = (BaseUserBean) request.getSession().getAttribute(CommonConstant.SESSION_KEY_USER_CUSTOMER_SERVICE);
	//					if(user!=null){
	//						bean.setCreate_ip(getIpAddr());
	//						bean.setCreate_id(user.getUser_id());
	//						bean.setUpdate_ip(getIpAddr());
	//						bean.setUpdate_id(user.getUser_id());
	//					}
	//					msg=consultMsgManager.saveOrUpdateData(bean);
	//			} catch (Exception e) {
	//				msg=e.getMessage();
	//			}
	//			request.setAttribute("msg",msg);
	//		}else{
	//			request.setAttribute("msg", "信息保存失败!");
	//		}
	//		return SUCCESS;
	//	}
	//	/**
	//	 * <p>
	//	 * 删除。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>删除。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String del() {
	//		log.info("C301Action del.........");
	//		
	//		String id=request.getParameter("id");
	//		TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//		bean1.setId(id);
	//		String msg="1";
	//		try {
	//			msg=consultMsgManager.deleteDataById(bean1);
	//		} catch (Exception e) {
	//			msg=e.getMessage();
	//		}
	//		request.setAttribute("msg",msg);
	//		return SUCCESS;
	//	}
	
	
	
	/**
	 * BEAN类  咨询信息取得
	 * @return BEAN类  咨询信息
	 */
	//	/**
	//	 * <p>
	//	 * 编辑。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>编辑。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String edit() {
	//		log.info("C301Action edit.........");
	//		String id=request.getParameter("id");
	//		if(id!=null){
	//			TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//			bean1.setId(id);
	//			bean=consultMsgManager.findDataById(bean1);
	//		}else{
	//			SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
	//			request.setAttribute("date", sdf.format(new Date()));
	//		}
	//		return "edit";
	//	}
	//	/**
	//	 * <p>
	//	 * 信息保存
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>新增。</div>
	//	 * <div>修改。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String save() {
	//		log.info("C301Action edit.........");
	//		if(bean!=null){
	//			String msg="1";
	//			try {
	//					BaseUserBean user = (BaseUserBean) request.getSession().getAttribute(CommonConstant.SESSION_KEY_USER_CUSTOMER_SERVICE);
	//					if(user!=null){
	//						bean.setCreate_ip(getIpAddr());
	//						bean.setCreate_id(user.getUser_id());
	//						bean.setUpdate_ip(getIpAddr());
	//						bean.setUpdate_id(user.getUser_id());
	//					}
	//					msg=consultMsgManager.saveOrUpdateData(bean);
	//			} catch (Exception e) {
	//				msg=e.getMessage();
	//			}
	//			request.setAttribute("msg",msg);
	//		}else{
	//			request.setAttribute("msg", "信息保存失败!");
	//		}
	//		return SUCCESS;
	//	}
	//	/**
	//	 * <p>
	//	 * 删除。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>删除。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String del() {
	//		log.info("C301Action del.........");
	//		
	//		String id=request.getParameter("id");
	//		TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//		bean1.setId(id);
	//		String msg="1";
	//		try {
	//			msg=consultMsgManager.deleteDataById(bean1);
	//		} catch (Exception e) {
	//			msg=e.getMessage();
	//		}
	//		request.setAttribute("msg",msg);
	//		return SUCCESS;
	//	}
	
	
	
	/**
	 * BEAN类  咨询信息设定
	 * @param bean BEAN类  咨询信息
	 */
	//	/**
	//	 * <p>
	//	 * 编辑。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>编辑。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String edit() {
	//		log.info("C301Action edit.........");
	//		String id=request.getParameter("id");
	//		if(id!=null){
	//			TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//			bean1.setId(id);
	//			bean=consultMsgManager.findDataById(bean1);
	//		}else{
	//			SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
	//			request.setAttribute("date", sdf.format(new Date()));
	//		}
	//		return "edit";
	//	}
	//	/**
	//	 * <p>
	//	 * 信息保存
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>新增。</div>
	//	 * <div>修改。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String save() {
	//		log.info("C301Action edit.........");
	//		if(bean!=null){
	//			String msg="1";
	//			try {
	//					BaseUserBean user = (BaseUserBean) request.getSession().getAttribute(CommonConstant.SESSION_KEY_USER_CUSTOMER_SERVICE);
	//					if(user!=null){
	//						bean.setCreate_ip(getIpAddr());
	//						bean.setCreate_id(user.getUser_id());
	//						bean.setUpdate_ip(getIpAddr());
	//						bean.setUpdate_id(user.getUser_id());
	//					}
	//					msg=consultMsgManager.saveOrUpdateData(bean);
	//			} catch (Exception e) {
	//				msg=e.getMessage();
	//			}
	//			request.setAttribute("msg",msg);
	//		}else{
	//			request.setAttribute("msg", "信息保存失败!");
	//		}
	//		return SUCCESS;
	//	}
	//	/**
	//	 * <p>
	//	 * 删除。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>删除。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String del() {
	//		log.info("C301Action del.........");
	//		
	//		String id=request.getParameter("id");
	//		TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//		bean1.setId(id);
	//		String msg="1";
	//		try {
	//			msg=consultMsgManager.deleteDataById(bean1);
	//		} catch (Exception e) {
	//			msg=e.getMessage();
	//		}
	//		request.setAttribute("msg",msg);
	//		return SUCCESS;
	//	}
	
	
	
	/**
	 * BEAN类  咨询信息取得
	 * @return BEAN类  咨询信息
	 */
	//	/**
	//	 * <p>
	//	 * 编辑。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>编辑。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String edit() {
	//		log.info("C301Action edit.........");
	//		String id=request.getParameter("id");
	//		if(id!=null){
	//			TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//			bean1.setId(id);
	//			bean=consultMsgManager.findDataById(bean1);
	//		}else{
	//			SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
	//			request.setAttribute("date", sdf.format(new Date()));
	//		}
	//		return "edit";
	//	}
	//	/**
	//	 * <p>
	//	 * 信息保存
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>新增。</div>
	//	 * <div>修改。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String save() {
	//		log.info("C301Action edit.........");
	//		if(bean!=null){
	//			String msg="1";
	//			try {
	//					BaseUserBean user = (BaseUserBean) request.getSession().getAttribute(CommonConstant.SESSION_KEY_USER_CUSTOMER_SERVICE);
	//					if(user!=null){
	//						bean.setCreate_ip(getIpAddr());
	//						bean.setCreate_id(user.getUser_id());
	//						bean.setUpdate_ip(getIpAddr());
	//						bean.setUpdate_id(user.getUser_id());
	//					}
	//					msg=consultMsgManager.saveOrUpdateData(bean);
	//			} catch (Exception e) {
	//				msg=e.getMessage();
	//			}
	//			request.setAttribute("msg",msg);
	//		}else{
	//			request.setAttribute("msg", "信息保存失败!");
	//		}
	//		return SUCCESS;
	//	}
	//	/**
	//	 * <p>
	//	 * 删除。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>删除。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String del() {
	//		log.info("C301Action del.........");
	//		
	//		String id=request.getParameter("id");
	//		TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//		bean1.setId(id);
	//		String msg="1";
	//		try {
	//			msg=consultMsgManager.deleteDataById(bean1);
	//		} catch (Exception e) {
	//			msg=e.getMessage();
	//		}
	//		request.setAttribute("msg",msg);
	//		return SUCCESS;
	//	}
	
	
	
	/**
	 * BEAN类  咨询信息设定
	 * @param bean BEAN类  咨询信息
	 */
	//	/**
	//	 * <p>
	//	 * 编辑。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>编辑。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String edit() {
	//		log.info("C301Action edit.........");
	//		String id=request.getParameter("id");
	//		if(id!=null){
	//			TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//			bean1.setId(id);
	//			bean=consultMsgManager.findDataById(bean1);
	//		}else{
	//			SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
	//			request.setAttribute("date", sdf.format(new Date()));
	//		}
	//		return "edit";
	//	}
	//	/**
	//	 * <p>
	//	 * 信息保存
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>新增。</div>
	//	 * <div>修改。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String save() {
	//		log.info("C301Action edit.........");
	//		if(bean!=null){
	//			String msg="1";
	//			try {
	//					BaseUserBean user = (BaseUserBean) request.getSession().getAttribute(CommonConstant.SESSION_KEY_USER_CUSTOMER_SERVICE);
	//					if(user!=null){
	//						bean.setCreate_ip(getIpAddr());
	//						bean.setCreate_id(user.getUser_id());
	//						bean.setUpdate_ip(getIpAddr());
	//						bean.setUpdate_id(user.getUser_id());
	//					}
	//					msg=consultMsgManager.saveOrUpdateData(bean);
	//			} catch (Exception e) {
	//				msg=e.getMessage();
	//			}
	//			request.setAttribute("msg",msg);
	//		}else{
	//			request.setAttribute("msg", "信息保存失败!");
	//		}
	//		return SUCCESS;
	//	}
	//	/**
	//	 * <p>
	//	 * 删除。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>删除。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String del() {
	//		log.info("C301Action del.........");
	//		
	//		String id=request.getParameter("id");
	//		TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//		bean1.setId(id);
	//		String msg="1";
	//		try {
	//			msg=consultMsgManager.deleteDataById(bean1);
	//		} catch (Exception e) {
	//			msg=e.getMessage();
	//		}
	//		request.setAttribute("msg",msg);
	//		return SUCCESS;
	//	}
	
	
	
	/**
	 * BEAN类  咨询信息取得
	 * @return BEAN类  咨询信息
	 */
	//	/**
	//	 * <p>
	//	 * 编辑。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>编辑。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String edit() {
	//		log.info("C301Action edit.........");
	//		String id=request.getParameter("id");
	//		if(id!=null){
	//			TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//			bean1.setId(id);
	//			bean=consultMsgManager.findDataById(bean1);
	//		}else{
	//			SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
	//			request.setAttribute("date", sdf.format(new Date()));
	//		}
	//		return "edit";
	//	}
	//	/**
	//	 * <p>
	//	 * 信息保存
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>新增。</div>
	//	 * <div>修改。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String save() {
	//		log.info("C301Action edit.........");
	//		if(bean!=null){
	//			String msg="1";
	//			try {
	//					BaseUserBean user = (BaseUserBean) request.getSession().getAttribute(CommonConstant.SESSION_KEY_USER_CUSTOMER_SERVICE);
	//					if(user!=null){
	//						bean.setCreate_ip(getIpAddr());
	//						bean.setCreate_id(user.getUser_id());
	//						bean.setUpdate_ip(getIpAddr());
	//						bean.setUpdate_id(user.getUser_id());
	//					}
	//					msg=consultMsgManager.saveOrUpdateData(bean);
	//			} catch (Exception e) {
	//				msg=e.getMessage();
	//			}
	//			request.setAttribute("msg",msg);
	//		}else{
	//			request.setAttribute("msg", "信息保存失败!");
	//		}
	//		return SUCCESS;
	//	}
	//	/**
	//	 * <p>
	//	 * 删除。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>删除。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String del() {
	//		log.info("C301Action del.........");
	//		
	//		String id=request.getParameter("id");
	//		TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//		bean1.setId(id);
	//		String msg="1";
	//		try {
	//			msg=consultMsgManager.deleteDataById(bean1);
	//		} catch (Exception e) {
	//			msg=e.getMessage();
	//		}
	//		request.setAttribute("msg",msg);
	//		return SUCCESS;
	//	}
	
	
	
	/**
	 * BEAN类  咨询信息取得
	 * @return BEAN类  咨询信息
	 */
	//	/**
	//	 * <p>
	//	 * 编辑。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>编辑。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String edit() {
	//		log.info("C301Action edit.........");
	//		String id=request.getParameter("id");
	//		if(id!=null){
	//			TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//			bean1.setId(id);
	//			bean=consultMsgManager.findDataById(bean1);
	//		}else{
	//			SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
	//			request.setAttribute("date", sdf.format(new Date()));
	//		}
	//		return "edit";
	//	}
	//	/**
	//	 * <p>
	//	 * 信息保存
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>新增。</div>
	//	 * <div>修改。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String save() {
	//		log.info("C301Action edit.........");
	//		if(bean!=null){
	//			String msg="1";
	//			try {
	//					BaseUserBean user = (BaseUserBean) request.getSession().getAttribute(CommonConstant.SESSION_KEY_USER_CUSTOMER_SERVICE);
	//					if(user!=null){
	//						bean.setCreate_ip(getIpAddr());
	//						bean.setCreate_id(user.getUser_id());
	//						bean.setUpdate_ip(getIpAddr());
	//						bean.setUpdate_id(user.getUser_id());
	//					}
	//					msg=consultMsgManager.saveOrUpdateData(bean);
	//			} catch (Exception e) {
	//				msg=e.getMessage();
	//			}
	//			request.setAttribute("msg",msg);
	//		}else{
	//			request.setAttribute("msg", "信息保存失败!");
	//		}
	//		return SUCCESS;
	//	}
	//	/**
	//	 * <p>
	//	 * 删除。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>删除。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String del() {
	//		log.info("C301Action del.........");
	//		
	//		String id=request.getParameter("id");
	//		TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//		bean1.setId(id);
	//		String msg="1";
	//		try {
	//			msg=consultMsgManager.deleteDataById(bean1);
	//		} catch (Exception e) {
	//			msg=e.getMessage();
	//		}
	//		request.setAttribute("msg",msg);
	//		return SUCCESS;
	//	}
	
	
	
	/**
	 * BEAN类  咨询信息设定
	 * @param bean BEAN类  咨询信息
	 */
	//	/**
	//	 * <p>
	//	 * 编辑。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>编辑。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String edit() {
	//		log.info("C301Action edit.........");
	//		String id=request.getParameter("id");
	//		if(id!=null){
	//			TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//			bean1.setId(id);
	//			bean=consultMsgManager.findDataById(bean1);
	//		}else{
	//			SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
	//			request.setAttribute("date", sdf.format(new Date()));
	//		}
	//		return "edit";
	//	}
	//	/**
	//	 * <p>
	//	 * 信息保存
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>新增。</div>
	//	 * <div>修改。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String save() {
	//		log.info("C301Action edit.........");
	//		if(bean!=null){
	//			String msg="1";
	//			try {
	//					BaseUserBean user = (BaseUserBean) request.getSession().getAttribute(CommonConstant.SESSION_KEY_USER_CUSTOMER_SERVICE);
	//					if(user!=null){
	//						bean.setCreate_ip(getIpAddr());
	//						bean.setCreate_id(user.getUser_id());
	//						bean.setUpdate_ip(getIpAddr());
	//						bean.setUpdate_id(user.getUser_id());
	//					}
	//					msg=consultMsgManager.saveOrUpdateData(bean);
	//			} catch (Exception e) {
	//				msg=e.getMessage();
	//			}
	//			request.setAttribute("msg",msg);
	//		}else{
	//			request.setAttribute("msg", "信息保存失败!");
	//		}
	//		return SUCCESS;
	//	}
	//	/**
	//	 * <p>
	//	 * 删除。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>删除。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String del() {
	//		log.info("C301Action del.........");
	//		
	//		String id=request.getParameter("id");
	//		TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//		bean1.setId(id);
	//		String msg="1";
	//		try {
	//			msg=consultMsgManager.deleteDataById(bean1);
	//		} catch (Exception e) {
	//			msg=e.getMessage();
	//		}
	//		request.setAttribute("msg",msg);
	//		return SUCCESS;
	//	}
	
	
	
	/**
	 * BEAN类  咨询信息取得
	 * @return BEAN类  咨询信息
	 */
	//	/**
	//	 * <p>
	//	 * 编辑。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>编辑。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String edit() {
	//		log.info("C301Action edit.........");
	//		String id=request.getParameter("id");
	//		if(id!=null){
	//			TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//			bean1.setId(id);
	//			bean=consultMsgManager.findDataById(bean1);
	//		}else{
	//			SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
	//			request.setAttribute("date", sdf.format(new Date()));
	//		}
	//		return "edit";
	//	}
	//	/**
	//	 * <p>
	//	 * 信息保存
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>新增。</div>
	//	 * <div>修改。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String save() {
	//		log.info("C301Action edit.........");
	//		if(bean!=null){
	//			String msg="1";
	//			try {
	//					BaseUserBean user = (BaseUserBean) request.getSession().getAttribute(CommonConstant.SESSION_KEY_USER_CUSTOMER_SERVICE);
	//					if(user!=null){
	//						bean.setCreate_ip(getIpAddr());
	//						bean.setCreate_id(user.getUser_id());
	//						bean.setUpdate_ip(getIpAddr());
	//						bean.setUpdate_id(user.getUser_id());
	//					}
	//					msg=consultMsgManager.saveOrUpdateData(bean);
	//			} catch (Exception e) {
	//				msg=e.getMessage();
	//			}
	//			request.setAttribute("msg",msg);
	//		}else{
	//			request.setAttribute("msg", "信息保存失败!");
	//		}
	//		return SUCCESS;
	//	}
	//	/**
	//	 * <p>
	//	 * 删除。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>删除。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String del() {
	//		log.info("C301Action del.........");
	//		
	//		String id=request.getParameter("id");
	//		TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//		bean1.setId(id);
	//		String msg="1";
	//		try {
	//			msg=consultMsgManager.deleteDataById(bean1);
	//		} catch (Exception e) {
	//			msg=e.getMessage();
	//		}
	//		request.setAttribute("msg",msg);
	//		return SUCCESS;
	//	}
	
	
	
	/**
	 * BEAN类  咨询信息取得
	 * @return BEAN类  咨询信息
	 */
	//	/**
	//	 * <p>
	//	 * 编辑。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>编辑。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String edit() {
	//		log.info("C301Action edit.........");
	//		String id=request.getParameter("id");
	//		if(id!=null){
	//			TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//			bean1.setId(id);
	//			bean=consultMsgManager.findDataById(bean1);
	//		}else{
	//			SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
	//			request.setAttribute("date", sdf.format(new Date()));
	//		}
	//		return "edit";
	//	}
	//	/**
	//	 * <p>
	//	 * 信息保存
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>新增。</div>
	//	 * <div>修改。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String save() {
	//		log.info("C301Action edit.........");
	//		if(bean!=null){
	//			String msg="1";
	//			try {
	//					BaseUserBean user = (BaseUserBean) request.getSession().getAttribute(CommonConstant.SESSION_KEY_USER_CUSTOMER_SERVICE);
	//					if(user!=null){
	//						bean.setCreate_ip(getIpAddr());
	//						bean.setCreate_id(user.getUser_id());
	//						bean.setUpdate_ip(getIpAddr());
	//						bean.setUpdate_id(user.getUser_id());
	//					}
	//					msg=consultMsgManager.saveOrUpdateData(bean);
	//			} catch (Exception e) {
	//				msg=e.getMessage();
	//			}
	//			request.setAttribute("msg",msg);
	//		}else{
	//			request.setAttribute("msg", "信息保存失败!");
	//		}
	//		return SUCCESS;
	//	}
	//	/**
	//	 * <p>
	//	 * 删除。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>删除。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String del() {
	//		log.info("C301Action del.........");
	//		
	//		String id=request.getParameter("id");
	//		TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//		bean1.setId(id);
	//		String msg="1";
	//		try {
	//			msg=consultMsgManager.deleteDataById(bean1);
	//		} catch (Exception e) {
	//			msg=e.getMessage();
	//		}
	//		request.setAttribute("msg",msg);
	//		return SUCCESS;
	//	}
	
	
	
	/**
	 * BEAN类  咨询信息设定
	 * @param bean BEAN类  咨询信息
	 */
	//	/**
	//	 * <p>
	//	 * 编辑。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>编辑。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String edit() {
	//		log.info("C301Action edit.........");
	//		String id=request.getParameter("id");
	//		if(id!=null){
	//			TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//			bean1.setId(id);
	//			bean=consultMsgManager.findDataById(bean1);
	//		}else{
	//			SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
	//			request.setAttribute("date", sdf.format(new Date()));
	//		}
	//		return "edit";
	//	}
	//	/**
	//	 * <p>
	//	 * 信息保存
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>新增。</div>
	//	 * <div>修改。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String save() {
	//		log.info("C301Action edit.........");
	//		if(bean!=null){
	//			String msg="1";
	//			try {
	//					BaseUserBean user = (BaseUserBean) request.getSession().getAttribute(CommonConstant.SESSION_KEY_USER_CUSTOMER_SERVICE);
	//					if(user!=null){
	//						bean.setCreate_ip(getIpAddr());
	//						bean.setCreate_id(user.getUser_id());
	//						bean.setUpdate_ip(getIpAddr());
	//						bean.setUpdate_id(user.getUser_id());
	//					}
	//					msg=consultMsgManager.saveOrUpdateData(bean);
	//			} catch (Exception e) {
	//				msg=e.getMessage();
	//			}
	//			request.setAttribute("msg",msg);
	//		}else{
	//			request.setAttribute("msg", "信息保存失败!");
	//		}
	//		return SUCCESS;
	//	}
	//	/**
	//	 * <p>
	//	 * 删除。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>删除。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String del() {
	//		log.info("C301Action del.........");
	//		
	//		String id=request.getParameter("id");
	//		TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//		bean1.setId(id);
	//		String msg="1";
	//		try {
	//			msg=consultMsgManager.deleteDataById(bean1);
	//		} catch (Exception e) {
	//			msg=e.getMessage();
	//		}
	//		request.setAttribute("msg",msg);
	//		return SUCCESS;
	//	}
	
	
	
	/**
	 * BEAN类  咨询信息取得
	 * @return BEAN类  咨询信息
	 */
	//	/**
	//	 * <p>
	//	 * 编辑。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>编辑。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String edit() {
	//		log.info("C301Action edit.........");
	//		String id=request.getParameter("id");
	//		if(id!=null){
	//			TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//			bean1.setId(id);
	//			bean=consultMsgManager.findDataById(bean1);
	//		}else{
	//			SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
	//			request.setAttribute("date", sdf.format(new Date()));
	//		}
	//		return "edit";
	//	}
	//	/**
	//	 * <p>
	//	 * 信息保存
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>新增。</div>
	//	 * <div>修改。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String save() {
	//		log.info("C301Action edit.........");
	//		if(bean!=null){
	//			String msg="1";
	//			try {
	//					BaseUserBean user = (BaseUserBean) request.getSession().getAttribute(CommonConstant.SESSION_KEY_USER_CUSTOMER_SERVICE);
	//					if(user!=null){
	//						bean.setCreate_ip(getIpAddr());
	//						bean.setCreate_id(user.getUser_id());
	//						bean.setUpdate_ip(getIpAddr());
	//						bean.setUpdate_id(user.getUser_id());
	//					}
	//					msg=consultMsgManager.saveOrUpdateData(bean);
	//			} catch (Exception e) {
	//				msg=e.getMessage();
	//			}
	//			request.setAttribute("msg",msg);
	//		}else{
	//			request.setAttribute("msg", "信息保存失败!");
	//		}
	//		return SUCCESS;
	//	}
	//	/**
	//	 * <p>
	//	 * 删除。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>删除。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String del() {
	//		log.info("C301Action del.........");
	//		
	//		String id=request.getParameter("id");
	//		TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//		bean1.setId(id);
	//		String msg="1";
	//		try {
	//			msg=consultMsgManager.deleteDataById(bean1);
	//		} catch (Exception e) {
	//			msg=e.getMessage();
	//		}
	//		request.setAttribute("msg",msg);
	//		return SUCCESS;
	//	}
	
	
	
	/**
	 * BEAN类  咨询信息设定
	 * @param bean BEAN类  咨询信息
	 */
	//	/**
	//	 * <p>
	//	 * 编辑。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>编辑。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String edit() {
	//		log.info("C301Action edit.........");
	//		String id=request.getParameter("id");
	//		if(id!=null){
	//			TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//			bean1.setId(id);
	//			bean=consultMsgManager.findDataById(bean1);
	//		}else{
	//			SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
	//			request.setAttribute("date", sdf.format(new Date()));
	//		}
	//		return "edit";
	//	}
	//	/**
	//	 * <p>
	//	 * 信息保存
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>新增。</div>
	//	 * <div>修改。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String save() {
	//		log.info("C301Action edit.........");
	//		if(bean!=null){
	//			String msg="1";
	//			try {
	//					BaseUserBean user = (BaseUserBean) request.getSession().getAttribute(CommonConstant.SESSION_KEY_USER_CUSTOMER_SERVICE);
	//					if(user!=null){
	//						bean.setCreate_ip(getIpAddr());
	//						bean.setCreate_id(user.getUser_id());
	//						bean.setUpdate_ip(getIpAddr());
	//						bean.setUpdate_id(user.getUser_id());
	//					}
	//					msg=consultMsgManager.saveOrUpdateData(bean);
	//			} catch (Exception e) {
	//				msg=e.getMessage();
	//			}
	//			request.setAttribute("msg",msg);
	//		}else{
	//			request.setAttribute("msg", "信息保存失败!");
	//		}
	//		return SUCCESS;
	//	}
	//	/**
	//	 * <p>
	//	 * 删除。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>删除。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String del() {
	//		log.info("C301Action del.........");
	//		
	//		String id=request.getParameter("id");
	//		TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//		bean1.setId(id);
	//		String msg="1";
	//		try {
	//			msg=consultMsgManager.deleteDataById(bean1);
	//		} catch (Exception e) {
	//			msg=e.getMessage();
	//		}
	//		request.setAttribute("msg",msg);
	//		return SUCCESS;
	//	}
	
	
	
	/**
	 * BEAN类  咨询信息取得
	 * @return BEAN类  咨询信息
	 */
	//	/**
	//	 * <p>
	//	 * 编辑。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>编辑。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String edit() {
	//		log.info("C301Action edit.........");
	//		String id=request.getParameter("id");
	//		if(id!=null){
	//			TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//			bean1.setId(id);
	//			bean=consultMsgManager.findDataById(bean1);
	//		}else{
	//			SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
	//			request.setAttribute("date", sdf.format(new Date()));
	//		}
	//		return "edit";
	//	}
	//	/**
	//	 * <p>
	//	 * 信息保存
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>新增。</div>
	//	 * <div>修改。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String save() {
	//		log.info("C301Action edit.........");
	//		if(bean!=null){
	//			String msg="1";
	//			try {
	//					BaseUserBean user = (BaseUserBean) request.getSession().getAttribute(CommonConstant.SESSION_KEY_USER_CUSTOMER_SERVICE);
	//					if(user!=null){
	//						bean.setCreate_ip(getIpAddr());
	//						bean.setCreate_id(user.getUser_id());
	//						bean.setUpdate_ip(getIpAddr());
	//						bean.setUpdate_id(user.getUser_id());
	//					}
	//					msg=consultMsgManager.saveOrUpdateData(bean);
	//			} catch (Exception e) {
	//				msg=e.getMessage();
	//			}
	//			request.setAttribute("msg",msg);
	//		}else{
	//			request.setAttribute("msg", "信息保存失败!");
	//		}
	//		return SUCCESS;
	//	}
	//	/**
	//	 * <p>
	//	 * 删除。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>删除。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String del() {
	//		log.info("C301Action del.........");
	//		
	//		String id=request.getParameter("id");
	//		TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//		bean1.setId(id);
	//		String msg="1";
	//		try {
	//			msg=consultMsgManager.deleteDataById(bean1);
	//		} catch (Exception e) {
	//			msg=e.getMessage();
	//		}
	//		request.setAttribute("msg",msg);
	//		return SUCCESS;
	//	}
	
	
	
	/**
	 * BEAN类  咨询信息取得
	 * @return BEAN类  咨询信息
	 */
	//	/**
	//	 * <p>
	//	 * 编辑。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>编辑。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String edit() {
	//		log.info("C301Action edit.........");
	//		String id=request.getParameter("id");
	//		if(id!=null){
	//			TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//			bean1.setId(id);
	//			bean=consultMsgManager.findDataById(bean1);
	//		}else{
	//			SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
	//			request.setAttribute("date", sdf.format(new Date()));
	//		}
	//		return "edit";
	//	}
	//	/**
	//	 * <p>
	//	 * 信息保存
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>新增。</div>
	//	 * <div>修改。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String save() {
	//		log.info("C301Action edit.........");
	//		if(bean!=null){
	//			String msg="1";
	//			try {
	//					BaseUserBean user = (BaseUserBean) request.getSession().getAttribute(CommonConstant.SESSION_KEY_USER_CUSTOMER_SERVICE);
	//					if(user!=null){
	//						bean.setCreate_ip(getIpAddr());
	//						bean.setCreate_id(user.getUser_id());
	//						bean.setUpdate_ip(getIpAddr());
	//						bean.setUpdate_id(user.getUser_id());
	//					}
	//					msg=consultMsgManager.saveOrUpdateData(bean);
	//			} catch (Exception e) {
	//				msg=e.getMessage();
	//			}
	//			request.setAttribute("msg",msg);
	//		}else{
	//			request.setAttribute("msg", "信息保存失败!");
	//		}
	//		return SUCCESS;
	//	}
	//	/**
	//	 * <p>
	//	 * 删除。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>删除。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String del() {
	//		log.info("C301Action del.........");
	//		
	//		String id=request.getParameter("id");
	//		TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//		bean1.setId(id);
	//		String msg="1";
	//		try {
	//			msg=consultMsgManager.deleteDataById(bean1);
	//		} catch (Exception e) {
	//			msg=e.getMessage();
	//		}
	//		request.setAttribute("msg",msg);
	//		return SUCCESS;
	//	}
	
	
	
	/**
	 * BEAN类  咨询信息设定
	 * @param bean BEAN类  咨询信息
	 */
	//	/**
	//	 * <p>
	//	 * 编辑。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>编辑。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String edit() {
	//		log.info("C301Action edit.........");
	//		String id=request.getParameter("id");
	//		if(id!=null){
	//			TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//			bean1.setId(id);
	//			bean=consultMsgManager.findDataById(bean1);
	//		}else{
	//			SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
	//			request.setAttribute("date", sdf.format(new Date()));
	//		}
	//		return "edit";
	//	}
	//	/**
	//	 * <p>
	//	 * 信息保存
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>新增。</div>
	//	 * <div>修改。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String save() {
	//		log.info("C301Action edit.........");
	//		if(bean!=null){
	//			String msg="1";
	//			try {
	//					BaseUserBean user = (BaseUserBean) request.getSession().getAttribute(CommonConstant.SESSION_KEY_USER_CUSTOMER_SERVICE);
	//					if(user!=null){
	//						bean.setCreate_ip(getIpAddr());
	//						bean.setCreate_id(user.getUser_id());
	//						bean.setUpdate_ip(getIpAddr());
	//						bean.setUpdate_id(user.getUser_id());
	//					}
	//					msg=consultMsgManager.saveOrUpdateData(bean);
	//			} catch (Exception e) {
	//				msg=e.getMessage();
	//			}
	//			request.setAttribute("msg",msg);
	//		}else{
	//			request.setAttribute("msg", "信息保存失败!");
	//		}
	//		return SUCCESS;
	//	}
	//	/**
	//	 * <p>
	//	 * 删除。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>删除。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String del() {
	//		log.info("C301Action del.........");
	//		
	//		String id=request.getParameter("id");
	//		TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//		bean1.setId(id);
	//		String msg="1";
	//		try {
	//			msg=consultMsgManager.deleteDataById(bean1);
	//		} catch (Exception e) {
	//			msg=e.getMessage();
	//		}
	//		request.setAttribute("msg",msg);
	//		return SUCCESS;
	//	}
	
	
	
	/**
	 * BEAN类  咨询信息取得
	 * @return BEAN类  咨询信息
	 */
	//	/**
	//	 * <p>
	//	 * 编辑。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>编辑。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String edit() {
	//		log.info("C301Action edit.........");
	//		String id=request.getParameter("id");
	//		if(id!=null){
	//			TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//			bean1.setId(id);
	//			bean=consultMsgManager.findDataById(bean1);
	//		}else{
	//			SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
	//			request.setAttribute("date", sdf.format(new Date()));
	//		}
	//		return "edit";
	//	}
	//	/**
	//	 * <p>
	//	 * 信息保存
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>新增。</div>
	//	 * <div>修改。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String save() {
	//		log.info("C301Action edit.........");
	//		if(bean!=null){
	//			String msg="1";
	//			try {
	//					BaseUserBean user = (BaseUserBean) request.getSession().getAttribute(CommonConstant.SESSION_KEY_USER_CUSTOMER_SERVICE);
	//					if(user!=null){
	//						bean.setCreate_ip(getIpAddr());
	//						bean.setCreate_id(user.getUser_id());
	//						bean.setUpdate_ip(getIpAddr());
	//						bean.setUpdate_id(user.getUser_id());
	//					}
	//					msg=consultMsgManager.saveOrUpdateData(bean);
	//			} catch (Exception e) {
	//				msg=e.getMessage();
	//			}
	//			request.setAttribute("msg",msg);
	//		}else{
	//			request.setAttribute("msg", "信息保存失败!");
	//		}
	//		return SUCCESS;
	//	}
	//	/**
	//	 * <p>
	//	 * 删除。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>删除。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String del() {
	//		log.info("C301Action del.........");
	//		
	//		String id=request.getParameter("id");
	//		TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//		bean1.setId(id);
	//		String msg="1";
	//		try {
	//			msg=consultMsgManager.deleteDataById(bean1);
	//		} catch (Exception e) {
	//			msg=e.getMessage();
	//		}
	//		request.setAttribute("msg",msg);
	//		return SUCCESS;
	//	}
	
	
	
	/**
	 * BEAN类  咨询信息设定
	 * @param bean BEAN类  咨询信息
	 */
	//	/**
	//	 * <p>
	//	 * 编辑。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>编辑。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String edit() {
	//		log.info("C301Action edit.........");
	//		String id=request.getParameter("id");
	//		if(id!=null){
	//			TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//			bean1.setId(id);
	//			bean=consultMsgManager.findDataById(bean1);
	//		}else{
	//			SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
	//			request.setAttribute("date", sdf.format(new Date()));
	//		}
	//		return "edit";
	//	}
	//	/**
	//	 * <p>
	//	 * 信息保存
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>新增。</div>
	//	 * <div>修改。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String save() {
	//		log.info("C301Action edit.........");
	//		if(bean!=null){
	//			String msg="1";
	//			try {
	//					BaseUserBean user = (BaseUserBean) request.getSession().getAttribute(CommonConstant.SESSION_KEY_USER_CUSTOMER_SERVICE);
	//					if(user!=null){
	//						bean.setCreate_ip(getIpAddr());
	//						bean.setCreate_id(user.getUser_id());
	//						bean.setUpdate_ip(getIpAddr());
	//						bean.setUpdate_id(user.getUser_id());
	//					}
	//					msg=consultMsgManager.saveOrUpdateData(bean);
	//			} catch (Exception e) {
	//				msg=e.getMessage();
	//			}
	//			request.setAttribute("msg",msg);
	//		}else{
	//			request.setAttribute("msg", "信息保存失败!");
	//		}
	//		return SUCCESS;
	//	}
	//	/**
	//	 * <p>
	//	 * 删除。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>删除。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String del() {
	//		log.info("C301Action del.........");
	//		
	//		String id=request.getParameter("id");
	//		TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//		bean1.setId(id);
	//		String msg="1";
	//		try {
	//			msg=consultMsgManager.deleteDataById(bean1);
	//		} catch (Exception e) {
	//			msg=e.getMessage();
	//		}
	//		request.setAttribute("msg",msg);
	//		return SUCCESS;
	//	}
	
	
	
	/**
	 * BEAN类  咨询信息取得
	 * @return BEAN类  咨询信息
	 */
	//	/**
	//	 * <p>
	//	 * 编辑。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>编辑。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String edit() {
	//		log.info("C301Action edit.........");
	//		String id=request.getParameter("id");
	//		if(id!=null){
	//			TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//			bean1.setId(id);
	//			bean=consultMsgManager.findDataById(bean1);
	//		}else{
	//			SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
	//			request.setAttribute("date", sdf.format(new Date()));
	//		}
	//		return "edit";
	//	}
	//	/**
	//	 * <p>
	//	 * 信息保存
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>新增。</div>
	//	 * <div>修改。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String save() {
	//		log.info("C301Action edit.........");
	//		if(bean!=null){
	//			String msg="1";
	//			try {
	//					BaseUserBean user = (BaseUserBean) request.getSession().getAttribute(CommonConstant.SESSION_KEY_USER_CUSTOMER_SERVICE);
	//					if(user!=null){
	//						bean.setCreate_ip(getIpAddr());
	//						bean.setCreate_id(user.getUser_id());
	//						bean.setUpdate_ip(getIpAddr());
	//						bean.setUpdate_id(user.getUser_id());
	//					}
	//					msg=consultMsgManager.saveOrUpdateData(bean);
	//			} catch (Exception e) {
	//				msg=e.getMessage();
	//			}
	//			request.setAttribute("msg",msg);
	//		}else{
	//			request.setAttribute("msg", "信息保存失败!");
	//		}
	//		return SUCCESS;
	//	}
	//	/**
	//	 * <p>
	//	 * 删除。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>删除。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String del() {
	//		log.info("C301Action del.........");
	//		
	//		String id=request.getParameter("id");
	//		TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//		bean1.setId(id);
	//		String msg="1";
	//		try {
	//			msg=consultMsgManager.deleteDataById(bean1);
	//		} catch (Exception e) {
	//			msg=e.getMessage();
	//		}
	//		request.setAttribute("msg",msg);
	//		return SUCCESS;
	//	}
	
	
	
	/**
	 * BEAN类  咨询信息取得
	 * @return BEAN类  咨询信息
	 */
	//	/**
	//	 * <p>
	//	 * 编辑。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>编辑。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String edit() {
	//		log.info("C301Action edit.........");
	//		String id=request.getParameter("id");
	//		if(id!=null){
	//			TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//			bean1.setId(id);
	//			bean=consultMsgManager.findDataById(bean1);
	//		}else{
	//			SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
	//			request.setAttribute("date", sdf.format(new Date()));
	//		}
	//		return "edit";
	//	}
	//	/**
	//	 * <p>
	//	 * 信息保存
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>新增。</div>
	//	 * <div>修改。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String save() {
	//		log.info("C301Action edit.........");
	//		if(bean!=null){
	//			String msg="1";
	//			try {
	//					BaseUserBean user = (BaseUserBean) request.getSession().getAttribute(CommonConstant.SESSION_KEY_USER_CUSTOMER_SERVICE);
	//					if(user!=null){
	//						bean.setCreate_ip(getIpAddr());
	//						bean.setCreate_id(user.getUser_id());
	//						bean.setUpdate_ip(getIpAddr());
	//						bean.setUpdate_id(user.getUser_id());
	//					}
	//					msg=consultMsgManager.saveOrUpdateData(bean);
	//			} catch (Exception e) {
	//				msg=e.getMessage();
	//			}
	//			request.setAttribute("msg",msg);
	//		}else{
	//			request.setAttribute("msg", "信息保存失败!");
	//		}
	//		return SUCCESS;
	//	}
	//	/**
	//	 * <p>
	//	 * 删除。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>删除。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String del() {
	//		log.info("C301Action del.........");
	//		
	//		String id=request.getParameter("id");
	//		TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//		bean1.setId(id);
	//		String msg="1";
	//		try {
	//			msg=consultMsgManager.deleteDataById(bean1);
	//		} catch (Exception e) {
	//			msg=e.getMessage();
	//		}
	//		request.setAttribute("msg",msg);
	//		return SUCCESS;
	//	}
	
	
	
	/**
	 * BEAN类  咨询信息设定
	 * @param bean BEAN类  咨询信息
	 */
	//	/**
	//	 * <p>
	//	 * 编辑。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>编辑。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String edit() {
	//		log.info("C301Action edit.........");
	//		String id=request.getParameter("id");
	//		if(id!=null){
	//			TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//			bean1.setId(id);
	//			bean=consultMsgManager.findDataById(bean1);
	//		}else{
	//			SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
	//			request.setAttribute("date", sdf.format(new Date()));
	//		}
	//		return "edit";
	//	}
	//	/**
	//	 * <p>
	//	 * 信息保存
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>新增。</div>
	//	 * <div>修改。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String save() {
	//		log.info("C301Action edit.........");
	//		if(bean!=null){
	//			String msg="1";
	//			try {
	//					BaseUserBean user = (BaseUserBean) request.getSession().getAttribute(CommonConstant.SESSION_KEY_USER_CUSTOMER_SERVICE);
	//					if(user!=null){
	//						bean.setCreate_ip(getIpAddr());
	//						bean.setCreate_id(user.getUser_id());
	//						bean.setUpdate_ip(getIpAddr());
	//						bean.setUpdate_id(user.getUser_id());
	//					}
	//					msg=consultMsgManager.saveOrUpdateData(bean);
	//			} catch (Exception e) {
	//				msg=e.getMessage();
	//			}
	//			request.setAttribute("msg",msg);
	//		}else{
	//			request.setAttribute("msg", "信息保存失败!");
	//		}
	//		return SUCCESS;
	//	}
	//	/**
	//	 * <p>
	//	 * 删除。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>删除。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String del() {
	//		log.info("C301Action del.........");
	//		
	//		String id=request.getParameter("id");
	//		TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//		bean1.setId(id);
	//		String msg="1";
	//		try {
	//			msg=consultMsgManager.deleteDataById(bean1);
	//		} catch (Exception e) {
	//			msg=e.getMessage();
	//		}
	//		request.setAttribute("msg",msg);
	//		return SUCCESS;
	//	}
	
	
	
	/**
	 * BEAN类  咨询信息取得
	 * @return BEAN类  咨询信息
	 */
	//	/**
	//	 * <p>
	//	 * 编辑。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>编辑。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String edit() {
	//		log.info("C301Action edit.........");
	//		String id=request.getParameter("id");
	//		if(id!=null){
	//			TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//			bean1.setId(id);
	//			bean=consultMsgManager.findDataById(bean1);
	//		}else{
	//			SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
	//			request.setAttribute("date", sdf.format(new Date()));
	//		}
	//		return "edit";
	//	}
	//	/**
	//	 * <p>
	//	 * 信息保存
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>新增。</div>
	//	 * <div>修改。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String save() {
	//		log.info("C301Action edit.........");
	//		if(bean!=null){
	//			String msg="1";
	//			try {
	//					BaseUserBean user = (BaseUserBean) request.getSession().getAttribute(CommonConstant.SESSION_KEY_USER_CUSTOMER_SERVICE);
	//					if(user!=null){
	//						bean.setCreate_ip(getIpAddr());
	//						bean.setCreate_id(user.getUser_id());
	//						bean.setUpdate_ip(getIpAddr());
	//						bean.setUpdate_id(user.getUser_id());
	//					}
	//					msg=consultMsgManager.saveOrUpdateData(bean);
	//			} catch (Exception e) {
	//				msg=e.getMessage();
	//			}
	//			request.setAttribute("msg",msg);
	//		}else{
	//			request.setAttribute("msg", "信息保存失败!");
	//		}
	//		return SUCCESS;
	//	}
	//	/**
	//	 * <p>
	//	 * 删除。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>删除。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String del() {
	//		log.info("C301Action del.........");
	//		
	//		String id=request.getParameter("id");
	//		TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//		bean1.setId(id);
	//		String msg="1";
	//		try {
	//			msg=consultMsgManager.deleteDataById(bean1);
	//		} catch (Exception e) {
	//			msg=e.getMessage();
	//		}
	//		request.setAttribute("msg",msg);
	//		return SUCCESS;
	//	}
	
	
	
	/**
	 * BEAN类  咨询信息取得
	 * @return BEAN类  咨询信息
	 */
	//	/**
	//	 * <p>
	//	 * 编辑。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>编辑。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String edit() {
	//		log.info("C301Action edit.........");
	//		String id=request.getParameter("id");
	//		if(id!=null){
	//			TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//			bean1.setId(id);
	//			bean=consultMsgManager.findDataById(bean1);
	//		}else{
	//			SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
	//			request.setAttribute("date", sdf.format(new Date()));
	//		}
	//		return "edit";
	//	}
	//	/**
	//	 * <p>
	//	 * 信息保存
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>新增。</div>
	//	 * <div>修改。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String save() {
	//		log.info("C301Action edit.........");
	//		if(bean!=null){
	//			String msg="1";
	//			try {
	//					BaseUserBean user = (BaseUserBean) request.getSession().getAttribute(CommonConstant.SESSION_KEY_USER_CUSTOMER_SERVICE);
	//					if(user!=null){
	//						bean.setCreate_ip(getIpAddr());
	//						bean.setCreate_id(user.getUser_id());
	//						bean.setUpdate_ip(getIpAddr());
	//						bean.setUpdate_id(user.getUser_id());
	//					}
	//					msg=consultMsgManager.saveOrUpdateData(bean);
	//			} catch (Exception e) {
	//				msg=e.getMessage();
	//			}
	//			request.setAttribute("msg",msg);
	//		}else{
	//			request.setAttribute("msg", "信息保存失败!");
	//		}
	//		return SUCCESS;
	//	}
	//	/**
	//	 * <p>
	//	 * 删除。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>删除。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String del() {
	//		log.info("C301Action del.........");
	//		
	//		String id=request.getParameter("id");
	//		TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//		bean1.setId(id);
	//		String msg="1";
	//		try {
	//			msg=consultMsgManager.deleteDataById(bean1);
	//		} catch (Exception e) {
	//			msg=e.getMessage();
	//		}
	//		request.setAttribute("msg",msg);
	//		return SUCCESS;
	//	}
	
	
	
	/**
	 * BEAN类  咨询信息设定
	 * @param bean BEAN类  咨询信息
	 */
	//	/**
	//	 * <p>
	//	 * 编辑。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>编辑。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String edit() {
	//		log.info("C301Action edit.........");
	//		String id=request.getParameter("id");
	//		if(id!=null){
	//			TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//			bean1.setId(id);
	//			bean=consultMsgManager.findDataById(bean1);
	//		}else{
	//			SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
	//			request.setAttribute("date", sdf.format(new Date()));
	//		}
	//		return "edit";
	//	}
	//	/**
	//	 * <p>
	//	 * 信息保存
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>新增。</div>
	//	 * <div>修改。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String save() {
	//		log.info("C301Action edit.........");
	//		if(bean!=null){
	//			String msg="1";
	//			try {
	//					BaseUserBean user = (BaseUserBean) request.getSession().getAttribute(CommonConstant.SESSION_KEY_USER_CUSTOMER_SERVICE);
	//					if(user!=null){
	//						bean.setCreate_ip(getIpAddr());
	//						bean.setCreate_id(user.getUser_id());
	//						bean.setUpdate_ip(getIpAddr());
	//						bean.setUpdate_id(user.getUser_id());
	//					}
	//					msg=consultMsgManager.saveOrUpdateData(bean);
	//			} catch (Exception e) {
	//				msg=e.getMessage();
	//			}
	//			request.setAttribute("msg",msg);
	//		}else{
	//			request.setAttribute("msg", "信息保存失败!");
	//		}
	//		return SUCCESS;
	//	}
	//	/**
	//	 * <p>
	//	 * 删除。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>删除。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String del() {
	//		log.info("C301Action del.........");
	//		
	//		String id=request.getParameter("id");
	//		TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//		bean1.setId(id);
	//		String msg="1";
	//		try {
	//			msg=consultMsgManager.deleteDataById(bean1);
	//		} catch (Exception e) {
	//			msg=e.getMessage();
	//		}
	//		request.setAttribute("msg",msg);
	//		return SUCCESS;
	//	}
	
	
	
	/**
	 * BEAN类  咨询信息取得
	 * @return BEAN类  咨询信息
	 */
	//	/**
	//	 * <p>
	//	 * 编辑。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>编辑。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String edit() {
	//		log.info("C301Action edit.........");
	//		String id=request.getParameter("id");
	//		if(id!=null){
	//			TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//			bean1.setId(id);
	//			bean=consultMsgManager.findDataById(bean1);
	//		}else{
	//			SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
	//			request.setAttribute("date", sdf.format(new Date()));
	//		}
	//		return "edit";
	//	}
	//	/**
	//	 * <p>
	//	 * 信息保存
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>新增。</div>
	//	 * <div>修改。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String save() {
	//		log.info("C301Action edit.........");
	//		if(bean!=null){
	//			String msg="1";
	//			try {
	//					BaseUserBean user = (BaseUserBean) request.getSession().getAttribute(CommonConstant.SESSION_KEY_USER_CUSTOMER_SERVICE);
	//					if(user!=null){
	//						bean.setCreate_ip(getIpAddr());
	//						bean.setCreate_id(user.getUser_id());
	//						bean.setUpdate_ip(getIpAddr());
	//						bean.setUpdate_id(user.getUser_id());
	//					}
	//					msg=consultMsgManager.saveOrUpdateData(bean);
	//			} catch (Exception e) {
	//				msg=e.getMessage();
	//			}
	//			request.setAttribute("msg",msg);
	//		}else{
	//			request.setAttribute("msg", "信息保存失败!");
	//		}
	//		return SUCCESS;
	//	}
	//	/**
	//	 * <p>
	//	 * 删除。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>删除。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String del() {
	//		log.info("C301Action del.........");
	//		
	//		String id=request.getParameter("id");
	//		TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//		bean1.setId(id);
	//		String msg="1";
	//		try {
	//			msg=consultMsgManager.deleteDataById(bean1);
	//		} catch (Exception e) {
	//			msg=e.getMessage();
	//		}
	//		request.setAttribute("msg",msg);
	//		return SUCCESS;
	//	}
	
	
	
	/**
	 * BEAN类  咨询信息设定
	 * @param bean BEAN类  咨询信息
	 */
	//	/**
	//	 * <p>
	//	 * 编辑。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>编辑。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String edit() {
	//		log.info("C301Action edit.........");
	//		String id=request.getParameter("id");
	//		if(id!=null){
	//			TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//			bean1.setId(id);
	//			bean=consultMsgManager.findDataById(bean1);
	//		}else{
	//			SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
	//			request.setAttribute("date", sdf.format(new Date()));
	//		}
	//		return "edit";
	//	}
	//	/**
	//	 * <p>
	//	 * 信息保存
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>新增。</div>
	//	 * <div>修改。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String save() {
	//		log.info("C301Action edit.........");
	//		if(bean!=null){
	//			String msg="1";
	//			try {
	//					BaseUserBean user = (BaseUserBean) request.getSession().getAttribute(CommonConstant.SESSION_KEY_USER_CUSTOMER_SERVICE);
	//					if(user!=null){
	//						bean.setCreate_ip(getIpAddr());
	//						bean.setCreate_id(user.getUser_id());
	//						bean.setUpdate_ip(getIpAddr());
	//						bean.setUpdate_id(user.getUser_id());
	//					}
	//					msg=consultMsgManager.saveOrUpdateData(bean);
	//			} catch (Exception e) {
	//				msg=e.getMessage();
	//			}
	//			request.setAttribute("msg",msg);
	//		}else{
	//			request.setAttribute("msg", "信息保存失败!");
	//		}
	//		return SUCCESS;
	//	}
	//	/**
	//	 * <p>
	//	 * 删除。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>删除。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String del() {
	//		log.info("C301Action del.........");
	//		
	//		String id=request.getParameter("id");
	//		TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//		bean1.setId(id);
	//		String msg="1";
	//		try {
	//			msg=consultMsgManager.deleteDataById(bean1);
	//		} catch (Exception e) {
	//			msg=e.getMessage();
	//		}
	//		request.setAttribute("msg",msg);
	//		return SUCCESS;
	//	}
	
	
	
	/**
	 * BEAN类  咨询信息取得
	 * @return BEAN类  咨询信息
	 */
	//	/**
	//	 * <p>
	//	 * 编辑。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>编辑。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String edit() {
	//		log.info("C301Action edit.........");
	//		String id=request.getParameter("id");
	//		if(id!=null){
	//			TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//			bean1.setId(id);
	//			bean=consultMsgManager.findDataById(bean1);
	//		}else{
	//			SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
	//			request.setAttribute("date", sdf.format(new Date()));
	//		}
	//		return "edit";
	//	}
	//	/**
	//	 * <p>
	//	 * 信息保存
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>新增。</div>
	//	 * <div>修改。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String save() {
	//		log.info("C301Action edit.........");
	//		if(bean!=null){
	//			String msg="1";
	//			try {
	//					BaseUserBean user = (BaseUserBean) request.getSession().getAttribute(CommonConstant.SESSION_KEY_USER_CUSTOMER_SERVICE);
	//					if(user!=null){
	//						bean.setCreate_ip(getIpAddr());
	//						bean.setCreate_id(user.getUser_id());
	//						bean.setUpdate_ip(getIpAddr());
	//						bean.setUpdate_id(user.getUser_id());
	//					}
	//					msg=consultMsgManager.saveOrUpdateData(bean);
	//			} catch (Exception e) {
	//				msg=e.getMessage();
	//			}
	//			request.setAttribute("msg",msg);
	//		}else{
	//			request.setAttribute("msg", "信息保存失败!");
	//		}
	//		return SUCCESS;
	//	}
	//	/**
	//	 * <p>
	//	 * 删除。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>删除。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String del() {
	//		log.info("C301Action del.........");
	//		
	//		String id=request.getParameter("id");
	//		TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//		bean1.setId(id);
	//		String msg="1";
	//		try {
	//			msg=consultMsgManager.deleteDataById(bean1);
	//		} catch (Exception e) {
	//			msg=e.getMessage();
	//		}
	//		request.setAttribute("msg",msg);
	//		return SUCCESS;
	//	}
	
	
	
	/**
	 * BEAN类  咨询信息取得
	 * @return BEAN类  咨询信息
	 */
	//	/**
	//	 * <p>
	//	 * 编辑。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>编辑。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String edit() {
	//		log.info("C301Action edit.........");
	//		String id=request.getParameter("id");
	//		if(id!=null){
	//			TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//			bean1.setId(id);
	//			bean=consultMsgManager.findDataById(bean1);
	//		}else{
	//			SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
	//			request.setAttribute("date", sdf.format(new Date()));
	//		}
	//		return "edit";
	//	}
	//	/**
	//	 * <p>
	//	 * 信息保存
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>新增。</div>
	//	 * <div>修改。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String save() {
	//		log.info("C301Action edit.........");
	//		if(bean!=null){
	//			String msg="1";
	//			try {
	//					BaseUserBean user = (BaseUserBean) request.getSession().getAttribute(CommonConstant.SESSION_KEY_USER_CUSTOMER_SERVICE);
	//					if(user!=null){
	//						bean.setCreate_ip(getIpAddr());
	//						bean.setCreate_id(user.getUser_id());
	//						bean.setUpdate_ip(getIpAddr());
	//						bean.setUpdate_id(user.getUser_id());
	//					}
	//					msg=consultMsgManager.saveOrUpdateData(bean);
	//			} catch (Exception e) {
	//				msg=e.getMessage();
	//			}
	//			request.setAttribute("msg",msg);
	//		}else{
	//			request.setAttribute("msg", "信息保存失败!");
	//		}
	//		return SUCCESS;
	//	}
	//	/**
	//	 * <p>
	//	 * 删除。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>删除。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String del() {
	//		log.info("C301Action del.........");
	//		
	//		String id=request.getParameter("id");
	//		TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//		bean1.setId(id);
	//		String msg="1";
	//		try {
	//			msg=consultMsgManager.deleteDataById(bean1);
	//		} catch (Exception e) {
	//			msg=e.getMessage();
	//		}
	//		request.setAttribute("msg",msg);
	//		return SUCCESS;
	//	}
	
	
	
	/**
	 * BEAN类  咨询信息设定
	 * @param bean BEAN类  咨询信息
	 */
	//	/**
	//	 * <p>
	//	 * 编辑。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>编辑。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String edit() {
	//		log.info("C301Action edit.........");
	//		String id=request.getParameter("id");
	//		if(id!=null){
	//			TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//			bean1.setId(id);
	//			bean=consultMsgManager.findDataById(bean1);
	//		}else{
	//			SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
	//			request.setAttribute("date", sdf.format(new Date()));
	//		}
	//		return "edit";
	//	}
	//	/**
	//	 * <p>
	//	 * 信息保存
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>新增。</div>
	//	 * <div>修改。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String save() {
	//		log.info("C301Action edit.........");
	//		if(bean!=null){
	//			String msg="1";
	//			try {
	//					BaseUserBean user = (BaseUserBean) request.getSession().getAttribute(CommonConstant.SESSION_KEY_USER_CUSTOMER_SERVICE);
	//					if(user!=null){
	//						bean.setCreate_ip(getIpAddr());
	//						bean.setCreate_id(user.getUser_id());
	//						bean.setUpdate_ip(getIpAddr());
	//						bean.setUpdate_id(user.getUser_id());
	//					}
	//					msg=consultMsgManager.saveOrUpdateData(bean);
	//			} catch (Exception e) {
	//				msg=e.getMessage();
	//			}
	//			request.setAttribute("msg",msg);
	//		}else{
	//			request.setAttribute("msg", "信息保存失败!");
	//		}
	//		return SUCCESS;
	//	}
	//	/**
	//	 * <p>
	//	 * 删除。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>删除。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String del() {
	//		log.info("C301Action del.........");
	//		
	//		String id=request.getParameter("id");
	//		TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//		bean1.setId(id);
	//		String msg="1";
	//		try {
	//			msg=consultMsgManager.deleteDataById(bean1);
	//		} catch (Exception e) {
	//			msg=e.getMessage();
	//		}
	//		request.setAttribute("msg",msg);
	//		return SUCCESS;
	//	}
	
	
	
	/**
	 * BEAN类  咨询信息取得
	 * @return BEAN类  咨询信息
	 */
	//	/**
	//	 * <p>
	//	 * 编辑。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>编辑。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String edit() {
	//		log.info("C301Action edit.........");
	//		String id=request.getParameter("id");
	//		if(id!=null){
	//			TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//			bean1.setId(id);
	//			bean=consultMsgManager.findDataById(bean1);
	//		}else{
	//			SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
	//			request.setAttribute("date", sdf.format(new Date()));
	//		}
	//		return "edit";
	//	}
	//	/**
	//	 * <p>
	//	 * 信息保存
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>新增。</div>
	//	 * <div>修改。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String save() {
	//		log.info("C301Action edit.........");
	//		if(bean!=null){
	//			String msg="1";
	//			try {
	//					BaseUserBean user = (BaseUserBean) request.getSession().getAttribute(CommonConstant.SESSION_KEY_USER_CUSTOMER_SERVICE);
	//					if(user!=null){
	//						bean.setCreate_ip(getIpAddr());
	//						bean.setCreate_id(user.getUser_id());
	//						bean.setUpdate_ip(getIpAddr());
	//						bean.setUpdate_id(user.getUser_id());
	//					}
	//					msg=consultMsgManager.saveOrUpdateData(bean);
	//			} catch (Exception e) {
	//				msg=e.getMessage();
	//			}
	//			request.setAttribute("msg",msg);
	//		}else{
	//			request.setAttribute("msg", "信息保存失败!");
	//		}
	//		return SUCCESS;
	//	}
	//	/**
	//	 * <p>
	//	 * 删除。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>删除。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String del() {
	//		log.info("C301Action del.........");
	//		
	//		String id=request.getParameter("id");
	//		TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//		bean1.setId(id);
	//		String msg="1";
	//		try {
	//			msg=consultMsgManager.deleteDataById(bean1);
	//		} catch (Exception e) {
	//			msg=e.getMessage();
	//		}
	//		request.setAttribute("msg",msg);
	//		return SUCCESS;
	//	}
	
	
	
	/**
	 * BEAN类  咨询信息取得
	 * @return BEAN类  咨询信息
	 */
	//	/**
	//	 * <p>
	//	 * 编辑。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>编辑。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String edit() {
	//		log.info("C301Action edit.........");
	//		String id=request.getParameter("id");
	//		if(id!=null){
	//			TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//			bean1.setId(id);
	//			bean=consultMsgManager.findDataById(bean1);
	//		}else{
	//			SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
	//			request.setAttribute("date", sdf.format(new Date()));
	//		}
	//		return "edit";
	//	}
	//	/**
	//	 * <p>
	//	 * 信息保存
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>新增。</div>
	//	 * <div>修改。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String save() {
	//		log.info("C301Action edit.........");
	//		if(bean!=null){
	//			String msg="1";
	//			try {
	//					BaseUserBean user = (BaseUserBean) request.getSession().getAttribute(CommonConstant.SESSION_KEY_USER_CUSTOMER_SERVICE);
	//					if(user!=null){
	//						bean.setCreate_ip(getIpAddr());
	//						bean.setCreate_id(user.getUser_id());
	//						bean.setUpdate_ip(getIpAddr());
	//						bean.setUpdate_id(user.getUser_id());
	//					}
	//					msg=consultMsgManager.saveOrUpdateData(bean);
	//			} catch (Exception e) {
	//				msg=e.getMessage();
	//			}
	//			request.setAttribute("msg",msg);
	//		}else{
	//			request.setAttribute("msg", "信息保存失败!");
	//		}
	//		return SUCCESS;
	//	}
	//	/**
	//	 * <p>
	//	 * 删除。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>删除。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String del() {
	//		log.info("C301Action del.........");
	//		
	//		String id=request.getParameter("id");
	//		TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//		bean1.setId(id);
	//		String msg="1";
	//		try {
	//			msg=consultMsgManager.deleteDataById(bean1);
	//		} catch (Exception e) {
	//			msg=e.getMessage();
	//		}
	//		request.setAttribute("msg",msg);
	//		return SUCCESS;
	//	}
	
	
	
	/**
	 * BEAN类  咨询信息设定
	 * @param bean BEAN类  咨询信息
	 */
	//	/**
	//	 * <p>
	//	 * 编辑。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>编辑。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String edit() {
	//		log.info("C301Action edit.........");
	//		String id=request.getParameter("id");
	//		if(id!=null){
	//			TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//			bean1.setId(id);
	//			bean=consultMsgManager.findDataById(bean1);
	//		}else{
	//			SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
	//			request.setAttribute("date", sdf.format(new Date()));
	//		}
	//		return "edit";
	//	}
	//	/**
	//	 * <p>
	//	 * 信息保存
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>新增。</div>
	//	 * <div>修改。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String save() {
	//		log.info("C301Action edit.........");
	//		if(bean!=null){
	//			String msg="1";
	//			try {
	//					BaseUserBean user = (BaseUserBean) request.getSession().getAttribute(CommonConstant.SESSION_KEY_USER_CUSTOMER_SERVICE);
	//					if(user!=null){
	//						bean.setCreate_ip(getIpAddr());
	//						bean.setCreate_id(user.getUser_id());
	//						bean.setUpdate_ip(getIpAddr());
	//						bean.setUpdate_id(user.getUser_id());
	//					}
	//					msg=consultMsgManager.saveOrUpdateData(bean);
	//			} catch (Exception e) {
	//				msg=e.getMessage();
	//			}
	//			request.setAttribute("msg",msg);
	//		}else{
	//			request.setAttribute("msg", "信息保存失败!");
	//		}
	//		return SUCCESS;
	//	}
	//	/**
	//	 * <p>
	//	 * 删除。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>删除。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String del() {
	//		log.info("C301Action del.........");
	//		
	//		String id=request.getParameter("id");
	//		TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//		bean1.setId(id);
	//		String msg="1";
	//		try {
	//			msg=consultMsgManager.deleteDataById(bean1);
	//		} catch (Exception e) {
	//			msg=e.getMessage();
	//		}
	//		request.setAttribute("msg",msg);
	//		return SUCCESS;
	//	}
	
	
	
	/**
	 * BEAN类  咨询信息取得
	 * @return BEAN类  咨询信息
	 */
	//	/**
	//	 * <p>
	//	 * 编辑。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>编辑。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String edit() {
	//		log.info("C301Action edit.........");
	//		String id=request.getParameter("id");
	//		if(id!=null){
	//			TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//			bean1.setId(id);
	//			bean=consultMsgManager.findDataById(bean1);
	//		}else{
	//			SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
	//			request.setAttribute("date", sdf.format(new Date()));
	//		}
	//		return "edit";
	//	}
	//	/**
	//	 * <p>
	//	 * 信息保存
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>新增。</div>
	//	 * <div>修改。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String save() {
	//		log.info("C301Action edit.........");
	//		if(bean!=null){
	//			String msg="1";
	//			try {
	//					BaseUserBean user = (BaseUserBean) request.getSession().getAttribute(CommonConstant.SESSION_KEY_USER_CUSTOMER_SERVICE);
	//					if(user!=null){
	//						bean.setCreate_ip(getIpAddr());
	//						bean.setCreate_id(user.getUser_id());
	//						bean.setUpdate_ip(getIpAddr());
	//						bean.setUpdate_id(user.getUser_id());
	//					}
	//					msg=consultMsgManager.saveOrUpdateData(bean);
	//			} catch (Exception e) {
	//				msg=e.getMessage();
	//			}
	//			request.setAttribute("msg",msg);
	//		}else{
	//			request.setAttribute("msg", "信息保存失败!");
	//		}
	//		return SUCCESS;
	//	}
	//	/**
	//	 * <p>
	//	 * 删除。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>删除。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String del() {
	//		log.info("C301Action del.........");
	//		
	//		String id=request.getParameter("id");
	//		TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//		bean1.setId(id);
	//		String msg="1";
	//		try {
	//			msg=consultMsgManager.deleteDataById(bean1);
	//		} catch (Exception e) {
	//			msg=e.getMessage();
	//		}
	//		request.setAttribute("msg",msg);
	//		return SUCCESS;
	//	}
	
	
	
	/**
	 * BEAN类  咨询信息设定
	 * @param bean BEAN类  咨询信息
	 */
	//	/**
	//	 * <p>
	//	 * 编辑。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>编辑。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String edit() {
	//		log.info("C301Action edit.........");
	//		String id=request.getParameter("id");
	//		if(id!=null){
	//			TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//			bean1.setId(id);
	//			bean=consultMsgManager.findDataById(bean1);
	//		}else{
	//			SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
	//			request.setAttribute("date", sdf.format(new Date()));
	//		}
	//		return "edit";
	//	}
	//	/**
	//	 * <p>
	//	 * 信息保存
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>新增。</div>
	//	 * <div>修改。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String save() {
	//		log.info("C301Action edit.........");
	//		if(bean!=null){
	//			String msg="1";
	//			try {
	//					BaseUserBean user = (BaseUserBean) request.getSession().getAttribute(CommonConstant.SESSION_KEY_USER_CUSTOMER_SERVICE);
	//					if(user!=null){
	//						bean.setCreate_ip(getIpAddr());
	//						bean.setCreate_id(user.getUser_id());
	//						bean.setUpdate_ip(getIpAddr());
	//						bean.setUpdate_id(user.getUser_id());
	//					}
	//					msg=consultMsgManager.saveOrUpdateData(bean);
	//			} catch (Exception e) {
	//				msg=e.getMessage();
	//			}
	//			request.setAttribute("msg",msg);
	//		}else{
	//			request.setAttribute("msg", "信息保存失败!");
	//		}
	//		return SUCCESS;
	//	}
	//	/**
	//	 * <p>
	//	 * 删除。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>删除。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String del() {
	//		log.info("C301Action del.........");
	//		
	//		String id=request.getParameter("id");
	//		TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//		bean1.setId(id);
	//		String msg="1";
	//		try {
	//			msg=consultMsgManager.deleteDataById(bean1);
	//		} catch (Exception e) {
	//			msg=e.getMessage();
	//		}
	//		request.setAttribute("msg",msg);
	//		return SUCCESS;
	//	}
	
	
	
	/**
	 * BEAN类  咨询信息取得
	 * @return BEAN类  咨询信息
	 */
	//	/**
	//	 * <p>
	//	 * 编辑。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>编辑。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String edit() {
	//		log.info("C301Action edit.........");
	//		String id=request.getParameter("id");
	//		if(id!=null){
	//			TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//			bean1.setId(id);
	//			bean=consultMsgManager.findDataById(bean1);
	//		}else{
	//			SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
	//			request.setAttribute("date", sdf.format(new Date()));
	//		}
	//		return "edit";
	//	}
	//	/**
	//	 * <p>
	//	 * 信息保存
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>新增。</div>
	//	 * <div>修改。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String save() {
	//		log.info("C301Action edit.........");
	//		if(bean!=null){
	//			String msg="1";
	//			try {
	//					BaseUserBean user = (BaseUserBean) request.getSession().getAttribute(CommonConstant.SESSION_KEY_USER_CUSTOMER_SERVICE);
	//					if(user!=null){
	//						bean.setCreate_ip(getIpAddr());
	//						bean.setCreate_id(user.getUser_id());
	//						bean.setUpdate_ip(getIpAddr());
	//						bean.setUpdate_id(user.getUser_id());
	//					}
	//					msg=consultMsgManager.saveOrUpdateData(bean);
	//			} catch (Exception e) {
	//				msg=e.getMessage();
	//			}
	//			request.setAttribute("msg",msg);
	//		}else{
	//			request.setAttribute("msg", "信息保存失败!");
	//		}
	//		return SUCCESS;
	//	}
	//	/**
	//	 * <p>
	//	 * 删除。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>删除。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String del() {
	//		log.info("C301Action del.........");
	//		
	//		String id=request.getParameter("id");
	//		TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//		bean1.setId(id);
	//		String msg="1";
	//		try {
	//			msg=consultMsgManager.deleteDataById(bean1);
	//		} catch (Exception e) {
	//			msg=e.getMessage();
	//		}
	//		request.setAttribute("msg",msg);
	//		return SUCCESS;
	//	}
	
	
	
	/**
	 * BEAN类  咨询信息取得
	 * @return BEAN类  咨询信息
	 */
	//	/**
	//	 * <p>
	//	 * 编辑。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>编辑。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String edit() {
	//		log.info("C301Action edit.........");
	//		String id=request.getParameter("id");
	//		if(id!=null){
	//			TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//			bean1.setId(id);
	//			bean=consultMsgManager.findDataById(bean1);
	//		}else{
	//			SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
	//			request.setAttribute("date", sdf.format(new Date()));
	//		}
	//		return "edit";
	//	}
	//	/**
	//	 * <p>
	//	 * 信息保存
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>新增。</div>
	//	 * <div>修改。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String save() {
	//		log.info("C301Action edit.........");
	//		if(bean!=null){
	//			String msg="1";
	//			try {
	//					BaseUserBean user = (BaseUserBean) request.getSession().getAttribute(CommonConstant.SESSION_KEY_USER_CUSTOMER_SERVICE);
	//					if(user!=null){
	//						bean.setCreate_ip(getIpAddr());
	//						bean.setCreate_id(user.getUser_id());
	//						bean.setUpdate_ip(getIpAddr());
	//						bean.setUpdate_id(user.getUser_id());
	//					}
	//					msg=consultMsgManager.saveOrUpdateData(bean);
	//			} catch (Exception e) {
	//				msg=e.getMessage();
	//			}
	//			request.setAttribute("msg",msg);
	//		}else{
	//			request.setAttribute("msg", "信息保存失败!");
	//		}
	//		return SUCCESS;
	//	}
	//	/**
	//	 * <p>
	//	 * 删除。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>删除。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String del() {
	//		log.info("C301Action del.........");
	//		
	//		String id=request.getParameter("id");
	//		TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//		bean1.setId(id);
	//		String msg="1";
	//		try {
	//			msg=consultMsgManager.deleteDataById(bean1);
	//		} catch (Exception e) {
	//			msg=e.getMessage();
	//		}
	//		request.setAttribute("msg",msg);
	//		return SUCCESS;
	//	}
	
	
	
	/**
	 * BEAN类  咨询信息设定
	 * @param bean BEAN类  咨询信息
	 */
	//	/**
	//	 * <p>
	//	 * 编辑。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>编辑。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String edit() {
	//		log.info("C301Action edit.........");
	//		String id=request.getParameter("id");
	//		if(id!=null){
	//			TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//			bean1.setId(id);
	//			bean=consultMsgManager.findDataById(bean1);
	//		}else{
	//			SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
	//			request.setAttribute("date", sdf.format(new Date()));
	//		}
	//		return "edit";
	//	}
	//	/**
	//	 * <p>
	//	 * 信息保存
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>新增。</div>
	//	 * <div>修改。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String save() {
	//		log.info("C301Action edit.........");
	//		if(bean!=null){
	//			String msg="1";
	//			try {
	//					BaseUserBean user = (BaseUserBean) request.getSession().getAttribute(CommonConstant.SESSION_KEY_USER_CUSTOMER_SERVICE);
	//					if(user!=null){
	//						bean.setCreate_ip(getIpAddr());
	//						bean.setCreate_id(user.getUser_id());
	//						bean.setUpdate_ip(getIpAddr());
	//						bean.setUpdate_id(user.getUser_id());
	//					}
	//					msg=consultMsgManager.saveOrUpdateData(bean);
	//			} catch (Exception e) {
	//				msg=e.getMessage();
	//			}
	//			request.setAttribute("msg",msg);
	//		}else{
	//			request.setAttribute("msg", "信息保存失败!");
	//		}
	//		return SUCCESS;
	//	}
	//	/**
	//	 * <p>
	//	 * 删除。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>删除。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String del() {
	//		log.info("C301Action del.........");
	//		
	//		String id=request.getParameter("id");
	//		TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//		bean1.setId(id);
	//		String msg="1";
	//		try {
	//			msg=consultMsgManager.deleteDataById(bean1);
	//		} catch (Exception e) {
	//			msg=e.getMessage();
	//		}
	//		request.setAttribute("msg",msg);
	//		return SUCCESS;
	//	}
	
	
	
	/**
	 * BEAN类  咨询信息取得
	 * @return BEAN类  咨询信息
	 */
	//	/**
	//	 * <p>
	//	 * 编辑。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>编辑。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String edit() {
	//		log.info("C301Action edit.........");
	//		String id=request.getParameter("id");
	//		if(id!=null){
	//			TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//			bean1.setId(id);
	//			bean=consultMsgManager.findDataById(bean1);
	//		}else{
	//			SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
	//			request.setAttribute("date", sdf.format(new Date()));
	//		}
	//		return "edit";
	//	}
	//	/**
	//	 * <p>
	//	 * 信息保存
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>新增。</div>
	//	 * <div>修改。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String save() {
	//		log.info("C301Action edit.........");
	//		if(bean!=null){
	//			String msg="1";
	//			try {
	//					BaseUserBean user = (BaseUserBean) request.getSession().getAttribute(CommonConstant.SESSION_KEY_USER_CUSTOMER_SERVICE);
	//					if(user!=null){
	//						bean.setCreate_ip(getIpAddr());
	//						bean.setCreate_id(user.getUser_id());
	//						bean.setUpdate_ip(getIpAddr());
	//						bean.setUpdate_id(user.getUser_id());
	//					}
	//					msg=consultMsgManager.saveOrUpdateData(bean);
	//			} catch (Exception e) {
	//				msg=e.getMessage();
	//			}
	//			request.setAttribute("msg",msg);
	//		}else{
	//			request.setAttribute("msg", "信息保存失败!");
	//		}
	//		return SUCCESS;
	//	}
	//	/**
	//	 * <p>
	//	 * 删除。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>删除。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String del() {
	//		log.info("C301Action del.........");
	//		
	//		String id=request.getParameter("id");
	//		TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//		bean1.setId(id);
	//		String msg="1";
	//		try {
	//			msg=consultMsgManager.deleteDataById(bean1);
	//		} catch (Exception e) {
	//			msg=e.getMessage();
	//		}
	//		request.setAttribute("msg",msg);
	//		return SUCCESS;
	//	}
	
	
	
	/**
	 * BEAN类  咨询信息设定
	 * @param bean BEAN类  咨询信息
	 */
	//	/**
	//	 * <p>
	//	 * 编辑。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>编辑。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String edit() {
	//		log.info("C301Action edit.........");
	//		String id=request.getParameter("id");
	//		if(id!=null){
	//			TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//			bean1.setId(id);
	//			bean=consultMsgManager.findDataById(bean1);
	//		}else{
	//			SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
	//			request.setAttribute("date", sdf.format(new Date()));
	//		}
	//		return "edit";
	//	}
	//	/**
	//	 * <p>
	//	 * 信息保存
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>新增。</div>
	//	 * <div>修改。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String save() {
	//		log.info("C301Action edit.........");
	//		if(bean!=null){
	//			String msg="1";
	//			try {
	//					BaseUserBean user = (BaseUserBean) request.getSession().getAttribute(CommonConstant.SESSION_KEY_USER_CUSTOMER_SERVICE);
	//					if(user!=null){
	//						bean.setCreate_ip(getIpAddr());
	//						bean.setCreate_id(user.getUser_id());
	//						bean.setUpdate_ip(getIpAddr());
	//						bean.setUpdate_id(user.getUser_id());
	//					}
	//					msg=consultMsgManager.saveOrUpdateData(bean);
	//			} catch (Exception e) {
	//				msg=e.getMessage();
	//			}
	//			request.setAttribute("msg",msg);
	//		}else{
	//			request.setAttribute("msg", "信息保存失败!");
	//		}
	//		return SUCCESS;
	//	}
	//	/**
	//	 * <p>
	//	 * 删除。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>删除。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String del() {
	//		log.info("C301Action del.........");
	//		
	//		String id=request.getParameter("id");
	//		TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//		bean1.setId(id);
	//		String msg="1";
	//		try {
	//			msg=consultMsgManager.deleteDataById(bean1);
	//		} catch (Exception e) {
	//			msg=e.getMessage();
	//		}
	//		request.setAttribute("msg",msg);
	//		return SUCCESS;
	//	}
	
	
	
	/**
	 * BEAN类  咨询信息取得
	 * @return BEAN类  咨询信息
	 */
	//	/**
	//	 * <p>
	//	 * 编辑。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>编辑。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String edit() {
	//		log.info("C301Action edit.........");
	//		String id=request.getParameter("id");
	//		if(id!=null){
	//			TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//			bean1.setId(id);
	//			bean=consultMsgManager.findDataById(bean1);
	//		}else{
	//			SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
	//			request.setAttribute("date", sdf.format(new Date()));
	//		}
	//		return "edit";
	//	}
	//	/**
	//	 * <p>
	//	 * 信息保存
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>新增。</div>
	//	 * <div>修改。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String save() {
	//		log.info("C301Action edit.........");
	//		if(bean!=null){
	//			String msg="1";
	//			try {
	//					BaseUserBean user = (BaseUserBean) request.getSession().getAttribute(CommonConstant.SESSION_KEY_USER_CUSTOMER_SERVICE);
	//					if(user!=null){
	//						bean.setCreate_ip(getIpAddr());
	//						bean.setCreate_id(user.getUser_id());
	//						bean.setUpdate_ip(getIpAddr());
	//						bean.setUpdate_id(user.getUser_id());
	//					}
	//					msg=consultMsgManager.saveOrUpdateData(bean);
	//			} catch (Exception e) {
	//				msg=e.getMessage();
	//			}
	//			request.setAttribute("msg",msg);
	//		}else{
	//			request.setAttribute("msg", "信息保存失败!");
	//		}
	//		return SUCCESS;
	//	}
	//	/**
	//	 * <p>
	//	 * 删除。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>删除。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String del() {
	//		log.info("C301Action del.........");
	//		
	//		String id=request.getParameter("id");
	//		TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//		bean1.setId(id);
	//		String msg="1";
	//		try {
	//			msg=consultMsgManager.deleteDataById(bean1);
	//		} catch (Exception e) {
	//			msg=e.getMessage();
	//		}
	//		request.setAttribute("msg",msg);
	//		return SUCCESS;
	//	}
	
	
	
	/**
	 * BEAN类  咨询信息取得
	 * @return BEAN类  咨询信息
	 */
	//	/**
	//	 * <p>
	//	 * 编辑。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>编辑。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String edit() {
	//		log.info("C301Action edit.........");
	//		String id=request.getParameter("id");
	//		if(id!=null){
	//			TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//			bean1.setId(id);
	//			bean=consultMsgManager.findDataById(bean1);
	//		}else{
	//			SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
	//			request.setAttribute("date", sdf.format(new Date()));
	//		}
	//		return "edit";
	//	}
	//	/**
	//	 * <p>
	//	 * 信息保存
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>新增。</div>
	//	 * <div>修改。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String save() {
	//		log.info("C301Action edit.........");
	//		if(bean!=null){
	//			String msg="1";
	//			try {
	//					BaseUserBean user = (BaseUserBean) request.getSession().getAttribute(CommonConstant.SESSION_KEY_USER_CUSTOMER_SERVICE);
	//					if(user!=null){
	//						bean.setCreate_ip(getIpAddr());
	//						bean.setCreate_id(user.getUser_id());
	//						bean.setUpdate_ip(getIpAddr());
	//						bean.setUpdate_id(user.getUser_id());
	//					}
	//					msg=consultMsgManager.saveOrUpdateData(bean);
	//			} catch (Exception e) {
	//				msg=e.getMessage();
	//			}
	//			request.setAttribute("msg",msg);
	//		}else{
	//			request.setAttribute("msg", "信息保存失败!");
	//		}
	//		return SUCCESS;
	//	}
	//	/**
	//	 * <p>
	//	 * 删除。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>删除。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String del() {
	//		log.info("C301Action del.........");
	//		
	//		String id=request.getParameter("id");
	//		TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//		bean1.setId(id);
	//		String msg="1";
	//		try {
	//			msg=consultMsgManager.deleteDataById(bean1);
	//		} catch (Exception e) {
	//			msg=e.getMessage();
	//		}
	//		request.setAttribute("msg",msg);
	//		return SUCCESS;
	//	}
	
	
	
	/**
	 * BEAN类  咨询信息设定
	 * @param bean BEAN类  咨询信息
	 */
	//	/**
	//	 * <p>
	//	 * 编辑。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>编辑。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String edit() {
	//		log.info("C301Action edit.........");
	//		String id=request.getParameter("id");
	//		if(id!=null){
	//			TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//			bean1.setId(id);
	//			bean=consultMsgManager.findDataById(bean1);
	//		}else{
	//			SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
	//			request.setAttribute("date", sdf.format(new Date()));
	//		}
	//		return "edit";
	//	}
	//	/**
	//	 * <p>
	//	 * 信息保存
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>新增。</div>
	//	 * <div>修改。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String save() {
	//		log.info("C301Action edit.........");
	//		if(bean!=null){
	//			String msg="1";
	//			try {
	//					BaseUserBean user = (BaseUserBean) request.getSession().getAttribute(CommonConstant.SESSION_KEY_USER_CUSTOMER_SERVICE);
	//					if(user!=null){
	//						bean.setCreate_ip(getIpAddr());
	//						bean.setCreate_id(user.getUser_id());
	//						bean.setUpdate_ip(getIpAddr());
	//						bean.setUpdate_id(user.getUser_id());
	//					}
	//					msg=consultMsgManager.saveOrUpdateData(bean);
	//			} catch (Exception e) {
	//				msg=e.getMessage();
	//			}
	//			request.setAttribute("msg",msg);
	//		}else{
	//			request.setAttribute("msg", "信息保存失败!");
	//		}
	//		return SUCCESS;
	//	}
	//	/**
	//	 * <p>
	//	 * 删除。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>删除。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String del() {
	//		log.info("C301Action del.........");
	//		
	//		String id=request.getParameter("id");
	//		TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//		bean1.setId(id);
	//		String msg="1";
	//		try {
	//			msg=consultMsgManager.deleteDataById(bean1);
	//		} catch (Exception e) {
	//			msg=e.getMessage();
	//		}
	//		request.setAttribute("msg",msg);
	//		return SUCCESS;
	//	}
	
	
	
	/**
	 * BEAN类  咨询信息取得
	 * @return BEAN类  咨询信息
	 */
	//	/**
	//	 * <p>
	//	 * 编辑。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>编辑。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String edit() {
	//		log.info("C301Action edit.........");
	//		String id=request.getParameter("id");
	//		if(id!=null){
	//			TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//			bean1.setId(id);
	//			bean=consultMsgManager.findDataById(bean1);
	//		}else{
	//			SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
	//			request.setAttribute("date", sdf.format(new Date()));
	//		}
	//		return "edit";
	//	}
	//	/**
	//	 * <p>
	//	 * 信息保存
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>新增。</div>
	//	 * <div>修改。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String save() {
	//		log.info("C301Action edit.........");
	//		if(bean!=null){
	//			String msg="1";
	//			try {
	//					BaseUserBean user = (BaseUserBean) request.getSession().getAttribute(CommonConstant.SESSION_KEY_USER_CUSTOMER_SERVICE);
	//					if(user!=null){
	//						bean.setCreate_ip(getIpAddr());
	//						bean.setCreate_id(user.getUser_id());
	//						bean.setUpdate_ip(getIpAddr());
	//						bean.setUpdate_id(user.getUser_id());
	//					}
	//					msg=consultMsgManager.saveOrUpdateData(bean);
	//			} catch (Exception e) {
	//				msg=e.getMessage();
	//			}
	//			request.setAttribute("msg",msg);
	//		}else{
	//			request.setAttribute("msg", "信息保存失败!");
	//		}
	//		return SUCCESS;
	//	}
	//	/**
	//	 * <p>
	//	 * 删除。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>删除。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String del() {
	//		log.info("C301Action del.........");
	//		
	//		String id=request.getParameter("id");
	//		TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//		bean1.setId(id);
	//		String msg="1";
	//		try {
	//			msg=consultMsgManager.deleteDataById(bean1);
	//		} catch (Exception e) {
	//			msg=e.getMessage();
	//		}
	//		request.setAttribute("msg",msg);
	//		return SUCCESS;
	//	}
	
	
	
	/**
	 * BEAN类  咨询信息取得
	 * @return BEAN类  咨询信息
	 */
	//	/**
	//	 * <p>
	//	 * 编辑。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>编辑。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String edit() {
	//		log.info("C301Action edit.........");
	//		String id=request.getParameter("id");
	//		if(id!=null){
	//			TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//			bean1.setId(id);
	//			bean=consultMsgManager.findDataById(bean1);
	//		}else{
	//			SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
	//			request.setAttribute("date", sdf.format(new Date()));
	//		}
	//		return "edit";
	//	}
	//	/**
	//	 * <p>
	//	 * 信息保存
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>新增。</div>
	//	 * <div>修改。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String save() {
	//		log.info("C301Action edit.........");
	//		if(bean!=null){
	//			String msg="1";
	//			try {
	//					BaseUserBean user = (BaseUserBean) request.getSession().getAttribute(CommonConstant.SESSION_KEY_USER_CUSTOMER_SERVICE);
	//					if(user!=null){
	//						bean.setCreate_ip(getIpAddr());
	//						bean.setCreate_id(user.getUser_id());
	//						bean.setUpdate_ip(getIpAddr());
	//						bean.setUpdate_id(user.getUser_id());
	//					}
	//					msg=consultMsgManager.saveOrUpdateData(bean);
	//			} catch (Exception e) {
	//				msg=e.getMessage();
	//			}
	//			request.setAttribute("msg",msg);
	//		}else{
	//			request.setAttribute("msg", "信息保存失败!");
	//		}
	//		return SUCCESS;
	//	}
	//	/**
	//	 * <p>
	//	 * 删除。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>删除。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String del() {
	//		log.info("C301Action del.........");
	//		
	//		String id=request.getParameter("id");
	//		TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//		bean1.setId(id);
	//		String msg="1";
	//		try {
	//			msg=consultMsgManager.deleteDataById(bean1);
	//		} catch (Exception e) {
	//			msg=e.getMessage();
	//		}
	//		request.setAttribute("msg",msg);
	//		return SUCCESS;
	//	}
	
	
	
	/**
	 * BEAN类  咨询信息设定
	 * @param bean BEAN类  咨询信息
	 */
	//	/**
	//	 * <p>
	//	 * 编辑。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>编辑。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String edit() {
	//		log.info("C301Action edit.........");
	//		String id=request.getParameter("id");
	//		if(id!=null){
	//			TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//			bean1.setId(id);
	//			bean=consultMsgManager.findDataById(bean1);
	//		}else{
	//			SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
	//			request.setAttribute("date", sdf.format(new Date()));
	//		}
	//		return "edit";
	//	}
	//	/**
	//	 * <p>
	//	 * 信息保存
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>新增。</div>
	//	 * <div>修改。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String save() {
	//		log.info("C301Action edit.........");
	//		if(bean!=null){
	//			String msg="1";
	//			try {
	//					BaseUserBean user = (BaseUserBean) request.getSession().getAttribute(CommonConstant.SESSION_KEY_USER_CUSTOMER_SERVICE);
	//					if(user!=null){
	//						bean.setCreate_ip(getIpAddr());
	//						bean.setCreate_id(user.getUser_id());
	//						bean.setUpdate_ip(getIpAddr());
	//						bean.setUpdate_id(user.getUser_id());
	//					}
	//					msg=consultMsgManager.saveOrUpdateData(bean);
	//			} catch (Exception e) {
	//				msg=e.getMessage();
	//			}
	//			request.setAttribute("msg",msg);
	//		}else{
	//			request.setAttribute("msg", "信息保存失败!");
	//		}
	//		return SUCCESS;
	//	}
	//	/**
	//	 * <p>
	//	 * 删除。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>删除。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String del() {
	//		log.info("C301Action del.........");
	//		
	//		String id=request.getParameter("id");
	//		TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//		bean1.setId(id);
	//		String msg="1";
	//		try {
	//			msg=consultMsgManager.deleteDataById(bean1);
	//		} catch (Exception e) {
	//			msg=e.getMessage();
	//		}
	//		request.setAttribute("msg",msg);
	//		return SUCCESS;
	//	}
	
	
	
	/**
	 * BEAN类  咨询信息取得
	 * @return BEAN类  咨询信息
	 */
	//	/**
	//	 * <p>
	//	 * 编辑。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>编辑。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String edit() {
	//		log.info("C301Action edit.........");
	//		String id=request.getParameter("id");
	//		if(id!=null){
	//			TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//			bean1.setId(id);
	//			bean=consultMsgManager.findDataById(bean1);
	//		}else{
	//			SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
	//			request.setAttribute("date", sdf.format(new Date()));
	//		}
	//		return "edit";
	//	}
	//	/**
	//	 * <p>
	//	 * 信息保存
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>新增。</div>
	//	 * <div>修改。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String save() {
	//		log.info("C301Action edit.........");
	//		if(bean!=null){
	//			String msg="1";
	//			try {
	//					BaseUserBean user = (BaseUserBean) request.getSession().getAttribute(CommonConstant.SESSION_KEY_USER_CUSTOMER_SERVICE);
	//					if(user!=null){
	//						bean.setCreate_ip(getIpAddr());
	//						bean.setCreate_id(user.getUser_id());
	//						bean.setUpdate_ip(getIpAddr());
	//						bean.setUpdate_id(user.getUser_id());
	//					}
	//					msg=consultMsgManager.saveOrUpdateData(bean);
	//			} catch (Exception e) {
	//				msg=e.getMessage();
	//			}
	//			request.setAttribute("msg",msg);
	//		}else{
	//			request.setAttribute("msg", "信息保存失败!");
	//		}
	//		return SUCCESS;
	//	}
	//	/**
	//	 * <p>
	//	 * 删除。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>删除。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String del() {
	//		log.info("C301Action del.........");
	//		
	//		String id=request.getParameter("id");
	//		TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//		bean1.setId(id);
	//		String msg="1";
	//		try {
	//			msg=consultMsgManager.deleteDataById(bean1);
	//		} catch (Exception e) {
	//			msg=e.getMessage();
	//		}
	//		request.setAttribute("msg",msg);
	//		return SUCCESS;
	//	}
	
	
	
	/**
	 * BEAN类  咨询信息设定
	 * @param bean BEAN类  咨询信息
	 */
	//	/**
	//	 * <p>
	//	 * 编辑。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>编辑。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String edit() {
	//		log.info("C301Action edit.........");
	//		String id=request.getParameter("id");
	//		if(id!=null){
	//			TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//			bean1.setId(id);
	//			bean=consultMsgManager.findDataById(bean1);
	//		}else{
	//			SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
	//			request.setAttribute("date", sdf.format(new Date()));
	//		}
	//		return "edit";
	//	}
	//	/**
	//	 * <p>
	//	 * 信息保存
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>新增。</div>
	//	 * <div>修改。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String save() {
	//		log.info("C301Action edit.........");
	//		if(bean!=null){
	//			String msg="1";
	//			try {
	//					BaseUserBean user = (BaseUserBean) request.getSession().getAttribute(CommonConstant.SESSION_KEY_USER_CUSTOMER_SERVICE);
	//					if(user!=null){
	//						bean.setCreate_ip(getIpAddr());
	//						bean.setCreate_id(user.getUser_id());
	//						bean.setUpdate_ip(getIpAddr());
	//						bean.setUpdate_id(user.getUser_id());
	//					}
	//					msg=consultMsgManager.saveOrUpdateData(bean);
	//			} catch (Exception e) {
	//				msg=e.getMessage();
	//			}
	//			request.setAttribute("msg",msg);
	//		}else{
	//			request.setAttribute("msg", "信息保存失败!");
	//		}
	//		return SUCCESS;
	//	}
	//	/**
	//	 * <p>
	//	 * 删除。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>删除。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String del() {
	//		log.info("C301Action del.........");
	//		
	//		String id=request.getParameter("id");
	//		TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//		bean1.setId(id);
	//		String msg="1";
	//		try {
	//			msg=consultMsgManager.deleteDataById(bean1);
	//		} catch (Exception e) {
	//			msg=e.getMessage();
	//		}
	//		request.setAttribute("msg",msg);
	//		return SUCCESS;
	//	}
	
	
	
	/**
	 * BEAN类  咨询信息取得
	 * @return BEAN类  咨询信息
	 */
	//	/**
	//	 * <p>
	//	 * 编辑。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>编辑。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String edit() {
	//		log.info("C301Action edit.........");
	//		String id=request.getParameter("id");
	//		if(id!=null){
	//			TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//			bean1.setId(id);
	//			bean=consultMsgManager.findDataById(bean1);
	//		}else{
	//			SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
	//			request.setAttribute("date", sdf.format(new Date()));
	//		}
	//		return "edit";
	//	}
	//	/**
	//	 * <p>
	//	 * 信息保存
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>新增。</div>
	//	 * <div>修改。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String save() {
	//		log.info("C301Action edit.........");
	//		if(bean!=null){
	//			String msg="1";
	//			try {
	//					BaseUserBean user = (BaseUserBean) request.getSession().getAttribute(CommonConstant.SESSION_KEY_USER_CUSTOMER_SERVICE);
	//					if(user!=null){
	//						bean.setCreate_ip(getIpAddr());
	//						bean.setCreate_id(user.getUser_id());
	//						bean.setUpdate_ip(getIpAddr());
	//						bean.setUpdate_id(user.getUser_id());
	//					}
	//					msg=consultMsgManager.saveOrUpdateData(bean);
	//			} catch (Exception e) {
	//				msg=e.getMessage();
	//			}
	//			request.setAttribute("msg",msg);
	//		}else{
	//			request.setAttribute("msg", "信息保存失败!");
	//		}
	//		return SUCCESS;
	//	}
	//	/**
	//	 * <p>
	//	 * 删除。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>删除。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String del() {
	//		log.info("C301Action del.........");
	//		
	//		String id=request.getParameter("id");
	//		TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//		bean1.setId(id);
	//		String msg="1";
	//		try {
	//			msg=consultMsgManager.deleteDataById(bean1);
	//		} catch (Exception e) {
	//			msg=e.getMessage();
	//		}
	//		request.setAttribute("msg",msg);
	//		return SUCCESS;
	//	}
	
	
	
	/**
	 * BEAN类  咨询信息取得
	 * @return BEAN类  咨询信息
	 */
	//	/**
	//	 * <p>
	//	 * 编辑。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>编辑。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String edit() {
	//		log.info("C301Action edit.........");
	//		String id=request.getParameter("id");
	//		if(id!=null){
	//			TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//			bean1.setId(id);
	//			bean=consultMsgManager.findDataById(bean1);
	//		}else{
	//			SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
	//			request.setAttribute("date", sdf.format(new Date()));
	//		}
	//		return "edit";
	//	}
	//	/**
	//	 * <p>
	//	 * 信息保存
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>新增。</div>
	//	 * <div>修改。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String save() {
	//		log.info("C301Action edit.........");
	//		if(bean!=null){
	//			String msg="1";
	//			try {
	//					BaseUserBean user = (BaseUserBean) request.getSession().getAttribute(CommonConstant.SESSION_KEY_USER_CUSTOMER_SERVICE);
	//					if(user!=null){
	//						bean.setCreate_ip(getIpAddr());
	//						bean.setCreate_id(user.getUser_id());
	//						bean.setUpdate_ip(getIpAddr());
	//						bean.setUpdate_id(user.getUser_id());
	//					}
	//					msg=consultMsgManager.saveOrUpdateData(bean);
	//			} catch (Exception e) {
	//				msg=e.getMessage();
	//			}
	//			request.setAttribute("msg",msg);
	//		}else{
	//			request.setAttribute("msg", "信息保存失败!");
	//		}
	//		return SUCCESS;
	//	}
	//	/**
	//	 * <p>
	//	 * 删除。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>删除。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String del() {
	//		log.info("C301Action del.........");
	//		
	//		String id=request.getParameter("id");
	//		TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//		bean1.setId(id);
	//		String msg="1";
	//		try {
	//			msg=consultMsgManager.deleteDataById(bean1);
	//		} catch (Exception e) {
	//			msg=e.getMessage();
	//		}
	//		request.setAttribute("msg",msg);
	//		return SUCCESS;
	//	}
	
	
	
	/**
	 * BEAN类  咨询信息设定
	 * @param bean BEAN类  咨询信息
	 */
	//	/**
	//	 * <p>
	//	 * 编辑。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>编辑。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String edit() {
	//		log.info("C301Action edit.........");
	//		String id=request.getParameter("id");
	//		if(id!=null){
	//			TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//			bean1.setId(id);
	//			bean=consultMsgManager.findDataById(bean1);
	//		}else{
	//			SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
	//			request.setAttribute("date", sdf.format(new Date()));
	//		}
	//		return "edit";
	//	}
	//	/**
	//	 * <p>
	//	 * 信息保存
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>新增。</div>
	//	 * <div>修改。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String save() {
	//		log.info("C301Action edit.........");
	//		if(bean!=null){
	//			String msg="1";
	//			try {
	//					BaseUserBean user = (BaseUserBean) request.getSession().getAttribute(CommonConstant.SESSION_KEY_USER_CUSTOMER_SERVICE);
	//					if(user!=null){
	//						bean.setCreate_ip(getIpAddr());
	//						bean.setCreate_id(user.getUser_id());
	//						bean.setUpdate_ip(getIpAddr());
	//						bean.setUpdate_id(user.getUser_id());
	//					}
	//					msg=consultMsgManager.saveOrUpdateData(bean);
	//			} catch (Exception e) {
	//				msg=e.getMessage();
	//			}
	//			request.setAttribute("msg",msg);
	//		}else{
	//			request.setAttribute("msg", "信息保存失败!");
	//		}
	//		return SUCCESS;
	//	}
	//	/**
	//	 * <p>
	//	 * 删除。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>删除。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String del() {
	//		log.info("C301Action del.........");
	//		
	//		String id=request.getParameter("id");
	//		TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//		bean1.setId(id);
	//		String msg="1";
	//		try {
	//			msg=consultMsgManager.deleteDataById(bean1);
	//		} catch (Exception e) {
	//			msg=e.getMessage();
	//		}
	//		request.setAttribute("msg",msg);
	//		return SUCCESS;
	//	}
	
	
	
	/**
	 * BEAN类  咨询信息取得
	 * @return BEAN类  咨询信息
	 */
	//	/**
	//	 * <p>
	//	 * 编辑。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>编辑。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String edit() {
	//		log.info("C301Action edit.........");
	//		String id=request.getParameter("id");
	//		if(id!=null){
	//			TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//			bean1.setId(id);
	//			bean=consultMsgManager.findDataById(bean1);
	//		}else{
	//			SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
	//			request.setAttribute("date", sdf.format(new Date()));
	//		}
	//		return "edit";
	//	}
	//	/**
	//	 * <p>
	//	 * 信息保存
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>新增。</div>
	//	 * <div>修改。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String save() {
	//		log.info("C301Action edit.........");
	//		if(bean!=null){
	//			String msg="1";
	//			try {
	//					BaseUserBean user = (BaseUserBean) request.getSession().getAttribute(CommonConstant.SESSION_KEY_USER_CUSTOMER_SERVICE);
	//					if(user!=null){
	//						bean.setCreate_ip(getIpAddr());
	//						bean.setCreate_id(user.getUser_id());
	//						bean.setUpdate_ip(getIpAddr());
	//						bean.setUpdate_id(user.getUser_id());
	//					}
	//					msg=consultMsgManager.saveOrUpdateData(bean);
	//			} catch (Exception e) {
	//				msg=e.getMessage();
	//			}
	//			request.setAttribute("msg",msg);
	//		}else{
	//			request.setAttribute("msg", "信息保存失败!");
	//		}
	//		return SUCCESS;
	//	}
	//	/**
	//	 * <p>
	//	 * 删除。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>删除。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String del() {
	//		log.info("C301Action del.........");
	//		
	//		String id=request.getParameter("id");
	//		TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//		bean1.setId(id);
	//		String msg="1";
	//		try {
	//			msg=consultMsgManager.deleteDataById(bean1);
	//		} catch (Exception e) {
	//			msg=e.getMessage();
	//		}
	//		request.setAttribute("msg",msg);
	//		return SUCCESS;
	//	}
	
	
	
	/**
	 * BEAN类  咨询信息取得
	 * @return BEAN类  咨询信息
	 */
	//	/**
	//	 * <p>
	//	 * 编辑。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>编辑。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String edit() {
	//		log.info("C301Action edit.........");
	//		String id=request.getParameter("id");
	//		if(id!=null){
	//			TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//			bean1.setId(id);
	//			bean=consultMsgManager.findDataById(bean1);
	//		}else{
	//			SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
	//			request.setAttribute("date", sdf.format(new Date()));
	//		}
	//		return "edit";
	//	}
	//	/**
	//	 * <p>
	//	 * 信息保存
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>新增。</div>
	//	 * <div>修改。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String save() {
	//		log.info("C301Action edit.........");
	//		if(bean!=null){
	//			String msg="1";
	//			try {
	//					BaseUserBean user = (BaseUserBean) request.getSession().getAttribute(CommonConstant.SESSION_KEY_USER_CUSTOMER_SERVICE);
	//					if(user!=null){
	//						bean.setCreate_ip(getIpAddr());
	//						bean.setCreate_id(user.getUser_id());
	//						bean.setUpdate_ip(getIpAddr());
	//						bean.setUpdate_id(user.getUser_id());
	//					}
	//					msg=consultMsgManager.saveOrUpdateData(bean);
	//			} catch (Exception e) {
	//				msg=e.getMessage();
	//			}
	//			request.setAttribute("msg",msg);
	//		}else{
	//			request.setAttribute("msg", "信息保存失败!");
	//		}
	//		return SUCCESS;
	//	}
	//	/**
	//	 * <p>
	//	 * 删除。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>删除。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String del() {
	//		log.info("C301Action del.........");
	//		
	//		String id=request.getParameter("id");
	//		TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//		bean1.setId(id);
	//		String msg="1";
	//		try {
	//			msg=consultMsgManager.deleteDataById(bean1);
	//		} catch (Exception e) {
	//			msg=e.getMessage();
	//		}
	//		request.setAttribute("msg",msg);
	//		return SUCCESS;
	//	}
	
	/**
	 * BEAN类  咨询信息取得
	 * @return BEAN类  咨询信息
	 */
	public TcCsConsultMsgBean getBean() {
	    return bean;
	}

	/**
	 * BEAN类  咨询信息设定
	 * @param bean BEAN类  咨询信息
	 */
	//	/**
	//	 * <p>
	//	 * 编辑。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>编辑。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String edit() {
	//		log.info("C301Action edit.........");
	//		String id=request.getParameter("id");
	//		if(id!=null){
	//			TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//			bean1.setId(id);
	//			bean=consultMsgManager.findDataById(bean1);
	//		}else{
	//			SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
	//			request.setAttribute("date", sdf.format(new Date()));
	//		}
	//		return "edit";
	//	}
	//	/**
	//	 * <p>
	//	 * 信息保存
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>新增。</div>
	//	 * <div>修改。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String save() {
	//		log.info("C301Action edit.........");
	//		if(bean!=null){
	//			String msg="1";
	//			try {
	//					BaseUserBean user = (BaseUserBean) request.getSession().getAttribute(CommonConstant.SESSION_KEY_USER_CUSTOMER_SERVICE);
	//					if(user!=null){
	//						bean.setCreate_ip(getIpAddr());
	//						bean.setCreate_id(user.getUser_id());
	//						bean.setUpdate_ip(getIpAddr());
	//						bean.setUpdate_id(user.getUser_id());
	//					}
	//					msg=consultMsgManager.saveOrUpdateData(bean);
	//			} catch (Exception e) {
	//				msg=e.getMessage();
	//			}
	//			request.setAttribute("msg",msg);
	//		}else{
	//			request.setAttribute("msg", "信息保存失败!");
	//		}
	//		return SUCCESS;
	//	}
	//	/**
	//	 * <p>
	//	 * 删除。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>删除。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String del() {
	//		log.info("C301Action del.........");
	//		
	//		String id=request.getParameter("id");
	//		TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//		bean1.setId(id);
	//		String msg="1";
	//		try {
	//			msg=consultMsgManager.deleteDataById(bean1);
	//		} catch (Exception e) {
	//			msg=e.getMessage();
	//		}
	//		request.setAttribute("msg",msg);
	//		return SUCCESS;
	//	}
	
	
	
	/**
	 * BEAN类  咨询信息取得
	 * @return BEAN类  咨询信息
	 */
	//	/**
	//	 * <p>
	//	 * 编辑。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>编辑。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String edit() {
	//		log.info("C301Action edit.........");
	//		String id=request.getParameter("id");
	//		if(id!=null){
	//			TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//			bean1.setId(id);
	//			bean=consultMsgManager.findDataById(bean1);
	//		}else{
	//			SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
	//			request.setAttribute("date", sdf.format(new Date()));
	//		}
	//		return "edit";
	//	}
	//	/**
	//	 * <p>
	//	 * 信息保存
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>新增。</div>
	//	 * <div>修改。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String save() {
	//		log.info("C301Action edit.........");
	//		if(bean!=null){
	//			String msg="1";
	//			try {
	//					BaseUserBean user = (BaseUserBean) request.getSession().getAttribute(CommonConstant.SESSION_KEY_USER_CUSTOMER_SERVICE);
	//					if(user!=null){
	//						bean.setCreate_ip(getIpAddr());
	//						bean.setCreate_id(user.getUser_id());
	//						bean.setUpdate_ip(getIpAddr());
	//						bean.setUpdate_id(user.getUser_id());
	//					}
	//					msg=consultMsgManager.saveOrUpdateData(bean);
	//			} catch (Exception e) {
	//				msg=e.getMessage();
	//			}
	//			request.setAttribute("msg",msg);
	//		}else{
	//			request.setAttribute("msg", "信息保存失败!");
	//		}
	//		return SUCCESS;
	//	}
	//	/**
	//	 * <p>
	//	 * 删除。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>删除。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String del() {
	//		log.info("C301Action del.........");
	//		
	//		String id=request.getParameter("id");
	//		TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//		bean1.setId(id);
	//		String msg="1";
	//		try {
	//			msg=consultMsgManager.deleteDataById(bean1);
	//		} catch (Exception e) {
	//			msg=e.getMessage();
	//		}
	//		request.setAttribute("msg",msg);
	//		return SUCCESS;
	//	}
	
	
	
	/**
	 * BEAN类  咨询信息设定
	 * @param bean BEAN类  咨询信息
	 */
	//	/**
	//	 * <p>
	//	 * 编辑。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>编辑。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String edit() {
	//		log.info("C301Action edit.........");
	//		String id=request.getParameter("id");
	//		if(id!=null){
	//			TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//			bean1.setId(id);
	//			bean=consultMsgManager.findDataById(bean1);
	//		}else{
	//			SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
	//			request.setAttribute("date", sdf.format(new Date()));
	//		}
	//		return "edit";
	//	}
	//	/**
	//	 * <p>
	//	 * 信息保存
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>新增。</div>
	//	 * <div>修改。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String save() {
	//		log.info("C301Action edit.........");
	//		if(bean!=null){
	//			String msg="1";
	//			try {
	//					BaseUserBean user = (BaseUserBean) request.getSession().getAttribute(CommonConstant.SESSION_KEY_USER_CUSTOMER_SERVICE);
	//					if(user!=null){
	//						bean.setCreate_ip(getIpAddr());
	//						bean.setCreate_id(user.getUser_id());
	//						bean.setUpdate_ip(getIpAddr());
	//						bean.setUpdate_id(user.getUser_id());
	//					}
	//					msg=consultMsgManager.saveOrUpdateData(bean);
	//			} catch (Exception e) {
	//				msg=e.getMessage();
	//			}
	//			request.setAttribute("msg",msg);
	//		}else{
	//			request.setAttribute("msg", "信息保存失败!");
	//		}
	//		return SUCCESS;
	//	}
	//	/**
	//	 * <p>
	//	 * 删除。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>删除。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String del() {
	//		log.info("C301Action del.........");
	//		
	//		String id=request.getParameter("id");
	//		TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//		bean1.setId(id);
	//		String msg="1";
	//		try {
	//			msg=consultMsgManager.deleteDataById(bean1);
	//		} catch (Exception e) {
	//			msg=e.getMessage();
	//		}
	//		request.setAttribute("msg",msg);
	//		return SUCCESS;
	//	}
	
	
	
	/**
	 * BEAN类  咨询信息取得
	 * @return BEAN类  咨询信息
	 */
	//	/**
	//	 * <p>
	//	 * 编辑。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>编辑。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String edit() {
	//		log.info("C301Action edit.........");
	//		String id=request.getParameter("id");
	//		if(id!=null){
	//			TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//			bean1.setId(id);
	//			bean=consultMsgManager.findDataById(bean1);
	//		}else{
	//			SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
	//			request.setAttribute("date", sdf.format(new Date()));
	//		}
	//		return "edit";
	//	}
	//	/**
	//	 * <p>
	//	 * 信息保存
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>新增。</div>
	//	 * <div>修改。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String save() {
	//		log.info("C301Action edit.........");
	//		if(bean!=null){
	//			String msg="1";
	//			try {
	//					BaseUserBean user = (BaseUserBean) request.getSession().getAttribute(CommonConstant.SESSION_KEY_USER_CUSTOMER_SERVICE);
	//					if(user!=null){
	//						bean.setCreate_ip(getIpAddr());
	//						bean.setCreate_id(user.getUser_id());
	//						bean.setUpdate_ip(getIpAddr());
	//						bean.setUpdate_id(user.getUser_id());
	//					}
	//					msg=consultMsgManager.saveOrUpdateData(bean);
	//			} catch (Exception e) {
	//				msg=e.getMessage();
	//			}
	//			request.setAttribute("msg",msg);
	//		}else{
	//			request.setAttribute("msg", "信息保存失败!");
	//		}
	//		return SUCCESS;
	//	}
	//	/**
	//	 * <p>
	//	 * 删除。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>删除。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String del() {
	//		log.info("C301Action del.........");
	//		
	//		String id=request.getParameter("id");
	//		TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//		bean1.setId(id);
	//		String msg="1";
	//		try {
	//			msg=consultMsgManager.deleteDataById(bean1);
	//		} catch (Exception e) {
	//			msg=e.getMessage();
	//		}
	//		request.setAttribute("msg",msg);
	//		return SUCCESS;
	//	}
	
	
	
	/**
	 * BEAN类  咨询信息取得
	 * @return BEAN类  咨询信息
	 */
	//	/**
	//	 * <p>
	//	 * 编辑。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>编辑。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String edit() {
	//		log.info("C301Action edit.........");
	//		String id=request.getParameter("id");
	//		if(id!=null){
	//			TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//			bean1.setId(id);
	//			bean=consultMsgManager.findDataById(bean1);
	//		}else{
	//			SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
	//			request.setAttribute("date", sdf.format(new Date()));
	//		}
	//		return "edit";
	//	}
	//	/**
	//	 * <p>
	//	 * 信息保存
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>新增。</div>
	//	 * <div>修改。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String save() {
	//		log.info("C301Action edit.........");
	//		if(bean!=null){
	//			String msg="1";
	//			try {
	//					BaseUserBean user = (BaseUserBean) request.getSession().getAttribute(CommonConstant.SESSION_KEY_USER_CUSTOMER_SERVICE);
	//					if(user!=null){
	//						bean.setCreate_ip(getIpAddr());
	//						bean.setCreate_id(user.getUser_id());
	//						bean.setUpdate_ip(getIpAddr());
	//						bean.setUpdate_id(user.getUser_id());
	//					}
	//					msg=consultMsgManager.saveOrUpdateData(bean);
	//			} catch (Exception e) {
	//				msg=e.getMessage();
	//			}
	//			request.setAttribute("msg",msg);
	//		}else{
	//			request.setAttribute("msg", "信息保存失败!");
	//		}
	//		return SUCCESS;
	//	}
	//	/**
	//	 * <p>
	//	 * 删除。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>删除。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String del() {
	//		log.info("C301Action del.........");
	//		
	//		String id=request.getParameter("id");
	//		TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//		bean1.setId(id);
	//		String msg="1";
	//		try {
	//			msg=consultMsgManager.deleteDataById(bean1);
	//		} catch (Exception e) {
	//			msg=e.getMessage();
	//		}
	//		request.setAttribute("msg",msg);
	//		return SUCCESS;
	//	}
	
	
	
	/**
	 * BEAN类  咨询信息设定
	 * @param bean BEAN类  咨询信息
	 */
	//	/**
	//	 * <p>
	//	 * 编辑。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>编辑。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String edit() {
	//		log.info("C301Action edit.........");
	//		String id=request.getParameter("id");
	//		if(id!=null){
	//			TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//			bean1.setId(id);
	//			bean=consultMsgManager.findDataById(bean1);
	//		}else{
	//			SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
	//			request.setAttribute("date", sdf.format(new Date()));
	//		}
	//		return "edit";
	//	}
	//	/**
	//	 * <p>
	//	 * 信息保存
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>新增。</div>
	//	 * <div>修改。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String save() {
	//		log.info("C301Action edit.........");
	//		if(bean!=null){
	//			String msg="1";
	//			try {
	//					BaseUserBean user = (BaseUserBean) request.getSession().getAttribute(CommonConstant.SESSION_KEY_USER_CUSTOMER_SERVICE);
	//					if(user!=null){
	//						bean.setCreate_ip(getIpAddr());
	//						bean.setCreate_id(user.getUser_id());
	//						bean.setUpdate_ip(getIpAddr());
	//						bean.setUpdate_id(user.getUser_id());
	//					}
	//					msg=consultMsgManager.saveOrUpdateData(bean);
	//			} catch (Exception e) {
	//				msg=e.getMessage();
	//			}
	//			request.setAttribute("msg",msg);
	//		}else{
	//			request.setAttribute("msg", "信息保存失败!");
	//		}
	//		return SUCCESS;
	//	}
	//	/**
	//	 * <p>
	//	 * 删除。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>删除。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String del() {
	//		log.info("C301Action del.........");
	//		
	//		String id=request.getParameter("id");
	//		TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//		bean1.setId(id);
	//		String msg="1";
	//		try {
	//			msg=consultMsgManager.deleteDataById(bean1);
	//		} catch (Exception e) {
	//			msg=e.getMessage();
	//		}
	//		request.setAttribute("msg",msg);
	//		return SUCCESS;
	//	}
	
	
	
	/**
	 * BEAN类  咨询信息取得
	 * @return BEAN类  咨询信息
	 */
	//	/**
	//	 * <p>
	//	 * 编辑。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>编辑。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String edit() {
	//		log.info("C301Action edit.........");
	//		String id=request.getParameter("id");
	//		if(id!=null){
	//			TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//			bean1.setId(id);
	//			bean=consultMsgManager.findDataById(bean1);
	//		}else{
	//			SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
	//			request.setAttribute("date", sdf.format(new Date()));
	//		}
	//		return "edit";
	//	}
	//	/**
	//	 * <p>
	//	 * 信息保存
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>新增。</div>
	//	 * <div>修改。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String save() {
	//		log.info("C301Action edit.........");
	//		if(bean!=null){
	//			String msg="1";
	//			try {
	//					BaseUserBean user = (BaseUserBean) request.getSession().getAttribute(CommonConstant.SESSION_KEY_USER_CUSTOMER_SERVICE);
	//					if(user!=null){
	//						bean.setCreate_ip(getIpAddr());
	//						bean.setCreate_id(user.getUser_id());
	//						bean.setUpdate_ip(getIpAddr());
	//						bean.setUpdate_id(user.getUser_id());
	//					}
	//					msg=consultMsgManager.saveOrUpdateData(bean);
	//			} catch (Exception e) {
	//				msg=e.getMessage();
	//			}
	//			request.setAttribute("msg",msg);
	//		}else{
	//			request.setAttribute("msg", "信息保存失败!");
	//		}
	//		return SUCCESS;
	//	}
	//	/**
	//	 * <p>
	//	 * 删除。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>删除。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String del() {
	//		log.info("C301Action del.........");
	//		
	//		String id=request.getParameter("id");
	//		TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//		bean1.setId(id);
	//		String msg="1";
	//		try {
	//			msg=consultMsgManager.deleteDataById(bean1);
	//		} catch (Exception e) {
	//			msg=e.getMessage();
	//		}
	//		request.setAttribute("msg",msg);
	//		return SUCCESS;
	//	}
	
	
	
	/**
	 * BEAN类  咨询信息设定
	 * @param bean BEAN类  咨询信息
	 */
	//	/**
	//	 * <p>
	//	 * 编辑。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>编辑。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String edit() {
	//		log.info("C301Action edit.........");
	//		String id=request.getParameter("id");
	//		if(id!=null){
	//			TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//			bean1.setId(id);
	//			bean=consultMsgManager.findDataById(bean1);
	//		}else{
	//			SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
	//			request.setAttribute("date", sdf.format(new Date()));
	//		}
	//		return "edit";
	//	}
	//	/**
	//	 * <p>
	//	 * 信息保存
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>新增。</div>
	//	 * <div>修改。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String save() {
	//		log.info("C301Action edit.........");
	//		if(bean!=null){
	//			String msg="1";
	//			try {
	//					BaseUserBean user = (BaseUserBean) request.getSession().getAttribute(CommonConstant.SESSION_KEY_USER_CUSTOMER_SERVICE);
	//					if(user!=null){
	//						bean.setCreate_ip(getIpAddr());
	//						bean.setCreate_id(user.getUser_id());
	//						bean.setUpdate_ip(getIpAddr());
	//						bean.setUpdate_id(user.getUser_id());
	//					}
	//					msg=consultMsgManager.saveOrUpdateData(bean);
	//			} catch (Exception e) {
	//				msg=e.getMessage();
	//			}
	//			request.setAttribute("msg",msg);
	//		}else{
	//			request.setAttribute("msg", "信息保存失败!");
	//		}
	//		return SUCCESS;
	//	}
	//	/**
	//	 * <p>
	//	 * 删除。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>删除。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String del() {
	//		log.info("C301Action del.........");
	//		
	//		String id=request.getParameter("id");
	//		TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//		bean1.setId(id);
	//		String msg="1";
	//		try {
	//			msg=consultMsgManager.deleteDataById(bean1);
	//		} catch (Exception e) {
	//			msg=e.getMessage();
	//		}
	//		request.setAttribute("msg",msg);
	//		return SUCCESS;
	//	}
	
	
	
	/**
	 * BEAN类  咨询信息取得
	 * @return BEAN类  咨询信息
	 */
	//	/**
	//	 * <p>
	//	 * 编辑。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>编辑。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String edit() {
	//		log.info("C301Action edit.........");
	//		String id=request.getParameter("id");
	//		if(id!=null){
	//			TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//			bean1.setId(id);
	//			bean=consultMsgManager.findDataById(bean1);
	//		}else{
	//			SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
	//			request.setAttribute("date", sdf.format(new Date()));
	//		}
	//		return "edit";
	//	}
	//	/**
	//	 * <p>
	//	 * 信息保存
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>新增。</div>
	//	 * <div>修改。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String save() {
	//		log.info("C301Action edit.........");
	//		if(bean!=null){
	//			String msg="1";
	//			try {
	//					BaseUserBean user = (BaseUserBean) request.getSession().getAttribute(CommonConstant.SESSION_KEY_USER_CUSTOMER_SERVICE);
	//					if(user!=null){
	//						bean.setCreate_ip(getIpAddr());
	//						bean.setCreate_id(user.getUser_id());
	//						bean.setUpdate_ip(getIpAddr());
	//						bean.setUpdate_id(user.getUser_id());
	//					}
	//					msg=consultMsgManager.saveOrUpdateData(bean);
	//			} catch (Exception e) {
	//				msg=e.getMessage();
	//			}
	//			request.setAttribute("msg",msg);
	//		}else{
	//			request.setAttribute("msg", "信息保存失败!");
	//		}
	//		return SUCCESS;
	//	}
	//	/**
	//	 * <p>
	//	 * 删除。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>删除。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String del() {
	//		log.info("C301Action del.........");
	//		
	//		String id=request.getParameter("id");
	//		TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//		bean1.setId(id);
	//		String msg="1";
	//		try {
	//			msg=consultMsgManager.deleteDataById(bean1);
	//		} catch (Exception e) {
	//			msg=e.getMessage();
	//		}
	//		request.setAttribute("msg",msg);
	//		return SUCCESS;
	//	}
	
	
	
	/**
	 * BEAN类  咨询信息取得
	 * @return BEAN类  咨询信息
	 */
	//	/**
	//	 * <p>
	//	 * 编辑。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>编辑。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String edit() {
	//		log.info("C301Action edit.........");
	//		String id=request.getParameter("id");
	//		if(id!=null){
	//			TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//			bean1.setId(id);
	//			bean=consultMsgManager.findDataById(bean1);
	//		}else{
	//			SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
	//			request.setAttribute("date", sdf.format(new Date()));
	//		}
	//		return "edit";
	//	}
	//	/**
	//	 * <p>
	//	 * 信息保存
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>新增。</div>
	//	 * <div>修改。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String save() {
	//		log.info("C301Action edit.........");
	//		if(bean!=null){
	//			String msg="1";
	//			try {
	//					BaseUserBean user = (BaseUserBean) request.getSession().getAttribute(CommonConstant.SESSION_KEY_USER_CUSTOMER_SERVICE);
	//					if(user!=null){
	//						bean.setCreate_ip(getIpAddr());
	//						bean.setCreate_id(user.getUser_id());
	//						bean.setUpdate_ip(getIpAddr());
	//						bean.setUpdate_id(user.getUser_id());
	//					}
	//					msg=consultMsgManager.saveOrUpdateData(bean);
	//			} catch (Exception e) {
	//				msg=e.getMessage();
	//			}
	//			request.setAttribute("msg",msg);
	//		}else{
	//			request.setAttribute("msg", "信息保存失败!");
	//		}
	//		return SUCCESS;
	//	}
	//	/**
	//	 * <p>
	//	 * 删除。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>删除。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String del() {
	//		log.info("C301Action del.........");
	//		
	//		String id=request.getParameter("id");
	//		TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//		bean1.setId(id);
	//		String msg="1";
	//		try {
	//			msg=consultMsgManager.deleteDataById(bean1);
	//		} catch (Exception e) {
	//			msg=e.getMessage();
	//		}
	//		request.setAttribute("msg",msg);
	//		return SUCCESS;
	//	}
	
	
	
	/**
	 * BEAN类  咨询信息设定
	 * @param bean BEAN类  咨询信息
	 */
	//	/**
	//	 * <p>
	//	 * 编辑。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>编辑。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String edit() {
	//		log.info("C301Action edit.........");
	//		String id=request.getParameter("id");
	//		if(id!=null){
	//			TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//			bean1.setId(id);
	//			bean=consultMsgManager.findDataById(bean1);
	//		}else{
	//			SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
	//			request.setAttribute("date", sdf.format(new Date()));
	//		}
	//		return "edit";
	//	}
	//	/**
	//	 * <p>
	//	 * 信息保存
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>新增。</div>
	//	 * <div>修改。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String save() {
	//		log.info("C301Action edit.........");
	//		if(bean!=null){
	//			String msg="1";
	//			try {
	//					BaseUserBean user = (BaseUserBean) request.getSession().getAttribute(CommonConstant.SESSION_KEY_USER_CUSTOMER_SERVICE);
	//					if(user!=null){
	//						bean.setCreate_ip(getIpAddr());
	//						bean.setCreate_id(user.getUser_id());
	//						bean.setUpdate_ip(getIpAddr());
	//						bean.setUpdate_id(user.getUser_id());
	//					}
	//					msg=consultMsgManager.saveOrUpdateData(bean);
	//			} catch (Exception e) {
	//				msg=e.getMessage();
	//			}
	//			request.setAttribute("msg",msg);
	//		}else{
	//			request.setAttribute("msg", "信息保存失败!");
	//		}
	//		return SUCCESS;
	//	}
	//	/**
	//	 * <p>
	//	 * 删除。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>删除。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String del() {
	//		log.info("C301Action del.........");
	//		
	//		String id=request.getParameter("id");
	//		TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//		bean1.setId(id);
	//		String msg="1";
	//		try {
	//			msg=consultMsgManager.deleteDataById(bean1);
	//		} catch (Exception e) {
	//			msg=e.getMessage();
	//		}
	//		request.setAttribute("msg",msg);
	//		return SUCCESS;
	//	}
	
	
	
	/**
	 * BEAN类  咨询信息取得
	 * @return BEAN类  咨询信息
	 */
	//	/**
	//	 * <p>
	//	 * 编辑。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>编辑。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String edit() {
	//		log.info("C301Action edit.........");
	//		String id=request.getParameter("id");
	//		if(id!=null){
	//			TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//			bean1.setId(id);
	//			bean=consultMsgManager.findDataById(bean1);
	//		}else{
	//			SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
	//			request.setAttribute("date", sdf.format(new Date()));
	//		}
	//		return "edit";
	//	}
	//	/**
	//	 * <p>
	//	 * 信息保存
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>新增。</div>
	//	 * <div>修改。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String save() {
	//		log.info("C301Action edit.........");
	//		if(bean!=null){
	//			String msg="1";
	//			try {
	//					BaseUserBean user = (BaseUserBean) request.getSession().getAttribute(CommonConstant.SESSION_KEY_USER_CUSTOMER_SERVICE);
	//					if(user!=null){
	//						bean.setCreate_ip(getIpAddr());
	//						bean.setCreate_id(user.getUser_id());
	//						bean.setUpdate_ip(getIpAddr());
	//						bean.setUpdate_id(user.getUser_id());
	//					}
	//					msg=consultMsgManager.saveOrUpdateData(bean);
	//			} catch (Exception e) {
	//				msg=e.getMessage();
	//			}
	//			request.setAttribute("msg",msg);
	//		}else{
	//			request.setAttribute("msg", "信息保存失败!");
	//		}
	//		return SUCCESS;
	//	}
	//	/**
	//	 * <p>
	//	 * 删除。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>删除。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String del() {
	//		log.info("C301Action del.........");
	//		
	//		String id=request.getParameter("id");
	//		TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//		bean1.setId(id);
	//		String msg="1";
	//		try {
	//			msg=consultMsgManager.deleteDataById(bean1);
	//		} catch (Exception e) {
	//			msg=e.getMessage();
	//		}
	//		request.setAttribute("msg",msg);
	//		return SUCCESS;
	//	}
	
	
	
	/**
	 * BEAN类  咨询信息取得
	 * @return BEAN类  咨询信息
	 */
	//	/**
	//	 * <p>
	//	 * 编辑。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>编辑。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String edit() {
	//		log.info("C301Action edit.........");
	//		String id=request.getParameter("id");
	//		if(id!=null){
	//			TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//			bean1.setId(id);
	//			bean=consultMsgManager.findDataById(bean1);
	//		}else{
	//			SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
	//			request.setAttribute("date", sdf.format(new Date()));
	//		}
	//		return "edit";
	//	}
	//	/**
	//	 * <p>
	//	 * 信息保存
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>新增。</div>
	//	 * <div>修改。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String save() {
	//		log.info("C301Action edit.........");
	//		if(bean!=null){
	//			String msg="1";
	//			try {
	//					BaseUserBean user = (BaseUserBean) request.getSession().getAttribute(CommonConstant.SESSION_KEY_USER_CUSTOMER_SERVICE);
	//					if(user!=null){
	//						bean.setCreate_ip(getIpAddr());
	//						bean.setCreate_id(user.getUser_id());
	//						bean.setUpdate_ip(getIpAddr());
	//						bean.setUpdate_id(user.getUser_id());
	//					}
	//					msg=consultMsgManager.saveOrUpdateData(bean);
	//			} catch (Exception e) {
	//				msg=e.getMessage();
	//			}
	//			request.setAttribute("msg",msg);
	//		}else{
	//			request.setAttribute("msg", "信息保存失败!");
	//		}
	//		return SUCCESS;
	//	}
	//	/**
	//	 * <p>
	//	 * 删除。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>删除。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String del() {
	//		log.info("C301Action del.........");
	//		
	//		String id=request.getParameter("id");
	//		TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//		bean1.setId(id);
	//		String msg="1";
	//		try {
	//			msg=consultMsgManager.deleteDataById(bean1);
	//		} catch (Exception e) {
	//			msg=e.getMessage();
	//		}
	//		request.setAttribute("msg",msg);
	//		return SUCCESS;
	//	}
	
	
	
	/**
	 * BEAN类  咨询信息设定
	 * @param bean BEAN类  咨询信息
	 */
	//	/**
	//	 * <p>
	//	 * 编辑。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>编辑。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String edit() {
	//		log.info("C301Action edit.........");
	//		String id=request.getParameter("id");
	//		if(id!=null){
	//			TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//			bean1.setId(id);
	//			bean=consultMsgManager.findDataById(bean1);
	//		}else{
	//			SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
	//			request.setAttribute("date", sdf.format(new Date()));
	//		}
	//		return "edit";
	//	}
	//	/**
	//	 * <p>
	//	 * 信息保存
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>新增。</div>
	//	 * <div>修改。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String save() {
	//		log.info("C301Action edit.........");
	//		if(bean!=null){
	//			String msg="1";
	//			try {
	//					BaseUserBean user = (BaseUserBean) request.getSession().getAttribute(CommonConstant.SESSION_KEY_USER_CUSTOMER_SERVICE);
	//					if(user!=null){
	//						bean.setCreate_ip(getIpAddr());
	//						bean.setCreate_id(user.getUser_id());
	//						bean.setUpdate_ip(getIpAddr());
	//						bean.setUpdate_id(user.getUser_id());
	//					}
	//					msg=consultMsgManager.saveOrUpdateData(bean);
	//			} catch (Exception e) {
	//				msg=e.getMessage();
	//			}
	//			request.setAttribute("msg",msg);
	//		}else{
	//			request.setAttribute("msg", "信息保存失败!");
	//		}
	//		return SUCCESS;
	//	}
	//	/**
	//	 * <p>
	//	 * 删除。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>删除。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String del() {
	//		log.info("C301Action del.........");
	//		
	//		String id=request.getParameter("id");
	//		TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//		bean1.setId(id);
	//		String msg="1";
	//		try {
	//			msg=consultMsgManager.deleteDataById(bean1);
	//		} catch (Exception e) {
	//			msg=e.getMessage();
	//		}
	//		request.setAttribute("msg",msg);
	//		return SUCCESS;
	//	}
	
	
	
	/**
	 * BEAN类  咨询信息取得
	 * @return BEAN类  咨询信息
	 */
	//	/**
	//	 * <p>
	//	 * 编辑。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>编辑。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String edit() {
	//		log.info("C301Action edit.........");
	//		String id=request.getParameter("id");
	//		if(id!=null){
	//			TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//			bean1.setId(id);
	//			bean=consultMsgManager.findDataById(bean1);
	//		}else{
	//			SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
	//			request.setAttribute("date", sdf.format(new Date()));
	//		}
	//		return "edit";
	//	}
	//	/**
	//	 * <p>
	//	 * 信息保存
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>新增。</div>
	//	 * <div>修改。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String save() {
	//		log.info("C301Action edit.........");
	//		if(bean!=null){
	//			String msg="1";
	//			try {
	//					BaseUserBean user = (BaseUserBean) request.getSession().getAttribute(CommonConstant.SESSION_KEY_USER_CUSTOMER_SERVICE);
	//					if(user!=null){
	//						bean.setCreate_ip(getIpAddr());
	//						bean.setCreate_id(user.getUser_id());
	//						bean.setUpdate_ip(getIpAddr());
	//						bean.setUpdate_id(user.getUser_id());
	//					}
	//					msg=consultMsgManager.saveOrUpdateData(bean);
	//			} catch (Exception e) {
	//				msg=e.getMessage();
	//			}
	//			request.setAttribute("msg",msg);
	//		}else{
	//			request.setAttribute("msg", "信息保存失败!");
	//		}
	//		return SUCCESS;
	//	}
	//	/**
	//	 * <p>
	//	 * 删除。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>删除。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String del() {
	//		log.info("C301Action del.........");
	//		
	//		String id=request.getParameter("id");
	//		TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//		bean1.setId(id);
	//		String msg="1";
	//		try {
	//			msg=consultMsgManager.deleteDataById(bean1);
	//		} catch (Exception e) {
	//			msg=e.getMessage();
	//		}
	//		request.setAttribute("msg",msg);
	//		return SUCCESS;
	//	}
	
	
	
	/**
	 * BEAN类  咨询信息设定
	 * @param bean BEAN类  咨询信息
	 */
	//	/**
	//	 * <p>
	//	 * 编辑。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>编辑。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String edit() {
	//		log.info("C301Action edit.........");
	//		String id=request.getParameter("id");
	//		if(id!=null){
	//			TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//			bean1.setId(id);
	//			bean=consultMsgManager.findDataById(bean1);
	//		}else{
	//			SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
	//			request.setAttribute("date", sdf.format(new Date()));
	//		}
	//		return "edit";
	//	}
	//	/**
	//	 * <p>
	//	 * 信息保存
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>新增。</div>
	//	 * <div>修改。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String save() {
	//		log.info("C301Action edit.........");
	//		if(bean!=null){
	//			String msg="1";
	//			try {
	//					BaseUserBean user = (BaseUserBean) request.getSession().getAttribute(CommonConstant.SESSION_KEY_USER_CUSTOMER_SERVICE);
	//					if(user!=null){
	//						bean.setCreate_ip(getIpAddr());
	//						bean.setCreate_id(user.getUser_id());
	//						bean.setUpdate_ip(getIpAddr());
	//						bean.setUpdate_id(user.getUser_id());
	//					}
	//					msg=consultMsgManager.saveOrUpdateData(bean);
	//			} catch (Exception e) {
	//				msg=e.getMessage();
	//			}
	//			request.setAttribute("msg",msg);
	//		}else{
	//			request.setAttribute("msg", "信息保存失败!");
	//		}
	//		return SUCCESS;
	//	}
	//	/**
	//	 * <p>
	//	 * 删除。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>删除。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String del() {
	//		log.info("C301Action del.........");
	//		
	//		String id=request.getParameter("id");
	//		TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//		bean1.setId(id);
	//		String msg="1";
	//		try {
	//			msg=consultMsgManager.deleteDataById(bean1);
	//		} catch (Exception e) {
	//			msg=e.getMessage();
	//		}
	//		request.setAttribute("msg",msg);
	//		return SUCCESS;
	//	}
	
	
	
	/**
	 * BEAN类  咨询信息取得
	 * @return BEAN类  咨询信息
	 */
	//	/**
	//	 * <p>
	//	 * 编辑。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>编辑。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String edit() {
	//		log.info("C301Action edit.........");
	//		String id=request.getParameter("id");
	//		if(id!=null){
	//			TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//			bean1.setId(id);
	//			bean=consultMsgManager.findDataById(bean1);
	//		}else{
	//			SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
	//			request.setAttribute("date", sdf.format(new Date()));
	//		}
	//		return "edit";
	//	}
	//	/**
	//	 * <p>
	//	 * 信息保存
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>新增。</div>
	//	 * <div>修改。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String save() {
	//		log.info("C301Action edit.........");
	//		if(bean!=null){
	//			String msg="1";
	//			try {
	//					BaseUserBean user = (BaseUserBean) request.getSession().getAttribute(CommonConstant.SESSION_KEY_USER_CUSTOMER_SERVICE);
	//					if(user!=null){
	//						bean.setCreate_ip(getIpAddr());
	//						bean.setCreate_id(user.getUser_id());
	//						bean.setUpdate_ip(getIpAddr());
	//						bean.setUpdate_id(user.getUser_id());
	//					}
	//					msg=consultMsgManager.saveOrUpdateData(bean);
	//			} catch (Exception e) {
	//				msg=e.getMessage();
	//			}
	//			request.setAttribute("msg",msg);
	//		}else{
	//			request.setAttribute("msg", "信息保存失败!");
	//		}
	//		return SUCCESS;
	//	}
	//	/**
	//	 * <p>
	//	 * 删除。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>删除。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String del() {
	//		log.info("C301Action del.........");
	//		
	//		String id=request.getParameter("id");
	//		TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//		bean1.setId(id);
	//		String msg="1";
	//		try {
	//			msg=consultMsgManager.deleteDataById(bean1);
	//		} catch (Exception e) {
	//			msg=e.getMessage();
	//		}
	//		request.setAttribute("msg",msg);
	//		return SUCCESS;
	//	}
	
	
	
	/**
	 * BEAN类  咨询信息取得
	 * @return BEAN类  咨询信息
	 */
	//	/**
	//	 * <p>
	//	 * 编辑。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>编辑。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String edit() {
	//		log.info("C301Action edit.........");
	//		String id=request.getParameter("id");
	//		if(id!=null){
	//			TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//			bean1.setId(id);
	//			bean=consultMsgManager.findDataById(bean1);
	//		}else{
	//			SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
	//			request.setAttribute("date", sdf.format(new Date()));
	//		}
	//		return "edit";
	//	}
	//	/**
	//	 * <p>
	//	 * 信息保存
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>新增。</div>
	//	 * <div>修改。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String save() {
	//		log.info("C301Action edit.........");
	//		if(bean!=null){
	//			String msg="1";
	//			try {
	//					BaseUserBean user = (BaseUserBean) request.getSession().getAttribute(CommonConstant.SESSION_KEY_USER_CUSTOMER_SERVICE);
	//					if(user!=null){
	//						bean.setCreate_ip(getIpAddr());
	//						bean.setCreate_id(user.getUser_id());
	//						bean.setUpdate_ip(getIpAddr());
	//						bean.setUpdate_id(user.getUser_id());
	//					}
	//					msg=consultMsgManager.saveOrUpdateData(bean);
	//			} catch (Exception e) {
	//				msg=e.getMessage();
	//			}
	//			request.setAttribute("msg",msg);
	//		}else{
	//			request.setAttribute("msg", "信息保存失败!");
	//		}
	//		return SUCCESS;
	//	}
	//	/**
	//	 * <p>
	//	 * 删除。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>删除。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String del() {
	//		log.info("C301Action del.........");
	//		
	//		String id=request.getParameter("id");
	//		TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//		bean1.setId(id);
	//		String msg="1";
	//		try {
	//			msg=consultMsgManager.deleteDataById(bean1);
	//		} catch (Exception e) {
	//			msg=e.getMessage();
	//		}
	//		request.setAttribute("msg",msg);
	//		return SUCCESS;
	//	}
	
	
	
	/**
	 * BEAN类  咨询信息设定
	 * @param bean BEAN类  咨询信息
	 */
	//	/**
	//	 * <p>
	//	 * 编辑。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>编辑。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String edit() {
	//		log.info("C301Action edit.........");
	//		String id=request.getParameter("id");
	//		if(id!=null){
	//			TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//			bean1.setId(id);
	//			bean=consultMsgManager.findDataById(bean1);
	//		}else{
	//			SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
	//			request.setAttribute("date", sdf.format(new Date()));
	//		}
	//		return "edit";
	//	}
	//	/**
	//	 * <p>
	//	 * 信息保存
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>新增。</div>
	//	 * <div>修改。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String save() {
	//		log.info("C301Action edit.........");
	//		if(bean!=null){
	//			String msg="1";
	//			try {
	//					BaseUserBean user = (BaseUserBean) request.getSession().getAttribute(CommonConstant.SESSION_KEY_USER_CUSTOMER_SERVICE);
	//					if(user!=null){
	//						bean.setCreate_ip(getIpAddr());
	//						bean.setCreate_id(user.getUser_id());
	//						bean.setUpdate_ip(getIpAddr());
	//						bean.setUpdate_id(user.getUser_id());
	//					}
	//					msg=consultMsgManager.saveOrUpdateData(bean);
	//			} catch (Exception e) {
	//				msg=e.getMessage();
	//			}
	//			request.setAttribute("msg",msg);
	//		}else{
	//			request.setAttribute("msg", "信息保存失败!");
	//		}
	//		return SUCCESS;
	//	}
	//	/**
	//	 * <p>
	//	 * 删除。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>删除。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String del() {
	//		log.info("C301Action del.........");
	//		
	//		String id=request.getParameter("id");
	//		TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//		bean1.setId(id);
	//		String msg="1";
	//		try {
	//			msg=consultMsgManager.deleteDataById(bean1);
	//		} catch (Exception e) {
	//			msg=e.getMessage();
	//		}
	//		request.setAttribute("msg",msg);
	//		return SUCCESS;
	//	}
	
	
	
	/**
	 * BEAN类  咨询信息取得
	 * @return BEAN类  咨询信息
	 */
	//	/**
	//	 * <p>
	//	 * 编辑。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>编辑。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String edit() {
	//		log.info("C301Action edit.........");
	//		String id=request.getParameter("id");
	//		if(id!=null){
	//			TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//			bean1.setId(id);
	//			bean=consultMsgManager.findDataById(bean1);
	//		}else{
	//			SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
	//			request.setAttribute("date", sdf.format(new Date()));
	//		}
	//		return "edit";
	//	}
	//	/**
	//	 * <p>
	//	 * 信息保存
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>新增。</div>
	//	 * <div>修改。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String save() {
	//		log.info("C301Action edit.........");
	//		if(bean!=null){
	//			String msg="1";
	//			try {
	//					BaseUserBean user = (BaseUserBean) request.getSession().getAttribute(CommonConstant.SESSION_KEY_USER_CUSTOMER_SERVICE);
	//					if(user!=null){
	//						bean.setCreate_ip(getIpAddr());
	//						bean.setCreate_id(user.getUser_id());
	//						bean.setUpdate_ip(getIpAddr());
	//						bean.setUpdate_id(user.getUser_id());
	//					}
	//					msg=consultMsgManager.saveOrUpdateData(bean);
	//			} catch (Exception e) {
	//				msg=e.getMessage();
	//			}
	//			request.setAttribute("msg",msg);
	//		}else{
	//			request.setAttribute("msg", "信息保存失败!");
	//		}
	//		return SUCCESS;
	//	}
	//	/**
	//	 * <p>
	//	 * 删除。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>删除。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String del() {
	//		log.info("C301Action del.........");
	//		
	//		String id=request.getParameter("id");
	//		TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//		bean1.setId(id);
	//		String msg="1";
	//		try {
	//			msg=consultMsgManager.deleteDataById(bean1);
	//		} catch (Exception e) {
	//			msg=e.getMessage();
	//		}
	//		request.setAttribute("msg",msg);
	//		return SUCCESS;
	//	}
	
	
	
	/**
	 * BEAN类  咨询信息设定
	 * @param bean BEAN类  咨询信息
	 */
	//	/**
	//	 * <p>
	//	 * 编辑。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>编辑。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String edit() {
	//		log.info("C301Action edit.........");
	//		String id=request.getParameter("id");
	//		if(id!=null){
	//			TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//			bean1.setId(id);
	//			bean=consultMsgManager.findDataById(bean1);
	//		}else{
	//			SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
	//			request.setAttribute("date", sdf.format(new Date()));
	//		}
	//		return "edit";
	//	}
	//	/**
	//	 * <p>
	//	 * 信息保存
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>新增。</div>
	//	 * <div>修改。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String save() {
	//		log.info("C301Action edit.........");
	//		if(bean!=null){
	//			String msg="1";
	//			try {
	//					BaseUserBean user = (BaseUserBean) request.getSession().getAttribute(CommonConstant.SESSION_KEY_USER_CUSTOMER_SERVICE);
	//					if(user!=null){
	//						bean.setCreate_ip(getIpAddr());
	//						bean.setCreate_id(user.getUser_id());
	//						bean.setUpdate_ip(getIpAddr());
	//						bean.setUpdate_id(user.getUser_id());
	//					}
	//					msg=consultMsgManager.saveOrUpdateData(bean);
	//			} catch (Exception e) {
	//				msg=e.getMessage();
	//			}
	//			request.setAttribute("msg",msg);
	//		}else{
	//			request.setAttribute("msg", "信息保存失败!");
	//		}
	//		return SUCCESS;
	//	}
	//	/**
	//	 * <p>
	//	 * 删除。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>删除。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String del() {
	//		log.info("C301Action del.........");
	//		
	//		String id=request.getParameter("id");
	//		TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//		bean1.setId(id);
	//		String msg="1";
	//		try {
	//			msg=consultMsgManager.deleteDataById(bean1);
	//		} catch (Exception e) {
	//			msg=e.getMessage();
	//		}
	//		request.setAttribute("msg",msg);
	//		return SUCCESS;
	//	}
	
	
	
	/**
	 * BEAN类  咨询信息取得
	 * @return BEAN类  咨询信息
	 */
	//	/**
	//	 * <p>
	//	 * 编辑。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>编辑。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String edit() {
	//		log.info("C301Action edit.........");
	//		String id=request.getParameter("id");
	//		if(id!=null){
	//			TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//			bean1.setId(id);
	//			bean=consultMsgManager.findDataById(bean1);
	//		}else{
	//			SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
	//			request.setAttribute("date", sdf.format(new Date()));
	//		}
	//		return "edit";
	//	}
	//	/**
	//	 * <p>
	//	 * 信息保存
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>新增。</div>
	//	 * <div>修改。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String save() {
	//		log.info("C301Action edit.........");
	//		if(bean!=null){
	//			String msg="1";
	//			try {
	//					BaseUserBean user = (BaseUserBean) request.getSession().getAttribute(CommonConstant.SESSION_KEY_USER_CUSTOMER_SERVICE);
	//					if(user!=null){
	//						bean.setCreate_ip(getIpAddr());
	//						bean.setCreate_id(user.getUser_id());
	//						bean.setUpdate_ip(getIpAddr());
	//						bean.setUpdate_id(user.getUser_id());
	//					}
	//					msg=consultMsgManager.saveOrUpdateData(bean);
	//			} catch (Exception e) {
	//				msg=e.getMessage();
	//			}
	//			request.setAttribute("msg",msg);
	//		}else{
	//			request.setAttribute("msg", "信息保存失败!");
	//		}
	//		return SUCCESS;
	//	}
	//	/**
	//	 * <p>
	//	 * 删除。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>删除。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String del() {
	//		log.info("C301Action del.........");
	//		
	//		String id=request.getParameter("id");
	//		TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//		bean1.setId(id);
	//		String msg="1";
	//		try {
	//			msg=consultMsgManager.deleteDataById(bean1);
	//		} catch (Exception e) {
	//			msg=e.getMessage();
	//		}
	//		request.setAttribute("msg",msg);
	//		return SUCCESS;
	//	}
	
	
	
	/**
	 * BEAN类  咨询信息取得
	 * @return BEAN类  咨询信息
	 */
	//	/**
	//	 * <p>
	//	 * 编辑。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>编辑。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String edit() {
	//		log.info("C301Action edit.........");
	//		String id=request.getParameter("id");
	//		if(id!=null){
	//			TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//			bean1.setId(id);
	//			bean=consultMsgManager.findDataById(bean1);
	//		}else{
	//			SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
	//			request.setAttribute("date", sdf.format(new Date()));
	//		}
	//		return "edit";
	//	}
	//	/**
	//	 * <p>
	//	 * 信息保存
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>新增。</div>
	//	 * <div>修改。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String save() {
	//		log.info("C301Action edit.........");
	//		if(bean!=null){
	//			String msg="1";
	//			try {
	//					BaseUserBean user = (BaseUserBean) request.getSession().getAttribute(CommonConstant.SESSION_KEY_USER_CUSTOMER_SERVICE);
	//					if(user!=null){
	//						bean.setCreate_ip(getIpAddr());
	//						bean.setCreate_id(user.getUser_id());
	//						bean.setUpdate_ip(getIpAddr());
	//						bean.setUpdate_id(user.getUser_id());
	//					}
	//					msg=consultMsgManager.saveOrUpdateData(bean);
	//			} catch (Exception e) {
	//				msg=e.getMessage();
	//			}
	//			request.setAttribute("msg",msg);
	//		}else{
	//			request.setAttribute("msg", "信息保存失败!");
	//		}
	//		return SUCCESS;
	//	}
	//	/**
	//	 * <p>
	//	 * 删除。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>删除。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String del() {
	//		log.info("C301Action del.........");
	//		
	//		String id=request.getParameter("id");
	//		TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//		bean1.setId(id);
	//		String msg="1";
	//		try {
	//			msg=consultMsgManager.deleteDataById(bean1);
	//		} catch (Exception e) {
	//			msg=e.getMessage();
	//		}
	//		request.setAttribute("msg",msg);
	//		return SUCCESS;
	//	}
	
	
	
	/**
	 * BEAN类  咨询信息设定
	 * @param bean BEAN类  咨询信息
	 */
	//	/**
	//	 * <p>
	//	 * 编辑。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>编辑。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String edit() {
	//		log.info("C301Action edit.........");
	//		String id=request.getParameter("id");
	//		if(id!=null){
	//			TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//			bean1.setId(id);
	//			bean=consultMsgManager.findDataById(bean1);
	//		}else{
	//			SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
	//			request.setAttribute("date", sdf.format(new Date()));
	//		}
	//		return "edit";
	//	}
	//	/**
	//	 * <p>
	//	 * 信息保存
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>新增。</div>
	//	 * <div>修改。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String save() {
	//		log.info("C301Action edit.........");
	//		if(bean!=null){
	//			String msg="1";
	//			try {
	//					BaseUserBean user = (BaseUserBean) request.getSession().getAttribute(CommonConstant.SESSION_KEY_USER_CUSTOMER_SERVICE);
	//					if(user!=null){
	//						bean.setCreate_ip(getIpAddr());
	//						bean.setCreate_id(user.getUser_id());
	//						bean.setUpdate_ip(getIpAddr());
	//						bean.setUpdate_id(user.getUser_id());
	//					}
	//					msg=consultMsgManager.saveOrUpdateData(bean);
	//			} catch (Exception e) {
	//				msg=e.getMessage();
	//			}
	//			request.setAttribute("msg",msg);
	//		}else{
	//			request.setAttribute("msg", "信息保存失败!");
	//		}
	//		return SUCCESS;
	//	}
	//	/**
	//	 * <p>
	//	 * 删除。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>删除。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String del() {
	//		log.info("C301Action del.........");
	//		
	//		String id=request.getParameter("id");
	//		TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//		bean1.setId(id);
	//		String msg="1";
	//		try {
	//			msg=consultMsgManager.deleteDataById(bean1);
	//		} catch (Exception e) {
	//			msg=e.getMessage();
	//		}
	//		request.setAttribute("msg",msg);
	//		return SUCCESS;
	//	}
	
	
	
	/**
	 * BEAN类  咨询信息取得
	 * @return BEAN类  咨询信息
	 */
	//	/**
	//	 * <p>
	//	 * 编辑。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>编辑。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String edit() {
	//		log.info("C301Action edit.........");
	//		String id=request.getParameter("id");
	//		if(id!=null){
	//			TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//			bean1.setId(id);
	//			bean=consultMsgManager.findDataById(bean1);
	//		}else{
	//			SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
	//			request.setAttribute("date", sdf.format(new Date()));
	//		}
	//		return "edit";
	//	}
	//	/**
	//	 * <p>
	//	 * 信息保存
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>新增。</div>
	//	 * <div>修改。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String save() {
	//		log.info("C301Action edit.........");
	//		if(bean!=null){
	//			String msg="1";
	//			try {
	//					BaseUserBean user = (BaseUserBean) request.getSession().getAttribute(CommonConstant.SESSION_KEY_USER_CUSTOMER_SERVICE);
	//					if(user!=null){
	//						bean.setCreate_ip(getIpAddr());
	//						bean.setCreate_id(user.getUser_id());
	//						bean.setUpdate_ip(getIpAddr());
	//						bean.setUpdate_id(user.getUser_id());
	//					}
	//					msg=consultMsgManager.saveOrUpdateData(bean);
	//			} catch (Exception e) {
	//				msg=e.getMessage();
	//			}
	//			request.setAttribute("msg",msg);
	//		}else{
	//			request.setAttribute("msg", "信息保存失败!");
	//		}
	//		return SUCCESS;
	//	}
	//	/**
	//	 * <p>
	//	 * 删除。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>删除。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String del() {
	//		log.info("C301Action del.........");
	//		
	//		String id=request.getParameter("id");
	//		TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//		bean1.setId(id);
	//		String msg="1";
	//		try {
	//			msg=consultMsgManager.deleteDataById(bean1);
	//		} catch (Exception e) {
	//			msg=e.getMessage();
	//		}
	//		request.setAttribute("msg",msg);
	//		return SUCCESS;
	//	}
	
	
	
	/**
	 * BEAN类  咨询信息取得
	 * @return BEAN类  咨询信息
	 */
	//	/**
	//	 * <p>
	//	 * 编辑。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>编辑。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String edit() {
	//		log.info("C301Action edit.........");
	//		String id=request.getParameter("id");
	//		if(id!=null){
	//			TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//			bean1.setId(id);
	//			bean=consultMsgManager.findDataById(bean1);
	//		}else{
	//			SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
	//			request.setAttribute("date", sdf.format(new Date()));
	//		}
	//		return "edit";
	//	}
	//	/**
	//	 * <p>
	//	 * 信息保存
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>新增。</div>
	//	 * <div>修改。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String save() {
	//		log.info("C301Action edit.........");
	//		if(bean!=null){
	//			String msg="1";
	//			try {
	//					BaseUserBean user = (BaseUserBean) request.getSession().getAttribute(CommonConstant.SESSION_KEY_USER_CUSTOMER_SERVICE);
	//					if(user!=null){
	//						bean.setCreate_ip(getIpAddr());
	//						bean.setCreate_id(user.getUser_id());
	//						bean.setUpdate_ip(getIpAddr());
	//						bean.setUpdate_id(user.getUser_id());
	//					}
	//					msg=consultMsgManager.saveOrUpdateData(bean);
	//			} catch (Exception e) {
	//				msg=e.getMessage();
	//			}
	//			request.setAttribute("msg",msg);
	//		}else{
	//			request.setAttribute("msg", "信息保存失败!");
	//		}
	//		return SUCCESS;
	//	}
	//	/**
	//	 * <p>
	//	 * 删除。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>删除。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String del() {
	//		log.info("C301Action del.........");
	//		
	//		String id=request.getParameter("id");
	//		TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//		bean1.setId(id);
	//		String msg="1";
	//		try {
	//			msg=consultMsgManager.deleteDataById(bean1);
	//		} catch (Exception e) {
	//			msg=e.getMessage();
	//		}
	//		request.setAttribute("msg",msg);
	//		return SUCCESS;
	//	}
	
	
	
	/**
	 * BEAN类  咨询信息设定
	 * @param bean BEAN类  咨询信息
	 */
	//	/**
	//	 * <p>
	//	 * 编辑。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>编辑。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String edit() {
	//		log.info("C301Action edit.........");
	//		String id=request.getParameter("id");
	//		if(id!=null){
	//			TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//			bean1.setId(id);
	//			bean=consultMsgManager.findDataById(bean1);
	//		}else{
	//			SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
	//			request.setAttribute("date", sdf.format(new Date()));
	//		}
	//		return "edit";
	//	}
	//	/**
	//	 * <p>
	//	 * 信息保存
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>新增。</div>
	//	 * <div>修改。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String save() {
	//		log.info("C301Action edit.........");
	//		if(bean!=null){
	//			String msg="1";
	//			try {
	//					BaseUserBean user = (BaseUserBean) request.getSession().getAttribute(CommonConstant.SESSION_KEY_USER_CUSTOMER_SERVICE);
	//					if(user!=null){
	//						bean.setCreate_ip(getIpAddr());
	//						bean.setCreate_id(user.getUser_id());
	//						bean.setUpdate_ip(getIpAddr());
	//						bean.setUpdate_id(user.getUser_id());
	//					}
	//					msg=consultMsgManager.saveOrUpdateData(bean);
	//			} catch (Exception e) {
	//				msg=e.getMessage();
	//			}
	//			request.setAttribute("msg",msg);
	//		}else{
	//			request.setAttribute("msg", "信息保存失败!");
	//		}
	//		return SUCCESS;
	//	}
	//	/**
	//	 * <p>
	//	 * 删除。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>删除。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String del() {
	//		log.info("C301Action del.........");
	//		
	//		String id=request.getParameter("id");
	//		TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//		bean1.setId(id);
	//		String msg="1";
	//		try {
	//			msg=consultMsgManager.deleteDataById(bean1);
	//		} catch (Exception e) {
	//			msg=e.getMessage();
	//		}
	//		request.setAttribute("msg",msg);
	//		return SUCCESS;
	//	}
	
	
	
	/**
	 * BEAN类  咨询信息取得
	 * @return BEAN类  咨询信息
	 */
	//	/**
	//	 * <p>
	//	 * 编辑。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>编辑。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String edit() {
	//		log.info("C301Action edit.........");
	//		String id=request.getParameter("id");
	//		if(id!=null){
	//			TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//			bean1.setId(id);
	//			bean=consultMsgManager.findDataById(bean1);
	//		}else{
	//			SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
	//			request.setAttribute("date", sdf.format(new Date()));
	//		}
	//		return "edit";
	//	}
	//	/**
	//	 * <p>
	//	 * 信息保存
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>新增。</div>
	//	 * <div>修改。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String save() {
	//		log.info("C301Action edit.........");
	//		if(bean!=null){
	//			String msg="1";
	//			try {
	//					BaseUserBean user = (BaseUserBean) request.getSession().getAttribute(CommonConstant.SESSION_KEY_USER_CUSTOMER_SERVICE);
	//					if(user!=null){
	//						bean.setCreate_ip(getIpAddr());
	//						bean.setCreate_id(user.getUser_id());
	//						bean.setUpdate_ip(getIpAddr());
	//						bean.setUpdate_id(user.getUser_id());
	//					}
	//					msg=consultMsgManager.saveOrUpdateData(bean);
	//			} catch (Exception e) {
	//				msg=e.getMessage();
	//			}
	//			request.setAttribute("msg",msg);
	//		}else{
	//			request.setAttribute("msg", "信息保存失败!");
	//		}
	//		return SUCCESS;
	//	}
	//	/**
	//	 * <p>
	//	 * 删除。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>删除。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String del() {
	//		log.info("C301Action del.........");
	//		
	//		String id=request.getParameter("id");
	//		TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//		bean1.setId(id);
	//		String msg="1";
	//		try {
	//			msg=consultMsgManager.deleteDataById(bean1);
	//		} catch (Exception e) {
	//			msg=e.getMessage();
	//		}
	//		request.setAttribute("msg",msg);
	//		return SUCCESS;
	//	}
	
	
	
	/**
	 * BEAN类  咨询信息设定
	 * @param bean BEAN类  咨询信息
	 */
	//	/**
	//	 * <p>
	//	 * 编辑。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>编辑。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String edit() {
	//		log.info("C301Action edit.........");
	//		String id=request.getParameter("id");
	//		if(id!=null){
	//			TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//			bean1.setId(id);
	//			bean=consultMsgManager.findDataById(bean1);
	//		}else{
	//			SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
	//			request.setAttribute("date", sdf.format(new Date()));
	//		}
	//		return "edit";
	//	}
	//	/**
	//	 * <p>
	//	 * 信息保存
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>新增。</div>
	//	 * <div>修改。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String save() {
	//		log.info("C301Action edit.........");
	//		if(bean!=null){
	//			String msg="1";
	//			try {
	//					BaseUserBean user = (BaseUserBean) request.getSession().getAttribute(CommonConstant.SESSION_KEY_USER_CUSTOMER_SERVICE);
	//					if(user!=null){
	//						bean.setCreate_ip(getIpAddr());
	//						bean.setCreate_id(user.getUser_id());
	//						bean.setUpdate_ip(getIpAddr());
	//						bean.setUpdate_id(user.getUser_id());
	//					}
	//					msg=consultMsgManager.saveOrUpdateData(bean);
	//			} catch (Exception e) {
	//				msg=e.getMessage();
	//			}
	//			request.setAttribute("msg",msg);
	//		}else{
	//			request.setAttribute("msg", "信息保存失败!");
	//		}
	//		return SUCCESS;
	//	}
	//	/**
	//	 * <p>
	//	 * 删除。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>删除。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String del() {
	//		log.info("C301Action del.........");
	//		
	//		String id=request.getParameter("id");
	//		TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//		bean1.setId(id);
	//		String msg="1";
	//		try {
	//			msg=consultMsgManager.deleteDataById(bean1);
	//		} catch (Exception e) {
	//			msg=e.getMessage();
	//		}
	//		request.setAttribute("msg",msg);
	//		return SUCCESS;
	//	}
	
	
	
	/**
	 * BEAN类  咨询信息取得
	 * @return BEAN类  咨询信息
	 */
	//	/**
	//	 * <p>
	//	 * 编辑。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>编辑。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String edit() {
	//		log.info("C301Action edit.........");
	//		String id=request.getParameter("id");
	//		if(id!=null){
	//			TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//			bean1.setId(id);
	//			bean=consultMsgManager.findDataById(bean1);
	//		}else{
	//			SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
	//			request.setAttribute("date", sdf.format(new Date()));
	//		}
	//		return "edit";
	//	}
	//	/**
	//	 * <p>
	//	 * 信息保存
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>新增。</div>
	//	 * <div>修改。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String save() {
	//		log.info("C301Action edit.........");
	//		if(bean!=null){
	//			String msg="1";
	//			try {
	//					BaseUserBean user = (BaseUserBean) request.getSession().getAttribute(CommonConstant.SESSION_KEY_USER_CUSTOMER_SERVICE);
	//					if(user!=null){
	//						bean.setCreate_ip(getIpAddr());
	//						bean.setCreate_id(user.getUser_id());
	//						bean.setUpdate_ip(getIpAddr());
	//						bean.setUpdate_id(user.getUser_id());
	//					}
	//					msg=consultMsgManager.saveOrUpdateData(bean);
	//			} catch (Exception e) {
	//				msg=e.getMessage();
	//			}
	//			request.setAttribute("msg",msg);
	//		}else{
	//			request.setAttribute("msg", "信息保存失败!");
	//		}
	//		return SUCCESS;
	//	}
	//	/**
	//	 * <p>
	//	 * 删除。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>删除。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String del() {
	//		log.info("C301Action del.........");
	//		
	//		String id=request.getParameter("id");
	//		TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//		bean1.setId(id);
	//		String msg="1";
	//		try {
	//			msg=consultMsgManager.deleteDataById(bean1);
	//		} catch (Exception e) {
	//			msg=e.getMessage();
	//		}
	//		request.setAttribute("msg",msg);
	//		return SUCCESS;
	//	}
	
	
	
	/**
	 * BEAN类  咨询信息取得
	 * @return BEAN类  咨询信息
	 */
	//	/**
	//	 * <p>
	//	 * 编辑。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>编辑。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String edit() {
	//		log.info("C301Action edit.........");
	//		String id=request.getParameter("id");
	//		if(id!=null){
	//			TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//			bean1.setId(id);
	//			bean=consultMsgManager.findDataById(bean1);
	//		}else{
	//			SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
	//			request.setAttribute("date", sdf.format(new Date()));
	//		}
	//		return "edit";
	//	}
	//	/**
	//	 * <p>
	//	 * 信息保存
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>新增。</div>
	//	 * <div>修改。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String save() {
	//		log.info("C301Action edit.........");
	//		if(bean!=null){
	//			String msg="1";
	//			try {
	//					BaseUserBean user = (BaseUserBean) request.getSession().getAttribute(CommonConstant.SESSION_KEY_USER_CUSTOMER_SERVICE);
	//					if(user!=null){
	//						bean.setCreate_ip(getIpAddr());
	//						bean.setCreate_id(user.getUser_id());
	//						bean.setUpdate_ip(getIpAddr());
	//						bean.setUpdate_id(user.getUser_id());
	//					}
	//					msg=consultMsgManager.saveOrUpdateData(bean);
	//			} catch (Exception e) {
	//				msg=e.getMessage();
	//			}
	//			request.setAttribute("msg",msg);
	//		}else{
	//			request.setAttribute("msg", "信息保存失败!");
	//		}
	//		return SUCCESS;
	//	}
	//	/**
	//	 * <p>
	//	 * 删除。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>删除。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String del() {
	//		log.info("C301Action del.........");
	//		
	//		String id=request.getParameter("id");
	//		TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//		bean1.setId(id);
	//		String msg="1";
	//		try {
	//			msg=consultMsgManager.deleteDataById(bean1);
	//		} catch (Exception e) {
	//			msg=e.getMessage();
	//		}
	//		request.setAttribute("msg",msg);
	//		return SUCCESS;
	//	}
	
	
	
	/**
	 * BEAN类  咨询信息设定
	 * @param bean BEAN类  咨询信息
	 */
	//	/**
	//	 * <p>
	//	 * 编辑。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>编辑。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String edit() {
	//		log.info("C301Action edit.........");
	//		String id=request.getParameter("id");
	//		if(id!=null){
	//			TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//			bean1.setId(id);
	//			bean=consultMsgManager.findDataById(bean1);
	//		}else{
	//			SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
	//			request.setAttribute("date", sdf.format(new Date()));
	//		}
	//		return "edit";
	//	}
	//	/**
	//	 * <p>
	//	 * 信息保存
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>新增。</div>
	//	 * <div>修改。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String save() {
	//		log.info("C301Action edit.........");
	//		if(bean!=null){
	//			String msg="1";
	//			try {
	//					BaseUserBean user = (BaseUserBean) request.getSession().getAttribute(CommonConstant.SESSION_KEY_USER_CUSTOMER_SERVICE);
	//					if(user!=null){
	//						bean.setCreate_ip(getIpAddr());
	//						bean.setCreate_id(user.getUser_id());
	//						bean.setUpdate_ip(getIpAddr());
	//						bean.setUpdate_id(user.getUser_id());
	//					}
	//					msg=consultMsgManager.saveOrUpdateData(bean);
	//			} catch (Exception e) {
	//				msg=e.getMessage();
	//			}
	//			request.setAttribute("msg",msg);
	//		}else{
	//			request.setAttribute("msg", "信息保存失败!");
	//		}
	//		return SUCCESS;
	//	}
	//	/**
	//	 * <p>
	//	 * 删除。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>删除。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String del() {
	//		log.info("C301Action del.........");
	//		
	//		String id=request.getParameter("id");
	//		TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//		bean1.setId(id);
	//		String msg="1";
	//		try {
	//			msg=consultMsgManager.deleteDataById(bean1);
	//		} catch (Exception e) {
	//			msg=e.getMessage();
	//		}
	//		request.setAttribute("msg",msg);
	//		return SUCCESS;
	//	}
	
	
	
	/**
	 * BEAN类  咨询信息取得
	 * @return BEAN类  咨询信息
	 */
	//	/**
	//	 * <p>
	//	 * 编辑。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>编辑。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String edit() {
	//		log.info("C301Action edit.........");
	//		String id=request.getParameter("id");
	//		if(id!=null){
	//			TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//			bean1.setId(id);
	//			bean=consultMsgManager.findDataById(bean1);
	//		}else{
	//			SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
	//			request.setAttribute("date", sdf.format(new Date()));
	//		}
	//		return "edit";
	//	}
	//	/**
	//	 * <p>
	//	 * 信息保存
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>新增。</div>
	//	 * <div>修改。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String save() {
	//		log.info("C301Action edit.........");
	//		if(bean!=null){
	//			String msg="1";
	//			try {
	//					BaseUserBean user = (BaseUserBean) request.getSession().getAttribute(CommonConstant.SESSION_KEY_USER_CUSTOMER_SERVICE);
	//					if(user!=null){
	//						bean.setCreate_ip(getIpAddr());
	//						bean.setCreate_id(user.getUser_id());
	//						bean.setUpdate_ip(getIpAddr());
	//						bean.setUpdate_id(user.getUser_id());
	//					}
	//					msg=consultMsgManager.saveOrUpdateData(bean);
	//			} catch (Exception e) {
	//				msg=e.getMessage();
	//			}
	//			request.setAttribute("msg",msg);
	//		}else{
	//			request.setAttribute("msg", "信息保存失败!");
	//		}
	//		return SUCCESS;
	//	}
	//	/**
	//	 * <p>
	//	 * 删除。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>删除。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String del() {
	//		log.info("C301Action del.........");
	//		
	//		String id=request.getParameter("id");
	//		TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//		bean1.setId(id);
	//		String msg="1";
	//		try {
	//			msg=consultMsgManager.deleteDataById(bean1);
	//		} catch (Exception e) {
	//			msg=e.getMessage();
	//		}
	//		request.setAttribute("msg",msg);
	//		return SUCCESS;
	//	}
	
	
	
	/**
	 * BEAN类  咨询信息取得
	 * @return BEAN类  咨询信息
	 */
	//	/**
	//	 * <p>
	//	 * 编辑。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>编辑。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String edit() {
	//		log.info("C301Action edit.........");
	//		String id=request.getParameter("id");
	//		if(id!=null){
	//			TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//			bean1.setId(id);
	//			bean=consultMsgManager.findDataById(bean1);
	//		}else{
	//			SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
	//			request.setAttribute("date", sdf.format(new Date()));
	//		}
	//		return "edit";
	//	}
	//	/**
	//	 * <p>
	//	 * 信息保存
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>新增。</div>
	//	 * <div>修改。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String save() {
	//		log.info("C301Action edit.........");
	//		if(bean!=null){
	//			String msg="1";
	//			try {
	//					BaseUserBean user = (BaseUserBean) request.getSession().getAttribute(CommonConstant.SESSION_KEY_USER_CUSTOMER_SERVICE);
	//					if(user!=null){
	//						bean.setCreate_ip(getIpAddr());
	//						bean.setCreate_id(user.getUser_id());
	//						bean.setUpdate_ip(getIpAddr());
	//						bean.setUpdate_id(user.getUser_id());
	//					}
	//					msg=consultMsgManager.saveOrUpdateData(bean);
	//			} catch (Exception e) {
	//				msg=e.getMessage();
	//			}
	//			request.setAttribute("msg",msg);
	//		}else{
	//			request.setAttribute("msg", "信息保存失败!");
	//		}
	//		return SUCCESS;
	//	}
	//	/**
	//	 * <p>
	//	 * 删除。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>删除。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String del() {
	//		log.info("C301Action del.........");
	//		
	//		String id=request.getParameter("id");
	//		TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//		bean1.setId(id);
	//		String msg="1";
	//		try {
	//			msg=consultMsgManager.deleteDataById(bean1);
	//		} catch (Exception e) {
	//			msg=e.getMessage();
	//		}
	//		request.setAttribute("msg",msg);
	//		return SUCCESS;
	//	}
	
	
	
	/**
	 * BEAN类  咨询信息设定
	 * @param bean BEAN类  咨询信息
	 */
	//	/**
	//	 * <p>
	//	 * 编辑。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>编辑。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String edit() {
	//		log.info("C301Action edit.........");
	//		String id=request.getParameter("id");
	//		if(id!=null){
	//			TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//			bean1.setId(id);
	//			bean=consultMsgManager.findDataById(bean1);
	//		}else{
	//			SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
	//			request.setAttribute("date", sdf.format(new Date()));
	//		}
	//		return "edit";
	//	}
	//	/**
	//	 * <p>
	//	 * 信息保存
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>新增。</div>
	//	 * <div>修改。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String save() {
	//		log.info("C301Action edit.........");
	//		if(bean!=null){
	//			String msg="1";
	//			try {
	//					BaseUserBean user = (BaseUserBean) request.getSession().getAttribute(CommonConstant.SESSION_KEY_USER_CUSTOMER_SERVICE);
	//					if(user!=null){
	//						bean.setCreate_ip(getIpAddr());
	//						bean.setCreate_id(user.getUser_id());
	//						bean.setUpdate_ip(getIpAddr());
	//						bean.setUpdate_id(user.getUser_id());
	//					}
	//					msg=consultMsgManager.saveOrUpdateData(bean);
	//			} catch (Exception e) {
	//				msg=e.getMessage();
	//			}
	//			request.setAttribute("msg",msg);
	//		}else{
	//			request.setAttribute("msg", "信息保存失败!");
	//		}
	//		return SUCCESS;
	//	}
	//	/**
	//	 * <p>
	//	 * 删除。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>删除。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String del() {
	//		log.info("C301Action del.........");
	//		
	//		String id=request.getParameter("id");
	//		TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//		bean1.setId(id);
	//		String msg="1";
	//		try {
	//			msg=consultMsgManager.deleteDataById(bean1);
	//		} catch (Exception e) {
	//			msg=e.getMessage();
	//		}
	//		request.setAttribute("msg",msg);
	//		return SUCCESS;
	//	}
	
	
	
	/**
	 * BEAN类  咨询信息取得
	 * @return BEAN类  咨询信息
	 */
	//	/**
	//	 * <p>
	//	 * 编辑。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>编辑。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String edit() {
	//		log.info("C301Action edit.........");
	//		String id=request.getParameter("id");
	//		if(id!=null){
	//			TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//			bean1.setId(id);
	//			bean=consultMsgManager.findDataById(bean1);
	//		}else{
	//			SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
	//			request.setAttribute("date", sdf.format(new Date()));
	//		}
	//		return "edit";
	//	}
	//	/**
	//	 * <p>
	//	 * 信息保存
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>新增。</div>
	//	 * <div>修改。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String save() {
	//		log.info("C301Action edit.........");
	//		if(bean!=null){
	//			String msg="1";
	//			try {
	//					BaseUserBean user = (BaseUserBean) request.getSession().getAttribute(CommonConstant.SESSION_KEY_USER_CUSTOMER_SERVICE);
	//					if(user!=null){
	//						bean.setCreate_ip(getIpAddr());
	//						bean.setCreate_id(user.getUser_id());
	//						bean.setUpdate_ip(getIpAddr());
	//						bean.setUpdate_id(user.getUser_id());
	//					}
	//					msg=consultMsgManager.saveOrUpdateData(bean);
	//			} catch (Exception e) {
	//				msg=e.getMessage();
	//			}
	//			request.setAttribute("msg",msg);
	//		}else{
	//			request.setAttribute("msg", "信息保存失败!");
	//		}
	//		return SUCCESS;
	//	}
	//	/**
	//	 * <p>
	//	 * 删除。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>删除。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String del() {
	//		log.info("C301Action del.........");
	//		
	//		String id=request.getParameter("id");
	//		TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//		bean1.setId(id);
	//		String msg="1";
	//		try {
	//			msg=consultMsgManager.deleteDataById(bean1);
	//		} catch (Exception e) {
	//			msg=e.getMessage();
	//		}
	//		request.setAttribute("msg",msg);
	//		return SUCCESS;
	//	}
	
	
	
	/**
	 * BEAN类  咨询信息设定
	 * @param bean BEAN类  咨询信息
	 */
	//	/**
	//	 * <p>
	//	 * 编辑。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>编辑。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String edit() {
	//		log.info("C301Action edit.........");
	//		String id=request.getParameter("id");
	//		if(id!=null){
	//			TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//			bean1.setId(id);
	//			bean=consultMsgManager.findDataById(bean1);
	//		}else{
	//			SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
	//			request.setAttribute("date", sdf.format(new Date()));
	//		}
	//		return "edit";
	//	}
	//	/**
	//	 * <p>
	//	 * 信息保存
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>新增。</div>
	//	 * <div>修改。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String save() {
	//		log.info("C301Action edit.........");
	//		if(bean!=null){
	//			String msg="1";
	//			try {
	//					BaseUserBean user = (BaseUserBean) request.getSession().getAttribute(CommonConstant.SESSION_KEY_USER_CUSTOMER_SERVICE);
	//					if(user!=null){
	//						bean.setCreate_ip(getIpAddr());
	//						bean.setCreate_id(user.getUser_id());
	//						bean.setUpdate_ip(getIpAddr());
	//						bean.setUpdate_id(user.getUser_id());
	//					}
	//					msg=consultMsgManager.saveOrUpdateData(bean);
	//			} catch (Exception e) {
	//				msg=e.getMessage();
	//			}
	//			request.setAttribute("msg",msg);
	//		}else{
	//			request.setAttribute("msg", "信息保存失败!");
	//		}
	//		return SUCCESS;
	//	}
	//	/**
	//	 * <p>
	//	 * 删除。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>删除。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String del() {
	//		log.info("C301Action del.........");
	//		
	//		String id=request.getParameter("id");
	//		TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//		bean1.setId(id);
	//		String msg="1";
	//		try {
	//			msg=consultMsgManager.deleteDataById(bean1);
	//		} catch (Exception e) {
	//			msg=e.getMessage();
	//		}
	//		request.setAttribute("msg",msg);
	//		return SUCCESS;
	//	}
	
	
	
	/**
	 * BEAN类  咨询信息取得
	 * @return BEAN类  咨询信息
	 */
	//	/**
	//	 * <p>
	//	 * 编辑。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>编辑。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String edit() {
	//		log.info("C301Action edit.........");
	//		String id=request.getParameter("id");
	//		if(id!=null){
	//			TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//			bean1.setId(id);
	//			bean=consultMsgManager.findDataById(bean1);
	//		}else{
	//			SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
	//			request.setAttribute("date", sdf.format(new Date()));
	//		}
	//		return "edit";
	//	}
	//	/**
	//	 * <p>
	//	 * 信息保存
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>新增。</div>
	//	 * <div>修改。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String save() {
	//		log.info("C301Action edit.........");
	//		if(bean!=null){
	//			String msg="1";
	//			try {
	//					BaseUserBean user = (BaseUserBean) request.getSession().getAttribute(CommonConstant.SESSION_KEY_USER_CUSTOMER_SERVICE);
	//					if(user!=null){
	//						bean.setCreate_ip(getIpAddr());
	//						bean.setCreate_id(user.getUser_id());
	//						bean.setUpdate_ip(getIpAddr());
	//						bean.setUpdate_id(user.getUser_id());
	//					}
	//					msg=consultMsgManager.saveOrUpdateData(bean);
	//			} catch (Exception e) {
	//				msg=e.getMessage();
	//			}
	//			request.setAttribute("msg",msg);
	//		}else{
	//			request.setAttribute("msg", "信息保存失败!");
	//		}
	//		return SUCCESS;
	//	}
	//	/**
	//	 * <p>
	//	 * 删除。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>删除。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String del() {
	//		log.info("C301Action del.........");
	//		
	//		String id=request.getParameter("id");
	//		TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//		bean1.setId(id);
	//		String msg="1";
	//		try {
	//			msg=consultMsgManager.deleteDataById(bean1);
	//		} catch (Exception e) {
	//			msg=e.getMessage();
	//		}
	//		request.setAttribute("msg",msg);
	//		return SUCCESS;
	//	}
	
	
	
	/**
	 * BEAN类  咨询信息取得
	 * @return BEAN类  咨询信息
	 */
	//	/**
	//	 * <p>
	//	 * 编辑。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>编辑。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String edit() {
	//		log.info("C301Action edit.........");
	//		String id=request.getParameter("id");
	//		if(id!=null){
	//			TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//			bean1.setId(id);
	//			bean=consultMsgManager.findDataById(bean1);
	//		}else{
	//			SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
	//			request.setAttribute("date", sdf.format(new Date()));
	//		}
	//		return "edit";
	//	}
	//	/**
	//	 * <p>
	//	 * 信息保存
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>新增。</div>
	//	 * <div>修改。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String save() {
	//		log.info("C301Action edit.........");
	//		if(bean!=null){
	//			String msg="1";
	//			try {
	//					BaseUserBean user = (BaseUserBean) request.getSession().getAttribute(CommonConstant.SESSION_KEY_USER_CUSTOMER_SERVICE);
	//					if(user!=null){
	//						bean.setCreate_ip(getIpAddr());
	//						bean.setCreate_id(user.getUser_id());
	//						bean.setUpdate_ip(getIpAddr());
	//						bean.setUpdate_id(user.getUser_id());
	//					}
	//					msg=consultMsgManager.saveOrUpdateData(bean);
	//			} catch (Exception e) {
	//				msg=e.getMessage();
	//			}
	//			request.setAttribute("msg",msg);
	//		}else{
	//			request.setAttribute("msg", "信息保存失败!");
	//		}
	//		return SUCCESS;
	//	}
	//	/**
	//	 * <p>
	//	 * 删除。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>删除。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String del() {
	//		log.info("C301Action del.........");
	//		
	//		String id=request.getParameter("id");
	//		TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//		bean1.setId(id);
	//		String msg="1";
	//		try {
	//			msg=consultMsgManager.deleteDataById(bean1);
	//		} catch (Exception e) {
	//			msg=e.getMessage();
	//		}
	//		request.setAttribute("msg",msg);
	//		return SUCCESS;
	//	}
	
	
	
	/**
	 * BEAN类  咨询信息设定
	 * @param bean BEAN类  咨询信息
	 */
	//	/**
	//	 * <p>
	//	 * 编辑。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>编辑。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String edit() {
	//		log.info("C301Action edit.........");
	//		String id=request.getParameter("id");
	//		if(id!=null){
	//			TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//			bean1.setId(id);
	//			bean=consultMsgManager.findDataById(bean1);
	//		}else{
	//			SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
	//			request.setAttribute("date", sdf.format(new Date()));
	//		}
	//		return "edit";
	//	}
	//	/**
	//	 * <p>
	//	 * 信息保存
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>新增。</div>
	//	 * <div>修改。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String save() {
	//		log.info("C301Action edit.........");
	//		if(bean!=null){
	//			String msg="1";
	//			try {
	//					BaseUserBean user = (BaseUserBean) request.getSession().getAttribute(CommonConstant.SESSION_KEY_USER_CUSTOMER_SERVICE);
	//					if(user!=null){
	//						bean.setCreate_ip(getIpAddr());
	//						bean.setCreate_id(user.getUser_id());
	//						bean.setUpdate_ip(getIpAddr());
	//						bean.setUpdate_id(user.getUser_id());
	//					}
	//					msg=consultMsgManager.saveOrUpdateData(bean);
	//			} catch (Exception e) {
	//				msg=e.getMessage();
	//			}
	//			request.setAttribute("msg",msg);
	//		}else{
	//			request.setAttribute("msg", "信息保存失败!");
	//		}
	//		return SUCCESS;
	//	}
	//	/**
	//	 * <p>
	//	 * 删除。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>删除。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String del() {
	//		log.info("C301Action del.........");
	//		
	//		String id=request.getParameter("id");
	//		TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//		bean1.setId(id);
	//		String msg="1";
	//		try {
	//			msg=consultMsgManager.deleteDataById(bean1);
	//		} catch (Exception e) {
	//			msg=e.getMessage();
	//		}
	//		request.setAttribute("msg",msg);
	//		return SUCCESS;
	//	}
	
	
	
	/**
	 * BEAN类  咨询信息取得
	 * @return BEAN类  咨询信息
	 */
	//	/**
	//	 * <p>
	//	 * 编辑。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>编辑。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String edit() {
	//		log.info("C301Action edit.........");
	//		String id=request.getParameter("id");
	//		if(id!=null){
	//			TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//			bean1.setId(id);
	//			bean=consultMsgManager.findDataById(bean1);
	//		}else{
	//			SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
	//			request.setAttribute("date", sdf.format(new Date()));
	//		}
	//		return "edit";
	//	}
	//	/**
	//	 * <p>
	//	 * 信息保存
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>新增。</div>
	//	 * <div>修改。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String save() {
	//		log.info("C301Action edit.........");
	//		if(bean!=null){
	//			String msg="1";
	//			try {
	//					BaseUserBean user = (BaseUserBean) request.getSession().getAttribute(CommonConstant.SESSION_KEY_USER_CUSTOMER_SERVICE);
	//					if(user!=null){
	//						bean.setCreate_ip(getIpAddr());
	//						bean.setCreate_id(user.getUser_id());
	//						bean.setUpdate_ip(getIpAddr());
	//						bean.setUpdate_id(user.getUser_id());
	//					}
	//					msg=consultMsgManager.saveOrUpdateData(bean);
	//			} catch (Exception e) {
	//				msg=e.getMessage();
	//			}
	//			request.setAttribute("msg",msg);
	//		}else{
	//			request.setAttribute("msg", "信息保存失败!");
	//		}
	//		return SUCCESS;
	//	}
	//	/**
	//	 * <p>
	//	 * 删除。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>删除。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String del() {
	//		log.info("C301Action del.........");
	//		
	//		String id=request.getParameter("id");
	//		TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//		bean1.setId(id);
	//		String msg="1";
	//		try {
	//			msg=consultMsgManager.deleteDataById(bean1);
	//		} catch (Exception e) {
	//			msg=e.getMessage();
	//		}
	//		request.setAttribute("msg",msg);
	//		return SUCCESS;
	//	}
	
	
	
	/**
	 * BEAN类  咨询信息设定
	 * @param bean BEAN类  咨询信息
	 */
	//	/**
	//	 * <p>
	//	 * 编辑。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>编辑。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String edit() {
	//		log.info("C301Action edit.........");
	//		String id=request.getParameter("id");
	//		if(id!=null){
	//			TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//			bean1.setId(id);
	//			bean=consultMsgManager.findDataById(bean1);
	//		}else{
	//			SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
	//			request.setAttribute("date", sdf.format(new Date()));
	//		}
	//		return "edit";
	//	}
	//	/**
	//	 * <p>
	//	 * 信息保存
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>新增。</div>
	//	 * <div>修改。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String save() {
	//		log.info("C301Action edit.........");
	//		if(bean!=null){
	//			String msg="1";
	//			try {
	//					BaseUserBean user = (BaseUserBean) request.getSession().getAttribute(CommonConstant.SESSION_KEY_USER_CUSTOMER_SERVICE);
	//					if(user!=null){
	//						bean.setCreate_ip(getIpAddr());
	//						bean.setCreate_id(user.getUser_id());
	//						bean.setUpdate_ip(getIpAddr());
	//						bean.setUpdate_id(user.getUser_id());
	//					}
	//					msg=consultMsgManager.saveOrUpdateData(bean);
	//			} catch (Exception e) {
	//				msg=e.getMessage();
	//			}
	//			request.setAttribute("msg",msg);
	//		}else{
	//			request.setAttribute("msg", "信息保存失败!");
	//		}
	//		return SUCCESS;
	//	}
	//	/**
	//	 * <p>
	//	 * 删除。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>删除。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String del() {
	//		log.info("C301Action del.........");
	//		
	//		String id=request.getParameter("id");
	//		TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//		bean1.setId(id);
	//		String msg="1";
	//		try {
	//			msg=consultMsgManager.deleteDataById(bean1);
	//		} catch (Exception e) {
	//			msg=e.getMessage();
	//		}
	//		request.setAttribute("msg",msg);
	//		return SUCCESS;
	//	}
	
	
	
	/**
	 * BEAN类  咨询信息取得
	 * @return BEAN类  咨询信息
	 */
	//	/**
	//	 * <p>
	//	 * 编辑。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>编辑。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String edit() {
	//		log.info("C301Action edit.........");
	//		String id=request.getParameter("id");
	//		if(id!=null){
	//			TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//			bean1.setId(id);
	//			bean=consultMsgManager.findDataById(bean1);
	//		}else{
	//			SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
	//			request.setAttribute("date", sdf.format(new Date()));
	//		}
	//		return "edit";
	//	}
	//	/**
	//	 * <p>
	//	 * 信息保存
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>新增。</div>
	//	 * <div>修改。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String save() {
	//		log.info("C301Action edit.........");
	//		if(bean!=null){
	//			String msg="1";
	//			try {
	//					BaseUserBean user = (BaseUserBean) request.getSession().getAttribute(CommonConstant.SESSION_KEY_USER_CUSTOMER_SERVICE);
	//					if(user!=null){
	//						bean.setCreate_ip(getIpAddr());
	//						bean.setCreate_id(user.getUser_id());
	//						bean.setUpdate_ip(getIpAddr());
	//						bean.setUpdate_id(user.getUser_id());
	//					}
	//					msg=consultMsgManager.saveOrUpdateData(bean);
	//			} catch (Exception e) {
	//				msg=e.getMessage();
	//			}
	//			request.setAttribute("msg",msg);
	//		}else{
	//			request.setAttribute("msg", "信息保存失败!");
	//		}
	//		return SUCCESS;
	//	}
	//	/**
	//	 * <p>
	//	 * 删除。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>删除。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String del() {
	//		log.info("C301Action del.........");
	//		
	//		String id=request.getParameter("id");
	//		TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//		bean1.setId(id);
	//		String msg="1";
	//		try {
	//			msg=consultMsgManager.deleteDataById(bean1);
	//		} catch (Exception e) {
	//			msg=e.getMessage();
	//		}
	//		request.setAttribute("msg",msg);
	//		return SUCCESS;
	//	}
	
	
	
	/**
	 * BEAN类  咨询信息取得
	 * @return BEAN类  咨询信息
	 */
	//	/**
	//	 * <p>
	//	 * 编辑。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>编辑。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String edit() {
	//		log.info("C301Action edit.........");
	//		String id=request.getParameter("id");
	//		if(id!=null){
	//			TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//			bean1.setId(id);
	//			bean=consultMsgManager.findDataById(bean1);
	//		}else{
	//			SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
	//			request.setAttribute("date", sdf.format(new Date()));
	//		}
	//		return "edit";
	//	}
	//	/**
	//	 * <p>
	//	 * 信息保存
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>新增。</div>
	//	 * <div>修改。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String save() {
	//		log.info("C301Action edit.........");
	//		if(bean!=null){
	//			String msg="1";
	//			try {
	//					BaseUserBean user = (BaseUserBean) request.getSession().getAttribute(CommonConstant.SESSION_KEY_USER_CUSTOMER_SERVICE);
	//					if(user!=null){
	//						bean.setCreate_ip(getIpAddr());
	//						bean.setCreate_id(user.getUser_id());
	//						bean.setUpdate_ip(getIpAddr());
	//						bean.setUpdate_id(user.getUser_id());
	//					}
	//					msg=consultMsgManager.saveOrUpdateData(bean);
	//			} catch (Exception e) {
	//				msg=e.getMessage();
	//			}
	//			request.setAttribute("msg",msg);
	//		}else{
	//			request.setAttribute("msg", "信息保存失败!");
	//		}
	//		return SUCCESS;
	//	}
	//	/**
	//	 * <p>
	//	 * 删除。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>删除。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String del() {
	//		log.info("C301Action del.........");
	//		
	//		String id=request.getParameter("id");
	//		TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//		bean1.setId(id);
	//		String msg="1";
	//		try {
	//			msg=consultMsgManager.deleteDataById(bean1);
	//		} catch (Exception e) {
	//			msg=e.getMessage();
	//		}
	//		request.setAttribute("msg",msg);
	//		return SUCCESS;
	//	}
	
	
	
	/**
	 * BEAN类  咨询信息设定
	 * @param bean BEAN类  咨询信息
	 */
	//	/**
	//	 * <p>
	//	 * 编辑。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>编辑。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String edit() {
	//		log.info("C301Action edit.........");
	//		String id=request.getParameter("id");
	//		if(id!=null){
	//			TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//			bean1.setId(id);
	//			bean=consultMsgManager.findDataById(bean1);
	//		}else{
	//			SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
	//			request.setAttribute("date", sdf.format(new Date()));
	//		}
	//		return "edit";
	//	}
	//	/**
	//	 * <p>
	//	 * 信息保存
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>新增。</div>
	//	 * <div>修改。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String save() {
	//		log.info("C301Action edit.........");
	//		if(bean!=null){
	//			String msg="1";
	//			try {
	//					BaseUserBean user = (BaseUserBean) request.getSession().getAttribute(CommonConstant.SESSION_KEY_USER_CUSTOMER_SERVICE);
	//					if(user!=null){
	//						bean.setCreate_ip(getIpAddr());
	//						bean.setCreate_id(user.getUser_id());
	//						bean.setUpdate_ip(getIpAddr());
	//						bean.setUpdate_id(user.getUser_id());
	//					}
	//					msg=consultMsgManager.saveOrUpdateData(bean);
	//			} catch (Exception e) {
	//				msg=e.getMessage();
	//			}
	//			request.setAttribute("msg",msg);
	//		}else{
	//			request.setAttribute("msg", "信息保存失败!");
	//		}
	//		return SUCCESS;
	//	}
	//	/**
	//	 * <p>
	//	 * 删除。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>删除。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String del() {
	//		log.info("C301Action del.........");
	//		
	//		String id=request.getParameter("id");
	//		TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//		bean1.setId(id);
	//		String msg="1";
	//		try {
	//			msg=consultMsgManager.deleteDataById(bean1);
	//		} catch (Exception e) {
	//			msg=e.getMessage();
	//		}
	//		request.setAttribute("msg",msg);
	//		return SUCCESS;
	//	}
	
	
	
	/**
	 * BEAN类  咨询信息取得
	 * @return BEAN类  咨询信息
	 */
	//	/**
	//	 * <p>
	//	 * 编辑。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>编辑。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String edit() {
	//		log.info("C301Action edit.........");
	//		String id=request.getParameter("id");
	//		if(id!=null){
	//			TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//			bean1.setId(id);
	//			bean=consultMsgManager.findDataById(bean1);
	//		}else{
	//			SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
	//			request.setAttribute("date", sdf.format(new Date()));
	//		}
	//		return "edit";
	//	}
	//	/**
	//	 * <p>
	//	 * 信息保存
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>新增。</div>
	//	 * <div>修改。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String save() {
	//		log.info("C301Action edit.........");
	//		if(bean!=null){
	//			String msg="1";
	//			try {
	//					BaseUserBean user = (BaseUserBean) request.getSession().getAttribute(CommonConstant.SESSION_KEY_USER_CUSTOMER_SERVICE);
	//					if(user!=null){
	//						bean.setCreate_ip(getIpAddr());
	//						bean.setCreate_id(user.getUser_id());
	//						bean.setUpdate_ip(getIpAddr());
	//						bean.setUpdate_id(user.getUser_id());
	//					}
	//					msg=consultMsgManager.saveOrUpdateData(bean);
	//			} catch (Exception e) {
	//				msg=e.getMessage();
	//			}
	//			request.setAttribute("msg",msg);
	//		}else{
	//			request.setAttribute("msg", "信息保存失败!");
	//		}
	//		return SUCCESS;
	//	}
	//	/**
	//	 * <p>
	//	 * 删除。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>删除。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String del() {
	//		log.info("C301Action del.........");
	//		
	//		String id=request.getParameter("id");
	//		TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//		bean1.setId(id);
	//		String msg="1";
	//		try {
	//			msg=consultMsgManager.deleteDataById(bean1);
	//		} catch (Exception e) {
	//			msg=e.getMessage();
	//		}
	//		request.setAttribute("msg",msg);
	//		return SUCCESS;
	//	}
	
	
	
	/**
	 * BEAN类  咨询信息取得
	 * @return BEAN类  咨询信息
	 */
	//	/**
	//	 * <p>
	//	 * 编辑。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>编辑。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String edit() {
	//		log.info("C301Action edit.........");
	//		String id=request.getParameter("id");
	//		if(id!=null){
	//			TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//			bean1.setId(id);
	//			bean=consultMsgManager.findDataById(bean1);
	//		}else{
	//			SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
	//			request.setAttribute("date", sdf.format(new Date()));
	//		}
	//		return "edit";
	//	}
	//	/**
	//	 * <p>
	//	 * 信息保存
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>新增。</div>
	//	 * <div>修改。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String save() {
	//		log.info("C301Action edit.........");
	//		if(bean!=null){
	//			String msg="1";
	//			try {
	//					BaseUserBean user = (BaseUserBean) request.getSession().getAttribute(CommonConstant.SESSION_KEY_USER_CUSTOMER_SERVICE);
	//					if(user!=null){
	//						bean.setCreate_ip(getIpAddr());
	//						bean.setCreate_id(user.getUser_id());
	//						bean.setUpdate_ip(getIpAddr());
	//						bean.setUpdate_id(user.getUser_id());
	//					}
	//					msg=consultMsgManager.saveOrUpdateData(bean);
	//			} catch (Exception e) {
	//				msg=e.getMessage();
	//			}
	//			request.setAttribute("msg",msg);
	//		}else{
	//			request.setAttribute("msg", "信息保存失败!");
	//		}
	//		return SUCCESS;
	//	}
	//	/**
	//	 * <p>
	//	 * 删除。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>删除。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String del() {
	//		log.info("C301Action del.........");
	//		
	//		String id=request.getParameter("id");
	//		TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//		bean1.setId(id);
	//		String msg="1";
	//		try {
	//			msg=consultMsgManager.deleteDataById(bean1);
	//		} catch (Exception e) {
	//			msg=e.getMessage();
	//		}
	//		request.setAttribute("msg",msg);
	//		return SUCCESS;
	//	}
	
	
	
	/**
	 * BEAN类  咨询信息设定
	 * @param bean BEAN类  咨询信息
	 */
	//	/**
	//	 * <p>
	//	 * 编辑。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>编辑。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String edit() {
	//		log.info("C301Action edit.........");
	//		String id=request.getParameter("id");
	//		if(id!=null){
	//			TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//			bean1.setId(id);
	//			bean=consultMsgManager.findDataById(bean1);
	//		}else{
	//			SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
	//			request.setAttribute("date", sdf.format(new Date()));
	//		}
	//		return "edit";
	//	}
	//	/**
	//	 * <p>
	//	 * 信息保存
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>新增。</div>
	//	 * <div>修改。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String save() {
	//		log.info("C301Action edit.........");
	//		if(bean!=null){
	//			String msg="1";
	//			try {
	//					BaseUserBean user = (BaseUserBean) request.getSession().getAttribute(CommonConstant.SESSION_KEY_USER_CUSTOMER_SERVICE);
	//					if(user!=null){
	//						bean.setCreate_ip(getIpAddr());
	//						bean.setCreate_id(user.getUser_id());
	//						bean.setUpdate_ip(getIpAddr());
	//						bean.setUpdate_id(user.getUser_id());
	//					}
	//					msg=consultMsgManager.saveOrUpdateData(bean);
	//			} catch (Exception e) {
	//				msg=e.getMessage();
	//			}
	//			request.setAttribute("msg",msg);
	//		}else{
	//			request.setAttribute("msg", "信息保存失败!");
	//		}
	//		return SUCCESS;
	//	}
	//	/**
	//	 * <p>
	//	 * 删除。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>删除。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String del() {
	//		log.info("C301Action del.........");
	//		
	//		String id=request.getParameter("id");
	//		TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//		bean1.setId(id);
	//		String msg="1";
	//		try {
	//			msg=consultMsgManager.deleteDataById(bean1);
	//		} catch (Exception e) {
	//			msg=e.getMessage();
	//		}
	//		request.setAttribute("msg",msg);
	//		return SUCCESS;
	//	}
	
	
	
	/**
	 * BEAN类  咨询信息取得
	 * @return BEAN类  咨询信息
	 */
	//	/**
	//	 * <p>
	//	 * 编辑。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>编辑。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String edit() {
	//		log.info("C301Action edit.........");
	//		String id=request.getParameter("id");
	//		if(id!=null){
	//			TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//			bean1.setId(id);
	//			bean=consultMsgManager.findDataById(bean1);
	//		}else{
	//			SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
	//			request.setAttribute("date", sdf.format(new Date()));
	//		}
	//		return "edit";
	//	}
	//	/**
	//	 * <p>
	//	 * 信息保存
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>新增。</div>
	//	 * <div>修改。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String save() {
	//		log.info("C301Action edit.........");
	//		if(bean!=null){
	//			String msg="1";
	//			try {
	//					BaseUserBean user = (BaseUserBean) request.getSession().getAttribute(CommonConstant.SESSION_KEY_USER_CUSTOMER_SERVICE);
	//					if(user!=null){
	//						bean.setCreate_ip(getIpAddr());
	//						bean.setCreate_id(user.getUser_id());
	//						bean.setUpdate_ip(getIpAddr());
	//						bean.setUpdate_id(user.getUser_id());
	//					}
	//					msg=consultMsgManager.saveOrUpdateData(bean);
	//			} catch (Exception e) {
	//				msg=e.getMessage();
	//			}
	//			request.setAttribute("msg",msg);
	//		}else{
	//			request.setAttribute("msg", "信息保存失败!");
	//		}
	//		return SUCCESS;
	//	}
	//	/**
	//	 * <p>
	//	 * 删除。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>删除。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String del() {
	//		log.info("C301Action del.........");
	//		
	//		String id=request.getParameter("id");
	//		TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//		bean1.setId(id);
	//		String msg="1";
	//		try {
	//			msg=consultMsgManager.deleteDataById(bean1);
	//		} catch (Exception e) {
	//			msg=e.getMessage();
	//		}
	//		request.setAttribute("msg",msg);
	//		return SUCCESS;
	//	}
	
	
	
	/**
	 * BEAN类  咨询信息设定
	 * @param bean BEAN类  咨询信息
	 */
	//	/**
	//	 * <p>
	//	 * 编辑。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>编辑。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String edit() {
	//		log.info("C301Action edit.........");
	//		String id=request.getParameter("id");
	//		if(id!=null){
	//			TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//			bean1.setId(id);
	//			bean=consultMsgManager.findDataById(bean1);
	//		}else{
	//			SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
	//			request.setAttribute("date", sdf.format(new Date()));
	//		}
	//		return "edit";
	//	}
	//	/**
	//	 * <p>
	//	 * 信息保存
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>新增。</div>
	//	 * <div>修改。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String save() {
	//		log.info("C301Action edit.........");
	//		if(bean!=null){
	//			String msg="1";
	//			try {
	//					BaseUserBean user = (BaseUserBean) request.getSession().getAttribute(CommonConstant.SESSION_KEY_USER_CUSTOMER_SERVICE);
	//					if(user!=null){
	//						bean.setCreate_ip(getIpAddr());
	//						bean.setCreate_id(user.getUser_id());
	//						bean.setUpdate_ip(getIpAddr());
	//						bean.setUpdate_id(user.getUser_id());
	//					}
	//					msg=consultMsgManager.saveOrUpdateData(bean);
	//			} catch (Exception e) {
	//				msg=e.getMessage();
	//			}
	//			request.setAttribute("msg",msg);
	//		}else{
	//			request.setAttribute("msg", "信息保存失败!");
	//		}
	//		return SUCCESS;
	//	}
	//	/**
	//	 * <p>
	//	 * 删除。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>删除。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String del() {
	//		log.info("C301Action del.........");
	//		
	//		String id=request.getParameter("id");
	//		TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//		bean1.setId(id);
	//		String msg="1";
	//		try {
	//			msg=consultMsgManager.deleteDataById(bean1);
	//		} catch (Exception e) {
	//			msg=e.getMessage();
	//		}
	//		request.setAttribute("msg",msg);
	//		return SUCCESS;
	//	}
	
	
	
	/**
	 * BEAN类  咨询信息取得
	 * @return BEAN类  咨询信息
	 */
	//	/**
	//	 * <p>
	//	 * 编辑。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>编辑。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String edit() {
	//		log.info("C301Action edit.........");
	//		String id=request.getParameter("id");
	//		if(id!=null){
	//			TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//			bean1.setId(id);
	//			bean=consultMsgManager.findDataById(bean1);
	//		}else{
	//			SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
	//			request.setAttribute("date", sdf.format(new Date()));
	//		}
	//		return "edit";
	//	}
	//	/**
	//	 * <p>
	//	 * 信息保存
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>新增。</div>
	//	 * <div>修改。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String save() {
	//		log.info("C301Action edit.........");
	//		if(bean!=null){
	//			String msg="1";
	//			try {
	//					BaseUserBean user = (BaseUserBean) request.getSession().getAttribute(CommonConstant.SESSION_KEY_USER_CUSTOMER_SERVICE);
	//					if(user!=null){
	//						bean.setCreate_ip(getIpAddr());
	//						bean.setCreate_id(user.getUser_id());
	//						bean.setUpdate_ip(getIpAddr());
	//						bean.setUpdate_id(user.getUser_id());
	//					}
	//					msg=consultMsgManager.saveOrUpdateData(bean);
	//			} catch (Exception e) {
	//				msg=e.getMessage();
	//			}
	//			request.setAttribute("msg",msg);
	//		}else{
	//			request.setAttribute("msg", "信息保存失败!");
	//		}
	//		return SUCCESS;
	//	}
	//	/**
	//	 * <p>
	//	 * 删除。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>删除。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String del() {
	//		log.info("C301Action del.........");
	//		
	//		String id=request.getParameter("id");
	//		TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//		bean1.setId(id);
	//		String msg="1";
	//		try {
	//			msg=consultMsgManager.deleteDataById(bean1);
	//		} catch (Exception e) {
	//			msg=e.getMessage();
	//		}
	//		request.setAttribute("msg",msg);
	//		return SUCCESS;
	//	}
	
	
	
	/**
	 * BEAN类  咨询信息取得
	 * @return BEAN类  咨询信息
	 */
	//	/**
	//	 * <p>
	//	 * 编辑。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>编辑。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String edit() {
	//		log.info("C301Action edit.........");
	//		String id=request.getParameter("id");
	//		if(id!=null){
	//			TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//			bean1.setId(id);
	//			bean=consultMsgManager.findDataById(bean1);
	//		}else{
	//			SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
	//			request.setAttribute("date", sdf.format(new Date()));
	//		}
	//		return "edit";
	//	}
	//	/**
	//	 * <p>
	//	 * 信息保存
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>新增。</div>
	//	 * <div>修改。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String save() {
	//		log.info("C301Action edit.........");
	//		if(bean!=null){
	//			String msg="1";
	//			try {
	//					BaseUserBean user = (BaseUserBean) request.getSession().getAttribute(CommonConstant.SESSION_KEY_USER_CUSTOMER_SERVICE);
	//					if(user!=null){
	//						bean.setCreate_ip(getIpAddr());
	//						bean.setCreate_id(user.getUser_id());
	//						bean.setUpdate_ip(getIpAddr());
	//						bean.setUpdate_id(user.getUser_id());
	//					}
	//					msg=consultMsgManager.saveOrUpdateData(bean);
	//			} catch (Exception e) {
	//				msg=e.getMessage();
	//			}
	//			request.setAttribute("msg",msg);
	//		}else{
	//			request.setAttribute("msg", "信息保存失败!");
	//		}
	//		return SUCCESS;
	//	}
	//	/**
	//	 * <p>
	//	 * 删除。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>删除。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String del() {
	//		log.info("C301Action del.........");
	//		
	//		String id=request.getParameter("id");
	//		TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//		bean1.setId(id);
	//		String msg="1";
	//		try {
	//			msg=consultMsgManager.deleteDataById(bean1);
	//		} catch (Exception e) {
	//			msg=e.getMessage();
	//		}
	//		request.setAttribute("msg",msg);
	//		return SUCCESS;
	//	}
	
	
	
	/**
	 * BEAN类  咨询信息设定
	 * @param bean BEAN类  咨询信息
	 */
	//	/**
	//	 * <p>
	//	 * 编辑。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>编辑。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String edit() {
	//		log.info("C301Action edit.........");
	//		String id=request.getParameter("id");
	//		if(id!=null){
	//			TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//			bean1.setId(id);
	//			bean=consultMsgManager.findDataById(bean1);
	//		}else{
	//			SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
	//			request.setAttribute("date", sdf.format(new Date()));
	//		}
	//		return "edit";
	//	}
	//	/**
	//	 * <p>
	//	 * 信息保存
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>新增。</div>
	//	 * <div>修改。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String save() {
	//		log.info("C301Action edit.........");
	//		if(bean!=null){
	//			String msg="1";
	//			try {
	//					BaseUserBean user = (BaseUserBean) request.getSession().getAttribute(CommonConstant.SESSION_KEY_USER_CUSTOMER_SERVICE);
	//					if(user!=null){
	//						bean.setCreate_ip(getIpAddr());
	//						bean.setCreate_id(user.getUser_id());
	//						bean.setUpdate_ip(getIpAddr());
	//						bean.setUpdate_id(user.getUser_id());
	//					}
	//					msg=consultMsgManager.saveOrUpdateData(bean);
	//			} catch (Exception e) {
	//				msg=e.getMessage();
	//			}
	//			request.setAttribute("msg",msg);
	//		}else{
	//			request.setAttribute("msg", "信息保存失败!");
	//		}
	//		return SUCCESS;
	//	}
	//	/**
	//	 * <p>
	//	 * 删除。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>删除。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String del() {
	//		log.info("C301Action del.........");
	//		
	//		String id=request.getParameter("id");
	//		TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//		bean1.setId(id);
	//		String msg="1";
	//		try {
	//			msg=consultMsgManager.deleteDataById(bean1);
	//		} catch (Exception e) {
	//			msg=e.getMessage();
	//		}
	//		request.setAttribute("msg",msg);
	//		return SUCCESS;
	//	}
	
	
	
	/**
	 * BEAN类  咨询信息取得
	 * @return BEAN类  咨询信息
	 */
	//	/**
	//	 * <p>
	//	 * 编辑。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>编辑。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String edit() {
	//		log.info("C301Action edit.........");
	//		String id=request.getParameter("id");
	//		if(id!=null){
	//			TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//			bean1.setId(id);
	//			bean=consultMsgManager.findDataById(bean1);
	//		}else{
	//			SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
	//			request.setAttribute("date", sdf.format(new Date()));
	//		}
	//		return "edit";
	//	}
	//	/**
	//	 * <p>
	//	 * 信息保存
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>新增。</div>
	//	 * <div>修改。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String save() {
	//		log.info("C301Action edit.........");
	//		if(bean!=null){
	//			String msg="1";
	//			try {
	//					BaseUserBean user = (BaseUserBean) request.getSession().getAttribute(CommonConstant.SESSION_KEY_USER_CUSTOMER_SERVICE);
	//					if(user!=null){
	//						bean.setCreate_ip(getIpAddr());
	//						bean.setCreate_id(user.getUser_id());
	//						bean.setUpdate_ip(getIpAddr());
	//						bean.setUpdate_id(user.getUser_id());
	//					}
	//					msg=consultMsgManager.saveOrUpdateData(bean);
	//			} catch (Exception e) {
	//				msg=e.getMessage();
	//			}
	//			request.setAttribute("msg",msg);
	//		}else{
	//			request.setAttribute("msg", "信息保存失败!");
	//		}
	//		return SUCCESS;
	//	}
	//	/**
	//	 * <p>
	//	 * 删除。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>删除。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String del() {
	//		log.info("C301Action del.........");
	//		
	//		String id=request.getParameter("id");
	//		TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//		bean1.setId(id);
	//		String msg="1";
	//		try {
	//			msg=consultMsgManager.deleteDataById(bean1);
	//		} catch (Exception e) {
	//			msg=e.getMessage();
	//		}
	//		request.setAttribute("msg",msg);
	//		return SUCCESS;
	//	}
	
	
	
	/**
	 * BEAN类  咨询信息设定
	 * @param bean BEAN类  咨询信息
	 */
	//	/**
	//	 * <p>
	//	 * 编辑。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>编辑。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String edit() {
	//		log.info("C301Action edit.........");
	//		String id=request.getParameter("id");
	//		if(id!=null){
	//			TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//			bean1.setId(id);
	//			bean=consultMsgManager.findDataById(bean1);
	//		}else{
	//			SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
	//			request.setAttribute("date", sdf.format(new Date()));
	//		}
	//		return "edit";
	//	}
	//	/**
	//	 * <p>
	//	 * 信息保存
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>新增。</div>
	//	 * <div>修改。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String save() {
	//		log.info("C301Action edit.........");
	//		if(bean!=null){
	//			String msg="1";
	//			try {
	//					BaseUserBean user = (BaseUserBean) request.getSession().getAttribute(CommonConstant.SESSION_KEY_USER_CUSTOMER_SERVICE);
	//					if(user!=null){
	//						bean.setCreate_ip(getIpAddr());
	//						bean.setCreate_id(user.getUser_id());
	//						bean.setUpdate_ip(getIpAddr());
	//						bean.setUpdate_id(user.getUser_id());
	//					}
	//					msg=consultMsgManager.saveOrUpdateData(bean);
	//			} catch (Exception e) {
	//				msg=e.getMessage();
	//			}
	//			request.setAttribute("msg",msg);
	//		}else{
	//			request.setAttribute("msg", "信息保存失败!");
	//		}
	//		return SUCCESS;
	//	}
	//	/**
	//	 * <p>
	//	 * 删除。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>删除。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String del() {
	//		log.info("C301Action del.........");
	//		
	//		String id=request.getParameter("id");
	//		TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//		bean1.setId(id);
	//		String msg="1";
	//		try {
	//			msg=consultMsgManager.deleteDataById(bean1);
	//		} catch (Exception e) {
	//			msg=e.getMessage();
	//		}
	//		request.setAttribute("msg",msg);
	//		return SUCCESS;
	//	}
	
	
	
	/**
	 * BEAN类  咨询信息取得
	 * @return BEAN类  咨询信息
	 */
	//	/**
	//	 * <p>
	//	 * 编辑。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>编辑。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String edit() {
	//		log.info("C301Action edit.........");
	//		String id=request.getParameter("id");
	//		if(id!=null){
	//			TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//			bean1.setId(id);
	//			bean=consultMsgManager.findDataById(bean1);
	//		}else{
	//			SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
	//			request.setAttribute("date", sdf.format(new Date()));
	//		}
	//		return "edit";
	//	}
	//	/**
	//	 * <p>
	//	 * 信息保存
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>新增。</div>
	//	 * <div>修改。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String save() {
	//		log.info("C301Action edit.........");
	//		if(bean!=null){
	//			String msg="1";
	//			try {
	//					BaseUserBean user = (BaseUserBean) request.getSession().getAttribute(CommonConstant.SESSION_KEY_USER_CUSTOMER_SERVICE);
	//					if(user!=null){
	//						bean.setCreate_ip(getIpAddr());
	//						bean.setCreate_id(user.getUser_id());
	//						bean.setUpdate_ip(getIpAddr());
	//						bean.setUpdate_id(user.getUser_id());
	//					}
	//					msg=consultMsgManager.saveOrUpdateData(bean);
	//			} catch (Exception e) {
	//				msg=e.getMessage();
	//			}
	//			request.setAttribute("msg",msg);
	//		}else{
	//			request.setAttribute("msg", "信息保存失败!");
	//		}
	//		return SUCCESS;
	//	}
	//	/**
	//	 * <p>
	//	 * 删除。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>删除。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String del() {
	//		log.info("C301Action del.........");
	//		
	//		String id=request.getParameter("id");
	//		TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//		bean1.setId(id);
	//		String msg="1";
	//		try {
	//			msg=consultMsgManager.deleteDataById(bean1);
	//		} catch (Exception e) {
	//			msg=e.getMessage();
	//		}
	//		request.setAttribute("msg",msg);
	//		return SUCCESS;
	//	}
	
	
	
	/**
	 * BEAN类  咨询信息取得
	 * @return BEAN类  咨询信息
	 */
	//	/**
	//	 * <p>
	//	 * 编辑。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>编辑。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String edit() {
	//		log.info("C301Action edit.........");
	//		String id=request.getParameter("id");
	//		if(id!=null){
	//			TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//			bean1.setId(id);
	//			bean=consultMsgManager.findDataById(bean1);
	//		}else{
	//			SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
	//			request.setAttribute("date", sdf.format(new Date()));
	//		}
	//		return "edit";
	//	}
	//	/**
	//	 * <p>
	//	 * 信息保存
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>新增。</div>
	//	 * <div>修改。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String save() {
	//		log.info("C301Action edit.........");
	//		if(bean!=null){
	//			String msg="1";
	//			try {
	//					BaseUserBean user = (BaseUserBean) request.getSession().getAttribute(CommonConstant.SESSION_KEY_USER_CUSTOMER_SERVICE);
	//					if(user!=null){
	//						bean.setCreate_ip(getIpAddr());
	//						bean.setCreate_id(user.getUser_id());
	//						bean.setUpdate_ip(getIpAddr());
	//						bean.setUpdate_id(user.getUser_id());
	//					}
	//					msg=consultMsgManager.saveOrUpdateData(bean);
	//			} catch (Exception e) {
	//				msg=e.getMessage();
	//			}
	//			request.setAttribute("msg",msg);
	//		}else{
	//			request.setAttribute("msg", "信息保存失败!");
	//		}
	//		return SUCCESS;
	//	}
	//	/**
	//	 * <p>
	//	 * 删除。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>删除。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String del() {
	//		log.info("C301Action del.........");
	//		
	//		String id=request.getParameter("id");
	//		TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//		bean1.setId(id);
	//		String msg="1";
	//		try {
	//			msg=consultMsgManager.deleteDataById(bean1);
	//		} catch (Exception e) {
	//			msg=e.getMessage();
	//		}
	//		request.setAttribute("msg",msg);
	//		return SUCCESS;
	//	}
	
	
	
	/**
	 * BEAN类  咨询信息设定
	 * @param bean BEAN类  咨询信息
	 */
	//	/**
	//	 * <p>
	//	 * 编辑。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>编辑。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String edit() {
	//		log.info("C301Action edit.........");
	//		String id=request.getParameter("id");
	//		if(id!=null){
	//			TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//			bean1.setId(id);
	//			bean=consultMsgManager.findDataById(bean1);
	//		}else{
	//			SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
	//			request.setAttribute("date", sdf.format(new Date()));
	//		}
	//		return "edit";
	//	}
	//	/**
	//	 * <p>
	//	 * 信息保存
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>新增。</div>
	//	 * <div>修改。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String save() {
	//		log.info("C301Action edit.........");
	//		if(bean!=null){
	//			String msg="1";
	//			try {
	//					BaseUserBean user = (BaseUserBean) request.getSession().getAttribute(CommonConstant.SESSION_KEY_USER_CUSTOMER_SERVICE);
	//					if(user!=null){
	//						bean.setCreate_ip(getIpAddr());
	//						bean.setCreate_id(user.getUser_id());
	//						bean.setUpdate_ip(getIpAddr());
	//						bean.setUpdate_id(user.getUser_id());
	//					}
	//					msg=consultMsgManager.saveOrUpdateData(bean);
	//			} catch (Exception e) {
	//				msg=e.getMessage();
	//			}
	//			request.setAttribute("msg",msg);
	//		}else{
	//			request.setAttribute("msg", "信息保存失败!");
	//		}
	//		return SUCCESS;
	//	}
	//	/**
	//	 * <p>
	//	 * 删除。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>删除。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String del() {
	//		log.info("C301Action del.........");
	//		
	//		String id=request.getParameter("id");
	//		TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//		bean1.setId(id);
	//		String msg="1";
	//		try {
	//			msg=consultMsgManager.deleteDataById(bean1);
	//		} catch (Exception e) {
	//			msg=e.getMessage();
	//		}
	//		request.setAttribute("msg",msg);
	//		return SUCCESS;
	//	}
	
	
	
	/**
	 * BEAN类  咨询信息取得
	 * @return BEAN类  咨询信息
	 */
	//	/**
	//	 * <p>
	//	 * 编辑。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>编辑。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String edit() {
	//		log.info("C301Action edit.........");
	//		String id=request.getParameter("id");
	//		if(id!=null){
	//			TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//			bean1.setId(id);
	//			bean=consultMsgManager.findDataById(bean1);
	//		}else{
	//			SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
	//			request.setAttribute("date", sdf.format(new Date()));
	//		}
	//		return "edit";
	//	}
	//	/**
	//	 * <p>
	//	 * 信息保存
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>新增。</div>
	//	 * <div>修改。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String save() {
	//		log.info("C301Action edit.........");
	//		if(bean!=null){
	//			String msg="1";
	//			try {
	//					BaseUserBean user = (BaseUserBean) request.getSession().getAttribute(CommonConstant.SESSION_KEY_USER_CUSTOMER_SERVICE);
	//					if(user!=null){
	//						bean.setCreate_ip(getIpAddr());
	//						bean.setCreate_id(user.getUser_id());
	//						bean.setUpdate_ip(getIpAddr());
	//						bean.setUpdate_id(user.getUser_id());
	//					}
	//					msg=consultMsgManager.saveOrUpdateData(bean);
	//			} catch (Exception e) {
	//				msg=e.getMessage();
	//			}
	//			request.setAttribute("msg",msg);
	//		}else{
	//			request.setAttribute("msg", "信息保存失败!");
	//		}
	//		return SUCCESS;
	//	}
	//	/**
	//	 * <p>
	//	 * 删除。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>删除。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String del() {
	//		log.info("C301Action del.........");
	//		
	//		String id=request.getParameter("id");
	//		TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//		bean1.setId(id);
	//		String msg="1";
	//		try {
	//			msg=consultMsgManager.deleteDataById(bean1);
	//		} catch (Exception e) {
	//			msg=e.getMessage();
	//		}
	//		request.setAttribute("msg",msg);
	//		return SUCCESS;
	//	}
	
	
	
	/**
	 * BEAN类  咨询信息取得
	 * @return BEAN类  咨询信息
	 */
	//	/**
	//	 * <p>
	//	 * 编辑。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>编辑。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String edit() {
	//		log.info("C301Action edit.........");
	//		String id=request.getParameter("id");
	//		if(id!=null){
	//			TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//			bean1.setId(id);
	//			bean=consultMsgManager.findDataById(bean1);
	//		}else{
	//			SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
	//			request.setAttribute("date", sdf.format(new Date()));
	//		}
	//		return "edit";
	//	}
	//	/**
	//	 * <p>
	//	 * 信息保存
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>新增。</div>
	//	 * <div>修改。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String save() {
	//		log.info("C301Action edit.........");
	//		if(bean!=null){
	//			String msg="1";
	//			try {
	//					BaseUserBean user = (BaseUserBean) request.getSession().getAttribute(CommonConstant.SESSION_KEY_USER_CUSTOMER_SERVICE);
	//					if(user!=null){
	//						bean.setCreate_ip(getIpAddr());
	//						bean.setCreate_id(user.getUser_id());
	//						bean.setUpdate_ip(getIpAddr());
	//						bean.setUpdate_id(user.getUser_id());
	//					}
	//					msg=consultMsgManager.saveOrUpdateData(bean);
	//			} catch (Exception e) {
	//				msg=e.getMessage();
	//			}
	//			request.setAttribute("msg",msg);
	//		}else{
	//			request.setAttribute("msg", "信息保存失败!");
	//		}
	//		return SUCCESS;
	//	}
	//	/**
	//	 * <p>
	//	 * 删除。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>删除。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String del() {
	//		log.info("C301Action del.........");
	//		
	//		String id=request.getParameter("id");
	//		TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//		bean1.setId(id);
	//		String msg="1";
	//		try {
	//			msg=consultMsgManager.deleteDataById(bean1);
	//		} catch (Exception e) {
	//			msg=e.getMessage();
	//		}
	//		request.setAttribute("msg",msg);
	//		return SUCCESS;
	//	}
	
	
	
	/**
	 * BEAN类  咨询信息设定
	 * @param bean BEAN类  咨询信息
	 */
	//	/**
	//	 * <p>
	//	 * 编辑。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>编辑。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String edit() {
	//		log.info("C301Action edit.........");
	//		String id=request.getParameter("id");
	//		if(id!=null){
	//			TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//			bean1.setId(id);
	//			bean=consultMsgManager.findDataById(bean1);
	//		}else{
	//			SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
	//			request.setAttribute("date", sdf.format(new Date()));
	//		}
	//		return "edit";
	//	}
	//	/**
	//	 * <p>
	//	 * 信息保存
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>新增。</div>
	//	 * <div>修改。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String save() {
	//		log.info("C301Action edit.........");
	//		if(bean!=null){
	//			String msg="1";
	//			try {
	//					BaseUserBean user = (BaseUserBean) request.getSession().getAttribute(CommonConstant.SESSION_KEY_USER_CUSTOMER_SERVICE);
	//					if(user!=null){
	//						bean.setCreate_ip(getIpAddr());
	//						bean.setCreate_id(user.getUser_id());
	//						bean.setUpdate_ip(getIpAddr());
	//						bean.setUpdate_id(user.getUser_id());
	//					}
	//					msg=consultMsgManager.saveOrUpdateData(bean);
	//			} catch (Exception e) {
	//				msg=e.getMessage();
	//			}
	//			request.setAttribute("msg",msg);
	//		}else{
	//			request.setAttribute("msg", "信息保存失败!");
	//		}
	//		return SUCCESS;
	//	}
	//	/**
	//	 * <p>
	//	 * 删除。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>删除。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String del() {
	//		log.info("C301Action del.........");
	//		
	//		String id=request.getParameter("id");
	//		TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//		bean1.setId(id);
	//		String msg="1";
	//		try {
	//			msg=consultMsgManager.deleteDataById(bean1);
	//		} catch (Exception e) {
	//			msg=e.getMessage();
	//		}
	//		request.setAttribute("msg",msg);
	//		return SUCCESS;
	//	}
	
	
	
	/**
	 * BEAN类  咨询信息取得
	 * @return BEAN类  咨询信息
	 */
	//	/**
	//	 * <p>
	//	 * 编辑。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>编辑。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String edit() {
	//		log.info("C301Action edit.........");
	//		String id=request.getParameter("id");
	//		if(id!=null){
	//			TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//			bean1.setId(id);
	//			bean=consultMsgManager.findDataById(bean1);
	//		}else{
	//			SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
	//			request.setAttribute("date", sdf.format(new Date()));
	//		}
	//		return "edit";
	//	}
	//	/**
	//	 * <p>
	//	 * 信息保存
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>新增。</div>
	//	 * <div>修改。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String save() {
	//		log.info("C301Action edit.........");
	//		if(bean!=null){
	//			String msg="1";
	//			try {
	//					BaseUserBean user = (BaseUserBean) request.getSession().getAttribute(CommonConstant.SESSION_KEY_USER_CUSTOMER_SERVICE);
	//					if(user!=null){
	//						bean.setCreate_ip(getIpAddr());
	//						bean.setCreate_id(user.getUser_id());
	//						bean.setUpdate_ip(getIpAddr());
	//						bean.setUpdate_id(user.getUser_id());
	//					}
	//					msg=consultMsgManager.saveOrUpdateData(bean);
	//			} catch (Exception e) {
	//				msg=e.getMessage();
	//			}
	//			request.setAttribute("msg",msg);
	//		}else{
	//			request.setAttribute("msg", "信息保存失败!");
	//		}
	//		return SUCCESS;
	//	}
	//	/**
	//	 * <p>
	//	 * 删除。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>删除。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String del() {
	//		log.info("C301Action del.........");
	//		
	//		String id=request.getParameter("id");
	//		TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//		bean1.setId(id);
	//		String msg="1";
	//		try {
	//			msg=consultMsgManager.deleteDataById(bean1);
	//		} catch (Exception e) {
	//			msg=e.getMessage();
	//		}
	//		request.setAttribute("msg",msg);
	//		return SUCCESS;
	//	}
	
	
	
	/**
	 * BEAN类  咨询信息设定
	 * @param bean BEAN类  咨询信息
	 */
	//	/**
	//	 * <p>
	//	 * 编辑。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>编辑。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String edit() {
	//		log.info("C301Action edit.........");
	//		String id=request.getParameter("id");
	//		if(id!=null){
	//			TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//			bean1.setId(id);
	//			bean=consultMsgManager.findDataById(bean1);
	//		}else{
	//			SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
	//			request.setAttribute("date", sdf.format(new Date()));
	//		}
	//		return "edit";
	//	}
	//	/**
	//	 * <p>
	//	 * 信息保存
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>新增。</div>
	//	 * <div>修改。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String save() {
	//		log.info("C301Action edit.........");
	//		if(bean!=null){
	//			String msg="1";
	//			try {
	//					BaseUserBean user = (BaseUserBean) request.getSession().getAttribute(CommonConstant.SESSION_KEY_USER_CUSTOMER_SERVICE);
	//					if(user!=null){
	//						bean.setCreate_ip(getIpAddr());
	//						bean.setCreate_id(user.getUser_id());
	//						bean.setUpdate_ip(getIpAddr());
	//						bean.setUpdate_id(user.getUser_id());
	//					}
	//					msg=consultMsgManager.saveOrUpdateData(bean);
	//			} catch (Exception e) {
	//				msg=e.getMessage();
	//			}
	//			request.setAttribute("msg",msg);
	//		}else{
	//			request.setAttribute("msg", "信息保存失败!");
	//		}
	//		return SUCCESS;
	//	}
	//	/**
	//	 * <p>
	//	 * 删除。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>删除。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String del() {
	//		log.info("C301Action del.........");
	//		
	//		String id=request.getParameter("id");
	//		TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//		bean1.setId(id);
	//		String msg="1";
	//		try {
	//			msg=consultMsgManager.deleteDataById(bean1);
	//		} catch (Exception e) {
	//			msg=e.getMessage();
	//		}
	//		request.setAttribute("msg",msg);
	//		return SUCCESS;
	//	}
	
	
	
	/**
	 * BEAN类  咨询信息取得
	 * @return BEAN类  咨询信息
	 */
	//	/**
	//	 * <p>
	//	 * 编辑。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>编辑。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String edit() {
	//		log.info("C301Action edit.........");
	//		String id=request.getParameter("id");
	//		if(id!=null){
	//			TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//			bean1.setId(id);
	//			bean=consultMsgManager.findDataById(bean1);
	//		}else{
	//			SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
	//			request.setAttribute("date", sdf.format(new Date()));
	//		}
	//		return "edit";
	//	}
	//	/**
	//	 * <p>
	//	 * 信息保存
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>新增。</div>
	//	 * <div>修改。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String save() {
	//		log.info("C301Action edit.........");
	//		if(bean!=null){
	//			String msg="1";
	//			try {
	//					BaseUserBean user = (BaseUserBean) request.getSession().getAttribute(CommonConstant.SESSION_KEY_USER_CUSTOMER_SERVICE);
	//					if(user!=null){
	//						bean.setCreate_ip(getIpAddr());
	//						bean.setCreate_id(user.getUser_id());
	//						bean.setUpdate_ip(getIpAddr());
	//						bean.setUpdate_id(user.getUser_id());
	//					}
	//					msg=consultMsgManager.saveOrUpdateData(bean);
	//			} catch (Exception e) {
	//				msg=e.getMessage();
	//			}
	//			request.setAttribute("msg",msg);
	//		}else{
	//			request.setAttribute("msg", "信息保存失败!");
	//		}
	//		return SUCCESS;
	//	}
	//	/**
	//	 * <p>
	//	 * 删除。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>删除。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String del() {
	//		log.info("C301Action del.........");
	//		
	//		String id=request.getParameter("id");
	//		TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//		bean1.setId(id);
	//		String msg="1";
	//		try {
	//			msg=consultMsgManager.deleteDataById(bean1);
	//		} catch (Exception e) {
	//			msg=e.getMessage();
	//		}
	//		request.setAttribute("msg",msg);
	//		return SUCCESS;
	//	}
	
	
	
	/**
	 * BEAN类  咨询信息取得
	 * @return BEAN类  咨询信息
	 */
	//	/**
	//	 * <p>
	//	 * 编辑。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>编辑。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String edit() {
	//		log.info("C301Action edit.........");
	//		String id=request.getParameter("id");
	//		if(id!=null){
	//			TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//			bean1.setId(id);
	//			bean=consultMsgManager.findDataById(bean1);
	//		}else{
	//			SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
	//			request.setAttribute("date", sdf.format(new Date()));
	//		}
	//		return "edit";
	//	}
	//	/**
	//	 * <p>
	//	 * 信息保存
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>新增。</div>
	//	 * <div>修改。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String save() {
	//		log.info("C301Action edit.........");
	//		if(bean!=null){
	//			String msg="1";
	//			try {
	//					BaseUserBean user = (BaseUserBean) request.getSession().getAttribute(CommonConstant.SESSION_KEY_USER_CUSTOMER_SERVICE);
	//					if(user!=null){
	//						bean.setCreate_ip(getIpAddr());
	//						bean.setCreate_id(user.getUser_id());
	//						bean.setUpdate_ip(getIpAddr());
	//						bean.setUpdate_id(user.getUser_id());
	//					}
	//					msg=consultMsgManager.saveOrUpdateData(bean);
	//			} catch (Exception e) {
	//				msg=e.getMessage();
	//			}
	//			request.setAttribute("msg",msg);
	//		}else{
	//			request.setAttribute("msg", "信息保存失败!");
	//		}
	//		return SUCCESS;
	//	}
	//	/**
	//	 * <p>
	//	 * 删除。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>删除。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String del() {
	//		log.info("C301Action del.........");
	//		
	//		String id=request.getParameter("id");
	//		TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//		bean1.setId(id);
	//		String msg="1";
	//		try {
	//			msg=consultMsgManager.deleteDataById(bean1);
	//		} catch (Exception e) {
	//			msg=e.getMessage();
	//		}
	//		request.setAttribute("msg",msg);
	//		return SUCCESS;
	//	}
	
	
	
	/**
	 * BEAN类  咨询信息设定
	 * @param bean BEAN类  咨询信息
	 */
	//	/**
	//	 * <p>
	//	 * 编辑。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>编辑。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String edit() {
	//		log.info("C301Action edit.........");
	//		String id=request.getParameter("id");
	//		if(id!=null){
	//			TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//			bean1.setId(id);
	//			bean=consultMsgManager.findDataById(bean1);
	//		}else{
	//			SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
	//			request.setAttribute("date", sdf.format(new Date()));
	//		}
	//		return "edit";
	//	}
	//	/**
	//	 * <p>
	//	 * 信息保存
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>新增。</div>
	//	 * <div>修改。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String save() {
	//		log.info("C301Action edit.........");
	//		if(bean!=null){
	//			String msg="1";
	//			try {
	//					BaseUserBean user = (BaseUserBean) request.getSession().getAttribute(CommonConstant.SESSION_KEY_USER_CUSTOMER_SERVICE);
	//					if(user!=null){
	//						bean.setCreate_ip(getIpAddr());
	//						bean.setCreate_id(user.getUser_id());
	//						bean.setUpdate_ip(getIpAddr());
	//						bean.setUpdate_id(user.getUser_id());
	//					}
	//					msg=consultMsgManager.saveOrUpdateData(bean);
	//			} catch (Exception e) {
	//				msg=e.getMessage();
	//			}
	//			request.setAttribute("msg",msg);
	//		}else{
	//			request.setAttribute("msg", "信息保存失败!");
	//		}
	//		return SUCCESS;
	//	}
	//	/**
	//	 * <p>
	//	 * 删除。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>删除。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String del() {
	//		log.info("C301Action del.........");
	//		
	//		String id=request.getParameter("id");
	//		TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//		bean1.setId(id);
	//		String msg="1";
	//		try {
	//			msg=consultMsgManager.deleteDataById(bean1);
	//		} catch (Exception e) {
	//			msg=e.getMessage();
	//		}
	//		request.setAttribute("msg",msg);
	//		return SUCCESS;
	//	}
	
	
	
	/**
	 * BEAN类  咨询信息取得
	 * @return BEAN类  咨询信息
	 */
	//	/**
	//	 * <p>
	//	 * 编辑。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>编辑。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String edit() {
	//		log.info("C301Action edit.........");
	//		String id=request.getParameter("id");
	//		if(id!=null){
	//			TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//			bean1.setId(id);
	//			bean=consultMsgManager.findDataById(bean1);
	//		}else{
	//			SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
	//			request.setAttribute("date", sdf.format(new Date()));
	//		}
	//		return "edit";
	//	}
	//	/**
	//	 * <p>
	//	 * 信息保存
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>新增。</div>
	//	 * <div>修改。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String save() {
	//		log.info("C301Action edit.........");
	//		if(bean!=null){
	//			String msg="1";
	//			try {
	//					BaseUserBean user = (BaseUserBean) request.getSession().getAttribute(CommonConstant.SESSION_KEY_USER_CUSTOMER_SERVICE);
	//					if(user!=null){
	//						bean.setCreate_ip(getIpAddr());
	//						bean.setCreate_id(user.getUser_id());
	//						bean.setUpdate_ip(getIpAddr());
	//						bean.setUpdate_id(user.getUser_id());
	//					}
	//					msg=consultMsgManager.saveOrUpdateData(bean);
	//			} catch (Exception e) {
	//				msg=e.getMessage();
	//			}
	//			request.setAttribute("msg",msg);
	//		}else{
	//			request.setAttribute("msg", "信息保存失败!");
	//		}
	//		return SUCCESS;
	//	}
	//	/**
	//	 * <p>
	//	 * 删除。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>删除。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String del() {
	//		log.info("C301Action del.........");
	//		
	//		String id=request.getParameter("id");
	//		TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//		bean1.setId(id);
	//		String msg="1";
	//		try {
	//			msg=consultMsgManager.deleteDataById(bean1);
	//		} catch (Exception e) {
	//			msg=e.getMessage();
	//		}
	//		request.setAttribute("msg",msg);
	//		return SUCCESS;
	//	}
	
	
	
	/**
	 * BEAN类  咨询信息取得
	 * @return BEAN类  咨询信息
	 */
	//	/**
	//	 * <p>
	//	 * 编辑。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>编辑。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String edit() {
	//		log.info("C301Action edit.........");
	//		String id=request.getParameter("id");
	//		if(id!=null){
	//			TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//			bean1.setId(id);
	//			bean=consultMsgManager.findDataById(bean1);
	//		}else{
	//			SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
	//			request.setAttribute("date", sdf.format(new Date()));
	//		}
	//		return "edit";
	//	}
	//	/**
	//	 * <p>
	//	 * 信息保存
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>新增。</div>
	//	 * <div>修改。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String save() {
	//		log.info("C301Action edit.........");
	//		if(bean!=null){
	//			String msg="1";
	//			try {
	//					BaseUserBean user = (BaseUserBean) request.getSession().getAttribute(CommonConstant.SESSION_KEY_USER_CUSTOMER_SERVICE);
	//					if(user!=null){
	//						bean.setCreate_ip(getIpAddr());
	//						bean.setCreate_id(user.getUser_id());
	//						bean.setUpdate_ip(getIpAddr());
	//						bean.setUpdate_id(user.getUser_id());
	//					}
	//					msg=consultMsgManager.saveOrUpdateData(bean);
	//			} catch (Exception e) {
	//				msg=e.getMessage();
	//			}
	//			request.setAttribute("msg",msg);
	//		}else{
	//			request.setAttribute("msg", "信息保存失败!");
	//		}
	//		return SUCCESS;
	//	}
	//	/**
	//	 * <p>
	//	 * 删除。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>删除。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String del() {
	//		log.info("C301Action del.........");
	//		
	//		String id=request.getParameter("id");
	//		TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//		bean1.setId(id);
	//		String msg="1";
	//		try {
	//			msg=consultMsgManager.deleteDataById(bean1);
	//		} catch (Exception e) {
	//			msg=e.getMessage();
	//		}
	//		request.setAttribute("msg",msg);
	//		return SUCCESS;
	//	}
	
	
	
	/**
	 * BEAN类  咨询信息设定
	 * @param bean BEAN类  咨询信息
	 */
	//	/**
	//	 * <p>
	//	 * 编辑。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>编辑。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String edit() {
	//		log.info("C301Action edit.........");
	//		String id=request.getParameter("id");
	//		if(id!=null){
	//			TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//			bean1.setId(id);
	//			bean=consultMsgManager.findDataById(bean1);
	//		}else{
	//			SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
	//			request.setAttribute("date", sdf.format(new Date()));
	//		}
	//		return "edit";
	//	}
	//	/**
	//	 * <p>
	//	 * 信息保存
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>新增。</div>
	//	 * <div>修改。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String save() {
	//		log.info("C301Action edit.........");
	//		if(bean!=null){
	//			String msg="1";
	//			try {
	//					BaseUserBean user = (BaseUserBean) request.getSession().getAttribute(CommonConstant.SESSION_KEY_USER_CUSTOMER_SERVICE);
	//					if(user!=null){
	//						bean.setCreate_ip(getIpAddr());
	//						bean.setCreate_id(user.getUser_id());
	//						bean.setUpdate_ip(getIpAddr());
	//						bean.setUpdate_id(user.getUser_id());
	//					}
	//					msg=consultMsgManager.saveOrUpdateData(bean);
	//			} catch (Exception e) {
	//				msg=e.getMessage();
	//			}
	//			request.setAttribute("msg",msg);
	//		}else{
	//			request.setAttribute("msg", "信息保存失败!");
	//		}
	//		return SUCCESS;
	//	}
	//	/**
	//	 * <p>
	//	 * 删除。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>删除。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String del() {
	//		log.info("C301Action del.........");
	//		
	//		String id=request.getParameter("id");
	//		TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//		bean1.setId(id);
	//		String msg="1";
	//		try {
	//			msg=consultMsgManager.deleteDataById(bean1);
	//		} catch (Exception e) {
	//			msg=e.getMessage();
	//		}
	//		request.setAttribute("msg",msg);
	//		return SUCCESS;
	//	}
	
	
	
	/**
	 * BEAN类  咨询信息取得
	 * @return BEAN类  咨询信息
	 */
	//	/**
	//	 * <p>
	//	 * 编辑。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>编辑。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String edit() {
	//		log.info("C301Action edit.........");
	//		String id=request.getParameter("id");
	//		if(id!=null){
	//			TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//			bean1.setId(id);
	//			bean=consultMsgManager.findDataById(bean1);
	//		}else{
	//			SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
	//			request.setAttribute("date", sdf.format(new Date()));
	//		}
	//		return "edit";
	//	}
	//	/**
	//	 * <p>
	//	 * 信息保存
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>新增。</div>
	//	 * <div>修改。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String save() {
	//		log.info("C301Action edit.........");
	//		if(bean!=null){
	//			String msg="1";
	//			try {
	//					BaseUserBean user = (BaseUserBean) request.getSession().getAttribute(CommonConstant.SESSION_KEY_USER_CUSTOMER_SERVICE);
	//					if(user!=null){
	//						bean.setCreate_ip(getIpAddr());
	//						bean.setCreate_id(user.getUser_id());
	//						bean.setUpdate_ip(getIpAddr());
	//						bean.setUpdate_id(user.getUser_id());
	//					}
	//					msg=consultMsgManager.saveOrUpdateData(bean);
	//			} catch (Exception e) {
	//				msg=e.getMessage();
	//			}
	//			request.setAttribute("msg",msg);
	//		}else{
	//			request.setAttribute("msg", "信息保存失败!");
	//		}
	//		return SUCCESS;
	//	}
	//	/**
	//	 * <p>
	//	 * 删除。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>删除。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String del() {
	//		log.info("C301Action del.........");
	//		
	//		String id=request.getParameter("id");
	//		TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//		bean1.setId(id);
	//		String msg="1";
	//		try {
	//			msg=consultMsgManager.deleteDataById(bean1);
	//		} catch (Exception e) {
	//			msg=e.getMessage();
	//		}
	//		request.setAttribute("msg",msg);
	//		return SUCCESS;
	//	}
	
	
	
	/**
	 * BEAN类  咨询信息设定
	 * @param bean BEAN类  咨询信息
	 */
	//	/**
	//	 * <p>
	//	 * 编辑。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>编辑。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String edit() {
	//		log.info("C301Action edit.........");
	//		String id=request.getParameter("id");
	//		if(id!=null){
	//			TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//			bean1.setId(id);
	//			bean=consultMsgManager.findDataById(bean1);
	//		}else{
	//			SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
	//			request.setAttribute("date", sdf.format(new Date()));
	//		}
	//		return "edit";
	//	}
	//	/**
	//	 * <p>
	//	 * 信息保存
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>新增。</div>
	//	 * <div>修改。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String save() {
	//		log.info("C301Action edit.........");
	//		if(bean!=null){
	//			String msg="1";
	//			try {
	//					BaseUserBean user = (BaseUserBean) request.getSession().getAttribute(CommonConstant.SESSION_KEY_USER_CUSTOMER_SERVICE);
	//					if(user!=null){
	//						bean.setCreate_ip(getIpAddr());
	//						bean.setCreate_id(user.getUser_id());
	//						bean.setUpdate_ip(getIpAddr());
	//						bean.setUpdate_id(user.getUser_id());
	//					}
	//					msg=consultMsgManager.saveOrUpdateData(bean);
	//			} catch (Exception e) {
	//				msg=e.getMessage();
	//			}
	//			request.setAttribute("msg",msg);
	//		}else{
	//			request.setAttribute("msg", "信息保存失败!");
	//		}
	//		return SUCCESS;
	//	}
	//	/**
	//	 * <p>
	//	 * 删除。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>删除。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String del() {
	//		log.info("C301Action del.........");
	//		
	//		String id=request.getParameter("id");
	//		TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//		bean1.setId(id);
	//		String msg="1";
	//		try {
	//			msg=consultMsgManager.deleteDataById(bean1);
	//		} catch (Exception e) {
	//			msg=e.getMessage();
	//		}
	//		request.setAttribute("msg",msg);
	//		return SUCCESS;
	//	}
	
	
	
	/**
	 * BEAN类  咨询信息取得
	 * @return BEAN类  咨询信息
	 */
	//	/**
	//	 * <p>
	//	 * 编辑。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>编辑。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String edit() {
	//		log.info("C301Action edit.........");
	//		String id=request.getParameter("id");
	//		if(id!=null){
	//			TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//			bean1.setId(id);
	//			bean=consultMsgManager.findDataById(bean1);
	//		}else{
	//			SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
	//			request.setAttribute("date", sdf.format(new Date()));
	//		}
	//		return "edit";
	//	}
	//	/**
	//	 * <p>
	//	 * 信息保存
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>新增。</div>
	//	 * <div>修改。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String save() {
	//		log.info("C301Action edit.........");
	//		if(bean!=null){
	//			String msg="1";
	//			try {
	//					BaseUserBean user = (BaseUserBean) request.getSession().getAttribute(CommonConstant.SESSION_KEY_USER_CUSTOMER_SERVICE);
	//					if(user!=null){
	//						bean.setCreate_ip(getIpAddr());
	//						bean.setCreate_id(user.getUser_id());
	//						bean.setUpdate_ip(getIpAddr());
	//						bean.setUpdate_id(user.getUser_id());
	//					}
	//					msg=consultMsgManager.saveOrUpdateData(bean);
	//			} catch (Exception e) {
	//				msg=e.getMessage();
	//			}
	//			request.setAttribute("msg",msg);
	//		}else{
	//			request.setAttribute("msg", "信息保存失败!");
	//		}
	//		return SUCCESS;
	//	}
	//	/**
	//	 * <p>
	//	 * 删除。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>删除。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String del() {
	//		log.info("C301Action del.........");
	//		
	//		String id=request.getParameter("id");
	//		TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//		bean1.setId(id);
	//		String msg="1";
	//		try {
	//			msg=consultMsgManager.deleteDataById(bean1);
	//		} catch (Exception e) {
	//			msg=e.getMessage();
	//		}
	//		request.setAttribute("msg",msg);
	//		return SUCCESS;
	//	}
	
	
	
	/**
	 * BEAN类  咨询信息取得
	 * @return BEAN类  咨询信息
	 */
	//	/**
	//	 * <p>
	//	 * 编辑。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>编辑。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String edit() {
	//		log.info("C301Action edit.........");
	//		String id=request.getParameter("id");
	//		if(id!=null){
	//			TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//			bean1.setId(id);
	//			bean=consultMsgManager.findDataById(bean1);
	//		}else{
	//			SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
	//			request.setAttribute("date", sdf.format(new Date()));
	//		}
	//		return "edit";
	//	}
	//	/**
	//	 * <p>
	//	 * 信息保存
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>新增。</div>
	//	 * <div>修改。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String save() {
	//		log.info("C301Action edit.........");
	//		if(bean!=null){
	//			String msg="1";
	//			try {
	//					BaseUserBean user = (BaseUserBean) request.getSession().getAttribute(CommonConstant.SESSION_KEY_USER_CUSTOMER_SERVICE);
	//					if(user!=null){
	//						bean.setCreate_ip(getIpAddr());
	//						bean.setCreate_id(user.getUser_id());
	//						bean.setUpdate_ip(getIpAddr());
	//						bean.setUpdate_id(user.getUser_id());
	//					}
	//					msg=consultMsgManager.saveOrUpdateData(bean);
	//			} catch (Exception e) {
	//				msg=e.getMessage();
	//			}
	//			request.setAttribute("msg",msg);
	//		}else{
	//			request.setAttribute("msg", "信息保存失败!");
	//		}
	//		return SUCCESS;
	//	}
	//	/**
	//	 * <p>
	//	 * 删除。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>删除。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String del() {
	//		log.info("C301Action del.........");
	//		
	//		String id=request.getParameter("id");
	//		TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//		bean1.setId(id);
	//		String msg="1";
	//		try {
	//			msg=consultMsgManager.deleteDataById(bean1);
	//		} catch (Exception e) {
	//			msg=e.getMessage();
	//		}
	//		request.setAttribute("msg",msg);
	//		return SUCCESS;
	//	}
	
	
	
	/**
	 * BEAN类  咨询信息设定
	 * @param bean BEAN类  咨询信息
	 */
	//	/**
	//	 * <p>
	//	 * 编辑。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>编辑。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String edit() {
	//		log.info("C301Action edit.........");
	//		String id=request.getParameter("id");
	//		if(id!=null){
	//			TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//			bean1.setId(id);
	//			bean=consultMsgManager.findDataById(bean1);
	//		}else{
	//			SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
	//			request.setAttribute("date", sdf.format(new Date()));
	//		}
	//		return "edit";
	//	}
	//	/**
	//	 * <p>
	//	 * 信息保存
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>新增。</div>
	//	 * <div>修改。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String save() {
	//		log.info("C301Action edit.........");
	//		if(bean!=null){
	//			String msg="1";
	//			try {
	//					BaseUserBean user = (BaseUserBean) request.getSession().getAttribute(CommonConstant.SESSION_KEY_USER_CUSTOMER_SERVICE);
	//					if(user!=null){
	//						bean.setCreate_ip(getIpAddr());
	//						bean.setCreate_id(user.getUser_id());
	//						bean.setUpdate_ip(getIpAddr());
	//						bean.setUpdate_id(user.getUser_id());
	//					}
	//					msg=consultMsgManager.saveOrUpdateData(bean);
	//			} catch (Exception e) {
	//				msg=e.getMessage();
	//			}
	//			request.setAttribute("msg",msg);
	//		}else{
	//			request.setAttribute("msg", "信息保存失败!");
	//		}
	//		return SUCCESS;
	//	}
	//	/**
	//	 * <p>
	//	 * 删除。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>删除。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String del() {
	//		log.info("C301Action del.........");
	//		
	//		String id=request.getParameter("id");
	//		TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//		bean1.setId(id);
	//		String msg="1";
	//		try {
	//			msg=consultMsgManager.deleteDataById(bean1);
	//		} catch (Exception e) {
	//			msg=e.getMessage();
	//		}
	//		request.setAttribute("msg",msg);
	//		return SUCCESS;
	//	}
	
	
	
	/**
	 * BEAN类  咨询信息取得
	 * @return BEAN类  咨询信息
	 */
	//	/**
	//	 * <p>
	//	 * 编辑。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>编辑。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String edit() {
	//		log.info("C301Action edit.........");
	//		String id=request.getParameter("id");
	//		if(id!=null){
	//			TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//			bean1.setId(id);
	//			bean=consultMsgManager.findDataById(bean1);
	//		}else{
	//			SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
	//			request.setAttribute("date", sdf.format(new Date()));
	//		}
	//		return "edit";
	//	}
	//	/**
	//	 * <p>
	//	 * 信息保存
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>新增。</div>
	//	 * <div>修改。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String save() {
	//		log.info("C301Action edit.........");
	//		if(bean!=null){
	//			String msg="1";
	//			try {
	//					BaseUserBean user = (BaseUserBean) request.getSession().getAttribute(CommonConstant.SESSION_KEY_USER_CUSTOMER_SERVICE);
	//					if(user!=null){
	//						bean.setCreate_ip(getIpAddr());
	//						bean.setCreate_id(user.getUser_id());
	//						bean.setUpdate_ip(getIpAddr());
	//						bean.setUpdate_id(user.getUser_id());
	//					}
	//					msg=consultMsgManager.saveOrUpdateData(bean);
	//			} catch (Exception e) {
	//				msg=e.getMessage();
	//			}
	//			request.setAttribute("msg",msg);
	//		}else{
	//			request.setAttribute("msg", "信息保存失败!");
	//		}
	//		return SUCCESS;
	//	}
	//	/**
	//	 * <p>
	//	 * 删除。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>删除。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String del() {
	//		log.info("C301Action del.........");
	//		
	//		String id=request.getParameter("id");
	//		TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//		bean1.setId(id);
	//		String msg="1";
	//		try {
	//			msg=consultMsgManager.deleteDataById(bean1);
	//		} catch (Exception e) {
	//			msg=e.getMessage();
	//		}
	//		request.setAttribute("msg",msg);
	//		return SUCCESS;
	//	}
	
	
	
	/**
	 * BEAN类  咨询信息设定
	 * @param bean BEAN类  咨询信息
	 */
	//	/**
	//	 * <p>
	//	 * 编辑。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>编辑。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String edit() {
	//		log.info("C301Action edit.........");
	//		String id=request.getParameter("id");
	//		if(id!=null){
	//			TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//			bean1.setId(id);
	//			bean=consultMsgManager.findDataById(bean1);
	//		}else{
	//			SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
	//			request.setAttribute("date", sdf.format(new Date()));
	//		}
	//		return "edit";
	//	}
	//	/**
	//	 * <p>
	//	 * 信息保存
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>新增。</div>
	//	 * <div>修改。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String save() {
	//		log.info("C301Action edit.........");
	//		if(bean!=null){
	//			String msg="1";
	//			try {
	//					BaseUserBean user = (BaseUserBean) request.getSession().getAttribute(CommonConstant.SESSION_KEY_USER_CUSTOMER_SERVICE);
	//					if(user!=null){
	//						bean.setCreate_ip(getIpAddr());
	//						bean.setCreate_id(user.getUser_id());
	//						bean.setUpdate_ip(getIpAddr());
	//						bean.setUpdate_id(user.getUser_id());
	//					}
	//					msg=consultMsgManager.saveOrUpdateData(bean);
	//			} catch (Exception e) {
	//				msg=e.getMessage();
	//			}
	//			request.setAttribute("msg",msg);
	//		}else{
	//			request.setAttribute("msg", "信息保存失败!");
	//		}
	//		return SUCCESS;
	//	}
	//	/**
	//	 * <p>
	//	 * 删除。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>删除。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String del() {
	//		log.info("C301Action del.........");
	//		
	//		String id=request.getParameter("id");
	//		TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//		bean1.setId(id);
	//		String msg="1";
	//		try {
	//			msg=consultMsgManager.deleteDataById(bean1);
	//		} catch (Exception e) {
	//			msg=e.getMessage();
	//		}
	//		request.setAttribute("msg",msg);
	//		return SUCCESS;
	//	}
	
	
	
	/**
	 * BEAN类  咨询信息取得
	 * @return BEAN类  咨询信息
	 */
	//	/**
	//	 * <p>
	//	 * 编辑。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>编辑。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String edit() {
	//		log.info("C301Action edit.........");
	//		String id=request.getParameter("id");
	//		if(id!=null){
	//			TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//			bean1.setId(id);
	//			bean=consultMsgManager.findDataById(bean1);
	//		}else{
	//			SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
	//			request.setAttribute("date", sdf.format(new Date()));
	//		}
	//		return "edit";
	//	}
	//	/**
	//	 * <p>
	//	 * 信息保存
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>新增。</div>
	//	 * <div>修改。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String save() {
	//		log.info("C301Action edit.........");
	//		if(bean!=null){
	//			String msg="1";
	//			try {
	//					BaseUserBean user = (BaseUserBean) request.getSession().getAttribute(CommonConstant.SESSION_KEY_USER_CUSTOMER_SERVICE);
	//					if(user!=null){
	//						bean.setCreate_ip(getIpAddr());
	//						bean.setCreate_id(user.getUser_id());
	//						bean.setUpdate_ip(getIpAddr());
	//						bean.setUpdate_id(user.getUser_id());
	//					}
	//					msg=consultMsgManager.saveOrUpdateData(bean);
	//			} catch (Exception e) {
	//				msg=e.getMessage();
	//			}
	//			request.setAttribute("msg",msg);
	//		}else{
	//			request.setAttribute("msg", "信息保存失败!");
	//		}
	//		return SUCCESS;
	//	}
	//	/**
	//	 * <p>
	//	 * 删除。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>删除。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String del() {
	//		log.info("C301Action del.........");
	//		
	//		String id=request.getParameter("id");
	//		TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//		bean1.setId(id);
	//		String msg="1";
	//		try {
	//			msg=consultMsgManager.deleteDataById(bean1);
	//		} catch (Exception e) {
	//			msg=e.getMessage();
	//		}
	//		request.setAttribute("msg",msg);
	//		return SUCCESS;
	//	}
	
	
	
	/**
	 * BEAN类  咨询信息取得
	 * @return BEAN类  咨询信息
	 */
	//	/**
	//	 * <p>
	//	 * 编辑。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>编辑。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String edit() {
	//		log.info("C301Action edit.........");
	//		String id=request.getParameter("id");
	//		if(id!=null){
	//			TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//			bean1.setId(id);
	//			bean=consultMsgManager.findDataById(bean1);
	//		}else{
	//			SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
	//			request.setAttribute("date", sdf.format(new Date()));
	//		}
	//		return "edit";
	//	}
	//	/**
	//	 * <p>
	//	 * 信息保存
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>新增。</div>
	//	 * <div>修改。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String save() {
	//		log.info("C301Action edit.........");
	//		if(bean!=null){
	//			String msg="1";
	//			try {
	//					BaseUserBean user = (BaseUserBean) request.getSession().getAttribute(CommonConstant.SESSION_KEY_USER_CUSTOMER_SERVICE);
	//					if(user!=null){
	//						bean.setCreate_ip(getIpAddr());
	//						bean.setCreate_id(user.getUser_id());
	//						bean.setUpdate_ip(getIpAddr());
	//						bean.setUpdate_id(user.getUser_id());
	//					}
	//					msg=consultMsgManager.saveOrUpdateData(bean);
	//			} catch (Exception e) {
	//				msg=e.getMessage();
	//			}
	//			request.setAttribute("msg",msg);
	//		}else{
	//			request.setAttribute("msg", "信息保存失败!");
	//		}
	//		return SUCCESS;
	//	}
	//	/**
	//	 * <p>
	//	 * 删除。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>删除。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String del() {
	//		log.info("C301Action del.........");
	//		
	//		String id=request.getParameter("id");
	//		TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//		bean1.setId(id);
	//		String msg="1";
	//		try {
	//			msg=consultMsgManager.deleteDataById(bean1);
	//		} catch (Exception e) {
	//			msg=e.getMessage();
	//		}
	//		request.setAttribute("msg",msg);
	//		return SUCCESS;
	//	}
	
	
	
	/**
	 * BEAN类  咨询信息设定
	 * @param bean BEAN类  咨询信息
	 */
	//	/**
	//	 * <p>
	//	 * 编辑。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>编辑。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String edit() {
	//		log.info("C301Action edit.........");
	//		String id=request.getParameter("id");
	//		if(id!=null){
	//			TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//			bean1.setId(id);
	//			bean=consultMsgManager.findDataById(bean1);
	//		}else{
	//			SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
	//			request.setAttribute("date", sdf.format(new Date()));
	//		}
	//		return "edit";
	//	}
	//	/**
	//	 * <p>
	//	 * 信息保存
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>新增。</div>
	//	 * <div>修改。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String save() {
	//		log.info("C301Action edit.........");
	//		if(bean!=null){
	//			String msg="1";
	//			try {
	//					BaseUserBean user = (BaseUserBean) request.getSession().getAttribute(CommonConstant.SESSION_KEY_USER_CUSTOMER_SERVICE);
	//					if(user!=null){
	//						bean.setCreate_ip(getIpAddr());
	//						bean.setCreate_id(user.getUser_id());
	//						bean.setUpdate_ip(getIpAddr());
	//						bean.setUpdate_id(user.getUser_id());
	//					}
	//					msg=consultMsgManager.saveOrUpdateData(bean);
	//			} catch (Exception e) {
	//				msg=e.getMessage();
	//			}
	//			request.setAttribute("msg",msg);
	//		}else{
	//			request.setAttribute("msg", "信息保存失败!");
	//		}
	//		return SUCCESS;
	//	}
	//	/**
	//	 * <p>
	//	 * 删除。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>删除。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String del() {
	//		log.info("C301Action del.........");
	//		
	//		String id=request.getParameter("id");
	//		TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//		bean1.setId(id);
	//		String msg="1";
	//		try {
	//			msg=consultMsgManager.deleteDataById(bean1);
	//		} catch (Exception e) {
	//			msg=e.getMessage();
	//		}
	//		request.setAttribute("msg",msg);
	//		return SUCCESS;
	//	}
	
	
	
	/**
	 * BEAN类  咨询信息取得
	 * @return BEAN类  咨询信息
	 */
	//	/**
	//	 * <p>
	//	 * 编辑。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>编辑。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String edit() {
	//		log.info("C301Action edit.........");
	//		String id=request.getParameter("id");
	//		if(id!=null){
	//			TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//			bean1.setId(id);
	//			bean=consultMsgManager.findDataById(bean1);
	//		}else{
	//			SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
	//			request.setAttribute("date", sdf.format(new Date()));
	//		}
	//		return "edit";
	//	}
	//	/**
	//	 * <p>
	//	 * 信息保存
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>新增。</div>
	//	 * <div>修改。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String save() {
	//		log.info("C301Action edit.........");
	//		if(bean!=null){
	//			String msg="1";
	//			try {
	//					BaseUserBean user = (BaseUserBean) request.getSession().getAttribute(CommonConstant.SESSION_KEY_USER_CUSTOMER_SERVICE);
	//					if(user!=null){
	//						bean.setCreate_ip(getIpAddr());
	//						bean.setCreate_id(user.getUser_id());
	//						bean.setUpdate_ip(getIpAddr());
	//						bean.setUpdate_id(user.getUser_id());
	//					}
	//					msg=consultMsgManager.saveOrUpdateData(bean);
	//			} catch (Exception e) {
	//				msg=e.getMessage();
	//			}
	//			request.setAttribute("msg",msg);
	//		}else{
	//			request.setAttribute("msg", "信息保存失败!");
	//		}
	//		return SUCCESS;
	//	}
	//	/**
	//	 * <p>
	//	 * 删除。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>删除。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String del() {
	//		log.info("C301Action del.........");
	//		
	//		String id=request.getParameter("id");
	//		TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//		bean1.setId(id);
	//		String msg="1";
	//		try {
	//			msg=consultMsgManager.deleteDataById(bean1);
	//		} catch (Exception e) {
	//			msg=e.getMessage();
	//		}
	//		request.setAttribute("msg",msg);
	//		return SUCCESS;
	//	}
	
	
	
	/**
	 * BEAN类  咨询信息取得
	 * @return BEAN类  咨询信息
	 */
	//	/**
	//	 * <p>
	//	 * 编辑。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>编辑。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String edit() {
	//		log.info("C301Action edit.........");
	//		String id=request.getParameter("id");
	//		if(id!=null){
	//			TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//			bean1.setId(id);
	//			bean=consultMsgManager.findDataById(bean1);
	//		}else{
	//			SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
	//			request.setAttribute("date", sdf.format(new Date()));
	//		}
	//		return "edit";
	//	}
	//	/**
	//	 * <p>
	//	 * 信息保存
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>新增。</div>
	//	 * <div>修改。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String save() {
	//		log.info("C301Action edit.........");
	//		if(bean!=null){
	//			String msg="1";
	//			try {
	//					BaseUserBean user = (BaseUserBean) request.getSession().getAttribute(CommonConstant.SESSION_KEY_USER_CUSTOMER_SERVICE);
	//					if(user!=null){
	//						bean.setCreate_ip(getIpAddr());
	//						bean.setCreate_id(user.getUser_id());
	//						bean.setUpdate_ip(getIpAddr());
	//						bean.setUpdate_id(user.getUser_id());
	//					}
	//					msg=consultMsgManager.saveOrUpdateData(bean);
	//			} catch (Exception e) {
	//				msg=e.getMessage();
	//			}
	//			request.setAttribute("msg",msg);
	//		}else{
	//			request.setAttribute("msg", "信息保存失败!");
	//		}
	//		return SUCCESS;
	//	}
	//	/**
	//	 * <p>
	//	 * 删除。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>删除。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String del() {
	//		log.info("C301Action del.........");
	//		
	//		String id=request.getParameter("id");
	//		TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//		bean1.setId(id);
	//		String msg="1";
	//		try {
	//			msg=consultMsgManager.deleteDataById(bean1);
	//		} catch (Exception e) {
	//			msg=e.getMessage();
	//		}
	//		request.setAttribute("msg",msg);
	//		return SUCCESS;
	//	}
	
	
	
	/**
	 * BEAN类  咨询信息设定
	 * @param bean BEAN类  咨询信息
	 */
	//	/**
	//	 * <p>
	//	 * 编辑。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>编辑。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String edit() {
	//		log.info("C301Action edit.........");
	//		String id=request.getParameter("id");
	//		if(id!=null){
	//			TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//			bean1.setId(id);
	//			bean=consultMsgManager.findDataById(bean1);
	//		}else{
	//			SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
	//			request.setAttribute("date", sdf.format(new Date()));
	//		}
	//		return "edit";
	//	}
	//	/**
	//	 * <p>
	//	 * 信息保存
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>新增。</div>
	//	 * <div>修改。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String save() {
	//		log.info("C301Action edit.........");
	//		if(bean!=null){
	//			String msg="1";
	//			try {
	//					BaseUserBean user = (BaseUserBean) request.getSession().getAttribute(CommonConstant.SESSION_KEY_USER_CUSTOMER_SERVICE);
	//					if(user!=null){
	//						bean.setCreate_ip(getIpAddr());
	//						bean.setCreate_id(user.getUser_id());
	//						bean.setUpdate_ip(getIpAddr());
	//						bean.setUpdate_id(user.getUser_id());
	//					}
	//					msg=consultMsgManager.saveOrUpdateData(bean);
	//			} catch (Exception e) {
	//				msg=e.getMessage();
	//			}
	//			request.setAttribute("msg",msg);
	//		}else{
	//			request.setAttribute("msg", "信息保存失败!");
	//		}
	//		return SUCCESS;
	//	}
	//	/**
	//	 * <p>
	//	 * 删除。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>删除。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String del() {
	//		log.info("C301Action del.........");
	//		
	//		String id=request.getParameter("id");
	//		TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//		bean1.setId(id);
	//		String msg="1";
	//		try {
	//			msg=consultMsgManager.deleteDataById(bean1);
	//		} catch (Exception e) {
	//			msg=e.getMessage();
	//		}
	//		request.setAttribute("msg",msg);
	//		return SUCCESS;
	//	}
	
	
	
	/**
	 * BEAN类  咨询信息取得
	 * @return BEAN类  咨询信息
	 */
	//	/**
	//	 * <p>
	//	 * 编辑。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>编辑。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String edit() {
	//		log.info("C301Action edit.........");
	//		String id=request.getParameter("id");
	//		if(id!=null){
	//			TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//			bean1.setId(id);
	//			bean=consultMsgManager.findDataById(bean1);
	//		}else{
	//			SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
	//			request.setAttribute("date", sdf.format(new Date()));
	//		}
	//		return "edit";
	//	}
	//	/**
	//	 * <p>
	//	 * 信息保存
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>新增。</div>
	//	 * <div>修改。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String save() {
	//		log.info("C301Action edit.........");
	//		if(bean!=null){
	//			String msg="1";
	//			try {
	//					BaseUserBean user = (BaseUserBean) request.getSession().getAttribute(CommonConstant.SESSION_KEY_USER_CUSTOMER_SERVICE);
	//					if(user!=null){
	//						bean.setCreate_ip(getIpAddr());
	//						bean.setCreate_id(user.getUser_id());
	//						bean.setUpdate_ip(getIpAddr());
	//						bean.setUpdate_id(user.getUser_id());
	//					}
	//					msg=consultMsgManager.saveOrUpdateData(bean);
	//			} catch (Exception e) {
	//				msg=e.getMessage();
	//			}
	//			request.setAttribute("msg",msg);
	//		}else{
	//			request.setAttribute("msg", "信息保存失败!");
	//		}
	//		return SUCCESS;
	//	}
	//	/**
	//	 * <p>
	//	 * 删除。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>删除。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String del() {
	//		log.info("C301Action del.........");
	//		
	//		String id=request.getParameter("id");
	//		TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//		bean1.setId(id);
	//		String msg="1";
	//		try {
	//			msg=consultMsgManager.deleteDataById(bean1);
	//		} catch (Exception e) {
	//			msg=e.getMessage();
	//		}
	//		request.setAttribute("msg",msg);
	//		return SUCCESS;
	//	}
	
	
	
	/**
	 * BEAN类  咨询信息设定
	 * @param bean BEAN类  咨询信息
	 */
	//	/**
	//	 * <p>
	//	 * 编辑。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>编辑。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String edit() {
	//		log.info("C301Action edit.........");
	//		String id=request.getParameter("id");
	//		if(id!=null){
	//			TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//			bean1.setId(id);
	//			bean=consultMsgManager.findDataById(bean1);
	//		}else{
	//			SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
	//			request.setAttribute("date", sdf.format(new Date()));
	//		}
	//		return "edit";
	//	}
	//	/**
	//	 * <p>
	//	 * 信息保存
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>新增。</div>
	//	 * <div>修改。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String save() {
	//		log.info("C301Action edit.........");
	//		if(bean!=null){
	//			String msg="1";
	//			try {
	//					BaseUserBean user = (BaseUserBean) request.getSession().getAttribute(CommonConstant.SESSION_KEY_USER_CUSTOMER_SERVICE);
	//					if(user!=null){
	//						bean.setCreate_ip(getIpAddr());
	//						bean.setCreate_id(user.getUser_id());
	//						bean.setUpdate_ip(getIpAddr());
	//						bean.setUpdate_id(user.getUser_id());
	//					}
	//					msg=consultMsgManager.saveOrUpdateData(bean);
	//			} catch (Exception e) {
	//				msg=e.getMessage();
	//			}
	//			request.setAttribute("msg",msg);
	//		}else{
	//			request.setAttribute("msg", "信息保存失败!");
	//		}
	//		return SUCCESS;
	//	}
	//	/**
	//	 * <p>
	//	 * 删除。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>删除。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String del() {
	//		log.info("C301Action del.........");
	//		
	//		String id=request.getParameter("id");
	//		TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//		bean1.setId(id);
	//		String msg="1";
	//		try {
	//			msg=consultMsgManager.deleteDataById(bean1);
	//		} catch (Exception e) {
	//			msg=e.getMessage();
	//		}
	//		request.setAttribute("msg",msg);
	//		return SUCCESS;
	//	}
	
	
	
	/**
	 * BEAN类  咨询信息取得
	 * @return BEAN类  咨询信息
	 */
	//	/**
	//	 * <p>
	//	 * 编辑。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>编辑。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String edit() {
	//		log.info("C301Action edit.........");
	//		String id=request.getParameter("id");
	//		if(id!=null){
	//			TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//			bean1.setId(id);
	//			bean=consultMsgManager.findDataById(bean1);
	//		}else{
	//			SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
	//			request.setAttribute("date", sdf.format(new Date()));
	//		}
	//		return "edit";
	//	}
	//	/**
	//	 * <p>
	//	 * 信息保存
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>新增。</div>
	//	 * <div>修改。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String save() {
	//		log.info("C301Action edit.........");
	//		if(bean!=null){
	//			String msg="1";
	//			try {
	//					BaseUserBean user = (BaseUserBean) request.getSession().getAttribute(CommonConstant.SESSION_KEY_USER_CUSTOMER_SERVICE);
	//					if(user!=null){
	//						bean.setCreate_ip(getIpAddr());
	//						bean.setCreate_id(user.getUser_id());
	//						bean.setUpdate_ip(getIpAddr());
	//						bean.setUpdate_id(user.getUser_id());
	//					}
	//					msg=consultMsgManager.saveOrUpdateData(bean);
	//			} catch (Exception e) {
	//				msg=e.getMessage();
	//			}
	//			request.setAttribute("msg",msg);
	//		}else{
	//			request.setAttribute("msg", "信息保存失败!");
	//		}
	//		return SUCCESS;
	//	}
	//	/**
	//	 * <p>
	//	 * 删除。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>删除。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String del() {
	//		log.info("C301Action del.........");
	//		
	//		String id=request.getParameter("id");
	//		TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//		bean1.setId(id);
	//		String msg="1";
	//		try {
	//			msg=consultMsgManager.deleteDataById(bean1);
	//		} catch (Exception e) {
	//			msg=e.getMessage();
	//		}
	//		request.setAttribute("msg",msg);
	//		return SUCCESS;
	//	}
	
	
	
	/**
	 * BEAN类  咨询信息取得
	 * @return BEAN类  咨询信息
	 */
	//	/**
	//	 * <p>
	//	 * 编辑。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>编辑。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String edit() {
	//		log.info("C301Action edit.........");
	//		String id=request.getParameter("id");
	//		if(id!=null){
	//			TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//			bean1.setId(id);
	//			bean=consultMsgManager.findDataById(bean1);
	//		}else{
	//			SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
	//			request.setAttribute("date", sdf.format(new Date()));
	//		}
	//		return "edit";
	//	}
	//	/**
	//	 * <p>
	//	 * 信息保存
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>新增。</div>
	//	 * <div>修改。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String save() {
	//		log.info("C301Action edit.........");
	//		if(bean!=null){
	//			String msg="1";
	//			try {
	//					BaseUserBean user = (BaseUserBean) request.getSession().getAttribute(CommonConstant.SESSION_KEY_USER_CUSTOMER_SERVICE);
	//					if(user!=null){
	//						bean.setCreate_ip(getIpAddr());
	//						bean.setCreate_id(user.getUser_id());
	//						bean.setUpdate_ip(getIpAddr());
	//						bean.setUpdate_id(user.getUser_id());
	//					}
	//					msg=consultMsgManager.saveOrUpdateData(bean);
	//			} catch (Exception e) {
	//				msg=e.getMessage();
	//			}
	//			request.setAttribute("msg",msg);
	//		}else{
	//			request.setAttribute("msg", "信息保存失败!");
	//		}
	//		return SUCCESS;
	//	}
	//	/**
	//	 * <p>
	//	 * 删除。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>删除。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String del() {
	//		log.info("C301Action del.........");
	//		
	//		String id=request.getParameter("id");
	//		TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//		bean1.setId(id);
	//		String msg="1";
	//		try {
	//			msg=consultMsgManager.deleteDataById(bean1);
	//		} catch (Exception e) {
	//			msg=e.getMessage();
	//		}
	//		request.setAttribute("msg",msg);
	//		return SUCCESS;
	//	}
	
	
	
	/**
	 * BEAN类  咨询信息设定
	 * @param bean BEAN类  咨询信息
	 */
	//	/**
	//	 * <p>
	//	 * 编辑。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>编辑。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String edit() {
	//		log.info("C301Action edit.........");
	//		String id=request.getParameter("id");
	//		if(id!=null){
	//			TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//			bean1.setId(id);
	//			bean=consultMsgManager.findDataById(bean1);
	//		}else{
	//			SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
	//			request.setAttribute("date", sdf.format(new Date()));
	//		}
	//		return "edit";
	//	}
	//	/**
	//	 * <p>
	//	 * 信息保存
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>新增。</div>
	//	 * <div>修改。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String save() {
	//		log.info("C301Action edit.........");
	//		if(bean!=null){
	//			String msg="1";
	//			try {
	//					BaseUserBean user = (BaseUserBean) request.getSession().getAttribute(CommonConstant.SESSION_KEY_USER_CUSTOMER_SERVICE);
	//					if(user!=null){
	//						bean.setCreate_ip(getIpAddr());
	//						bean.setCreate_id(user.getUser_id());
	//						bean.setUpdate_ip(getIpAddr());
	//						bean.setUpdate_id(user.getUser_id());
	//					}
	//					msg=consultMsgManager.saveOrUpdateData(bean);
	//			} catch (Exception e) {
	//				msg=e.getMessage();
	//			}
	//			request.setAttribute("msg",msg);
	//		}else{
	//			request.setAttribute("msg", "信息保存失败!");
	//		}
	//		return SUCCESS;
	//	}
	//	/**
	//	 * <p>
	//	 * 删除。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>删除。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String del() {
	//		log.info("C301Action del.........");
	//		
	//		String id=request.getParameter("id");
	//		TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//		bean1.setId(id);
	//		String msg="1";
	//		try {
	//			msg=consultMsgManager.deleteDataById(bean1);
	//		} catch (Exception e) {
	//			msg=e.getMessage();
	//		}
	//		request.setAttribute("msg",msg);
	//		return SUCCESS;
	//	}
	
	
	
	/**
	 * BEAN类  咨询信息取得
	 * @return BEAN类  咨询信息
	 */
	//	/**
	//	 * <p>
	//	 * 编辑。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>编辑。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String edit() {
	//		log.info("C301Action edit.........");
	//		String id=request.getParameter("id");
	//		if(id!=null){
	//			TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//			bean1.setId(id);
	//			bean=consultMsgManager.findDataById(bean1);
	//		}else{
	//			SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
	//			request.setAttribute("date", sdf.format(new Date()));
	//		}
	//		return "edit";
	//	}
	//	/**
	//	 * <p>
	//	 * 信息保存
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>新增。</div>
	//	 * <div>修改。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String save() {
	//		log.info("C301Action edit.........");
	//		if(bean!=null){
	//			String msg="1";
	//			try {
	//					BaseUserBean user = (BaseUserBean) request.getSession().getAttribute(CommonConstant.SESSION_KEY_USER_CUSTOMER_SERVICE);
	//					if(user!=null){
	//						bean.setCreate_ip(getIpAddr());
	//						bean.setCreate_id(user.getUser_id());
	//						bean.setUpdate_ip(getIpAddr());
	//						bean.setUpdate_id(user.getUser_id());
	//					}
	//					msg=consultMsgManager.saveOrUpdateData(bean);
	//			} catch (Exception e) {
	//				msg=e.getMessage();
	//			}
	//			request.setAttribute("msg",msg);
	//		}else{
	//			request.setAttribute("msg", "信息保存失败!");
	//		}
	//		return SUCCESS;
	//	}
	//	/**
	//	 * <p>
	//	 * 删除。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>删除。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String del() {
	//		log.info("C301Action del.........");
	//		
	//		String id=request.getParameter("id");
	//		TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//		bean1.setId(id);
	//		String msg="1";
	//		try {
	//			msg=consultMsgManager.deleteDataById(bean1);
	//		} catch (Exception e) {
	//			msg=e.getMessage();
	//		}
	//		request.setAttribute("msg",msg);
	//		return SUCCESS;
	//	}
	
	
	
	/**
	 * BEAN类  咨询信息取得
	 * @return BEAN类  咨询信息
	 */
	//	/**
	//	 * <p>
	//	 * 编辑。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>编辑。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String edit() {
	//		log.info("C301Action edit.........");
	//		String id=request.getParameter("id");
	//		if(id!=null){
	//			TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//			bean1.setId(id);
	//			bean=consultMsgManager.findDataById(bean1);
	//		}else{
	//			SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
	//			request.setAttribute("date", sdf.format(new Date()));
	//		}
	//		return "edit";
	//	}
	//	/**
	//	 * <p>
	//	 * 信息保存
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>新增。</div>
	//	 * <div>修改。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String save() {
	//		log.info("C301Action edit.........");
	//		if(bean!=null){
	//			String msg="1";
	//			try {
	//					BaseUserBean user = (BaseUserBean) request.getSession().getAttribute(CommonConstant.SESSION_KEY_USER_CUSTOMER_SERVICE);
	//					if(user!=null){
	//						bean.setCreate_ip(getIpAddr());
	//						bean.setCreate_id(user.getUser_id());
	//						bean.setUpdate_ip(getIpAddr());
	//						bean.setUpdate_id(user.getUser_id());
	//					}
	//					msg=consultMsgManager.saveOrUpdateData(bean);
	//			} catch (Exception e) {
	//				msg=e.getMessage();
	//			}
	//			request.setAttribute("msg",msg);
	//		}else{
	//			request.setAttribute("msg", "信息保存失败!");
	//		}
	//		return SUCCESS;
	//	}
	//	/**
	//	 * <p>
	//	 * 删除。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>删除。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String del() {
	//		log.info("C301Action del.........");
	//		
	//		String id=request.getParameter("id");
	//		TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//		bean1.setId(id);
	//		String msg="1";
	//		try {
	//			msg=consultMsgManager.deleteDataById(bean1);
	//		} catch (Exception e) {
	//			msg=e.getMessage();
	//		}
	//		request.setAttribute("msg",msg);
	//		return SUCCESS;
	//	}
	
	
	
	/**
	 * BEAN类  咨询信息设定
	 * @param bean BEAN类  咨询信息
	 */
	//	/**
	//	 * <p>
	//	 * 编辑。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>编辑。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String edit() {
	//		log.info("C301Action edit.........");
	//		String id=request.getParameter("id");
	//		if(id!=null){
	//			TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//			bean1.setId(id);
	//			bean=consultMsgManager.findDataById(bean1);
	//		}else{
	//			SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
	//			request.setAttribute("date", sdf.format(new Date()));
	//		}
	//		return "edit";
	//	}
	//	/**
	//	 * <p>
	//	 * 信息保存
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>新增。</div>
	//	 * <div>修改。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String save() {
	//		log.info("C301Action edit.........");
	//		if(bean!=null){
	//			String msg="1";
	//			try {
	//					BaseUserBean user = (BaseUserBean) request.getSession().getAttribute(CommonConstant.SESSION_KEY_USER_CUSTOMER_SERVICE);
	//					if(user!=null){
	//						bean.setCreate_ip(getIpAddr());
	//						bean.setCreate_id(user.getUser_id());
	//						bean.setUpdate_ip(getIpAddr());
	//						bean.setUpdate_id(user.getUser_id());
	//					}
	//					msg=consultMsgManager.saveOrUpdateData(bean);
	//			} catch (Exception e) {
	//				msg=e.getMessage();
	//			}
	//			request.setAttribute("msg",msg);
	//		}else{
	//			request.setAttribute("msg", "信息保存失败!");
	//		}
	//		return SUCCESS;
	//	}
	//	/**
	//	 * <p>
	//	 * 删除。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>删除。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String del() {
	//		log.info("C301Action del.........");
	//		
	//		String id=request.getParameter("id");
	//		TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//		bean1.setId(id);
	//		String msg="1";
	//		try {
	//			msg=consultMsgManager.deleteDataById(bean1);
	//		} catch (Exception e) {
	//			msg=e.getMessage();
	//		}
	//		request.setAttribute("msg",msg);
	//		return SUCCESS;
	//	}
	
	
	
	/**
	 * BEAN类  咨询信息取得
	 * @return BEAN类  咨询信息
	 */
	//	/**
	//	 * <p>
	//	 * 编辑。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>编辑。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String edit() {
	//		log.info("C301Action edit.........");
	//		String id=request.getParameter("id");
	//		if(id!=null){
	//			TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//			bean1.setId(id);
	//			bean=consultMsgManager.findDataById(bean1);
	//		}else{
	//			SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
	//			request.setAttribute("date", sdf.format(new Date()));
	//		}
	//		return "edit";
	//	}
	//	/**
	//	 * <p>
	//	 * 信息保存
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>新增。</div>
	//	 * <div>修改。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String save() {
	//		log.info("C301Action edit.........");
	//		if(bean!=null){
	//			String msg="1";
	//			try {
	//					BaseUserBean user = (BaseUserBean) request.getSession().getAttribute(CommonConstant.SESSION_KEY_USER_CUSTOMER_SERVICE);
	//					if(user!=null){
	//						bean.setCreate_ip(getIpAddr());
	//						bean.setCreate_id(user.getUser_id());
	//						bean.setUpdate_ip(getIpAddr());
	//						bean.setUpdate_id(user.getUser_id());
	//					}
	//					msg=consultMsgManager.saveOrUpdateData(bean);
	//			} catch (Exception e) {
	//				msg=e.getMessage();
	//			}
	//			request.setAttribute("msg",msg);
	//		}else{
	//			request.setAttribute("msg", "信息保存失败!");
	//		}
	//		return SUCCESS;
	//	}
	//	/**
	//	 * <p>
	//	 * 删除。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>删除。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String del() {
	//		log.info("C301Action del.........");
	//		
	//		String id=request.getParameter("id");
	//		TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//		bean1.setId(id);
	//		String msg="1";
	//		try {
	//			msg=consultMsgManager.deleteDataById(bean1);
	//		} catch (Exception e) {
	//			msg=e.getMessage();
	//		}
	//		request.setAttribute("msg",msg);
	//		return SUCCESS;
	//	}
	
	
	
	/**
	 * BEAN类  咨询信息设定
	 * @param bean BEAN类  咨询信息
	 */
	//	/**
	//	 * <p>
	//	 * 编辑。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>编辑。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String edit() {
	//		log.info("C301Action edit.........");
	//		String id=request.getParameter("id");
	//		if(id!=null){
	//			TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//			bean1.setId(id);
	//			bean=consultMsgManager.findDataById(bean1);
	//		}else{
	//			SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
	//			request.setAttribute("date", sdf.format(new Date()));
	//		}
	//		return "edit";
	//	}
	//	/**
	//	 * <p>
	//	 * 信息保存
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>新增。</div>
	//	 * <div>修改。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String save() {
	//		log.info("C301Action edit.........");
	//		if(bean!=null){
	//			String msg="1";
	//			try {
	//					BaseUserBean user = (BaseUserBean) request.getSession().getAttribute(CommonConstant.SESSION_KEY_USER_CUSTOMER_SERVICE);
	//					if(user!=null){
	//						bean.setCreate_ip(getIpAddr());
	//						bean.setCreate_id(user.getUser_id());
	//						bean.setUpdate_ip(getIpAddr());
	//						bean.setUpdate_id(user.getUser_id());
	//					}
	//					msg=consultMsgManager.saveOrUpdateData(bean);
	//			} catch (Exception e) {
	//				msg=e.getMessage();
	//			}
	//			request.setAttribute("msg",msg);
	//		}else{
	//			request.setAttribute("msg", "信息保存失败!");
	//		}
	//		return SUCCESS;
	//	}
	//	/**
	//	 * <p>
	//	 * 删除。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>删除。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String del() {
	//		log.info("C301Action del.........");
	//		
	//		String id=request.getParameter("id");
	//		TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//		bean1.setId(id);
	//		String msg="1";
	//		try {
	//			msg=consultMsgManager.deleteDataById(bean1);
	//		} catch (Exception e) {
	//			msg=e.getMessage();
	//		}
	//		request.setAttribute("msg",msg);
	//		return SUCCESS;
	//	}
	
	
	
	/**
	 * BEAN类  咨询信息取得
	 * @return BEAN类  咨询信息
	 */
	//	/**
	//	 * <p>
	//	 * 编辑。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>编辑。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String edit() {
	//		log.info("C301Action edit.........");
	//		String id=request.getParameter("id");
	//		if(id!=null){
	//			TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//			bean1.setId(id);
	//			bean=consultMsgManager.findDataById(bean1);
	//		}else{
	//			SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
	//			request.setAttribute("date", sdf.format(new Date()));
	//		}
	//		return "edit";
	//	}
	//	/**
	//	 * <p>
	//	 * 信息保存
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>新增。</div>
	//	 * <div>修改。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String save() {
	//		log.info("C301Action edit.........");
	//		if(bean!=null){
	//			String msg="1";
	//			try {
	//					BaseUserBean user = (BaseUserBean) request.getSession().getAttribute(CommonConstant.SESSION_KEY_USER_CUSTOMER_SERVICE);
	//					if(user!=null){
	//						bean.setCreate_ip(getIpAddr());
	//						bean.setCreate_id(user.getUser_id());
	//						bean.setUpdate_ip(getIpAddr());
	//						bean.setUpdate_id(user.getUser_id());
	//					}
	//					msg=consultMsgManager.saveOrUpdateData(bean);
	//			} catch (Exception e) {
	//				msg=e.getMessage();
	//			}
	//			request.setAttribute("msg",msg);
	//		}else{
	//			request.setAttribute("msg", "信息保存失败!");
	//		}
	//		return SUCCESS;
	//	}
	//	/**
	//	 * <p>
	//	 * 删除。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>删除。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String del() {
	//		log.info("C301Action del.........");
	//		
	//		String id=request.getParameter("id");
	//		TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//		bean1.setId(id);
	//		String msg="1";
	//		try {
	//			msg=consultMsgManager.deleteDataById(bean1);
	//		} catch (Exception e) {
	//			msg=e.getMessage();
	//		}
	//		request.setAttribute("msg",msg);
	//		return SUCCESS;
	//	}
	
	
	
	/**
	 * BEAN类  咨询信息取得
	 * @return BEAN类  咨询信息
	 */
	//	/**
	//	 * <p>
	//	 * 编辑。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>编辑。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String edit() {
	//		log.info("C301Action edit.........");
	//		String id=request.getParameter("id");
	//		if(id!=null){
	//			TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//			bean1.setId(id);
	//			bean=consultMsgManager.findDataById(bean1);
	//		}else{
	//			SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
	//			request.setAttribute("date", sdf.format(new Date()));
	//		}
	//		return "edit";
	//	}
	//	/**
	//	 * <p>
	//	 * 信息保存
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>新增。</div>
	//	 * <div>修改。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String save() {
	//		log.info("C301Action edit.........");
	//		if(bean!=null){
	//			String msg="1";
	//			try {
	//					BaseUserBean user = (BaseUserBean) request.getSession().getAttribute(CommonConstant.SESSION_KEY_USER_CUSTOMER_SERVICE);
	//					if(user!=null){
	//						bean.setCreate_ip(getIpAddr());
	//						bean.setCreate_id(user.getUser_id());
	//						bean.setUpdate_ip(getIpAddr());
	//						bean.setUpdate_id(user.getUser_id());
	//					}
	//					msg=consultMsgManager.saveOrUpdateData(bean);
	//			} catch (Exception e) {
	//				msg=e.getMessage();
	//			}
	//			request.setAttribute("msg",msg);
	//		}else{
	//			request.setAttribute("msg", "信息保存失败!");
	//		}
	//		return SUCCESS;
	//	}
	//	/**
	//	 * <p>
	//	 * 删除。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>删除。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String del() {
	//		log.info("C301Action del.........");
	//		
	//		String id=request.getParameter("id");
	//		TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//		bean1.setId(id);
	//		String msg="1";
	//		try {
	//			msg=consultMsgManager.deleteDataById(bean1);
	//		} catch (Exception e) {
	//			msg=e.getMessage();
	//		}
	//		request.setAttribute("msg",msg);
	//		return SUCCESS;
	//	}
	
	
	
	/**
	 * BEAN类  咨询信息设定
	 * @param bean BEAN类  咨询信息
	 */
	//	/**
	//	 * <p>
	//	 * 编辑。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>编辑。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String edit() {
	//		log.info("C301Action edit.........");
	//		String id=request.getParameter("id");
	//		if(id!=null){
	//			TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//			bean1.setId(id);
	//			bean=consultMsgManager.findDataById(bean1);
	//		}else{
	//			SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
	//			request.setAttribute("date", sdf.format(new Date()));
	//		}
	//		return "edit";
	//	}
	//	/**
	//	 * <p>
	//	 * 信息保存
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>新增。</div>
	//	 * <div>修改。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String save() {
	//		log.info("C301Action edit.........");
	//		if(bean!=null){
	//			String msg="1";
	//			try {
	//					BaseUserBean user = (BaseUserBean) request.getSession().getAttribute(CommonConstant.SESSION_KEY_USER_CUSTOMER_SERVICE);
	//					if(user!=null){
	//						bean.setCreate_ip(getIpAddr());
	//						bean.setCreate_id(user.getUser_id());
	//						bean.setUpdate_ip(getIpAddr());
	//						bean.setUpdate_id(user.getUser_id());
	//					}
	//					msg=consultMsgManager.saveOrUpdateData(bean);
	//			} catch (Exception e) {
	//				msg=e.getMessage();
	//			}
	//			request.setAttribute("msg",msg);
	//		}else{
	//			request.setAttribute("msg", "信息保存失败!");
	//		}
	//		return SUCCESS;
	//	}
	//	/**
	//	 * <p>
	//	 * 删除。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>删除。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String del() {
	//		log.info("C301Action del.........");
	//		
	//		String id=request.getParameter("id");
	//		TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//		bean1.setId(id);
	//		String msg="1";
	//		try {
	//			msg=consultMsgManager.deleteDataById(bean1);
	//		} catch (Exception e) {
	//			msg=e.getMessage();
	//		}
	//		request.setAttribute("msg",msg);
	//		return SUCCESS;
	//	}
	
	
	
	/**
	 * BEAN类  咨询信息取得
	 * @return BEAN类  咨询信息
	 */
	//	/**
	//	 * <p>
	//	 * 编辑。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>编辑。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String edit() {
	//		log.info("C301Action edit.........");
	//		String id=request.getParameter("id");
	//		if(id!=null){
	//			TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//			bean1.setId(id);
	//			bean=consultMsgManager.findDataById(bean1);
	//		}else{
	//			SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
	//			request.setAttribute("date", sdf.format(new Date()));
	//		}
	//		return "edit";
	//	}
	//	/**
	//	 * <p>
	//	 * 信息保存
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>新增。</div>
	//	 * <div>修改。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String save() {
	//		log.info("C301Action edit.........");
	//		if(bean!=null){
	//			String msg="1";
	//			try {
	//					BaseUserBean user = (BaseUserBean) request.getSession().getAttribute(CommonConstant.SESSION_KEY_USER_CUSTOMER_SERVICE);
	//					if(user!=null){
	//						bean.setCreate_ip(getIpAddr());
	//						bean.setCreate_id(user.getUser_id());
	//						bean.setUpdate_ip(getIpAddr());
	//						bean.setUpdate_id(user.getUser_id());
	//					}
	//					msg=consultMsgManager.saveOrUpdateData(bean);
	//			} catch (Exception e) {
	//				msg=e.getMessage();
	//			}
	//			request.setAttribute("msg",msg);
	//		}else{
	//			request.setAttribute("msg", "信息保存失败!");
	//		}
	//		return SUCCESS;
	//	}
	//	/**
	//	 * <p>
	//	 * 删除。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>删除。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String del() {
	//		log.info("C301Action del.........");
	//		
	//		String id=request.getParameter("id");
	//		TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//		bean1.setId(id);
	//		String msg="1";
	//		try {
	//			msg=consultMsgManager.deleteDataById(bean1);
	//		} catch (Exception e) {
	//			msg=e.getMessage();
	//		}
	//		request.setAttribute("msg",msg);
	//		return SUCCESS;
	//	}
	
	
	
	/**
	 * BEAN类  咨询信息设定
	 * @param bean BEAN类  咨询信息
	 */
	//	/**
	//	 * <p>
	//	 * 编辑。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>编辑。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String edit() {
	//		log.info("C301Action edit.........");
	//		String id=request.getParameter("id");
	//		if(id!=null){
	//			TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//			bean1.setId(id);
	//			bean=consultMsgManager.findDataById(bean1);
	//		}else{
	//			SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
	//			request.setAttribute("date", sdf.format(new Date()));
	//		}
	//		return "edit";
	//	}
	//	/**
	//	 * <p>
	//	 * 信息保存
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>新增。</div>
	//	 * <div>修改。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String save() {
	//		log.info("C301Action edit.........");
	//		if(bean!=null){
	//			String msg="1";
	//			try {
	//					BaseUserBean user = (BaseUserBean) request.getSession().getAttribute(CommonConstant.SESSION_KEY_USER_CUSTOMER_SERVICE);
	//					if(user!=null){
	//						bean.setCreate_ip(getIpAddr());
	//						bean.setCreate_id(user.getUser_id());
	//						bean.setUpdate_ip(getIpAddr());
	//						bean.setUpdate_id(user.getUser_id());
	//					}
	//					msg=consultMsgManager.saveOrUpdateData(bean);
	//			} catch (Exception e) {
	//				msg=e.getMessage();
	//			}
	//			request.setAttribute("msg",msg);
	//		}else{
	//			request.setAttribute("msg", "信息保存失败!");
	//		}
	//		return SUCCESS;
	//	}
	//	/**
	//	 * <p>
	//	 * 删除。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>删除。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String del() {
	//		log.info("C301Action del.........");
	//		
	//		String id=request.getParameter("id");
	//		TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//		bean1.setId(id);
	//		String msg="1";
	//		try {
	//			msg=consultMsgManager.deleteDataById(bean1);
	//		} catch (Exception e) {
	//			msg=e.getMessage();
	//		}
	//		request.setAttribute("msg",msg);
	//		return SUCCESS;
	//	}
	
	
	
	/**
	 * BEAN类  咨询信息取得
	 * @return BEAN类  咨询信息
	 */
	//	/**
	//	 * <p>
	//	 * 编辑。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>编辑。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String edit() {
	//		log.info("C301Action edit.........");
	//		String id=request.getParameter("id");
	//		if(id!=null){
	//			TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//			bean1.setId(id);
	//			bean=consultMsgManager.findDataById(bean1);
	//		}else{
	//			SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
	//			request.setAttribute("date", sdf.format(new Date()));
	//		}
	//		return "edit";
	//	}
	//	/**
	//	 * <p>
	//	 * 信息保存
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>新增。</div>
	//	 * <div>修改。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String save() {
	//		log.info("C301Action edit.........");
	//		if(bean!=null){
	//			String msg="1";
	//			try {
	//					BaseUserBean user = (BaseUserBean) request.getSession().getAttribute(CommonConstant.SESSION_KEY_USER_CUSTOMER_SERVICE);
	//					if(user!=null){
	//						bean.setCreate_ip(getIpAddr());
	//						bean.setCreate_id(user.getUser_id());
	//						bean.setUpdate_ip(getIpAddr());
	//						bean.setUpdate_id(user.getUser_id());
	//					}
	//					msg=consultMsgManager.saveOrUpdateData(bean);
	//			} catch (Exception e) {
	//				msg=e.getMessage();
	//			}
	//			request.setAttribute("msg",msg);
	//		}else{
	//			request.setAttribute("msg", "信息保存失败!");
	//		}
	//		return SUCCESS;
	//	}
	//	/**
	//	 * <p>
	//	 * 删除。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>删除。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String del() {
	//		log.info("C301Action del.........");
	//		
	//		String id=request.getParameter("id");
	//		TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//		bean1.setId(id);
	//		String msg="1";
	//		try {
	//			msg=consultMsgManager.deleteDataById(bean1);
	//		} catch (Exception e) {
	//			msg=e.getMessage();
	//		}
	//		request.setAttribute("msg",msg);
	//		return SUCCESS;
	//	}
	
	
	
	/**
	 * BEAN类  咨询信息取得
	 * @return BEAN类  咨询信息
	 */
	//	/**
	//	 * <p>
	//	 * 编辑。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>编辑。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String edit() {
	//		log.info("C301Action edit.........");
	//		String id=request.getParameter("id");
	//		if(id!=null){
	//			TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//			bean1.setId(id);
	//			bean=consultMsgManager.findDataById(bean1);
	//		}else{
	//			SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
	//			request.setAttribute("date", sdf.format(new Date()));
	//		}
	//		return "edit";
	//	}
	//	/**
	//	 * <p>
	//	 * 信息保存
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>新增。</div>
	//	 * <div>修改。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String save() {
	//		log.info("C301Action edit.........");
	//		if(bean!=null){
	//			String msg="1";
	//			try {
	//					BaseUserBean user = (BaseUserBean) request.getSession().getAttribute(CommonConstant.SESSION_KEY_USER_CUSTOMER_SERVICE);
	//					if(user!=null){
	//						bean.setCreate_ip(getIpAddr());
	//						bean.setCreate_id(user.getUser_id());
	//						bean.setUpdate_ip(getIpAddr());
	//						bean.setUpdate_id(user.getUser_id());
	//					}
	//					msg=consultMsgManager.saveOrUpdateData(bean);
	//			} catch (Exception e) {
	//				msg=e.getMessage();
	//			}
	//			request.setAttribute("msg",msg);
	//		}else{
	//			request.setAttribute("msg", "信息保存失败!");
	//		}
	//		return SUCCESS;
	//	}
	//	/**
	//	 * <p>
	//	 * 删除。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>删除。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String del() {
	//		log.info("C301Action del.........");
	//		
	//		String id=request.getParameter("id");
	//		TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//		bean1.setId(id);
	//		String msg="1";
	//		try {
	//			msg=consultMsgManager.deleteDataById(bean1);
	//		} catch (Exception e) {
	//			msg=e.getMessage();
	//		}
	//		request.setAttribute("msg",msg);
	//		return SUCCESS;
	//	}
	
	
	
	/**
	 * BEAN类  咨询信息设定
	 * @param bean BEAN类  咨询信息
	 */
	//	/**
	//	 * <p>
	//	 * 编辑。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>编辑。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String edit() {
	//		log.info("C301Action edit.........");
	//		String id=request.getParameter("id");
	//		if(id!=null){
	//			TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//			bean1.setId(id);
	//			bean=consultMsgManager.findDataById(bean1);
	//		}else{
	//			SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
	//			request.setAttribute("date", sdf.format(new Date()));
	//		}
	//		return "edit";
	//	}
	//	/**
	//	 * <p>
	//	 * 信息保存
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>新增。</div>
	//	 * <div>修改。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String save() {
	//		log.info("C301Action edit.........");
	//		if(bean!=null){
	//			String msg="1";
	//			try {
	//					BaseUserBean user = (BaseUserBean) request.getSession().getAttribute(CommonConstant.SESSION_KEY_USER_CUSTOMER_SERVICE);
	//					if(user!=null){
	//						bean.setCreate_ip(getIpAddr());
	//						bean.setCreate_id(user.getUser_id());
	//						bean.setUpdate_ip(getIpAddr());
	//						bean.setUpdate_id(user.getUser_id());
	//					}
	//					msg=consultMsgManager.saveOrUpdateData(bean);
	//			} catch (Exception e) {
	//				msg=e.getMessage();
	//			}
	//			request.setAttribute("msg",msg);
	//		}else{
	//			request.setAttribute("msg", "信息保存失败!");
	//		}
	//		return SUCCESS;
	//	}
	//	/**
	//	 * <p>
	//	 * 删除。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>删除。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String del() {
	//		log.info("C301Action del.........");
	//		
	//		String id=request.getParameter("id");
	//		TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//		bean1.setId(id);
	//		String msg="1";
	//		try {
	//			msg=consultMsgManager.deleteDataById(bean1);
	//		} catch (Exception e) {
	//			msg=e.getMessage();
	//		}
	//		request.setAttribute("msg",msg);
	//		return SUCCESS;
	//	}
	
	
	
	/**
	 * BEAN类  咨询信息取得
	 * @return BEAN类  咨询信息
	 */
	//	/**
	//	 * <p>
	//	 * 编辑。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>编辑。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String edit() {
	//		log.info("C301Action edit.........");
	//		String id=request.getParameter("id");
	//		if(id!=null){
	//			TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//			bean1.setId(id);
	//			bean=consultMsgManager.findDataById(bean1);
	//		}else{
	//			SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
	//			request.setAttribute("date", sdf.format(new Date()));
	//		}
	//		return "edit";
	//	}
	//	/**
	//	 * <p>
	//	 * 信息保存
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>新增。</div>
	//	 * <div>修改。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String save() {
	//		log.info("C301Action edit.........");
	//		if(bean!=null){
	//			String msg="1";
	//			try {
	//					BaseUserBean user = (BaseUserBean) request.getSession().getAttribute(CommonConstant.SESSION_KEY_USER_CUSTOMER_SERVICE);
	//					if(user!=null){
	//						bean.setCreate_ip(getIpAddr());
	//						bean.setCreate_id(user.getUser_id());
	//						bean.setUpdate_ip(getIpAddr());
	//						bean.setUpdate_id(user.getUser_id());
	//					}
	//					msg=consultMsgManager.saveOrUpdateData(bean);
	//			} catch (Exception e) {
	//				msg=e.getMessage();
	//			}
	//			request.setAttribute("msg",msg);
	//		}else{
	//			request.setAttribute("msg", "信息保存失败!");
	//		}
	//		return SUCCESS;
	//	}
	//	/**
	//	 * <p>
	//	 * 删除。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>删除。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String del() {
	//		log.info("C301Action del.........");
	//		
	//		String id=request.getParameter("id");
	//		TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//		bean1.setId(id);
	//		String msg="1";
	//		try {
	//			msg=consultMsgManager.deleteDataById(bean1);
	//		} catch (Exception e) {
	//			msg=e.getMessage();
	//		}
	//		request.setAttribute("msg",msg);
	//		return SUCCESS;
	//	}
	
	
	
	/**
	 * BEAN类  咨询信息取得
	 * @return BEAN类  咨询信息
	 */
	//	/**
	//	 * <p>
	//	 * 编辑。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>编辑。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String edit() {
	//		log.info("C301Action edit.........");
	//		String id=request.getParameter("id");
	//		if(id!=null){
	//			TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//			bean1.setId(id);
	//			bean=consultMsgManager.findDataById(bean1);
	//		}else{
	//			SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
	//			request.setAttribute("date", sdf.format(new Date()));
	//		}
	//		return "edit";
	//	}
	//	/**
	//	 * <p>
	//	 * 信息保存
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>新增。</div>
	//	 * <div>修改。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String save() {
	//		log.info("C301Action edit.........");
	//		if(bean!=null){
	//			String msg="1";
	//			try {
	//					BaseUserBean user = (BaseUserBean) request.getSession().getAttribute(CommonConstant.SESSION_KEY_USER_CUSTOMER_SERVICE);
	//					if(user!=null){
	//						bean.setCreate_ip(getIpAddr());
	//						bean.setCreate_id(user.getUser_id());
	//						bean.setUpdate_ip(getIpAddr());
	//						bean.setUpdate_id(user.getUser_id());
	//					}
	//					msg=consultMsgManager.saveOrUpdateData(bean);
	//			} catch (Exception e) {
	//				msg=e.getMessage();
	//			}
	//			request.setAttribute("msg",msg);
	//		}else{
	//			request.setAttribute("msg", "信息保存失败!");
	//		}
	//		return SUCCESS;
	//	}
	//	/**
	//	 * <p>
	//	 * 删除。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>删除。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String del() {
	//		log.info("C301Action del.........");
	//		
	//		String id=request.getParameter("id");
	//		TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//		bean1.setId(id);
	//		String msg="1";
	//		try {
	//			msg=consultMsgManager.deleteDataById(bean1);
	//		} catch (Exception e) {
	//			msg=e.getMessage();
	//		}
	//		request.setAttribute("msg",msg);
	//		return SUCCESS;
	//	}
	
	
	
	/**
	 * BEAN类  咨询信息设定
	 * @param bean BEAN类  咨询信息
	 */
	//	/**
	//	 * <p>
	//	 * 编辑。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>编辑。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String edit() {
	//		log.info("C301Action edit.........");
	//		String id=request.getParameter("id");
	//		if(id!=null){
	//			TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//			bean1.setId(id);
	//			bean=consultMsgManager.findDataById(bean1);
	//		}else{
	//			SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
	//			request.setAttribute("date", sdf.format(new Date()));
	//		}
	//		return "edit";
	//	}
	//	/**
	//	 * <p>
	//	 * 信息保存
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>新增。</div>
	//	 * <div>修改。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String save() {
	//		log.info("C301Action edit.........");
	//		if(bean!=null){
	//			String msg="1";
	//			try {
	//					BaseUserBean user = (BaseUserBean) request.getSession().getAttribute(CommonConstant.SESSION_KEY_USER_CUSTOMER_SERVICE);
	//					if(user!=null){
	//						bean.setCreate_ip(getIpAddr());
	//						bean.setCreate_id(user.getUser_id());
	//						bean.setUpdate_ip(getIpAddr());
	//						bean.setUpdate_id(user.getUser_id());
	//					}
	//					msg=consultMsgManager.saveOrUpdateData(bean);
	//			} catch (Exception e) {
	//				msg=e.getMessage();
	//			}
	//			request.setAttribute("msg",msg);
	//		}else{
	//			request.setAttribute("msg", "信息保存失败!");
	//		}
	//		return SUCCESS;
	//	}
	//	/**
	//	 * <p>
	//	 * 删除。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>删除。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String del() {
	//		log.info("C301Action del.........");
	//		
	//		String id=request.getParameter("id");
	//		TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//		bean1.setId(id);
	//		String msg="1";
	//		try {
	//			msg=consultMsgManager.deleteDataById(bean1);
	//		} catch (Exception e) {
	//			msg=e.getMessage();
	//		}
	//		request.setAttribute("msg",msg);
	//		return SUCCESS;
	//	}
	
	
	
	/**
	 * BEAN类  咨询信息取得
	 * @return BEAN类  咨询信息
	 */
	//	/**
	//	 * <p>
	//	 * 编辑。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>编辑。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String edit() {
	//		log.info("C301Action edit.........");
	//		String id=request.getParameter("id");
	//		if(id!=null){
	//			TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//			bean1.setId(id);
	//			bean=consultMsgManager.findDataById(bean1);
	//		}else{
	//			SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
	//			request.setAttribute("date", sdf.format(new Date()));
	//		}
	//		return "edit";
	//	}
	//	/**
	//	 * <p>
	//	 * 信息保存
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>新增。</div>
	//	 * <div>修改。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String save() {
	//		log.info("C301Action edit.........");
	//		if(bean!=null){
	//			String msg="1";
	//			try {
	//					BaseUserBean user = (BaseUserBean) request.getSession().getAttribute(CommonConstant.SESSION_KEY_USER_CUSTOMER_SERVICE);
	//					if(user!=null){
	//						bean.setCreate_ip(getIpAddr());
	//						bean.setCreate_id(user.getUser_id());
	//						bean.setUpdate_ip(getIpAddr());
	//						bean.setUpdate_id(user.getUser_id());
	//					}
	//					msg=consultMsgManager.saveOrUpdateData(bean);
	//			} catch (Exception e) {
	//				msg=e.getMessage();
	//			}
	//			request.setAttribute("msg",msg);
	//		}else{
	//			request.setAttribute("msg", "信息保存失败!");
	//		}
	//		return SUCCESS;
	//	}
	//	/**
	//	 * <p>
	//	 * 删除。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>删除。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String del() {
	//		log.info("C301Action del.........");
	//		
	//		String id=request.getParameter("id");
	//		TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//		bean1.setId(id);
	//		String msg="1";
	//		try {
	//			msg=consultMsgManager.deleteDataById(bean1);
	//		} catch (Exception e) {
	//			msg=e.getMessage();
	//		}
	//		request.setAttribute("msg",msg);
	//		return SUCCESS;
	//	}
	
	
	
	/**
	 * BEAN类  咨询信息设定
	 * @param bean BEAN类  咨询信息
	 */
	//	/**
	//	 * <p>
	//	 * 编辑。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>编辑。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String edit() {
	//		log.info("C301Action edit.........");
	//		String id=request.getParameter("id");
	//		if(id!=null){
	//			TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//			bean1.setId(id);
	//			bean=consultMsgManager.findDataById(bean1);
	//		}else{
	//			SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
	//			request.setAttribute("date", sdf.format(new Date()));
	//		}
	//		return "edit";
	//	}
	//	/**
	//	 * <p>
	//	 * 信息保存
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>新增。</div>
	//	 * <div>修改。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String save() {
	//		log.info("C301Action edit.........");
	//		if(bean!=null){
	//			String msg="1";
	//			try {
	//					BaseUserBean user = (BaseUserBean) request.getSession().getAttribute(CommonConstant.SESSION_KEY_USER_CUSTOMER_SERVICE);
	//					if(user!=null){
	//						bean.setCreate_ip(getIpAddr());
	//						bean.setCreate_id(user.getUser_id());
	//						bean.setUpdate_ip(getIpAddr());
	//						bean.setUpdate_id(user.getUser_id());
	//					}
	//					msg=consultMsgManager.saveOrUpdateData(bean);
	//			} catch (Exception e) {
	//				msg=e.getMessage();
	//			}
	//			request.setAttribute("msg",msg);
	//		}else{
	//			request.setAttribute("msg", "信息保存失败!");
	//		}
	//		return SUCCESS;
	//	}
	//	/**
	//	 * <p>
	//	 * 删除。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>删除。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String del() {
	//		log.info("C301Action del.........");
	//		
	//		String id=request.getParameter("id");
	//		TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//		bean1.setId(id);
	//		String msg="1";
	//		try {
	//			msg=consultMsgManager.deleteDataById(bean1);
	//		} catch (Exception e) {
	//			msg=e.getMessage();
	//		}
	//		request.setAttribute("msg",msg);
	//		return SUCCESS;
	//	}
	
	
	
	/**
	 * BEAN类  咨询信息取得
	 * @return BEAN类  咨询信息
	 */
	//	/**
	//	 * <p>
	//	 * 编辑。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>编辑。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String edit() {
	//		log.info("C301Action edit.........");
	//		String id=request.getParameter("id");
	//		if(id!=null){
	//			TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//			bean1.setId(id);
	//			bean=consultMsgManager.findDataById(bean1);
	//		}else{
	//			SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
	//			request.setAttribute("date", sdf.format(new Date()));
	//		}
	//		return "edit";
	//	}
	//	/**
	//	 * <p>
	//	 * 信息保存
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>新增。</div>
	//	 * <div>修改。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String save() {
	//		log.info("C301Action edit.........");
	//		if(bean!=null){
	//			String msg="1";
	//			try {
	//					BaseUserBean user = (BaseUserBean) request.getSession().getAttribute(CommonConstant.SESSION_KEY_USER_CUSTOMER_SERVICE);
	//					if(user!=null){
	//						bean.setCreate_ip(getIpAddr());
	//						bean.setCreate_id(user.getUser_id());
	//						bean.setUpdate_ip(getIpAddr());
	//						bean.setUpdate_id(user.getUser_id());
	//					}
	//					msg=consultMsgManager.saveOrUpdateData(bean);
	//			} catch (Exception e) {
	//				msg=e.getMessage();
	//			}
	//			request.setAttribute("msg",msg);
	//		}else{
	//			request.setAttribute("msg", "信息保存失败!");
	//		}
	//		return SUCCESS;
	//	}
	//	/**
	//	 * <p>
	//	 * 删除。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>删除。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String del() {
	//		log.info("C301Action del.........");
	//		
	//		String id=request.getParameter("id");
	//		TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//		bean1.setId(id);
	//		String msg="1";
	//		try {
	//			msg=consultMsgManager.deleteDataById(bean1);
	//		} catch (Exception e) {
	//			msg=e.getMessage();
	//		}
	//		request.setAttribute("msg",msg);
	//		return SUCCESS;
	//	}
	
	
	
	/**
	 * BEAN类  咨询信息取得
	 * @return BEAN类  咨询信息
	 */
	//	/**
	//	 * <p>
	//	 * 编辑。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>编辑。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String edit() {
	//		log.info("C301Action edit.........");
	//		String id=request.getParameter("id");
	//		if(id!=null){
	//			TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//			bean1.setId(id);
	//			bean=consultMsgManager.findDataById(bean1);
	//		}else{
	//			SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
	//			request.setAttribute("date", sdf.format(new Date()));
	//		}
	//		return "edit";
	//	}
	//	/**
	//	 * <p>
	//	 * 信息保存
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>新增。</div>
	//	 * <div>修改。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String save() {
	//		log.info("C301Action edit.........");
	//		if(bean!=null){
	//			String msg="1";
	//			try {
	//					BaseUserBean user = (BaseUserBean) request.getSession().getAttribute(CommonConstant.SESSION_KEY_USER_CUSTOMER_SERVICE);
	//					if(user!=null){
	//						bean.setCreate_ip(getIpAddr());
	//						bean.setCreate_id(user.getUser_id());
	//						bean.setUpdate_ip(getIpAddr());
	//						bean.setUpdate_id(user.getUser_id());
	//					}
	//					msg=consultMsgManager.saveOrUpdateData(bean);
	//			} catch (Exception e) {
	//				msg=e.getMessage();
	//			}
	//			request.setAttribute("msg",msg);
	//		}else{
	//			request.setAttribute("msg", "信息保存失败!");
	//		}
	//		return SUCCESS;
	//	}
	//	/**
	//	 * <p>
	//	 * 删除。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>删除。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String del() {
	//		log.info("C301Action del.........");
	//		
	//		String id=request.getParameter("id");
	//		TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//		bean1.setId(id);
	//		String msg="1";
	//		try {
	//			msg=consultMsgManager.deleteDataById(bean1);
	//		} catch (Exception e) {
	//			msg=e.getMessage();
	//		}
	//		request.setAttribute("msg",msg);
	//		return SUCCESS;
	//	}
	
	
	
	/**
	 * BEAN类  咨询信息设定
	 * @param bean BEAN类  咨询信息
	 */
	//	/**
	//	 * <p>
	//	 * 编辑。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>编辑。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String edit() {
	//		log.info("C301Action edit.........");
	//		String id=request.getParameter("id");
	//		if(id!=null){
	//			TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//			bean1.setId(id);
	//			bean=consultMsgManager.findDataById(bean1);
	//		}else{
	//			SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
	//			request.setAttribute("date", sdf.format(new Date()));
	//		}
	//		return "edit";
	//	}
	//	/**
	//	 * <p>
	//	 * 信息保存
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>新增。</div>
	//	 * <div>修改。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String save() {
	//		log.info("C301Action edit.........");
	//		if(bean!=null){
	//			String msg="1";
	//			try {
	//					BaseUserBean user = (BaseUserBean) request.getSession().getAttribute(CommonConstant.SESSION_KEY_USER_CUSTOMER_SERVICE);
	//					if(user!=null){
	//						bean.setCreate_ip(getIpAddr());
	//						bean.setCreate_id(user.getUser_id());
	//						bean.setUpdate_ip(getIpAddr());
	//						bean.setUpdate_id(user.getUser_id());
	//					}
	//					msg=consultMsgManager.saveOrUpdateData(bean);
	//			} catch (Exception e) {
	//				msg=e.getMessage();
	//			}
	//			request.setAttribute("msg",msg);
	//		}else{
	//			request.setAttribute("msg", "信息保存失败!");
	//		}
	//		return SUCCESS;
	//	}
	//	/**
	//	 * <p>
	//	 * 删除。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>删除。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String del() {
	//		log.info("C301Action del.........");
	//		
	//		String id=request.getParameter("id");
	//		TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//		bean1.setId(id);
	//		String msg="1";
	//		try {
	//			msg=consultMsgManager.deleteDataById(bean1);
	//		} catch (Exception e) {
	//			msg=e.getMessage();
	//		}
	//		request.setAttribute("msg",msg);
	//		return SUCCESS;
	//	}
	
	
	
	/**
	 * BEAN类  咨询信息取得
	 * @return BEAN类  咨询信息
	 */
	//	/**
	//	 * <p>
	//	 * 编辑。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>编辑。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String edit() {
	//		log.info("C301Action edit.........");
	//		String id=request.getParameter("id");
	//		if(id!=null){
	//			TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//			bean1.setId(id);
	//			bean=consultMsgManager.findDataById(bean1);
	//		}else{
	//			SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
	//			request.setAttribute("date", sdf.format(new Date()));
	//		}
	//		return "edit";
	//	}
	//	/**
	//	 * <p>
	//	 * 信息保存
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>新增。</div>
	//	 * <div>修改。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String save() {
	//		log.info("C301Action edit.........");
	//		if(bean!=null){
	//			String msg="1";
	//			try {
	//					BaseUserBean user = (BaseUserBean) request.getSession().getAttribute(CommonConstant.SESSION_KEY_USER_CUSTOMER_SERVICE);
	//					if(user!=null){
	//						bean.setCreate_ip(getIpAddr());
	//						bean.setCreate_id(user.getUser_id());
	//						bean.setUpdate_ip(getIpAddr());
	//						bean.setUpdate_id(user.getUser_id());
	//					}
	//					msg=consultMsgManager.saveOrUpdateData(bean);
	//			} catch (Exception e) {
	//				msg=e.getMessage();
	//			}
	//			request.setAttribute("msg",msg);
	//		}else{
	//			request.setAttribute("msg", "信息保存失败!");
	//		}
	//		return SUCCESS;
	//	}
	//	/**
	//	 * <p>
	//	 * 删除。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>删除。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String del() {
	//		log.info("C301Action del.........");
	//		
	//		String id=request.getParameter("id");
	//		TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//		bean1.setId(id);
	//		String msg="1";
	//		try {
	//			msg=consultMsgManager.deleteDataById(bean1);
	//		} catch (Exception e) {
	//			msg=e.getMessage();
	//		}
	//		request.setAttribute("msg",msg);
	//		return SUCCESS;
	//	}
	
	
	
	/**
	 * BEAN类  咨询信息设定
	 * @param bean BEAN类  咨询信息
	 */
	//	/**
	//	 * <p>
	//	 * 编辑。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>编辑。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String edit() {
	//		log.info("C301Action edit.........");
	//		String id=request.getParameter("id");
	//		if(id!=null){
	//			TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//			bean1.setId(id);
	//			bean=consultMsgManager.findDataById(bean1);
	//		}else{
	//			SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
	//			request.setAttribute("date", sdf.format(new Date()));
	//		}
	//		return "edit";
	//	}
	//	/**
	//	 * <p>
	//	 * 信息保存
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>新增。</div>
	//	 * <div>修改。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String save() {
	//		log.info("C301Action edit.........");
	//		if(bean!=null){
	//			String msg="1";
	//			try {
	//					BaseUserBean user = (BaseUserBean) request.getSession().getAttribute(CommonConstant.SESSION_KEY_USER_CUSTOMER_SERVICE);
	//					if(user!=null){
	//						bean.setCreate_ip(getIpAddr());
	//						bean.setCreate_id(user.getUser_id());
	//						bean.setUpdate_ip(getIpAddr());
	//						bean.setUpdate_id(user.getUser_id());
	//					}
	//					msg=consultMsgManager.saveOrUpdateData(bean);
	//			} catch (Exception e) {
	//				msg=e.getMessage();
	//			}
	//			request.setAttribute("msg",msg);
	//		}else{
	//			request.setAttribute("msg", "信息保存失败!");
	//		}
	//		return SUCCESS;
	//	}
	//	/**
	//	 * <p>
	//	 * 删除。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>删除。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String del() {
	//		log.info("C301Action del.........");
	//		
	//		String id=request.getParameter("id");
	//		TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//		bean1.setId(id);
	//		String msg="1";
	//		try {
	//			msg=consultMsgManager.deleteDataById(bean1);
	//		} catch (Exception e) {
	//			msg=e.getMessage();
	//		}
	//		request.setAttribute("msg",msg);
	//		return SUCCESS;
	//	}
	
	
	
	/**
	 * BEAN类  咨询信息取得
	 * @return BEAN类  咨询信息
	 */
	//	/**
	//	 * <p>
	//	 * 编辑。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>编辑。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String edit() {
	//		log.info("C301Action edit.........");
	//		String id=request.getParameter("id");
	//		if(id!=null){
	//			TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//			bean1.setId(id);
	//			bean=consultMsgManager.findDataById(bean1);
	//		}else{
	//			SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
	//			request.setAttribute("date", sdf.format(new Date()));
	//		}
	//		return "edit";
	//	}
	//	/**
	//	 * <p>
	//	 * 信息保存
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>新增。</div>
	//	 * <div>修改。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String save() {
	//		log.info("C301Action edit.........");
	//		if(bean!=null){
	//			String msg="1";
	//			try {
	//					BaseUserBean user = (BaseUserBean) request.getSession().getAttribute(CommonConstant.SESSION_KEY_USER_CUSTOMER_SERVICE);
	//					if(user!=null){
	//						bean.setCreate_ip(getIpAddr());
	//						bean.setCreate_id(user.getUser_id());
	//						bean.setUpdate_ip(getIpAddr());
	//						bean.setUpdate_id(user.getUser_id());
	//					}
	//					msg=consultMsgManager.saveOrUpdateData(bean);
	//			} catch (Exception e) {
	//				msg=e.getMessage();
	//			}
	//			request.setAttribute("msg",msg);
	//		}else{
	//			request.setAttribute("msg", "信息保存失败!");
	//		}
	//		return SUCCESS;
	//	}
	//	/**
	//	 * <p>
	//	 * 删除。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>删除。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String del() {
	//		log.info("C301Action del.........");
	//		
	//		String id=request.getParameter("id");
	//		TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//		bean1.setId(id);
	//		String msg="1";
	//		try {
	//			msg=consultMsgManager.deleteDataById(bean1);
	//		} catch (Exception e) {
	//			msg=e.getMessage();
	//		}
	//		request.setAttribute("msg",msg);
	//		return SUCCESS;
	//	}
	
	
	
	/**
	 * BEAN类  咨询信息取得
	 * @return BEAN类  咨询信息
	 */
	//	/**
	//	 * <p>
	//	 * 编辑。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>编辑。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String edit() {
	//		log.info("C301Action edit.........");
	//		String id=request.getParameter("id");
	//		if(id!=null){
	//			TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//			bean1.setId(id);
	//			bean=consultMsgManager.findDataById(bean1);
	//		}else{
	//			SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
	//			request.setAttribute("date", sdf.format(new Date()));
	//		}
	//		return "edit";
	//	}
	//	/**
	//	 * <p>
	//	 * 信息保存
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>新增。</div>
	//	 * <div>修改。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String save() {
	//		log.info("C301Action edit.........");
	//		if(bean!=null){
	//			String msg="1";
	//			try {
	//					BaseUserBean user = (BaseUserBean) request.getSession().getAttribute(CommonConstant.SESSION_KEY_USER_CUSTOMER_SERVICE);
	//					if(user!=null){
	//						bean.setCreate_ip(getIpAddr());
	//						bean.setCreate_id(user.getUser_id());
	//						bean.setUpdate_ip(getIpAddr());
	//						bean.setUpdate_id(user.getUser_id());
	//					}
	//					msg=consultMsgManager.saveOrUpdateData(bean);
	//			} catch (Exception e) {
	//				msg=e.getMessage();
	//			}
	//			request.setAttribute("msg",msg);
	//		}else{
	//			request.setAttribute("msg", "信息保存失败!");
	//		}
	//		return SUCCESS;
	//	}
	//	/**
	//	 * <p>
	//	 * 删除。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>删除。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String del() {
	//		log.info("C301Action del.........");
	//		
	//		String id=request.getParameter("id");
	//		TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//		bean1.setId(id);
	//		String msg="1";
	//		try {
	//			msg=consultMsgManager.deleteDataById(bean1);
	//		} catch (Exception e) {
	//			msg=e.getMessage();
	//		}
	//		request.setAttribute("msg",msg);
	//		return SUCCESS;
	//	}
	
	
	
	/**
	 * BEAN类  咨询信息设定
	 * @param bean BEAN类  咨询信息
	 */
	//	/**
	//	 * <p>
	//	 * 编辑。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>编辑。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String edit() {
	//		log.info("C301Action edit.........");
	//		String id=request.getParameter("id");
	//		if(id!=null){
	//			TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//			bean1.setId(id);
	//			bean=consultMsgManager.findDataById(bean1);
	//		}else{
	//			SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
	//			request.setAttribute("date", sdf.format(new Date()));
	//		}
	//		return "edit";
	//	}
	//	/**
	//	 * <p>
	//	 * 信息保存
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>新增。</div>
	//	 * <div>修改。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String save() {
	//		log.info("C301Action edit.........");
	//		if(bean!=null){
	//			String msg="1";
	//			try {
	//					BaseUserBean user = (BaseUserBean) request.getSession().getAttribute(CommonConstant.SESSION_KEY_USER_CUSTOMER_SERVICE);
	//					if(user!=null){
	//						bean.setCreate_ip(getIpAddr());
	//						bean.setCreate_id(user.getUser_id());
	//						bean.setUpdate_ip(getIpAddr());
	//						bean.setUpdate_id(user.getUser_id());
	//					}
	//					msg=consultMsgManager.saveOrUpdateData(bean);
	//			} catch (Exception e) {
	//				msg=e.getMessage();
	//			}
	//			request.setAttribute("msg",msg);
	//		}else{
	//			request.setAttribute("msg", "信息保存失败!");
	//		}
	//		return SUCCESS;
	//	}
	//	/**
	//	 * <p>
	//	 * 删除。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>删除。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String del() {
	//		log.info("C301Action del.........");
	//		
	//		String id=request.getParameter("id");
	//		TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//		bean1.setId(id);
	//		String msg="1";
	//		try {
	//			msg=consultMsgManager.deleteDataById(bean1);
	//		} catch (Exception e) {
	//			msg=e.getMessage();
	//		}
	//		request.setAttribute("msg",msg);
	//		return SUCCESS;
	//	}
	
	
	
	/**
	 * BEAN类  咨询信息取得
	 * @return BEAN类  咨询信息
	 */
	//	/**
	//	 * <p>
	//	 * 编辑。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>编辑。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String edit() {
	//		log.info("C301Action edit.........");
	//		String id=request.getParameter("id");
	//		if(id!=null){
	//			TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//			bean1.setId(id);
	//			bean=consultMsgManager.findDataById(bean1);
	//		}else{
	//			SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
	//			request.setAttribute("date", sdf.format(new Date()));
	//		}
	//		return "edit";
	//	}
	//	/**
	//	 * <p>
	//	 * 信息保存
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>新增。</div>
	//	 * <div>修改。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String save() {
	//		log.info("C301Action edit.........");
	//		if(bean!=null){
	//			String msg="1";
	//			try {
	//					BaseUserBean user = (BaseUserBean) request.getSession().getAttribute(CommonConstant.SESSION_KEY_USER_CUSTOMER_SERVICE);
	//					if(user!=null){
	//						bean.setCreate_ip(getIpAddr());
	//						bean.setCreate_id(user.getUser_id());
	//						bean.setUpdate_ip(getIpAddr());
	//						bean.setUpdate_id(user.getUser_id());
	//					}
	//					msg=consultMsgManager.saveOrUpdateData(bean);
	//			} catch (Exception e) {
	//				msg=e.getMessage();
	//			}
	//			request.setAttribute("msg",msg);
	//		}else{
	//			request.setAttribute("msg", "信息保存失败!");
	//		}
	//		return SUCCESS;
	//	}
	//	/**
	//	 * <p>
	//	 * 删除。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>删除。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String del() {
	//		log.info("C301Action del.........");
	//		
	//		String id=request.getParameter("id");
	//		TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//		bean1.setId(id);
	//		String msg="1";
	//		try {
	//			msg=consultMsgManager.deleteDataById(bean1);
	//		} catch (Exception e) {
	//			msg=e.getMessage();
	//		}
	//		request.setAttribute("msg",msg);
	//		return SUCCESS;
	//	}
	
	
	
	/**
	 * BEAN类  咨询信息取得
	 * @return BEAN类  咨询信息
	 */
	//	/**
	//	 * <p>
	//	 * 编辑。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>编辑。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String edit() {
	//		log.info("C301Action edit.........");
	//		String id=request.getParameter("id");
	//		if(id!=null){
	//			TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//			bean1.setId(id);
	//			bean=consultMsgManager.findDataById(bean1);
	//		}else{
	//			SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
	//			request.setAttribute("date", sdf.format(new Date()));
	//		}
	//		return "edit";
	//	}
	//	/**
	//	 * <p>
	//	 * 信息保存
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>新增。</div>
	//	 * <div>修改。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String save() {
	//		log.info("C301Action edit.........");
	//		if(bean!=null){
	//			String msg="1";
	//			try {
	//					BaseUserBean user = (BaseUserBean) request.getSession().getAttribute(CommonConstant.SESSION_KEY_USER_CUSTOMER_SERVICE);
	//					if(user!=null){
	//						bean.setCreate_ip(getIpAddr());
	//						bean.setCreate_id(user.getUser_id());
	//						bean.setUpdate_ip(getIpAddr());
	//						bean.setUpdate_id(user.getUser_id());
	//					}
	//					msg=consultMsgManager.saveOrUpdateData(bean);
	//			} catch (Exception e) {
	//				msg=e.getMessage();
	//			}
	//			request.setAttribute("msg",msg);
	//		}else{
	//			request.setAttribute("msg", "信息保存失败!");
	//		}
	//		return SUCCESS;
	//	}
	//	/**
	//	 * <p>
	//	 * 删除。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>删除。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String del() {
	//		log.info("C301Action del.........");
	//		
	//		String id=request.getParameter("id");
	//		TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//		bean1.setId(id);
	//		String msg="1";
	//		try {
	//			msg=consultMsgManager.deleteDataById(bean1);
	//		} catch (Exception e) {
	//			msg=e.getMessage();
	//		}
	//		request.setAttribute("msg",msg);
	//		return SUCCESS;
	//	}
	
	
	
	/**
	 * BEAN类  咨询信息设定
	 * @param bean BEAN类  咨询信息
	 */
	//	/**
	//	 * <p>
	//	 * 编辑。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>编辑。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String edit() {
	//		log.info("C301Action edit.........");
	//		String id=request.getParameter("id");
	//		if(id!=null){
	//			TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//			bean1.setId(id);
	//			bean=consultMsgManager.findDataById(bean1);
	//		}else{
	//			SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
	//			request.setAttribute("date", sdf.format(new Date()));
	//		}
	//		return "edit";
	//	}
	//	/**
	//	 * <p>
	//	 * 信息保存
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>新增。</div>
	//	 * <div>修改。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String save() {
	//		log.info("C301Action edit.........");
	//		if(bean!=null){
	//			String msg="1";
	//			try {
	//					BaseUserBean user = (BaseUserBean) request.getSession().getAttribute(CommonConstant.SESSION_KEY_USER_CUSTOMER_SERVICE);
	//					if(user!=null){
	//						bean.setCreate_ip(getIpAddr());
	//						bean.setCreate_id(user.getUser_id());
	//						bean.setUpdate_ip(getIpAddr());
	//						bean.setUpdate_id(user.getUser_id());
	//					}
	//					msg=consultMsgManager.saveOrUpdateData(bean);
	//			} catch (Exception e) {
	//				msg=e.getMessage();
	//			}
	//			request.setAttribute("msg",msg);
	//		}else{
	//			request.setAttribute("msg", "信息保存失败!");
	//		}
	//		return SUCCESS;
	//	}
	//	/**
	//	 * <p>
	//	 * 删除。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>删除。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String del() {
	//		log.info("C301Action del.........");
	//		
	//		String id=request.getParameter("id");
	//		TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//		bean1.setId(id);
	//		String msg="1";
	//		try {
	//			msg=consultMsgManager.deleteDataById(bean1);
	//		} catch (Exception e) {
	//			msg=e.getMessage();
	//		}
	//		request.setAttribute("msg",msg);
	//		return SUCCESS;
	//	}
	
	
	
	/**
	 * BEAN类  咨询信息取得
	 * @return BEAN类  咨询信息
	 */
	//	/**
	//	 * <p>
	//	 * 编辑。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>编辑。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String edit() {
	//		log.info("C301Action edit.........");
	//		String id=request.getParameter("id");
	//		if(id!=null){
	//			TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//			bean1.setId(id);
	//			bean=consultMsgManager.findDataById(bean1);
	//		}else{
	//			SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
	//			request.setAttribute("date", sdf.format(new Date()));
	//		}
	//		return "edit";
	//	}
	//	/**
	//	 * <p>
	//	 * 信息保存
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>新增。</div>
	//	 * <div>修改。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String save() {
	//		log.info("C301Action edit.........");
	//		if(bean!=null){
	//			String msg="1";
	//			try {
	//					BaseUserBean user = (BaseUserBean) request.getSession().getAttribute(CommonConstant.SESSION_KEY_USER_CUSTOMER_SERVICE);
	//					if(user!=null){
	//						bean.setCreate_ip(getIpAddr());
	//						bean.setCreate_id(user.getUser_id());
	//						bean.setUpdate_ip(getIpAddr());
	//						bean.setUpdate_id(user.getUser_id());
	//					}
	//					msg=consultMsgManager.saveOrUpdateData(bean);
	//			} catch (Exception e) {
	//				msg=e.getMessage();
	//			}
	//			request.setAttribute("msg",msg);
	//		}else{
	//			request.setAttribute("msg", "信息保存失败!");
	//		}
	//		return SUCCESS;
	//	}
	//	/**
	//	 * <p>
	//	 * 删除。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>删除。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String del() {
	//		log.info("C301Action del.........");
	//		
	//		String id=request.getParameter("id");
	//		TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//		bean1.setId(id);
	//		String msg="1";
	//		try {
	//			msg=consultMsgManager.deleteDataById(bean1);
	//		} catch (Exception e) {
	//			msg=e.getMessage();
	//		}
	//		request.setAttribute("msg",msg);
	//		return SUCCESS;
	//	}
	
	
	
	/**
	 * BEAN类  咨询信息设定
	 * @param bean BEAN类  咨询信息
	 */
	//	/**
	//	 * <p>
	//	 * 编辑。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>编辑。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String edit() {
	//		log.info("C301Action edit.........");
	//		String id=request.getParameter("id");
	//		if(id!=null){
	//			TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//			bean1.setId(id);
	//			bean=consultMsgManager.findDataById(bean1);
	//		}else{
	//			SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
	//			request.setAttribute("date", sdf.format(new Date()));
	//		}
	//		return "edit";
	//	}
	//	/**
	//	 * <p>
	//	 * 信息保存
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>新增。</div>
	//	 * <div>修改。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String save() {
	//		log.info("C301Action edit.........");
	//		if(bean!=null){
	//			String msg="1";
	//			try {
	//					BaseUserBean user = (BaseUserBean) request.getSession().getAttribute(CommonConstant.SESSION_KEY_USER_CUSTOMER_SERVICE);
	//					if(user!=null){
	//						bean.setCreate_ip(getIpAddr());
	//						bean.setCreate_id(user.getUser_id());
	//						bean.setUpdate_ip(getIpAddr());
	//						bean.setUpdate_id(user.getUser_id());
	//					}
	//					msg=consultMsgManager.saveOrUpdateData(bean);
	//			} catch (Exception e) {
	//				msg=e.getMessage();
	//			}
	//			request.setAttribute("msg",msg);
	//		}else{
	//			request.setAttribute("msg", "信息保存失败!");
	//		}
	//		return SUCCESS;
	//	}
	//	/**
	//	 * <p>
	//	 * 删除。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>删除。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String del() {
	//		log.info("C301Action del.........");
	//		
	//		String id=request.getParameter("id");
	//		TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//		bean1.setId(id);
	//		String msg="1";
	//		try {
	//			msg=consultMsgManager.deleteDataById(bean1);
	//		} catch (Exception e) {
	//			msg=e.getMessage();
	//		}
	//		request.setAttribute("msg",msg);
	//		return SUCCESS;
	//	}
	
	
	
	/**
	 * BEAN类  咨询信息取得
	 * @return BEAN类  咨询信息
	 */
	//	/**
	//	 * <p>
	//	 * 编辑。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>编辑。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String edit() {
	//		log.info("C301Action edit.........");
	//		String id=request.getParameter("id");
	//		if(id!=null){
	//			TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//			bean1.setId(id);
	//			bean=consultMsgManager.findDataById(bean1);
	//		}else{
	//			SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
	//			request.setAttribute("date", sdf.format(new Date()));
	//		}
	//		return "edit";
	//	}
	//	/**
	//	 * <p>
	//	 * 信息保存
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>新增。</div>
	//	 * <div>修改。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String save() {
	//		log.info("C301Action edit.........");
	//		if(bean!=null){
	//			String msg="1";
	//			try {
	//					BaseUserBean user = (BaseUserBean) request.getSession().getAttribute(CommonConstant.SESSION_KEY_USER_CUSTOMER_SERVICE);
	//					if(user!=null){
	//						bean.setCreate_ip(getIpAddr());
	//						bean.setCreate_id(user.getUser_id());
	//						bean.setUpdate_ip(getIpAddr());
	//						bean.setUpdate_id(user.getUser_id());
	//					}
	//					msg=consultMsgManager.saveOrUpdateData(bean);
	//			} catch (Exception e) {
	//				msg=e.getMessage();
	//			}
	//			request.setAttribute("msg",msg);
	//		}else{
	//			request.setAttribute("msg", "信息保存失败!");
	//		}
	//		return SUCCESS;
	//	}
	//	/**
	//	 * <p>
	//	 * 删除。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>删除。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String del() {
	//		log.info("C301Action del.........");
	//		
	//		String id=request.getParameter("id");
	//		TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//		bean1.setId(id);
	//		String msg="1";
	//		try {
	//			msg=consultMsgManager.deleteDataById(bean1);
	//		} catch (Exception e) {
	//			msg=e.getMessage();
	//		}
	//		request.setAttribute("msg",msg);
	//		return SUCCESS;
	//	}
	
	
	
	/**
	 * BEAN类  咨询信息取得
	 * @return BEAN类  咨询信息
	 */
	//	/**
	//	 * <p>
	//	 * 编辑。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>编辑。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String edit() {
	//		log.info("C301Action edit.........");
	//		String id=request.getParameter("id");
	//		if(id!=null){
	//			TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//			bean1.setId(id);
	//			bean=consultMsgManager.findDataById(bean1);
	//		}else{
	//			SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
	//			request.setAttribute("date", sdf.format(new Date()));
	//		}
	//		return "edit";
	//	}
	//	/**
	//	 * <p>
	//	 * 信息保存
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>新增。</div>
	//	 * <div>修改。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String save() {
	//		log.info("C301Action edit.........");
	//		if(bean!=null){
	//			String msg="1";
	//			try {
	//					BaseUserBean user = (BaseUserBean) request.getSession().getAttribute(CommonConstant.SESSION_KEY_USER_CUSTOMER_SERVICE);
	//					if(user!=null){
	//						bean.setCreate_ip(getIpAddr());
	//						bean.setCreate_id(user.getUser_id());
	//						bean.setUpdate_ip(getIpAddr());
	//						bean.setUpdate_id(user.getUser_id());
	//					}
	//					msg=consultMsgManager.saveOrUpdateData(bean);
	//			} catch (Exception e) {
	//				msg=e.getMessage();
	//			}
	//			request.setAttribute("msg",msg);
	//		}else{
	//			request.setAttribute("msg", "信息保存失败!");
	//		}
	//		return SUCCESS;
	//	}
	//	/**
	//	 * <p>
	//	 * 删除。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>删除。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String del() {
	//		log.info("C301Action del.........");
	//		
	//		String id=request.getParameter("id");
	//		TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//		bean1.setId(id);
	//		String msg="1";
	//		try {
	//			msg=consultMsgManager.deleteDataById(bean1);
	//		} catch (Exception e) {
	//			msg=e.getMessage();
	//		}
	//		request.setAttribute("msg",msg);
	//		return SUCCESS;
	//	}
	
	
	
	/**
	 * BEAN类  咨询信息设定
	 * @param bean BEAN类  咨询信息
	 */
	//	/**
	//	 * <p>
	//	 * 编辑。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>编辑。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String edit() {
	//		log.info("C301Action edit.........");
	//		String id=request.getParameter("id");
	//		if(id!=null){
	//			TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//			bean1.setId(id);
	//			bean=consultMsgManager.findDataById(bean1);
	//		}else{
	//			SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
	//			request.setAttribute("date", sdf.format(new Date()));
	//		}
	//		return "edit";
	//	}
	//	/**
	//	 * <p>
	//	 * 信息保存
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>新增。</div>
	//	 * <div>修改。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String save() {
	//		log.info("C301Action edit.........");
	//		if(bean!=null){
	//			String msg="1";
	//			try {
	//					BaseUserBean user = (BaseUserBean) request.getSession().getAttribute(CommonConstant.SESSION_KEY_USER_CUSTOMER_SERVICE);
	//					if(user!=null){
	//						bean.setCreate_ip(getIpAddr());
	//						bean.setCreate_id(user.getUser_id());
	//						bean.setUpdate_ip(getIpAddr());
	//						bean.setUpdate_id(user.getUser_id());
	//					}
	//					msg=consultMsgManager.saveOrUpdateData(bean);
	//			} catch (Exception e) {
	//				msg=e.getMessage();
	//			}
	//			request.setAttribute("msg",msg);
	//		}else{
	//			request.setAttribute("msg", "信息保存失败!");
	//		}
	//		return SUCCESS;
	//	}
	//	/**
	//	 * <p>
	//	 * 删除。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>删除。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String del() {
	//		log.info("C301Action del.........");
	//		
	//		String id=request.getParameter("id");
	//		TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//		bean1.setId(id);
	//		String msg="1";
	//		try {
	//			msg=consultMsgManager.deleteDataById(bean1);
	//		} catch (Exception e) {
	//			msg=e.getMessage();
	//		}
	//		request.setAttribute("msg",msg);
	//		return SUCCESS;
	//	}
	
	
	
	/**
	 * BEAN类  咨询信息取得
	 * @return BEAN类  咨询信息
	 */
	//	/**
	//	 * <p>
	//	 * 编辑。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>编辑。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String edit() {
	//		log.info("C301Action edit.........");
	//		String id=request.getParameter("id");
	//		if(id!=null){
	//			TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//			bean1.setId(id);
	//			bean=consultMsgManager.findDataById(bean1);
	//		}else{
	//			SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
	//			request.setAttribute("date", sdf.format(new Date()));
	//		}
	//		return "edit";
	//	}
	//	/**
	//	 * <p>
	//	 * 信息保存
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>新增。</div>
	//	 * <div>修改。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String save() {
	//		log.info("C301Action edit.........");
	//		if(bean!=null){
	//			String msg="1";
	//			try {
	//					BaseUserBean user = (BaseUserBean) request.getSession().getAttribute(CommonConstant.SESSION_KEY_USER_CUSTOMER_SERVICE);
	//					if(user!=null){
	//						bean.setCreate_ip(getIpAddr());
	//						bean.setCreate_id(user.getUser_id());
	//						bean.setUpdate_ip(getIpAddr());
	//						bean.setUpdate_id(user.getUser_id());
	//					}
	//					msg=consultMsgManager.saveOrUpdateData(bean);
	//			} catch (Exception e) {
	//				msg=e.getMessage();
	//			}
	//			request.setAttribute("msg",msg);
	//		}else{
	//			request.setAttribute("msg", "信息保存失败!");
	//		}
	//		return SUCCESS;
	//	}
	//	/**
	//	 * <p>
	//	 * 删除。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>删除。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String del() {
	//		log.info("C301Action del.........");
	//		
	//		String id=request.getParameter("id");
	//		TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//		bean1.setId(id);
	//		String msg="1";
	//		try {
	//			msg=consultMsgManager.deleteDataById(bean1);
	//		} catch (Exception e) {
	//			msg=e.getMessage();
	//		}
	//		request.setAttribute("msg",msg);
	//		return SUCCESS;
	//	}
	
	
	
	/**
	 * BEAN类  咨询信息取得
	 * @return BEAN类  咨询信息
	 */
	//	/**
	//	 * <p>
	//	 * 编辑。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>编辑。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String edit() {
	//		log.info("C301Action edit.........");
	//		String id=request.getParameter("id");
	//		if(id!=null){
	//			TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//			bean1.setId(id);
	//			bean=consultMsgManager.findDataById(bean1);
	//		}else{
	//			SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
	//			request.setAttribute("date", sdf.format(new Date()));
	//		}
	//		return "edit";
	//	}
	//	/**
	//	 * <p>
	//	 * 信息保存
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>新增。</div>
	//	 * <div>修改。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String save() {
	//		log.info("C301Action edit.........");
	//		if(bean!=null){
	//			String msg="1";
	//			try {
	//					BaseUserBean user = (BaseUserBean) request.getSession().getAttribute(CommonConstant.SESSION_KEY_USER_CUSTOMER_SERVICE);
	//					if(user!=null){
	//						bean.setCreate_ip(getIpAddr());
	//						bean.setCreate_id(user.getUser_id());
	//						bean.setUpdate_ip(getIpAddr());
	//						bean.setUpdate_id(user.getUser_id());
	//					}
	//					msg=consultMsgManager.saveOrUpdateData(bean);
	//			} catch (Exception e) {
	//				msg=e.getMessage();
	//			}
	//			request.setAttribute("msg",msg);
	//		}else{
	//			request.setAttribute("msg", "信息保存失败!");
	//		}
	//		return SUCCESS;
	//	}
	//	/**
	//	 * <p>
	//	 * 删除。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>删除。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String del() {
	//		log.info("C301Action del.........");
	//		
	//		String id=request.getParameter("id");
	//		TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//		bean1.setId(id);
	//		String msg="1";
	//		try {
	//			msg=consultMsgManager.deleteDataById(bean1);
	//		} catch (Exception e) {
	//			msg=e.getMessage();
	//		}
	//		request.setAttribute("msg",msg);
	//		return SUCCESS;
	//	}
	
	
	
	/**
	 * BEAN类  咨询信息设定
	 * @param bean BEAN类  咨询信息
	 */
	//	/**
	//	 * <p>
	//	 * 编辑。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>编辑。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String edit() {
	//		log.info("C301Action edit.........");
	//		String id=request.getParameter("id");
	//		if(id!=null){
	//			TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//			bean1.setId(id);
	//			bean=consultMsgManager.findDataById(bean1);
	//		}else{
	//			SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
	//			request.setAttribute("date", sdf.format(new Date()));
	//		}
	//		return "edit";
	//	}
	//	/**
	//	 * <p>
	//	 * 信息保存
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>新增。</div>
	//	 * <div>修改。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String save() {
	//		log.info("C301Action edit.........");
	//		if(bean!=null){
	//			String msg="1";
	//			try {
	//					BaseUserBean user = (BaseUserBean) request.getSession().getAttribute(CommonConstant.SESSION_KEY_USER_CUSTOMER_SERVICE);
	//					if(user!=null){
	//						bean.setCreate_ip(getIpAddr());
	//						bean.setCreate_id(user.getUser_id());
	//						bean.setUpdate_ip(getIpAddr());
	//						bean.setUpdate_id(user.getUser_id());
	//					}
	//					msg=consultMsgManager.saveOrUpdateData(bean);
	//			} catch (Exception e) {
	//				msg=e.getMessage();
	//			}
	//			request.setAttribute("msg",msg);
	//		}else{
	//			request.setAttribute("msg", "信息保存失败!");
	//		}
	//		return SUCCESS;
	//	}
	//	/**
	//	 * <p>
	//	 * 删除。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>删除。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String del() {
	//		log.info("C301Action del.........");
	//		
	//		String id=request.getParameter("id");
	//		TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//		bean1.setId(id);
	//		String msg="1";
	//		try {
	//			msg=consultMsgManager.deleteDataById(bean1);
	//		} catch (Exception e) {
	//			msg=e.getMessage();
	//		}
	//		request.setAttribute("msg",msg);
	//		return SUCCESS;
	//	}
	
	
	
	/**
	 * BEAN类  咨询信息取得
	 * @return BEAN类  咨询信息
	 */
	//	/**
	//	 * <p>
	//	 * 编辑。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>编辑。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String edit() {
	//		log.info("C301Action edit.........");
	//		String id=request.getParameter("id");
	//		if(id!=null){
	//			TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//			bean1.setId(id);
	//			bean=consultMsgManager.findDataById(bean1);
	//		}else{
	//			SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
	//			request.setAttribute("date", sdf.format(new Date()));
	//		}
	//		return "edit";
	//	}
	//	/**
	//	 * <p>
	//	 * 信息保存
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>新增。</div>
	//	 * <div>修改。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String save() {
	//		log.info("C301Action edit.........");
	//		if(bean!=null){
	//			String msg="1";
	//			try {
	//					BaseUserBean user = (BaseUserBean) request.getSession().getAttribute(CommonConstant.SESSION_KEY_USER_CUSTOMER_SERVICE);
	//					if(user!=null){
	//						bean.setCreate_ip(getIpAddr());
	//						bean.setCreate_id(user.getUser_id());
	//						bean.setUpdate_ip(getIpAddr());
	//						bean.setUpdate_id(user.getUser_id());
	//					}
	//					msg=consultMsgManager.saveOrUpdateData(bean);
	//			} catch (Exception e) {
	//				msg=e.getMessage();
	//			}
	//			request.setAttribute("msg",msg);
	//		}else{
	//			request.setAttribute("msg", "信息保存失败!");
	//		}
	//		return SUCCESS;
	//	}
	//	/**
	//	 * <p>
	//	 * 删除。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>删除。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String del() {
	//		log.info("C301Action del.........");
	//		
	//		String id=request.getParameter("id");
	//		TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//		bean1.setId(id);
	//		String msg="1";
	//		try {
	//			msg=consultMsgManager.deleteDataById(bean1);
	//		} catch (Exception e) {
	//			msg=e.getMessage();
	//		}
	//		request.setAttribute("msg",msg);
	//		return SUCCESS;
	//	}
	
	
	
	/**
	 * BEAN类  咨询信息设定
	 * @param bean BEAN类  咨询信息
	 */
	//	/**
	//	 * <p>
	//	 * 编辑。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>编辑。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String edit() {
	//		log.info("C301Action edit.........");
	//		String id=request.getParameter("id");
	//		if(id!=null){
	//			TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//			bean1.setId(id);
	//			bean=consultMsgManager.findDataById(bean1);
	//		}else{
	//			SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
	//			request.setAttribute("date", sdf.format(new Date()));
	//		}
	//		return "edit";
	//	}
	//	/**
	//	 * <p>
	//	 * 信息保存
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>新增。</div>
	//	 * <div>修改。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String save() {
	//		log.info("C301Action edit.........");
	//		if(bean!=null){
	//			String msg="1";
	//			try {
	//					BaseUserBean user = (BaseUserBean) request.getSession().getAttribute(CommonConstant.SESSION_KEY_USER_CUSTOMER_SERVICE);
	//					if(user!=null){
	//						bean.setCreate_ip(getIpAddr());
	//						bean.setCreate_id(user.getUser_id());
	//						bean.setUpdate_ip(getIpAddr());
	//						bean.setUpdate_id(user.getUser_id());
	//					}
	//					msg=consultMsgManager.saveOrUpdateData(bean);
	//			} catch (Exception e) {
	//				msg=e.getMessage();
	//			}
	//			request.setAttribute("msg",msg);
	//		}else{
	//			request.setAttribute("msg", "信息保存失败!");
	//		}
	//		return SUCCESS;
	//	}
	//	/**
	//	 * <p>
	//	 * 删除。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>删除。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String del() {
	//		log.info("C301Action del.........");
	//		
	//		String id=request.getParameter("id");
	//		TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//		bean1.setId(id);
	//		String msg="1";
	//		try {
	//			msg=consultMsgManager.deleteDataById(bean1);
	//		} catch (Exception e) {
	//			msg=e.getMessage();
	//		}
	//		request.setAttribute("msg",msg);
	//		return SUCCESS;
	//	}
	
	
	
	/**
	 * BEAN类  咨询信息取得
	 * @return BEAN类  咨询信息
	 */
	//	/**
	//	 * <p>
	//	 * 编辑。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>编辑。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String edit() {
	//		log.info("C301Action edit.........");
	//		String id=request.getParameter("id");
	//		if(id!=null){
	//			TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//			bean1.setId(id);
	//			bean=consultMsgManager.findDataById(bean1);
	//		}else{
	//			SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
	//			request.setAttribute("date", sdf.format(new Date()));
	//		}
	//		return "edit";
	//	}
	//	/**
	//	 * <p>
	//	 * 信息保存
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>新增。</div>
	//	 * <div>修改。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String save() {
	//		log.info("C301Action edit.........");
	//		if(bean!=null){
	//			String msg="1";
	//			try {
	//					BaseUserBean user = (BaseUserBean) request.getSession().getAttribute(CommonConstant.SESSION_KEY_USER_CUSTOMER_SERVICE);
	//					if(user!=null){
	//						bean.setCreate_ip(getIpAddr());
	//						bean.setCreate_id(user.getUser_id());
	//						bean.setUpdate_ip(getIpAddr());
	//						bean.setUpdate_id(user.getUser_id());
	//					}
	//					msg=consultMsgManager.saveOrUpdateData(bean);
	//			} catch (Exception e) {
	//				msg=e.getMessage();
	//			}
	//			request.setAttribute("msg",msg);
	//		}else{
	//			request.setAttribute("msg", "信息保存失败!");
	//		}
	//		return SUCCESS;
	//	}
	//	/**
	//	 * <p>
	//	 * 删除。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>删除。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String del() {
	//		log.info("C301Action del.........");
	//		
	//		String id=request.getParameter("id");
	//		TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//		bean1.setId(id);
	//		String msg="1";
	//		try {
	//			msg=consultMsgManager.deleteDataById(bean1);
	//		} catch (Exception e) {
	//			msg=e.getMessage();
	//		}
	//		request.setAttribute("msg",msg);
	//		return SUCCESS;
	//	}
	
	
	
	/**
	 * BEAN类  咨询信息取得
	 * @return BEAN类  咨询信息
	 */
	//	/**
	//	 * <p>
	//	 * 编辑。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>编辑。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String edit() {
	//		log.info("C301Action edit.........");
	//		String id=request.getParameter("id");
	//		if(id!=null){
	//			TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//			bean1.setId(id);
	//			bean=consultMsgManager.findDataById(bean1);
	//		}else{
	//			SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
	//			request.setAttribute("date", sdf.format(new Date()));
	//		}
	//		return "edit";
	//	}
	//	/**
	//	 * <p>
	//	 * 信息保存
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>新增。</div>
	//	 * <div>修改。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String save() {
	//		log.info("C301Action edit.........");
	//		if(bean!=null){
	//			String msg="1";
	//			try {
	//					BaseUserBean user = (BaseUserBean) request.getSession().getAttribute(CommonConstant.SESSION_KEY_USER_CUSTOMER_SERVICE);
	//					if(user!=null){
	//						bean.setCreate_ip(getIpAddr());
	//						bean.setCreate_id(user.getUser_id());
	//						bean.setUpdate_ip(getIpAddr());
	//						bean.setUpdate_id(user.getUser_id());
	//					}
	//					msg=consultMsgManager.saveOrUpdateData(bean);
	//			} catch (Exception e) {
	//				msg=e.getMessage();
	//			}
	//			request.setAttribute("msg",msg);
	//		}else{
	//			request.setAttribute("msg", "信息保存失败!");
	//		}
	//		return SUCCESS;
	//	}
	//	/**
	//	 * <p>
	//	 * 删除。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>删除。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String del() {
	//		log.info("C301Action del.........");
	//		
	//		String id=request.getParameter("id");
	//		TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//		bean1.setId(id);
	//		String msg="1";
	//		try {
	//			msg=consultMsgManager.deleteDataById(bean1);
	//		} catch (Exception e) {
	//			msg=e.getMessage();
	//		}
	//		request.setAttribute("msg",msg);
	//		return SUCCESS;
	//	}
	
	
	
	/**
	 * BEAN类  咨询信息设定
	 * @param bean BEAN类  咨询信息
	 */
	//	/**
	//	 * <p>
	//	 * 编辑。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>编辑。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String edit() {
	//		log.info("C301Action edit.........");
	//		String id=request.getParameter("id");
	//		if(id!=null){
	//			TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//			bean1.setId(id);
	//			bean=consultMsgManager.findDataById(bean1);
	//		}else{
	//			SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
	//			request.setAttribute("date", sdf.format(new Date()));
	//		}
	//		return "edit";
	//	}
	//	/**
	//	 * <p>
	//	 * 信息保存
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>新增。</div>
	//	 * <div>修改。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String save() {
	//		log.info("C301Action edit.........");
	//		if(bean!=null){
	//			String msg="1";
	//			try {
	//					BaseUserBean user = (BaseUserBean) request.getSession().getAttribute(CommonConstant.SESSION_KEY_USER_CUSTOMER_SERVICE);
	//					if(user!=null){
	//						bean.setCreate_ip(getIpAddr());
	//						bean.setCreate_id(user.getUser_id());
	//						bean.setUpdate_ip(getIpAddr());
	//						bean.setUpdate_id(user.getUser_id());
	//					}
	//					msg=consultMsgManager.saveOrUpdateData(bean);
	//			} catch (Exception e) {
	//				msg=e.getMessage();
	//			}
	//			request.setAttribute("msg",msg);
	//		}else{
	//			request.setAttribute("msg", "信息保存失败!");
	//		}
	//		return SUCCESS;
	//	}
	//	/**
	//	 * <p>
	//	 * 删除。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>删除。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String del() {
	//		log.info("C301Action del.........");
	//		
	//		String id=request.getParameter("id");
	//		TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//		bean1.setId(id);
	//		String msg="1";
	//		try {
	//			msg=consultMsgManager.deleteDataById(bean1);
	//		} catch (Exception e) {
	//			msg=e.getMessage();
	//		}
	//		request.setAttribute("msg",msg);
	//		return SUCCESS;
	//	}
	
	
	
	/**
	 * BEAN类  咨询信息取得
	 * @return BEAN类  咨询信息
	 */
	//	/**
	//	 * <p>
	//	 * 编辑。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>编辑。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String edit() {
	//		log.info("C301Action edit.........");
	//		String id=request.getParameter("id");
	//		if(id!=null){
	//			TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//			bean1.setId(id);
	//			bean=consultMsgManager.findDataById(bean1);
	//		}else{
	//			SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
	//			request.setAttribute("date", sdf.format(new Date()));
	//		}
	//		return "edit";
	//	}
	//	/**
	//	 * <p>
	//	 * 信息保存
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>新增。</div>
	//	 * <div>修改。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String save() {
	//		log.info("C301Action edit.........");
	//		if(bean!=null){
	//			String msg="1";
	//			try {
	//					BaseUserBean user = (BaseUserBean) request.getSession().getAttribute(CommonConstant.SESSION_KEY_USER_CUSTOMER_SERVICE);
	//					if(user!=null){
	//						bean.setCreate_ip(getIpAddr());
	//						bean.setCreate_id(user.getUser_id());
	//						bean.setUpdate_ip(getIpAddr());
	//						bean.setUpdate_id(user.getUser_id());
	//					}
	//					msg=consultMsgManager.saveOrUpdateData(bean);
	//			} catch (Exception e) {
	//				msg=e.getMessage();
	//			}
	//			request.setAttribute("msg",msg);
	//		}else{
	//			request.setAttribute("msg", "信息保存失败!");
	//		}
	//		return SUCCESS;
	//	}
	//	/**
	//	 * <p>
	//	 * 删除。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>删除。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String del() {
	//		log.info("C301Action del.........");
	//		
	//		String id=request.getParameter("id");
	//		TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//		bean1.setId(id);
	//		String msg="1";
	//		try {
	//			msg=consultMsgManager.deleteDataById(bean1);
	//		} catch (Exception e) {
	//			msg=e.getMessage();
	//		}
	//		request.setAttribute("msg",msg);
	//		return SUCCESS;
	//	}
	
	
	
	/**
	 * BEAN类  咨询信息设定
	 * @param bean BEAN类  咨询信息
	 */
	//	/**
	//	 * <p>
	//	 * 编辑。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>编辑。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String edit() {
	//		log.info("C301Action edit.........");
	//		String id=request.getParameter("id");
	//		if(id!=null){
	//			TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//			bean1.setId(id);
	//			bean=consultMsgManager.findDataById(bean1);
	//		}else{
	//			SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
	//			request.setAttribute("date", sdf.format(new Date()));
	//		}
	//		return "edit";
	//	}
	//	/**
	//	 * <p>
	//	 * 信息保存
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>新增。</div>
	//	 * <div>修改。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String save() {
	//		log.info("C301Action edit.........");
	//		if(bean!=null){
	//			String msg="1";
	//			try {
	//					BaseUserBean user = (BaseUserBean) request.getSession().getAttribute(CommonConstant.SESSION_KEY_USER_CUSTOMER_SERVICE);
	//					if(user!=null){
	//						bean.setCreate_ip(getIpAddr());
	//						bean.setCreate_id(user.getUser_id());
	//						bean.setUpdate_ip(getIpAddr());
	//						bean.setUpdate_id(user.getUser_id());
	//					}
	//					msg=consultMsgManager.saveOrUpdateData(bean);
	//			} catch (Exception e) {
	//				msg=e.getMessage();
	//			}
	//			request.setAttribute("msg",msg);
	//		}else{
	//			request.setAttribute("msg", "信息保存失败!");
	//		}
	//		return SUCCESS;
	//	}
	//	/**
	//	 * <p>
	//	 * 删除。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>删除。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String del() {
	//		log.info("C301Action del.........");
	//		
	//		String id=request.getParameter("id");
	//		TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//		bean1.setId(id);
	//		String msg="1";
	//		try {
	//			msg=consultMsgManager.deleteDataById(bean1);
	//		} catch (Exception e) {
	//			msg=e.getMessage();
	//		}
	//		request.setAttribute("msg",msg);
	//		return SUCCESS;
	//	}
	
	
	
	/**
	 * BEAN类  咨询信息取得
	 * @return BEAN类  咨询信息
	 */
	//	/**
	//	 * <p>
	//	 * 编辑。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>编辑。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String edit() {
	//		log.info("C301Action edit.........");
	//		String id=request.getParameter("id");
	//		if(id!=null){
	//			TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//			bean1.setId(id);
	//			bean=consultMsgManager.findDataById(bean1);
	//		}else{
	//			SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
	//			request.setAttribute("date", sdf.format(new Date()));
	//		}
	//		return "edit";
	//	}
	//	/**
	//	 * <p>
	//	 * 信息保存
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>新增。</div>
	//	 * <div>修改。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String save() {
	//		log.info("C301Action edit.........");
	//		if(bean!=null){
	//			String msg="1";
	//			try {
	//					BaseUserBean user = (BaseUserBean) request.getSession().getAttribute(CommonConstant.SESSION_KEY_USER_CUSTOMER_SERVICE);
	//					if(user!=null){
	//						bean.setCreate_ip(getIpAddr());
	//						bean.setCreate_id(user.getUser_id());
	//						bean.setUpdate_ip(getIpAddr());
	//						bean.setUpdate_id(user.getUser_id());
	//					}
	//					msg=consultMsgManager.saveOrUpdateData(bean);
	//			} catch (Exception e) {
	//				msg=e.getMessage();
	//			}
	//			request.setAttribute("msg",msg);
	//		}else{
	//			request.setAttribute("msg", "信息保存失败!");
	//		}
	//		return SUCCESS;
	//	}
	//	/**
	//	 * <p>
	//	 * 删除。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>删除。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String del() {
	//		log.info("C301Action del.........");
	//		
	//		String id=request.getParameter("id");
	//		TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//		bean1.setId(id);
	//		String msg="1";
	//		try {
	//			msg=consultMsgManager.deleteDataById(bean1);
	//		} catch (Exception e) {
	//			msg=e.getMessage();
	//		}
	//		request.setAttribute("msg",msg);
	//		return SUCCESS;
	//	}
	
	
	
	/**
	 * BEAN类  咨询信息取得
	 * @return BEAN类  咨询信息
	 */
	//	/**
	//	 * <p>
	//	 * 编辑。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>编辑。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String edit() {
	//		log.info("C301Action edit.........");
	//		String id=request.getParameter("id");
	//		if(id!=null){
	//			TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//			bean1.setId(id);
	//			bean=consultMsgManager.findDataById(bean1);
	//		}else{
	//			SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
	//			request.setAttribute("date", sdf.format(new Date()));
	//		}
	//		return "edit";
	//	}
	//	/**
	//	 * <p>
	//	 * 信息保存
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>新增。</div>
	//	 * <div>修改。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String save() {
	//		log.info("C301Action edit.........");
	//		if(bean!=null){
	//			String msg="1";
	//			try {
	//					BaseUserBean user = (BaseUserBean) request.getSession().getAttribute(CommonConstant.SESSION_KEY_USER_CUSTOMER_SERVICE);
	//					if(user!=null){
	//						bean.setCreate_ip(getIpAddr());
	//						bean.setCreate_id(user.getUser_id());
	//						bean.setUpdate_ip(getIpAddr());
	//						bean.setUpdate_id(user.getUser_id());
	//					}
	//					msg=consultMsgManager.saveOrUpdateData(bean);
	//			} catch (Exception e) {
	//				msg=e.getMessage();
	//			}
	//			request.setAttribute("msg",msg);
	//		}else{
	//			request.setAttribute("msg", "信息保存失败!");
	//		}
	//		return SUCCESS;
	//	}
	//	/**
	//	 * <p>
	//	 * 删除。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>删除。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String del() {
	//		log.info("C301Action del.........");
	//		
	//		String id=request.getParameter("id");
	//		TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//		bean1.setId(id);
	//		String msg="1";
	//		try {
	//			msg=consultMsgManager.deleteDataById(bean1);
	//		} catch (Exception e) {
	//			msg=e.getMessage();
	//		}
	//		request.setAttribute("msg",msg);
	//		return SUCCESS;
	//	}
	
	
	
	/**
	 * BEAN类  咨询信息设定
	 * @param bean BEAN类  咨询信息
	 */
	//	/**
	//	 * <p>
	//	 * 编辑。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>编辑。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String edit() {
	//		log.info("C301Action edit.........");
	//		String id=request.getParameter("id");
	//		if(id!=null){
	//			TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//			bean1.setId(id);
	//			bean=consultMsgManager.findDataById(bean1);
	//		}else{
	//			SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
	//			request.setAttribute("date", sdf.format(new Date()));
	//		}
	//		return "edit";
	//	}
	//	/**
	//	 * <p>
	//	 * 信息保存
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>新增。</div>
	//	 * <div>修改。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String save() {
	//		log.info("C301Action edit.........");
	//		if(bean!=null){
	//			String msg="1";
	//			try {
	//					BaseUserBean user = (BaseUserBean) request.getSession().getAttribute(CommonConstant.SESSION_KEY_USER_CUSTOMER_SERVICE);
	//					if(user!=null){
	//						bean.setCreate_ip(getIpAddr());
	//						bean.setCreate_id(user.getUser_id());
	//						bean.setUpdate_ip(getIpAddr());
	//						bean.setUpdate_id(user.getUser_id());
	//					}
	//					msg=consultMsgManager.saveOrUpdateData(bean);
	//			} catch (Exception e) {
	//				msg=e.getMessage();
	//			}
	//			request.setAttribute("msg",msg);
	//		}else{
	//			request.setAttribute("msg", "信息保存失败!");
	//		}
	//		return SUCCESS;
	//	}
	//	/**
	//	 * <p>
	//	 * 删除。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>删除。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String del() {
	//		log.info("C301Action del.........");
	//		
	//		String id=request.getParameter("id");
	//		TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//		bean1.setId(id);
	//		String msg="1";
	//		try {
	//			msg=consultMsgManager.deleteDataById(bean1);
	//		} catch (Exception e) {
	//			msg=e.getMessage();
	//		}
	//		request.setAttribute("msg",msg);
	//		return SUCCESS;
	//	}
	
	
	
	/**
	 * BEAN类  咨询信息取得
	 * @return BEAN类  咨询信息
	 */
	//	/**
	//	 * <p>
	//	 * 编辑。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>编辑。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String edit() {
	//		log.info("C301Action edit.........");
	//		String id=request.getParameter("id");
	//		if(id!=null){
	//			TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//			bean1.setId(id);
	//			bean=consultMsgManager.findDataById(bean1);
	//		}else{
	//			SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
	//			request.setAttribute("date", sdf.format(new Date()));
	//		}
	//		return "edit";
	//	}
	//	/**
	//	 * <p>
	//	 * 信息保存
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>新增。</div>
	//	 * <div>修改。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String save() {
	//		log.info("C301Action edit.........");
	//		if(bean!=null){
	//			String msg="1";
	//			try {
	//					BaseUserBean user = (BaseUserBean) request.getSession().getAttribute(CommonConstant.SESSION_KEY_USER_CUSTOMER_SERVICE);
	//					if(user!=null){
	//						bean.setCreate_ip(getIpAddr());
	//						bean.setCreate_id(user.getUser_id());
	//						bean.setUpdate_ip(getIpAddr());
	//						bean.setUpdate_id(user.getUser_id());
	//					}
	//					msg=consultMsgManager.saveOrUpdateData(bean);
	//			} catch (Exception e) {
	//				msg=e.getMessage();
	//			}
	//			request.setAttribute("msg",msg);
	//		}else{
	//			request.setAttribute("msg", "信息保存失败!");
	//		}
	//		return SUCCESS;
	//	}
	//	/**
	//	 * <p>
	//	 * 删除。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>删除。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String del() {
	//		log.info("C301Action del.........");
	//		
	//		String id=request.getParameter("id");
	//		TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//		bean1.setId(id);
	//		String msg="1";
	//		try {
	//			msg=consultMsgManager.deleteDataById(bean1);
	//		} catch (Exception e) {
	//			msg=e.getMessage();
	//		}
	//		request.setAttribute("msg",msg);
	//		return SUCCESS;
	//	}
	
	
	
	/**
	 * BEAN类  咨询信息取得
	 * @return BEAN类  咨询信息
	 */
	//	/**
	//	 * <p>
	//	 * 编辑。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>编辑。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String edit() {
	//		log.info("C301Action edit.........");
	//		String id=request.getParameter("id");
	//		if(id!=null){
	//			TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//			bean1.setId(id);
	//			bean=consultMsgManager.findDataById(bean1);
	//		}else{
	//			SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
	//			request.setAttribute("date", sdf.format(new Date()));
	//		}
	//		return "edit";
	//	}
	//	/**
	//	 * <p>
	//	 * 信息保存
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>新增。</div>
	//	 * <div>修改。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String save() {
	//		log.info("C301Action edit.........");
	//		if(bean!=null){
	//			String msg="1";
	//			try {
	//					BaseUserBean user = (BaseUserBean) request.getSession().getAttribute(CommonConstant.SESSION_KEY_USER_CUSTOMER_SERVICE);
	//					if(user!=null){
	//						bean.setCreate_ip(getIpAddr());
	//						bean.setCreate_id(user.getUser_id());
	//						bean.setUpdate_ip(getIpAddr());
	//						bean.setUpdate_id(user.getUser_id());
	//					}
	//					msg=consultMsgManager.saveOrUpdateData(bean);
	//			} catch (Exception e) {
	//				msg=e.getMessage();
	//			}
	//			request.setAttribute("msg",msg);
	//		}else{
	//			request.setAttribute("msg", "信息保存失败!");
	//		}
	//		return SUCCESS;
	//	}
	//	/**
	//	 * <p>
	//	 * 删除。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>删除。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String del() {
	//		log.info("C301Action del.........");
	//		
	//		String id=request.getParameter("id");
	//		TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//		bean1.setId(id);
	//		String msg="1";
	//		try {
	//			msg=consultMsgManager.deleteDataById(bean1);
	//		} catch (Exception e) {
	//			msg=e.getMessage();
	//		}
	//		request.setAttribute("msg",msg);
	//		return SUCCESS;
	//	}
	
	
	
	/**
	 * BEAN类  咨询信息设定
	 * @param bean BEAN类  咨询信息
	 */
	//	/**
	//	 * <p>
	//	 * 编辑。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>编辑。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String edit() {
	//		log.info("C301Action edit.........");
	//		String id=request.getParameter("id");
	//		if(id!=null){
	//			TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//			bean1.setId(id);
	//			bean=consultMsgManager.findDataById(bean1);
	//		}else{
	//			SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
	//			request.setAttribute("date", sdf.format(new Date()));
	//		}
	//		return "edit";
	//	}
	//	/**
	//	 * <p>
	//	 * 信息保存
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>新增。</div>
	//	 * <div>修改。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String save() {
	//		log.info("C301Action edit.........");
	//		if(bean!=null){
	//			String msg="1";
	//			try {
	//					BaseUserBean user = (BaseUserBean) request.getSession().getAttribute(CommonConstant.SESSION_KEY_USER_CUSTOMER_SERVICE);
	//					if(user!=null){
	//						bean.setCreate_ip(getIpAddr());
	//						bean.setCreate_id(user.getUser_id());
	//						bean.setUpdate_ip(getIpAddr());
	//						bean.setUpdate_id(user.getUser_id());
	//					}
	//					msg=consultMsgManager.saveOrUpdateData(bean);
	//			} catch (Exception e) {
	//				msg=e.getMessage();
	//			}
	//			request.setAttribute("msg",msg);
	//		}else{
	//			request.setAttribute("msg", "信息保存失败!");
	//		}
	//		return SUCCESS;
	//	}
	//	/**
	//	 * <p>
	//	 * 删除。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>删除。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String del() {
	//		log.info("C301Action del.........");
	//		
	//		String id=request.getParameter("id");
	//		TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//		bean1.setId(id);
	//		String msg="1";
	//		try {
	//			msg=consultMsgManager.deleteDataById(bean1);
	//		} catch (Exception e) {
	//			msg=e.getMessage();
	//		}
	//		request.setAttribute("msg",msg);
	//		return SUCCESS;
	//	}
	
	
	
	/**
	 * BEAN类  咨询信息取得
	 * @return BEAN类  咨询信息
	 */
	//	/**
	//	 * <p>
	//	 * 编辑。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>编辑。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String edit() {
	//		log.info("C301Action edit.........");
	//		String id=request.getParameter("id");
	//		if(id!=null){
	//			TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//			bean1.setId(id);
	//			bean=consultMsgManager.findDataById(bean1);
	//		}else{
	//			SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
	//			request.setAttribute("date", sdf.format(new Date()));
	//		}
	//		return "edit";
	//	}
	//	/**
	//	 * <p>
	//	 * 信息保存
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>新增。</div>
	//	 * <div>修改。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String save() {
	//		log.info("C301Action edit.........");
	//		if(bean!=null){
	//			String msg="1";
	//			try {
	//					BaseUserBean user = (BaseUserBean) request.getSession().getAttribute(CommonConstant.SESSION_KEY_USER_CUSTOMER_SERVICE);
	//					if(user!=null){
	//						bean.setCreate_ip(getIpAddr());
	//						bean.setCreate_id(user.getUser_id());
	//						bean.setUpdate_ip(getIpAddr());
	//						bean.setUpdate_id(user.getUser_id());
	//					}
	//					msg=consultMsgManager.saveOrUpdateData(bean);
	//			} catch (Exception e) {
	//				msg=e.getMessage();
	//			}
	//			request.setAttribute("msg",msg);
	//		}else{
	//			request.setAttribute("msg", "信息保存失败!");
	//		}
	//		return SUCCESS;
	//	}
	//	/**
	//	 * <p>
	//	 * 删除。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>删除。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String del() {
	//		log.info("C301Action del.........");
	//		
	//		String id=request.getParameter("id");
	//		TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//		bean1.setId(id);
	//		String msg="1";
	//		try {
	//			msg=consultMsgManager.deleteDataById(bean1);
	//		} catch (Exception e) {
	//			msg=e.getMessage();
	//		}
	//		request.setAttribute("msg",msg);
	//		return SUCCESS;
	//	}
	
	
	
	/**
	 * BEAN类  咨询信息设定
	 * @param bean BEAN类  咨询信息
	 */
	//	/**
	//	 * <p>
	//	 * 编辑。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>编辑。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String edit() {
	//		log.info("C301Action edit.........");
	//		String id=request.getParameter("id");
	//		if(id!=null){
	//			TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//			bean1.setId(id);
	//			bean=consultMsgManager.findDataById(bean1);
	//		}else{
	//			SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
	//			request.setAttribute("date", sdf.format(new Date()));
	//		}
	//		return "edit";
	//	}
	//	/**
	//	 * <p>
	//	 * 信息保存
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>新增。</div>
	//	 * <div>修改。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String save() {
	//		log.info("C301Action edit.........");
	//		if(bean!=null){
	//			String msg="1";
	//			try {
	//					BaseUserBean user = (BaseUserBean) request.getSession().getAttribute(CommonConstant.SESSION_KEY_USER_CUSTOMER_SERVICE);
	//					if(user!=null){
	//						bean.setCreate_ip(getIpAddr());
	//						bean.setCreate_id(user.getUser_id());
	//						bean.setUpdate_ip(getIpAddr());
	//						bean.setUpdate_id(user.getUser_id());
	//					}
	//					msg=consultMsgManager.saveOrUpdateData(bean);
	//			} catch (Exception e) {
	//				msg=e.getMessage();
	//			}
	//			request.setAttribute("msg",msg);
	//		}else{
	//			request.setAttribute("msg", "信息保存失败!");
	//		}
	//		return SUCCESS;
	//	}
	//	/**
	//	 * <p>
	//	 * 删除。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>删除。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String del() {
	//		log.info("C301Action del.........");
	//		
	//		String id=request.getParameter("id");
	//		TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//		bean1.setId(id);
	//		String msg="1";
	//		try {
	//			msg=consultMsgManager.deleteDataById(bean1);
	//		} catch (Exception e) {
	//			msg=e.getMessage();
	//		}
	//		request.setAttribute("msg",msg);
	//		return SUCCESS;
	//	}
	
	
	
	/**
	 * BEAN类  咨询信息取得
	 * @return BEAN类  咨询信息
	 */
	//	/**
	//	 * <p>
	//	 * 编辑。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>编辑。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String edit() {
	//		log.info("C301Action edit.........");
	//		String id=request.getParameter("id");
	//		if(id!=null){
	//			TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//			bean1.setId(id);
	//			bean=consultMsgManager.findDataById(bean1);
	//		}else{
	//			SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
	//			request.setAttribute("date", sdf.format(new Date()));
	//		}
	//		return "edit";
	//	}
	//	/**
	//	 * <p>
	//	 * 信息保存
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>新增。</div>
	//	 * <div>修改。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String save() {
	//		log.info("C301Action edit.........");
	//		if(bean!=null){
	//			String msg="1";
	//			try {
	//					BaseUserBean user = (BaseUserBean) request.getSession().getAttribute(CommonConstant.SESSION_KEY_USER_CUSTOMER_SERVICE);
	//					if(user!=null){
	//						bean.setCreate_ip(getIpAddr());
	//						bean.setCreate_id(user.getUser_id());
	//						bean.setUpdate_ip(getIpAddr());
	//						bean.setUpdate_id(user.getUser_id());
	//					}
	//					msg=consultMsgManager.saveOrUpdateData(bean);
	//			} catch (Exception e) {
	//				msg=e.getMessage();
	//			}
	//			request.setAttribute("msg",msg);
	//		}else{
	//			request.setAttribute("msg", "信息保存失败!");
	//		}
	//		return SUCCESS;
	//	}
	//	/**
	//	 * <p>
	//	 * 删除。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>删除。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String del() {
	//		log.info("C301Action del.........");
	//		
	//		String id=request.getParameter("id");
	//		TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//		bean1.setId(id);
	//		String msg="1";
	//		try {
	//			msg=consultMsgManager.deleteDataById(bean1);
	//		} catch (Exception e) {
	//			msg=e.getMessage();
	//		}
	//		request.setAttribute("msg",msg);
	//		return SUCCESS;
	//	}
	
	
	
	/**
	 * BEAN类  咨询信息取得
	 * @return BEAN类  咨询信息
	 */
	//	/**
	//	 * <p>
	//	 * 编辑。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>编辑。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String edit() {
	//		log.info("C301Action edit.........");
	//		String id=request.getParameter("id");
	//		if(id!=null){
	//			TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//			bean1.setId(id);
	//			bean=consultMsgManager.findDataById(bean1);
	//		}else{
	//			SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
	//			request.setAttribute("date", sdf.format(new Date()));
	//		}
	//		return "edit";
	//	}
	//	/**
	//	 * <p>
	//	 * 信息保存
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>新增。</div>
	//	 * <div>修改。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String save() {
	//		log.info("C301Action edit.........");
	//		if(bean!=null){
	//			String msg="1";
	//			try {
	//					BaseUserBean user = (BaseUserBean) request.getSession().getAttribute(CommonConstant.SESSION_KEY_USER_CUSTOMER_SERVICE);
	//					if(user!=null){
	//						bean.setCreate_ip(getIpAddr());
	//						bean.setCreate_id(user.getUser_id());
	//						bean.setUpdate_ip(getIpAddr());
	//						bean.setUpdate_id(user.getUser_id());
	//					}
	//					msg=consultMsgManager.saveOrUpdateData(bean);
	//			} catch (Exception e) {
	//				msg=e.getMessage();
	//			}
	//			request.setAttribute("msg",msg);
	//		}else{
	//			request.setAttribute("msg", "信息保存失败!");
	//		}
	//		return SUCCESS;
	//	}
	//	/**
	//	 * <p>
	//	 * 删除。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>删除。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String del() {
	//		log.info("C301Action del.........");
	//		
	//		String id=request.getParameter("id");
	//		TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//		bean1.setId(id);
	//		String msg="1";
	//		try {
	//			msg=consultMsgManager.deleteDataById(bean1);
	//		} catch (Exception e) {
	//			msg=e.getMessage();
	//		}
	//		request.setAttribute("msg",msg);
	//		return SUCCESS;
	//	}
	
	
	
	/**
	 * BEAN类  咨询信息设定
	 * @param bean BEAN类  咨询信息
	 */
	//	/**
	//	 * <p>
	//	 * 编辑。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>编辑。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String edit() {
	//		log.info("C301Action edit.........");
	//		String id=request.getParameter("id");
	//		if(id!=null){
	//			TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//			bean1.setId(id);
	//			bean=consultMsgManager.findDataById(bean1);
	//		}else{
	//			SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
	//			request.setAttribute("date", sdf.format(new Date()));
	//		}
	//		return "edit";
	//	}
	//	/**
	//	 * <p>
	//	 * 信息保存
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>新增。</div>
	//	 * <div>修改。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String save() {
	//		log.info("C301Action edit.........");
	//		if(bean!=null){
	//			String msg="1";
	//			try {
	//					BaseUserBean user = (BaseUserBean) request.getSession().getAttribute(CommonConstant.SESSION_KEY_USER_CUSTOMER_SERVICE);
	//					if(user!=null){
	//						bean.setCreate_ip(getIpAddr());
	//						bean.setCreate_id(user.getUser_id());
	//						bean.setUpdate_ip(getIpAddr());
	//						bean.setUpdate_id(user.getUser_id());
	//					}
	//					msg=consultMsgManager.saveOrUpdateData(bean);
	//			} catch (Exception e) {
	//				msg=e.getMessage();
	//			}
	//			request.setAttribute("msg",msg);
	//		}else{
	//			request.setAttribute("msg", "信息保存失败!");
	//		}
	//		return SUCCESS;
	//	}
	//	/**
	//	 * <p>
	//	 * 删除。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>删除。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String del() {
	//		log.info("C301Action del.........");
	//		
	//		String id=request.getParameter("id");
	//		TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//		bean1.setId(id);
	//		String msg="1";
	//		try {
	//			msg=consultMsgManager.deleteDataById(bean1);
	//		} catch (Exception e) {
	//			msg=e.getMessage();
	//		}
	//		request.setAttribute("msg",msg);
	//		return SUCCESS;
	//	}
	
	
	
	/**
	 * BEAN类  咨询信息取得
	 * @return BEAN类  咨询信息
	 */
	//	/**
	//	 * <p>
	//	 * 编辑。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>编辑。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String edit() {
	//		log.info("C301Action edit.........");
	//		String id=request.getParameter("id");
	//		if(id!=null){
	//			TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//			bean1.setId(id);
	//			bean=consultMsgManager.findDataById(bean1);
	//		}else{
	//			SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
	//			request.setAttribute("date", sdf.format(new Date()));
	//		}
	//		return "edit";
	//	}
	//	/**
	//	 * <p>
	//	 * 信息保存
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>新增。</div>
	//	 * <div>修改。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String save() {
	//		log.info("C301Action edit.........");
	//		if(bean!=null){
	//			String msg="1";
	//			try {
	//					BaseUserBean user = (BaseUserBean) request.getSession().getAttribute(CommonConstant.SESSION_KEY_USER_CUSTOMER_SERVICE);
	//					if(user!=null){
	//						bean.setCreate_ip(getIpAddr());
	//						bean.setCreate_id(user.getUser_id());
	//						bean.setUpdate_ip(getIpAddr());
	//						bean.setUpdate_id(user.getUser_id());
	//					}
	//					msg=consultMsgManager.saveOrUpdateData(bean);
	//			} catch (Exception e) {
	//				msg=e.getMessage();
	//			}
	//			request.setAttribute("msg",msg);
	//		}else{
	//			request.setAttribute("msg", "信息保存失败!");
	//		}
	//		return SUCCESS;
	//	}
	//	/**
	//	 * <p>
	//	 * 删除。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>删除。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String del() {
	//		log.info("C301Action del.........");
	//		
	//		String id=request.getParameter("id");
	//		TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//		bean1.setId(id);
	//		String msg="1";
	//		try {
	//			msg=consultMsgManager.deleteDataById(bean1);
	//		} catch (Exception e) {
	//			msg=e.getMessage();
	//		}
	//		request.setAttribute("msg",msg);
	//		return SUCCESS;
	//	}
	
	
	
	/**
	 * BEAN类  咨询信息设定
	 * @param bean BEAN类  咨询信息
	 */
	//	/**
	//	 * <p>
	//	 * 编辑。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>编辑。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String edit() {
	//		log.info("C301Action edit.........");
	//		String id=request.getParameter("id");
	//		if(id!=null){
	//			TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//			bean1.setId(id);
	//			bean=consultMsgManager.findDataById(bean1);
	//		}else{
	//			SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
	//			request.setAttribute("date", sdf.format(new Date()));
	//		}
	//		return "edit";
	//	}
	//	/**
	//	 * <p>
	//	 * 信息保存
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>新增。</div>
	//	 * <div>修改。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String save() {
	//		log.info("C301Action edit.........");
	//		if(bean!=null){
	//			String msg="1";
	//			try {
	//					BaseUserBean user = (BaseUserBean) request.getSession().getAttribute(CommonConstant.SESSION_KEY_USER_CUSTOMER_SERVICE);
	//					if(user!=null){
	//						bean.setCreate_ip(getIpAddr());
	//						bean.setCreate_id(user.getUser_id());
	//						bean.setUpdate_ip(getIpAddr());
	//						bean.setUpdate_id(user.getUser_id());
	//					}
	//					msg=consultMsgManager.saveOrUpdateData(bean);
	//			} catch (Exception e) {
	//				msg=e.getMessage();
	//			}
	//			request.setAttribute("msg",msg);
	//		}else{
	//			request.setAttribute("msg", "信息保存失败!");
	//		}
	//		return SUCCESS;
	//	}
	//	/**
	//	 * <p>
	//	 * 删除。
	//	 * </p>
	//	 * <ol>
	//	 * [功能概要] <div>删除。</div>
	//	 * </ol>
	//	 * @return 转发字符串
	//	 */
	//	public String del() {
	//		log.info("C301Action del.........");
	//		
	//		String id=request.getParameter("id");
	//		TcCsConsultMsgBean bean1=new TcCsConsultMsgBean();
	//		bean1.setId(id);
	//		String msg="1";
	//		try {
	//			msg=consultMsgManager.deleteDataById(bean1);
	//		} catch (Exception e) {
	//			msg=e.getMessage();
	//		}
	//		request.setAttribute("msg",msg);
	//		return SUCCESS;
	//	}
	
	/**
	 * BEAN类  咨询信息设定
	 * @param bean BEAN类  咨询信息
	 */
	public void setBean(TcCsConsultMsgBean bean) {
	    this.bean = bean;
	}

	/**
	 * BEAN类  咨询取得
	 * @return BEAN类  咨询
	 */
	public TcCsConsultBean getCbean() {
	    return cbean;
	}

	/**
	 * BEAN类  咨询设定
	 * @param cbean BEAN类  咨询
	 */
	public void setCbean(TcCsConsultBean cbean) {
	    this.cbean = cbean;
	}

	/**
	 * BEAN类  咨询 集合取得
	 * @return BEAN类  咨询 集合
	 */
	public List<TcCsConsultMsgBean> getBeans() {
	    return beans;
	}

	/**
	 * BEAN类  咨询 集合设定
	 * @param beans BEAN类  咨询 集合
	 */
	public void setBeans(List<TcCsConsultMsgBean> beans) {
	    this.beans = beans;
	}

	/**
	 * 咨询 业务处理取得
	 * @return 咨询 业务处理
	 */
	public IConsultManager getConsultManager() {
	    return consultManager;
	}

	/**
	 * 咨询 业务处理设定
	 * @param consultManager 咨询 业务处理
	 */
	public void setConsultManager(IConsultManager consultManager) {
	    this.consultManager = consultManager;
	}

	/**
	 * 咨询管理 业务处理取得
	 * @return 咨询管理 业务处理
	 */
	public IConsultMsgManager getConsultMsgManager() {
	    return consultMsgManager;
	}

	/**
	 * 咨询管理 业务处理设定
	 * @param consultMsgManager 咨询管理 业务处理
	 */
	public void setConsultMsgManager(IConsultMsgManager consultMsgManager) {
	    this.consultMsgManager = consultMsgManager;
	}

	/**
	 * redis缓存工具类取得
	 * @return redis缓存工具类
	 */
	public JedisHelper getJedisHelper() {
	    return jedisHelper;
	}

	/**
	 * redis缓存工具类设定
	 * @param jedisHelper redis缓存工具类
	 */
	public void setJedisHelper(JedisHelper jedisHelper) {
	    this.jedisHelper = jedisHelper;
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
