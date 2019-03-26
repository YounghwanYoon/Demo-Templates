package com.ray.sqlitetemplate

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.AdapterView
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast

class ListView_Activity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sql_listview)

        //Attribute sql data
        var dbController = DatabaseController(this)
        var myData = dbController.getData()

        //Setting adapter
        //R.Layout.each_row will represent each individual row of list of data
        val itemsAdapter = MyArrayAdapter(this, R.layout.each_row, myData)
        val listView = findViewById<ListView>(R.id.lists)

        listView.adapter = itemsAdapter
     }


}
