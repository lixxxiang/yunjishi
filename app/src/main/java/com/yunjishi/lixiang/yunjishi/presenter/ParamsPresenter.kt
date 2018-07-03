package com.yunjishi.lixiang.yunjishi.presenter

import com.android.lixiang.base.ext.execute
import com.android.lixiang.base.presenter.BasePresenter
import com.android.lixiang.base.rx.BaseObserver
import com.yunjishi.lixiang.yunjishi.presenter.data.bean.SubmitOrderBean
import com.yunjishi.lixiang.yunjishi.presenter.view.MissionView
import com.yunjishi.lixiang.yunjishi.presenter.view.ParamsView
import com.yunjishi.lixiang.yunjishi.service.MissionService
import com.yunjishi.lixiang.yunjishi.service.ParamsService
import javax.inject.Inject

class ParamsPresenter @Inject constructor() : BasePresenter<ParamsView>() {
    @Inject
    lateinit var mParamsService: ParamsService

    fun submitOrder(string1: String, string2: String, string3: String, string4: String, string5: String, string6: String, string7: String) {
        mParamsService.submitOrder(string1, string2, string3, string4, string5, string6, string7).execute(object : BaseObserver<SubmitOrderBean>() {
            override fun onNext(t: SubmitOrderBean) {
                super.onNext(t)
                mView.onSubmitOrderResult(t)
            }
        }, lifecycleProvider)
    }

}