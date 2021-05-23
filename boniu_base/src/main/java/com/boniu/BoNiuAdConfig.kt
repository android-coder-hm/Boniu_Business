package com.boniu

import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import com.adk.getShareValue
import com.adk.putShareValue
import com.bytedance.sdk.openadsdk.*
import com.qq.e.ads.rewardvideo.RewardVideoAD
import com.qq.e.ads.rewardvideo.RewardVideoADListener
import com.qq.e.ads.splash.SplashAD
import com.qq.e.ads.splash.SplashADListener
import com.qq.e.comm.managers.GDTADManager
import com.qq.e.comm.util.AdError

/**
 * 博牛广告配置
 */
object BoNiuAdConfig {

    // 广告配置存储
    private const val SHARE_AD_FILE = "adShareFile"

    // 开屏广告
    private const val SHARE_KEY_LAST_SPLASH_KEY = "splashLastKey"
    private const val SHARE_VALUE_SPLASH_GDT = "gdtSplash"
    private const val SHARE_VALUE_SPLASH_TT = "ttSplash"

    // 激励视频
    private const val SHARE_KEY_LAST_URGE_KEY = "urgeLastKey"
    private const val SHARE_VALUE_URGE_GDT = "gdtUrge"
    private const val SHARE_VALUE_URGE_TT = "ttUrge"

    private var isShowAd: Boolean = true // 是否显示广告

    private lateinit var ttAdSplashCodeId: String  // 穿山甲开屏广告广告位ID
    private lateinit var ttAdUrgeCodeId: String // 穿山甲激励视频广告位ID

    private lateinit var gdtAdSplashCodeId: String  // 广点通开屏广告广告位ID
    private lateinit var gdtAdUrgeCodeId: String // 广点通激励视频广告位ID


    fun init(
        context: Context, isShowAd: Boolean,
        ttAdAppId: String, ttAdSplashCodeId: String, ttAdUrgeCodeId: String,
        gdtAdAppId: String, gdtAdSplashCodeId: String, gdtAdUrgeCodeId: String
    ) {
        this.isShowAd = isShowAd
        this.ttAdSplashCodeId = ttAdSplashCodeId
        this.ttAdUrgeCodeId = ttAdUrgeCodeId
        this.gdtAdSplashCodeId = gdtAdSplashCodeId
        this.gdtAdUrgeCodeId = gdtAdUrgeCodeId

        if (isShowAd) {
            initTTAd(context, ttAdAppId)
            initGDTAd(context, gdtAdAppId)
        }
    }

    /**
     * 初始化穿山甲
     */
    private fun initTTAd(context: Context, appId: String) {
        val config = TTAdConfig.Builder()
            .appId(appId)
            .useTextureView(true)
            .appName(BoNiuConfig.getAppName())
            .titleBarTheme(TTAdConstant.TITLE_BAR_THEME_DARK)
            .allowShowNotify(true) //是否允许sdk展示通知栏提示
            .allowShowPageWhenScreenLock(true) //是否在锁屏场景支持展示广告落地页
            .debug(BuildConfig.DEBUG) //测试阶段打开，可以通过日志排查问题，上线时去除该调用
            .directDownloadNetworkType(
                TTAdConstant.NETWORK_STATE_WIFI,
                TTAdConstant.NETWORK_STATE_3G
            ) //允许直接下载的网络状态集合
            .supportMultiProcess(false)//是否支持多进程
            .build()
        TTAdSdk.init(context, config)
    }

    /**
     * 初始化广点通
     */
    private fun initGDTAd(context: Context, gdtAdAppId: String) {
        GDTADManager.getInstance().initWith(context, gdtAdAppId)
    }

    private fun getLastSplashAdName(): String {
        return getShareValue(SHARE_AD_FILE, SHARE_KEY_LAST_SPLASH_KEY, SHARE_VALUE_SPLASH_GDT)
    }

    private fun saveLastSplashAdName(adName: String) {
        putShareValue(SHARE_AD_FILE, SHARE_KEY_LAST_SPLASH_KEY, adName)
    }

    private fun getLastUrgeAdName(): String {
        return getShareValue(SHARE_AD_FILE, SHARE_KEY_LAST_URGE_KEY, SHARE_VALUE_URGE_GDT)
    }

    private fun saveLastUrgeAdName(adName: String) {
        putShareValue(SHARE_AD_FILE, SHARE_KEY_LAST_URGE_KEY, adName)
    }

    /**
     * 加载开屏广告
     */
    fun showSplashAd(activity: AppCompatActivity, container: ViewGroup, finishFun: () -> Unit) {
        if (!isShowAd) {
            finishFun()
            return
        }
        when (getLastSplashAdName()) {
            SHARE_VALUE_SPLASH_GDT -> {
                showTTSplashAd(activity, container, finishFun)
            }
            SHARE_VALUE_SPLASH_TT -> {
                showGDTSplashAd(activity, container, finishFun)
            }
        }
    }

    /**
     * 显示激励视频
     */
    fun showUrgeAd(activity: AppCompatActivity, finishFun: () -> Unit, errorFun: () -> Unit, loadingFun: () -> Unit, dismissLoadingFun: () -> Unit) {
        //获取上次加载的
        when (getLastUrgeAdName()) {
            SHARE_VALUE_URGE_TT -> {
                showGDTUrgeAd(activity, finishFun, errorFun, loadingFun, dismissLoadingFun)
            }
            SHARE_VALUE_URGE_GDT -> {
                showTTUrgeAd(activity, finishFun, errorFun, loadingFun, dismissLoadingFun)
            }
        }
    }

