package com.shuben.contact.lib.dataBase;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.base.zhixing.www.BaseApp;
import com.base.zhixing.www.dataBase.DB;
import com.base.zhixing.www.dataBase.DBHelper;
import com.base.zhixing.www.util.ChineseCharToEn;
import com.orhanobut.logger.Logger;
import com.shuben.contact.lib.bean.Bean;
import com.shuben.contact.lib.bean.Type;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/8/18/018.
 */

public class ConstantDB extends DB {
    public   static ConstantDB dao;
    private static DBHelper dbHelper;
    private static SQLiteDatabase db;
    public static synchronized ConstantDB getInstance() {
        if (dao == null) {
            synchronized (DB.class) {
                if (dao == null) {
                    dao = new ConstantDB();
                    dbHelper = new DBHelper(BaseApp.application);
                    db = dbHelper.getWritableDatabase();
                }
            }
        }
        return dao;
    }



    ///-------------
   /* public void addQC_Reason(ArrayList<QC_Reason> reasons){
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
    */

    //----------------------

    public void addPerson(ArrayList<Bean> beans){
        db.beginTransaction();
        ChineseCharToEn toEn = new ChineseCharToEn();
        for(int i=0;i<beans.size();i++){
            Bean b = beans.get(i);
            db.execSQL("insert into persons(userid,usercode,username,sex,phone,cardno,photo,bpaddress,email,head,oid,ocode,oname,parent,pys,bys) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);",new Object[]{b.getUserId(),b.getUserCode(),b.getUserName(),b.getSex(),b.getPhoneNumber(),b.getCardNo(),b.getPhotoURL(),b.getBPAddress(),b.getEmail(),b.getHeadShip(),b.getOrganizeId(),b.getOrganizeCode(),b.getOrganizeName(),b.getParentCode(),toEn.getFirstLetter(b.getUserName()).toUpperCase(),toEn.getFirstLetter(b.getOrganizeName()).toUpperCase()});
        }
      //  personDataRecf(toEn);
        db.setTransactionSuccessful();
        db.endTransaction();
    }


    public void getPersons(ArrayList<Type> types, Context context){
        types.clear();
        db.beginTransaction();
        Cursor cursor0 = null;
        String sql0 = "select pys from persons group by pys order by pys asc";
        cursor0 = db.rawQuery(sql0,null);
        while(cursor0.moveToNext()){
            Type type = new Type();
            type.setPys(getString(cursor0,"pys"));
            type.setType(1);
            types.add(type);
        }
        cursor0 = db.rawQuery("select oid,userName,head ,photo,phone,pys ,userId from persons group by userId",null);
        while(cursor0.moveToNext()){
            Type type = new Type();
            type.setImg(getString(cursor0,"photo"));
            type.setZhiwei(getString(cursor0,"head"));
            type.setName(getString(cursor0,"userName"));
            type.setPys(getString(cursor0,"pys"));
            type.setPhone(getString(cursor0,"phone"));
             type.setOid(getString(cursor0,"oId"));
             type.setId(getString(cursor0,"userId"));
             type.setType(0);
             types.add(type);
        }

        db.setTransactionSuccessful();
        db.endTransaction();

    }
    public void getBersons(ArrayList<Type> types, Context context){
        types.clear();
        db.beginTransaction();
        Cursor cursor0 = null;
        String sql0 = "select  bys   from persons where parent=(select ocode from persons where parent is null and ocode is not null group by parent) group by bys order by bys";
        cursor0 = db.rawQuery(sql0,null);
        while(cursor0.moveToNext()){
            Type type = new Type();
            type.setPys(getString(cursor0,"bys"));
            type.setType(1);
            types.add(type);
        }
        cursor0 = db.rawQuery("select oid,oname,bys,count(oid) as count from persons where parent=(select ocode from persons where parent is null and ocode is not null group by parent) group by oid",null);
        while(cursor0.moveToNext()){
            Type type = new Type();
            //type.setImg(SharedPreferencesTool.getMStool(context).getIp()+getString(cursor0,"photo"));
            type.setZhiwei(getString(cursor0,"count")+"人");
            type.setName(getString(cursor0,"oName"));
            type.setPys(getString(cursor0,"bys"));
            type.setType(0);
            types.add(type);
        }

        db.setTransactionSuccessful();
        db.endTransaction();

    }

    public void clear(){
        db.execSQL("DELETE FROM persons");
    }
    public void clear(String tableName){
        db.execSQL("DELETE FROM "+tableName);
    }
}
