package cn.com.softvan.filter;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;

import cn.com.softvan.common.CommonConstant;
import cn.com.softvan.service.member.IMemberManager;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

/**
 * struts2 监听 权限
 * 
 * @author {wuxiaogang}
 * 
 */
public class MemberAuthInterceptor extends AbstractInterceptor {
	private static final long serialVersionUID = 5006910018523878858L;

	/** 会员信息管理 业务处理 */
	@Autowired
	private IMemberManager memberManager;

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
			basePath = request.getScheme() + "://" + request.getServerName()
					+ path;
		}
		request.setAttribute("path", path);
		request.setAttribute("basePath", basePath);
		// log.info("basePath值："+ basePath);
		String actionName = invocation.getInvocationContext().getName();

		// 判断用户是否登陆
		if (request.getSession().getAttribute(
				CommonConstant.SESSION_KEY_USER_MEMBER_INFO) == null
				&& !"m201_login".equals(actionName)) {
			// 回到登录页面
			return "mlogin";
		}
		// 执行该拦截器的下一个拦截器，或者如果没有下一个拦截器，直接执行Action的execute方法
		return invocation.invoke();
	}

	/**
	 * 会员信息管理 业务处理取得
	 * 
	 * @return 会员信息管理 业务处理
	 */
	public IMemberManager getMemberManager() {
		return memberManager;
	}

	/**
	 * 会员信息管理 业务处理设定
	 * 
	 * @param memberManager
	 *            会员信息管理 业务处理
	 */
	public void setMemberManager(IMemberManager memberManager) {
		this.memberManager = memberManager;
	}
}