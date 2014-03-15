/*
 * 发送短信接口类
 *
 * VERSION  DATE        BY              REASON
 * -------- ----------- --------------- ------------------------------------------
 * 1.00     2011.11.28  Huyunlin           程序・发布
 * -------- ----------- --------------- ------------------------------------------
 * Copyright 2011 softvan System. - All Rights Reserved.
 *
 */

package cn.com.softvan.common.sms;

/**
 * <p>发送短信接口</p>
 *
 * @author Huyunlin
 */
public interface ISmsSender {
	
    /**
     * <p>发送短信</p>
     * <ol>[功能概要]
     * <div>发送短信</div>
     * </ol>
     * @return 发送结果
     */
	public boolean send(SmsInfo smsInfo);
}
