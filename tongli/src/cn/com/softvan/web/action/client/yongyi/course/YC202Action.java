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
package cn.com.softvan.web.action.client.yongyi.course;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.apache.log4j.Logger;

import cn.com.softvan.bean.BaseUserBean;
import cn.com.softvan.bean.addres.TcAddresBean;
import cn.com.softvan.bean.sys.TcSysVariableBean;
import cn.com.softvan.bean.yongyi.course.TcYAddresBean;
import cn.com.softvan.bean.yongyi.course.TcYCourseBean;
import cn.com.softvan.bean.yongyi.course.TcYCourseBespeakBean;
import cn.com.softvan.bean.yongyi.course.TcYCourseSyllabusBean;
import cn.com.softvan.bean.yongyi.course.TcYCourseVsAddresBean;
import cn.com.softvan.common.CommonConstant;
import cn.com.softvan.common.DateUtil;
import cn.com.softvan.common.IdUtils;
import cn.com.softvan.common.Validator;
import cn.com.softvan.service.sys.IVariableManager;
import cn.com.softvan.service.yongyi.course.ITcYAddresManager;
import cn.com.softvan.service.yongyi.course.ITcYCourseBespeakManager;
import cn.com.softvan.service.yongyi.course.ITcYCourseManager;
import cn.com.softvan.service.yongyi.course.ITcYCourseSyllabusManager;
import cn.com.softvan.web.action.BaseAction;

/**
 * 课程-预约参观  ActionClass
 * 
 * @author wuxiaogang
 * 
 */
public class YC202Action extends BaseAction {

	/**
	 * 序列号
	 */
	private static final long serialVersionUID = -3061791975484213551L;
	private static final transient Logger log = Logger.getLogger(YC202Action.class);
	
