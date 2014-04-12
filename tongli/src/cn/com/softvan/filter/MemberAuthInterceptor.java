package cn.com.softvan.filter;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import cn.com.softvan.bean.BaseUserBean;
import cn.com.softvan.common.CommonConstant;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
/**
 * struts2 监听 权限
 * @author {wuxiaogang}
 *
 */
public class MemberAuthInterceptor extends AbstractInterceptor {
	private static final long serialVersionUID = 5006910018523878858L;

	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		/*
		 * 获取该http请求的一些信息，下面的日志会使用到
		 */
		HttpServletRequest request = ServletActionContext.getRequest(); // 获取客户端发过来的HTTP请求
		String path = request.getContextPath();
	    String basePath = null;
        if (request.getServerPort() != 80) {
    		basePath = request.getScheme() + "://" + request.getServerName() 
					+ ":" + request.getServerPort() + path;	
        } else {
        	basePath = request.getScheme() + "://" + request.getServerName() + path;
        }
		request.setAttribute("path", path);
		request.setAttribute("basePath", basePath);
//		log.info("basePath值："+ basePath);
		String actionName=invocation.getInvocationContext().getName();
//		//用户信息
//		BaseUserBean userBean=new BaseUserBean();
//		//用户名
//		userBean.setUser_id("admin");
//		request.getSession().setAttribute(CommonConstant.SESSION_KEY_USER, userBean);
		//判断用户是否登陆 
		if(request.getSession().getAttribute(CommonConstant.SESSION_KEY_USER)==null
				&&!"home_login".equals(actionName)
				){
			//回到登录页面
			return "login";
		}
			// 执行该拦截器的下一个拦截器，或者如果没有下一个拦截器，直接执行Action的execute方法
		return invocation.invoke();
	}
}
