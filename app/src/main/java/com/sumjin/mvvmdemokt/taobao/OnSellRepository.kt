package com.sumjin.mvvmdemokt.taobao

import com.sumjin.mvvmdemokt.api.RetrofitClient

class OnSellRepository {

    suspend fun getOnSellList(page: Int) =
        RetrofitClient.apiService.getOnSellList(page).apiData()

}