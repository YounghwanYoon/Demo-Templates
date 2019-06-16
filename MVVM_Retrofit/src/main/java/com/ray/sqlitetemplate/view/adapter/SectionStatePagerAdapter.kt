package com.ray.sqlitetemplate.view.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class SectionStatePagerAdapter(fm: androidx.fragment.app.FragmentManager?) : androidx.fragment.app.FragmentPagerAdapter(fm) {

    private val mFragmentList: MutableList<androidx.fragment.app.Fragment> = mutableListOf()
    private val mFragmentTitleList: MutableList<String> = mutableListOf()

    //Adding Fragment List
    fun addFragment(fragment: androidx.fragment.app.Fragment, title:String){
        mFragmentList.add(fragment)
        mFragmentTitleList.add(title)
    }
    override fun getItem(p0: Int): androidx.fragment.app.Fragment {
            return mFragmentList.get(p0)
    }

    override fun getCount(): Int {
        return mFragmentList.size
    }
}