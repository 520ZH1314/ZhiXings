package com.sdk.chat.message

/**
 * @author wxx 2018/5/11. 18:45.
 */
class TextMessage(var text:String = ""): Message() {
  init {
    content = text
  }
}