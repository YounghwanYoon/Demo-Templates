package com.ray.sqlitetemplate.view.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter

class SectionStatePagerAdapter(fm: FragmentManager?) : FragmentPagerAdapter(fm) {

    private val mFragmentList: MutableList<Fragment> = mutableListOf()
    private val mFragmentTitleList: MutableList<String> = mutableListOf()

    //Adding Fragment List
    fun addFragment(fragment:Fragment, title:String){
        mFragmentList.add(fragment)
        mFragmentTitleList.add(title)
    }
    override fun getItem(p0: Int): Fragment {
            return mFragmentList.get(p0)
    }

    override fun getCount(): Int {
        return mFragmentList.size
    }
}