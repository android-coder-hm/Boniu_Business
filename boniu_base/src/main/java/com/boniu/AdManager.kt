package com.boniu

import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import com.adk.getShareValue
import com.adk.logError
import com.adk.logInfo
import com.adk.putShareValue
import com.bytedance.sdk.openadsdk.*
import com.qq.e.ads.rewardvideo.RewardVideoAD
import com.qq.e.ads.rewardvideo.RewardVideoADListener
import com.qq.e.ads.splash.SplashAD
import com.qq.e.ads.splash.SplashADListener
import com.qq.e.comm.util.AdError

private const val TAG = "AdManager"
private const val SHARE_AD_FILE = "adFile"
private const val SHARE_KEY_AD = "adKey"
const val SHARE_VALUE_GDT = "gdt"
const val SHARE_VALUE_TT = "tt"

private const val SHARE_KEY_AD_FULL = "adFullKey"
const val SHARE_VALUE_GDT_FULL = "gdtFull"
const val SHARE_VALUE_TT_FULL = "ttFuLL"


fun getLashAdName(): String {
    return getShareValue(SHARE_AD_FILE, SHARE_KEY_AD, SHARE_VALUE_GDT)
}

fun saveLastAdName(adName: String) {
    putShareValue(SHARE_AD_FILE, SHARE_KEY_AD, adName)
}

fun getLashAdFullName(): String {
    return getShareValue(SHARE_AD_FILE, SHARE_KEY_AD_FULL, SHARE_VALUE_GDT_FULL)
}

fun saveLastAdFullName(adName: String) {
    putShareValue(SHARE_AD_FILE, SHARE_KEY_AD_FULL, adName)
}

/**
 * 加载开屏广告
 */

fun showSplashAd(activity: AppCompatActivity, container: ViewGroup, finishFun: () -> Unit) {
    //获取上次加载的
    when (getLashAdName()) {
        SHARE_VALUE_TT -> {
            showGDTSplashAd(activity, container, finishFun)
        }
        SHARE_VALUE_GDT -> {
            showTTSplashAd(activity, container, finishFun)
        }
    }
}

private fun showTTSplashAd(activity: AppCompatActivity, container: ViewGroup, finishFun: () -> Unit, isFromError: Boolean = false) {
    logInfo(TAG, "加载穿山甲开屏广告")
    val ttAdNative = TTAdSdk.getAdManager().createAdNative(activity)
    val adSlot = AdSlot.Builder().setCodeId("887365104").setSupportDeepLink(true).setImageAcceptedSize(1080, 1920).build()
    ttAdNative.loadSplashAd(adSlot, object : TTAdNative.SplashAdListener {
        override fun onError(p0: Int, p1: String?) {
            logInfo(TAG, "加载穿山甲开屏广告失败:${p1}")
            if (!isFromError) {
                showGDTSplashAd(activity, container, finishFun, true)
            } else {
                finishFun()
            }
        }

        override fun onTimeout() {
            logInfo(TAG, "加载穿山甲开屏广告超时")
            if (!isFromError) {
                showGDTSplashAd(activity, container, finishFun, true)
            } else {
                finishFun()
            }
        }

        override fun onSplashAdLoad(p0: TTSplashAd?) {
            logInfo(TAG, "穿山甲开屏广告加载成功")
            saveLastAdName(SHARE_VALUE_TT)
            p0?.let {
                container.removeAllViews()
                container.addView(it.splashView)
                it.setSplashInteractionListener(object : TTSplashAd.AdInteractionListener {
                    override fun onAdClicked(p0: View?, p1: Int) {
                    }

                    override fun onAdShow(p0: View?, p1: Int) {
                    }

                    override fun onAdSkip() {
                        finishFun()
                    }

                    override fun onAdTimeOver() {
                        finishFun()
                    }
                })
            }
        }
    })
}

/**
 * 显示广点通  开屏广告
 */
private fun showGDTSplashAd(activity: AppCompatActivity, container: ViewGroup, finishFun: () -> Unit, isFromError: Boolean = false) {
    val gdtSplashAD = SplashAD(activity, "4001423523202488", object : SplashADListener {
        override fun onADExposure() {
            logInfo(TAG, "加载广点通开屏广告:onADExposure()")
        }

        override fun onADDismissed() {
            logInfo(TAG, "加载广点通开屏广告:onADDismissed()")
            saveLastAdName(SHARE_VALUE_GDT)
            finishFun()
        }

        override fun onADPresent() {
            logInfo(TAG, "加载广点通开屏广告:onADPresent()")
        }

        override fun onADLoaded(p0: Long) {
            logInfo(TAG, "加载广点通开屏广告:onADPresent()")
        }

        override fun onNoAD(p0: AdError?) {
            logInfo(TAG, "加载广点通开屏广告:onNoAD()")
            if (!isFromError) {
                showTTSplashAd(activity, container, finishFun, true)
            } else {
                finishFun()
            }
        }

        override fun onADClicked() {
            logInfo(TAG, "加载广点通开屏广告:onADClicked()")
        }

        override fun onADTick(p0: Long) {
            logInfo(TAG, "加载广点通开屏广告:onADTick()")
        }

    }, 0)
    gdtSplashAD.fetchAndShowIn(container)
}

/**
 * 显示全屏激励视频
 */
