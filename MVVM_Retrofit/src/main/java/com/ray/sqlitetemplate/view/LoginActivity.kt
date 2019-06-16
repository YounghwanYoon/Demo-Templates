package com.ray.sqlitetemplate.view

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.annotation.TargetApi
import android.content.pm.PackageManager

import android.app.Activity
import android.app.LoaderManager.LoaderCallbacks
import android.content.CursorLoader
import android.content.Loader
import android.database.Cursor
import android.net.Uri
import android.os.AsyncTask
import android.os.Build
import android.os.Bundle
import android.provider.ContactsContract
import android.text.TextUtils
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.ArrayAdapter
import android.widget.TextView

import java.util.ArrayList
import android.Manifest.permission.READ_CONTACTS
import androidx.viewpager.widget.ViewPager
import androidx.appcompat.app.AppCompatActivity
import com.ray.sqlitetemplate.R
import com.ray.sqlitetemplate.R.id.*
import com.ray.sqlitetemplate.view.adapter.SectionStatePagerAdapter

import kotlinx.android.synthetic.main.activity_login.*

/**
 * A login screen that offers login via email/password.
 */
class LoginActivity : AppCompatActivity(){

    private val TAG = "LoginActivity.kt"
    private lateinit var mSectionStatePagerAdapter:SectionStatePagerAdapter
    private lateinit var mViewPager: androidx.viewpager.widget.ViewPager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        mSectionStatePagerAdapter = SectionStatePagerAdapter(supportFragmentManager)
        mViewPager = findViewById(R.id.container)

        //setup the pager
        setupViewPager(mViewPager, mSectionStatePagerAdapter)
    }

    private fun setupViewPager(viewPager: androidx.viewpager.widget.ViewPager, pagerAdapter: SectionStatePagerAdapter){
        val adapter = pagerAdapter
        //val adapter =  SectionStatePagerAdapter(getSupportFragmentManager())
        adapter.addFragment(Login_Fragment(),"LoginPage_Fragment")
        adapter.addFragment(CreateAccount_Fragment(), "CreateAccount_Fragment")
        mViewPager.adapter = adapter
    }

    fun setViewPager(fragmentNumber:Int){
        mViewPager.setCurrentItem(fragmentNumber)
    }
}
