/*
 * 前台首页 ActionClass
 *
 * VERSION  DATE        BY              REASON
 * -------- ----------- --------------- ------------------------------------------
 * 1.00     2014.04.01  wuxiaogang      程序・发布
 * -------- ----------- --------------- ------------------------------------------
 * Copyright 2014 jfq  System. - All Rights Reserved.
 *
 */
package cn.com.softvan.web.action.course;

import java.util.List;

import org.apache.log4j.Logger;

import cn.com.softvan.bean.BaseUserBean;
import cn.com.softvan.bean.course.TcCourseBean;
import cn.com.softvan.common.CommonConstant;
import cn.com.softvan.common.Validator;
import cn.com.softvan.service.course.ICourseManager;
import cn.com.softvan.web.action.BaseAction;
import cn.com.softvan.web.tag.PageInfo;

/**
 * 前台首页 ActionClass
 * 
 * @author wuxiaogang
 * 
 */
public class C101Action extends BaseAction {

	/**
	 * 序列号
	 */
	private static final long serialVersionUID = -3061791975484213551L;
	private static final transient Logger log = Logger.getLogger(C101Action.class);
	/** 课程管理 业务处理*/
	private ICourseManager courseManager;
	/**课程信息BEAN*/
	private TcCourseBean bean;
	/**课程信息BEAN集合*/
	private List<TcCourseBean> beans;
	public C101Action() {
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
		TcCourseBean bean1 = new TcCourseBean();
		bean1.setPageInfo(page);
		//列表
		List<TcCourseBean> beans=courseManager.findDataIsPage(bean1);
		request.setAttribute("beans",beans);
		request.setAttribute(CommonConstant.PAGEROW_OBJECT_KEY,page);
		
		return "init";
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
			TcCourseBean bean1=new TcCourseBean();
			bean1.setId(id);
			bean=courseManager.findDataById(bean1);
		}
		//栏目树
//		request.setAttribute("tree",courseManager.findDataIsTree(null));
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
		TcCourseBean bean1=new TcCourseBean();
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
		TcCourseBean bean1=new TcCourseBean();
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
	 * 课程信息BEAN取得
	 * @return 课程信息BEAN
	 */
	public TcCourseBean getBean() {
	    return bean;
	}

	/**
	 * 课程信息BEAN设定
	 * @param bean 课程信息BEAN
	 */
	public void setBean(TcCourseBean bean) {
	    this.bean = bean;
	}

	/**
	 * 课程信息BEAN集合取得
	 * @return 课程信息BEAN集合
	 */
	public List<TcCourseBean> getBeans() {
	    return beans;
	}

	/**
	 * 课程信息BEAN集合设定
	 * @param beans 课程信息BEAN集合
	 */
	public void setBeans(List<TcCourseBean> beans) {
	    this.beans = beans;
	}


}
