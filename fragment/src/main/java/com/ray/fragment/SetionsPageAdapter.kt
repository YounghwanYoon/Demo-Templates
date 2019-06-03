package com.ray.fragment

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter

class SectionsPageAdapter(fm: FragmentManager): FragmentPagerAdapter(fm){

    //List of Fragment
    private var mFragmentList:MutableList<Fragment> = mutableListOf()

    //return selected fragment
    override fun getItem(p0: Int): Fragment {
        return mFragmentList.get(p0)
    }

    //total size of fragment
    override fun getCount(): Int {
        return mFragmentList.size
    }

    //add Fragment method
    fun addFragment(fragment:Fragment){
        mFragmentList.add(fragment)
    }
}