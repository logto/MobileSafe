package com.logto.mobilesafe.activity;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.logto.mobilesafe.R;

public class SelectorContactActivity extends Activity {
	private ListView lvList;
	private SharedPreferences sp;
	private String number_selected;
	private Button btn_confirm;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_selector_contact);
		initView();
		setAdapter();

	}
	private void setAdapter() {
		lvList = (ListView) findViewById(R.id.lv_list);
		final ArrayList<HashMap<String, String>> readContact = readContact();
		// System.out.println(readContact);
		lvList.setAdapter(new SimpleAdapter(this, readContact,
				R.layout.contact_list_item, new String[] { "name", "phone" },
				new int[] { R.id.tv_name, R.id.tv_phone }));

		lvList.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				number_selected = readContact.get(position).get("phone");
			}
		});
	}
	private void initView() {
		number_selected = null;
		btn_confirm = (Button) findViewById(R.id.btn_confirm);
		sp = getSharedPreferences("config", MODE_PRIVATE);
		final Editor editor = sp.edit();

		btn_confirm.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				//保存号码
				editor.putString("SAFE_NUMBER", number_selected);
				editor.commit();
				//关闭当前页面
				finish();
			}
		});
	}
	/**
	 *得到联系人
	 **/
	private ArrayList<HashMap<String, String>> readContact() {
		// 首先,从raw_contacts中读取联系人的id("contact_id")
		// 其次, 根据contact_id从data表中查询出相应的电话号码和联系人名称
		// 然后,根据mimetype来区分哪个是联系人,哪个是电话号码

		Uri rawContactsUri = Uri.parse("content://com.android.contacts/raw_contacts");
		Uri dataUri = Uri.parse("content://com.android.contacts/data");

		ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();

		// 从raw_contacts中读取所有联系人的id("contact_id")
		Cursor rawContactsCursor = getContentResolver().query(rawContactsUri,
				new String[] { "contact_id" }, null, null, null);
		if (rawContactsCursor != null) {
			while (rawContactsCursor.moveToNext()) {
				String contactId = rawContactsCursor.getString(0);
				// System.out.println("得到的contact_id="+contactId);

				// 根据contact_id从data表中查询出相应的电话号码和联系人名称, 实际上查询的是视图view_data
				Cursor dataCursor = getContentResolver().query(dataUri,
						new String[] { "data1", "mimetype" }, "contact_id=?",
						new String[] { contactId }, null);

				if (dataCursor != null) {
					HashMap<String, String> map = new HashMap<String, String>();
					while (dataCursor.moveToNext()) {
						String data1 = dataCursor.getString(0);
						String mimetype = dataCursor.getString(1);
						// System.out.println(contactId + ";" + data1 + ";"
						// + mimetype);
						if ("vnd.android.cursor.item/phone_v2".equals(mimetype)) {//手机号码
							map.put("phone", data1);
						} else if ("vnd.android.cursor.item/name".equals(mimetype)) {//联系人名字
							map.put("name", data1);
						}
					}
					list.add(map);
					dataCursor.close();
				}
			}
			rawContactsCursor.close();
		}
		return list;
	}
}