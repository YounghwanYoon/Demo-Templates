package com.ray.sqlitetemplate.view_model

import android.annotation.SuppressLint
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import android.util.Log
import com.ray.sqlitetemplate.repository.model.MovieData
import com.ray.sqlitetemplate.repository.remote_data_source.MovieData_Repo
import com.ray.sqlitetemplate.view.MovieData_Activity

class MovieDataViewModel(application: Application): AndroidViewModel(application) {

    private val TAG = "MovieDataViewModel"

    private lateinit var mAllMovieData:MutableLiveData<List<MovieData>>

    private val mMovieDataObservable:LiveData<List<MovieData>>

    init{
        mMovieDataObservable = MovieData_Repo.getDataSet()
        Log.d(TAG, "MovieDataViewModel - init called")
    }


    fun getMovieDataObservable():LiveData<List<MovieData>>{
        return mMovieDataObservable
        Log.d(TAG, "MovieDataViewModel - getMovieDataObservable() called")

    }
    //LiverData will not be changed directly but only indirectly.
    //This prevent data change in such case where user leave
    // smart phone for a period of time and come back, data still won't change
    fun getMovieData(): LiveData<List<MovieData>>{

        //Retrieved data from Repository
        mAllMovieData= MovieData_Repo.getDataSet()
        Log.d(TAG, "getMovieData(): ${mAllMovieData.value?.size}")

    return mAllMovieData
    }
}