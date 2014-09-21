/*
 * 课程管理 ActionClass
 *
 * VERSION  DATE        BY              REASON
 * -------- ----------- --------------- ------------------------------------------
 * 1.00     2014.04.01  wuxiaogang      程序・发布
 * -------- ----------- --------------- ------------------------------------------
 * Copyright 2014 童励  System. - All Rights Reserved.
 *
 */
package cn.com.softvan.web.action.yongyi.course;

import java.util.List;

import org.apache.log4j.Logger;

import cn.com.softvan.bean.BaseUserBean;
import cn.com.softvan.bean.sys.TcSysVariableBean;
import cn.com.softvan.bean.yongyi.course.TcYAddresBean;
import cn.com.softvan.bean.yongyi.course.TcYCourseBean;
import cn.com.softvan.bean.yongyi.course.TcYCourseVsAddresBean;
import cn.com.softvan.common.CommonConstant;
import cn.com.softvan.common.IdUtils;
import cn.com.softvan.common.Validator;
import cn.com.softvan.service.yongyi.course.ITcYAddresManager;
import cn.com.softvan.service.yongyi.course.ITcYCourseManager;
import cn.com.softvan.web.action.BaseAction;
import cn.com.softvan.web.tag.PageInfo;

/**
 * 课程管理 ActionClass
 * 
 * @author wuxiaogang
 * 
 */
public class YC101Action extends BaseAction {

	/**
	 * 序列号
	 */
	private static final long serialVersionUID = -3061791975484213551L;
	private static final transient Logger log = Logger.getLogger(YC101Action.class);
	/** 课程管理  业务处理*/
	private ITcYCourseManager courseManager;
	/** 地址管理  业务处理*/
	private ITcYAddresManager addresManager;
	/**课程信息BEAN*/
	private TcYCourseBean bean;
	/**课程信息BEAN集合*/
	private List<TcYCourseBean> beans;
	public YC101Action() {
		log.info("默认构造器......C101Action");
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
		log.info("C101Action init.........");
		
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
		TcYCourseBean bean1 = new TcYCourseBean();
		bean1.setPageInfo(page);
		bean1.setDel_flag("0");
		//列表
		List<TcYCourseBean> beans=courseManager.findDataIsPage(bean1);
		request.setAttribute("beans",beans);
		request.setAttribute(CommonConstant.PAGEROW_OBJECT_KEY,page);
		
		return "init";
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
		log.info("C101Action recycle.........");
		
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
		TcYCourseBean bean1 = new TcYCourseBean();
		bean1.setPageInfo(page);
		bean1.setDel_flag("1");
		//列表
		List<TcYCourseBean> beans=courseManager.findDataIsPage(bean1);
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
		log.info("C101Action edit.........");
		String id=request.getParameter("id");
		if(id!=null){
			TcYCourseBean bean1=new TcYCourseBean();
			bean1.setId(id);
			bean=courseManager.findDataById(bean1);
			
			//当前课程关联地址
			TcYCourseVsAddresBean addres_bean=new TcYCourseVsAddresBean();
			addres_bean.setCourse_id(bean1.getId());
			List<TcYAddresBean> course_addres_beans=addresManager.findDataIsListAddres(addres_bean);
			request.setAttribute("course_addres_beans",course_addres_beans);
		}
		if(bean==null){
			bean=new TcYCourseBean();
			bean.setId(IdUtils.createUUID(32));
		}
		//数据字典中获取课程类型
		TcSysVariableBean bean1=new TcSysVariableBean();
		bean1.setVariable_id("course_subject");//课程主题
		request.setAttribute("course_subject",variableManager.findDataIsList(bean1));
		
		//所有地址
		List<TcYAddresBean> addres_beans=addresManager.findDataIsList(null);
		request.setAttribute("addres_beans",addres_beans);
		
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
		log.info("C101Action del.........");
		String id=request.getParameter("id");
		TcYCourseBean bean1=new TcYCourseBean();
		bean1.setId(id);
		String msg="1";
		try {
			msg=courseManager.deleteDataById(bean1);
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
		log.info("C101Action delxx.........");
		String id=request.getParameter("id");
		TcYCourseBean bean1=new TcYCourseBean();
		bean1.setId(id);
		String msg="1";
		try {
			msg=courseManager.deleteData(bean1);
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
		log.info("C101Action edit.........");
		if(bean!=null){
			String msg="1";
			try {
				if(Validator.isEmpty(bean.getCourse_type())){
					msg="保存失败!信息为空!";
				}else{
					BaseUserBean user = (BaseUserBean) request.getSession().getAttribute(CommonConstant.SESSION_KEY_USER);
					if(user!=null){
						bean.setCreate_ip(getIpAddr());
						bean.setCreate_id(user.getUser_id());
						bean.setUpdate_ip(getIpAddr());
						bean.setUpdate_id(user.getUser_id());
					}
					msg=courseManager.saveOrUpdateData(bean);
				}
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
		log.info("C101Action recovery.........");
		String id=request.getParameter("id");
		TcYCourseBean bean1=new TcYCourseBean();
		bean1.setId(id);
		String msg="1";
		try {
			msg=courseManager.recoveryDataById(bean1);
		} catch (Exception e) {
			msg=e.getMessage();
		}
		request.setAttribute("msg",msg);
		
		return SUCCESS;
	}

	/**
	 * 课程管理  业务处理取得
	 * @return 课程管理  业务处理
	 */
	public ITcYCourseManager getCourseManager() {
	    return courseManager;
	}

	/**
	 * 课程管理  业务处理设定
	 * @param courseManager 课程管理  业务处理
	 */
	public void setCourseManager(ITcYCourseManager courseManager) {
	    this.courseManager = courseManager;
	}

	/**
	 * 地址管理  业务处理取得
	 * @return 地址管理  业务处理
	 */
	public ITcYAddresManager getAddresManager() {
	    return addresManager;
	}

	/**
	 * 地址管理  业务处理设定
	 * @param addresManager 地址管理  业务处理
	 */
	public void setAddresManager(ITcYAddresManager addresManager) {
	    this.addresManager = addresManager;
	}

	/**
	 * 课程信息BEAN取得
	 * @return 课程信息BEAN
	 */
	public TcYCourseBean getBean() {
	    return bean;
	}

	/**
	 * 课程信息BEAN设定
	 * @param bean 课程信息BEAN
	 */
	public void setBean(TcYCourseBean bean) {
	    this.bean = bean;
	}

	/**
	 * 课程信息BEAN集合取得
	 * @return 课程信息BEAN集合
	 */
	public List<TcYCourseBean> getBeans() {
	    return beans;
	}

	/**
	 * 课程信息BEAN集合设定
	 * @param beans 课程信息BEAN集合
	 */
	public void setBeans(List<TcYCourseBean> beans) {
	    this.beans = beans;
	}


}
