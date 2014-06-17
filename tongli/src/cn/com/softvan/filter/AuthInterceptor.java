package cn.com.softvan.filter;



import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import cn.com.softvan.bean.backuser.TcUaUmBaseUserBean;
import cn.com.softvan.common.CommonConstant;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
/**
 * struts2 监听 权限
 * @author {wuxiaogang}
 *
 */
public class AuthInterceptor extends AbstractInterceptor {
	private static final long serialVersionUID = 5006910018523878858L;
	
	private boolean permistionPass(String uri, Set<String> perms) {
		String u = null;
		int i;
		boolean flag = false;
	    for (String perm : perms) {
				if (uri.startsWith(perm)) {
					  flag= true;
					break;
				}
			}
		 
		return flag;
	}
	
	

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
		//用户信息
		//判断用户是否登陆 
		if(request.getSession().getAttribute(CommonConstant.SESSION_SYS_KEY_USER)==null
				&&!"home_login".equals(actionName)){
			//回到登录页面
			return "login";
		}
		String refereuri = request.getHeader("referer");
		
		if(actionName.equals("home_login")){
			return invocation.invoke();
		}
		
		//判断用户是否有权限操作
		TcUaUmBaseUserBean user=(TcUaUmBaseUserBean) request.getSession().getAttribute(CommonConstant.SESSION_SYS_KEY_USER);
		if (user!=null&&!user.isSuper()&& !permistionPass("/"+actionName, user.getPerms())) {
			//说明该用户没有当前action的操作权限
			//return refereuri;
			return "noauth";
		}
		
		return invocation.invoke();
	}
}
