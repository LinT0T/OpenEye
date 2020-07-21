package com.lint0t.openeye.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.lint0t.openeye.R
import com.lint0t.openeye.bean.CommunityFollowData
import com.lint0t.openeye.view.myview.MyImageView
import com.lint0t.openeye.view.myview.MyRoundRectImageView
import kotlinx.android.synthetic.main.item_auto_play_follow_card.view.*
import me.samlss.broccoli.Broccoli
import java.text.SimpleDateFormat
import java.util.*

class AutoPlayAdapter(val context: Context, val mList: MutableList<CommunityFollowData>?) :
    RecyclerView.Adapter<AutoPlayAdapter.ViewHolder>() {

    private lateinit var onItemClickListener: OnItemClickListener
    var isScrolling = false
    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.onItemClickListener = listener
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val cover: MyRoundRectImageView = view.img_cover_auto_play_item
        val title: TextView = view.tv_title_auto_play_item
        val name: TextView = view.tv_name_auto_play_item
        val content: TextView = view.tv_content_auto_play_item
        val time: TextView = view.tv_time_auto_play_item
        val duration: TextView = view.tv_duration_auto_play_item
        val avatar: MyImageView = view.img_avatar_auto_play_item
        val good: TextView = view.tv_good_auto_play_item
        val message: TextView = view.tv_message_auto_play_item
        val share: TextView = view.tv_share_auto_play_item
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_auto_play_follow_card, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        if (mList == null)
            return 1
        return mList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val broccoli = Broccoli()
        if (!isScrolling) {
            broccoli.clearAllPlaceholders()
            if (mList != null) {
                Glide.with(context).load(mList[position].url).into(holder.cover)
                Glide.with(context).load(mList[position].avatar).into(holder.avatar)
                holder.title.text = mList[position].title
                val formatter = SimpleDateFormat("mm:ss")
                formatter.timeZone = TimeZone.getTimeZone("GMT+00:00")
                val hms = formatter.format(mList[position].time * 1000)
                holder.duration.text = hms
                val now = System.currentTimeMillis()
                val form = SimpleDateFormat("yyyy/MM/dd HH:mm:ss")
                form.timeZone = TimeZone.getTimeZone("GMT+08:00")
                val ms = form.format(mList[position].createTime)
                val day: Long =
                    (now / (24 * 60 * 60 * 1000) - (mList[position].createTime + 8 * 60 * 60 * 1000) / (24 * 60 * 60 * 1000))
                if (day < 1) {

                    val s = "${ms.substring(ms.length - 8, ms.length - 3)} 发布："
                    holder.time.text = s
                } else {


                    val s = "${ms.substring(IntRange(0, 10))} 发布："
                    holder.time.text = s
                }
                holder.content.text = mList[position].description
                holder.name.text = mList[position].author
                holder.good.text = mList[position].good.toString()
                holder.message.text = mList[position].message.toString()
                holder.share.text = mList[position].shareCount.toString()
                holder.cover.setOnClickListener {
                    onItemClickListener.onItemClick(
                        holder.itemView,
                        position
                    )
                }
            } else holder.title.text = "加载失败了"
        } else {
            if (mList != null) {
                broccoli.addPlaceholders(holder.cover)
                broccoli.show()
                holder.title.text = "停止滑动以加载"
                holder.content.text = "内容"
                holder.name.text = "作者"
                holder.time.text = "时间"
                holder.good.text = "0"
                holder.message.text = "0"
                holder.share.text = "0"
            } else holder.title.text = "加载失败了"
        }
    }


    interface OnItemClickListener {
        fun onItemClick(view: View, position: Int)
        fun onItemLongClick(view: View, position: Int)
    }

    fun addMore(moreList: MutableList<CommunityFollowData>) {
        mList?.addAll(moreList)
    }
}