/*
 * 资讯内容 ActionClass
 *
 * VERSION  DATE        BY              REASON
 * -------- ----------- --------------- ------------------------------------------
 * 1.00     2014.03.30  wuxiaogang      程序・发布
 * -------- ----------- --------------- ------------------------------------------
 * Copyright 2014 jfq  System. - All Rights Reserved.
 *
 */
package cn.com.softvan.web.action.client;

import java.util.List;

import org.apache.log4j.Logger;

import cn.com.softvan.bean.sys.TcSysNewsBean;
import cn.com.softvan.bean.sys.TcSysNewsTypeBean;
import cn.com.softvan.service.sys.INewsManager;
import cn.com.softvan.service.sys.INewsTypeManager;
import cn.com.softvan.web.action.BaseAction;

/**
 * 资讯内容 ActionClass
 * 
 * @author wuxiaogang
 * 
 */
public class C003Action extends BaseAction {

	/**
	 * 序列号
	 */
	private static final long serialVersionUID = -3061791975484213551L;
	private static final transient Logger log = Logger.getLogger(C003Action.class);
	
	/**BEAN类  资讯信息*/
	private TcSysNewsBean bean;
	/**BEAN类  资讯信息 集合*/
	private List<TcSysNewsBean> beans;
	/**资讯信息管理 业务处理*/
	private INewsManager newsManager;
	/**资讯栏目信息管理 业务处理*/
	private INewsTypeManager newsTypeManager;
	private String info_source="1";
	//
	public C003Action() {
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
		String id=request.getParameter("id");
		TcSysNewsBean bean1=new TcSysNewsBean();
		bean1.setId(id);
		bean=newsManager.findDataById(bean1);
		String tid=request.getParameter("tid");//栏目id
		String pid=request.getParameter("pid");//栏目父级id
		//栏目树
		TcSysNewsTypeBean bean2=new TcSysNewsTypeBean();
		bean2.setParent_id(pid);
		request.setAttribute("tree_array",newsTypeManager.findDataIsTree(bean2));
		//当前栏目id
		request.setAttribute("tid",tid);
		//当前栏目详情
		TcSysNewsTypeBean typeBean=new TcSysNewsTypeBean();
		typeBean.setId(tid);
		request.setAttribute("typeBean", newsTypeManager.findDataById(typeBean));
		//当前父栏目id
		request.setAttribute("pid",pid);
		//当前 父级 栏目详情
		TcSysNewsTypeBean typeBeanP=new TcSysNewsTypeBean();
		typeBeanP.setId(pid);
		request.setAttribute("typeBeanP", newsTypeManager.findDataById(typeBeanP));
		
		
		return "init";
	}
	/**
	 * <p>
	 * 焦点图信息。
	 * </p>
	 * <ol>
	 * [功能概要] <div>信息列表。</div>
	 * </ol>
	 * @return 转发字符串
	 */
	public String list1() {
		log.info("C001Action list1.........");
		
//		6fba86e8436049e5b30123c538b7fc83	焦点图资讯 ==

		TcSysNewsBean bean1=new TcSysNewsBean();
		bean1.setInfo_source(info_source);
		bean1.setLimit_s(0);
		bean1.setLimit_e(5);
		bean1.setType_id("6fba86e8436049e5b30123c538b7fc83");
		beans=newsManager.findDataIsList(bean1);
		
		return "list1";
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
