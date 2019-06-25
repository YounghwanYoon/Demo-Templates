package com.ray.srt_smi_converter.view

import android.Manifest
import android.app.Activity
import android.content.Intent.getIntent
import android.content.pm.PackageManager
import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.ListFragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ray.srt_smi_converter.R
import com.ray.srt_smi_converter.view.adapter.CustomedAdapter
import com.ray.srt_smi_converter.viewmodel.SharedViewModel
import java.io.File

class ListofFiles: Fragment() {

    private var TAG:String = this.javaClass.simpleName.toString()

    private lateinit var mSharedVM:SharedViewModel
    protected var mPreviousSelectedPath: String? = null

    //RecyclerView Related variables
    private lateinit var mView:View
    private lateinit var recyclerView:RecyclerView

    private lateinit var myAdapter: CustomedAdapter
    val resource = R.layout.each_file

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        Log.d(TAG, "ListofFiles class -  onCreateView() is called")

        mView = inflater.inflate(R.layout.fragment_list__of__files,container, false)
        return mView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        Log.d(TAG, "ListofFiles class -  onActivityCreated() is called")
        super.onActivityCreated(savedInstanceState)

        mSharedVM = ViewModelProviders.of(this).get(SharedViewModel::class.java)
        mSharedVM.setDirectory("Testing Directory")

        val list = getListOfDirectory() as MutableList<File>
        Log.d(TAG, "ListofFiles class -  list size is ${list.size}")

        recyclerView = mView.findViewById(R.id.recycle_view)
        myAdapter = CustomedAdapter(context, resource, list)

        Log.d(TAG, "ListofFiles class - context is ${context.toString()}")

        recyclerView.adapter = myAdapter
        recyclerView.layoutManager = LinearLayoutManager(activity)
    }
    private fun getListOfDirectory(): MutableList<Any>{
        return mSharedVM.getListOfCurrentDirectory()
    }

     fun onListItemClick(l: ListView, v: View, position: Int, id: Long) {
         Log.d(TAG, "ListofFiles class -  onListItemClick() is called")

        Toast.makeText(activity,"ONe of list is clicked", Toast.LENGTH_SHORT).show()
      /*
        //One of item in the current folder is selected
        val selected_file = File(itemsInCurrentPath!![position])

        if (selected_file.isDirectory) {
            if (selected_file.canRead()) {
                //Calling previouslySelectedPath() to store most recently visited folder
                //previouslySelectedPath(selected_file);
                //Log.v("SourceListActivity.java", "Last saved music fold was:" + mPreviousSelectedPath);
                getDir(selected_file)
            } else
            //Double caution for selecting non-readable file (which was sorted in getDir();
                Toast.makeText(this@SourceListActivity, "It cannot be read", Toast.LENGTH_SHORT)

        } else if (selected_file.path.endsWith(".mp3") || selected_file.path.endsWith(".mp4") || selected_file.path.endsWith(".mkv") ||
                selected_file.path.endsWith(".avi") || selected_file.path.endsWith(".wmv") || selected_file.path.endsWith(".mpg")) {
            val returnIntent = getIntent()//new Intent();
            returnIntent.putExtra("resultMediaFile", selected_file.path)
            setResult(Activity.RESULT_OK, returnIntent)
            finish()
        } else
            Toast.makeText(this@SourceListActivity, "It is not a directory", Toast.LENGTH_SHORT)//Once selected file is a media file, then it return to parent activity.
    */
    }

    //This method save most recent path that user looked.
    protected fun previouslySelectedPath(previousPath: File) {
        if (previousPath.path.endsWith(".mp3"))
            mPreviousSelectedPath = previousPath.parent
        else
            mPreviousSelectedPath = previousPath.toString()
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
    }


}
