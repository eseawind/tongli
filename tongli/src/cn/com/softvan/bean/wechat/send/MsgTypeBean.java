package cn.com.softvan.bean.wechat.send;

/**
 * Created with IntelliJ IDEA.
 * User: Kone
 * Date: 13-5-21
 * Time: 上午12:18
 * To change this template use File | Settings | File Templates.
 */

/**
 * 消息类型
 *
 * @author Kone
 */
public enum MsgTypeBean{
    TEXT(0), VOICE(3), IMAGE(2), VIDEO(4), IMAGE_TEXT(10),
    SINGLE_TEXT(1), SINGLE_VOICE(3), SINGLE_IMAGE(2), SINGLE_VIDEO(4), SINGLE_IMAGE_TEXT(10);
    private int type;

    private MsgTypeBean(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }

}
