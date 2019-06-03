package com.ray.sqlitetemplate.view

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import com.ray.sqlitetemplate.R

class CreateAccount_Fragment(): Fragment(){

    private lateinit var btnCreateAccount:Button
    private lateinit var btnBackToLoginFragment: Button

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_create,container,false)
        btnCreateAccount = view.findViewById<Button>(R.id.create_account_btn)

        btnBackToLoginFragment=view.findViewById<Button>(R.id.back_to_login_fragment)

        btnCreateAccount.setOnClickListener(object:View.OnClickListener{
            override fun onClick(p0: View?) {
                //TODO Verify Input and Handle Creating Account, Then go back to login page
                //verify
                //add
                //navigate back to login_fragment
            }
        })

        btnBackToLoginFragment.setOnClickListener(object:View.OnClickListener{
            override fun onClick(p0: View?) {
                Toast.makeText(activity, "Back to Login_Fragment", Toast.LENGTH_SHORT).show()
                //navtigate to Login_fragment
                (activity as LoginActivity).setViewPager(0)
            }
        })
        return view
    }
}