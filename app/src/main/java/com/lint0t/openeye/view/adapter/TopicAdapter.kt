package com.lint0t.openeye.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.lint0t.openeye.R
import com.lint0t.openeye.bean.SpecialData
import com.lint0t.openeye.bean.TopicData
import com.lint0t.openeye.view.myview.MyRoundRectImageView
import kotlinx.android.synthetic.main.item_special.view.*
import kotlinx.android.synthetic.main.item_topic.view.*

class TopicAdapter(val context: Context, val mList: List<TopicData>?) :
    RecyclerView.Adapter<TopicAdapter.ViewHolder>() {
    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val image: MyRoundRectImageView = view.img_topic_item
        val title: TextView = view.tv_title_topic_item
        val content: TextView = view.tv_content_topic_item
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_topic, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        if (mList == null)
            return 1
        return mList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (mList != null) {
            Glide.with(context).load(mList[position].url).into(holder.image)
            holder.title.text = mList[position].title
            holder.content.text = mList[position].content
        } else holder.title.text = "加载失败了"
    }

}