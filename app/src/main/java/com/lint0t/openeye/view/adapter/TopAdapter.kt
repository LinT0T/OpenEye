package com.lint0t.openeye.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.lint0t.openeye.R
import com.lint0t.openeye.bean.TopData
import com.lint0t.openeye.view.myview.MyRoundRectImageView
import kotlinx.android.synthetic.main.item_top.view.*
import java.text.SimpleDateFormat
import java.util.*

class TopAdapter(val context: Context, val mList: List<TopData>?) :
    RecyclerView.Adapter<TopAdapter.ViewHolder>() {
    private lateinit var onItemClickListener: OnItemClickListener

    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.onItemClickListener = listener
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val image: MyRoundRectImageView = view.img_top_item
        val time: TextView = view.tv_time_top_item
        val title: TextView = view.tv_title_top_item
        val author: TextView = view.tv_author_top_item
        val root: LinearLayout = view.root_top_item
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_top, parent, false)
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
            val formatter = SimpleDateFormat("mm:ss")
            formatter.timeZone = TimeZone.getTimeZone("GMT+00:00");
            val hms = formatter.format(mList[position].time * 1000);
            holder.time.text = hms
            val s = "#${mList[position].author}"
            holder.author.text = s
            holder.root.setOnClickListener {
                onItemClickListener.onItemClick(
                    holder.itemView,
                    position
                )
            }
        } else holder.title.text = "加载失败了"
    }

    interface OnItemClickListener {
        fun onItemClick(view: View, position: Int)
        fun onItemLongClick(view: View, position: Int)
    }
}