	/** 课程-预约参观 管理 业务处理*/
	private ITcYCourseBespeakManager tcYCourseBespeakManager;
	/**课程-预约参观 信息BEAN*/
	private TcYCourseBespeakBean bean;
	/**课程-预约参观 信息BEAN集合*/
	private List<TcYCourseBespeakBean> beans;
	/** 课程管理  业务处理*/
	private ITcYCourseManager tcYCourseManager;
	/**课程表管理 业务处理 */
	private ITcYCourseSyllabusManager tcYCourseSyllabusManager;
	/**课程地址信息表 业务处理接口类。 */
	private ITcYAddresManager tcYAddresManager;
	/** 数据字典管理 service  业务处理 */
	protected IVariableManager variableManager;
	//
	public YC202Action() {
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
			TcYCourseBespeakBean bean1=new TcYCourseBespeakBean();
			bean1.setId(id);
			bean=tcYCourseBespeakManager.findDataById(bean1);
		}
		if(bean==null){
			bean=new TcYCourseBespeakBean();
			bean.setId(IdUtils.createUUID(32));
		}
		//数据字典中获取课程类型
		TcSysVariableBean bean1=new TcSysVariableBean();
		bean1.setVariable_id("course_subject");//课程主题
		List<TcSysVariableBean> course_subjects=variableManager.findDataIsList(bean1);
		//课程列表
		TcYCourseBean course_bean_1=new TcYCourseBean();
		List<TcYCourseBean> course_beans_all=tcYCourseManager.findDataIsList(course_bean_1);
		//--
		List<TcYCourseBean> course_beans=new ArrayList<TcYCourseBean>();
		if(course_subjects!=null){
			for(TcSysVariableBean variablebean:course_subjects){
				TcYCourseBean course_bean=new TcYCourseBean();
				course_bean.setSubject_id(variablebean.getVariable_sub_id());
				course_bean.setSubject_name(variablebean.getVariable_sub_name());
				List<TcYCourseBean> course_beans_temp=new ArrayList<TcYCourseBean>();
				if(course_beans_all!=null){
					for(TcYCourseBean courseBean:course_beans_all){
						if(course_bean.getSubject_id().equals(courseBean.getSubject_id())){
							course_beans_temp.add(courseBean);
						}
					}
				}
				if(course_beans_temp.size()>0){
					course_bean.setBeans(course_beans_temp);
					course_beans.add(course_bean);
				}
			}
		}
		request.setAttribute("course_beans", course_beans);
		
//		//日期提前一周--
//	    Calendar cal = Calendar.getInstance();
//	    cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DATE)+7);
//		request.setAttribute("startDate",DateUtil.getDateStr(cal.getTime()));
		
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
				msg=tcYCourseBespeakManager.saveOrUpdateData(bean);
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
		TcYCourseBespeakBean bean1 = new TcYCourseBespeakBean();
		bean1.setId(request.getParameter("cid"));
		bean=tcYCourseBespeakManager.findDataById(bean1);
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
			TcYCourseSyllabusBean course_bean=new TcYCourseSyllabusBean();
			course_bean.setCourse_id(cid);
			course_bean.setType("0");
			List<TcYCourseSyllabusBean> course_beans=tcYCourseSyllabusManager.findDataIsListDate(course_bean);
			if(course_beans!=null){
				for(TcYCourseSyllabusBean courseSyllabusBean:course_beans){
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
			TcYCourseVsAddresBean course_vs_addres_bean=new TcYCourseVsAddresBean();
			course_vs_addres_bean.setCourse_id(cid);
			List<TcYAddresBean> addres_beans=tcYAddresManager.findDataIsListAddres(course_vs_addres_bean);
			if(addres_beans!=null){
				for(TcYAddresBean addresBean:addres_beans){
					sb.append("<option value=\""+addresBean.getAddres()+"\">"+addresBean.getAddres()+"</option>");
				}
			}
		}
		getWriter().print(sb.toString());
		return null;
	}
	public static void main(String[] args) {
		//日期提前一周--
		  Calendar cal = Calendar.getInstance();
		  cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DATE)+7);
		System.out.println(DateUtil.getDateStr(cal.getTime()));
	}

	/**
	 * 课程-预约参观 管理 业务处理取得
	 * @return 课程-预约参观 管理 业务处理
	 */
	public ITcYCourseBespeakManager getTcYCourseBespeakManager() {
	    return tcYCourseBespeakManager;
	}

	/**
	 * 课程-预约参观 管理 业务处理设定
	 * @param tcYCourseBespeakManager 课程-预约参观 管理 业务处理
	 */
	public void setTcYCourseBespeakManager(ITcYCourseBespeakManager tcYCourseBespeakManager) {
	    this.tcYCourseBespeakManager = tcYCourseBespeakManager;
	}

	/**
	 * 课程-预约参观 信息BEAN取得
	 * @return 课程-预约参观 信息BEAN
	 */
	public TcYCourseBespeakBean getBean() {
	    return bean;
	}

	/**
	 * 课程-预约参观 信息BEAN设定
	 * @param bean 课程-预约参观 信息BEAN
	 */
	public void setBean(TcYCourseBespeakBean bean) {
	    this.bean = bean;
	}

	/**
	 * 课程-预约参观 信息BEAN集合取得
	 * @return 课程-预约参观 信息BEAN集合
	 */
	public List<TcYCourseBespeakBean> getBeans() {
	    return beans;
	}

	/**
	 * 课程-预约参观 信息BEAN集合设定
	 * @param beans 课程-预约参观 信息BEAN集合
	 */
	public void setBeans(List<TcYCourseBespeakBean> beans) {
	    this.beans = beans;
	}

	/**
	 * 课程管理  业务处理取得
	 * @return 课程管理  业务处理
	 */
	public ITcYCourseManager getTcYCourseManager() {
	    return tcYCourseManager;
	}

	/**
	 * 课程管理  业务处理设定
	 * @param tcYCourseManager 课程管理  业务处理
	 */
	public void setTcYCourseManager(ITcYCourseManager tcYCourseManager) {
	    this.tcYCourseManager = tcYCourseManager;
	}

	/**
	 * 课程表管理 业务处理取得
	 * @return 课程表管理 业务处理
	 */
	public ITcYCourseSyllabusManager getTcYCourseSyllabusManager() {
	    return tcYCourseSyllabusManager;
	}

	/**
	 * 课程表管理 业务处理设定
	 * @param tcYCourseSyllabusManager 课程表管理 业务处理
	 */
	public void setTcYCourseSyllabusManager(ITcYCourseSyllabusManager tcYCourseSyllabusManager) {
	    this.tcYCourseSyllabusManager = tcYCourseSyllabusManager;
	}

	/**
	 * 课程地址信息表 业务处理接口类。取得
	 * @return 课程地址信息表 业务处理接口类。
	 */
	public ITcYAddresManager getTcYAddresManager() {
	    return tcYAddresManager;
	}

	/**
	 * 课程地址信息表 业务处理接口类。设定
	 * @param tcYAddresManager 课程地址信息表 业务处理接口类。
	 */
	public void setTcYAddresManager(ITcYAddresManager tcYAddresManager) {
	    this.tcYAddresManager = tcYAddresManager;
	}

	/**
	 * 数据字典管理 service  业务处理取得
	 * @return 数据字典管理 service  业务处理
	 */
	public IVariableManager getVariableManager() {
	    return variableManager;
	}

	/**
	 * 数据字典管理 service  业务处理设定
	 * @param variableManager 数据字典管理 service  业务处理
	 */
	public void setVariableManager(IVariableManager variableManager) {
	    this.variableManager = variableManager;
	}

}
