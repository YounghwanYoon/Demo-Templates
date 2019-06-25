package com.ray.srt_smi_converter.viewmodel

import android.R
import android.os.Environment
import android.util.Log
import com.ray.srt_smi_converter.view.adapter.CustomedAdapter
import java.io.File

class ReadData {

    private var TAG:String = this.javaClass.simpleName.toString()

    protected lateinit var itemsInCurrentPath: MutableList<Any>
    private lateinit var currentPath: File
    private lateinit var rootDir: String
    protected var mPreviousSelectedPath: String? = null
    protected lateinit var rootFile: File
    lateinit  var files: MutableList<File>

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
    fun returnListInPath(currentFile: File):MutableList<Any> {
        itemsInCurrentPath = mutableListOf()
        //Display current directory location
        //currentFile = filePath

        //Add current path starting from second call of returnListInPath
        if(firstCall){
            //First Item in itemInCurrentPath is previous directory
            itemsInCurrentPath.add(currentFile)
            firstCall = false
        }
        if(rootDir == currentFile.path){
            firstCall = true
        }
        val unsortedFiles =  currentFile.listFiles().toMutableList()
        //files now has list of files in the current folder(directory)
        files = sortFilesInOrder(unsortedFiles)
        itemsInCurrentPath = filterFiles(files)
        /*
        val fileList = CustomedAdapter(mView.context, R.layout.simple_list_item_1, itemsInCurrentPath, filePath)
        listAdapter = fileList
*/
        return itemsInCurrentPath
    }
    private fun filterFiles(files:MutableList<File>):MutableList<Any>{
        try {
            if (files.size == 0) {
                Log.d(TAG, "Length of files is empty")
            } else {
                //Add all of files in the current Path/Folder to list
                for (i in files.indices) {
                    //Filter files with None-hidden and readableFiles
                    if (!files[i].isHidden || files[i].canRead()) {
                        itemsInCurrentPath.add(files[i])
                    }
                }
            }
            //files[i].parent + "/" + files[i].name + "/"
        } catch (e: NullPointerException) {
            e.stackTrace
        }
        return itemsInCurrentPath
    }

    private fun sortFilesInOrder(files:MutableList<File>):MutableList<File>{
        return files.sorted() as MutableList<File>
    }
    /*
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
*/
    //This method save most recent path that user looked.
    protected fun previouslySelectedPath(previousPath: File) {
        if (previousPath.path.endsWith(".mp3"))
            mPreviousSelectedPath = previousPath.parent
        else
            mPreviousSelectedPath = previousPath.toString()
    }

}
