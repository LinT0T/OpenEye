package com.lint0t.openeye.view.fragment

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.lint0t.openeye.R
import com.lint0t.openeye.bean.CommunityFollowData
import com.lint0t.openeye.bean.TopData
import com.lint0t.openeye.view.activity.PlayVideoActivity
import com.lint0t.openeye.view.adapter.AutoPlayAdapter
import com.lint0t.openeye.viewmodel.CommunityFollowFragmentViewModel
import com.scwang.smart.refresh.footer.ClassicsFooter
import com.scwang.smart.refresh.header.ClassicsHeader
import kotlinx.android.synthetic.main.fragment_community_follow.*

class CommunityFollowFragment : Fragment() {
    private val viewModel by lazy { ViewModelProvider(this).get(CommunityFollowFragmentViewModel::class.java) }
    private lateinit var followAdapter: AutoPlayAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_community_follow, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val recLayoutManager = LinearLayoutManager(context)
        rv_community_follow.layoutManager = recLayoutManager
        viewModel.loadFollow()
        viewModel.communityFollowPathData.observe(viewLifecycleOwner, Observer { result ->
            val list = result.getOrNull()
            if (list !== null) {
                viewModel.listData.clear()
                viewModel.listData.addAll(list)
                followAdapter = context?.let { AutoPlayAdapter(it, list) }!!
                rv_community_follow.adapter = followAdapter
                rv_community_follow.adapter?.notifyDataSetChanged()
                followAdapter.setOnItemClickListener(object : AutoPlayAdapter.OnItemClickListener {
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
            } else {
                val emptyRecData =
                    CommunityFollowData(
                        "",
                        "",
                        0,
                        0,
                        "",
                        "加载失败了,下拉刷新试试",
                        "",
                        "",
                        "",
                        "textCard",
                        0,
                        0,
                        0,
                        "",
                        ""
                    )
                val emptyList = mutableListOf(emptyRecData)
                viewModel.listData.clear()
                viewModel.listData.addAll(emptyList)
                followAdapter = context?.let { AutoPlayAdapter(it, emptyList) }!!
                rv_community_follow.adapter = followAdapter
                rv_community_follow.adapter?.notifyDataSetChanged()
                refresh_layout_community_follow.setEnableLoadMore(false)
                Toast.makeText(context, "日报加载失败了>_<", Toast.LENGTH_SHORT).show()
            }
        })
        refresh_layout_community_follow.setRefreshHeader(ClassicsHeader(context))
        refresh_layout_community_follow.setRefreshFooter(ClassicsFooter(context))
        refresh_layout_community_follow.setOnRefreshListener {
            refresh_layout_community_follow.finishRefresh()
            viewModel.loadFollow()
            refresh_layout_community_follow.setEnableLoadMore(true)
        }

        viewModel.morePathData.observe(viewLifecycleOwner, Observer { result ->
            val list = result.getOrNull()
            val emptyData = mutableListOf<CommunityFollowData>()
            if (list != null && list != emptyData) {
                viewModel.listData.addAll(list)
                followAdapter.addMore(list)
                followAdapter.notifyDataSetChanged()
            } else {
                Toast.makeText(context, "已经是最后一个啦", Toast.LENGTH_SHORT).show()
                refresh_layout_community_follow.finishLoadMoreWithNoMoreData()
                refresh_layout_community_follow.setEnableLoadMore(false)
            }

        })
        refresh_layout_community_follow.setOnLoadMoreListener {
            refresh_layout_community_follow.finishLoadMore()
            viewModel.loadMore(viewModel.listData[viewModel.listData.size - 1].nextUrl)
            println(viewModel.listData[viewModel.listData.size - 1].nextUrl)
        }

        rv_community_follow.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    followAdapter.isScrolling = false
                    followAdapter.notifyDataSetChanged()
                } else {
                    followAdapter.isScrolling = true
                }
            }
        })
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
    }


}