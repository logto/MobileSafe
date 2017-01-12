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
				//���û�����ð�ȫ������Ӧ�ý��뵽�¸�ҳ��
				//�ֻ�������ǰ�᣺��sim���������ð�ȫ���룬��������δʵ������ʵ���ֻ���������
				String number = et_number.getText().toString();
				if(TextUtils.isEmpty(number)){
					shakeView();
					Toast.makeText(NumberAddressQueryActivity.this, "��ȫ����δ���ã������ð�ȫ����", 0).show();
					return;
				}
				//�жϺ����Ƿ���ȷ
				Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");  
				Matcher m = p.matcher(number);  
				if(!m.matches()){
					Toast.makeText(NumberAddressQueryActivity.this, "��������ȷ�ĵ绰����", 0).show();
					return;
				}

				//��ѯ���������
				String phoneNumber = et_number.getText().toString();
				showDialog(addressUtil.queryAddress(phoneNumber), phoneNumber);
			}
		});
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		//������Դ
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
			et_number.setText("������绰����");
		}else if (map == null){
			tv_address.setText("û��ƥ�������");
		}else{
			String province = map.get("province");
			String city = map.get("city");
			if (province == null || city == null || province.isEmpty() || city.isEmpty()){
				tv_address.setText("û��ƥ��ļ�¼");
			}else if ( province.equals(city))
				tv_address.setText(province);
			else
				tv_address.setText(province + "  " + city);
		}
	}


	private void shakeView() {
		Animation shake = AnimationUtils.loadAnimation(this, R.anim.shake);//���ض�����Դ�ļ�  
		et_number.startAnimation(shake); //��������Ŷ���Ч��  
		Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE); 
		//��30����  
        vibrator.vibrate(30);
	}

}
