/*
 * 系统管理_学员管理 ActionClass
 *
 * VERSION  DATE        BY              REASON
 * -------- ----------- --------------- ------------------------------------------
 * 1.00     2014.04.06  wuxiaogang      程序・发布
 * -------- ----------- --------------- ------------------------------------------
 * Copyright 2014 童励  System. - All Rights Reserved.
 *
 */
package cn.com.softvan.web.action.student;

import java.util.List;

import org.apache.log4j.Logger;

import cn.com.softvan.bean.BaseUserBean;
import cn.com.softvan.bean.student.TcStudentBean;
import cn.com.softvan.common.CommonConstant;
import cn.com.softvan.common.Validator;
import cn.com.softvan.service.student.IStudentManager;
import cn.com.softvan.web.action.BaseAction;
import cn.com.softvan.web.tag.PageInfo;

/**
 * 系统管理_学员管理 ActionClass
 * 
 * @author wuxiaogang
 * 
 */
public class S201Action extends BaseAction {

	/**
	 * 序列号
	 */
	private static final long serialVersionUID = -3061791975484213551L;
	private static final transient Logger log = Logger.getLogger(S201Action.class);
	
	/**BEAN类  学员信息*/
	private TcStudentBean bean;
	/**BEAN类  学员信息 集合*/
	private List<TcStudentBean> beans;
	/**学员信息管理 业务处理*/
	private IStudentManager studentManager;
	public S201Action() {
		log.info("默认构造器......M001Action");
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
		log.info("M001Action init.........");
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
		TcStudentBean bean1 = new TcStudentBean();
		bean1.setPageInfo(page);
		//学员列表
		List<TcStudentBean> beans=studentManager.findDataIsPage(bean1);
		request.setAttribute("beans",beans);
		request.setAttribute(CommonConstant.PAGEROW_OBJECT_KEY,page);
		return "init";
	}
	/**
	 * <p>
	 * 编辑。
	 * </p>
	 * <ol>
	 * [功能概要] <div>编辑。</div>
	 * </ol>
	 * @return 转发字符串
	 */
	public String edit() {
		log.info("M001Action edit.........");
		String id=request.getParameter("id");
		if(id!=null){
			TcStudentBean bean1=new TcStudentBean();
			bean1.setId(id);
			bean=studentManager.findDataById(bean1);
		}
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
		log.info("M001Action del.........");
		String id=request.getParameter("id");
		TcStudentBean bean1=new TcStudentBean();
		bean1.setId(id);
		String msg="1";
		try {
			msg=studentManager.deleteDataById(bean1);
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
		log.info("M001Action edit.........");
		if(bean!=null){
			String msg="1";
			try {
				if(Validator.isEmpty(bean.getName())){
					msg="保存失败!信息为空!";
				}else{
					BaseUserBean user = (BaseUserBean) request.getSession().getAttribute(CommonConstant.SESSION_KEY_USER);
					if(user!=null){
						bean.setCreate_ip(getIpAddr());
						bean.setCreate_id(user.getUser_id());
						bean.setUpdate_ip(getIpAddr());
						bean.setUpdate_id(user.getUser_id());
					}
					msg=studentManager.saveOrUpdateData(bean);
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
	 * 预览。
	 * </p>
	 * <ol>
	 * [功能概要] <div>预览。</div>
	 * </ol>
	 * @return 转发字符串
	 */
	public String view() {
		log.info("M001Action view.........");
		String id=request.getParameter("id");
		if(id!=null){
			TcStudentBean bean1=new TcStudentBean();
			bean1.setId(id);
			bean=studentManager.findDataById(bean1);
		}
		return "view";
	}
	/**
	 * <p>
	 * 回收站。
	 * </p>
	 * <ol>
	 * [功能概要] <div>回收站。</div>
	 * </ol>
	 * @return 转发字符串
	 */
	public String recycle() {
		log.info("M001Action recycle.........");
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
		TcStudentBean bean1 = new TcStudentBean();
		bean1.setPageInfo(page);
		//已删除
		bean1.setDel_flag("1");
		//学员列表
		List<TcStudentBean> beans=studentManager.findDataIsPage(bean1);
		request.setAttribute("beans",beans);
		request.setAttribute(CommonConstant.PAGEROW_OBJECT_KEY,page);
		return "recycle";
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
		log.info("M001Action recovery.........");
		String id=request.getParameter("id");
		TcStudentBean bean1=new TcStudentBean();
		bean1.setId(id);
		String msg="1";
		try {
			msg=studentManager.recoveryDataById(bean1);
		} catch (Exception e) {
			msg=e.getMessage();
		}
		request.setAttribute("msg",msg);
		
		return SUCCESS;
	}

	/**
	 * BEAN类  学员信息取得
	 * @return BEAN类  学员信息
	 */
	public TcStudentBean getBean() {
	    return bean;
	}

	/**
	 * BEAN类  学员信息设定
	 * @param bean BEAN类  学员信息
	 */
	public void setBean(TcStudentBean bean) {
	    this.bean = bean;
	}

	/**
	 * BEAN类  学员信息 集合取得
	 * @return BEAN类  学员信息 集合
	 */
	public List<TcStudentBean> getBeans() {
	    return beans;
	}

	/**
	 * BEAN类  学员信息 集合设定
	 * @param beans BEAN类  学员信息 集合
	 */
	public void setBeans(List<TcStudentBean> beans) {
	    this.beans = beans;
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
}
