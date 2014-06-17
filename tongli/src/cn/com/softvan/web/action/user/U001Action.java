package cn.com.softvan.web.action.user;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import cn.com.softvan.bean.BaseUserBean;
import cn.com.softvan.bean.backuser.TcUaUmBaseRoleBean;
import cn.com.softvan.bean.backuser.TcUaUmBaseUserBean;
import cn.com.softvan.common.CommonConstant;
import cn.com.softvan.common.IdUtils;
import cn.com.softvan.common.Validator;
import cn.com.softvan.service.user.ITcUaUmBaseRoleManager;
import cn.com.softvan.service.user.ITcUaUmBaseUserManager;
import cn.com.softvan.web.action.BaseAction;
import cn.com.softvan.web.tag.PageInfo;
/**
 * 用户管理
 * @author ll
 *
 */


public class U001Action extends BaseAction{
	/**
	 * 序列号
	 */
	
	private static final transient Logger log = Logger.getLogger(U001Action.class);
	
	/**BEAN类   系统用户 集合*/
	private List<TcUaUmBaseUserBean> beans;
	
	/**用户管理  业务处理*/
	@Autowired
	private ITcUaUmBaseUserManager userManager;
	

	/**角色管理  业务处理*/
	@Autowired
	private ITcUaUmBaseRoleManager roleManager;
	
	
	/** 信息 bean*/
	private TcUaUmBaseUserBean bean;
	
	public String init() {
		log.info("U001Action init.........");
		return "init";
	}
	/**
	 * <p>
	 * 分页查询。
	 * </p>
	 * <ol>
	 * [功能概要] <div>分页查询。</div>
	 * </ol>
	 * @return 转发字符串
	 */
	public String list() {
		log.info("U001Action list.........");
		int offset = 0;
		// 分页偏移量
		if (!Validator.isNullEmpty(request.getParameter("offset"))
				&& Validator.isNum(request.getParameter("offset"))) {
			offset = Integer.parseInt(request.getParameter("offset"));
		}
		TcUaUmBaseUserBean bean1=new TcUaUmBaseUserBean();
		PageInfo page = new PageInfo(); 
		//当前页
		page.setCurrOffset(offset);
		//每页显示条数
		page.setPageRowCount(CommonConstant.PAGEROW_DEFAULT_COUNT);
		bean1.setPageInfo(page);
		beans=userManager.findDataIsPage(bean1);
		request.setAttribute("beans",beans);
		request.setAttribute(CommonConstant.PAGEROW_OBJECT_KEY,page);
		return "list";
	}
	
	
	
