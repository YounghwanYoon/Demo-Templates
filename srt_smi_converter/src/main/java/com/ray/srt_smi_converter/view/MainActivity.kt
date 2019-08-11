package com.ray.srt_smi_converter.view

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.Window
import android.view.WindowManager
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProviders
import androidx.viewpager.widget.ViewPager
import com.ray.srt_smi_converter.R
import com.ray.srt_smi_converter.view.adapter.StatePagerAdapter
import com.ray.srt_smi_converter.viewmodel.SharedViewModel
import com.ray.srt_smi_converter.viewmodel.SubtitleHandler
import java.io.File

//FragmentActivity()
class MainActivity :  FragmentActivity(){
    private var TAG:String = this.javaClass.simpleName.toString()

private var mViewAdapter= StatePagerAdapter(supportFragmentManager)
    private lateinit var mViewPager:ViewPager
    private lateinit var mSharedVM:SharedViewModel

    // Storage Permissions
    protected val REQUEST_EXTERNAL_STORAGE = 1
    protected var PERMISSIONS_STORAGE =
            arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE)

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d(TAG, "MainActivity OnCreate() is called")
        super.onCreate(savedInstanceState)
        screenLayoutHandler()
        setContentView(R.layout.activity_main)
        mSharedVM = ViewModelProviders.of(this).get(SharedViewModel::class.java)
        mViewPager = findViewById(R.id.container_viewpager)
        setupViewPager(mViewPager, mViewAdapter)
    }

    fun setupViewPager(viewPager: ViewPager, adapter: StatePagerAdapter){
        Log.d(TAG, "MainActivity setupViewPager() is called")

        mSharedVM.setupViewPager(viewPager, adapter)
    }
    fun screenLayoutHandler(){
        Log.d(TAG, "MainActivity screenLayoutHandler() is called")

        //Remove Title Bar
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        //Remove Notification Bar
        getWindow()?.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        //Verify Permission for Storage
        let { verifyStoragePermissions(it)}
    }

    /**
     * Checks if the app has permission to write to device storage
     *
     * If the app does not has permission then the user will be prompted to grant permissions
     *
     * @param activity
     */
    fun verifyStoragePermissions(activity: Activity) {
        // Check if we have write permission
        val permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE)

        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            )
        }
    }
}
