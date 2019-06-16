package com.ray.srt_smi_converter.view

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.ray.srt_smi_converter.R
import com.ray.srt_smi_converter.viewmodel.SharedViewModel


class Select_Type_Fragment : Fragment() {
    private lateinit var selectBtn: Button
    private lateinit var mSharedVM:SharedViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_select__type_, container, false)
        button_setup(view)
        return view
    }

    fun button_setup(view:View){
        val currentItem = 1
        selectBtn = view.findViewById(R.id.select_file_button)
        selectBtn.setOnClickListener {
            mSharedVM.changeFragment(currentItem )
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        var mSelectedTextView = view?.findViewById<TextView>(R.id.selected_file_textview)

        mSharedVM = ViewModelProviders.of(this).get(SharedViewModel::class.java)
        mSharedVM.getDirectory().observe(this, Observer<String> {
            //update UI
            mSelectedTextView?.text = mSharedVM.getDirectory().value
        })
    }
}
