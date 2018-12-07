package com.sdk.chat.callback;


import com.sdk.chat.contact.ErrorCode;

public abstract class IPacketListener<T> {

  /**
   * 数据包进入消息队列的时间，方便log控制台查看
   */
  public long createTime= System.currentTimeMillis();

  /**
   * 一个数据包发送成功后的回调
   *
   * @param response 自定义类型
   */
  public abstract void onSuccess(T response);

  /**
   * socket数据包发送失败的回调
   *
   * @param errorCode 错误码，包括服务器的response的错误码
   * @param extra     通常是服务器给的具体的错误信息，json格式
   */
  public abstract void onFail(ErrorCode errorCode, String extra);
}
