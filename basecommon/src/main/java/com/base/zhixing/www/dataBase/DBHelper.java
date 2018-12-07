package com.base.zhixing.www.dataBase;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import com.base.zhixing.www.common.Common;


public class DBHelper extends SQLiteOpenHelper{
	/**
	 * 数据库操作
	 * @param context
	 * @param name
	 * @param factory
	 * @param versionê
	 */
	private String DATABASE_PATH ; 
	@SuppressWarnings("unused")
	private Context context;
	public DBHelper(Context context) {
		// TODO Auto-generated constructor stub
		super(context, Common.DB_NAME, null, Common.DB_VERSION);
		 
	}
	public DBHelper(Context context, String name, CursorFactory factory,
                    int version) {
		super(context, name, factory, version);
		// TODO Auto-generated constructor stub
		this.context = context;
		DATABASE_PATH ="/data/data/"+context.getPackageName()+"/databases/";

	}	
	private void create (SQLiteDatabase db){
	db.beginTransaction();
	//不良原因和不良现象
	db.execSQL("create table qc_reason(i integer primary key autoincrement,id varchar,excepName varchar,type int);");
	//组织架构
		db.execSQL("create table organize(i integer primary key autoincrement,id varchar,code varchar,name varchar,parent varchar);");
		//人员信息表
		db.execSQL("create table persons(i integer primary key autoincrement,userId varchar,userCode varchar,userName varchar,sex varchar,phone varchar,cardNo varchar,photo varchar,bpAddress varchar,email varchar,head varchar,oId varchar,oCode varchar,oName varchar,parent varchar,pys varchar,bys varchar);");


		db.setTransactionSuccessful();
	db.endTransaction();
	}
	private void drop(SQLiteDatabase db){
		db.beginTransaction();
		db.execSQL("DROP TABLE IF EXISTS organize");
		db.execSQL("DROP TABLE IF EXISTS persons");
		db.execSQL("DROP TABLE IF EXISTS qc_reason");
		db.setTransactionSuccessful();
		db.endTransaction();
		//此处是删除数据表，在实际的业务中一般是需要数据备份的
	}
	@Override
	public void onCreate(SQLiteDatabase db) {
		create(db);
	}
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

		drop(db);
		create(db);
	}
	  
    
    public boolean checkDataBase() {  
        SQLiteDatabase db = null;  
        try {  
            String databaseFilename = DATABASE_PATH + Common.DB_NAME;  
            db = SQLiteDatabase.openDatabase(databaseFilename, null,SQLiteDatabase.OPEN_READWRITE);  
        } catch (SQLiteException e) {  
  
        }  
        if (db != null) {  
            db.close();  
        }  
        return db != null ? true : false;  
    }   
  

}
