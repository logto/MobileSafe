package com.logto.mobilesafe.utils;

import java.util.HashMap;
import java.util.Map;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class DatabaseDAO{
	private SQLiteDatabase db;
	
	public DatabaseDAO(SQLiteDatabase db){
		this.db = db;
	}
	
	/**��ȡָ�����ŵ�ʡ�ݺ͵�����.*/
	public Map<String,String> queryAeraCode(String number){
		return queryNumber("0", number);
	}

	/**��ȡָ�������ʡ�ݺ͵�������
	 * <code>select city_id from number_0 limit arg1,arg2.</code>
	 * arg1��ʾ�ӵڼ��У��������㿪ʼ����ʼ��arg2��ʾ��ѯ��������.*/
	public Map<String,String> queryNumber(String prefix, String center){
		if (center.isEmpty() || !isTableExists("number_" + prefix))
			return null;
		
		int num = Integer.parseInt(center) - 1;
		String sql1 = "select city_id from number_" + prefix + " limit " + num + ",1";//�ո����� 
		String sql2 = "select province_id from city where _id = (" + sql1 + ")";
		String sql = "select province,city from province,city where _id=("+sql1+")and id=("+sql2+")";
		
		return getCursorResult(sql);
	}

	/**��ѯ���п��ܵĳ��С�*/
	public Cursor getPossibleCities(String city){
		if (city.isEmpty())
			return null;
		String sql = "select _id,city from city where city like '%" + city +"%' and flag = 2";
		return getCursor(sql);
	}
	
	public Cursor getPossibleCountry(String country){
		if (country.isEmpty())
			return null;
		String sql = "select _id,country,country_tw,country_en from country where country like '%" + country + "%'";
		return getCursor(sql);
	}
	
	/**��ѯ�������š�*/
	public Map<String,String> queryCity(String city){
		if (city.isEmpty())
			return null;
		String sql1 = "select _id from city where city ='" + city + "'";
		String sql = "select rowid as rownumber from number_0 where city_id =(" + sql1 + ")";
		return getCursorResult(sql);
	}
	
	/**��ѯ���ҵĴ��š�*/
	public Map<String,String> queryCountry(String country){
		if (country.isEmpty())
			return null;
		String sql1 = "select _id from country where country  ='" + country + "'";
		String sql = "select rowid as rownumber from number_00 where country_id =(" + sql1 + ")";
		return getCursorResult(sql);
	}
	
	/**���ز�ѯ�����*/
	private Map<String, String> getCursorResult(String sql) {
		Cursor cursor = getCursor(sql);
		int col_len = cursor.getColumnCount();
		Map<String, String> map = new HashMap<String, String>();
		
		while (cursor.moveToNext()){
			for (int i = 0; i < col_len; i++){
				String columnName = cursor.getColumnName(i);
				String columnValue = cursor.getString(cursor.getColumnIndex(columnName));
				if (columnValue == null)
					columnValue = "";
				map.put(columnName, columnValue);
			}
		}
		return map;
	}

	private Cursor getCursor(String sql) {
		return  db.rawQuery(sql, null);
	}

	/**�ж�ָ���ı��Ƿ���ڡ�*/
	public boolean isTableExists(String tableName){
		boolean result = false;
		if (tableName == null)
			return false;
		Cursor cursor = null;
		try{
			String sql = "select count(*) as c from sqlite_master where type='table' and " +
					"name = '" + tableName.trim() +"' ";
			cursor = db.rawQuery(sql, null);
			if (cursor.moveToNext()){
				int count = cursor.getInt(0);
				if (count > 0)
					result = true;
			}
		}catch(Exception e){
			
		}
		return result;
	}
	
	/**�ر����ݿ⡣*/
	public void closeDB(){
		if(db != null){
			db = null;
			db.close();
		}
	}
}