package com.android.lixiang.base.rx

import io.reactivex.Observer
import io.reactivex.disposables.Disposable

open class BaseObserver<T>: Observer<T> {
    override fun onComplete() {
        println("------onComplete------")
    }

    override fun onSubscribe(d: Disposable) {
        println("------onSubscribe------" + d)
    }

    override fun onNext(t: T) {
        println("------onNext------" + t)
    }

    override fun onError(e: Throwable) {
        println("------onError------" + e)
    }
}