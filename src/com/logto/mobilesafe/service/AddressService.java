package com.logto.mobilesafe.service;

import java.util.Map;

import com.logto.mobilesafe.utils.AddressUtil;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.widget.Toast;

public class AddressService extends Service {
	private TelephonyManager mTelephonyManager;//�绰����
	private MyPhonestateListener listener;
	private AddressUtil mAddressUtil;
	@Override
	public IBinder onBind(Intent intent) {
		throw new UnsupportedOperationException("Not yet implemented");
	}
	
	@Override
	public void onCreate() {
		super.onCreate();
		mAddressUtil = new AddressUtil(this);
		listener = new MyPhonestateListener();
		mTelephonyManager = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
		//��������
		mTelephonyManager.listen(listener, PhoneStateListener.LISTEN_CALL_STATE);
	}
	
	private class MyPhonestateListener extends PhoneStateListener {  
		  
        @Override  
        public void onCallStateChanged(int state, String incomingNumber) {  
            super.onCallStateChanged(state, incomingNumber);  
            switch (state) {  
            // �Ҷ��ֻ�ʱ  
        /*    case TelephonyManager.CALL_STATE_IDLE:  
                if (view != null) { // �Ƴ���ӵ�С����  
                    wm.removeView(view);  
                    view = null;  
                }  
                break; */ 
            // �ֻ�����ʱ  
            case TelephonyManager.CALL_STATE_RINGING:  
                //String location = AddressDBDao.getAddress(incomingNumber);  
                // Toast.makeText(PhoneAddressService.this, location,  
                // 1).show(); 
            	Map<String, String> map = mAddressUtil.queryAddress(incomingNumber);
            	if(map==null){
            		Toast.makeText(getApplication(),"��������", 0).show();
            		return;
            	}
            	String province = map.get("province");
    			String city = map.get("city");
                System.out.println(province+"  " + city);  
                Toast.makeText(getApplication(), province+"  " + city, 0).show();
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
	}
	
}
