package com.boniu

import com.adk.sendPostJsonRequest
import com.adk.uploadFile
import com.boniu.http.SendPhoneVerifyCodeReqBean
import com.boniu.http.*
import com.google.gson.Gson
import java.io.File


// 获取App更新信息
fun getAppUpdateInfo(): AppUpdateInfoResBean {
    val result = sendPostJsonRequest(getAppUpdateInfoUrl(), Gson().toJson(AppUpdateInfoReqBean()))
    return result.parseHttpResponseFromJsonObj(AppUpdateInfoResBean::class.java).parseServerResult()
}


//发送短信验证码
fun sendVerifyCode(sendPhoneVerifyCodeReqBean: SendPhoneVerifyCodeReqBean) {
    val result = sendPostJsonRequest(getSendVerifyCodeUrl(), Gson().toJson(sendPhoneVerifyCodeReqBean))
    result.parseHttpResponse<Boolean>().parseServerResult()
}


//用户登录
fun userLogin(userLoginReqBean: UserLoginReqBean): UserLoginResBean {
    val result = sendPostJsonRequest(getUserLoginUrl(), Gson().toJson(userLoginReqBean))
    return result.parseHttpResponseFromJsonObj(UserLoginResBean::class.java).parseServerResult()
}

//获取账户信息
fun getAccountInfo(userAccountInfoReqBean: UserAccountInfoReqBean): UserAccountInfoResBean {
    val result = sendPostJsonRequest(getUserAccountInfoUrl(), Gson().toJson(userAccountInfoReqBean))
    return result.parseHttpResponseFromJsonObj(UserAccountInfoResBean::class.java).parseServerResult()
}

//注销账号
fun cancelAccount(cancelAccountReqBean: CancelAccountReqBean): CancelAccountResBean {
    val result = sendPostJsonRequest(getCancelAccountUrl(), Gson().toJson(cancelAccountReqBean))
    return result.parseHttpResponseFromJsonObj(CancelAccountResBean::class.java).parseServerResult()
}


//发送反馈
fun sendFeedBack(feedbackReqBean: FeedbackReqBean) {
    val result = sendPostJsonRequest(getAddFeedbackUrl(), Gson().toJson(feedbackReqBean))
    result.parseHttpResponse<Boolean>().parseServerResult()
}

//退出登录
fun quitLogin(quitLoginReqBean: QuitLoginReqBean) {
    val result = sendPostJsonRequest(getUserLogoutUrl(), Gson().toJson(quitLoginReqBean))
    result.parseHttpResponse<Boolean>().parseServerResult()

}

//更新用户信息
fun updateUserInfo(updateUserInfoReqBean: UpdateUserInfoReqBean) {
    val result = sendPostJsonRequest(getEditAccountInfoUrl(), Gson().toJson(updateUserInfoReqBean))
    result.parseHttpResponse<Boolean>().parseServerResult()
}


//头像上传
fun uploadHeadImage(filePath: String, folderType: String, appName: String): String {
    val result = uploadFile(getUploadFileUrl(folderType, appName), File(filePath), "multipartFile")
    return result.parseHttpResponse<String>().parseServerResult()
}

/**
 * 重新获取AccountId
 */
fun getAccountId(): String {
    val result = sendPostJsonRequest(getGetNewAccountIdUrl(), Gson().toJson(GetAccountIdReqBean(getLoginAccountInfo().mobile, getToken())))
    return result.parseHttpResponse<String>().parseServerResult()
}

/**
 * 获取支付方式
 */
fun getPayChannelList(): List<PayChannelResBean> {
    val result = sendPostJsonRequest(getPayChannel(), Gson().toJson(PayChannelReqBean()))
    return result.parseHttpResponseFromJsonArray(PayChannelResBean::class.java).parseServerResult()
}

/**
 * 获取支付的产品信息
 */
fun getPayProductInfo(): List<ProductInfo> {
    val result = sendPostJsonRequest(getPayProductInfoUrl(), Gson().toJson(PayProdcutReqBean()))
    return result.parseHttpResponseFromJsonArray(ProductInfo::class.java).parseServerResult()
}

/**
 * 创建订单
 */
fun createOrder(productId: String): String {
    val result = sendPostJsonRequest(getCreateOrderUrl(), Gson().toJson(CreateOrderReqBean(productId)))
    return result.parseHttpResponse<String>().parseServerResult()
}

/**
 * 获取订单支付信息
 */
fun getOrderPayInfo(orderId: String, payChannel: String): WechatPayInfo {
    val result = sendPostJsonRequest(getOrderPayInfoUrl(), Gson().toJson(PayOrderInfoReqBean(orderId, payChannel)))
    val wechatPayInfoString = result.parseHttpResponseFromJsonObj(WechatPayOrder::class.java).parseServerResult().payInfo
    return Gson().fromJson(wechatPayInfoString, WechatPayInfo::class.java)
}

/**
 * 获取订单支付结果
 */
fun queryPayResult(orderId: String) {
    sendPostJsonRequest(getPayResultUrl(), Gson().toJson(PayResultReqBean(orderId)))
}
