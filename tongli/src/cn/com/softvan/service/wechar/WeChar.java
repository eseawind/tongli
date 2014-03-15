package cn.com.softvan.service.wechar;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.log4j.Logger;
import org.jdom.Document;
import org.jdom.JDOMException;
import org.jdom.output.XMLOutputter;

import cn.com.softvan.bean.wechar.receive.WxRecvMsg;
import cn.com.softvan.bean.wechar.reply.WxReplyMsg;
import cn.com.softvan.common.wechar.WxMsgKit;

public final class WeChar {
	private static final transient Logger log = Logger.getLogger(WeChar.class);
	public static boolean access(String token,String signature,String timestamp,String nonce) {
		List<String> ss = new ArrayList<String>();
		ss.add(timestamp);
		ss.add(nonce);
		ss.add(token);
		
		Collections.sort(ss);
		
		StringBuilder builder = new StringBuilder();
		for(String s : ss) {
			builder.append(s);
		}
		return signature.equalsIgnoreCase(HashKit.sha1(builder.toString()));
	}
	
	public static WxRecvMsg recv(InputStream in) throws JDOMException, IOException {
		return WxMsgKit.parse(in);
	}
	
	public static void send(WxReplyMsg msg,OutputStream out) throws JDOMException,IOException {
		Document doc = WxMsgKit.parse(msg);
		if(null != doc) {
			new XMLOutputter().output(doc, out);
		} else {
			log.error("发送消息时,解析出dom为空 msg :"+msg);
		}
	}
	
	public static WxReplyMsg builderSendByRecv(WxRecvMsg msg) {
		WxRecvMsg m = new WxRecvMsg(msg);
		String from = m.getFromUser();
		m.setFromUser(m.getToUser());
		m.setToUser(from);
		m.setCreateDt((System.currentTimeMillis())+"");
		return new WxReplyMsg(m);
	}
}
