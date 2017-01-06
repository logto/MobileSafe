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
		//1.�õ�֮ǰ��sim����Ϣ
		sp = context.getSharedPreferences("config",context.MODE_PRIVATE);
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
	            smsManager.sendTextMessage(sim_current, null, text, null, null);    
	        }  
		}
	}
}
