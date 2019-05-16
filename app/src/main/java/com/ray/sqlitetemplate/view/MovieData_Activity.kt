package com.ray.sqlitetemplate.view

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.LinearLayout
import com.ray.sqlitetemplate.R
import com.ray.sqlitetemplate.repository.model.MovieData
import com.ray.sqlitetemplate.view.adapter.MovieData_RecycleAdapter
import com.ray.sqlitetemplate.view_model.MovieDataViewModel

class MovieData_Activity : AppCompatActivity() {

    private lateinit var mMovieData:ArrayList<MovieData>
    private lateinit var mMovieDataRecycleAdapter:MovieData_RecycleAdapter
    private lateinit var mMovieDataViewModel:MovieDataViewModel

    companion object{
        private val TAG ="MovieData_Activity"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_data_)

        mMovieDataViewModel = ViewModelProviders.of(this).get(MovieDataViewModel::class.java)
        mMovieDataViewModel.getMovieData().observe(this,object:Observer<List<MovieData>>{
            override fun onChanged(t: List<MovieData>?) {
                mMovieDataRecycleAdapter.notifyDataSetChanged()
            }
        })

        getData()
        initRecycleView()
    }
    fun getData(){
        mMovieData = mMovieDataViewModel.getMovieData().value as ArrayList<MovieData>
    }

    private fun initRecycleView(){
        //select recycle view from Activity Layout
        val movieRecycleView:RecyclerView= findViewById(R.id.movie_recycler_view)
        //instantiate custom adapter
        mMovieDataRecycleAdapter = MovieData_RecycleAdapter(mMovieData, this)
        //assign custom adapter to recycle view
        movieRecycleView.adapter  = mMovieDataRecycleAdapter
        //assign a layout and its orientation
        movieRecycleView.layoutManager= LinearLayoutManager(this, LinearLayout.HORIZONTAL, false)
    }

}
