package com.shuben.zhixing.www;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.multidex.MultiDex;

import com.base.zhixing.www.BaseApp;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.sdk.chat.ChatSdk;
import com.sdk.chat.callback.IConnectListener;
import com.sdk.chat.contact.ErrorCode;
import com.sdk.chat.message.Message;
import com.sdk.chat.server.SdkConfig;
import com.shuben.zhixing.module.andon.AndonRecive;
import com.shuben.zhixing.push.UrlConfig;
import com.base.zhixing.www.util.SharedPreferencesTool;
import com.tencent.smtt.sdk.QbSdk;
import com.xdandroid.hellodaemon.DaemonEnv;
import com.base.zhixing.www.common.Common;
import com.base.zhixing.www.common.P;
import com.shuben.zhixing.push.LoginServer;
import com.shuben.zhixing.www.service.TraceServiceImpl;
import com.shuben.zhixing.www.util.NotificationUtils;
import com.shuben.zhixing.www.util.SMEDTools;
import com.zhixing.kpilib.utils.KLog;
import com.zhixing.kpilib.utils.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;

public class BaseApplication extends BaseApp {
    private static final boolean DEVELOPER_MODE = true;
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
        P.c("推送IP"+IP);
        if(IP.startsWith("http")){
            //无论是http还是https开头都这么认为
            IP = IP.split("://")[1];
        }
        SdkConfig.setIP(IP);

        P.c("连接推送"+IP);
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

        loadPush();


        ChatSdk.setReceiveMessageListener(new Function1<Message, Unit>() {
            @Override
            public Unit invoke(Message message) {
                //收到了服务器推送的消息
                //NotificationTool.showDefaultNotification(getApplicationContext(), message.getContent());
                Context context=getApplicationContext();

                int flag = 0;
                String txt= message.getContent();
//
                P.c("application接收"+message.getContent());

                try {
                    JSONObject jsonObject = new JSONObject(txt);
                    flag = jsonObject.getInt("flag");
                } catch (JSONException e) {
                    e.printStackTrace();
                    return null;
                }
              switch (flag){
                  case 0:
                      //默认错误界面

                      break;
                  case 3:
                      //安灯
                     P.c("发送安灯推送"+txt);
                     sendReviceTo(AndonRecive.action,txt);
                     break;
              }
                return null;
            }
        });
        SMEDTools.init();
        initImageLoader(this);
        startServiceKeep();
    }
    public void loadPush(){
        final String userId =SharedPreferencesTool.getMStool(application).getUserId();
        if(!userId.equals("")){
            ChatSdk.close();
            ChatSdk.init(application);
            ChatSdk.setConnectListener(new IConnectListener() {
                @Override
                public void onConnectSuccess() {
                    //123是用户的Id
                    P.c("发送ID"+userId);
                    ChatSdk.INSTANCE.sendDataBuf(new LoginServer(userId), null);
                }

                @Override
                public void onConnectError(ErrorCode code) {

                }
            });
        }
    }


    private void sendReviceTo(String action,String txt){
        Intent intent = new Intent();
        intent.setPackage(getPackageName());
        intent.setAction(action);
        intent.putExtra("msg",txt);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        sendBroadcast(intent);
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
