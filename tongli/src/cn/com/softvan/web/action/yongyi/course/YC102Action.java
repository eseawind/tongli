/*
 * 课程表管理 ActionClass
 *
 * VERSION  DATE        BY              REASON
 * -------- ----------- --------------- ------------------------------------------
 * 1.00     2014.04.07  wuxiaogang      程序・发布
 * -------- ----------- --------------- ------------------------------------------
 * Copyright 2014 童励  System. - All Rights Reserved.
 *
 */
package cn.com.softvan.web.action.yongyi.course;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.apache.log4j.Logger;

import cn.com.softvan.bean.BaseUserBean;
import cn.com.softvan.bean.sys.TcSysVariableBean;
import cn.com.softvan.bean.yongyi.course.TcYAddresBean;
import cn.com.softvan.bean.yongyi.course.TcYCourseBean;
import cn.com.softvan.bean.yongyi.course.TcYCourseSyllabusBean;
import cn.com.softvan.bean.yongyi.course.TcYCourseVsAddresBean;
import cn.com.softvan.common.CommonConstant;
import cn.com.softvan.common.DateUtil;
import cn.com.softvan.common.IdUtils;
import cn.com.softvan.common.Validator;
import cn.com.softvan.service.yongyi.course.ITcYAddresManager;
import cn.com.softvan.service.yongyi.course.ITcYCourseManager;
import cn.com.softvan.service.yongyi.course.ITcYCourseSyllabusManager;
import cn.com.softvan.web.action.BaseAction;
import cn.com.softvan.web.tag.PageInfo;

/**
 * 课程表管理 ActionClass
 * 
 * @author wuxiaogang
 * 
 */
public class YC102Action extends BaseAction {

