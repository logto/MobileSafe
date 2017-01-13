package com.logto.mobilesafe.service;

import java.util.Map;

import com.logto.mobilesafe.R;
import com.logto.mobilesafe.utils.AddressUtil;
import com.logto.mobilesafe.utils.CustomToastUtil;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.os.SystemClock;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.Toast;

public class AddressService extends Service {
	private TelephonyManager mTelephonyManager;//�绰����
	private MyPhonestateListener listener;
	private AddressUtil mAddressUtil;
	private OutCallReceiver mOutCallReceiver;
	private static final int OUT_CALL = 0;
	private static final int IN_CALL = 0;
	private CustomToastUtil mToastUtil;
	@Override
	public IBinder onBind(Intent intent) {
		throw new UnsupportedOperationException("Not yet implemented");
	}

	@Override
	public void onCreate() {
		super.onCreate();
		mAddressUtil = new AddressUtil(this);
		mToastUtil = new CustomToastUtil(this);
		listener = new MyPhonestateListener();
		mTelephonyManager = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
		//��������
		mTelephonyManager.listen(listener, PhoneStateListener.LISTEN_CALL_STATE);
		//ע��㲥
		IntentFilter filter = new IntentFilter();   
		filter.addAction("android.intent.action.NEW_OUTGOING_CALL");   
		mOutCallReceiver = new OutCallReceiver();    
		registerReceiver(mOutCallReceiver,filter);  
	}

	private class MyPhonestateListener extends PhoneStateListener {  

		@Override  
		public void onCallStateChanged(int state, String incomingNumber) {  
			super.onCallStateChanged(state, incomingNumber);  
			switch (state) {  
			// �Ҷ��ֻ�ʱ  
			case TelephonyManager.CALL_STATE_IDLE: 
				mToastUtil.hideToast(); 
				break;  
			case TelephonyManager.CALL_STATE_OFFHOOK:
				SystemClock.sleep(2000);
				mToastUtil.hideToast(); 
				break; 
			// �ֻ�����ʱ  
			case TelephonyManager.CALL_STATE_RINGING:  
				showToast(incomingNumber,IN_CALL);
				break;  
			} 

		}
	}  

	@Override
	public void onDestroy() {
		super.onDestroy();
		mAddressUtil.close();
		//ȡ������
		mTelephonyManager.listen(listener, PhoneStateListener.LISTEN_NONE);
		listener = null;
		//ȡ���㲥��ע��
		unregisterReceiver(mOutCallReceiver);
		mOutCallReceiver = null;
	}

	private void showToast(String number,int type){
		Map<String, String> map = mAddressUtil.queryAddress(number);
		if(map==null){
			if(type==OUT_CALL){
				mToastUtil.popToast("����ȥ��");
			}else {
				mToastUtil.popToast("��������");
			}
			return;
		}
		String province = map.get("province");
		String city = map.get("city");
		String result = province+"  " + city;
		System.out.println(result);  
		mToastUtil.popToast(result);
	}

	private class OutCallReceiver extends BroadcastReceiver {
		@Override
		public void onReceive(Context context, Intent intent) {
			showToast(getResultData(),OUT_CALL);
		}
	}

}
