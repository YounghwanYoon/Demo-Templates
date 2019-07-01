package com.ray.srt_smi_converter.view

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.ray.srt_smi_converter.R
import com.ray.srt_smi_converter.viewmodel.SharedViewModel


class Select_Type_Fragment : Fragment() {
    private var TAG:String = this.javaClass.simpleName.toString()

    private lateinit var selectBtn: Button
    private lateinit var mSharedVM:SharedViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        Log.d(TAG, "Select_Type_Fragment onCreateView() is called")

        val view = inflater.inflate(R.layout.fragment_select__type_, container, false)
        button_setup(view)
        return view
    }

    fun button_setup(view:View){
        Log.d(TAG, "Select_Type_Fragment button_setup() is called")

        val currentItem = 1
        selectBtn = view.findViewById(R.id.select_file_button)
        selectBtn.setOnClickListener {
            mSharedVM.changeFragment(currentItem )
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        Log.d(TAG, "Select_Type_Fragment onActivityCreated() is called")
        super.onActivityCreated(savedInstanceState)

        val mSelectedTextView = view?.findViewById<TextView>(R.id.selected_file_textview)

        mSharedVM = ViewModelProviders.of(this).get(SharedViewModel::class.java)
        mSharedVM.getDirectory().observe(this, androidx.lifecycle.Observer<String> {
            //update UI
            mSelectedTextView?.text = mSharedVM.getDirectory().value
        })
    }
}
