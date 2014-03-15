package cn.com.softvan.web.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.dispatcher.SessionMap;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.apache.struts2.interceptor.SessionAware;
import org.apache.struts2.util.ServletContextAware;

import cn.com.softvan.common.IpUtils;

import com.opensymphony.xwork2.ActionSupport;

public class BaseAction extends ActionSupport implements SessionAware, ServletRequestAware, ServletResponseAware,ServletContextAware {
	private static final long serialVersionUID = 23436543543L;
	protected SessionMap<?, ?>  session;
	protected HttpServletRequest  request;
	protected HttpServletResponse  response;
	protected ServletContext servletContext;
//	/** 管理员操作日志  service类 */
//	protected IUserLogsManager userLogsManager;
	/***
	 * 返回上次访问链接
	 * @return
	 */
	public String getBackPath(){
		return ServletActionContext.getRequest().getHeader("referer"); 
	}
	/***
	 * 输出
	 * @return
	 * @throws IOException
	 */
	public PrintWriter getWriter() throws IOException {
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		return response.getWriter();
	}
	/**
	 * 获取用户IP
	 * @return
	 */
	public String getIpAddr(){
		return IpUtils.getIpAddr(request);
	}
	public void setServletResponse(HttpServletResponse response) {
		this.response=response;
	}
	public void setServletRequest(HttpServletRequest request) {
		this.request=request;
	}
	public void setSession(Map<String, Object> session) {
		this.session=(SessionMap<?, ?>) session;
	}
	/**
	 * session取得
	 * @return session
	 */
	public SessionMap<?,?> getSession() {
	    return session;
	}
	/**
	 * session设定
	 * @param session session
	 */
	public void setSession(SessionMap<?,?> session) {
	    this.session = session;
	}
	/**
	 * request取得
	 * @return request
	 */
	public HttpServletRequest getRequest() {
	    return request;
	}
	/**
	 * request设定
	 * @param request request
	 */
	public void setRequest(HttpServletRequest request) {
	    this.request = request;
	}
	/**
	 * response取得
	 * @return response
	 */
	public HttpServletResponse getResponse() {
	    return response;
	}
	/**
	 * response设定
	 * @param response response
	 */
	public void setResponse(HttpServletResponse response) {
	    this.response = response;
	}
	/**
	 * servletContext取得
	 * @return servletContext
	 */
	public ServletContext getServletContext() {
	    return servletContext;
	}
	/**
	 * servletContext设定
	 * @param servletContext servletContext
	 */
	public void setServletContext(ServletContext servletContext) {
		this.servletContext=servletContext;
	}
//	/**
//	 * 管理员操作日志  service类取得
//	 * @return 管理员操作日志  service类
//	 */
//	public IUserLogsManager getUserLogsManager() {
//	    return userLogsManager;
//	}
//	/**
//	 * 管理员操作日志  service类设定
//	 * @param userLogsManager 管理员操作日志  service类
//	 */
//	public void setUserLogsManager(IUserLogsManager userLogsManager) {
//	    this.userLogsManager = userLogsManager;
//	}

}
