package com.yunjishi.lixiang.yunjishi.presenter.data.repository

import com.android.lixiang.base.common.BaseConstant
import com.android.lixiang.base.data.net.RetrofitFactory
import com.yunjishi.lixiang.yunjishi.presenter.data.bean.SubmitOrderBean
import com.yunjishi.lixiang.yunjishi.presenter.data.api.Api
import io.reactivex.Observable
import javax.inject.Inject

class MainRepository @Inject constructor() {
    fun submitOrder(string1: String,string2: String,string3: String,string4: String,string5: String,string6: String,string7: String): Observable<SubmitOrderBean> {
        return RetrofitFactory(BaseConstant.SERVER_ADDRESS_8081)
                .create(Api::class.java)
                .submitOrder(string1, string2, string3, string4, string5, string6, string7)
    }
}