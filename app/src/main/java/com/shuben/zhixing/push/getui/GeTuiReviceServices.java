package com.shuben.zhixing.push.getui;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.base.zhixing.www.common.Common;
import com.base.zhixing.www.common.FileUtils;
import com.base.zhixing.www.common.P;
import com.base.zhixing.www.inter.VolleyResult;
import com.base.zhixing.www.util.SharedPreferencesTool;
import com.igexin.sdk.GTIntentService;
import com.igexin.sdk.message.GTCmdMessage;
import com.igexin.sdk.message.GTNotificationMessage;
import com.igexin.sdk.message.GTTransmitMessage;
import com.shuben.zhixing.module.andon.AndonActivity;
import com.shuben.zhixing.push.VoiceService;
import com.shuben.zhixing.www.BaseApplication;
import com.shuben.zhixing.www.R;
import com.shuben.zhixing.www.common.T;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.HashMap;
import java.util.Map;

/**
 * 继承 GTIntentService 接收来自个推的消息, 所有消息在线程中回调, 如果注册了该服务, 则务必要在 AndroidManifest中声明, 否则无法接受消息<br>
 * onReceiveMessageData 处理透传消息<br>
 * onReceiveClientId 接收 cid <br>
 * onReceiveOnlineState cid 离线上线通知 <br>
 * onReceiveCommandResult 各种事件处理回执 <br>
 * @author cloor
 */
public class GeTuiReviceServices extends GTIntentService {
       boolean isOnline = false;
    @Override
    public int onStartCommand(Intent intent, int i, int i1) {
        if(intent!=null&&intent.hasExtra("status")){

            //双保险验证
            if(System.currentTimeMillis()-isConnected>30*1000){
                P.push("长时间没有消息，重新连接");
                sendStatus();
            }else{
                if(!isOnline){
                    sendStatus();
                    P.push("推送服务连接断开==>");
                }else{
                    P.push("推送服务连接正常");
                }
            }


        }else if(intent!=null&&intent.hasExtra("blind")){
           //暂时不处理，目前先设计在有推送id的时候主动去绑定~
        }

        return super.onStartCommand(intent, i, i1);
    }
    private void sendStatus(){
        Intent tip = new Intent(this, TraceServiceImpl.class);
        tip.putExtra("push_rev","push_rev");
        startService(tip);
    }

    @Override
    public void onReceiveServicePid(Context context, int i) {

    }
    @Override
    public void onReceiveClientId(Context context, String clientid) {
        P.push("onReceiveClientId -> " + "clientid = " + clientid);
        blindUser(clientid);
    }

    @Override
    public void onReceiveMessageData(Context context, GTTransmitMessage msg) {
        String appid = msg.getAppid();
        String taskid = msg.getTaskId();
        String messageid = msg.getMessageId();
        byte[] payload = msg.getPayload();
        String pkg = msg.getPkgName();
        String cid = msg.getClientId();
        P.push("onReceiveMessageData -> " + "appid = " + appid + "\ntaskid = " + taskid + "\nmessageid = " + messageid + "\npkg = " + pkg
                + "\ncid = " + cid);
        if(payload!=null){
            String data = new String(payload);
           P.push("receiver payload = " + data);
            parsePayload(data);
        }


    }

    @Override
    public void onReceiveOnlineState(Context context, boolean b) {
        if((!isOnline)&&b){
            P.push("恢复推送连接");
        }
        isOnline = b;
            P.push("推送在线状态"+b);
    }

    @Override
    public void onReceiveCommandResult(Context context, GTCmdMessage gtCmdMessage) {

    }

    @Override
    public void onNotificationMessageArrived(Context context, GTNotificationMessage gtNotificationMessage) {
                     
    }

