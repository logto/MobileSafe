package com.logto.mobilesafe.receivers;

import com.logto.mobilesafe.R;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;

public class SmsReceiver extends BroadcastReceiver {
	private double latitude=0.0;  
	private double longitude =0.0;
	private SharedPreferences sp;
	private Location location;
	private LocationManager locationManager;
	private String provider; 
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
					getLoacation(context);
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
					abortBroadcast();//拦截短信--->放置手机盗用者接受都此条短信
				}
			}
		}
	}

	private void getLoacation(Context context) {
		locationManager = (LocationManager)context.getSystemService(Context.LOCATION_SERVICE);
		getProvider();

		if(locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){  
			location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);  
			while(location  == null)  
			{  
				locationManager.requestLocationUpdates("gps", 60000, 1, locationListener);
			}
			if(location != null){  
				latitude = location.getLatitude();  
				longitude = location.getLongitude();  
				System.out.println("坐标0： "+ latitude + "--" + longitude);
			}  
			System.out.println("坐标1： "+ latitude + "--" + longitude);
		}
	}

	private void updateWithNewLocation(Location location2) {  
		// TODO Auto-generated method stub  
		while(location == null){  
			locationManager.requestLocationUpdates(provider, 2000, (float) 0.1, locationListener);  
		}  
		if (location != null) {  
			latitude = ((int)(location.getLatitude()*100000));  
			longitude = (int)(location.getLongitude()*100000);  
			//          changeFormat(latitude,longitude);  
		} else {  
			latitude = 3995076;  
			longitude = 11619675;  
		}  
	}  
	private final LocationListener locationListener = new LocationListener() {  

		// 位置发生改变后调用  
		public void onLocationChanged(Location location1) {  
			location = location1;
			updateWithNewLocation(location1); 
		}  
		// provider 被用户关闭后调用  
		public void onProviderDisabled(String provider) {  
			updateWithNewLocation(null);  
		}  

		// provider 被用户开启后调用  
		public void onProviderEnabled(String provider) {          

		}  

		// provider 状态变化时调用  
		public void onStatusChanged(String provider, int status,Bundle extras) {  
		}  
	};  
	private void getProvider() {  
		// TODO Auto-generated method stub  
		// 构建位置查询条件  
		Criteria criteria = new Criteria();  
		// 查询精度：高  
		criteria.setAccuracy(Criteria.ACCURACY_FINE);  
		// 是否查询海拨：否  
		criteria.setAltitudeRequired(false);  
		// 是否查询方位角 : 否  
		criteria.setBearingRequired(false);  
		// 是否允许付费：是  
		criteria.setCostAllowed(true);  
		// 电量要求：低  
		criteria.setPowerRequirement(Criteria.POWER_LOW);  
		// 返回最合适的符合条件的 provider ，第 2 个参数为 true 说明 , 如果只有一个 provider 是有效的 , 则返回当前  
		// provider  
		provider = locationManager.getBestProvider(criteria, true);  
	}
}
