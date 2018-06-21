package com.android.lixiang.base.ui.activity

import android.os.Bundle
import com.android.lixiang.base.common.BaseApplication
import com.android.lixiang.base.injection.component.ActivityComponent
import com.android.lixiang.base.injection.component.DaggerActivityComponent
import com.android.lixiang.base.injection.module.ActivityModule
import com.android.lixiang.base.injection.module.LifecycleProviderModule
import com.android.lixiang.base.presenter.BasePresenter
import com.android.lixiang.base.presenter.view.BaseView
import javax.inject.Inject

abstract class BaseMvpActivity<T: BasePresenter<*>>: BaseActivity(), BaseView {
    @Inject
    lateinit var mPresenter: T

    @Inject
    lateinit var activityComponent: ActivityComponent
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initActivityInjection()
        injectComponent()
    }
    abstract fun injectComponent()


    private fun initActivityInjection() {
        activityComponent = DaggerActivityComponent.builder()
                .appComponent((application as BaseApplication).appComponent)
                .activityModule(ActivityModule(this))
                .lifecycleProviderModule(LifecycleProviderModule(this))
                .build()
    }
}