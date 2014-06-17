package cn.com.softvan.web.action.user;


import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import cn.com.softvan.bean.BaseUserBean;
import cn.com.softvan.bean.backuser.TcUaUmBaseRoleBean;
import cn.com.softvan.common.CommonConstant;
import cn.com.softvan.common.IdUtils;
import cn.com.softvan.common.Validator;
import cn.com.softvan.service.user.ITcUaUmBaseRoleManager;
import cn.com.softvan.web.action.BaseAction;
import cn.com.softvan.web.tag.PageInfo;
/**
 * 角色管理
 * @author ll
 *
 */


public class U002Action extends BaseAction{
	/**
	 * 序列号
	 */
	
	private static final transient Logger log = Logger.getLogger(U002Action.class);
	
	/**BEAN类   系统用户 集合*/
	private List<TcUaUmBaseRoleBean> beans;
	
	/**角色管理  业务处理*/
	@Autowired
	private ITcUaUmBaseRoleManager roleManager;
	
	/** 信息 bean*/
	private TcUaUmBaseRoleBean bean;
	
	public String init() {
		log.info("U002Action init.........");
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
		log.info("U002Action list.........");
		int offset = 0;
		// 分页偏移量
		if (!Validator.isNullEmpty(request.getParameter("offset"))
				&& Validator.isNum(request.getParameter("offset"))) {
			offset = Integer.parseInt(request.getParameter("offset"));
		}
		TcUaUmBaseRoleBean bean1=new TcUaUmBaseRoleBean();
		PageInfo page = new PageInfo(); 
		//当前页
		page.setCurrOffset(offset);
		//每页显示条数
		page.setPageRowCount(15);
		bean1.setPageInfo(page);
		beans=roleManager.findDataIsList(bean1);
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
		if(id!=null){
			request.setAttribute("id",id);
			if(StringUtils.isNotBlank(id)){
				TcUaUmBaseRoleBean rolebean=new TcUaUmBaseRoleBean();
				rolebean.setRole_id(id);
				//根据id查询信息
				bean = roleManager.findDataById(rolebean);
				if(bean == null ){
					bean = new TcUaUmBaseRoleBean();
				}
				Set<java.lang.String> perms=bean.getPerms();
				String roleperms="";
				if(perms.size()==0){
					roleperms="[]";
				}else{
					int i=0;
					 for (String perm : perms) {
						 i++;
						 if(i==1){
							 roleperms+="[";
						 }
						 roleperms+="'"+perm+"'";
						 if(i<perms.size()){
							 roleperms+=",";
						 }
						 if(i==perms.size()){
							 roleperms+="]";
						 }
						 
						}
				}
				request.setAttribute("perms", roleperms);
				request.setAttribute("bean", bean);
			
		}
		
		 }
		
		if(bean==null){//新增
			bean=new TcUaUmBaseRoleBean();
			bean.setRole_id(IdUtils.createUUID(32));
			bean.setRo_super("0");
		    request.setAttribute("perms", "[]");
		}
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
		log.info("U002Action save.........");
		if(bean!=null){
			String msg="1";
			try {
				if(Validator.isEmpty(bean.getRo_name())){
					msg="保存失败!信息为空!";
				}else{
					BaseUserBean user = (BaseUserBean) request.getSession().getAttribute(CommonConstant.SESSION_KEY_USER);
					if(user!=null){
						bean.setCreate_ip(getIpAddr());
						bean.setCreate_id(user.getUser_id());
						bean.setUpdate_ip(getIpAddr());
						bean.setUpdate_id(user.getUser_id());
					}
					String[] perms=request.getParameterValues("perms");
					String ro_super=request.getParameter("ro_super");
					bean.setRo_super(ro_super);
					Set<String> set = new HashSet<String>();
						if (perms != null) {
							for (String perm : perms) {
								for (String p : StringUtils.split(perm, ',')) {
									if (!StringUtils.isBlank(p)) {
										set.add(p);
									}
								}
							}
					   }
					bean.setPerms(set);
			        msg=roleManager.saveOrUpdateData(bean);
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
		log.info("U002Action del.........");
		String id=request.getParameter("id");
		TcUaUmBaseRoleBean bean1=new TcUaUmBaseRoleBean();
		bean1.setRole_id(id);
		String msg="1";
		try {
			BaseUserBean user = (BaseUserBean) request.getSession().getAttribute(CommonConstant.SESSION_KEY_USER);
			if(user!=null){
				bean1.setUpdate_ip(getIpAddr());
				bean1.setUpdate_id(user.getUser_id());
			}
			
			msg=roleManager.deleteDataById(bean1);
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
	public List<TcUaUmBaseRoleBean> getBeans() {
	    return beans;
	}
	/**
	 * BEAN类   系统用户 集合设定。
	 * @param beans BEAN类   系统用户 集合
	 */
	public void setBeans(List<TcUaUmBaseRoleBean> beans) {
	    this.beans = beans;
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
	public TcUaUmBaseRoleBean getBean() {
	    return bean;
	}
	/**
	 * 信息 bean设定。
	 * @param bean 信息 bean
	 */
	public void setBean(TcUaUmBaseRoleBean bean) {
	    this.bean = bean;
	}
	
	 

 
}