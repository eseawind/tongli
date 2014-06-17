/*
 * home
 *
 * VERSION  DATE        BY              REASON
 * -------- ----------- --------------- ------------------------------------------
 * 1.00     2012.11.26  wuxiaogang           程序・发布
 * -------- ----------- --------------- ------------------------------------------
 * Copyright 2014 车主管家  System. - All Rights Reserved.
 *
 */
package cn.com.softvan.web.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import cn.com.softvan.bean.BaseUserBean;
import cn.com.softvan.bean.backuser.TcUaUmBaseUserBean;
import cn.com.softvan.common.CipherUtils;
import cn.com.softvan.common.CommonConstant;
import cn.com.softvan.common.IpUtils;
import cn.com.softvan.common.Validator;
import cn.com.softvan.common.VerifyUtils;
import cn.com.softvan.service.user.ITcUaUmBaseUserManager;

public class HomeAction  extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6103432072290645133L;
	private static final transient Logger log = Logger
			.getLogger(HomeAction.class);

	/** 默认的构造函数 */
	public HomeAction() {
		log.info("HomeAction constructed");
	}
	
	/**用户管理  业务处理*/
	private ITcUaUmBaseUserManager userManager;
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
		log.info("HomeAction login");
			/*//用户名密码正确
			 if("bxadmin".equals(request.getParameter("username"))&&"admin".equals(request.getParameter("password"))){
				//用户信息
				BaseUserBean userBean=new BaseUserBean();
				//用户名
				userBean.setUser_id(request.getParameter("username"));
				//用户密码
				userBean.setPwd(request.getParameter("password"));
				request.getSession().setAttribute(CommonConstant.SESSION_KEY_USER, userBean);
				return "home";
			}else{
				request.setAttribute("msg","登陆失败!用户名或密码错误!");
			}*/
			String authCode=request.getParameter("authCode");
			if(Validator.isEmpty(authCode)||VerifyUtils.checkVeifyCode(request, authCode)){
				request.setAttribute("msg","验证码校验失败!");
				return "login";
			}
            String username = request.getParameter("username");
            if(username==null||(username!=null&&username.trim().equals(""))){
            	request.setAttribute("msg","登陆失败!用户名不能为空!");
				return "login";
            }
			//密码
			String password = request.getParameter("password");
			if(password!=null&&!password.trim().equals("")){
				password=CipherUtils.md5(password);
			}else{
				request.setAttribute("msg","登陆失败!密码不能为空!");
				return "login";
			}
			
			HttpSession session = request.getSession();
			if(session != null){
				TcUaUmBaseUserBean bean=new TcUaUmBaseUserBean();
				BaseUserBean baseBean=new BaseUserBean();
				bean.setUsername(username);
				bean.setPasswd(password);
				TcUaUmBaseUserBean 	userbean=userManager.userLogin(bean);
				if(userbean!=null){
					  request.getSession().setAttribute(CommonConstant.SESSION_SYS_KEY_USER, userbean);
					  request.getSession().setAttribute(CommonConstant.SESSION_SYS_KEY_USER_PERMS, userbean.getPerms());
					  baseBean.setUser_id(userbean.getUsername());
					  baseBean.setId(userbean.getId());
					  request.getSession().setAttribute(CommonConstant.SESSION_KEY_USER,baseBean);
					  
//					  //TODO --客服信息--
//					  TcCsCustomerServiceBean customerServiceBean=new TcCsCustomerServiceBean();
//					  customerServiceBean.setId(userbean.getId());
//					  customerServiceBean.setUid(userbean.getUsername());
//					  request.getSession().setAttribute(CommonConstant.SESSION_KEY_USER_CUSTOMER_SERVICE, customerServiceBean);
					  
					 return "home";
				 }else{
					request.setAttribute("msg","登陆失败!用户名或密码错误!");
				}
			}

		return "login";
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
		log.info("HomeAction logout");
		//清空用户登录信息
		HttpServletRequest request = ServletActionContext.getRequest();
		SessionUtils.clearAdminSession(request);
		return "login";
	}
	
	
	
	/**
	 * 编辑登录用户相关信息
	 * @return
	 */
	public String edit(){
		log.info("HomeAction ..userEdit...");
		HttpServletRequest request = ServletActionContext.getRequest();
		//用户id
		String id=request.getParameter("id");
	
		if(id!=null&&!"".equals(id)){
			request.setAttribute("id",id);
			if(StringUtils.isNotBlank(id)){//修改
				TcUaUmBaseUserBean bean=new TcUaUmBaseUserBean();
				bean.setId(id);
				//根据id查询信息
				bean = userManager.findDataById(bean);
				if(bean == null ){
					bean = new TcUaUmBaseUserBean();
				}
				request.setAttribute("bean", bean);
		 }
		 }
		return "useredit";
		
		
	}
	

	/**
	 * 保存用户相关信息
	 * @return
	 */
	public String save() throws Exception{
		log.info("HomeAction ..usersave...");
		HttpServletRequest request = ServletActionContext.getRequest();
		String msg="1";
		TcUaUmBaseUserBean userbean = (TcUaUmBaseUserBean)request.getSession().getAttribute(CommonConstant.SESSION_SYS_KEY_USER);
		String id=request.getParameter("id");
	
		String password="";
		if(userbean!=null){
			//用户id
			try {
				request.setAttribute("id",id);
				request.setAttribute("bean", userbean);
			
			String oldpassword=request.getParameter("oldpassword");
			
		
			if(oldpassword!=null&&!oldpassword.trim().equals("")){
				oldpassword=CipherUtils.md5(oldpassword);
			}else{
				request.setAttribute("msg","原密码不能为空!");
				return "useredit";
			}
			
			if(!oldpassword.equals(userbean.getPasswd())){
				request.setAttribute("msg","原密码不正确!");
				return "useredit";
				
			}
			 password=request.getParameter("password");
			
			
			if(password==null||(password!=null&&password.trim().equals(""))){
				request.setAttribute("msg","新密码不能为空!");
				return "useredit";
			}
			userbean.setPasswd(password);
			userbean.setId(id);
			userbean.setUpdate_ip(IpUtils.getIpAddr(request));
			 msg=userManager.saveOrUpdateData(userbean);
			}catch (Exception e) {
				msg=e.getMessage();
			}
			userbean.setPasswd(password);
		    request.getSession().setAttribute(CommonConstant.SESSION_SYS_KEY_USER, userbean);
		}else{
			request.setAttribute("msg","操作失败!");
			return "useredit";
		}
		request.setAttribute("msg",msg);
		return "useredit";
		
		
	}
	
	
	/**
	 * <p>
	 * 系统后台主页
	 * </p>
	 * <ol>
	 * [功能概要] <div>主页。</div>
	 * </ol>
	 * @return 转发字符串
	 */
	public String init() throws Exception {
		log.info("HomeAction init");
		return "init";
	}
	/**
	 * 用户管理  业务处理取得
	 * @return 用户管理  业务处理
	 */
	public ITcUaUmBaseUserManager getUserManager() {
	    return userManager;
	}
	/**
	 * 用户管理  业务处理设定
	 * @param userManager 用户管理  业务处理
	 */
	public void setUserManager(ITcUaUmBaseUserManager userManager) {
	    this.userManager = userManager;
	}
}
