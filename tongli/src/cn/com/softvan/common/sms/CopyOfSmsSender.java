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

import java.io.IOException;
import java.util.Arrays;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.util.URIUtil;
import org.apache.log4j.Logger;

import cn.com.softvan.common.CommonConstant;
import cn.com.softvan.common.Resources;
import cn.com.softvan.common.Validator;

import com.wondertek.esmp.esms.empp.EMPPConnectResp;
import com.wondertek.esmp.esms.empp.EMPPData;
import com.wondertek.esmp.esms.empp.EMPPObject;
import com.wondertek.esmp.esms.empp.EMPPShortMsg;
import com.wondertek.esmp.esms.empp.EMPPSubmitSM;
import com.wondertek.esmp.esms.empp.EmppApi;


/**
 * <p>发送短信</p>
 * <ol>[提供机能]
 * <li>利用中国移动的短信网关发送短消息。
 * </ol>
 *
 * @author Huyunlin
 */
public class CopyOfSmsSender implements ISmsSender {

	private static final transient Logger log = Logger.getLogger("sms"); 
	
//	/** 短信API */
//	private static EmppApi emppApi = new EmppApi();
//	/** 监听器 */
//	private static RecvListener listener = new RecvListener(emppApi);
	//init
	public CopyOfSmsSender(){
	}
//    /**
//     * <p>初始化发送服务器</p>
//     * <ol>[功能概要]
//     * <div>初始化发送服务器</div>
//     * </ol>
//     * @return 发送结果
//     */
//	public boolean init() {
//		
//		if (emppApi == null) {
//			emppApi = new EmppApi();
//			listener = new RecvListener(emppApi);
//		}
//
//		try {
//			//建立同服务器的连接
//			EMPPConnectResp response = emppApi.connect(Resources.getData("sms.SMS_HOST"), 
//					Integer.parseInt(Resources.getData("sms.SMS_PORT")), 
//					Resources.getData("sms.SMS_ACCOUNT_ID"), 
//					Resources.getData("sms.SMS_PASSWORD"), 
//					listener);
//			
//			//log.info(response);
//			if (response == null) {
//				log.error("连接ESMP服务器超时");
//				return false;
//			}
//			if (!emppApi.isConnected()) {
//				log.error("连接ESMP服务器失败:响应包状态位=" + response.getStatus());
//				return false;
//			}
////			log.info("连接ESMP服务器成功");
//		} catch (Exception ex) {
//			log.error(CommonConstant.LOG_ERROR_TITLE, ex);
//			return false;
//		}
//		
//		return true;
//	}

//    /**
//     * <p>发送短信</p>
//     * <ol>[功能概要]
//     * <div>发送短信</div>
//     * </ol>
//     * @param smsInfo 短消息对象
//     * @return 发送结果
//     */
//	public boolean send1(SmsInfo smsInfo) {
//		
//		// 没有初始化
//		if (emppApi == null || !emppApi.isConnected()) {
//			//log.error("尚未与ESMP服务器建立连接");
//			if (!init()) {
//				return false;
//			}
//			
//		// 是否有权利提交
//		} else if (!emppApi.isSubmitable()) {
//        	log.error("登录帐号没有权利发送短信 ");
//        	return false;
//		}
//		
//		// 设定源号码
//		if (Validator.isNullEmpty(smsInfo.getSrcTermId())) {
//			smsInfo.setSrcTermId(Resources.getData("sms.SMS_ACCOUNT_ID"));
//		}
//
//		// 检查是否长短信
//		if (smsInfo.getBody().length() > SmsConstant.SMS_LONG_CHAR) {
//			sendLongMsg(smsInfo);
//		} else {
//			sendShortMsg(smsInfo);
//		}
//
//		return true;
//	}
	
	
//	
//	 /**
//     * <p>哈朵企业短信平台发送短信</p>
//     * <ol>[功能概要]
//     * <div>发送短信</div>
//     * </ol>
//     * @param smsInfo 短消息对象
//     * @return 发送结果
//     */
//	public boolean send(SmsInfo smsInfo) {
//		String  host=Resources.getData("sms.SMS_HOST");
//		String account=Resources.getData("sms.SMS_ACCOUNT_ID");
//		String psw=Resources.getData("sms.SMS_PASSWORD");
//		String content=smsInfo.getBody();
//		String phonestr=smsInfo.getSMS_DST_ID();//收短信人号码
//		/*for (int i = 0; i < count; i++) {
//			phonestr=phonestr+dstId[i];
//			if(i<count-1){
//				
//				phonestr=phonestr+";";
//			}
//		}*/
//		String x = new SmsSender().doGet(host, "Sd_UserName="+account+"&Sd_UserName="+psw+"&Sd_Phones="+phonestr+"&Sd_MsgContent="+content,
//					"utf-8", false); 
//
//		return true;
//	}
	/**
     * <p>哈朵企业短信平台发送短信</p>
     * <ol>[功能概要]
     * <div>发送短信</div>
     * </ol>
     * @param smsInfo 短消息对象
     * @return 发送结果
     */
	public boolean send(SmsInfo smsInfo) {
		String  host=Resources.getData("sms.SMS_HOST");
		String account=Resources.getData("sms.SMS_ACCOUNT_ID");
		String psw=Resources.getData("sms.SMS_PASSWORD");
		String content=smsInfo.getBody();
		String phonestr=smsInfo.getSms_dst_id();//收短信人号码
		/*for (int i = 0; i < count; i++) {
			phonestr=phonestr+dstId[i];
			if(i<count-1){
				
				phonestr=phonestr+";";
			}
		}*/
		String x = new CopyOfSmsSender().doGet(host, "Sd_UserName="+account+"&Sd_UserName="+psw+"&Sd_Phones="+phonestr+"&Sd_MsgContent="+content,
					"utf-8", false); 

		return true;
	}
	
