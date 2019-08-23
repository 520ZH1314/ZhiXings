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
    public static final String TABLE_NAME_permission ="permission";
    public static final String TABLE_NAME_standInfo = "standInfo";
    //创建UriMatcher对象
    private static UriMatcher uriMatcher;
    //添加整形常亮
    public static final int PERMISSION = 0;
    public static final int PERMISSION_PRODUCTS = 1;
    public static final int PERMISSION_MODULE = 2;
    public  static final int STANDINFO = 4;
    public static final int STANDINFO_FIR = 5;
    public  static final int CLEAR_ALL = 99;
    //创建静态代码块
    static {
        //实例化UriMatcher对象
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        //可以实现匹配URI的功能
        //参数1：authority 参数2：路径 参数3：自定义代码
        uriMatcher.addURI(AUTHORITY, TABLE_NAME_permission+"", PERMISSION);
        uriMatcher.addURI(AUTHORITY, TABLE_NAME_permission+"/products", PERMISSION_PRODUCTS);
        uriMatcher.addURI(AUTHORITY, TABLE_NAME_permission+"/*", PERMISSION_MODULE);
        //-------------
        uriMatcher.addURI(AUTHORITY, TABLE_NAME_standInfo+"", STANDINFO);
        uriMatcher.addURI(AUTHORITY, TABLE_NAME_standInfo+"/fir", STANDINFO_FIR);
        uriMatcher.addURI(AUTHORITY,"clear/cache",CLEAR_ALL);

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

        switch (uriMatcher.match(uri)) {
            case STANDINFO_FIR:
                //查看基础信息
                cursor = db.rawQuery("select *   from standInfo ",null);
                break;
            case PERMISSION:
//                String appCode = uri.getPathSegments().get(0);
//                String permissionCode = uri.getPathSegments().get(1);
                
                cursor = db.query(TABLE_NAME_permission, projection, "appCode=? and permissionCode=?",whereArgs, null, null, sortOrder);
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
//                db.execSQL("delete from "+TABLE_NAME_permission);
                db.insert(TABLE_NAME_permission,null,contentValues);
                break;
            case STANDINFO:
                db.execSQL("delete from "+TABLE_NAME_standInfo);

                db.insert(TABLE_NAME_standInfo,null,contentValues);
                break;
        }
        return null;
    }
    //全部清空
    @Override
    public int delete(  Uri uri,   String selection,  String[] selectionArgs) {
        //此处只是对权限表进行了清空，如需
        SQLiteDatabase db = permissionHelper.getWritableDatabase();
        int deleteInt = 0;
        switch (uriMatcher.match(uri)){
            case PERMISSION:

                P.c("清除ContentProvider权限信息");
                //参数1：表名   参数2：约束删除列的名字   参数3：具体行的值

                db.execSQL("delete from "+TABLE_NAME_permission);
//                deleteInt = db.delete(TABLE_NAME_permission, selection, selectionArgs);
                break;
            case STANDINFO:
                P.c("清除ContentProvider个人信息");
                //参数1：表名   参数2：约束删除列的名字   参数3：具体行的值
                db.execSQL("delete from "+TABLE_NAME_standInfo);
//                deleteInt = db.delete(TABLE_NAME_standInfo, selection, selectionArgs);
                break;
            case CLEAR_ALL:
                //清除所有
                P.c("清除所有");
                db.execSQL("delete from "+TABLE_NAME_permission);
                db.execSQL("delete from "+TABLE_NAME_standInfo);
                break;
        }
        return deleteInt;
    }

    @Override
    public int update(  Uri uri,  ContentValues contentValues,   String s,  String[] strings) {
        return 0;
    }
}
