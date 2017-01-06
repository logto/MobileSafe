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
				//�������
				editor.putString("SAFE_NUMBER", number_selected);
				editor.commit();
				//�رյ�ǰҳ��
				finish();
			}
		});
	}
	/**
	 *�õ���ϵ��
	 **/
	private ArrayList<HashMap<String, String>> readContact() {
		// ����,��raw_contacts�ж�ȡ��ϵ�˵�id("contact_id")
		// ���, ����contact_id��data���в�ѯ����Ӧ�ĵ绰�������ϵ������
		// Ȼ��,����mimetype�������ĸ�����ϵ��,�ĸ��ǵ绰����

		Uri rawContactsUri = Uri.parse("content://com.android.contacts/raw_contacts");
		Uri dataUri = Uri.parse("content://com.android.contacts/data");

		ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();

		// ��raw_contacts�ж�ȡ������ϵ�˵�id("contact_id")
		Cursor rawContactsCursor = getContentResolver().query(rawContactsUri,
				new String[] { "contact_id" }, null, null, null);
		if (rawContactsCursor != null) {
			while (rawContactsCursor.moveToNext()) {
				String contactId = rawContactsCursor.getString(0);
				// System.out.println("�õ���contact_id="+contactId);

				// ����contact_id��data���в�ѯ����Ӧ�ĵ绰�������ϵ������, ʵ���ϲ�ѯ������ͼview_data
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
						if ("vnd.android.cursor.item/phone_v2".equals(mimetype)) {//�ֻ�����
							map.put("phone", data1);
						} else if ("vnd.android.cursor.item/name".equals(mimetype)) {//��ϵ������
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