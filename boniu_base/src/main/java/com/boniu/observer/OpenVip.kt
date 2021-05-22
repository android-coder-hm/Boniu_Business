package com.boniu.observer

/**
 * 开通VIP的被观察者
 */
object OpenVipSubject {
    private val openVipObserverList: MutableList<OpenVipObserver> = mutableListOf()

    fun add(openVipObserver: OpenVipObserver) {
        if (!openVipObserverList.contains(openVipObserver)) {
            openVipObserverList.add(openVipObserver)
        }
    }

    fun remove(openVipObserver: OpenVipObserver) {
        if (openVipObserverList.contains(openVipObserver)) {
            openVipObserverList.remove(openVipObserver)
        }
    }

    fun notifyObserver() {
        openVipObserverList.forEach {
            it.openVipSuccess()
        }
    }

}


interface OpenVipObserver {
    fun openVipSuccess()
}
