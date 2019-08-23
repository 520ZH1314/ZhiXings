package com.shuben.zhixing.provider;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;



public class PermissionHelper extends SQLiteOpenHelper{
	public static String DB_NAME = "permission_zhjy";//智慧精益权限数据库
	public static int DB_VERSION = 3;
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
	public PermissionHelper(Context context) {
		// TODO Auto-generated constructor stub
		super(context, DB_NAME, null, DB_VERSION);
		 
	}
	public PermissionHelper(Context context, String name, CursorFactory factory,
                    int version) {
		super(context, name, factory, version);
		// TODO Auto-generated constructor stub
		this.context = context;
		DATABASE_PATH ="/data/data/"+context.getPackageName()+"/databases/";

	}	
	private void create (SQLiteDatabase db){
	db.beginTransaction();
	//权限控制表
	db.execSQL("create table permission(i integer primary key autoincrement,appCode varchar ,permissionCode varchar,permissionRemark varchar,parentCode varchar,seq int);");
	db.execSQL("create table standInfo(i integer primary key autoincrement,TenantId varchar ,UserName varchar,UserCode varchar,UserId varchar);");

		db.setTransactionSuccessful();
	db.endTransaction();
	}
	private void drop(SQLiteDatabase db){
		db.beginTransaction();
		db.execSQL("DROP TABLE IF EXISTS permission");
		db.execSQL("DROP TABLE IF EXISTS standInfo");
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
            String databaseFilename = DATABASE_PATH + DB_NAME;
            db = SQLiteDatabase.openDatabase(databaseFilename, null,SQLiteDatabase.OPEN_READWRITE);  
        } catch (SQLiteException e) {  
  
        }  
        if (db != null) {  
            db.close();  
        }  
        return db != null ? true : false;  
    }   
  

}
