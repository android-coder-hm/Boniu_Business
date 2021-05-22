package com.boniu.observer

object LoginResultSubject {
    private val loginResultObserverList: MutableList<LoginResultObserver> = mutableListOf()

    fun add(loginResultObserver: LoginResultObserver) {
        if (!loginResultObserverList.contains(loginResultObserver)) {
            loginResultObserverList.add(loginResultObserver)
        }
    }

    fun remove(loginResultObserver: LoginResultObserver) {
        if (!loginResultObserverList.contains(loginResultObserver)) {
            loginResultObserverList.remove(loginResultObserver)
        }
    }

    fun notifyLoginSuccessObserver() {
        loginResultObserverList.forEach {
            it.loginSuccess()
        }
    }

    fun notifyLoginOutSuccessObserver() {
        loginResultObserverList.forEach {
            it.logoutSuccess()
        }
    }
}

interface LoginResultObserver {
    fun loginSuccess()
    fun logoutSuccess()
}