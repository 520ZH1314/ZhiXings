package com.shuben.zhixing.www.util;

import android.content.Context;

/**
 * 瀛楃涓叉搷浣滃伐鍏峰寘
 * 
 * @author liux (http://my.oschina.net/liux)
 * @version 1.0
 * @created 2012-3-21
 */
public class StringUtils {

    /**
     * 鍒ゆ柇瀛楃涓叉槸鍚︿负绌?
     * @param str 鐩爣瀛楃涓?
     * @return
     */
   public static boolean notNull(String str){
       if(str!=null&&!str.equals("")&&!str.equalsIgnoreCase("null")){
           return true;
       }
       return false;
   }


    public static String getStringById(Context context,int id){
        if(context==null||id==0){
            return null;
        }

        return  context.getResources().getString(id);
    }
}
