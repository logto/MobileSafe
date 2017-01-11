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
				//如果没有设置安全号码则不应该进入到下个页面
				//手机防盗的前提：绑定sim卡，并设置安全号码，以上两部未实现则不能实现手机防盗功能
				String number = et_number.getText().toString();
				if(TextUtils.isEmpty(number)){
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


	/**得到输入区号中的前三位数字或前四位数字去掉首位为零后的数字。*/
	public String getAreaCodePrefix(String number){
		if (number.charAt(1) == '1' || number.charAt(1) == '2')
			return number.substring(1,3);
		return number.substring(1,4);
	}

	/**得到输入手机号码的前三位数字。*/
	public String getMobilePrefix(String number){
		return number.substring(0,3);
	}

	/**得到输入号码的中间四位号码，用来判断手机号码归属地。*/
	public String getCenterNumber(String number){
		return number.substring(3,7);
	}

	/**判断号码是否以零开头*/
	public boolean isZeroStarted(String number){
		if (number == null || number.isEmpty()){
			return false;
		}
		return number.charAt(0) == '0';
	}

	/**得到号码的长度*/
	public int getNumLength(String number){
		if (number == null || number.isEmpty()  )
			return 0;
		return number.length();
	}

}
