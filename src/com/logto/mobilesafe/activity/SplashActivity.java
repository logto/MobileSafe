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
 * ����ҳ��
 * 1.��ʾlogo
 * 2.�ж��Ƿ�������
 * 3.�Ƿ�����
 * 4.�жϺϷ���
 * 5.�����Ƿ���sdcard
 * @author Lenovo
 */
public class SplashActivity extends Activity {
	private TextView tv_splash_version;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        initView();
        //���ð汾���ƣ�
        tv_splash_version.setText("version:  "+getVersionName());
        //�������
        checkVersion();
    }
    
    /**
     * �汾����
     * �����Ƿ����°汾������������
     */
    private void checkVersion() {
	   /** ����������
    	*1.���°汾�������� ��
    	*1.1 �����Ի������û�ѡ���Ƿ�����
    	* 1.2:��ѡ����������ֱ�ӽ�����ҳ��
    	* 1.3��ѡ�������������ظ��£��滻��װ������
    	*2.û����ֱ�ӽ�����ҳ��
    	*/
    	//�ڹ����߳��з�����������
    	new Thread(){
    		public void run() {
    			//�������������õ����������µİ汾��Ϣ
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
     * ��ȡ�汾����
     * @return
     */
	private String getVersionName() {
		//�õ��������������õ��嵥�ļ���Ϣ
		PackageManager pm = getPackageManager();
		//��ȡ�嵥�ļ���Ϣ
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
