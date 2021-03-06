package cn.com.softvan.common.wechat;

import org.jdom.Element;
import org.jdom.JDOMException;

import cn.com.softvan.bean.wechat.receive.WxRecvMsg;
import cn.com.softvan.bean.wechat.receive.WxRecvVideoMsg;
/**
 * 视频消息解析程序
 * @author wuxiaogang 
 *
 */
public class WxRecvVideoParser extends WxRecvMsgBaseParser {

	@Override
	protected WxRecvVideoMsg parser(Element root, WxRecvMsg msg) throws JDOMException {
		return new WxRecvVideoMsg(msg, getElementText(root, "MediaId"),getElementText(root, "ThumbMediaId"));
	}

}
