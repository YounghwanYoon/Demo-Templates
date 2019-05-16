package com.ray.sqlitetemplate.view

import android.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ray.sqlitetemplate.R

/**
 * A placeholder fragment containing a simple view.
 */
class MovieData_ActivityFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_movie_data_, container, false)
    }
}
