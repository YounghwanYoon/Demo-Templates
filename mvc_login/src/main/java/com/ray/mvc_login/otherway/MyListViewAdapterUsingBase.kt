package com.ray.mvc_login.otherway

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import androidx.annotation.NonNull
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.view.LayoutInflater
import android.widget.BaseAdapter
import android.widget.ImageView
import com.ray.sqlitetemplate.repository.model.UserData
import com.ray.sqlitetemplate.R


class MyListViewAdapterUsingBase(private val activity: Activity, resource:Int, @NonNull private val users:ArrayList<Pair<Long, UserData>>):BaseAdapter()/*ArrayAdapter<Pair<Long,UserData>>(context, 0, users)*/{

    @SuppressLint("ViewHolder", "InflateParams")
    override fun getView(position: Int, convertView: View, parent: ViewGroup?): View {
        // Get the data item for this position
        var user:Pair<Long, UserData> = getItem(position)

        var listView = convertView as View

        val inflater = activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

        if(convertView == null)
            listView = inflater.inflate(R.layout.each_row, null)

        // Lookup view for data population
        val userUniqueID:TextView? = listView.findViewById(R.id.unique_id_text) as TextView
        val userImage: ImageView? = listView.findViewById(R.id.each_image) as ImageView
        val userID:TextView?= listView.findViewById(R.id.login_id_text) as TextView
        val userPW:TextView? = listView.findViewById(R.id.login_pw_text)as TextView

        // Populate the data into the template view using the data object
        userUniqueID!!.text = user.first.toString()
        userID!!.text = user.second.mLoginID
        userPW!!.text = user.second.mLoginPW
        // Return the completed view to render on screen
        return listView
    }
    override fun getItem(p0: Int): Pair<Long, UserData> {
        return users.get(p0)
    }

    override fun getItemId(p0: Int): Long {
        return p0.toLong()
    }

    override fun getCount(): Int {
        return users.size
    }
}