package com.boniu

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
        ttAdAppId: String, ttAdSplashCodeId: String, ttAdUrgeCodeId: String,
        gdtAdAppId: String, gdtAdSplashCodeId: String, gdtAdUrgeCodeId: String
    ) {
        this.ttAdAppId = ttAdAppId
        this.ttAdSplashCodeId = ttAdSplashCodeId
        this.ttAdUrgeCodeId = ttAdUrgeCodeId
        this.gdtAdAppId = gdtAdAppId
        this.gdtAdSplashCodeId = gdtAdSplashCodeId
        this.gdtAdUrgeCodeId = gdtAdUrgeCodeId

    }

}