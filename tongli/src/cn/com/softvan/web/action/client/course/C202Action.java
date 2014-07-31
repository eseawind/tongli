/*
 * 课程-预约参观 Action Class
 *
 * VERSION  DATE        BY              REASON
 * -------- ----------- --------------- ------------------------------------------
 * 1.00     2014.05.17  wuxiaogang      程序・发布
 * -------- ----------- --------------- ------------------------------------------
 * Copyright 2014 童励  System. - All Rights Reserved.
 *
 */
package cn.com.softvan.web.action.client.course;

import java.util.Calendar;
import java.util.List;

import org.apache.log4j.Logger;

import cn.com.softvan.bean.BaseUserBean;
import cn.com.softvan.bean.addres.TcAddresBean;
import cn.com.softvan.bean.addres.TcCourseVsAddresBean;
import cn.com.softvan.bean.course.TcCourseBean;
import cn.com.softvan.bean.course.TcCourseBespeakBean;
import cn.com.softvan.bean.course.TcCourseSyllabusBean;
import cn.com.softvan.common.CommonConstant;
import cn.com.softvan.common.DateUtil;
import cn.com.softvan.common.IdUtils;
import cn.com.softvan.common.Validator;
import cn.com.softvan.service.addres.IAddresManager;
import cn.com.softvan.service.course.ICourseBespeakManager;
import cn.com.softvan.service.course.ICourseManager;
import cn.com.softvan.service.course.ICourseSyllabusManager;
import cn.com.softvan.web.action.BaseAction;

/**
 * 课程-预约参观  ActionClass
 * 
 * @author wuxiaogang
 * 
 */
public class C202Action extends BaseAction {

	/**
	 * 序列号
	 */
	private static final long serialVersionUID = -3061791975484213551L;
	private static final transient Logger log = Logger.getLogger(C202Action.class);
	
