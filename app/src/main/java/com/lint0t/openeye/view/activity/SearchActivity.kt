package com.lint0t.openeye.view.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.gyf.immersionbar.ktx.immersionBar
import com.lint0t.openeye.OpenEyeApplication.Companion.context
import com.lint0t.openeye.R
import com.lint0t.openeye.bean.RecData
import com.lint0t.openeye.bean.TopData
import com.lint0t.openeye.view.adapter.RecAdapter
import com.lint0t.openeye.viewmodel.SearchViewModel
import com.scwang.smart.refresh.footer.ClassicsFooter
import com.wyt.searchbox.SearchFragment
import kotlinx.android.synthetic.main.activity_search.*
import kotlinx.android.synthetic.main.fragment_daily.*


class SearchActivity : AppCompatActivity() {
    private lateinit var recAdapter: RecAdapter
    private val viewModel by lazy { ViewModelProvider(this).get(SearchViewModel::class.java) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        initBar()
        init()
    }

    private fun init() {
        recAdapter = RecAdapter(context, viewModel.listData)
        rv_search_search.adapter = recAdapter
        refresh_layout_search.setEnableRefresh(false)
        val recLayoutManager = LinearLayoutManager(context)
        rv_search_search.layoutManager = recLayoutManager
        // 搜索框框架
        val searchFragment = SearchFragment.newInstance()
        searchFragment.setOnSearchClickListener { keyword -> // 搜索框搜索处理
            viewModel.loadSearch(keyword)
            img_search_search.alpha = 0f
            img_search_search.animate().alpha(1f).duration = 1000
        }
        searchFragment.showFragment( // 显示搜索框
            supportFragmentManager,
            SearchFragment.TAG
        )
        img_search_search.setOnClickListener {
            searchFragment.showFragment(
                supportFragmentManager,
                SearchFragment.TAG
            )
        }

        recAdapter.setOnItemClickListener(object : RecAdapter.OnItemClickListener {
            override fun onItemClick(view: View, position: Int) {
                val intent = Intent(context, PlayVideoActivity::class.java)
                val bundle = Bundle()
                val topData = TopData(
                    viewModel.listData[position].url,
                    viewModel.listData[position].playUrl,
                    viewModel.listData[position].time,
                    viewModel.listData[position].title,
                    viewModel.listData[position].author,
                    viewModel.listData[position].description ?: "",
                    viewModel.listData[position].id,
                    viewModel.listData[position].blurred
                )
                bundle.putSerializable("data", topData)
                intent.putExtras(bundle)
                startActivity(intent)
            }

            override fun onItemLongClick(view: View, position: Int) {}
        })

        viewModel.searchPathData.observe(this, Observer { result ->
            val list = result.getOrNull()
            if (list !== null) {
                viewModel.listData.clear()
                viewModel.listData.addAll(list)
                recAdapter.notifyDataSetChanged()
                if (viewModel.listData[viewModel.listData.size - 1].nextUrl == null) {
                    refresh_layout_search.setEnableLoadMore(false)
                }
            } else {
                viewModel.listData.clear()
                refresh_layout_search.setEnableLoadMore(false)
                Toast.makeText(context, "加载失败", Toast.LENGTH_SHORT).show()
                result.exceptionOrNull()?.printStackTrace()
            }
        })

        rv_search_search.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    recAdapter.isScrolling = false
                    recAdapter.notifyDataSetChanged()
                } else {
                    recAdapter.isScrolling = true
                }
            }
        })


        refresh_layout_search.setRefreshFooter(ClassicsFooter(context))
        refresh_layout_search.setOnLoadMoreListener {
            refresh_layout_search.finishLoadMore()
            viewModel.loadMore(viewModel.listData[viewModel.listData.size - 1].nextUrl)
        }

        viewModel.morePathData.observe(this, Observer { result ->
            val list = result.getOrNull()
            if (list != null && list.size > 0) {
                viewModel.listData.addAll(list)
                recAdapter.notifyDataSetChanged()
            } else {
                Toast.makeText(context, "已经是最后一个啦", Toast.LENGTH_SHORT).show()
                refresh_layout_search.finishLoadMoreWithNoMoreData()
                refresh_layout_search.setEnableLoadMore(false)
            }
        })
    }


    private fun initBar() {
        //   hideBottomUIMenu(this)
        immersionBar {
            transparentStatusBar()
            transparentNavigationBar()
            fullScreen(true)
            statusBarDarkFont(true)
            navigationBarDarkIcon(true)
            statusBarColor("#FBFBFB")
            navigationBarColor("#FBFBFB")
        }
        val params: WindowManager.LayoutParams = window.attributes
        params.systemUiVisibility =
            View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or View.SYSTEM_UI_FLAG_IMMERSIVE
        window.attributes = params
    }
}
