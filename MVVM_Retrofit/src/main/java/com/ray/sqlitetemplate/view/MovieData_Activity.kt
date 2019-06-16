package com.ray.sqlitetemplate.view

import android.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.net.ConnectivityManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.util.Log
import android.view.Window
import android.view.WindowManager
import android.widget.LinearLayout
import com.ray.sqlitetemplate.R
import com.ray.sqlitetemplate.repository.model.MovieData
import com.ray.sqlitetemplate.view.adapter.MovieData_RecycleAdapter
import com.ray.sqlitetemplate.view_model.MovieDataViewModel

//https://proandroiddev.com/mvvm-architecture-viewmodel-and-livedata-part-1-604f50cda1
class MovieData_Activity : AppCompatActivity() {

    private lateinit var mMovieData:ArrayList<MovieData>
    private lateinit var mMovieDataRecycleAdapter:MovieData_RecycleAdapter
    private lateinit var mMovieDataViewModel:MovieDataViewModel

    companion object{
        private val TAG ="MovieData_Activity"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //Remove Title
        requestWindowFeature(Window.FEATURE_NO_TITLE);



        //set up full screen
        getWindow()
                .setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_movie_data_)

/*
        mMovieDataViewModel = ViewModelProviders.of(this).get(MovieDataViewModel::class.java)
        mMovieDataViewModel.getMovieData().observe(this,object:Observer<List<MovieData>>{
            override fun onChanged(t: List<MovieData>?) {
                mMovieDataRecycleAdapter.notifyDataSetChanged()
            }
        })*/



        this.mMovieDataViewModel = ViewModelProviders.of(this).get(MovieDataViewModel::class.java)
        observeViewModel(mMovieDataViewModel)
        //getData()
       // initRecycleView()
    }


    private fun observeViewModel(viewModel:MovieDataViewModel){
        viewModel.getMovieData()/*getMovieDataObservable()*/.observe(this, object: Observer<List<MovieData>> {
            override fun onChanged(t: List<MovieData>?) {
                Log.d(TAG,"Is value of data  null ? ${t == null}")
                when(t){
                    null -> errorHanding()
                    else -> mMovieDataRecycleAdapter.notifyDataSetChanged()//initRecycleView(ArrayList(t))
                }
                //initRecycleView(ArrayList(t))
            }
        })
    }
    private fun errorHanding(){
        if(isNetworkConnected()){
            AlertDialog.Builder(this).setTitle("Something went wrong!")
                    .setMessage("Unknown error occured, please try it again")
                    .setPositiveButton(android.R.string.ok) { _, _ -> }
                    .setIcon(android.R.drawable.ic_dialog_alert).show()
        }
        else{
            AlertDialog.Builder(this).setTitle("No Internet Connection")
                    .setMessage("Please check your internet connection and try again")
                    .setPositiveButton(android.R.string.ok, object:DialogInterface.OnClickListener{
                        override fun onClick(p0: DialogInterface?, p1: Int) {
                            val refresh:Intent = Intent(applicationContext, LoginActivity::class.java)

                            // Adds the FLAG_ACTIVITY_NO_HISTORY flag
                            refresh.setFlags(refresh.getFlags() or Intent.FLAG_ACTIVITY_NO_HISTORY)
                            startActivity(refresh)
                        }
                    })//_, _ -> }
                    .setIcon(android.R.drawable.ic_dialog_alert).show()
        }
    }

    //Checking Network Connectivity
    private fun isNetworkConnected(): Boolean {
        val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager //1
        val networkInfo = connectivityManager.activeNetworkInfo //2
        return networkInfo != null && networkInfo.isConnected //3
    }

    private fun initRecycleView(MovieDataList:ArrayList<MovieData>){
        //select recycle view from Activity Layout
        val movieRecycleView: RecyclerView = findViewById(R.id.movie_recycler_view)

        var mMovieArray = ArrayList (MovieDataList)

        //instantiate custom adapter
        mMovieDataRecycleAdapter = MovieData_RecycleAdapter(mMovieDataViewModel.getMovieData().value as ArrayList<MovieData>/*mMovieArray*/, this)
        //assign custom adapter to recycle view
        movieRecycleView.adapter  = mMovieDataRecycleAdapter
        //assign a layout and its orientation
        movieRecycleView.layoutManager= LinearLayoutManager(this, LinearLayout.HORIZONTAL, false)
    }

    fun getData(){
        //mMovieDataViewModel.getMovieData()
        mMovieData = mMovieDataViewModel.getMovieData().value as ArrayList<MovieData>
    }

    private fun initRecycleView(){

        //select recycle view from Activity Layout
        val movieRecycleView: RecyclerView = findViewById(R.id.movie_recycler_view)
        //instantiate custom adapter
        mMovieDataRecycleAdapter = MovieData_RecycleAdapter(mMovieData, this)
        //assign custom adapter to recycle view
        movieRecycleView.adapter  = mMovieDataRecycleAdapter
        //assign a layout and its orientation
        movieRecycleView.layoutManager= LinearLayoutManager(this, LinearLayout.HORIZONTAL, false)
    }

     fun updateUI(webData:ArrayList<MovieData>){
        //select recycle view from Activity Layout
        val movieRecycleView: RecyclerView = findViewById(R.id.movie_recycler_view)
        //instantiate custom adapter
        mMovieDataRecycleAdapter = MovieData_RecycleAdapter(webData, this)
        //assign custom adapter to recycle view
        movieRecycleView.adapter  = mMovieDataRecycleAdapter
        //assign a layout and its orientation
        movieRecycleView.layoutManager= LinearLayoutManager(this, LinearLayout.HORIZONTAL, false)
    }
}
