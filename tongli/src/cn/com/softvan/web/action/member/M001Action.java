/*
 * 系统管理_会员管理 ActionClass
 *
 * VERSION  DATE        BY              REASON
 * -------- ----------- --------------- ------------------------------------------
 * 1.00     2014.04.06  wuxiaogang      程序・发布
 * -------- ----------- --------------- ------------------------------------------
 * Copyright 2014 童励  System. - All Rights Reserved.
 *
 */
package cn.com.softvan.web.action.member;

import java.util.List;

import org.apache.log4j.Logger;

import cn.com.softvan.bean.BaseUserBean;
import cn.com.softvan.bean.member.TcMemberBean;
import cn.com.softvan.bean.sys.TcSysNewsBean;
import cn.com.softvan.common.CommonConstant;
import cn.com.softvan.common.IdUtils;
import cn.com.softvan.common.Validator;
import cn.com.softvan.service.member.IMemberManager;
import cn.com.softvan.service.student.IStudentManager;
import cn.com.softvan.web.action.BaseAction;
import cn.com.softvan.web.tag.PageInfo;

/**
 * 系统管理_会员管理 ActionClass
 * 
 * @author wuxiaogang
 * 
 */
public class M001Action extends BaseAction {

	/**
	 * 序列号
	 */
	private static final long serialVersionUID = -3061791975484213551L;
	private static final transient Logger log = Logger.getLogger(M001Action.class);
	
	/**BEAN类  会员信息*/
	private TcMemberBean bean;
	/**BEAN类  会员信息 集合*/
	private List<TcMemberBean> beans;
	/**会员信息管理 业务处理*/
	private IMemberManager memberManager;
	/**学员信息管理 业务处理*/
	private IStudentManager studentManager;
	public M001Action() {
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
		TcMemberBean bean1 = new TcMemberBean();
		bean1.setPageInfo(page);
		//会员列表
		List<TcMemberBean> beans=memberManager.findDataIsPage(bean1);
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
			TcMemberBean bean1=new TcMemberBean();
			bean1.setId(id);
			bean=memberManager.findDataById(bean1);
			//当前会员关联的学员
			request.setAttribute("member_student_beans", memberManager.findDataIsListStudent(bean1));
		}
		if(bean==null){
			bean=new TcMemberBean();
			bean.setId(IdUtils.createUUID(32));
		}
		//所有学员信息
		request.setAttribute("student_beans",studentManager.findDataIsList(null));
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
		TcMemberBean bean1=new TcMemberBean();
		bean1.setId(id);
		String msg="1";
		try {
			msg=memberManager.deleteDataById(bean1);
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
				if(Validator.isEmpty(bean.getUser_id())){
					msg="保存失败!信息为空!";
				}else{
					BaseUserBean user = (BaseUserBean) request.getSession().getAttribute(CommonConstant.SESSION_KEY_USER);
					if(user!=null){
						bean.setCreate_ip(getIpAddr());
						bean.setCreate_id(user.getUser_id());
						bean.setUpdate_ip(getIpAddr());
						bean.setUpdate_id(user.getUser_id());
					}
					msg=memberManager.saveOrUpdateData(bean);
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
			TcMemberBean bean1=new TcMemberBean();
			bean1.setId(id);
			bean=memberManager.findDataById(bean1);
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
		TcMemberBean bean1 = new TcMemberBean();
		bean1.setPageInfo(page);
		//已删除
		bean1.setDel_flag("1");
		//会员列表
		List<TcMemberBean> beans=memberManager.findDataIsPage(bean1);
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
		TcMemberBean bean1=new TcMemberBean();
		bean1.setId(id);
		String msg="1";
		try {
			msg=memberManager.recoveryDataById(bean1);
		} catch (Exception e) {
			msg=e.getMessage();
		}
		request.setAttribute("msg",msg);
		
		return SUCCESS;
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
	 * BEAN类  会员信息 集合取得
	 * @return BEAN类  会员信息 集合
	 */
	public List<TcMemberBean> getBeans() {
	    return beans;
	}

	/**
	 * BEAN类  会员信息 集合设定
	 * @param beans BEAN类  会员信息 集合
	 */
	public void setBeans(List<TcMemberBean> beans) {
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
}
