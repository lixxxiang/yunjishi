package com.yunjishi.lixiang.yunjishi.presenter.injection.module

import com.yunjishi.lixiang.yunjishi.service.MissionService
import com.yunjishi.lixiang.yunjishi.service.ParamsService
import com.yunjishi.lixiang.yunjishi.service.impl.MissionServiceImpl
import com.yunjishi.lixiang.yunjishi.service.impl.ParamsServiceImpl
import dagger.Module
import dagger.Provides

@Module
class ParamsModule {
    @Provides
    fun provideParamsService(service: ParamsServiceImpl): ParamsService {
        return service
    }
}