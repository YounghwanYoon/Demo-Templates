package com.ray.sqlitetemplate.repository.remote_data_source

import com.ray.sqlitetemplate.repository.model.MovieData
import retrofit2.http.GET
import retrofit2.Call

interface MovieDataAPI_retrofit {

    val BASE_URL: String
        get() = "https://simplifiedcoding.net/demos/"

    @GET("marvel")
    fun getMovieData(): Call<List<MovieData>>
}