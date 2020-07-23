package com.lint0t.openeye.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.lint0t.openeye.R
import com.lint0t.openeye.bean.SpecialData
import kotlinx.android.synthetic.main.item_special.view.*

class SpecialAdapter(val context: Context, val mList: List<SpecialData>?) :
    RecyclerView.Adapter<SpecialAdapter.ViewHolder>() {
    private lateinit var onItemClickListener: OnItemClickListener
    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.onItemClickListener = listener
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val image = view.img_special_item
        val title = view.tv_title_special_item
        val root = view.root_special_item
        val background = view.img_background_special_item
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_special, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        if (mList == null)
            return 2
        return mList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (mList != null) {
            Glide.with(context).load(mList[position].url).into(holder.image)
            if ( mList[position].title == ""){
                holder.background.visibility = View.GONE
            }
            holder.title.text = mList[position].title
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