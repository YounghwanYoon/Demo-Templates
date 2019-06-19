package com.ray.srt_smi_converter.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.ray.srt_smi_converter.R

class CustomedAdapter(context: Context, val singleLayout:Int, list: MutableList<String>) : ArrayAdapter<String>(context,singleLayout,list){
    private var TAG:String = this.javaClass.simpleName.toString()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View? {
        var eachView = convertView
        if(eachView== null){
            eachView = LayoutInflater.from(context).inflate(singleLayout, parent, false) as View
        }

        val image = eachView.findViewById<ImageView>(R.id.imageView)
        val directory = eachView.findViewById<TextView>(R.id.resource_dir_textview)
        val data = getItem(position)
        //Set Image based on directory type

        //Set directory
        directory.text = data
        return eachView
    }

    fun chooseImage(directory:String): Int{
        val dirType = checkTypeOfFile(directory)
        var imageChoice:Int = 0

        //Choose Image based on directory type.
        when(dirType){
            "mp3" -> imageChoice = R.drawable.ic_music//return Image
            "mp4" -> imageChoice = R.drawable.ic_video
            "avi" -> imageChoice = R.drawable.ic_video
            "smi" -> imageChoice = R.drawable.ic_file
            "srt" -> imageChoice = R.drawable.ic_file

        }

        return imageChoice
    }

    fun checkTypeOfFile(directory:String):String{
        var fileType:String
        if(directory.endsWith(".mp3")) {fileType = "mp3"}
        else if(directory.endsWith(".mp4")){fileType = "avi"}
        else if(directory.endsWith(".avi")){fileType = "mp4"}
        else if(directory.endsWith(".smi")){fileType = "smi"}
        else if(directory.endsWith(".srt")){fileType = "srt"}
        else{
            fileType="unknown"
        }
        return fileType
    }

    override fun getItem(position: Int): String? {
        return super.getItem(position)
    }

    override fun getCount(): Int {
        return super.getCount()
    }

    override fun getPosition(item: String?): Int {
        return super.getPosition(item)
    }
}