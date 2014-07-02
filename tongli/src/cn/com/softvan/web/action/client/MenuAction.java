/*
 * 前端菜单 ActionClass
 *
 * VERSION  DATE        BY              REASON
 * -------- ----------- --------------- ------------------------------------------
 * 1.00     2014.06.28  wuxiaogang      程序・发布
 * -------- ----------- --------------- ------------------------------------------
 * Copyright 2014 童励  System. - All Rights Reserved.
 *
 */
package cn.com.softvan.web.action.client;

import java.util.List;

import org.apache.log4j.Logger;

import cn.com.softvan.bean.sys.TcSysNewsTypeBean;
import cn.com.softvan.service.sys.INewsTypeManager;
import cn.com.softvan.web.action.BaseAction;

/**
 * 前端菜单 ActionClass
 * 
 * @author wuxiaogang
 * 
 */
public class MenuAction extends BaseAction {

	/**
	 * 序列号
	 */
	private static final long serialVersionUID = -3061791975484213551L;
	private static final transient Logger log = Logger.getLogger(MenuAction.class);
	/**资讯栏目信息管理 业务处理*/
	private INewsTypeManager newsTypeManager;
	public MenuAction() {
		log.info("默认构造器......MenuAction");
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
		log.info("MenuAction init.........");
		String pid=request.getParameter("pid");//栏目父级id
		//栏目树
		TcSysNewsTypeBean bean2=new TcSysNewsTypeBean();
		bean2.setParent_id(pid);
		
		List<TcSysNewsTypeBean> tree_array=(List<TcSysNewsTypeBean>) request.getSession().getAttribute("tree_array"+pid);
		if(tree_array==null){
			tree_array=newsTypeManager.findDataIsTree(bean2);
			request.getSession().setAttribute("tree_array"+pid,tree_array);
		}
		request.setAttribute("tree_array",tree_array);
		return "init";
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
