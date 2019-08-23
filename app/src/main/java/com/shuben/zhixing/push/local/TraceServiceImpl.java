package com.shuben.zhixing.push.local;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.RequiresApi;
import com.sdk.chat.ChatSdk;
import com.sdk.chat.callback.IConnectListener;
import com.sdk.chat.contact.ErrorCode;
import com.sdk.chat.message.Message;
import com.shuben.zhixing.module.andon.AndonRecive;
import com.shuben.zhixing.provider.StandInfoBean;
import com.xdandroid.hellodaemon.AbsWorkService;
import com.base.zhixing.www.common.P;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;
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

    //跨进程获取登录对象
    private StandInfoBean getInfo(){
        Uri uri = Uri.parse("content://com.zhixing.provider/standInfo/fir");//这么使用
        Cursor cursor = getContentResolver().query(uri,null,null,null,null);
        Map map = new HashMap();

        StandInfoBean bean = new StandInfoBean();

        if( cursor.moveToFirst()){
            do{
                String TenantId = cursor.getString(cursor.getColumnIndex("TenantId"));
                String UserId = cursor.getString(cursor.getColumnIndex("UserId"));
                bean.setTenantId(TenantId);
                bean.setUserId(UserId);


            }while (cursor.moveToNext());
        }
        P.push("用户"+bean.getUserId()+"~~~~~~~~~"+bean.getTenantId());
        return  bean;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if(intent!=null&&intent.hasExtra("push_rev")){
            loadPush();
        }
        return super.onStartCommand(intent, flags, startId);
    }
    public  static volatile  long isConnected  = System.currentTimeMillis();
    @Override
    public void startWork(Intent intent, int flags, int startId) {
        //P.push("检查磁盘中是否有上次销毁时保存的数据");
       // P.push("唤醒服务");
        //停止数据收集
     /*   if(sDisposable==null)
       sDisposable = Observable
                .interval(3, TimeUnit.SECONDS)
                //取消任务时取消定时唤醒
                .doOnDispose(() -> {
                   // P.push("取消任务。");
                    cancelJobAlarmSub();
                })
                .subscribe(count -> {
                    P.push(ChatSdk.isConnectSuccess()+"连接情况"+(System.currentTimeMillis()-isConnected));
                    if(!ChatSdk.isConnectSuccess()||()){
                         ChatSdk.close();
                         loadPush();
                    }else{
//                        isConnected = false;
                    }

//                    P.push("每 3 秒采集一次数据... count = " + count);
//                    if (count > 0 && count % 18 == 0) System.out.println("保存数据到磁盘。 saveCount = " + (count / 18 - 1));
                });*/
        P.push("空运行~");
        if(System.currentTimeMillis()-isConnected>20*1000){
            P.push("长时间没有消息，重新连接");
            ChatSdk.close();
            loadPush();
        }



        if(heartDisposable==null)
        heartDisposable = Observable.interval(15,TimeUnit.SECONDS)
                .subscribe(count ->{
                  //  P.push(TimeUtil.getTime(isConnected));

                    if(getInfo().getUserId()!=null){
                        P.push("心跳~");
                        ChatSdk.INSTANCE.sendDataBuf(new HeartServer(null), null);
                    }else{
                        P.push("没有登录~停止发送心跳");
                        ChatSdk.close();
                    }

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
        P.push("保存数据到磁盘");

    }


    public void loadPush(){

        final String userId =  getInfo().getUserId();
        P.push("本地推送重连~"+userId);
        if(userId!=null&&!userId.equals("")){
            ChatSdk.close();
            ChatSdk.init(this);
            ChatSdk.setConnectListener(new IConnectListener() {
                @Override
                public void onConnectSuccess() {
                    //123是用户的Id
                    isConnected = System.currentTimeMillis();
                    P.push("绑定推送ID"+userId);
                    ChatSdk.INSTANCE.sendDataBuf(new LoginServer(userId), null);
                    rev();
                }

                @Override
                public void onConnectError(ErrorCode code) {
                    P.push("本地推送连接失败");
                }
            });
        }
    }
    StringBuffer buffer = new StringBuffer();
    public   final String END_TAG = "</END>";
    private void rev(){
        P.push("执行！！！！！！！！！！");
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
                P.push("推送接收-->"+message.getContent());
              /*  if(message.getContent().equals("heart_recv")){
                    TraceServiceImpl.isConnected = System.currentTimeMillis();
                    return null;
                }else{*/
                    buffer.append(message.getContent());
                    String temp = buffer.toString();
                    P.push("检查是否是结束"+(temp.endsWith(END_TAG)));
                    if(temp.endsWith(END_TAG)){


                        String[] tps = temp.split(END_TAG);


                        Set<String> set = new LinkedHashSet<>();


                        for(int i=0;i<tps.length;i++){

                            set.add(tps[i]);
                        }

                      /*  List<String> lists = null;
                        P.push("5");
                        try {
                            P.push("6");
                                 lists = Arrays.asList(tps).stream().distinct().collect(Collectors.toList());;
                        }catch (Exception e){
                            P.push("java8语法错误"+e.getMessage());
                        }*/
                      if(set.size()!=0){
                          Iterator<String> it = set.iterator();
                          while(it.hasNext()){
                             String tmp =  it.next();
                              try {
                                  P.push("分解==》"+tmp);

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
                          P.push("清除buffer##################");

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
                P.push("发送安灯推送"+txt);
                sendReviceTo(AndonRecive.action,txt);
                break;
        }
        return  null;
    }

    //发送已读到服务端
    private void sendReadOnly(String messId){
        String userId = getInfo().getUserId();
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
        P.push("发送已读"+object.toString());
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
