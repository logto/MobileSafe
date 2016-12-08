package com.logto.mobilesafe.activity;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.json.JSONException;
import org.json.JSONObject;

import com.logto.mobilesafe.R;
import com.logto.mobilesafe.utils.StreamTool;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.util.Log;
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
	protected static final int ENTER_HOME = 0;//������ҳ��
	protected static final int SHOW_UPDAPTE_DIALOG = 1;//��ʾ�Ի���
	protected static final int URL_ERROR = 2;//URL�쳣
	protected static final int NETWORK_ERROR = 3;//�����쳣
	protected static final int JSON_EROR = 4 ;//JSON�����쳣
	private TextView tv_splash_version;
	private String description;//����������Ϣ
	private String apkurl;//������apk�ĵ�ַ
	private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        initView();
        //������Ϣ
        handleMessage();
        //���ð汾���ƣ�
        tv_splash_version.setText("version:  "+getVersionName());
        //�������
        checkVersion();
    }  
    /**
     * ������Ϣ
     */
    private void handleMessage() { 
		handler = new Handler(){
			public void handleMessage(Message msg) {
				switch (msg.what) {
				case ENTER_HOME:
					enterHome();
					break;
				case SHOW_UPDAPTE_DIALOG:
					Log.e("SHOW_UPDAPTE_DIALOG","SHOW_UPDAPTE_DIALOG");
					break;
				case URL_ERROR:
					Log.e("URL_ERROR","URL_ERROR");
					break;
				case NETWORK_ERROR:
					Log.e("NETWORK_ERROR","NETWORK_ERROR");
					break;
				case JSON_EROR:
					Log.e("JSON_EROR","JSON_EROR");
					break;

				default:
					break;
				}
			}

		};
	}
    
    
    /**
     * ���뵽��ҳ��
     */
    private void enterHome() {
    	Intent intent = new Intent(this, HomeActivity.class);
    	startActivity(intent);
    	//�رյ�����ҳ��
    	finish();
	};
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
    	Log.e("checkVersion", "checkVersion");
    	new Thread(){
			public void run() {
    			//�������������õ����������µİ汾��Ϣ
				Message msg = Message.obtain();
    			try {
    				
					URL url = new URL(getString(R.string.serverurl));
					HttpURLConnection con = (HttpURLConnection) url.openConnection();
					
					
					con.setRequestMethod("GET");//�������󷽷�
					con.setConnectTimeout(4000);//���ó�ʱ��ʱ��
					
					int code = con.getResponseCode();
					if(200 == code){
						Log.e("200", "200");
						//����ɹ�
						//����ת����String����---���õ�json�ַ���
						String result = StreamTool.streamToString(con.getInputStream());
						Log.e("TAG", "result: "+result);
						
						//����Json�õ��汾��Ϣ
						JSONObject obj = new JSONObject(result);
						String version = (String) obj.get("version");
						description = (String) obj.get("description");
						apkurl = (String) obj.get("apkurl");
						
						//�ж��Ƿ���Ҫ����
						if(getVersionName().equals(version)){
							//�����°汾������Ҫ����--->���뵽��ҳ��
							msg.what=ENTER_HOME;
							
						}else {
							//�����Ի�����ʾ�û��Ƿ���Ҫ���� 
							msg.what = SHOW_UPDAPTE_DIALOG;
							handler.sendMessage(msg);
						}
						
					}
					
				} catch (MalformedURLException e) {
					// URL�쳣
					e.printStackTrace();
					msg.what = URL_ERROR;
					handler.sendMessage(msg);
				} catch (IOException e) {
					//�����쳣
					e.printStackTrace();
					msg.what = NETWORK_ERROR;
					handler.sendMessage(msg);
				} catch (JSONException e) {
					//�����쳣
					e.printStackTrace();
					msg.what = JSON_EROR;
					handler.sendMessage(msg);
				} finally{
					Log.e("finally", "finally");
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
	
    
}
