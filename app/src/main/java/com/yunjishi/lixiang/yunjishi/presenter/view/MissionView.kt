package com.yunjishi.lixiang.yunjishi.presenter.view

import com.android.lixiang.base.presenter.view.BaseView
import com.yunjishi.lixiang.yunjishi.presenter.data.bean.SubmitOrderBean

interface MissionView : BaseView {
    fun onSubmitOrderResult(res: SubmitOrderBean)
}