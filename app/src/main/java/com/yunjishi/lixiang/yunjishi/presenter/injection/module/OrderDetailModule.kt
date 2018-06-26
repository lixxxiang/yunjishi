package com.yunjishi.lixiang.yunjishi.presenter.injection.module

import com.yunjishi.lixiang.yunjishi.service.MissionService
import com.yunjishi.lixiang.yunjishi.service.OrderDetailService
import com.yunjishi.lixiang.yunjishi.service.impl.MissionServiceImpl
import com.yunjishi.lixiang.yunjishi.service.impl.OrderDetailServiceImpl
import dagger.Module
import dagger.Provides

@Module
class OrderDetailModule {
    @Provides
    fun provideOrderDetailService(service: OrderDetailServiceImpl): OrderDetailService {
        return service
    }
}