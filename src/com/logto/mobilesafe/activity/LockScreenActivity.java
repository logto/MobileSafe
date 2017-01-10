package com.logto.mobilesafe.activity;

import com.logto.mobilesafe.receivers.LockScreenReceiver;

import android.os.Bundle;
import android.app.Activity;
import android.app.admin.DeviceAdminReceiver;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;

public class LockScreenActivity extends Activity {

	private DevicePolicyManager mDevicePolicyManager;
	private ComponentName mComponentName;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// setContentView(R.layout.activity_main);
		//��˵�е�Log.i
		Log.i("--->lock!!", "start lock");
		mDevicePolicyManager = (DevicePolicyManager) getSystemService(Context.DEVICE_POLICY_SERVICE);
		mComponentName = new ComponentName(this, LockScreenReceiver.class);
		// �ж��Ƿ���Ȩ��
		if (mDevicePolicyManager.isAdminActive(mComponentName)) {
			mDevicePolicyManager.lockNow(); 
			mDevicePolicyManager.resetPassword("", 0);//��������󣬽�ʹ�������ֻ��޷�����
			
			/*mDevicePolicyManager.wipeData(0);//���ֻ��ָ���������
			mDevicePolicyManager.wipeData(DevicePolicyManager.WIPE_EXTERNAL_STORAGE);//���sd���е�����
			//���ֻ��������������δ��룬������ֻ����ݱ�ɾ��
			 
*/			// �������ж�����ʹ����android4.2��
			// android.os.Process.killProcess(android.os.Process.myPid());
			// System.exit(0);
			finish();
		} else {
			activeManager();
		}
	}

	private void activeManager() {
		// �����豸��������ȡȨ��
		Intent intent = new Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);
		intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, mComponentName);
		intent.putExtra(DevicePolicyManager.EXTRA_ADD_EXPLANATION, "��˵�е�һ������");
		startActivity(intent);
		finish();
	}




}
