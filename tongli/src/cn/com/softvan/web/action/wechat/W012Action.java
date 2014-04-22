/*
 * 微信服务_关注者(粉丝)消息 ActionClass
 *
 * VERSION  DATE        BY              REASON
 * -------- ----------- --------------- ------------------------------------------
 * 1.00     2014.03.20  wuxiaogang      程序・发布
 * -------- ----------- --------------- ------------------------------------------
 * Copyright 2014 童励  System. - All Rights Reserved.
 *
 */
package cn.com.softvan.web.action.wechat;

import java.util.List;

import org.apache.log4j.Logger;

import cn.com.softvan.bean.wechat.TcWxUserBean;
import cn.com.softvan.common.CommonConstant;
import cn.com.softvan.common.Validator;
import cn.com.softvan.service.wechat.ITcWxUserManager;
import cn.com.softvan.web.action.BaseAction;
import cn.com.softvan.web.tag.PageInfo;

/**
 * 微信服务_关注者(粉丝)消息 ActionClass
 * 
 * @author wuxiaogang
 * 
 */
public class W012Action extends BaseAction {

	/**
	 * 序列号
	 */
	private static final long serialVersionUID = -3061791975484213551L;
	private static final transient Logger log = Logger.getLogger(W012Action.class);
	/**BEAN类  微信关注者(粉丝)信息*/
	private TcWxUserBean bean;
	/**BEAN类  微信关注者(粉丝)信息 集合*/
	private List<TcWxUserBean> beans;
	/**微信服务_关注者(粉丝)信息管理 业务处理*/
	private ITcWxUserManager tcWxUserManager;
	//
	public W012Action() {
		log.info("默认构造器......W012Action");
	}
	/**
	 * <p>
	 * 初始化。
	 * </p>
	 * <ol>
	 * [功能概要] <div>初始化。</div>
	 * </ol>
	 * @return 转发字符串
	 */
	public String init() {
		log.info("W012Action init.........");
		return "init";
	}
	/**
	 * <p>
	 * 分页查询。
	 * </p>
	 * <ol>
	 * [功能概要] <div>分页查询。</div>
	 * </ol>
	 * @return 转发字符串
	 */
	public String list() {
		log.info("W012Action list.........");
		int offset = 0;
		// 分页偏移量
		if (!Validator.isNullEmpty(request.getParameter("offset"))
				&& Validator.isNum(request.getParameter("offset"))) {
			offset = Integer.parseInt(request.getParameter("offset"));
		}
		TcWxUserBean bean1=new TcWxUserBean();
		PageInfo page = new PageInfo(); 
		//当前页
		page.setCurrOffset(offset);
		//每页显示条数
		page.setPageRowCount(15);
		bean1.setPageInfo(page);
		beans=tcWxUserManager.findDataIsPage(bean1);
		request.setAttribute("beans",beans);
		request.setAttribute(CommonConstant.PAGEROW_OBJECT_KEY,page);
		return "list";
	}
	/**
	 * <p>
	 * 拉取所有粉丝。
	 * </p>
	 * <ol>
	 * [功能概要] <div>拉取所有粉丝。</div>
	 * </ol>
	 * @return 转发字符串
	 * @throws Exception 
	 */
	public String down() throws Exception {
		log.info("W012Action down.........");
		String msg=tcWxUserManager.downUser();
		getWriter().print(msg);
		return null;
	}
	/**
	 * BEAN类  微信关注者(粉丝)信息取得
	 * @return BEAN类  微信关注者(粉丝)信息
	 */
	public TcWxUserBean getBean() {
	    return bean;
	}

	/**
	 * BEAN类  微信关注者(粉丝)信息设定
	 * @param bean BEAN类  微信关注者(粉丝)信息
	 */
	public void setBean(TcWxUserBean bean) {
	    this.bean = bean;
	}

	/**
	 * BEAN类  微信关注者(粉丝)信息 集合取得
	 * @return BEAN类  微信关注者(粉丝)信息 集合
	 */
	public List<TcWxUserBean> getBeans() {
	    return beans;
	}

	/**
	 * BEAN类  微信关注者(粉丝)信息 集合设定
	 * @param beans BEAN类  微信关注者(粉丝)信息 集合
	 */
	public void setBeans(List<TcWxUserBean> beans) {
	    this.beans = beans;
	}

	/**
	 * 微信服务_关注者(粉丝)信息管理 业务处理取得
	 * @return 微信服务_关注者(粉丝)信息管理 业务处理
	 */
	public ITcWxUserManager getTcWxUserManager() {
	    return tcWxUserManager;
	}

	/**
	 * 微信服务_关注者(粉丝)信息管理 业务处理设定
	 * @param tcWxUserManager 微信服务_关注者(粉丝)信息管理 业务处理
	 */
	public void setTcWxUserManager(ITcWxUserManager tcWxUserManager) {
	    this.tcWxUserManager = tcWxUserManager;
	}
}
