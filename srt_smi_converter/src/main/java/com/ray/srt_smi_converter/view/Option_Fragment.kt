package com.ray.srt_smi_converter.view

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.ray.srt_smi_converter.R
import com.ray.srt_smi_converter.model.SelectedFile
import com.ray.srt_smi_converter.viewmodel.SharedViewModel
import java.io.File


class Option_Fragment : Fragment() {
    private var TAG:String = this.javaClass.simpleName.toString()
    private lateinit var mSelectedTextView:TextView
    private lateinit var selectBtn: Button
    private lateinit var mSharedVM:SharedViewModel
    private lateinit var mConvertBtn:Button
    private val toListOfFile= 1

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        Log.d(TAG, "Option_Fragment onCreateView() is called")

        val view = inflater.inflate(R.layout.fragment_select__type_, container, false)
        button_setup(view)
        textview_setup(view)
        initSharedVM()
        return view
    }


    fun initSharedVM() {
        Log.d(TAG, "Init sharedVM")
        mSharedVM = ViewModelProviders.of(this.activity!!).get(SharedViewModel::class.java)
    }

    private fun button_setup(view:View){
        Log.d(TAG, "Option_Fragment button_setup() is called")

        selectBtn = view.findViewById(R.id.select_file_button)
        selectBtn.setOnClickListener {
            mSharedVM.changeFragment(toListOfFile)
        }
        mConvertBtn = view.findViewById(R.id.convert_button)
        mConvertBtn.setOnClickListener{
            Log.d(TAG, "mConvertBtn is called")
            mSharedVM.convertFile()
        }
    }

    private fun textview_setup(view:View){
        Log.d(TAG, "textview_setup is called")
        mSelectedTextView= view?.findViewById<TextView>(R.id.selected_file_textview)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        Log.d(TAG, "Option_Fragment onActivityCreated() is called")

        mSharedVM.getSelectedFile().observe(viewLifecycleOwner, Observer<File>{
            Log.d(TAG, "under observe Live SelectedFile: ${it.path}")
            changeText(it)
            //mSelectedTextView.text= it.path.toString()
        })
        Log.d(TAG, "observe() is called")
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "I am under onResume()")
        Log.d(TAG, "VM's selected file is ${mSharedVM.getSelectedFile().value}")
    }

    private fun changeText(file:File){
        //update UI
        Log.d(TAG, "changeText() is called")
        //mSelectedTextView?.text = mSharedVM.getSelectedFile().value?.name//sf?.name
        mSelectedTextView?.text = file.name
    }

    override fun onDestroyView() {
        Log.d(TAG, "onDestroyView is called")
        super.onDestroyView()
    }
}
