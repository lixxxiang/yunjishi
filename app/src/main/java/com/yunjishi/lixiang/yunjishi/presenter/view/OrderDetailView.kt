package com.yunjishi.lixiang.yunjishi.presenter.view

import com.android.lixiang.base.presenter.view.BaseView
import com.yunjishi.lixiang.yunjishi.presenter.data.bean.DemandDetailBean
import com.yunjishi.lixiang.yunjishi.presenter.data.bean.SubmitOrderBean

interface OrderDetailView : BaseView {
    fun onGetDemandDetailResult(res: DemandDetailBean)
}