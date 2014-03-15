/**
 * 编码过滤器 
 * VERSION          DATE                 BY              REASON
 * -------- -------------------  ---------------------- ------------------------------------------
 * 1.00     2014-03-13 15:36:21             wangzi              程序发布
 * -------- ----------- --------------- ------------------------------------------
 * Copyright 2014 车主管家  System. - All Rights Reserved.
 */
package cn.com.softvan.filter;


import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 编码过滤器
 * <p>
 * 
 * @author wangzi
 * 
 */
public class EncodingFilter implements Filter {
	private String encoding;

	public void init(FilterConfig fConfig) {
		encoding = fConfig.getInitParameter("encoding");
	}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) {
		HttpServletRequest httprequest = (HttpServletRequest) request;

		if ("GET".equals(httprequest.getMethod())) {
			// 将httpRequest进行包装
			EncodingHttpServletRequest wrapper = new EncodingHttpServletRequest(
					httprequest, encoding);
			try {
				chain.doFilter(wrapper, response);
			} catch (Exception e) {
				// Auto-generated catch block
				e.printStackTrace();
			}
		} else {

			try {
				request.setCharacterEncoding(encoding);
				response.setContentType("text/html;charset=" + encoding);
				chain.doFilter(request, response);
			} catch (Exception e) {
				// Auto-generated catch block
				e.printStackTrace();
			}

		}
	}

	public void destroy() {

	}

}
