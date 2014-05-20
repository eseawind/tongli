/*
 * 发送短信工具类
 *
 * VERSION  DATE        BY              REASON
 * -------- ----------- --------------- ------------------------------------------
 * 1.00     2011.11.16  Huyunlin           程序・发布
 * -------- ----------- --------------- ------------------------------------------
 * Copyright 2011 softvan System. - All Rights Reserved.
 *
 */
package cn.com.softvan.common.sms;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.util.URIUtil;
import org.apache.log4j.Logger;

import cn.com.softvan.common.CommonConstant;
import cn.com.softvan.common.DateUtil;
import cn.com.softvan.common.IdUtils;
import cn.com.softvan.common.Validator;
import cn.com.softvan.dao.sms.SmsInfoDao;


/**
 * <p>发送短信工具类</p>
 * <ol>[提供机能]
 * <li>通过这个工具可以完成异步发送短消息。
 * </ol>
 *
 * @author Huyunlin
 */
public class SmsSenderHelper {
	
	private static final transient Logger log = Logger.getLogger("sms"); 
	
	// 发送短信对象
	private ISmsSender smsSender;
	
    /**
     * <p>同步发送短信</p>
     * <ol>[功能概要]
     * <div>异步发送短信，不会阻塞主程序</div>
     * </ol>
     * @return 发送结果
     */
	public boolean synSend(SmsInfo smsInfo) {
		if (smsSender == null) {
			log.error("发送短信对象未初始化");
			return false;
		}

		return this.smsSender.send(smsInfo);
	}
	
    /**
     * <p>发送短信</p>
     * <ol>[功能概要]
     * <div>系统将短信内容保存到数据库中，然后系统自动发送已经保存的短消息</div>
     * </ol>
     * @return 结果 true:成功；false:失败
     */
	public boolean send(SmsInfo info) {
		
		// 检查短消息对象和内容
		if (!checkSmsinfo(info)) {
			return false;
		}
		
		Map data = new HashMap();
		
		// 发信人
		if (!Validator.isNullEmpty(info.getSrcTermId())) {
			data.put("SMS_SRC_ID", info.getSrcTermId());
		}
		// 短信内容
		data.put("SMS_CONTENT", info.getBody());
		// 预定发送时间
    	if (info.getSendDate() != null) {
    		data.put("SMS_SEND_TIME", DateUtil.getDateTimeStr(info.getSendDate()));
    	} else {
    		data.put("SMS_SEND_TIME", DateUtil.getDateTimeStr(new Date()));
    	}
    	
    	SmsInfoDao smsDao = new SmsInfoDao();
    	try {
    		smsDao.initConn();
		} catch (Exception e) {
        	log.error(CommonConstant.LOG_ERROR_TITLE, e);
        	log.error("初始化数据库连接失败");
        	return false;
		}
		
		// 拼结起来的收信人字符串大于255时，保存一次
		try {
	    	Iterator<String> ite =  info.getDstTermId().iterator();
	    	StringBuilder sb = null;
	    	int count = info.getDstTermId().size();
	    	System.out.println(count);
	    	
	    	
	    	while(ite.hasNext()) {
	    		String mobile = ite.next();
	    		if (sb == null) {
	    			sb = new StringBuilder();
	    		} else {
	    			// 保存短信
	        		if ((sb.toString().length() + mobile.length()) > 11) { // SmsConstant.SMS_TO_LENGTH
	        			
	        			// 编号
	        			data.put("SMS_ID", IdUtils.createUUID(8));
	        			// 收件人
	        			data.put("SMS_DST_ID", sb.toString());
	        			
	        			smsDao.saveInfo(data, SmsInfoDao.TABLE_NAME);
	        			
	        			// 下一条邮件记录
	        			sb = new StringBuilder();
	        		} else {
	        			sb.append(SmsConstant.SMS_SEPRATE_TO);
	        		}
	    		}
	    		
	    		sb.append(mobile);
	    	}
	    
	    	if (sb.length() > 0) {
    			// 邮件编号
    			data.put("SMS_ID", IdUtils.createUUID(32));
    			// 收件人
    			data.put("SMS_DST_ID", sb.toString());
    			smsDao.saveInfo(data, SmsInfoDao.TABLE_NAME);
	    	}
	    	
	    	smsDao.commit();
	    	
		} catch (Exception ex) {
			try {
				smsDao.rollback();
			} catch (Exception e) {
	        	log.error(CommonConstant.LOG_ERROR_TITLE, e);
	        	log.error("数据库回滚失败");
			}
			
        	log.error(CommonConstant.LOG_ERROR_TITLE, ex);
        	log.error("邮件提交失败");
        	return false;
		}
		
		return true;
	}
	
	
	

