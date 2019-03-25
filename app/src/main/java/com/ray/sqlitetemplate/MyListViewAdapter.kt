package com.ray.sqlitetemplate

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.support.annotation.NonNull
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import android.view.LayoutInflater
import android.widget.BaseAdapter
import android.widget.ImageView


class MyListViewAdapter(private val activity: Activity, resource:Int, @NonNull private val users:ArrayList<Pair<Long,LoginData>>):BaseAdapter()/*ArrayAdapter<Pair<Long,LoginData>>(context, 0, users)*/{

    @SuppressLint("ViewHolder", "InflateParams")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        // Get the data item for this position
        var user:Pair<Long,LoginData> = getItem(position)

        var listView = convertView as View

        val inflater = activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        listView = inflater.inflate(R.layout.each_row, null)

        /*// Check if an existing view is being reused, otherwise inflate the view
        if (listView == null) {
            listView = LayoutInflater.from(this.activity).inflate(com.ray.sqlitetemplate.R.layout.each_row, parent, false)
        }*/
        // Lookup view for data population
        val userUniqueID:TextView? = listView.findViewById(com.ray.sqlitetemplate.R.id.unique_id_text) as TextView
        val userImage: ImageView? = listView.findViewById(com.ray.sqlitetemplate.R.id.each_image) as ImageView
        val userID:TextView?= listView.findViewById(com.ray.sqlitetemplate.R.id.login_id_text) as TextView
        val userPW:TextView? = listView.findViewById(com.ray.sqlitetemplate.R.id.login_pw_text)as TextView

        // Populate the data into the template view using the data object
        userUniqueID!!.text = user.first.toString()
        userID!!.text = user.second.mLoginID
        userPW!!.text = user.second.mLoginPW
        // Return the completed view to render on screen
        return listView
    }
    override fun getItem(p0: Int): Pair<Long,LoginData> {
        return users.get(p0)
    }

    override fun getItemId(p0: Int): Long {
        return p0.toLong()
    }

    override fun getCount(): Int {
        return users.size
    }
}