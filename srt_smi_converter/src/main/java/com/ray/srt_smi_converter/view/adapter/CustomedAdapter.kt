package com.ray.srt_smi_converter.view.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ray.srt_smi_converter.R
import java.io.File

class CustomedAdapter(val context: Context?, val singleLayout:Int, var list: MutableList<File>) : RecyclerView.Adapter<CustomedAdapter.MyViewHolder>(){

    private var TAG:String = this.javaClass.simpleName.toString()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        Log.d(TAG, "CustomedAdapter class -  onCreateViewHolder() is called")

        var eachView = LayoutInflater.from(context).inflate(singleLayout, parent, false) as View
        val myViewHolder = MyViewHolder(eachView)

        return myViewHolder
    }

    override fun getItemCount(): Int {
        Log.d(TAG, "CustomedAdapter class -  getItemCount() is called")
        return list.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        Log.d(TAG, "CustomedAdapter class -  onBindViewHolder() is called")

        holder.eachView(list[position].path)
    }

    class MyViewHolder(private var eachView:View): RecyclerView.ViewHolder(eachView){
        private var TAG:String = this.javaClass.simpleName.toString()

        fun eachView(dir: String){
            Log.d(TAG, "MyViewHolder innerclass -  eachView() is called")

            val image = eachView.findViewById<ImageView>(R.id.imageView)
            val directory = eachView.findViewById<TextView>(R.id.resource_dir_textview)

            //Set directory
            directory.text = dir
            image.setImageResource(chooseImage(dir))
        }

        private fun chooseImage(directory:String): Int{
            Log.d(TAG, "MyViewHolder innerclass -  chooseImage() is called")

            val dirType = checkTypeOfFile(directory)
            var imageChoice:Int = 0

            //Choose Image based on directory type.
            imageChoice = when(dirType){
                "mp3" -> R.drawable.ic_music//return Image
                "mp4" -> R.drawable.ic_video
                "avi" -> R.drawable.ic_video
                "smi" -> R.drawable.ic_file
                "srt" -> R.drawable.ic_file
                else
                -> R.drawable.ic_folder
            }

            return imageChoice
        }

        fun checkTypeOfFile(directory:String):String{
            Log.d(TAG, "MyViewHolder innerclass -  checkTypeOfFile() is called")

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
    }
}