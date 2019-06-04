package com.ray.fragment.view.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

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
    fun addFragment(fragment: Fragment){
        mFragmentList.add(fragment)
    }
}