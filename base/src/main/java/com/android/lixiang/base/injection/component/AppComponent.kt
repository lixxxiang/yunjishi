package com.android.lixiang.base.injection.component

import android.content.Context
import com.android.lixiang.base.injection.module.AppModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(AppModule::class))
interface AppComponent {

    fun context(): Context
}
