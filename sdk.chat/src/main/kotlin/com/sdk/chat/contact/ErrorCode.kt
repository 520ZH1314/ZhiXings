package com.sdk.chat.contact

enum class ErrorCode {
  UNKNOWN, //未知错误
  PARAMETER_ERROR, //参数错误
  TOKEN_OUT_TIME, //token超时
  DIS_CONNECT, //与服务器断开连接
  INVALID_USER, //无效的用户
  NET_ERROR, //网络错误
  NO_LOGIN,
  INVALID_ROOM,
  ROOM_BLOCK, //封禁
  BANNED, //禁言
  SENSITIVE_WORD, //内容包含了敏感词
  NOT_IN_GROUP,
  TIME_OUT
}
