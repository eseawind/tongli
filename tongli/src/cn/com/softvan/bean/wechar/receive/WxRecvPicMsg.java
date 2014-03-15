package cn.com.softvan.bean.wechar.receive;



public class WxRecvPicMsg extends WxRecvMsg {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6244148466695438814L;
	private String picUrl;

	public WxRecvPicMsg(WxRecvMsg msg,String picUrl) {
		super(msg);
		this.picUrl = picUrl;
	}
	
	public String getPicUrl() {
		return picUrl;
	}

	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}
}
