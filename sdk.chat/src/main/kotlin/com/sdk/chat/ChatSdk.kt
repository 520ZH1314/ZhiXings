package com.sdk.chat

import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import com.google.gson.Gson
import com.sdk.chat.callback.IConnectListener
import com.sdk.chat.callback.IPacketListener
import com.sdk.chat.message.Message
import com.sdk.chat.receiver.ChatSdkReceiver
import com.sdk.chat.server.DataBuf
import com.sdk.chat.server.SocketClient

/**
 * @author wxx
 */
object ChatSdk {
    private val socketClient by lazy { SocketClient() }
    var receiver: ChatSdkReceiver? = null

    @JvmStatic
    fun init(context: Context) {
        receiver = ChatSdkReceiver()
        val intentFilter = IntentFilter()
        intentFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION)
        intentFilter.addAction(Intent.ACTION_SCREEN_ON)
        context.registerReceiver(receiver, intentFilter)
        connect()
    }
    @JvmStatic
    fun close(){
        socketClient.closeChannel();
    }
    @JvmStatic
    fun connect() {
        socketClient.doConnect()
    }

    @JvmStatic
    fun setReceiveMessageListener(listener: (Message) -> Unit) {
        ChatLauncher.receiveMessageListener = listener
    }

    @JvmStatic
    fun setConnectListener(listener: IConnectListener) {
        ChatLauncher.connectListener = listener
    }

    private fun sendMessage(message: String, listener: IPacketListener<Any>?) {
//        socketClient.sendString(message, listener)
    }

    fun sendDataBuf(message: DataBuf, listener: IPacketListener<Any>?) {
//        sendMessage(Gson().toJson(message), listener)
        socketClient.sendString(message, null)
    }
    @JvmStatic
    fun isConnectSuccess() = socketClient.isChannelOk()

    fun coerciveReconnection(){
        socketClient.coerciveReconnection()
    }
}