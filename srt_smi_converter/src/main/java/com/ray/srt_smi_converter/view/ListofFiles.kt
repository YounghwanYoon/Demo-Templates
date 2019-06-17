package com.ray.srt_smi_converter.view

import android.Manifest
import android.app.Activity
import android.content.Intent.getIntent
import android.content.pm.PackageManager
import android.content.res.Configuration
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.*
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.fragment.app.ListFragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.RecyclerView
import com.ray.srt_smi_converter.R
import com.ray.srt_smi_converter.view.adapter.CustomedAdapter
import com.ray.srt_smi_converter.viewmodel.SharedViewModel
import java.io.File
import java.util.ArrayList

class ListofFiles: ListFragment() {

    private var TAG:String = this.javaClass.simpleName.toString()
    private lateinit var mSharedVM:SharedViewModel

    protected var itemsInCurrentPath: MutableList<String>? = null
    protected var currentPath: MutableList<String>? = null
    protected var root: String? = null
    protected var mPreviousSelectedPath: String? = null
    protected lateinit var myPath: TextView
    protected lateinit var rootFile: File
    protected var files: Array<File>? = null
    protected var listView: View? = null

    // Storage Permissions
    protected val REQUEST_EXTERNAL_STORAGE = 1
    protected var PERMISSIONS_STORAGE =
            arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE)


    //this will store a path of folder that contains music(s) that most recently played.
    var lastSavePath: String? = null

    //RecyclerView Related variables
    private lateinit var mView:View
    private lateinit var recyclerView:RecyclerView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mView = inflater.inflate(R.layout.fragment_list__of__files,container, false)
        return mView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        //Remove Title Bar
        activity?.requestWindowFeature(Window.FEATURE_NO_TITLE)
        //Remove Notification Bar
        activity?.getWindow()?.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        //Verify Permission for Storage
        activity?.let { verifyStoragePermissions(it)}

        mSharedVM = ViewModelProviders.of(this).get(SharedViewModel::class.java)
        mSharedVM.setDirectory("Testing Directory")

        var list =mSharedVM.getListOfCurrentDirectory()

        recyclerView = mView.findViewById(R.id.list_of_items_recycleview)
        val resource = R.layout.each_file
        val adapter: CustomedAdapter? = this.context?.let { CustomedAdapter(it, resource, list) }

    }

    override fun onListItemClick(l: ListView, v: View, position: Int, id: Long) {
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


    /**
     * Checks if the app has permission to write to device storage
     *
     * If the app does not has permission then the user will be prompted to grant permissions
     *
     * @param activity
     */
    fun verifyStoragePermissions(activity: Activity) {
        // Check if we have write permission
        val permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE)

        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            )
        }
    }
}
