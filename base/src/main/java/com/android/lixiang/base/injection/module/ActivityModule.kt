package com.android.lixiang.base.injection.module

import android.app.Activity
import com.android.lixiang.base.injection.ActivityScope
import dagger.Module
import dagger.Provides

@Module
class ActivityModule(private val activity: Activity) {

    @ActivityScope
    @Provides
    fun provideActivity(): Activity {
        return activity
    }
}