package com.ray.sqlitetemplate.view_model

import android.annotation.SuppressLint
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.util.Log
import com.ray.sqlitetemplate.repository.model.MovieData
import com.ray.sqlitetemplate.repository.remote_data_source.MovieData_Repo
import com.ray.sqlitetemplate.view.MovieData_Activity


class MovieDataViewModel:ViewModel(){

    private val TAG = "MovieDataViewModel"

    private lateinit var mAllMovieData:MutableLiveData<List<MovieData>>

    @SuppressLint("StaticFieldLeak")
    private var activity_View = MovieData_Activity()
/*
    init{
        //Retrieved data from Repository
        mAllMovieData= MovieData_Repo.getDataSet()
    }
*/
    //LiverData will not be changed directly but only indirectly.
    //This prevent data change in such case where user leave
    // smart phone for a period of time and come back, data still won't change
    fun getMovieData(): LiveData<List<MovieData>>{

        //Retrieved data from Repository
        mAllMovieData= MovieData_Repo.getDataSet()
        Log.d(TAG, "getMovieData(): ${mAllMovieData.value?.size}")

    return mAllMovieData
    }


    fun updateUserView(webData:List<MovieData>){
        var arrayListData = ArrayList(webData)
        activity_View.updateUI(arrayListData)
    }

}