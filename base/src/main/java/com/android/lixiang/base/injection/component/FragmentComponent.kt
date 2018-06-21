package com.android.lixiang.base.injection.component

import android.app.Activity
import android.content.Context
import android.support.v4.app.Fragment
import com.android.lixiang.base.injection.ActivityScope
import com.android.lixiang.base.injection.FragmentScope
import com.android.lixiang.base.injection.module.ActivityModule
import com.android.lixiang.base.injection.module.FragmentModule
import com.android.lixiang.base.injection.module.LifecycleProviderModule
import com.trello.rxlifecycle2.LifecycleProvider
import dagger.Component

@FragmentScope
@Component(dependencies = arrayOf(AppComponent::class),modules = arrayOf(FragmentModule::class, LifecycleProviderModule::class))
interface FragmentComponent {
    fun fragment(): Fragment
    fun LifecycleProvider(): LifecycleProvider<*>
    fun context(): Context

}