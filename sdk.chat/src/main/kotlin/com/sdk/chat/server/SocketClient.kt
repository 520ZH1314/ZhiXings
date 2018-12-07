package com.sdk.chat.server

import android.os.Handler
import android.os.Looper
import com.google.gson.Gson
import com.sdk.chat.callback.IPacketListener
import com.sdk.chat.contact.ChatSdkConstant
import com.sdk.chat.contact.ErrorCode
import com.sdk.chat.util.ChatSdkLogTool
import io.netty.bootstrap.Bootstrap
import io.netty.channel.*
import io.netty.channel.nio.NioEventLoopGroup
import io.netty.channel.socket.nio.NioSocketChannel
import io.netty.handler.codec.string.StringDecoder
import io.netty.handler.codec.string.StringEncoder
import java.nio.charset.Charset


class SocketClient {
  var currentChannel: Channel? = null
  private var bootstrap: Bootstrap = Bootstrap()
  private var reconnectDelay = 2
  private var isConnecting: Boolean = false
  private val handler = Handler(Looper.getMainLooper())
  private val connectRunnable = Runnable { doConnect() }

  init {
    bootstrap.group(NioEventLoopGroup()).channel(NioSocketChannel::class.java)
    bootstrap.handler(object : ChannelInitializer<Channel>() {
      @Throws(Exception::class)
      override fun initChannel(ch: Channel) {
          val pipeline = ch.pipeline()
          pipeline.addLast(StringDecoder(Charset.forName("GBK")))
          pipeline.addLast(StringEncoder(Charset.forName("GBK")))
          pipeline.addLast(MsgServerHandler())
      }
    })
    bootstrap.option(ChannelOption.TCP_NODELAY, true)
    bootstrap.option(ChannelOption.SO_KEEPALIVE, true)
    bootstrap.option(ChannelOption.CONNECT_TIMEOUT_MILLIS, ChatSdkConstant.CONNECT_TIMEOUT_MILLIS)
  }

  fun doConnect() {
    if (isConnecting) {
      return
    }
    val channelFuture = bootstrap.connect(SdkConfig.IP, SdkConfig.PORT)
    //ChatSdkLogTool.logSocket("连接服务器中..."+SdkConfig.IP+"==="+SdkConfig.PORT)
    isConnecting = true
    channelFuture.addListener(object : ChannelFutureListener {
      override fun operationComplete(future: ChannelFuture?) {
        isConnecting = false
        if (future?.isSuccess() ?: false) {
          currentChannel = future?.channel()
          resetReconnectConfig()
        } else {
          //ChatSdkLogTool.logSocket("连接服务器失败，${reconnectDelay}秒后重连")
          if (reconnectDelay * 1000 < ChatSdkConstant.MAX_CONNECT_TIME) {
            handler.postDelayed(connectRunnable, (reconnectDelay * 1000).toLong())
            //取消时间限制
            //reconnectDelay = reconnectDelay * 2
          } else {
            resetReconnectConfig()
          }
        }
      }
    })
  }

  /**
   * 强制重连，例如手机监听到网络正常了
   */
  fun coerciveReconnection() {
    currentChannel?.disconnect()
    doConnect()
    /*ChatSdkLogTool.logSocket("强制重连了")
    resetReconnectConfig()
    handler.post(connectRunnable)*/
  }

  fun resetReconnectConfig() {
    handler.removeCallbacks(connectRunnable)
    reconnectDelay = 2
  }

  fun sendString(dataBuf: DataBuf, listener: IPacketListener<Any>?) {
    if (currentChannel == null) {
      listener?.onFail(ErrorCode.DIS_CONNECT, "已经与服务器断开连接")
      return
    }

    ListenerQueue.push(dataBuf.seq, listener)
    currentChannel?.writeAndFlush(Gson().toJson(dataBuf))?.addListener { future ->
      if (!future.isSuccess) {
        listener?.onFail(ErrorCode.DIS_CONNECT, "")
        ListenerQueue.pop(dataBuf.seq)
      }
    }
  }

  /**
   * 提供接口关闭socket连接
   */
  fun closeChannel() {
    ChatSdkLogTool.logSocket("推送服务断开...")
    if (currentChannel != null && currentChannel!!.isActive) {
      currentChannel!!.close()
    }
    currentChannel = null
  }

  fun isChannelOk() = (currentChannel != null && currentChannel!!.isActive && currentChannel!!.isOpen)
}
