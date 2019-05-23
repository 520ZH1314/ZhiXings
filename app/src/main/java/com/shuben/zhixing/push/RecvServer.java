package com.shuben.zhixing.push;

import com.sdk.chat.server.DataBuf;

public class RecvServer extends DataBuf {
    public String data;
    public RecvServer(String data) {
        super("recv");
        this.data = data;
    }
}
