package com.ray.sqlitetemplate

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.support.v4.content.ContextCompat.getSystemService
import android.support.v7.app.AppCompatActivity
import android.text.Layout
import android.transition.TransitionManager
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
        eachView.setOnLongClickListener(View.OnLongClickListener{ view ->
            when(view.id){
                R.id.login_id_text -> {
                    //Initialize a layout inflater instance
                    val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
                    //inflate a custom view  using layout inflater
                    val view = inflater.inflate(R.layout.popup_window, null)
                    val popUpView:PopupWindow = PopupWindow(view,
                            LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT)
                    popUpView.elevation = 10.0F

                    //Set pop up window buttons
                    val removeButton:Button = view.findViewById(R.id.remove_button)
                    val updateButton:Button = view.findViewById(R.id.update_button)

                    //Set up button a onClick Listern
                    removeButton.setOnClickListener{object:View.OnClickListener{
                            override fun onClick(p0: View?) {
                                Toast.makeText(context, "Works!", Toast.LENGTH_SHORT ).show()
                            }
                        }}
                    updateButton.setOnClickListener{object:View.OnClickListener{
                        override fun onClick(p0: View?) {
                            Toast.makeText(context, "Works!", Toast.LENGTH_SHORT ).show()
                        }
                    }}
//https://android--code.blogspot.com/2018/02/android-kotlin-popup-window-example.html
                    popUpView.showAtLocation()

                }
            }
            Toast.makeText(context,"I am clicked for a long time", Toast.LENGTH_SHORT).show()
            true
        })
        // Return the completed view to render on screen
        return eachView
    }
}

