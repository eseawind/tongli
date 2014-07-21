/*
 * 会员登录-教练首页  ActionClass
 *
 * VERSION  DATE        BY              REASON
 * -------- ----------- --------------- ------------------------------------------
 * 1.00     2014.04.14  wuxiaogang      程序・发布
 * -------- ----------- --------------- ------------------------------------------
 * Copyright 2014 童励  System. - All Rights Reserved.
 *
 */
package cn.com.softvan.web.action.client.teacher;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import cn.com.softvan.bean.comment.TcCommentBean;
import cn.com.softvan.bean.course.TcCourseSyllabusBean;
import cn.com.softvan.bean.course.TcCourseSyllabusItemsBean;
import cn.com.softvan.bean.course.TcCourseSyllabusPhotoBean;
import cn.com.softvan.bean.member.TcMemberBean;
import cn.com.softvan.bean.student.TcStudentBean;
import cn.com.softvan.bean.sys.TcSysSmsBean;
import cn.com.softvan.common.CommonConstant;
import cn.com.softvan.common.Validator;
import cn.com.softvan.service.comment.ICommentManager;
import cn.com.softvan.service.course.ICourseManager;
import cn.com.softvan.service.course.ICourseSyllabusItemsManager;
import cn.com.softvan.service.course.ICourseSyllabusManager;
import cn.com.softvan.service.course.ICourseSyllabusPhotoManager;
import cn.com.softvan.service.member.IMemberManager;
import cn.com.softvan.service.student.IStudentManager;
import cn.com.softvan.service.sys.ISmsManager;
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
	/** 短信 业务处理*/
	private ISmsManager smsManager;
	/** 课程表管理-相册 业务处理*/
	private ICourseSyllabusPhotoManager courseSyllabusPhotoManager;
	/**评论信息 管理业务处理*/
	private ICommentManager commentManager;
	/**BEAN类 评论信息*/
	private TcCommentBean cbean;
	/**ID集合*/
	private List<String> item_ids;
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
		TcMemberBean member=(TcMemberBean) request.getSession().getAttribute(CommonConstant.SESSION_KEY_USER_TEACHER_INFO);
		//当前会员关联的学员
		request.setAttribute("student_beans", memberManager.findDataIsListStudent(member));
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
		log.info("T001Action list1.........");
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
		TcMemberBean member=(TcMemberBean) request.getSession().getAttribute(CommonConstant.SESSION_KEY_USER_TEACHER_INFO);
		bean1.setTeacher_id(member.getId());//教师id
		//--已完成课程
		bean1.setNote("1");
		//课程列表
		List<TcCourseSyllabusBean> beans=courseSyllabusItemsManager.findDataIsPageCourse2(bean1);
		request.setAttribute("beans",beans);
		request.setAttribute(CommonConstant.PAGEROW_OBJECT_KEY,page);
		return "list1";
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
		log.info("T001Action list2.........");
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
		TcMemberBean member=(TcMemberBean) request.getSession().getAttribute(CommonConstant.SESSION_KEY_USER_TEACHER_INFO);
		bean1.setTeacher_id(member.getId());//教师id
		//--未完成课程
		bean1.setNote("0");
		//课程列表
		List<TcCourseSyllabusBean> beans=courseSyllabusItemsManager.findDataIsPageCourse2(bean1);
		request.setAttribute("beans",beans);
		request.setAttribute(CommonConstant.PAGEROW_OBJECT_KEY,page);
		return "list2";
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
			bean1.setUser_id(uid);
			bean1.setPasswd(pwd);
			bean1.setUser_type("0");//
			bean=memberManager.checkMemberPWD(bean1);
			if(bean!=null){
				request.getSession().setAttribute(CommonConstant.SESSION_KEY_USER_TEACHER_INFO, bean);
				return SUCCESS;
			}
		}
		request.setAttribute("msg", "登录失败,用户名或密码错误!");

		String requestURL = request.getRequestURL().toString(); // 获取客户端请求的URL
		if(requestURL.contains("/w/")){
			// 回到登录页面
			return "w_tlogin";
		}
		return "tlogin";
	}
	/**
	 * <p>
	 * 信息保存
	 * </p>
	 * <ol>
	 * [功能概要] 
	 * <div>教练给学员评分。</div>
	 * </ol>
	 * @return 转发字符串
	 * @throws IOException 
	 */
	public String save() throws IOException {
		log.info("T001Action save.........");
		String token=request.getParameter("token");
		String token2=(String) request.getSession().getAttribute("token");
		if(token!=null && token.equals(token2)){
			getWriter().print("请不要重复提交!");
			return null;
		}else{
			if(token!=null){
				request.getSession().setAttribute("token",token);
			}
		}
		String msg="1";
		if(item_ids!=null){
			try {
				TcMemberBean user = (TcMemberBean) request.getSession().getAttribute(CommonConstant.SESSION_KEY_USER_TEACHER_INFO);
				TcCourseSyllabusItemsBean s_bean=null;
				for(String item_id:item_ids){
					s_bean=new TcCourseSyllabusItemsBean();
					s_bean.setId(item_id);
					s_bean.setTeacher_id(user.getId());
					s_bean.setUpdate_ip(getIpAddr());
					s_bean.setUpdate_id(user.getUser_id());
					s_bean.setStudent_status(request.getParameter("sstatus"+item_id));
					s_bean.setStudent_status_note(request.getParameter("sstatus_note"+item_id));
					s_bean.setStudent_id(request.getParameter("sid"+item_id));
					s_bean.setCourse_syllabus_id(request.getParameter("course_syllabus_id"));
					msg=courseSyllabusItemsManager.updateDataByStudent(s_bean);
					
					try {
						if(s_bean.getStudent_id()!=null){
						//TODO 获取学员家长联系方式
						TcStudentBean studentBean=new TcStudentBean();
						studentBean.setId(s_bean.getStudent_id());
						List<TcMemberBean> memberBeans=memberManager.findDataIsListMember(studentBean);
							if(memberBeans!=null){
								String course_title=request.getParameter("course_title");//课程名称
								for(TcMemberBean memberBean:memberBeans){
									String tel=null;
									if(Validator.notEmpty(memberBean.getBind_mobile())){
										tel=memberBean.getBind_mobile();
									}else if(Validator.notEmpty(memberBean.getTel())){
										tel=memberBean.getTel();
									}
									if(tel!=null){
										if(Validator.isMobile(tel)){
											//---------短信通知--教师打分情况----------
											TcSysSmsBean smsBean=new TcSysSmsBean();
											smsBean.setSms_dst_id(tel);
											String text="学员["+request.getParameter("sname"+item_id)+"]";
											if("0".equals(s_bean.getStudent_status())){
												text+="已完成";
											}
											if("1".equals(s_bean.getStudent_status())||"2".equals(s_bean.getStudent_status())){
												text+="缺席";
											}
											text+="课程["+course_title+"]";
											if("1".equals(s_bean.getStudent_status())){
												text+=",原因[旷课]";
											}
											if("2".equals(s_bean.getStudent_status())){
												text+=",原因[请假]";
											}
											smsBean.setSms_content(text);
											smsManager.saveOrUpdateData(smsBean);
										}
									}
								}
							}
						}
					} catch (Exception e) {
						log.error("课程签到!发送短信系统错误!",e);
					}
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
		log.info("T001Action savePic.........");
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
				TcMemberBean user = (TcMemberBean) request.getSession().getAttribute(CommonConstant.SESSION_KEY_USER_TEACHER_INFO);
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
		log.info("T001Action clist1.........");
		
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
			bean1.setIs_show("1");//0否1是
//			TcMemberBean user = (TcMemberBean) request.getSession().getAttribute(CommonConstant.SESSION_KEY_USER_TEACHER_INFO);
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
		log.info("T001Action csave.........");
		String msg="1";
		if(cbean!=null){
			try {
				if(Validator.isEmpty(cbean.getDetail_info())){
					msg="信息保存失败!输入信息为空!";
				}else{
					TcMemberBean user = (TcMemberBean) request.getSession().getAttribute(CommonConstant.SESSION_KEY_USER_TEACHER_INFO);
					cbean.setUpdate_ip(getIpAddr());
					cbean.setUpdate_id(user.getUser_id());
					cbean.setCreate_ip(getIpAddr());
					cbean.setCreate_id(user.getUser_id());
					cbean.setMember_id(user.getId());//
					cbean.setMember_type(user.getUser_type());//
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
		log.info("T001Action logout");
		//清空用户登录信息
		request.getSession().removeAttribute(CommonConstant.SESSION_KEY_USER_TEACHER_INFO);
		request.getSession().invalidate();
		
		String requestURL = request.getRequestURL().toString(); // 获取客户端请求的URL
		if(requestURL.contains("/w/")){
			// 回到登录页面
			return "w_tlogin";
		}
		return "tlogin";
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
		log.info("T001Action check");
		String msg="1";
		if(request.getSession().getAttribute(CommonConstant.SESSION_KEY_USER_TEACHER_INFO)==null){
			msg="0";
		}
		
		getWriter().print(msg);
		
		return null;
	}

	/**
	 * 短信 业务处理取得
	 * @return 短信 业务处理
	 */
	public ISmsManager getSmsManager() {
	    return smsManager;
	}

	/**
	 * 短信 业务处理设定
	 * @param smsManager 短信 业务处理
	 */
	public void setSmsManager(ISmsManager smsManager) {
	    this.smsManager = smsManager;
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
	 * ID集合取得
	 * @return ID集合
	 */
	public List<String> getItem_ids() {
	    return item_ids;
	}

	/**
	 * ID集合设定
	 * @param item_ids ID集合
	 */
	public void setItem_ids(List<String> item_ids) {
	    this.item_ids = item_ids;
	}

}
