package com.ray.srt_smi_converter.view.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter

class StatePagerAdapter(fm: FragmentManager): FragmentStatePagerAdapter(fm){

     private var fragmentList = mutableListOf<Fragment>()

    override fun getItem(position: Int): Fragment {
        return fragmentList[position]
    }

    override fun getCount(): Int {
        return fragmentList.size
    }

    fun addFragment(fg:Fragment){
        fragmentList.add(fg)
    }
}