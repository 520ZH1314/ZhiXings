package com.shuben.zhixing.module.andon;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.base.zhixing.www.common.Common;
import com.shuben.zhixing.push.getui.TraceServiceImpl;
import com.shuben.zhixing.push.VoiceService;
import com.shuben.zhixing.www.BaseApplication;
import com.shuben.zhixing.www.R;
import com.base.zhixing.www.common.P;
import com.base.zhixing.www.util.SharedPreferencesTool;
import com.shuben.zhixing.www.common.T;
import org.json.JSONException;
import org.json.JSONObject;
public class AndonRecive extends BroadcastReceiver {
    public static String action = "com.shuben.zhixing.andon";
    private int icon=R.mipmap.logo;
    private int id_Notify = 0;
    @Override
    public void onReceive(Context context, Intent ient) {
        //统一处理安灯推送
        P.c(ient.getAction());

        if(ient.getAction().equals(AndonRecive.action)){
            String title="";
            int flag = 0;
            String txt=ient.getStringExtra("msg");
//                Toasty.INSTANCE.showToast(application,txt);
            JSONObject content = null;
            int action = 0;
            String des = "";
            P.c("推送接受"+txt);
            try {
                JSONObject jsonObject = new JSONObject(txt);
                title =  jsonObject.getString("title");
                flag = jsonObject.getInt("flag");
                des =  jsonObject.getString("des");
                action = jsonObject.getInt("action");
                content  =jsonObject.getJSONObject("txt");
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
                    String p0 =  SharedPreferencesTool.getMStool(context).getUserId();
                    String p1 =  SharedPreferencesTool.getMStool(context).getUserCode();
                    String p2 = "";
                    String p3 = SharedPreferencesTool.getMStool(context).getTenantId();
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
                    P.c("发送"+js);
                    intent.putExtra("init_value",js);
                    intent.setClass(context, AndonActivity.class);
                    //context. startActivity(intent2);
                    break;
            }

//            int id=0;
            int time = 30;
            //  NotificationUtils.createNotif(context, icon, tickerText, title, content, intent, id, time);
            P.c("id_Notify++"+id_Notify);

            BaseApplication.application.getNotificationUtils().sendNotification(title,des,intent,icon,id_Notify++);

            Intent tip = new Intent(context, VoiceService.class);
            tip.putExtra("des",des);
            context.startService(tip);


        }else if(ient.getAction().equals(Intent.ACTION_SCREEN_ON)){
            Intent tip = new Intent(context, TraceServiceImpl.class);
            tip.putExtra("push_rev","push_rev");
            context.startService(tip);

        }else if(ient.getAction().equals(Intent.ACTION_USER_PRESENT)){
            Intent tip = new Intent(context, TraceServiceImpl.class);
            tip.putExtra("push_rev","push_rev");
            context.startService(tip);
        }
    }

    private String params(String params) {
        return "\'"+params+"\'";
    }
}
