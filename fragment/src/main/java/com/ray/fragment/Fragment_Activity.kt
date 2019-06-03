package com.ray.fragment

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.view.ViewPager
import android.view.MenuItem

class Fragment_Activity : AppCompatActivity() {

    private val navListener = object:BottomNavigationView.OnNavigationItemSelectedListener{
        override fun onNavigationItemSelected(p0: MenuItem): Boolean {

            when(p0.itemId){
                R.id.nav_home ->  mViewPager.setCurrentItem(0) //Java way of calling setCurrentItem()
                R.id.nav_favorites -> mViewPager.currentItem = 1 // Kotlin way of set current item
                R.id.nav_people -> mViewPager.currentItem=2
            }
            return true
        }
    }
    private val pageChangeListener = object:ViewPager.OnPageChangeListener{
        override fun onPageScrollStateChanged(p0: Int) {
        }

        override fun onPageScrolled(p0: Int, p1: Float, p2: Int) {
        }

        override fun onPageSelected(p0: Int) {
            bottomNav.menu.getItem(p0).isChecked = false
        }
    }

    lateinit var mAdapter:SectionsPageAdapter
    lateinit var mViewPager: ViewPager
    lateinit var bottomNav:BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fragment)

        bottomNav= findViewById(R.id.bottom_navigation)
        bottomNav.setOnNavigationItemSelectedListener(navListener)

        //instantiate SectionPageAdapter
        mAdapter = SectionsPageAdapter(getSupportFragmentManager())
        //instantiate View Pager from activity_fragment
        mViewPager = findViewById(R.id.fragment_container)
        //add Page Change Listener to update bottom navigation icon whenever user switch fragment with swiping screen
        mViewPager.addOnPageChangeListener(pageChangeListener)

        setupViewPager(mViewPager, mAdapter)
    }


    fun setupViewPager(viewPager: ViewPager, adapter:SectionsPageAdapter){
        adapter.addFragment(Home_Fragment())
        adapter.addFragment(Favorites_Fragment())
        adapter.addFragment(People_Fragment())
        viewPager.adapter = adapter
    }
}
