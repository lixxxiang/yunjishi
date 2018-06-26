package com.yunjishi.lixiang.yunjishi.service.impl

import com.yunjishi.lixiang.yunjishi.presenter.data.bean.DemandDetailBean
import com.yunjishi.lixiang.yunjishi.presenter.data.bean.SubmitOrderBean
import com.yunjishi.lixiang.yunjishi.presenter.data.repository.MainRepository
import com.yunjishi.lixiang.yunjishi.presenter.data.repository.OrderDetailRepository
import com.yunjishi.lixiang.yunjishi.service.MissionService
import com.yunjishi.lixiang.yunjishi.service.OrderDetailService
import io.reactivex.Observable
import io.reactivex.ObservableSource
import io.reactivex.functions.Function
import javax.inject.Inject


class OrderDetailServiceImpl @Inject constructor(): OrderDetailService {
    override fun getDemandDetail(string1: String, string2: String): Observable<DemandDetailBean> {
        return orderDetailRepository.getDemandDetail(string1, string2).flatMap(Function<DemandDetailBean, ObservableSource<DemandDetailBean>> { t ->
            return@Function Observable.just(t)
        })
    }

    @Inject
    lateinit var orderDetailRepository: OrderDetailRepository
}