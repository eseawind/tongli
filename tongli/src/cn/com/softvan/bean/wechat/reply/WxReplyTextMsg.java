package cn.com.softvan.bean.wechat.reply;

import org.jdom.Document;

public class WxReplyTextMsg extends WxReplyMsg {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1289441151764222007L;
	private String content;
	public WxReplyTextMsg(String content) {
		setMsgType("text");
		this.content = content;
	}
	public WxReplyTextMsg(WxReplyMsg msg,String content) {
		super(msg);
		setMsgType("text");
		this.content = content;
	}

	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
	@Override
	public Document toDocument() {
		Document doc = super.toDocument();
		createElement(doc.getRootElement(), "Content", getContent());
		return doc;
	}
}
