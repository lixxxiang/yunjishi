package com.android.lixiang.base.rx

import com.android.lixiang.base.common.ResultCode
import com.android.lixiang.base.data.protocol.BaseResp
import io.reactivex.Observable
import io.reactivex.functions.Function

/*
    通用数据类型转换封装
 */
class BaseFunc<T> : Function<BaseResp<T>, Observable<T>> {
    override fun apply(t: BaseResp<T>): Observable<T> {
        if (t.status != ResultCode.SUCCESS) {
            return Observable.error(BaseException(t.status, t.message))
        }
        return Observable.just(t.data)
    }
}
