
/*	
 * 班级信息表  ACTION类	
 *		
 * VERSION      DATE          BY              REASON		
 * -------- ----------- --------------- ------------------------------------------	
 * 1.00     2014.07.26      wuxiaogang         程序.发布		
 * -------- ----------- --------------- ------------------------------------------	
 * Copyright 2014 tongli  System. - All Rights Reserved.		
 *	
 */
package cn.com.softvan.web.action.classes;
import java.util.List;

import org.apache.log4j.Logger;

import cn.com.softvan.bean.BaseUserBean;
import cn.com.softvan.bean.classes.TcClassesBean;
import cn.com.softvan.common.CommonConstant;
import cn.com.softvan.common.IdUtils;
import cn.com.softvan.common.Validator;
import cn.com.softvan.service.classes.IClassesManager;
import cn.com.softvan.service.student.IStudentManager;
import cn.com.softvan.web.action.BaseAction;
import cn.com.softvan.web.tag.PageInfo;
/**
 * <p>班级信息表  ACTION类。</p>	
 * <ol>[功能概要] 
 * <div>初始化。</div> 
 * <div>编辑页面(页面)(新增or修改)。</div> 
 * <div>信息保存(功能)(新增or修改)。</div> 
 * <div>预览(页面)。</div> 
 * <div>回收站(页面)。</div> 
 * <div>逻辑删除(功能)。</div> 
 * <div>物理删除(功能)。</div> 
 * <div>恢复逻辑删除(功能)。</div> 
 *</ol> 
 * @author wuxiaogang
 */
public class C401Action extends BaseAction{

	private static final long serialVersionUID = -403318159539951021L;
	private static final transient Logger log = Logger.getLogger(C401Action.class);
	/**BEAN类  班级信息表*/
	private TcClassesBean bean;
	/**BEAN类  班级信息表 集合*/
	private List<TcClassesBean> beans;
	/**班级信息表 业务处理*/
	private IClassesManager classesManager;
	/**学员信息管理 业务处理*/
	private IStudentManager studentManager;
	/**
	 * <p> 初始化处理。 </p>
	 * <ol>
	 * [功能概要] 
	 * <div>初始化处理。</div>
	 * </ol>
	 * @return 转发字符串
	 */
	public String init() {
		log.info("C401Action init.........");
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
		TcClassesBean bean1 = new TcClassesBean();
		bean1.setPageInfo(page);
		//列表
		List<TcClassesBean> beans=classesManager.findDataIsPage(bean1);
		request.setAttribute("beans",beans);
		request.setAttribute(CommonConstant.PAGEROW_OBJECT_KEY,page);
		return "init";
	}
	/**
	 * <p> 编辑。</p>
	 * <ol>
	 * [功能概要] 
	 * <div>编辑。</div>
	 * </ol>
	 * @return 转发字符串
	 */
	public String edit() {
		log.info("C401Action edit.........");
		String id=request.getParameter("id");
		if(id!=null){
			TcClassesBean bean1=new TcClassesBean();
			bean1.setId(id);
			bean=classesManager.findDataById(bean1);
			//当前会员关联的学员
			request.setAttribute("classes_student_beans", classesManager.findDataIsListStudent(bean1));
		}
		if(bean==null){
			bean=new TcClassesBean();
			bean.setId(IdUtils.createUUID(32));
		}
		//所有学员信息
		request.setAttribute("student_beans",studentManager.findDataIsList(null));
		return "edit";
	}
	/**
	 * <p> 删除。 </p>
	 * <ol>
	 * [功能概要] 
	 * <div>逻辑删除。</div>
	 * </ol>
	 * @return 转发字符串
	 */
	public String del() {
		log.info("C401Action del.........");
		String id=request.getParameter("id");
			TcClassesBean bean1=new TcClassesBean();
		bean1.setId(id);
		String msg="1";
		try {
			msg=classesManager.deleteDataById(bean1);
		} catch (Exception e) {
			msg=e.getMessage();
		}
		request.setAttribute("msg",msg);
		
		return SUCCESS;
	}
	/**
	 * <p> 信息保存 </p>
	 * <ol>
	 * [功能概要] 
	 * <div>新增。</div>
	 * <div>修改。</div>
	 * </ol>
	 * @return 转发字符串
	 */
	public String save() {
		log.info("C401Action save.........");
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
					msg=classesManager.saveOrUpdateData(bean);
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
	 * <p> 预览。</p>
	 * <ol>
	 * [功能概要] 
	 * <div>预览。</div>
	 * </ol>
	 * @return 转发字符串
	 */
	public String view() {
		log.info("C401Action view.........");
		String id=request.getParameter("id");
		if(id!=null){
			TcClassesBean bean1=new TcClassesBean();
			bean1.setId(id);
			bean=classesManager.findDataById(bean1);
		}
		return "view";
	}
	/**
	 * <p> 回收站。</p>
	 * <ol>
	 * [功能概要] 
	 * <div>回收站。</div>
	 * </ol>
	 * @return 转发字符串
	 */
	public String recycle() {
		log.info("C401Action recycle.........");
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
		TcClassesBean bean1 = new TcClassesBean();
		bean1.setPageInfo(page);
		//已删除
		bean1.setDel_flag("1");
		//栏目资讯列表
		List<TcClassesBean> beans=classesManager.findDataIsPage(bean1);
		request.setAttribute("beans",beans);
		request.setAttribute(CommonConstant.PAGEROW_OBJECT_KEY,page);
		return "recycle";
	}
	/**
	 * <p> 恢复。</p>
	 * <ol>[功能概要] 
	 * <div>恢复逻辑删除的数据。</div>
	 * </ol>
	 * @return 转发字符串
	 */
	public String recovery() {
		log.info("C401Action recovery.........");
		String id=request.getParameter("id");
		TcClassesBean bean1=new TcClassesBean();
		bean1.setId(id);
		String msg="1";
		try {
			msg=classesManager.recoveryDataById(bean1);
		} catch (Exception e) {
			msg=e.getMessage();
		}
		request.setAttribute("msg",msg);
		
		return SUCCESS;
	}
	/**
	 * BEAN类  班级信息表取得
	 * @return BEAN类  班级信息表
	 */
	public TcClassesBean getBean(){
		return bean;
	}
	/**
	 * BEAN类  班级信息表设定
	 * @param bean BEAN类  班级信息表
	 */
	public void setBean(TcClassesBean bean){
		this.bean=bean;
	}
	/**
	 * BEAN类  班级信息表 集合取得
	 * @return BEAN类  班级信息表 集合
	 */
	public List<TcClassesBean> getBeans(){
		return beans;
	}
	/**
	 * BEAN类  班级信息表 集合设定
	 * @param beans BEAN类  班级信息表 集合
	 */
	public void setBeans(List<TcClassesBean> beans){
		this.beans=beans;
	}
	/**
	 * 班级信息表 业务处理取得
	 * @return 班级信息表 业务处理
	 */
	public IClassesManager getClassesManager() {
	    return classesManager;
	}
	/**
	 * 班级信息表 业务处理设定
	 * @param classesManager 班级信息表 业务处理
	 */
	public void setClassesManager(IClassesManager classesManager) {
	    this.classesManager = classesManager;
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