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
        val initRecData = RecData("搜索感兴趣的东西吧", "", "", 0, "", "", "", "", "", "", "textCard", "")
        recAdapter = RecAdapter(context, mutableListOf(initRecData))
        rv_search_search.adapter = recAdapter
        refresh_layout_search.setEnableLoadMore(false)
        recAdapter.notifyDataSetChanged()
        val recLayoutManager = LinearLayoutManager(context)
        rv_search_search.layoutManager = recLayoutManager
        viewModel.searchPathData.observe(this, Observer { result ->
            val list = result.getOrNull()
            if (list !== null) {
                viewModel.listData.clear()
                viewModel.listData.addAll(list)
                recAdapter = RecAdapter(context, list)
                rv_search_search.adapter = recAdapter
                rv_search_search.adapter?.notifyDataSetChanged()
                recAdapter.setOnItemClickListener(object : RecAdapter.OnItemClickListener {
                    override fun onItemClick(view: View, position: Int) {
                        val intent = Intent(context, PlayVideoActivity::class.java)
                        val bundle = Bundle()
                        val topData = TopData(
                            list[position].url,
                            list[position].playUrl,
                            list[position].time,
                            list[position].title,
                            list[position].author,
                            list[position].description ?: "",
                            list[position].id,
                            list[position].blurred
                        )
                        bundle.putSerializable("data", topData)
                        intent.putExtras(bundle)
                        startActivity(intent)
                    }

                    override fun onItemLongClick(view: View, position: Int) {

                    }
                })
                refresh_layout_search.setEnableLoadMore(true)
            } else {
                val emptyRecData =
                    RecData("加载失败了", "", "", 0, "", "", "", "", "", "", "textCard", "")
                val emptyList = mutableListOf(emptyRecData)
                viewModel.listData.clear()
                viewModel.listData.addAll(emptyList)
                recAdapter = RecAdapter(context, emptyList)
                rv_search_search.adapter = recAdapter
                rv_search_search.adapter?.notifyDataSetChanged()
                refresh_layout_search.setEnableLoadMore(false)
                Toast.makeText(context, "网络不太好哦", Toast.LENGTH_SHORT).show()
            }
        })

        val searchFragment = SearchFragment.newInstance()
        searchFragment.setOnSearchClickListener { keyword -> //这里处理逻辑
            viewModel.loadSearch(keyword)
            img_search_search.alpha = 0f
            img_search_search.animate().alpha(1f).duration = 1000

        }
        searchFragment.showFragment(
            supportFragmentManager,
            SearchFragment.TAG
        )
        img_search_search.setOnClickListener {
            searchFragment.showFragment(
                supportFragmentManager,
                SearchFragment.TAG
            )
        }
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
        viewModel.morePathData.observe(this, Observer { result ->
            val list = result.getOrNull()
            val emptyData = mutableListOf<RecData>()
            if (list != null && list != emptyData) {
                viewModel.listData.addAll(list)
                recAdapter.addMore(list)
                recAdapter.notifyDataSetChanged()
            } else {
                Toast.makeText(context, "已经是最后一个啦", Toast.LENGTH_SHORT).show()
                refresh_layout_search.finishLoadMoreWithNoMoreData()
                refresh_layout_search.setEnableLoadMore(false)
            }

        })
        refresh_layout_search.setRefreshFooter(ClassicsFooter(context))
        refresh_layout_search.setEnableRefresh(false)
        refresh_layout_search.setOnLoadMoreListener {
            refresh_layout_search.finishLoadMore()
            viewModel.loadMore(viewModel.listData[viewModel.listData.size - 1].nextUrl)
        }
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
