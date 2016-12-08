package com.logto.mobilesafe.activity;

import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;

import org.json.JSONException;
import org.json.JSONObject;

import com.logto.mobilesafe.R;
import com.logto.mobilesafe.utils.StreamTool;

import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

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
	protected static final int ENTER_HOME = 0;//进入主页面
	protected static final int SHOW_UPDAPTE_DIALOG = 1;//显示对话框
	protected static final int URL_ERROR = 2;//URL异常
	protected static final int NETWORK_ERROR = 3;//网络异常
	protected static final int JSON_EROR = 4 ;//JSON解析异常
	private TextView tv_splash_version;
	private String description;//升级描述信息
	private String apkurl;//升级的apk的地址
	private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        initView();
        Log.e("ip",getString(R.string.serverurl));
        String ip = getString(R.string.serverurl);
        //处理消息
        handleMessage();
        //设置版本名称：
        tv_splash_version.setText("version:  "+getVersionName());
        //软件升级
        checkVersion();
    }  
    /**
     * 处理消息
     */
    private void handleMessage() { 
		handler = new Handler(){
			public void handleMessage(Message msg) {
				switch (msg.what) {
				case ENTER_HOME:
					enterHome();
					break;
				case SHOW_UPDAPTE_DIALOG:
					showUpdateDialog();
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
    
    
    protected void showUpdateDialog() {
    	AlertDialog dialog = new Builder(this).setTitle("提示").
    			setMessage(description).
    			setNegativeButton("下次再说", null).
    			setPositiveButton("立刻升级", new OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						//一般是下载到sd卡，所以要先判断外部存储SD卡状态是否可用
						if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
							 
							//下载apk--使用aFinal框架下载
							FinalHttp http = new FinalHttp();
							http.download(apkurl,Environment.getExternalStorageDirectory()+"/mobilesafe.apk",new AjaxCallBack<File>() {

								@Override
								public void onFailure(Throwable t, String strMsg) {
									// TODO Auto-generated method stub
									super.onFailure(t, strMsg);
								}

								@Override
								public void onSuccess(File t) {
									// TODO Auto-generated method stub
									super.onSuccess(t);
								}

								@Override
								public AjaxCallBack<File> progress(
										boolean progress, int rate) {
									// TODO Auto-generated method stub
									return super.progress(progress, rate);
								}
								
							});
							
							
							
							
							//替换安装
						}else {
							Toast.makeText(SplashActivity.this, "外部存储卡不可用", 0).show();
						}
					}
				}).create();
    	
    	dialog.show();
	}
	/**
     * 进入到主页面
     */
    private void enterHome() {
    	Intent intent = new Intent(this, HomeActivity.class);
    	startActivity(intent);
    	//关闭掉启动页面
    	finish();
	};
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
    	Log.e("checkVersion", "checkVersion");
    	new Thread(){
			public void run() {
    			//发起网络请求，拿到网络上最新的版本信息
				Message msg = Message.obtain();
    			try {
    				
    				Log.e("TAG", "4000");
					URL url = new URL(getString(R.string.serverurl));
					Log.e("ip",getString(R.string.serverurl));
					HttpURLConnection con = (HttpURLConnection) url.openConnection();
					
					
					con.setRequestMethod("GET");//设置请求方法
					con.setConnectTimeout(4000);//设置超时的时间
					int code = con.getResponseCode();
					if(200 == code){
						Log.e("200", "200");
						//请求成功
						//把流转换成String类型---即拿到json字符串
						String result = StreamTool.streamToString(con.getInputStream());
						Log.e("TAG", "result: "+result);
						
						//解析Json拿到版本信息
						JSONObject obj = new JSONObject(result);
						String version = (String) obj.get("version");
						description = (String) obj.get("description");
						apkurl = (String) obj.get("apkurl");
						
						//判断是否需要升级
						if(getVersionName().equals(version)){
							//是最新版本，不需要升级--->进入到主页面
							msg.what=ENTER_HOME;
							handler.sendMessage(msg);
							
						}else {
							//弹出对话框，提示用户是否需要升级 
//							msg.what = SHOW_UPDAPTE_DIALOG;
							msg.what=ENTER_HOME;
							handler.sendMessage(msg);
						}
						
					}
					
				} catch (MalformedURLException e) {
					// URL异常
					e.printStackTrace();
					msg.what = URL_ERROR;
					handler.sendMessage(msg);
				} catch (IOException e) {
					//网络异常
					e.printStackTrace();
					msg.what = NETWORK_ERROR;
					handler.sendMessage(msg);
				} catch (JSONException e) {
					//解析异常
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
	
    
}
