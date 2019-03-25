package com.ray.sqlitetemplate

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.TextView
import kotlinx.android.synthetic.main.each_row.*
import kotlinx.android.synthetic.main.list_view.*

class blank : AppCompatActivity() {

    lateinit var id_text:TextView
    lateinit var pw_text:TextView
    lateinit var uniq_text:TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_blank)

        uniq_text = findViewById(R.id.UniID)
        id_text = findViewById(R.id.id)
        pw_text=findViewById(R.id.pw)

        id_text.text = "Testing ID"
        pw_text.text = "Testing PW"

        var dbController = DatabaseController(this)
        var myData = dbController.getData()

        uniq_text.text = myData.first().first.toString()
        id_text.text = myData.first().second.mLoginID
        pw_text.text = myData.first().second.mLoginPW

        val itemsAdapter = ListViewActivity(this, R.layout.each_row, myData)
        val listView = findViewById<ListView>(R.id.lists)

        listView.adapter = itemsAdapter
    }
}
