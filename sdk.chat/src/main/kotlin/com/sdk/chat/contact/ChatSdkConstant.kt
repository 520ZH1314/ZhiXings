package com.sdk.chat.contact

object ChatSdkConstant {
    /**
     * 每次获取历史消息的数目
     */
    val CHAT_ROOM_INIT_MSG_SIZE = 50

    /**
     * 聊天室最多获取最新的50条数据
     */
    val MSG_COUNT_PER_PAGE = 20

    /**
     * 连接服务器的超时时间（单位:毫秒）
     */
    val CONNECT_TIMEOUT_MILLIS = 15 * 1000

    /**
     * 发送消息的超时时间
     */
    val OUT_TIME_DURATION = (10 * 1000).toLong()

    /**
     * 连接60分钟要是还连接不上，那就不再尝试连接了
     */
    val MAX_CONNECT_TIME = (60 * 60 * 1000).toLong()
}
