package com.sdk.chat.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import com.sdk.chat.ChatSdk

import com.sdk.chat.util.ChatSdkLogTool
import com.sdk.chat.util.NetworkUtil

class ChatSdkReceiver : BroadcastReceiver() {
  //程序每次启动会触发改网络改变的广播
  private var isFirst = true

  override fun onReceive(context: Context, intent: Intent) {
    val action = intent.action
//    ChatSdkLogTool.logSocket("收到系统广播$action")

    if (ConnectivityManager.CONNECTIVITY_ACTION == action) {
      //网络变化
      val isConnect = NetworkUtil.isConnectionNet(context)
      if (isFirst) {
        isFirst = false
        return
      }
      if (isConnect) {
        ChatSdk.coerciveReconnection()
      }
    } else if (Intent.ACTION_SCREEN_ON == action) {
      //屏幕唤醒
      if (!ChatSdk.isConnectSuccess()) {
        ChatSdk.coerciveReconnection()
      }
    }
  }
}
