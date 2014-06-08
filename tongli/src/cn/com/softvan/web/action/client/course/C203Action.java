/*
 * 课程-在线报名 Action Class
 *
 * VERSION  DATE        BY              REASON
 * -------- ----------- --------------- ------------------------------------------
 * 1.00     2014.06.07  wuxiaogang      程序・发布
 * -------- ----------- --------------- ------------------------------------------
 * Copyright 2014 童励  System. - All Rights Reserved.
 *
 */
package cn.com.softvan.web.action.client.course;

import java.util.List;

import org.apache.log4j.Logger;

import cn.com.softvan.bean.BaseUserBean;
import cn.com.softvan.bean.course.TcCourseBean;
import cn.com.softvan.bean.course.TcCourseWebEnrollBean;
import cn.com.softvan.common.CommonConstant;
import cn.com.softvan.common.IdUtils;
import cn.com.softvan.service.course.ICourseManager;
import cn.com.softvan.service.course.ICourseWebEnrollManager;
import cn.com.softvan.web.action.BaseAction;

/**
 * 课程-在线报名  ActionClass
 * 
 * @author wuxiaogang
 * 
 */
public class C203Action extends BaseAction {

	/**
	 * 序列号
	 */
	private static final long serialVersionUID = -3061791975484213551L;
	private static final transient Logger log = Logger.getLogger(C203Action.class);
	
	/** 课程-在线报名 管理 业务处理*/
	private ICourseWebEnrollManager courseWebEnrollManager;
	/**课程-在线报名 信息BEAN*/
	private TcCourseWebEnrollBean bean;
	/**课程-在线报名 信息BEAN集合*/
	private List<TcCourseWebEnrollBean> beans;
	/** 课程管理  业务处理*/
	private ICourseManager courseManager;
	//
	public C203Action() {
		log.info("默认构造器......C203Action");
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
		log.info("C203Action init.........");
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
		
		//课程列表
		TcCourseBean course_bean=new TcCourseBean();
		List<TcCourseBean> course_beans=courseManager.findDataIsList(course_bean);
		request.setAttribute("course_beans", course_beans);
		
		
		return "init";
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
		log.info("C102Action save.........");
		if(bean!=null){
			String msg="1";
			try {
				BaseUserBean user = (BaseUserBean) request.getSession().getAttribute(CommonConstant.SESSION_KEY_USER_MEMBER_INFO);
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
		return "view";
	}
	/**
	 * <p>
	 * 课程详情。
	 * </p>
	 * <ol>
	 * [功能概要] <div>课程详情。</div>
	 * </ol>
	 * @return 转发字符串
	 */
	public String view() {
		log.info("C203Action view.........");
		TcCourseWebEnrollBean bean1 = new TcCourseWebEnrollBean();
		bean1.setId(request.getParameter("cid"));
		bean=courseWebEnrollManager.findDataById(bean1);
		return "init";
	}

	/**
	 * 课程-在线报名 管理 业务处理取得
	 * @return 课程-在线报名 管理 业务处理
	 */
	public ICourseWebEnrollManager getCourseWebEnrollManager() {
	    return courseWebEnrollManager;
	}

	/**
	 * 课程-在线报名 管理 业务处理设定
	 * @param courseWebEnrollManager 课程-在线报名 管理 业务处理
	 */
	public void setCourseWebEnrollManager(ICourseWebEnrollManager courseWebEnrollManager) {
	    this.courseWebEnrollManager = courseWebEnrollManager;
	}

	/**
	 * 课程-在线报名 信息BEAN取得
	 * @return 课程-在线报名 信息BEAN
	 */
	public TcCourseWebEnrollBean getBean() {
	    return bean;
	}

	/**
	 * 课程-在线报名 信息BEAN设定
	 * @param bean 课程-在线报名 信息BEAN
	 */
	public void setBean(TcCourseWebEnrollBean bean) {
	    this.bean = bean;
	}

	/**
	 * 课程-在线报名 信息BEAN集合取得
	 * @return 课程-在线报名 信息BEAN集合
	 */
	public List<TcCourseWebEnrollBean> getBeans() {
	    return beans;
	}

	/**
	 * 课程-在线报名 信息BEAN集合设定
	 * @param beans 课程-在线报名 信息BEAN集合
	 */
	public void setBeans(List<TcCourseWebEnrollBean> beans) {
	    this.beans = beans;
	}

	/**
	 * 课程管理  业务处理取得
	 * @return 课程管理  业务处理
	 */
	public ICourseManager getCourseManager() {
	    return courseManager;
	}

	/**
	 * 课程管理  业务处理设定
	 * @param courseManager 课程管理  业务处理
	 */
	public void setCourseManager(ICourseManager courseManager) {
	    this.courseManager = courseManager;
	}
}
