package com.logto.mobilesafe.receivers;

import android.app.admin.DeviceAdminReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class LockScreenReceiver extends DeviceAdminReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		Log.i("--->DeviceAdminReceiver !!", "����");
		super.onReceive(context, intent);
	}

	@Override
	public void onEnabled(Context context, Intent intent) {
		Log.i("--->DeviceAdminReceiver !!", "����");
		super.onEnabled(context, intent);
	}

	@Override
	public void onDisabled(Context context, Intent intent) {
		Log.i("--->DeviceAdminReceiver !!", "ȡ��");
		super.onDisabled(context, intent);
	}

}