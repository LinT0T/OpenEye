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
import com.lint0t.openeye.bean.MessageData
import kotlinx.android.synthetic.main.item_auto_play_follow_card.view.*
import kotlinx.android.synthetic.main.item_message.view.*
import me.samlss.broccoli.Broccoli
import java.text.SimpleDateFormat
import java.util.*

class MessageAdapter(val context: Context, val mList: MutableList<MessageData>?) :
    RecyclerView.Adapter<MessageAdapter.ViewHolder>() {

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val title: TextView = view.tv_title_message_item
        val content: TextView = view.tv_content_message_item
        val time: TextView = view.tv_time_message_item

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_message, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        if (mList == null)
            return 1
        return mList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (mList != null) {
            holder.title.text = mList[position].title
            val form = SimpleDateFormat("yyyy/MM/dd HH:mm:ss")
            form.timeZone = TimeZone.getTimeZone("GMT+08:00")
            val ms = form.format(mList[position].date)
            holder.time.text = ms
            holder.content.text = mList[position].content

        } else holder.title.text = "加载失败了"

    }

    fun addMore(moreList: MutableList<MessageData>) {
        mList?.addAll(moreList)
    }
}