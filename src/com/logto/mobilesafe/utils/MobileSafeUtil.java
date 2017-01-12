package com.logto.mobilesafe.utils;

import java.util.List;

import android.app.ActivityManager;
import android.content.Context;

public class MobileSafeUtil {
	/**
	 * �ж�ĳ�������Ƿ���������
	 * @param mContext ������
	 * @param className ��Ҫ�жϵķ����ȫ����
	 * @return ���������򷵻�true,����ͷ���false
	 */
	public static boolean isServiceRunning(Context mContext,String className){
		boolean isRunning = false;
		ActivityManager mActivityManager = (ActivityManager) mContext.getSystemService(mContext.ACTIVITY_SERVICE);
		List<ActivityManager.RunningServiceInfo> serviceList = mActivityManager.getRunningServices(100);
		if(!(serviceList.size()>0)){
			return false;
		}
		
		for(int i=serviceList.size()-1;i>=0;i--){
			if(serviceList.get(i).service.getClassName().equalsIgnoreCase(className)){
				isRunning = true;
				break;
			}
		}
		return isRunning;
	}
}
