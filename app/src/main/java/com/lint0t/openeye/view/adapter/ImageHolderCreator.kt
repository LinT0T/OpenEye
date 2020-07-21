package com.lint0t.openeye.view.adapter

import android.content.Context
import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.lint0t.openeye.view.myview.MyRoundRectImageView
import com.to.aboomy.banner.HolderCreator


//举个栗子
class ImageHolderCreator : HolderCreator {
    override fun createView(context: Context?, index: Int, o: Any?): View {
        val iv = MyRoundRectImageView(context)
        iv.scaleType = ImageView.ScaleType.FIT_XY
        Glide.with(iv).load(o).into(iv)
        //内部实现点击事件
        iv.setOnClickListener { }
        return iv
    }
}