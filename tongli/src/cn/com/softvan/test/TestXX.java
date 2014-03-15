package cn.com.softvan.test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class TestXX {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		List<TestBean> beans=new ArrayList<TestBean>();
		for(int i=0;i<5;i++){
			TestBean bean=new TestBean();
			bean.setA("a1");
			bean.setB("b1");
			bean.setC("c1");
			bean.setD("d1");
			bean.setE("e1");
			bean.setNum(10);
			beans.add(bean);
		}
		for(int i=0;i<2;i++){
			TestBean bean=new TestBean();
			bean.setA("a2");
			bean.setB("b2");
			bean.setC("c2");
			bean.setD("d2");
			bean.setE("e2");
			bean.setNum(2);
			beans.add(bean);
		}
		Map<String,TestBean> m=new HashMap<String,TestBean>();
		String t=null;
		TestBean bean2=null;
		for(TestBean bean:beans){
			t=bean.getA()+bean.getB()+bean.getC()+bean.getD()+bean.getE();
			bean2=m.get(t);
			if(bean2!=null){
				bean2.setNum(bean2.getNum()+bean.getNum());
			}else{
				m.put(t, bean);
			}
		}
		
		for(TestBean bean:m.values()){
			System.out.println(bean.getNum());
		}
		
		
		
	}

}
class TestBean{
	String a;
	String b;
	String c;
	String d;
	String e;
	long num;
	/**
	 * a取得
	 * @return a
	 */
	public String getA() {
	    return a;
	}
	/**
	 * a设定
	 * @param a a
	 */
	public void setA(String a) {
	    this.a = a;
	}
	/**
	 * b取得
	 * @return b
	 */
	public String getB() {
	    return b;
	}
	/**
	 * b设定
	 * @param b b
	 */
	public void setB(String b) {
	    this.b = b;
	}
	/**
	 * c取得
	 * @return c
	 */
	public String getC() {
	    return c;
	}
	/**
	 * c设定
	 * @param c c
	 */
	public void setC(String c) {
	    this.c = c;
	}
	/**
	 * d取得
	 * @return d
	 */
	public String getD() {
	    return d;
	}
	/**
	 * d设定
	 * @param d d
	 */
	public void setD(String d) {
	    this.d = d;
	}
	/**
	 * e取得
	 * @return e
	 */
	public String getE() {
	    return e;
	}
	/**
	 * e设定
	 * @param e e
	 */
	public void setE(String e) {
	    this.e = e;
	}
	/**
	 * num取得
	 * @return num
	 */
	public long getNum() {
	    return num;
	}
	/**
	 * num设定
	 * @param num num
	 */
	public void setNum(long num) {
	    this.num = num;
	}
}
