package com.ray.sqlitetemplate
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.Window
import android.view.WindowManager
import android.widget.*
import com.ray.mvc_login.MyArrayAdapter
import com.ray.mvc_login.R

class ListViewActivity : AppCompatActivity() {
    private val Tag:String = this.javaClass.name
    private val dbController = DatabaseController(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //Remove title bar
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        //Remove notification bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_sql_listview)

        //Attribute sql data
        val myData = dbController.getData()

        //Setting adapter
        //R.Layout.each_row will represent each individual row of list of data
        val itemsAdapter = MyArrayAdapter(this, R.layout.each_row, myData, dbController)
        val listView = findViewById<ListView>(R.id.lists)
        //setListener(listView)
        listView.adapter = itemsAdapter
    }
}
