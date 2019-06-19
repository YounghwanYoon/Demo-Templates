package com.ray.srt_smi_converter.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.ray.srt_smi_converter.R

class CustomedAdapter(context: Context, resource:Int, list: MutableList<String>) : ArrayAdapter<String>(context,resource,list){
    private var TAG:String = this.javaClass.simpleName.toString()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View? {
        var eachView = convertView
        if(eachView== null){
            eachView = LayoutInflater.from(context).inflate(R.layout.each_file, parent, false) as View
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
            "mp3" -> imageChoice = 1 //return Image
            "mp4" -> imageChoice = 2
            "avi" -> imageChoice = 2
            "smi" -> imageChoice = 3
            "srt" -> imageChoice = 3
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