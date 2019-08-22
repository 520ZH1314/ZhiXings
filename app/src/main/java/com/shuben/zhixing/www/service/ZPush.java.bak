package com.shuben.zhixing.www.service;

import android.app.Service;
import android.content.Intent;
import android.net.IpPrefix;
import android.os.IBinder;
import android.os.RemoteException;
import com.base.zhixing.www.common.P;
import com.sdk.chat.ChatSdk;
import com.shuben.common.IPush;
import com.shuben.zhixing.push.PushMessageModel;
import com.wxx.net.HttpResult;

import org.json.JSONException;
import org.json.JSONObject;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;

public class ZPush extends Service {


    @Override
    public IBinder onBind(Intent intent) {
        return stub;
    }

    IPush.Stub stub = new IPush.Stub() {

        @Override
        public void push(String IP,String from,String tos,String title,String des,String txt,int flag,int action,String module) throws RemoteException {
           pushToUsers(IP,from,tos, title, des, txt, flag, action, module);
        }
    };
    /**
     * @param tos 接收人，多个人之间用逗号分隔
     * @param title 推送标题
     *              des 描述
     * @param txt 推送内容，json形式
     * @param flag 设备来源  H5是3
     * @param  action 动作，表示点击推送消息之后要产生什么动作。默认空字符
     *                module  模块名称
     */
    public void pushToUsers(String IP,String from,String tos,String title,String des,String txt,int flag,int action,String module){
        P.c(ChatSdk.isConnectSuccess()+tos+"=="+title+"=="+des+"=="+txt+"=="+flag+"=="+action+"==="+module);
        if(!ChatSdk.isConnectSuccess()){
            try {
                JSONObject object = new JSONObject();
                object.put("title",title);
                object.put("des",des);
                JSONObject o = new JSONObject(txt);
                object.put("txt",o);
                object.put("flag",flag);
                object.put("action",action);
                P.c("打包的推送json"+object.toString( ));
                PushMessageModel push=new PushMessageModel(object.toString(),from,tos,module,IP);
                push.getSource(new Function1<HttpResult<String>, Unit>() {
                    @Override
                    public Unit invoke(HttpResult<String> stringHttpResult) {
                        if (stringHttpResult.getSuccess()){
                            //服务器响应成功
                            stringHttpResult.getData();
                           P.c("推送结果"+stringHttpResult.getData());
                        }else {
                            P.c("推送失败");
//                            Toasty.INSTANCE.showToast(this,stringHttpResult.getMessage());
                        }
                        return null;
                    }
                });
            } catch (JSONException e) {
                e.printStackTrace();
                P.c("推送数据解析"+e.getMessage());
            }

        }
    }

}
