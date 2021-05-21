package com.boniu.http

import com.adk.HttpException
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import ikidou.reflect.TypeBuilder
import java.io.StringReader
import java.lang.reflect.Type


/**
 * 当结果时候对象时调用此方法
 */
fun <T> String.parseHttpResponseFromJsonObj(clazz: Class<T>): HttpResponseBean<T> {
    val type: Type = TypeBuilder.newInstance(HttpResponseBean::class.java)
        .addTypeParam(clazz)
        .build()

    return Gson().fromJson(StringReader(this), type)
}


/**
 * 当结果是集合时调用此方法
 */
fun <T> String.parseHttpResponseFromJsonArray(clazz: Class<T>): HttpResponseBean<List<T>> {
    val type: Type = TypeBuilder.newInstance(HttpResponseBean::class.java)
        .beginSubType(List::class.java)
        .addTypeParam(clazz)
        .endSubType()
        .build()
    return Gson().fromJson(StringReader(this), type)
}

/**
 * 当结果是基本数据类型时  调用此方法
 */
fun <T> String.parseHttpResponse(): HttpResponseBean<T> =
    Gson().fromJson(this, object : TypeToken<HttpResponseBean<T>>() {}.type)


fun <T> HttpResponseBean<T>.parseServerResult(): T {
    if (this.success) {
        return this.result
    } else {
        throw HttpException(this.errorCode, this.errorMsg)
    }
}
