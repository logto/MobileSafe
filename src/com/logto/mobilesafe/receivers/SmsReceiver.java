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
					//�������λ����Ϣ�ķ���
					Intent locationintent = new Intent(context,GPSService.class);
					//��������
					context.startService(locationintent);
					//�õ�location
					String location = sp.getString("LOCATION", "");
					if(TextUtils.isEmpty(location)){
						SmsManager.getDefault().sendTextMessage(sender, null, "getting location......", null, null);
					}else {
						SmsManager.getDefault().sendTextMessage(sender, null, location, null, null);
					}
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
					abortBroadcast();//���ض���	-->�����ֻ������߽��ܶ���������
					Intent lockScreenIntent = new Intent(context,LockScreenActivity.class);
					//����Ҫ���������flag�����߳����޷����У�������setFlag()��
					lockScreenIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					context.startActivity(lockScreenIntent);
				}
			}
		}
	}
}
