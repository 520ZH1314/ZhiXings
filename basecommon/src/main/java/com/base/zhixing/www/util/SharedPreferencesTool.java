package com.base.zhixing.www.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class SharedPreferencesTool {
    private static volatile SharedPreferencesTool mStool;
    private SharedPreferences preferences;



    public SharedPreferencesTool(Context context) {
        preferences = context.getSharedPreferences("JR_P2P", Context.MODE_PRIVATE);
    }

    /**
     * 获取SharedPreferencesTool对象
     */
    public static SharedPreferencesTool getMStool(Context context) {
        if (mStool == null && context!=null) {
            mStool = new SharedPreferencesTool(context);
        }
        return mStool;
    }

    /**
     *
     */
    public void saveUserToken(String Token) {
        Editor editor = preferences.edit();
        editor.putString("Token", Token);
        editor.commit();
    }
    public void savePassword(String phone) {
        Editor editor = preferences.edit();
        editor.putString("Password", phone);
        editor.commit();
    }

    public String getPassword() {
        return preferences.getString("Password", "");
    }
    /**
     *
     */
    public void savePhone(String phone) {
        Editor editor = preferences.edit();
        editor.putString("Phone", phone);
        editor.commit();
    }
    public String getPhone() {
        return preferences.getString("Phone","");
    }
    /**
     *保存Ip
     */
    public void saveIp(String userId) {
        Editor editor = preferences.edit();
        editor.putString("IP", userId);
        editor.commit();
    }


    /**
     *获取UserIp
     */
    public String getIp() {
        String ip = preferences.getString("IP",UrlUtil.BaseUrl);
        String port = preferences.getString("PORT","");
        String res = null;
        if(ip.length()==0){
            return "";
        }
        if(ip.startsWith("http")){
            //使用全地址
            if(port.length()!=0){
                res= ip+":"+port;
            }else{
                res = ip;
            }

        }else{
              res="http://"+preferences.getString("IP",UrlUtil.BaseUrl)+":"+preferences.getString("PORT","");
        }
        return res;
    }

    /**
     *保存UserId
     */
    public void saveUserId(String userId) {
        Editor editor = preferences.edit();
        editor.putString("UserId", userId);
        editor.commit();
    }

        public void clear(String key) {
            Editor editor = preferences.edit();
            editor.remove(key);
            editor.commit();
        }

        public void clearAll(){
        Editor editor = preferences.edit();
        editor.clear();
        editor.commit();
        }


    /**
     *获取UserId
     */

    public String getUserId() {
       return preferences.getString("UserId","");
    }




    /**
     *保存UserName
     */
    public void saveUserCode(String userCode) {
        Editor editor = preferences.edit();
        editor.putString("UserCode", userCode);
        editor.commit();
    }

    public void setString(String txt,String value){
        Editor editor = preferences.edit();
        editor.putString(txt, value);
        editor.commit();
    }
    public String getString(String param){
        return  preferences.getString(param,"");
    }




    /**
     *获取UserName
     */
    public String getUserCode() {
        return preferences.getString("UserCode","");
    }

    /**
     *保存UserName
     */
    public void saveUserName(String userId) {
        Editor editor = preferences.edit();
        editor.putString("UserName", userId);
        editor.commit();
    }


    /**
     *获取UserName
     */
    public String getUserName() {
        return preferences.getString("UserName","");
    }


    /**
     *保存saveTenantId
     */
    public void saveTenantId(String userId) {
        Editor editor = preferences.edit();
        editor.putString("TenantId", userId);
        editor.commit();
    }

    /**
     *获取TenantId
     */
    public String getTenantId() {
        return preferences.getString("TenantId","");
    }

    public void saveTenantId02(String userId) {
        Editor editor = preferences.edit();
        editor.putString("TenantId02", userId);
        editor.commit();
    }

    /**
     *获取TenantId
     */
    public String getTenantId02() {
        return preferences.getString("TenantId02","");
    }


    public void saveUpdateUrl(String updateUrl) {
        Editor editor = preferences.edit();
        editor.putString("UpdateUrl", updateUrl);
        editor.commit();
    }

    /**
     *获取UserId
     */
    public String getUpdateUrl() {
        return preferences.getString("UpdateUrl","");
    }



    /**
     *保存saveTenantId
     */
    public void saveLinePatrolClassId(String name,String userId) {
        Editor editor = preferences.edit();
        editor.putString(name, userId);
        editor.commit();
    }

    /**
     *获取TenantId
     */
    public String GetLinePatrolClassId(String name) {
        return preferences.getString(name,"");
    }


}
