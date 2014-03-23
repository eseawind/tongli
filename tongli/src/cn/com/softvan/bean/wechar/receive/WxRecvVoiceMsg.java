package cn.com.softvan.bean.wechar.receive;



public class WxRecvVoiceMsg extends WxRecvMsg {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6244148466695438814L;
	/**语音格式，如amr，speex等*/
	private String Format;
	/**图片消息媒体id，可以调用多媒体文件下载接口拉取数据。*/
	private String MediaId;
	public WxRecvVoiceMsg(WxRecvMsg msg,String MediaId,String Format) {
		super(msg);
		this.Format = Format;
		this.MediaId=MediaId;
	}
	/**
	 * 语音格式，如amr，speex等取得
	 * @return 语音格式，如amr，speex等
	 */
	public String getFormat() {
	    return Format;
	}
	/**
	 * 语音格式，如amr，speex等设定
	 * @param Format 语音格式，如amr，speex等
	 */
	public void setFormat(String Format) {
	    this.Format = Format;
	}
	/**
	 * 图片消息媒体id，可以调用多媒体文件下载接口拉取数据。取得
	 * @return 图片消息媒体id，可以调用多媒体文件下载接口拉取数据。
	 */
	public String getMediaId() {
	    return MediaId;
	}
	/**
	 * 图片消息媒体id，可以调用多媒体文件下载接口拉取数据。设定
	 * @param MediaId 图片消息媒体id，可以调用多媒体文件下载接口拉取数据。
	 */
	public void setMediaId(String MediaId) {
	    this.MediaId = MediaId;
	}
}
