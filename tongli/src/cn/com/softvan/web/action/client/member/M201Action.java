/*
 * 会员登录-会员首页  ActionClass
 *
 * VERSION  DATE        BY              REASON
 * -------- ----------- --------------- ------------------------------------------
 * 1.00     2014.04.14  wuxiaogang      程序・发布
 * -------- ----------- --------------- ------------------------------------------
 * Copyright 2014 车主管家  System. - All Rights Reserved.
 *
 */
package cn.com.softvan.web.action.client.member;

import java.io.IOException;
import java.util.List;

import org.apache.log4j.Logger;

import cn.com.softvan.bean.course.TcCourseSyllabusBean;
import cn.com.softvan.bean.course.TcCourseSyllabusItemsBean;
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
 * 会员登录-会员首页  ActionClass
 * 
 * @author wuxiaogang
 * 
 */
public class M201Action extends BaseAction {

	/**
	 * 序列号
	 */
	private static final long serialVersionUID = -3061791975484213551L;
	private static final transient Logger log = Logger.getLogger(M201Action.class);
	
	/**BEAN类  会员信息*/
	private TcMemberBean bean;
	/**BEAN类 课程详情*/
	private TcCourseSyllabusItemsBean item_bean;
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
	public M201Action() {
		log.info("默认构造器......M201Action");
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
		log.info("M201Action init.........");
		TcMemberBean member=(TcMemberBean) request.getSession().getAttribute(CommonConstant.SESSION_KEY_USER_MEMBER_INFO);
		//当前会员关联的学员
		request.setAttribute("student_beans", memberManager.findDataIsListStudent(member));
		return "init";
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
	public String list1() {
		log.info("M201Action list1.........");
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
		TcCourseSyllabusItemsBean bean1 = new TcCourseSyllabusItemsBean();
		bean1.setPageInfo(page);
		bean1.setDel_flag("0");
		bean1.setStudent_id(request.getParameter("sid"));//学员id
		//课程列表
		List<TcCourseSyllabusBean> beans=courseSyllabusItemsManager.findDataIsPageCourse(bean1);
		request.setAttribute("beans",beans);
		request.setAttribute(CommonConstant.PAGEROW_OBJECT_KEY,page);
		return "list1";
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
		log.info("M201Action mlogin.........");
		String uid=request.getParameter("uid");
		String pwd=request.getParameter("pwd");
		if(Validator.notEmpty(uid)&&Validator.notEmpty(pwd)){
			TcMemberBean bean1=new TcMemberBean();
			bean1.setUser_id(uid);
			bean1.setPasswd(pwd);
			bean1.setUser_type("1");//
			bean=memberManager.checkMemberPWD(bean1);
			if(bean!=null){
				request.getSession().setAttribute(CommonConstant.SESSION_KEY_USER_MEMBER_INFO, bean);
				return SUCCESS;
			}
		}
		request.setAttribute("msg", "登录失败,用户名或密码错误!");
		return "mlogin";
	}
	/**
	 * <p>
	 * 信息保存
	 * </p>
	 * <ol>
	 * [功能概要] 
	 * <div>学员给教练评分。</div>
	 * </ol>
	 * @return 转发字符串
	 * @throws IOException 
	 */
	public String save() throws IOException {
		log.info("M201Action save.........");
		String msg="1";
		if(item_bean!=null){
			try {
				TcMemberBean user = (TcMemberBean) request.getSession().getAttribute(CommonConstant.SESSION_KEY_USER_MEMBER_INFO);
				item_bean.setUpdate_ip(getIpAddr());
				item_bean.setUpdate_id(user.getUser_id());
				msg=courseSyllabusItemsManager.updateDataByTeacher(item_bean);
			} catch (Exception e) {
				msg=e.getMessage();
			}
		}else{
			msg="信息保存失败!";
		}
		getWriter().print(msg);
		return null;
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
	 * BEAN类 课程详情取得
	 * @return BEAN类 课程详情
	 */
	public TcCourseSyllabusItemsBean getItem_bean() {
	    return item_bean;
	}

	/**
	 * BEAN类 课程详情设定
	 * @param item_bean BEAN类 课程详情
	 */
	public void setItem_bean(TcCourseSyllabusItemsBean item_bean) {
	    this.item_bean = item_bean;
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
	/**
	 * <p>
	 * 用户登出
	 * </p>
	 * <ol>
	 * [功能概要] <div>登出。</div>
	 * </ol>
	 * @return 转发字符串
	 */
	public String logout() throws Exception {
		log.info("M201Action logout");
		//清空用户登录信息
		request.getSession().removeAttribute(CommonConstant.SESSION_KEY_USER_MEMBER_INFO);
		request.getSession().invalidate();
		return "mlogin";
	}
	/**
	 * <p>
	 * 验证是否登录
	 * </p>
	 * <ol>
	 * [功能概要] <div>验证是否登录。</div>
	 * </ol>
	 * @return 转发字符串
	 */
	public String check() throws Exception {
		log.info("M201Action check");
		String msg="1";
		if(request.getSession().getAttribute(CommonConstant.SESSION_KEY_USER_MEMBER_INFO)==null){
			msg="0";
		}
		
		getWriter().print(msg);
		
		return null;
	}
}
