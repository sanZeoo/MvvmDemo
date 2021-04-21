package com.sumjin.mvvmdemokt.taobao

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sumjin.mvvmdemokt.domain.LoadState
import com.sumjin.mvvmdemokt.domain.MapData
import kotlinx.coroutines.launch
import java.lang.Exception
import java.lang.NullPointerException

class OnSellViewModel : ViewModel() {

    val contentList = MutableLiveData<MutableList<MapData>>()

    val loadState = MutableLiveData<LoadState>()

    private val onSellRepository by lazy {
        OnSellRepository()
    }

    companion object {
        //默认第一页
        const val DEFAULT_PAGE = 1
    }

    //当前页
    private var mCurrentPage = DEFAULT_PAGE

    private var isLoaderMore = false

    //加载更多
    fun loaderMore() {
        isLoaderMore = true
        //去加载更多
        loadState.value = LoadState.LOADER_MORE_LOADING
        mCurrentPage++
        this.listContentByPage(mCurrentPage)
    }

    //加载首页内容
    fun loadContent() {
        isLoaderMore = false
        loadState.value = LoadState.LOADING
        listContentByPage(mCurrentPage)
    }

    private fun listContentByPage(page: Int) {
        //挂起的方式 免去了回调
        viewModelScope.launch {

            try {
                val onSellList = onSellRepository.getOnSellList(page)

                val oldValue = contentList.value?: mutableListOf()
                println("result size ==" + onSellList.tbk_dg_optimus_material_response.result_list.map_data.size)

                oldValue.addAll(onSellList.tbk_dg_optimus_material_response.result_list.map_data)
                contentList.value = oldValue
                if (onSellList.tbk_dg_optimus_material_response.result_list.map_data.isEmpty()) {
                    loadState.value =
                        if (isLoaderMore) LoadState.LOADER_MORE_EMPTY else LoadState.EMPTY
                } else {
//                    contentList.postValue(onSellList.tbk_dg_optimus_material_response.result_list.map_data)
                    loadState.value = LoadState.SUCEESS
                }

            } catch (e: Exception) {
                mCurrentPage--
                e.printStackTrace()
                if (e is NullPointerException) {
                    //没有更多
                    loadState.value = LoadState.LOADER_MORE_EMPTY
                } else {
                    loadState.value =
                        if (isLoaderMore) LoadState.LOADER_MORE_ERROR else LoadState.ERROR
                }

            }

        }
    }


}