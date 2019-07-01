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
import com.ray.srt_smi_converter.view.interfaces.RecyclerViewOnClickListener
import com.ray.srt_smi_converter.viewmodel.ReadData
import java.io.File

class CustomedAdapter(private val context: Context?, val singleLayout:Int, var list: MutableList<File>) : RecyclerView.Adapter<CustomedAdapter.MyViewHolder>(){

    private var TAG:String = this.javaClass.simpleName.toString()
    lateinit var mListener: RecyclerViewOnClickListener

    fun setOnItemClickListener(listener:RecyclerViewOnClickListener){
        mListener = listener
    }

    fun updateList(updatedList:MutableList<File>){
        this.list = updatedList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        Log.d(TAG, "CustomedAdapter class -  onCreateViewHolder() is called")

        val eachView = LayoutInflater.from(context).inflate(singleLayout, parent, false) as View
        val myViewHolder = MyViewHolder(eachView)

        return myViewHolder
    }

    override fun getItemCount(): Int {
        Log.d(TAG, "CustomedAdapter class -  getItemCount() is called")
        return list.size
    }

    //Declaring onClick Listener here will be wasteful
    //Each Scroll will call this onBindViewHolder() and will be wasteful
    //Intead, declear click listener under MyViewHolder. eachView()
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        Log.d(TAG, "CustomedAdapter class -  onBindViewHolder() is called")

        holder.eachView(list[position])
    }

    inner class MyViewHolder(private var eachView:View): RecyclerView.ViewHolder(eachView), View.OnClickListener{

        private var TAG:String = this.javaClass.simpleName.toString()

        fun eachView(dir: File){
            Log.d(TAG, "MyViewHolder innerclass -  eachView() is called")

            val image = eachView.findViewById<ImageView>(R.id.imageView)
            val directory = eachView.findViewById<TextView>(R.id.resource_dir_textview)

            //Set directory
            directory.text = dir.name
            image.setImageResource(chooseImage(dir.absolutePath))

            eachView.setOnClickListener(this)
        }
        private fun chooseImage(directory:String): Int{
            Log.d(TAG, "MyViewHolder innerclass -  chooseImage() is called")
            Log.d(TAG, "MyViewHolder innerclass -  directory is ${directory}")
            val dirType = ReadData.checkTypeOfFile(directory)
            val imageChoice: Int

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
/*
        fun checkTypeOfFile(directory:String):String{
            Log.d(TAG, "MyViewHolder innerclass -  checkTypeOfFile() is called")

             val fileType:String
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
*/
        private fun filterText(text:String):String{
            val tempText =text

            return tempText
        }

        override fun onClick(p0: View?) {
            val mPosition = adapterPosition
            //Checking whether this position is valid
            if(mPosition != RecyclerView.NO_POSITION){
                mListener.onItemClickListener(mPosition)
            }
        }
    }
}