package com.ray.sqlitetemplate.view.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.Glide.init
import com.ray.sqlitetemplate.R
import com.ray.sqlitetemplate.repository.model.MovieData

class MovieData_RecycleAdapter(private val movieList:ArrayList<MovieData>, private val mContext:Context):RecyclerView.Adapter<MovieData_RecycleAdapter.ViewHolder>(){

    companion object {
        private val TAG = "MovieData_RecycleAdapter"
    }

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): ViewHolder{
        val view: View =LayoutInflater.from(parent.context).inflate(R.layout.movie_each_row, parent,false)
        return ViewHolder(view, mContext)
    }

    //return size of list
    override fun getItemCount(): Int {
        return movieList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItems(movieList[position])
    }

    class ViewHolder(itemView:View, context:Context):RecyclerView.ViewHolder(itemView) {

        private lateinit var mMovieTitle_View:TextView
        private lateinit var mMovieImage_View: ImageView
        private lateinit var mCreatedBy_View:TextView
        private lateinit var mBio_View:TextView
        private val mContext= context

        init{
            mMovieTitle_View = itemView.findViewById<TextView>(R.id.movie_title)
            mMovieImage_View = itemView.findViewById(R.id.movie_image)
            mCreatedBy_View=itemView.findViewById(R.id.movie_createdby)
            mBio_View = itemView.findViewById(R.id.movie_bio)
        }

        @SuppressLint("LongLogTag")
        //bind view with each data
        fun bindItems(movieData: MovieData){
            Log.d(TAG, "bindItem")
            mMovieTitle_View.text = movieData.name

            Glide.with(this.mContext)
                    .asBitmap()
                    .load(movieData.imageurl)
                    .into(this.mMovieImage_View)

            mCreatedBy_View.text = movieData.createdby
            mBio_View.text = movieData.bio
        }
    }
}