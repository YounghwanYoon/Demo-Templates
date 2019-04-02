package com.ray.sqlitetemplate

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.ColorDrawable
import android.support.v4.content.ContextCompat.startActivity
import android.transition.TransitionManager
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*

//ArrayAdapter is Binding between listview and each view .
//resource is the each_row.xml
class MyArrayAdapter(context: Context , private val resource:Int, users:ArrayList<Pair<Long,LoginData>>, val db:DatabaseController):ArrayAdapter<Pair<Long, LoginData>>(context, resource, users){
    val TAG = "MyArrayAdapter.java"

    @SuppressLint("ViewHolder")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        //getItem(position) return current position of data/users
        val user = getItem(position)

        var eachView = convertView

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

        setListener(eachView)
        // Return the completed view to render on screen
        return eachView
    }

    fun setListener(oneListView:View){
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

        //inflate a custom view  using layout inflater
        val myPopUpView = inflater.inflate(R.layout.popup_window, null) //inflate custome view
        val popUpWindow:PopupWindow = PopupWindow(
                myPopUpView,//Custom View
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        )
        //Set pop up window buttons
        val removeButton:Button = myPopUpView.findViewById<Button>(R.id.remove_button)
        val updateButton:Button = myPopUpView.findViewById<Button>(R.id.update_button)

        //Set up button a onClick Listener
        removeButton.setOnClickListener(object:View.OnClickListener{
            override fun onClick(p0: View?) {
                var uniqueID:String
                val someView = oneListView.findViewById<TextView>(p0!!.id)
                Toast.makeText(context, "selected id is ${context.getText(p0!!.id).toString()}!", Toast.LENGTH_SHORT ).show()

                val refreshIntent: Intent =   Intent(context, ListViewActivity::class.java)
                refreshIntent.setFlags(refreshIntent.getFlags() or Intent.FLAG_ACTIVITY_NO_HISTORY) // Adds the FLAG_ACTIVITY_NO_HISTORY flag

                //Refreshing activity
                startActivity(refreshIntent);
                popUpWindow.dismiss() //close window
            }
        })

        updateButton.setOnClickListener(View.OnClickListener {
                Toast.makeText(context, "updateButton!", Toast.LENGTH_SHORT ).show()
                popUpWindow.dismiss()
        })

        //setOnItemClickListener
        oneListView.setOnLongClickListener(View.OnLongClickListener{ view ->
            popUpWindow.elevation = 10.0F

            //this method is called after client call dismiss()
            popUpWindow.setOnDismissListener {
                Toast.makeText(context, "Closing PopUpWindow!", Toast.LENGTH_SHORT ).show()
            }

            //This closes popup window after touching outer layer/Linear Layout.
            //It also prevent open multiple  popup view whenever touching other row of data.
            popUpWindow.setBackgroundDrawable(ColorDrawable())
            popUpWindow.isOutsideTouchable = true

            //Show the popup window
            //TransitionManager.beginDelayedTransition()

            popUpWindow.showAtLocation(
                    myPopUpView.rootView, //Location to display popup window
                    Gravity.CENTER, //Center the item
                    0,
                    0
            )

            Toast.makeText(context,"I am clicked for a long time", Toast.LENGTH_SHORT).show()
            true
        })
    }
}

