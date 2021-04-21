package com.sumjin.mvvmdemokt.domain

import com.sumjin.mvvmdemokt.api.ApiException

data class ResultData<T>(
    val success: Boolean,
    val code: Int,
    val message: String,
    val data: T
) {
    companion object {
        const val CODE_SUCCESS = 10000
    }

    fun apiData(): T {
        //如果是成功的code，我们就返回数据，否则抛出异常
        if (code == CODE_SUCCESS){
            return data
        }else{
            throw ApiException(code, message)
        }
    }

}