	/**
	 * 执行一个HTTP GET请求，返回请求响应的HTML
	 * 
	 * @param url
	 *            请求的URL地址
	 * @param queryString
	 *            请求的查询参数,可以为null
	 * @param charset
	 *            字符集
	 * @param pretty
	 *            是否美化
	 * @return 返回请求响应的HTML
	 */
	
	public  String doGet(String url, String queryString, String charset,boolean pretty) {
		StringBuffer response = new StringBuffer();
		HttpClient client = new HttpClient();
		HttpMethod method = new GetMethod(url);
		try {
			if (queryString != null && !queryString.equals(""))
				// 对get请求参数做了http请求默认编码，好像没有任何问题，汉字编码后，就成为%式样的字符串
				method.setQueryString(URIUtil.encodeQuery(queryString));
			client.executeMethod(method);
			if (method.getStatusCode() == HttpStatus.SC_OK) {
		 String result = method.getResponseBodyAsString();
		 System.out.println(result);
	}else{
		return "短信接口无法访问!";
	}
} catch (IOException e) {
	e.printStackTrace();
} finally {
	method.releaseConnection();
}
return response.toString();
}
//	
//    /**
//     * <p>简单方式发送短信，发送长短信</p>
//     * <ol>[功能概要]
//     * <div>发送字符数超过60个的短信</div>
//     * </ol>
//     * @param smsInfo 短消息对象
//     * @return 发送结果
//     */
//	private int sendLongMsg(SmsInfo smsInfo) {
//
//		//log.info("简单方式发送短信");
//
//		try{
//			int count = smsInfo.getDstTermId().size();
//			String[] dstId = smsInfo.getDstTermId().toArray(new String[count]);
//			for (int i = 0; i < count; i = i + SmsConstant.SMS_GROUP_DST_COUNT) {
//				if ((i + SmsConstant.SMS_GROUP_DST_COUNT) >= count) {
//					emppApi.submitMsgAsync(smsInfo.getBody(), 
//							(String[])Arrays.copyOfRange(dstId, i, count), 
//							//SmsConstant.SMS_SERVICE_ID, 
//							smsInfo.getSrcTermId());
//				} else {
//					emppApi.submitMsgAsync(smsInfo.getBody(), 
//							(String[])Arrays.copyOfRange(dstId, i, SmsConstant.SMS_GROUP_DST_COUNT + i), 
//							//SmsConstant.SMS_SERVICE_ID, 
//							smsInfo.getSrcTermId());
//				}
//			}
//		}catch (Exception ex) {
//			log.error(CommonConstant.LOG_ERROR_TITLE, ex);
//			return SmsConstant.SMS_STATUS_ERROR;
//		} 
//		
//		return SmsConstant.SMS_STATUS_OK;
//	}
//	
//    /**
//     * <p>详细设置参数方式发送短信</p>
//     * @param smsInfo 短消息对象
//     * @return 发送结果
//     */
//	private int sendShortMsg(SmsInfo smsInfo) { 
//			
//		//log.info("详细设置参数方式发送短信");
//		
//		// 详细设置短信的各个属性,不支持长短信
//		EMPPSubmitSM msg = (EMPPSubmitSM) EMPPObject.createEMPP(EMPPData.EMPP_SUBMIT);
//		msg.setSrcTermId(smsInfo.getSrcTermId());
//		//msg.setServiceId(SmsConstant.SMS_SERVICE_ID);
//		EMPPShortMsg msgContent = new EMPPShortMsg(EMPPShortMsg.EMPP_MSG_CONTENT_MAXLEN);
//
//		try {
//            msgContent.setMessage(smsInfo.getBody().getBytes(Resources.getData("sms.SMS_DEFAULT_ENCODE")));
//            msg.setShortMessage(msgContent);
//            msg.assignSequenceNumber();
//            
//			// 群发设置
//			int count = smsInfo.getDstTermId().size();
//			for (int i = 0; i < count; i = i + SmsConstant.SMS_GROUP_DST_COUNT) {
//				if ((i + SmsConstant.SMS_GROUP_DST_COUNT) >= count) {
//					msg.setDstTermId(smsInfo.getDstTermId().subList(i, count));
//				} else {
//					msg.setDstTermId(smsInfo.getDstTermId().subList(i, i + SmsConstant.SMS_GROUP_DST_COUNT));
//				}
//
//	            emppApi.submitMsgAsync(msg);
//			}
//
//		} catch (Exception ex) {
//			log.error(CommonConstant.LOG_ERROR_TITLE, ex);
//			return SmsConstant.SMS_STATUS_ERROR;
//		}
//
//		return SmsConstant.SMS_STATUS_OK;
//	}
}