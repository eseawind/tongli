/**
 * 判断url是否有权限
 * VERSION          DATE                 BY              REASON
 * -------- -------------------  ---------------------- ------------------------------------------
 * 1.00     2014 上午9:50:11             wangzi              程序发布
 * -------- ----------- --------------- ------------------------------------------
 * Copyright 2014 车主管家  System. - All Rights Reserved.
 */
package cn.com.softvan.web.tag;

import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import cn.com.softvan.bean.backuser.TcUaUmBaseUserBean;
import cn.com.softvan.common.CommonConstant;



/**
 * <p> 判断url是否有权限 <p>
 * @author wangzi
 *
 */
public class PermissionTag extends TagSupport{
	private static final transient Logger log = Logger.getLogger(PermissionTag.class);
	
	/**
	 * 序列号
	 */
	private static final long serialVersionUID = 1L;
	

    /** 判断的url地址 */
    private String name;
    /** 判断的url对应的id */
    private String id;


	/**
	 * 判断的url地址を取得します。
	 * @return 判断的url地址
	 */
	public String getName() {
	    return name;
	}


	/**
	 * 判断的url地址を設定します。
	 * @param name 判断的url地址
	 */
	public void setName(String name) {
	    this.name = name;
	}
	/**
	 * 判断的url对应的idを取得します。
	 * @return 判断的url对应的id
	 */
	public String getId() {
	    return id;
	}


	/**
	 * 判断的url对应的idを設定します。
	 * @param id 判断的url对应的id
	 */
	public void setId(String id) {
	    this.id = id;
	}

	@Override
	public int doEndTag() throws JspException {
		//处理标签后，继续处理JSP后面的内容。
		return EVAL_PAGE;
	}
	
	@Override
	public int doStartTag() throws JspException {
		boolean flag = false;
		HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
		HttpSession session = request.getSession();
		
		if(session != null){
			
			TcUaUmBaseUserBean user=(TcUaUmBaseUserBean) request.getSession().getAttribute(CommonConstant.SESSION_SYS_KEY_USER);
			if (user!=null&&user.isSuper()) {//用户对应的角色拥有所有权限
				return TagSupport.EVAL_BODY_INCLUDE;
			}
			
			
			String url =this.name;
			if (StringUtils.isBlank(url)) {
				// url为空，则认为有权限。
				flag = true;
			} else {
				Set<String> perms = (Set<String>) request.getSession().getAttribute(CommonConstant.SESSION_SYS_KEY_USER_PERMS);
				if (perms == null) {
					// perms为null，则代表不需要判断权限。
					flag = true;
				} else {
					for (String perm:perms) {
						if (url.startsWith(perm)) {
							flag = true;
							break;
						}
					}
				}
			}
		
	}
		if(flag){
			//表示需要处理标签体
			return TagSupport.EVAL_BODY_INCLUDE;
		}else{
			//表示不用处理标签体，直接调用doEndTag()方法。
			return TagSupport.SKIP_BODY;
		}

	
}
	
}
