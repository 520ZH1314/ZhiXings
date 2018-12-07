package com.wxx.net

class ResponseListModelImpl<T>(private val url: String,
                               private val method: HttpMethod,
                               private val params: ParamsTool? = null,
                               private val classType: Class<T>): ResponseListModel<T>() {
    override fun getSource(callback: (HttpResult<MutableList<T>>) -> Unit) {
        HttpEngine.request(method, url, params, callback.wrapList(classType))
    }

}