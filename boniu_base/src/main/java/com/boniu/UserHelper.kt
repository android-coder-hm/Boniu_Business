package com.boniu

import com.adk.getShareValue
import com.adk.putShareValue
import com.boniu.http.UserAccountInfoResBean
import com.google.gson.Gson
import java.util.*

//通用的share文件
private const val SHARE_COMMON_FILE = "shareCommonFile"

//是否是第一次打开应用
private const val SHARE_KEY_IS_FIRST_OPEN = "isFirstOpen"

//网络请求中的TOKEN
private const val SHARE_KEY_TOKEN = "token"

//网络请求的AccountId
private const val SHARE_KEY_ACCOUNT_ID = "accountId"

//是否已经申请注销账号
private const val SHARE_KEY_IS_CANCEL_TOKEN = "isCancelAccount"

//登录的账号信息
private const val SHARE_KEY_LOGIN_ACCOUNT_INFO = "loginAccountInfo"


/*************    是否是第一次打开应用        *****************/
fun isFirstOpen() = getShareValue(SHARE_COMMON_FILE, SHARE_KEY_IS_FIRST_OPEN, true)

fun updateIsFirstOpen() {
    putShareValue(SHARE_COMMON_FILE, SHARE_KEY_IS_FIRST_OPEN, false)
}

/*************    是否已经申请注销账号        *****************/
fun haveCancelAccount() = getShareValue(SHARE_COMMON_FILE, SHARE_KEY_IS_CANCEL_TOKEN, false)

fun setCancelAccount() {
    putShareValue(SHARE_COMMON_FILE, SHARE_KEY_IS_CANCEL_TOKEN, true)
}


/***************  获取AccountId    *******************/
fun getLoginAccountId() = getShareValue(SHARE_COMMON_FILE, SHARE_KEY_ACCOUNT_ID, "")

fun saveLoginAccountId(accountId: String) {
    putShareValue(
        SHARE_COMMON_FILE,
        SHARE_KEY_ACCOUNT_ID, accountId
    )
}

/***************     获取TOKEN      *******************/
fun getToken() = getShareValue(SHARE_COMMON_FILE, SHARE_KEY_TOKEN, "")

fun saveToken(token: String) {
    putShareValue(SHARE_COMMON_FILE, SHARE_KEY_TOKEN, token)
}


/**
 * 获取登录的账户信息
 */
fun getLoginAccountInfo(): UserAccountInfoResBean {
    val data = getShareValue(SHARE_COMMON_FILE, SHARE_KEY_LOGIN_ACCOUNT_INFO, "")
    return Gson().fromJson(data, UserAccountInfoResBean::class.java)
}

fun saveLoginAccountInfo(userAccountInfoResBean: UserAccountInfoResBean?) {
    if (null == userAccountInfoResBean) {
        putShareValue(SHARE_COMMON_FILE, SHARE_KEY_LOGIN_ACCOUNT_INFO, "")
    } else {
        putShareValue(
            SHARE_COMMON_FILE, SHARE_KEY_LOGIN_ACCOUNT_INFO, Gson().toJson(userAccountInfoResBean)
        )
    }
}

/**
 * 是否已经登录
 */
fun haveLogin() = getShareValue(SHARE_COMMON_FILE, SHARE_KEY_LOGIN_ACCOUNT_INFO, "").isNotEmpty()

/**
 * 清楚本地存储的登录信息
 */
fun clearLoginStatus() {
    saveLoginAccountId("")
    saveToken("")
    saveLoginAccountInfo(null)
}

/**
 * 是否是VIP
 */
fun isVip(): Boolean {
    if (haveLogin()) {
        if ("normal" != getLoginAccountInfo().type.toLowerCase(Locale.ROOT)) {
            return true
        }
        return false
    }
    return false
}

/**
 * 是否是永久会员
 */
fun isForeverVip(): Boolean {
    if (haveLogin()) {
        return "FOREVER_VIP" == getLoginAccountInfo().type.toUpperCase(Locale.ROOT)
    }
    return false
}