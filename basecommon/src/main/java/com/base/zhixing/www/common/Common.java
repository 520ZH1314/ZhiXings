package com.base.zhixing.www.common;

import android.os.Environment;

public class Common {
    public static String BASE_DIR = Environment
            .getExternalStorageDirectory().getAbsolutePath();
    public static String DIR = "/SHUBEN_CACHE/";
    public static String SD = BASE_DIR+DIR;
    public static String DB_NAME ="shuben.ads";
    public static int DB_VERSION  = 5;
    //13632854178
    public static final String FACTORY_TAG = "@STD数本科技";
    public static String CONSTANTDATA="constantdata";
}
