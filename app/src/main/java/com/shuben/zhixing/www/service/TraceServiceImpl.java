package com.shuben.zhixing.www.service;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import com.base.zhixing.www.util.SharedPreferencesTool;
import com.base.zhixing.www.util.TimeUtil;
import com.sdk.chat.ChatSdk;
import com.sdk.chat.callback.IConnectListener;
import com.sdk.chat.contact.ErrorCode;
import com.sdk.chat.message.Message;
import com.shuben.common.IPush;
import com.shuben.zhixing.module.andon.AndonRecive;
import com.shuben.zhixing.push.LoginServer;
import com.xdandroid.hellodaemon.AbsWorkService;
import com.base.zhixing.www.common.P;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.concurrent.TimeUnit;
import io.reactivex.Observable;
import io.reactivex.disposables.*;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;

public class TraceServiceImpl extends AbsWorkService {

    //是否 任务完成, 不再需要服务运行?
    public static boolean sShouldStopService;
    public static Disposable sDisposable;

    public static void stopService() {
        //我们现在不再需要服务运行了, 将标志位置为 true
        sShouldStopService = true;
        //取消对任务的订阅
        if (sDisposable != null) sDisposable.dispose();
        //取消 Job / Alarm / Subscription
        cancelJobAlarmSub();
    }

    /**
     * 是否 任务完成, 不再需要服务运行?
     * @return 应当停止服务, true; 应当启动服务, false; 无法判断, 什么也不做, null.
     */
    @Override
    public Boolean shouldStopService(Intent intent, int flags, int startId) {

        return sShouldStopService;
    }
    @Override
    public void startWork(Intent intent, int flags, int startId) {
        //P.c("检查磁盘中是否有上次销毁时保存的数据");
       // P.c("唤醒服务");
        //停止数据收集
       sDisposable = Observable
                .interval(3, TimeUnit.SECONDS)
                //取消任务时取消定时唤醒
                .doOnDispose(() -> {
                   // P.c("取消任务。");
                    cancelJobAlarmSub();
                })
                .subscribe(count -> {
                    P.c(ChatSdk.isConnectSuccess()+"连接情况"+TimeUtil.getTime(System.currentTimeMillis()));
                    if(!ChatSdk.isConnectSuccess()){
                         ChatSdk.close();
                         loadPush();
                    }

//                    P.c("每 3 秒采集一次数据... count = " + count);
//                    if (count > 0 && count % 18 == 0) System.out.println("保存数据到磁盘。 saveCount = " + (count / 18 - 1));
                });
    }

    @Override
    public void stopWork(Intent intent, int flags, int startId) {
        stopService();
    }

    /**
     * 任务是否正在运行?
     * @return 任务正在运行, true; 任务当前不在运行, false; 无法判断, 什么也不做, null.
     */
    @Override
    public Boolean isWorkRunning(Intent intent, int flags, int startId) {
        //若还没有取消订阅, 就说明任务仍在运行.

        return sDisposable != null && !sDisposable.isDisposed();
    }

    @Override
    public IBinder onBind(Intent intent, Void v) {
        return null;
    }

    @Override
    public void onServiceKilled(Intent rootIntent) {
        //BaseApplication.application.startServiceKeep();
        P.c("保存数据到磁盘");

    }


    public void loadPush(){
        final String userId =SharedPreferencesTool.getMStool(this).getUserId();
        if(!userId.equals("")){
            ChatSdk.close();
            ChatSdk.init(this);
            ChatSdk.setConnectListener(new IConnectListener() {
                @Override
                public void onConnectSuccess() {
                    //123是用户的Id
                    P.c("绑定推送ID"+userId);
                    ChatSdk.INSTANCE.sendDataBuf(new LoginServer(userId), null);
                    rev();
                }

                @Override
                public void onConnectError(ErrorCode code) {

                }
            });
        }
    }
    private void rev(){
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
    }

    private void sendReviceTo(String action,String txt){
        Intent intent = new Intent();
        intent.setPackage(getPackageName());
        intent.setAction(action);
        intent.putExtra("msg",txt);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        sendBroadcast(intent);
    }
}
