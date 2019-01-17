package com.zhixing.netlib.base;

/**
 * 服务器返回的错误
 */
public class ServerException extends RuntimeException {
    public int getCode() {
        return Code;
    }

    public void setCode(int code) {
        Code = code;
    }

    private int Code;
    private String msg;

    public ServerException( String msg) {

        this.msg = msg;
    }
    public ServerException( String msg,int Code) {
    this.Code=Code;
        this.msg = msg;
    }


    public String getMsg() {
        return msg;
    }
}