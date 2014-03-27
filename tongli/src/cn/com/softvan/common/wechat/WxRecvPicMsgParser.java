package cn.com.softvan.common.wechat;

import org.jdom.Element;
import org.jdom.JDOMException;

import cn.com.softvan.bean.wechat.receive.WxRecvMsg;
import cn.com.softvan.bean.wechat.receive.WxRecvPicMsg;
/**
 * 图片消息解析程序
 * @author wuxiaogang 
 *
 */
public class WxRecvPicMsgParser extends WxRecvMsgBaseParser {

	@Override
	protected WxRecvPicMsg parser(Element root, WxRecvMsg msg) throws JDOMException {
		return new WxRecvPicMsg(msg, getElementText(root, "PicUrl"),getElementText(root, "MediaId"));
	}

}
