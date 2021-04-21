package com.sumjin.mvvmdemokt.api

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * 创建Retrofit已经提供的Api Service  object静态
 */
object RetrofitClient {
    //val 不可变的  var
    val okHttpClient=OkHttpClient.Builder()
        .callTimeout(30,TimeUnit.SECONDS)
        .build()

    val retrofit = Retrofit.Builder()
        .baseUrl(ApiService.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(okHttpClient)
        .build()

    val apiService=retrofit.create(ApiService::class.java)
}