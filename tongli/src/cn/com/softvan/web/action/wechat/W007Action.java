/*
 * 微信服务_自定义菜单 ActionClass
 *
 * VERSION  DATE        BY              REASON
 * -------- ----------- --------------- ------------------------------------------
 * 1.00     2014.03.04  wuxiaogang      程序・发布
 * -------- ----------- --------------- ------------------------------------------
 * Copyright 2014 车主管家  System. - All Rights Reserved.
 *
 */
package cn.com.softvan.web.action.wechat;

import java.io.IOException;
import java.util.List;

import org.apache.log4j.Logger;

import cn.com.softvan.bean.BaseUserBean;
import cn.com.softvan.bean.wechat.TcWxMenuBean;
import cn.com.softvan.common.CommonConstant;
import cn.com.softvan.common.wechat.WxApiUtil;
import cn.com.softvan.service.wechat.ITcWxMenuManager;
import cn.com.softvan.web.action.BaseAction;

/**
 * 微信服务_自动回复(关键字)_文章消息 ActionClass
 * 
 * @author wuxiaogang
 * 
 */
public class W007Action extends BaseAction {

	/**
	 * 序列号
	 */
	private static final long serialVersionUID = -3061791975484213551L;
	private static final transient Logger log = Logger.getLogger(W007Action.class);
	/**微信自定义菜单bean*/
	private TcWxMenuBean bean;
	/**微信自定义菜单列表beans*/
	private List<TcWxMenuBean> beans;
	/**微信服务_自定义菜单  service*/
	private ITcWxMenuManager tcWxMenuManager;
	//
	public W007Action() {
		log.info("默认构造器......W007Action");
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
		log.info("W007Action init.........");
		TcWxMenuBean bean=new TcWxMenuBean();
		bean.setInfo_source("0");
		beans=tcWxMenuManager.findDataIsTree(bean);
		return "init";
	}
	/**
	 * <p>
	 * 编辑。
	 * </p>
	 * <ol>
	 * [功能概要] <div>编辑页面初始化。</div>
	 * </ol>
	 * @return 转发字符串
	 */
	public String edit() {
		log.info("W007Action edit.........");
		bean=new TcWxMenuBean();
		bean.setId(request.getParameter("id"));
		bean.setInfo_source("0");
		//详情
		bean=tcWxMenuManager.findDataById(this.bean);
		//顶级菜单列表
		beans=tcWxMenuManager.findDataIsList(null);
		return "edit";
	}
	/**
	 * <p>
	 * 编辑。
	 * </p>
	 * <ol>
	 * [功能概要] <div>保存入库。</div>
	 * </ol>
	 * @return 转发字符串
	 * @throws Exception 
	 */
	public String save() throws Exception {
		log.info("W007Action save.........");
		BaseUserBean user = (BaseUserBean) request.getSession().getAttribute(CommonConstant.SESSION_KEY_USER);
		if(user!=null){
			bean.setCreate_id(user.getUser_id());
			bean.setCreate_ip(getIpAddr());
			bean.setUpdate_id(user.getUser_id());
			bean.setUpdate_ip(getIpAddr());
		}
		bean.setInfo_source("0");
		String msg=tcWxMenuManager.saveOrUpdateData(this.bean);
		request.setAttribute("msg",msg);
		
		return SUCCESS;
	}
	/**
	 * <p>
	 * 编辑。
	 * </p>
	 * <ol>
	 * [功能概要] <div>编辑页面初始化。</div>
	 * </ol>
	 * @return 转发字符串
	 */
	public String del() {
		log.info("W007Action edit.........");
		bean=new TcWxMenuBean();
		bean.setId(request.getParameter("id"));
		bean.setInfo_source("0");
		String msg=tcWxMenuManager.deleteData(bean);
		request.setAttribute("msg",msg);
		
		return SUCCESS;
	}
	/**
	 * <p>
	 * 菜单下载。
	 * </p>
	 * <ol>
	 * [功能概要] <div>菜单下载。</div>
	 * </ol>
	 * @return 转发字符串
	 */
	public String downMenu() {
		log.info("W007Action downMenu.........");
		
		
//		List<TcWxMenuBean> beans=new WxApiUtil().getMenu(access_token);
		
		
		
		
		
		return null;
	}
	/**
	 * <p>
	 * 菜单发布。
	 * </p>
	 * <ol>
	 * [功能概要] <div>菜单发布。</div>
	 * </ol>
	 * @return 转发字符串
	 * @throws Exception 
	 */
	public String uploadMenu() {
		log.info("W007Action uploadMenu.........");
		try {
			//0 成功 1失败
			getWriter().print(tcWxMenuManager.uploadMenu());
		} catch (IOException e) {
			log.error("菜单生成失败!",e);
		}
		return null;
	}
	/**
	 * 微信自定义菜单bean取得
	 * @return 微信自定义菜单bean
	 */
	public TcWxMenuBean getBean() {
	    return bean;
	}

	/**
	 * 微信自定义菜单bean设定
	 * @param bean 微信自定义菜单bean
	 */
	public void setBean(TcWxMenuBean bean) {
	    this.bean = bean;
	}

	/**
	 * 微信自定义菜单列表beans取得
	 * @return 微信自定义菜单列表beans
	 */
	public List<TcWxMenuBean> getBeans() {
	    return beans;
	}

	/**
	 * 微信自定义菜单列表beans设定
	 * @param beans 微信自定义菜单列表beans
	 */
	public void setBeans(List<TcWxMenuBean> beans) {
	    this.beans = beans;
	}

	/**
	 * 微信服务_自定义菜单  service取得
	 * @return 微信服务_自定义菜单  service
	 */
	public ITcWxMenuManager getTcWxMenuManager() {
	    return tcWxMenuManager;
	}

	/**
	 * 微信服务_自定义菜单  service设定
	 * @param tcWxMenuManager 微信服务_自定义菜单  service
	 */
	public void setTcWxMenuManager(ITcWxMenuManager tcWxMenuManager) {
	    this.tcWxMenuManager = tcWxMenuManager;
	}
}
