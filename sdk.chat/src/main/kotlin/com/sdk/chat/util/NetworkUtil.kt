package com.sdk.chat.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.telephony.TelephonyManager

object NetworkUtil {

  fun isWifiEnabled(context: Context): Boolean {
    val connectManager = getConnectivityManager(context)
    val mgrTel = context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
    return connectManager.activeNetworkInfo != null && connectManager
        .activeNetworkInfo.state == NetworkInfo.State.CONNECTED || mgrTel
        .networkType == TelephonyManager.NETWORK_TYPE_UMTS
  }


  /**
   * 判断是否打开数据连接
   *
   * @param context
   * @return boolean
   */
  fun isMobile(context: Context): Boolean {
    val networkINfo = getConnectivityManager(context).activeNetworkInfo
    return networkINfo != null && networkINfo.type == ConnectivityManager.TYPE_MOBILE
  }

  fun isWifi(context: Context): Boolean {
    val networkINfo = getConnectivityManager(context).activeNetworkInfo
    return networkINfo != null && networkINfo.type == ConnectivityManager.TYPE_WIFI
  }


  fun isConnectionNet(context: Context): Boolean {
    val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val networkINfo = cm.activeNetworkInfo
    return networkINfo != null
  }

  private fun getConnectivityManager(context: Context): ConnectivityManager {
    return context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
  }
}
