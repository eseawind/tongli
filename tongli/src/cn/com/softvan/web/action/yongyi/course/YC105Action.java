
/*	
 * 课程地址信息表  ACTION类	
 *		
 * VERSION      DATE          BY              REASON		
 * -------- ----------- --------------- ------------------------------------------	
 * 1.00     2014.07.27      wuxiaogang         程序.发布		
 * -------- ----------- --------------- ------------------------------------------	
 * Copyright 2014 tongli  System. - All Rights Reserved.		
 *	
 */
package cn.com.softvan.web.action.yongyi.course;
import java.util.List;

import org.apache.log4j.Logger;

import cn.com.softvan.bean.BaseUserBean;
import cn.com.softvan.bean.yongyi.course.TcYAddresBean;
import cn.com.softvan.common.CommonConstant;
import cn.com.softvan.common.IdUtils;
import cn.com.softvan.common.Validator;
import cn.com.softvan.service.yongyi.course.ITcYAddresManager;
import cn.com.softvan.web.action.BaseAction;
import cn.com.softvan.web.tag.PageInfo;
/**
 * <p>课程地址信息表  ACTION类。</p>	
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
public class YC105Action extends BaseAction{

	private static final long serialVersionUID = -580041169492433677L;
	private static final transient Logger log = Logger.getLogger(YC105Action.class);
	/**BEAN类  课程地址信息表*/
	private TcYAddresBean bean;
	/**BEAN类  课程地址信息表 集合*/
	private List<TcYAddresBean> beans;
	/**课程地址信息表 业务处理*/
	private ITcYAddresManager addresManager;
	/**
	 * <p> 初始化处理。 </p>
	 * <ol>
	 * [功能概要] 
	 * <div>初始化处理。</div>
	 * </ol>
	 * @return 转发字符串
	 */
	public String init() {
		log.info("C105Action init.........");
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
		TcYAddresBean bean1 = new TcYAddresBean();
		bean1.setPageInfo(page);
		//列表
		List<TcYAddresBean> beans=addresManager.findDataIsPage(bean1);
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
		log.info("C105Action edit.........");
		String id=request.getParameter("id");
		if(id!=null){
			TcYAddresBean bean1=new TcYAddresBean();
			bean1.setId(id);
			bean=addresManager.findDataById(bean1);
		}
		if(bean==null){
			bean=new TcYAddresBean();
			bean.setId(IdUtils.createUUID(32));
		}
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
		log.info("C105Action del.........");
		String id=request.getParameter("id");
			TcYAddresBean bean1=new TcYAddresBean();
		bean1.setId(id);
		String msg="1";
		try {
			msg=addresManager.deleteDataById(bean1);
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
		log.info("C105Action save.........");
		if(bean!=null){
			String msg="1";
			try {
				if(Validator.isEmpty(bean.getAddres())){
					msg="保存失败!信息为空!";
				}else{
					BaseUserBean user = (BaseUserBean) request.getSession().getAttribute(CommonConstant.SESSION_KEY_USER);
					if(user!=null){
						bean.setCreate_ip(getIpAddr());
						bean.setCreate_id(user.getUser_id());
						bean.setUpdate_ip(getIpAddr());
						bean.setUpdate_id(user.getUser_id());
					}
					msg=addresManager.saveOrUpdateData(bean);
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
		log.info("C105Action view.........");
		String id=request.getParameter("id");
		if(id!=null){
			TcYAddresBean bean1=new TcYAddresBean();
			bean1.setId(id);
			bean=addresManager.findDataById(bean1);
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
		log.info("C105Action recycle.........");
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
		TcYAddresBean bean1 = new TcYAddresBean();
		bean1.setPageInfo(page);
		//已删除
		bean1.setDel_flag("1");
		//栏目资讯列表
		List<TcYAddresBean> beans=addresManager.findDataIsPage(bean1);
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
		log.info("C105Action recovery.........");
		String id=request.getParameter("id");
		TcYAddresBean bean1=new TcYAddresBean();
		bean1.setId(id);
		String msg="1";
		try {
			msg=addresManager.recoveryDataById(bean1);
		} catch (Exception e) {
			msg=e.getMessage();
		}
		request.setAttribute("msg",msg);
		
		return SUCCESS;
	}
	/**
	 * BEAN类  课程地址信息表取得
	 * @return BEAN类  课程地址信息表
	 */
	public TcYAddresBean getBean() {
	    return bean;
	}
	/**
	 * BEAN类  课程地址信息表设定
	 * @param bean BEAN类  课程地址信息表
	 */
	public void setBean(TcYAddresBean bean) {
	    this.bean = bean;
	}
	/**
	 * BEAN类  课程地址信息表 集合取得
	 * @return BEAN类  课程地址信息表 集合
	 */
	public List<TcYAddresBean> getBeans() {
	    return beans;
	}
	/**
	 * BEAN类  课程地址信息表 集合设定
	 * @param beans BEAN类  课程地址信息表 集合
	 */
	public void setBeans(List<TcYAddresBean> beans) {
	    this.beans = beans;
	}
	/**
	 * 课程地址信息表 业务处理取得
	 * @return 课程地址信息表 业务处理
	 */
	public ITcYAddresManager getAddresManager() {
	    return addresManager;
	}
	/**
	 * 课程地址信息表 业务处理设定
	 * @param addresManager 课程地址信息表 业务处理
	 */
	public void setAddresManager(ITcYAddresManager addresManager) {
	    this.addresManager = addresManager;
	}
}