package com.lint0t.openeye.view.activity

import android.app.Activity
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.lint0t.openeye.R
import com.lint0t.openeye.viewmodel.WelcomeViewModel
import kotlinx.android.synthetic.main.activity_welcome.*


class WelcomeActivity : AppCompatActivity() {
    private val viewModel by lazy { ViewModelProvider(this).get(WelcomeViewModel::class.java) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)
        hideBottomUIMenu(this)
        init()
    }

    private fun init() {
        img_welcome.alpha = 0f
        img_welcome.visibility = View.VISIBLE
        tv_welcome.alpha = 0f
        tv_welcome.visibility = View.VISIBLE
        viewModel.loadImage()
        viewModel.imagePathData.observe(this, Observer { result ->
            val imageData = result.getOrNull()
            if (imageData != null) {
                val path = "http://cn.bing.com${imageData.url}"
                Glide.with(this@WelcomeActivity)
                    .load(path)
                    .into(img_welcome)
                //   img_logo.animate().alpha(0f).duration=5000
                img_welcome.animate()
                    .alpha(1f).duration = 1000
                img_welcome.animate()
                    .scaleX(1.1f)
                    .scaleY(1.1f).duration = 4000
                tv_welcome.text = imageData.text
                tv_welcome.animate().alpha(1f).duration = 1000
            } else {
                Toast.makeText(this, "网络不太好哦，每日一图加载失败了>_<", Toast.LENGTH_SHORT).show()
            }
        })


        val handler = Handler()
        val runnable = Runnable {
            val intent = Intent()
            intent.setClass(
                this@WelcomeActivity,
                MainActivity::class.java
            )
            startActivity(intent)
            finish()
        }
        handler.postDelayed(runnable, 5000)
    }

    fun hideBottomUIMenu(a: Activity) {
        //隐藏虚拟按键，并且全屏
        if (Build.VERSION.SDK_INT > 11 && Build.VERSION.SDK_INT < 19) { // lower api
            val v = a.window.decorView
            v.systemUiVisibility = View.GONE
        } else if (Build.VERSION.SDK_INT >= 19) {
            //for new api versions.
            val decorView = a.window.decorView
            val uiOptions = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide nav bar
                    //                    | View.SYSTEM_UI_FLAG_FULLSCREEN // hide status bar
                    or View.SYSTEM_UI_FLAG_IMMERSIVE)
            decorView.systemUiVisibility = uiOptions
            a.window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION)
        }
    }


}
