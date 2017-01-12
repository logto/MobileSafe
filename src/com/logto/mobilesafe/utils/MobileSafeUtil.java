package com.logto.mobilesafe.utils;

import java.util.List;

import android.app.ActivityManager;
import android.content.Context;

public class MobileSafeUtil {
	/**
	 * 判断某个服务是否正在运行
	 * @param mContext 上下文
	 * @param className 需要判断的服务的全类名
	 * @return 正在运行则返回true,否则就返回false
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
