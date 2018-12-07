package com.wxx.net

abstract class ResponseListModel<T> {
    abstract fun getSource(callback: (HttpResult<MutableList<T>>)->Unit)
}