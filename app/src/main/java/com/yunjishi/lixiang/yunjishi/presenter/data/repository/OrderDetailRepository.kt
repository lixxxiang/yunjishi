package com.yunjishi.lixiang.yunjishi.presenter.data.repository

import com.android.lixiang.base.common.BaseConstant
import com.android.lixiang.base.data.net.RetrofitFactory
import com.yunjishi.lixiang.yunjishi.presenter.data.bean.SubmitOrderBean
import com.yunjishi.lixiang.yunjishi.presenter.data.api.Api
import com.yunjishi.lixiang.yunjishi.presenter.data.bean.DemandDetailBean
import io.reactivex.Observable
import javax.inject.Inject

class OrderDetailRepository @Inject constructor() {
    fun getDemandDetail(string1: String,string2: String): Observable<DemandDetailBean> {
        return RetrofitFactory(BaseConstant.SERVER_ADDRESS_8081)
                .create(Api::class.java)
                .getDemandDetail(string1, string2)
    }
}