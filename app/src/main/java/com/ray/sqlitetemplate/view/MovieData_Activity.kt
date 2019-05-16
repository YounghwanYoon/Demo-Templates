package com.ray.sqlitetemplate.view

import android.os.Bundle
import android.app.Activity
import com.ray.sqlitetemplate.R
import com.ray.sqlitetemplate.repository.model.MovieData
import com.ray.sqlitetemplate.repository.remote_data_source.MovieDataAPI_retrofit
import retrofit2.Call
import retrofit2.Retrofit

class MovieData_Activity : Activity(), MovieDataAPI_retrofit {
    override fun getMovieData(): Call<List<MovieData>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_data_)

        initRetrofit()
    }

    fun initRetrofit(){
        var retrofit_api :MovieDataAPI_retrofit
        var retrofit: Retrofit.Builder? = Retrofit.Builder().baseUrl(retrofit_api.BASE_URL)

    }
}
