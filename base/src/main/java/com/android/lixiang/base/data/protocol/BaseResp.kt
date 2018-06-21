package com.android.lixiang.base.data.protocol

data class BaseResp<out T>(val data: T, val message:String, val status:Int)
