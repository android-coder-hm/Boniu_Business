package com.boniu.http

import com.google.gson.annotations.SerializedName

data class HttpResponseBean<out T>(
    val errorCode: String,
    val errorMsg: String,
    val errorStack: String,
    val returnCode: String,
    val success: Boolean,
    val timeOut: Boolean,
    val result: T
)

data class UserLoginResBean(
    val isNew: String,
    val syncStatus: String,
    val token: String,
    val accountId: String
)

data class UserAccountInfoResBean(
    val applyCancelTime: String,
    val autograph: String?,
    val birthday: String?,
    val email: String?,
    val headImg: String,
    val inviteCode: String,
    val mobile: String,
    val nickname: String,
    val registerTime: String,
    val sexual: String?,
    val type: String,
    val vipExpireDays: String,
    val vipExpireTime: String
)

data class CancelAccountResBean(
    val applyTime: String,
    val day: Int,
    val finishTime: String,
    val mobile: String
)

//支付方式
data class PayChannelResBean(val desc: String, val type: String)

//支付的产品信息VO
data class ProductInfo(
    val alipayPrice: Double, val autoType: String, val days: Int,
    val discountPrice: Double, val goodsId: String, val price: Double,
    val productId: String, val productName: String, val remarks: String,
    val tag: String, val type: String, val wechatPrice: Double
)


data class WechatPayOrder(
    val resultCode: String,
    val resultMsg: String,
    val payInfo: String
)

//微信支付Vo
data class WechatPayInfo(
    val packageValue: String,
    @SerializedName("appid")
    val appID: String,
    val sign: String,
    val partnerid: String,
    val prepayid: String,
    val noncestr: String,
    val timestamp: String
)

/**
 * app更新信息
 */
data class VersionInfo(
    val title: String, val version: String, val content: String, val linkUrl: String,
    val forceUp: Boolean, val pwcixo: String, val marketFlag: String
)

data class AppUpdateInfoResBean(val versionInfoVo: VersionInfo)
