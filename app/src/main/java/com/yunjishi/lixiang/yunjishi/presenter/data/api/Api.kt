package com.yunjishi.lixiang.yunjishi.presenter.data.api

import com.yunjishi.lixiang.yunjishi.presenter.data.bean.DemandDetailBean
import com.yunjishi.lixiang.yunjishi.presenter.data.bean.SubmitOrderBean
import io.reactivex.Observable
import retrofit2.http.*

interface Api {
    @POST("submitOrder")
    @FormUrlEncoded
    fun submitOrder(@Field("userId") targetSentence: String,
                    @Field("demandType") targetSentence2: String, @Field("demandGeo") targetSentenc3: String,
                    @Field("resolution") targetSentence4: String, @Field("startTime") targetSentenc5: String,
                    @Field("endTime") targetSentence6: String, @Field("times") targetSentenc7: String): Observable<SubmitOrderBean>


    @Headers("Content-Type:text/html;charset=utf-8", "Accept:application/json;")
    @GET("getDemandDetail")
    fun getDemandDetail(@Query("userId") targetSentence: String,
                        @Query("demandId") targetSentence2: String): Observable<DemandDetailBean>
}