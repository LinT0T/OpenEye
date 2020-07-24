package com.lint0t.openeye.view.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.gyf.immersionbar.ktx.immersionBar
import com.lint0t.openeye.OpenEyeApplication.Companion.context
import com.lint0t.openeye.R
import com.lint0t.openeye.bean.AllTagData
import com.lint0t.openeye.bean.SpecialData
import com.lint0t.openeye.bean.TopData
import com.lint0t.openeye.view.adapter.SpecialAdapter
import com.lint0t.openeye.viewmodel.AllTagViewModel
import com.scwang.smart.refresh.footer.ClassicsFooter
import com.scwang.smart.refresh.header.ClassicsHeader
import kotlinx.android.synthetic.main.activity_all_tag.*

class AllTagActivity : AppCompatActivity() {
    private val viewModel by lazy { ViewModelProvider(this).get(AllTagViewModel::class.java) }
    private lateinit var allTagAdapter: SpecialAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initBar()
        init()
    }

    private fun init() {
        setContentView(R.layout.activity_all_tag)
        val gridLayoutManager = GridLayoutManager(this, 3)
        gridLayoutManager.orientation = GridLayoutManager.VERTICAL
        rv_all_tag.layoutManager = gridLayoutManager
        viewModel.loadAllTag()
        viewModel.allTagPathData.observe(this, Observer { result ->
            val list = result.getOrNull()
            if (list != null) {
                viewModel.listData.clear()
                viewModel.listData.addAll(list)
                val tagList = mutableListOf<SpecialData>()
                for (i in 0 until list.size) {
                    val specialData = SpecialData(list[i].cover, list[i].title, list[i].id)
                    tagList.add(specialData)
                }

                allTagAdapter = SpecialAdapter(this, tagList)
                rv_all_tag.adapter = allTagAdapter
                rv_all_tag.adapter?.notifyDataSetChanged()
                allTagAdapter.setOnItemClickListener(object : SpecialAdapter.OnItemClickListener {
                    override fun onItemClick(view: View, position: Int) {
                        val intent = Intent(this@AllTagActivity, TagActivity::class.java)
                        intent.putExtra("id", list[position].id)
                        startActivity(intent)
                    }

                    override fun onItemLongClick(view: View, position: Int) {

                    }
                })
            } else {
                val emptyAllTagData = SpecialData(
                    "",
                    "加载失败了,下拉刷新试试",
                    ""
                )
                val emptyAllTagData2 = AllTagData(
                    "加载失败了,下拉刷新试试",
                    "",
                    ""
                )
                val emptyList = mutableListOf(emptyAllTagData)
                val emptyList2 = mutableListOf(emptyAllTagData2)
                viewModel.listData.clear()
                viewModel.listData.addAll(emptyList2)
                allTagAdapter = SpecialAdapter(context, emptyList)
                rv_all_tag.adapter = allTagAdapter
                rv_all_tag.adapter?.notifyDataSetChanged()
                refresh_layout_all_tag.setEnableLoadMore(false)
                Toast.makeText(context, "加载失败了", Toast.LENGTH_SHORT).show()
            }
        })
        refresh_layout_all_tag.setRefreshHeader(ClassicsHeader(context))
        refresh_layout_all_tag.setRefreshFooter(ClassicsFooter(context))
        refresh_layout_all_tag.setOnRefreshListener {
            refresh_layout_all_tag.finishRefresh()
            viewModel.loadAllTag()
            refresh_layout_all_tag.setEnableLoadMore(true)
        }
    }
    private fun initBar() {
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

