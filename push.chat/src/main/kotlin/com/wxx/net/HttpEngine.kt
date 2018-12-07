package com.wxx.net

import android.os.Handler
import android.os.Looper
import com.google.gson.GsonBuilder
import com.wxx.net.callback.HttpBaseCallback
import okhttp3.*
import java.io.IOException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import java.util.concurrent.TimeUnit

/**
 * @author wxx
 */

object HttpEngine {
  val handler by lazy { Handler(Looper.getMainLooper()) }
  private val httpClient by lazy {
    OkHttpClient.Builder().apply {
      connectTimeout(10, TimeUnit.SECONDS)
      writeTimeout(40, TimeUnit.SECONDS)
      readTimeout(20, TimeUnit.SECONDS)
    }.build()
  }
  /**
   * 将参数拼接到URL中
   */
  private fun genUrl(url: String, params: ParamsTool?): String{
    val urlStringBuilder = StringBuffer(url)

    urlStringBuilder.append("?")

    params?.params?.forEach {
      urlStringBuilder.apply {
        append(it.key)
        append("=")
        append(it.value)
        append("&")
      }
    }
    urlStringBuilder.deleteCharAt(urlStringBuilder.lastIndex)
    return urlStringBuilder.toString()
  }

  private fun genRequestBody(paramsTool: ParamsTool?): RequestBody{
    return RequestBody.create(MediaType.parse("application/json; charset=utf-8"), genJsonString(paramsTool))
  }

  private fun genJsonString(paramsTool: ParamsTool?): String{
    val jsonParams =paramsTool?.let {
      GsonBuilder().disableHtmlEscaping().create().toJson(it.params)
    }
    return jsonParams ?: ""
  }

  @JvmStatic
  fun request(method: HttpMethod, url: String, params: ParamsTool?, callback: HttpBaseCallback<*>){
    when(method){
      HttpMethod.GET -> { get(url, params, callback) }
      HttpMethod.POST -> { postJson(url, params, callback) }
      HttpMethod.PUT -> { put(url, params, callback) }
      HttpMethod.DELETE -> { delete(url, params, callback) }
    }
  }

  /**
   * Get请求
   */
  @JvmStatic
  fun get(url: String, params: ParamsTool?, callback: HttpBaseCallback<*>){
    val request = Request.Builder().url(genUrl(url, params)).get().build()
    execute(request, params, callback)
  }

  /**
   * Delete请求
   */
  @JvmStatic
  fun delete(url: String, params: ParamsTool?, callback: HttpBaseCallback<*>){
    val requestBody = genRequestBody(params)
    val request = Request.Builder().url(url).delete(requestBody).build()

    execute(request, params, callback)
  }

  /**
   * Put请求
   */
  @JvmStatic
  fun put(url: String, params: ParamsTool?, callback: HttpBaseCallback<*>){
    val requestBody = genRequestBody(params)
    val request = Request.Builder().url(url).put(requestBody).build()

    execute(request, params, callback)
  }

  /**
   * post一段json字符串给服务器
   */
  @JvmStatic
  fun postJson(url: String, params: ParamsTool?, callback: HttpBaseCallback<*>) {
    val requestBody = genRequestBody(params)
    val request = Request.Builder().url(url).post(requestBody).build()

    execute(request, params, callback)
  }

  /**
   * 发起网络请求
   * @return 返回call，call可以取消发送请求
   */
  private fun execute(request: Request, params: ParamsTool?, callback: HttpBaseCallback<*>?): Call {
    val call = httpClient.newCall(request)
    call.enqueue(object : Callback {
      override fun onFailure(call: Call, e: IOException) {
        e.printStackTrace()
        //切换到主线程
        handler.post {
          when (e) {
            is SocketTimeoutException -> {
              callback?.onFailure(ErrorCode.TIME_OUT, "链接服务器超时")
            }
            is UnknownHostException -> {
              callback?.onFailure(ErrorCode.CONNECT_FAIL, "未知的的主机")
            }
            is ConnectException -> {
              callback?.onFailure(ErrorCode.CONNECT_FAIL, "连接失败")
            }
            else -> {
              callback?.onFailure(ErrorCode.CONNECT_FAIL, "连接服务器失败")
            }
          }
        }
      }

      @Throws(IOException::class)
      override fun onResponse(call: Call, response: Response) {
        val responseBody = response.body()
        val result = responseBody?.string()
        handler.post {
          callback?.onResponse(result ?: "")
        }
      }
    })
    return call
  }
}
