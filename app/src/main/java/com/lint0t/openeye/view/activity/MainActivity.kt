package com.lint0t.openeye.view.activity


import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager.OnPageChangeListener
import com.gyf.immersionbar.ktx.immersionBar
import com.lint0t.openeye.R
import com.lint0t.openeye.view.adapter.MyViewPagerAdapter
import com.lint0t.openeye.view.fragment.CommunityFragment
import com.lint0t.openeye.view.fragment.HomeFragment
import com.lint0t.openeye.view.fragment.MeFragment
import com.lint0t.openeye.view.fragment.MessageFragment
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    private var fragments: MutableList<Fragment> = mutableListOf()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initBar()
        initView()
    }

    private fun initView() {
        val homeFragment = HomeFragment()
        val communityFragment = CommunityFragment()
        val messageFragment = MessageFragment()
        val meFragment = MeFragment()
        fragments.add(homeFragment)
        fragments.add(communityFragment)
        fragments.add(messageFragment)
        fragments.add(meFragment)
        val viewPagerAdapter = MyViewPagerAdapter(
            supportFragmentManager,
            FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT,
            fragments
        )

        vp_main.addOnPageChangeListener(object : OnPageChangeListener {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
            }

            override fun onPageSelected(position: Int) {
                tb_main.menu.getItem(position).isChecked = true
            }

            override fun onPageScrollStateChanged(state: Int) {}
        })
        vp_main.adapter = viewPagerAdapter
        vp_main.offscreenPageLimit = 4
        tb_main.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.item_home -> {
                    vp_main.currentItem = 0
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.item_community -> {
                    vp_main.currentItem = 1
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.item_message -> {
                    vp_main.currentItem = 2
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.item_me -> {
                    vp_main.currentItem = 3
                    return@setOnNavigationItemSelectedListener true
                }
            }

            false
        }
    }

    private fun initBar() {
        //   hideBottomUIMenu(this)
        immersionBar {
            transparentStatusBar()
            transparentNavigationBar()
            fullScreen(true)
            statusBarDarkFont(true)
            navigationBarDarkIcon(true)
            statusBarColor("#FBFBFB")
            navigationBarColor("#FBFBFB")
        }
        val params: WindowManager.LayoutParams = window.attributes
        params.systemUiVisibility =
            View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or View.SYSTEM_UI_FLAG_IMMERSIVE
        window.attributes = params
    }


    override fun onRestart() {
        super.onRestart()
        initBar()
    }

}

