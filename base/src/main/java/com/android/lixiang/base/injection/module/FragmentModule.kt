package com.android.lixiang.base.injection.module

import android.app.Activity
import android.support.v4.app.Fragment
import com.android.lixiang.base.injection.ActivityScope
import com.android.lixiang.base.injection.FragmentScope
import dagger.Module
import dagger.Provides

@Module
class FragmentModule(private val fragment: Fragment) {

    @FragmentScope
    @Provides
    fun providefragment(): Fragment {
        return fragment
    }
}