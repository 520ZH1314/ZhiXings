package com.wxx.net

/**
 * @author wxx 2018/3/26. 16:35.
 */
enum class ErrorCode {
  /**
   * 超时
   */
  TIME_OUT,
  /**
   * 连接服务器失败
   */
  CONNECT_FAIL,
  /**
   * 未知错误
   */
  UNKNOWN,
  /**
   * 解析错误
   */
  PARSE_FAIL
}