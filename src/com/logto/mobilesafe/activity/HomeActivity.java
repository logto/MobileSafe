package com.logto.mobilesafe.activity;


import com.logto.mobilesafe.R;

import android.app.Activity;
import android.location.GpsStatus.NmeaListener;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
/**
 * ��ҳ��
 * @author logto
 */
public class HomeActivity extends Activity {
	private GridView gv_home;
	private static final String [] names = {"�ֻ���ʿ","ͨѶ��ʿ","Ӧ�ù���","���̹���","����ͳ��","�ֻ�ɱ��","��������","�߼�����","��������"};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);
		initView();
	}
	/**
	 * ��ʼ���ؼ�
	 */
	private void initView() {
		gv_home = (GridView) findViewById(R.id.gv_home);
	}

	
	/**
	 * ������
	 */
	private class HomeAdapter extends BaseAdapter {
		@Override
		public int getCount() {
			return names.length;
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			return null;
		}
		
	}
}
