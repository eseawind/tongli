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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import cn.com.softvan.bean.BaseUserBean;
import cn.com.softvan.bean.course.TcCourseBean;
import cn.com.softvan.bean.course.TcCourseWebEnrollBean;
import cn.com.softvan.bean.sys.TcSysVariableBean;
import cn.com.softvan.common.CommonConstant;
import cn.com.softvan.common.IdUtils;
import cn.com.softvan.common.StrUtil;
import cn.com.softvan.common.Validator;
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
		
		//数据字典中获取课程类型
		TcSysVariableBean bean1=new TcSysVariableBean();
		bean1.setVariable_id("course_subject");//课程主题
		List<TcSysVariableBean> course_subjects=variableManager.findDataIsList(bean1);
		//课程列表
		TcCourseBean course_bean_1=new TcCourseBean();
		List<TcCourseBean> course_beans_all=courseManager.findDataIsList(course_bean_1);
		//--
		List<TcCourseBean> course_beans=new ArrayList<TcCourseBean>();
		if(course_subjects!=null){
			for(TcSysVariableBean variablebean:course_subjects){
				TcCourseBean course_bean=new TcCourseBean();
				course_bean.setSubject_id(variablebean.getVariable_sub_id());
				course_bean.setSubject_name(variablebean.getVariable_sub_name());
				List<TcCourseBean> course_beans_temp=new ArrayList<TcCourseBean>();
				if(course_beans_all!=null){
					for(TcCourseBean courseBean:course_beans_all){
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
		//数据字典  网上报名 价格
		TcSysVariableBean bean2=new TcSysVariableBean();
		bean2.setVariable_id("course_web_enroll");//课程主题
		List<TcSysVariableBean> course_web_enroll_list=variableManager.findDataIsList(bean2);
		if(course_web_enroll_list!=null){
			for(TcSysVariableBean course_web_enroll:course_web_enroll_list){
				if(Validator.isNumber1(course_web_enroll.getVariable_sub_name())){
					request.setAttribute(course_web_enroll.getVariable_sub_id(),course_web_enroll.getVariable_sub_name());
				}else{
					request.setAttribute(course_web_enroll.getVariable_sub_id(),0);
				}
			}
		}
		
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
				
				if("0".equals(bean.getType())){
					//培训班
					if(Validator.notEmpty(bean.getCourse())){
						//存储课程map
						Map<String,TcCourseBean> course_beans_all_map=new HashMap<String,TcCourseBean>();
						//课程列表
						TcCourseBean course_bean_1=new TcCourseBean();
						List<TcCourseBean> course_beans_all=courseManager.findDataIsList(course_bean_1);
						if(course_beans_all!=null){
							for(TcCourseBean courseBean:course_beans_all){
								course_beans_all_map.put(courseBean.getId().trim(), courseBean);
							}
						}
						//获取所有选择的所有课程 且 计算课程总价
						String[] courses=bean.getCourse().split(",");
						if(courses!=null){
							Double total_price=0d;
							for(int i=0;i<courses.length;i++){
								if(courses[i]!=null){
									TcCourseBean courseBean=course_beans_all_map.get(courses[i].trim());
									 String member_price=courseBean.getMarket_price();
									if(Validator.isNumber1(member_price)){
										total_price+=Double.parseDouble(member_price);
										bean.setCourse(StrUtil.replaceAll(bean.getCourse(),courseBean.getId(), courseBean.getTitle()));
									}
								}
							}
							bean.setPrice(""+total_price);
						}
					}
				}else if("1".equals(bean.getType())){
					//夏令营
					if(Validator.notEmpty(bean.getCode())){
						//数据字典  网上报名 价格
						Map<String,String> course_web_enroll_map=new HashMap<String,String>();
						TcSysVariableBean bean2=new TcSysVariableBean();
						bean2.setVariable_id("course_web_enroll");//课程主题
						List<TcSysVariableBean> course_web_enroll_list=variableManager.findDataIsList(bean2);
						if(course_web_enroll_list!=null){
							for(TcSysVariableBean course_web_enroll:course_web_enroll_list){
								if(Validator.isNumber1(course_web_enroll.getVariable_sub_name())){
									course_web_enroll_map.put(course_web_enroll.getVariable_sub_id(),course_web_enroll.getVariable_sub_name());
								}else{
									course_web_enroll_map.put(course_web_enroll.getVariable_sub_id(),""+0);
								}
							}
						}
						String[] codes=bean.getCode().split(",");
						if(codes!=null){
							for(int i=0;i<codes.length;i++){
								bean.setPrice(course_web_enroll_map.get("course_web_enroll_"+codes[i]));
							}
							if(codes.length==2){
								bean.setPrice(course_web_enroll_map.get("course_web_enroll_total"));
							}
						}
					}
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
	public static void main(String[] args) {
		System.out.println(Validator.isNumber1("2.0"));
		System.out.println(Validator.isNumber1("2"));
		System.out.println(Validator.isNumber("2.0"));
		System.out.println(Validator.isNumber("2"));
		System.out.println(StrUtil.replaceAll("675ebeaaefc144588c73b3799b41dbbd, 8cef612813894fd1bb3ba29971017f75", "8cef612813894fd1bb3ba29971017f75", "哈哈哈"));
	}
}
