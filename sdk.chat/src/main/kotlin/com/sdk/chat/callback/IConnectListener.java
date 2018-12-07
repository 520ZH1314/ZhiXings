package com.sdk.chat.callback;

import com.sdk.chat.contact.ErrorCode;

public interface IConnectListener {
  /**
   * 成功
   */
  void onConnectSuccess();

  /**
   * 与服务器断开连接
   *
   * @param code
   */
  void onConnectError(ErrorCode code);
}
