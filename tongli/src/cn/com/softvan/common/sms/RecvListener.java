/*
 * 发送短信监听类
 *
 * VERSION  DATE        BY              REASON
 * -------- ----------- --------------- ------------------------------------------
 * 1.00     2011.11.16  Huyunlin           程序・发布
 * -------- ----------- --------------- ------------------------------------------
 * Copyright 2011 softvan System. - All Rights Reserved.
 *
 */
package cn.com.softvan.common.sms;

import java.math.BigInteger;

import org.apache.log4j.Logger;

import cn.com.softvan.common.CommonConstant;

import com.wondertek.esmp.esms.empp.EMPPAnswer;
import com.wondertek.esmp.esms.empp.EMPPChangePassResp;
import com.wondertek.esmp.esms.empp.EMPPDeliver;
import com.wondertek.esmp.esms.empp.EMPPDeliverReport;
import com.wondertek.esmp.esms.empp.EMPPObject;
import com.wondertek.esmp.esms.empp.EMPPRecvListener;
import com.wondertek.esmp.esms.empp.EMPPReqNoticeResp;
import com.wondertek.esmp.esms.empp.EMPPSubmitSM;
import com.wondertek.esmp.esms.empp.EMPPSubmitSMResp;
import com.wondertek.esmp.esms.empp.EMPPSyncAddrBookResp;
import com.wondertek.esmp.esms.empp.EMPPTerminate;
import com.wondertek.esmp.esms.empp.EMPPUnAuthorization;
import com.wondertek.esmp.esms.empp.EmppApi;

/**
 * <p>发送短信监听类</p>
 * <ol>[提供机能]
 * <li>监听发送短消息类的各种动作和返回消息。
 * </ol>
 *
 * @author Huyunlin
 */
public class RecvListener implements EMPPRecvListener {

	private static final transient Logger log = Logger.getLogger("sms"); 
	
	private static final long RECONNECT_TIME = 10 * 1000;
    
    private EmppApi emppApi = null;
    
    private int closedCount = 0;
    
    protected RecvListener(){
    }
    
    public RecvListener(EmppApi emppApia){
        this.emppApi = emppApia;
    }
  
 	 // 处理接收到的消息
    public void onMessage(EMPPObject message) {
		if(message instanceof EMPPUnAuthorization){
			EMPPUnAuthorization unAuth=(EMPPUnAuthorization)message;
		    log.info("客户端无权执行此操作 commandId="+unAuth.getUnAuthCommandId());
		    return;
		}
		
		if(message instanceof EMPPSubmitSMResp){
			EMPPSubmitSMResp resp=(EMPPSubmitSMResp)message;
			log.info("收到sumbitResp:");
			byte[] msgId=fiterBinaryZero(resp.getMsgId());
			
			log.info("msgId="+new BigInteger(msgId));
			log.info("result="+resp.getResult());
			return;
		}
		
		if(message instanceof EMPPDeliver){
			EMPPDeliver deliver = (EMPPDeliver)message;
			if(deliver.getRegister()==EMPPSubmitSM.EMPP_STATUSREPORT_TRUE){
				//收到状态报告
				EMPPDeliverReport report=deliver.getDeliverReport();
				log.info("收到状态报告:");
				byte[] msgId=fiterBinaryZero(report.getMsgId());
			    
				String stat = report.getStat();
				if ("DELIVRD".equals(stat)) {
					log.info("msgId="+new BigInteger(msgId));
				    log.info("status="+report.getStat());
				    log.info("mobile="+report.getDstAddr());
				} else {
					log.error("msgId="+new BigInteger(msgId));
				    log.error("status="+report.getStat());
				    log.error("mobile="+report.getDstAddr());
				}
			    
			}else{
				//收到手机回复
				log.info("收到"+deliver.getSrcTermId()+"发送的短信");
				log.info("短信内容为："+deliver.getMsgContent());
			}
		    return;
		}
		
		if(message instanceof EMPPSyncAddrBookResp){
			EMPPSyncAddrBookResp resp=(EMPPSyncAddrBookResp)message;
			if(resp.getResult()!=EMPPSyncAddrBookResp.RESULT_OK)
				log.info("同步通讯录失败");
			else{
				log.info("收到服务器发送的通讯录信息");
				log.info("通讯录类型为："+resp.getAddrBookType());
				log.info(resp.getAddrBook());
			}
		}
		
		if(message instanceof EMPPChangePassResp){
			EMPPChangePassResp resp=(EMPPChangePassResp)message;
			if(resp.getResult()==EMPPChangePassResp.RESULT_VALIDATE_ERROR)
				log.info("更改密码：验证失败");
			if(resp.getResult()==EMPPChangePassResp.RESULT_OK) {
				log.info("更改密码成功,新密码为："+resp.getPassword());
				emppApi.setPassword(resp.getPassword());
			}
			return;    
		} 
		
		if(message instanceof EMPPReqNoticeResp){
			EMPPReqNoticeResp response=(EMPPReqNoticeResp)message;
			if(response.getResult()!=EMPPReqNoticeResp.RESULT_OK) {
				log.info("查询运营商发布信息失败");
			} else {
				log.info("收到运营商发布的信息");
				log.info(response.getNotice());
			}
			return;
		}
  		 
		if(message instanceof EMPPAnswer){
			log.info("收到企业疑问解答");
			EMPPAnswer  answer=(EMPPAnswer)message;
			log.info(answer.getAnswer()); 
		}
		
  		log.info(message);
  	    
    }
    
 	 //处理连接断掉事件
    public void OnClosed(Object object) {
        // 该连接是被服务器主动断掉，不需要重连
        if(object instanceof EMPPTerminate){
        	log.info("收到服务器发送的Terminate消息，连接终止");
            return;
        }
        //这里注意要将emppApi做为参数传入构造函数
        RecvListener listener = new RecvListener(emppApi);
        log.info("连接断掉次数："+(++closedCount));
        for(int i = 1;!emppApi.isConnected();i++){
            try {
            	log.info("重连次数:"+i);
                Thread.sleep(RECONNECT_TIME);
               // System.out.println("emppApi="+emppApi);
               // System.out.println("listener="+listener);
                emppApi.reConnect(listener);
            }catch (Exception e) {
                //e.printStackTrace();
            }
        }
        log.info("重连成功");
    }
 
 	//处理错误事件
    public void OnError(Exception ex) {
    	log.error(CommonConstant.LOG_ERROR_TITLE, ex);
    }
    
    private static byte[] fiterBinaryZero(byte[] bytes) {
        byte[] returnBytes = new byte[8];
        for (int i = 0; i < 8; i++) {
            returnBytes[i] = bytes[i];
        }
        return returnBytes;
    }
}
