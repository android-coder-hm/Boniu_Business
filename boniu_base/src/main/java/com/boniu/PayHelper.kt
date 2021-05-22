package com.boniu

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import com.adk.logInfo
import com.adk.showToast
import com.alipay.sdk.app.PayTask
import com.boniu.http.WechatPayInfo
import com.boniu.observer.PayResultSubject
import com.tencent.mm.opensdk.modelpay.PayReq
import com.tencent.mm.opensdk.openapi.WXAPIFactory
import java.util.concurrent.Executor
import java.util.concurrent.Executors

/**
 * 支付帮助类
 */
object PayHelper {
    private const val TAG = "PayHelper"
    var wechat_pay_app_id = ""
    private val executor: Executor by lazy {
        Executors.newSingleThreadExecutor()
    }

    fun aliPay(result: String, activity: AppCompatActivity) {
        executor.execute {
            val aliPayResult = PayTask(activity).payV2(result, true)
            logInfo(TAG, "支付宝支付结果：${aliPayResult}")
            when {
                aliPayResult["resultStatus"] == "9000" -> {
                    activity.runOnUiThread {
                        PayResultSubject.notifyObserver()
                    }
                }
                "6001" == aliPayResult["resultStatus"] -> {
                    activity.runOnUiThread {
                        showToast("支付取消")
                    }
                }
                else -> {
                    activity.runOnUiThread {
                        showToast("支付失败")
                    }
                }
            }
        }
    }

    fun wechatPay(wechatPayVo: WechatPayInfo, context: Context) {
        wechat_pay_app_id = wechatPayVo.appID
        val request = PayReq()
        request.appId = wechatPayVo.appID
        request.partnerId = wechatPayVo.partnerid
        request.prepayId = wechatPayVo.prepayid
        request.packageValue = wechatPayVo.packageValue
        request.nonceStr = wechatPayVo.noncestr
        request.timeStamp = wechatPayVo.timestamp
        request.sign = wechatPayVo.sign
        WXAPIFactory.createWXAPI(context, wechatPayVo.appID).sendReq(request)
    }
}