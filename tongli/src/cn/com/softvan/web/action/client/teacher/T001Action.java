/*
 * 会员登录-教练首页  ActionClass
 *
 * VERSION  DATE        BY              REASON
 * -------- ----------- --------------- ------------------------------------------
 * 1.00     2014.04.14  wuxiaogang      程序・发布
 * -------- ----------- --------------- ------------------------------------------
 * Copyright 2014 车主管家  System. - All Rights Reserved.
 *
 */
package cn.com.softvan.web.action.client.teacher;

import java.util.List;

import org.apache.log4j.Logger;

import cn.com.softvan.bean.course.TcCourseSyllabusBean;
import cn.com.softvan.bean.member.TcMemberBean;
import cn.com.softvan.common.CommonConstant;
import cn.com.softvan.common.Validator;
import cn.com.softvan.service.course.ICourseManager;
import cn.com.softvan.service.course.ICourseSyllabusItemsManager;
import cn.com.softvan.service.course.ICourseSyllabusManager;
import cn.com.softvan.service.member.IMemberManager;
import cn.com.softvan.service.student.IStudentManager;
import cn.com.softvan.web.action.BaseAction;
import cn.com.softvan.web.tag.PageInfo;

/**
 * 会员登录-教练首页  ActionClass
 * 
 * @author wuxiaogang
 * 
 */
public class T001Action extends BaseAction {

	/**
	 * 序列号
	 */
	private static final long serialVersionUID = -3061791975484213551L;
	private static final transient Logger log = Logger.getLogger(T001Action.class);
	
	/**BEAN类  会员信息*/
	private TcMemberBean bean;
	/**会员信息管理 业务处理*/
	private IMemberManager memberManager;
	/**学员信息管理 业务处理*/
	private IStudentManager studentManager;
	/** 课程管理 业务处理*/
	private ICourseManager courseManager;
	/** 课程表管理 业务处理*/
	private ICourseSyllabusManager courseSyllabusManager;
	/** 课程表-详情管理 业务处理*/
	private ICourseSyllabusItemsManager courseSyllabusItemsManager;
	public T001Action() {
		log.info("默认构造器......T001Action");
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
		log.info("T001Action init.........");
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
		TcCourseSyllabusBean bean1 = new TcCourseSyllabusBean();
		bean1.setPageInfo(page);
		bean1.setDel_flag("0");
		//列表
		List<TcCourseSyllabusBean> beans=courseSyllabusManager.findDataIsPage(bean1);
		request.setAttribute("beans",beans);
		request.setAttribute(CommonConstant.PAGEROW_OBJECT_KEY,page);
		
		return "init";
	}
	/**
	 * <p>
	 * 登录验证。
	 * </p>
	 * <ol>
	 * [功能概要] <div>登录。</div>
	 * </ol>
	 * @return 转发字符串
	 */
	public String login() {
		log.info("T001Action mlogin.........");
		String uid=request.getParameter("uid");
		String pwd=request.getParameter("pwd");
		if(Validator.notEmpty(uid)&&Validator.notEmpty(pwd)){
			TcMemberBean bean1=new TcMemberBean();
			bean1.setUser_name(uid);
			bean1.setPasswd(pwd);
			bean1.setUser_type("0");//
			bean=memberManager.checkMemberPWD(bean1);
			if(bean!=null){
				request.getSession().setAttribute(CommonConstant.SESSION_KEY_USER_MEMBER_INFO, bean);
				return SUCCESS;
			}
		}
		return "tlogin";
	}

	/**
	 * BEAN类  会员信息取得
	 * @return BEAN类  会员信息
	 */
	public TcMemberBean getBean() {
	    return bean;
	}

	/**
	 * BEAN类  会员信息设定
	 * @param bean BEAN类  会员信息
	 */
	public void setBean(TcMemberBean bean) {
	    this.bean = bean;
	}

	/**
	 * 会员信息管理 业务处理取得
	 * @return 会员信息管理 业务处理
	 */
	public IMemberManager getMemberManager() {
	    return memberManager;
	}

	/**
	 * 会员信息管理 业务处理设定
	 * @param memberManager 会员信息管理 业务处理
	 */
	public void setMemberManager(IMemberManager memberManager) {
	    this.memberManager = memberManager;
	}

	/**
	 * 学员信息管理 业务处理取得
	 * @return 学员信息管理 业务处理
	 */
	public IStudentManager getStudentManager() {
	    return studentManager;
	}

	/**
	 * 学员信息管理 业务处理设定
	 * @param studentManager 学员信息管理 业务处理
	 */
	public void setStudentManager(IStudentManager studentManager) {
	    this.studentManager = studentManager;
	}

	/**
	 * 课程管理 业务处理取得
	 * @return 课程管理 业务处理
	 */
	public ICourseManager getCourseManager() {
	    return courseManager;
	}

	/**
	 * 课程管理 业务处理设定
	 * @param courseManager 课程管理 业务处理
	 */
	public void setCourseManager(ICourseManager courseManager) {
	    this.courseManager = courseManager;
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
	 * 课程表-详情管理 业务处理取得
	 * @return 课程表-详情管理 业务处理
	 */
	public ICourseSyllabusItemsManager getCourseSyllabusItemsManager() {
	    return courseSyllabusItemsManager;
	}

	/**
	 * 课程表-详情管理 业务处理设定
	 * @param courseSyllabusItemsManager 课程表-详情管理 业务处理
	 */
	public void setCourseSyllabusItemsManager(ICourseSyllabusItemsManager courseSyllabusItemsManager) {
	    this.courseSyllabusItemsManager = courseSyllabusItemsManager;
	}

}
