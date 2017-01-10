package com.logto.mobilesafe.receivers;

import java.util.List;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
/**
 * ���������㲥
 * @author Lenovo
 *
 */
public class BootCompleteReceiver extends BroadcastReceiver {
	private SharedPreferences sp;
	@Override
	public void onReceive(Context context, Intent intent) {
		//�����ֻ������Ƿ��Ѿ�����
		sp = context.getSharedPreferences("config",context.MODE_PRIVATE);
		if(!sp.getBoolean("PROTECTED", false)){
			//û�п�������ֱ�ӷ��� 
			return;
		}
		//1.�õ�֮ǰ��sim����Ϣ
		String sim_sp = sp.getString("SIM", null);
		//2.�õ���ǰ�ֻ���sim����Ϣ
		TelephonyManager tm = (TelephonyManager) context.getSystemService(context.TELEPHONY_SERVICE);
		String sim_current = tm.getSimSerialNumber();
		//3.�Ƚ�����sim��Ϣ�Ƿ�һ��
		//4.�����һ�£��򷢶��Ÿ���ȫ����
		if(!TextUtils.isEmpty(sim_current)||sim_current.equalsIgnoreCase(sim_sp)){
			//��ȡ���Ź�����   
	        android.telephony.SmsManager smsManager = android.telephony.SmsManager.getDefault();  
	        //��ֶ������ݣ��ֻ����ų������ƣ�    
	        List<String> divideContents = smsManager.divideMessage("����ֻ������Ѷ�ʧ�������й�ע����"+sim_current);   
	        for (String text : divideContents) {    
	            smsManager.sendTextMessage(sp.getString("SAFE_NUMBER", "120"), null, text, null, null);    
	        }  
		}
	}
}