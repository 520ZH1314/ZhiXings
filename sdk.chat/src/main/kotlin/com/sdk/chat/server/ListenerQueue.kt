package com.sdk.chat.server

import android.os.Handler
import android.os.Looper
import com.sdk.chat.callback.IPacketListener
import com.sdk.chat.contact.ChatSdkConstant
import com.sdk.chat.contact.ErrorCode
import com.sdk.chat.util.ChatSdkLogTool


import java.text.SimpleDateFormat
import java.util.concurrent.ConcurrentHashMap

object ListenerQueue {
  private var stopFlag = false
  private var hasTask = false
  var callbackQueue: MutableMap<Int, IPacketListener<*>> = ConcurrentHashMap()

  private val timerHandler = Handler(Looper.getMainLooper())


  fun onStart() {
    stopFlag = false
    startTimer()
  }

  /**
   * 监听消息发送的时长，为了判断超时
   */
  private fun startTimer() {
    if (!stopFlag && !hasTask) {
      hasTask = true
      timerHandler.postDelayed({
        timerImpl()
        hasTask = false
        startTimer()
      }, (5 * 1000).toLong())
    }
  }

  /**
   * 判断消息超时
   */
  private fun timerImpl() {
    val currentTime = System.currentTimeMillis()

    for ((seqNo, packetListener) in callbackQueue) {

      val timeRange = currentTime - packetListener.createTime

      if (timeRange >= ChatSdkConstant.OUT_TIME_DURATION) {
        ChatSdkLogTool.logListenerQueue("请求号是$seqNo 的协议超时")
        val listener = pop(seqNo)
        listener?.onFail(ErrorCode.TIME_OUT, "")
      }
    }
  }

  fun onDestroy() {
    callbackQueue.clear()
    stopTimer()
  }

  private fun stopTimer() {
    stopFlag = true
  }

  fun push(requestNo: Int, packetListener: IPacketListener<*>?) {
    if (requestNo <= 0 || null == packetListener) {
      ChatSdkLogTool.logListenerQueue("请求号是$requestNo 的socket协议不进入请求队列，因为客户端没监听")
      return
    }
    val simpleDateFormat = SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒")
    ChatSdkLogTool.logListenerQueue("请求号是$requestNo 的socket协议请求进入队列(${simpleDateFormat.format(packetListener.createTime)})")
    callbackQueue.put(requestNo, packetListener)
  }

  fun pop(requestNo: Int): IPacketListener<*>? {
    if (callbackQueue.containsKey(requestNo)) {
      val packetListener = callbackQueue.remove(requestNo)
      ChatSdkLogTool.logListenerQueue("从请求队列中移除$requestNo 成功${SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒").format(System.currentTimeMillis())}")
      return packetListener
    }
    return null
  }

}
