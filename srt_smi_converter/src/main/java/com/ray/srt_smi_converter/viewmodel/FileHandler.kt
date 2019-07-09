package com.ray.srt_smi_converter.viewmodel

import android.os.Environment
import android.util.Log
import java.io.File

class FileHandler {

    private var TAG:String = this.javaClass.simpleName.toString()

    private lateinit var itemsInCurrentPath: MutableList<File>
    private lateinit var currentPath: File
    private var mPreviousSelectedPath: String? = null
    lateinit  var files: MutableList<File>

    companion object{
        var commonFolderCall: Boolean= false
        private lateinit var rootFile: File
        private lateinit var startingDir: String
        fun commonFolderCall(){
            commonFolderCall = true
        }
        fun checkTypeOfFile(directory:String):String{
            val fileType:String
            if(directory.endsWith(".mp3")) {fileType = "mp3"}
            else if(directory.endsWith(".mp4")){fileType = "mp4"}
            else if(directory.endsWith(".avi")){fileType = "avi"}
            else if(directory.endsWith(".mkv")){fileType = "mkv"}
            else if(directory.endsWith(".smi")){fileType = "smi"}
            else if(directory.endsWith(".srt")){fileType = "srt"}
            else{
                fileType="unknown"
            }
            return fileType
        }
        fun isRootDir (file:File):Boolean{
            val isRootDir:Boolean = file == rootFile
            return isRootDir
        }
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
            rootFile = File(samsungFile.path + "/mnt")
        } else {
            Log.d(TAG, "MANUFACTURER Name: " + manufacturer)
            rootFile = testingRootFile
        }

        //Save startingDir
        startingDir = rootFile.path
        return rootFile
    }

    //Return list of given URL and user will recursively call this method until non-directory file such as mp3,or srt is selected
    fun returnListInPath(currentFile: File): MutableList<File> {
        Log.d(TAG, "returnListInPath() - current file is ${currentFile.path}")

        itemsInCurrentPath = mutableListOf()
        itemsInCurrentPath.removeAll(itemsInCurrentPath)

        //if selected directory is not same as starting directory
        if(currentFile.path != startingDir && !commonFolderCall){
            Log.d(TAG, "returnListInPath() - if is called")
            //return to parent file
            itemsInCurrentPath.add(currentFile.parentFile)
            Log.d(TAG, "returnListInPath() - itemsInCurrentPath first file is ${itemsInCurrentPath[0].path}")
            val filteredList = filterFiles(currentFile.listFiles().toMutableList())
            //add list of filtered files
            itemsInCurrentPath.addAll(filteredList)
            itemsInCurrentPath = sortFiles(itemsInCurrentPath)

        }
        //common folder is selected
        else if (currentFile.path != startingDir && commonFolderCall){
            Log.d(TAG, "returnListInPath() - if is called")
            //return to parent file
            itemsInCurrentPath.add(rootFile)
            Log.d(TAG, "returnListInPath() - itemsInCurrentPath first file is ${itemsInCurrentPath[0].path}")
            val filteredList = filterFiles(currentFile.listFiles().toMutableList())
            //add list of filtered files
            itemsInCurrentPath.addAll(filteredList)
            itemsInCurrentPath = sortFiles(itemsInCurrentPath)

            commonFolderCall = false
        }
        else{
            Log.d(TAG, "returnListInPath() - else is called")
            //list of selected file
            itemsInCurrentPath.addAll(rootFileFilter(currentFile.listFiles().toMutableList()))
            itemsInCurrentPath = sortFiles(itemsInCurrentPath)
        }
        Log.d(TAG, "returnListInPath() - itemsInCurrentPath first file is ${itemsInCurrentPath[0].path}")

        return itemsInCurrentPath
    }
    private fun rootFileFilter(files:MutableList<File>): MutableList<File> {
        val filteredList:MutableList<File> = mutableListOf()
        for(file in files){
            if(file.name.contains("sdcard")|| file.name.contains("extSdCard"))
                filteredList.add(file)
        }
        return filteredList
    }

        private fun filterFiles(files:MutableList<File>): MutableList<File> {
            val filteredFiles:MutableList<File> = mutableListOf()
        try {
            if (files.size == 0) {
                Log.d(TAG, "Length of files is empty")
            } else {
                //Add all of files in the current Path/Folder to list
                for (i in files.indices) {
                    //Filter files with None-hidden and readableFiles
                    if (filterTypes(files[i]))
                    {
                        filteredFiles.add(files[i])
                    }
                }
            }
            //files[i].parent + "/" + files[i].name + "/"
        } catch (e: NullPointerException) {
            e.stackTrace
        }
        return filteredFiles
    }

    private fun sortFiles(files:MutableList<File>):MutableList<File>{
        return files.sorted() as MutableList<File>
    }
    private fun filterTypes(file:File):Boolean{
        val isFiltered: Boolean
        val dirType = checkTypeOfFile(file.path)
        if(!file.isHidden || file.canRead()){

            if(dirType == "srt" || dirType == "smi" || file.isDirectory && !file.name[0].equals('.'))
                isFiltered = true
            else
                isFiltered = false
        }
        else
            isFiltered = false

        return isFiltered
    }

    //This method save most recent path that user looked.
    protected fun previouslySelectedPath(previousPath: File) {
        if (previousPath.path.endsWith(".mp3"))
            mPreviousSelectedPath = previousPath.parent
        else
            mPreviousSelectedPath = previousPath.toString()
    }

}
