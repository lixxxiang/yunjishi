package com.android.lixiang.base.presenter

import com.android.lixiang.base.presenter.view.BaseView
import com.trello.rxlifecycle2.LifecycleProvider
import javax.inject.Inject

open class BasePresenter<T: BaseView> @Inject constructor(){
    lateinit var mView: T
    @Inject
    lateinit var lifecycleProvider: LifecycleProvider<*>
}