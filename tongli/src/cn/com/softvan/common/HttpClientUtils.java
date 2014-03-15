package cn.com.softvan.common;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpMethodBase;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.cookie.CookiePolicy;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class HttpClientUtils {

	private static Log log = LogFactory.getLog(HttpClientUtils.class);

	// 获得ConnectionManager，设置相关参数
	private static MultiThreadedHttpConnectionManager manager = new MultiThreadedHttpConnectionManager();
	private static int connectionTimeOut = 20000;
	private static int socketTimeOut = 10000;
	private static int maxConnectionPerHost = 5;
	private static int maxTotalConnections = 40;
	
	
	/**
	 * 验证码获取 URL
	 */
	private static String verifyUrl = "http://www.shjtaq.com/Server1/validatecode.asp?Action=RELOAD";

	/**
	 * post提交的url
	 */
	private static String postUrl = "http://www.shjtaq.com/Server1/dzjc_new.asp";

	/**
	 * 本地存放验证码图片 路劲
	 */
	private static String destfilename = "E:\\yz.png";

	// 初始化ConnectionManger的方法
	static {
		manager.getParams().setConnectionTimeout(connectionTimeOut);
		manager.getParams().setSoTimeout(socketTimeOut);
		manager.getParams().setDefaultMaxConnectionsPerHost(
				maxConnectionPerHost);
		manager.getParams().setMaxTotalConnections(maxTotalConnections);
	}

	/**
	 * 对外提供 创建客户端实例方法
	 * 
	 * @return
	 */
	public static HttpClient getInstance(){
		return new HttpClient(manager);
	}
	
	/**
	 * 
	 * 交通信息网抓取验证码 并转化为字节
	 * 
	 */
	public static byte[] downloadVerifyPic(HttpClient httpclient) {

		GetMethod httpget = new GetMethod(verifyUrl);
		InputStream in = null;
		byte[] tmp = new byte[2048];
		try {

			httpclient.executeMethod(httpget);
			if (httpget.getStatusCode() == HttpStatus.SC_OK) {
				in = httpget.getResponseBodyAsStream();
				int l = -1;
				
				while ((l = in.read(tmp)) != -1) {
				}
			}
		} catch (HttpException e) {
			log.error("下载验证码失败", e);

		} catch (IOException e) {
			log.error("存储验证码图片失败", e);
		} finally {
			try {
				if (in != null) {
					in.close();
				}
			} catch (IOException e) {
				log.error("关闭流异常", e);
			}
			
			// 释放连接
			httpget.releaseConnection();
		}

		return tmp;
		
	}

	/**
	 * 执行一个HTTP POST请求，返回请求响应的HTML
	 * 
	 * @param nameValuePair
	 */
	public static String doPost(HttpClient httpclient, NameValuePair[] nameValuePair) {

		String response = null;
		
		PostMethod httppost = new PostMethod(postUrl);
		
		httppost.setFollowRedirects(false);
		httppost.getParams().setParameter("http.protocol.cookie-policy",
				CookiePolicy.BROWSER_COMPATIBILITY);
		httppost.getParams().setParameter(
				HttpMethodParams.HTTP_CONTENT_CHARSET, "gb2312");

		httppost.setRequestBody(nameValuePair);

		try {
			httpclient.executeMethod(httppost);
			if (httppost.getStatusCode() == HttpStatus.SC_OK) {
				((HttpMethodBase) httppost).getResponseBodyAsString();
					response = new String(httppost.getResponseBody(), 1,
						httppost.getResponseBody().length - 1, "gb2312");
			}
		} catch (HttpException e) {
			log.error(postUrl + "连接异常!", e);
		} catch (IOException e) {
			log.error(postUrl + "获取数据异常!", e);
		} finally {
			httppost.releaseConnection();
		}
		
		return response;
	}


}
