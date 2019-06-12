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
    val selectBtn = findViewById<Button>(R.id.select_file_button)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mViewPager = findViewById(R.id.container_viewpager)
        setupViewPager(mViewPager, mViewAdapter)
        button_setup()
    }

    fun setupViewPager(viewPager: ViewPager, adapter: StatePagerAdapter){
        adapter.addFragment(Select_Type_Fragment())
        adapter.addFragment(List_Of_Files())
        viewPager.adapter = adapter
    }

    fun button_setup(){
        selectBtn.setOnClickListener {
            mViewPager.currentItem = 1
        }

    }
}
