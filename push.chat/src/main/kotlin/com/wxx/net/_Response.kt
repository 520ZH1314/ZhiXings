package com.wxx.net

import android.text.TextUtils
import com.google.gson.Gson
import com.google.gson.JsonParser
import com.wxx.net.callback.HttpBaseCallback
import org.json.JSONObject

val gson by lazy { Gson() }

fun<T> ((HttpResult<T>)->Unit).wrapSimple(classType: Class<T>):HttpBaseCallback<T>{
    return object : HttpBaseCallback<T>(){
        override fun onFailure(errorCode: ErrorCode, errorMessage: String?) {
            val httpResult = HttpResult<T>(false)
            httpResult.message = errorMessage ?: ""
            this@wrapSimple.invoke(httpResult)
        }

        override fun onResponse(response: String) {
            super.onResponse(response)
            val httpResult = HttpResult<T>()
            if (TextUtils.isEmpty(response)){
                this@wrapSimple.invoke(httpResult)
                return
            }

            try {
                val jsonObject = JSONObject(response)
                httpResult.code = jsonObject.optInt(CommonKeys.CODE)
                httpResult.success = httpResult.code == 200
                httpResult.message = jsonObject.optString(CommonKeys.MESSAGE)
                if(httpResult.success){
                    val dataStr = jsonObject.optString(CommonKeys.DATA)
                    if (classType == String::class.java){
                        httpResult.data = dataStr as T
                    }else {
                        httpResult.data = gson.fromJson(dataStr, classType)
                    }
                }
            }catch (e: Exception){
                httpResult.success = false
                httpResult.message ="数据解析异常"
            }finally {
                this@wrapSimple.invoke(httpResult)
            }
        }

    }
}

fun ((HttpResult<String>)->Unit).wrapOriginalResponse():HttpBaseCallback<String>{
    return object : HttpBaseCallback<String>(){
        override fun onFailure(errorCode: ErrorCode, errorMessage: String?) {
            val httpResult = HttpResult<String>(false)
            httpResult.message = errorMessage ?: ""
            this@wrapOriginalResponse.invoke(httpResult)
        }

        override fun onResponse(response: String) {
            super.onResponse(response)
            val httpResult = HttpResult<String>()
            httpResult.data = response
            this@wrapOriginalResponse.invoke(httpResult)
        }

    }
}


fun<T> ((HttpResult<MutableList<T>>)->Unit).wrapList(classType: Class<T>):HttpBaseCallback<T>{
    return object : HttpBaseCallback<T>(){
        override fun onFailure(errorCode: ErrorCode, errorMessage: String?) {
            val httpResult = HttpResult<MutableList<T>>(false)
            httpResult.message = errorMessage ?: ""
            this@wrapList.invoke(httpResult)
        }

        override fun onResponse(response: String) {
            super.onResponse(response)
            val httpResult = HttpResult<MutableList<T>>()
            if (TextUtils.isEmpty(response)){
                this@wrapList.invoke(httpResult)
                return
            }

            try {
                val jsonObject = JSONObject(response)
                httpResult.code = jsonObject.optInt(CommonKeys.CODE)
                httpResult.success = httpResult.code == 200
                httpResult.message = jsonObject.optString(CommonKeys.MESSAGE)
                if(httpResult.success){
                    val dataStr = jsonObject.optString(CommonKeys.DATA)
                    val jsonArray = JsonParser().parse(dataStr).asJsonArray
                    val dataList = mutableListOf<T>()
                    jsonArray?.forEach {
                        dataList.add(gson.fromJson(it.toString(), classType))
                    }
                    httpResult.data = dataList
                }
            }catch (e: Exception){
                httpResult.success = false
                httpResult.message = "数据解析异常"
            }finally {
                this@wrapList.invoke(httpResult)
            }
        }

    }
}