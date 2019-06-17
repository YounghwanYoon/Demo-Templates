package com.ray.srt_smi_converter.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.viewpager.widget.ViewPager
import com.ray.srt_smi_converter.view.ListofFiles
import com.ray.srt_smi_converter.view.Select_Type_Fragment
import com.ray.srt_smi_converter.view.adapter.StatePagerAdapter
import java.io.File

class SharedViewModel: ViewModel(){
    private val TAG = this.javaClass.simpleName.toString()
    private val directory : MutableLiveData<String> = MutableLiveData()
    private lateinit var mViewPager: ViewPager
    private lateinit var mAdapter:StatePagerAdapter
    private lateinit var mReadData:ReadData

    //Return selected directory of file
    fun getDirectory(): LiveData<String> {
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

    fun getListOfCurrentDirectory():MutableList<String>{
        mReadData = ReadData()
        var file = mReadData.setStartingURLByManufacturer(android.os.Build.MANUFACTURER)
        var files =  mReadData.returnListInPath(file)
        return files
    }
    fun updatedList(newDir: File):MutableList<String>{
        var files = mReadData.returnListInPath(newDir)
        return files
    }
}