    /**
     * 显示穿山甲开屏广告
     */
    private fun showTTSplashAd(activity: AppCompatActivity, container: ViewGroup, finishFun: () -> Unit, isFromError: Boolean = false) {
        val ttAdNative = TTAdSdk.getAdManager().createAdNative(activity)
        val adSlot = AdSlot.Builder().setCodeId(ttAdUrgeCodeId).setSupportDeepLink(true).setImageAcceptedSize(1080, 1920).build()
        ttAdNative.loadSplashAd(adSlot, object : TTAdNative.SplashAdListener {
            override fun onError(p0: Int, p1: String) {
                if (!isFromError) {
                    showGDTSplashAd(activity, container, finishFun, true)
                } else {
                    finishFun()
                }
            }

            override fun onTimeout() {
                if (!isFromError) {
                    showGDTSplashAd(activity, container, finishFun, true)
                } else {
                    finishFun()
                }
            }

            override fun onSplashAdLoad(p0: TTSplashAd) {
                saveLastSplashAdName(SHARE_VALUE_SPLASH_TT)
                container.removeAllViews()
                container.addView(p0.splashView)
                p0.setSplashInteractionListener(object : TTSplashAd.AdInteractionListener {
                    override fun onAdClicked(p0: View?, p1: Int) {}

                    override fun onAdShow(p0: View?, p1: Int) {}

                    override fun onAdSkip() {
                        finishFun()
                    }

                    override fun onAdTimeOver() {
                        finishFun()
                    }
                })

            }
        })
    }

    /**
     * 显示广点通  开屏广告
     */
    private fun showGDTSplashAd(activity: AppCompatActivity, container: ViewGroup, finishFun: () -> Unit, isFromError: Boolean = false) {
        val gdtSplashAD = SplashAD(activity, gdtAdSplashCodeId, object : SplashADListener {
            override fun onADExposure() {}

            override fun onADDismissed() {
                saveLastSplashAdName(SHARE_VALUE_SPLASH_GDT)
                finishFun()
            }

            override fun onADPresent() {}

            override fun onADLoaded(p0: Long) {}

            override fun onNoAD(p0: AdError?) {
                if (!isFromError) {
                    showTTSplashAd(activity, container, finishFun, true)
                } else {
                    finishFun()
                }
            }

            override fun onADClicked() {}

            override fun onADTick(p0: Long) {}

        }, 0)
        gdtSplashAD.fetchAndShowIn(container)
    }

    /**
     * 显示穿山甲激励视频
     */
    private fun showTTUrgeAd(activity: AppCompatActivity, finishFun: () -> Unit, errorFun: () -> Unit, loadingFun: () -> Unit, dismissLoadingFun: () -> Unit, isFromError: Boolean = false) {
        val ttAdNative = TTAdSdk.getAdManager().createAdNative(activity)
        val adSlot = AdSlot.Builder().setCodeId(ttAdUrgeCodeId).setSupportDeepLink(true).setOrientation(2).build()
        loadingFun()
        ttAdNative.loadRewardVideoAd(adSlot, object : TTAdNative.RewardVideoAdListener {
            override fun onError(p0: Int, p1: String?) {
                if (!isFromError) {
                    showGDTUrgeAd(activity, finishFun, errorFun, loadingFun, dismissLoadingFun, true)
                } else {
                    dismissLoadingFun()
                    errorFun()
                }
            }

            override fun onRewardVideoAdLoad(p0: TTRewardVideoAd?) {
                p0?.let {
                    it.setRewardAdInteractionListener(object : TTRewardVideoAd.RewardAdInteractionListener {
                        override fun onAdShow() {
                            dismissLoadingFun()
                        }

                        override fun onAdVideoBarClick() {}

                        override fun onAdClose() {
                            saveLastUrgeAdName(SHARE_VALUE_URGE_TT)
                            dismissLoadingFun()
                            finishFun()
                        }

                        override fun onVideoComplete() {}

                        override fun onVideoError() {
                            if (!isFromError) {
                                showGDTUrgeAd(activity, finishFun, errorFun, loadingFun, dismissLoadingFun, true)
                            } else {
                                dismissLoadingFun()
                                errorFun()
                            }
                        }

                        override fun onRewardVerify(p0: Boolean, p1: Int, p2: String, p3: Int, p4: String) {}


                        override fun onSkippedVideo() {}
                    })
                    it.showRewardVideoAd(activity)
                }
            }

            override fun onRewardVideoCached() {}
        })
    }

    /**
     * 优量汇激励视频
     */
    private fun showGDTUrgeAd(activity: AppCompatActivity, finishFun: () -> Unit, errorFun: () -> Unit, loadingFun: () -> Unit, dismissLoadingFun: () -> Unit, isFromError: Boolean = false) {
        var rewardVideoAD: RewardVideoAD? = null
        rewardVideoAD = RewardVideoAD(activity, gdtAdUrgeCodeId, object : RewardVideoADListener {

            override fun onADLoad() {
                dismissLoadingFun()
                rewardVideoAD?.showAD()
            }

            override fun onVideoCached() {}

            override fun onADShow() {}

            override fun onADExpose() {}

            override fun onReward() {}

            override fun onADClick() {}

            override fun onVideoComplete() {}

            override fun onADClose() {
                saveLastUrgeAdName(SHARE_VALUE_URGE_GDT)
                dismissLoadingFun()
                finishFun()
            }

            override fun onError(p0: AdError?) {
                if (!isFromError) {
                    showTTUrgeAd(activity, finishFun, errorFun, loadingFun, dismissLoadingFun, true)
                } else {
                    dismissLoadingFun()
                    errorFun()
                }
            }
        })
        loadingFun()
        rewardVideoAD.loadAD()
    }


}