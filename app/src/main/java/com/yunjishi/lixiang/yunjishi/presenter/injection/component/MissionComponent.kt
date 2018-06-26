package com.yunjishi.lixiang.yunjishi.presenter.injection.component

import com.android.lixiang.base.injection.ComponentScope
import com.android.lixiang.base.injection.component.FragmentComponent
import com.yunjishi.lixiang.yunjishi.presenter.injection.module.MissionModule
import com.yunjishi.lixiang.yunjishi.view.fragment.MissionFragment
import dagger.Component

@ComponentScope
@Component(dependencies = arrayOf(FragmentComponent::class), modules = arrayOf(MissionModule::class))
interface MissionComponent {
    fun inject(fragment: MissionFragment)
}