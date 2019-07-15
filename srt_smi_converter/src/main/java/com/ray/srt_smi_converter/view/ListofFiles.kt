package com.ray.srt_smi_converter.view

import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ray.srt_smi_converter.view.adapter.CustomedAdapter
import com.ray.srt_smi_converter.view.interfaces.RecyclerViewOnClickListener
import com.ray.srt_smi_converter.viewmodel.SharedViewModel
import java.io.File
import com.ray.srt_smi_converter.model.BasedSubtitleData
import com.ray.srt_smi_converter.viewmodel.FileHandler
import com.ray.srt_smi_converter.viewmodel.SubtitleHandler
import kotlinx.android.synthetic.main.fragment_list__of__files.*


class ListofFiles: Fragment(), RecyclerViewOnClickListener,View.OnClickListener {
    private var baseDirectory: String = Environment.getRootDirectory().parentFile.path +  "/mnt/sdcard"
    override fun onClick(p0: View?) {
        when(p0){
            download_folder -> {
                FileHandler.commonFolderCall()
                updateList(File(baseDirectory+"/Download"))
            }
            movie_folder -> {
                FileHandler.commonFolderCall()
                updateList(File(baseDirectory+"/Movies"))
            }
            photo_folder -> {
                FileHandler.commonFolderCall()
                updateList(File(baseDirectory + "/DCIM"))
            }
        }
    }

    private var TAG:String = this.javaClass.simpleName.toString()

    private lateinit var mSharedVM:SharedViewModel
    protected var mPreviousSelectedPath: String? = null

    //RecyclerView Related variables
    private lateinit var mView:View
    //private lateinit var recyclerView:RecyclerView

    private lateinit var myAdapter: CustomedAdapter
    val resource = com.ray.srt_smi_converter.R.layout.each_file

    private lateinit var list:MutableList<File>

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        Log.d(TAG, "ListofFiles class -  onCreateView() is called")

        mView = inflater.inflate(com.ray.srt_smi_converter.R.layout.fragment_list__of__files,container, false)

        return mView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        Log.d(TAG, "ListofFiles class -  onActivityCreated() is called")
        super.onActivityCreated(savedInstanceState)

        mSharedVM = ViewModelProviders.of(this).get(SharedViewModel::class.java)
        mSharedVM.setDirectory("Testing Directory")

        list = getListOfDirectory()
        Log.d(TAG, "ListofFiles class -  list size is ${list.size}")

        //recyclerView = mView.findViewById(com.ray.srt_smi_converter.R.id.recyclerView)
        myAdapter = CustomedAdapter(context, resource, list)

        Log.d(TAG, "ListofFiles class - context is ${context.toString()}")

        //recyclerView.addOnItemTouchListener(object: RecyclerViewTouchListener(context, recyclerView, this){})
        recyclerView.adapter = myAdapter
        myAdapter.setOnItemClickListener(this)
        recyclerView.layoutManager = LinearLayoutManager(activity)

        //common_folder set on click listener
        download_folder.setOnClickListener(this)
        movie_folder.setOnClickListener(this)
        movie_folder.setOnClickListener(this)

    }
    private fun getListOfDirectory(): MutableList<File>{
        return mSharedVM.getListOfCurrentDirectory()
    }


    //from interface
    override fun onItemClickListener(position: Int) {
        Log.d(TAG, "ListofFiles class -  onItemClickListener() is called")
        Log.d(TAG, "ListofFiles class -  path of clicked item is ${(list[position] ).absolutePath}")
        Toast.makeText(activity,"One of list is clicked $position" , Toast.LENGTH_SHORT).show()
        val tempFile = (list[position])

        if(tempFile.isDirectory && tempFile.canRead())
            updateList(tempFile)
        else{
            Toast.makeText(activity,"Desired FIle is clicked ${tempFile.path}" , Toast.LENGTH_SHORT).show()
            var ListOfAllTexts: MutableList<BasedSubtitleData> = context?.let { SubtitleHandler.parseData(tempFile, it) }!!
            SubtitleHandler.createSRT(ListOfAllTexts, tempFile, context!!)
        }
    }

    fun updateList(file:File){
        list = mSharedVM.updatedList(file)
        myAdapter.updateList(list)
        myAdapter.notifyDataSetChanged()
    }
}
