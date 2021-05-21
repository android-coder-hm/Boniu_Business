package com.boniu

object BoNiuConfig {

    private lateinit var appServerName: String
    private lateinit var versionName: String
    private lateinit var channel: String
    private lateinit var baseUrl: String
    private lateinit var appName: String

    fun init(appServerName: String, versionName: String, channel: String, appName: String, baseUrl: String) {
        this.appServerName = appServerName
        this.versionName = versionName
        this.channel = channel
        this.baseUrl = baseUrl
        this.appName = appName
    }

    fun getAppServerName() = appServerName

    fun getVersionName() = versionName

    fun getChannel() = channel

    fun getBaseUrl() = baseUrl

    fun getAppName() = appName
}