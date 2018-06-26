package com.yunjishi.lixiang.yunjishi.service

import com.yunjishi.lixiang.yunjishi.presenter.data.bean.DemandDetailBean
import com.yunjishi.lixiang.yunjishi.presenter.data.bean.SubmitOrderBean
import io.reactivex.Observable

interface OrderDetailService {
    fun getDemandDetail(string1: String,string2: String): Observable<DemandDetailBean>
}