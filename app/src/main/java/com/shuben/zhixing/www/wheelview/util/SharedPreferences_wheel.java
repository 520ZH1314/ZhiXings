package com.shuben.zhixing.www.wheelview.util;

import android.content.Context;
import android.content.SharedPreferences.Editor;

public class SharedPreferences_wheel {
    public static SharedPreferences_wheel mStool;
    private android.content.SharedPreferences preferences;
    public SharedPreferences_wheel(Context context) {
        preferences = context.getSharedPreferences("JR_P2P", Context.MODE_PRIVATE);
    }

    /**
     * 获取SharedPreferencesTool对象
     */
    public static SharedPreferences_wheel getMStool(Context context) {
        if (mStool == null && context!=null) {
            mStool = new SharedPreferences_wheel(context);

        }
        return mStool;
    }


    /**
     * 读取用户登录状态
     */
    public String readId() {
        return preferences.getString("id", "");
    }
 
    /**
     * 保存用户登录状态
     */
    public void saveId(String id) {
        Editor editor = preferences.edit();
        editor.putString("id", id);
        editor.commit();
    }

    /**
     * 保存用户登录状态(华融支付用户的token)
     */
    public void saveUserToken(String Token) {
        Editor editor = preferences.edit();
        editor.putString("Token", Token);
        editor.commit();
    }


    /**
     * 保存用户最近登录的手机号码
     */
    public void savePhone(String phone) {
        Editor editor = preferences.edit();
        editor.putString("Phone", phone);
        editor.commit();
    }

  

    /**
     * 读取用户最近登录的手机号码
     */
    public String readPhone() {
        return preferences.getString("Phone", "");
    }
    /**
     * 保存用户实名认证的名字
     */
    public void saveName(String Name) {
        Editor editor = preferences.edit();
        editor.putString("Name", Name);
        editor.commit();
    }

    /**
     * 读取用户实名认证的名字
     */
    public String readName() {
        return preferences.getString("Name", "");
    }


    /**
     * 判断跳转
     */
    public void saveIdCardNo(String IdCardNo) {
        Editor editor = preferences.edit();
        editor.putString("IdCardNo", IdCardNo);
        editor.commit();
    }


    public String readIdCardNo() {
        return preferences.getString("IdCardNo", "");
    }

    /**
     * 保存用户实登录密码
     */
    public void savePassword(String password) {
        Editor editor = preferences.edit();
        editor.putString("Password", password);
        editor.commit();
    }

    /**
     * 读取用户实登录密码
     */
    public String readPassword() {
        return preferences.getString("Password", "");
    }

    /**
     * 保存用户头像
     * @param UerHeadImage
     */
    public void saveUerHeadImage(String UerHeadImage) {
        Editor editor = preferences.edit();
        editor.putString("UerHeadImage", UerHeadImage);
        editor.commit();
    }
    /**
     * 读取用户头像
     */
    public String readUerHeadImage() {
        return preferences.getString("UerHeadImage", "");
    }
    


    /**
     * 保存获取的网络域名
     */
    public void save_url(String user_id) {
        Editor editor = preferences.edit();
        editor.putString("user_id", user_id);
        editor.commit();
    }
    /**
     *
     */
    public String read_url() {
        return preferences.getString("user_id", "");
    }
    /**
     * 获取是否有下属
     */
    public void save_UR_TYPE(String UR_TYPE) {
        Editor editor = preferences.edit();
        editor.putString("UR_TYPE", UR_TYPE);
        editor.commit();
    }
    public String read_UR_TYPE() {
        return preferences.getString("UR_TYPE", "");
    }
    /**
     * 获取关键字改变颜色
     */
    public void save_Key(String Key) {
        Editor editor = preferences.edit();
        editor.putString("Key", Key);
        editor.commit();
    }
    /**
     *
     */
    public String read_Key() {
        return preferences.getString("Key", "");
    }

}
