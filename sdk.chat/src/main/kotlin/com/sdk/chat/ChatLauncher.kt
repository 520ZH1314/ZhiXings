package com.sdk.chat

import com.sdk.chat.callback.IConnectListener
import com.sdk.chat.contact.ErrorCode
import com.sdk.chat.message.Message

/**
 * @author wxx 2018/5/9. 18:36.
 */
object ChatLauncher {
  var connectListener: IConnectListener? = null
  var receiveMessageListener: ((Message) -> Unit)? = null

  fun postConnectSuccess() {
    connectListener?.onConnectSuccess()
  }

  fun postConnectFail(errorCode: ErrorCode = ErrorCode.DIS_CONNECT) {
    connectListener?.onConnectError(errorCode)
  }

  fun postMessageReceive(message: Message) {
    receiveMessageListener?.invoke(message)
  }

}