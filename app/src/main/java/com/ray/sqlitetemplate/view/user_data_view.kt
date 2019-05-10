package com.ray.sqlitetemplate.view

import android.os.Bundle
import android.app.Activity
import android.support.v7.widget.RecyclerView
import android.widget.ArrayAdapter
import com.ray.sqlitetemplate.R
import com.ray.sqlitetemplate.view.view_adapter.user_data_recycle_adapter

class user_data_view : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {1
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_data_view)

        var userRecycleView: RecyclerView = findViewById(R.id.recycler_view);

        var userRecyclerAdapter:user_data_recycle_adapter;

        userRecycleView.adapter(userRecyclerAdapter)
    }

}
