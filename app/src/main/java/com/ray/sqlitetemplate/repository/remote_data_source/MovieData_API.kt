package com.ray.sqlitetemplate.repository.remote_data_source

import com.ray.sqlitetemplate.repository.model.MovieData
//import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.Call

interface MovieData_API {

    companion object {
        val BASE_URL: String
            get() = "https://simplifiedcoding.net/demos/"
    }

    @GET("marvel")
    fun getMovieData(): Call<List<MovieData>>

    //@GET("marvel")
    //fun listMovieData(): Observable<List<MovieData>>
}