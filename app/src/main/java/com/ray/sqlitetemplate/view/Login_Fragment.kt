package com.ray.sqlitetemplate.view

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import com.google.android.gms.common.SignInButton
import com.ray.sqlitetemplate.R

class Login_Fragment: androidx.fragment.app.Fragment(){

    private lateinit var btnCreate: Button
    private lateinit var btnEmailLogin:Button
    private lateinit var btnGoogleLogin: SignInButton
    companion object {
        private val TAG = "Login_Fragment.kt"
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        Log.d(TAG, "onCreateView: Started")

        val view:View = inflater.inflate(R.layout.fragment_login, container, false)
        btnCreate = view.findViewById(R.id.activate_create_fragment)
        btnEmailLogin = view.findViewById(R.id.email_sign_in_button)
        btnGoogleLogin=view.findViewById(R.id.google_sign_in_btn)

        btnCreate.setOnClickListener(object: View.OnClickListener{
            override fun onClick(p0: View?) {
                Toast.makeText(activity, "Going to Create_Fragment", Toast.LENGTH_SHORT).show()
                // Navigate to create fragment
                (activity as LoginActivity).setViewPager(1)
            }
        })
        btnEmailLogin.setOnClickListener(object: View.OnClickListener{
            override fun onClick(p0: View?) {
                Toast.makeText(activity, "Going to Second Activity", Toast.LENGTH_SHORT).show()
                //Verify Login
                //verifyLogin()

                //Starting new activity
                val intent:Intent = Intent(activity, MovieData_Activity::class.java)
                startActivity(intent)
            }
        })
        btnGoogleLogin.setOnClickListener(object: View.OnClickListener{
            override fun onClick(p0: View?) {
                Toast.makeText(activity, "Going to Second Activity", Toast.LENGTH_SHORT).show()
                //Verify Login
                verifyLogin()

                //Starting new activity
                val intent:Intent = Intent(activity, MovieData_Activity.javaClass)
                startActivity(intent)
            }
        })

        return view
    }


    private fun verifyLogin(){
            //handle verfying login information

    }
}