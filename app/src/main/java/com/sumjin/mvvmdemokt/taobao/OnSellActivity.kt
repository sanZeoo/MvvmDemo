package com.sumjin.mvvmdemokt.taobao

import android.graphics.Rect
import android.os.Bundle
import android.os.PersistableBundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout
import com.sumjin.mvvmdemokt.R
import com.sumjin.mvvmdemokt.adapter.OnSellListAdapter
import com.sumjin.mvvmdemokt.domain.LoadState
import com.sumjin.mvvmdemokt.util.SizeUtils
import kotlinx.android.synthetic.main.activity_on_error.*
import kotlinx.android.synthetic.main.activity_on_sell.*

class OnSellActivity : AppCompatActivity() {

    private val mViewModel by lazy {
        ViewModelProvider(this).get(OnSellViewModel::class.java)
    }

    private val mAdapter by lazy {
        OnSellListAdapter()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_on_sell)
        initView()
        initObserve()
        //mViewModel.loadContent()
    }


    //观察数据变化
    private fun initObserve() {
        mViewModel.apply {
            contentList.observe(this@OnSellActivity, Observer {
                //内容列表更新
                //更新适配器
                mAdapter.setData(it)

            })
            loadState.observe(this@OnSellActivity, Observer {
                //根据状态来显示UI控件
                if (it != LoadState.LOADER_MORE_LOADING) {
                    hideAll()
                }
                println(it)

                when (it) {
                    LoadState.LOADING -> {
                        loadingView.visibility = View.VISIBLE
                    }
                    LoadState.EMPTY -> {
                        emptyView.visibility = View.VISIBLE
                    }
                    LoadState.ERROR -> {
                        errorView.visibility = View.VISIBLE
                    }
                    else ->{
                        contentRefreshView.visibility = View.VISIBLE
                        contentRefreshView.finishLoadmore()
                    }
                }
                when (it){
                    LoadState.LOADER_MORE_ERROR -> {
                        Toast.makeText(this@OnSellActivity, "网络不佳，请稍后重试", Toast.LENGTH_SHORT).show()
                    }
                    LoadState.LOADER_MORE_EMPTY -> {
                        Toast.makeText(this@OnSellActivity, "已经加载全部内容", Toast.LENGTH_SHORT).show()
                    }
                }

            })

        }.loadContent()
    }

    //初始化控件
    private fun initView() {
        contentRefreshView.run {
            setEnableLoadmore(true)
            setEnableRefresh(false)
            setEnableOverScroll(true)
            setOnRefreshListener(object : RefreshListenerAdapter() {
                override fun onLoadMore(refreshLayout: TwinklingRefreshLayout?) {
                    //开始执行加载更多
                    mViewModel.loaderMore()
                }
            })
        }

        reloadLL.setOnClickListener {
            mViewModel.loadContent()
        }

        contentListRv.run {
            layoutManager = LinearLayoutManager(this@OnSellActivity)
            adapter = mAdapter
            addItemDecoration(
                object : RecyclerView.ItemDecoration() {
                    override fun getItemOffsets(
                        outRect: Rect,
                        view: View,
                        parent: RecyclerView,
                        state: RecyclerView.State
                    ) {
                        outRect.apply {
                            val padding = SizeUtils.dip2px(this@OnSellActivity, 4.0f)
                            top = padding
                            bottom = padding
                            right = padding
                            left = padding
                        }
                    }
                }

            )
        }
    }

    private fun hideAll() {
        contentRefreshView.visibility = View.GONE
        emptyView.visibility = View.GONE
        loadingView.visibility = View.GONE
        errorView.visibility = View.GONE
    }
}