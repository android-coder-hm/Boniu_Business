package com.boniu

import android.os.Build
import com.adk.getDefaultOkHttpClient
import com.boniu.http.AccountIdAndTokenExpireInterceptor
import okhttp3.OkHttpClient
import java.util.*

fun getAndroidId(): String {
    return UUID.randomUUID().toString()
}

fun getBrand(): String {
    return Build.BRAND
}

fun getDeviceModel(): String {
    return Build.MODEL
}

fun getBoNiuOkHttpClient(): OkHttpClient {
    return getDefaultOkHttpClient().newBuilder().addInterceptor(AccountIdAndTokenExpireInterceptor()).build()
}