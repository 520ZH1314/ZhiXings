package com.shuben.zhixing.push;

public class UrlConfig {
    private   String BASE_IP ;

    public String getBASE_IP() {
        return BASE_IP+":2051/im/";
    }

    public void setBASE_IP(String BASE_IP) {
        this.BASE_IP = BASE_IP;
    }

    //  String BASE_URL =  BASE_IP+":2051/im/";

//      String  PUSH_MESSAGE = BASE_URL;
}
