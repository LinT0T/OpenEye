package com.lint0t.openeye.view.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.lint0t.openeye.OpenEyeApplication.Companion.context
import com.lint0t.openeye.R
import com.lint0t.openeye.bean.TagRecData
import com.lint0t.openeye.bean.TopData
import com.lint0t.openeye.view.adapter.TagRecAdapter
import com.lint0t.openeye.viewmodel.TagViewModel
import com.scwang.smart.refresh.footer.ClassicsFooter
import com.scwang.smart.refresh.header.ClassicsHeader
import kotlinx.android.synthetic.main.activity_tag.*

class TagActivity : AppCompatActivity() {
    private val viewModel by lazy { ViewModelProvider(this).get(TagViewModel::class.java) }
    private lateinit var tagRecAdapter: TagRecAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tag)
        init()
    }

    private fun init() {
        val linearLayoutManager = LinearLayoutManager(this)
        rv_tag.layoutManager = linearLayoutManager
        val id = intent.getStringExtra("id")
        viewModel.loadTagInfo(id ?: "30")
        viewModel.tagPathData.observe(this, Observer { result ->
            val list = result.getOrNull()
            if (list != null) {
                tv_title_tag.text = list.name
                tv_description_tag.text = list.description
                Glide.with(this).load(list.cover).into(img_cover_tag)
            } else {
                tv_title_tag.text = "加载失败"
            }
        })
        viewModel.tagRecPathData.observe(this, Observer { result ->
            val list = result.getOrNull()
            if (list != null) {
                viewModel.listData.clear()
                viewModel.listData.addAll(list)
                tagRecAdapter = TagRecAdapter(this, list)
                rv_tag.adapter = tagRecAdapter
                rv_tag.adapter?.notifyDataSetChanged()
                tagRecAdapter.setOnItemClickListener(object : TagRecAdapter.OnItemClickListener {
                    override fun onItemClick(view: View, position: Int) {
                        val intent = Intent(this@TagActivity, PlayVideoActivity::class.java)
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
            } else {
                val emptyRecData = TagRecData(
                    "加载失败了,下拉刷新试试",
                    "",
                    "",
                    0,
                    "",
                    "",
                    "",
                    "",
                    "",
                    "",
                    "textCard",
                    "",
                    0, 0, 0, 0
                )
                val emptyList = mutableListOf(emptyRecData)
                viewModel.listData.clear()
                viewModel.listData.addAll(emptyList)
                tagRecAdapter = context?.let { TagRecAdapter(it, emptyList) }!!
                rv_tag.adapter = tagRecAdapter
                rv_tag.adapter?.notifyDataSetChanged()
                refresh_layout_tag.setEnableLoadMore(false)
                Toast.makeText(context, "网络不太好哦，加载失败了>_<", Toast.LENGTH_SHORT).show()
            }
        })
        refresh_layout_tag.setRefreshHeader(ClassicsHeader(context))
        refresh_layout_tag.setRefreshFooter(ClassicsFooter(context))
        refresh_layout_tag.setOnRefreshListener {
            refresh_layout_tag.finishRefresh()
            viewModel.loadTagInfo(id)
            refresh_layout_tag.setEnableLoadMore(true)
        }

        viewModel.morePathData.observe(this, Observer { result ->
            val list = result.getOrNull()
            val emptyData = mutableListOf<TagRecData>()
            if (list != null && list != emptyData) {
                viewModel.listData.addAll(list)
                tagRecAdapter.addMore(list)
                tagRecAdapter.notifyDataSetChanged()
            } else {
                Toast.makeText(context, "已经是最后一个啦", Toast.LENGTH_SHORT).show()
                refresh_layout_tag.finishLoadMoreWithNoMoreData()
                refresh_layout_tag.setEnableLoadMore(false)
            }

        })
        refresh_layout_tag.setOnLoadMoreListener {
            refresh_layout_tag.finishLoadMore()
            viewModel.loadMore(viewModel.listData[viewModel.listData.size - 1].nextUrl)
        }

        rv_tag.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    tagRecAdapter.isScrolling = false
                    tagRecAdapter.notifyDataSetChanged()
                } else {
                    tagRecAdapter.isScrolling = true
                }

            }
        })
    }
}
