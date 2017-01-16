package com.logto.mobilesafe.test;

import java.util.Random;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.test.AndroidTestCase;

import com.logto.mobilesafe.utils.BlackContactDBOpenHelper;
import com.logto.mobilesafe.utils.BlackContactDao;

public class TestCreateBlactContactDB extends AndroidTestCase{
	public void testCreateDBOphenHeplerDB(){
		BlackContactDBOpenHelper helper = new BlackContactDBOpenHelper(getContext());
		SQLiteDatabase writableDatabase = helper.getWritableDatabase();
		BlackContactDao dao = new BlackContactDao(getContext());
	}

	public void add(){
		BlackContactDao dao = new BlackContactDao(getContext());
		Random random = new Random(2);
		for(int i=0;i<100;i++){
			if(i<10){
				dao.add("1589229380"+i,String.valueOf(random.nextInt(3)));
			}else {
				dao.add("158922938"+i, "1");
			}
		}
	}

	public void delete(){
		BlackContactDao dao = new BlackContactDao(getContext());
		dao.delete("110");
		System.out.println("110");
	}

	public void update(){
		BlackContactDao dao = new BlackContactDao(getContext());
		dao.update("111", "2");

	}

	public void query(){
		BlackContactDao dao = new BlackContactDao(getContext());
		boolean reuslt = dao.query("112");
		assertEquals(true, reuslt);
	}

	public void queryMode(){
		BlackContactDao dao = new BlackContactDao(getContext());
		String reuslt = dao.queryMode("112");
		System.out.println(reuslt);
	}

}
