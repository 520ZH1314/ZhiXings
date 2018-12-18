package com.base.zhixing.www;

import android.app.Application;

//import com.luliang.shapeutils.DevShapeUtils;
import com.alibaba.android.arouter.launcher.ARouter;
import com.base.zhixing.www.common.P;
import com.luliang.shapeutils.DevShapeUtils;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;

public class BaseApp extends Application {
    public static final boolean DEVELOPER_MODE = true;
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
        P.c(packgeName);
        if (DEVELOPER_MODE) {           // 这两行必须写在init之前，否则这些配置在init过程中将无效
            ARouter.openLog();     // 打印日志
            ARouter.openDebug();   // 开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
        }
        ARouter.init(application); //

        //改变控件的selecter,阴影,圆角
        DevShapeUtils.init(this);

    }

}
