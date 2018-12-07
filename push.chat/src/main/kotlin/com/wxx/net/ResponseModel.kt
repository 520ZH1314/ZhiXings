package com.wxx.net

abstract class ResponseModel<T> {
    abstract fun getSource(callback: (HttpResult<T>)->Unit)
}