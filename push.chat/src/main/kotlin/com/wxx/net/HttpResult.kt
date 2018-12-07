package com.wxx.net

data class HttpResult<T>(var success: Boolean = true) {
    var code = 0
    var message = ""
    var data: T?  = null
}