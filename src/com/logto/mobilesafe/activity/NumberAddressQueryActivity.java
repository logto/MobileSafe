package com.logto.mobilesafe.activity;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.logto.mobilesafe.R;
import com.logto.mobilesafe.utils.AssetsDatabaseManager;
import com.logto.mobilesafe.utils.DatabaseDAO;

public class NumberAddressQueryActivity extends Activity {
	private EditText et_number;
	private TextView tv_address;
	private Button btn_confirm;

	public SQLiteDatabase sqliteDB;
	public DatabaseDAO dao;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_number_address_query);
		initDB();
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
				queryAddress();
			}
		});
	}

	private void initDB() {
		AssetsDatabaseManager.initManager(this);
		AssetsDatabaseManager mg = AssetsDatabaseManager.getAssetsDatabaseManager();
		sqliteDB = mg.getDatabase("number_location.zip");
		dao = new DatabaseDAO(sqliteDB);
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		AssetsDatabaseManager.closeAllDatabase();
	}

	private void initView() {
		et_number = (EditText) findViewById(R.id.et_number);
		tv_address = (TextView) findViewById(R.id.tv_address);
		btn_confirm = (Button) findViewById(R.id.bt_confirm);
	}

	private void queryAddress() {
		String phoneNumber = et_number.getText().toString();
		String prefix, center;
		Map<String,String> map = null;

		if (isZeroStarted(phoneNumber) && getNumLength(phoneNumber) > 2){
			prefix = getAreaCodePrefix(phoneNumber);
			map = dao.queryAeraCode(prefix);

		}else if (!isZeroStarted(phoneNumber) && getNumLength(phoneNumber) > 6){
			prefix = getMobilePrefix(phoneNumber);
			center = getCenterNumber(phoneNumber);
			map = dao.queryNumber(prefix, center);
		}

		showDialog(map, phoneNumber);

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


	/**�õ����������е�ǰ��λ���ֻ�ǰ��λ����ȥ����λΪ�������֡�*/
	public String getAreaCodePrefix(String number){
		if (number.charAt(1) == '1' || number.charAt(1) == '2')
			return number.substring(1,3);
		return number.substring(1,4);
	}

	/**�õ������ֻ������ǰ��λ���֡�*/
	public String getMobilePrefix(String number){
		return number.substring(0,3);
	}

	/**�õ����������м���λ���룬�����ж��ֻ���������ء�*/
	public String getCenterNumber(String number){
		return number.substring(3,7);
	}

	/**�жϺ����Ƿ����㿪ͷ*/
	public boolean isZeroStarted(String number){
		if (number == null || number.isEmpty()){
			return false;
		}
		return number.charAt(0) == '0';
	}

	/**�õ�����ĳ���*/
	public int getNumLength(String number){
		if (number == null || number.isEmpty()  )
			return 0;
		return number.length();
	}

}
