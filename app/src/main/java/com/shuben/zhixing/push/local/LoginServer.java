package com.shuben.zhixing.push.local;

import com.sdk.chat.server.DataBuf;

public class LoginServer extends DataBuf {
    public String data;
    public LoginServer(String data) {
        super("login");
        this.data = data;
    }
}
