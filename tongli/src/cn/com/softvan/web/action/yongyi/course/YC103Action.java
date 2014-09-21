/*
 * 预约参观管理 ActionClass
 *
 * VERSION  DATE        BY              REASON
 * -------- ----------- --------------- ------------------------------------------
 * 1.00     2014.05.20  wuxiaogang      程序・发布
 * -------- ----------- --------------- ------------------------------------------
 * Copyright 2014 童励  System. - All Rights Reserved.
 *
 */
package cn.com.softvan.web.action.yongyi.course;

import java.util.List;

import org.apache.log4j.Logger;

import cn.com.softvan.bean.BaseUserBean;
import cn.com.softvan.bean.yongyi.course.TcYCourseBespeakBean;
import cn.com.softvan.common.CommonConstant;
import cn.com.softvan.common.IdUtils;
import cn.com.softvan.common.Validator;
import cn.com.softvan.service.yongyi.course.ITcYCourseBespeakManager;
import cn.com.softvan.web.action.BaseAction;
import cn.com.softvan.web.tag.PageInfo;

/**
 * 预约参观管理 ActionClass
 * 
 * @author wuxiaogang
 * 
 */
public class YC103Action extends BaseAction {

	/**
	 * 序列号
	 */
	private static final long serialVersionUID = -3061791975484213551L;
	private static final transient Logger log = Logger.getLogger(YC103Action.class);
	/** 预约参观管理 业务处理*/
	private ITcYCourseBespeakManager courseBespeakManager;
	/**课程信息BEAN*/
	private TcYCourseBespeakBean bean;
	/**课程信息BEAN集合*/
	private List<TcYCourseBespeakBean> beans;
	
	public YC103Action() {
		log.info("默认构造器......C103Action");
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
		log.info("C103Action init.........");
		return "init";
	}
	/**
	 * <p>
	 * 信息列表。
	 * </p>
	 * <ol>
	 * [功能概要] <div>列表。</div>
	 * </ol>
	 * @return 转发字符串
	 */
	public String list1() {
		log.info("C103Action list1.........");
		
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
		if(bean==null){
			bean = new TcYCourseBespeakBean();
		}
		bean.setPageInfo(page);
		bean.setDel_flag("0");
		//列表
		List<TcYCourseBespeakBean> beans=courseBespeakManager.findDataIsPage(bean);
		request.setAttribute("beans",beans);
		request.setAttribute(CommonConstant.PAGEROW_OBJECT_KEY,page);
		return "list1";
	}
	/**
	 * <p>
	 * 信息列表。
	 * </p>
	 * <ol>
	 * [功能概要] <div>回收站。</div>
	 * </ol>
	 * @return 转发字符串
	 */
	public String recycle() {
		log.info("C103Action recycle.........");
		
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
		TcYCourseBespeakBean bean1 = new TcYCourseBespeakBean();
		bean1.setPageInfo(page);
		bean1.setDel_flag("1");
		//列表
		List<TcYCourseBespeakBean> beans=courseBespeakManager.findDataIsPage(bean1);
		request.setAttribute("beans",beans);
		request.setAttribute(CommonConstant.PAGEROW_OBJECT_KEY,page);
		
		return "recycle";
	}
	
