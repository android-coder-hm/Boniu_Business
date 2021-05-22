package com.boniu.observer

object PayResultSubject {
    private val payResultObserverList = mutableListOf<PayResultObserver>()

    fun addObserver(payResultObserver: PayResultObserver) {
        if (!payResultObserverList.contains(payResultObserver)) {
            payResultObserverList.add(payResultObserver)
        }
    }

    fun removeObserver(payResultObserver: PayResultObserver) {
        if (payResultObserverList.contains(payResultObserver)) {
            payResultObserverList.remove(payResultObserver)
        }
    }

    fun notifyObserver() {
        payResultObserverList.forEach {
            it.paySuccess()
        }
    }
}

/**
 * 支付结果观察者
 */
interface PayResultObserver {
    fun paySuccess()
}