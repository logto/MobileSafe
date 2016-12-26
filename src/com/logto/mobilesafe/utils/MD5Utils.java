package com.logto.mobilesafe.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Utils {
	/**
	 * md5����
	 * @param password ����
	 * @return ���ܺ������
	 */
	public static String ecoder(String password){
		//md5����
		//1.��ϢժҪ��
		MessageDigest digest = null;
	 	try {
			digest = MessageDigest.getInstance("md5");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return null;
		}
		//2.���byte����
		byte[] bytes = digest.digest(password.getBytes());
		//3.ÿһ��byte��8��������λ��������
		StringBuffer password_md5 = new StringBuffer();
		for(byte b: bytes){
			int number = b&0xff;
			//4.��int����ת����ʮ������
			String numberStr = Integer.toHexString(number);
			//5.����λ�Ĳ�ȫ
			if(numberStr.length()==1){
				password_md5.append("0");
			}
			password_md5.append(numberStr);
		}
		return password_md5.toString();
	}
}
