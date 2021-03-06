package cn.com.softvan.common.wechat;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

import cn.com.softvan.bean.wechat.receive.WxRecvMsg;
import cn.com.softvan.bean.wechat.reply.WxReplyMsg;
/**
 * 
 * @author wuxiaogang 
 *
 */
public final class WxMsgKit {
	
	private static final Map<String, WxRecvMsgParser> recvParserMap = new HashMap<String, WxRecvMsgParser>();
	
	static {
		// 文本消息解析程序
		recvParserMap.put("text", new WxRecvTextMsgParser());
		// 链接消息解析程序
		recvParserMap.put("link", new WxRecvLinkMsgParser());
		// 地址消息解析程序
		recvParserMap.put("location", new WxRecvGeoMsgParser());
		// 图片消息解析程序
		recvParserMap.put("image", new WxRecvPicMsgParser());
		// 事件消息解析程序
		recvParserMap.put("event", new WxRecvEventMsgParser());
		// 语音消息解析程序
		recvParserMap.put("voice", new WxRecvVoiceParser());
		// 视频消息解析程序
		recvParserMap.put("video", new WxRecvVideoParser());
	}
	
	public static WxRecvMsg parse(InputStream in) throws JDOMException, IOException {
		Document dom = new SAXBuilder().build(in);
		Element msgType = dom.getRootElement().getChild("MsgType");
		if(null != msgType) {
			String txt = msgType.getText().toLowerCase();
			WxRecvMsgParser parser = recvParserMap.get(txt);
			if(null != parser) {
				return parser.parser(dom);
			} else {
				System.out.println(txt);
			}
		}
		return null;
	}
	
	public static Document parse(WxReplyMsg msg) throws JDOMException {
		return msg.toDocument();
	}
}
