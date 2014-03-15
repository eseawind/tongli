package cn.com.softvan.bean.wechar.send;

import cn.com.softvan.bean.BaseBean;
/**
 * 粉丝数量json对象
 *
 * @author Kone
 */
public class FansCountBean extends BaseBean{
    /**
	 * 
	 */
	private static final long serialVersionUID = -3283789956709090753L;
	private int id;
    private String name;
    private int num;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

}
