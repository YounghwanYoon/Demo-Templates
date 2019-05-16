package com.ray.sqlitetemplate.view_model

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.ray.sqlitetemplate.repository.model.MovieData
import com.ray.sqlitetemplate.repository.remote_data_source.MovieData_Repo


class MovieDataViewModel:ViewModel(){

    private lateinit var mAllMovieData:MutableLiveData<List<MovieData>>
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
        return mAllMovieData
    }

}