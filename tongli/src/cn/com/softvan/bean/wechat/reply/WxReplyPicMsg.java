package cn.com.softvan.bean.wechat.reply;

import org.jdom.Document;
import org.jdom.Element;

public class WxReplyPicMsg extends WxReplyMsg {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7364736287271813591L;
	/**通过上传多媒体文件，得到的id。*/
	private String MediaId;
	public WxReplyPicMsg(String MediaId) {
		setMsgType("image");
		this.MediaId = MediaId;
	}
	public WxReplyPicMsg(WxReplyMsg msg,String MediaId) {
		super(msg);
		setMsgType("image");
		this.MediaId = MediaId;
	}
	
	@Override
	public Document toDocument() {
		Document doc = super.toDocument();
		Element music = createElement(doc.getRootElement(), "Image", "");
		createElement(music, "MediaId", getMediaId());
		return doc;
	}
	/**
	 * 通过上传多媒体文件，得到的id。取得
	 * @return 通过上传多媒体文件，得到的id。
	 */
	public String getMediaId() {
	    return MediaId;
	}
	/**
	 * 通过上传多媒体文件，得到的id。设定
	 * @param MediaId 通过上传多媒体文件，得到的id。
	 */
	public void setMediaId(String MediaId) {
	    this.MediaId = MediaId;
	}
}
