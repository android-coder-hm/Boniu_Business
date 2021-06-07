package com.boniu.observer

object BoNiuSubject {

    private val observerList = mutableListOf<BoNiuObserver>()

    fun addObserver(observer: BoNiuObserver) {
        if (!observerList.contains(observer)) {
            observerList.add(observer)
        }
    }

    fun removeObserver(observer: BoNiuObserver) {
        if (observerList.contains(observer)) {
            observerList.remove(observer)
        }
    }

    fun notifyLoginSuccess() {
        observerList.forEach {
            it.loginSuccess()
        }
    }

    fun notifyLogoutSuccess() {
        observerList.forEach {
            it.logoutSuccess()
        }
    }

    fun notifyPaySuccess() {
        observerList.forEach {
            it.paySuccess()
        }
    }

    fun notifyOpenVipSuccess() {
        observerList.forEach {
            it.openVipSuccess()
        }
    }
}