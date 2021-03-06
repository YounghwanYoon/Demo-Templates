package com.ray.mvc_login.otherway

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import android.os.*
import androidx.core.app.ActivityCompat
import androidx.appcompat.app.AppCompatActivity
import android.util.Log
import android.widget.ListView
import android.widget.Toast
import com.ray.mvc_login.R
import com.ray.sqlitetemplate.DatabaseController


class List_View_Activity() :AppCompatActivity()/*, AsyncTask<String, String, String>()  */{
    var PERMISSIONS_STORAGE = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE)

    private val Tag:String = "List_View_Activity.kt"

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        Log.i(Tag, "I am inside List_ViewActivity!!!")
        verifyStoragePermissions(this)

        setView()
        setContentView(R.layout.list_view)
    }

    fun setView(){
        val dbController = DatabaseController(this)

        val arrayOfUser= dbController.getData()
        Toast.makeText(this,"size of array is (${arrayOfUser.size})", Toast.LENGTH_SHORT).show()
        Log.i(Tag, "Size of Array is ${arrayOfUser.size}")

        val listViewAdapter = MyListViewAdapterUsingBase(this, R.layout.each_row, arrayOfUser)

        //com.ray.sqlitetemplate.
        val listView:ListView = findViewById(R.id.listView)
        listView.adapter = listViewAdapter
    }

    fun verifyStoragePermissions(activity: Activity) {
        // Check if we have write permission
        val permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.READ_EXTERNAL_STORAGE)

        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    activity,
                    arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                    0
            )
        }
    }
}