	/** 课程-预约参观 管理 业务处理*/
	private ICourseBespeakManager courseBespeakManager;
	/**课程-预约参观 信息BEAN*/
	private TcCourseBespeakBean bean;
	/**课程-预约参观 信息BEAN集合*/
	private List<TcCourseBespeakBean> beans;
	/** 课程管理  业务处理*/
	private ICourseManager courseManager;
	/**课程表管理 业务处理 */
	private ICourseSyllabusManager courseSyllabusManager;
	/**课程地址信息表 业务处理接口类。 */
	private IAddresManager addresManager;
	//
	public C202Action() {
		log.info("默认构造器......C202Action");
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
		log.info("C202Action init.........");
		String id=request.getParameter("id");
		if(id!=null){
			TcCourseBespeakBean bean1=new TcCourseBespeakBean();
			bean1.setId(id);
			bean=courseBespeakManager.findDataById(bean1);
		}
		if(bean==null){
			bean=new TcCourseBespeakBean();
			bean.setId(IdUtils.createUUID(32));
		}
		
		//课程列表
		TcCourseBean course_bean=new TcCourseBean();
		List<TcCourseBean> course_beans=courseManager.findDataIsList(course_bean);
		request.setAttribute("course_beans", course_beans);
		
		//日期提前一周--
	    Calendar cal = Calendar.getInstance();
	    cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DATE)+7);
		request.setAttribute("startDate",DateUtil.getDateStr(cal.getTime()));
		
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
				msg=courseBespeakManager.saveOrUpdateData(bean);
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
		log.info("C202Action view.........");
		TcCourseBespeakBean bean1 = new TcCourseBespeakBean();
		bean1.setId(request.getParameter("cid"));
		bean=courseBespeakManager.findDataById(bean1);
		return "init";
	}
	/**
	 * <p>
	 * 根据课程id获取参观时间
	 * </p>
	 * <ol>
	 * [功能概要] <div>课程时间检索。</div>
	 * </ol>
	 * @return 转发字符串
	 * @throws Exception 
	 */
	public String getDate() throws Exception {
		log.info("C202Action getDate.........");
		String cid=request.getParameter("cid");
		StringBuffer sb=new StringBuffer("");
		if(Validator.notEmpty(cid)){
			TcCourseSyllabusBean course_bean=new TcCourseSyllabusBean();
			course_bean.setCourse_id(cid);
			course_bean.setType("0");
			List<TcCourseSyllabusBean> course_beans=courseSyllabusManager.findDataIsListDate(course_bean);
			if(course_beans!=null){
				for(TcCourseSyllabusBean courseSyllabusBean:course_beans){
					sb.append("<option value=\""+courseSyllabusBean.getDay()+" "+courseSyllabusBean.getBegin_time()+"\">"+courseSyllabusBean.getDay()+" "+courseSyllabusBean.getBegin_time()+"</option>");
				}
			}
		}
		getWriter().print(sb.toString());
		return null;
	}
	/**
	 * <p>
	 * 根据课程id获取课程地址
	 * </p>
	 * <ol>
	 * [功能概要] <div>课程地址检索。</div>
	 * </ol>
	 * @return 转发字符串
	 * @throws Exception 
	 */
	public String getAddres() throws Exception {
		log.info("C202Action getAddres.........");
		String cid=request.getParameter("cid");
		StringBuffer sb=new StringBuffer("");
		if(Validator.notEmpty(cid)){
			TcCourseVsAddresBean course_vs_addres_bean=new TcCourseVsAddresBean();
			course_vs_addres_bean.setCourse_id(cid);
			List<TcAddresBean> addres_beans=addresManager.findDataIsListAddres(course_vs_addres_bean);
			if(addres_beans!=null){
				for(TcAddresBean addresBean:addres_beans){
					sb.append("<option value=\""+addresBean.getAddres()+"\">"+addresBean.getAddres()+"</option>");
				}
			}
		}
		getWriter().print(sb.toString());
		return null;
	}
	/**
	 * 课程-预约参观 管理 业务处理取得
	 * @return 课程-预约参观 管理 业务处理
	 */
	public ICourseBespeakManager getCourseBespeakManager() {
	    return courseBespeakManager;
	}

	/**
	 * 课程-预约参观 管理 业务处理设定
	 * @param courseBespeakManager 课程-预约参观 管理 业务处理
	 */
	public void setCourseBespeakManager(ICourseBespeakManager courseBespeakManager) {
	    this.courseBespeakManager = courseBespeakManager;
	}

	/**
	 * 课程-预约参观 信息BEAN取得
	 * @return 课程-预约参观 信息BEAN
	 */
	public TcCourseBespeakBean getBean() {
	    return bean;
	}

	/**
	 * 课程-预约参观 信息BEAN设定
	 * @param bean 课程-预约参观 信息BEAN
	 */
	public void setBean(TcCourseBespeakBean bean) {
	    this.bean = bean;
	}

	/**
	 * 课程-预约参观 信息BEAN集合取得
	 * @return 课程-预约参观 信息BEAN集合
	 */
	public List<TcCourseBespeakBean> getBeans() {
	    return beans;
	}

	/**
	 * 课程-预约参观 信息BEAN集合设定
	 * @param beans 课程-预约参观 信息BEAN集合
	 */
	public void setBeans(List<TcCourseBespeakBean> beans) {
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
	public static void main(String[] args) {
		//日期提前一周--
		  Calendar cal = Calendar.getInstance();
		  cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DATE)+7);
		System.out.println(DateUtil.getDateStr(cal.getTime()));
	}

	/**
	 * 课程表管理 业务处理取得
	 * @return 课程表管理 业务处理
	 */
	public ICourseSyllabusManager getCourseSyllabusManager() {
	    return courseSyllabusManager;
	}

	/**
	 * 课程表管理 业务处理设定
	 * @param courseSyllabusManager 课程表管理 业务处理
	 */
	public void setCourseSyllabusManager(ICourseSyllabusManager courseSyllabusManager) {
	    this.courseSyllabusManager = courseSyllabusManager;
	}

	/**
	 * 课程地址信息表 业务处理接口类。取得
	 * @return 课程地址信息表 业务处理接口类。
	 */
	public IAddresManager getAddresManager() {
	    return addresManager;
	}

	/**
	 * 课程地址信息表 业务处理接口类。设定
	 * @param addresManager 课程地址信息表 业务处理接口类。
	 */
	public void setAddresManager(IAddresManager addresManager) {
	    this.addresManager = addresManager;
	}
}
