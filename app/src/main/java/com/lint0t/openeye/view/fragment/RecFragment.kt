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
import com.lint0t.openeye.bean.RecData
import com.lint0t.openeye.bean.TopData
import com.lint0t.openeye.view.activity.PlayVideoActivity
import com.lint0t.openeye.view.adapter.RecAdapter
import com.lint0t.openeye.viewmodel.DiscoveryFragmentViewModel
import com.lint0t.openeye.viewmodel.RecFragmentViewModel
import com.scwang.smart.refresh.footer.ClassicsFooter
import com.scwang.smart.refresh.header.ClassicsHeader
import com.scwang.smart.refresh.layout.api.RefreshLayout
import com.scwang.smart.refresh.layout.listener.OnLoadMoreListener
import com.scwang.smart.refresh.layout.listener.OnRefreshListener
import kotlinx.android.synthetic.main.fragment_discovery.*
import kotlinx.android.synthetic.main.fragment_rec.*

class RecFragment : Fragment() {
    private val viewModel by lazy { ViewModelProvider(this).get(RecFragmentViewModel::class.java) }
    private lateinit var recAdapter: RecAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_rec, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val recLayoutManager = LinearLayoutManager(context)
        recAdapter = context?.let { RecAdapter(it, mutableListOf()) }!!
        rv_rec.layoutManager = recLayoutManager
        init()
        viewModel.recPathData.observe(viewLifecycleOwner, Observer { result ->
            val list = result.getOrNull()
            if (list !== null) {
                viewModel.listData.clear()
                viewModel.listData.addAll(list)
                recAdapter = context?.let { RecAdapter(it, list) }!!
                rv_rec.adapter = recAdapter
                rv_rec.adapter?.notifyDataSetChanged()
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
                            list[position].description?:"",
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
                val emptyRecData = RecData("加载失败了,下拉刷新试试","","",0,"","","","","","","textCard","")
                val emptyList = mutableListOf(emptyRecData)
                viewModel.listData.clear()
                viewModel.listData.addAll(emptyList)
                recAdapter = context?.let { RecAdapter(it, emptyList) }!!
                rv_rec.adapter = recAdapter
                rv_rec.adapter?.notifyDataSetChanged()
                refresh_layout.setEnableLoadMore(false)
                Toast.makeText(context, "网络不太好哦，首页加载失败了>_<", Toast.LENGTH_SHORT).show()
            }
        })
        refresh_layout.setRefreshHeader(ClassicsHeader(context))
        refresh_layout.setRefreshFooter(ClassicsFooter(context))
        refresh_layout.setOnRefreshListener {
            refresh_layout.finishRefresh()
            init()
        }
        viewModel.morePathData.observe(viewLifecycleOwner, Observer { result ->
            val list = result.getOrNull()
            val emptyData = mutableListOf<RecData>()
            if (list != null && list != emptyData) {
                viewModel.listData.addAll(list)
                recAdapter.addMore(list)
                recAdapter.notifyDataSetChanged()
            } else {
                Toast.makeText(context, "已经是最后一个啦", Toast.LENGTH_SHORT).show()
                refresh_layout.finishLoadMoreWithNoMoreData()
                refresh_layout.setEnableLoadMore(false)
            }

        })
        refresh_layout.setOnLoadMoreListener {
            refresh_layout.finishLoadMore()
                viewModel.loadMore(viewModel.listData[viewModel.listData.size - 1].nextUrl)
        }

        rv_rec.addOnScrollListener(object : RecyclerView.OnScrollListener(){
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                if (newState == RecyclerView.SCROLL_STATE_IDLE){
                    recAdapter.isScrolling = false
                    recAdapter.notifyDataSetChanged()
                } else
                {
                    recAdapter.isScrolling = true
                }
            }
        })
    }

    private fun init() {
        refresh_layout.setEnableLoadMore(true)
        viewModel.loadRec()

        refresh_layout.setEnableLoadMore(true)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
    }


}