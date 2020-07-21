package com.lint0t.openeye.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.lint0t.openeye.R
import com.lint0t.openeye.bean.RecData
import com.lint0t.openeye.view.myview.MyImageView
import com.lint0t.openeye.view.myview.MyRoundRectImageView
import kotlinx.android.synthetic.main.item_small_video_rec.view.*
import kotlinx.android.synthetic.main.item_text_rec.view.*
import kotlinx.android.synthetic.main.item_topic.view.*
import kotlinx.android.synthetic.main.item_video_rec.view.*
import me.samlss.broccoli.Broccoli
import java.text.SimpleDateFormat
import java.util.*

class RecAdapter(val context: Context, private val mList: MutableList<RecData>?) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    companion object {
        const val TYPE_SQUARE_CARD_COLLECTION = 1
        const val TYPE_TEXT = 2
        const val TYPE_FOLLOW_CARD = 3
        const val TYPE_VIDEO_SMALL_CARD = 4
        const val TYPE_BRIEF_CARD = 5
    }

    private lateinit var onItemClickListener: OnItemClickListener
    var isScrolling = false
    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.onItemClickListener = listener
    }

    inner class TextViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val text: TextView = view.tv_text_rec
    }

    inner class SquareCardViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val title: TextView = view.tv_title_video_rec_item
        val text: TextView = view.tv_video_rec_item
        val root: LinearLayout = view.root_video_rec_item
        val author: TextView = view.tv_author_video_rec_item
        val image: MyRoundRectImageView = view.img_video_rec_item
        val avatar: MyImageView = view.img_avatar_video_rec_item
        val time: TextView = view.tv_time_video_rec_item
    }

    inner class FollowCardViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val title: TextView = view.tv_title_small_rec_item
        val author: TextView = view.tv_author_small_rec_item
        val root: LinearLayout = view.root_small_rec_item
        val time: TextView = view.tv_time_small_rec_item
        val image: MyRoundRectImageView = view.img_small_rec_item
    }

    inner class BriefCardViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val image: MyRoundRectImageView = view.img_topic_item
        val title: TextView = view.tv_title_topic_item
        val content: TextView = view.tv_content_topic_item
    }

    override fun getItemViewType(position: Int): Int {
        if (mList?.get(position)?.type == "squareCardCollection") {
            return TYPE_SQUARE_CARD_COLLECTION
        }
        if (mList?.get(position)?.type == "textCard") {
            return TYPE_TEXT
        }
        if (mList?.get(position)?.type == "followCard") {
            return TYPE_FOLLOW_CARD
        }
        if (mList?.get(position)?.type == "videoSmallCard") {
            return TYPE_VIDEO_SMALL_CARD
        }
        if (mList?.get(position)?.type == "briefCard") {
            return TYPE_BRIEF_CARD
        }
        return 0
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            TYPE_TEXT -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_text_rec, parent, false)
                TextViewHolder(view)
            }
            TYPE_SQUARE_CARD_COLLECTION -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_video_rec, parent, false)
                SquareCardViewHolder(view)
            }
            TYPE_VIDEO_SMALL_CARD -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_small_video_rec, parent, false)
                FollowCardViewHolder(view)
            }
            TYPE_FOLLOW_CARD -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_small_video_rec, parent, false)
                FollowCardViewHolder(view)
            }
            TYPE_BRIEF_CARD -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_topic, parent, false)
                BriefCardViewHolder(view)
            }
            else -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_small_video_rec, parent, false)
                FollowCardViewHolder(view)
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
        val broccoli = Broccoli()
        if (!isScrolling) {
            broccoli.clearAllPlaceholders()
            if (mList != null) {
                when (viewType) {
                    TYPE_TEXT -> {
                        val textHolder = holder as TextViewHolder
                        textHolder.text.text = mList[position].text
                    }
                    TYPE_SQUARE_CARD_COLLECTION -> {
                        val squareCardViewHolder = holder as SquareCardViewHolder
                        if (position == 0) {
                            squareCardViewHolder.text.text = mList[position].text
                            squareCardViewHolder.text.visibility = View.VISIBLE
                        } else {
                            squareCardViewHolder.text.visibility = View.GONE
                        }
                        squareCardViewHolder.author.text = mList[position].author
                        squareCardViewHolder.title.text = mList[position].title
                        val formatter = SimpleDateFormat("mm:ss")
                        formatter.timeZone = TimeZone.getTimeZone("GMT+00:00")
                        val hms = formatter.format(mList[position].time * 1000)
                        squareCardViewHolder.time.text = hms
                        Glide.with(context).load(mList[position].avatar)
                            .into(squareCardViewHolder.avatar)
                        Glide.with(context).load(mList[position].url)
                            .into(squareCardViewHolder.image)
                        holder.root.setOnClickListener {
                            onItemClickListener.onItemClick(
                                holder.itemView,
                                position
                            )
                        }
                    }
                    TYPE_FOLLOW_CARD -> {
                        val followCardViewHolder = holder as FollowCardViewHolder
                        followCardViewHolder.author.text = mList[position].author
                        followCardViewHolder.title.text = mList[position].title
                        val formatter = SimpleDateFormat("mm:ss")
                        formatter.timeZone = TimeZone.getTimeZone("GMT+00:00")
                        val hms = formatter.format(mList[position].time * 1000)
                        followCardViewHolder.time.text = hms
                        Glide.with(context).load(mList[position].url)
                            .into(followCardViewHolder.image)
                        holder.root.setOnClickListener {
                            onItemClickListener.onItemClick(
                                holder.itemView,
                                position
                            )
                        }
                    }
                    TYPE_VIDEO_SMALL_CARD -> {
                        val followCardViewHolder = holder as FollowCardViewHolder
                        followCardViewHolder.author.text = mList[position].author
                        followCardViewHolder.title.text = mList[position].title
                        val formatter = SimpleDateFormat("mm:ss")
                        formatter.timeZone = TimeZone.getTimeZone("GMT+00:00")
                        val hms = formatter.format(mList[position].time * 1000)
                        followCardViewHolder.time.text = hms
                        Glide.with(context).load(mList[position].url)
                            .into(followCardViewHolder.image)
                        holder.root.setOnClickListener {
                            onItemClickListener.onItemClick(
                                holder.itemView,
                                position
                            )
                        }
                    }
                    TYPE_BRIEF_CARD -> {
                        val briefCardViewHolder = holder as BriefCardViewHolder
                        briefCardViewHolder.content.text = mList[position].description
                        briefCardViewHolder.title.text = mList[position].title
                        Glide.with(context).load(mList[position].avatar)
                            .into(briefCardViewHolder.image)
                    }
                }
            }
        } else { //滑动卡顿优化，设置占位图
            if (mList != null) {
                when (viewType) {
                    TYPE_TEXT -> {
                        val textHolder = holder as TextViewHolder
                        textHolder.text.text = ""
                    }
                    TYPE_SQUARE_CARD_COLLECTION -> {
                        val squareCardViewHolder = holder as SquareCardViewHolder
                        if (position == 0) {
                            squareCardViewHolder.text.text = ""
                            squareCardViewHolder.text.visibility = View.VISIBLE
                        } else {
                            squareCardViewHolder.text.visibility = View.GONE
                        }
                        squareCardViewHolder.author.text = ""
                        squareCardViewHolder.title.text = ""
                        squareCardViewHolder.time.text = "00:00"

                        broccoli.addPlaceholders(
                            squareCardViewHolder.image
                        )
                        broccoli.show()
                        holder.root.setOnClickListener {
                            onItemClickListener.onItemClick(
                                holder.itemView,
                                position
                            )
                        }
                    }
                    TYPE_FOLLOW_CARD -> {
                        val followCardViewHolder = holder as FollowCardViewHolder
                        followCardViewHolder.author.text = ""
                        followCardViewHolder.title.text = ""
                        followCardViewHolder.time.text = "00:00"
                        broccoli.addPlaceholders(followCardViewHolder.image)
                        broccoli.show()
                        holder.root.setOnClickListener {
                            onItemClickListener.onItemClick(
                                holder.itemView,
                                position
                            )
                        }
                    }
                    TYPE_VIDEO_SMALL_CARD -> {
                        val followCardViewHolder = holder as FollowCardViewHolder
                        followCardViewHolder.author.text = ""
                        followCardViewHolder.title.text = ""
                        followCardViewHolder.time.text = "00:00"
                        broccoli.addPlaceholders(followCardViewHolder.image)
                        broccoli.show()
                        holder.root.setOnClickListener {
                            onItemClickListener.onItemClick(
                                holder.itemView,
                                position
                            )
                        }
                    }
                    TYPE_BRIEF_CARD -> {
                        val briefCardViewHolder = holder as BriefCardViewHolder
                        briefCardViewHolder.content.text = ""
                        briefCardViewHolder.title.text = ""
                        broccoli.addPlaceholders(briefCardViewHolder.image)
                        broccoli.show()
                        Glide.with(context).load(mList[position].avatar)
                            .into(briefCardViewHolder.image)
                    }
                }
            }
        }
    }

    fun addMore(moreList: MutableList<RecData>) {
        mList?.addAll(moreList)
    }

    fun refreshData(newList: MutableList<RecData>) {
        mList?.clear()
        mList?.addAll(newList)
    }

    interface OnItemClickListener {
        fun onItemClick(view: View, position: Int)
        fun onItemLongClick(view: View, position: Int)
    }
}