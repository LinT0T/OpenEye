package com.lint0t.openeye.view.fragment

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentPagerAdapter
import com.ToxicBakery.viewpager.transforms.CubeOutTransformer
import com.google.android.material.tabs.TabLayout
import com.lint0t.openeye.R
import com.lint0t.openeye.view.activity.SearchActivity
import com.lint0t.openeye.view.adapter.MyHomeViewPagerAdapter
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var fragments = mutableListOf<Fragment>()
        val discoveryFragment = DiscoveryFragment()
        val recFragment = RecFragment()
        val newsFragment = DailyFragment()
        fragments.add(discoveryFragment)
        fragments.add(recFragment)
        fragments.add(newsFragment)

        val viewPagerAdapter = MyHomeViewPagerAdapter(
            childFragmentManager,
            FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT,
            fragments
        )
        vp_home.adapter = viewPagerAdapter
        vp_home.offscreenPageLimit = 3
        tb_home.setupWithViewPager(vp_home)
        tb_home.tabMode = TabLayout.MODE_FIXED

        tb_home.getTabAt(0)?.text = "发现"
        tb_home.getTabAt(1)?.text = "推荐"
        tb_home.getTabAt(2)?.text = "日报"
        vp_home.setCurrentItem(1, false)
        vp_home.setPageTransformer(true, CubeOutTransformer())
        img_search_home.setOnClickListener {
            val intent = Intent(activity, SearchActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
    }


}