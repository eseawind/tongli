package cn.com.softvan.bean.wechar.send;

import cn.com.softvan.bean.BaseBean;

/**
 * 群发消息返回json对象
 *
 * @author Kone
 */
public class MsgJsonBean  extends BaseBean{
    /**
	 * 
	 */
	private static final long serialVersionUID = 5743322993378441314L;
	private int ret;
    private String msg;

    public int getRet() {
        return ret;
    }

    public void setRet(int ret) {
        this.ret = ret;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

}
