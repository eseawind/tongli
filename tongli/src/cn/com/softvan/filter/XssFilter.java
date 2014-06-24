/**
 * 防止无聊人士XSS攻击.
 * VERSION          DATE                 BY              REASON
 * -------- -------------------  ---------------------- ------------------------------------------
 * 1.00           2014-06-24             wuxiaogang       程序.发布
 * -------- ----------- --------------- ------------------------------------------
 * Copyright 2014 车主管家  System. - All Rights Reserved.
 */
package cn.com.softvan.filter;

import java.io.IOException;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import cn.com.softvan.common.StrUtil;

/**
 * 防止无聊人士XSS攻击.
 * 
 * @author wuxiaogang
 * 
 */
public class XssFilter  implements Filter {
	@Override  
	public void init(FilterConfig filterConfig) throws ServletException {  
    }  
  
    @Override  
    public void doFilter(ServletRequest request, ServletResponse response,  
            FilterChain chain) throws IOException, ServletException { 
    	request.setCharacterEncoding("utf-8");
    	Map<String, String[]> paramsMap = (Map<String, String[]>) request.getParameterMap(); // 获取所有的请求参数
		/*
		 * 获取所有参数的名值对信息的字符串表示，存储在变量paramsStr中
		 */
		if (paramsMap != null && paramsMap.size() > 0) {
			Set<Entry<String, String[]>> paramsSet = paramsMap.entrySet();
			for (Entry<String, String[]> param : paramsSet) {
				String paramName = param.getKey(); // 参数的名字
				if(!paramName.contains("carimg")){//html5 编码后的某页面img字符....因为太长
					String[] paramValues = param.getValue(); // 参数的值
					if (paramValues.length == 1) { // 参数只有一个值，绝大多数情况
						//TODO XSS 字符过滤
						paramValues[0]=StrUtil.xssEncode(paramValues[0]);
						System.out.println("------"+paramValues[0]);
					} else {
						for (String paramValue : paramValues) {
							//TODO XSS 字符过滤
							paramValue=StrUtil.xssEncode(paramValue);
							System.out.println("------"+paramValue);
						}
					}
				}
			}
		}
        chain.doFilter(request, response);  
    }  
    @Override  
    public void destroy() {  
    }  
}
