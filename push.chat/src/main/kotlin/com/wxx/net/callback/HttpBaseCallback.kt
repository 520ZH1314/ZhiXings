package com.wxx.net.callback

import android.text.TextUtils
import com.wxx.net.ErrorCode
/**
 * @author wxx
 */

abstract class HttpBaseCallback<T> {

  /**
   * 请求失败(包括服务器响应success为false)
   * @param errorCode 错误码[ErrorCode]
   * @param errorMessage 错误的具体信息
   */
  abstract fun onFailure(errorCode: ErrorCode, errorMessage: String?)

  /**
   * 得到服务器的响应
   */
  open fun onResponse(response: String) {
    if (TextUtils.isEmpty(response)) {
      return
    }
  }
}
