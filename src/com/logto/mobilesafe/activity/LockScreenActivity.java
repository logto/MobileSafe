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
		//传说中的Log.i
		Log.i("--->lock!!", "start lock");
		mDevicePolicyManager = (DevicePolicyManager) getSystemService(Context.DEVICE_POLICY_SERVICE);
		mComponentName = new ComponentName(this, LockScreenReceiver.class);
		// 判断是否有权限
		if (mDevicePolicyManager.isAdminActive(mComponentName)) {
			mDevicePolicyManager.lockNow(); 
			mDevicePolicyManager.resetPassword("", 0);//设置密码后，将使被盗的手机无法解锁
			
			/*mDevicePolicyManager.wipeData(0);//让手机恢复出厂设置
			mDevicePolicyManager.wipeData(DevicePolicyManager.WIPE_EXTERNAL_STORAGE);//清除sd卡中的数据
			//在手机上运行以上两段代码，会造成手机数据被删除
			 
*/			// 下面两行都不好使，在android4.2上
			// android.os.Process.killProcess(android.os.Process.myPid());
			// System.exit(0);
			finish();
		} else {
			activeManager();
		}
	}

	private void activeManager() {
		// 激活设备管理器获取权限
		Intent intent = new Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);
		intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, mComponentName);
		intent.putExtra(DevicePolicyManager.EXTRA_ADD_EXPLANATION, "传说中的一键锁屏");
		startActivity(intent);
		finish();
	}




}
