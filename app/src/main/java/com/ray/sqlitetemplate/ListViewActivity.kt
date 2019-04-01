package com.ray.sqlitetemplate

import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.nfc.Tag
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import kotlinx.android.synthetic.main.list_view.*

class ListViewActivity : AppCompatActivity() {
    private val dbController = DatabaseController(this)

    companion object {
        val TAG:String = this.javaClass.name
        fun removeData() {

        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sql_listview)

        //Attribute sql data
        val myData = dbController.getData()

        //Setting adapter
        //R.Layout.each_row will represent each individual row of list of data
        val itemsAdapter = MyArrayAdapter(this, R.layout.each_row, myData)
        val listView = findViewById<ListView>(R.id.lists)
        //setListener(listView)
        listView.adapter = itemsAdapter

        listView.onItemClickListener = object: AdapterView.OnItemClickListener{
            
            override fun onItemClick(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {

                val uniqueID:Pair<Long,LoginData> = listView.adapter.getItem(p2) as Pair<Long, LoginData>

                Log.i(TAG, "Unique is ${uniqueID.first.toString()}")
                Log.i(TAG, "Long is $p3")
                removeUser(uniqueID.first.toString())


            }
        }

    }

    fun removeUser(uniqueID:String){
        dbController.removeData(uniqueID)
        //Refreshing activity
        startActivity(getIntent());
    }
    fun setPopUpWindow(listV:ListView, myAdapter:MyArrayAdapter, position: Int, convertView: View?, parent: ViewGroup){


        val inflater = this.applicationContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

        //inflate a custom view  using layout inflater
        val myPopUpView = inflater.inflate(R.layout.popup_window, null) //inflate custome view
        val popUpWindow: PopupWindow = PopupWindow(
                myPopUpView,//Custom View
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        )
        //Set pop up window buttons
        val removeButton: Button = myPopUpView.findViewById<Button>(R.id.remove_button)
        val updateButton: Button = myPopUpView.findViewById<Button>(R.id.update_button)

        //Set up button a onClick Listener
        removeButton.setOnClickListener(object: View.OnClickListener{
            override fun onClick(p0: View?) {
                popUpWindow.dismiss() //close window
            }
        })
        removeButton.setOnClickListener(object: AdapterView.OnItemClickListener, View.OnClickListener {
            override fun onClick(p0: View?) {

            }
            override fun onItemClick(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                val uniqueID:Pair<Long,LoginData> = listV.adapter.getItem(p2) as Pair<Long, LoginData>
                Log.i(TAG, "Unique is ${uniqueID.first.toString()}")
                Log.i(TAG, "Long is $p3")
                removeUser(uniqueID.first.toString())
                popUpWindow.dismiss() //close window

            }
        })

        updateButton.setOnClickListener(View.OnClickListener {
            popUpWindow.dismiss()
        })

        //setOnItemClickListener
        listV.setOnLongClickListener(View.OnLongClickListener{ view ->
            popUpWindow.elevation = 10.0F

            //this method is called after client call dismiss()
            popUpWindow.setOnDismissListener {
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

            Toast.makeText(this@ListViewActivity,"I am clicked for a long time", Toast.LENGTH_SHORT).show()
            true
        })
    }
}
