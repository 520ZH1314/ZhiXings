package com.shuben.zhixing.www;

import android.app.Application;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Environment;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.multidex.MultiDex;

import com.base.zhixing.www.BaseApp;
import com.liulishuo.filedownloader.FileDownloader;
import com.liulishuo.filedownloader.util.FileDownloadLog;
import com.liulishuo.filedownloader.util.FileDownloadUtils;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.sdk.chat.server.SdkConfig;
import com.base.zhixing.www.util.SharedPreferencesTool;
import com.shuben.common.IPush;
import com.shuben.zhixing.www.activity.LoginActivity;
import com.tencent.smtt.sdk.QbSdk;
import com.xdandroid.hellodaemon.DaemonEnv;
import com.base.zhixing.www.common.Common;
import com.base.zhixing.www.common.P;
import com.shuben.zhixing.www.service.TraceServiceImpl;
import com.shuben.zhixing.www.util.NotificationUtils;
import com.shuben.zhixing.www.util.SMEDTools;
import com.zhixing.kpilib.utils.KLog;
import com.zhixing.kpilib.utils.Utils;
import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class BaseApplication extends BaseApp {
    public static BaseApplication application;
    //    protected boolean isNeedCaughtExeption = true;// 是否捕获未知异常
    private MyUncaughtExceptionHandler uncaughtExceptionHandler;
    private static String packgeName;
    public static String getPackgeName(){
        return packgeName;
    }
    private   NotificationUtils notificationUtils;
    public NotificationUtils getNotificationUtils(){
        return  notificationUtils;
    }
        private void preinitX5WebCore() {
        QbSdk.PreInitCallback cb = new QbSdk.PreInitCallback() {

            @Override
            public void onViewInitFinished(boolean arg0) {
//x5內核初始化完成的回调，为true表示x5内核加载成功，否则表示x5内核加载失败，会自动切换到系统内核。
              P.c("onViewInitFinished");
            }

            @Override
            public void onCoreInitFinished() {

            }
        };
//x5内核初始化接口
        QbSdk.initX5Environment(getApplicationContext(), cb);
    }
    @Override
    public void onCreate() {
       /* if (Constant.Config.DEVELOPER_MODE && Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
            StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectAll().penaltyDialog().build());
            StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder().detectAll().penaltyDeath().build());
        }

        if (BuildConfig.DEBUG) {
            // 针对线程的相关策略
            StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                    .detectDiskReads()
                    .detectDiskWrites()
                    .detectNetwork()   // or .detectAll() for all detectable problems
                    .penaltyLog()
                    .build());

            // 针对VM的相关策略
            StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
                    .detectLeakedSqlLiteObjects()
                    .detectLeakedClosableObjects()
                    .penaltyLog()
                    .penaltyDeath()
                    .build());
        }*/
        super.onCreate();
        //预加载x5内核
        String IP = SharedPreferencesTool.getMStool(this).getString("IP");

//        UrlConfig.BASE_IP = IP;

        if(IP.startsWith("http")){
            //无论是http还是https开头都这么认为
            try {
                IP = IP.split("://")[1];
            }catch (ArrayIndexOutOfBoundsException e){

            }

        }
        SdkConfig.setIP(IP);
        P.c("推送服务初始化"+IP);
        preinitX5WebCore();

        /*ChatSdk.init(this);
        ChatSdk.setConnectListener(new IConnectListener() {
            @Override
            public void onConnectSuccess() {
                //123是用户的Id

                ChatSdk.INSTANCE.sendDataBuf(new LoginServer(SharedPreferencesTool.getMStool(getApplicationContext()).getUserCode()), null);
            }

            @Override
            public void onConnectError(ErrorCode code) {

            }
        });*/
        application = this;
        KLog.init(BuildConfig.DEBUG);
        setApplication(application);
        packgeName = getPackageName();
        P.c("初始化");
        if (DEVELOPER_MODE) {
            cauchException();
        }
        notificationUtils = new NotificationUtils(this);

//        loadPush();



        SMEDTools.init();
        initImageLoader(this);
        startServiceKeep();
        initFiled();

    }

    /**
    * @author cloor
    * @time   2019-1-10 17:54
    * @describe  :
    */
    private void initFiled(){
        FileDownloadLog.NEED_LOG = DEVELOPER_MODE;
        FileDownloader.setupOnApplicationOnCreate(this)
                /*.connectionCreator(new FileDownloadUrlConnection
                        .Creator(new FileDownloadUrlConnection.Configuration()
                        .connectTimeout(15_000) // set connection timeout.
                        .readTimeout(15_000) // set read timeout.
                ))*/
                .commit();



    }





    //启动保活服务
    public void startServiceKeep(){
        DaemonEnv.initialize(this, TraceServiceImpl.class, DaemonEnv.DEFAULT_WAKE_UP_INTERVAL);
        TraceServiceImpl.sShouldStopService = false;
        DaemonEnv.startServiceMayBind(TraceServiceImpl.class);
    }


    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    private void cauchException() {
        System.out
                .println("-----------------------------------------------------");
        // 程序崩溃时触发线程
        uncaughtExceptionHandler = new MyUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(uncaughtExceptionHandler);
    }

    private class MyUncaughtExceptionHandler implements
            Thread.UncaughtExceptionHandler {
        @Override
        public void uncaughtException(Thread thread, Throwable ex) {
            // 保存错误日志
            saveCatchInfo2File(ex);
            // 如果报错就不进行重启
//			android.os.Process.killProcess(android.os.Process.myPid());
            //System.exit(0);
        }

    }

    ;


    /**
     * 保存错误信息到文件中
     *
     * @return 返回文件名称
     */
    private String saveCatchInfo2File(Throwable ex) {
        Writer writer = new StringWriter();
        PrintWriter printWriter = new PrintWriter(writer);
        ex.printStackTrace(printWriter);
        Throwable cause = ex.getCause();
        while (cause != null) {
            cause.printStackTrace(printWriter);
            cause = cause.getCause();
        }
        printWriter.close();
        String sb = writer.toString();
        P.c("日志" + sb);
       // Toasty.INSTANCE.showToast(application,sb);
        try {
            DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
            String time = formatter.format(new Date());
            String fileName = time + ".txt";
            System.out.println("fileName:" + fileName);
            if (Environment.getExternalStorageState().equals(
                    Environment.MEDIA_MOUNTED)) {
                String filePath = Common.BASE_DIR + Common.DIR
                        + packgeName + "/crash/";
                File dir = new File(filePath);
                if (!dir.exists()) {
                    if (!dir.mkdirs()) {
                        // 创建目录失败: 一般是因为SD卡被拔出了
                        return "";
                    }
                }
                P.c("filePath + fileName:" + filePath + fileName);
                FileOutputStream fos = new FileOutputStream(filePath + fileName);
                fos.write(sb.getBytes());
                fos.close();
                // 文件保存完了之后,在应用下次启动的时候去检查错误日志,发现新的错误日志,就发送给开发者
            }
            return fileName;
        } catch (Exception e) {
            P.c("an error occured while writing file..." + e.getMessage());
        }
        return null;
    }
    public static void initImageLoader(Context context) {
        // This configuration tuning is custom. You can tune every option, you may tune some of them,
        // or you can create default configuration by
        //  ImageLoaderConfiguration.createDefault(this);
        // method.
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context)
                .threadPriority(Thread.NORM_PRIORITY )
                .denyCacheImageMultipleSizesInMemory()
                .discCacheFileNameGenerator(new Md5FileNameGenerator())
                .tasksProcessingOrder(QueueProcessingType.LIFO)
                .writeDebugLogs() // Remove for release app
                .build();
        // Initialize ImageLoader with configuration.
        ImageLoader.getInstance().init(config);
    }


    private static synchronized void setApplication(@NonNull BaseApplication application) {

        //初始化工具类
        Utils.init(application);
    }

}
