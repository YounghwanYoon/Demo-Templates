package com.ray.sqlitetemplate

import android.os.*
import android.provider.BaseColumns
import android.support.v4.app.FragmentActivity
import android.widget.ListView

class List_View_Activity() :FragmentActivity(), AsyncTask<String, String, String>()  {


    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        setContentView(com.ray.sqlitetemplate.R.layout.list_view)

        var arrayOfUser= arrayListOf<LoginData>()

        var listViewAdapter:MyListViewAdapter = MyListViewAdapter(this,0,arrayOfUser)
        //com.ray.sqlitetemplate.
        var listView: ListView = findViewById(R.id.list_item)
        listView.adapter = listViewAdapter
        var db = DatabaseHelper(this, DatabaseHelper.FeedEntry.DATABASE_NAME,null,1)
        var readCursor = db.getData()
        val itemIds = mutableListOf<Long>()
        with(readCursor){
            while(moveToNext()){
                val itemID = getLong(getColumnIndexOrThrow(BaseColumns._ID))
                itemIds.add(itemID)
            }
        }
    }

}