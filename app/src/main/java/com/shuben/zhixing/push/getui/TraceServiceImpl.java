package com.shuben.zhixing.push.getui;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.IBinder;
import com.base.zhixing.www.common.P;
import com.igexin.sdk.PushManager;
import com.shuben.zhixing.provider.StandInfoBean;
import com.xdandroid.hellodaemon.AbsWorkService;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;

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
        return  bean;
    }

    private boolean isConnect(){
        Uri uri = Uri.parse("content://com.zhixing.provider/standInfo/fir");//这么使用
        Cursor cursor = getContentResolver().query(uri,null,null,null,null);
        if(cursor!=null&&cursor.moveToFirst()){

            if(cursor.getCount()!=0){
                stopPush();
                return true;
            }
        }


        return false;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if(intent!=null&&intent.hasExtra("push_rev")){
            loadPush();
        }
        return super.onStartCommand(intent, flags, startId);
    }
   // public  static volatile  long isConnected  = System.currentTimeMillis();
    @Override
    public void startWork(Intent intent, int flags, int startId) {
        //P.push("检查磁盘中是否有上次销毁时保存的数据");
       // P.push("唤醒服务");
        //停止数据收集
      /*  if(sDisposable==null)
       sDisposable = Observable
                .interval(10, TimeUnit.SECONDS)
                //取消任务时取消定时唤醒
                .doOnDispose(() -> {
                   // P.push("取消任务。");
                    cancelJobAlarmSub();
                })
                .subscribe(count -> {
                 //   P.push(ChatSdk.isConnectSuccess()+"连接情况"+(System.currentTimeMillis()-isConnected));
                       sendStatus();
//                    P.push("每 3 秒采集一次数据... count = " + count);
//                    if (count > 0 && count % 18 == 0) System.out.println("保存数据到磁盘。 saveCount = " + (count / 18 - 1));
                });*/


        P.push("空运行~~外网");
        if(heartDisposable==null)
        heartDisposable = Observable.interval(15,TimeUnit.SECONDS)
                .subscribe(count ->{
                  //  P.push(TimeUtil.getTime(isConnected));

                   if(getInfo().getUserId()!=null){
                       //登录状态
                       P.push("心跳~外网");
                       sendStatus();
                   }else{
                       P.push("没有登录~停止发送心跳~外网");
                       stopPush();
                   }

                });

                

    }

    private void sendStatus(){
        Intent tip = new Intent(this, GeTuiReviceServices.class);
        tip.putExtra("status","get");
        startService(tip);
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
//        BaseApplication.application.startServiceKeep();
        P.push("保存数据到磁盘0");
      //  BaseApplication.application.startServiceKeep();
        //这里还没有处理好。。。。。。。。。2019-06-12
        /*DaemonEnv.initialize(this, TraceServiceImpl.class, DaemonEnv.DEFAULT_WAKE_UP_INTERVAL);
        TraceServiceImpl.sShouldStopService = false;
        DaemonEnv.startServiceMayBind(TraceServiceImpl.class);*/
        P.push("保存数据到磁盘1");

    }


    public void loadPush(){

            if(getInfo().getTenantId()!=null){
                P.push("模拟重连");
                PushManager.getInstance().initialize(this.getApplicationContext(), GTPushService.class);
                PushManager.getInstance().registerPushIntentService(this.getApplicationContext(), GeTuiReviceServices.class);
            }else{
                P.push("未登录，不能重连");
            }

        //PushManager.getInstance().setHeartbeatInterval(this,3000);

    }
    private void stopPush(){
        PushManager.getInstance().stopService(this);
    }





}
