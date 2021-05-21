package com.boniu.http

import com.boniu.BoNiuConfig


//发送短信验证码
private const val SEND_VERIFY_CODE = "standard/account/sendVerifyCode"

//用户登录
private const val USER_LOGIN = "standard/account/login"

//获取账户信息
private const val USER_ACCOUNT_INFO = "standard/account/getAccountInfo"

//编辑账号信息
private const val EDIT_ACCOUNT_INFO = "standard/account/editAccountInfo"

//用户登出
private const val USER_LOGOUT = "standard/account/logout"

//注销账号
private const val CANCEL_ACCOUNT = "standard/account/cancelAccount"

//发送反馈
private const val ADD_FEEDBACK = "standard/common/addFeedback"

//获取新的AccountId
private const val GET_NEW_ACCOUNT_ID = "standard/account/getNewAccountId"

//文件上传
private const val UPLOAD_FILE = "standard/common/uploadFile"

//获取支付方式
private const val PAY_CHANNEL = "standard/product/payChannel"

//支付价格信息
private const val PAY_PRODUCT_INFO = "standard/product/productList"

// 创建订单
private const val CREATE_ORDER = "standard/order/create"

//
// 订单支付信息
private const val ORDER_PAY_INFO = "standard/order/submitOrder"

//
//订单支付结果查询
private const val QUERY_PAY_RESULT = "standard/order/queryPayOrder"

// app更新信息请求
private const val APP_UPDATE_INFO = "standard/common/base"

/********************************** 以下为暴露的接口 ********************************************/

fun getSendVerifyCodeUrl() = BoNiuConfig.getBaseUrl() + SEND_VERIFY_CODE
fun getUserLoginUrl() = BoNiuConfig.getBaseUrl() + USER_LOGIN
fun getUserAccountInfoUrl() = BoNiuConfig.getBaseUrl() + USER_ACCOUNT_INFO
fun getEditAccountInfoUrl() = BoNiuConfig.getBaseUrl() + EDIT_ACCOUNT_INFO
fun getUserLogoutUrl() = BoNiuConfig.getBaseUrl() + USER_LOGOUT
fun getCancelAccountUrl() = BoNiuConfig.getBaseUrl() + CANCEL_ACCOUNT
fun getAddFeedbackUrl() = BoNiuConfig.getBaseUrl() + ADD_FEEDBACK
fun getGetNewAccountIdUrl() = BoNiuConfig.getBaseUrl() + GET_NEW_ACCOUNT_ID
fun getUploadFileUrl(folderType: String, appName: String) = BoNiuConfig.getBaseUrl() + UPLOAD_FILE + "?folderType=$folderType&appName=$appName"
fun getPayChannel() = BoNiuConfig.getBaseUrl() + PAY_CHANNEL
fun getPayProductInfoUrl() = BoNiuConfig.getBaseUrl() + PAY_PRODUCT_INFO
fun getCreateOrderUrl() = BoNiuConfig.getBaseUrl() + CREATE_ORDER
fun getOrderPayInfoUrl() = BoNiuConfig.getBaseUrl() + ORDER_PAY_INFO
fun getPayResultUrl() = BoNiuConfig.getBaseUrl() + QUERY_PAY_RESULT
fun getAppUpdateInfoUrl() = BoNiuConfig.getBaseUrl() + APP_UPDATE_INFO

