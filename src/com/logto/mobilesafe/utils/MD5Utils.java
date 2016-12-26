package com.logto.mobilesafe.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Utils {
	/**
	 * md5加密
	 * @param password 明文
	 * @return 加密后的密文
	 */
	public static String ecoder(String password){
		//md5加密
		//1.信息摘要器
		MessageDigest digest = null;
	 	try {
			digest = MessageDigest.getInstance("md5");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return null;
		}
		//2.变成byte数组
		byte[] bytes = digest.digest(password.getBytes());
		//3.每一个byte和8个而进制位做与运算
		StringBuffer password_md5 = new StringBuffer();
		for(byte b: bytes){
			int number = b&0xff;
			//4.把int类型转换成十六进制
			String numberStr = Integer.toHexString(number);
			//5.不足位的补全
			if(numberStr.length()==1){
				password_md5.append("0");
			}
			password_md5.append(numberStr);
		}
		return password_md5.toString();
	}
}
