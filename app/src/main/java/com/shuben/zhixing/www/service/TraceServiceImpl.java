package com.shuben.zhixing.www.service;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.RequiresApi;

import com.base.zhixing.www.util.SharedPreferencesTool;
import com.base.zhixing.www.util.TimeUtil;
import com.sdk.chat.ChatSdk;
import com.sdk.chat.callback.IConnectListener;
import com.sdk.chat.contact.ErrorCode;
import com.sdk.chat.message.Message;
import com.shuben.common.IPush;
import com.shuben.zhixing.module.andon.AndonRecive;
import com.shuben.zhixing.push.HeartServer;
import com.shuben.zhixing.push.LoginServer;
import com.shuben.zhixing.push.RecvServer;
import com.xdandroid.hellodaemon.AbsWorkService;
import com.base.zhixing.www.common.P;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.Socket;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import io.reactivex.Observable;
import io.reactivex.disposables.*;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;

public class TraceServiceImpl extends AbsWorkService {

    //是否 任务完成, 不再需要服务运行?
    public static boolean sShouldStopService;
    public static Disposable sDisposable;
    public static Disposable heartDisposable;
    public static void stopService() {
        //我们现在不再需要服务运行了, 将标志位置为 true
        sShouldStopService = true;
        //取消对任务的订阅
        if (sDisposable != null) sDisposable.dispose();
        if(heartDisposable!=null)heartDisposable.dispose();
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
    public int onStartCommand(Intent intent, int flags, int startId) {
        if(intent.hasExtra("push_rev")){
            loadPush();
        }
        return super.onStartCommand(intent, flags, startId);
    }
    public  static volatile  long isConnected  = System.currentTimeMillis();
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
                    P.c(ChatSdk.isConnectSuccess()+"连接情况"+(System.currentTimeMillis()-isConnected));
                    if(!ChatSdk.isConnectSuccess()||(System.currentTimeMillis()-isConnected>20*1000)){
                         ChatSdk.close();
                         loadPush();
                    }else{
//                        isConnected = false;
                    }

//                    P.c("每 3 秒采集一次数据... count = " + count);
//                    if (count > 0 && count % 18 == 0) System.out.println("保存数据到磁盘。 saveCount = " + (count / 18 - 1));
                });

        heartDisposable = Observable.interval(8,TimeUnit.SECONDS)
                .subscribe(count ->{
                  //  P.c(TimeUtil.getTime(isConnected));
                    ChatSdk.INSTANCE.sendDataBuf(new HeartServer(null), null);
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
                    isConnected = System.currentTimeMillis();
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
    StringBuffer buffer = new StringBuffer();

    private void rev(){
        P.c("执行！！！！！！！！！！");
        ChatSdk.setReceiveMessageListener(new Function1<Message, Unit>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public Unit invoke(Message message) {
                //收到了服务器推送的消息
                //NotificationTool.showDefaultNotification(getApplicationContext(), message.getContent());
                Context context=getApplicationContext();

               // int flag = 0;
                TraceServiceImpl.isConnected = System.currentTimeMillis();

//
                P.c("推送接收-->"+message.getContent());
              /*  if(message.getContent().equals("heart_recv")){
                    TraceServiceImpl.isConnected = System.currentTimeMillis();
                    return null;
                }else{*/
                    buffer.append(message.getContent());
                    String temp = buffer.toString();
                    P.c("检查是否是结束"+(temp.endsWith("</END>")));
                    if(temp.endsWith("</END>")){


                        String[] tps = temp.split("</END>");


                        Set<String> set = new LinkedHashSet<>();


                        for(int i=0;i<tps.length;i++){

                            set.add(tps[i]);
                        }

                      /*  List<String> lists = null;
                        P.c("5");
                        try {
                            P.c("6");
                                 lists = Arrays.asList(tps).stream().distinct().collect(Collectors.toList());;
                        }catch (Exception e){
                            P.c("java8语法错误"+e.getMessage());
                        }*/
                      if(set.size()!=0){
                          Iterator<String> it = set.iterator();
                          while(it.hasNext()){
                             String tmp =  it.next();
                              try {
                                  P.c("分解==》"+tmp);

                                  JSONObject jsonObject  = new JSONObject(tmp);
                                  String txt = jsonObject.getString("sendTxt");
                                  String messId = jsonObject.getString("messageId");

                                  sendReadOnly(messId);

                                  parseTxt(txt);
                              } catch (JSONException e) {
                                  e.printStackTrace();
                                  //如果解析出来的不是json就不做处理
                                  continue;
                              }finally {
                                  tmp = null;
                              }
                          }
                          set.clear();
                          set = null;
                          buffer.delete(0,buffer.length());
                          temp = null;
                          P.c("清除buffer##################");

                      }





                    }else{
                        return null;
                    }

              //  }
                
                return null;
            }
        });
    }

    private Unit parseTxt( String txt){
        int  flag = 0;
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
        return  null;
    }

    //发送已读到服务端
    private void sendReadOnly(String messId){
        String userId = SharedPreferencesTool.getMStool(TraceServiceImpl.this).getUserId();
        JSONObject object = new JSONObject();
        JSONObject obj = new JSONObject();
        try {
            object.put("action","recv");
            obj.put("user",userId);
            obj.put("messageId",messId);
            object.put("data",obj.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        ChatSdk.INSTANCE.sendDataBuf(new RecvServer(obj.toString()), null);
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
