/*
 * 发送短信BATCH类
 *
 * VERSION  DATE        BY              REASON
 * -------- ----------- --------------- ------------------------------------------
 * 1.00     2011.11.16  Huyunlin           程序・发布
 * -------- ----------- --------------- ------------------------------------------
 * Copyright 2011 softvanSystem. - All Rights Reserved.
 *
 */

package cn.com.softvan.thread;
import java.util.List;

import org.apache.log4j.Logger;

import cn.com.softvan.common.CommonConstant;
import cn.com.softvan.common.sms.SmsConstant;
import cn.com.softvan.common.sms.SmsInfo;
import cn.com.softvan.common.sms.SmsSender;
import cn.com.softvan.common.sms.SmsSenderHelper;



/**
 * <p>发送短信BATCH</p>
 * <ol>[提供机能]
 * <li>定时从数据库取得短消息，执行发送动作。
 * </ol>
 *
 * @author Huyunlin
 */
public class SmsAutoSendBatch implements Runnable{

	private static final transient Logger log = Logger.getLogger("sms"); 
	/**
	 * init
	 */
	public SmsAutoSendBatch(){
		log.info("短信扫描程序启动");
	}
//	public static void main(String args[]) {
//
//		Thread thread = new Thread(new Runnable() {
			public void run() {
				
				SmsSenderHelper helper = new SmsSenderHelper();
				List<SmsInfo> smsList =null;
				while(true) {
					// 取得短信信息--------------------------//
					try {
						helper.setSmsSender(new SmsSender());
						smsList = helper.queryNoSendInfo();
//						System.out.println(smsList.size());
						if (smsList != null && !smsList.isEmpty()) {
							SmsInfo info = null;
							for (int i = 0; i < smsList.size(); i++) {
								info = smsList.get(i);
								info.copyValue();
								boolean result = helper.synSend(info);
								if(!result) {
									log.error("短信编号【"+ info.getSms_id() +"】发送失败");
								}
								
								// 更新短信的状态
								boolean dbResult = helper.changeStatus(info, result);
								if(!dbResult) {
									log.error("短信编号【"+ info.getSms_id() +"】状态更新失败");
								}
							}
						}
						
						Thread.currentThread().sleep(SmsConstant.SMS_TIMER_COUNT * 1000);
						
					} catch (Exception e) {
						log.error(CommonConstant.LOG_ERROR_TITLE, e);
					}
				}
			}
//		});
//		thread.setDaemon(true);//守护线程	
//		thread.setName("SMS_THREAD");		
//		thread.start();
//	}
	
}