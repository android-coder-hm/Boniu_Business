package com.boniu

import androidx.lifecycle.MutableLiveData
import com.adk.base.BaseViewModel
import com.boniu.http.*

/**
 * 博牛的业务都在这里面
 */
class BoNiuViewModel : BaseViewModel() {

    // 是否需要显示广告
    val needShowAdLiveData = MutableLiveData<Boolean>()

    // 短信验证码发送结果
    val sendSmsVerifyCodeLiveData = MutableLiveData<Boolean>()

    // 登录结果
    val loginLiveData = MutableLiveData<Boolean>()

    // 退出登录
    val quitLoginLiveData = MutableLiveData<Boolean>()

    // 注销账号
    val cancelAccountLiveData = MutableLiveData<CancelAccountResBean>()

    // 更新用户信息
    val updateUserInfoLiveData = MutableLiveData<Boolean>()

    // 发送反馈
    val sendFeedbackLiveData = MutableLiveData<Boolean>()

    // 版本升级信息
    val appUpdateInfoLiveData = MutableLiveData<AppUpdateInfoResBean>()

    // 支付方式
    val payChannelListLiveData = MutableLiveData<List<PayChannelResBean>>()

    // 支付产品信息
    val payProductInfoLiveData = MutableLiveData<List<ProductInfo>>()

    // 支付信息
    val payInfoLiveData = MutableLiveData<Map<String, Any>>()

    // 支付结果查询
    val payResultLiveData = MutableLiveData<Boolean>()


    /**
     * 检测是否需要显示开屏广告
     */
    fun checkNeedShowSplashAd() {
        wrapExecute(bloc = {
            val appUpdateInfoResBean = getAppUpdateInfo()
            needShowAdLiveData.postValue(BoNiuConfig.getVersionName() == appUpdateInfoResBean.versionInfoVo.version)
        })
    }

    /**
     * 发送短信验证码
     */
    fun sendSmsVerifyCode(phone: String) {
        wrapExecute({
            sendVerifyCode(SendPhoneVerifyCodeReqBean(phone))
            sendSmsVerifyCodeLiveData.postValue(false)
        })
    }

    /**
     * 用户登录
     */
    fun login(phone: String, smsVerifyCode: String) {
        wrapExecute({
            val loginResult = userLogin(UserLoginReqBean(phone, smsVerifyCode))
            saveLoginAccountId(loginResult.accountId)
            saveToken(loginResult.token)
            val accountInfo = getAccountInfo(UserAccountInfoReqBean())
            saveLoginAccountInfo(accountInfo)
            loginLiveData.postValue(true)
        })
    }

    /**
     * 退出登录
     */
    fun quit() {
        wrapExecute({
            quitLogin(QuitLoginReqBean())
            clearLoginStatus()
            quitLoginLiveData.postValue(true)
        })
    }

    /**
     * 注销账户
     */
    fun destoryAccount() {
        wrapExecute({
            val result = cancelAccount(CancelAccountReqBean())
            setCancelAccount()
            cancelAccountLiveData.postValue(result)
        })
    }


    /**
     * 获取版本更新信息
     */
    fun checkAppUpdate() {
        wrapExecute({
            appUpdateInfoLiveData.postValue(getAppUpdateInfo())
        }, showLoading = false)
    }

    /**
     * 更新用户昵称
     */
    fun updateNickName(nickName: String) {
        wrapExecute({
            updateUserInfo(UpdateUserInfoReqBean(getLoginAccountInfo().headImg, nickName))
            val result = getAccountInfo(UserAccountInfoReqBean())
            saveLoginAccountInfo(result)
            updateUserInfoLiveData.postValue(true)
        })
    }

    /**
     * 更新用户头像
     */
    fun updateUserHead(filePath: String, folderType: String, appName: String) {
        wrapExecute({
            val headUrl = uploadHeadImage(filePath, folderType, appName)
            updateUserInfo(UpdateUserInfoReqBean(headUrl, getLoginAccountInfo().nickname))
            val result = getAccountInfo(UserAccountInfoReqBean())
            saveLoginAccountInfo(result)
            updateUserInfoLiveData.value = true
        })
    }

    /**
     * 发送反馈
     */
    fun feedback(content: String, type: String) {
        wrapExecute({
            sendFeedBack(FeedbackReqBean(type, content))
            sendFeedbackLiveData.postValue(true)
        })
    }


    /**
     * 获取支付方式
     */
    fun getPayChannel() {
        wrapExecute({
            payChannelListLiveData.postValue(getPayChannelList())
        })
    }

    /**
     * 获取产信息
     */
    fun getProductInfo() {
        payProductInfoLiveData.postValue(getPayProductInfo())
    }

    /**
     * 支付
     */
    fun pay(payType: String, productId: String) {
        wrapExecute({
            // 创建订单
            val orderId = createOrder(productId)
            // 订单支付信息
            val payInfo = getOrderPayInfo(orderId, payType)
            val payInfoMap = HashMap<String, Any>()
            payInfoMap["orderId"] = orderId
            payInfoMap["payInfo"] = payInfo
            payInfoLiveData.postValue(payInfoMap)
        })
    }

    /**
     * 查询支付结果
     */
    fun getPayResult(orderId: String) {
        wrapExecute({
            queryPayResult(orderId)
            val result = getAccountInfo(UserAccountInfoReqBean())
            saveLoginAccountInfo(result)
            payResultLiveData.postValue(true)
        })
    }
}