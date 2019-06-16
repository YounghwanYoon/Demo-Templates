package com.ray.srt_smi_converter.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.viewpager.widget.ViewPager
import com.ray.srt_smi_converter.view.ListofFiles
import com.ray.srt_smi_converter.view.Select_Type_Fragment
import com.ray.srt_smi_converter.view.adapter.StatePagerAdapter

class SharedViewModel: ViewModel(){
    private val TAG = this.javaClass.simpleName.toString()
    private val directory : MutableLiveData<String> = MutableLiveData()
    private lateinit var mViewPager:ViewPager
    private lateinit var mAdapter:StatePagerAdapter

    //Return selected directory of file
    fun getDirectory():LiveData<String>{
        return directory
    }

    fun setDirectory(selected_dir:String){
        directory.value = selected_dir
    }

    fun changeFragment(currentItem: Int){
        mViewPager.currentItem = currentItem
    }
    fun setupViewPager(viewPager: ViewPager, adapter: StatePagerAdapter){
        mViewPager= viewPager
        mAdapter = adapter

        mAdapter.addFragment(Select_Type_Fragment())
        mAdapter.addFragment(ListofFiles())
        mViewPager.adapter = adapter
    }

}