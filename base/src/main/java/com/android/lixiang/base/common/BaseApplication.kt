package com.android.lixiang.base.common

import android.app.Application
import com.android.lixiang.base.injection.component.AppComponent
import com.android.lixiang.base.injection.component.DaggerAppComponent
import com.android.lixiang.base.injection.module.AppModule
import javax.inject.Inject

class BaseApplication: Application(){

    @Inject
    lateinit var appComponent: AppComponent
    override fun onCreate() {
        super.onCreate()
        initAppInjection()
    }

    private fun initAppInjection() {
        appComponent = DaggerAppComponent.builder().appModule(AppModule(this)).build()
    }
}