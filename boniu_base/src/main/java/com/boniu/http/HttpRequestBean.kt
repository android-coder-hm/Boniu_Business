package com.boniu.http

import com.boniu.*


/**
 * app更新信息请求实体
 */
data class AppUpdateInfoReqBean(
    val appName: String = BoNiuConfig.getAppServerName(),
    val version: String = BoNiuConfig.getVersionName(),
    val uuid: String = getAndroidId(),
    val brand: String = getBrand(),
    val deviceModel: String = getDeviceModel(),
    val deviceType: String = "ANDROID",
    val channel: String = BoNiuConfig.getChannel(),
)

/**
 * 发送短信验证码请求实体
 */
data class SendPhoneVerifyCodeReqBean(
    val mobile: String,
    val appName: String = BoNiuConfig.getAppServerName(),
)

/**
 * 用户登录请求实体
 */
data class UserLoginReqBean(
    val mobile: String,
    val verifyCode: String,
    val appName: String = BoNiuConfig.getAppServerName(),
    val accountId: String = getLoginAccountId(),
    val headImg: String = if (haveLogin()) getLoginAccountInfo().headImg else "",
    val brand: String = getBrand(),
    val channel: String = BoNiuConfig.getChannel(),
    val deviceModel: String = getDeviceModel(),
    val deviceType: String = "ANDROID",
    val uuid: String = getAndroidId(),
    val version: String = BoNiuConfig.getVersionName()
)

/**
 * 账号信息请求实体
 */
data class UserAccountInfoReqBean(
    val appName: String = BoNiuConfig.getAppServerName(),
    val accountId: String = getLoginAccountId(),
    val headImg: String = if (haveLogin()) getLoginAccountInfo().headImg else "",
    val brand: String = getBrand(),
    val channel: String = BoNiuConfig.getChannel(),
    val deviceModel: String = getDeviceModel(),
    val deviceType: String = "ANDROID",
    val uuid: String = getAndroidId(),
    val version: String = BoNiuConfig.getVersionName()
)

/**
 * 注销账号请求实体
 */
data class CancelAccountReqBean(
    val appName: String = BoNiuConfig.getAppServerName(),
    val accountId: String = getLoginAccountId(),
    val headImg: String = if (haveLogin()) getLoginAccountInfo().headImg else "",
    val brand: String = getBrand(),
    val channel: String = BoNiuConfig.getChannel(),
    val deviceModel: String = getDeviceModel(),
    val deviceType: String = "ANDROID",
    val uuid: String = getAndroidId(),
    val version: String = BoNiuConfig.getVersionName()
)

/**
 * 发送反馈请求实体
 */
data class FeedbackReqBean(
    val type: String,
    val content: String,
    val appName: String = BoNiuConfig.getAppServerName(),
    val accountId: String = getLoginAccountId(),
    val headImg: String = if (haveLogin()) getLoginAccountInfo().headImg else "",
    val brand: String = getBrand(),
    val channel: String = BoNiuConfig.getChannel(),
    val deviceModel: String = getDeviceModel(),
    val deviceType: String = "ANDROID",
    val uuid: String = getAndroidId(),
    val version: String = BoNiuConfig.getVersionName()
)

/**
 * 获取AccountId 请求实体
 */
data class GetAccountIdReqBean(
    val mobile: String,
    val token: String,
    val appName: String = BoNiuConfig.getAppServerName(),
    val accountId: String = getLoginAccountId(),
    val headImg: String = if (haveLogin()) getLoginAccountInfo().headImg else "",
    val brand: String = getBrand(),
    val channel: String = BoNiuConfig.getChannel(),
    val deviceModel: String = getDeviceModel(),
    val deviceType: String = "ANDROID",
    val uuid: String = getAndroidId(),
    val version: String = BoNiuConfig.getVersionName()
)

/**
 * 退出登录请求实体
 */
data class QuitLoginReqBean(
    val appName: String = BoNiuConfig.getAppServerName(),
    val accountId: String = getLoginAccountId(),
    val headImg: String = if (haveLogin()) getLoginAccountInfo().headImg else "",
    val brand: String = getBrand(),
    val channel: String = BoNiuConfig.getChannel(),
    val deviceModel: String = getDeviceModel(),
    val deviceType: String = "ANDROID",
    val uuid: String = getAndroidId(),
    val version: String = BoNiuConfig.getVersionName()
)

/**
 * 修改用户信息请求实体
 */
data class UpdateUserInfoReqBean(
    val headImg: String,
    val nickname: String,
    val autograph: String = getLoginAccountInfo().autograph ?: "",
    val birthday: String = getLoginAccountInfo().birthday ?: "",
    val sexual: String = getLoginAccountInfo().sexual ?: "",
    val accountId: String = getLoginAccountId(),
    val appName: String = BoNiuConfig.getAppServerName(),
    val brand: String = getBrand(),
    val channel: String = BoNiuConfig.getChannel(),
    val deviceModel: String = getDeviceModel(),
    val deviceType: String = "ANDROID",
    val uuid: String = getAndroidId(),
    val version: String = BoNiuConfig.getVersionName()
)


/**
 * 获取支付方式
 */
data class PayChannelReqBean(
    val accountId: String = getLoginAccountId(),
    val appName: String = BoNiuConfig.getAppServerName(),
    val brand: String = getBrand(),
    val channel: String = BoNiuConfig.getChannel(),
    val deviceModel: String = getDeviceModel(),
    val deviceType: String = "ANDROID",
    val uuid: String = getAndroidId(),
    val version: String = BoNiuConfig.getVersionName()
)

/**
 * 获取支付方式
 */
data class PayProdcutReqBean(
    val accountId: String = getLoginAccountId(),
    val appName: String = BoNiuConfig.getAppServerName(),
    val brand: String = getBrand(),
    val channel: String = BoNiuConfig.getChannel(),
    val deviceModel: String = getDeviceModel(),
    val deviceType: String = "ANDROID",
    val uuid: String = getAndroidId(),
    val version: String = BoNiuConfig.getVersionName()
)

/**
 * 创建订单
 */
data class CreateOrderReqBean(
    val productId: String,
    val accountId: String = getLoginAccountId(),
    val appName: String = BoNiuConfig.getAppServerName(),
    val brand: String = getBrand(),
    val channel: String = BoNiuConfig.getChannel(),
    val deviceModel: String = getDeviceModel(),
    val deviceType: String = "ANDROID",
    val uuid: String = getAndroidId(),
    val version: String = BoNiuConfig.getVersionName()
)

/**
 * 订单支付信息
 */
data class PayOrderInfoReqBean(
    val orderId: String,
    val payChannel: String,
    val accountId: String = getLoginAccountId(),
    val appName: String = BoNiuConfig.getAppServerName(),
)

/**
 * 订单支付结果查询
 */
data class PayResultReqBean(
    val orderId: String,
    val accountId: String = getLoginAccountId(),
    val appName: String = BoNiuConfig.getAppServerName(),
    val brand: String = getBrand(),
    val channel: String = BoNiuConfig.getChannel(),
    val deviceModel: String = getDeviceModel(),
    val deviceType: String = "ANDROID",
    val uuid: String = getAndroidId(),
    val version: String = BoNiuConfig.getVersionName()
)

