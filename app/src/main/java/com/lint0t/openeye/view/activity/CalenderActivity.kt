package com.lint0t.openeye.view.activity

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.bumptech.glide.Glide
import com.gyf.immersionbar.ktx.immersionBar
import com.lint0t.openeye.OpenEyeApplication.Companion.context
import com.lint0t.openeye.R
import com.lint0t.openeye.bean.CommunityRecData
import com.lint0t.openeye.view.adapter.CommunityRecAdapter
import com.lint0t.openeye.viewmodel.CalenderViewModel
import com.scwang.smart.refresh.footer.ClassicsFooter
import com.scwang.smart.refresh.header.ClassicsHeader
import kotlinx.android.synthetic.main.activity_calender.*
import kotlinx.android.synthetic.main.fragment_community_rec.*
import java.text.SimpleDateFormat
import java.util.*

class CalenderActivity : AppCompatActivity() {
    private val viewModel by lazy { ViewModelProvider(this).get(CalenderViewModel::class.java) }
    private lateinit var communityRecAdapter: CommunityRecAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calender)
        initBar()
        val staggeredGridLayoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        staggeredGridLayoutManager.gapStrategy = StaggeredGridLayoutManager.GAP_HANDLING_NONE
        rv_calender.layoutManager = staggeredGridLayoutManager
        communityRecAdapter = CommunityRecAdapter(this, viewModel.listData)
        rv_calender.adapter = communityRecAdapter
        val now = System.currentTimeMillis()
        val form = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        form.timeZone = TimeZone.getTimeZone("GMT+08:00")
        val ymd = form.format(now)
        val day = ymd.substring(IntRange(8, 9))
        val month = ymd.substring(IntRange(5, 6))
        val year = ymd.substring(IntRange(0, 3))
        val date = ymd.substring(IntRange(0, 10))
        println("$year $month $day $date")
        viewModel.loadCalender(date)
        viewModel.calenderPathData.observe(this, androidx.lifecycle.Observer { result ->
            val calenderData = result.getOrNull()
            if (calenderData != null) {
                val list = calenderData.listCommunityRecData
                viewModel.listData.clear()
                viewModel.listData.addAll(list)
                tv_day_calender.animateText(day)
                tv_month_calender.animateText("${month}月")
                tv_year_calender.animateText("${year}年")
                tv_week_destination_calender.animateText(calenderData.weeklyDestination)
                tv_daily_destination_calender.animateText(calenderData.dailyDestination)
                tv_rec_reason_calender.animateText(calenderData.recReason)
                tv_second_week_destination_calender.animateText(calenderData.weeklyDestination2)
                tv_second_rec_reason_calender.animateText(calenderData.recReason2)
                Glide.with(this).load(calenderData.imageUrl).into(img_cover_calender)
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
                rv_calender.adapter?.notifyDataSetChanged()
            } else {
                viewModel.listData.clear()
                rv_calender.adapter?.notifyDataSetChanged()
                Toast.makeText(context,"加载失败",Toast.LENGTH_SHORT).show()
            }

        })


        viewModel.morePathData.observe(this, androidx.lifecycle.Observer { result ->
            val list = result.getOrNull()
            if (list != null && list.size >  0) {
                viewModel.listData.addAll(list)
                communityRecAdapter.notifyDataSetChanged()
            } else {
                Toast.makeText(context, "已经是最后一个啦", Toast.LENGTH_SHORT).show()
            }

        })

        //使用smartRefreshLayout会显示不全
        tv_more_calender.setOnClickListener {
            viewModel.loadMore(viewModel.listData[viewModel.listData.size - 1].nextUrl)
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
