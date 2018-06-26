package com.yunjishi.lixiang.yunjishi.presenter.injection.module

import com.yunjishi.lixiang.yunjishi.service.MissionService
import com.yunjishi.lixiang.yunjishi.service.impl.MissionServiceImpl
import dagger.Module
import dagger.Provides

@Module
class MissionModule {
    @Provides
    fun provideMissionService(service: MissionServiceImpl): MissionService{
        return service
    }
}