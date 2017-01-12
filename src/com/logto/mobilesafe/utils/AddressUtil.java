package com.logto.mobilesafe.utils;

import java.util.Map;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class AddressUtil {
	private Context mContext;
	public SQLiteDatabase sqliteDB;
	public DatabaseDAO dao;
	
	public AddressUtil(Context context){
		mContext = context;
		initDB();
	}
	private void initDB() {
		AssetsDatabaseManager.initManager(mContext);
		AssetsDatabaseManager mg = AssetsDatabaseManager.getAssetsDatabaseManager();
		sqliteDB = mg.getDatabase("number_location.zip");
		dao = new DatabaseDAO(sqliteDB);
	}
	
	public Map<String,String> queryAddress(String phoneNumber) {
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
		
		return map;
	}
	
	/**������Դ*/
	public void close(){
		AssetsDatabaseManager.closeAllDatabase();
	}
	
	/**�õ����������е�ǰ��λ���ֻ�ǰ��λ����ȥ����λΪ�������֡�*/
	private String getAreaCodePrefix(String number){
		if (number.charAt(1) == '1' || number.charAt(1) == '2')
			return number.substring(1,3);
		return number.substring(1,4);
	}

	/**�õ������ֻ������ǰ��λ���֡�*/
	private String getMobilePrefix(String number){
		return number.substring(0,3);
	}

	/**�õ����������м���λ���룬�����ж��ֻ���������ء�*/
	private String getCenterNumber(String number){
		return number.substring(3,7);
	}

	/**�жϺ����Ƿ����㿪ͷ*/
	private boolean isZeroStarted(String number){
		if (number == null || number.isEmpty()){
			return false;
		}
		return number.charAt(0) == '0';
	}

	/**�õ�����ĳ���*/
	private int getNumLength(String number){
		if (number == null || number.isEmpty()  )
			return 0;
		return number.length();
	}

}
