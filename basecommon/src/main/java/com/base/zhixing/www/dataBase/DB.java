package com.base.zhixing.www.dataBase;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.base.zhixing.www.BaseApp;
import java.util.ArrayList;

/**
 * Created by Administrator on 2017/8/18/018.
 */

public class DB {

    // --------------------------
    // ---------------获取数据处理
    public String getString(Cursor cursor, String indexName) {
        return cursor.getString(cursor.getColumnIndex(indexName));
    }

    private int getInt(Cursor cursor, String indexName) {
        return cursor.getInt(cursor.getColumnIndex(indexName));
    }

    private double getDouble(Cursor cursor, String indexName) {
        return cursor.getDouble(cursor.getColumnIndex(indexName));
    }

    private long getLong(Cursor cursor, String indexName) {
        return cursor.getLong(cursor.getColumnIndex(indexName));
    }

    private boolean getBoolean(Cursor cursor, String indexName) {
        return cursor.getInt(cursor.getColumnIndex(indexName)) == 1 ? true
                : false;
    }




}
