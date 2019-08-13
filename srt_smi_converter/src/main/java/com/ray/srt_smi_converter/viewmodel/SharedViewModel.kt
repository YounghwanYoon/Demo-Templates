package com.ray.srt_smi_converter.viewmodel

import android.os.AsyncTask
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.viewpager.widget.ViewPager
import com.ray.srt_smi_converter.view.ListofFiles
import com.ray.srt_smi_converter.view.Option_Fragment
import com.ray.srt_smi_converter.view.adapter.StatePagerAdapter
import com.ray.srt_smi_converter.viewmodel.SharedViewModel.viewpager.mViewPager
import java.io.File

class SharedViewModel: ViewModel(){
    private val TAG = this.javaClass.simpleName.toString()
    private val mFileList: MutableLiveData<List<File>> = MutableLiveData()
    private lateinit var mFileHandler:FileHandler
    private lateinit var mAdapter:StatePagerAdapter

    private var mSelectedFile:MutableLiveData<File> = MutableLiveData()
    fun getSelectedFile():LiveData<File>{
        return mSelectedFile
    }
    fun setSelectedFile(file:File){
        mSelectedFile.value= file
    }
    fun convertFile(){
        Log.d(TAG,"convertFile is clicked")
        val bg_work = mSelectedFile.value?.let { Background_Work(it).execute() };
        Log.d(TAG,"convertFile work: $bg_work")
    }

    object viewpager{
        internal lateinit var mViewPager: ViewPager
    }

    //Return selected directory of file
    fun getFileList(): LiveData<List<File>> {
        return mFileList
    }

    fun setFile(selected_File: File){
        Log.d(TAG,"setFile is clicked")
        mFileList.value = mFileHandler.returnListInPath(selected_File)
                //selected_File.listFiles().toList().sorted()
    }

    fun setStartingList(){
        mFileList.value = getListOfStartingDirectory()
    }

    fun changeFragment(currentItem: Int){
        Log.d(TAG, "inside SharedViewModel's changeFragment method")
        mViewPager.currentItem = currentItem
    }

    fun setupViewPager(viewPager: ViewPager, adapter: StatePagerAdapter){

        Log.d(TAG, "inside SharedViewModel's setupViewPager method")
        mViewPager= viewPager
        mAdapter = adapter
        mAdapter.addFragment(Option_Fragment())
        mAdapter.addFragment(ListofFiles())
        mViewPager.adapter = adapter
    }

    fun getListOfStartingDirectory(): MutableList<File> {
        Log.d(TAG, "inside SharedViewModel's getListOfStartingDirectory method")

        mFileHandler = FileHandler()
        //Starting Location/File
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

    fun getBaseDir():File{
        return mFileHandler.setStartingURLByManufacturer(android.os.Build.MANUFACTURER)
    }

    private class Background_Work(val mCurrentFile: File): AsyncTask<Void, Void, String>() {
        override fun doInBackground(vararg p0: Void?): String {
            SubtitleHandler.createSRT(mCurrentFile)
            return "Completed"
        }
        override fun onPostExecute(result: String) {
        }
    }
}