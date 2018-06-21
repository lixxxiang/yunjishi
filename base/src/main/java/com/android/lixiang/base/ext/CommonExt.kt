package com.android.lixiang.base.ext

import com.android.lixiang.base.rx.BaseObserver
import com.trello.rxlifecycle2.LifecycleProvider
import com.trello.rxlifecycle2.kotlin.bindToLifecycle
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.reactivestreams.Subscriber

fun <T> Observable<T>.execute(observer: BaseObserver<T>, lifecycleProvider: LifecycleProvider<*>){
    println("----Observable----")
    this.observeOn(AndroidSchedulers.mainThread())
            .compose(lifecycleProvider.bindToLifecycle())
            .subscribeOn(Schedulers.io())
            .subscribe(observer)
    return
}