    /**
     * <p>检索短信</p>
     * </ol>
     * 
     * @return 检索结果
     */
	public List<SmsInfo> queryNoSendInfo() {
		
		try {
			List<SmsInfo> smsList = SmsInfoDao.queryNoSendInfo();
//			// 修改状态
//			List<String> idList = new ArrayList<String>();
//			if (smsList != null && !smsList.isEmpty()) {
//				for (int i = 0; i < smsList.size(); i++) {
//					idList.add(smsList.get(i).getSMS_ID());
//				}
//				SmsInfoDao.updateStatus(idList);
//			}

			return smsList;
		} catch (Exception e) {
        	log.error(CommonConstant.LOG_ERROR_TITLE, e);
        	return null;
		}
	}
	
    /**
     * <p>改变短信的发送状态</p>
     * </ol>
     * 
     * @param info 短信信息对象
     * @param isOk 是否发送成功
     * @return true:成功；false:失败
     */
	public boolean changeStatus(SmsInfo info, boolean isOk) {
		
		Map data = new HashMap();
		data.put(SmsInfoDao.PK_NAME, info.getSms_id());
		if (isOk) {
			// 预定发送时间
			data.put("SMS_SENDED_TIME", DateUtil.getDateTimeStr(new Date()));
			// 发送成功
			data.put("SMS_STATUS", SmsConstant.SMS_STATUS_FINISHED);
		} //else {
//			// 发送失败
//			data.put("SMS_STATUS", SmsConstant.SMS_STATUS_NO);
//		}
		int count = info.getSms_send_count() == null?0:Integer.parseInt(info.getSms_send_count());
		data.put("SMS_SEND_COUNT", (count + 1) + "");
		try {
			SmsInfoDao.updateById(data);
		} catch (Exception e) {
        	log.error(CommonConstant.LOG_ERROR_TITLE, e);
        	return false;
		}
		
		return true;
	}
	
    /**
     * <p>检查短信息是否符合要求</p>
     * @param smsInfo 短消息对象
     * @return 结果 true:成功；false:失败
     */
	private boolean checkSmsinfo(SmsInfo smsInfo) {

		// 短消息对象为空
		if (smsInfo == null) {
			//log.error("短消息对象为NULL");
			return false;
		}
		// 短消息内容为空
		if (Validator.isNullEmpty(smsInfo.getBody())) {
			//log.error("短消息内容为空");
			return false;
		}
		// 短消息内容超过140个字符
		if (smsInfo.getBody().length() > SmsConstant.SMS_CHAR_COUNT 
				|| Validator.getWordCount(smsInfo.getBody()) > SmsConstant.SMS_CHAR_COUNT * 2) {
			//log.error("短消息内容超过了"+ SmsConstant.SMS_CHAR_COUNT +"个字符");
			return false;
		}		
		// 收信人为空	
		if (smsInfo.getDstTermId() == null || smsInfo.getDstTermId().isEmpty()) {
			//log.error("收信人为空");
			return false;
		}
		
		// 收信人手机号码不正确
		Iterator<String> ite = smsInfo.getDstTermId().iterator();
		while(ite.hasNext()) {
			if (!Validator.isMobile(ite.next())) {
				//log.error("收信人手机号码不正确");
				return false;
			}
		}
			
		return true;
	}
	
    /**
     * smsSender取得
     * @return smsSender
     */
    public ISmsSender getSmsSender() {
        return smsSender;
    }

    /**
     * smsSender设定
     * @param smsSender smsSender
     */
    public void setSmsSender(ISmsSender smsSender) {
        this.smsSender = smsSender;
    }
    
