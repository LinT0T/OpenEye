package com.lint0t.openeye.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.lint0t.openeye.R
import com.lint0t.openeye.bean.CommunityRecData
import com.lint0t.openeye.bean.RecData
import com.lint0t.openeye.view.myview.MyImageView
import com.lint0t.openeye.view.myview.MyRoundRectImageView
import kotlinx.android.synthetic.main.item_community_rec.view.*
import kotlinx.android.synthetic.main.item_top.view.*
import java.text.SimpleDateFormat
import java.util.*

class CommunityRecAdapter(val context: Context, val mList: MutableList<CommunityRecData>?) :
    RecyclerView.Adapter<CommunityRecAdapter.ViewHolder>() {
    private lateinit var onItemClickListener: OnItemClickListener

    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.onItemClickListener = listener
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val image: MyRoundRectImageView = view.img_community_rec_item
        val avatar: MyImageView = view.img_avatar_community_rec_item
        val description: TextView = view.tv_content_description_rec_item
        val name: TextView = view.tv_name_community_rec_item
        val good: TextView = view.tv_good_community_rec_item
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_community_rec, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        if (mList == null)
            return 1
        return mList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (mList != null) {
            Glide.with(context).load(mList[position].cover).into(holder.image)
            Glide.with(context).load(mList[position].avatar).into(holder.avatar)
            holder.name.text = mList[position].name
            holder.good.text = mList[position].good.toString()
            holder.description.text = mList[position].description
            holder.image.setOnClickListener {
                onItemClickListener.onItemClick(
                    holder.itemView,
                    position
                )
            }
        } else holder.description.text = "加载失败了"
    }

    fun addMore(moreList: MutableList<CommunityRecData>) {
      mList?.addAll(moreList)
    }
    fun refresh(list: MutableList<CommunityRecData>) {
        mList?.clear()
        mList?.addAll(list)
    }
    interface OnItemClickListener {
        fun onItemClick(view: View, position: Int)
        fun onItemLongClick(view: View, position: Int)
    }
}