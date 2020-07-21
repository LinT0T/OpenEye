package com.lint0t.openeye.view.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentPagerAdapter
import com.ToxicBakery.viewpager.transforms.TabletTransformer
import com.google.android.material.tabs.TabLayout
import com.lint0t.openeye.R
import com.lint0t.openeye.view.adapter.MyHomeViewPagerAdapter
import kotlinx.android.synthetic.main.fragment_community.*

class CommunityFragment : Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_community, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var fragments = mutableListOf<Fragment>()
        val communityRecFragment = CommunityRecFragment()
        val communityFollowFragment = CommunityFollowFragment()
        fragments.add(communityRecFragment)
        fragments.add(communityFollowFragment)

        val viewPagerAdapter = MyHomeViewPagerAdapter(
            childFragmentManager,
            FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT,
            fragments
        )
        vp_community.adapter = viewPagerAdapter
        vp_community.offscreenPageLimit = 2
        tb_community.setupWithViewPager(vp_community)
        tb_community.tabMode = TabLayout.MODE_FIXED

        tb_community.getTabAt(0)?.text = "推荐"
        tb_community.getTabAt(1)?.text = "关注"
        vp_community.setPageTransformer(true, TabletTransformer())
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
    }
}