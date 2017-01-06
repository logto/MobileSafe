package com.logto.mobilesafe.receivers;

import java.util.List;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
/**
 * 监听开机广播
 * @author Lenovo
 *
 */
public class BootCompleteReceiver extends BroadcastReceiver {
	private SharedPreferences sp;
	@Override
	public void onReceive(Context context, Intent intent) {
		//1.得到之前的sim卡信息
		sp = context.getSharedPreferences("config",context.MODE_PRIVATE);
		String sim_sp = sp.getString("SIM", null);
		//2.得到当前手机的sim卡信息
		TelephonyManager tm = (TelephonyManager) context.getSystemService(context.TELEPHONY_SERVICE);
		String sim_current = tm.getSimSerialNumber();
		//3.比较两者sim信息是否一致
		//4.如果不一致，则发短信给安全号码
		if(!TextUtils.isEmpty(sim_current)||sim_current.equalsIgnoreCase(sim_sp)){
			//获取短信管理器   
	        android.telephony.SmsManager smsManager = android.telephony.SmsManager.getDefault();  
	        //拆分短信内容（手机短信长度限制）    
	        List<String> divideContents = smsManager.divideMessage("你的手机或许已丢失，请密切关注号码"+sim_current);   
	        for (String text : divideContents) {    
	            smsManager.sendTextMessage(sim_current, null, text, null, null);    
	        }  
		}
	}
}
