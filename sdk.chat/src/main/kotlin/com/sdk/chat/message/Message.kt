package com.sdk.chat.message

open class Message {
  var localId: Long? = null
  var messageId: Long = 0

  /**
   * 消息类型(文本、语音等)
   */
  var type: Int? = null

  /**
   * 消息状态(正常、回撤、失败等)
   */
  var state: Int? = null

  /**
   * 消息创建的时间(服务器给的时间戳是秒为单位、该字段在接收到服务器消息已经转换成毫秒)
   */
  var created: Long? = null

  /**
   * 消息的创建者
   */
  var from: String? = null

  /**
   * 消息的接受者(群聊是群组的Id)
   */
  var to: String? = null

  /**
   * 聊天类型（聊天室、群聊、单聊）
   */
  var chatType: Int? = null

  /**
   * 消息的内容，该字段是json字符串、不同的消息类型其json字符串不同
   */
  var content: String? = null

}
