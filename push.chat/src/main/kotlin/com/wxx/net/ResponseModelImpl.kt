package com.wxx.net

class ResponseModelImpl<T>(private val url: String,
                           private val method: HttpMethod,
                           private val params: ParamsTool? = null,
                           private val classType: Class<T>): ResponseModel<T>() {
    override fun getSource(callback: (HttpResult<T>) -> Unit) {
        HttpEngine.request(method, url, params, callback.wrapSimple(classType))
    }
}