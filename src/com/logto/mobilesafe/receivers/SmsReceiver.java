package com.logto.mobilesafe.receivers;

import com.logto.mobilesafe.R;
import com.logto.mobilesafe.service.GPSService;

import com.logto.mobilesafe.activity.LockScreenActivity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.text.TextUtils;

public class SmsReceiver extends BroadcastReceiver {
	private SharedPreferences sp;
	@Override
	public void onReceive(Context context, Intent intent) {
		sp = context.getSharedPreferences("config", context.MODE_PRIVATE);
		Object [] pdus = (Object[]) intent.getExtras().get("pdus");
		for(Object pdu: pdus){
			SmsMessage smsMessage = SmsMessage.createFromPdu((byte[])pdu);
			//得到发送者
			String sender = smsMessage.getOriginatingAddress();//模拟器上会显示15555215556
			String safeNumber = sp.getString("SAFE_NUMBER", "");//5556
			//得到内容
			String body = smsMessage.getMessageBody();
			//模拟器上需要对安全号码进行操作（在真机上并不需要）
			if(sender.contains(safeNumber)){
				if("#location#".equals(body)){
					//拿到手机的GPS位置
					System.out.println("得到手机的GPS位置");
					abortBroadcast();//拦截短信--->放置手机盗用者接受都此条短信
					//启动获得位置信息的服务
					Intent locationintent = new Intent(context,GPSService.class);
					//启动服务
					context.startService(locationintent);
					//拿到location
					String location = sp.getString("LOCATION", "");
					if(TextUtils.isEmpty(location)){
						SmsManager.getDefault().sendTextMessage(sender, null, "getting location......", null, null);
					}else {
						SmsManager.getDefault().sendTextMessage(sender, null, location, null, null);
					}
				}else if("#alarm#".equals(body)){
					//播放报警音乐
					System.out.println("播放报警音乐");
					abortBroadcast();//拦截短信--->放置手机盗用者接受都此条短信
					//播放铃声
					MediaPlayer media = MediaPlayer.create(context, R.raw.moon);
					media.setVolume(1.0f, 1.0f);//将音量调到最大
					media.start();
				}else if("#wipedata#".equals(body)){
					//清除数据
					System.out.println("清除数据");
					abortBroadcast();//拦截短信--->放置手机盗用者接受都此条短信
				}else if("#lockscreen#".equals(body)){
					//锁屏
					System.out.println("锁屏");
					abortBroadcast();//拦截短信	-->放置手机盗用者接受都此条短信
					Intent lockScreenIntent = new Intent(context,LockScreenActivity.class);
					//必须要添加上以下flag，否者程序将无法运行（不能是setFlag()）
					lockScreenIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					context.startActivity(lockScreenIntent);
				}
			}
		}
	}
}
