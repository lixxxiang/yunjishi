package com.yunjishi.lixiang.yunjishi.presenter.injection.component

import com.android.lixiang.base.injection.ComponentScope
import com.android.lixiang.base.injection.component.ActivityComponent
import com.android.lixiang.base.injection.component.FragmentComponent
import com.yunjishi.lixiang.yunjishi.presenter.injection.module.MissionModule
import com.yunjishi.lixiang.yunjishi.presenter.injection.module.OrderDetailModule
import com.yunjishi.lixiang.yunjishi.view.activity.OrderDetailActivity
import com.yunjishi.lixiang.yunjishi.view.fragment.MissionFragment
import dagger.Component

@ComponentScope
@Component(dependencies = arrayOf(ActivityComponent::class), modules = arrayOf(OrderDetailModule::class))
interface OrderDetailComponent {
    fun inject(activity: OrderDetailActivity)
}