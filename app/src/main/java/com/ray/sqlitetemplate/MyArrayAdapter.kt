package com.ray.sqlitetemplate

import android.annotation.SuppressLint
import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*

//ArrayAdapter is Binding between listview and each view .
//resource is the each_row.xml
class MyArrayAdapter(context: Context , val resource:Int, users:ArrayList<Pair<Long,LoginData>>):ArrayAdapter<Pair<Long, LoginData>>(context, resource, users){

    @SuppressLint("ViewHolder")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        //getItem(position) return current position of data/users
        val user = getItem(position)

        var eachView:View? = convertView

        //convertView is the old view to reuse. Make sure it is not null
        if(convertView == null)
             eachView = LayoutInflater.from(context).inflate(resource, parent, false) as View

        // Lookup view for data population
        val userUniqueID: TextView? = eachView?.findViewById(com.ray.sqlitetemplate.R.id.unique_id_text) as TextView
        val userImage: ImageView? = eachView?.findViewById(com.ray.sqlitetemplate.R.id.each_image) as ImageView
        val userID: TextView?= eachView?.findViewById(com.ray.sqlitetemplate.R.id.login_id_text) as TextView
        val userPW: TextView? = eachView?.findViewById(com.ray.sqlitetemplate.R.id.login_pw_text)as TextView

        // Populate the data into the template view using the data object
        userUniqueID!!.text = user.first.toString()
        userID!!.text = user.second.mLoginID
        userPW!!.text = user.second.mLoginPW

        //setOnItemClickListener
        eachView.setOnLongClickListener(View.OnLongClickListener{
            Toast.makeText(context,"I am clicked for a long time", Toast.LENGTH_SHORT).show()
            true
        })

        // Return the completed view to render on screen
        return eachView
    }

    class popUpActivity():AppCompatActivity(){

    }

}