package com.lint0t.openeye.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.lint0t.openeye.R
import com.lint0t.openeye.bean.ColumnData
import com.lint0t.openeye.bean.ReplyData
import com.lint0t.openeye.bean.SpecialData
import com.lint0t.openeye.view.myview.MyImageView
import kotlinx.android.synthetic.main.item_column.view.*
import kotlinx.android.synthetic.main.item_reply.view.*
import kotlinx.android.synthetic.main.item_special.view.*
import kotlinx.android.synthetic.main.item_text.view.*
import me.samlss.broccoli.Broccoli
import java.text.SimpleDateFormat
import java.util.*

class ReplyAdapter(val context: Context, private val mList: List<ReplyData>?) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    companion object {
        const val TYPE_TEXT = 1
        const val TYPE_REPLY = 2
    }

    inner class TextViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val text: TextView = view.tv_text_reply
    }

    inner class ReplyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val name: TextView = view.tv_name_reply_item
        val good: TextView = view.tv_good_reply_item
        val image: MyImageView = view.img_reply_item
        val content: TextView = view.tv_content_reply_item
        val time: TextView = view.tv_time_reply_item
    }

    override fun getItemViewType(position: Int): Int {
        if (mList?.get(position)?.type == "leftAlignTextHeader") {
            return TYPE_TEXT
        }
        if (mList?.get(position)?.type == "reply") {
            return TYPE_REPLY
        }
        return 0
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            TYPE_TEXT -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_text, parent, false)
                TextViewHolder(view)
            }
            TYPE_REPLY -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_reply, parent, false)
                ReplyViewHolder(view)
            }
            else -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_reply, parent, false)
                ReplyViewHolder(view)
            }
        }

    }

    override fun getItemCount(): Int {
        if (mList == null)
            return 0
        return mList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val viewType = getItemViewType(position)
        if (mList != null) {
            when (viewType) {
                TYPE_TEXT -> {
                    val textHolder = holder as TextViewHolder
                    textHolder.text.text = mList[position].text
                }
                TYPE_REPLY -> {
                    val replyHolder = holder as ReplyViewHolder
                    replyHolder.name.text = mList[position].nickName
                    replyHolder.content.text = mList[position].message
                    val formatter = SimpleDateFormat("yyyy/MM/dd HH:mm:ss")
                    formatter.timeZone = TimeZone.getTimeZone("GMT+00:00")
                    val hms = formatter.format(mList[position].time);
                    replyHolder.time.text = hms
                    replyHolder.good.text = mList[position].likeCount.toString()
                    Glide.with(context).load(mList[position].avatar).into(replyHolder.image)
                }
            }
        }
    }


}