    @Override
    public void onNotificationMessageClicked(Context context, GTNotificationMessage gtNotificationMessage) {

    }
    public  static volatile  long isConnected  = System.currentTimeMillis();
    private int icon= R.mipmap.logo;
    private int id_Notify = 0;
    private void parsePayload(String payloadText){
        //更新推送时间
        isConnected  = System.currentTimeMillis();

        String title="";
        int flag = 0;

//                Toasty.INSTANCE.showToast(application,txt);
        JSONObject content = null;
        int action = 0;
        String des = null;
        String TaskId = null;
        //P.push("推送接受"+payloadText);
        try {
            JSONObject jsonObject = new JSONObject(payloadText);
            title =  jsonObject.getString("title");
            flag = jsonObject.getInt("flag");
            des =  jsonObject.getString("des");
            action = jsonObject.getInt("action");
            content  =jsonObject.getJSONObject("txt");
            TaskId = jsonObject.getString("TaskId");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        action = 3;
        Intent intent=new Intent();
        switch (action){
            case 3:
                //进入安灯初始化
//                    String user= SharedPreferencesTool.getMStool(context).getPhone();
//                    String psw= SharedPreferencesTool.getMStool(context).getPassword();
                intent.putExtra("file", Common.SD+ T.ANDON+"/MODULE/index.html");
//                    intent.putExtra("url","http://192.168.2.133:8081");
                String p0 =  SharedPreferencesTool.getMStool(this).getUserId();
                String p1 =  SharedPreferencesTool.getMStool(this).getUserCode();
                String p2 = "";
                String p3 = SharedPreferencesTool.getMStool(this).getTenantId();
                String p4 ="";
                String p7  ="";
                String p5="";
                String p6="";
                String p8 = "";
                try {
                    p4 = content.getString("factory_id");
                    p7 = content.getString("workshop_id");
                    p5 = content.getString("line_id");
                    p6 = content.getString("station_id");
                    p8 = content.getString("ExceptionId");//异常id
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                String js = "javascript:loginInfoMsg("+params(p0)+","+params(p1)+","+params(p2)+","+params(p3)+","+params(p4)+","+params(p7)+","+params(p5)+","+params(p6)+","+params(p8)+")";
                P.push("发送"+js);
                intent.putExtra("init_value",js);
                intent.setClass(this, AndonActivity.class);
                //context. startActivity(intent2);
                break;
        }

//            int id=0;
        int time = 30;
        //  NotificationUtils.createNotif(context, icon, tickerText, title, content, intent, id, time);
        P.push("id_Notify++"+id_Notify);
        if(des!=null){
            //
            BaseApplication.application.getNotificationUtils().sendNotification(title,des,intent,id_Notify%2==0?icon:R.mipmap.andon_ico,id_Notify++);
            Intent tip = new Intent(this, VoiceService.class);
            tip.putExtra("des",des);
            startService(tip);
        }
        if(TaskId!=null){
            sendReader(TaskId);
        }


    }
    private String params(String params) {
        return "\'"+params+"\'";
    }
    //绑定信息
    private void blindUser(String ClientId){

        Uri uri = Uri.parse("content://com.zhixing.provider/standInfo/fir");//这么使用
        Cursor cursor = getContentResolver().query(uri,null,null,null,null);
       Map map = new HashMap();
        if( cursor.moveToFirst()){
            do{
                map.put("TenantId",cursor.getString(cursor.getColumnIndex("TenantId")));
                map.put("UserId",cursor.getString(cursor.getColumnIndex("UserId")));
                map.put("UserName",cursor.getString(cursor.getColumnIndex("UserName")));
            }while (cursor.moveToNext());
        }
        P.push("什么情况~"+map.isEmpty());
       if(!map.isEmpty()){
           //执行绑定操作
           map.put("ClientId",ClientId);

           httpPostSONVolley(GTI.BLIND_URL, map, new VolleyResult() {
               @Override
               public void success(JSONObject jsonObject) {

               }

               @Override
               public void error(VolleyError error) {

               }
           });

       }
    }
    //发送已读
    
    private void sendReader(String TaskId){
        Uri uri = Uri.parse("content://com.zhixing.provider/standInfo/fir");//这么使用
        Cursor cursor = getContentResolver().query(uri,null,null,null,null);
        Map map = new HashMap();
        String TenantId = null;
        if( cursor.moveToFirst()){
            do{
                TenantId = cursor.getString(cursor.getColumnIndex("TenantId"));

            }while (cursor.moveToNext());
        }
        map.put("TenantId",TenantId);
        map.put("TaskId",TaskId);
        if(TenantId==null){
            P.push("已退出，无法提交已读状态");
            return;
        }
        httpPostSONVolley(GTI.SEND_READ, map, new VolleyResult() {
            @Override
            public void success(JSONObject jsonObject) {

            }

            @Override
            public void error(VolleyError error) {

            }
        });
    }

    private RequestQueue requestQueue ;

    @Override
    public void onCreate() {
        super.onCreate();

        requestQueue = Volley.newRequestQueue(this);
    }



    //联网操作
    private void httpPostSONVolley(String URL, Map<String,String> params, final VolleyResult result){
        FileUtils.parms(params);

        JsonObjectRequest newMissRequest = new JsonObjectRequest(Request.Method.POST, URL, new JSONObject(params), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                if(result!=null){
                    P.push("推送绑定联网"+jsonObject.toString());
                    result.success(jsonObject);

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                P.push("错误"+volleyError.getLocalizedMessage()+"~~"+volleyError.getMessage());
                if(result!=null){
                    result.error(volleyError);

                }
            }
        });
        newMissRequest.setRetryPolicy( new DefaultRetryPolicy( 50000,//默认超时时间，应设置一个稍微大点儿的，例如本处的500000
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,//默认最大尝试次数
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT ) );


        requestQueue.add(newMissRequest);
    }

}
