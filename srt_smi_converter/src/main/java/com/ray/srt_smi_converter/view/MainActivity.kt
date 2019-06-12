package com.ray.srt_smi_converter.view

import android.os.Bundle
import android.widget.Button
import androidx.fragment.app.FragmentActivity
import androidx.viewpager.widget.ViewPager
import com.ray.srt_smi_converter.R
import com.ray.srt_smi_converter.view.adapter.StatePagerAdapter

class MainActivity : FragmentActivity() {
    private var mViewAdapter= StatePagerAdapter(supportFragmentManager)
    lateinit var mViewPager:ViewPager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mViewPager = findViewById(R.id.container_viewpager)
        setupViewPager(mViewPager, mViewAdapter)


        val selectBtn = findViewById<Button>(R.id.select_file_button)
        selectBtn.setOnClickListener {
            mViewPager.currentItem = 1
        }
    }

    fun setupViewPager(viewPager: ViewPager, adapter: StatePagerAdapter){
        adapter.addFragment(Select_Type_Fragment())
        adapter.addFragment(List_Of_Files())
        viewPager.adapter = adapter
    }
}
