package com.ray.srt_smi_converter.view

import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.ray.srt_smi_converter.view.adapter.CustomedAdapter
import com.ray.srt_smi_converter.view.interfaces.RecyclerViewOnClickListener
import com.ray.srt_smi_converter.viewmodel.SharedViewModel
import java.io.File
import com.ray.srt_smi_converter.viewmodel.FileHandler
import kotlinx.android.synthetic.main.fragment_list__of__files.*

class ListofFiles: Fragment(), RecyclerViewOnClickListener,View.OnClickListener {
    private var localBaseDir: String = Environment.getRootDirectory().parentFile.path +  "/mnt/sdcard"
    private val option_fragment = 0
    override fun onClick(p0: View?) {
        when(p0){
            download_folder -> {
                FileHandler.commonFolderCall()
                updateList(File(localBaseDir+"/Download"))
            }
            movie_folder -> {
                FileHandler.commonFolderCall()
                updateList(File(localBaseDir+"/Movies"))
            }
            starting_folder-> {
                FileHandler.commonFolderCall()
                updateList(mSharedVM.getBaseDir())
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
        init()
        return mView
    }

    fun init(){
        Log.d(TAG, "Init sharedVM")
        mSharedVM = ViewModelProviders.of(this.activity!!).get(SharedViewModel::class.java)
        if(mSharedVM.getFileList().value == null)
            mSharedVM.setStartingList()
            //mSharedVM.setFile(File(localBaseDir))
        /*
        if(list == null){
            list = getListOfDirectory()
        }
        */

        //recyclerView = mView.findViewById(com.ray.srt_smi_converter.R.id.recyclerView)
        myAdapter = CustomedAdapter(context, resource, mSharedVM.getFileList().value as MutableList<File>)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        //recyclerView.addOnItemTouchListener(object: RecyclerViewTouchListener(context, recyclerView, this){})
        recyclerView.adapter = myAdapter
        myAdapter.setOnItemClickListener(this)
        recyclerView.layoutManager = LinearLayoutManager(activity)

        //common_folder set on click listener
        download_folder.setOnClickListener(this)
        movie_folder.setOnClickListener(this)
        starting_folder.setOnClickListener(this)
        //movie_folder.setOnClickListener(this)
        super.onActivityCreated(savedInstanceState)
    }
    private fun getListOfDirectory(): MutableList<File>{
        return mSharedVM.getListOfStartingDirectory()
    }

    //from interface
    override fun onItemClickListener(position: Int) {
        Log.d(TAG, "ListofFiles class -  onItemClickListener() is called")

        val selectedFile = mSharedVM.getFileList().value!!.get(position)//(list[position])

        if(selectedFile.isDirectory && selectedFile.canRead())
            updateList(selectedFile)
        else{
            Log.d(TAG, "SelectedFile extension is ${selectedFile.extension}")
            if(selectedFile.extension =="smi"){
                Toast.makeText(activity,"Desired File is clicked ${selectedFile.name}" , Toast.LENGTH_SHORT).show()
                mSharedVM.setSelectedFile(selectedFile)
                Log.d(TAG, "selected file is ${mSharedVM.getSelectedFile().value?.name}")
                mSharedVM.changeFragment(option_fragment )
                //var ListOfAllTexts: MutableList<BasedSubtitleData> = context?.let { SubtitleHandler.createSRT(selectedFile, it) }!!
            }
            else{
                Log.d(TAG, "Other than smi, it is not supported")
            }
        }
    }

    fun updateList(file:File){
        mSharedVM.setFile(file)
        myAdapter.updateList(mSharedVM.getFileList().value as MutableList<File>)
        myAdapter.notifyDataSetChanged()
        /*
        list = mSharedVM.updatedList(file)
        myAdapter.updateList(mSharedVM.updatedList(file))
        myAdapter.notifyDataSetChanged()
        */
    }
}
