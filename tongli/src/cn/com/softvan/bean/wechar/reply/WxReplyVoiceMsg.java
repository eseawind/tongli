package cn.com.softvan.bean.wechar.reply;

import org.jdom.Document;
import org.jdom.Element;

public class WxReplyVoiceMsg extends WxReplyMsg {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**通过上传多媒体文件，得到的id。*/
	private String MediaId;
	public WxReplyVoiceMsg(String MediaId) {
		setMsgType("voice");
		this.MediaId = MediaId;
	}
	public WxReplyVoiceMsg(WxReplyMsg msg,String MediaId) {
		super(msg);
		setMsgType("voice");
		this.MediaId = MediaId;
	}
	
	@Override
	public Document toDocument() {
		Document doc = super.toDocument();
		Element music = createElement(doc.getRootElement(), "Voice", "");
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
