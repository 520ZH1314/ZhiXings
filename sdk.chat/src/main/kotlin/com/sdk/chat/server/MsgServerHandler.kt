package com.sdk.chat.server

import com.sdk.chat.ChatLauncher
import com.sdk.chat.message.TextMessage
import com.sdk.chat.util.ChatSdkLogTool
import io.netty.channel.ChannelHandlerContext
import io.netty.channel.SimpleChannelInboundHandler

class MsgServerHandler : SimpleChannelInboundHandler<String>() {
  @Throws(Exception::class)
  override fun channelRead0(channelHandlerContext: ChannelHandlerContext, byteBuf: String) {
    decode(byteBuf)
  }

  override fun exceptionCaught(ctx: ChannelHandlerContext, cause: Throwable) {
    ctx.close()
  }

  @Throws(Exception::class)
  override fun channelInactive(ctx: ChannelHandlerContext) {
    super.channelInactive(ctx)
    ChatSdkLogTool.logSocket("与服务器断开连接")
    ChatLauncher.postConnectFail()
  }

  @Throws(Exception::class)
  override fun channelActive(ctx: ChannelHandlerContext) {
    super.channelActive(ctx)
    ChatSdkLogTool.logSocket("连接服务器成功")
    ChatLauncher.postConnectSuccess()
  }

  /**
   * 收到数据解析
   */
  private fun decode(dataBuf: String?) {
    ChatSdkLogTool.logSocket("收到服务器端额数据:$dataBuf")
    val message = TextMessage(dataBuf ?: "")
//    AppExecutors.runOnMainThread {
      ChatLauncher.postMessageReceive(message)
//    }
  }
}
