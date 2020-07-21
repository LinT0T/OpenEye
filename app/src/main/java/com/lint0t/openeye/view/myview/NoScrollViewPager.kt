package com.lint0t.openeye.view.myview

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.viewpager.widget.ViewPager

class NoScrollViewPager:ViewPager {
    private val isScroll: Boolean = false

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)


    override fun onTouchEvent(event: MotionEvent): Boolean {
        return this.isScroll
    }

    override fun onInterceptTouchEvent(event: MotionEvent): Boolean {
        return this.isScroll
    }
}

