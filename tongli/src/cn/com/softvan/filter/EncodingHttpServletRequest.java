/**
 * httpRequest进行包装类
 * VERSION          DATE                 BY              REASON
 * -------- -------------------  ---------------------- ------------------------------------------
 * 1.00     2014 下午3:43:44             wangzi              程序发布
 * -------- ----------- --------------- ------------------------------------------
 * Copyright 2014 车主管家  System. - All Rights Reserved.
 */
package cn.com.softvan.filter;

import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

/**
 * <p>httpRequest进行包装类 <p>
 * 
 * @author wangzi
 * 
 */
public class EncodingHttpServletRequest extends HttpServletRequestWrapper {

	private HttpServletRequest request;
	private String encoding;

	public EncodingHttpServletRequest(HttpServletRequest request) {
		super(request);
		this.request = request;
	}

	public EncodingHttpServletRequest(HttpServletRequest request,
			String encoding) {
		super(request);
		this.request = request;
		this.encoding = encoding;

	}

	@Override
	public String getParameter(String name) {
		String value = request.getParameter(name);
		if (null != value) {
			try {
				// tomcat默认以ISO8859-1处理GET传来的参数。把tomcat上的值用ISO8859-1获取字节流，再转换成UTF-8字符串
				value = new String(value.getBytes("ISO8859-1"), encoding);
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		return value;
	}

}
