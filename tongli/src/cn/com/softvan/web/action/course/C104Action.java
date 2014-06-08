/*
 * 在线报名管理 ActionClass
 *
 * VERSION  DATE        BY              REASON
 * -------- ----------- --------------- ------------------------------------------
 * 1.00     2014.06.08  wuxiaogang      程序・发布
 * -------- ----------- --------------- ------------------------------------------
 * Copyright 2014 童励  System. - All Rights Reserved.
 *
 */
package cn.com.softvan.web.action.course;

import java.util.List;

import org.apache.log4j.Logger;

import cn.com.softvan.bean.BaseUserBean;
import cn.com.softvan.bean.course.TcCourseWebEnrollBean;
import cn.com.softvan.common.CommonConstant;
import cn.com.softvan.common.IdUtils;
import cn.com.softvan.common.Validator;
import cn.com.softvan.service.course.ICourseWebEnrollManager;
import cn.com.softvan.web.action.BaseAction;
import cn.com.softvan.web.tag.PageInfo;

/**
 * 在线报名管理 ActionClass
 * 
 * @author wuxiaogang
 * 
 */
public class C104Action extends BaseAction {

	/**
	 * 序列号
	 */
	private static final long serialVersionUID = -3061791975484213551L;
	private static final transient Logger log = Logger.getLogger(C104Action.class);
	/** 在线报名管理 业务处理*/
	private ICourseWebEnrollManager courseWebEnrollManager;
	/**课程信息BEAN*/
	private TcCourseWebEnrollBean bean;
	/**课程信息BEAN集合*/
	private List<TcCourseWebEnrollBean> beans;
	
	public C104Action() {
		log.info("默认构造器......C104Action");
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
		log.info("C104Action init.........");
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
		log.info("C104Action list1.........");
		
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
			bean = new TcCourseWebEnrollBean();
		}
		bean.setPageInfo(page);
		bean.setDel_flag("0");
		//列表
		List<TcCourseWebEnrollBean> beans=courseWebEnrollManager.findDataIsPage(bean);
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
		log.info("C104Action recycle.........");
		
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
		TcCourseWebEnrollBean bean1 = new TcCourseWebEnrollBean();
		bean1.setPageInfo(page);
		bean1.setDel_flag("1");
		//列表
		List<TcCourseWebEnrollBean> beans=courseWebEnrollManager.findDataIsPage(bean1);
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
		log.info("C104Action edit.........");
		String id=request.getParameter("id");
		if(id!=null){
			TcCourseWebEnrollBean bean1=new TcCourseWebEnrollBean();
			bean1.setId(id);
			bean=courseWebEnrollManager.findDataById(bean1);
		}
		if(bean==null){
			bean=new TcCourseWebEnrollBean();
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
		log.info("C104Action del.........");
		String id=request.getParameter("id");
		TcCourseWebEnrollBean bean1=new TcCourseWebEnrollBean();
		bean1.setId(id);
		String msg="1";
		try {
			msg=courseWebEnrollManager.deleteDataById(bean1);
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
		log.info("C104Action delxx.........");
		String id=request.getParameter("id");
		TcCourseWebEnrollBean bean1=new TcCourseWebEnrollBean();
		bean1.setId(id);
		String msg="1";
		try {
			msg=courseWebEnrollManager.deleteData(bean1);
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
		log.info("C104Action save.........");
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
				msg=courseWebEnrollManager.saveOrUpdateData(bean);
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
		log.info("C104Action recovery.........");
		String id=request.getParameter("id");
		TcCourseWebEnrollBean bean1=new TcCourseWebEnrollBean();
		bean1.setId(id);
		String msg="1";
		try {
			msg=courseWebEnrollManager.recoveryDataById(bean1);
		} catch (Exception e) {
			msg=e.getMessage();
		}
		request.setAttribute("msg",msg);
		
		return SUCCESS;
	}

	/**
	 * 在线报名管理 业务处理取得
	 * @return 在线报名管理 业务处理
	 */
	public ICourseWebEnrollManager getCourseWebEnrollManager() {
	    return courseWebEnrollManager;
	}

	/**
	 * 在线报名管理 业务处理设定
	 * @param courseWebEnrollManager 在线报名管理 业务处理
	 */
	public void setCourseWebEnrollManager(ICourseWebEnrollManager courseWebEnrollManager) {
	    this.courseWebEnrollManager = courseWebEnrollManager;
	}

	/**
	 * 课程信息BEAN取得
	 * @return 课程信息BEAN
	 */
	public TcCourseWebEnrollBean getBean() {
	    return bean;
	}

	/**
	 * 课程信息BEAN设定
	 * @param bean 课程信息BEAN
	 */
	public void setBean(TcCourseWebEnrollBean bean) {
	    this.bean = bean;
	}

	/**
	 * 课程信息BEAN集合取得
	 * @return 课程信息BEAN集合
	 */
	public List<TcCourseWebEnrollBean> getBeans() {
	    return beans;
	}

	/**
	 * 课程信息BEAN集合设定
	 * @param beans 课程信息BEAN集合
	 */
	public void setBeans(List<TcCourseWebEnrollBean> beans) {
	    this.beans = beans;
	}

}
