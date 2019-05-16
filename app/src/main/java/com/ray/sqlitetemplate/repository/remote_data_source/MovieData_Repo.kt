package com.ray.sqlitetemplate.repository.remote_data_source

import android.arch.lifecycle.MutableLiveData
import android.util.Log
import android.widget.Toast
import com.bumptech.glide.Glide.init
import com.ray.sqlitetemplate.repository.model.MovieData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/*
    Singleton
    Webservice-like data
 */
object MovieData_Repo {

    private lateinit var mMovieData:ArrayList<MovieData>
    val TAG = "MovieData_Repo"
    init{
        mMovieData
    }

    fun getDataSet(): MutableLiveData<List<MovieData>>{
        getDataFromRest()

        var data:MutableLiveData<List<MovieData>> = MutableLiveData()
        data.value = mMovieData

        return data
    }

    private fun getDataFromRest(){
        //Retrieve data using Retrofit2 from RESTFUL API
        //TODO Retrofit2

        var retrofit:Retrofit =
                Retrofit.
                        Builder()
                        .baseUrl(MovieDataAPI_retrofit.BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build()

        var mdAPI = retrofit.create(MovieDataAPI_retrofit::class.java)

        var callData: Call<List<MovieData>> = mdAPI.getMovieData()

        callData.enqueue(object: Callback<List<MovieData>> {
            override fun onFailure(call: Call<List<MovieData>>?, t: Throwable?) {
                Log.d(TAG, " Something went wrong: ${t!!.message}")
            }

            override fun onResponse(call: Call<List<MovieData>>?, response: Response<List<MovieData>>?) {
                Log.d(TAG, " Success: ${response.toString()}")

            }

        })
    }
}