package com.base.zhixing.www;

import android.app.Application;

//import com.luliang.shapeutils.DevShapeUtils;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;

public class BaseApp extends Application {
    public static BaseApp application;
    private static String packgeName;
    public static String getPackgeName(){
        return packgeName;
    }
    @Override
    public void onCreate() {
        super.onCreate();
        application = this;
        packgeName = getPackageName();
        Logger.addLogAdapter(new AndroidLogAdapter());

    }

}
