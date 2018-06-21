package com.android.lixiang.base.data.net

import com.android.lixiang.base.common.BaseConstant
import com.orhanobut.logger.Logger
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class RetrofitFactory (url: String) {
    companion object {
        val instance: RetrofitFactory by lazy { RetrofitFactory(url = String()) }
    }

    private val retrofit: Retrofit
    private val interceptor: Interceptor

    init {

        interceptor = Interceptor { chain ->
            val request = chain.request()
                    .newBuilder()
                    .addHeader("Content_Type", "application/json")
                    .addHeader("charset", "UTF-8")
                    .build()

            chain.proceed(request)
        }



        retrofit = Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(initClient())
                .build()
    }

    private fun initClient(): OkHttpClient? {
        return OkHttpClient.Builder()
                .addInterceptor(initLogInterceptor())
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .build()
    }

    private fun initLogInterceptor(): Interceptor {
//        val interceptor = HttpLoggingInterceptor()
//        interceptor.level = HttpLoggingInterceptor.Level.BODY
        return interceptor
    }

    fun <T> create(service: Class<T>): T {
        Logger.d(retrofit.baseUrl())
        return retrofit.create(service)
    }
}