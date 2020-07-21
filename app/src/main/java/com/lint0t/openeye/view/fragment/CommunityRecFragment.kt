package com.lint0t.openeye.view.fragment

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.lint0t.openeye.R
import com.lint0t.openeye.bean.CommunityRecData
import com.lint0t.openeye.view.activity.BigCoverActivity
import com.lint0t.openeye.view.adapter.CommunityRecAdapter
import com.lint0t.openeye.viewmodel.CommunityRecFragmentViewModel
import com.scwang.smart.refresh.footer.ClassicsFooter
import com.scwang.smart.refresh.header.ClassicsHeader
import kotlinx.android.synthetic.main.fragment_community_rec.*

class CommunityRecFragment : Fragment() {
    private val viewModel by lazy { ViewModelProvider(this).get(CommunityRecFragmentViewModel::class.java) }
    private lateinit var communityRecAdapter: CommunityRecAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_community_rec, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val staggeredGridLayoutManager =
            StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        staggeredGridLayoutManager.gapStrategy = StaggeredGridLayoutManager.GAP_HANDLING_NONE
        rv_community_rec.layoutManager = staggeredGridLayoutManager
        viewModel.loadCommunity()
        viewModel.communityPathData.observe(viewLifecycleOwner, Observer { result ->
            val list = result.getOrNull()
            if (list !== null) {
                viewModel.listData.clear()
                viewModel.listData.addAll(list)
                communityRecAdapter = context?.let { CommunityRecAdapter(it, list) }!!
                communityRecAdapter.setOnItemClickListener(object :
                    CommunityRecAdapter.OnItemClickListener {
                    override fun onItemClick(view: View, position: Int) {
                        val intent = Intent(context, BigCoverActivity::class.java)
                        intent.putExtra("url", viewModel.listData[position].bigCover)
                        startActivity(intent)
                    }

                    override fun onItemLongClick(view: View, position: Int) {

                    }
                })
                rv_community_rec.adapter = communityRecAdapter
            } else {
                val emptyList = mutableListOf<CommunityRecData>()
                communityRecAdapter = context?.let { CommunityRecAdapter(it, emptyList) }!!
                communityRecAdapter.setOnItemClickListener(object :
                    CommunityRecAdapter.OnItemClickListener {
                    override fun onItemClick(view: View, position: Int) {
                    }

                    override fun onItemLongClick(view: View, position: Int) {

                    }
                })
                rv_community_rec.adapter = communityRecAdapter
                rv_community_rec.adapter?.notifyDataSetChanged()
                Log.e("community rec", "list is null")
                refresh_layout_community_rec.setEnableLoadMore(false)
            }
        })


        refresh_layout_community_rec.setRefreshHeader(ClassicsHeader(context))
        refresh_layout_community_rec.setRefreshFooter(ClassicsFooter(context))
        refresh_layout_community_rec.setOnRefreshListener {
            refresh_layout_community_rec.finishRefresh()
            //viewModel.loadCommunity()
        }
        viewModel.morePathData.observe(viewLifecycleOwner, Observer { result ->
            val list = result.getOrNull()
            val emptyData = mutableListOf<CommunityRecData>()
            if (list != null && list != emptyData) {
                viewModel.listData.addAll(list)
                communityRecAdapter.addMore(list)
                communityRecAdapter.notifyDataSetChanged()
            } else {
                Toast.makeText(context, "已经是最后一个啦", Toast.LENGTH_SHORT).show()
                refresh_layout_community_rec.finishLoadMoreWithNoMoreData()
                refresh_layout_community_rec.setEnableLoadMore(false)
            }

        })
        refresh_layout_community_rec.setOnLoadMoreListener {
            refresh_layout_community_rec.finishLoadMore()
            viewModel.loadMore(viewModel.listData[viewModel.listData.size - 1].nextUrl)
        }
    }


    override fun onAttach(context: Context) {
        super.onAttach(context)
    }


}