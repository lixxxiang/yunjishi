package com.android.lixiang.base.ui.fragment

import android.os.Bundle
import com.android.lixiang.base.common.BaseApplication
import com.android.lixiang.base.injection.component.ActivityComponent
import com.android.lixiang.base.injection.component.DaggerActivityComponent
import com.android.lixiang.base.injection.component.DaggerFragmentComponent
import com.android.lixiang.base.injection.component.FragmentComponent
import com.android.lixiang.base.injection.module.ActivityModule
import com.android.lixiang.base.injection.module.FragmentModule
import com.android.lixiang.base.injection.module.LifecycleProviderModule

import com.android.lixiang.base.presenter.BasePresenter
import com.android.lixiang.base.presenter.view.BaseView
import javax.inject.Inject

abstract class BaseMvpFragment<T: BasePresenter<*>>: BaseFragment(), BaseView {
    @Inject
    lateinit var mPresenter: T

    @Inject
    lateinit var fragmentComponent: FragmentComponent
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initActivityInjection()
        injectComponent()
    }

    abstract fun injectComponent()


    private fun initActivityInjection() {
//        activityComponent = DaggerActivityComponent.builder()
//                .appComponent((activity.application as BaseApplication).appComponent)
//                .activityModule(ActivityModule(activity))
//                .build()
//
        fragmentComponent = DaggerFragmentComponent.builder()
                .appComponent((activity!!.application as BaseApplication).appComponent)
                .fragmentModule(FragmentModule(this))
                .lifecycleProviderModule(LifecycleProviderModule(this))
                .build()
    }
}