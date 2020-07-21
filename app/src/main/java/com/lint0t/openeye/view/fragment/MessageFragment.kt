package com.lint0t.openeye.view.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.lint0t.openeye.R
import com.lint0t.openeye.bean.MessageData
import com.lint0t.openeye.view.adapter.MessageAdapter
import com.lint0t.openeye.viewmodel.MessageFragmentViewModel
import com.scwang.smart.refresh.footer.ClassicsFooter
import com.scwang.smart.refresh.header.ClassicsHeader
import kotlinx.android.synthetic.main.fragment_message.*

class MessageFragment : Fragment() {
    private val viewModel by lazy { ViewModelProvider(this).get(MessageFragmentViewModel::class.java) }
    private lateinit var messageAdapter: MessageAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_message, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tb_message.addTab(tb_message.newTab().setText("推送"))
        val linearLayoutManager = LinearLayoutManager(context)
        rv_message.layoutManager = linearLayoutManager
        viewModel.loadMessage()
        viewModel.messagePathData.observe(viewLifecycleOwner, Observer { result ->
            val list = result.getOrNull()
            if (list !== null) {
                viewModel.listData.clear()
                viewModel.listData.addAll(list)
                messageAdapter = context?.let { MessageAdapter(it, list) }!!
                rv_message.adapter = messageAdapter
                rv_message.adapter?.notifyDataSetChanged()

            } else {
                val emptyRecData =
                    MessageData(
                        "",
                        "",
                        0,
                        "0"
                    )
                val emptyList = mutableListOf(emptyRecData)
                viewModel.listData.clear()
                viewModel.listData.addAll(emptyList)
                messageAdapter = context?.let { MessageAdapter(it, list) }!!
                rv_message.adapter = messageAdapter
                rv_message.adapter?.notifyDataSetChanged()
                refresh_layout_message.setEnableLoadMore(false)
                Toast.makeText(context, "推送加载失败了>_<", Toast.LENGTH_SHORT).show()
            }
        })
        refresh_layout_message.setRefreshHeader(ClassicsHeader(context))
        refresh_layout_message.setRefreshFooter(ClassicsFooter(context))
        refresh_layout_message.setOnRefreshListener {
            refresh_layout_message.finishRefresh()
            viewModel.loadMessage()
            refresh_layout_message.setEnableLoadMore(true)
        }

        viewModel.morePathData.observe(viewLifecycleOwner, Observer { result ->
            val list = result.getOrNull()
            val emptyData = mutableListOf<MessageData>()
            if (list != null && list != emptyData) {
                viewModel.listData.addAll(list)
                messageAdapter.addMore(list)
                messageAdapter.notifyDataSetChanged()
            } else {
                Toast.makeText(context, "已经是最后一个啦", Toast.LENGTH_SHORT).show()
                refresh_layout_message.finishLoadMoreWithNoMoreData()
                refresh_layout_message.setEnableLoadMore(false)
            }

        })
        refresh_layout_message.setOnLoadMoreListener {
            refresh_layout_message.finishLoadMore()
            viewModel.loadMore(viewModel.listData[viewModel.listData.size - 1].nextUrl)
            println(viewModel.listData[viewModel.listData.size - 1].nextUrl)
        }

    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
    }
}