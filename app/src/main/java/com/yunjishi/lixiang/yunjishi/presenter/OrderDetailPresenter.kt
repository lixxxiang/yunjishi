package com.yunjishi.lixiang.yunjishi.presenter

import com.android.lixiang.base.ext.execute
import com.android.lixiang.base.presenter.BasePresenter
import com.android.lixiang.base.rx.BaseObserver
import com.yunjishi.lixiang.yunjishi.presenter.data.bean.DemandDetailBean
import com.yunjishi.lixiang.yunjishi.presenter.data.bean.SubmitOrderBean
import com.yunjishi.lixiang.yunjishi.presenter.view.MissionView
import com.yunjishi.lixiang.yunjishi.presenter.view.OrderDetailView
import com.yunjishi.lixiang.yunjishi.service.MissionService
import com.yunjishi.lixiang.yunjishi.service.OrderDetailService
import javax.inject.Inject

class OrderDetailPresenter @Inject constructor() : BasePresenter<OrderDetailView>() {
    @Inject
    lateinit var mOrderDetailService: OrderDetailService

    fun getDemandDetail(string1: String, string2: String) {
        mOrderDetailService.getDemandDetail(string1, string2).execute(object : BaseObserver<DemandDetailBean>() {
            override fun onNext(t: DemandDetailBean) {
                super.onNext(t)
                mView.onGetDemandDetailResult(t)
            }
        }, lifecycleProvider)
    }

}