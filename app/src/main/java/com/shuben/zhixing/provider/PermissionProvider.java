package com.shuben.zhixing.provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import com.base.zhixing.www.common.P;

/**
 * 目的是防止有其他业务，保证跨进程使用
 */
public class PermissionProvider  extends ContentProvider {
    //创建authority
    public static final String AUTHORITY = "com.zhixing.provider";
    //表名
    public static final String TABLE_NAME ="permission";
    //创建UriMatcher对象
    private static UriMatcher uriMatcher;
    //添加整形常亮
    public static final int PERMISSION = 0;
    public static final int PERMISSION_PRODUCTS = 1;
    public static final int PERMISSION_MODULE = 2;
    //创建静态代码块
    static {
        //实例化UriMatcher对象
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        //可以实现匹配URI的功能
        //参数1：authority 参数2：路径 参数3：自定义代码
        uriMatcher.addURI(AUTHORITY, TABLE_NAME, PERMISSION);
        uriMatcher.addURI(AUTHORITY, TABLE_NAME+"/products", PERMISSION_PRODUCTS);
        uriMatcher.addURI(AUTHORITY, TABLE_NAME+"/*", PERMISSION_MODULE);
    }

    public PermissionProvider() {
    }
    private  PermissionHelper permissionHelper;
    @Override
    public boolean onCreate() {
        permissionHelper = new PermissionHelper(getContext());

        return true;
    }

    
    @Override
    public Cursor query(  Uri uri, String[] projection, String where, String[] whereArgs, String sortOrder) {
        SQLiteDatabase db = permissionHelper.getWritableDatabase();
        Cursor cursor = null;
        P.c(uri+"=="+(uriMatcher.match(uri)));
        switch (uriMatcher.match(uri)) {

            case PERMISSION:
//                String appCode = uri.getPathSegments().get(0);
//                String permissionCode = uri.getPathSegments().get(1);
                
                cursor = db.query(TABLE_NAME, projection, "appCode=? and permissionCode=?",whereArgs, null, null, sortOrder);
                break;
            case PERMISSION_PRODUCTS:
                P.c("查看所有");
                cursor = db.rawQuery("SELECT * FROM PERMISSION WHERE PARENTCODE=(select PERMISSIONCODE from permission where   appCode='EPS' AND parentCode ='')",whereArgs);

                break;
            case PERMISSION_MODULE:
                P.c("查看模块");
                cursor = db.rawQuery("SELECT * FROM PERMISSION WHERE PARENTCODE=?",whereArgs);

                break;
            default:
        }
        return cursor;
    }

    @Override
    public String getType(  Uri uri) {
        return null;
    }

    @Override
    public Uri insert(  Uri uri,   ContentValues contentValues) {
        SQLiteDatabase db  = permissionHelper.getWritableDatabase();
        switch (uriMatcher.match(uri)){
            case PERMISSION:
                //清空数据，保证每次都是新的
//                db.execSQL("delete from "+TABLE_NAME);

                db.insert(TABLE_NAME,null,contentValues);
                break;
        }
        return null;
    }
    //全部清空
    @Override
    public int delete(  Uri uri,   String selection,  String[] selectionArgs) {
        SQLiteDatabase db = permissionHelper.getWritableDatabase();
        int deleteInt = 0;
        //参数1：表名   参数2：约束删除列的名字   参数3：具体行的值
        deleteInt = db.delete(TABLE_NAME, selection, selectionArgs);
        return deleteInt;
    }

    @Override
    public int update(  Uri uri,  ContentValues contentValues,   String s,  String[] strings) {
        return 0;
    }
}
