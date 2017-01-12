package com.logto.mobilesafe.activity;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Vibrator;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.logto.mobilesafe.R;
import com.logto.mobilesafe.utils.AddressUtil;
import com.logto.mobilesafe.utils.AssetsDatabaseManager;
import com.logto.mobilesafe.utils.DatabaseDAO;

public class NumberAddressQueryActivity extends Activity {
	private EditText et_number;
	private TextView tv_address;
	private Button btn_confirm;
	
	private AddressUtil addressUtil;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_number_address_query);
		initView();
		setListeners();
	}

	private void setListeners() {
		btn_confirm.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				//如果没有设置安全号码则不应该进入到下个页面
				//手机防盗的前提：绑定sim卡，并设置安全号码，以上两部未实现则不能实现手机防盗功能
				String number = et_number.getText().toString();
				if(TextUtils.isEmpty(number)){
					shakeView();
					Toast.makeText(NumberAddressQueryActivity.this, "安全号码未设置，请设置安全号码", 0).show();
					return;
				}
				//判断号码是否正确
				Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");  
				Matcher m = p.matcher(number);  
				if(!m.matches()){
					Toast.makeText(NumberAddressQueryActivity.this, "请输入正确的电话号码", 0).show();
					return;
				}

				//查询号码归属地
				String phoneNumber = et_number.getText().toString();
				showDialog(addressUtil.queryAddress(phoneNumber), phoneNumber);
			}
		});
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		//回收资源
		addressUtil.close();
	}

	private void initView() {
		et_number = (EditText) findViewById(R.id.et_number);
		tv_address = (TextView) findViewById(R.id.tv_address);
		btn_confirm = (Button) findViewById(R.id.bt_confirm);
		addressUtil = new AddressUtil(this);
	}

	private void showDialog(Map<String, String> map, String number) {
		if (number.isEmpty()){
			et_number.setText("请输入电话号码");
		}else if (map == null){
			tv_address.setText("没有匹配的数据");
		}else{
			String province = map.get("province");
			String city = map.get("city");
			if (province == null || city == null || province.isEmpty() || city.isEmpty()){
				tv_address.setText("没有匹配的记录");
			}else if ( province.equals(city))
				tv_address.setText(province);
			else
				tv_address.setText(province + "  " + city);
		}
	}


	private void shakeView() {
		Animation shake = AnimationUtils.loadAnimation(this, R.anim.shake);//加载动画资源文件  
		et_number.startAnimation(shake); //给组件播放动画效果  
		Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE); 
		//震动30毫秒  
        vibrator.vibrate(30);
	}

}
