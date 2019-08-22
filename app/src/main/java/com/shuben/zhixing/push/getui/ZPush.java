package com.shuben.zhixing.push.getui;

import android.app.Service;
import android.content.Intent;
import android.net.IpPrefix;
import android.os.IBinder;
import android.os.RemoteException;
import com.base.zhixing.www.common.P;
import com.shuben.common.IPush;
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
        P.push(tos+"=="+title+"=="+des+"=="+txt+"=="+flag+"=="+action+"==="+module);

    }

}
