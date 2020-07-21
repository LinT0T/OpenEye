package com.lint0t.openeye.view.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.lint0t.openeye.R
import com.lint0t.openeye.viewmodel.MeViewModel
import kotlinx.android.synthetic.main.fragment_me.*

class MeFragment : Fragment() {
    private val viewModel by lazy { ViewModelProvider(this).get(MeViewModel::class.java) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_me, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        tv_thinks.setOnClickListener {
            if (!viewModel.isHind) {
                img_me_logo.animate().alpha(0f).duration = 1000
                tv_thinks.animate().alpha(0f).duration = 1000
                viewModel.isHind = true
            } else {
                img_me_logo.animate().alpha(1f).duration = 1000
                tv_thinks.animate().alpha(1f).duration = 1000
                viewModel.isHind = false
            }
        }
        viewModel.loadImage()
        viewModel.imagePathData.observe(viewLifecycleOwner, Observer { result ->
            val imageData = result.getOrNull()
            if (imageData != null) {
                val path = "http://cn.bing.com${imageData.url}"
                Glide.with(context)
                    .load(path)
                    .into(img_me)
                //   img_logo.animate().alpha(0f).duration=5000
                img_me.animate()
                    .alpha(1f).duration = 1000
                tv_thinks.text = imageData.text
                tv_thinks.animate().alpha(1f).duration = 1000
            }

        })

    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
    }
}