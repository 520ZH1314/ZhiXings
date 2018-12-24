package com.zhixing.masslib.dataBase;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.base.zhixing.www.BaseApp;
import com.base.zhixing.www.dataBase.DB;
import com.base.zhixing.www.dataBase.DBHelper;
import com.zhixing.masslib.bean.QC_Reason;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/8/18/018.
 */

public class MassDB extends DB {

    public   static MassDB dao;
    private static DBHelper dbHelper;
    private static SQLiteDatabase db;
    public static synchronized MassDB getInstance() {
        if (dao == null) {
            synchronized (com.base.zhixing.www.dataBase.DB.class) {
                if (dao == null) {
                    dao = new MassDB();
                    dbHelper = new DBHelper(BaseApp.application);
                    db = dbHelper.getWritableDatabase();
                }
            }
        }
        return dao;
    }
    /**
     * 根据字段名字获得数据
     *
     * @param cursor
     * @param indexName
     * @return
     */
    // --------------------------


    private boolean getBoolean(Cursor cursor, String indexName) {
        return cursor.getInt(cursor.getColumnIndex(indexName)) == 1 ? true
                : false;
    }
    public void addQC_Reason(ArrayList<QC_Reason> reasons){
        db.beginTransaction();
        clear("qc_reason");
        for(int i=0;i<reasons.size();i++){
            QC_Reason reason = reasons.get(i);
            db.execSQL("insert into qc_reason(id,excepName,type) values(?,?,?);",new Object[]{reason.getId(),reason.getName(),reason.getType()});
        }
        db.setTransactionSuccessful();
        db.endTransaction();
    }

    public void  getQc_reasons(ArrayList<QC_Reason> reasons,int type){
        db.beginTransaction();
        Cursor cursor = null;
        String sql  = "select id,excepName from qc_reason where type=?";
        cursor = db.rawQuery(sql,new String[]{String.valueOf(type)});
        reasons.clear();
        while(cursor.moveToNext()){
            //查询数据
            QC_Reason ra = new QC_Reason();
            ra.setName(getString(cursor,"excepName"));
            ra.setId(getString(cursor,"id"));
            reasons.add(ra);
        }
        db.setTransactionSuccessful();
        db.endTransaction();
    }

    /*public void clear(){
        db.execSQL("DELETE FROM persons");
    }*/
    public void clear(String tableName){
        db.execSQL("DELETE FROM "+tableName);
    }
}
