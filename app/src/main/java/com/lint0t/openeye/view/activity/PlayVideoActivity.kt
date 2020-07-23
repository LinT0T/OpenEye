package com.lint0t.openeye.view.activity

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.ImageView
import android.widget.RemoteViews
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.SimpleTarget
import com.gyf.immersionbar.ktx.immersionBar
import com.lint0t.openeye.R
import com.lint0t.openeye.bean.TopData
import com.lint0t.openeye.view.adapter.RelatedAdapter
import com.lint0t.openeye.view.adapter.ReplyAdapter
import com.lint0t.openeye.viewmodel.PlayVideoViewModel
import com.shuyu.gsyvideoplayer.GSYBaseActivityDetail
import com.shuyu.gsyvideoplayer.builder.GSYVideoOptionBuilder
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer
import kotlinx.android.synthetic.main.activity_play_video.*


class PlayVideoActivity : GSYBaseActivityDetail<StandardGSYVideoPlayer>() {
    private val viewModel by lazy { ViewModelProvider(this).get(PlayVideoViewModel::class.java) }
    private var mTitle: String = ""
    private var url: String = ""
    private var author: String = ""
    private var content: String = ""
    private var blurred: String = ""
    private var cover: String = ""
    private var id: String = ""
    private val receiver = ServiceReceiver()//----注册广播
    private lateinit var manager: NotificationManager
    private lateinit var detailPlayer: StandardGSYVideoPlayer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_play_video)
        initBar()
        initData()
        loadTop()
        initVideoBuilderMode()
        initService()
    }

    private fun initData() {
        val bundle = intent.extras
        val data: TopData = bundle?.getSerializable("data") as TopData
        url = data.playUrl
        mTitle = data.title
        author = "#${data.author}"
        content = data.description
        blurred = data.blurred
        cover = data.url
        id = data.id
        Glide.with(this)
            .asBitmap()
            .load(blurred)
            .into(object : SimpleTarget<Bitmap>() {

                override fun onResourceReady(
                    resource: Bitmap?,
                    transition: com.bumptech.glide.request.transition.Transition<in Bitmap>?
                ) {
                    val drawable = BitmapDrawable(resources, resource)
                    root_play_video.background = drawable
                }

            })
        tv_title_play_video.animateText(mTitle)
        tv_author_play_video.animateText(author)
        tv_content_play_video.animateText(content)
        tv_content_play_video.visibility = View.VISIBLE

        detailPlayer = findViewById(R.id.video_player)
        detailPlayer.titleTextView.visibility = View.GONE
        detailPlayer.backButton.visibility = View.GONE
    }

    private fun initBar() {
        immersionBar {
            transparentStatusBar()
            transparentNavigationBar()
            fullScreen(true)
        }
        val params: WindowManager.LayoutParams = window.attributes
        params.systemUiVisibility =
            View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or View.SYSTEM_UI_FLAG_IMMERSIVE
        window.attributes = params
    }


    override fun clickForFullScreen() {

    }

    override fun getDetailOrientationRotateAuto(): Boolean {
        return true
    }

    override fun getGSYVideoPlayer(): StandardGSYVideoPlayer {
        return detailPlayer
    }

    override fun getGSYVideoOptionBuilder(): GSYVideoOptionBuilder {
        val imageView = ImageView(this)
        loadCover(imageView, cover);
        return GSYVideoOptionBuilder()
            .setThumbImageView(imageView)
            .setUrl(url)
            .setCacheWithPlay(true)
            .setVideoTitle(mTitle)
            .setIsTouchWiget(true) //.setAutoFullWithSize(true)
            .setRotateViewAuto(false)
            .setLockLand(false)
            .setShowFullAnimation(false) //打开动画
            .setNeedLockFull(true)
            .setSeekRatio(1f)
    }

    private fun loadCover(
        imageView: ImageView,
        url: String
    ) {
        imageView.scaleType = ImageView.ScaleType.CENTER_CROP
        imageView.setImageResource(R.mipmap.open_eye)
        Glide.with(this.applicationContext)
            .setDefaultRequestOptions(
                RequestOptions()
                    .frame(3000000)
                    .centerCrop()
                    .error(R.mipmap.place_holder)
                    .placeholder(R.mipmap.place_holder)
            )
            .load(url)
            .into(imageView)
    }

    private fun loadTop() {
        val topLayoutManager = LinearLayoutManager(this)
        rv_related_play_video.layoutManager = topLayoutManager
        viewModel.loadRelated(id)
        viewModel.relatedPathData.observe(this, Observer { result ->
            val list = result.getOrNull()
            if (list !== null) {
                val topAdapter = RelatedAdapter(this, list)
                rv_related_play_video.adapter = topAdapter
                topAdapter.setOnItemClickListener(object : RelatedAdapter.OnItemClickListener {
                    override fun onItemClick(view: View, position: Int) {
                        val intent = Intent(this@PlayVideoActivity, PlayVideoActivity::class.java)
                        val bundle = Bundle()
                        bundle.putSerializable("data", list[position])
                        intent.putExtras(bundle)
                        startActivity(intent)
                    }

                    override fun onItemLongClick(view: View, position: Int) {

                    }
                })
            } else {
                Toast.makeText(this, "网络不太好哦，推荐加载失败了>_<", Toast.LENGTH_SHORT).show()
            }
        })
        val replyLayoutManager = LinearLayoutManager(this)
        rv_reply_play_video.layoutManager = replyLayoutManager
        viewModel.replyPathData.observe(this, Observer { result ->
            val list = result.getOrNull()
            if (list !== null) {
                val replyAdapter = ReplyAdapter(this, list)
                rv_reply_play_video.adapter = replyAdapter
            } else {
                Toast.makeText(this, "网络不太好哦，评论加载失败了>_<", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun initService() {
        manager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager


        val intentFilter = IntentFilter()


        registerReceiver(receiver, intentFilter)
        showCustomView()
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(receiver)
    }

    private fun showCustomView() {
        try {


            val channelId = "ChannelId"
            val channelName = "默认通知"
            //需添加的代码
            //需添加的代码
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                manager.createNotificationChannel(
                    NotificationChannel(
                        channelId,
                        channelName,
                        NotificationManager.IMPORTANCE_HIGH
                    )
                )
            }

            val remoteViews = RemoteViews(
                packageName,
                R.layout.notification
            )
            if (mTitle != "") {
                remoteViews.setTextViewText(R.id.widget_title, mTitle) //设置textview
                remoteViews.setTextViewText(R.id.widget_artist, author)

            } else {
                remoteViews.setTextViewText(R.id.widget_title, "还没有数据哦") //设置textview
                remoteViews.setTextViewText(R.id.widget_artist, "")
            }
            val builder =
                NotificationCompat.Builder(this, channelId)
            builder.setContent(remoteViews).setSmallIcon(R.mipmap.open_eye_icon)
                .setOngoing(true).setCustomBigContentView(remoteViews)
                .setTicker("music is playing")
            manager.notify(1, builder.build())
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    class ServiceReceiver : BroadcastReceiver() {
        override fun onReceive(
            context: Context,
            intent: Intent
        ) {
        }

    }
}
