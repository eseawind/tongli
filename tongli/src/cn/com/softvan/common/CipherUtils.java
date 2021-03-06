//
//
//  Generated by StarUML(tm) Java Add-In
//
//  @ Project : ������
//  @ File Name : CipherUtils.java
//  @ Date : 2012-11-08
//  @ Author : 
//
//



package cn.com.softvan.common;


import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.log4j.Logger;

import sun.misc.BASE64Encoder;


public class CipherUtils {
	private static final transient Logger log = Logger.getLogger(CipherUtils.class);
	
	//加密key
	private static String ENC_KEY = "foobar";
	
    /**利用MD5进行加密
     * @param str  待加密的字符串
     * @return  加密后的字符串
     * @throws NoSuchAlgorithmException 
     * @throws NoSuchAlgorithmException  没有这种产生消息摘要的算法
     * @throws UnsupportedEncodingException 
     * @throws UnsupportedEncodingException  
     */
    public static String md5(String str) throws NoSuchAlgorithmException, UnsupportedEncodingException{
        //确定计算方法
        MessageDigest md5=MessageDigest.getInstance("MD5");
        //加密后的字符串
        return new BASE64Encoder().encode(md5.digest(str.getBytes("utf-8")));
    }
    
	/**
	 * @param args
	 * @throws NoSuchPaddingException 
	 * @throws BadPaddingException 
	 * @throws UnsupportedEncodingException 
	 * @throws NoSuchAlgorithmException 
	 * @throws IllegalBlockSizeException 
	 * @throws InvalidKeyException 
	 */
	public static void main(String[] args) throws InvalidKeyException, IllegalBlockSizeException, NoSuchAlgorithmException, UnsupportedEncodingException, BadPaddingException, NoSuchPaddingException {
		String testdata ="";
//		String encdata ="";
//		String decdata ="";
//
//		log.info(testdata);
//		
//		encdata = encrypt(testdata);
//		log.info(encdata);
//		
//		decdata = decrypt(encdata);
		
		log.info("Md5:"+ md5(testdata));

	}

	/**
	 * 加密
	 * @param text 被加密文本
	 * @return 已经加密的文本
	 * @throws javax.crypto.IllegalBlockSizeException
	 * @throws java.security.InvalidKeyException
	 * @throws java.security.NoSuchAlgorithmException
	 * @throws java.io.UnsupportedEncodingException
	 * @throws javax.crypto.BadPaddingException
	 * @throws javax.crypto.NoSuchPaddingException
	 */
	public static String encrypt(String text)
			throws javax.crypto.IllegalBlockSizeException,
			java.security.InvalidKeyException,
			java.security.NoSuchAlgorithmException,
			java.io.UnsupportedEncodingException,
			javax.crypto.BadPaddingException,
			javax.crypto.NoSuchPaddingException {
		// 指定加密key和加密方式
		SecretKeySpec sksSpec = new SecretKeySpec(ENC_KEY.getBytes(), "Blowfish");
		
		// 生成加密解密实例
		Cipher cipher = Cipher.getInstance("BLOWFISH/CBC/PKCS5Padding");
		
		cipher.init(javax.crypto.Cipher.ENCRYPT_MODE, sksSpec);

		// 实施加密
		byte[] encrypted = cipher.doFinal(text.getBytes());

		// 初期化ベクトル取得
		byte[] iv = cipher.getIV();

		StringBuffer ivsb = new StringBuffer();
		for (int i = 0; i < iv.length; i++) {
			ivsb.append(Integer.toHexString((iv[i] >> 4) & 0x0f)); 
			ivsb.append(Integer.toHexString(iv[i] & 0x0f));
		}

		for (int i = 0; i < encrypted.length; i++) {
			ivsb.append(Integer.toHexString((encrypted[i] >> 4) & 0x0f));
			ivsb.append(Integer.toHexString(encrypted[i] & 0x0f));
		}

		return ivsb.toString();
	}

	/**
	 * 解密
	 * @param encrypted_in 被解密字符串
	 * @return 已经解密的字符串
	 * @throws javax.crypto.IllegalBlockSizeException
	 * @throws java.security.InvalidKeyException
	 * @throws java.security.NoSuchAlgorithmException
	 * @throws java.io.UnsupportedEncodingException
	 * @throws javax.crypto.BadPaddingException
	 * @throws javax.crypto.NoSuchPaddingException
	 */
	public static String decrypt(String encrypted_in)
			throws javax.crypto.IllegalBlockSizeException,
			java.security.InvalidKeyException,
			java.security.NoSuchAlgorithmException,
			java.io.UnsupportedEncodingException,
			javax.crypto.BadPaddingException,
			javax.crypto.NoSuchPaddingException {
		if(encrypted_in==null||encrypted_in.length()<16){
			return null;
		}
		String encrypted_iv = encrypted_in.substring(0, 16);
		String encrypted = encrypted_in.substring(16);

		ByteArrayOutputStream baosiv = new ByteArrayOutputStream();
		for (int i = 0; i < encrypted_iv.length(); i += 2) {
			int biv = Integer.parseInt(encrypted_iv.substring(i, i + 2), 16);
			baosiv.write(biv);
		}

		IvParameterSpec dps = new IvParameterSpec(baosiv.toByteArray());

		// 指定加密key和加密方式
		SecretKeySpec sksSpec = new SecretKeySpec(ENC_KEY.getBytes(), "Blowfish");
		
		// 生成加密解密实例
		Cipher cipher = Cipher.getInstance("BLOWFISH/CBC/PKCS5Padding");
		
		try {
			cipher.init(Cipher.DECRYPT_MODE, sksSpec, dps);
		} catch (InvalidAlgorithmParameterException e) {
			// TODO 自動生成された catch ブロック
			log.error(e);
		}

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		for (int i = 0; i < encrypted.length(); i += 2) {
			int b = Integer.parseInt(encrypted.substring(i, i + 2), 16);
			baos.write(b);
		}

		byte[] decrypted = cipher.doFinal(baos.toByteArray());

		return new String(decrypted);
	}
}

