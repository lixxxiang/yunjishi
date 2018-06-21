package com.android.lixiang.base.injection.module

import android.content.Context
import com.android.lixiang.base.common.BaseApplication
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule(private val context: BaseApplication) {

    @Singleton
    @Provides
    fun provideContext(): Context {
        return this.context
    }
}