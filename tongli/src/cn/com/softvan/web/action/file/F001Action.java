/*
 * 附件管理 ActionClass
 *
 * VERSION  DATE        BY              REASON
 * -------- ----------- --------------- ------------------------------------------
 * 1.00     2013.04.24  wuxiaogang           程序・发布
 * -------- ----------- --------------- ------------------------------------------
 * Copyright 2014 车主管家  System. - All Rights Reserved.
 *
 */
package cn.com.softvan.web.action.file;

import org.apache.log4j.Logger;

import cn.com.softvan.bean.BaseFileBean;
import cn.com.softvan.bean.BaseUserBean;
import cn.com.softvan.common.CommonConstant;
import cn.com.softvan.common.Validator;
import cn.com.softvan.service.IBaseFileManager;
import cn.com.softvan.web.action.BaseAction;
import cn.com.softvan.web.tag.PageInfo;

/**
 * 附件管理 ActionClass
 * @author wuxiaogang
 *
 */
public class F001Action extends BaseAction{

	/**
	 * 序列号
	 */
	private static final long serialVersionUID = -3061791975484213551L;
	private static final transient Logger log = Logger
			.getLogger(F001Action.class);
	/**附件管理 service 业务处理类*/
	private IBaseFileManager baseFileManager;
	public F001Action(){
		log.info("默认构造器......F001Action");
	}
	/**
	 * <p>附件列表 单条信息。</p>
	 * <ol>[功能概要] 
	 * <div>附件列表 单条信息。</div>
	 * </ol>
	 * @return 转发字符串
	 */
	public String list2(){
//		log.info("F001Action list2.........");
//		BaseFileBean bean=new BaseFileBean();
//		
//		bean.setInfo_id(request.getParameter("id"));
//		
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
//		page.setPageRowCount(5);
//		//
//		bean.setPageInfo(page);
//		//
//		request.setAttribute("beans", baseFileManager.findBaseFileBeanIsPage(bean));
//		request.setAttribute(CommonConstant.PAGEROW_OBJECT_KEY,page);
//		request.setAttribute("event_file", "list2");
//		request.setAttribute("id", request.getParameter("id"));
		return "list2";
	}
	/**
	 * <p>附件列表 单个站点。</p>
	 * <ol>[功能概要] 
	 * <div>附件列表 单个站点。</div>
	 * </ol>
	 * @return 转发字符串
	 */
	public String list3(){
//		log.info("F001Action list3.........");
//		BaseFileBean bean=new BaseFileBean();
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
//		page.setPageRowCount(5);
//		//
//		bean.setPageInfo(page);
//		//
//		request.setAttribute("beans", baseFileManager.findBaseFileBeanIsPage(bean));
//		request.setAttribute(CommonConstant.PAGEROW_OBJECT_KEY,page);
//		request.setAttribute("event_file", "list3");
//		request.setAttribute("id", request.getParameter("id"));
		return "list2";
	}
	/**
	 * 附件管理 service 业务处理类取得
	 * @return 附件管理 service 业务处理类
	 */
	public IBaseFileManager getBaseFileManager() {
	    return baseFileManager;
	}
	/**
	 * 附件管理 service 业务处理类设定
	 * @param baseFileManager 附件管理 service 业务处理类
	 */
	public void setBaseFileManager(IBaseFileManager baseFileManager) {
	    this.baseFileManager = baseFileManager;
	}
}
