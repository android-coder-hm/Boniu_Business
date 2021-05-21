package com.boniu

import android.content.Context
import com.bytedance.sdk.openadsdk.TTAdConfig
import com.bytedance.sdk.openadsdk.TTAdConstant
import com.bytedance.sdk.openadsdk.TTAdSdk
import com.qq.e.comm.managers.GDTADManager

/**
 * 博牛广告配置
 */
object BoNiuAdConfig {
    lateinit var ttAdAppId: String // 穿山甲appId
    lateinit var ttAdSplashCodeId: String  // 穿山甲开屏广告广告位ID
    lateinit var ttAdUrgeCodeId: String // 穿山甲激励视频广告位ID

    lateinit var gdtAdAppId: String // 广点通appId
    lateinit var gdtAdSplashCodeId: String  // 广点通开屏广告广告位ID
    lateinit var gdtAdUrgeCodeId: String // 广点通激励视频广告位ID


    fun init(
        context: Context,
        ttAdAppId: String, ttAdSplashCodeId: String, ttAdUrgeCodeId: String,
        gdtAdAppId: String, gdtAdSplashCodeId: String, gdtAdUrgeCodeId: String
    ) {
        this.ttAdAppId = ttAdAppId
        this.ttAdSplashCodeId = ttAdSplashCodeId
        this.ttAdUrgeCodeId = ttAdUrgeCodeId
        this.gdtAdAppId = gdtAdAppId
        this.gdtAdSplashCodeId = gdtAdSplashCodeId
        this.gdtAdUrgeCodeId = gdtAdUrgeCodeId

        initTTAd(context)
    }

    private fun initTTAd(context: Context) {
        val config = TTAdConfig.Builder()
            .appId(this.ttAdAppId)
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
    private fun initGDTAd(context: Context) {
        GDTADManager.getInstance().initWith(context, this.gdtAdAppId)
    }
}