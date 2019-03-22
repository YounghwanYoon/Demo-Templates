package com.ray.sqlitetemplate

import android.content.Context
import android.support.annotation.NonNull
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import android.view.LayoutInflater
import android.widget.ImageView


class MyListViewAdapter(context: Context, resource:Int, @NonNull users: List<LoginData>):ArrayAdapter<LoginData>(context, 0, users){

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View? {
        // Get the data item for this position
        val user = getItem(position)

        var listView:View? =convertView
        // Check if an existing view is being reused, otherwise inflate the view
        if (listView == null) {
            listView = LayoutInflater.from(this.context).inflate(com.ray.sqlitetemplate.R.layout.each_row, parent, false)
        }
        // Lookup view for data population
        val userImage: ImageView? = listView!!.findViewById(com.ray.sqlitetemplate.R.id.each_image) as ImageView
        val userID:TextView?= listView!!.findViewById(com.ray.sqlitetemplate.R.id.login_id_text) as TextView
        val userPW:TextView? = listView!!.findViewById(com.ray.sqlitetemplate.R.id.login_pw_text)as TextView

        // Populate the data into the template view using the data object
        userID?.setText(user!!.mLoginID)
        userPW?.setText(user!!.mLoginPW)
        // Return the completed view to render on screen
        return listView
    }


}