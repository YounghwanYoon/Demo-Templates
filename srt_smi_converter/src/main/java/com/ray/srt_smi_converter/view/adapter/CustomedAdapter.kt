package com.ray.srt_smi_converter.view.adapter

import android.content.Context
import android.widget.ArrayAdapter

class CustomedAdapter(context: Context, resource:Int, list: MutableList<String>) : ArrayAdapter<String>(context,resource,list){
    private var TAG:String = this.javaClass.simpleName.toString()

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