package com.ray.srt_smi_converter.viewmodel

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.content.res.Configuration
import android.os.Environment
import android.util.Log
import android.view.*
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import java.io.File

class ReadData {

    private var TAG:String = this.javaClass.simpleName.toString()

    protected lateinit var itemsInCurrentPath: MutableList<String>
    private lateinit var currentPath: String
    private lateinit var rootDir: String
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

    fun getCurrentDirectory():String{return currentPath}

    companion object{
        var firstCall: Boolean= true
    }

    //Set Starting Point of URL which will be called only once
    //android.os.Build.MANUFACTURER
    fun setStartingURLByManufacturer(deviceManufacturer: String):File{

        val manufacturer = deviceManufacturer
        val testingRootFile = Environment.getRootDirectory().parentFile
        var samsungFile = Environment.getRootDirectory().parentFile

        //If user using samsung device
        if (manufacturer.contains("Samsung") || manufacturer.contains("samsung")) {
            Log.d(TAG, "MANUFACTURER Name: " + manufacturer)
            rootFile = samsungFile
        } else {
            Log.d(TAG, "MANUFACTURER Name: " + manufacturer)
            rootFile = testingRootFile
        }

        rootDir = rootFile.path
        return rootFile
    }

    //Return list of given URL and user will recursively call this method until non-directory file such as mp3,or srt is selected
    fun returnListInPath(filePath: File):MutableList<String> {
        itemsInCurrentPath = mutableListOf()
        //Display current directory location
        currentPath = filePath.path

        //Add current path starting from second call of returnListInPath
        if(firstCall){
            firstCall = false
        }
        else{
            //First Item in itemInCurrentPath is previous directory
            itemsInCurrentPath.add(currentPath)
        }

        if(rootDir == filePath.path){
            firstCall = true
        }

        //files now has list of files in the current folder(directory)
        files = filePath.listFiles()
        try {
            if (files!!.size == 0) {
                Log.d(TAG, "Length of files is empty")
            } else {
                //Add all of files in the current Path/Folder to list
                for (i in files!!.indices) {
                    if (!files!![i].isHidden || files!![i].canRead()) {
                        itemsInCurrentPath!!.add(files!![i].parent + "/" + files!![i].name + "/")
                    }
                }
            }
        } catch (e: NullPointerException) {
            e.stackTrace
        }

/*
        val fileList = CustomedAdapter(mView.context, android.R.layout.simple_list_item_1, itemsInCurrentPath, filePath)
        listAdapter = fileList
*/
        return itemsInCurrentPath
    }

    override fun onListItemClick(l: ListView, v: View, position: Int, id: Long) {
        //One of item in the current folder is selected
        val selected_file = File(itemsInCurrentPath!![position])

        if (selected_file.isDirectory) {
            if (selected_file.canRead()) {
                //Calling previouslySelectedPath() to store most recently visited folder
                //previouslySelectedPath(selected_file);
                //Log.v("SourceListActivity.java", "Last saved music fold was:" + mPreviousSelectedPath);
                returnListInPath(selected_file)
            } else
            //Double caution for selecting non-readable file (which was sorted in returnListInPath();
                Toast.makeText(this@SourceListActivity, "It cannot be read", Toast.LENGTH_SHORT)

        } else if (selected_file.path.endsWith(".mp3") || selected_file.path.endsWith(".mp4") || selected_file.path.endsWith(".mkv") ||
                selected_file.path.endsWith(".avi") || selected_file.path.endsWith(".wmv") || selected_file.path.endsWith(".mpg")) {
            val returnIntent = Intent.getIntent()//new Intent();
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

}