fun showFullScreenAd(
    activity: AppCompatActivity,
    finishFun: () -> Unit,
    errorFun: () -> Unit,
    loadingFun: () -> Unit,
    dismissLoadingFun: () -> Unit
) {
    //获取上次加载的
    when (getLashAdFullName()) {
        SHARE_VALUE_TT_FULL -> {
            showGDTFullScreenAd(activity, finishFun, errorFun, loadingFun, dismissLoadingFun)
        }
        SHARE_VALUE_GDT_FULL -> {
            showTTFullScreenAd(activity, finishFun, errorFun, loadingFun, dismissLoadingFun)
        }
    }

}

/**
 * 显示穿山甲全屏广告
 */
private fun showTTFullScreenAd(
    activity: AppCompatActivity,
    finishFun: () -> Unit,
    errorFun: () -> Unit,
    loadingFun: () -> Unit,
    dismissLoadingFun: () -> Unit,
    isFromError: Boolean = false
) {
    logInfo(TAG, "加载穿山甲激励视频")
    val ttAdNative = TTAdSdk.getAdManager().createAdNative(activity)
    val adSlot = AdSlot.Builder().setCodeId("945751120")
        .setSupportDeepLink(true)
        .setOrientation(2).build()
    loadingFun()
    ttAdNative.loadRewardVideoAd(adSlot, object : TTAdNative.RewardVideoAdListener {
        override fun onError(p0: Int, p1: String?) {
            logInfo(TAG, "加载穿山甲激励视频出现错误")
            if (!isFromError) {
                showGDTFullScreenAd(activity, finishFun, errorFun, loadingFun, dismissLoadingFun, true)
            } else {
                dismissLoadingFun()
                errorFun()
            }
        }

        override fun onRewardVideoAdLoad(p0: TTRewardVideoAd?) {
            p0?.let {
                it.setRewardAdInteractionListener(object : TTRewardVideoAd.RewardAdInteractionListener {
                    override fun onAdShow() {
                        logInfo(TAG, "加载穿山甲激励视频-->onAdShow")
                        dismissLoadingFun()
                    }

                    override fun onAdVideoBarClick() {
                        logInfo(TAG, "加载穿山甲激励视频-->onAdVideoBarClick")
                    }

                    override fun onAdClose() {
                        logInfo(TAG, "加载穿山甲激励视频-->onAdClose")
                        saveLastAdFullName(SHARE_VALUE_TT_FULL)
                        dismissLoadingFun()
                        finishFun()
                    }

                    override fun onVideoComplete() {
                        logInfo(TAG, "加载穿山甲激励视频-->onVideoComplete")
                    }

                    override fun onVideoError() {
                        logInfo(TAG, "加载穿山甲激励视频-->onVideoError")
                        if (!isFromError) {
                            showGDTFullScreenAd(activity, finishFun, errorFun, loadingFun, dismissLoadingFun, true)
                        } else {
                            dismissLoadingFun()
                            errorFun()
                        }
                    }

                    override fun onRewardVerify(p0: Boolean, p1: Int, p2: String, p3: Int, p4: String) {
                        logInfo(TAG, "加载穿山甲激励视频-->onRewardVerify")
                    }


                    override fun onSkippedVideo() {
                        logInfo(TAG, "加载穿山甲激励视频-->onSkippedVideo")
                    }
                })
                it.showRewardVideoAd(activity)
            }
        }

        override fun onRewardVideoCached() {
            logInfo(TAG, "加载穿山甲激励视频-->onRewardVideoCached")
        }
    })
}

/**
 * 显示优量汇全屏广告
 */
private fun showGDTFullScreenAd(
    activity: AppCompatActivity,
    finishFun: () -> Unit,
    errorFun: () -> Unit,
    loadingFun: () -> Unit,
    dismissLoadingFun: () -> Unit,
    isFromError: Boolean = false
) {
    var rewardVideoAD: RewardVideoAD? = null
    logInfo(TAG, "加载优量汇激励视频")
    rewardVideoAD = RewardVideoAD(activity, "9031343843098595", object : RewardVideoADListener {

        override fun onADLoad() {
            logInfo(TAG, "加载优量汇激励视频 --》 onADLoad")
            dismissLoadingFun()
            rewardVideoAD?.showAD()
        }

        override fun onVideoCached() {
            logInfo(TAG, "加载优量汇激励视频 --》 onVideoCached")

        }

        override fun onADShow() {
            logInfo(TAG, "加载优量汇激励视频 --》 onADShow")
        }

        override fun onADExpose() {
            logInfo(TAG, "加载优量汇激励视频 --》 onADExpose")
        }

        override fun onReward() {
            logInfo(TAG, "加载优量汇激励视频 --》 onReward")
        }

        override fun onADClick() {
            logInfo(TAG, "加载优量汇激励视频 --》 onADClick")
        }

        override fun onVideoComplete() {
            logInfo(TAG, "加载优量汇激励视频 --》 onVideoComplete")
        }

        override fun onADClose() {
            logInfo(TAG, "加载优量汇激励视频 --》 onADClose")
            saveLastAdFullName(SHARE_VALUE_GDT_FULL)
            dismissLoadingFun()
            finishFun()
        }

        override fun onError(p0: AdError?) {
            logError(TAG, "加载优量汇激励视频 --》 onError")
            if (!isFromError) {
                showTTFullScreenAd(activity, finishFun, errorFun, loadingFun, dismissLoadingFun, true)
            } else {
                dismissLoadingFun()
                errorFun()
            }
        }
    })
    loadingFun()
    rewardVideoAD.loadAD()
}

