package com.shuben.zhixing.push;

import com.base.zhixing.www.BaseApp;
import com.base.zhixing.www.common.P;
import com.base.zhixing.www.util.SharedPreferencesTool;
import com.shuben.zhixing.www.activity.LoginActivity;
import com.wxx.net.HttpEngine;
import com.wxx.net.HttpResult;
import com.wxx.net.ParamsTool;
import com.wxx.net.ResponseModel;
import com.wxx.net._ResponseKt;

import org.jetbrains.annotations.NotNull;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;

public class PushMessageModel extends ResponseModel<String> {
    String message; 
    String from;
    String receivers;
    String module = "";
    public PushMessageModel(String message, String from, String receivers,String module) {
        this.message = message;
        this.from = from;
        this.receivers = receivers;
        this.module = module;
    }
    public PushMessageModel(String message, String from, String receivers) {
        this.message = message;
        this.from = from;
        this.receivers = receivers;

    }
    @Override
    public void getSource(@NotNull Function1<? super HttpResult<String>, Unit> callback) {
     //   UrlConfig config = new UrlConfig();

        String IP = SharedPreferencesTool.getMStool(BaseApp.application).getString("IP");

        if(IP.startsWith("http")){
            //无论是http还是https开头都这么认为
            IP = IP.split("://")[1];
        }
        String url = "http://"+IP+":2051/im/";
        ParamsTool params = new ParamsTool();
        params.setParams("content", message);
        params.setParams("action", "push");
        params.setParams("from", from);
        params.setParams("module",module);
        params. setParams("receivers", receivers);
        P.c("提示"+url);
        HttpEngine.get(url, params, _ResponseKt.wrapSimple(callback, String.class));
    }
}
