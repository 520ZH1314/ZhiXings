package com.wxx.net

/**
 * @author wxx
 */
class ParamsTool{
  val params by lazy { HashMap<String, Any?>() }

  fun setParams(key: String, value: Any?): ParamsTool {
    params[key] = value
    return this
  }
}