	/**
	 * <p>
	 * 信息编辑。
	 * </p>
	 * <ol>
	 * [功能概要] <div>编辑。</div>
	 * </ol>
	 * @return 转发字符串
	 */
	public String edit() {
		log.info("C103Action edit.........");
		String id=request.getParameter("id");
		if(id!=null){
			TcYCourseBespeakBean bean1=new TcYCourseBespeakBean();
			bean1.setId(id);
			bean=courseBespeakManager.findDataById(bean1);
		}
		if(bean==null){
			bean=new TcYCourseBespeakBean();
			bean.setId(IdUtils.createUUID(32));
		}
		return "edit";
	}
	/**
	 * <p>
	 * 删除。
	 * </p>
	 * <ol>
	 * [功能概要] <div>逻辑删除。</div>
	 * </ol>
	 * @return 转发字符串
	 */
	public String del() {
		log.info("C103Action del.........");
		String id=request.getParameter("id");
		TcYCourseBespeakBean bean1=new TcYCourseBespeakBean();
		bean1.setId(id);
		String msg="1";
		try {
			msg=courseBespeakManager.deleteDataById(bean1);
		} catch (Exception e) {
			msg=e.getMessage();
		}
		request.setAttribute("msg",msg);
		
		return SUCCESS;
	}
	/**
	 * <p>
	 * 删除。
	 * </p>
	 * <ol>
	 * [功能概要] <div>物理删除。</div>
	 * </ol>
	 * @return 转发字符串
	 */
	public String delxx() {
		log.info("C103Action delxx.........");
		String id=request.getParameter("id");
		TcYCourseBespeakBean bean1=new TcYCourseBespeakBean();
		bean1.setId(id);
		String msg="1";
		try {
			msg=courseBespeakManager.deleteData(bean1);
		} catch (Exception e) {
			msg=e.getMessage();
		}
		request.setAttribute("msg",msg);
		
		return SUCCESS;
	}
	/**
	 * <p>
	 * 信息保存
	 * </p>
	 * <ol>
	 * [功能概要] <div>新增。</div>
	 * <div>修改。</div>
	 * </ol>
	 * @return 转发字符串
	 */
	public String save() {
		log.info("C103Action save.........");
		if(bean!=null){
			String msg="1";
			try {
				BaseUserBean user = (BaseUserBean) request.getSession().getAttribute(CommonConstant.SESSION_KEY_USER);
				if(user!=null){
					bean.setCreate_ip(getIpAddr());
					bean.setCreate_id(user.getUser_id());
					bean.setUpdate_ip(getIpAddr());
					bean.setUpdate_id(user.getUser_id());
				}
				msg=courseBespeakManager.saveOrUpdateData(bean);
			} catch (Exception e) {
				msg=e.getMessage();
			}
			request.setAttribute("msg",msg);
		}else{
			request.setAttribute("msg", "信息保存失败!");
		}
		return SUCCESS;
	}
	/**
	 * <p>
	 * 恢复。
	 * </p>
	 * <ol>[功能概要] 
	 * <div>恢复逻辑删除的数据。</div>
	 * </ol>
	 * @return 转发字符串
	 */
	public String recovery() {
		log.info("C103Action recovery.........");
		String id=request.getParameter("id");
		TcYCourseBespeakBean bean1=new TcYCourseBespeakBean();
		bean1.setId(id);
		String msg="1";
		try {
			msg=courseBespeakManager.recoveryDataById(bean1);
		} catch (Exception e) {
			msg=e.getMessage();
		}
		request.setAttribute("msg",msg);
		
		return SUCCESS;
	}

	/**
	 * 预约参观管理 业务处理取得
	 * @return 预约参观管理 业务处理
	 */
	public ITcYCourseBespeakManager getCourseBespeakManager() {
	    return courseBespeakManager;
	}

	/**
	 * 预约参观管理 业务处理设定
	 * @param courseBespeakManager 预约参观管理 业务处理
	 */
	public void setCourseBespeakManager(ITcYCourseBespeakManager courseBespeakManager) {
	    this.courseBespeakManager = courseBespeakManager;
	}

	/**
	 * 课程信息BEAN取得
	 * @return 课程信息BEAN
	 */
	public TcYCourseBespeakBean getBean() {
	    return bean;
	}

	/**
	 * 课程信息BEAN设定
	 * @param bean 课程信息BEAN
	 */
	public void setBean(TcYCourseBespeakBean bean) {
	    this.bean = bean;
	}

	/**
	 * 课程信息BEAN集合取得
	 * @return 课程信息BEAN集合
	 */
	public List<TcYCourseBespeakBean> getBeans() {
	    return beans;
	}

	/**
	 * 课程信息BEAN集合设定
	 * @param beans 课程信息BEAN集合
	 */
	public void setBeans(List<TcYCourseBespeakBean> beans) {
	    this.beans = beans;
	}


}
