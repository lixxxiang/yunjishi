package com.yunjishi.lixiang.yunjishi.service.impl

import com.yunjishi.lixiang.yunjishi.presenter.data.bean.SubmitOrderBean
import com.yunjishi.lixiang.yunjishi.presenter.data.repository.MainRepository
import com.yunjishi.lixiang.yunjishi.service.MissionService
import com.yunjishi.lixiang.yunjishi.service.ParamsService
import io.reactivex.Observable
import io.reactivex.ObservableSource
import io.reactivex.functions.Function
import javax.inject.Inject


class ParamsServiceImpl @Inject constructor(): ParamsService {
    @Inject
    lateinit var mainRepository: MainRepository
    override fun submitOrder(string1: String,string2: String,string3: String,string4: String,string5: String,string6: String,string7: String): Observable<SubmitOrderBean> {
        return mainRepository.submitOrder(string1, string2, string3, string4, string5, string6, string7).flatMap(Function<SubmitOrderBean, ObservableSource<SubmitOrderBean>> { t ->
            return@Function Observable.just(t)
        })
    }
}