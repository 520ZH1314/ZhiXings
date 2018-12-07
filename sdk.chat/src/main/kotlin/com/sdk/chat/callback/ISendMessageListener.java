package com.sdk.chat.callback;


import com.sdk.chat.contact.ErrorCode;
import com.sdk.chat.message.Message;

/**
 * 作者：万祥新 2017/12/21 14:26
 * 发送消息的接口
 */

public interface ISendMessageListener {
  /**
   * 消息插入本地数据库，开始发送
   *
   * @param message
   */
  void onStart(Message message);

  /**
   * 消息发送成功
   *
   * @param message
   */
  void onSuccess(Message message);

  /**
   * 消息发送失败
   *
   * @param message
   * @param errorCode
   */
  void onError(Message message, ErrorCode errorCode);
}