	/**
	 * 序列号
	 */
	private static final long serialVersionUID = -3061791975484213551L;
	private static final transient Logger log = Logger.getLogger(YC102Action.class);
	/** 课程表管理 业务处理*/
	private ITcYCourseSyllabusManager courseSyllabusManager;
	/**课程信息BEAN*/
	private TcYCourseSyllabusBean bean;
	/**课程信息BEAN集合*/
	private List<TcYCourseSyllabusBean> beans;
	/**课程信息管理 业务处理*/
	private ITcYCourseManager courseManager;
	/**课程地址信息表 业务处理接口类。 */
	private ITcYAddresManager addresManager;
	public YC102Action() {
		log.info("默认构造器......C102Action");
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
		log.info("C102Action init.........");
		
		return "init";
	}
	/**
	 * <p>
	 * 已完成的课程。
	 * </p>
	 * <ol>
	 * [功能概要] <div>已完成的课程。</div>
	 * </ol>
	 * @return 转发字符串
	 */
	public String list1() {
		log.info("C102Action list1.........");
		String s="list1";//普通课程
		String t=request.getParameter("t");//0 普通课程1夏令营2冬令营
		if(t!=null){
			if("1".equals(t)){
				s="list1_1";//1夏令营
			}
			if("2".equals(t)){
				s="list1_2";//2冬令营
			}
		}
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
		TcYCourseSyllabusBean bean1 = new TcYCourseSyllabusBean();
		bean1.setPageInfo(page);
		bean1.setDel_flag("0");
		//--已完成课程--
		bean1.setCourse_status("1");
		//--
		bean1.setType(t);
		//列表
		List<TcYCourseSyllabusBean> beans=courseSyllabusManager.findDataIsPage(bean1);
		request.setAttribute("beans",beans);
		request.setAttribute(CommonConstant.PAGEROW_OBJECT_KEY,page);
		
		return s;
	}
	/**
	 * <p>
	 * 未完成的课程。
	 * </p>
	 * <ol>
	 * [功能概要] <div>未完成的课程。</div>
	 * </ol>
	 * @return 转发字符串
	 */
	public String list2() {
		log.info("C102Action list2.........");
		String s="list2";//普通课程
		String t=request.getParameter("t");//0 普通课程1夏令营2冬令营
		if(t!=null){
			if("1".equals(t)){
				s="list2_1";//1夏令营
			}
			if("2".equals(t)){
				s="list2_2";//2冬令营
			}
		}
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
		TcYCourseSyllabusBean bean1 = new TcYCourseSyllabusBean();
		bean1.setPageInfo(page);
		bean1.setDel_flag("0");
		//--未完成课程--
		bean1.setCourse_status("0");
		//--
		bean1.setType(t);
		//列表
		List<TcYCourseSyllabusBean> beans=courseSyllabusManager.findDataIsPage(bean1);
		request.setAttribute("beans",beans);
		request.setAttribute(CommonConstant.PAGEROW_OBJECT_KEY,page);
		return s;
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
	public String init2() {
		log.info("C102Action init2.........");
		
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
		TcYCourseSyllabusBean bean1 = new TcYCourseSyllabusBean();
		bean1.setPageInfo(page);
		bean1.setDel_flag("0");
		//列表
		List<TcYCourseSyllabusBean> beans=courseSyllabusManager.findDataIsPage(bean1);
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
		log.info("C102Action recycle.........");
		
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
		TcYCourseSyllabusBean bean1 = new TcYCourseSyllabusBean();
		bean1.setPageInfo(page);
		bean1.setDel_flag("1");
		//列表
		List<TcYCourseSyllabusBean> beans=courseSyllabusManager.findDataIsPage(bean1);
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
		log.info("C102Action edit.........");
		String id=request.getParameter("id");
		if(id!=null){
			TcYCourseSyllabusBean bean1=new TcYCourseSyllabusBean();
			bean1.setId(id);
			bean=courseSyllabusManager.findDataById(bean1);
		}
		String s="edit";//普通课程
		if(bean==null){
			bean=new TcYCourseSyllabusBean();
			bean.setId(IdUtils.createUUID(32));
		}
		if(Validator.isEmpty(bean.getType())){
			String type=request.getParameter("type");
			if(type!=null){
				if("0".equals(type)){
					bean.setType("0");//普通课程
				}else{
					bean.setType(type);
					s="edit2";//冬夏令营
				}
				request.setAttribute("type",type);
			}else{
				bean.setType("0");//普通课程
			}
		}
		//--------------课程--all----------
		//数据字典中获取课程类型
		TcSysVariableBean bean1=new TcSysVariableBean();
		bean1.setVariable_id("course_subject");//课程主题
		List<TcSysVariableBean> course_subjects=variableManager.findDataIsList(bean1);
		//课程列表
		TcYCourseBean course_bean_1=new TcYCourseBean();
		List<TcYCourseBean> course_beans_all=courseManager.findDataIsList(course_bean_1);
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
		
		BaseUserBean user = (BaseUserBean) request.getSession().getAttribute(CommonConstant.SESSION_KEY_USER);
		request.setAttribute("uid", user.getUser_id());//
		
		return s;
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
		log.info("C102Action del.........");
		String id=request.getParameter("id");
		TcYCourseSyllabusBean bean1=new TcYCourseSyllabusBean();
		bean1.setId(id);
		String msg="1";
		try {
			msg=courseSyllabusManager.deleteDataById(bean1);
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
		log.info("C102Action delxx.........");
		String id=request.getParameter("id");
		TcYCourseSyllabusBean bean1=new TcYCourseSyllabusBean();
		bean1.setId(id);
		String msg="1";
		try {
			msg=courseSyllabusManager.deleteData(bean1);
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
		log.info("C102Action save.........");
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
				//-----冬夏令营
				if("2x".equals(request.getParameter("type_flag"))){
					if(Validator.isEmpty(bean.getCourse_id())||Validator.isEmpty(bean.getDate1())||Validator.isEmpty(bean.getDate2())){
						msg="保存失败!信息为空!";
					}else{
						String[] day_week=request.getParameterValues("day_week");
						if(day_week!=null){
							String s=bean.getDate1();
							String e=bean.getDate2();
							Calendar ca=Calendar.getInstance();
							ca.setTime(DateUtil.parseDate(s));
							Calendar ca1=Calendar.getInstance();
							ca1.setTime(DateUtil.parseDate(e));
							
							for(;ca.getTimeInMillis()<=ca1.getTimeInMillis();){
//								System.out.println("星期"+(ca.get(Calendar.DAY_OF_WEEK)-1));
//								System.out.println(DateUtil.getDateStr(ca.getTime()));
								try {
									String DAY_OF_WEEK=""+(ca.get(Calendar.DAY_OF_WEEK)-1);
									for(String week:day_week){
										if(DAY_OF_WEEK.equals(week)){
											bean.setId(null);
											bean.setDay(DateUtil.getDateStr(ca.getTime()));//上课日期
											msg=courseSyllabusManager.saveOrUpdateData(bean);
										}
									}
									ca.set(Calendar.DATE,ca.get(Calendar.DATE)+1);
								} catch (Exception e1) {
									log.error("冬夏令营课程信息保存失败!", e1);
								}
							}
						}
					}
				}else{
					//-----普通课程
					if(Validator.isEmpty(bean.getCourse_id())){
						msg="保存失败!信息为空!";
					}else{
						msg=courseSyllabusManager.saveOrUpdateData(bean);
					}
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
		log.info("C102Action recovery.........");
		String id=request.getParameter("id");
		TcYCourseSyllabusBean bean1=new TcYCourseSyllabusBean();
		bean1.setId(id);
		String msg="1";
		try {
			msg=courseSyllabusManager.recoveryDataById(bean1);
		} catch (Exception e) {
			msg=e.getMessage();
		}
		request.setAttribute("msg",msg);
		
		return SUCCESS;
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
		log.info("C102Action getAddres.........");
		String cid=request.getParameter("cid");
		StringBuffer sb=new StringBuffer("");
		if(Validator.notEmpty(cid)){
			TcYCourseVsAddresBean course_vs_addres_bean=new TcYCourseVsAddresBean();
			course_vs_addres_bean.setCourse_id(cid);
			List<TcYAddresBean> addres_beans=addresManager.findDataIsListAddres(course_vs_addres_bean);
			if(addres_beans!=null){
				for(TcYAddresBean addresBean:addres_beans){
					sb.append("<option value=\""+addresBean.getAddres()+"\">"+addresBean.getAddres()+"</option>");
				}
			}
		}
		getWriter().print(sb.toString());
		return null;
	}

	/**
	 * 课程表管理 业务处理取得
	 * @return 课程表管理 业务处理
	 */
	public ITcYCourseSyllabusManager getCourseSyllabusManager() {
	    return courseSyllabusManager;
	}

	/**
	 * 课程表管理 业务处理设定
	 * @param courseSyllabusManager 课程表管理 业务处理
	 */
	public void setCourseSyllabusManager(ITcYCourseSyllabusManager courseSyllabusManager) {
	    this.courseSyllabusManager = courseSyllabusManager;
	}

	/**
	 * 课程信息BEAN取得
	 * @return 课程信息BEAN
	 */
	public TcYCourseSyllabusBean getBean() {
	    return bean;
	}

	/**
	 * 课程信息BEAN设定
	 * @param bean 课程信息BEAN
	 */
	public void setBean(TcYCourseSyllabusBean bean) {
	    this.bean = bean;
	}

	/**
	 * 课程信息BEAN集合取得
	 * @return 课程信息BEAN集合
	 */
	public List<TcYCourseSyllabusBean> getBeans() {
	    return beans;
	}

	/**
	 * 课程信息BEAN集合设定
	 * @param beans 课程信息BEAN集合
	 */
	public void setBeans(List<TcYCourseSyllabusBean> beans) {
	    this.beans = beans;
	}

	/**
	 * 课程信息管理 业务处理取得
	 * @return 课程信息管理 业务处理
	 */
	public ITcYCourseManager getCourseManager() {
	    return courseManager;
	}

	/**
	 * 课程信息管理 业务处理设定
	 * @param courseManager 课程信息管理 业务处理
	 */
	public void setCourseManager(ITcYCourseManager courseManager) {
	    this.courseManager = courseManager;
	}

	/**
	 * 课程地址信息表 业务处理接口类。取得
	 * @return 课程地址信息表 业务处理接口类。
	 */
	public ITcYAddresManager getAddresManager() {
	    return addresManager;
	}

	/**
	 * 课程地址信息表 业务处理接口类。设定
	 * @param addresManager 课程地址信息表 业务处理接口类。
	 */
	public void setAddresManager(ITcYAddresManager addresManager) {
	    this.addresManager = addresManager;
	}
}
