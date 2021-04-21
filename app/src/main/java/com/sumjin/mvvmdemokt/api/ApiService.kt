package com.sumjin.mvvmdemokt.api

import com.sumjin.mvvmdemokt.domain.OnSellData
import com.sumjin.mvvmdemokt.domain.ResultData
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    //const val==常量 public final static 自定判单类型
    companion object {
        const val BASE_URL = "https://api.sunofbeach.net/shop/"
    }

    //suspend fun  支持挂起
    @GET("onSell/{page}")
    suspend fun getOnSellList(@Path("page") page: Int): ResultData<OnSellData>

}