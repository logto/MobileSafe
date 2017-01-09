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
			//�õ�������
			String sender = smsMessage.getOriginatingAddress();//ģ�����ϻ���ʾ15555215556
			String safeNumber = sp.getString("SAFE_NUMBER", "");//5556
			//�õ�����
			String body = smsMessage.getMessageBody();
			//ģ��������Ҫ�԰�ȫ������в�����������ϲ�����Ҫ��
			if(sender.contains(safeNumber)){
				if("#location#".equals(body)){
					//�õ��ֻ���GPSλ��
					System.out.println("�õ��ֻ���GPSλ��");
					abortBroadcast();//���ض���--->�����ֻ������߽��ܶ���������
					getLoacation(context);
				}else if("#alarm#".equals(body)){
					//���ű�������
					System.out.println("���ű�������");
					abortBroadcast();//���ض���--->�����ֻ������߽��ܶ���������
					//��������
					MediaPlayer media = MediaPlayer.create(context, R.raw.moon);
					media.setVolume(1.0f, 1.0f);//�������������
					media.start();
				}else if("#wipedata#".equals(body)){
					//�������
					System.out.println("�������");
					abortBroadcast();//���ض���--->�����ֻ������߽��ܶ���������
				}else if("#lockscreen#".equals(body)){
					//����
					System.out.println("����");
					abortBroadcast();//���ض���--->�����ֻ������߽��ܶ���������
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
				System.out.println("����0�� "+ latitude + "--" + longitude);
			}  
			System.out.println("����1�� "+ latitude + "--" + longitude);
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

		// λ�÷����ı�����  
		public void onLocationChanged(Location location1) {  
			location = location1;
			updateWithNewLocation(location1); 
		}  
		// provider ���û��رպ����  
		public void onProviderDisabled(String provider) {  
			updateWithNewLocation(null);  
		}  

		// provider ���û����������  
		public void onProviderEnabled(String provider) {          

		}  

		// provider ״̬�仯ʱ����  
		public void onStatusChanged(String provider, int status,Bundle extras) {  
		}  
	};  
	private void getProvider() {  
		// TODO Auto-generated method stub  
		// ����λ�ò�ѯ����  
		Criteria criteria = new Criteria();  
		// ��ѯ���ȣ���  
		criteria.setAccuracy(Criteria.ACCURACY_FINE);  
		// �Ƿ��ѯ��������  
		criteria.setAltitudeRequired(false);  
		// �Ƿ��ѯ��λ�� : ��  
		criteria.setBearingRequired(false);  
		// �Ƿ������ѣ���  
		criteria.setCostAllowed(true);  
		// ����Ҫ�󣺵�  
		criteria.setPowerRequirement(Criteria.POWER_LOW);  
		// ��������ʵķ��������� provider ���� 2 ������Ϊ true ˵�� , ���ֻ��һ�� provider ����Ч�� , �򷵻ص�ǰ  
		// provider  
		provider = locationManager.getBestProvider(criteria, true);  
	}
}