	public static void main(String[] args) throws MalformedURLException, IOException, Exception {
		
		SmsSenderHelper helper = new SmsSenderHelper();
	    helper.setSmsSender(new SmsSender());
		
		SmsInfo s = new SmsInfo();
		s.setBody("测试：详细设置参数方式发送短信");
		//s.setBody("01234567890123456789012345678901234567890123456789012345678901234567012345670123456701234567测试");
		//s.setBody("012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567测试145字符");
		s.addDstTermId("13681818031");
//		s.addDstTermId("13681818031");
//		s.addDstTermId("13681818031");
//		s.addDstTermId("13681818031");
//		s.addDstTermId("13681818031");
//		s.addDstTermId("13681818031");
		
		
		/*
		 if (helper.synSend(s)) {
			log.info("短消息提交成功");
		} else {
			log.info("短消息提交失败");
		}
		*/
		 
	/*post 方式提交
	Map<String, String> m=new HashMap<String, String>();
	m.put("account", "10657109042619");
	m.put("psw", "Shanghai1106Tour");
	m.put("phonestr", "13641601853");
	m.put("content", "测试");
     String y = new SmsSenderHelper().doPost("http://210.13.105.244:1117/sendsms.aspx", m,
							"utf-8", false);
 	*/				
					
    String x = new SmsSender().doGet("http://sms.5ikh.com:81/SmsService/UnicomWdslRec.asmx/SendMessage", "Sd_UserName=201401131151&Sd_UserPsd=123456&Sd_Phones=15021522231&Sd_MsgContent=test2这是一条测试信息&Sd_SchTime=&Sd_ExNumber=&Sd_SeqNum=",
			"utf-8", false);			
    System.out.println("=============="+x+"=================");
	 }
	
	
//	/**
//	 * 执行一个HTTP POST请求，返回请求响应的HTML
//	 * 
//	 * @param url
//	 *            请求的URL地址
//	 * @param params
//	 *            请求的查询参数,可以为null
//	 * @param charset
//	 *            字符集
//	 * @param pretty
//	 *            是否美化
//	 * @return 返回请求响应的HTML
//	 */
//	public  String doPost(String url, Map<String, String> params,
//			String charset, boolean pretty) {
//		StringBuffer response = new StringBuffer();
//		final MultiThreadedHttpConnectionManager manager = new MultiThreadedHttpConnectionManager();
//        final HttpClient client = new HttpClient(manager);
//
//		client.setConnectionTimeout(1000*3);
//		client.setTimeout(1000*3);
//		final  PostMethod method = new PostMethod(url);
//		
//		//method.setRequestHeader("Content-Type","application/x-www-form-urlencoded;charset="+charset);  
//		// 设置Http Post数据
//		 if (params != null) {
//			for (Map.Entry<String, String> entry : params.entrySet()) {
//				method.addParameter(entry.getKey(), entry.getValue());  
//				System.out.println(entry.getKey()+"="+entry.getValue());
//			}
//			
//		}
//		System.out.println(url);
//		Thread t = new Thread(new Runnable() {
//            public void run() {
//                try {
//                    client.executeMethod(method);
//                    String result = method.getResponseBodyAsString();
//                	System.out.println(result);
//                } catch (Exception e) {
//                	System.out.println("短信发送失败--时间--"+new Date()+"---");
//                }
//            }
//        }, "Timeout guard");
//        t.setDaemon(true);
//        t.start();
//        try {
//            t.join(1000*3);  //等待3s后结束
//        } catch (InterruptedException e) {
//            ((MultiThreadedHttpConnectionManager) client.getHttpConnectionManager()).shutdown();
//        }
//        if (t.isAlive()) {
//            ((MultiThreadedHttpConnectionManager) client.getHttpConnectionManager()).shutdown();
//            t.interrupt();
//        }
//        return response.toString();
//	}
//	/**
//	 * 执行一个HTTP GET请求，返回请求响应的HTML
//	 * 
//	 * @param url
//	 *            请求的URL地址
//	 * @param queryString
//	 *            请求的查询参数,可以为null
//	 * @param charset
//	 *            字符集
//	 * @param pretty
//	 *            是否美化
//	 * @return 返回请求响应的HTML
//	 */
//	
//	public  String doGet(String url, String queryString, String charset,boolean pretty) {
//		StringBuffer response = new StringBuffer();
//		HttpClient client = new HttpClient();
//		HttpMethod method = new GetMethod(url);
//		try {
//			if (queryString != null && !queryString.equals(""))
//				// 对get请求参数做了http请求默认编码，好像没有任何问题，汉字编码后，就成为%式样的字符串
//				method.setQueryString(URIUtil.encodeQuery(queryString));
//			client.executeMethod(method);
//			if (method.getStatusCode() == HttpStatus.SC_OK) {
//		 String result = method.getResponseBodyAsString();
//		 System.out.println(result);
//	}else{
//		return "短信接口无法访问!";
//	}
//} catch (IOException e) {
//	e.printStackTrace();
//} finally {
//	method.releaseConnection();
//}
//return response.toString();
//}
	
	 
}
