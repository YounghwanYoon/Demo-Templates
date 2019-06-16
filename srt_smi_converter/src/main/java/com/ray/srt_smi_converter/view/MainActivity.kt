package com.ray.srt_smi_converter.view

import android.os.Bundle
import android.widget.Button
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProviders
import androidx.viewpager.widget.ViewPager
import com.ray.srt_smi_converter.R
import com.ray.srt_smi_converter.view.adapter.StatePagerAdapter
import com.ray.srt_smi_converter.viewmodel.SharedViewModel

class MainActivity : FragmentActivity() {
    private var TAG:String = this.javaClass.simpleName.toString()

private var mViewAdapter= StatePagerAdapter(supportFragmentManager)
    private lateinit var mViewPager:ViewPager
    private lateinit var mSharedVM:SharedViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mSharedVM = ViewModelProviders.of(this).get(SharedViewModel::class.java)
        mViewPager = findViewById(R.id.container_viewpager)
        setupViewPager(mViewPager, mViewAdapter)
    }

    fun setupViewPager(viewPager: ViewPager, adapter: StatePagerAdapter){
        mSharedVM.setupViewPager(viewPager, adapter)
    }
}
