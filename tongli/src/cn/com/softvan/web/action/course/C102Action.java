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
package cn.com.softvan.web.action.course;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.apache.log4j.Logger;

import cn.com.softvan.bean.BaseUserBean;
import cn.com.softvan.bean.comment.TcCommentBean;
import cn.com.softvan.bean.course.TcCourseSyllabusBean;
import cn.com.softvan.bean.course.TcCourseSyllabusItemsBean;
import cn.com.softvan.bean.course.TcCourseSyllabusPhotoBean;
import cn.com.softvan.bean.member.TcMemberBean;
import cn.com.softvan.common.CommonConstant;
import cn.com.softvan.common.DateUtil;
import cn.com.softvan.common.IdUtils;
import cn.com.softvan.common.Validator;
import cn.com.softvan.service.comment.ICommentManager;
import cn.com.softvan.service.course.ICourseManager;
import cn.com.softvan.service.course.ICourseSyllabusItemsManager;
import cn.com.softvan.service.course.ICourseSyllabusManager;
import cn.com.softvan.service.course.ICourseSyllabusPhotoManager;
import cn.com.softvan.service.member.IMemberManager;
import cn.com.softvan.service.student.IStudentManager;
import cn.com.softvan.web.action.BaseAction;
import cn.com.softvan.web.tag.PageInfo;

/**
 * 课程表管理 ActionClass
 * 
 * @author wuxiaogang
 * 
 */
public class C102Action extends BaseAction {

