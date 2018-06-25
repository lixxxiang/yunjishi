package com.yunjishi.lixiang.yunjishi.activity

import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.android.lixiang.base.utils.view.StatusBarUtil
import com.yunjishi.lixiang.yunjishi.R

class OrderDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_detail)
        StatusBarUtil.setColor(this, Color.parseColor("#404040"), 0)
    }
}
