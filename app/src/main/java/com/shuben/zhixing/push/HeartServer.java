package com.shuben.zhixing.push;

import com.sdk.chat.server.DataBuf;

public class HeartServer extends DataBuf {
    public String data;
    public HeartServer(String data) {
        super("heart");
        this.data = data;
    }
}