	/**
	 * 序列号
	 */
	private static final long serialVersionUID = -3061791975484213551L;
	private static final transient Logger log = Logger.getLogger(C102Action.class);
	/** 课程表管理 业务处理*/
	private ICourseSyllabusManager courseSyllabusManager;
	/** 课程表-详情管理 业务处理*/
	private ICourseSyllabusItemsManager courseSyllabusItemsManager;
	/**课程信息BEAN*/
	private TcCourseSyllabusBean bean;
	/**BEAN类 评论信息*/
	private TcCommentBean cbean;
	/**课程信息BEAN集合*/
	private List<TcCourseSyllabusBean> beans;
	/**会员信息管理 业务处理*/
	private IMemberManager memberManager;
	/**学员信息管理 业务处理*/
	private IStudentManager studentManager;
	/**课程信息管理 业务处理*/
	private ICourseManager courseManager;
	/** 课程表管理-相册 业务处理*/
	private ICourseSyllabusPhotoManager courseSyllabusPhotoManager;
	/**评论信息 管理业务处理*/
	private ICommentManager commentManager;
	public C102Action() {
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
		TcCourseSyllabusBean bean1 = new TcCourseSyllabusBean();
		bean1.setPageInfo(page);
		bean1.setDel_flag("0");
		//--已完成课程--
		bean1.setCourse_status("1");
		//--
		bean1.setType(t);
		//列表
		List<TcCourseSyllabusBean> beans=courseSyllabusManager.findDataIsPage(bean1);
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
		TcCourseSyllabusBean bean1 = new TcCourseSyllabusBean();
		bean1.setPageInfo(page);
		bean1.setDel_flag("0");
		//--未完成课程--
		bean1.setCourse_status("0");
		//--
		bean1.setType(t);
		//列表
		List<TcCourseSyllabusBean> beans=courseSyllabusManager.findDataIsPage(bean1);
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
		TcCourseSyllabusBean bean1 = new TcCourseSyllabusBean();
		bean1.setPageInfo(page);
		bean1.setDel_flag("1");
		//列表
		List<TcCourseSyllabusBean> beans=courseSyllabusManager.findDataIsPage(bean1);
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
			TcCourseSyllabusBean bean1=new TcCourseSyllabusBean();
			bean1.setId(id);
			bean=courseSyllabusManager.findDataById(bean1);
			//--当前学员集合---
			TcCourseSyllabusItemsBean bean2=new TcCourseSyllabusItemsBean();
			bean2.setCourse_syllabus_id(bean1.getId());
			try {
				request.setAttribute("course_student_beans", courseSyllabusItemsManager.findDataIsListStudent(bean2));
				if(bean!=null){
					TcCourseSyllabusPhotoBean photoBean=new TcCourseSyllabusPhotoBean();
					photoBean.setCourse_syllabus_id(bean1.getId());
					bean.setPicBeans(courseSyllabusPhotoManager.findDataIsList(photoBean));
				}
			} catch (Exception e) {
				log.error("学员集合获取出错!", e);
			}
		}
		String s="edit";//普通课程
		if(bean==null){
			bean=new TcCourseSyllabusBean();
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
			}else{
				bean.setType("0");//普通课程
			}
		}
		//-------------学员集合-all------
		request.setAttribute("student_beans", studentManager.findDataIsList(null));
		//--------------教师--all-----
		TcMemberBean memberBean=new TcMemberBean();
		memberBean.setUser_type("0");//教师
		request.setAttribute("teacher_beans", memberManager.findDataIsList(memberBean));
		//--------------课程--all----------
		request.setAttribute("course_beans", courseManager.findDataIsList(null));
		
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
		TcCourseSyllabusBean bean1=new TcCourseSyllabusBean();
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
		TcCourseSyllabusBean bean1=new TcCourseSyllabusBean();
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
					if(Validator.isEmpty(bean.getTeacher_id())||Validator.isEmpty(bean.getDate1())||Validator.isEmpty(bean.getDate2())){
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
					if(Validator.isEmpty(bean.getTeacher_id())){
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
		TcCourseSyllabusBean bean1=new TcCourseSyllabusBean();
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
	 * 课程信息BEAN取得
	 * @return 课程信息BEAN
	 */
	public TcCourseSyllabusBean getBean() {
	    return bean;
	}

	/**
	 * 课程信息BEAN设定
	 * @param bean 课程信息BEAN
	 */
	public void setBean(TcCourseSyllabusBean bean) {
	    this.bean = bean;
	}

	/**
	 * BEAN类 评论信息取得
	 * @return BEAN类 评论信息
	 */
	public TcCommentBean getCbean() {
	    return cbean;
	}

	/**
	 * BEAN类 评论信息设定
	 * @param cbean BEAN类 评论信息
	 */
	public void setCbean(TcCommentBean cbean) {
	    this.cbean = cbean;
	}

	/**
	 * 课程信息BEAN集合取得
	 * @return 课程信息BEAN集合
	 */
	public List<TcCourseSyllabusBean> getBeans() {
	    return beans;
	}

	/**
	 * 课程信息BEAN集合设定
	 * @param beans 课程信息BEAN集合
	 */
	public void setBeans(List<TcCourseSyllabusBean> beans) {
	    this.beans = beans;
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
	 * 课程信息管理 业务处理取得
	 * @return 课程信息管理 业务处理
	 */
	public ICourseManager getCourseManager() {
	    return courseManager;
	}

	/**
	 * 课程信息管理 业务处理设定
	 * @param courseManager 课程信息管理 业务处理
	 */
	public void setCourseManager(ICourseManager courseManager) {
	    this.courseManager = courseManager;
	}

	/**
	 * 课程表管理-相册 业务处理取得
	 * @return 课程表管理-相册 业务处理
	 */
	public ICourseSyllabusPhotoManager getCourseSyllabusPhotoManager() {
	    return courseSyllabusPhotoManager;
	}

	/**
	 * 课程表管理-相册 业务处理设定
	 * @param courseSyllabusPhotoManager 课程表管理-相册 业务处理
	 */
	public void setCourseSyllabusPhotoManager(ICourseSyllabusPhotoManager courseSyllabusPhotoManager) {
	    this.courseSyllabusPhotoManager = courseSyllabusPhotoManager;
	}
	/**
	 * <p>
	 * 信息保存 相册
	 * </p>
	 * <ol>
	 * [功能概要] 
	 * <div>保存课程相册。</div>
	 * </ol>
	 * @return 转发字符串
	 * @throws IOException 
	 */
	public String savePic() throws IOException {
		log.info("C102Action savePic.........");
//		String token=request.getParameter("token");
//		String token2=(String) request.getSession().getAttribute("token");
//		if(token!=null && token.equals(token2)){
//			getWriter().print("请不要重复提交!");
//			return null;
//		}else{
//			if(token!=null){
//				request.getSession().setAttribute("token",token);
//			}
//		}
		String msg="1";
		String[] picids=request.getParameterValues("picid");
		if(picids!=null){
			try {
				BaseUserBean user = (BaseUserBean) request.getSession().getAttribute(CommonConstant.SESSION_KEY_USER);
				//--相册信息集合--
				List<TcCourseSyllabusPhotoBean> photoBeans=new ArrayList<TcCourseSyllabusPhotoBean>();
				TcCourseSyllabusPhotoBean p_bean=null;
				String course_syllabus_id=request.getParameter("course_syllabus_id");//课程表id
				for(String picid:picids){
					p_bean=new TcCourseSyllabusPhotoBean();
					
					p_bean.setCourse_syllabus_id(course_syllabus_id);//课程表id
					
					p_bean.setId(picid);//照片信息id
					p_bean.setPic_url(request.getParameter("picurl"+picid));//照片链接
					p_bean.setPic_title(request.getParameter("pictit"+picid));//照片链接
					p_bean.setDel_flag(request.getParameter("delflag"+picid));//删除标记
					
					p_bean.setUpdate_ip(getIpAddr());
					p_bean.setUpdate_id(user.getUser_id());
					p_bean.setCreate_ip(getIpAddr());
					p_bean.setCreate_id(user.getUser_id());
					
					if(Validator.notEmpty(p_bean.getSort_num())){
						p_bean.setSort_num("0");//TODO--------
					}else{
						p_bean.setSort_num("0");
					}
					
					photoBeans.add(p_bean);
				}
				msg=courseSyllabusPhotoManager.saveOrUpdateData(photoBeans);
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
	 * <p>
	 * 课程评论信息 。
	 * </p>
	 * <ol>
	 * [功能概要] <div>信息列表。</div>
	 * </ol>
	 * @return 转发字符串
	 */
	public String clist1() {
		log.info("C102Action clist1.........");
		
		String cid=request.getParameter("cid");
		if(cid!=null){
				
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
			page.setPageRowCount(5);
			TcCommentBean bean1 = new TcCommentBean();
			bean1.setPageInfo(page);
			bean1.setDel_flag("0");
//			TcMemberBean user = (TcMemberBean) request.getSession().getAttribute(CommonConstant.SESSION_KEY_USER_MEMBER_INFO);
	//		bean1.setMember_id(user.getId());//会员id
	//		bean1.setMember_type(user.getUser_type());//会员类型
			bean1.setInfo_id(cid);//被评论信息id
			//列表
			List<TcCommentBean> beans=commentManager.findDataIsPage(bean1);
			request.setAttribute("beans",beans);
			request.setAttribute(CommonConstant.PAGEROW_OBJECT_KEY,page);
			//---div id---
			request.setAttribute("did",request.getParameter("did"));
			//---div id---
			request.setAttribute("cid",cid);
		}
		return "clist1";
	}
	/**
	 * <p>
	 * 评论信息保存
	 * </p>
	 * <ol>
	 * [功能概要] 
	 * <div>课程评论。</div>
	 * </ol>
	 * @return 转发字符串
	 * @throws IOException 
	 */
	public String csave() throws IOException {
		log.info("C102Action csave.........");
		String msg="1";
		if(cbean!=null){
			try {
				if(Validator.isEmpty(cbean.getDetail_info())){
					msg="信息保存失败!输入信息为空!";
				}else{
					BaseUserBean user = (BaseUserBean) request.getSession().getAttribute(CommonConstant.SESSION_KEY_USER);
					cbean.setUpdate_ip(getIpAddr());
					cbean.setUpdate_id(user.getUser_id());
					cbean.setCreate_ip(getIpAddr());
					cbean.setCreate_id(user.getUser_id());
					cbean.setMember_id(user.getId());//
					cbean.setMember_type("3");//后台管理员
					msg=commentManager.saveOrUpdateData(cbean);
				}
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
	 * 评论信息 管理业务处理取得
	 * @return 评论信息 管理业务处理
	 */
	public ICommentManager getCommentManager() {
	    return commentManager;
	}

	/**
	 * 评论信息 管理业务处理设定
	 * @param commentManager 评论信息 管理业务处理
	 */
	public void setCommentManager(ICommentManager commentManager) {
	    this.commentManager = commentManager;
	}
}
