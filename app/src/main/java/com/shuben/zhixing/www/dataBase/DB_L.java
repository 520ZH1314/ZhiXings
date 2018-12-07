package com.shuben.zhixing.www.dataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Geyan on 2018/5/26.
 */

public class DB_L {
    private static DB_L mydb;
    private SQLiteDatabase db;

    public DB_L(Context context) {
        DBL dBhelper = new DBL(context,"Input",null,1);
        db = dBhelper.getWritableDatabase();
    }

    public synchronized static DB_L getInstance(Context context){
        if(mydb == null)
            mydb = new DB_L(context);
        return  mydb;
    }

    public void saveInput(String str){
        ContentValues values = new ContentValues();
        values.put("input",str);
        db.insert("Input",null,values);
    }

    public List<String> loadInput(){
        List<String> list = new ArrayList<String>();
        Cursor cursor;
        cursor = db.query("Input",new String[]{"*"},null,null,null,null,null);
        if(cursor.moveToFirst()){
            do{
                list.add(cursor.getString(cursor.getColumnIndex("input")));
            }while (cursor.moveToNext());
            if(cursor != null)
                cursor.close();
        }
        return list;
    }
}
