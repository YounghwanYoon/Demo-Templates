package com.ray.sqlitetemplate
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.*

class ListViewActivity : AppCompatActivity() {
    private val Tag:String = this.javaClass.name
    private val dbController = DatabaseController(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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
