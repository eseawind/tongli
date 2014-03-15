package cn.com.softvan.common;
 import java.util.Random;

public class RandomUtils {
	 
	private static Random random = new Random();
	
	
   
	 /**
	  * 随机生成用户名
	  * @return
	  */
	 public static String randomUserName() {  
 	    String str = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";  
 	    StringBuffer buf = new StringBuffer(); 
 	    buf.append("weixin");
 	    for (int i = 0; i < 8; i++) {  
 	        int num = random.nextInt(62);  
 	        buf.append(str.charAt(num));  
 	    }  
 	    return buf.toString();  
 	}  
  
	 /**
	  * 随机生成电话号码
	  * @return
	  */
	 public static String randomTel() {  
	 	    String str = "1234567890";  
	 	    StringBuffer buf = new StringBuffer();  
	 	    for (int i = 0; i < 11; i++) {  
	 	        int num = random.nextInt(9);  
	 	        buf.append(str.charAt(num));  
	 	    }  
	 	    System.out.println(buf.toString());
	 	    return buf.toString();  
	 	}  
	  /**
	   * 随机生成身份证
	   * @return
	   */
	 public static String randomIdentity() {  
	 	    String str = "1234567890";  
	 	    StringBuffer buf = new StringBuffer();  
	 	    for (int i = 0; i < 18; i++) {  
	 	        int num = random.nextInt(9);  
	 	        buf.append(str.charAt(num));  
	 	    }  
	 	    System.out.println(buf.toString());
	 	    return buf.toString();  
	 	}  
	  
	 public static void main(String[] args) {
			RandomUtils ran = new RandomUtils();
			randomUserName();
			randomTel();
			randomIdentity();
		}
		
	 

}