	/**
	 * 编辑用户相关信息
	 * @return
	 */
	public String edit(){
		log.info("U001Action ..edit...");
		//用户id
		String id=request.getParameter("id");
		String flag=request.getParameter("flag");
		request.setAttribute("flag", flag);
		if(id!=null&&!"".equals(id)&&flag.equals("edit")){
			request.setAttribute("id",id);
			if(StringUtils.isNotBlank(id)){//修改
				TcUaUmBaseUserBean userBean=new TcUaUmBaseUserBean();
				userBean.setId(id);
				//根据id查询信息
				userBean = userManager.findDataById(userBean);
				if(userBean == null ){
					userBean = new TcUaUmBaseUserBean();
				}
				request.setAttribute("bean", userBean);
				request.setAttribute("roleIds", userBean.getRoleIds());
				Set<java.lang.String> roleIds = new HashSet<String>();
					if (userBean.getRoleIds() != null) {
						for (String roleId : userBean.getRoleIds()) {
							for (String p : StringUtils.split(roleId, ',')) {
								if (!StringUtils.isBlank(p)) {
									roleIds.add(p);
								}
							}
						}
				   }
				String roles="";
				if(roleIds.size()==0){
					roles="[]";
				}else{
					int i=0;
					 for (String rid : roleIds) {
						 i++;
						 if(i==1){
							 roles+="[";
						 }
						 roles+="'"+rid+"'";
						 if(i<roleIds.size()){
							 rid+=",";
						 }
						 if(i==roleIds.size()){
							 rid+="]";
						 }
						 
						}
				}
				request.setAttribute("roles", roles);
				
				
		 }
		 }
		
		if(flag.equals("add")){//新增
			TcUaUmBaseUserBean userbean=new TcUaUmBaseUserBean();
			userbean.setId(IdUtils.createUUID(32));
		    request.setAttribute("bean", userbean);
		    request.setAttribute("roles", "[]");
		}
		List<TcUaUmBaseRoleBean> roleList =new ArrayList<>();
		TcUaUmBaseRoleBean bean1=new TcUaUmBaseRoleBean();
		roleList=roleManager.findDataIsList(bean1);
		request.setAttribute("roleList", roleList);
		return "edit";
		
		
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
		log.info("U001Action save.........");
		if(bean!=null){
			String msg="1";
			try {
				if(Validator.isEmpty(bean.getId())){
					msg="保存失败!";
				}else{
					TcUaUmBaseUserBean bean1=new TcUaUmBaseUserBean();
					bean1.setUsername(bean.getUsername());
					String flag=request.getParameter("flag");
					if(flag!=null&&flag.equals("add")){
						boolean userFlag=userManager.isUsernameExist(bean1);
						
						if(userFlag){//该用户名已经存在
							request.setAttribute("msg","用户名已存在，请重新填写");
							List<TcUaUmBaseRoleBean> roleList =new ArrayList<>();
							TcUaUmBaseRoleBean roleBean=new TcUaUmBaseRoleBean();
							roleList=roleManager.findDataIsList(roleBean);
							request.setAttribute("roleList", roleList);
							request.setAttribute("flag", flag);
							request.setAttribute("bean", bean);
							return "edit";
						}
						
					}
					BaseUserBean user = (BaseUserBean) request.getSession().getAttribute(CommonConstant.SESSION_KEY_USER);
					if(user!=null){
						bean.setCreate_ip(getIpAddr());
						bean.setCreate_id(user.getUser_id());
						bean.setUpdate_ip(getIpAddr());
						bean.setUpdate_id(user.getUser_id());
					}
					String[] roleIds=request.getParameterValues("roleIds");
					Set<String> set = new HashSet<String>();
						if (roleIds != null) {
							for (String roleId : roleIds) {
								for (String p : StringUtils.split(roleId, ',')) {
									if (!StringUtils.isBlank(p)) {
										set.add(p);
									}
								}
							}
					   }
					bean.setRoleSetIds(set);
			        msg=userManager.saveOrUpdateData(bean);
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
	 * 删除。
	 * </p>
	 * <ol>
	 * [功能概要] <div>逻辑删除。</div>
	 * </ol>
	 * @return 转发字符串
	 */
	public String del() {
		log.info("U001Action del.........");
		String id=request.getParameter("id");
		TcUaUmBaseUserBean bean1=new TcUaUmBaseUserBean();
		bean1.setId(id);
		String msg="1";
		try {
			BaseUserBean user = (BaseUserBean) request.getSession().getAttribute(CommonConstant.SESSION_KEY_USER);
			if(user!=null){
				bean1.setUpdate_ip(getIpAddr());
				bean1.setUpdate_id(user.getUser_id());
			}
			
			msg=userManager.deleteDataById(bean1);
		} catch (Exception e) {
			msg=e.getMessage();
		}
		request.setAttribute("msg",msg);
		
		return SUCCESS;
	}
	/**
	 * BEAN类   系统用户 集合取得。
	 * @return BEAN类   系统用户 集合
	 */
	public List<TcUaUmBaseUserBean> getBeans() {
	    return beans;
	}
	/**
	 * BEAN类   系统用户 集合设定。
	 * @param beans BEAN类   系统用户 集合
	 */
	public void setBeans(List<TcUaUmBaseUserBean> beans) {
	    this.beans = beans;
	}
	/**
	 * 用户管理  业务处理取得。
	 * @return 用户管理  业务处理
	 */
	public ITcUaUmBaseUserManager getUserManager() {
	    return userManager;
	}
	/**
	 * 用户管理  业务处理设定。
	 * @param userManager 用户管理  业务处理
	 */
	public void setUserManager(ITcUaUmBaseUserManager userManager) {
	    this.userManager = userManager;
	}
	/**
	 * 角色管理  业务处理取得。
	 * @return 角色管理  业务处理
	 */
	public ITcUaUmBaseRoleManager getRoleManager() {
	    return roleManager;
	}
	/**
	 * 角色管理  业务处理设定。
	 * @param roleManager 角色管理  业务处理
	 */
	public void setRoleManager(ITcUaUmBaseRoleManager roleManager) {
	    this.roleManager = roleManager;
	}
	/**
	 * 信息 bean取得。
	 * @return 信息 bean
	 */
	public TcUaUmBaseUserBean getBean() {
	    return bean;
	}
	/**
	 * 信息 bean设定。
	 * @param bean 信息 bean
	 */
	public void setBean(TcUaUmBaseUserBean bean) {
	    this.bean = bean;
	}

 
}