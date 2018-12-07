package com.sdk.chat.server

/**
 * 作者：万祥新 2017/12/20 20:42
 * 每个数据包唯一标示
 */

object PacketNo {
  private var no: Int = 1

  /**
   * 生成唯一的标示
   * @return 标示号
   */
  fun generateNo(): Int {
    synchronized(no) {
      return no++
    }
  }
}
