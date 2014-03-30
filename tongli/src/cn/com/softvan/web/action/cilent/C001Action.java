/*
 * 前台首页 ActionClass
 *
 * VERSION  DATE        BY              REASON
 * -------- ----------- --------------- ------------------------------------------
 * 1.00     2014.03.30  wuxiaogang      程序・发布
 * -------- ----------- --------------- ------------------------------------------
 * Copyright 2014 jfq  System. - All Rights Reserved.
 *
 */
package cn.com.softvan.web.action.cilent;

import java.util.List;

import org.apache.log4j.Logger;

import cn.com.softvan.bean.sys.TcSysNewsBean;
import cn.com.softvan.common.CommonConstant;
import cn.com.softvan.common.Validator;
import cn.com.softvan.service.sys.INewsManager;
import cn.com.softvan.service.sys.INewsTypeManager;
import cn.com.softvan.web.action.BaseAction;
import cn.com.softvan.web.tag.PageInfo;

/**
 * 前台首页 ActionClass
 * 
 * @author wuxiaogang
 * 
 */
public class C001Action extends BaseAction {

	/**
	 * 序列号
	 */
	private static final long serialVersionUID = -3061791975484213551L;
	private static final transient Logger log = Logger.getLogger(C001Action.class);
	
	/**BEAN类  资讯信息*/
	private TcSysNewsBean bean;
	/**BEAN类  资讯信息 集合*/
	private List<TcSysNewsBean> beans;
	/**资讯信息管理 业务处理*/
	private INewsManager newsManager;
	/**资讯栏目信息管理 业务处理*/
	private INewsTypeManager newsTypeManager;
	//
	public C001Action() {
		log.info("默认构造器......S001Action");
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
		log.info("S001Action init.........");
//		int offset = 0;
//		// 分页偏移量
//		if (!Validator.isNullEmpty(request.getParameter("offset"))
//				&& Validator.isNum(request.getParameter("offset"))) {
//			offset = Integer.parseInt(request.getParameter("offset"));
//		}
//		PageInfo page = new PageInfo(); 
//		//当前页
//		page.setCurrOffset(offset);
//		//每页显示条数
//		page.setPageRowCount(15);
//		TcSysNewsBean bean1 = new TcSysNewsBean();
//		bean1.setPageInfo(page);
//		//栏目资讯列表
//		List<TcSysNewsBean> beans=newsManager.findDataIsPage(bean1);
//		request.setAttribute("beans",beans);
//		request.setAttribute(CommonConstant.PAGEROW_OBJECT_KEY,page);
		return "init";
	}

	/**
	 * BEAN类  资讯信息取得
	 * @return BEAN类  资讯信息
	 */
	public TcSysNewsBean getBean() {
	    return bean;
	}

	/**
	 * BEAN类  资讯信息设定
	 * @param bean BEAN类  资讯信息
	 */
	public void setBean(TcSysNewsBean bean) {
	    this.bean = bean;
	}

	/**
	 * BEAN类  资讯信息 集合取得
	 * @return BEAN类  资讯信息 集合
	 */
	public List<TcSysNewsBean> getBeans() {
	    return beans;
	}

	/**
	 * BEAN类  资讯信息 集合设定
	 * @param beans BEAN类  资讯信息 集合
	 */
	public void setBeans(List<TcSysNewsBean> beans) {
	    this.beans = beans;
	}

	/**
	 * 资讯信息管理 业务处理取得
	 * @return 资讯信息管理 业务处理
	 */
	public INewsManager getNewsManager() {
	    return newsManager;
	}

	/**
	 * 资讯信息管理 业务处理设定
	 * @param newsManager 资讯信息管理 业务处理
	 */
	public void setNewsManager(INewsManager newsManager) {
	    this.newsManager = newsManager;
	}

	/**
	 * 资讯栏目信息管理 业务处理取得
	 * @return 资讯栏目信息管理 业务处理
	 */
	public INewsTypeManager getNewsTypeManager() {
	    return newsTypeManager;
	}

	/**
	 * 资讯栏目信息管理 业务处理设定
	 * @param newsTypeManager 资讯栏目信息管理 业务处理
	 */
	public void setNewsTypeManager(INewsTypeManager newsTypeManager) {
	    this.newsTypeManager = newsTypeManager;
	}
}
