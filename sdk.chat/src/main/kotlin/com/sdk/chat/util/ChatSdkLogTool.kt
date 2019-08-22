package com.sdk.chat.util

import android.util.Log


internal object ChatSdkLogTool {

  /**
   * 控制台socket信息过滤查看的key
   */
  private val SOCKET_TAG = "数本im_socket"

  /**
   * 控制台socket队列情况
   */
  private val LISTENER_QUEUE_TAG = "数本im_queue"

  /**
   * 输出socket连接的信息
   */
  fun logSocket(str: String) {
    printLog(SOCKET_TAG, str)
  }

  /**
   * 输出socket连接的信息
   */
  fun logListenerQueue(str: String) {
    printLog(LISTENER_QUEUE_TAG, str)
  }

  private fun printLog(tag: String, message: String) {
    //暂时取消这个日志
    Log.e(tag, message)
  }
}
