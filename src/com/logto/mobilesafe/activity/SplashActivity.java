package com.logto.mobilesafe.activity;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;

import com.logto.mobilesafe.R;

import android.os.Bundle;
import android.app.Activity;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.view.Menu;
import android.widget.TextView;

/**
 * 启动页面
 * 1.显示logo
 * 2.判断是否有网络
 * 3.是否升级
 * 4.判断合法性
 * 5.检验是否有sdcard
 * @author Lenovo
 */
public class SplashActivity extends Activity {
	private TextView tv_splash_version;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        initView();
        //设置版本名称：
        tv_splash_version.setText("version:  "+getVersionName());
        //软件升级
        checkVersion();
    }
    
    /**
     * 版本升级
     * 检验是否有新版本，若有则升级
     */
    private void checkVersion() {
	   /** 升级的流程
    	*1.有新版本，则下载 ；
    	*1.1 弹出对话框，让用户选择是否升级
    	* 1.2:不选择升级，则直接进入主页面
    	* 1.3：选择升级，则下载更新，替换安装，启动
    	*2.没有则直接进入主页面
    	*/
    	//在工作线程中发起网络请求
    	new Thread(){
    		public void run() {
    			//发起网络请求，拿到网络上最新的版本信息
    			try {
					URL uri = new URL("http://192.168.1.112:8080/updateversion.html");
					
				} catch (MalformedURLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
    			
    		};
    	}.start();
    	
		
	}


	/**
     * 获取版本名称
     * @return
     */
	private String getVersionName() {
		//拿到包管理器，并拿到清单文件信息
		PackageManager pm = getPackageManager();
		//获取清单文件信息
		try {
			PackageInfo packageInfo = pm.getPackageInfo("com.logto.mobilesafe", 0);
			return packageInfo.versionName;
		} catch (NameNotFoundException e) {
			e.printStackTrace();
			return null;
		}
	}





	private void initView() {
    	tv_splash_version = (TextView) findViewById(R.id.tv_splash_version);
	}


	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.splash, menu);
        return true;
    }
    
}
