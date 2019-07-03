package com.ray.srt_smi_converter.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.viewpager.widget.ViewPager
import com.ray.srt_smi_converter.view.ListofFiles
import com.ray.srt_smi_converter.view.Select_Type_Fragment
import com.ray.srt_smi_converter.view.adapter.StatePagerAdapter
import com.ray.srt_smi_converter.viewmodel.SharedViewModel.viewpager.mViewPager
import java.io.File

class SharedViewModel: ViewModel(){
    private val TAG = this.javaClass.simpleName.toString()
    private val directory : MutableLiveData<String> = MutableLiveData()
    private lateinit var mFileHandler:FileHandler
    private lateinit var mAdapter:StatePagerAdapter

    object viewpager{
        internal lateinit var mViewPager: ViewPager
    }

    //Return selected directory of file
    fun getDirectory(): LiveData<String> {
        return directory
    }

    fun setDirectory(selected_dir:String){
        directory.value = selected_dir
    }

    fun changeFragment(currentItem: Int){
        Log.d(TAG, "inside SharedViewModel's changeFragment method")
        mViewPager.currentItem = currentItem
    }

    fun setupViewPager(viewPager: ViewPager, adapter: StatePagerAdapter){

        Log.d(TAG, "inside SharedViewModel's setupViewPager method")
        mViewPager= viewPager
        mAdapter = adapter
        mAdapter.addFragment(Select_Type_Fragment())
        mAdapter.addFragment(ListofFiles())
        mViewPager.adapter = adapter
    }

    fun getListOfCurrentDirectory(): MutableList<File> {
        Log.d(TAG, "inside SharedViewModel's getListOfCurrentDirectory method")

        mFileHandler = FileHandler()
        val file = mFileHandler.setStartingURLByManufacturer(android.os.Build.MANUFACTURER)
        val files =  mFileHandler.returnListInPath(file)
        return files
    }

    fun updatedList(newDir: File):MutableList<File>{
        Log.d(TAG, "inside SharedViewModel's updatedList ()")
        Log.d(TAG, "inside SharedViewModel's newDir is ${newDir.path}")

        val files = mFileHandler.returnListInPath(newDir)
        return files
    }
}