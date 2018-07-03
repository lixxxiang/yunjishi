package com.yunjishi.lixiang.yunjishi.service

import com.yunjishi.lixiang.yunjishi.presenter.data.bean.SubmitOrderBean
import io.reactivex.Observable

interface ParamsService {
    fun submitOrder(string1: String,string2: String,string3: String,string4: String,string5: String,string6: String,string7: String): Observable<SubmitOrderBean>
}